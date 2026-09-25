package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/widget")
public class WidgetController extends AbstractController {
	
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	private static final String[] RPS_17 = new String[] {"A7711544174","M6610218175","B3710738106"};

	@RequestMapping(value="/validacionesVigencia", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> validacionesVigencia(@RequestBody Persona persona){
		//KEYS para el map de resultados
		final String KEY_ERROR = "error";
		final String KEY_MENSAJE = "mensaje";
		final String KEY_REGISTRADO = "registrado";
		final String KEY_DOMICILIO_UMF = "tieneDomicilioUmf";
		final String KEY_ASEGURADO = "asegurado";
		
		Long idPersona = persona.getIdPersona();
		
		//
		GrupoFamiliar asegurado = null;
		Map<String,Object> result = new HashMap<String, Object>();
		
		try {
			asegurado = grupoFamiliarServiceRemote.getDatosWidgetVigencia(idPersona);
		} catch(DerechohabientesBusinessException e) {
			log.error("ocurrio un error al consultar la vigencia del asegurado", e);
			result.put(KEY_ERROR, true);
			result.put(KEY_MENSAJE, e.getSituacion());
		}
		
		if(asegurado != null) {
			result.put(KEY_ASEGURADO, asegurado);
			result.put(KEY_ERROR, false);
			
			Boolean registrado = asegurado.getIndRegistrado().equals(1);
			result.put(KEY_REGISTRADO, registrado);
			
			if(registrado) {
				Boolean tieneDomicilioYUmf = grupoFamiliarServiceRemote.tieneDomicilioYUMF(asegurado.getAsignacionNSS().getIdAsignacionNSS(), idPersona);
				result.put(KEY_DOMICILIO_UMF, tieneDomicilioYUmf);
			}
			
		}
		
		return result;
		
	}

	@RequestMapping(value = "/detalle/{idDummy}", method = RequestMethod.GET)
	public String detalleBeneficiosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idDummy) {

		String datosDummy = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In adipiscing nulla in lacus porttitor viverra. Donec tempus felis vitae dui consectetur, non commodo est placerat. Integer non eros est. Aliquam porttitor in orci sit amet tincidunt. Donec nec varius enim. Donec sit amet posuere velit. Phasellus commodo quam eu massa aliquam posuere. Quisque pharetra ipsum non urna porta, at dapibus mi pharetra. Sed auctor, arcu non consequat sodales, enim magna commodo sem, in ultricies ligula libero in dolor. Morbi gravida lacus id luctus hendrerit. Nam ac quam ullamcorper, imperdiet quam sed, vehicula risus. Sed ac risus a enim mollis pellentesque. In a metus suscipit, dapibus orci scelerisque, cursus dolor.";

		model.addAttribute("DATOS_DUMMY", datosDummy);

		return "widgetDummyContenido";
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping( value = "/vigencia/{idPersona}/{mostrarOpciones}/{busquedaPorNss}/{nss}")
	public String initDerechohabienteVigenciaWidget(Model model, HttpSession session, HttpServletRequest request, @PathVariable Long idPersona
			, @PathVariable Long mostrarOpciones, @PathVariable Integer busquedaPorNss,@PathVariable String nss ) { 
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);
		fisica.setNss(nss);
		
		model.addAttribute("fisica", fisica);
		model.addAttribute("busquedaPorNss", busquedaPorNss);
		model.addAttribute("mostrarOpciones", mostrarOpciones);
		
		return "widgetVigenciaDerechohabienteInit";
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping( value = "/vigencia/detalle/{idPersona}/{mostrarOpciones}/{busquedaPorNss}/{nss}")
	public String detalleDerechohabienteVigenciaWidget(Model model, HttpSession session, HttpServletRequest request, @PathVariable Long idPersona, 
			@PathVariable Long mostrarOpciones,@PathVariable Integer busquedaPorNss, @PathVariable String nss) { 
		
		GrupoFamiliar asegurado = null;
		boolean buscaPorNss = busquedaPorNss.equals(1);
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		boolean modalidad17 = false;
		Boolean isPensionadoMod17Convenio = false;
		Boolean isMod17Convenio = false;
		
		try {
			if(buscaPorNss && !StringUtils.isBlank(nss)) {
				asegurado = grupoFamiliarServiceRemote.getDatosVigenciaPorNss(nss);
			} else {
				asegurado = grupoFamiliarServiceRemote.getDatosWidgetVigencia(idPersona);
			}
			
			cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(asegurado.getAsignacionNSS().getIdAsignacionNSS());
			
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
				modalidad17 = cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getIdModalidad().equals(ModalidadEnum.DIECISIETE.getId()); 
			}
			//Si la calidad es pensionado(6), con último movimiento afiliatorio modalidad 17 y el patron 
			//tiene convenio, se toma el valor <ConDerechoSm> que regresa el WS de vigencia.
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if((cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
					for(String rpMod17: RPS_17) {
						if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
							log.error("Si es patron con convenio " + rpMod17);
							isPensionadoMod17Convenio = true;
						}
					}
				} else {
					if((cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
						for(String rpMod17: RPS_17) {
							if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
								log.error("Si es patron con convenio " + rpMod17);
								isMod17Convenio = true;
							}
						}
					}	
				}
			}
		} catch(DerechohabientesBusinessException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getSituacion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("ocurrio un erro no cachado", e);
			request.setAttribute("error", e.getMessage());	
		}
		
		model.addAttribute("modalidad17", modalidad17);
		model.addAttribute("isMod17Convenio", isMod17Convenio);
		model.addAttribute("asegurado", asegurado);
		model.addAttribute("mostrarOpciones",mostrarOpciones);
		model.addAttribute("isPensionadoMod17Convenio",isPensionadoMod17Convenio);
		
		return "widgetVigenciaDerechohabienteContenido";
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping( value = "/adscripcionVigencia/{nss}/{idIntegrante}/{idAsignacionNss}")
	public String initAdscripcionBeneficiarioVigenciaWidget(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idIntegrante, @PathVariable String nss, @PathVariable Long idAsignacionNss) { 
		
		AsignacionNSS asignacion = new AsignacionNSS();
		asignacion.setIdAsignacionNSS(idAsignacionNss);
		asignacion.setIdPersona(idIntegrante);
		asignacion.setNss(nss);
		
		model.addAttribute("asignacion", asignacion);
		
		return "widgetAdscripcionVigenciaInit";
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping( value = "/adscripcionVigencia/detalle/{nss}/{idIntegrante}/{idAsignacionNss}")
	public String detalleAdscripcionBeneficiarioVigenciaWidget(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idIntegrante, @PathVariable String nss, @PathVariable Long idAsignacionNss) { 
		
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		boolean modalidad17 = false;
		Boolean isPensionadoMod17Convenio = false;
		Boolean isMod17Convenio = false;
		try {
			GrupoFamiliar derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(idAsignacionNss, idIntegrante);
			cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
			
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
				modalidad17 = cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getIdModalidad().equals(ModalidadEnum.DIECISIETE.getId());
			}
			//Si la calidad es pensionado(6), con último movimiento afiliatorio modalidad 17 y el patron 
			//tiene convenio, se toma el valor <ConDerechoSm> que regresa el WS de vigencia.
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if((cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
					for(String rpMod17: RPS_17) {
						if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
							log.error("Si es patron con convenio " + rpMod17);
							isPensionadoMod17Convenio = true;
						}
					}
				} else {
					if((cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17"))){
						for(String rpMod17: RPS_17) {
							if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
								log.error("Si es patron con convenio " + rpMod17);
								isMod17Convenio = true;
							}
						}
					}	
				}
			}
			//Agregamos al modelo los datos del derechohabiente y el domicilio particular
			model.addAttribute("derechohabiente", derechohabiente);
			model.addAttribute("patronImss", cabezaGrupoFamiliar.getPatronImss().equals(1));
			model.addAttribute("isAsegurado",false);
			model.addAttribute("modalidad17", modalidad17);
			model.addAttribute("isMod17Convenio", isMod17Convenio);
			model.addAttribute("isPensionadoMod17Convenio", isPensionadoMod17Convenio);
		}
		catch(DerechohabientesBusinessException e) {
			log.error("ocurrio un erro de derechohabientes", e);
			request.setAttribute("error", e.getSituacion());
			
		}catch (Exception e){
			log.error("ocurrio un erro no cachado", e);
			request.setAttribute("error", e.getMessage());	
		}
		
		return "widgetAdscripcionVigenciaContenido";
	}
	
	@RequestMapping( value = "/servicios/{idAsignacionNss}")
	public String initDerechohabienteServicios(Model model, HttpSession session, HttpServletRequest request, @PathVariable Long idAsignacionNss) {
		
		AsignacionNSS nss = new AsignacionNSS();
		nss.setIdAsignacionNSS(idAsignacionNss);
		
		model.addAttribute("nss", nss);
		
		return "widgetServiciosAseguradoInit";
	}
	
	@RequestMapping( value = "/servicios/detalle/{idAsignacionNss}")
	public String detalleDerechohabienteServicios(Model model, HttpSession session, HttpServletRequest request, @PathVariable Long idAsignacionNss) {
		
		List<ServiciosDTO> servicios = new ArrayList<ServiciosDTO>();
		
		try {
			servicios = grupoFamiliarServiceRemote.getServiciosGrupoFamiliar(idAsignacionNss);
			
		} catch(DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al obtener los servicios", e);
			model.addAttribute("error",e.getSituacion());
		} catch (Exception e) {
			log.error("Ocurrio un error al obtener los servicios", e);
			model.addAttribute("error", "No se encontraron servicios");
		}
		
		model.addAttribute("servicios", servicios);
		
		return "widgetServiciosAseguradoContenido";
	}
	
	@RequestMapping( value = "/domicilio/integrante/{idAsignacionNss}/{nss}/{idIntegrante}")
	public String initDomicilioBeneficiario(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss,@PathVariable String nss, @PathVariable Long idIntegrante) {
		
		AsignacionNSS fisica = new AsignacionNSS();
		fisica.setIdPersona(idIntegrante);
		fisica.setNss(nss);
		fisica.setIdAsignacionNSS(idAsignacionNss);
		
		model.addAttribute("fisica", fisica);
		
		return "widgetDomicilioGrupoFamiliarInit";
	}
	
	@RequestMapping( value = "/domicilio/integrante/detalle/{idAsignacionNss}/{nss}/{idIntegrante}")
	public String detalleDomicilioBeneficiario(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss,@PathVariable String nss, @PathVariable Long idIntegrante) {

		GrupoFamiliar derechohabiente = null;
		try {
			derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(idAsignacionNss, idIntegrante);
			
			if(derechohabiente != null && derechohabiente.getDomicilio() != null) {
				//Agregamos al modelo los datos del derechohabiente y el domicilio particular
				model.addAttribute("derechohabiente", derechohabiente);
			} else {
				model.addAttribute("error", "No se encontro el domicilio asociado al integrante del grupo familiar.");
			}
			
		}
		catch(DerechohabientesBusinessException e) {
			log.error("ocurrio un erro de derechohabientes", e);
			model.addAttribute("error", e.getSituacion());
			
		}catch (Exception e){
			log.error("ocurrio un erro no cachado", e);
			model.addAttribute("error", e.getMessage());	
		}
		
		return "widgetDomicilioGrupoFamiliarContenido";
	}
	
	/*
	 * Comienza metodo que valida si el derechohabiente tiene domicilio asignado
	 */
	@RequestMapping(value = "/validaDomicilio/{idAsignacionNss}/{idPersona}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validaDomicilio(
			HttpServletResponse response, HttpServletRequest request, 
			@PathVariable Long idAsignacionNss, @PathVariable Long idPersona){
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			GrupoFamiliar dhab = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(idAsignacionNss, idPersona);
			
			// ---------------------------------------------------------------------------
			// En caso de no tener domicilio o UMF se lanza el wizard de domicilios
			// para capturar sus datos antes de mostrar el portal de asegurado
			// ---------------------------------------------------------------------------
			if( (dhab.getDomicilio() == null) || (  dhab.getMedicoEnTurno() == null ) || (  dhab.getMedicoEnTurno().getUnidadMedicaFamiliar() == null )  ){
				
				// ------------------------------------------------------------
				// No tiene domicilio
				// No tiene medico asignado
				// No tiene UMF asiganda
				// ------------------------------------------------------------
				result.put("estatus", "0");
			
			}else{
				result.put("estatus", "1");
			}
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			result.put("estatus", "error");
		}
		
		return result;
	}
}
