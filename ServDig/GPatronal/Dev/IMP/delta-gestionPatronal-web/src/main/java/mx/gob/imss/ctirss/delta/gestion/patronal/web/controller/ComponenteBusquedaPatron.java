package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.PatronSustitucionFusionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/componente/busquedaRP")
public class ComponenteBusquedaPatron extends AbstractController {

	private final String VIEW_PRUEBA= "pruebaComponentePatrones";
	private final String VIEW_COMPONENTE = "inicioComponenteBusquedaRP";
	public static final String KEY_PATRONES_FUSIONADOS = "keySesssionPatronesEncontrados";
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	@Autowired
	private PatronSustitucionFusionBusinessRemote patronSustitucionFusionBusinessRemote;
	
	@RequestMapping("/")
	public String init(HttpSession session) {
		log.debug("::: Quitando de sesion lista de patrones a sustituir");
		session.removeAttribute(KEY_PATRONES_FUSIONADOS);
		return VIEW_COMPONENTE;
	}
	
	@RequestMapping("/prueba")
	public String pruebaComponente() {
		return VIEW_PRUEBA;
	}
	
	@RequestMapping(value="/buscar" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPatron(@RequestBody SujetoObligado busqueda, HttpSession session) {
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		SujetoObligado sujetoEncontrado = null;
		Boolean fusionado = false;
		Boolean fusionadoConOtro= false;
		Boolean patronMismaEmpresa = false;
		Boolean patronMismoMunicipio = false; //se modifica por solicitud de usuario
		String rfc = busqueda.getFisica() != null && busqueda.getFisica().getRfc() != null ? busqueda.getFisica().getRfc() : null;
		
		log.debug("busquedaPatron =======>  Buscando patron a sustituir " + busqueda.getNumeroRegistroPatronal());
		sujetoEncontrado = sujetoObligadoServiceBusinessRemote.consultarPorNumeroRegistroPatronal(busqueda.getNumeroRegistroPatronal());
		
		if(sujetoEncontrado != null) {
			
			log.debug("busquedaPatron =======>  Patron a sustituir " + busqueda.getNumeroRegistroPatronal() + " encontrado");
			log.debug("busquedaPatron =======>  Estado de baja " + sujetoEncontrado.getDescSituacionBaja() + " - " + sujetoEncontrado.getFechaBaja());
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////
			//Se omite regla de mismo municipio para todos los patrones por requerimiento normativo INC975684/4395073
			//////////////////////////////////////////////////////////////////////////////////////////////////////////
//			if( !(sujetoEncontrado.getClasificacion() != null && sujetoEncontrado.getClasificacion().getIndRegPatClase() != null
//					&& sujetoEncontrado.getClasificacion().getIndRegPatClase().intValue() == 1) ){
//				log.debug("::: El rp a sustituir " + sujetoEncontrado.getNumeroRegistroPatronal() + " no es RPC");
//				String cveMpio = busqueda.getMunicipioIMSS() != null && busqueda.getMunicipioIMSS().getIdMunicipio() != null ? busqueda.getMunicipioIMSS().getIdMunicipio() : null;
//				log.debug("busquedaPatron =======> El municipio del patron seleccionado para el tramite es: " + cveMpio);
//				if(cveMpio != null){
//					String mpioEncontrado = sujetoEncontrado.getMunicipioIMSS() != null ? sujetoEncontrado.getMunicipioIMSS().getIdMunicipio() : sujetoEncontrado.getMunicipioIMSS().getIdMunicipio();
//					log.debug("busquedaPatron =======> El Municipio del patron a sustituir "+sujetoEncontrado.getNumeroRegistroPatronal()+" es: " + mpioEncontrado);
//					if(cveMpio.equals(mpioEncontrado)){ // se pone en false para no enviar el error ya que los rp's son del mismo mpio
//						patronMismoMunicipio = false;
//						log.debug("busquedaPatron =======> El patron a sustituir "+sujetoEncontrado.getNumeroRegistroPatronal() + " se marca como mismo municipio");
//					}						
//				}
//			}else{ // se pone en false para no enviar el error ya que no aplica para RPC
//				patronMismoMunicipio = false;
//				log.debug("busquedaPatron =======> El rp a sustituir " + sujetoEncontrado.getNumeroRegistroPatronal() + " tiene marca de RPC se omite validacion de municipio");
//			}

			//Se omite a solicitud de usuario normativo
//			if( !(sujetoEncontrado.getClasificacion() != null && sujetoEncontrado.getClasificacion().getIndRegPatClase() != null
//			&& sujetoEncontrado.getClasificacion().getIndRegPatClase().intValue() == 1) ){
//				log.debug("::: El rp a sustituir " + sujetoEncontrado.getNumeroRegistroPatronal() + " no es RPC, se aplica regla de RFC");
//				log.debug("busquedaPatron =======> El rfc de la empresa es " + rfc);
//				if(!patronMismoMunicipio && rfc != null ) {
//					String rfcEncontrado = sujetoEncontrado.getFisica() != null ? sujetoEncontrado.getFisica().getRfc() : sujetoEncontrado.getMoral().getRfc();
//					log.debug("busquedaPatron =======> El rfc de la empresa es " + rfc + " y el rfc del patron encontrado es " + rfcEncontrado);
//					//veriricamos si el rfc del patron capturado es el mismo que es del patorn que realiza el tramite
//					patronMismaEmpresa = rfc.equals(rfcEncontrado);
//				}
//			}else{
//				patronMismaEmpresa = false;
//				log.debug("busquedaPatron =======> El rp a sustituir " + sujetoEncontrado.getNumeroRegistroPatronal()
//						+ " tiene marca de RPC se omite validacion de RFC");
//			}
			
			//vemos si verificamos la fusion
			//Se omite ya que por Mm no se valida si ya tiene sustitucion
//			if(!patronMismoMunicipio && !patronMismaEmpresa && busqueda.getCveIdSujetoObligado() !=null) {
//				Long idPatronSO = busqueda.getCveIdSujetoObligado();
//				Long idPatronSOFusionar = sujetoEncontrado.getCveIdSujetoObligado();
//				//validamos si el patron ya se encuentra relacionado con el patron que realiza el tramite 
//				fusionado = patronSustitucionFusionBusinessRemote.validarExistenciaFusion(idPatronSO, idPatronSOFusionar);
//				//Sino se encuentra relacionado verificamos que el patron a fusionar no se encuentre ya fusionado con otros
//				if(!fusionado) {
//					fusionadoConOtro = patronSustitucionFusionBusinessRemote.validarPatronExisteComoFusionado(idPatronSOFusionar);
//				}
//				
//			}
			
//			if(!patronMismoMunicipio && !patronMismaEmpresa && !fusionado && !fusionadoConOtro) {				
//				List<SujetoObligado> listaPatrones = (List<SujetoObligado>) session.getAttribute(KEY_PATRONES_FUSIONADOS);
//				listaPatrones = listaPatrones != null ? listaPatrones : new ArrayList<SujetoObligado>();
//				listaPatrones.add(sujetoEncontrado);
//				session.setAttribute(KEY_PATRONES_FUSIONADOS, listaPatrones);
//			}

			List<SujetoObligado> listaPatrones = (List<SujetoObligado>) session.getAttribute(KEY_PATRONES_FUSIONADOS);
			listaPatrones = listaPatrones != null ? listaPatrones : new ArrayList<SujetoObligado>();
			listaPatrones.add(sujetoEncontrado);
			session.setAttribute(KEY_PATRONES_FUSIONADOS, listaPatrones);
			
		}else{
			log.debug("busquedaPatron =======>  El rp a sustituir " + busqueda.getNumeroRegistroPatronal() + " no fue encontrado");
		}
		
		log.debug("busquedaPatron =======>  Resultado:::: isFusionado:"
				+ fusionado + ", isFusionadoConOtro: " + fusionadoConOtro
				+ ", isMismaEmpresa: " + patronMismaEmpresa
				+ ", isMismoMunicipio: " + patronMismoMunicipio);
		if (sujetoEncontrado != null
				&& sujetoEncontrado.getClasificacion() != null
				&& sujetoEncontrado.getClasificacion().getPrimaSRTActual() != null) {
			log.debug("busquedaPatron =======> PrimaSRTActual: " + sujetoEncontrado.getClasificacion().getPrimaSRTActual());			
		}
		
		respuesta.put("isFusionado", fusionado);
		respuesta.put("isFusionadoConOtro", fusionadoConOtro);
		respuesta.put("sujetoObligado", sujetoEncontrado);
		respuesta.put("isMismaEmpresa", patronMismaEmpresa);
		respuesta.put("isMismoMunicipio", patronMismoMunicipio);
		
		return respuesta;
	}
	
	
	@RequestMapping(value="/validaFechaBaja" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validaFechaBaja(@RequestBody SujetoObligado busqueda, HttpSession session) throws ParseException {
		log.debug("::: Fecha de baja a validar: " + busqueda.getFechaBaja());	
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		boolean res = validaFechaBaja(formato.parse(busqueda.getFechaBaja()));
		Map<String, Object> respuesta = new HashMap<String, Object>();
		if(res) {
			respuesta.put("resultado", res);
		}else {
			respuesta.put("resultado", false);
		}
		return respuesta;
	}
			
	@RequestMapping(value="/delete" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> eliminarPatron(@RequestBody SujetoObligado eliminar, HttpSession session) {
		
		log.debug("::: Sujeto obligado a borrar del listado: " + eliminar.getCveIdSujetoObligado());
		
		List<SujetoObligado> listaPatrones = (List<SujetoObligado>) session.getAttribute(KEY_PATRONES_FUSIONADOS);
		List<SujetoObligado> listaAux = new ArrayList<SujetoObligado>();
		
		if(listaPatrones != null){
			for(SujetoObligado sujeto: listaPatrones) {
				if(!sujeto.getCveIdSujetoObligado().equals(eliminar.getCveIdSujetoObligado())) {
					listaAux.add(sujeto);
				}
			}
		}
		
		log.debug("::: Patrones en la lista: " + listaAux.size());
		session.setAttribute(KEY_PATRONES_FUSIONADOS, listaAux);
		return null;
	}
	
	@RequestMapping(value = "/setPatrones", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPatronesDefault(@RequestBody SujetoObligado sujeto, HttpSession session) {
		session.setAttribute(KEY_PATRONES_FUSIONADOS, sujeto.getSujetosObligados());
		return null;
	}
	
	
	private boolean validaFechaBaja(Date fechaBaja){
		Calendar calFecBajaNrp = Calendar.getInstance();
		calFecBajaNrp.setTime(fechaBaja); //setea la fecha de baja del patron
		calFecBajaNrp.set(Calendar.HOUR, 0);
		calFecBajaNrp.set(Calendar.MINUTE, 0);
		calFecBajaNrp.set(Calendar.SECOND, 0);
		calFecBajaNrp.set(Calendar.MILLISECOND, 0);
		calFecBajaNrp.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaBajaNRP = calFecBajaNrp.getTime();				
		
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);		
		Date fechaActual = calendario.getTime();
		Calendar calendarioSeisMesesAnt = Calendar.getInstance();
		calendarioSeisMesesAnt.setTime(fechaActual);
		calendarioSeisMesesAnt.add(Calendar.DATE, -180); //Valida 6 meses
		calendarioSeisMesesAnt.set(Calendar.HOUR, 0);
		calendarioSeisMesesAnt.set(Calendar.MINUTE, 0);
		calendarioSeisMesesAnt.set(Calendar.SECOND, 0);
		calendarioSeisMesesAnt.set(Calendar.MILLISECOND, 0);
		calendarioSeisMesesAnt.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaSeisMesesAnt = calendarioSeisMesesAnt.getTime();

		if(fechaBajaNRP.before(fechaSeisMesesAnt)){
			log.debug("::: La fecha de baja del NRP es anterior a 6 meses");
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="/obtienePrimaHistorica" , method = RequestMethod.POST)
	@ResponseBody
	public String obtienePrimaHistorica(@RequestParam("nrp") String nrp,			
			@RequestParam("fechaSurteEfecto") Date fechaSurteEfecto, HttpSession session, HttpServletRequest request) throws ParseException {
		log.debug("::: Se buscara la prima historica mas cercana a la fecha surte efecto. nrp: "
				+ nrp  + ", fechaSurteEfecto: " + fechaSurteEfecto);
		
		SimpleDateFormat fechaBase = new SimpleDateFormat("yyyy/MM/dd");
		String fecSurteEfecto = fechaBase.format(fechaSurteEfecto);
		
		// En fechaAlta viene la fecha surte efecto
		String primaH = sujetoObligadoServiceBusinessRemote.consultaPrimaHistorica(nrp, fecSurteEfecto);
		log.debug("::: Prima historica encontrada: " + primaH);
		if(primaH != null && primaH.trim().length() >= 0) {
			return primaH;
		}

		return "error";
	}
	
}
