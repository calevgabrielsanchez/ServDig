package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ProcesoSIMEServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.bean.UploadFileBean;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.ProcesoSIMEValidator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.Empleado;
import mx.gob.imss.ctirss.delta.model.asegurado.EmpleadoEmpresa;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoNssEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoRegistroSIMEEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.AseguradoWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/sime")
public class ProcesoSIMEController extends AbstractController {

	private static final String INFO_PATRON_SESSION_KEY = "PATRON_SIME";
	private static final String CVE_SUDELEG_SIME = "59";
	
	public static final String LISTA_EXTRANJEROS_SESSION_KEY = "EXTRANJEROS_SIME";
	public static final String LISTA_EXTRANJEROS_PROCESADOS_SESSION_KEY = "EXTRANJEROS_PROCESADOS_SIME";
	public static final String EXTRANJERO_SESSION_KEY = "EXTRANJERO_SIME";
	public static final String PROCESO_SIME_SESSION_KEY = "FROM_SIME";

	@Autowired
	private RuleServiceBusinessRemote ruleServiceBusiness;
	@Autowired
	private ProcesoSIMEServiceBusinessRemote procesoSIMEServiceBusiness;
	@Autowired
	private HomeController homeController;

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String cargarArchivoAseguradosInicio(Model model,
			HttpSession session, HttpServletRequest request) {

		session.removeAttribute(INFO_PATRON_SESSION_KEY);
		session.removeAttribute(LISTA_EXTRANJEROS_PROCESADOS_SESSION_KEY);
		session.removeAttribute(LISTA_EXTRANJEROS_SESSION_KEY);
		session.removeAttribute(EXTRANJERO_SESSION_KEY);
		session.removeAttribute(PROCESO_SIME_SESSION_KEY);
		
		this.homeController.generarUsuarioSession(model, session, request);

		UploadFileBean uploadItem = new UploadFileBean();
		model.addAttribute("uploadItem", uploadItem);

		return "sime.cargarArchivo";
	}

	@RequestMapping(value = "/cargarArchivo", method = RequestMethod.POST)
	public String cargarArchivoAsegurados(
			@ModelAttribute("uploadItem") UploadFileBean uploadItem,
			BindingResult result, SessionStatus status, HttpSession session,
			Model model, HttpServletRequest request, Locale locale) {

		String view = null;
		String capitalNTilde = new String("\u00D1");
		
		Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
		
		InputStream stream = null;

		new ProcesoSIMEValidator().validate(uploadItem, result);
				
		if (!result.hasErrors()) {
			try {
				
				/*
				 * Una vez que se pasan las validaciones de datos requeridos, se
				 * checa que el contenido del archivo sea válido y que la
				 * cantidad de registros sea la permitida, estas validaciones
				 * no se hacen en el Validator ya que se quiere evitar parsear doble
				 * el XML una al validar y la otra al generar la lista de objetos
				 */
				stream = uploadItem.getFileData().getInputStream();
				
				JAXBContext jaxbContext = JAXBContext
						.newInstance(EmpleadoEmpresa.class);				
				
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				EmpleadoEmpresa empleadoEmpresa = (EmpleadoEmpresa) jaxbUnmarshaller
						.unmarshal(stream);
								
				int numMaxRegistros = 50;
				
				if (empleadoEmpresa.getEmpleado() != null) {
					if (empleadoEmpresa.getEmpleado().isEmpty()) {
						result.rejectValue("fileData", "field.file.xml.content.empty");
					} else if (empleadoEmpresa.getEmpleado().size() > numMaxRegistros) {
						Object[] errorArgs = { numMaxRegistros };
						result.rejectValue("fileData", "field.file.xml.content.max",
								errorArgs, "");
					}
				} else {
					result.rejectValue("fileData", "field.file.xml.content.empty");
				}
				
				if (!result.hasErrors()) {
					
					this.log.debug("Validaciones de contenido y cantidad de registros exitosas");
					
					String nrp = uploadItem.getErpName().toUpperCase();
					SujetoObligado patron = ruleServiceBusiness
							.validarNumeroDeRegistroPatronal(nrp);
					
					/*
					 * Una vez que se valido el NRP se procede a generar el mapa con
					 * los registros del archivo XML
					 */
					Map<Integer, Empleado> extranjerosSIME = new LinkedHashMap<Integer, Empleado>();
					Date fechaNacimiento = null;
					Date fechaIngreso = null;
					Calendar calendar = null;
					
					int count = 0;
					String cveSubdelegacionUsuario = null;
					
					if (usuario != null 
							&& usuario.getUsuarioFuncionario() != null 
							&& usuario.getUsuarioFuncionario().getSubdelegacion() != null) {
						try {
							Integer.parseInt(usuario.getUsuarioFuncionario().getSubdelegacion().getClave());
							cveSubdelegacionUsuario = usuario.getUsuarioFuncionario().getSubdelegacion().getClave();
						} catch(NumberFormatException e) {
							this.log.error("Error al parsear la clave de la subdelegacion", e);
							cveSubdelegacionUsuario = CVE_SUDELEG_SIME;
						}
					} else {
						cveSubdelegacionUsuario = CVE_SUDELEG_SIME;
					}
															
					for (Empleado extranjeroXML : empleadoEmpresa.getEmpleado()) {
	
						extranjeroXML.setCveRegPatron(nrp);
	
						/*
						 * Se buscan los # y &, que en realidad son "enies", pero
						 * que desde el archivo viene mal
						 */
						if (StringUtils.isNotBlank(extranjeroXML.getNombre())) {
							extranjeroXML.setNombre(extranjeroXML.getNombre()
									.replaceAll("[#|&]", capitalNTilde));
						}
	
						if (StringUtils.isNotBlank(extranjeroXML
								.getApellidoPaterno())) {
							extranjeroXML.setApellidoPaterno(extranjeroXML
									.getApellidoPaterno().replaceAll("[#|&]",
											capitalNTilde));
						}
	
						if (StringUtils.isNotBlank(extranjeroXML
								.getApellidoMaterno())) {
							extranjeroXML.setApellidoMaterno(extranjeroXML
									.getApellidoMaterno().replaceAll("[#|&]",
											capitalNTilde));
						}
	
						if (StringUtils
								.isNotBlank(extranjeroXML.getDiaNacimiento())
								&& StringUtils.isNotBlank(extranjeroXML
										.getMesNacimiento())
								&& StringUtils.isNotBlank(extranjeroXML
										.getAnioNacimiento())) {
	
							try {
								calendar = Calendar.getInstance();
								calendar.set(Calendar.DAY_OF_MONTH, Integer
										.valueOf(extranjeroXML.getDiaNacimiento()));
								calendar.set(Calendar.MONTH,
										Integer.valueOf(extranjeroXML
												.getMesNacimiento()) - 1);
								calendar.set(Calendar.YEAR, Integer
										.valueOf(extranjeroXML.getAnioNacimiento()));
	
								fechaNacimiento = calendar.getTime();
							} catch (Exception e) {
								this.log.warn(
										"Error al tratar de genera la fecha de nacimiento a partir de los campos particulares:",
										e);
								fechaNacimiento = generarFechaNacimientoDesdeCURP(extranjeroXML
										.getCurp());
							}
						} else {
							fechaNacimiento = generarFechaNacimientoDesdeCURP(extranjeroXML
									.getCurp());
						}
						
						if (StringUtils.isNotBlank(extranjeroXML.getDiaIngreso())
								&& StringUtils.isNotBlank(extranjeroXML.getMesIngreso())
								&& StringUtils.isNotBlank(extranjeroXML.getAnioIngreso())) {
							
							try {
								calendar = Calendar.getInstance();
								calendar.set(Calendar.DAY_OF_MONTH, Integer
										.valueOf(extranjeroXML.getDiaIngreso()));
								calendar.set(Calendar.MONTH,
										Integer.valueOf(extranjeroXML
												.getMesIngreso()) - 1);
								calendar.set(Calendar.YEAR, Integer
										.valueOf(extranjeroXML.getAnioIngreso()));
	
								fechaIngreso = calendar.getTime();
							} catch (Exception e) {
								this.log.warn(
										"Error al tratar de genera la fecha de ingreso", e);
							}
						}
	
						extranjeroXML.setFecNacimiento(fechaNacimiento);
						extranjeroXML.setFecIngreso(fechaIngreso);
	
						extranjeroXML.setLugarNacimiento(StringUtils.leftPad(extranjeroXML.getLugarNacimiento(), 2, "0"));
						
						count++;
						
						extranjeroXML.setLlaveRegistro(count);
						extranjeroXML.setEstadoRegistro(EstadoRegistroSIMEEnum.POR_PROCESAR);
						extranjeroXML.setCveSubdelegacionUsuario(cveSubdelegacionUsuario);
						
						extranjerosSIME.put(count, extranjeroXML);
	
					}
	
					session.setAttribute(LISTA_EXTRANJEROS_SESSION_KEY,
							extranjerosSIME);
					session.setAttribute(INFO_PATRON_SESSION_KEY, patron);
	
					model.addAttribute("fisica", new Fisica());
	
					view = "sime.listaExtranjeros";
				} else {
					view = "sime.cargarArchivo";
				}
			} catch (GestionPatronalBusinessException e) {
				String message = messageSource.getMessage(e.getMessage(), null,
						locale);
				result.rejectValue("errorFormGeneral", e.getMessage());
				log.error("Error al validar el patr\u00F3n: " + message);
				
				view = "sime.cargarArchivo";
			} catch (IOException e) {
				this.log.error(e);
				view = "sime.cargarArchivo";
			} catch (JAXBException e) {
				this.log.error(e);
				result.rejectValue("fileData", "field.file.xml.content.invalid");
				view = "sime.cargarArchivo";
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						this.log.error(e);
					}
				}
			}
		} else {
			view = "sime.cargarArchivo";
		}

		return view;
	}

	@RequestMapping(value = "/nss/recuperado", method = RequestMethod.POST)
	public String procesarNssRecuperado(@ModelAttribute Empleado extranjeroAux,
			BindingResult result, Model model, HttpSession session) {

		procesarExtranjeroCommon(session, extranjeroAux,
				EstadoNssEnum.RECUPERADO, EstadoRegistroSIMEEnum.EXITO);

		model.addAttribute("fisica", new Fisica());

		return "sime.listaExtranjeros";
	}

	@RequestMapping(value = "/nss/asignado", method = RequestMethod.POST)
	public String procesarNssAsignado(@ModelAttribute Empleado extranjeroAux,
			BindingResult result, Model model, HttpSession session) {
		
		EstadoNssEnum estadoNss = null;
		
		if (extranjeroAux.getEstadoRegistroAux() == EstadoRegistroSIMEEnum.EXITO.getClave()) {
			estadoNss = EstadoNssEnum.ASIGNADO;
		}
		
		procesarExtranjeroCommon(session, extranjeroAux, estadoNss,
				EstadoRegistroSIMEEnum.obtenerEnumById(extranjeroAux
						.getEstadoRegistroAux()));

		model.addAttribute("fisica", new Fisica());

		return "sime.listaExtranjeros";
	}

	@RequestMapping(value = "/actualizar/registro-en-proceso", method = RequestMethod.POST)
	public @ResponseBody Empleado actualizarAseguradoSession(
			@RequestBody AseguradoWrapper asegurado, HttpSession session) {

		Empleado empSession = (Empleado) session
				.getAttribute(ProcesoSIMEController.EXTRANJERO_SESSION_KEY);
		empSession.setNss(asegurado.getNss());
		empSession.setEstadoRegistro(EstadoRegistroSIMEEnum.EXITO);
		
		UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
		umf.setIdUMF(asegurado.getUmf().getIdUMF());
		umf.setNoEconomico(asegurado.getUmf().getNoEconomico());
		umf.setSubdelegacion(asegurado.getUmf().getSubdelegacion());
		empSession.setUmf(umf);

		return empSession;
	}
	
	@RequestMapping(value = "/ignorar/registro-en-proceso", method = RequestMethod.POST)
	public String ignorarNssAsignado(Model model,
			HttpSession session) {

		Empleado empSession = (Empleado) session
				.getAttribute(ProcesoSIMEController.EXTRANJERO_SESSION_KEY);
		empSession.setEstadoRegistro(EstadoRegistroSIMEEnum.PENDIENTE);
					
		model.addAttribute("fisica", new Fisica());

		return "sime.listaExtranjeros";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/generar/archivo-saiia", method = RequestMethod.POST)
	public void generarArchivoSAIIA(
			HttpSession session, HttpServletResponse response) {

		Map<Integer, Empleado> extranjerosProcesados = (Map<Integer, Empleado>) session
				.getAttribute(LISTA_EXTRANJEROS_PROCESADOS_SESSION_KEY);
		
		byte[] x = this.procesoSIMEServiceBusiness.generarContenidoArchivoSAIIA(extranjerosProcesados);
		
		response.addHeader("Accept-Ranges","bytes");
		response.addHeader("Cache-Control","public");
		response.addHeader("Cache-Control","must-revalidate");
		response.addHeader("Pragma","public");
		response.setContentType("text/plain");
        response.setContentLength(x.length);
        response.setHeader("Content-Disposition","attachment; filename=archivoSAIIA.txt"); 
        
        try {
            FileCopyUtils.copy(x, response.getOutputStream());
        } catch (IOException e) {
            this.log.error(e);
        }
	}
	
	private Date generarFechaNacimientoDesdeCURP(String curp) {

		DateFormat formatter = new SimpleDateFormat("yyMMdd");
		Date fechaNacimiento = null;

		this.log.debug("El registro no cuenta con los datos requeridos para la fecha de nacimiento, se va a obtener de la CURP");

		if (StringUtils.isNotBlank(curp)) {
			try {
				fechaNacimiento = formatter.parse(curp.substring(4, 10));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		} else {
			this.log.debug("El registro no cuenta con CURP");
		}

		return fechaNacimiento;
	}

	@SuppressWarnings("unchecked")
	private void procesarExtranjeroCommon(HttpSession session,
			Empleado extranjeroAux, EstadoNssEnum estadoNSS,
			EstadoRegistroSIMEEnum estadoRegistro) {

		Map<Integer, Empleado> extranjerosProcesados = (Map<Integer, Empleado>) session
				.getAttribute(LISTA_EXTRANJEROS_PROCESADOS_SESSION_KEY);

		if (extranjerosProcesados == null) {
			extranjerosProcesados = new LinkedHashMap<Integer, Empleado>();
			session.setAttribute(LISTA_EXTRANJEROS_PROCESADOS_SESSION_KEY,
					extranjerosProcesados);
		}

		Map<Integer, Empleado> extranjerosSIME = (Map<Integer, Empleado>) session
				.getAttribute(ProcesoSIMEController.LISTA_EXTRANJEROS_SESSION_KEY);

		Empleado extranjero = extranjerosSIME.get(extranjeroAux
				.getLlaveRegistro());

		extranjero.setNss(extranjeroAux.getNss());
		extranjero.setEstadoNss(estadoNSS);
		extranjero.setUmf(extranjeroAux.getUmf());
		extranjero.setEstadoRegistro(estadoRegistro);
		
		/*
		 * Sólo cuando el registro haya sido exitoso se pasa a la lista de
		 * procesados y se quita de la lista por procesar
		 */
		if (estadoRegistro.getClave() == EstadoRegistroSIMEEnum.EXITO.getClave()) {
			extranjerosProcesados.put(extranjeroAux.getLlaveRegistro(), extranjero);
	
			extranjerosSIME.remove(extranjeroAux.getLlaveRegistro());
		}

	}
		
}
