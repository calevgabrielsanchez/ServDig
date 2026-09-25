package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.implementacion.ClienteWebserviceCurp;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;

import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.model.table.RowStateMap;

import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.util.DataTableData;
import mx.gob.imss.ctirss.sso.util.ValidationUtils;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAprobador;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.SolicitudMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.controller.SolicituddMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;

import java.util.Locale;
import java.util.Iterator;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.forgerock.opendj.ldap.EntryNotFoundException;


@ManagedBean(name = "registraSolicitudAdmonMB")
@CustomScoped("#{window}")
public class RegistraSolicitudAdmonMB {


	@ManagedProperty("#{solicituddMB}")
	private SolicituddMB solicitudMB;

	@ManagedProperty("#{solicitudMB}")
	private SolicitudMB solMB;

	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuario;

	@ManagedProperty(value = "#{catalogoMB}")
	private CatalogosMB catalogoMB;
    
	@ManagedProperty(value="#{consultaGenericaAdmon}")	 
	private ConsultaGenericaControllerAdmon filtrosConsulta;
	
	@EJB
	private SolicitudServiceLocal solicitudCriteria;

	@EJB
	private BitacoraServiceLocal bitacoraService;

	private static Log log = LogFactory.getLog(RegistraSolicitudAdmonMB.class);

	private boolean registroUsuario = false;
	private Long idSolicitud = 0L;


	private int defaultDelegationValue;
	private int defaultSubDelegationValue;
	private int defaultUmfValue;
	private String errorModulos;
	private String errorRoles;
	

	private boolean combos = true;
	private boolean botonAdd = true;

	private boolean errorFlag;
	private boolean usuarioInterno;
	private boolean inactiveEmployeeFlag;

	private int fieldToDeleteAreaNormativa;
	private int fieldToDeleteDepartamento;
	private int fieldToDeletePuesto;
	private int fieldToDeleteModulo;
	
	private int cveAreaNorm;
	
	private RowStateMap stateMapFromTableOne = new RowStateMap();
	private RowStateMap stateMapFromTableTwo = new RowStateMap();

	private boolean dblClick = false;
	private boolean instantUpdate = true;

	private Collection<UsuarioDTO> usuarios;
	private List<SolicitudDTO> solicitudes;

	private boolean sinmodifcar = true;
	private boolean modificar = true;
	private boolean modifcarCombos = true;
	
	private SolicitudDTO solSelect; 

	private HtmlDataTable tabla; /*
								 * new SelectItem("multiplecell",
								 * "Multiple Cell")
								 */
	String curpAeliminar="";
	
   	private static int AREA_NIVEL_CENTRAL = 1;
   	private static int AREA_NIVEL_DELEGACION = 2;
   	private static int AREA_NIVEL_SUBDELEGACION = 3;
   	private static int AREA_NIVEL_UMF = 4;
   	
	
	private String selectionMode = AVAILABLE_MODES[0].getValue().toString();
	private static final SelectItem[] AVAILABLE_MODES = {
			new SelectItem("single", "Single Row"),
			new SelectItem("multiple", "Multiple Rows"),
			new SelectItem("singlecell", "Single Cell") };

	private RowStateMap stateMap = new RowStateMap();

	private static String CURP_PATTERN = "([A-Z-a-z]{4})([0-9]{6})([A-Z-a-z]{6})([A-Z-a-z-0-9]{1})([0-9]{1})";
	private static String MATRICULA_PATTERN = "([0-9]{8})";
	private static String MATRICULA_PATTERN2 = "([0-9]{9})";
	private static String MATRICULA_PATTERN3 = "([0-9]{7})";
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private String curp = "";
	private String telefono = "";
	private String correo = "";
	
	private boolean flagFirstSlide = false;

	public void init() throws AdmonUsuariosException {
		try {
			solicitudes = new ArrayList<SolicitudDTO>();
			filtrosConsulta.obtieneDatosUsuario();
			CargaTablaUno();
			CargaTablaDos();
			consultarSolicitudes();
		} catch (Exception e) {
			System.out.println("Error RegistraSolicitudMB");
			e.printStackTrace();
			log.error("Error RegistraSolicitudMB ", e);
		}
	}
	
	
	

	public RegistraSolicitudAdmonMB() {
	}

	public String inicializaSolicitud() {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		registroUsuario = false;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		errorModulos = "";
		errorRoles = "";
		CargaTablaUno();
		CargaTablaDos();
		filtrosConsulta.obtieneDatosUsuario();
		modifcarCombos = true;
		return "inicializadaAdmon";
	}

	public void inicializaSolicitud2() {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		registroUsuario = false;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		errorModulos = "";
		errorRoles = "";
		CargaTablaUno();
		CargaTablaDos();
		filtrosConsulta.obtieneDatosUsuario();
		modifcarCombos = true;
		curp = "";
		correo = "";
		telefono = "";
	}

	public void inicializaSolicitud2(AjaxBehaviorEvent e) {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		registroUsuario = false;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		errorModulos = "";
		errorRoles = "";
		CargaTablaUno();
		CargaTablaDos();
		filtrosConsulta.obtieneDatosUsuario();
		modifcarCombos = true;
	}

	public void limpia(){
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefaultSinCurp();
		registroUsuario = false;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		errorModulos = "";
		errorRoles = "";
		CargaTablaUno();
		CargaTablaDos();
		filtrosConsulta.obtieneDatosUsuario();
		modifcarCombos = true;
		solicitudMB.setMatricula("");
		solicitudMB.setNssNom("");
		solicitudMB.setPuestoDescNom("");
		solicitudMB.setDepartamentoDescNom("");
		solicitudMB.setCveDelegacionNom("");
		solicitudMB.setCveSubdelegacionNom("");
		solicitudMB.setCveUmfNom("");

	}

	public void lisCurp(ValueChangeEvent event) throws AdmonUsuariosException {
		curp = (String) event.getNewValue();
	}

	public void lisTelefono(ValueChangeEvent event) throws AdmonUsuariosException {
		telefono = (String) event.getNewValue();
	}

	public void lisCorreo(ValueChangeEvent event) throws AdmonUsuariosException {
		correo = (String) event.getNewValue();
	}

	
	public void CargaTablaUno() {
		solicitudMB.setRolesData(new ArrayList<PuestoDTO>(DataTableData
				.getDefaultData()));
	}

	public void CargaTablaDos() {
		solicitudMB.setModulosData(new ArrayList<ModuloDTO>(DataTableData
				.getDefaultDataTD()));
	}

	public SolicituddMB getSolicitudMB() {
		return solicitudMB;
	}

	public void setSolicitudMB(SolicituddMB solicitudMB) {
		this.solicitudMB = solicitudMB;
	}

	public boolean isRegistroUsuario() {
		return registroUsuario;
	}

	public void setRegistroUsuario(boolean registroUsuario) {
		this.registroUsuario = registroUsuario;
	}


	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public int getDefaultDelegationValue() {
		return defaultDelegationValue;
	}

	public void setDefaultDelegationValue(int defaultDelegationValue) {
		this.defaultDelegationValue = defaultDelegationValue;
	}

	public int getDefaultSubDelegationValue() {
		return defaultSubDelegationValue;
	}

	public void setDefaultSubDelegationValue(int defaultSubDelegationValue) {
		this.defaultSubDelegationValue = defaultSubDelegationValue;
	}

	public int getDefaultUmfValue() {
		return defaultUmfValue;
	}

	public void setDefaultUmfValue(int defaultUmfValue) {
		this.defaultUmfValue = defaultUmfValue;
	}

	public String getErrorModulos() {
		return errorModulos;
	}

	public void setErrorModulos(String errorModulos) {
		this.errorModulos = errorModulos;
	}

	public String getErrorRoles() {
		return errorRoles;
	}

	public void setErrorRoles(String errorRoles) {
		this.errorRoles = errorRoles;
	}


	public String validaryCargarDatos() {

		log.info("##### FIX CUPR | JMGD | 08/07/2026 #####");
		
		limpia();
		String respuesta = "";
		modificar = true;
		try {
			Locale defloc = Locale.getDefault();
			solicitudMB.setCurp(solicitudMB.getCurp().toUpperCase(defloc));
			//if (validarEstructuraCurp(solicitudMB.getCurp()) == false) {
			if (!ValidationUtils.isValidCurp(solicitudMB.getCurp())){
				filtrosConsulta.showMsg(" El curp capturado es invalido, recuerde que la longitud minima es a 18 posiciones, favor de validarlo");
				return "inicializadaAdmon";
			}
			respuesta = solicitudMB.buscaCurpExistente(solicitudMB.getCurp());
		} catch (Exception e) {
		}
		
		if (!"".equals(respuesta)) {
			curpAeliminar = respuesta;
			errorFlag = true;
			String msgDesc = "La CURP ya se encuentra registrada y el estatus de la solicitud es: ";
			
			try {
				String resEstatus = solicitudMB.buscaEstatusSolicitud(solicitudMB.getCurp());
				msgDesc = msgDesc + ". "+resEstatus;
				if ("BAJA".equals(resEstatus)|| "CAMBIO AREA ADSCRIPCION".equals(resEstatus))
					msgDesc = msgDesc	+ " Probablemente requiera reactivación de la cuenta";
				
			} catch (Exception e) {
			}

			filtrosConsulta.showMsg(msgDesc);
			botonAdd = true;
		} else {
			if(cargaDatosUsuario(solicitudMB.getCurp())&&cargaMatriculaImss(solicitudMB.getCurp()))
			{
				errorFlag = true;
				curpAeliminar = "";
				botonAdd = false;
				modificar = false;
				registroUsuario = false;
			}
			else
			{
				botonAdd = true;
				modificar = true;
			}

		}
		return "inicializadaAdmon";
	}

	
	public void validaryCargarDatos2() {
		log.info("##### FIX CUPR | JMGD | 08/07/2026 #####");
		limpia();
		String respuesta = "";
		String matricula = "";
		modificar = true;
		flagFirstSlide = true;
		try {
			Locale defloc = Locale.getDefault();
			solicitudMB.setCurp(curp.toUpperCase(defloc));
			//if (validarEstructuraCurp(solicitudMB.getCurp()) == false) {
			
			log.info("FLAG CURP: " +ValidationUtils.isValidCurp(solicitudMB.getCurp()));
			
			if (!ValidationUtils.isValidCurp(solicitudMB.getCurp())) {	
				filtrosConsulta.showMsg(" El curp capturado es invalido, recuerde que la longitud minima es a 18 posiciones, favor de validarlo");
			}
			else
			{
				respuesta = solicitudMB.buscaCurpExistente(solicitudMB.getCurp());
				
				Long id = usuario.getAprobadorSession().getCveIdAprobador();
				SsoAprobador sso = bitacoraService.obtenDatosSession(id);
				
				String curps = sso.getSsoSolicitud().getDesUsrCurp();
				
				Long estatusAlta = 0L;
				int empKeyPro = 0;
				Long estatusSIAP = 0L;
				int empKeyProSIAP = 0;
				
				log.info("##### Se procede a validar la CURP [ " + solicitudMB.getCurp() + " ] #####");
				log.info("##### Orden de validacion\n1.-SIAP\n2.-TTDS\n#####");
				
				UsuarioNominaResponse unr = bitacoraService.datosSIAP(solicitudMB.getCurp(), matricula);
				
				if(unr != null) {
					log.info("##### Se procede a validar CURP en SIAP #####");
					estatusSIAP = Long.parseLong(unr.getEstatus());
					empKeyProSIAP = Integer.parseInt(unr.getTipoContratacion());
					estatusAlta = estatusSIAP;
					empKeyPro = empKeyProSIAP;
				} else {
					log.info("##### Se procede a validar CURP en TTDS #####");
					UsuarioTTD ttd = bitacoraService.datosTTD(solicitudMB.getCurp(), matricula);
					if(ttd != null) {
						log.info("##### La CURP existe en TTDS #####");
						estatusAlta = Long.parseLong(ttd.getEstatus());
						empKeyPro = Integer.parseInt(ttd.getTipoContratacion());
					} else {
						log.info("##### No existe CURP en SIAP ni en TTDS #####");
						estatusAlta = 0L;
						empKeyPro = 0;
					}
				}				
											
				log.info("##### Voy a validar la EMP_KEYPRO [ " + empKeyPro + " ] y el ESTATUS [ " + estatusAlta + " ] en registro AdmonMB #####");
				
				if(!(empKeyPro < 10) || !(estatusAlta.equals(1L))) {
					String msgDesc = "No se puede dar de alta un usuario jubilado o pensionado y debe estar activo en SIAP.";
					filtrosConsulta.showMsg(msgDesc);
				}else if(respuesta.equals(curps)) {
					String msgDesc = "El Usuario aprobador no puede registrarse a el mismo.";
					filtrosConsulta.showMsg(msgDesc);
				} else if (!"".equals(respuesta)) {
					curpAeliminar = respuesta;
					errorFlag = true;
					String msgDesc = "La CURP ya se encuentra registrada y el estatus de la solicitud es: ";
					
					try {
						String resEstatus = solicitudMB.buscaEstatusSolicitud(solicitudMB.getCurp());
						msgDesc = msgDesc + ". "+resEstatus;
						if ("BAJA".equals(resEstatus)|| "CAMBIO AREA ADSCRIPCION".equals(resEstatus))
							msgDesc = msgDesc	+ " Probablemente requiera reactivación de la cuenta";
						
					} catch (Exception e) {
						log.error("::: Error al obtener la solicitud... " + e.getMessage());
						e.printStackTrace();
					}

					filtrosConsulta.showMsg(msgDesc);
					botonAdd = true;

				} else {
					if (cargaDatosUsuario(solicitudMB.getCurp()) && cargaMatriculaImss(solicitudMB.getCurp())) {
						log.info("::: La CURP SI existe en RENAPO... ");
						log.info("::: La CURP SI tiene Matricula... ");
						errorFlag = true;
						curpAeliminar = "";
						botonAdd = false;
						modificar = false;
						registroUsuario = false;
						flagFirstSlide = false;
						filtrosConsulta.showMsg("Se verifico correctamente la información relacionada al CURP");
					}
					else {
						log.info("::: La CURP esta inactiva en SIAP... ");

						botonAdd = true;
						modificar = true;
						filtrosConsulta.showMsg("El CURP esta inactivo en SIAP");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


    public void sleep() {
        try {
            Thread.currentThread().sleep(2500);
        } catch(Exception e) {}
    }
    


	public boolean cargaDatosUsuario(String curp) {
		log.info("::: Se validara la CURP con el servicio Web de RENAPO... " + curp);
		try {
			ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
			Fisica fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo(curp);
			log.info("::: El servicio Web de RENAPO regreso respuesta...");
			solicitudMB.setNombre(fisica.getNombre());
			solicitudMB.setNombrePaterno(fisica.getPrimerApellido());
			solicitudMB.setNombreMaterno(fisica.getSegundoApellido());
			return true;
		}catch(ClienteWebserviceRenapoCurpException wsr) {
			filtrosConsulta.showMsg("El servicio Web de RENAPO no esta disponible intente mas tarde");
			log.error("***** Entre al primer catch *****");
			solicitudMB.setNombre("");
			solicitudMB.setNombrePaterno("");
			solicitudMB.setNombreMaterno("");
			log.error(":::Error::cargaDatosUsuario... " + wsr.getStackTrace());
			log.error("  ", wsr);
			return false;
		}catch(Exception e) {
			filtrosConsulta.showMsg("La CURP no se encontró en el servicio Web RENAPO");
			log.info("##### Entre al segundo catch #####");
			solicitudMB.setNombre("");
			solicitudMB.setNombrePaterno("");
			solicitudMB.setNombreMaterno("");
			log.error(":::Error::cargaDatosUsuario... " + e.getStackTrace());
			log.error("  ", e);
			return false;
		}
	}

	public boolean cargaMatriculaImss(String curp) {
		log.info("::: Se validara la CURP al cargar matricula IMSS... " + curp);
		try {
			ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
			UsuarioNominaResponse usImss = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(curp, "");
			if (usImss != null) {
				log.info("###### Se validara la CURP [ " + curp + " ] en el servicio WEB del SIAP ######");
				solicitudMB.setMatricula(usImss.getMatricula());
				solicitudMB.setNssNom(usImss.getNss());
				solicitudMB.setPuestoDescNom(usImss.getPuestoDesc());
				solicitudMB.setDepartamentoDescNom(usImss.getDepartamentoDesc());
				solicitudMB.setCveDelegacionNom(usImss.getDelegacionCve());
				solicitudMB.setCveSubdelegacionNom("");
				solicitudMB.setCveUmfNom("");
				if (usImss.getEstatus() != null)
					solicitudMB.setCveEstatusNom(new Long(usImss.getEstatus())
							.longValue());
				else
					solicitudMB.setCveEstatusNom(0);
				if (usImss.getTipoContratacion() != null
						&& usImss.getTipoContratacion().equals("11")) {
					filtrosConsulta
							.showMsg("No se puede registrar la cuenta de usuario debido a que esta CURP pertenece a personal jubilado");
					return false;
				} else {
					if (usImss.getEstatus() != null
							&& usImss.getEstatus().equals("2")) {
						filtrosConsulta
								.showMsg("No se puede registrar la cuenta de usuario debido a que esta CURP no se encuentra activo en la nomina");
						return false;

					} else
						return true;
				}
			} else {
				log.info("###### Se validara la CURP [ " + curp + " ] en WS de Trabajador de Tiempo Determinado ######");
				ClienteConsultaCurpTTDS ccct = new ClienteConsultaCurpTTDS();
				UsuarioTTD ut = ccct.invocarServicioConsultaCurpTTDS(curp, "");
				if(ut != null) {
					solicitudMB.setMatricula(ut.getMatricula());
					solicitudMB.setNssNom(ut.getNss());
					solicitudMB.setPuestoDescNom(ut.getPuestoDesc());
					solicitudMB.setDepartamentoDescNom(ut.getDepartamentoDesc());
					solicitudMB.setCveDelegacionNom(ut.getDelegacionCve());
					solicitudMB.setCveSubdelegacionNom("");
					solicitudMB.setCveUmfNom("");
					if (ut.getEstatus() != null)
						solicitudMB.setCveEstatusNom(new Long(ut.getEstatus()).longValue());
					else
						solicitudMB.setCveEstatusNom(0);
					if (ut.getTipoContratacion() != null && ut.getTipoContratacion().equals("11")) {
						filtrosConsulta.showMsg("No se puede registrar la cuenta de usuario debido a que esta CURP pertenece a personal jubilado");
						return false;
					}else {
						if (ut.getEstatus() != null && ut.getEstatus().equals("2")) {
							filtrosConsulta.showMsg("No se puede registrar la cuenta de usuario debido a que esta CURP no se encuentra activo en la nomina");
							return false;

						}else
							return true;
					}
				}
				return false;
			}

		}catch (Exception e) {
			log.error("::: El servicio Web de nomina IMSS no responde... " + e.getMessage());
			e.printStackTrace();
			filtrosConsulta.showMsg("El servicio Web de nomina IMSS no responde, el registro no se puede llevar a cabo. Favor de intentar mas tarde");
			inactiveEmployeeFlag = true;
			log.error(" Error -> {} ", e);
			log.error("ErrorCause -> {}", e.getCause());
			log.error("Error -> {}", e.initCause(e));
			return false;
		}
	}

	public String registrarSolicitudNVer() throws AdmonUsuariosException{
		String respuesta = "";
		
		errorModulos = "";
		errorRoles = "";
		// solicitud
		boolean res2;
		boolean res3;
		
		Locale defloc = Locale.getDefault();


		solicitudMB.setClaveAreaNormativa((int)filtrosConsulta.getClaveAreaNormativa());

		solicitudMB.setCurp(solicitudMB.getCurp().toUpperCase(defloc));
		solicitudMB.setClaveDepartamento((int)filtrosConsulta.getClaveDepartamento());
		solicitudMB.setClavePuesto((int)filtrosConsulta.getClavePuesto());

		solicitudMB.setCorreoElectronico(solicitudMB.getSolicitudDTO().getRefCorreoElectronico());

		// Validaciones para regresar un error sino estan bien los datos
		String msgDesc ="";
		if ("".equals(solicitudMB.getNombre())|| "".equals(solicitudMB.getNombrePaterno())) {
			msgDesc = "Se requieren los datos obligatorios, El Nombre o Apeido paterno es incorrecto";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}

		if ("".equals(solicitudMB.getCurp())) {
			msgDesc = "El Curp es incorrecta";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}

		if ("".equals(solicitudMB.getCorreoElectronico())) {
			msgDesc = "El Correo electrónico es incorrecto, recuerde capturar su correo sin el dominio @imss.gob.mx";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}

		
		if (filtrosConsulta.getClaveDepartamento() <1) {
			msgDesc = "Se requieren los datos obligatorios, se requiere seleccionar un departamento, favor de verificar";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}
		if (filtrosConsulta.getClavePuesto() <1) {
			msgDesc = "Se requieren los datos obligatorios, se requiere seleccionar un puesto, favor de verificar";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}
		
		int cveAN = (int)filtrosConsulta.getClaveAreaNormativa();
		if(cveAN==AREA_NIVEL_DELEGACION&&filtrosConsulta.getClaveDelegacion()<1)
		{
			msgDesc = "Se requieren los datos obligatorios, se requiere seleccionar la delegación, favor de verificar";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}

		if(cveAN==AREA_NIVEL_SUBDELEGACION&&filtrosConsulta.getClaveSubdelegacion()<1)
		{
			msgDesc = "Se requieren los datos obligatorios, se requiere seleccionar la subdelegación, favor de verificar";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}
		
		if(cveAN==AREA_NIVEL_UMF&&filtrosConsulta.getClaveUMF()<1)
		{
			msgDesc = "Se requieren los datos obligatorios, se requiere seleccionar la umf, favor de verificar";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}
		
		res2 = validarEmail(solicitudMB.getCorreoElectronico()+"@imss.gob.mx");
		if(!res2)
		{
			msgDesc = "Se requieren los datos obligatorios, El correo electrónico es incorrecto, favor de verificar";
			filtrosConsulta.showMsg(msgDesc);
			return "inicializadaAdmon";
		}
		
		if ("".equals(curpAeliminar)) {// Indica que es la primera ocasion que
										// se registra
			PuestoDTO rol = new PuestoDTO();
			rol.setCvePuesto(filtrosConsulta.getClavePuesto());
			DepartamentoDTO dep = new DepartamentoDTO();
			dep.setCveSsodepto(filtrosConsulta.getClaveDepartamento());
			AreaNormativaDTO an = new AreaNormativaDTO();
			
			an.setCveSsoareanorma(Long.parseLong(String.valueOf(filtrosConsulta.getClaveAreaNormativa())));

			solicitudMB.agregarClaves(an, dep, rol);
			
			solicitudMB.getSolicitudDTO().setAreaNorm(usuario.getAprobadorSession().getSolicitud().getAreaNorm());
			
			DepartamentoDTO depto = new DepartamentoDTO();
			depto.setCveSsodepto(filtrosConsulta.getClaveDepartamento());
			solicitudMB.getSolicitudDTO().setDptoDTO(depto);
			
			PuestoDTO puesto = new PuestoDTO();
			puesto.setCvePuesto(filtrosConsulta.getClavePuesto());
			solicitudMB.getSolicitudDTO().setPuestoDTO(puesto);
			

			if(cveAN==AREA_NIVEL_DELEGACION)
			{
				DelegacionDTO del = new DelegacionDTO();
				del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
				solicitudMB.getSolicitudDTO().setDelDTO(del);
			}
			else
				if(cveAN==AREA_NIVEL_SUBDELEGACION)
				{
					DelegacionDTO del = new DelegacionDTO();
					del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
					solicitudMB.getSolicitudDTO().setDelDTO(del);

					SubdelegacionDTO subdel = new SubdelegacionDTO();
					subdel.setCveSubelegacion(filtrosConsulta.getClaveSubdelegacion());
					solicitudMB.getSolicitudDTO().setSubdelDTO(subdel);
					
				}
				else
					if(cveAN==AREA_NIVEL_UMF)
					{
						DelegacionDTO del = new DelegacionDTO();
						del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
						solicitudMB.getSolicitudDTO().setDelDTO(del);

						SubdelegacionDTO subdel = new SubdelegacionDTO();
						subdel.setCveSubelegacion(filtrosConsulta.getClaveSubdelegacion());
						solicitudMB.getSolicitudDTO().setSubdelDTO(subdel);

						UmfDTO umf = new UmfDTO();
						umf.setCveUmf(filtrosConsulta.getClaveUMF());
						solicitudMB.getSolicitudDTO().setUmfDTO(umf);
					}
			
			solicitudMB.setCorreoElectronico(solicitudMB.getCorreoElectronico() + "@imss.gob.mx");
			String respuestaRegistro = solicitudMB.registrarSolicitudCuentaNVer();
			if(respuestaRegistro.equals("inicializada"))
				respuestaRegistro = "inicializadaAdmon";

			bitacoraService.guardaSolicitudBit(new Long(solicitudMB.getIdSolicitud()), usuario.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_REGISTRO);
			
			filtrosConsulta.seteaDepto();
			
			filtrosConsulta.showMsg("La cuenta de usuario se ha registrado exitosamente, recuerde agregar Grupos y Módulos. Esta nueva cuenta quedará inactiva hasta su autorización.");

			registroUsuario = true;
			
			agregaaPerfilDefault();

			modifcarCombos = false;
			botonAdd = true;
			solicitudMB.setClaveAreaNormativa((int)filtrosConsulta.getClaveAreaNormativa());
			return respuestaRegistro;
		} else {
			solicitudMB.eliminaPerfilesLDAP(curpAeliminar);
			Long idSol = solicitudMB.obtenerIDSolicitud(curpAeliminar);
			solicitudMB.eliminaPerfilesModulosBD(idSol,solicitudMB.getSolicitudDTO());
			solicitudMB.actualizarSolicitudBD(idSol);
			// Roles
			for (PuestoDTO rolint : solicitudMB.getRolesData()) {
				solicitudMB.registrarPerfiles(idSol,
						(int) rolint.getCvePuesto());
			}
			// Modulos
			for (ModuloDTO modint : solicitudMB.getModulosData()) {
				solicitudMB.registrarModulos(idSol, (int) modint.getDptoDTO()
						.getCveSsodepto(), (int) modint.getCveIdModulo(), 1);
			}

			msgDesc = "USUARIO AGREGADO!!!!!";
			registroUsuario = false;
			botonAdd = true;
			return "inicializadaAdmon";
		}
	}

	public void registrarSolicitudCuenta() throws AdmonUsuariosException{
		String respuesta = "";
		
		errorModulos = "";
		errorRoles = "";
		// solicitud
		boolean res2;
		boolean res3;
		
		Locale defloc = Locale.getDefault();


		solicitudMB.setClaveAreaNormativa((int)filtrosConsulta.getClaveAreaNormativa());

		solicitudMB.setCurp(solicitudMB.getCurp().toUpperCase(defloc));
		solicitudMB.setClaveDepartamento((int)filtrosConsulta.getClaveDepartamento());
		solicitudMB.setClavePuesto((int)filtrosConsulta.getClavePuesto());

		solicitudMB.setCorreoElectronico(correo);

		// Validaciones para regresar un error sino estan bien los datos
		String msgDesc ="";
		if ("".equals(solicitudMB.getNombre())|| "".equals(solicitudMB.getNombrePaterno())) {
			filtrosConsulta.showMsg("Se requieren los datos obligatorios, El Nombre o Apeido paterno es incorrecto");
		}
		else
		{
			if ("".equals(solicitudMB.getCurp())) {
				filtrosConsulta.showMsg("El Curp es incorrecta");
			}
			else
			{
				if ("".equals(solicitudMB.getCorreoElectronico())) {
					filtrosConsulta.showMsg("El Correo electrónico es incorrecto, recuerde capturar su correo sin el dominio @imss.gob.mx");
				}
				else
				{
					if (filtrosConsulta.getClaveDepartamento() <1) {
						filtrosConsulta.showMsg("Se requieren los datos obligatorios, se requiere seleccionar un departamento, favor de verificar");
					}
					else
					{
						if (filtrosConsulta.getClavePuesto() <1) {
							filtrosConsulta.showMsg("Se requieren los datos obligatorios, se requiere seleccionar un puesto, favor de verificar");
						}
						else
						{
							int cveAN = (int)filtrosConsulta.getClaveAreaNormativa();
							if(cveAN==AREA_NIVEL_DELEGACION&&filtrosConsulta.getClaveDelegacion()<1)
							{
								filtrosConsulta.showMsg("Se requieren los datos obligatorios, se requiere seleccionar la delegación, favor de verificar");
							}
							else
							{
								if(cveAN==AREA_NIVEL_SUBDELEGACION&&filtrosConsulta.getClaveSubdelegacion()<1)
								{
									filtrosConsulta.showMsg("Se requieren los datos obligatorios, se requiere seleccionar la subdelegación, favor de verificar");
								}
								else
								{
									if(cveAN==AREA_NIVEL_UMF&&filtrosConsulta.getClaveUMF()<1)
									{
										filtrosConsulta.showMsg("Se requieren los datos obligatorios, se requiere seleccionar la umf, favor de verificar");
									}
									else
									{
										res2 = validarEmail(solicitudMB.getCorreoElectronico()+"@imss.gob.mx");
										if(!res2)
										{
											filtrosConsulta.showMsg("Se requieren los datos obligatorios, El correo electrónico es incorrecto, favor de verificar");
										}
										else
										{
											if ("".equals(curpAeliminar)) {// Indica que es la primera ocasion que
												// se registra
												PuestoDTO rol = new PuestoDTO();
												rol.setCvePuesto(filtrosConsulta.getClavePuesto());
												DepartamentoDTO dep = new DepartamentoDTO();
												dep.setCveSsodepto(filtrosConsulta.getClaveDepartamento());
												AreaNormativaDTO an = new AreaNormativaDTO();
												
												an.setCveSsoareanorma(Long.parseLong(String.valueOf(filtrosConsulta.getClaveAreaNormativa())));
							
												solicitudMB.agregarClaves(an, dep, rol);
												
												solicitudMB.getSolicitudDTO().setAreaNorm(usuario.getAprobadorSession().getSolicitud().getAreaNorm());
												
												DepartamentoDTO depto = new DepartamentoDTO();
												depto.setCveSsodepto(filtrosConsulta.getClaveDepartamento());
												solicitudMB.getSolicitudDTO().setDptoDTO(depto);
												
												PuestoDTO puesto = new PuestoDTO();
												puesto.setCvePuesto(filtrosConsulta.getClavePuesto());
												solicitudMB.getSolicitudDTO().setPuestoDTO(puesto);
												
							
												if(cveAN>0)
												{
													if(filtrosConsulta.getClaveAreaNormativa()>0)
													{
														DelegacionDTO del = new DelegacionDTO();
														del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
														solicitudMB.getSolicitudDTO().setDelDTO(del);
														if(filtrosConsulta.getClaveSubdelegacion()>0)
														{
															SubdelegacionDTO subdel = new SubdelegacionDTO();
															subdel.setCveSubelegacion(filtrosConsulta.getClaveSubdelegacion());
															solicitudMB.getSolicitudDTO().setSubdelDTO(subdel);
															if(filtrosConsulta.getClaveUMF()>0)
															{
																UmfDTO umf = new UmfDTO();
																umf.setCveUmf(filtrosConsulta.getClaveUMF());
																solicitudMB.getSolicitudDTO().setUmfDTO(umf);
															}
														}
													}
												}

												solicitudMB.setCorreoElectronico(solicitudMB.getCorreoElectronico() + "@imss.gob.mx");
												solicitudMB.setTelefono(telefono);

												String respuestaRegistro = solicitudMB.registrarSolicitudCuentaNVer();
												if(respuestaRegistro.equals("inicializada"))
													respuestaRegistro = "inicializadaAdmon";
							
												bitacoraService.guardaSolicitudBit(new Long(solicitudMB.getIdSolicitud()), usuario.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_REGISTRO);
												
												filtrosConsulta.seteaDepto();
												
												filtrosConsulta.showMsg("La cuenta de usuario se ha registrado exitosamente, recuerde agregar Grupos y Módulos. Esta nueva cuenta quedará inactiva hasta su autorización.");
							
												registroUsuario = true;
												
												agregaaPerfilDefault();
							
												modifcarCombos = false;
												botonAdd = true;
												solicitudMB.setClaveAreaNormativa((int)filtrosConsulta.getClaveAreaNormativa());
											} else {
												solicitudMB.eliminaPerfilesLDAP(curpAeliminar);
												Long idSol = solicitudMB.obtenerIDSolicitud(curpAeliminar);
												solicitudMB.eliminaPerfilesModulosBD(idSol,solicitudMB.getSolicitudDTO());
												solicitudMB.actualizarSolicitudBD(idSol);
												// Roles
												for (PuestoDTO rolint : solicitudMB.getRolesData()) {
													solicitudMB.registrarPerfiles(idSol,
															(int) rolint.getCvePuesto());
												}
												// Modulos
												for (ModuloDTO modint : solicitudMB.getModulosData()) {
													solicitudMB.registrarModulos(idSol, (int) modint.getDptoDTO()
															.getCveSsodepto(), (int) modint.getCveIdModulo(), 1);
												}
							
												msgDesc = "USUARIO AGREGADO!!!!!";
												registroUsuario = false;
												botonAdd = true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PuestoDTO> getMultiRowFromTableOne() {
		return (ArrayList<PuestoDTO>) stateMapFromTableOne.getSelected();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ModuloDTO> getMultiRowFromTableTwo() {
		return (ArrayList<ModuloDTO>) stateMapFromTableTwo.getSelected();
	}

	// Es el manejador de eventos para el grid uno
	public void handleSelectUsuarioFromTableOne(SelectEvent se)
			throws AdmonUsuariosException {
		fieldToDeleteAreaNormativa = this.getMultiRowFromTableOne().get(0)
				.getCveArea();
		fieldToDeleteDepartamento = this.getMultiRowFromTableOne().get(0)
				.getCveDepartamento();
		fieldToDeletePuesto = (int) this.getMultiRowFromTableOne().get(0)
				.getCvePuesto();
	}

	// Es el manejador del grid dos
	public void handleSelectUsuarioFromTableTwo(SelectEvent se)
			throws AdmonUsuariosException {
		fieldToDeleteAreaNormativa = (int) this.getMultiRowFromTableTwo()
				.get(0).getAreaNormDTO().getCveSsoareanorma();
		fieldToDeleteDepartamento = (int) this.getMultiRowFromTableTwo().get(0)
				.getDptoDTO().getCveSsodepto();
		fieldToDeleteModulo = (int) this.getMultiRowFromTableTwo().get(0)
				.getCveIdModulo();
	}

	public void eliminaDeTablaUno() {
		boolean isDefault = DataTableData.consultaModuloDefault(
				(ArrayList) solicitudMB.getRolesData(),
				fieldToDeleteAreaNormativa, fieldToDeleteDepartamento,
				fieldToDeletePuesto);
		if (isDefault == true) {
			filtrosConsulta.showMsg("No se puede eliminar el grupo por default.");
			return;
		}

		solicitudMB.setRolesData(DataTableData.removeRoleFromTableOne(fieldToDeletePuesto, (ArrayList) solicitudMB.getRolesData()));
		try {
		
			solicitudMB.eliminarPerfilesSolicitudNVer(fieldToDeletePuesto);
			bitacoraService.guardaPuestoBit(new Long(solicitudMB.getIdSolicitud()), usuario.getAprobadorSession().getCveIdAprobador(), new Long(fieldToDeletePuesto), false);
			filtrosConsulta.showMsg("se elimino el grupo de la cuenta de usuario.");

		} catch (Exception e) {
			System.out.println("eliminaDeTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void eliminaDeTablaUnoEditar() {
		boolean isDefault = DataTableData.consultaModuloDefault(
				(ArrayList) solicitudMB.getRolesData(),
				fieldToDeleteAreaNormativa, fieldToDeleteDepartamento,
				fieldToDeletePuesto);

		if (isDefault == true) {
			filtrosConsulta.showMsg("NO SE PUEDE ELIMINAR EL PERFIL POR DEFAULT");
			return;
		}
		solicitudMB.setRolesData(DataTableData.removeRoleFromTableOne(
				fieldToDeletePuesto, (ArrayList) solicitudMB.getRolesData()));
	}

	public void eliminaDeTablaUnoAp() {
		solicitudMB.setRolesData(DataTableData.removeRoleFromTableOne(
				fieldToDeletePuesto, (ArrayList) solicitudMB.getRolesData()));
	}

	public void eliminaDeTablaDos() {
		solicitudMB.setModulosData(DataTableData.removeModuleFromTableTwo(
				fieldToDeleteModulo, (ArrayList) solicitudMB.getModulosData()));
		try {
			eliminarModulosSolicitudNVer(fieldToDeleteDepartamento,fieldToDeleteModulo);
			bitacoraService.guardaModulosBit(new Long(solicitudMB.getIdSolicitud()),usuario.getAprobadorSession().getCveIdAprobador(), new Long(fieldToDeleteModulo), false);
			filtrosConsulta.showMsg("se elimino el módulo de la cuenta de usuario.");

		} catch (Exception e) {
			System.out.println("eliminaDeTablaDos");
			e.printStackTrace();
			log.error("  ", e);
		}
	}
	
	public void eliminaDeTablaDosEditar() {
		solicitudMB.setModulosData(DataTableData.removeModuleFromTableTwo(
				fieldToDeleteModulo, (ArrayList) solicitudMB.getModulosData()));
	}

	public void eliminaDeTablaDosAp() {
		solicitudMB.setModulosData(DataTableData.removeModuleFromTableTwo(
				fieldToDeleteModulo, (ArrayList) solicitudMB.getModulosData()));
	}

	public String registrarPerfilesSolicitudNVer(int cvePuesto)
			throws AdmonUsuariosException {
		solicitudMB.registrarPerfiles(Long.parseLong(idSolicitud + ""),
				cvePuesto);
		return "inicializadaAdmon";
	}

	public String eliminarModulosSolicitudNVer(int cveDepartamento,
			int cveModulo) throws AdmonUsuariosException {
		solicitudMB.eliminarModulosSolicitudNVer(cveDepartamento, cveModulo);
		return "inicializadaAdmon";
	}

	public boolean validarEmail(String email) {
		return email.matches(EMAIL_PATTERN);
	}

	public boolean validarMatricula(String matricula) {
		boolean res = false;
		if (matricula == null || "".equals(matricula)) {
			return res;
		}
		else
		{
			res = matricula.matches(MATRICULA_PATTERN);

			if (res == false)
				res = matricula.matches(MATRICULA_PATTERN2);

			if (res == false)
				res = matricula.matches(MATRICULA_PATTERN3);
		}
		return res;
	}

//	public boolean validarEstructuraCurp(String curp) {
//		if (curp == null || "".equals(curp)) {
//			return false;
//		}
//		return  curp.matches(CURP_PATTERN);
//	}

	public void obtenerSolicitudByFiltro() throws AdmonUsuariosException{
		solMB.llenaFiltro();
		EstatusDTO s = new EstatusDTO();
		s.setCveSsoestatus(Constantes.ESTATUS.AUTORIZADO.getOpcion());
		solMB.getFiltro().setEstatusDTO(s);
		System.out.println("Invoca search solicitudes - RegistraSolicitudAdmonMB");
		solicitudes = solicitudCriteria.searchSolicitudes(solMB.getFiltro(), usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
	}

	

	public String getCurpAeliminar() {
		return curpAeliminar;
	}

	public void setCurpAeliminar(String curpAeliminar) {
		this.curpAeliminar = curpAeliminar;
	}


	public boolean isErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(boolean errorFlag) {
		this.errorFlag = errorFlag;
	}

	public boolean isInactiveEmployeeFlag() {
		return inactiveEmployeeFlag;
	}

	public void setInactiveEmployeeFlag(boolean inactiveEmployeeFlag) {
		this.inactiveEmployeeFlag = inactiveEmployeeFlag;
	}

	public int getFieldToDeleteAreaNormativa() {
		return fieldToDeleteAreaNormativa;
	}

	public void setFieldToDeleteAreaNormativa(int fieldToDeleteAreaNormativa) {
		this.fieldToDeleteAreaNormativa = fieldToDeleteAreaNormativa;
	}

	public int getFieldToDeleteDepartamento() {
		return fieldToDeleteDepartamento;
	}

	public void setFieldToDeleteDepartamento(int fieldToDeleteDepartamento) {
		this.fieldToDeleteDepartamento = fieldToDeleteDepartamento;
	}

	public int getFieldToDeletePuesto() {
		return fieldToDeletePuesto;
	}

	public void setFieldToDeletePuesto(int fieldToDeletePuesto) {
		this.fieldToDeletePuesto = fieldToDeletePuesto;
	}

	public int getFieldToDeleteModulo() {
		return fieldToDeleteModulo;
	}

	public void setFieldToDeleteModulo(int fieldToDeleteModulo) {
		this.fieldToDeleteModulo = fieldToDeleteModulo;
	}

	public RowStateMap getStateMapFromTableOne() {
		return stateMapFromTableOne;
	}

	public void setStateMapFromTableOne(RowStateMap stateMapFromTableOne) {
		this.stateMapFromTableOne = stateMapFromTableOne;
	}

	public RowStateMap getStateMapFromTableTwo() {
		return stateMapFromTableTwo;
	}

	public void setStateMapFromTableTwo(RowStateMap stateMapFromTableTwo) {
		this.stateMapFromTableTwo = stateMapFromTableTwo;
	}





	public void agregaaTablaUnoPrevio() {
		agregaaTablaUno("No");
	}

	public void agregaaTablaUno(String defaultRol) {
		int areanormativa = (int)filtrosConsulta.getClaveAreaNormativa();
		int deptoPuesto = (int) filtrosConsulta.getClaveDepartamentoRolAdd();
		int puesto = (int) filtrosConsulta.getClaveRolAdd();
		
		if (registroUsuario == false) { 
			filtrosConsulta.showMsg("NO PUEDE AGREGAR ROLES HASTA QUE REGISTRE AL USUARIO");
			return;
		} else {
			errorRoles = "";
		}
		if (puesto == -99) {
			filtrosConsulta.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
			return;
		}

		boolean res = DataTableData.validaPuestoExistente((ArrayList) solicitudMB.getRolesData(), areanormativa,deptoPuesto, puesto);
		try {
			if (res == false) {
				solicitudMB.setRolesData(DataTableData.addRoletoTableOne((ArrayList) solicitudMB.getRolesData(),areanormativa, 
																			solicitudMB.buscaAreaNormativa(areanormativa),
																			deptoPuesto, solicitudMB.buscaDepartamento(areanormativa, deptoPuesto),
																			puesto, solicitudMB.buscaPuesto(deptoPuesto,puesto), defaultRol));
				solicitudMB.registrarPerfilesSolicitudNVer(puesto,defaultRol);
				bitacoraService.guardaPuestoBit(new Long(solicitudMB.getIdSolicitud()), usuario.getAprobadorSession().getCveIdAprobador(), filtrosConsulta.getClaveRolAdd(), true);
				filtrosConsulta.showMsg("El grupo seleccionado se agrego a la cuenta de usuario.");
			}
			else
			{
				filtrosConsulta.showMsg("El grupo seleccionado ya existe relacionado a la cuenta de usuario.");
			}
		} catch (Exception e) {
			System.out.println("agregaaTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void agregaaPerfilDefault() {
		int areanormativa = (int)filtrosConsulta.getClaveAreaNormativa();
		int deptoPuesto = (int) filtrosConsulta.getClaveDepartamentoRolAdd();
		int puesto = (int) filtrosConsulta.getClavePuesto();
		
		try {
				solicitudMB.setRolesData(DataTableData.addRoletoTableOne((ArrayList) solicitudMB.getRolesData(),areanormativa, 
																			solicitudMB.buscaAreaNormativa(areanormativa),
																			deptoPuesto, solicitudMB.buscaDepartamento(areanormativa, deptoPuesto),
																			puesto, solicitudMB.buscaPuesto(deptoPuesto,puesto), "Si"));
				solicitudMB.registrarPerfilesSolicitudNVer(puesto,"Si");
		} catch (Exception e) {
			System.out.println("agregaaTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}


	public void agregaaTablaUnoAp() {
		try {

			int areanormativa = (int)filtrosConsulta.getClaveAreaNormativa();
			int deptoPuesto = (int) filtrosConsulta.getClaveDepartamentoRolAdd();
			int puesto = (int) filtrosConsulta.getClaveRolAdd();

			solicitudMB.setRolesData(DataTableData.addRoletoTableOne(
					(ArrayList) solicitudMB.getRolesData(), areanormativa,
					solicitudMB.buscaAreaNormativa(areanormativa),
					deptoPuesto, solicitudMB.buscaDepartamento(
							areanormativa, deptoPuesto),
					puesto, solicitudMB.buscaPuesto(deptoPuesto,
							puesto), "Si"));
			solicitudMB.setClaveAreaNormativa(areanormativa);
		} catch (Exception e) {
			System.out.println("agregaaTablaUnoAp ");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void agregaaTablaDos() {
		if (registroUsuario == false) {
			filtrosConsulta.showMsg("NO PUEDE AGREGAR MODULOS HASTA QUE REGISTRE AL USUARIO");
			return;
		}

		int areanormativa = (int)filtrosConsulta.getClaveAreaNormativa();
		int deptoModulo = (int) filtrosConsulta.getClaveDepartamentoModAdd();
		int moludo = (int) filtrosConsulta.getClaveModAdd();

		try {
			if (moludo == -99) {
				filtrosConsulta.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
				return;
			}

			// Validar si existe en el registro actual
			boolean res = DataTableData.validaModuloExistente(
					(ArrayList) solicitudMB.getModulosData(),
					areanormativa, deptoModulo, moludo);
			if (res == false) {
				solicitudMB.setModulosData(DataTableData.addRoletoTableTwoN(
						(ArrayList<ModuloDTO>) solicitudMB.getModulosData(),
						solicitudMB.buscaAreaNormativaObj(areanormativa),
						solicitudMB.buscaDepartamentoObj(areanormativa,
								deptoModulo), moludo, solicitudMB
								.buscaModulo(deptoModulo, moludo)));
				solicitudMB.registrarModulosSolicitudNVer(deptoModulo,moludo);
				bitacoraService.guardaModulosBit(new Long(solicitudMB.getIdSolicitud()),usuario.getAprobadorSession().getCveIdAprobador(), filtrosConsulta.getClaveModAdd(), true);
				filtrosConsulta.showMsg("El móludo se agrego a la cuenta de usuario.");
			}
			else
			{
				filtrosConsulta.showMsg("El móludo seleccionado ya existe relacionado a la cuenta de usuario.");
			}
			solicitudMB.setClaveAreaNormativa(areanormativa);
		} catch (Exception e) {
			System.out.println("agregaaTablaDos ");
			e.printStackTrace();
			log.error("  ", e);
		}
	}
	
	





	public String registraSolicitudInicialCambio()throws AdmonUsuariosException {
		solicitudMB.setCurp("");
		solicitudMB.setNombre("");
		solicitudMB.setNombrePaterno("");
		solicitudMB.setNombreMaterno("");
		solicitudMB.setMatricula("");
		solicitudMB.setCorreoElectronico("");
		solicitudMB.setTelefono("");
		solicitudMB.setClaveDelegacion(-99);
		solicitudMB.setClaveSubDelegacion(-99);
		solicitudMB.setClaveUMF(-99);
		CargaTablaUno();
		CargaTablaDos();
		solicitudMB.setCurpNueva("");
		return "registraSolicitudCambio";
	}

	public String bajaDefinitivaUsuario() {
		filtrosConsulta.obtieneDatosUsuario();
		solicitudMB.setClaveAreaNormativa((int)usuario.getAreaNormativa());
		solicitudMB.setClaveDepartamento((int)usuario.getAprobadorSession().getSolicitud().getDepartamentoId());
		solicitudMB.setClavePuesto((int)usuario.getAprobadorSession().getSolicitud().getPuestoId());
		solicitudMB.setClaveDelegacion((int)usuario.getAprobadorSession().getSolicitud().getDelegacionId()); 
		solicitudMB.setCveDeleg((int)usuario.getAprobadorSession().getSolicitud().getDelegacionId());
		solicitudMB.setClaveSubDelegacion((int)usuario.getAprobadorSession().getSolicitud().getSubdelegacionId());
		solicitudMB.setClaveUMF((int)usuario.getAprobadorSession().getSolicitud().getUmfId());
		solicitudMB.setFlagEliminacion(true);
		try {
			consultarSolicitudes();
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
		
		return "bajaDefinitivaUsuario";
	}


	public void consultarSolicitudes() throws AdmonUsuariosException {
		solMB.llenaFiltro();
//		solicitudes = solicitudCriteria.solicitudesByFiltro(filtro, new Long(2), true, usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
		System.out.println("Invoca search solicitudes - RegistraSolicitudAdmonMB");
		solicitudes = solicitudCriteria.searchSolicitudes(solMB.getFiltro(), usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
	}
	
	
    public void selecionaSolicitud(SelectEvent event) {
    	solSelect = (SolicitudDTO)event.getObject();
    }
	
	public void bajaDeUsuario(ActionEvent actionEvent) {
		if(solSelect!=null)
		{
			try {
				
				solicitudes.clear();
				solicitudMB.desactivarUsuario(solSelect.getDesUsrCurp());
			} catch (Exception e) {
				System.out.println("No fue posible desactivar el usuario en LDAP");
				log.error("  ", e);
			}
			try {
				solicitudMB.eliminarUsuario(solSelect.getDesUsrCurp(),4);
			} catch (Exception e1) {
				System.out.println("No fue posible actualizar la solicitud");
				log.error("  ", e1);
			}
			try {
				consultarSolicitudes();
			} catch (AdmonUsuariosException e) {
				System.out.println("error al actualizar la lista de solicitudes");
				e.printStackTrace();
			}
		}
	}

	public void bajaDeUsuarioCambioCurp(ActionEvent actionEvent) {
		if(solSelect!=null)
		{
			try {
				solicitudes.clear();
				solicitudMB.desactivarUsuario(solSelect.getDesUsrCurp());
			} catch (Exception e) {
				System.out.println("No fue posible desactivar el usuario en LDAP");
				log.error("  ", e);
			}
			try {
				solicitudMB.eliminarUsuario(solSelect.getDesUsrCurp(), 6);
			} catch (Exception e1) {
				System.out.println("No fue posible actualizar la solicitud");
				log.error("  ", e1);
			}
			try {
				consultarSolicitudes();
			} catch (AdmonUsuariosException e) {
				System.out.println("error al actualizar la lista de solicitudes");
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<UsuarioDTO> getMultiRow() {
		return (ArrayList<UsuarioDTO>) stateMap.getSelected();
	}

	public boolean isDblClick() {
		return dblClick;
	}

	public void setDblClick(boolean dblClick) {
		this.dblClick = dblClick;
	}

	public boolean isInstantUpdate() {
		return instantUpdate;
	}

	public void setInstantUpdate(boolean instantUpdate) {
		this.instantUpdate = instantUpdate;
	}

	public String getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}

	public Collection<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public HtmlDataTable getTabla() {
		return tabla;
	}

	public void setTabla(HtmlDataTable tabla) {
		this.tabla = tabla;
	}

	public RowStateMap getStateMap() {
		return stateMap;
	}

	public void setStateMap(RowStateMap stateMap) {
		this.stateMap = stateMap;
	}


	public UsuarioMB getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioMB usuario) {
		this.usuario = usuario;
	}

	public CatalogosMB getCatalogoMB() {
		return catalogoMB;
	}

	public void setCatalogoMB(CatalogosMB catalogoMB) {
		this.catalogoMB = catalogoMB;
	}

	public boolean isUsuarioInterno() {
		return usuarioInterno;
	}

	public void setUsuarioInterno(boolean usuarioInterno) {
		this.usuarioInterno = usuarioInterno;
	}

	public int getCveAreaNorm() {
		return cveAreaNorm;
	}

	public void setCveAreaNorm(int cveAreaNorm) {
		this.cveAreaNorm = cveAreaNorm;
	}

	public boolean isCombos() {
		return combos;
	}

	public void setCombos(boolean combos) {
		this.combos = combos;
	}

	public List<SolicitudDTO> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudDTO> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public SolicitudServiceLocal getSolicitudCriteria() {
		return solicitudCriteria;
	}

	public void setSolicitudCriteria(SolicitudServiceLocal solicitudCriteria) {
		this.solicitudCriteria = solicitudCriteria;
	}
	public ConsultaGenericaControllerAdmon getFiltrosConsulta() {
		return filtrosConsulta;
	}
	public void setFiltrosConsulta(ConsultaGenericaControllerAdmon filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}



	public boolean isBotonAdd() {
		return botonAdd;
	}

	public void setBotonAdd(boolean botonAdd) {
		this.botonAdd = botonAdd;
	}



	public SolicitudMB getSolMB() {
		return solMB;
	}



	public void setSolMB(SolicitudMB solMB) {
		this.solMB = solMB;
	}

	public boolean isSinmodifcar() {
		return sinmodifcar;
	}



	public void setSinmodifcar(boolean sinmodifcar) {
		this.sinmodifcar = sinmodifcar;
	}



	public boolean isModificar() {
		return modificar;
	}



	public void setModifcar(boolean modificar) {
		this.modificar = modificar;
	}



	public boolean isModifcarCombos() {
		return modifcarCombos;
	}



	public void setModifcarCombos(boolean modifcarCombos) {
		this.modifcarCombos = modifcarCombos;
	}




	public String getCurp() {
		return curp;
	}




	public void setCurp(String curp) {
		this.curp = curp;
	}




	public String getTelefono() {
		return telefono;
	}




	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}




	public String getCorreo() {
		return correo;
	}




	public void setCorreo(String correo) {
		this.correo = correo;
	}




	public boolean isFlagFirstSlide() {
		return flagFirstSlide;
	}




	public void setFlagFirstSlide(boolean flagFirstSlide) {
		this.flagFirstSlide = flagFirstSlide;
	}


	
}
