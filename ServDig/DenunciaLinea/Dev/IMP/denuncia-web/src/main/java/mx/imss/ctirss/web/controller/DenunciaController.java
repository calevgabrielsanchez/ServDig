package mx.imss.ctirss.web.controller;



import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.model.DomicilioGeografico;
import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.catalogos.model.DlcActEconomica;
import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.catalogos.model.DlcStatus;
import mx.imss.ctirss.catalogos.model.DlcSubdelegacion;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcTipodocumento;
import mx.imss.ctirss.catalogos.model.DlcTiposformapago;
import mx.imss.ctirss.denuncia.vo.DenunciaVO;
import mx.imss.ctirss.denuncia.vo.DomicilioVO;
import mx.imss.ctirss.denuncia.vo.FormaPagoVO;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.utils.ConstantesBusiness;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltDerivaSub;
import mx.imss.ctirss.model.DltFormapago;
import mx.imss.ctirss.model.DltInfotrabajo;
import mx.imss.ctirss.model.DltMotivodenuncia;
import mx.imss.ctirss.model.DltMotivodenunciaPK;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.promocion.base.paginador.PatronesDenunciadosWrapperDataTable;
import mx.imss.ctirss.service.interfaces.ICatalogoService;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.web.bean.DenunciaDTO;
import mx.imss.ctirss.web.bean.DerivaDTO;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
@Controller
@RequestMapping(value="/denuncia")
@SessionAttributes("denunciaDTO")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DenunciaController extends AbstractController{
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private IDenunciaService<AbstractModel> denunciaServiceBean;
	

	@Autowired 
	private ImageCaptchaService captchaService;
	
	private static final String SESSION_BEAN=DenunciaDTO.SES_NAME;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(DenunciaController.class); 
	
	@RequestMapping(method=RequestMethod.GET)	
	public String getCreateForm(Model model, HttpServletRequest request) {		
		 logger.debug("Creando forma denuncia controller");
		 model.addAttribute( new DltDenuncia());
		 return "";
	}
	
	/* Devuelve la fecha del sistema para el cálculo de la vigencia */
	@RequestMapping(value="/obtenerFechaServidor", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		String date = ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY_DIAG);	
		return date;
	}
	
	@RequestMapping(value="/obtenerFechaMinDeriva", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaMinDeriva(@RequestBody Long cveFolioDenuncia, HttpServletRequest request){
		ArrayList listaDeriva = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDerivaSub  where  CVE_FOLIODENUNCIA = '"+ cveFolioDenuncia +"' order by CVE_DERIVASUB desc");
		ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia where  CVE_FOLIODENUNCIA = '"+ cveFolioDenuncia +"'");
		DltDenuncia den;
		DltDerivaSub der;
		Date fechaPresenta = null;
		if (listaDeriva!=null && listaDeriva.size()>0){
			der =  (DltDerivaSub) listaDeriva.get(0);
			fechaPresenta = der.getFecFechaDeriva();
		}else if(lista!=null && lista.size()>0){
			 den = (DltDenuncia) lista.get(0);
			 fechaPresenta = den.getFecFechaenv();
		 }
		
		String date = ConstantesBusiness.dateToStringFormat(fechaPresenta, ConstantesBusiness.DD_MM_YYYY_DIAG);	
		return date;
	}
	
	@RequestMapping(value="/obtenerFechaDerivacion", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaDerivacion(@RequestBody Long cveFolioDenuncia, HttpServletRequest request){
		ArrayList listaDeriva = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDerivaSub  where  CVE_FOLIODENUNCIA = '"+ cveFolioDenuncia +"' order by CVE_DERIVASUB desc");
		DltDerivaSub der;
		Date fechaPresenta = null;
		if (listaDeriva!=null && listaDeriva.size()>0){
			der =  (DltDerivaSub) listaDeriva.get(0);
			fechaPresenta = der.getFecFechaDeriva();
		}
		
		String date = ConstantesBusiness.dateToStringFormat(fechaPresenta, ConstantesBusiness.DD_MM_YYYY_DIAG);	
		return date;
	}
	
	
	/* Devuelve la fecha del sistema para el cálculo de la vigencia */
	@RequestMapping(value="/consultaDomicilio", method=RequestMethod.POST)
	public @ResponseBody String obtenerDomicilio(@RequestBody Long idDomicilio, HttpServletRequest request){
		 ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DgDomicilioGeografico dom where dom.domicilioId = "+ idDomicilio );
		 DgDomicilioGeografico dom = null;
		 if(lista!=null && lista.size()>0){
			 dom = (DgDomicilioGeografico)lista.get(0);
		 }
		return dom.getDgAsentamiento().getDgCatLocalidad().getNomLoc();
	}
	
	/* Devuelve la fecha del sistema para el cálculo de la vigencia */
	
	public DgDomicilioGeografico obtenerObjDomicilio(@RequestBody Long idDomicilio, HttpServletRequest request){
		 ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DgDomicilioGeografico dom where dom.domicilioId = "+ idDomicilio );
		 DgDomicilioGeografico dom = null;
		 if(lista!=null && lista.size()>0){
			 dom = (DgDomicilioGeografico)lista.get(0);
		 }
		return dom;
	}
	
		
	/* Devuelve la fecha del sistema para el cálculo de la vigencia */
	@RequestMapping(value="/consultaCurp")
	public ModelAndView consultaDenuncias(@ModelAttribute DltUsuarioden dltUsuarioden, BindingResult bR, HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		
		UserSession usrSession = (UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		if(dltUsuarioden==null || dltUsuarioden.getCveUsuarioden()==null)
			dltUsuarioden =(DltUsuarioden) request.getSession().getAttribute("dltUsuarioden");
		DenunciaDTO denunciaDTO =  (DenunciaDTO)request.getSession().getAttribute("denunciaDTO");
		ArrayList denuncias = (ArrayList) request.getSession().getAttribute("listaDenuncias");
		ModelAndView mwNext= new ModelAndView("consultaCurp");				
		denunciaDTO.setDenuncias(denuncias);
		mwNext.addObject("denunciaDTO", denunciaDTO);
		mwNext.addObject("dltUsuarioden", dltUsuarioden);
		mwNext.addObject("denuncias", denuncias);				
		//request.getSession().removeAttribute("listaDenuncias");		
		request.getSession().setAttribute("dltUsuarioden", dltUsuarioden);
		return mwNext;
		
	}
	
	@RequestMapping(value="/nueva/paginaPatronesComplemento", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<DltDatospatron> pagina(@RequestBody PatronesDenunciadosWrapperDataTable aoData ,HttpServletResponse response,
    		                                                        HttpServletRequest request) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new DltDatospatron());
		//send.setsSearch(aoData.)
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaPatronesDenunciados(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	

	@RequestMapping(value="/nueva/{idDenuncia}")
	public String nuevaDenuncia(@PathVariable("idDenuncia") String idDenuncia, @ModelAttribute DltUsuarioden dltUsuarioden, BindingResult bR, HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		if(idDenuncia != null){
			if(idDenuncia.trim().equalsIgnoreCase("nueva")){
				idDenuncia = "";
				ses.removeAttribute("datosDenuncia");
			}
						
			request.getSession().setAttribute("idDenuncia", idDenuncia);
			ses.setAttribute("idDenuncia", idDenuncia);
		}
		return "nueva";
	}
	
	@RequestMapping(value="/creaDenuncia", method=RequestMethod.POST )
	public @ResponseBody DenunciaVO creaDenuncia(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		DenunciaVO denuncia = new DenunciaVO();
		ses.removeAttribute("datosDenuncia");
		UserSession usu=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		denuncia.setTipoUsuario(usu.getIdTipoUsuario());
	
		return denuncia;
	}

	
	@RequestMapping(value="/cargaDenuncia", method=RequestMethod.POST )
	public @ResponseBody DltDenuncia cargaDenuncia(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		if(dltDenuncia != null){
			ArrayList denunciaList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia d where d.cveFoliodenuncia = '"+ dltDenuncia.getCveFoliodenuncia() +"'");
			if(denunciaList.size() == 1){
				dltDenuncia = (DltDenuncia)denunciaList.get(0);
			}
		}
		return dltDenuncia;
	}
	
	@RequestMapping(value="/cargaPersonas", method=RequestMethod.POST )
	public @ResponseBody ArrayList<DltPersona> cargaPersonas(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		ArrayList <DltPersona> list = null;
		if(dltDenuncia != null){
			list = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltPersona p where p.dltDenuncia.cveFoliodenuncia = "+ dltDenuncia.getCveFoliodenuncia());		
		}
		return list;
	}
	
	@RequestMapping(value="/cargaPatronPrincipal", method=RequestMethod.POST )
	public @ResponseBody DltDatospatron cargaPatronPrincipal(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		ArrayList <DltDatospatron> list = null;
		DltDatospatron patronPrincipal = null;
		if(dltDenuncia != null){
			list = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltDatospatron p where p.idPatronprincipal = 1 and p.dltDenuncia.cveFoliodenuncia = "+ dltDenuncia.getCveFoliodenuncia());			
			if(list != null && list.size() > 0){
			   patronPrincipal = (DltDatospatron)list.get(0);
			}else{
				patronPrincipal = new DltDatospatron();
			}
			
		}
		return patronPrincipal;
	}
	
	@RequestMapping(value="/cargaMotivosDenuncia", method=RequestMethod.POST )
	public @ResponseBody ArrayList cargaMotivosDenuncia(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		ArrayList <DltMotivodenuncia> list = null;		
		if(dltDenuncia != null){
			list = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltMotivodenuncia p where p.dltDenuncia.cveFoliodenuncia = "+ dltDenuncia.getCveFoliodenuncia());						
		}
		return list;
	}
	
	@RequestMapping(value="/cargaDatosTrabajo", method=RequestMethod.POST )
	public @ResponseBody DltInfotrabajo cargaDatosTrabajo(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		ArrayList <DltInfotrabajo> list = null;
		DltInfotrabajo dltInfotrabajo = null;
		if(dltDenuncia != null){
			list = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltInfotrabajo p where p.dltDenuncia.cveFoliodenuncia = "+ dltDenuncia.getCveFoliodenuncia());			
			if(list.size() == 1){
				dltInfotrabajo = (DltInfotrabajo)list.get(0);
			}
		}
		return dltInfotrabajo;
	}
	
	@RequestMapping(value="/cargaFormasPago", method=RequestMethod.POST )
	public @ResponseBody ArrayList cargaFormasPago(@RequestBody DltInfotrabajo dltInfotrabajo, HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		ArrayList <DltFormapago> list = null;		
		if(dltInfotrabajo != null){
			list = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltFormapago p where p.dltInfotrabajo.cveInfotrabajo = "+ dltInfotrabajo.getCveInfotrabajo());						
		}
		return list;
	}
	
	
	@RequestMapping(value="/enviar/{idDenuncia}", method=RequestMethod.POST)
	public @ResponseBody String enviar(@PathVariable("idDenuncia") String idDenuncia, SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response){

		DltDenuncia dltDenuncia = null;
		DltUsuarioden dltUsuarioden = null;

		UserSession usuario =(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		request.getSession().setAttribute("idDenuncia", idDenuncia);
		ArrayList denunciaList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia d where d.cveFoliodenuncia = '"+ idDenuncia +"'");
		if(denunciaList.size() == 1){
			dltDenuncia = (DltDenuncia)denunciaList.get(0);
		}

		request.getSession().setAttribute("numFolioDenuncia", dltDenuncia.getNumFoliodenuncia());

		DlcStatus dlcStatus = new DlcStatus();		

		ArrayList dlcStatusList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcStatus s where s.idStatus = 2"); 
		if(dlcStatusList.size() == 1){
			dlcStatus = (DlcStatus)dlcStatusList.get(0);
		}

		dltDenuncia.setDlcStatus(dlcStatus); 
		dltDenuncia.setIdStatus(new Long(2));            						


		dltDenuncia = (DltDenuncia)denunciaServiceBean.saveDenuncia(dltDenuncia, usuario);		

		return "";

	}
	
	@RequestMapping(value="/modificaDenuncia/{idDenuncia}",method=RequestMethod.POST)
	public String modificaDenuncia(@PathVariable("idDenuncia") String idDenuncia,@ModelAttribute DenunciaDTO denunciaDTO,
												BindingResult bdr,HttpServletRequest request, HttpSession ses, Model model ) {		
		
		 if(denunciaDTO != null){
			 // o lo consulto de nuevo o busco y presento la denuncia q viene cargada ya en el dto
			 Iterator<DltDenuncia> it = denunciaDTO.getDenuncias().iterator();
			 while(it.hasNext()){
				 DltDenuncia dn = (DltDenuncia)it.next();
				 if(dn.getCveFoliodenuncia() == new Long(idDenuncia).longValue()){
					 denunciaDTO.setDltDenuncia(dn);
					    Iterator personasIt = dn.getDltPersonas().iterator();
					    while(personasIt.hasNext()){
					    	 DltPersona pr = (DltPersona)personasIt.next();
					    	 if(pr.getCveTipodenunciante() == 1){ //Trabajador
					    		 denunciaDTO.setDltPersonaT(pr);
					    	 }
					    	 if(pr.getCveTipodenunciante() == 2){ //Beneficiario
					    		 denunciaDTO.setDltPersonaB(pr);
					    	 }
					    	 if(pr.getCveTipodenunciante() == 3){ //Representante
					    		 denunciaDTO.setDltPersonaRL(pr);
					    	 }
					    }
				 }
			 }			 
			 		 			 
		 }else{
			 denunciaDTO = new DenunciaDTO();			 
			 
		 }	
		 		
		 model.addAttribute("denunciaDTO", denunciaDTO);
		 return "denuncia/datosTrabajadorMain";
	}
	
	
	@RequestMapping(value="/agregaPatron/{idDenuncia}", method=RequestMethod.POST)
	public @ResponseBody DltDatospatron agregarPatron(@RequestBody DltDatospatron dltDatospatron,@PathVariable("idDenuncia") String idDenuncia, HttpServletRequest request){
		DltDenuncia dltDenuncia = null;
        DltDatospatron dp = null;
        System.out.println(dltDatospatron.getDesRfc());
        if(idDenuncia != null && !idDenuncia.trim().equalsIgnoreCase("")){   	
		    ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia denuncia where denuncia.cveFoliodenuncia = '"+ idDenuncia +"'");
			if(lista.size() ==1){
				dltDenuncia = (DltDenuncia)lista.get(0);
		    }

 	        dltDatospatron.setCveFoliodenuncia(dltDenuncia.getCveFoliodenuncia());
 	        dltDatospatron.setDltDenuncia(dltDenuncia);
 	        dltDatospatron.setFecFechareg(new Date());
 	        
 			dp = (DltDatospatron) catalogoServiceBean.agregar((DltDatospatron)dltDatospatron);
        }
        
		return dp;
	}
	

	/** Se crea primero la subdenuncia a la que se asociará el patron complemento **/
	@RequestMapping(value="/agregaPatronComplemento/{idDenuncia}", method=RequestMethod.POST)
	public @ResponseBody DltDatospatron agregaPatronComplemento(@RequestBody DltDatospatron dltDatospatron,@PathVariable("idDenuncia") String idDenuncia, HttpServletRequest request){
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		DltDenuncia dltDenuncia = null;
		
		if(idDenuncia != null && !idDenuncia.trim().equalsIgnoreCase("")){
			//traeer la denunci papa
			ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia denuncia where denuncia.cveFoliodenuncia = '"+ idDenuncia +"'");
			if(lista.size() ==1){
				dltDenuncia = (DltDenuncia)lista.get(0);
		    }
			
			DltDenuncia dltDenunciaComplemento = denunciaServiceBean.generaDenunciaPatronComplemento(dltDenuncia, dltDatospatron, user);
			
			
			dltDatospatron.setCveFoliodenuncia(dltDenunciaComplemento.getCveFoliodenuncia());
			dltDatospatron.setDltDenuncia(dltDenunciaComplemento);
		//	dltDatospatron.setNumSubfoliodenuncia(dltDenuncia.getNumFoliodenuncia());
			
		    
			
			dltDatospatron = (DltDatospatron) catalogoServiceBean.agregar((DltDatospatron)dltDatospatron);
		   
			
		}
		return dltDatospatron;
	}
	
	
	public boolean validaDenuncia(){
		//hay denuncia en sesion, true - false
		return false;
		
	}
	
	@RequestMapping(value="/generaDenuncia", method=RequestMethod.POST)
	public @ResponseBody DltDenuncia generaDenuncia(@RequestBody DltDenuncia dltDenuncia, HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);

		
	
		ArrayList catalogoD = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodenunciante dlcTipodenunciante where dlcTipodenunciante.cveTipodenunciante = '"+ dltDenuncia.getCveTipodenunciante() +"'");
		if(catalogoD.size() == 1){
			dltDenuncia.setDlcTipodenunciante((DlcTipodenunciante)catalogoD.get(0));
		}
		
		DlcStatus dlcStatus = new DlcStatus();
				
		ArrayList catStat = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcStatus dlcStatus where dlcStatus.idStatus = " + new Long(1) );	
		if(catStat.size() == 1){
			dlcStatus = (DlcStatus)catStat.get(0);
		}
		
		dltDenuncia.setDlcStatus(dlcStatus);
		dltDenuncia.setIdStatus(new Long(1));
	//	dltDenuncia.setCveSeqSubfolio(new BigDecimal(0));
		
	    dltDenuncia = (DltDenuncia)denunciaServiceBean.saveDenuncia(dltDenuncia, user);
		
		return dltDenuncia;
	}
	
	@RequestMapping(value="/borrarDomicilio", method=RequestMethod.POST)
	public @ResponseBody String borrarDomicilio(@RequestBody DgDomicilioGeografico domicilio, HttpServletResponse response, 
			                                                HttpServletRequest request, HttpSession ses){
		
		
		ArrayList<DltPersona> personasList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltPersona d where d.dgDomicilioGeografico.domicilioId = "+ domicilio.getDomicilioId());
		ArrayList<DltInfotrabajo> infoTrabajoList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltInfotrabajo d where d.idDomicilio = "+ domicilio.getDomicilioId());
		ArrayList<DltDatospatron> datosPatronList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDatospatron d where d.domicilioId = "+ domicilio.getDomicilioId());
		for(DltPersona per:personasList){
			per.setDgDomicilioGeografico(null);
			catalogoServiceBean.actualizar(per);
		}
		
		for(DltInfotrabajo info:infoTrabajoList){
			info.setIdDomicilio(null);
			catalogoServiceBean.actualizar(info);
		}
		
		for(DltDatospatron patron:datosPatronList){
			patron.setDomicilioId(null);
			catalogoServiceBean.actualizar(patron);
		}
		
	
		if(domicilio.getDomicilioId()!=null){
			denunciaServiceBean.eliminarDomicilio(domicilio.getDomicilioId());			
		}
			
		
		
	
	
		return "Exitoso";
	}
	
	
	@RequestMapping(value="/guardarDenuncia", method=RequestMethod.POST)
	public @ResponseBody DenunciaVO guardarDenuncia(@RequestBody DenunciaVO denunciaVO, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		System.out.print("El sexo es "+denunciaVO.getDatosTrabajadorVO().getTrabajador().getSexoTrabajador());
													
		UserSession usu=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);	
		if(denunciaVO.isFinalizado() || denunciaVO.isConsultaAcuse()){
			
			DgDomicilioGeografico domTrab = obtenerObjDomicilio( denunciaVO.getDatosTrabajadorVO().getTrabajador().getIdDomicilio(), request);
			request.getSession().setAttribute("domicilioTrabajador", domTrab);
			DgDomicilioGeografico domBen = null;
			DgDomicilioGeografico domRep = null;
			DgDomicilioGeografico domPat = null;
			DgDomicilioGeografico domCenTrab = null;
			
			if( denunciaVO.getDatosTrabajadorVO().getBeneficiario() !=null){
				domBen = obtenerObjDomicilio( denunciaVO.getDatosTrabajadorVO().getBeneficiario().getIdDomicilio(), request);
				request.getSession().setAttribute("domicilioBeneficiario", domBen);
			}
			if( denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal() !=null){
				domRep = obtenerObjDomicilio( denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getIdDomicilio(), request);
				request.getSession().setAttribute("domicilioRepresentante", domRep);
			}
			if( denunciaVO.getDatosCentroTrabajoVO() !=null){
				domCenTrab = obtenerObjDomicilio( denunciaVO.getDatosCentroTrabajoVO().getIdDomicilio(), request);
				request.getSession().setAttribute("domicilioCentroTrabajo", domCenTrab);
			}
			if( denunciaVO.getDatosPatronVO() !=null){
				domPat = obtenerObjDomicilio( denunciaVO.getDatosPatronVO().getIdDomicilio(), request);
				request.getSession().setAttribute("domicilioPatron", domPat);
			}
			
			//DlcTipodocumento documentoTrab = (DlcTipodocumento) request.getSession().getAttribute("documentoTrab");
			//DlcTipodocumento documentoBen =(DlcTipodocumento) request.getSession().getAttribute("documentoBen");
			//DlcTipodocumento documentoRep =(DlcTipodocumento) request.getSession().getAttribute("documentoRep");
		
			ArrayList denuncianteList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodenunciante d where d.cveTipodenunciante = "+ denunciaVO.getTipoDenunciante());
			if(denuncianteList!=null && denuncianteList.size()>0){
				DlcTipodenunciante denunciante =(DlcTipodenunciante) denuncianteList.get(0);
				request.getSession().setAttribute("denunciante", denunciante);
			}
			ArrayList documentoIDList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento d where d.cveTipodocumento = "+ denunciaVO.getDatosTrabajadorVO().getTrabajador().getDocOficial());
			if(documentoIDList!=null && documentoIDList.size()>0){
				DlcTipodocumento documentoTrab =(DlcTipodocumento) documentoIDList.get(0);
				request.getSession().setAttribute("documentoTrab", documentoTrab);
			}
			ArrayList documentoBenIDList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento d where d.cveTipodocumento = "+ denunciaVO.getDatosTrabajadorVO().getBeneficiario().getDocOficial());
			if(documentoBenIDList!=null && documentoBenIDList.size()>0){
				DlcTipodocumento documentoBen =(DlcTipodocumento) documentoBenIDList.get(0);
				request.getSession().setAttribute("documentoBen", documentoBen);
			}
			ArrayList documentoRepIDList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento d where d.cveTipodocumento = "+ denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getDocOficial());
			if(documentoRepIDList!=null && documentoRepIDList.size()>0){
				DlcTipodocumento documentoRep =(DlcTipodocumento) documentoRepIDList.get(0);
				request.getSession().setAttribute("documentoRep", documentoRep);
			}
			if(denunciaVO.getDatosPatronVO()!=null && denunciaVO.getDatosPatronVO().getGiroPatron()!=null){
				ArrayList listaActividad  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcActEconomica a where a.cveActEconomica = " + denunciaVO.getDatosPatronVO().getGiroPatron());
				if(listaActividad!=null && listaActividad.size()>0){
					DlcActEconomica actEco = (DlcActEconomica)listaActividad.get(0);
					request.getSession().setAttribute("actividadEconomica", actEco.getTxActividad());
				}
			}else{
				request.getSession().setAttribute("actividadEconomica", "");
			}
			
			String comprobantesPago = "";
			if(denunciaVO.getDatosCentroTrabajoVO()!=null && denunciaVO.getDatosCentroTrabajoVO().getFormasPago()!=null){
				ArrayList listComprobantesPago = (ArrayList) denunciaVO.getDatosCentroTrabajoVO().getFormasPago();
				if(comprobantesPago!=null){
					for(int i=0; i<listComprobantesPago.size();i++){
						FormaPagoVO pago = (FormaPagoVO)listComprobantesPago.get(i);
						ArrayList listaComprobantesPago  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcTiposformapago f where f.cveFormapago = " + pago.getCveFormaPago());
						if(listaComprobantesPago!=null){
							DlcTiposformapago fp = (DlcTiposformapago)listaComprobantesPago.get(0);
							comprobantesPago = comprobantesPago + ", " + fp.getDescFormapago();
						}
						
					}
				}
				
			}
			
			
			/*if(denunciaVO.getDatosCentroTrabajoVO()!=null && denunciaVO.getDatosCentroTrabajoVO().getPagoComprobantePago()!=null){
				ArrayList listaComprobantesPago  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcTiposformapago f where f.cveFormapago = " + denunciaVO.getDatosCentroTrabajoVO().getPagoComprobantePago().getCveFormaPago());
				if(listaComprobantesPago!=null && listaComprobantesPago.size()>0){
					DlcTiposformapago pagoVO = (DlcTiposformapago)listaComprobantesPago.get(0);
					request.getSession().setAttribute("formaPago", pagoVO.getDescFormapago());
				}
			}*/
			String documentacionAdjunta = "";
			if(denunciaVO.getDatosTrabajadorVO().getTrabajador()!=null && denunciaVO.getDatosTrabajadorVO().getTrabajador().getNombreDocumento()!=null){
				documentacionAdjunta +=  denunciaVO.getDatosTrabajadorVO().getTrabajador().getNombreDocumento();
			}
			if(denunciaVO.getDatosTrabajadorVO().getBeneficiario()!=null && denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombreDocumento()!=null && !denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombreDocumento().equals("")){
				documentacionAdjunta +=  ", " +denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombreDocumento();
			}
			if(denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal()!=null && denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombreDocumento()!=null && !denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombreDocumento().equals("")){
				documentacionAdjunta +=  ", " +denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombreDocumento();
			}
			
			String formaPago = "";
			String comprobantePago = "";
			String periodoPago = "";
			ArrayList listaInfoTrab  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DltInfotrabajo i where i.cveFoliodenuncia = " + denunciaVO.getCveDenuncia());
			if(listaInfoTrab!=null && listaInfoTrab.size()>0){
				DltInfotrabajo dltInfoTrab = (DltInfotrabajo) listaInfoTrab.get(0);
				if(dltInfoTrab!=null){
					ArrayList listaFormPag  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DltFormapago f where f.id.cveInfotrabajo = " + dltInfoTrab.getCveInfotrabajo());
					if(listaFormPag!=null && listaFormPag.size()>0){
						for(int i=0; i<listaFormPag.size(); i++){
							DltFormapago fompag = (DltFormapago)listaFormPag.get(i);
							if(fompag.getNomDocumento()!=null)
								documentacionAdjunta += ", "+ fompag.getNomDocumento();
							if(fompag.getDlcTipoconcepto().getCveConcepto()==2L){
								comprobantePago+= fompag.getDlcTiposformapago().getDescFormapago() ;
							}
							if(fompag.getDlcTipoconcepto().getCveConcepto()==1L){
								periodoPago+= fompag.getDlcTiposformapago().getDescFormapago() ;
							}
							if(fompag.getDlcTipoconcepto().getCveConcepto()==3L){
								String  descFormaPago = fompag.getDlcTiposformapago().getDescFormapago();
								if(fompag.getDlcTiposformapago().getCveFormapago().equals(ConstantesBusiness.TIPO_FORMA_PAGO_OTRO)){
									descFormaPago = (fompag.getDesEspecifique()==null?"":fompag.getDesEspecifique());
								}
								if(!descFormaPago.equals("")){
									formaPago+= descFormaPago + ", ";
								}
							}
						}
						if(formaPago.length()>2)
							formaPago = formaPago.substring(0, formaPago.length()-2);
					}
				}
			}
			request.getSession().setAttribute("comprobantePago", comprobantePago);
			request.getSession().setAttribute("periodoPago", periodoPago);
			request.getSession().setAttribute("formaPago", formaPago);
			request.getSession().setAttribute("documentacionAdjunta", documentacionAdjunta);
		}
		DenunciaVO den=denunciaVO;
		if (!denunciaVO.isConsultaAcuse()){
			den = denunciaServiceBean.guardarDenuncia(denunciaVO,usu);
		}
		
		
		if(denunciaVO.isFinalizado() || denunciaVO.isConsultaAcuse()){
			request.getSession().setAttribute("denunciaVO", den);
		}
		return den;
		
	
	}
	
	@RequestMapping(value="/ratificaDenuncia", method=RequestMethod.POST)
	public @ResponseBody DenunciaVO ratificaDenuncia(@RequestBody DenunciaVO denunciaVO, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		
		UserSession usu=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		request.getSession().setAttribute("folioAcuse", denunciaVO.getFolioDenuncia());
		if(denunciaVO.getCveSubdelegacion()!=null){
			ArrayList listaSub = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcSubdelegacion d where d.cveSubdelegacion = " + denunciaVO.getCveSubdelegacion());
			if(listaSub!=null && listaSub.size()>0){
				DlcSubdelegacion sub = (DlcSubdelegacion)listaSub.get(0);
				request.getSession().setAttribute("subAcuse", sub.getNomNombre());
				
			}
		}else{
			if(usu.getIdSubDelegacion()!=null){
				ArrayList listaSub = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcSubdelegacion d where d.cveSubdelegacion = " + usu.getIdSubDelegacion());
				if(listaSub!=null && listaSub.size()>0){
					DlcSubdelegacion sub = (DlcSubdelegacion)listaSub.get(0);
					request.getSession().setAttribute("subAcuse", sub.getNomNombre());
					
				}
			}
			
			
		}
		
		DgDomicilioGeografico domTrab = obtenerObjDomicilio( denunciaVO.getDatosTrabajadorVO().getTrabajador().getIdDomicilio(), request);
		request.getSession().setAttribute("domicilioTrabajador", domTrab);
		DgDomicilioGeografico domBen = null;
		DgDomicilioGeografico domRep = null;
		DgDomicilioGeografico domPat = null;
		DgDomicilioGeografico domCenTrab = null;
		
		if( denunciaVO.getDatosTrabajadorVO().getBeneficiario() !=null){
			domBen = obtenerObjDomicilio( denunciaVO.getDatosTrabajadorVO().getBeneficiario().getIdDomicilio(), request);
			request.getSession().setAttribute("domicilioBeneficiario", domBen);
		}
		if( denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal() !=null){
			domRep = obtenerObjDomicilio( denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getIdDomicilio(), request);
			request.getSession().setAttribute("domicilioRepresentante", domRep);
		}
		if( denunciaVO.getDatosCentroTrabajoVO() !=null){
			domCenTrab = obtenerObjDomicilio( denunciaVO.getDatosCentroTrabajoVO().getIdDomicilio(), request);
			request.getSession().setAttribute("domicilioCentroTrabajo", domCenTrab);
		}
		if( denunciaVO.getDatosPatronVO() !=null){
			domPat = obtenerObjDomicilio( denunciaVO.getDatosPatronVO().getIdDomicilio(), request);
			request.getSession().setAttribute("domicilioPatron", domPat);
		}
		
		//DlcTipodocumento documentoTrab = (DlcTipodocumento) request.getSession().getAttribute("documentoTrab");
		//DlcTipodocumento documentoBen =(DlcTipodocumento) request.getSession().getAttribute("documentoBen");
		//DlcTipodocumento documentoRep =(DlcTipodocumento) request.getSession().getAttribute("documentoRep");
	
		ArrayList denuncianteList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodenunciante d where d.cveTipodenunciante = "+ denunciaVO.getTipoDenunciante());
		if(denuncianteList!=null && denuncianteList.size()>0){
			DlcTipodenunciante denunciante =(DlcTipodenunciante) denuncianteList.get(0);
			request.getSession().setAttribute("denunciante", denunciante);
		}
		ArrayList documentoIDList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento d where d.cveTipodocumento = "+ denunciaVO.getDatosTrabajadorVO().getTrabajador().getDocOficial());
		if(documentoIDList!=null && documentoIDList.size()>0){
			DlcTipodocumento documentoTrab =(DlcTipodocumento) documentoIDList.get(0);
			request.getSession().setAttribute("documentoTrab", documentoTrab);
		}
		ArrayList documentoBenIDList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento d where d.cveTipodocumento = "+ denunciaVO.getDatosTrabajadorVO().getBeneficiario().getDocOficial());
		if(documentoBenIDList!=null && documentoBenIDList.size()>0){
			DlcTipodocumento documentoBen =(DlcTipodocumento) documentoBenIDList.get(0);
			request.getSession().setAttribute("documentoBen", documentoBen);
		}
		ArrayList documentoRepIDList = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento d where d.cveTipodocumento = "+ denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getDocOficial());
		if(documentoRepIDList!=null && documentoRepIDList.size()>0){
			DlcTipodocumento documentoRep =(DlcTipodocumento) documentoRepIDList.get(0);
			request.getSession().setAttribute("documentoRep", documentoRep);
		}
		if(denunciaVO.getDatosPatronVO()!=null && denunciaVO.getDatosPatronVO().getGiroPatron()!=null){
			ArrayList listaActividad  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcActEconomica a where a.cveActEconomica = " + denunciaVO.getDatosPatronVO().getGiroPatron());
			if(listaActividad!=null && listaActividad.size()>0){
				DlcActEconomica actEco = (DlcActEconomica)listaActividad.get(0);
				request.getSession().setAttribute("actividadEconomica", actEco.getTxActividad());
			}
		}else{
			request.getSession().setAttribute("actividadEconomica", "");
		}
		
		String comprobantesPago = "";
		if(denunciaVO.getDatosCentroTrabajoVO()!=null && denunciaVO.getDatosCentroTrabajoVO().getFormasPago()!=null){
			ArrayList listComprobantesPago = (ArrayList) denunciaVO.getDatosCentroTrabajoVO().getFormasPago();
			if(comprobantesPago!=null){
				for(int i=0; i<listComprobantesPago.size();i++){
					FormaPagoVO pago = (FormaPagoVO)listComprobantesPago.get(i);
					ArrayList listaComprobantesPago  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcTiposformapago f where f.cveFormapago = " + pago.getCveFormaPago());
					if(listaComprobantesPago!=null){
						DlcTiposformapago fp = (DlcTiposformapago)listaComprobantesPago.get(0);
						comprobantesPago = comprobantesPago + ", " + fp.getDescFormapago();
					}
					
				}
			}
			
		}
		
		
		/*if(denunciaVO.getDatosCentroTrabajoVO()!=null && denunciaVO.getDatosCentroTrabajoVO().getPagoComprobantePago()!=null){
			ArrayList listaComprobantesPago  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DlcTiposformapago f where f.cveFormapago = " + denunciaVO.getDatosCentroTrabajoVO().getPagoComprobantePago().getCveFormaPago());
			if(listaComprobantesPago!=null && listaComprobantesPago.size()>0){
				DlcTiposformapago pagoVO = (DlcTiposformapago)listaComprobantesPago.get(0);
				request.getSession().setAttribute("formaPago", pagoVO.getDescFormapago());
			}
		}*/
		String documentacionAdjunta = "";
		if(denunciaVO.getDatosTrabajadorVO().getTrabajador()!=null && denunciaVO.getDatosTrabajadorVO().getTrabajador().getNombreDocumento()!=null){
			documentacionAdjunta +=  denunciaVO.getDatosTrabajadorVO().getTrabajador().getNombreDocumento();
		}
		if(denunciaVO.getDatosTrabajadorVO().getBeneficiario()!=null && denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombreDocumento()!=null && !denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombreDocumento().equals("")){
			documentacionAdjunta +=  ", " +denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombreDocumento();
		}
		if(denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal()!=null && denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombreDocumento()!=null && !denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombreDocumento().equals("")){
			documentacionAdjunta +=  ", " +denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombreDocumento();
		}
		
		String formaPago = "";
		String comprobantePago = "";
		String periodoPago = "";
		ArrayList listaInfoTrab  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DltInfotrabajo i where i.cveFoliodenuncia = " + denunciaVO.getCveDenuncia());
		if(listaInfoTrab!=null && listaInfoTrab.size()>0){
			DltInfotrabajo dltInfoTrab = (DltInfotrabajo) listaInfoTrab.get(0);
			if(dltInfoTrab!=null){
				ArrayList listaFormPag  = (ArrayList) catalogoServiceBean.consultaLibrePorClave(1L, "from DltFormapago f where f.id.cveInfotrabajo = " + dltInfoTrab.getCveInfotrabajo());
				if(listaFormPag!=null && listaFormPag.size()>0){
					for(int i=0; i<listaFormPag.size(); i++){
						DltFormapago fompag = (DltFormapago)listaFormPag.get(i);
						if(fompag.getNomDocumento()!=null)
							documentacionAdjunta += ", "+ fompag.getNomDocumento();
						if(fompag.getDlcTipoconcepto().getCveConcepto()==2L){
							comprobantePago+= fompag.getDlcTiposformapago().getDescFormapago() ;
						}
						if(fompag.getDlcTipoconcepto().getCveConcepto()==1L){
							periodoPago+= fompag.getDlcTiposformapago().getDescFormapago() ;
						}
						if(fompag.getDlcTipoconcepto().getCveConcepto()==3L){
							String descFormaPago = fompag.getDlcTiposformapago().getDescFormapago();
							if(fompag.getDlcTiposformapago().getCveFormapago().equals(ConstantesBusiness.TIPO_FORMA_PAGO_OTRO)){
								descFormaPago = (fompag.getDesEspecifique()==null?"":fompag.getDesEspecifique());
							}
							if(!descFormaPago.equals("")){
								formaPago+= descFormaPago + ", ";
							}
						}
					}
					if(formaPago.length()>2)
						formaPago = formaPago.substring(0, formaPago.length()-2);
				}
			}
		}
		request.getSession().setAttribute("comprobantePago", comprobantePago);
		request.getSession().setAttribute("periodoPago", periodoPago);
		request.getSession().setAttribute("formaPago", formaPago);
		request.getSession().setAttribute("documentacionAdjunta", documentacionAdjunta);
		DenunciaVO den =denunciaServiceBean.ratificaDenuncia(denunciaVO,usu);
		
		request.getSession().setAttribute("denunciaVO", den);
		
		return den;
		
	
	}
	
	
	@RequestMapping(value="/cargaArchivo", method=RequestMethod.POST)
	public  String cargaArchivo(HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses) throws FileUploadException, IOException{
		String val="";
		boolean flag;
		boolean flagExtension=true;
		String errorExtension="";
		try{
			MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("Filedata");
			
			String[] listaExtensiones=ConstantesBusiness.EXTENSIONES_ARCHIVOS.split(",");
			for(int s=0;s<listaExtensiones.length;s++){
				if(file.getOriginalFilename().contains(listaExtensiones[s])){
					flagExtension=false;
					errorExtension="ERROR :El archivo que intenta adjuntar es de tipo "+listaExtensiones[s]+" y no es aceptado por el sistema, favor de anexar un archivo valido (pdf, jpg, png, bmp y gif).";
					break;
				}else if(file.getBytes().length>ConstantesBusiness.TAM_ARCHIVO){
					flagExtension=false;
					errorExtension="ERROR :El archivo no puede exceder 1Mb ";
					break;
				}
			}
			
			if(flagExtension){
				DltDenuncia den=new DltDenuncia();
				den.setCveFoliodenuncia(Long.valueOf(request.getParameter("cveDenuncia")));
				den.setCveTipodenunciante(Long.valueOf(request.getParameter("cveTipoDenunciante")));
				flag=denunciaServiceBean.guardaDocumentoPersona(den, file.getBytes(), Long.valueOf(request.getParameter("cveTipoDocumento")), file.getOriginalFilename());			
				val= file.getOriginalFilename();
				if(!flag){
					val="ERROR : No se ha podido adjuntar el archivo debido a que no se ha guardado el denunciante correspondiente";
				}
			}else{
				val=errorExtension;
			}
			
		}catch(Exception e){
			val= "ERROR: No se pudo adjuntar el archivo, favor de reintentar";
			e.printStackTrace();
		}
		ses.setAttribute("archivoAdjunto", val);
		return "refArchivo";
	}
	
	@RequestMapping(value="/cargaArchivoFormaPago", method=RequestMethod.POST)
	public  String cargaArchivoFormaPago(HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses) throws FileUploadException, IOException{
		String val="";
		boolean flag=false;
		boolean flagExtension=true;
		String errorExtension="";
		try{
			MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("Filedata");
			
			String[] listaExtensiones=ConstantesBusiness.EXTENSIONES_ARCHIVOS.split(",");
			for(int s=0;s<listaExtensiones.length;s++){
				if(file.getOriginalFilename().contains(listaExtensiones[s])){
					flagExtension=false;
					errorExtension="ERROR :El archivo que intenta adjuntar es de tipo "+listaExtensiones[s]+" y no es aceptado por el sistema, favor de anexar un archivo valido (pdf, jpg, png, bmp y gif).";
					break;
				}else if(file.getBytes().length>ConstantesBusiness.TAM_ARCHIVO){
					flagExtension=false;
					errorExtension="ERROR :El archivo no puede exceder 1Mb ";
					break;
				}
			}
			
			if(flagExtension){
				DltDenuncia den=new DltDenuncia();
				den.setCveFoliodenuncia(Long.valueOf(request.getParameter("cveDenuncia")));
				
				val=file.getOriginalFilename();
				flag=denunciaServiceBean.guardaFormaPago(den, file.getBytes(), Long.valueOf(request.getParameter("cveFormaPago")), file.getOriginalFilename());			
				if(!flag){
					val="ERROR : No se ha podido adjuntar el archivo debido a que no se ha guardado el tipo de Pago Correspondiente";
				}
			}else{
				val=errorExtension;
			}
			
			
		
		}catch(Exception e){
			e.printStackTrace();
			val= "ERROR: No se pudjo adjuntar el archivo, favor de reintentar";
		}
		ses.setAttribute("archivoAdjunto", val);
		return "refArchivo";
	}
	
	
	
	private byte[] getBytesFromRequest(HttpServletRequest request) throws IOException {  
        ServletInputStream is = request.getInputStream();  
        
        // Get the size of the file  
        int length = request.getContentLength();  
       
        // Create the byte array to hold the data  
        byte[] bytes = new byte[length];  
      
        // Read in the bytes  
        int offset = 0;  
        int numRead = 0;  
        while ((offset < bytes.length) && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {  
            offset += numRead;  
        }  
      
        System.out.println("offset "+offset);
        System.out.println(" "+bytes.length);
        
        // Ensure all the bytes have been read in  
        if (offset < bytes.length) {  
            throw new IOException("Could not completely read request body");  
        }  
      
        // Close the input stream and return bytes  
        is.close();  
        return bytes;  
    } 
	
	
	@RequestMapping(value="/consultarDenuncia/{idDenuncia}")
	public String consultarDenunci(@PathVariable("idDenuncia") String idDenuncia, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		DenunciaVO denunciaVO = new DenunciaVO();
		denunciaVO.setCveDenuncia(new Long(idDenuncia));
		UserSession usu=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		DltDenuncia dltDenuncia=new DltDenuncia();
		dltDenuncia.setCveFoliodenuncia((new Long(idDenuncia)));
		denunciaVO = denunciaServiceBean.consultaDenuncia(dltDenuncia);
		denunciaVO.setTipoUsuario(usu.getIdTipoUsuario());
		ses.setAttribute("idDenuncia", denunciaVO.getCveDenuncia().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			ses.setAttribute("datosDenuncia", mapper.writeValueAsString(denunciaVO));		
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "nueva";			
	}
	
	@RequestMapping(value="/recuperaDenunciaFinalizada/{idDenuncia}")
	public @ResponseBody DenunciaVO recuperaDenunciaFinalizada(@PathVariable("idDenuncia") String idDenuncia, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		DenunciaVO denunciaVO = new DenunciaVO();
		denunciaVO.setCveDenuncia(new Long(idDenuncia));
		UserSession usu=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		DltDenuncia dltDenuncia=new DltDenuncia();
		dltDenuncia.setCveFoliodenuncia((new Long(idDenuncia)));
		denunciaVO = denunciaServiceBean.consultaDenuncia(dltDenuncia);
		denunciaVO.setTipoUsuario(usu.getIdTipoUsuario());
		ses.setAttribute("idDenuncia", denunciaVO.getCveDenuncia().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			ses.setAttribute("datosDenuncia", mapper.writeValueAsString(denunciaVO));
//			ses.setAttribute("denunciaVOAcuse", denunciaVO)----------------------------------------------------------------
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return denunciaVO;			
	}
	
	
	@RequestMapping(value="/consultaDen")
	public String consultaDen(String idDenuncia, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		
		String nuFolio=request.getParameter("numeroFolioDenuncia");
		
		DltDenuncia dltDenuncia=denunciaServiceBean.getDenunciaByNumFolio(nuFolio);
		System.out.println("la denuncia es "+dltDenuncia);
		DenunciaVO denunciaVO = new DenunciaVO();
		if(dltDenuncia==null){
			return "nueva";
		}
		//dltDenuncia.setCveFoliodenuncia((new Long(idDenuncia)));
		denunciaVO = denunciaServiceBean.consultaDenuncia(dltDenuncia);
		denunciaVO.setFuncionario(true);
		
		ses.setAttribute("idDenuncia", denunciaVO.getCveDenuncia().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			ses.setAttribute("datosDenuncia", mapper.writeValueAsString(denunciaVO));		
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "nueva";			
	}
	
	
	

	@RequestMapping(value="/muestraPDF")
	public String muestraPDF(HttpServletResponse response,   HttpServletRequest request, HttpSession ses){
		System.out.println("SDFASDF");
		return "denuncia/ratificacionMain";			
	}
	
	@RequestMapping(value="/muestraRatPDF")
	public String muestraRatPDF(HttpServletResponse response,   HttpServletRequest request, HttpSession ses){
		System.out.println("SDFASDF");
		return "denuncia/ratificacionRatMain";			
	}
	
	@RequestMapping(value="/guardaPersona/{idDenuncia}", method=RequestMethod.POST)
	public @ResponseBody DltPersona agregarPersona(@RequestBody DltPersona dltPersona,@PathVariable("idDenuncia") String idDenuncia){
		DltDenuncia dltDenuncia = null;
		
     
        if(idDenuncia != null && !idDenuncia.trim().equalsIgnoreCase("")){     
		    ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia denuncia where denuncia.cveFoliodenuncia = '"+ idDenuncia +"'");
			if(lista.size() ==1){
				dltDenuncia = (DltDenuncia)lista.get(0);
				dltPersona.setCveFoliodenuncia(dltDenuncia.getCveFoliodenuncia());				
				dltPersona.setDltDenuncia(dltDenuncia);
				
				
				ArrayList catalogoD = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodenunciante dlcTipodenunciante where dlcTipodenunciante.cveTipodenunciante = '"+ dltPersona.getCveTipodenunciante() +"'");
				if(catalogoD.size() == 1){
					dltPersona.setDlcTipodenunciante((DlcTipodenunciante)catalogoD.get(0));
				}
				
				ArrayList documentoC = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcTipodocumento dlcTipodocumento where dlcTipodocumento.cveTipodocumento = '"+ dltPersona.getCveTipodocumento() +"'");
				if(documentoC.size() == 1){
					dltPersona.setDlcTipodocumento((DlcTipodocumento)documentoC.get(0));
				}

			    dltPersona.setFecFechareg(new Date());				
				dltPersona = (DltPersona)catalogoServiceBean.agregar((DltPersona)dltPersona);				
		    }									
        }
		return dltPersona;
	}
	
	
	@RequestMapping(value="/guardaMotivo/{cveFoliodenuncia}", method=RequestMethod.POST)
	public @ResponseBody DltMotivodenuncia agregaMotivoDenuncia(@RequestBody DltMotivodenunciaPK dltMotivodenunciaPK,@PathVariable("cveFoliodenuncia") String cveFoliodenuncia){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		DltMotivodenuncia dltMotivodenuncia = null;
		DltDenuncia dltDenuncia = null;
		try{
			
			if(cveFoliodenuncia != null && !cveFoliodenuncia.trim().equalsIgnoreCase("")){     
			    ArrayList lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia denuncia where denuncia.cveFoliodenuncia = '"+ cveFoliodenuncia +"'");				
			    if(lista.size() ==1){
					dltDenuncia = (DltDenuncia)lista.get(0);
					
					dltMotivodenuncia = new DltMotivodenuncia();
					dltMotivodenuncia.setDltDenuncia(dltDenuncia);
					dltMotivodenuncia.setId(dltMotivodenunciaPK);
					
					ArrayList catalogoD = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DlcMotivodenuncia dlcMotivodenuncia where dlcMotivodenuncia.cveMotivodenuncia = '"+ dltMotivodenunciaPK.getCveMotivodenuncia() +"'");
					if(catalogoD.size() == 1){
						dltMotivodenuncia.setDlcMotivodenuncia((DlcMotivodenuncia)catalogoD.get(0));
					}
					
					if(dltMotivodenunciaPK.getCveMotivodenuncia().longValue() == new Long(2).longValue() || 
							dltMotivodenunciaPK.getCveMotivodenuncia().longValue() == new Long(1).longValue() || 
							   dltMotivodenunciaPK.getCveMotivodenuncia().longValue() == new Long(4).longValue()){
						try{
							dltMotivodenuncia.setFecLabdelIngreso(formatter.parse(dltMotivodenunciaPK.getValorInicial()));
							dltMotivodenuncia.setFecLabalDejolab(formatter.parse(dltMotivodenunciaPK.getValorFinal()));
						}catch(ParseException pe){
						    logger.error(pe.getMessage());	
						}
						
					}else{
						try{
							dltMotivodenuncia.setImpSalarioReal(new BigDecimal(dltMotivodenunciaPK.getValorInicial().trim()));
							dltMotivodenuncia.setImpSalarioReg(new BigDecimal(dltMotivodenunciaPK.getValorFinal().trim()));
						}catch(Exception e){
							logger.error(e.getMessage());
						}
					}
	                
					dltMotivodenuncia.setFecFechareg(new Date());
					dltMotivodenuncia = (DltMotivodenuncia)catalogoServiceBean.agregar((DltMotivodenuncia)dltMotivodenuncia);										
			    }
			    ArrayList listaD = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia denuncia where denuncia.cveFoliodenuncia = '"+ dltDenuncia.getCveFoliodenuncia() +"'");				
			    if(listaD.size() ==1){
			    	dltDenuncia = (DltDenuncia)listaD.get(0);
			        denunciaServiceBean.actualizaNumFolio(dltDenuncia);
			    }
	        }
		}catch(Exception pe){
			
			logger.error(pe.getMessage());
		}
		
		return dltMotivodenuncia;
	}
	
	@RequestMapping(value="/enviaRatificacion")
	public String nuevaDenuncia(HttpServletResponse response, HttpServletRequest request, HttpSession ses){
		
		String idDenuncia = (String)request.getSession().getAttribute("idDenuncia");
		String numFolioDenuncia = (String)request.getSession().getAttribute("numFolioDenuncia");
		
		request.getSession().setAttribute("idDenuncia", idDenuncia);
		request.getSession().setAttribute("numFolioDenuncia", numFolioDenuncia);
		
		return "ratificacion";
	}
	
	
	
	@RequestMapping(value="/guardaDomicilio", method=RequestMethod.POST)
	public @ResponseBody DomicilioGeografico guardaDomicilio(@RequestBody DomicilioVO domicilioVO, HttpServletResponse response, HttpServletRequest request){
		System.out.println("");
		
		UserSession userSession = new UserSession();
			userSession = (UserSession)request.getSession().getAttribute("USR_SESSION");
		
		//SE genera el objeto de domicilio
		DomicilioGeografico domGeo = new DomicilioGeografico();
		domGeo.setCodigo(domicilioVO.getCodigo()!=null ? domicilioVO.getCodigo() : "" );
		domGeo.setCveAsen(domicilioVO.getCveAsen().equals("-1")?null: domicilioVO.getCveAsen() );
		domGeo.setCveEnt(domicilioVO.getCveEnt());
		domGeo.setCveLoc(domicilioVO.getCveLoc());
		domGeo.setCveMun(domicilioVO.getCveMun());
		if(domicilioVO.getNomVial().equals("") || domicilioVO.getNomVial()==null){
			domGeo.setNomVial("SIN NOMBRE");
		}else
			domGeo.setNomVial(domicilioVO.getNomVial());
		
		domGeo.setNumExtNum(domicilioVO.getNumExtNum().intValue());
		domGeo.setNumExtAlf(domicilioVO.getNumExtAlf());
		domGeo.setNumIntNum(domicilioVO.getNumIntNum().intValue());
		domGeo.setNumIntAlf(domicilioVO.getNumIntAlf());
		domGeo.setCvePeriodo(1);
		domGeo.setCveTipoDom(1);
		domGeo.setCveUsuario(userSession.getNomUsuarioSistema());
		domGeo.setFechaHoraAlta(new Date());
		domGeo.setCveViaPrin(domicilioVO.getCveViaPrin());
		domGeo.setCveViaRef1(domicilioVO.getCveViaRef1());
		domGeo.setCveViaRef2(domicilioVO.getCveViaRef2());
		domGeo.setCveViaRef3(domicilioVO.getCveViaRef3());
		domGeo.setDescripcion(domicilioVO.getDescripc());
		
		
		catalogoServiceBean.agregar(domGeo);
		ArrayList listaDoms =  (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DomicilioGeografico d where d.cveUsuario = '"+userSession.getNomUsuarioSistema() +"' order by d.domicilioId desc");
		if(listaDoms!=null && listaDoms.size()>0){
			domGeo = (DomicilioGeografico)listaDoms.get(0);
		}
		
		
		return domGeo;
	}
	
	@RequestMapping(value="/consultaUsuario", method=RequestMethod.POST)
	public @ResponseBody DltUsuarioden consultaUsuario (@RequestBody DltUsuarioden user, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		if(user==null || user.getDesEmail()==null || user.getDesEmail().equals("")){
			user = new DltUsuarioden();
			String mail =(String) request.getSession().getAttribute("recUsu");
			user.setDesEmail(mail);
		}
		DltUsuarioden usuario = new DltUsuarioden();
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden d where d.desEmail = '"+ user.getDesEmail() +"'");
		if(lista!=null && lista.size()>0){
			usuario = (DltUsuarioden) lista.get(0);
		}
	return usuario;
	}
	
	@RequestMapping(value="/obtenerPreguntasPorID", method=RequestMethod.POST)
	public @ResponseBody ArrayList obtenerPreguntasPorID(@RequestBody long cvePregunta, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		ArrayList listaPreguntas = new ArrayList();
		listaPreguntas = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DlcPregunta p where p.cvePregunta = " + cvePregunta);
		return listaPreguntas;
	}
	
	
	@RequestMapping(value="/recuperarContrasena", method=RequestMethod.POST)
	public @ResponseBody int recuperaContrasena(@RequestBody DltUsuarioden usuario, HttpServletResponse response,HttpServletRequest request){
				//SE INICIA LA VALIDACION DEL CAPTCHA
				//Verificamos existencia de usuario
				ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden rn where rn.desEmail = '"+usuario.getDesEmail()+"'");
				if(lista.size()>0){
					DltUsuarioden usuarioRegistrado = (DltUsuarioden)lista.get(0);
					if(usuarioRegistrado!=null && usuarioRegistrado.getDesRespuesta().equals(usuario.getDesRespuesta())){
							//enviamos Correo
							System.out.println("Se envia correo");
							enviaCorreo(usuario.getDesEmail(), usuarioRegistrado.getDesEmail(), usuarioRegistrado.getDesPassword());
							return 3;
						}else
							return 2;
					}
			
				
				
				
				return 2;
			}
			
	
	public boolean validaCaptcha(HttpServletRequest request, String captchaElement){
		boolean isResponseCorrect = false;
		String captchaId = request.getSession().getId();
		String response = captchaElement;

		try { 
		if(response != null){
			isResponseCorrect = captchaService.validateResponseForID(captchaId, response);
		}
		} catch (CaptchaServiceException e) {

		}
		return isResponseCorrect;
	}
	
	
	@RequestMapping(value = "/redireccionConsulta", method = RequestMethod.POST)
	public String redireccionConsulta(HttpServletResponse response, HttpServletRequest request) {
	//	UserSession usrSession = super.getUsuarioFirmado(request);
		//final DlcMenu menuParam = new DlcMenu();
		//menuParam.setIdUsuario(usrSession.getCveIdUsuario());
		//menuParam.setIdPerfil(dlcUsuario.getIdPerfil());
		//usrSession.setMenu(this.transformarMenu(menuServiceBean.consultar(menuParam)),request);
		logger.debug("camino a welcome");
		return "consultaCurp";
	}
	
	@RequestMapping(value = "/redireccionConsultaFunc", method = RequestMethod.POST)
	public String redireccionConsultaFunc(HttpServletResponse response, HttpServletRequest request) {
	//	UserSession usrSession = super.getUsuarioFirmado(request);
		//final DlcMenu menuParam = new DlcMenu();
		//menuParam.setIdUsuario(usrSession.getCveIdUsuario());
		//menuParam.setIdPerfil(dlcUsuario.getIdPerfil());
		//usrSession.setMenu(this.transformarMenu(menuServiceBean.consultar(menuParam)),request);
		logger.debug("camino a welcome");
		return "consultaCurpFunc";
	}
	
	
	public void enviaCorreo(String toCorreo, String usuario, String contrasena){
		try
        {
			//Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "11.254.171.213");
            //props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "denuncia.enlinea@imss.gob.mx");
            props.setProperty("mail.smtp.auth", "true");
       //     props.setProperty("mail.mime.charset","ISO-8859-1");
            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("denuncia.enlinea@imss.gob.mx"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toCorreo));
            message.setSubject("Recuperación de Contraseña Denuncia en Línea IMSS","UTF-8");
          
            message.setContent(
            	" Contrase&ntilde;a de Acceso al Sistema <br><br>" +
            	" Estimado  Usuario <b>" + usuario + "<b> <br><br>" +
            	" Ha recuperado satisfactoriamente su CONTRASE&Ntilde;A DE ACCESO registrada en el sistema, a fin de "+
            	" que pueda ingresar al mismo para continuar con la captura de los datos correspondientes a su "+
            	" denuncia, y consultar el estado que guarda la misma. "+
            	" CONTRASE&Ntilde;A: <b>" + contrasena + "</b> <br><br>" +
            	" Se le recomienda de nueva cuenta resguardar y recordar la contrase&ntilde;a registrada y no <br>" +
            	" proporcionarla a ninguna persona ajena a su confianza, toda vez que es responsabilidad del <br>" +
            	" usuario el correcto uso de la misma, as&iacute; como de la informaci&oacute;n capturada en el Sistema, por lo <br>" +
            	" que el Instituto Mexicano del Seguro Social no se hace responsable del mal uso que se pudiera <br>" +
            	" ejercer sobre la misma, su olvido o extrav&iacute;o. <br><br>" +
            	" En caso de recibir esta comunicaci&oacute;n por error, no obstante encontrarse dirigida a persona " +
            	" diversa, omita su reproducci&oacute;n, eliminando el mensaje y archivo adjunto." +
            	"<br><br>" +
            	" Agradecemos su registro en el sistema  <br>"
            	, "text/html; charset=ISO-8859-1");
            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("denuncia.enlinea@imss.gob.mx", "Denunci@2012*");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
        	logger.debug("error al enviar correo");
        	logger.error("error al enviar correo");
            e.printStackTrace();
        }
	}
	
	
	public void enviaMail(String toCorreo, String subject, String mensaje){
		try
        {
			//Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "11.254.171.213");
            //props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "denuncia.enlinea@imss.gob.mx");
            props.setProperty("mail.smtp.auth", "true");
       //     props.setProperty("mail.mime.charset","ISO-8859-1");
            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("denuncia.enlinea@imss.gob.mx"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toCorreo));
            message.setSubject(subject,"UTF-8");
          
            message.setContent(mensaje, "text/html; charset=ISO-8859-1");
            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("denuncia.enlinea@imss.gob.mx", "Denunci@2012*");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
        	logger.debug("error al enviar correo");
        	logger.error("error al enviar correo");
            e.printStackTrace();
        }
	}
	
	
	
	
	@RequestMapping(value="/mostrarImagen", method=RequestMethod.POST)
	public @ResponseBody String mostrarImagen(@RequestBody DenunciaVO denunciaVO, HttpServletResponse response, HttpServletRequest request){
			DltPersona dltPersona=null;			
			ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltPersona p where p.cveFoliodenuncia = " + denunciaVO.getCveDenuncia() + " and p.cveTipodenunciante = " + denunciaVO.getTipoDenunciante());
			request.getSession().removeAttribute("dltPersona");
			if(lista!=null && lista.size()>0){
				dltPersona =(DltPersona)lista.get(0);
				request.getSession().setAttribute("dltPersona",dltPersona);
		    }
	  return "";
	}	
	
	@RequestMapping(value="/mostrarImagenPago", method=RequestMethod.POST)
	public @ResponseBody String mostrarImagenPago(@RequestBody DenunciaDTO denunciaDTO, HttpServletResponse response, HttpServletRequest request){
			DltInfotrabajo dltInfotrabajo = null;
			
			ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltInfotrabajo it where it.cveFoliodenuncia = " + denunciaDTO.getDltDenuncia().getCveFoliodenuncia());
			if(lista!=null && lista.size()>0){
				request.getSession().removeAttribute("dltFormaPago");
				dltInfotrabajo =(DltInfotrabajo)lista.get(0);
				Set<DltFormapago> formaPagos = dltInfotrabajo.getDltFormapagos();
				Iterator itera = formaPagos.iterator();
				DltFormapago formaPago = null;
				Integer idTipoPago = denunciaDTO.getFormaPagoVO().getCveFormaPago();
				while(itera.hasNext()){
					formaPago = (DltFormapago)itera.next();
					if(formaPago.getDlcTiposformapago().getCveFormapago().equals(idTipoPago.longValue())){
						request.getSession().setAttribute("dltFormapago",formaPago);
						break;
					}
				}
				
		    }
	  return "";
		
	}	
	
	@RequestMapping(value="/derivacion", method=RequestMethod.POST)
	public String derivacion(HttpServletResponse response, HttpServletRequest request){
		request.setAttribute("cveFolioDenuncia", request.getParameter("cveFolioDenuncia"));
		return "derivacion";
	}
	
	@RequestMapping(value="/seguimiento", method=RequestMethod.POST)
	public String seguimiento(HttpServletResponse response, HttpServletRequest request){
		request.setAttribute("cveFolioDenuncia", request.getParameter("cveFolioDenuncia"));
		return "seguimiento";
	}
	
	
	@RequestMapping(value="/guardarDerivacion", method=RequestMethod.POST)
	public @ResponseBody String guardarDerivacion(@RequestBody DerivaDTO deriva, HttpServletResponse response, HttpServletRequest request ){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		UserSession usu=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		DltDerivaSub objDeriva = new DltDerivaSub();
		objDeriva.setCveFolioDenuncia(deriva.getCveFolioDenuncia());
		objDeriva.setCveIdUsuarioFuncionario(usu.getCveIdFuncionario());
		objDeriva.setDlcStatus(new DlcStatus());
		objDeriva.getDlcStatus().setIdStatus(ConstantesBusiness.ESTATUS_RATIFICADA);
		objDeriva.setDesCorreo(deriva.getDesCorreo());
		objDeriva.setDesInstrucciones(deriva.getDesInstrucciones());
		objDeriva.setDlcSubdelegacion(new DlcSubdelegacion());
		objDeriva.getDlcSubdelegacion().setCveSubdelegacion(deriva.getCveSubdelegacion());
		try {
			objDeriva.setFecFechaDeriva(formatter.parse(deriva.getFecFechaDeriva().toString()));
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		objDeriva.setFecFechaReg(new Date());
		denunciaServiceBean.saveDerivaSub(objDeriva);
		String subject = "Falta el subject";
		String mensaje = "falta mensaje";
		enviaMail(deriva.getDesCorreo(), subject, mensaje);
		return "";
	}

	@RequestMapping(value="/guardarSeguimiento", method=RequestMethod.POST)
	public @ResponseBody String guardarSeguimiento(@RequestBody DerivaDTO deriva, HttpServletResponse response, HttpServletRequest request ){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DltDerivaSub seguimiento = new DltDerivaSub();
		seguimiento.setDlcStatus(new DlcStatus());
		seguimiento.getDlcStatus().setIdStatus(deriva.getIdStatus());
		try {
			seguimiento.setFechFechaConclusion(formatter.parse(deriva.getFechFechaConclusion()));
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		seguimiento.setDesSeguimiento(deriva.getDesInstrucciones());
		seguimiento.setCveFolioDenuncia(deriva.getCveFolioDenuncia());
		denunciaServiceBean.guardarSeguimiento(seguimiento);
		return "";
	}
	
	@RequestMapping(value="/recuperarDerivaciones", method=RequestMethod.POST)
	public @ResponseBody ArrayList recuperarDerivaciones(@RequestBody Long cveFolioDenuncia, HttpServletResponse response, HttpServletRequest request ){
		ArrayList<AbstractModel> lista = (ArrayList<AbstractModel>)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDerivaSub where CVE_FOLIODENUNCIA = '"+ cveFolioDenuncia +"' order by CVE_DERIVASUB");
		ArrayList<DltDerivaSub> derivaciones = new ArrayList<DltDerivaSub>();
		int i=1;
		for (AbstractModel der: lista){
			DltDerivaSub deriva = (DltDerivaSub) der;
			deriva.setCveDerivasub(i++);
			derivaciones.add(deriva);
		}
		return derivaciones;
	}
}
