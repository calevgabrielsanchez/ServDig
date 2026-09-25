/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.csdiss.sdroc.dto.*;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.csdiss.sdroc.util.CorreoTemplate;
import mx.gob.imss.csdiss.sdroc.util.ExtensionEnum;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * @author daniel.hernandez
 * 
 */
@Controller
@PropertySource("classpath:messages.properties")
public class RegistroObraController {

	@Autowired
	EmailServiceRemote emailServiceRemote;

	@Autowired
	RegistroObraService registroObraService;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private Environment env;
	
	@Autowired
	PersonaBusinessRemote personaBusinessRemote;
	
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	
	@Autowired
	FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	private final String KEY_FIRMA_E = "firmaElectronicaSession";
	
	List<TipoObraDTO> listaTiposObra = new ArrayList<TipoObraDTO>();

	@RequestMapping(value = { "/registro" })
	public ModelAndView redireccionObra(HttpSession session) {
		
		ModelAndView modelo = new ModelAndView("/registro/obra/registroObra");

		List<ObjetoContratoDTO> listaObjetosContrato = new ArrayList<ObjetoContratoDTO>();

		listaObjetosContrato = registroObraService.consultarObjetoContrato();
		modelo.addObject("tiposObra", listaTiposObra);
		modelo.addObject("objetosContrato", listaObjetosContrato);


		modelo.addObject("rfc", session.getAttribute("rfc"));
		modelo.addObject("rp", session.getAttribute("rp"));
		modelo.addObject("razonSocial", session.getAttribute("razonSocial"));

		return modelo;
	}

	@RequestMapping(value = "/categoriasObras")
	public @ResponseBody
	JsonResponseDTO cargaCategoriasObraPorTipoObra(
			@RequestBody(required = true) String idTipoObra, HttpSession session) {
		
		JsonResponseDTO jsonResponse = new JsonResponseDTO();

		if (idTipoObra != "" && idTipoObra != null) {
			listaTiposObra = registroObraService.consultarTiposObra(idTipoObra);
		}

		jsonResponse.setEstatus("SUCCESS");
		jsonResponse.setResultado(listaTiposObra);

		return jsonResponse;
	}
	


	@RequestMapping(value = "/registrarObra")
	public void peticionRegistroObra(@RequestBody String datosObra,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session) throws MessagingException, IOException {
		
		SesionDataCoreDTO sesionDTO = new SesionDataCoreDTO();
		sesionDTO.setCveRfc(session.getAttribute("rfc").toString());
		sesionDTO.setCveRegPatronal(session.getAttribute("rp").toString());
		sesionDTO.setRefRazonSocial(session.getAttribute("rs").toString());
		sesionDTO.setFirmaElectronica(session.getAttribute(KEY_FIRMA_E));
		HashMap<String, Object> informacionObra;
		try {
			informacionObra = registrarObraProceso(datosObra, request, sesionDTO, session);
			
			String correo = (String) session.getAttribute("correoEnvio");
			session.setAttribute("registroObraArchivoPDF", informacionObra.get("reporte"));
			
			if (informacionObra.get("reporte") != null) {
				envioCorreo(correo, informacionObra);
			}
			
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getSituacion());
		}catch (Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
	}

	@RequestMapping(value = "/registrarObraRemplazo")
	public void peticionRegistroObraRemplazo(@RequestBody String datosObra,
			 HttpServletResponse response, HttpServletRequest request,
			 HttpSession session) throws MessagingException, IOException {

		SesionDataCoreDTO sesionDTO = new SesionDataCoreDTO();
		sesionDTO.setCveRfc(session.getAttribute("rfc").toString());
		sesionDTO.setCveRegPatronal(session.getAttribute("rp").toString());
		sesionDTO.setRefRazonSocial(session.getAttribute("rs").toString());
		sesionDTO.setFirmaElectronica(session.getAttribute(KEY_FIRMA_E));
		HashMap<String, Object> informacionObra;

		try {

			informacionObra = registrarObraProceso(datosObra, request, sesionDTO, session);

			String correo = (String) session.getAttribute("correoEnvio");
			session.setAttribute("registroObraArchivoPDF", informacionObra.get("reporte"));

			if (informacionObra.get("reporte") != null) {
				envioCorreo(correo, informacionObra);
			}

		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getSituacion());
		}catch (Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
	}

	@RequestMapping(value = "/getRegistroObraPDF", method = RequestMethod.GET)
	public void getRegistroObraPDF(HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		byte[] reportePDF = (byte[]) session.getAttribute("registroObraArchivoPDF");
		
		try {
			generaArchivoRespuesta(response, reportePDF, "registroObra", ExtensionEnum.PDF);
			session.removeAttribute("registroObraArchivoPDF");
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	@RequestMapping(value = "/datosFirma")
	public @ResponseBody
	String procesarDatosFirma(@RequestBody String datosFirma, HttpSession session) {

		Gson parseJson = new Gson();
		FirmaElectronica firmaElectronica = parseJson.fromJson(datosFirma, FirmaElectronica.class);
		session.setAttribute(KEY_FIRMA_E, firmaElectronica);

		return "OK";
	}

	/**
	 * Forma correo electronico a enviar en el registro de obra
	 * 
	 * @param nombreReporte
	 * @throws MessagingException
	 */
	private void envioCorreo(String mail, HashMap<String, Object> informacionObra) throws MessagingException {
		
		String nombreAdjunto = "archivo.pdf";
		byte[] reportePDF = (byte[]) informacionObra.get("reporte");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String mensaje = null;
		String titulo = null;
		
		if(informacionObra.get("tipoObra").equals("aviso")){
			titulo = "Acuse de aviso de obra.";
			mensaje = "Se remite Acuse de recibo, Aviso de Ubicaci&oacute;n de Obra de Construcci&oacute;n con n&uacute;mero: ".concat(String.valueOf(informacionObra.get("cveObra"))).concat(", de fecha ").concat(format.format(new Date()));
			nombreAdjunto = "avisoObra.pdf";
		
		}else{
			titulo = "Acuse de registro de obra.";
			mensaje = "Se remite Acuse de recibo, Registro de Obra de Construcci&oacute;n con n&uacute;mero de registro de obra: ".concat(String.valueOf(informacionObra.get("claveObra"))).concat(", de fecha ").concat(format.format(new Date()));
			nombreAdjunto = "registroObra.pdf";
		}
		
		String template = CorreoTemplate.construirPlantillaCorreo(titulo, mensaje);
		
		try {
			Map<String, byte[]> archivosAdjuntos = new HashMap<String, byte[]>();
			archivosAdjuntos.put(nombreAdjunto, reportePDF);
			emailServiceRemote.enviaCorreoConDocumentoAdjunto(mail,null,titulo,template, archivosAdjuntos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Realiza la busqueda de avisos o registros de obra, dependiendo de los datos que envien. 
	 * Si el criterio de busqueda inicia con 'C' es una registro de obra y si solo contiene numeros es un aviso de obra. 
	 * 
	 * Codigos de respuesta para avisos de obra: 
	 * 
	 * 33 No se econtro el aviso de obra 
	 * 30 No existe esa obra para ese RFC 
	 * 31 No existe esa obra para ese Registro Patronal 
	 * 32 La obra ya esta cerrada 
	 * 
	 * @param numRegObra
	 * @return JsonResponse
	 */
	@RequestMapping(value = "/consultaPorNumReg")
	public @ResponseBody
	JsonResponseDTO consultaObraPorNumRegObra(@RequestBody String numRegObra, HttpSession session) {
		
		SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy");		
		JsonResponseDTO jsonResponse = new JsonResponseDTO();
		InformacionObraDTO obraRegistrada = null;
		AvisoObraDTO avisoObra = null;
		Boolean isRegistro = null;

		
		String rfc = (String) session.getAttribute("rfc");
		String registroPatronal = (String) session.getAttribute("rp");
		
		try {
			if(numRegObra.startsWith("C")){
				isRegistro = Boolean.TRUE; 
				obraRegistrada = registroObraService.obtenerInformacionObraPorNumRegObra(numRegObra);
			}else{
				isRegistro = Boolean.FALSE;
				avisoObra = registroObraService.consultaAvisoUbicacionObra(numRegObra, rfc, registroPatronal);
			}
			
			if(obraRegistrada != null){
				jsonResponse.setEstatus("SUCCESS");
				jsonResponse.setResultado(obraRegistrada);
				jsonResponse.setFechaInicio(forma.format(obraRegistrada.getFecIniObra()));
				jsonResponse.setFechaFin(forma.format(obraRegistrada.getFecFinObra()));
				jsonResponse.setCertificacionObra("1");
				
			}else if(avisoObra != null){
				
				if(avisoObra.getCodigoRespuesta().equals("29")){
					jsonResponse.setEstatus("SUCCESS");
					jsonResponse.setResultado(avisoObra);
					jsonResponse.setFechaInicio(forma.format(avisoObra.getFecIniObra()));
					jsonResponse.setFechaFin(forma.format(avisoObra.getFecFinObra()));
					jsonResponse.setCertificacionObra("0");
				}else{
					if(avisoObra.getCodigoRespuesta().equals("33")){
						jsonResponse.setEstatus("ERROR");
						jsonResponse.setMensaje(env.getProperty("reg.obra.error.busqueda.aviso"));
						if (numRegObra.startsWith("I")){
							jsonResponse.setMensaje(env.getProperty("reg.obra.error.busqueda.aviso.intermediario"));
						}
						jsonResponse.setCertificacionObra(isRegistro == true ? "1" : "0");
					}else if(avisoObra.getCodigoRespuesta().equals("31")){
						jsonResponse.setEstatus("ERROR");
						jsonResponse.setMensaje(env.getProperty("reg.obra.error.busqueda.aviso.registro.pat"));
						jsonResponse.setCertificacionObra(isRegistro == true ? "1" : "0");
						
					}else if(avisoObra.getCodigoRespuesta().equals("32")){
						jsonResponse.setEstatus("ERROR");
						jsonResponse.setMensaje(env.getProperty("reg.obra.error.busqueda.aviso.estatus"));
						jsonResponse.setCertificacionObra(isRegistro == true ? "1" : "0");
					}
				}

			} else{
				jsonResponse.setEstatus("ERROR");
				jsonResponse.setMensaje(env.getProperty("reg.obra.error.busqueda.registro"));
				jsonResponse.setCertificacionObra("1");
			}
		}catch (Exception e) {
			jsonResponse.setEstatus("ERROR");
			jsonResponse.setMensaje("Ocurrio un error, no se encontro la obra o aviso que buscabas");
			jsonResponse.setCertificacionObra("1");
		}
		return jsonResponse;
	}

	/**
	 * Proceso para registro de obra
	 * 
	 * @param datosObra
	 * @param request
	 * @param SesionDataCoreDTO
	 * @return nombre de acuse generado en el registro
	 */
	private HashMap<String, Object> registrarObraProceso(String datosObra, HttpServletRequest request, SesionDataCoreDTO sesionDTO, HttpSession session) throws DomicilioNoLocalizadoException {
		
		String pathRegistroObraAcuse = "";
		String pathImg = "";
		HashMap<String, Object> informacionObra = new HashMap<String, Object>();
		InformacionObraDTO datosRegistroObra = null;
		AvisoObraDTO datosRegistroAviso = null;
		// obtenemos los datos de la firma electronica
		FirmaElectronica datosFirma = (FirmaElectronica) sesionDTO.getFirmaElectronica();
		Long CVE_PATRON_SUBESP = 5L;
		String DES_PATRON_SUBESP = "Subcontratista de obra especializada";
		
		datosObra.toString();
		try {
			Gson parseJson = new Gson();
			
			try {
				datosRegistroObra = parseJson.fromJson(datosObra, InformacionObraDTO.class);	
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(!datosRegistroObra.isAplicaIncRemplazo()) {
			try {
				datosRegistroAviso = parseJson.fromJson(datosObra, AvisoObraDTO.class);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			}

			
			pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
			pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
			
			if(datosRegistroAviso != null){
				
				if (datosRegistroAviso.getCveRfcPatron() == null || datosRegistroAviso.getCveRfcPatron().equals("")) {
					InformacionPatronDTO infoPatron = datosRegistroObra.getInformacionPatronDTO();
					infoPatron.setCveRfc(sesionDTO.getCveRfc());
					infoPatron.setCveRegPatronal(sesionDTO.getCveRegPatronal());
					infoPatron.setRefRazonSocial(sesionDTO.getRefRazonSocial());
					datosRegistroObra.setInformacionPatronDTO(infoPatron);

					
					if (datosRegistroObra.getUbicacionObraDTO().getCodigoPostal() == null) {
						
						if(datosRegistroObra.getNumProcedimiento() != null && !datosRegistroObra.getNumProcedimiento().startsWith("C")){
							
							AvisoObraDTO aux = 	registroObraService.consultaAvisoUbicacionObra(datosRegistroObra.getNumProcedimiento(), sesionDTO.getCveRfc(), sesionDTO.getCveRegPatronal());
							
							if(aux.getCodigoRespuesta().equals("33")){
								datosRegistroObra.setNumProcedimiento("");
								InformacionObraDTO registro = registroObraService.obtenerInformacionObraPorCveInformacionObra(datosRegistroObra.getCveRegistroObraPrincipal());
								registro.getUbicacionObraDTO().setRefObservacion(datosRegistroObra.getUbicacionObraDTO().getRefObservacion());
								datosRegistroObra.setUbicacionObraDTO(registro.getUbicacionObraDTO());
							}else{
								aux.getUbicacionObraDTO().setRefObservacion(datosRegistroObra.getUbicacionObraDTO().getRefObservacion());
								datosRegistroObra.setUbicacionObraDTO(aux.getUbicacionObraDTO());
								registroObraService.actualizarAvisoObra(aux.getCveAvisoObra());
							}
							
						}else if(datosRegistroObra.getCveRegistroObraPrincipal() != null){
							InformacionObraDTO aux = registroObraService.obtenerInformacionObraPorCveInformacionObra(datosRegistroObra.getCveRegistroObraPrincipal());
							aux.getUbicacionObraDTO().setRefObservacion(datosRegistroObra.getUbicacionObraDTO().getRefObservacion());
							datosRegistroObra.setUbicacionObraDTO(aux.getUbicacionObraDTO());
						}
					}
					
					//validamos el codigo postal
					String cPValidar = datosRegistroObra.getUbicacionObraDTO().getCodigoPostal();
					System.out.println("Se esta registrando una obra, el cp que se valida es " + cPValidar);
					boolean codigoValido = true;
					
					try{
						codigoValido =  this.validarCPInterno(session, cPValidar);
					} catch(Exception e) {
						throw new DomicilioNoLocalizadoException("No fue posible validar el C&oacute;digo Postal");
					}

					if(!codigoValido) {
						throw new DomicilioNoLocalizadoException("El C&oacute;digo Postal " + cPValidar + " no corresponde con la Subdelegacion del patr&oacute;n");
					}
					
					informacionObra = registroObraService.registrarObra(datosRegistroObra, null, pathRegistroObraAcuse, pathImg, datosFirma);
					
				} else {
					InformacionPatronDTO infoPatron = datosRegistroAviso.getInformacionPatronDTO();
					infoPatron.setCveRfc(sesionDTO.getCveRfc());
					infoPatron.setCveRegPatronal(sesionDTO.getCveRegPatronal());
					infoPatron.setRefRazonSocial(sesionDTO.getRefRazonSocial());
					datosRegistroAviso.setInformacionPatronDTO(infoPatron);

					informacionObra = registroObraService.registrarObra(null, datosRegistroAviso, pathRegistroObraAcuse, pathImg, datosFirma);
				}
			}

			if(datosRegistroObra.isAplicaIncRemplazo()){



				InformacionObraDTO obraAnterior = registroObraService.obtenerInformacionObraPorCveInformacionObra(datosRegistroObra.getCveInformacionObra());
				InformacionObraDTO obraRemplazada = registroObraService.obtenerInformacionObraPorCveInformacionObra(datosRegistroObra.getCveInformacionObra());

				obraAnterior.setFecFinObra(datosRegistroObra.getFecFinObra());
				obraAnterior.setDesObjetoContratoSubEsp(datosRegistroObra.getDesObjetoContratoSubEsp());
				obraAnterior.setImpObra(datosRegistroObra.getImpObra());
				obraAnterior.setNumAproxTrabajadores(datosRegistroObra.getNumAproxTrabajadores());
				obraAnterior.setNumRegStps(datosRegistroObra.getNumRegStps());
				obraAnterior.setRefObservacion(datosRegistroObra.getRefObservacion());
				obraAnterior.setCveObraRemplazado(datosRegistroObra.getCveObraRemplazado());
				obraAnterior.getInformacionPatronDTO().getTipoPatronDTO().setCveTipoPatron(CVE_PATRON_SUBESP);
				obraAnterior.getInformacionPatronDTO().getTipoPatronDTO().setDesTipoPatron(DES_PATRON_SUBESP);

				informacionObra = registroObraService.registrarObra(obraAnterior, null, pathRegistroObraAcuse, pathImg, datosFirma);

				obraRemplazada.getEstatusObraDTO().setCveEstatusObra(6L);
				obraRemplazada.setAplicaIncRemplazo(true);
				registroObraService.actualizaObra(obraRemplazada);

			}

		} catch (DomicilioNoLocalizadoException e){
			throw e;
		}catch (Exception e) {
			System.out.println("Ocurrio un error al finalizar el registro de obra");
			e.printStackTrace();
			throw new DomicilioNoLocalizadoException("Ocurrio un error al finalizar el registro de obra"); 
		}
		// limpiamos de la session los datos de firma electronica
		//session.removeAttribute(KEY_FIRMA_E);

		return informacionObra;
	}
	
	@RequestMapping(value = "/validaRfc/{rfc}", method = RequestMethod.GET)
	public @ResponseBody
	String validaRfcSinNumReg(@PathVariable("rfc") String rfc) {
		String razonSocial = "";
		try {
			//Pattern patter = Pattern.compile("^([A-Z&Ññ]{3}|[A-Z][AEIOU][A-Z]{2})\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01])|02(0[1-9]|[12]\\d)|(04|06|09|11)(0[1-9]|[12]\\d|30))([A-Z0-9]{2}[0-9A])?$");
			//Matcher mat = patter.matcher(rfc);
			//if (mat.find()) {
				if (!rfc.isEmpty()) {
					if (rfc.length() == 12) {
						Moral empresa = personaBusinessRemote.buscarPersonaMoralPorRfcEnSat(rfc);
						razonSocial = empresa.getRazonSocial();

					} else if (rfc.length() == 13) {
						Fisica persona = personaBusinessRemote.buscarPersonaFisicaPorRfcEnSat(rfc);
						razonSocial = persona.getNombre();
					}
				}
			//}
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		}
		return razonSocial;
	}

	/**
	 * @param registroObraService
	 *            the registroObraService to set
	 */
	public void setRegistroObraService(RegistroObraService registroObraService) {
		this.registroObraService = registroObraService;
	}

	@RequestMapping(value = "/validaCodigoPostal/{rp}/{codigoPostal}", method = RequestMethod.GET)
	public @ResponseBody
	Boolean validaRPCodigoPostal(@PathVariable("rp") String rp,
			@PathVariable("codigoPostal") String codigoPostal, HttpSession session) throws Exception {
		
		Boolean isValido = null;

		String subRp = null;
		if (rp != null && rp.length() > 10)
			subRp = rp.substring(0, 10);
		isValido = registroObraService.validaCpUbicacionObra(subRp, codigoPostal);
		
		return isValido;
	}
	
	@RequestMapping(value = "/validarCodigoPostal/{codigoPostal}")
	@ResponseBody
	public ResponseEntity<Boolean> validCodigoPostal(@PathVariable("codigoPostal") String codigoPostal, HttpSession session, HttpServletResponse response){
		
		Boolean isValido = false;
		ResponseEntity<Boolean> myResponse = null;
		
		try {
			isValido = this.validarCPInterno(session, codigoPostal);
			myResponse = new ResponseEntity<Boolean>(isValido,HttpStatus.OK);
		} catch(Exception e) {
			Log.error("Ocurrio un error al validar la cricunscripcon", e);
			myResponse = new ResponseEntity<Boolean>(false,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
		
        return myResponse;
	}
	
	private boolean validarCPInterno(HttpSession session, String codigoPostal) {
		Boolean isValido = true;
		SujetoObligado patron = (SujetoObligado) session.getAttribute("patron");
		Boolean isRPC = patron.getClasificacion().getIndRegPatClase() != null && patron.getClasificacion().getIndRegPatClase().intValue() == 1;
		Long idDelegacion = patron.getSubdelegacion().getDelegacion().getId();
		Long idSubdelegacion = patron.getSubdelegacion().getId();
		//Si el patron no es RPC validamos la ubicacion, ya que si es RPC puede registrar donde quiera
		if(!isRPC) {
			isValido = registroObraService.validCircunscripcion(codigoPostal, idDelegacion, idSubdelegacion );
		}
		return isValido;

	}
	
	@RequestMapping(value = "/obtenerSubDelegacionPorCodigoPostal/{codigoPostal}", method = RequestMethod.GET)
	public @ResponseBody
	String obtenerSubDelegacionesPorCodigoPostal(@PathVariable("codigoPostal") String codigoPostal, HttpSession session) {
		
		StringBuilder subdelegaciones = new StringBuilder();
		List<SubDelegacionDTO> subdelegacionesDTOs = registroObraService
				.obtenerSubDelegacionesPorCodigoPostal(codigoPostal);
		for (int i = 0; i < subdelegacionesDTOs.size(); i++) {
			subdelegaciones.append(subdelegacionesDTOs.get(i)
					.getNomSubdelegacion().toLowerCase());
			if (i != subdelegacionesDTOs.size() - 1) {
				subdelegaciones.append(", ");
			}
		}
		return subdelegaciones.toString();
	}
	
	@RequestMapping(value = "/getAcuseRegistro/{num_obra}")
	public void consultarAcuseRegistroObra(HttpServletResponse response, @PathVariable("num_obra") String num_obra) {
		InformacionObraDTO obraRegistrada = null;
		AvisoObraDTO avisoObra = null;
		Long idTramite = null;
		Date fechaSolicitud = null;
		String pathRegistroObraAcuse = context.getRealPath(File.separator + "static" + File.separator + "report");
		String pathImg = context.getRealPath(File.separator + "static" + File.separator + "images");
		
		if(num_obra.startsWith("C")){
			obraRegistrada = registroObraService.obtenerInformacionObraPorNumRegObra(num_obra);
			idTramite = obraRegistrada.getCveIdTramite();
		}else{
			avisoObra = registroObraService.consultaAvisoUbicacionObra(num_obra);
			idTramite = avisoObra.getCveIdTramite();
		}
		
		try {
			Log.error("El id del tramite relacionado a la obra es " + idTramite);
			
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			fechaSolicitud = solicitud.getFechaConclusion();
			FirmaElectronica firma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitud);
			
			if(obraRegistrada != null) {
				obraRegistrada.setFolio(solicitud.getNoFolioSolicitud());
				obraRegistrada.setNumSeqNotaria(firma.getSecuenciaNotaria());
				obraRegistrada.setRefCadenaOriginal(firma.getCadenaOriginal());
				obraRegistrada.setRefNumSerie(firma.getSerialCertificado());
				obraRegistrada.setRefSelloDigital(firma.getRecibo());
			} else {
				avisoObra.setFolio(solicitud.getNoFolioSolicitud());
				avisoObra.setNumSeqNotaria(firma.getSecuenciaNotaria());
				avisoObra.setRefCadenaOriginal(firma.getCadenaOriginal());
				avisoObra.setRefNumSerie(firma.getSerialCertificado());
				avisoObra.setRefSelloDigital(firma.getRecibo());
			}
			
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		byte[] acuse = null;
		String nombreAcuse = null;
		if(obraRegistrada != null) {
			acuse = registroObraService.generaAcuseRegistroObra(obraRegistrada, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
			nombreAcuse = "registroObra";
		} else {
			acuse = registroObraService.generaAcuseAvisoObra(avisoObra, pathRegistroObraAcuse, pathImg, fechaSolicitud, false);
			nombreAcuse = "ubicacionObraAcuse";
		}
		
		try {
			generaArchivoRespuesta(response, acuse,nombreAcuse, ExtensionEnum.PDF);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
	


	private void generaArchivoRespuesta(HttpServletResponse response, byte[] reporte, String nombreReporte, ExtensionEnum extension)
			throws IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		nombreReporte = formatter.format(new Date(System.currentTimeMillis()));
		OutputStream ouputStream = null;
		if (reporte != null) {
			// Si el archivo se genero lo regresamos para su descarga
			response.setContentType(extension.getContentType());
			response.addHeader("Content-Disposition", "inline; filename=" + nombreReporte + extension.getExtension());
			response.setContentLength(reporte.length);
			ouputStream = response.getOutputStream();
			ouputStream.write(reporte, 0, reporte.length);
			ouputStream.flush();
			ouputStream.close();
		}
	}
}
