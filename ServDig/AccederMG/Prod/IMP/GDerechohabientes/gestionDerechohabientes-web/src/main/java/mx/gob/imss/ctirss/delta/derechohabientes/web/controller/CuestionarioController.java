/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PreguntaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author ghdolores
 *
 */
@Controller
@RequestMapping(value = "/cuestionario/*")
public class CuestionarioController extends AbstractController{
	
	@Autowired
	PreguntaServiceRemote preguntasService;
	
	@Autowired
	DerechohabienteServiceRemote derechohabienteService;
	
	@Autowired
	DocumentosServiceRemote documentosService;
	
	@Autowired
	GrupoFamiliarServiceRemote grupoFamiliarService; 
	
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	
	//Pantalla de prueba 
	@RequestMapping(value = "/principal")
	public String pantallaPrincipal( Model model,HttpServletRequest request){
		
		return "principal";
	}
	
	
	@RequestMapping(value = "/obtenerPDF")
	public void obtenerPDF(@RequestParam(value="idSolicitud",required=true) Long idSolicitud,
							@RequestParam(value="idPersona",required=true) Long idPersona,
							Model model,HttpServletResponse response){
		
		
		try {
			//	byte[] res  = (byte[])preguntasService.generaCuestionario(idPersona,idSolicitud,ParentescoEnum.PADRES.getId());
	
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition","inline;filename = sav" );
				//response.getOutputStream().write(res);
				response.getOutputStream().flush();
				response.getOutputStream().close(); 
		     	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo que relaciona el cuestionario aun tipo de tramite
	 * @param registro
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/generaCuestionario",method=RequestMethod.POST)
	public @ResponseBody RespuestaJSON<Boolean>  generarCuestionario(@RequestBody TramiteRegistroDerechohabiente registro, Model model,HttpServletResponse response){
		RespuestaJSON<Boolean> respuesta = new RespuestaJSON<Boolean>();
		try {
			
			Solicitud sol = this.obtenerSolicitud(registro.getTramiteId());
			
			//preguntasService.saveCuestionario(sol.getSolicitudId(), 
			//		registro.getTramiteId(), registro.getFisica().getIdPersona(), ParentescoEnum.PADRES.getId());
			respuesta.setModelo(false);
		} catch (Exception e) {
			respuesta.setModelo(true);
			return respuesta;
		}
		
		return respuesta;
	}
	
	/**
	 * Metodo que busca las preguntas para el cuestioanrio convivencia dependencia
	 * @param idSolicitud
	 * @param idPersona
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pregunta", method=RequestMethod.POST )
	public String capturaCuestionario(@RequestParam("idTramite") Long idTramite,
										Model model,HttpServletRequest request){
		try {
			
			TramiteRegistroDerechohabiente dato= new TramiteRegistroDerechohabiente();
			dato.setTramiteId(idTramite);
			/*
			dato.setTramite(new Tramite());
			dato.getTramite().setIdTramite(idTramite);*/
			
			model.addAttribute("registroDerechohabiente", dato);
		} catch (Exception e) {
			request.setAttribute("errores", e.getMessage());
		}
		
		return "cuestionario";
	}
	
	@RequestMapping(value = "/guardar/")
	public String saveCuestionario(@ModelAttribute("prorrogaEstudios")ConstanciaEstudio prorroga ,
									BindingResult result, SessionStatus status,HttpSession session,Model model) {
		String vista="prorrogasEstudios";
		  return vista;
	}
	
	
	@RequestMapping(value="/pdf", method = RequestMethod.GET)
	public void getCuestionario(@RequestParam(value="idSolicitud") Long idSolicitud
			,@RequestParam(value="idPersona") Long idPersona,HttpServletResponse response){
		
		try {
			byte[] res  = null; //(byte[])preguntasService.generaCuestionario(idPersona,idSolicitud,ParentescoEnum.PADRES.getId());
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition","filename = sav" );
			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
    }
	
	@RequestMapping(value="/actualizar", method = RequestMethod.POST)
	public void updateCuestionario(@RequestBody  TramiteRegistroDerechohabiente registro ,HttpSession session,HttpServletResponse response){
		
		Usuario usuario=(Usuario)session.getAttribute(Usuario.SES_NAME); 
		
		try {
			//preguntasService.updateCuestionario(registro.getEvaluacionCuestionario(),registro.getTramiteId(),usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/actualizarRegistro", method = RequestMethod.POST)
	public void updateRegistroDerechohabiente(@RequestBody TramiteRegistroDerechohabiente registro ,HttpServletResponse response){
		
		registro.setEstadoTramite(new EstadoTramite());
		if(registro.getResultado()){
			registro.getEstadoTramite().setIdEstadoTramitePersona(Integer.valueOf(""+EstadoTramiteEnum.ESPERA_TRAMITADOR.getId()));
		}else{
			registro.getEstadoTramite().setIdEstadoTramitePersona(Integer.valueOf(""+EstadoTramiteEnum.ESPERA_AUTORIZACION.getId()));
		}
		
		try {
			derechohabienteService.updateResultadoCuestionario(registro);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	
	/**
	 * Metodo que extrae la calificacion del cuestionario, que fue respondido por sistema
	 * @param idSolicitud
	 * @param idPersona
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/corroborarResultado", method=RequestMethod.POST )
	public String corroborarResultado(@RequestParam("idTramite") Long idTramite,Model model,HttpServletRequest request){
		TramiteRegistroDerechohabiente derechohabiente =null;
			try {
				derechohabiente =derechohabienteService.getRegistroDerechohabiente(idTramite);
			} catch (DerechohabientesBusinessException e) {
				request.setAttribute("errores", "msg05" );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(derechohabiente==null||derechohabiente.getEvaluacionCuestionario()==null
					|| derechohabiente.getEvaluacionCuestionario().longValue()<1){
				request.setAttribute("errores", "msg05" );
			}else{
				model.addAttribute("derechohabiente", derechohabiente);
			}
		
		return  "corroborarResultado";
	}
	
	private Solicitud obtenerSolicitud(Long idTramite) {
		
		Solicitud encontrada = null;
		try {
			encontrada = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
		}catch(Exception e) {
			log.error("No se pudo consutar la solicitud", e);
		}
		return encontrada;
	}
}
