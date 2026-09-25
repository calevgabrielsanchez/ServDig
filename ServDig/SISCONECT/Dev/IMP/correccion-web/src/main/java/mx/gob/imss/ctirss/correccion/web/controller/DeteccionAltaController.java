package mx.gob.imss.ctirss.correccion.web.controller;

import java.beans.Beans;
import java.math.BigDecimal;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtDeteccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.model.UsuarioFirmadoVO;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;
import mx.gob.imss.ctirss.domiciliosInegi.web.controller.DomGeograficosController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/deteccion/alta")
public class DeteccionAltaController extends AbstractController{

	@Autowired
	private DeteccionService<CrtDeteccion> deteccionServiceBean;
	
	@Autowired
	private DeteccionService<SatUbicacion> ubicacionServiceBean;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
		
	@Autowired
	private IPatronesService patronesService;
	
	@Autowired private ICatalogoService<SegUsuario> usuarioService;
	
	
	@Autowired
	protected DomicilioServiceBusinessRemote domiciliosServiceBean;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		   model.addAttribute(new CrtDeteccion());
		   model.addAttribute(ConstantesSession.DOMICILIOS_GEOGRAFICOS_MODEL,new DgDomicilioGeografico());
		   
		   removeDomicilioInegiSession(request);
		   
    	 return "deteccion/alta/deteccionAltaMain";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String getCreateGenericForm(Model model) {
		
		 return "deteccionAlta/modalAlta";
	}
	
	@RequestMapping(value="/deteccionDomGeografico" , method=RequestMethod.GET)
	public String callDomGeograficos(HttpServletResponse response, HttpServletRequest request, Model model) {
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);		
		
		DgDomicilioGeografico dg = dom!=null ? (DgDomicilioGeografico)dom.get("dom") : null;
		
		return new DomGeograficosController().getCreateGenericForm(model,dg,request);
		
	}	
	
	@RequestMapping(value="/obtenerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtDeteccion obtenerDomicilio(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		String numExt = "";
		String numInt = "";
		if(dom!=null){
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");		
			deteccion.setRefColonia(dg.getDgAsentamiento().getNomAsen());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
				deteccion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
				deteccion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
			if(dg.getNomvial()!=null){
				if(!dg.getNomvial().equalsIgnoreCase(""))
					deteccion.setDomCalle(dg.getNomvial());
				else
					deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
			}else{
				deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
			}
			if(dg.getNumextnum() != null){
				numExt = dg.getNumextnum().toString();
			}
			if(dg.getNumextalf() != null && !dg.getNumextalf().equals("")){
				numExt += " - " + dg.getNumextalf();
			}
			deteccion.setNumNroext(numExt);
			if(dg.getNumintnum() != null){
				numInt = dg.getNumintnum().toString();
			}
			if(dg.getNumintalf() != null && !dg.getNumintalf().equals("")){
				numInt += " - " + dg.getNumintalf();
			}
			deteccion.setNumNroint(numInt);
			deteccion.setNumCodigopostal(dg.getDgCodigosPostales().getId().getCodigo());
			deteccion.setDomicilioInegi(dg);
		}
			
		return deteccion;
	}
	
	@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,
			HttpServletRequest request) {
		
		domicilioInegi.setHastableKeyDG("dom");
		
		return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
	}
	
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrtDeteccion> consultar(@RequestBody CrtDeteccion deteccion,HttpServletRequest request , HttpServletResponse response)  {
		
		List<CrtDeteccion> rsFinal = new ArrayList<CrtDeteccion>();
		String sessionDelegacion = "";
		String dgDelegacion = "";
		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getNombreDelegacion() != null){
			sessionDelegacion = session.getNombreDelegacion().toUpperCase();
		}
		if(dg != null && dg.getDgCatLocalidad() != null && dg.getDgCatLocalidad().getDgCatMunicipio() != null && 
				dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado() != null && dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt() != null){
			dgDelegacion = dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().toUpperCase();
		}
		if(!sessionDelegacion.equals("") && !dgDelegacion.equals("")){
			if(sessionDelegacion.equals(dgDelegacion)){
				Domicilio domiBdtu=new Domicilio();
				domiBdtu.setClave(new Long(dg.getDomicilioId()).intValue());
				try {
					domiBdtu=domiciliosServiceBean.consultarDomicilio(domiBdtu);
				} catch (DomicilioNoLocalizadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DgDomicilioGeografico nuevo=new DgDomicilioGeografico();
				nuevo.setDomicilioBDTU(domiBdtu);
				
				List<DgDomicilioGeografico> domicilios = new ArrayList<DgDomicilioGeografico>(); 
				domicilios.add(nuevo);
				//List<DgDomicilioGeografico> domicilios = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DgDomicilioGeografico dg where dg.dgCodigosPostales.id.codigo = '" +dg.getDgCodigosPostales().getId().getCodigo()+ "'" );
				if(domicilios != null && domicilios.size()>0){
					for (Iterator iterator = domicilios.iterator(); iterator.hasNext();) {
						DgDomicilioGeografico dgDomicilioGeografico = (DgDomicilioGeografico) iterator.next();
						CrtDeteccion detFind = new CrtDeteccion();
						detFind.setDomicilioId((int) dgDomicilioGeografico.getDomicilioId());
						//Implementacion anterior
//						ArrayList lstDet = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtDeteccion d where d.domicilioId = " +detFind.getDomicilioId().intValue() );
//						if(lstDet != null && lstDet.size()>0){
//							CrtDeteccion temp = (CrtDeteccion)lstDet.get(0);
//							rsFinal.add(temp);
//						}
						//Implementacion anterior						
						
						//Se cambia por 
						String codigoPostal=nuevo.getDomicilioBDTU().getCodigoPostal().getCodigoPostal();
						String cpRecuperado;
						ArrayList detecciones = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtDeteccion");
						for(Object ob:detecciones){
							//por cada deteccion se busca el domicilio y se compara el codigo postal
							CrtDeteccion de=(CrtDeteccion) ob;
							cpRecuperado=recuperaCodigoPostal(de.getDomicilioId());
							if(cpRecuperado!=null && cpRecuperado.equals(codigoPostal)){
								rsFinal.add(de);
							}							
						}
						//Se cambia por
					}					
				}
			}else{
				CrtDeteccion detEdo = new CrtDeteccion();
				detEdo.setEstatus("diferente");
				rsFinal.add(detEdo);
			}	
		}
		
		if(rsFinal != null && rsFinal.size()>0){
			return rsFinal;
		}else
			return null;
	}
	
	
	private String recuperaCodigoPostal(Integer cveDomicilio){
		Domicilio domiBdtu=new Domicilio();
		domiBdtu.setClave(cveDomicilio);
		try {
			domiBdtu=domiciliosServiceBean.consultarDomicilio(domiBdtu);
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
			
		}
		return domiBdtu.getCodigoPostal().getCodigoPostal();
	}
	
	
	private Domicilio recuperaDomicilio(Integer cveDomicilio){
		Domicilio domiBdtu=new Domicilio();
		domiBdtu.setClave(cveDomicilio);
		try {
			domiBdtu=domiciliosServiceBean.consultarDomicilio(domiBdtu);
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
			
		}
		return domiBdtu;
	}
	
	
	@RequestMapping(value="/validar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> valida(@RequestBody CrtDeteccionWrapperDataTable aoData,  HttpServletResponse response,HttpServletRequest request ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		List<CrtDeteccion> rsFinal = new ArrayList<CrtDeteccion>();
			
		if(aoData.getoForm().getRefColonia()!= null && aoData.getoForm().getDomCalle()!= null &&
				aoData.getoForm().getNumNroext()!= null &&	aoData.getoForm().getNumCodigopostal()!= null){
			if(!aoData.getoForm().getRefColonia().equalsIgnoreCase("") && !aoData.getoForm().getDomCalle().equalsIgnoreCase("") &&
					!aoData.getoForm().getNumNroext().equalsIgnoreCase("") && !aoData.getoForm().getNumCodigopostal().equalsIgnoreCase("")){
				
				send.parserArray(aoData.getAoData());
				send.setModelo(aoData.getoForm());
				
				Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
				DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
				
				
				Domicilio domiBdtu=new Domicilio();
				domiBdtu.setClave(new Long(dg.getDomicilioId()).intValue());
				try {
					domiBdtu=domiciliosServiceBean.consultarDomicilio(domiBdtu);
				} catch (DomicilioNoLocalizadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DgDomicilioGeografico nuevo=new DgDomicilioGeografico();
				nuevo.setDomicilioBDTU(domiBdtu);
				
				List<DgDomicilioGeografico> domicilios = new ArrayList<DgDomicilioGeografico>(); 
				domicilios.add(nuevo);
				
				
				
				
				
				//List<DgDomicilioGeografico> domicilios = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DgDomicilioGeografico dg where dg.dgCodigosPostales.id.codigo = '" +dg.getDgCodigosPostales().getId().getCodigo()+ "'" ); 
				
				//List<DgDomicilioGeografico> domicilios = this.domiciliosInegiServiceBean.consultar(dg);
				
				if(domicilios != null && domicilios.size()>0){
					for (Iterator iterator = domicilios.iterator(); iterator.hasNext();) {
						//Implementacion Anterior
//						DgDomicilioGeografico dgDomicilioGeografico = (DgDomicilioGeografico) iterator.next();
//						CrtDeteccion detFind = new CrtDeteccion();
//						detFind.setDomicilioId((int) dgDomicilioGeografico.getDomicilioId());
//						ArrayList lstDeta = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtDeteccion d where d.domicilioId = " +detFind.getDomicilioId().intValue() );
//						CrtDeteccion lstDet = null;
//						if(lstDeta!=null & lstDeta.size()>0){
//							lstDet = (CrtDeteccion)lstDeta.get(0);
//						}
//						if(lstDet != null){
//							lstDet.setDomCalle(dgDomicilioGeografico.getNomvial());
//							lstDet.setRefColonia(dgDomicilioGeografico.getDgAsentamiento().getNomAsen());
//							lstDet.setNumNroint(dgDomicilioGeografico.getNumintnum() == null ? "" : dgDomicilioGeografico.getNumintnum().toString());
//							lstDet.setNumNroext(dgDomicilioGeografico.getNumextnum() == null ? "" : dgDomicilioGeografico.getNumextnum().toString());
//							lstDet.setNumCodigopostal(dgDomicilioGeografico.getDgCodigosPostales().getId().getCodigo());
//							rsFinal.add(lstDet);
//						}
						//Implementacion Anterior
						
						DgDomicilioGeografico dgDomicilioGeografico = (DgDomicilioGeografico) iterator.next();
						CrtDeteccion detFind = new CrtDeteccion();
						detFind.setDomicilioId((int) dgDomicilioGeografico.getDomicilioId());
						ArrayList lstDeta = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtDeteccion d ");
						String cpRecuperado=domiBdtu.getCodigoPostal().getCodigoPostal();
						CrtDeteccion lstDet = null;
						String recuperado;
						for(Object ob:lstDeta){
							lstDet=(CrtDeteccion) ob;	
							recuperado=recuperaCodigoPostal(lstDet.getDomicilioId());
							if(recuperado!=null && recuperado.equals(cpRecuperado)){
								Domicilio domici=recuperaDomicilio(lstDet.getDomicilioId());
								if(domici==null){
									continue;
								}
								DgDomicilioGeografico objDomici=new DgDomicilioGeografico();
								objDomici.setDomicilioBDTU(domici);
								
								lstDet.setDomCalle(objDomici.getNomvial());
								lstDet.setRefColonia(objDomici.getDgAsentamiento().getNomAsen());
								lstDet.setNumNroint(objDomici.getNumintnum() == null ? "" : objDomici.getNumintnum().toString());
								lstDet.setNumNroext(objDomici.getNumextnum() == null ? "" : objDomici.getNumextnum().toString());
								lstDet.setNumCodigopostal(objDomici.getDgCodigosPostales().getId().getCodigo());
								rsFinal.add(lstDet);
							}
						}
						//Se cambia por
					}
					
				}
				
				int iTotalRecords = 0;
				/*Se debe de obtener el numero total de registros en la base de datos*/
				if(rsFinal!=null)
					iTotalRecords = rsFinal.size();
				
				/**
				 * Total records, after filtering (i.e. the total number of records
				 * after filtering has been applied - not just the number of records
				 * being returned in this result set)
				 */
				int iTotalDisplayRecords = 0;
				
				if(rsFinal!=null)
					iTotalDisplayRecords = rsFinal.size();
			     
				reply.setAaData(rsFinal);
				reply.setiTotalDisplayRecords(iTotalDisplayRecords);
				reply.setiTotalRecords(iTotalRecords);
		        reply.setsEcho(send.getsEcho());
			}
		}
	        
        return reply;
    }
	
	@RequestMapping(value="/validarSatic", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> validaSatic(@RequestBody CrtDeteccionWrapperDataTable aoData ,HttpServletRequest request ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		String numCP = "";
		if(aoData != null && !aoData.getoForm().getNumCodigopostal().equals("")){
			numCP = aoData.getoForm().getNumCodigopostal();
			UserSession session = this.getUsuarioFirmado(request);
			if(session != null && session.getIdSubDelegacion() != null){
				aoData.getoForm().setSdelegOrig(BigDecimal.valueOf(session.getIdSubDelegacion()));
			}
					
			send.parserArray(aoData.getAoData());
			send.setModelo(aoData.getoForm());
			
			reply = this.deteccionServiceBean.validacionObraSatic(send);
			if(reply.getAaData() != null && reply.getAaData().size() > 0){
//				for (Iterator iterator = reply.getAaData().iterator(); iterator.hasNext();) {
//					CrtDeteccion type = (CrtDeteccion) iterator.next();
//					if(type != null && type.getCveFkPatron() != null){
//						SatPatron patron = this.patronesService.getById(type.getCveFkPatron());
//						if(patron != null && patron.getUbicacion() != null){
//							SatUbicacion ubicacion = new SatUbicacion();
//							ubicacion.setPatron(patron);
//							ubicacion = this.ubicacionServiceBean.buscaUbicacion(ubicacion);
//							if(ubicacion != null){
//								type.setDomCalle(ubicacion.getCalle());
//								type.setNumNroext(ubicacion.getNumeroExterior());
//								type.setNumCodigopostal(ubicacion.getCodigoPostal());
//							}
//							
//						}
//					}
//					
//				}
				reply.setsEcho(send.getsEcho());
			}
			else if(reply.getAaData() != null && reply.getAaData().size() > 0 || !send.getsSearch().equals("")){	
				reply.setsEcho(send.getsEcho());
				}else{
				reply = null;
				}
			
		}
   
        return reply;
    }
	
	@RequestMapping(value="/removerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtDeteccion removerDomicilio(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);	
		request.getSession().removeAttribute("sinDomicilio");
		return deteccion;
	}
	
	@RequestMapping(value="/actualizar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion actualizar(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		if(deteccion.getCvePkFaseConst()!=null && (deteccion.getCvePkFaseConst()==-1 || deteccion.getCvePkFaseConst()==0)) deteccion.setCvePkFaseConst(null);
		if(deteccion.getCvePkTipObra()!=null && (deteccion.getCvePkTipObra()==-1 || deteccion.getCvePkTipObra()==0)) deteccion.setCvePkTipObra(null);
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona()==-1) deteccion.setCveFkZona(null);
		
		if(deteccion.getFechaEstimIncio()!=null && !deteccion.getFechaEstimIncio().equalsIgnoreCase("")){
			deteccion.setFecFechainicioEst(Functions.stringToDate(deteccion.getFechaEstimIncio()));			
		}if(deteccion.getFechaEstTerm()!=null && !deteccion.getFechaEstTerm().equalsIgnoreCase("")){
			deteccion.setFecFechaterminoEst(Functions.stringToDate(deteccion.getFechaEstTerm()));
		}
		
		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));
		deteccion.setFecFechareg(Functions.stringToDate(deteccion.getFechaRegistro()));
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		deteccion.setDomicilioId(new BigDecimal(dg.getDomicilioId()).intValue());		
		this.deteccionServiceBean.modificar(deteccion);
		removeDomicilioInegiSession(request);		
		return deteccion;
	}
	
	@RequestMapping(value="/censores", method=RequestMethod.POST)
	public @ResponseBody ArrayList censores(@RequestBody CrtDeteccion det,HttpServletRequest request) {		
		UserSession user = getUsuarioFirmado(request);
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaSQL(  "select u.CVE_ID_USUARIO, u.NOM_NOMBRE ,u.NUM_NSS, u.NUM_MATRICULA,u.NOM_PATERNO,u.NOM_MATERNO " +
																		" from SEG_USUARIO u " +
																		" inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO" +
																		" inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL" +
																		" inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO" +
																		" where pu.CVE_ROL = 3" +
																		" and uf.CVE_ID_SUBDELEGACION =" + user.getIdSubDelegacion() +
																		" and u.CVE_ID_USUARIO= "+user.getCveIdUsuario()+
																		" order by u.NOM_PATERNO,u.NOM_PATERNO,u.NOM_NOMBRE");		
		return lista;
	}	
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion create(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		deteccion.setNuFoliodeteccion(this.generaFolioDeteccion(new Long(user.getCveCodigoDelegacion()),new Long(user.getCveCodigoSubDelegacion()),Functions.stringToDate(deteccion.getFechaDeteccion())));
		deteccion.setFecFechareg(Functions.stringToDate(dateFormat.format(new Date())));		
		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));	
		if(deteccion.getDomicilioId() == null){
			deteccion.setDomicilioId(new BigDecimal(dg.getDomicilioId()).intValue());
		}
		
		if(deteccion.getFechaEstimIncio2()!=null)
			deteccion.setFecFechainicioEst(Functions.FormateaFecha(deteccion.getFechaEstimIncio2(), "-"));
		if(deteccion.getFechaEstTerm2()!=null)
			deteccion.setFecFechaterminoEst(Functions.FormateaFecha(deteccion.getFechaEstTerm2(), "-"));
		if(deteccion.getTipClaseobra().equals("-1")){ 
			deteccion.setTipClaseobra(null);
		}
		if(deteccion.getCvePkTipObra()!=null && deteccion.getCvePkTipObra().intValue() < 1){ 
			deteccion.setCvePkTipObra(null);
		}
		if(deteccion.getCvePkFaseConst()!=null && deteccion.getCvePkFaseConst().intValue() < 1){ 
			deteccion.setCvePkFaseConst(null);
		}
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona().intValue() < 1){
			deteccion.setCveFkZona(null);
		}
		deteccion.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		deteccion.setCveUsuario(user.getCurpUsuario().toString());				
		deteccion.setTxActividad(deteccion.getActividad());
		
		this.deteccionServiceBean.agregar(deteccion);
		removeDomicilioInegiSession(request);	
		request.getSession().removeAttribute("sinDomicilio");
		return deteccion;
	}
	
	public synchronized String generaFolioDeteccion(Long del,Long sDel, Date fecha){
		
		String consecutivo = this.deteccionServiceBean.obtieneFolios(del, sDel, fecha);
		
		
		return consecutivo;
	}
	
	@RequestMapping(value="/validaRegPatron" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion consultar(@RequestBody CrtDeteccion deteccion) {
		SatPatron pat = this.patronesService.validaRegistroPatronalWS(deteccion.getRegPatron(), false);
		if(pat!=null){
			deteccion.setCveFkPatron(pat.getCvePK());
			deteccion.setTxRfcpatron(pat.getRfc());
			deteccion.setTxCurppatron(pat.getCurp());
			deteccion.setNomRazonsocial(pat.getRazonSocial());
			deteccion.setActividad(pat.getActividad());
		}else{
			deteccion.setCveFkPatron(null);
			deteccion.setTxRfcpatron(null);
			deteccion.setTxCurppatron(null);
			deteccion.setNomRazonsocial(null);
			deteccion.setActividad(null);
		}
		return deteccion;
	}
	
	@RequestMapping(value="/validaNuReporte", method=RequestMethod.GET)
	public @ResponseBody boolean validaNuReporte(@RequestParam String nuReportectrlobra,@RequestParam String fechaDeteccion,HttpServletRequest request){
		
		CrtDeteccion det = new CrtDeteccion();
		boolean resp = false;
		UserSession user = getUsuarioFirmado(request);
		System.out.println("Numero Subdelegacion "+user.getIdSubDelegacion());
		if(nuReportectrlobra != null && !nuReportectrlobra.equals("") ){
			det.setNuReportectrlobra(nuReportectrlobra);
			det.setFechaDeteccion(fechaDeteccion);
			det.setFecFechadeteccionFc(Functions.stringToDate(fechaDeteccion));
			det.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
			det = this.deteccionServiceBean.validaNuReporte(det);
		}
		
		if(det != null){
			resp = false;
		}else{
			resp = true;
		}
		
		return resp;
	}	
	
	
	@RequestMapping(value="/validaIdDeteccion", method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion validaIdDeteccion(@RequestBody CrtDeteccion det,HttpServletRequest request){
		
		Object obj = request.getSession().getAttribute("sinDomicilio");
		Long idDeteccion = (Long) obj;
		if(idDeteccion != null){
			det.setCveDeteccion(idDeteccion);
			det = this.deteccionServiceBean.consultaPorClave(det);
			if(det.getFecFechadeteccionFc() != null){
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				det.setFechaDeteccion(formato.format(det.getFecFechadeteccionFc()));
			}
			if(det.getFecFechainicioEst() != null){
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				det.setFechaEstimIncio2(formato.format(det.getFecFechainicioEst()));
			}
			if(det.getFecFechaterminoEst() != null){
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				det.setFechaEstTerm2(formato.format(det.getFecFechaterminoEst()));
			}
			
		}else{
			det = null;
		}

		return det;
	}	
	
	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con el objetivo de evitar de fallas en los calculos de fechas. 
	 * Cabe la posibilidad de que la creacion y obtencion de las fechas actuales sean incorrectas si esta operacion se le delega a JAVASCRIPT
	 * ya que con cambiar la fecha en la maquina local los calendarios generados se reajustaran a esta fecha local, en cambio si la fecha del dia
	 * se pide al servidor no habra este tipo de errores, claro a menos de que la fecha del servidor tambien este mal. 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), "dd-MM-yyyy");
	}
	
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, "dd-MM-yyyy");
	}
	
	
	@RequestMapping(value="/validaNuReporteControlObra.do",method=RequestMethod.POST)
	public @ResponseBody boolean validaNumReporteObra(@RequestBody CrtDeteccion deteccion){
		System.out.println("dentor de validaNuReporteControlObra");
		System.out.println("fecha: " + deteccion.getFechaDeteccion() + " numCtrlObra : "+ deteccion.getNuReportectrlobra() );
		boolean numeroValido = false;
		List<CrtDeteccion> listaResultado= deteccionServiceBean.consultar(deteccion);
		if(null == listaResultado || listaResultado.isEmpty()){
			
		}
		
		return true;
		
	}
	
	@RequestMapping(value="/censor", method=RequestMethod.POST)
	public @ResponseBody SegUsuario censor(@RequestBody CrtDeteccion usu, HttpServletRequest request) {		
		
		UserSession user = getUsuarioFirmado(request);
		SegUsuario usuario = new SegUsuario();
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaSQL(  "select u.CVE_ID_USUARIO, u.NOM_NOMBRE ,u.NUM_NSS, u.NUM_MATRICULA" +
																		" from SEG_USUARIO u " +
																		" inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO" +
																		" inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL" +
																		" inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO" +
																		" where pu.CVE_ROL = 3" +
																		" and u.CVE_ID_USUARIO = " + usu.getIdOrigen() +
																		" and uf.CVE_ID_SUBDELEGACION =" + user.getIdSubDelegacion() +
																		" order by u.NOM_PATERNO,u.NOM_PATERNO,u.NOM_NOMBRE");	
		if(lista != null){
			
			Iterator itera = lista.iterator();			
			while(itera.hasNext()){
				
				Object[] obj = (Object[])itera.next();
				usuario.setNumNss(obj[2] != null ? ((String)obj[2]) : null);
				usuario.setNumMatricula(obj[3] != null ? ((String)obj[3]) : null);
			}
		
		}
		
		return usuario;
	}	
}
