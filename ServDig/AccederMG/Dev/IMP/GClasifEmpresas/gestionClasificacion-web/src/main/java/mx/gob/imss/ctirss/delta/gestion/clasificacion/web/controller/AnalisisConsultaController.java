/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:AnalisisConsultaController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:15/05/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.EjercicioDictamen;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FiltrosConsultaDictamenDataTable;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.DocumentosAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsultaDataTable;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.web.validator.FiltrosAnalisisConsultaValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/analisis")
public class AnalisisConsultaController extends AbstractController {

	@Autowired
	SolicitudServiceBusinessRemote solicitudBusiness;

	@Autowired
	TramiteServiceBusinessRemote tramiteBusiness;

	@Autowired
	AnalisisServiceBusinessRemote analisisBusiness;

	@Autowired
	DocumentosAnalisisServiceBusinessRemote documentosAnalisisBusiness;
	
	@Autowired
	DictamenServiceBusinessRemote dictamenServiceBusinessRemote;

	DateFormat df = Constantes.DATE_FORMAT_DD_MM_YYYY;
	
	private final String GRUPO_DICTAMEN = "3";

	@RequestMapping(method = RequestMethod.GET)
	public String consultarSolicitudesConcluidas(Model model, HttpSession session) {
		this.log.debug("[AnalisisConsultaController] - " + "consultarSolicitudesConcluidas ");
		session.setAttribute("menuDecoration", "1");
		
		String grupoTramite = (String) session.getAttribute("grupoTramite");
		String vista = "analisisConsulta";
		
		if(grupoTramite.equals(GRUPO_DICTAMEN)){
			model.addAttribute("dictamen", new DictamenDTO());
			vista = "analisisConsultaDictamen";
		}
		
		return vista;
	}

	@RequestMapping(value = "/seleccionar", method = RequestMethod.GET)
	public String consultarSolicitudesConcluidas(@RequestParam String grupoTramite, HttpSession session, Model model) {
		Usuario usuario = (Usuario)session.getAttribute("usuario");
	 	int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
	 	if (!perfilUsuarioValido(iRol)) {
			log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta acción, rol: " + iRol);
			return "internalError";
		}
		
		this.log.debug("[AnalisisConsultaController] - " + "consultarSolicitudesConcluidas ");
		this.log.debug("grupoTramite ... " + grupoTramite);
		String vista = "analisisConsulta";

		List<TipoTramite> listaTipoTramite = new ArrayList<TipoTramite>();
		List<EstatusAnalisisModel> listaEstatusAnalisis = new ArrayList<EstatusAnalisisModel>();
		List<EjercicioDictamen> ejercicios = new ArrayList<EjercicioDictamen>();

		try {
			long cveIdGrupoAnalisis = new Long(grupoTramite).longValue();
			listaTipoTramite = tramiteBusiness.consultaTipoTramitePorGrupoAnalisis(cveIdGrupoAnalisis);
			listaEstatusAnalisis = analisisBusiness.consultaEstatusAnalisisPorGrupoAnalisis(cveIdGrupoAnalisis);
			
			if(grupoTramite.endsWith(GRUPO_DICTAMEN)) {
				List<EstatusAnalisisModel> aux = new ArrayList<EstatusAnalisisModel>();
				for(EstatusAnalisisModel estatus: listaEstatusAnalisis) {
					EstatusAnalisisModel est= estatus;
					est.setDesEstatus(estatus.getDesEstatus().replaceAll("RECTIFICADO", "ENVIAR A REVISION").replaceAll("RECTIFICACION", "ENVIAR A REVISION"));
					aux.add(est);
				}
				
				listaEstatusAnalisis = aux;
			}
			session.setAttribute("tipoMovimientoGrupoTramite", TipoTramiteEnum.ALTA_SRT.getCodigo().toString());
			session.setAttribute("grupoTramite", grupoTramite);
			session.setAttribute("lstTipoTramite", listaTipoTramite);
			session.setAttribute("lstEstatusAnalisis", listaEstatusAnalisis);
			session.setAttribute("menuDecoration", "1");			
		} catch (Exception e) {
			log.error("Ocurrio un error al obtener catalogos.");
			e.printStackTrace();
		}
		
		if(grupoTramite.endsWith(GRUPO_DICTAMEN)) {
			vista = "analisisConsultaDictamen";
			ejercicios = dictamenServiceBusinessRemote.getPeriodosDictamen();
			model.addAttribute("dictamen", new DictamenDTO());
			session.setAttribute("lstEjercicios", ejercicios);
		}

		return vista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/paginar/solicitudes/concluidas", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<SolicitudConcluida> paginarSolicitudesConcluidas(@RequestBody FiltrosAnalisisConsultaDataTable aoData,
			HttpSession session, HttpServletResponse response) {

		this.log.debug("[AnalisisConsultaController] - " + "paginarSolicitudesConcluidas ");

		DatosSalidaPaginador<SolicitudConcluida> solicitudes = null;
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);

		FiltrosAnalisisConsulta filtro = aoData.getoForm();
		if (filtro.getTipoRegistro() != null && filtro.getTipoRegistro().compareTo(TipoRegistroEnum.TODOS.getClave()) == 0) {
			filtro.setTipoRegistro(null);
			aoData.setoForm(filtro);
		}

		filtro.setGrupoTramite((String) session.getAttribute("grupoTramite"));
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();

		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
		) {
			aoData.getoForm().setSubDelegacion(usuario.getCveIdSubdelegacion().toString());
		}

		DatosEntradaPaginador<FiltrosAnalisisConsulta> send = new DatosEntradaPaginador<FiltrosAnalisisConsulta>();
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		/* Codigo para el manejo de las validaciones de los campos requeridos */
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(aoData.getoForm(), "model");
		new FiltrosAnalisisConsultaValidator().validate(aoData.getoForm(), errors);

		// si el usuario no es de nivel central se verifica el campo delegacion
		if (!(iRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue())) {
			if (aoData.getoForm().getDelegacion() == null
					|| aoData.getoForm().getDelegacion().equals("-1")
					|| aoData.getoForm().getDelegacion().equals("")) {
				log.debug("************** Error en la consulta, no se ha recibido el campo de la delegacion, aoData.getoForm().getDelegacion(): "
						+ aoData.getoForm().getDelegacion());
				errors.rejectValue("delegacion", "field.delegacion.wrong");
			}
		}

		if (errors.hasErrors()) {
			solicitudes = new DatosSalidaPaginador<SolicitudConcluida>();
			this.procesaErroresDeCaptura(errors, result, response);
			solicitudes.setErroresCaptura((List) result.get(KEY_CODE_ERROR_FIELDS));
			return solicitudes;
		}

		this.log.debug("[ Invocando al servicio EJB solicitudService ] - " + "consultarSolicitudesConcluidas");
		solicitudes = solicitudBusiness.consultarSolicitudesConcluidas(send);
		solicitudes.setsEcho(send.getsEcho());

		// guarda en la sesion los rps encontrados para reimprimirlos al
		// regresar del detalle

		if (send.getModelo().getRegistroPatronal() != null && send.getModelo().getRegistroPatronal().trim().length() > 0)
			session.setAttribute("regPatConservar", send.getModelo().getRegistroPatronal());
		else
			session.setAttribute("regPatConservar", "12345678");

		if (send.getModelo().getStrPeriodoInicio() != null && send.getModelo().getStrPeriodoInicio().trim().length() > 0)
			session.setAttribute("fechaInicioConservar", send.getModelo().getStrPeriodoInicio());
		if (send.getModelo().getStrPeriodoFin() != null && send.getModelo().getStrPeriodoFin().trim().length() > 0)
			session.setAttribute("fechaFinConservar", send.getModelo().getStrPeriodoFin());

		if (send.getModelo().getTipoPersona() != null && send.getModelo().getTipoPersona().trim().length() > 0)
			session.setAttribute("tipoPersonaConservar", send.getModelo().getTipoPersona());

		if (send.getModelo().getTipoRegistro() != null && send.getModelo().getTipoRegistro().trim().length() > 0)
			session.setAttribute("tipoRegistroConservar", send.getModelo().getTipoRegistro());
		else
			session.setAttribute("tipoRegistroConservar", "-1");

		if (send.getModelo().getEstatus() != null && send.getModelo().getEstatus().trim().length() > 0)
			session.setAttribute("estatusConservar", send.getModelo().getEstatus());
		else
			session.setAttribute("estatusConservar", "");

		if (send.getModelo().getDelegacion() != null)
			session.setAttribute("delegacionConservar", send.getModelo().getDelegacion());
		else
			session.setAttribute("delegacionConservar", "-1");

		if (send.getModelo().getSubDelegacion() != null)
			session.setAttribute("subdelegacionConservar", send.getModelo().getSubDelegacion());
		else
			session.setAttribute("subdelegacionConservar", "-1");

		if (send.getModelo().getTipoMovimiento() != null)
			session.setAttribute("tipoMovimientoConservar", send.getModelo().getTipoMovimiento());
		else
			session.setAttribute("tipoMovimientoConservar", "-1");

		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		this.log.info("Datos a guardar");
		this.log.info("regPatConservar: " + session.getAttribute("regPatConservar"));
		this.log.info("fechaInicioConservar: " + session.getAttribute("fechaInicioConservar"));
		this.log.info("fechaFinConservar: " + session.getAttribute("fechaFinConservar"));
		this.log.info("tipoPersonaConservar: " + session.getAttribute("tipoPersonaConservar"));
		this.log.info("tipoRegistroConservar: " + session.getAttribute("tipoRegistroConservar"));
		this.log.info("estatusConservar: " + session.getAttribute("estatusConservar"));
		this.log.info("delegacionConservar: " + session.getAttribute("delegacionConservar"));
		this.log.info("subdelegacionConservar: " + session.getAttribute("subdelegacionConservar"));
		this.log.info("tipoMovimientoConservar: " + session.getAttribute("tipoMovimientoConservar"));
		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");

		this.log.debug(" [Termino la invocacion..] solicitudes [" + solicitudes + "]");
		return solicitudes;
	}
	
	@RequestMapping(value = "/paginar/dictamenes", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<DictamenDTO> paginarDictamenes(@RequestBody FiltrosConsultaDictamenDataTable aoData,
			HttpSession session, HttpServletResponse response) {

		this.log.debug("[AnalisisConsultaController] - " + "paginarSolicitudesConcluidas ");
		DatosSalidaPaginador<DictamenDTO> dictamentes = null;
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);

		DictamenDTO filtro = aoData.getoForm();
		session.setAttribute("idEjercicioDictamen", filtro.getIdEjercicio());
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();

		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
		) {
			aoData.getoForm().setIdSubDelegacion(usuario.getCveIdSubdelegacion());
		}

		DatosEntradaPaginador<DictamenDTO> send = new DatosEntradaPaginador<DictamenDTO>();
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());


		this.log.debug("[ Invocando al servicio EJB solicitudService ] - " + "consultarSolicitudesConcluidas");
		dictamentes = dictamenServiceBusinessRemote.consultarDictamentesPaginado(send);
		dictamentes.setsEcho(send.getsEcho());
		
		if (send.getModelo().getRegistroPatronal() != null && send.getModelo().getRegistroPatronal().trim().length() > 0)
			session.setAttribute("regPatConservar", send.getModelo().getRegistroPatronal());
		else
			session.setAttribute("regPatConservar", "12345678");

		if (send.getModelo().getIdEjercicio() != null && send.getModelo().getIdEjercicio() > 0)
			session.setAttribute("idEjercicioConservar", send.getModelo().getIdEjercicio());
		
		if (send.getModelo().getIdSubDelegacion() != null)
			session.setAttribute("subdelegacionConservar", send.getModelo().getIdSubDelegacion());
		else
			session.setAttribute("subdelegacionConservar", "-1");


		if (send.getModelo().getIdDelegacion() != null)
			session.setAttribute("delegacionConservar", send.getModelo().getIdDelegacion());
		else
			session.setAttribute("delegacionConservar", "-1");

		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		this.log.info("Datos a guardar");
		this.log.info("regPatConservar: " + session.getAttribute("regPatConservar"));
		this.log.info("fechaInicioConservar: " + session.getAttribute("fechaInicioConservar"));
		this.log.info("fechaFinConservar: " + session.getAttribute("fechaFinConservar"));
		this.log.info("tipoPersonaConservar: " + session.getAttribute("tipoPersonaConservar"));
		this.log.info("tipoRegistroConservar: " + session.getAttribute("tipoRegistroConservar"));
		this.log.info("estatusConservar: " + session.getAttribute("estatusConservar"));
		this.log.info("delegacionConservar: " + session.getAttribute("delegacionConservar"));
		this.log.info("subdelegacionConservar: " + session.getAttribute("subdelegacionConservar"));
		this.log.info("tipoMovimientoConservar: " + session.getAttribute("tipoMovimientoConservar"));
		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		this.log.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");

		this.log.debug(" [Termino la invocacion..] solicitudes [" + dictamentes + "]");
		return dictamentes;
	}

	@RequestMapping(value = "/viene/detalle", method = RequestMethod.POST)
	public String vieneDetalle(Model model, HttpSession session, @RequestParam Long cveIdPatronDictamen) {
		this.log.debug("[AnalisisConsultaController] - " + "vieneDetalle ");

		this.log.info("////////////////////////////////////////////////////////////");
		this.log.info("////////////////////////////////////////////////////////////");
		this.log.info("Datos recuperados");
		this.log.info("regPatConservar: " + session.getAttribute("regPatConservar"));
		this.log.info("fechaInicioConservar: " + session.getAttribute("fechaInicioConservar"));
		this.log.info("fechaFinConservar: " + session.getAttribute("fechaFinConservar"));
		this.log.info("tipoPersonaConservar: " + session.getAttribute("tipoPersonaConservar"));
		this.log.info("tipoRegistroConservar: " + session.getAttribute("tipoRegistroConservar"));
		this.log.info("estatusConservar: " + session.getAttribute("estatusConservar"));
		this.log.info("delegacionConservar: " + session.getAttribute("delegacionConservar"));
		this.log.info("subdelegacionConservar: " + session.getAttribute("subdelegacionConservar"));
		this.log.info("tipoMovimientoConservar: " + session.getAttribute("tipoMovimientoConservar"));
		this.log.info("////////////////////////////////////////////////////////////");
		this.log.info("////////////////////////////////////////////////////////////");

		model.addAttribute("vieneDetalle", "1"); // bandera que indica que la
													// peticion viene del
													// detalle
		model.addAttribute("regPatConservar", session.getAttribute("regPatConservar"));
		model.addAttribute("fechaInicioConservar", session.getAttribute("fechaInicioConservar"));
		model.addAttribute("fechaFinConservar", session.getAttribute("fechaFinConservar"));
		model.addAttribute("tipoPersonaConservar", session.getAttribute("tipoPersonaConservar"));
		model.addAttribute("tipoRegistroConservar", session.getAttribute("tipoRegistroConservar"));
		model.addAttribute("estatusConservar", session.getAttribute("estatusConservar"));
		model.addAttribute("delegacionConservar", session.getAttribute("delegacionConservar"));
		model.addAttribute("subdelegacionConservar", session.getAttribute("subdelegacionConservar"));
		model.addAttribute("tipoMovimientoConservar", session.getAttribute("tipoMovimientoConservar"));

		session.removeAttribute("regPatConservar");
		session.removeAttribute("fechaInicioConservar");
		session.removeAttribute("fechaFinConservar");
		session.removeAttribute("tipoPersonaConservar");
		session.removeAttribute("tipoRegistroConservar");
		session.removeAttribute("estatusConservar");
		session.removeAttribute("delegacionConservar");
		session.removeAttribute("subdelegacionConservar");
		session.removeAttribute("tipoMovimientoConservar");
		model.addAttribute("dictamen", new DictamenDTO());
		session.setAttribute("menuDecoration", "1");
		return cveIdPatronDictamen == null ? "analisisConsulta" : "analisisConsultaDictamen";
	}

	/**
	 * Obtiene el estatus de los documentos de análisis según existan o no para
	 * la solicitud.
	 * 
	 * @param cveIdSolicitud
	 * @return
	 */
	@RequestMapping(value = "/get/listaDocumentos", method = RequestMethod.GET)
	public @ResponseBody
	DocumentosAnalisis getListaDocumentos(@RequestParam String cveIdSolicitud) {

		this.log.info("Obteniendo el elemento [" + cveIdSolicitud + "] para visualizar documentos de analisis");
		DocumentosAnalisis doctos = new DocumentosAnalisis();

		try {
			doctos = documentosAnalisisBusiness.validaExistenciaDocumentos(new Long(cveIdSolicitud));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctos;
	}

	/**
	 * Muestra el documento de análisis en formato PDF.
	 * 
	 * @param cveIdSolicitud
	 * @return
	 */
	@RequestMapping(value = "/get/mostrarDocumento", method = RequestMethod.GET)
	public @ResponseBody
	String mostrarDocumento(@RequestParam String id, @RequestParam String tipo, HttpServletResponse sresponse) {
		byte[] response = null;
		String sFileName = "";

		System.out.println("ID ... [" + id + "] ---------- TIPO_DOCUMENTO ... [" + tipo + "]");

		switch (new Integer(tipo).intValue()) {
		case Constantes.TIPO_DOCUMENTO_CLEM:
			sFileName = "DocumentoProbatorioCLEM";
			break;
		case Constantes.TIPO_DOCUMENTO_AVISO:
			sFileName = "DocumentoProbatorioAviso";
			break;
		case Constantes.TIPO_DOCUMENTO_TIP:
			sFileName = "DocumentoProbatorioTIP";
			break;
		case Constantes.TIPO_DOCUMENTO_ARP:
			sFileName = "DocumentoProbatorioARP";
			break;
		}

		try {
			response = documentosAnalisisBusiness.obtenRefDocumento(new Long(id), new Integer(tipo).intValue());
			if (response != null) {
				log.debug("Se obtuvo el archivo PDF");
				sresponse.setContentType("application/pdf");
				sresponse.addHeader("content-disposition", "attachment; filename=" + sFileName + ".pdf");
				sresponse.addHeader("X-Download-Options", "open");
				try {
					log.debug("bytes pdf:" + response.length);
					sresponse.setContentLength(response.length);
					sresponse.getOutputStream().write(response);
					sresponse.getOutputStream().flush();
					sresponse.getOutputStream().close();
				} catch (IOException e) {
					log.error("Error en el metodo init previo : " + e);
					e.printStackTrace();
				}
			} else {
				log.debug("No se obtuvo el archivo PDF.");
			}
		} catch (Exception e) {
			log.error("Error al obtener el archivo PDF.");
			e.printStackTrace();
		}

		return "analisisConsulta";
	}

	
	@RequestMapping(value = "/verDocumentoAdjunto", method = RequestMethod.GET)
	public void verDocumentoClem(@RequestParam String documento, HttpServletResponse sresponse) {
		byte[] response = null;
		try {
			log.debug("::: Obteniendo el documento adjunto: " + documento);
			response = readFileToBytes(documento);			
			if(response!=null){
				log.debug("Se obtuvo el archivo adjunto: " + documento);
				
				//obtenemos la extencion del archivo
				String ext = "";
				String nombreArch = "";
				String[] datos = documento.split("/");
				nombreArch = datos[datos.length -1];
				ext = nombreArch.substring(nombreArch.indexOf('.') + 1, nombreArch.length());

				sresponse.setContentType("application/"+ext);
				sresponse.addHeader("content-disposition","attachment; filename="+nombreArch);
				sresponse.addHeader("X-Download-Options", "open");
				try {
					log.debug("bytes doc:"+response.length);
					sresponse.setContentLength(response.length);
					sresponse.getOutputStream().write(response);
					sresponse.getOutputStream().flush();
					sresponse.getOutputStream().close();
				}catch(IOException e) {
					log.error("Error al obtener documento adjunto : " + e);
					e.printStackTrace();
				}
			}else{
				log.debug("No se obtuvo el archivo adjunto: " + documento);
			}
		} catch (Exception e) {
			log.error("Error al obtener el archivo adjunto.");
			e.printStackTrace();
		}
		
	}
		
	private byte[] readFileToBytes(String filePath) throws IOException {
		File file = new File(filePath);
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.read(bytes);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return bytes;
	}
	
	//Metodo que comprueba que el rol iniciado en sesion tenga acceso a la vista.
	private boolean perfilUsuarioValido(int iRol){
			if (iRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.NORMATIVO_DEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
			) {
				return true;
			}
			return false;
		}
}