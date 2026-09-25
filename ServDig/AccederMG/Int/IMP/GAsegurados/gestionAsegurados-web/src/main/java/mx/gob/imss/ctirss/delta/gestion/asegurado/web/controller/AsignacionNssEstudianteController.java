package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.bean.UploadFileBean;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.EstudiantesSimeValidationEventHandler;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils.FtpUploader;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.CapturaPatronSIEValidator;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.CargaArchivoSIEValidator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.SystemKeyParameters;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.EmpleadoEmpresa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionMasivaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.SAXException;

@Controller
@RequestMapping(value = "/asignacion")
public class AsignacionNssEstudianteController extends AbstractController {
	private static final String FROM_PORTAL_KEY = "FROM_PORTAL";

	@Autowired
	private HomeController homeController;
	@Autowired
	private FtpUploader ftpUploader;
	@Autowired
	private RuleServiceBusinessRemote ruleServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private ParametrosServiceBusinessRemote parametrosServiceBusiness;
	
	//variables del tipo de tramite y solicitud
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String DESC_TIPO_SOLICITUD = "ASIGNACION NSS ESTUDIANTE";

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String cargarArchivoAseguradosInicio(Model model) {
		
		UploadFileBean uploadItem = new UploadFileBean();
		model.addAttribute("uploadItem", uploadItem);
		
		return "asignacion.capturaPatron";
	}
	
	@RequestMapping(value = "/portal/inicio/{nrp}", method = RequestMethod.GET)
	public String cargarArchivoAseguradosInicioDesdePortal(Model model,
			HttpServletRequest request, @PathVariable String nrp, HttpSession session) {
		
		UploadFileBean uploadItem = new UploadFileBean();
		uploadItem.setErpName(nrp);
		
		SujetoObligado so = this.sujetoObligadoServiceBusiness.consultarPorRegistroPatronalBasic(nrp, null);
		List<CorreoElectronico> mailsContacto = null;
		
		String nombreComercial = null;
		Persona personaAcuse = null;
		if (so.getFisica() != null) {
			personaAcuse = so.getFisica();
			nombreComercial = so.getFisica().getNombreCompleto().replace("\"", "");
		} else if (so.getMoral() != null) {
			personaAcuse = so.getMoral();
			nombreComercial = so.getMoral().getRazonSocial().replace("\"", "");
		} else {
			nombreComercial = "SIN NOMBRE COMERCIAL";
		}
		
		if (so.getCntroTrabajo() != null
				&& so.getCntroTrabajo().getMediosContacto() != null
				&& !so.getCntroTrabajo().getMediosContacto().isEmpty()) {
			mailsContacto = new ArrayList<CorreoElectronico>();
			CorreoElectronico correo = null;
			for (MedioContacto medio : so.getCntroTrabajo().getMediosContacto()) {
				if (medio instanceof CorreoElectronico) {
					mailsContacto.add((CorreoElectronico)medio);
				} else if (medio.getTipoMedioContacto() != null
						&& medio.getTipoMedioContacto()
								.getIdTipoMedioContacto().intValue() == TipoContactoEnum.CORREO_ELECTRONICO
								.getCodigo().intValue()) {
					correo = new CorreoElectronico();
					correo.setCorreo(medio.getDesFormaContacto());
					mailsContacto.add(correo);
				}
			}
		}
		
		uploadItem.setNombreComercialERP(nombreComercial);
		
		if (mailsContacto != null && !mailsContacto.isEmpty()) {
			uploadItem.setCorreosContacto(mailsContacto);
		}
		
		model.addAttribute("uploadItem", uploadItem);
		model.addAttribute(FROM_PORTAL_KEY, true);
		
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ASIGNACION_NSS.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ASIGNACION_NSS.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
		//datos de acuse
		String rp = so.getNumeroRegistroPatronal() + ""+ so.getModalidad().getNumModalidad() + "" + so.getDigVerificador();
		obtenerDatosAcuse(personaAcuse,rp, session);
		generarCadenaOriginal(personaAcuse, rp, session);
		
		return "asignacion.portal.cargarArchivoAsegurado";
	}
	
	@RequestMapping(value = "/validar-patron", method = RequestMethod.POST)
	public String validarPatron(
			@ModelAttribute("uploadItem") UploadFileBean uploadItem,
			BindingResult result, SessionStatus status, HttpSession session,
			Model model, HttpServletRequest request, Locale locale) {

		uploadItem.setErpName(uploadItem.getErpName().toUpperCase());
		
		String view = null;

		new CapturaPatronSIEValidator().validate(uploadItem, result);

		if (!result.hasErrors()) {
			try {
				String nrp = uploadItem.getErpName();

				// TODO: MASE - Validar que el NRP este activo
				SujetoObligado so = ruleServiceBusiness.validarNumeroDeRegistroPatronal(nrp);

				String nombreComercial = null;
				
				if (so.getFisica() != null) {
					nombreComercial = so.getFisica().getNombreCompleto();
				} else if (so.getMoral() != null) {
					nombreComercial = so.getMoral().getRazonSocial();
				} else {
					nombreComercial = "SIN NOMBRE COMERCIAL";
				}
				
				model.addAttribute("PATRON_SIE", true);
				
				uploadItem.setNombreComercialERP(nombreComercial);

				view = "asignacion.capturaPatron";
			} catch (GestionPatronalBusinessException e) {
				String message = messageSource.getMessage(e.getMessage(), null,
						locale);
				result.rejectValue("errorFormGeneral", e.getMessage());
				log.error("Error al validar el patr\u00F3n: " + message);

				view = "asignacion.capturaPatron";

			}
		} else {
			view = "asignacion.capturaPatron";
		}

		return view;
	}
	
	@RequestMapping(value = "/confirmar-patron", method = RequestMethod.POST)
	public String confirmarPatron (@ModelAttribute("uploadItem") UploadFileBean uploadItem,
			BindingResult result, SessionStatus status, HttpSession session,
			Model model, HttpServletRequest request) {
		
		return "asignacion.cargarArchivoAsegurado";
	}
	
	@RequestMapping(value="/registro-masivo/cargarArchivo", method=RequestMethod.POST)
	public String cargarArchivoAsegurados(
			@ModelAttribute("uploadItem") UploadFileBean uploadItem,
			BindingResult result, SessionStatus status, HttpSession session,
			Model model, HttpServletRequest request, Locale locale) {
		
		String view = cargarArchivoAseguradosCommon(uploadItem, result, status,
				session, model, request, locale, false);
				
		return view;
	}
	
	@RequestMapping(value="/portal/registro-masivo/cargarArchivo", method=RequestMethod.POST)
	public String cargarArchivoAseguradosDesdePortal(
			@ModelAttribute("uploadItem") UploadFileBean uploadItem,
			BindingResult result, SessionStatus status, HttpSession session,
			Model model, HttpServletRequest request, Locale locale) {
		
		String view = cargarArchivoAseguradosCommon(uploadItem, result, status,
				session, model, request, locale, true);
		
		model.addAttribute(FROM_PORTAL_KEY, true);
				
		return view;
	}
			
	private String cargarArchivoAseguradosCommon( UploadFileBean uploadItem,
			BindingResult result, SessionStatus status, HttpSession session,
			Model model, HttpServletRequest request, Locale locale, boolean fromPortal) {
		
		String msgError = null;
		String view = null;
		InputStream stream = null;
		StringBuilder sbFilePath = new StringBuilder();
		
		Usuario usuarioSesion = (Usuario) this.getUsuarioEnSesion(session);
		if (usuarioSesion == null) {
			/*
			 * Esta validación se puso para que cuando se venga del portal
			 * aún así se pueda recuperar el usuario que inicio sesión
			 */
			this.homeController.generarUsuarioSession(model, session, request);
			usuarioSesion = (Usuario) this.getUsuarioEnSesion(session);
		}

		new CargaArchivoSIEValidator().validate(uploadItem, result);

		if (!result.hasErrors()) {
			try {
				String nrp = uploadItem.getErpName().toUpperCase();
				
				/*
				 * Una vez que se pasan las validaciones de datos requeridos y
				 * extensión del archivo, se checa que el contenido del archivo
				 * sea válido y que la cantidad de registros sea la permitida,
				 * estas validaciones no se hacen en el Validator ya que se
				 * quiere evitar parsear doble el XML una al validar y la otra
				 * al generar la lista de objetos
				 */
				stream = uploadItem.getFileData().getInputStream();
				
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = sf.newSchema(ClassUtils.getDefaultClassLoader().getResource("xsd/archivoDisMag.xsd"));
				
				JAXBContext jc = JAXBContext.newInstance(EmpleadoEmpresa.class);
				
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				unmarshaller.setSchema(schema);
				ValidationEventCollector validationCollector = new EstudiantesSimeValidationEventHandler();
				unmarshaller.setEventHandler(validationCollector);

				EmpleadoEmpresa empleadoEmpresa = (EmpleadoEmpresa) unmarshaller
						.unmarshal(stream);
				
				if (validationCollector.hasEvents()) {
					StringBuffer error = new StringBuffer();
					Set<Integer> lineasError = new LinkedHashSet<Integer>();
					
					error.append("Valor inválido en las líneas: ");
					
					for (ValidationEvent event : validationCollector.getEvents()) {
						lineasError.add(event.getLocator().getLineNumber());
					}
					
					for (Integer linea : lineasError) {
						error.append(linea);
						error.append(", ");
					}
					
					error.replace(error.length() - 2, error.length(), "");
					
					throw new TransformacionException(error.toString());
				}
				
				if (empleadoEmpresa.getEmpleado() == null
						|| (empleadoEmpresa.getEmpleado() != null 
								&& empleadoEmpresa.getEmpleado().isEmpty())) {
					result.rejectValue("errorFormGeneral", "field.file.xml.content.empty");
				}
				
				if (!result.hasErrors()) {
					
					this.log.debug("Validaciones de contenido y cantidad de registros exitosas para proceso ESTUDIANTES");
						
					CommonsMultipartFile fileData = uploadItem.getFileData();
					
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					
					String tempPath = this.parametrosServiceBusiness.obtenerParametroDeConfiguracion(SystemKeyParameters.KEY_FS_SIE) ;
					
					StringBuffer fileName = new StringBuffer();
					fileName.append(nrp).append("_");
					fileName.append(timeStamp).append("_");
					fileName.append(fileData.getOriginalFilename());
					
					this.log.info("Cantidad de registros cargados en el archivo "
							+ fileName.toString() + " para estudiantes: "
							+ empleadoEmpresa.getEmpleado().size());
					
					sbFilePath.append(tempPath).append("/").append(fileName);
	
					log.info("El archivo se guardara en : " + sbFilePath.toString());
	
					byte[] fileContent = fileData.getFileItem().get();
					boolean archivoGenerado = ftpUploader.uploadFileContent(
							sbFilePath.toString(), fileContent);
					
					if (!archivoGenerado) {
						throw new FileUploadException("El archivo no fue almacenado en el servidor FTP. Intente nuevamente");
					}
					
					AsignacionMasivaWrapper wrapper = new AsignacionMasivaWrapper();
					wrapper.setNrp(nrp);
					wrapper.setUsuario(usuarioSesion);
					wrapper.setFileName(fileName.toString());
					wrapper.setCorreosContacto(uploadItem.getCorreosContacto());
					
					OrigenSolicitud origenSolicitud = new OrigenSolicitud();
					
					if (fromPortal) {
						origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
					} else {
						origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.VENTANILLA.getId());
					}
					
					FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
					
					Solicitud solicitud = this.serviceBusiness.crearEncolarSolicitudAsignacionMasivaSIE(wrapper,firma);
					
					model.addAttribute("FOLIO_SOLIC_SIE", solicitud.getNoFolioSolicitud());
					
					
					if (!fromPortal) {
						view = "asignacion.confirmarCargaArchivo";
					} else {
						view = "asignacion.portal.confirmarCargaArchivo";
					}
				} else {
					if (!fromPortal) {
						view = "asignacion.cargarArchivoAsegurado";
					} else {
						msgError = generarMsgError(result, session.getServletContext());
						view = "asignacion.portal.confirmarCargaArchivo";
					}
				}
			} catch (IOException e) {
				result.reject("errorFormGeneral", "Ocurri\u00F3 un error al guardar el archivo");
				log.error("Error al guardar el archivo: ", e);
				
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (FileUploadException e) {
				result.reject("errorFormGeneral", e.getMessage());
				log.error("Error al guardar el archivo: ", e);
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (JAXBException e) {
				result.rejectValue("errorFormGeneral", "field.file.xml.content.invalid");
				log.error("Error al guardar el archivo: ", e);
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (SAXException e) {
				result.rejectValue("errorFormGeneral", "field.file.xml.content.invalid");
				log.error("Error al guardar el archivo: ", e);
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (TransformacionException e) {
				Object[] errorArgs = { e.getMessage() };
				result.rejectValue("errorFormGeneral",
						"field.file.xml.content.invalid.detalle", errorArgs, "");
				log.error("Error al guardar el archivo: ", e);
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (SolicitudNoValidaException e) {
				result.rejectValue("errorFormGeneral", "error.crear.solicitud.SIE");
				log.error("Error al generar la solicitud para SIE: ", e);
				
				ftpUploader.removeFile(sbFilePath.toString());
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (SolicitudNoEncontradaException e) {
				result.rejectValue("errorFormGeneral", "error.crear.solicitud.SIE");
				log.error("Error al generar la solicitud para SIE: ", e);
				
				ftpUploader.removeFile(sbFilePath.toString());
				
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch (TramiteNoEncontradoException e) {
				result.rejectValue("errorFormGeneral", "error.crear.solicitud.SIE");
				log.error("Error al generar la solicitud para SIE: ", e);
				
				ftpUploader.removeFile(sbFilePath.toString());
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
			} catch ( SolicitudException se){
				
				result.rejectValue("errorFormGeneral", "error.crear.solicitud.SIE");
				log.error("Error al generar la solicitud para SIE: ", se);
				
				ftpUploader.removeFile(sbFilePath.toString());
					
				if (!fromPortal) {
					view = "asignacion.cargarArchivoAsegurado";
				} else {
					msgError = generarMsgError(result, session.getServletContext());
					view = "asignacion.portal.confirmarCargaArchivo";
				}
				
				
			}finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						this.log.error(e);
					}
				}
			}
		} else {
			if (!fromPortal) {
				view = "asignacion.cargarArchivoAsegurado";
			} else {
				msgError = generarMsgError(result, session.getServletContext());
				view = "asignacion.portal.confirmarCargaArchivo";
			}
		}

		if (fromPortal && StringUtils.isNotBlank(msgError)) {
			model.addAttribute("MSG_ERROR", msgError);
		}
		
		return view;
	}
	
	@RequestMapping(value = "/portal/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital de forma temporal " + firmaElectronica);
		return null;
	}
	
	private void obtenerDatosAcuse(Persona persona, String nrp, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());

		// RFC
		datosAcuse.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial().replace("\"", ""));
		}
		datosAcuse.setNombreCompleto(sbnombre.toString());

		if (persona instanceof Fisica) {
			datosAcuse.setCurp(((Fisica)persona).getCurp());
		}
		
		if(nrp != null) {
			datosAcuse.setRegistroPatronal(nrp);
		}

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}
	
	private void generarCadenaOriginal(Persona persona, String nrp, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TIPO_SOLICITUD).append("|");

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());

		// Folio
		contenidoAFirmar.append("Folio:|");

		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial().replace("\"", ""));
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());

		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		} else {
			contenidoAFirmar.append("|");
		}

		// Registro Patronal(No aplica)
		contenidoAFirmar.append("Registro Patronal:").append(nrp).append("|");

		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}
	
	private String generarMsgError(BindingResult result, ServletContext context) {

		StringBuffer msgError = new StringBuffer();
		Properties prop = new Properties();

		try {

			// load a properties file
			prop.load(context.getResourceAsStream("/WEB-INF/config/messages/messages.properties"));

			for (ObjectError error : result.getAllErrors()) {
				msgError.append(prop.getProperty(error.getCode()));
				msgError.append(", ");
			}

			msgError.replace(msgError.lastIndexOf(", "), msgError.length(), "");

		} catch (IOException e) {
			this.log.error(e);
			msgError.append("Error Inesperado");
		} 

		return msgError.toString();
	}
}