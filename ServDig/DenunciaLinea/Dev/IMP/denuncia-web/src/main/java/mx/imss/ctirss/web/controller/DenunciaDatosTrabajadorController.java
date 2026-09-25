package mx.imss.ctirss.web.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcMotivodenuncia;
import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcTipodocumento;
import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltMotivodenuncia;
import mx.imss.ctirss.model.DltMotivodenunciaPK;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.service.interfaces.ICatalogoService;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.web.bean.DenunciaDTO;
import mx.imss.ctirss.web.bean.FileUploadVB;
import mx.imss.ctirss.web.utils.MotivoDenunciaWrapper;
import mx.imss.ctirss.web.validator.DenunciaValidator;



@Controller
@RequestMapping(value="/denunciaLinea/datosTrabajador")
@SessionAttributes("denunciaDTO")
public class DenunciaDatosTrabajadorController extends AbstractController{
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private IDenunciaService<AbstractModel> denunciaServiceBean;
		

	private static final String SESSION_BEAN=DenunciaDTO.SES_NAME;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(DenunciaDatosTrabajadorController.class); 
	
	 @InitBinder("denunciaDTO")  
	 protected void initBinder(WebDataBinder binder) {        
		 binder.setValidator(new DenunciaValidator()); // registramos el validador    
	 }
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView getCreateForm(Model model, HttpSession ses, HttpServletRequest request) {	
		DenunciaDTO denunciaDTO = null;		 
		DenunciaDTO denunciaDTOSes = (DenunciaDTO)request.getSession().getAttribute("denunciaDTO");
		 if(denunciaDTOSes != null){
			 denunciaDTO = denunciaDTOSes;
			 ses.setAttribute(SESSION_BEAN, denunciaDTOSes);			 			 
		 }else{
			 denunciaDTO = new DenunciaDTO();
			 ses.setAttribute(SESSION_BEAN, denunciaDTO);
		 }
		 		 		 
		 ModelAndView mw = new ModelAndView("denuncia/datosTrabajadorMain");
		 mw.addObject("denunciaDTO", denunciaDTO);
		
		 return mw;
	}

	
	@RequestMapping(value="/guardarDatosTrabajador", method=RequestMethod.POST)
	public ModelAndView submit(@Valid DenunciaDTO denunciaDTO, BindingResult denunciaDTOBr,SessionStatus status,
			                            HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat();
		UserSession dltUsuarioden =(UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		DltDenuncia dltDenuncia = denunciaDTO.getDltDenuncia();
		Long folioD = dltDenuncia.getCveFoliodenuncia();
		logger.debug("ingresa DenunciaDatosTrabajador controller submit");
        
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String stringDate = formatter.format(new Date());
		
		
		if(denunciaDTOBr.hasErrors()){
			ModelAndView mw = new ModelAndView("denuncia/datosTrabajadorMain");
			mw.addObject("denunciaDTO", denunciaDTO);			
			return mw;
		}
		
		if(folioD == null){
						

			 DltDenuncia dltDenunciaN = this.denunciaServiceBean.saveDenuncia(dltDenuncia,  dltUsuarioden);
			//DltDenuncia dltDenunciaN = new DltDenuncia();
			 
			 dltDenunciaN.setCveTipodenunciante(new Long(denunciaDTO.getStrTipoDenunciante()));
			 dltDenunciaN.setIdStatus(new Long(1));
			 try{
				 dltDenunciaN.setFecFechareg ( formatter.parse(stringDate));
				 dltDenunciaN.setFecRegistro(formatter.parse(stringDate));
			 }catch(Exception e){
				 e.printStackTrace();
			 }				
			 HashSet<DltMotivodenuncia> hstMotivos = new HashSet<DltMotivodenuncia>();
			 HashSet<DltPersona> personas = new HashSet<DltPersona>();
			 //dltDenuncia.setCveTipodenunciante(denunciaDTO.getDltPersonaT().getCveTipodenunciante());
			 
			 dltDenunciaN.setDltPersonas(personas);
			 dltDenunciaN.setDltMotivodenuncias(hstMotivos);
	
			 String[] mD = request.getParameterValues("md");

	        try{
			for(int i=0; i < mD.length; i ++){
	        	
	        	if(mD[i].equalsIgnoreCase("1")){
	        		
	        		//DlcMotivodenuncia dlcMotivodenuncia = new DlcMotivodenuncia();
	        		//dlcMotivodenuncia.setCveMotivodenuncia(1);        		
	        		
	        		DltMotivodenuncia dltMotivodenuncia = new DltMotivodenuncia();   
	        		DltMotivodenunciaPK id = new DltMotivodenunciaPK();
	        		id.setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
	        		//id.setCveMotivodenuncia(dlcMotivodenuncia.getCveMotivodenuncia());
	        		id.setCveMotivodenuncia(new Long(1));
	        		dltMotivodenuncia.setId(id);
	        		//dltMotivodenuncia.setDlcMotivodenuncia(dlcMotivodenuncia);
	        		dltMotivodenuncia.setDltDenuncia(dltDenunciaN);
	        		dltMotivodenuncia.setFecLabdelIngreso(sdf.parse(denunciaDTO.getValoresMotivoDenuncia().getMd1fechaInicio()));
	        		dltMotivodenuncia.setFecLabalDejolab(sdf.parse(denunciaDTO.getValoresMotivoDenuncia().getMd1fechaFin()));
	        		dltDenunciaN.getDltMotivodenuncias().add(dltMotivodenuncia);
	        	}
	        	
	        	if(mD[i].equalsIgnoreCase("2")){
        		
	        		//DlcMotivodenuncia dlcMotivodenuncia = new DlcMotivodenuncia();
	        		//dlcMotivodenuncia.setCveMotivodenuncia(2);
	        		
	        		DltMotivodenuncia dltMotivodenuncia = new DltMotivodenuncia();   
	        		DltMotivodenunciaPK id = new DltMotivodenunciaPK();
	        		id.setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
	        		//id.setCveMotivodenuncia(dlcMotivodenuncia.getCveMotivodenuncia());
	        		id.setCveMotivodenuncia( new Long(2));
	        		dltMotivodenuncia.setId(id);
	        		//dltMotivodenuncia.setDlcMotivodenuncia(dlcMotivodenuncia);
	        		dltMotivodenuncia.setDltDenuncia(dltDenunciaN);
	
	        		dltMotivodenuncia.setFecLabdelIngreso(sdf.parse(denunciaDTO.getValoresMotivoDenuncia().getMd2fechaInicio()));
	        		dltMotivodenuncia.setFecLabalDejolab(sdf.parse(denunciaDTO.getValoresMotivoDenuncia().getMd2fechaFin()));
	        		
	        		dltDenunciaN.getDltMotivodenuncias().add(dltMotivodenuncia);
	
	        	}
	        	
	        	if(mD[i].equalsIgnoreCase("3")){
        		
	        		//DlcMotivodenuncia dlcMotivodenuncia = new DlcMotivodenuncia();
	        		//dlcMotivodenuncia.setCveMotivodenuncia(3);
	        		
	        		DltMotivodenuncia dltMotivodenuncia = new DltMotivodenuncia();   
	        		DltMotivodenunciaPK id = new DltMotivodenunciaPK();
	        		id.setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
	        		//id.setCveMotivodenuncia(dlcMotivodenuncia.getCveMotivodenuncia());
	        		id.setCveMotivodenuncia( new Long(3));
	        		dltMotivodenuncia.setId(id);
	        		//dltMotivodenuncia.setDlcMotivodenuncia(dlcMotivodenuncia);
	        		dltMotivodenuncia.setDltDenuncia(dltDenunciaN);
	        		
	        		//denunciaDTO.getValoresMotivoDenuncia().getMd3ImporteImss(); // falta la columna de éste
	        		dltMotivodenuncia.setImpSalarioReal(denunciaDTO.getValoresMotivoDenuncia().getMd3ImporteReal());       		
	        		
	        		dltDenunciaN.getDltMotivodenuncias().add(dltMotivodenuncia);
	        	}
	        	
	        	if(mD[i].equalsIgnoreCase("4")){
        		
	        		//DlcMotivodenuncia dlcMotivodenuncia = new DlcMotivodenuncia();
	        		//dlcMotivodenuncia.setCveMotivodenuncia(4);
	        		
	        		DltMotivodenuncia dltMotivodenuncia = new DltMotivodenuncia();   
	        		DltMotivodenunciaPK id = new DltMotivodenunciaPK();
	        		id.setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
	        		//id.setCveMotivodenuncia(dlcMotivodenuncia.getCveMotivodenuncia());
	        		id.setCveMotivodenuncia( new Long(4));
	        		dltMotivodenuncia.setId(id);
	        		//dltMotivodenuncia.setDlcMotivodenuncia(dlcMotivodenuncia);
	        		dltMotivodenuncia.setDltDenuncia(dltDenunciaN);
	        		dltMotivodenuncia.setFecLabalDejolab(sdf.parse(denunciaDTO.getValoresMotivoDenuncia().getMd4fechaFin()));
	        		
	        		dltDenunciaN.getDltMotivodenuncias().add(dltMotivodenuncia);
	        	}
	        }
	        
        }catch(ParseException pe){
        	logger.error("Error parseando la fecha" + pe.getMessage());        	
        }

		//DlcTipodocumento dlcTipoDocumentoT = new DlcTipodocumento();
		//dlcTipoDocumentoT.setCveTipodocumento(denunciaDTO.getDltPersonaT().getCveTipodocumento());		
		//denunciaDTO.getDltPersonaT().setDlcTipodocumento(dlcTipoDocumentoT);
	        
	     /*   ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from  DlcTipodocumento d where d.cveFoliodenuncia = '"+dltDenunciaN.getCveFoliodenuncia()+"'");
			 if(lista.size() ==1){
				 dltDenunciaN = (DltDenuncia)lista.get(0);
			 }*/
	        
	      denunciaDTO.getDltPersonaT().setCveTipodocumento(new Long(denunciaDTO.getStrTipoDocTr()));
		
		//DlcTipodenunciante tipoDenunciante = new DlcTipodenunciante();
		//tipoDenunciante.setCveTipodenunciante(denunciaDTO.getDltPersonaT().getCveTipodenunciante());		
		//dltDenuncia.setDlcTipodenunciante(tipoDenunciante);
		
//	        dltDenunciaN.setCveTipodenunciante(denunciaDTO.getDltPersonaT().getCveTipodenunciante());
	      dltDenunciaN.setCveTipodenunciante(new Long(denunciaDTO.getStrTipoDenunciante()));
		
	    if(denunciaDTO.getStrTipoDenunciante().equalsIgnoreCase("1")){
		    //if(denunciaDTO.getDltPersonaT().getCveTipodenunciante()== 1){							
			
			denunciaDTO.getDltPersonaT().setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
			denunciaDTO.getDltPersonaT().setDltDenuncia(dltDenunciaN);
			denunciaDTO.getDltPersonaT().setCveTipodenunciante(new Long(1));
			//denunciaDTO.getDltPersonaT().setDlcTipodenunciante(tipoDenunciante);
	
			dltDenunciaN.getDltPersonas().add(denunciaDTO.getDltPersonaT());
		}
	    if(denunciaDTO.getStrTipoDenunciante().equalsIgnoreCase("2")){
		//if(denunciaDTO.getDltPersonaT().getCveTipodenunciante()== 2){		
			
			//denunciaDTO.getDltPersonaB().setDlcTipodenunciante(tipoDenunciante);
			denunciaDTO.getDltPersonaB().setCveTipodenunciante(new Long(2));
			//DlcTipodenunciante tipoDenuncianteT = new DlcTipodenunciante();
			//tipoDenuncianteT.setCveTipodenunciante(1);			
			//denunciaDTO.getDltPersonaT().setDlcTipodenunciante(tipoDenuncianteT);
			denunciaDTO.getDltPersonaT().setCveTipodenunciante(new Long(1));
			
			//DlcTipodocumento dlcTipoDocumentoB = new DlcTipodocumento();			
			//dlcTipoDocumentoB.setCveTipodocumento(denunciaDTO.getDltPersonaB().getCveTipodocumento());			
			//denunciaDTO.getDltPersonaB().setDlcTipodocumento(dlcTipoDocumentoB);
			
			denunciaDTO.getDltPersonaB().setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
			denunciaDTO.getDltPersonaB().setDltDenuncia(dltDenunciaN);
			denunciaDTO.getDltPersonaT().setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
			denunciaDTO.getDltPersonaT().setDltDenuncia(dltDenunciaN);

			dltDenunciaN.getDltPersonas().add(denunciaDTO.getDltPersonaB());
			dltDenunciaN.getDltPersonas().add(denunciaDTO.getDltPersonaT());
			
		}
		
	    if(denunciaDTO.getStrTipoDenunciante().equalsIgnoreCase("3")){
		//if(denunciaDTO.getDltPersonaT().getCveTipodenunciante() == 3){
			
			//denunciaDTO.getDltPersonaRL().setDlcTipodenunciante(tipoDenunciante);
			denunciaDTO.getDltPersonaRL().setCveTipodenunciante(new Long(3));
			
			//DlcTipodenunciante tipoDenuncianteT = new DlcTipodenunciante();
			//tipoDenuncianteT.setCveTipodenunciante(1);
			//denunciaDTO.getDltPersonaT().setDlcTipodenunciante(tipoDenuncianteT);
			denunciaDTO.getDltPersonaT().setCveTipodenunciante(new Long(1));
			
			//DlcTipodocumento dlcTipoDocumentoRL = new DlcTipodocumento();
			//dlcTipoDocumentoRL.setCveTipodocumento(denunciaDTO.getDltPersonaRL().getCveTipodocumento());			
			//denunciaDTO.getDltPersonaRL().setDlcTipodocumento(dlcTipoDocumentoRL);
			
			denunciaDTO.getDltPersonaRL().setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
			denunciaDTO.getDltPersonaRL().setDltDenuncia(dltDenunciaN);
			denunciaDTO.getDltPersonaT().setCveFoliodenuncia(dltDenunciaN.getCveFoliodenuncia());
			denunciaDTO.getDltPersonaT().setDltDenuncia(dltDenunciaN);
			
			dltDenunciaN.getDltPersonas().add(denunciaDTO.getDltPersonaRL());
			dltDenunciaN.getDltPersonas().add(denunciaDTO.getDltPersonaT());
		}
		
	        //update
			dltDenunciaN =this.denunciaServiceBean.saveDenuncia(dltDenunciaN, dltUsuarioden);
			denunciaDTO.setDltDenuncia(dltDenunciaN);
		}
		else{
			
			dltDenuncia = this.denunciaServiceBean.updateDenuncia(dltDenuncia, dltUsuarioden);
			denunciaDTO.setDltDenuncia(dltDenuncia);
		}
		 
		 
		 
		 ModelAndView mw = new ModelAndView("denuncia/datosPatronMain");		
		 mw.addObject("denunciaDTO", denunciaDTO);
	
		 return mw;
	}

	
	//cargar el documento.
	
	
	
}
