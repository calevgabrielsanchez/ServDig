package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.constantes.ConstantesConsultaEC;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion.AbstractCedulasSQL;
import mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion.DescargaCedula;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaA;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaG;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaH;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaI;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaQ;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaR;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCop;

import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controlador que permite revisarla información
 * que el patrón dió de alta durante la elaboración
 * del estudio de corrección.
 * 
 * 
 * @author Marco Antonio Nieto Plett
 * @version 1.0.0
 */
@Controller
@RequestMapping(value="/consultaEstudioCorreccion")
public class ConsultaEstudioCorreccionController extends AbstractController{

	/**
	 * Servicio de Consulta para Cédula A
	 */
	@Autowired
	 private GeneraTablaCedulaA cedulaA;
	
	/**
	 * Servicio de Consulta para Cédula G
	 */
	@Autowired
	 private GeneraTablaCedulaG cedulaG;
	
	/**
	 * Servicio de Consulta para Cédula G
	 */
	@Autowired
	 private GeneraTablaCedulaH cedulaH;
	
	/**
	 * Servicio de Consulta para Cédula I
	 */
	@Autowired
	 private GeneraTablaCedulaI cedulaI;

	/**
	 * Servicio de Consulta para Cédula Q
	 */
	@Autowired
	 private GeneraTablaCedulaQ cedulaQ;
	
	/**
	 * Servicio de Consulta para Cédula O
	 */
	@Autowired
	 private GeneraTablaCedulaO cedulaO;

	/**
	 * Servicio de Consulta COP
	 */
	@Autowired
	 private GeneraTablaCop cop;	
	

	/**
	 * Servicio de Consulta R
	 */
	@Autowired
	 private GeneraTablaCedulaR cedulaR;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBeanPeriodo;
	
	/**
	 * Logger.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = Logger
			.getLogger(ConsultaEstudioCorreccionController.class);
	
	/**
	 * Método utilizado por el menu, carga inicial de la
	 * consulta.
	 * 
	 * @see ConstantesConsultaEC
	 * @author Marco Antonio Nieto Plett
	 * @version 1.0.1
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		model.addAttribute(ConstantesConsultaEC.MODEL_CONSULTAS_ESTUIDO_CORRECCION,new ConsultaEstudioCorreccionVO());
		
	    return determinaURL(request, "consultaEstudioCorreccion/consultaECMain", "consultaEstudioCorreccion/consultaECMain/patron");
	}
	
	/**
	 * Permite consultar y cargar las cédulas 
	 * A, G, H, I, O, Q y COPS de forma simultanea.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @author Gerardo Salazar Vega
	 * @version 1.0.2
	 */
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public String consultar(ConsultaEstudioCorreccionVO cec, Model model, HttpServletRequest request, HttpServletResponse response) {
		// Se agrega la subdelegacion para poder validar la informacion de las consultas SQL
		UserSession user = getUsuarioFirmado(request);
		if(user.getCveCodigoDelegacion()==null && user.getCveCodigoSubDelegacion()==null){//normativo
			System.out.println("Usuario Normativo");
			cec.setIdSubDelegacion(null);
			cec.setTablaCedulaA(cedulaA.generarVistaCedula(cec));
			cec.setTablaCedulaG(cedulaG.generarVistaCedula(cec));
			cec.setTablaCedulaH(cedulaH.generarVistaCedula(cec));
			cec.setTablaCedulaI(cedulaI.generarVistaCedula(cec));
			cec.setTablaCedulaQ(cedulaQ.generarVistaCedula(cec));
			cec.setTablaCedulaO(cedulaO.generarVistaCedula(cec));
			cec.setTablaCedulaR(cedulaR.generarVistaCedula(cec));
			cec.setTablaCop(cop.generarVistaCedula(cec));
			model.addAttribute(ConstantesConsultaEC.MODEL_CONSULTAS_ESTUIDO_CORRECCION,cec);
			
			return determinaURL(request, "consultaEstudioCorreccion/consultaECMain", "consultaEstudioCorreccion/consultaECMain/patron");

			
		}
		String ssddUsr = user.getCveCodigoDelegacion().length()<2 ? ("0"+user.getCveCodigoDelegacion()) : user.getCveCodigoDelegacion();
		ssddUsr += user.getCveCodigoSubDelegacion().length()<2 ? ("0"+user.getCveCodigoSubDelegacion()) : user.getCveCodigoSubDelegacion();
		
		String ssddFolio = cec.getFolioCorreccion().substring(0,4);
		cec.setIdSubDelegacion(String.valueOf(user.getIdSubDelegacion()));
		if(!ssddFolio.equals(ssddUsr)){
			cec.setError("El n\u00famero de folio no pertenece a la Delegaci\u00f3n/Subdelegación del usuario firmado en el sistema");
			
		}else{
			cec.setIdSubDelegacion(user.getIdSubDelegacion().toString());
			cec.setTablaCedulaA(cedulaA.generarVistaCedula(cec));
			cec.setTablaCedulaG(cedulaG.generarVistaCedula(cec));
			cec.setTablaCedulaH(cedulaH.generarVistaCedula(cec));
			cec.setTablaCedulaI(cedulaI.generarVistaCedula(cec));
			cec.setTablaCedulaQ(cedulaQ.generarVistaCedula(cec));
			cec.setTablaCedulaO(cedulaO.generarVistaCedula(cec));
			cec.setTablaCedulaR(cedulaR.generarVistaCedula(cec));
			cec.setTablaCop(cop.generarVistaCedula(cec));
			

		}
			
		model.addAttribute(ConstantesConsultaEC.MODEL_CONSULTAS_ESTUIDO_CORRECCION,cec);
		
		return determinaURL(request, "consultaEstudioCorreccion/consultaECMain", "consultaEstudioCorreccion/consultaECMain/patron");
	}	
	
	@RequestMapping(value="/getPeriodosCorreccion" , method=RequestMethod.POST)
	public @ResponseBody List<?> getPeriodosCorreccion(@RequestBody ConsultaEstudioCorreccionVO cec,HttpServletRequest request) {
		
		
		String SQL = AbstractCedulasSQL.OBTENER_PERIODOS_CORRECCION.replace("{1}",cec.getFolioCorreccion());
		
		List<?> ls = catalogoServiceBeanPeriodo.consultaSQL(SQL);
				
		return ls;
	}
	

	@RequestMapping(value="/consultarCedula", method=RequestMethod.POST)
	public @ResponseBody ConsultaEstudioCorreccionVO seguimientoCorreccionMain(@RequestBody ConsultaEstudioCorreccionVO clase,HttpServletRequest request,HttpServletResponse response) {
		UserSession user = getUsuarioFirmado(request);
		
				
		clase.setIdSubDelegacion(user.getIdSubDelegacion().toString());
		clase.setRegistroPatronal(clase.getRegistroPatronal().substring(0,10));
		clase.setTablaCedulaA(cedulaA.generarVistaCedula(clase));
		clase.setTablaCedulaG(cedulaG.generarVistaCedula(clase));
		clase.setTablaCedulaH(cedulaH.generarVistaCedula(clase));
		clase.setTablaCedulaI(cedulaI.generarVistaCedula(clase));
		clase.setTablaCedulaQ(cedulaQ.generarVistaCedula(clase));
		clase.setTablaCedulaO(cedulaO.generarVistaCedula(clase));
		clase.setTablaCedulaR(cedulaR.generarVistaCedula(clase));
		clase.setTablaCop(cop.generarVistaCedula(clase));
		return clase;
	}
	
	@RequestMapping(value="/obtenerUsuarioSession.do", method=RequestMethod.POST)
	public @ResponseBody UserSession obtenerFechaServidor(HttpServletRequest request){
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		return user;
	}

}
	