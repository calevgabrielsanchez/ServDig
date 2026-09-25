/**
 * WelcomeController.java
 * @package mx.gob.imss.ctirss.delta.derechohabientes.web.controller
 * @project gestionDerechohabientes-web	
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Login;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.OpcionesProperties;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.model.utility.bean.GrupoFamiliarDataTable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2011
 */
@Controller
@RequestMapping(value = "/inicio/*")
public class GrupoFamiliarController extends AbstractController {
	private static final Logger logger = Logger.getLogger(GrupoFamiliarController.class);
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private OpcionesProperties opcionesProperties;
	
	
	@RequestMapping(value = "grupoFamiliar")
    public String home(Model model, HttpServletRequest request, HttpSession session) {	
		
		//Elimina el bean de documentos probatorios en caso de que existe
		this.limpiarSession(session);
		
		/**
		 * Lista los elementos que se encuentran en session despues de ejecutar un tramite
		 */
		this.log.debug("Lista los elementos que se encuentran en session despues de ejecutar un tramite...");
		this.log.debug("-> "+session.getAttributeNames().toString());
	
		
		String fordward = Constants.GRUPO_FAMILIAR_FORDWARD;
		String conAsegurado = Constants.CON_ASEGURADO_INTERNO;
		String elemento = Constants.EXTERNO;
		
		List<ServiciosDTO> servicios = new ArrayList<ServiciosDTO>();
		List<SujetoObligado> patrones = new ArrayList<SujetoObligado>();
		
		
		
		Usuario usuario = (Usuario)session.getAttribute(Usuario.SES_NAME);
		AsignacionNSS miAsignacionNss = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		CabezaGrupoFamiliar miCabezaGF = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		GrupoFamiliar miGrupoFamiliar = null;
	
		if (usuario.getPerfilUsuario().getIdPerfilUsuario().equals(PerfilesEnum.ASEGURADO.getId())
				|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(PerfilesEnum.PENSIONADO.getId())
				|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(PerfilesEnum.CONYUGE.getId())) {
			elemento = Constants.EXTERNO;
		}else{
			elemento = Constants.INTERNO;
		}
		
		
		
		
		try{
			// ------------------------------------------------------------------------
			// Actualiza la cabeza en caso de que sea nula o que no sea estudiante
			// ------------------------------------------------------------------------
			miCabezaGF = this.actualizaCabezaGrupoFamiliar(miAsignacionNss, miCabezaGF);
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("exception", "exception.RNGD0071");
			return "internalError"; 
		}
		
		
		if( miCabezaGF.getEsEstudiante() )
			fordward = Constants.GRUPO_FAMILIAR_ESTUDIANTES;
		
		
		
		if (miCabezaGF != null) {
			
			
			try{
				
				if( !miCabezaGF.getEsEstudiante() ){
					String strServicioMedico ="";
					String strTipoPension = "";
					miGrupoFamiliar = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
					if(miGrupoFamiliar != null){
						 strServicioMedico = miGrupoFamiliar.getConDerechoSm();
						 strTipoPension = miGrupoFamiliar.getAsignacionNSS().getTipoPension();
						 if(StringUtils.isEmpty(strServicioMedico) || StringUtils.isEmpty(strTipoPension) ){
							 log.debug("entre por que alguno de los valores es NULOsssss servicio["+strServicioMedico+"] pension["+strTipoPension+"]");
							 try{
								 GrupoFamiliar grupoComplementado = grupoFamiliarService.getGrupoFamiliar(miGrupoFamiliar.getAsignacionNSS().getNss(), true);
								 strServicioMedico = grupoComplementado.getConDerechoSm();
								 strTipoPension = grupoComplementado.getAsignacionNSS().getTipoPension();
								 
							 }catch(Exception e){
								 log.error("ERROR Al consultar al asegurado como grupo familiar", e);
							 }
						 }
					}
					miGrupoFamiliar = grupoFamiliarService.getCabezaGrupaFamilarRegistrada(miAsignacionNss, miCabezaGF);
					miGrupoFamiliar.setConDerechoSm(strServicioMedico);
					miGrupoFamiliar.getAsignacionNSS().setTipoPension(strTipoPension);
					//se setea el tipo de pension para el caso de los nuevos registros
					if(StringUtils.isEmpty(miAsignacionNss.getTipoPension())){
						miAsignacionNss.setTipoPension(strTipoPension);
						session.setAttribute(Constants.ASIGNACION_NSS_SESSION_NAME, miAsignacionNss);
					}
				
					
				}else{
					miGrupoFamiliar = (GrupoFamiliar) session.getAttribute("miGrupoFamiliar");
				}
			}catch(Exception e){
				logger.debug("ocurio un error cachado al consultar el grupo familiar", e);
				request.setAttribute("exception", "No se pudo localizar la cabeza del grupo familiar exception.RNGD0073");
				return "internalError";
			}	
				
			if(miGrupoFamiliar != null && miGrupoFamiliar.getIndRegistrado() == 0){
				
				// ---------------------------------------------------------
				// Si el estudiante no esta registrado en GrupoFamiliar
				// se envía al cambio de clínica
				// ---------------------------------------------------------
				fordward = Constants.GRUPO_FAMILIAR_FORDWARD2;
				if( miCabezaGF.getEsEstudiante() )
					fordward = Constants.CAMBIO_CLINICA_ESTUDIANTES;
				
			
				try {
					
					if(miCabezaGF.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()))
						conAsegurado = Constants.SIN_ASEGURADO_INTERNO;
					else
						conAsegurado = Constants.SIN_PENSIONADO_INTERNO;
					
				} catch (Exception e) {
					logger.debug(e.getMessage());
					//TODO - agregar mensaje de reggla de negocio
				}
				
			}
					
		}
		
		
		
		try{
			if(!miCabezaGF.getEsEstudiante()) {
				patrones = (List<SujetoObligado>) session.getAttribute("patrones");
				
				if(patrones == null) {
					patrones  = grupoFamiliarService.getPatronesAsegurado(miAsignacionNss);
				}
				
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			request.setAttribute("exception", "Ocurri&oacute; un error al consultar los patrones, favor de intentarlo nuevamente.");
			request.setAttribute("error", "");
			return "internalError";
		}
		
		session.setAttribute("miGrupoFamiliar", miGrupoFamiliar);
		session.setAttribute("patrones", patrones);
		session.setAttribute("conAsegurado", conAsegurado);
		session.setAttribute(Constants.CABEZA_GRUPO_FAM_SESSION, miCabezaGF);
		
		request.setAttribute("opciones", opcionesProperties.getOpciones());
		model.addAttribute("servicios", servicios);
		return fordward;
    }
	
	private void limpiarSession(HttpSession session) {
		session.removeAttribute(FileUploadVB.SES_NAME);
		session.removeAttribute("domicilioAsegurado");
		session.removeAttribute("integranteCambioMedicoSession");
		session.removeAttribute("solicitudActiva");
		session.removeAttribute("datosSolicitudSession");
		session.removeAttribute("datosIntegranteCorreccionSession");
		session.removeAttribute("afectadoAutorizacionCircunscripcion");
		session.removeAttribute("mostrarBoton");
		session.removeAttribute("exception");
		session.removeAttribute("error");
		session.removeAttribute("solicitud");
		session.removeAttribute("reporte");
	}
	
	
	@RequestMapping(value = "busquedaPrincipal")
    public String home1(Model model,Busqueda busqueda, HttpServletRequest request, HttpSession session) {		
		
		String resultado = "";
		Usuario miUsuario = (Usuario) session.getAttribute("usuario");
		
		
		
		if(miUsuario.getPerfilUsuario().getDescripcion().equals("TRAMITADOR")){
			request.setAttribute("ruta",  request.getContextPath() +"/inicio/busquedaPrincipal");
		}else{
			request.setAttribute("ruta", request.getContextPath() +"/inicio/grupoFamiliar");
		}
		
		if(busqueda == null){
			resultado = "busquedaPrincipal";
		}else{
			if(busqueda.equals("1")){
				resultado="grupoFamiliar";
			}else{
				resultado="grupoFamiliar";
			}
		}		
        return resultado;
    }
	
	@RequestMapping(value = "uno/valida")
    public String home2(@Valid Login login, BindingResult result, Model model) {
        		
		String miUsuario=login.getUsuario().toUpperCase(); 		
		String resultado="welcome";
		
		if (result.hasErrors()) {
			return null;
		}
    	if (miUsuario.equals("TRAMITADOR"))
    		resultado="grupoFamiliar";
    	
    	if (miUsuario.equals("DERECHOHABIENTE"))
    		resultado="grupoFamiliar";
    	
    	if (miUsuario.equals("JEFE"))
    		resultado="grupoFamiliar";
    	
        return resultado;
    }
	
	@RequestMapping(value = "/listar/grupoFamiliar", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<GrupoFamiliar> recuperaDatosArchivo(@RequestBody GrupoFamiliarDataTable aoData, SessionStatus status, HttpSession session, HttpServletRequest request){
		log.info("Entrada");
		
		DatosEntradaPaginador<AsignacionNSS> envio = new DatosEntradaPaginador<AsignacionNSS>();
		AsignacionNSS miAsignacionNss = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
        envio.setModelo(miAsignacionNss);
        envio.parserArray(aoData.getAoData());
        
        DatosSalidaPaginador<GrupoFamiliar> salida = null;
		try {
			salida = grupoFamiliarService.paginarGrupoFamiliar(envio);		
		} catch (DerechohabientesBusinessException e) {
			logger.debug(e.getMessage());
			request.setAttribute("errores", "exception.RNGD0073");
		}catch (Exception e) {
			logger.debug(e.getMessage());
			request.setAttribute("exception", "exception.RNGD0073");
			
		}
        salida.setsEcho(envio.getsEcho());
        
        return  salida;		
	}
	
	@RequestMapping(value = "/getParentescos")
	public @ResponseBody List<Parentesco> getUMFBySubdelegacion() throws DerechohabientesBusinessException{
		List<Parentesco> datos = null;
		try {
			datos = grupoFamiliarService.getParentescos();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return datos;
	}
	
	
	/**
	 * Actualiza la cabeza para los asegurados que no son estudiantes
	 * 
	 * @param idAsignacionNss
	 * @param miCabezaGF
	 * @return
	 * @throws Exception
	 */
	private CabezaGrupoFamiliar actualizaCabezaGrupoFamiliar( AsignacionNSS miAsignacionNss, CabezaGrupoFamiliar miCabezaGF ) throws Exception{
		
		if (miCabezaGF == null) {
			miCabezaGF = grupoFamiliarService.cabezaGrupoFamiliar(miAsignacionNss.getIdAsignacionNSS());
		}else{
			if( !miCabezaGF.getEsEstudiante() )
				miCabezaGF = grupoFamiliarService.cabezaGrupoFamiliar(miAsignacionNss.getIdAsignacionNSS());
		}
			
		return miCabezaGF;	
	}

}
