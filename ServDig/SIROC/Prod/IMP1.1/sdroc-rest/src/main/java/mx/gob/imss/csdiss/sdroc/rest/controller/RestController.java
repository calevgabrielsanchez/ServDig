package mx.gob.imss.csdiss.sdroc.rest.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.NamingException;

import mx.gob.imss.csdiss.sdroc.dto.*;
import mx.gob.imss.csdiss.sdroc.service.interfaces.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller

public class RestController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6224850252839948737L;
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);
	
	
	@Autowired
	TipoObraService tipoObraServiceEjb;
	
	@Autowired
	TipoPatronService tipoPatronServiceEjb;
	
	@Autowired
	TipoPersonaService tipoPersonaServiceEjb;
	
	@Autowired
	TipoRegistroService tipoRegistroServiceEjb;
	
	@Autowired
	TipoIncidenciaService tipoIncidenciaServiceEjb;
	
	@Autowired
	ObjetoContratoService objetoContratoServiceEjb;

	@Autowired
	MotivoService motivoServiceEjb;
	
	@Autowired
	InformacionObraService informacionObraServiceEjb;
	
	@Autowired
	InformacionIncidenciaService informacionIncidenciaServiceEjb;
	
	@Autowired
	AvisoObraService avisoObraServiceEjb;
	
	@Autowired
	UbicacionObraService ubicacionObraServiceEjb;
	
	@Autowired
	SubdelegacionService subdelegacionServiceEJB;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "status";
	}	
	/**
	 * Devuelve la fecha del sistema obtenida del servidor.
	 * @return
	 * @throws NamingException
	 */
	@RequestMapping(value = "/fechaActual", method = RequestMethod.GET)
	@ResponseBody
	public Long getDate() throws NamingException {
		
		Date hoy = new Date();
		
		return hoy.getTime(); 
	}
	
	@RequestMapping(value = "/tipoObras", method = RequestMethod.GET)
	@ResponseBody
	public List<TipoObraDTO> getAllTiposObras() throws NamingException {
		
		List<TipoObraDTO> lista = null;		
		logger.info("Inside getAllTiposObras() method...");
		lista = tipoObraServiceEjb.consultarTiposDeObras();
		return lista;

	}
	
	@RequestMapping(value = "/actualizaAvisoObra/{cveAvisoObra}", method = RequestMethod.GET)
	@ResponseBody
	public String actualizaAvisoObraByCveAvisoObra(@PathVariable("cveAvisoObra") Long cveAvisoObra) throws NamingException {
		
		logger.info("Inside update aviso obras() method...");
		avisoObraServiceEjb.actualizarAvisoObra(cveAvisoObra);
		
		return "";
	}
	
	
	/*
	 * @RequestMapping(value = "/consultarAvisoObraPorCveAvisoObra/{cveAvisoObra}", method = RequestMethod.GET)
	@ResponseBody
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(@PathVariable("cveAvisoObra") String cveAvisoObra) throws NamingException {
		AvisoObraDTO  avisoObraDTO = avisoObraServiceEjb.consultarAvisoObraPorCveAvisoObra(cveAvisoObra);
		return avisoObraDTO;

	}*
	 */
	
	@RequestMapping(value = "/tipoObrasPorClasificacion/{cveClasificacionObra}", method = RequestMethod.GET)
	@ResponseBody
	public List<TipoObraDTO> getAllTiposObrasPorClasificacionObra(@PathVariable("cveClasificacionObra") long cveClasificacionObra) throws NamingException {
		List<TipoObraDTO> lista = tipoObraServiceEjb.consultarTiposDeObrasPorClasificacionObra(cveClasificacionObra);
		return lista;

	}

	
	@RequestMapping(value = "/tiposPatrones", method = RequestMethod.GET)
	@ResponseBody
	public List<TipoPatronDTO> getAllTiposPatrones() throws NamingException {
		List<TipoPatronDTO> lista = tipoPatronServiceEjb.consultarTodosTiposPatrones();
		return lista;

	}
	
	@RequestMapping(value = "/tiposPersonas", method = RequestMethod.GET)
	@ResponseBody
	public List<TipoPersonaDTO> getAllTiposPersonas() throws NamingException {
		List<TipoPersonaDTO> lista = tipoPersonaServiceEjb.consultarTodosTiposPersonas();
		return lista;

	}
	
	@RequestMapping(value = "/tiposRegistros", method = RequestMethod.GET)
	@ResponseBody
	public List<TipoRegistroDTO> getAllTiposRegistros() throws NamingException {
		List<TipoRegistroDTO> lista = tipoRegistroServiceEjb.consultarTodosTiposRegistros();
		return lista;

	}
	
	@RequestMapping(value = "/tiposIncidencias", method = RequestMethod.GET)
	@ResponseBody
	public List<TipoIncidenciaDTO> getAllTiposIncidencias() throws NamingException {
		List<TipoIncidenciaDTO> lista = tipoIncidenciaServiceEjb.consultarTodosTiposIncidencias();
		return lista;

	}
	
	@RequestMapping(value = "/objetosContrato", method = RequestMethod.GET)
	@ResponseBody
	public List<ObjetoContratoDTO> getAllObjetosContrato() throws NamingException {
		List<ObjetoContratoDTO> lista = objetoContratoServiceEjb.consultarTodosObjetosContrato();
		return lista;

	}

	@RequestMapping(value = "/motivosPorIncidencia/{cveTipoIncidencia}", method = RequestMethod.GET)
	@ResponseBody	
	public List<MotivoDTO> getAllMotivosPorTiposIncidencias(@PathVariable("cveTipoIncidencia") Long cveTipoIncidencia) throws NamingException {
		List<MotivoDTO> lista = motivoServiceEjb.consultarMotivosPorTipoIncidencia(cveTipoIncidencia);
		return lista;

	}
	
	@RequestMapping(value = "/informacionObras", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> getAllInformacionObras() throws NamingException {
		List<InformacionObraDTO> lista = informacionObraServiceEjb.consultarTodasInformacionObras();
		return lista;

	}
	
	@RequestMapping(value = "/consultarObraPorCveRfcYCveRegPatronal/{cveRfc}/{cveRegPatronal}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcYCveRegPatronal(@PathVariable("cveRfc") String cveRfc,
			@PathVariable("cveRegPatronal") String cveRegPatronal) throws NamingException {
		List<InformacionObraDTO> lista = informacionObraServiceEjb.consultarInformacionObrasPorCveRfcYCveRegPatronal(cveRfc,cveRegPatronal);
		return lista;

	}
	
	@RequestMapping(value = "/consultarObras/{cveRfc}/{cveRegPatronal}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarObras(@PathVariable("cveRfc") String cveRfc,
			@PathVariable("cveRegPatronal") String cveRegPatronal) throws NamingException {
		Map<String, Object>  mapObras= informacionObraServiceEjb.consultarObrasPorRfcYRp(cveRfc,cveRegPatronal,null, null);
		List<InformacionObraDTO> lista = (List<InformacionObraDTO>)mapObras.get("obras");
		return lista;

	}
	
	@RequestMapping(value = "/consultarObrasPorCveRfc/{cveRfc}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfc(@PathVariable("cveRfc") String cveRfc) throws NamingException {
		List<InformacionObraDTO> lista = informacionObraServiceEjb.consultarInformacionObrasPorCveRfc(cveRfc);
		return lista;

	}
	
	@RequestMapping(value = "/consultarformacionObraAgrupadaByCveRfc/{cveRfc}", method = RequestMethod.GET)
	@ResponseBody
	public List<RegistroPatronalDTO> consultarformacionObraAgrupadaByCveRfc(@PathVariable("cveRfc") String cveRfc) throws NamingException {
		List<RegistroPatronalDTO> lista = informacionObraServiceEjb.consultarformacionObraAgrupadaByCveRfc(cveRfc);
		return lista;

	}
	
	@RequestMapping(value = "/consultarObrasPorCveRegPatronal/{cveRegPatronal}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRegPatronal(@PathVariable("cveRegPatronal") String cveRegPatronal) throws NamingException {
		List<InformacionObraDTO> lista = informacionObraServiceEjb.consultarInformacionObrasPorCveRegPatronal(cveRegPatronal);
		return lista;

	}

	@RequestMapping(value = "/consultarObraPorCveRegistroObra/{cveRegistroObra}", method = RequestMethod.GET)
	@ResponseBody
	public InformacionObraDTO consultarInformacionObraPorCveRegistroObra(@PathVariable("cveRegistroObra") String cveRegistroObra) throws NamingException {
		InformacionObraDTO  informacionObraDTO = informacionObraServiceEjb.consultarInformacionObraPorCveRegistroObra(cveRegistroObra);
		return informacionObraDTO;

	}
	
	@RequestMapping(value = "/consultarObrasPorCveRegistroObraPrincipal/{cveRegistroObraPrincipal}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarInformacionObraPorCveRegistroObraPrincipal(@PathVariable("cveRegistroObraPrincipal") Long cveRegistroObraPrincipal) throws NamingException {
		List<InformacionObraDTO> lista  = informacionObraServiceEjb.consultarInformacionObrasPorCveRegistroObraPrincipal(cveRegistroObraPrincipal);
		return lista;

	}	
	
	@RequestMapping(value = "/consultarObraPorCveInformacionObra/{cveInformacionObra}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InformacionObraDTO> consultarInformacionObraPorCveInformacionObra(@PathVariable("cveInformacionObra") Long cveInformacionObra) throws NamingException {
		InformacionObraDTO  informacionObraDTO = informacionObraServiceEjb.consultarInformacionObraPorId(cveInformacionObra);
		ResponseEntity<InformacionObraDTO> myResponse = new ResponseEntity<InformacionObraDTO>(informacionObraDTO,HttpStatus.OK);
		return myResponse;

	}	
	
	@RequestMapping(value = "/guardarInformacionObra", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<InformacionObraDTO> createInformacionObra(@RequestBody InformacionObraDTO informacionObraDTO) throws NamingException {
		InformacionObraDTO informacionObra = null;
		informacionObra = informacionObraServiceEjb.insertarInformacionObra(informacionObraDTO);
		ResponseEntity<InformacionObraDTO> myResponse = new ResponseEntity<InformacionObraDTO>(informacionObra,HttpStatus.OK);
        return myResponse;


	}
	
	@RequestMapping(value = "/actualizarInformacionObra", method = RequestMethod.POST)
	@ResponseBody
	public void updateInformacionObra(@RequestBody InformacionObraDTO informacionObraDTO) throws NamingException {
		informacionObraServiceEjb.actualizarInformacionObra(informacionObraDTO);


	}

	@RequestMapping(value = "/guardarInformacionIncidencia", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<InformacionIncidenciaDTO> createInformacionIncidencia(@RequestBody InformacionIncidenciaDTO informacionIncidenciaDTO) throws NamingException {
		InformacionIncidenciaDTO informacionIncidencia = informacionIncidenciaServiceEjb.insertarInformacionIncidencia(informacionIncidenciaDTO);
		ResponseEntity<InformacionIncidenciaDTO> myResponse = new ResponseEntity<InformacionIncidenciaDTO>(informacionIncidencia,HttpStatus.OK);
        return myResponse;


	}
	
	@RequestMapping(value = "/obtenerDesMotivo/{cveIncidencia}/{cveTipoIn}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> obtenerDescripcionMotivo(@PathVariable("cveIncidencia") Long cveIncidencia, @PathVariable("cveTipoIn")Long cveTipoIn) throws NamingException {
		String desMotivo = null;
		desMotivo = informacionIncidenciaServiceEjb.consultarDescripcionMotivoPorClaveMotivo(cveIncidencia,cveTipoIn);
		ResponseEntity<String> myResponse = new ResponseEntity<String>(desMotivo,HttpStatus.OK);		
		return myResponse;
	}
	
	@RequestMapping(value = "/consultarUltimoReporteBimestralPorCveInformacionObra/{cveInformacionObra}", method = RequestMethod.GET)
	@ResponseBody
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPorCveInformacionObra(@PathVariable("cveInformacionObra") Long cveInformacionObra) throws NamingException {
				
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		informacionIncidenciaDTO = informacionIncidenciaServiceEjb.consultarUltimoReporteBimestralPorCveInformacionObra(cveInformacionObra);
		
		return informacionIncidenciaDTO;
	}
	
	@RequestMapping(value = "/consultarIncidenciasPorTipoIncidenciaPorCveInformacionObra/{cveInformacionObra}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionIncidenciaDTO> consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(@PathVariable("cveInformacionObra") Long cveInformacionObra) throws NamingException {
		List<InformacionIncidenciaDTO> listaInformacionIncidenciaDTO = null;
		listaInformacionIncidenciaDTO = informacionIncidenciaServiceEjb.consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(cveInformacionObra);
		return listaInformacionIncidenciaDTO;

	}
	
	
	@RequestMapping(value = "/consultarIncidenciaPorTipoIncidenciaPorCveInformacionObra/{cveInformacionObra}/{cveTipoIncidencia}", method = RequestMethod.GET)
	@ResponseBody
	public InformacionIncidenciaDTO consultarUltimasIncidenciaPorTipoIncidenciaPorCveInformacionObra(@PathVariable("cveInformacionObra") Long cveInformacionObra,@PathVariable("cveTipoIncidencia") Long cveTipoIncidencia) throws NamingException {
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		informacionIncidenciaDTO = informacionIncidenciaServiceEjb.consultarUltimaIncidenciaPorTipoIncidenciaPorCveInformacionObra(cveInformacionObra, cveTipoIncidencia);
		return informacionIncidenciaDTO;

	}		
	
	
	@RequestMapping(value = "/consultarAvisoObraPorCveAvisoObra/{cveAvisoObra}", method = RequestMethod.GET)
	@ResponseBody
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(@PathVariable("cveAvisoObra") String cveAvisoObra) throws NamingException {
		AvisoObraDTO  avisoObraDTO = avisoObraServiceEjb.consultarAvisoObraPorCveAvisoObra(cveAvisoObra);
		return avisoObraDTO;

	}
	
	@RequestMapping(value = "/consultarAvisoObraPorCveAvisoObra/{cveAvisoObra}/{rfc}/{registroPatronal}", method = RequestMethod.GET)
	@ResponseBody
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(@PathVariable("cveAvisoObra") String cveAvisoObra, @PathVariable("rfc") String rfc, @PathVariable("registroPatronal") String registroPatronal) throws NamingException {
		AvisoObraDTO  avisoObraDTO = avisoObraServiceEjb.consultarAvisoObraPorCveAvisoObra(cveAvisoObra, rfc, registroPatronal);
		return avisoObraDTO;

	}
	
	@RequestMapping(value = "/consultarAvisoObraPorCveRfc/{cveRfc}", method = RequestMethod.GET)
	@ResponseBody
	public List<AvisoObraDTO> consultarAvisoObraPorCveRfc(@PathVariable("cveRfc") String cveRfc) throws NamingException {
		
		List<AvisoObraDTO> listaAvisoObrasDTO = null;
		
		listaAvisoObrasDTO = avisoObraServiceEjb.consultarAvisosObraPorCveRfc(cveRfc);
		
		return listaAvisoObrasDTO;

	}
	
	@RequestMapping(value = "/consultarNumeroTotalObrasPorCveRfcyAnio/{cveRfc}/{anio}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Long> consultarNumeroTotalObrasPorCveRfcyAnio(@PathVariable("cveRfc") String cveRfc,
			@PathVariable("anio") String anio) throws NamingException {
		
		int numTotalObras = 0;
		numTotalObras = informacionObraServiceEjb.consultarNumeroTotalObrasPorCveRfcyAnio(cveRfc, anio);
		ResponseEntity<Long> myResponse = new ResponseEntity<Long>(new Long(numTotalObras),HttpStatus.OK);
        return myResponse;


	}
	
	@RequestMapping(value = "/consultarInformacionObrasPorCveRfcyAnio/{cveRfc}/{anio}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcyAnio(@PathVariable("cveRfc") String cveRfc,
			@PathVariable("anio") String anio) throws NamingException {
		
		List<InformacionObraDTO>  informacionObraDTO = informacionObraServiceEjb.consultarInformacionObrasPorCveRfcyAnio(cveRfc, anio);
        return informacionObraDTO;


	}	

	@RequestMapping(value = "/guardarAvisoObra", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AvisoObraDTO> createAvisoObra(@RequestBody AvisoObraDTO avisoObraDTO) throws NamingException {

		AvisoObraDTO avisoObra = null;
		avisoObra = avisoObraServiceEjb.insertarAvisoObra(avisoObraDTO);
		ResponseEntity<AvisoObraDTO> myResponse = new ResponseEntity<AvisoObraDTO>(avisoObra,HttpStatus.OK);
        return myResponse;


	}
	
	@RequestMapping(value = "/isValidoCpUbicacionObra/{cveRp}/{cveCodigoPostal}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> isValidoCpUbicacionObra(@PathVariable("cveRp") String cveRp,
			@PathVariable("cveCodigoPostal") String cveCodigoPostal) throws NamingException {
		
		Boolean isValido = false;
		
		isValido = ubicacionObraServiceEjb.isValidoCpUbicacionObra(cveRp, cveCodigoPostal);
		
		ResponseEntity<Boolean> myResponse = new ResponseEntity<Boolean>(isValido,HttpStatus.OK);
        return myResponse;
	}
	
	@RequestMapping(value = "/validaCircunscripcionCp/{codigoPostal}/{idDelegacion}/{idSubdelegacion}")
	@ResponseBody
	public ResponseEntity<Boolean> validCircunscripcionCp(@PathVariable("codigoPostal") String codigoPostal,
			@PathVariable("idDelegacion") Long idDelegacion, @PathVariable("idSubdelegacion") Long idSubdelegacion){
		
		Boolean isValido = false;
		
		isValido = ubicacionObraServiceEjb.validCircunscripcion(codigoPostal, idDelegacion, idSubdelegacion );
		
		ResponseEntity<Boolean> myResponse = new ResponseEntity<Boolean>(isValido,HttpStatus.OK);
        return myResponse;
	}
	
	@RequestMapping(value = "/consultarUltimoReporteBimestralPresentado/{cveInformacionObra}", method = RequestMethod.GET)
	@ResponseBody
	public InformacionIncidenciaDTO consultarUltimoReporteBimestralPresentado(@PathVariable("cveInformacionObra") Long cveInformacionObra) throws NamingException {
		InformacionIncidenciaDTO informacionIncidenciaDTO = null;
		informacionIncidenciaDTO = informacionIncidenciaServiceEjb.consultarUltimoReporteBimestralPresentado(cveInformacionObra);		
		return informacionIncidenciaDTO;
	}	
	
	@RequestMapping(value = "/consultarReporteInformacionObrasPorCveRfcyAnio/{cveRfc}/{anio}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarReporteInformacionObrasPorCveRfcyAnio(@PathVariable("cveRfc") String cveRfc,
			@PathVariable("anio") String anio) throws NamingException {
		
		List<InformacionObraDTO>  informacionObraDTO = informacionObraServiceEjb.consultarReporteGeneralObrasPorCveRfcYAnio(cveRfc, anio);
        return informacionObraDTO;
	}
	
	@RequestMapping(value = "/consultarReporteInformacionObrasPorCveRegPatronal/{cveRegPatronal}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionObraDTO> consultarReporteInformacionObrasPorCveRegPatronal(@PathVariable("cveRegPatronal") String cveRegPatronal) throws NamingException {
		
		List<InformacionObraDTO>  informacionObraDTO = informacionObraServiceEjb.consultarReporteGeneralObrasPorCveRegPatronal(cveRegPatronal);
        return informacionObraDTO;
	}
	
	
	@RequestMapping(value = "/consultarSubdelegacionesImssPorCp/{cveCodigoPostal}", method = RequestMethod.GET)
	@ResponseBody
	public List<SubDelegacionDTO> consultarSubdelegacionesImssByCp(@PathVariable("cveCodigoPostal") String cveCodigoPostal) throws NamingException {
		
		List<SubDelegacionDTO>  listaSubDelegacionDTO = subdelegacionServiceEJB.consultarSubdelegacionesImssByCp(cveCodigoPostal);
		
        return listaSubDelegacionDTO;
	}
	
	
	
	@RequestMapping(value = "/consultarIncidenciasPorCveInformacionObra/{cveInformacionObra}", method = RequestMethod.GET)
	@ResponseBody
	public List<InformacionIncidenciaDTO> consultarIncidenciasPorCveInformacionObra(@PathVariable("cveInformacionObra") String cveInformacionObra) throws NamingException {
		
		List<InformacionIncidenciaDTO>  listaInformacionIncidenciaDTO = informacionIncidenciaServiceEjb.consultarIncidenciasPorCveInformacionObra(cveInformacionObra);
		
        return listaInformacionIncidenciaDTO;
	}
	
	@RequestMapping(value = "/consultarBimestreCorrespondiente/{mes}", method = RequestMethod.GET)
	@ResponseBody
	public CalendarioReporteDTO consultarBimestreCorrespondiente(@PathVariable("mes") String mes) throws NamingException {
				
		CalendarioReporteDTO calendarioReporteDTO = null;
		calendarioReporteDTO = informacionIncidenciaServiceEjb.consultarBimestreCorrespondiente(mes);
		
		return calendarioReporteDTO;
	}
	
}