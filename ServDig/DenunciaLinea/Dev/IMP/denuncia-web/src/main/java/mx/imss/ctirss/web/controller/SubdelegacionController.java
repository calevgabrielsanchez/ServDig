package mx.imss.ctirss.web.controller;

import mx.imss.ctirss.base.model.AbstractDltDenuncia;
import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.catalogos.model.DlcStatus;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcTiposformapago;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.framework.base.model.AbstractModel;

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
import org.springframework.web.servlet.ModelAndView;

import mx.imss.ctirss.framework.utils.*;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltFormapago;
import mx.imss.ctirss.model.DltInfotrabajo;
import mx.imss.ctirss.model.DltMotivodenuncia;
import mx.imss.ctirss.model.DltMotivodenunciaPK;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.promocion.base.paginador.DenunciasWrapperDataTable;
import mx.imss.ctirss.promocion.base.paginador.PatronesDenunciadosWrapperDataTable;
import mx.imss.ctirss.service.interfaces.ICatalogoService;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.web.bean.DenunciaDTO;

import org.apache.log4j.Logger;

import javax.servlet.http.*;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Controller
@RequestMapping(value="/subdelegacion/denuncia")
@SessionAttributes("denunciaDTO")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SubdelegacionController extends AbstractController{
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private IDenunciaService<AbstractModel> denunciaServiceBean;
	

	private static final String SESSION_BEAN=DenunciaDTO.SES_NAME;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(SubdelegacionController.class); 
	
	@RequestMapping(method=RequestMethod.GET)	
	public String getCreateForm(Model model, HttpServletRequest request) {		
		 logger.debug("Creando forma denuncia controller");
		 model.addAttribute( new DltDenuncia());
		 return "inicioDenunciaSub";
	}
	
	
	@RequestMapping(value="/nueva")
	public String nueva(){
		return "inicioDenunciaSub";
	}
	
	@RequestMapping(value="/existente")
	public String existente(){
		return "inicioDenunciaSub";
	}
	
	
	@RequestMapping(value="/generaDenuncia", method=RequestMethod.POST)
	public @ResponseBody DltDenuncia generaDenuncia(@RequestBody DltDenuncia dltDenuncia,HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		
	//Obtenemos usuario firmado
		
		@SuppressWarnings("unused")
		UserSession usrSession	= (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		ArrayList listaDenuncias = null;
		if(dltDenuncia.getNumFoliodenuncia()!=null && !dltDenuncia.getNumFoliodenuncia().equals("")){
			listaDenuncias = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia d where d.numFoliodenuncia = '" +dltDenuncia.getNumFoliodenuncia().trim() +"'");
			if(listaDenuncias!=null && listaDenuncias.size()>0){
				DltDenuncia dltDenunciaTemp = (DltDenuncia) listaDenuncias.get(0);
				dltDenuncia.setCveFoliodenuncia(dltDenunciaTemp.getCveFoliodenuncia());
			}
		}
		
		dltDenuncia.setNumFoliodenuncia(denunciaServiceBean.generaNumFolioDenuncia(dltDenuncia));
		dltDenuncia.setFecFechareg(new Date());
		dltDenuncia.setCveIdUsuarioFuncionario(new BigDecimal(usrSession.getCveIdUsuario()));
		if(listaDenuncias==null)
			dltDenuncia = (DltDenuncia) catalogoServiceBean.agregar(dltDenuncia);
		dltDenuncia.setNumFoliodenuncia(dltDenuncia.getNumFoliodenuncia()+""+dltDenuncia.getCveFoliodenuncia());
		dltDenuncia = (DltDenuncia) catalogoServiceBean.actualizar(dltDenuncia);
		return dltDenuncia;
	}
	
	@RequestMapping(value="/guardaMotivosDenuncia", method=RequestMethod.POST)
	public @ResponseBody String guardaMotivosDenuncia(@RequestBody ArrayList<DltMotivodenuncia> motivosDenuncia,HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		
		//Debemos borrar todos los motivos cada que se actualiza
		
		
		//	Obtenemos los motivos de denuncia y los guardamos
		System.out.println("motivosdenuncia");
		for(int i=0; i<motivosDenuncia.size(); i++){
			Map m = (Map) motivosDenuncia.get(i);
			//creamos el id de motivo denuncia
			DltMotivodenuncia motivoDenuncia = new DltMotivodenuncia();
			DltMotivodenunciaPK id = new DltMotivodenunciaPK();
			//Verificamos que el objeto tenga datos validos para guardarlo
			String fecLabalDejolab = (String) m.get("fecLabalDejolab");
			String fecLabdelIngreso = (String) m.get("fecLabdelIngreso");
			String impSalarioReal = (String) m.get("impSalarioReal");
			String impSalarioReg = (String) m.get("impSalarioReg");
			String cveFoliodenuncia =(String) m.get("cveFoliodenuncia");
			String cveMotivodenuncia =(String) m.get("cveMotivodenuncia");
			if(i==0){
				if(fecLabalDejolab!=null && !fecLabalDejolab.equals("") && fecLabdelIngreso!=null && !fecLabdelIngreso.equals("")){
					id.setCveFoliodenuncia(new Long(cveFoliodenuncia));
					id.setCveMotivodenuncia(new Long(cveMotivodenuncia));
					motivoDenuncia.setId(id);
					motivoDenuncia.setFecLabdelIngreso(new Date());
					motivoDenuncia.setFecLabalDejolab(new Date());
					catalogoServiceBean.agregar(motivoDenuncia);
				}
				
				
			}
			if(i==1){
				if(fecLabalDejolab!=null && !fecLabalDejolab.equals("") && fecLabdelIngreso!=null && !fecLabdelIngreso.equals("")){
					id.setCveFoliodenuncia(new Long(cveFoliodenuncia));
					id.setCveMotivodenuncia(new Long(cveMotivodenuncia));
					motivoDenuncia.setId(id);
					motivoDenuncia.setFecLabdelIngreso(new Date());
					motivoDenuncia.setFecLabalDejolab(new Date());
					catalogoServiceBean.agregar(motivoDenuncia);
				}
				
				
			}
			if(i==2){
				if(impSalarioReal!=null && !impSalarioReal.equals("") &&  impSalarioReg!=null && !impSalarioReg.equals("")){
					id.setCveFoliodenuncia(new Long(cveFoliodenuncia));
					id.setCveMotivodenuncia(new Long(cveMotivodenuncia));
					motivoDenuncia.setId(id);
					motivoDenuncia.setImpSalarioReal(new BigDecimal(impSalarioReal));
					motivoDenuncia.setImpSalarioReg(new BigDecimal(impSalarioReg));
					catalogoServiceBean.agregar(motivoDenuncia);
				}
				
				
			}
			if(i==3){
				if(fecLabdelIngreso!=null && !fecLabdelIngreso.equals("")){
					id.setCveFoliodenuncia(new Long(cveFoliodenuncia));
					id.setCveMotivodenuncia(new Long(cveMotivodenuncia));
					motivoDenuncia.setId(id);
					motivoDenuncia.setFecLabdelIngreso(new Date());
					catalogoServiceBean.agregar(motivoDenuncia);
				}
				
				
			}
			
			
			
			
			
		}
		
		
		
		return "";
	}
	
	@RequestMapping(value="/guardaDatosTrabajador", method=RequestMethod.POST)
	public @ResponseBody DltPersona guardaDatosTrabajador(@RequestBody DltPersona persona,HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		
		//Debemos borrar todos los motivos cada que se actualiza
		//	GUARDAMOS LOS DATOS DEL TRABAJADOR
		
		ArrayList listaPersonas = null;
		if(persona!=null && persona.getCveFoliodenuncia()!=null ){
			listaPersonas = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from DltPersona p where p.cveFoliodenuncia = " +persona.getCveFoliodenuncia());
			if(listaPersonas!=null && listaPersonas.size()>0){
				DltPersona dltPersonaTemp = (DltPersona) listaPersonas.get(0);
				persona.setCvePersona(dltPersonaTemp.getCvePersona());
				persona = 	(DltPersona) catalogoServiceBean.actualizar(persona);
			}else{
				persona = 	(DltPersona) catalogoServiceBean.agregar(persona);	
			}
			
		}
		
		
		
		return persona;
	}
	
	
	
	
}
