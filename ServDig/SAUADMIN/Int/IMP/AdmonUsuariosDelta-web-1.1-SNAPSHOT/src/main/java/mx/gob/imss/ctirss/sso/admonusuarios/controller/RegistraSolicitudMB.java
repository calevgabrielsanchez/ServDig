package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAprobador;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.SolicitudMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.implementacion.ClienteWebserviceCurp;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;
import mx.gob.imss.ctirss.sso.util.DataTableData;
import mx.gob.imss.ctirss.sso.util.ValidationUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.model.table.RowStateMap;

@ManagedBean(name = "registraSolicitudMB")
@CustomScoped("#{window}")
public class RegistraSolicitudMB {

	@ManagedProperty("#{solicituddMB}")
	private SolicituddMB solicitudMB;

	@ManagedProperty("#{solicitudMB}")
	private SolicitudMB solMB;

	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuario;

	@ManagedProperty(value = "#{catalogoMB}")
	private CatalogosMB catalogoMB;

	@ManagedProperty(value = "#{consultaGenerica}")
	private ConsultaGenericaController filtrosConsulta;

	@EJB
	private SolicitudServiceLocal solicitudCriteria;
	@EJB
	private MensajeriaSessionLocal mensajeriaService;
	@EJB
	private BitacoraServiceLocal bitacoraService;
	@EJB
	private AdmonUsuariosSessionLocal admonUsuarios;
	@EJB
	private AprobadoresServiceLocal aprobadoresService;

	private static Log log = LogFactory.getLog(RegistraSolicitudMB.class);

	private boolean registroUsuario = false;
	private Long idSolicitud = 0L;

	private int claveAreaNormativa;
	private int claveDepartamento;
	private int clavePuesto;
	private int claveModulo;
	private int claveDepartamentoSub;
	private int clavePuestoSub;
	private int claveAreaNormativaSub;

	private int defaultDelegationValue;
	private int defaultSubDelegationValue;
	private int defaultUmfValue;

	private boolean flag4;
	private boolean flag3;
	private boolean flag2;
	private boolean flag1;
	private boolean flagFirstSlide = false;
	private boolean flagSecondSlide;
	private boolean flagThirdSlide;
	private boolean flagLoad;
	private boolean flagCambiaCurp;

	private boolean msg = false;
	private String msgDesc = "";

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
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMF = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>();
	private List<SelectItem> lstDelegacion = new ArrayList<SelectItem>();
	private boolean dblClick = false;
	private boolean instantUpdate = true;
	private Collection<UsuarioDTO> usuarios;
	private List<SolicitudDTO> solicitudes;

	private int claveDelegacion;
	private int claveUMF;
	private int claveSubdelegacion;
	private int claveSubDelegacion;

	private SolicitudDTO solSelect;

	private String curp = "";
	private String telefono = "";
	private String correo = "";
	
	private String curpValidado;
	private String curpAeliminar;

	private HtmlDataTable tabla; /*
								 * new SelectItem("multiplecell",
								 * "Multiple Cell")
								 */
	private boolean mist = false;

	private String selectionMode = AVAILABLE_MODES[0].getValue().toString();
	private static final SelectItem[] AVAILABLE_MODES = {
			new SelectItem("single", "Single Row"),
			new SelectItem("multiple", "Multiple Rows"),
			new SelectItem("singlecell", "Single Cell") };

	private RowStateMap stateMap = new RowStateMap();

	//private static String CURP_PATTERN = "([A-Z-a-z]{4})([0-9]{6})([A-Z-a-z]{6})([A-Z-a-z-0-9]{1})([0-9]{1})";
	private static String MATRICULA_PATTERN = "([0-9]{8})";
	private static String MATRICULA_PATTERN2 = "([0-9]{9})";
	private static String MATRICULA_PATTERN3 = "([0-9]{7})";
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static int ESTATUS_BAJA_SOLICITUD = 4;
	private static int ESTATUS_BAJA_CURP_SOLICITUD = 6;

	public void init() throws AdmonUsuariosException {
		try {
			solicitudes = new ArrayList<SolicitudDTO>();
			obtieneDatosUsuario();
			cargaAreaNormativaNCat();
			cargaDelegacionNCat();
			CargaTablaUno();
			CargaTablaDos();
		} catch (Exception e) {
			System.out.println("Error RegistraSolicitudMB");
			e.printStackTrace();
			log.error("Error RegistraSolicitudMB ", e);
		}
	}

	public void obtieneDatosUsuario() {
		try {
			solicitudMB.setClaveAreaNormativa((int) usuario.getAreaNormativa());

			if (usuario.getAprobadorSession().getSolicitud().getDelDTO() != null)
				claveDelegacion = (int) usuario.getAprobadorSession()
						.getSolicitud().getDelDTO().getCveDelegacion();
			else
				claveDelegacion = 0;
			if (usuario.getAprobadorSession().getSolicitud().getSubdelDTO() != null)
				claveSubdelegacion = (int) usuario.getAprobadorSession()
						.getSolicitud().getSubdelDTO().getCveSubelegacion();
			else
				claveSubdelegacion = 0;
			if (usuario.getAprobadorSession().getSolicitud().getDptoDTO() != null)
				claveDepartamento = (int) usuario.getAprobadorSession()
						.getSolicitud().getDptoDTO().getCveSsodepto();
			else
				claveDepartamento = 0;
			if (usuario.getAprobadorSession().getSolicitud().getPuestoDTO() != null)
				clavePuesto = (int) usuario.getAprobadorSession()
						.getSolicitud().getPuestoDTO().getCvePuesto();
			else
				clavePuesto = 0;
			cargaSubdelegacionNCat(new Long(claveDelegacion));
			cargaDepartamentoCat(new Long(claveAreaNormativa));
			cargaPuestoCat(new Long(claveDepartamento));
			cargaUmfCat(new Long(claveSubdelegacion));
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}

	}

	public void cargaSubdelegacionNCat(Long idDelegacion)
			throws AdmonUsuariosException {
		this.lstSubDelegacion.clear();
		List<SubdelegacionDTO> lista = catalogoMB
				.cargaSubdelegacionNCat(idDelegacion);
		for (SubdelegacionDTO dl : lista) {
			this.lstSubDelegacion.add(new SelectItem(dl.getCveSubelegacion(),
					dl.getNombreSubelegacion()));
		}
	}

	public void cargaDepartamentoCat(Long idArea) throws AdmonUsuariosException {
		this.lstDepartamento.clear();
		List<DepartamentoDTO> lista = catalogoMB.cargaDepartamentoCat(idArea);
		for (DepartamentoDTO dl : lista) {
			this.lstDepartamento.add(new SelectItem(dl.getCveSsodepto(), dl
					.getDesDepartamento()));
		}
	}

	public void cargaPuestoCat(Long idDepto) throws AdmonUsuariosException {
		this.lstPuesto.clear();
		List<PuestoDTO> lista = catalogoMB.cargaPuestoCat(idDepto);
		for (PuestoDTO dl : lista) {
			this.lstPuesto.add(new SelectItem(dl.getCvePuesto(), dl
					.getNombrePuesto()));
		}
	}

	public void cargaUmfCat(Long idSubdeleg) throws AdmonUsuariosException {
		this.lstUMF.clear();
		List<UmfDTO> lista = catalogoMB.cargaUfmCat(idSubdeleg);
		for (UmfDTO dl : lista) {
			this.lstUMF.add(new SelectItem(dl.getCveUmf(), dl.getNombreUmf()));
		}
	}

	public RegistraSolicitudMB() {
	}

	public String inicializaSolicitud() {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		curp = "";
		clavePuesto = -99;
		claveDepartamento = -99;
		claveAreaNormativaSub = -99;
		claveDepartamentoSub = -99;
		clavePuestoSub = -99;
		claveModulo = -99;
		registroUsuario = false;
		idSolicitud = 0L;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		flag4 = false;
		flagFirstSlide = false;
		flagSecondSlide = true;
		flagThirdSlide = true;
		claveAreaNormativa = -99;
		claveDepartamento = -99;
		clavePuesto = -99;
		claveModulo = -99;
		CargaTablaUno();
		CargaTablaDos();
		msg = false;
		msgDesc = "";
		filtrosConsulta.obtieneDatosUsuario();
		return "inicializada";
	}

	public void inicializaSolicitud2() {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		curp = "";
		clavePuesto = -99;
		claveDepartamento = -99;
		claveAreaNormativaSub = -99;
		claveDepartamentoSub = -99;
		clavePuestoSub = -99;
		claveModulo = -99;
		registroUsuario = false;
		idSolicitud = 0L;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		flag4 = false;
		flagFirstSlide = false;
		flagSecondSlide = true;
		flagThirdSlide = true;
		claveAreaNormativa = -99;
		claveDepartamento = -99;
		clavePuesto = -99;
		claveModulo = -99;
		CargaTablaUno();
		CargaTablaDos();
		msg = false;
		msgDesc = "";
		filtrosConsulta.obtieneDatosUsuario();
		correo = "";
		telefono = "";
		curp = "";

	}

	public void inicializaSolicitud2(AjaxBehaviorEvent e) {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		curp = "";
		clavePuesto = -99;
		claveDepartamento = -99;
		claveAreaNormativaSub = -99;
		claveDepartamentoSub = -99;
		clavePuestoSub = -99;
		claveModulo = -99;
		registroUsuario = false;
		idSolicitud = 0L;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		flag4 = false;
		flagFirstSlide = false;
		flagSecondSlide = true;
		flagThirdSlide = true;
		claveAreaNormativa = -99;
		claveDepartamento = -99;
		clavePuesto = -99;
		claveModulo = -99;
		CargaTablaUno();
		CargaTablaDos();
		msg = false;
		msgDesc = "";
		filtrosConsulta.obtieneDatosUsuario();
		correo = "";
		telefono = "";
		curp = "";

	}

	public void limpia() {
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitudMB.eliminaValoresDefault();
		clavePuesto = -99;
		claveDepartamento = -99;
		claveAreaNormativaSub = -99;
		claveDepartamentoSub = -99;
		clavePuestoSub = -99;
		claveModulo = -99;
		registroUsuario = false;
		idSolicitud = 0L;
		defaultDelegationValue = -99;
		defaultSubDelegationValue = -99;
		defaultUmfValue = -99;
		flag4 = false;
		flagFirstSlide = false;
		flagSecondSlide = true;
		flagThirdSlide = true;
		claveAreaNormativa = -99;
		claveDepartamento = -99;
		clavePuesto = -99;
		claveModulo = -99;
		CargaTablaUno();
		CargaTablaDos();
		msg = false;
		msgDesc = "";
		curpValidado = "";
		filtrosConsulta.obtieneDatosUsuario();
		solicitudMB.setMatricula("");
		solicitudMB.setNssNom("");
		solicitudMB.setPuestoDescNom("");
		solicitudMB.setDepartamentoDescNom("");
		solicitudMB.setCveDelegacionNom("");
		solicitudMB.setCveSubdelegacionNom("");
		solicitudMB.setCveUmfNom("");
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

	public int getClaveAreaNormativa() {
		return claveAreaNormativa;
	}

	public void setClaveAreaNormativa(int claveAreaNormativa) {
		this.claveAreaNormativa = claveAreaNormativa;
	}

	public int getClaveDepartamento() {
		return claveDepartamento;
	}

	public void setClaveDepartamento(int claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}

	public int getClavePuesto() {
		return clavePuesto;
	}

	public void setClavePuesto(int clavePuesto) {
		this.clavePuesto = clavePuesto;
	}

	public int getClaveModulo() {
		return claveModulo;
	}

	public void setClaveModulo(int claveModulo) {
		this.claveModulo = claveModulo;
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

	public boolean isFlag4() {
		return flag4;
	}

	public void setFlag4(boolean flag4) {
		this.flag4 = flag4;
	}

	public boolean isFlagFirstSlide() {
		return flagFirstSlide;
	}

	public void setFlagFirstSlide(boolean flagFirstSlide) {
		this.flagFirstSlide = flagFirstSlide;
	}

	public boolean isFlagSecondSlide() {
		return flagSecondSlide;
	}

	public void setFlagSecondSlide(boolean flagSecondSlide) {
		this.flagSecondSlide = flagSecondSlide;
	}

	public boolean isFlagThirdSlide() {
		return flagThirdSlide;
	}

	public void setFlagThirdSlide(boolean flagThirdSlide) {
		this.flagThirdSlide = flagThirdSlide;
	}

	public void validaryCargarDatos2() {
		log.info("##### FIX CUPR | JMGD | 08/07/2026 #####");
		limpia();
		String respuesta = "";
		String matricula = "";
		flagFirstSlide = true;
		try {
			Locale defloc = Locale.getDefault();
			solicitudMB.setCurp(curp.toUpperCase(defloc));
			//if (validarEstructuraCurp(solicitudMB.getCurp()) == false) {
			if(!ValidationUtils.isValidCurp(solicitudMB.getCurp())){
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
				
				System.out.println("##### Voy a validar la CURP [ " + solicitudMB.getCurp() + " ] #####");
				System.out.println("#####\nOrden de validación\n1.-SIAP\n2.-TTDS\n#####");
				
				UsuarioNominaResponse unr = bitacoraService.datosSIAP(solicitudMB.getCurp(), matricula);
				
				if(unr != null) {
					System.out.println("##### Entre a CURP en SIAP #####");
					estatusSIAP = Long.parseLong(unr.getEstatus());
					empKeyProSIAP = Integer.parseInt(unr.getTipoContratacion());
					estatusAlta = estatusSIAP;
					empKeyPro = empKeyProSIAP;
				}else {
					System.out.println("##### Entre a CURP en TTDS #####");
					UsuarioTTD ttd = bitacoraService.datosTTD(solicitudMB.getCurp(), matricula);
					if(ttd != null) {
						System.out.println("##### Si existo en TTDS #####");
						estatusAlta = Long.parseLong(ttd.getEstatus());
						empKeyPro = Integer.parseInt(ttd.getTipoContratacion());
					}else {
						System.out.println("##### No existo en SIAP ni en TTDS #####");
						estatusAlta = 0L;
						empKeyPro = 0;
					}
				}				
											
				System.out.println("*** Voy a validar la EMP_KEYPRO [ " + empKeyPro + " ] y el ESTATUS [ " + estatusAlta + " ] en registro SolicitudMB ***");
								
				if(!(empKeyPro < 10) || !(estatusAlta.equals(1L))) {
					String msgDesc = "No se puede dar de alta un usuario jubilado o pensionado y debe estar activo en SIAP.";
					filtrosConsulta.showMsg(msgDesc);
				}else if(respuesta.equals(curps)) {
					String msgDesc = "El Usuario aprobador no puede registrarse a el mismo.";
					filtrosConsulta.showMsg(msgDesc);
				}else if (!"".equals(respuesta)) 
				{
					curpAeliminar = respuesta;
					errorFlag = true;
					String resultCurp = "La CURP ya se encuentra registrada y el estatus de la solicitud es: ";
					try {
						String resEstatus = solicitudMB
								.buscaEstatusSolicitud(solicitudMB.getCurp());
						resultCurp = resultCurp + resEstatus + ". ";
						if ("BAJA".equals(resEstatus)
								|| "CAMBIO AREA ADSCRIPCION".equals(resEstatus))
							resultCurp = resultCurp
									+ " Probablemente requiera reactivación de la cuenta";
					} catch (Exception e) {
					}

					botonAdd = true;
					filtrosConsulta.showMsg(resultCurp);
				} else {
					log.info("---> Voy a validar datos en LDAP <---");
					if (validaUsuarioLdap(solicitudMB.getCurp())) {
						log.info("##### ValidaUsuarioLDAP devolvio [ true ] #####");
						log.info("---> Voy a validar datos usuario y matricula IMSS <---");
						if (cargaDatosUsuario(solicitudMB.getCurp()) && cargaMatriculaImss(solicitudMB.getCurp())) {
							log.info("##### CargaDatosUsuario devolvio [ true ] #####");
							log.info("##### CargaMatriculaIMSS devolvio [ true ] #####");
							errorFlag = true;
							curpAeliminar = "";
							flagFirstSlide = false;
							botonAdd = false;
							curpValidado = curp;
							registroUsuario = false;
							filtrosConsulta.showMsg("Se verifico correctamente la información relacionada al CURP");
						} else {
							botonAdd = true;
							filtrosConsulta.showMsg("El CURP esta inactivo en SIAP");
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al validar CURP usuario en la base de datos");
		}

	}

	public String validaryCargarDatos() {
		limpia();
		String respuesta = "";
		flagFirstSlide = true;
		try {
			Locale defloc = Locale.getDefault();
			solicitudMB.setCurp(curp.toUpperCase(defloc));
			//if (validarEstructuraCurp(solicitudMB.getCurp()) == false) {
			if(!ValidationUtils.isValidCurp(solicitudMB.getCurp())){
				return "inicializada";
			}
			respuesta = solicitudMB.buscaCurpExistente(solicitudMB.getCurp());
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Error al validar CURP usuario en la base de datos");
		}

		if (!"".equals(respuesta)) {
			curpAeliminar = respuesta;
			errorFlag = true;
			String resultCurp = "La CURP ya se encuentra registrada y el estatus de la solicitud es: ";
			try {
				String resEstatus = solicitudMB
						.buscaEstatusSolicitud(solicitudMB.getCurp());
				resultCurp = resultCurp + resEstatus + ". ";
				if ("BAJA".equals(resEstatus)
						|| "CAMBIO AREA ADSCRIPCION".equals(resEstatus))
					resultCurp = resultCurp
							+ " Probablemente requiera reactivación de la cuenta";
			} catch (Exception e) {
			}

			botonAdd = true;
			filtrosConsulta.showMsg(resultCurp);
		} else {
			if (validaUsuarioLdap(solicitudMB.getCurp())) {
				if (cargaDatosUsuario(solicitudMB.getCurp())
						&& cargaMatriculaImss(solicitudMB.getCurp())) {
					errorFlag = true;
					curpAeliminar = "";
					flagFirstSlide = false;
					botonAdd = false;
					curpValidado = curp;
					registroUsuario = false;
				} else {
					botonAdd = true;
				}
			}

		}
		return "consultada";
	}

	public void sleep() {
		try {
			Thread.currentThread().sleep(2500);
		} catch (Exception e) {
		}
	}

	public String validaryCargarDatosCambioCurp() {
		String respuesta = "";
		curpAeliminar = "";
		usuarioInterno = false;
		try {
			Locale defloc = Locale.getDefault();
			solicitudMB.setCurp(curp.toUpperCase(defloc));
			//if (validarEstructuraCurp(solicitudMB.getCurp()) == false) {
			if(!ValidationUtils.isValidCurp(solicitudMB.getCurp())){
				return "inicializada";
			}

			curpAeliminar = solicitudMB.getCurp();
			respuesta = solicitudMB.buscaCurpExistente(solicitudMB.getCurp());
			solicitudMB.setRolesData(obtenRolesLDAP());

			if (!"".equals(respuesta))
				usuarioInterno = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("".equals(solicitudMB.getCurp())) {
			errorFlag = true;
			filtrosConsulta.showMsg("NO SE ENCONTRO AL USUARIO");
			return "registraSolicitudCambio";
		} else {
			errorFlag = true;
		}
		solicitudMB.setTelefono("987654321");
		if (usuarioInterno == true)
			cargaMatriculaImss(solicitudMB.getCurp());

		else
			solicitudMB.setMatricula("1234567");
		flagCambiaCurp = false;
		return "registraSolicitudCambio";
	}

	public List<PuestoDTO> obtenRolesLDAP() {
		try {
			List<String> listaRoles = solicitudMB
					.consultaRolesPerfilesLDAP(solicitudMB.getCurp()); // this.admonsPerfilesService
			List<PuestoDTO> listaRolesRespuesta = new ArrayList<PuestoDTO>();

			for (String cadena : listaRoles) {
				if (cadena.indexOf(": ") > 0)
					cadena = cadena.substring(cadena.indexOf(": ") + 2,
							cadena.length());
				try {
					// PuestoDTO existeCatalogo =
					// solicitudMB.consultaPerfilBD(cadena); //catalogService
					// listaRolesRespuesta.add(existeCatalogo);
				} catch (Exception e) {
				}
			}
			return listaRolesRespuesta;
		} catch (Exception e) {
			System.out.println("obtenRolesLDAP");
			e.printStackTrace();
			log.error("  ", e);
		}
		return null;
	}

	public boolean cargaDatosUsuario(String curp) {
		try {
			log.info("##### Entrando al try de RENAPO #####");
			ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
			log.info("##### Consultando CURP [" + curp + " ] en RENAPO #####");
			Fisica fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo(curp);
			log.info("##### Ya termine la consulta de la CURP [" + curp + " ] en RENAPO #####");
			log.info("$$$$$ Fisica viene [ " + fisica + " ] $$$$$");
			log.info("$$$$$ Fisica dato prueba [ " + fisica.getNombre() + " ] $$$$$");
			log.info("---> Si encontre info <---");
			solicitudMB.setNombre(fisica.getNombre());
			solicitudMB.setNombrePaterno(fisica.getPrimerApellido());
			solicitudMB.setNombreMaterno(fisica.getSegundoApellido());
			return true;
		}

		catch (ClienteWebserviceRenapoCurpException wsr) {
			log.info("***** Entre al primer catch *****");
			filtrosConsulta.showMsg("El servicio Web de RENAPO no esta disponible intente mas tarde");
			
			flagLoad = true;
			solicitudMB.setNombre("");
			solicitudMB.setNombrePaterno("");
			solicitudMB.setNombreMaterno("");
			System.out.println("Error::cargaDatosUsuario "
					+ wsr.getStackTrace());
			log.error("  ", wsr);
			return false;
		}

		catch (Exception e) {
			log.info("##### Entre al segundo catch #####");
			filtrosConsulta.showMsg("La CURP no se encontró en el servicio Web RENAPO");
			
			flagLoad = true;
			solicitudMB.setNombre("");
			solicitudMB.setNombrePaterno("");
			solicitudMB.setNombreMaterno("");
			System.out.println("Error::cargaDatosUsuario " + e.getStackTrace());
			log.error("  ", e);
			return false;
		}
	}

	public boolean validaUsuarioLdap(String curp) {
		log.info("##### Voy a validar en LDAP la CURP [ " + curp + " ] #####");
		try {
			if (admonUsuarios.obtenUsuario(curp) != null) {
				filtrosConsulta
						.showMsg("El CURP ya se encuentra registrado con un perfil de usuario externo");
				return false;
			} else {
				return true;
			}
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
			System.out.println("Error al validar el usuario en ldap");
			filtrosConsulta.showMsg("Error al validar usuario existente");
			return false;
		}
	}

	public boolean cargaMatriculaImss(String curp) {
		try {
			ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
			UsuarioNominaResponse usImss = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(curp, "");
			if (usImss != null) {
				log.info("###### Voy a validar la CURP [ " + curp + " ] en SIAP ######");
				solicitudMB.setMatricula(usImss.getMatricula());
				solicitudMB.setNssNom(usImss.getNss());
				solicitudMB.setPuestoDescNom(usImss.getPuestoDesc());
				solicitudMB
						.setDepartamentoDescNom(usImss.getDepartamentoDesc());
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
				log.info("###### Voy a validar la CURP [ " + curp + " ] en TTDS ######");
				ClienteConsultaCurpTTDS ccct = new ClienteConsultaCurpTTDS();
				UsuarioTTD ut = ccct.invocarServicioConsultaCurpTTDS(curp, "");
				if(ut != null) {
					solicitudMB.setMatricula(ut.getMatricula());
					solicitudMB.setNssNom(ut.getNss());
					solicitudMB.setPuestoDescNom(ut.getPuestoDesc());
					solicitudMB
							.setDepartamentoDescNom(ut.getDepartamentoDesc());
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
			e.printStackTrace();
			filtrosConsulta.showMsg("El servicio Web de nomina IMSS no responde, el registro no se puede llevar a cabo. Favor de intentar mas tarde");
			inactiveEmployeeFlag = true;
			log.error(" Error -> {} ", e);
			log.error("ErrorCause -> {}", e.getCause());
			log.error("Error -> {}", e.initCause(e));
			return false;
		}
	}

	public String registrarSolicitudNVer() throws AdmonUsuariosException {

		System.out.println("incia el proceso de registro de solicitud");

		String respuesta = "";
		boolean res2;

		Locale defloc = Locale.getDefault();

		claveAreaNormativa = (int) usuario.getAreaNormativa();
		if (usuario.getAprobadorSession().getSolicitud().getDelDTO() != null)
			claveDelegacion = (int) usuario.getAprobadorSession()
					.getSolicitud().getDelDTO().getCveDelegacion();
		claveDepartamento = (int) filtrosConsulta.getClaveDepartamento();
		clavePuesto = (int) filtrosConsulta.getClavePuesto();
		if (usuario.getAprobadorSession().getSolicitud().getSubdelDTO() != null)
			claveSubdelegacion = (int) usuario.getAprobadorSession()
					.getSolicitud().getSubdelDTO().getCveSubelegacion();
		if (usuario.getAprobadorSession().getSolicitud().getUmfDTO() != null)
			claveUMF = usuario.getAprobadorSession().getSolicitud().getUmfDTO()
					.getCveUmf().intValue();

		solicitudMB.setClaveAreaNormativa(claveAreaNormativa);

		solicitudMB.setCurp(solicitudMB.getCurp().toUpperCase(defloc));
		solicitudMB.setClaveDepartamento(claveDepartamento);
		solicitudMB.setClavePuesto(clavePuesto);

		solicitudMB.setCorreoElectronico(solicitudMB.getSolicitudDTO()
				.getRefCorreoElectronico());

		// Validaciones para regresar un error sino estan bien los datos

		if (!curpValidado.trim().equals(curp.trim())) {
			filtrosConsulta.showMsg("CURP Incorrecta");
			return "inicializada";
		}

		if ("".equals(solicitudMB.getNombre())
				|| "".equals(solicitudMB.getNombrePaterno())) {
			filtrosConsulta
					.showMsg("El usuario no cuenta con un nombre y apellido valido");
			return "inicializada";
		}

		if ("".equals(solicitudMB.getCorreoElectronico())) {
			filtrosConsulta
					.showMsg("El Correo electrónico es incorrecto, recuerde capturar su correo sin el dominio @imss.gob.mx");
			return "inicializada";
		}

		if (claveDepartamento < 1 || clavePuesto < 1) {
			filtrosConsulta
					.showMsg("Selecciones un departamento y puesto valido");
			return "inicializada";
		}
		claveDepartamentoSub = claveDepartamento;
		clavePuestoSub = clavePuesto;
		res2 = validarEmail(solicitudMB.getCorreoElectronico() + "@imss.gob.mx");
		if (true) {

			if (res2 == false) {
				filtrosConsulta.showMsg("EMAIL INCORRECTO");
				flagFirstSlide = false;
				return "inicializada";
			}
		}

		if ("".equals(curpAeliminar)) {// Indica que es la primera ocasion que
										// se registra
			PuestoDTO rol = new PuestoDTO();
			rol.setCvePuesto(this.clavePuesto);
			DepartamentoDTO dep = new DepartamentoDTO();
			dep.setCveSsodepto(this.claveDepartamento);
			AreaNormativaDTO an = new AreaNormativaDTO();

			claveAreaNormativa = solicitudMB.getCveAreaNorm();
			claveAreaNormativaSub = solicitudMB.getCveAreaNorm();
			an.setCveSsoareanorma(Long.parseLong(String
					.valueOf(this.claveAreaNormativa)));
			claveAreaNormativaSub = claveAreaNormativa;

			solicitudMB.agregarClaves(an, dep, rol);

			solicitudMB.getSolicitudDTO().setAreaNorm(
					usuario.getAprobadorSession().getSolicitud().getAreaNorm());
			DepartamentoDTO depto = new DepartamentoDTO();
			depto.setCveSsodepto(claveDepartamento);
			solicitudMB.getSolicitudDTO().setDptoDTO(depto);
			PuestoDTO puesto = new PuestoDTO();
			puesto.setCvePuesto(clavePuesto);
			solicitudMB.getSolicitudDTO().setPuestoDTO(puesto);

			if (filtrosConsulta.getClaveDelegacion() != 0
					&& filtrosConsulta.getClaveDelegacion() > 0) {
				DelegacionDTO del = new DelegacionDTO();
				del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
				solicitudMB.getSolicitudDTO().setDelDTO(del);
			} else
				solicitudMB.getSolicitudDTO().setDelDTO(
						usuario.getAprobadorSession().getSolicitud()
								.getDelDTO());
			if (filtrosConsulta.getClaveSubdelegacion() != 0
					&& filtrosConsulta.getClaveSubdelegacion() > 0) {
				SubdelegacionDTO subdel = new SubdelegacionDTO();
				subdel.setCveSubelegacion(filtrosConsulta
						.getClaveSubdelegacion());
				solicitudMB.getSolicitudDTO().setSubdelDTO(subdel);
			} else
				solicitudMB.getSolicitudDTO().setSubdelDTO(
						usuario.getAprobadorSession().getSolicitud()
								.getSubdelDTO());
			if (filtrosConsulta.getClaveUMF() != 0
					&& filtrosConsulta.getClaveUMF() > 0) {
				UmfDTO umf = new UmfDTO();
				umf.setCveUmf(filtrosConsulta.getClaveUMF());
				solicitudMB.getSolicitudDTO().setUmfDTO(umf);

			} else
				solicitudMB.getSolicitudDTO().setUmfDTO(
						usuario.getAprobadorSession().getSolicitud()
								.getUmfDTO());

			solicitudMB.setCorreoElectronico(solicitudMB.getCorreoElectronico()
					+ "@imss.gob.mx");

			System.out
					.println("----------se invoca el metodo de registra de solicitud");

			String respuestaRegistro = solicitudMB
					.registrarSolicitudCuentaNVer();

			System.out
					.println("----------termina invoca el metodo de registra de solicitud");

			bitacoraService.guardaSolicitudBit(
					new Long(solicitudMB.getIdSolicitud()), usuario
							.getAprobadorSession().getCveIdAprobador(),
					Constantes.TIPO_MOV_REGISTRO);

			claveAreaNormativa = solicitudMB.getCveAreaNorm();
			claveAreaNormativaSub = solicitudMB.getCveAreaNorm();
			claveAreaNormativa = solicitudMB.getClaveAreaNormativa();
			clavePuesto = (int) usuario.getAprobadorSession().getSolicitud()
					.getPuestoId();

			claveAreaNormativa = (int) filtrosConsulta.getClaveAreaNormativa();
			claveDepartamento = (int) filtrosConsulta.getClaveDepartamento();
			clavePuesto = (int) filtrosConsulta.getClavePuesto();

			filtrosConsulta.seteaDepto();

			this.msg = true;
			msgDesc = "La cuenta de usuario se ha registrado exitosamente, recuerde agregar Grupos y Módulos. Esta nueva cuenta quedará inactiva hasta su autorización.";

			registroUsuario = true;

			System.out
					.println("----------se invoca el metodo para registrar el perfil default");

			agregaaTablaUnoDefault();

			System.out
					.println("----------se invoca el metodo para registrar el perfil default");

			clavePuesto = -99;
			solicitudMB.setCorreoElectronico(solicitudMB.getCorreoElectronico()
					.replaceAll("@imss.gob.mx", ""));

			flagFirstSlide = true;
			flagSecondSlide = false;
			flag4 = true;
			flag1 = true;
			this.flagThirdSlide = true;

			botonAdd = true;
			claveAreaNormativa = claveAreaNormativaSub;
			solicitudMB.setClaveAreaNormativa(claveAreaNormativaSub);
			if (claveAreaNormativaSub == 3)
				flag2 = true;
			System.out
					.println("----------termina el proceso de registro de solicitud");

			return respuestaRegistro;
		} else {
			solicitudMB.eliminaPerfilesLDAP(curpAeliminar);
			Long idSol = solicitudMB.obtenerIDSolicitud(curpAeliminar);
			solicitudMB.eliminaPerfilesModulosBD(idSol,
					solicitudMB.getSolicitudDTO());
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
			botonAdd = true;

			System.out
					.println("----------termina el proceso de registro de solicitud");

			return "exitoGuardar";
		}
	}

	public void registrarSolicitudCuenta() throws AdmonUsuariosException {

		String respuesta = "";
		boolean res2;

		Locale defloc = Locale.getDefault();

		claveAreaNormativa = (int) usuario.getAreaNormativa();
		if (usuario.getAprobadorSession().getSolicitud().getDelDTO() != null)
			claveDelegacion = (int) usuario.getAprobadorSession().getSolicitud().getDelDTO().getCveDelegacion();
		claveDepartamento = (int) filtrosConsulta.getClaveDepartamento();
		clavePuesto = (int) filtrosConsulta.getClavePuesto();
		if (usuario.getAprobadorSession().getSolicitud().getSubdelDTO() != null)
			claveSubdelegacion = (int) usuario.getAprobadorSession().getSolicitud().getSubdelDTO().getCveSubelegacion();
		if (usuario.getAprobadorSession().getSolicitud().getUmfDTO() != null)
			claveUMF = usuario.getAprobadorSession().getSolicitud().getUmfDTO().getCveUmf().intValue();

		solicitudMB.setClaveAreaNormativa(claveAreaNormativa);

		solicitudMB.setCurp(solicitudMB.getCurp().toUpperCase(defloc));
		solicitudMB.setClaveDepartamento(claveDepartamento);
		solicitudMB.setClavePuesto(clavePuesto);
	
		solicitudMB.setCorreoElectronico(correo);

		// Validaciones para regresar un error sino estan bien los datos

		if (!curpValidado.trim().equals(curp.trim())) {
			filtrosConsulta.showMsg("CURP Incorrecta");
		} else {
			if ("".equals(solicitudMB.getNombre())
					|| "".equals(solicitudMB.getNombrePaterno())) {
				filtrosConsulta
						.showMsg("El usuario no cuenta con un nombre y apellido valido");
			} else {
				if ("".equals(solicitudMB.getCorreoElectronico())) {
					filtrosConsulta.showMsg("El Correo electrónico es incorrecto, recuerde capturar su correo sin el dominio @imss.gob.mx");
				} else {

					if (claveDepartamento < 1 || clavePuesto < 1) {
						filtrosConsulta
								.showMsg("Selecciones un departamento y puesto valido");
					} else {
						claveDepartamentoSub = claveDepartamento;
						clavePuestoSub = clavePuesto;
						res2 = validarEmail(correo + "@imss.gob.mx");
						if (true) {

							if (res2 == false) {
								filtrosConsulta.showMsg("EMAIL INCORRECTO");
								flagFirstSlide = false;
							}
						}

						if ("".equals(curpAeliminar)) {// Indica que es la
														// primera ocasion que
														// se registra
							PuestoDTO rol = new PuestoDTO();
							rol.setCvePuesto(this.clavePuesto);
							DepartamentoDTO dep = new DepartamentoDTO();
							dep.setCveSsodepto(this.claveDepartamento);
							AreaNormativaDTO an = new AreaNormativaDTO();

							claveAreaNormativa = solicitudMB.getCveAreaNorm();
							claveAreaNormativaSub = solicitudMB.getCveAreaNorm();
							an.setCveSsoareanorma(Long.parseLong(String
									.valueOf(this.claveAreaNormativa)));
							claveAreaNormativaSub = claveAreaNormativa;

							solicitudMB.agregarClaves(an, dep, rol);

							solicitudMB.getSolicitudDTO().setAreaNorm(
									usuario.getAprobadorSession()
											.getSolicitud().getAreaNorm());
							DepartamentoDTO depto = new DepartamentoDTO();
							depto.setCveSsodepto(claveDepartamento);
							solicitudMB.getSolicitudDTO().setDptoDTO(depto);
							PuestoDTO puesto = new PuestoDTO();
							puesto.setCvePuesto(clavePuesto);
							solicitudMB.getSolicitudDTO().setPuestoDTO(puesto);

							if (filtrosConsulta.getClaveDelegacion() != 0
									&& filtrosConsulta.getClaveDelegacion() > 0) {
								DelegacionDTO del = new DelegacionDTO();
								del.setCveDelegacion(filtrosConsulta
										.getClaveDelegacion());
								solicitudMB.getSolicitudDTO().setDelDTO(del);
							} else
								solicitudMB.getSolicitudDTO().setDelDTO(
										usuario.getAprobadorSession()
												.getSolicitud().getDelDTO());
							if (filtrosConsulta.getClaveSubdelegacion() != 0
									&& filtrosConsulta.getClaveSubdelegacion() > 0) {
								SubdelegacionDTO subdel = new SubdelegacionDTO();
								subdel.setCveSubelegacion(filtrosConsulta
										.getClaveSubdelegacion());
								solicitudMB.getSolicitudDTO().setSubdelDTO(
										subdel);
							} else
								solicitudMB.getSolicitudDTO().setSubdelDTO(
										usuario.getAprobadorSession()
												.getSolicitud().getSubdelDTO());
							if (filtrosConsulta.getClaveUMF() != 0
									&& filtrosConsulta.getClaveUMF() > 0) {
								UmfDTO umf = new UmfDTO();
								umf.setCveUmf(filtrosConsulta.getClaveUMF());
								solicitudMB.getSolicitudDTO().setUmfDTO(umf);

							} else
								solicitudMB.getSolicitudDTO().setUmfDTO(
										usuario.getAprobadorSession()
												.getSolicitud().getUmfDTO());

							solicitudMB.setCorreoElectronico(solicitudMB.getCorreoElectronico() + "@imss.gob.mx");
							solicitudMB.setTelefono(telefono);
							System.out.println("----------se invoca el metodo de registra de solicitud");

							String respuestaRegistro = solicitudMB.registrarSolicitudCuentaNVer();

							System.out
									.println("----------termina invoca el metodo de registra de solicitud");

							bitacoraService.guardaSolicitudBit(new Long(
									solicitudMB.getIdSolicitud()), usuario
									.getAprobadorSession().getCveIdAprobador(),
									Constantes.TIPO_MOV_REGISTRO);

							claveAreaNormativa = solicitudMB.getCveAreaNorm();
							claveAreaNormativaSub = solicitudMB
									.getCveAreaNorm();
							claveAreaNormativa = solicitudMB
									.getClaveAreaNormativa();
							clavePuesto = (int) usuario.getAprobadorSession()
									.getSolicitud().getPuestoId();

							claveAreaNormativa = (int) filtrosConsulta
									.getClaveAreaNormativa();
							claveDepartamento = (int) filtrosConsulta
									.getClaveDepartamento();
							clavePuesto = (int) filtrosConsulta
									.getClavePuesto();

							filtrosConsulta.seteaDepto();

							this.msg = true;
							filtrosConsulta.showMsg("La cuenta de usuario se ha registrado exitosamente, recuerde agregar Grupos y Módulos. Esta nueva cuenta quedará inactiva hasta su autorización.");
							registroUsuario = true;

							System.out
									.println("----------se invoca el metodo para registrar el perfil default");

							agregaaTablaUnoDefault();

							System.out
									.println("----------se invoca el metodo para registrar el perfil default");

							clavePuesto = -99;
							solicitudMB.setCorreoElectronico(solicitudMB
									.getCorreoElectronico().replaceAll(
											"@imss.gob.mx", ""));

							flagFirstSlide = true;
							flagSecondSlide = false;
							flag4 = true;
							flag1 = true;
							this.flagThirdSlide = true;

							botonAdd = true;
							claveAreaNormativa = claveAreaNormativaSub;
							solicitudMB
									.setClaveAreaNormativa(claveAreaNormativaSub);
							if (claveAreaNormativaSub == 3)
								flag2 = true;
							System.out
									.println("----------termina el proceso de registro de solicitud");

						} else {
							solicitudMB.eliminaPerfilesLDAP(curpAeliminar);
							Long idSol = solicitudMB
									.obtenerIDSolicitud(curpAeliminar);
							solicitudMB.eliminaPerfilesModulosBD(idSol,
									solicitudMB.getSolicitudDTO());
							solicitudMB.actualizarSolicitudBD(idSol);
							// Roles
							for (PuestoDTO rolint : solicitudMB.getRolesData()) {
								solicitudMB.registrarPerfiles(idSol,
										(int) rolint.getCvePuesto());
							}
							// Modulos
							for (ModuloDTO modint : solicitudMB
									.getModulosData()) {
								solicitudMB.registrarModulos(idSol,
										(int) modint.getDptoDTO()
												.getCveSsodepto(), (int) modint
												.getCveIdModulo(), 1);
							}
							botonAdd = true;

							System.out
									.println("----------termina el proceso de registro de solicitud");

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
			filtrosConsulta
					.showMsg("NO SE PUEDE ELIMINAR EL PERFIL POR DEFAULT");
			return;
		}

		solicitudMB.setRolesData(DataTableData.removeRoleFromTableOne(
				fieldToDeletePuesto, (ArrayList) solicitudMB.getRolesData()));
		try {
			solicitudMB.eliminarPerfilesSolicitudNVer(fieldToDeletePuesto);

			bitacoraService.guardaPuestoBit(
					new Long(solicitudMB.getIdSolicitud()), usuario
							.getAprobadorSession().getCveIdAprobador(),
					new Long(fieldToDeletePuesto), false);

			filtrosConsulta
					.showMsg("El grupo se elimino de la cuenta de usuario.");
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
			filtrosConsulta
					.showMsg("NO SE PUEDE ELIMINAR EL PERFIL POR DEFAULT");
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
		solicitudMB.setModulosData(DataTableData.removeModuleFromTableTwo(fieldToDeleteModulo, (ArrayList) solicitudMB.getModulosData()));
		try {
			eliminarModulosSolicitudNVer(fieldToDeleteDepartamento,fieldToDeleteModulo);
			bitacoraService.guardaModulosBit(new Long(solicitudMB.getIdSolicitud()), usuario.getAprobadorSession().getCveIdAprobador(),	new Long(fieldToDeleteModulo), false);
			filtrosConsulta.showMsg("El módulo se elimino de la cuenta de usuario.");
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
		return "inicializada";
	}

	public String eliminarModulosSolicitudNVer(int cveDepartamento,
			int cveModulo) throws AdmonUsuariosException {
		solicitudMB.eliminarModulosSolicitudNVer(cveDepartamento, cveModulo);
		return "inicializada";
	}

	public boolean validarEmail(String email) {
		if (email == null || "".equals(email)) {
			filtrosConsulta
					.showMsg("El correo electrónico es incorrecto, favor de verificar");
		}

		boolean res = email.matches(EMAIL_PATTERN);
		if (res == false) {
			filtrosConsulta
					.showMsg("El correo electrónico es incorrecto, favor de verificar");
		}
		return res;
	}

//	public boolean validarEstructuraCurp(String curp) {
//		if (curp == null || "".equals(curp)) {
//			return false;
//		}
//		return  curp.matches(CURP_PATTERN);
//	}

	public void obtenerSolicitudByFiltro() throws AdmonUsuariosException {
		solMB.llenaFiltro();
		EstatusDTO s = new EstatusDTO();
		s.setCveSsoestatus(Constantes.ESTATUS.AUTORIZADO.getOpcion());
		solMB.getFiltro().setEstatusDTO(s);
		System.out.println("Invoca search solicitudes - RegistraSolicitudMB");
		solicitudes = solicitudCriteria.searchSolicitudes(solMB.getFiltro(),
				usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
	}

	public String getCurpAeliminar() {
		return curpAeliminar;
	}

	public void setCurpAeliminar(String curpAeliminar) {
		this.curpAeliminar = curpAeliminar;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlagLoad() {
		return flagLoad;
	}

	public void setFlagLoad(boolean flagLoad) {
		this.flagLoad = flagLoad;
	}

	public boolean isFlagCambiaCurp() {
		return flagCambiaCurp;
	}

	public void setFlagCambiaCurp(boolean flagCambiaCurp) {
		this.flagCambiaCurp = flagCambiaCurp;
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

	public List<SelectItem> getLstAreaNormativa() {
		return lstAreaNormativa;
	}

	public void setLstAreaNormativa(List<SelectItem> lstAreaNormativa) {
		this.lstAreaNormativa = lstAreaNormativa;
	}

	public List<SelectItem> getLstSubDelegacion() {
		return lstSubDelegacion;
	}

	public void setLstSubDelegacion(List<SelectItem> lstSubDelegacion) {
		this.lstSubDelegacion = lstSubDelegacion;
	}

	public List<SelectItem> getLstUMF() {
		return lstUMF;
	}

	public void setLstUMF(List<SelectItem> lstUMF) {
		this.lstUMF = lstUMF;
	}

	public List<SelectItem> getLstDepartamento() {
		return lstDepartamento;
	}

	public void setLstDepartamento(List<SelectItem> lstDepartamento) {
		this.lstDepartamento = lstDepartamento;
	}

	public List<SelectItem> getLstPuesto() {
		return lstPuesto;
	}

	public void setLstPuesto(List<SelectItem> lstPuesto) {
		this.lstPuesto = lstPuesto;
	}

	public List<SelectItem> getLstModulo() {
		return lstModulo;
	}

	public void setLstModulo(List<SelectItem> lstModulo) {
		this.lstModulo = lstModulo;
	}

	public List<SelectItem> getLstDelegacion() {
		return lstDelegacion;
	}

	public void setLstDelegacion(List<SelectItem> lstDelegacion) {
		this.lstDelegacion = lstDelegacion;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public void cargaAreaNormativaNCat() throws AdmonUsuariosException {
		this.lstAreaNormativa.clear();
		List<AreaNormativaDTO> lista = solicitudMB.cargaAreaNormativaNCat();
		for (AreaNormativaDTO an : lista) {
			this.lstAreaNormativa.add(new SelectItem(an.getCveSsoareanorma(),
					an.getDesAreanorma()));
		}
	}

	public void cargaDelegacionnNCat() throws AdmonUsuariosException {
		this.lstDelegacion.clear();
		List<DelegacionDTO> lista = solicitudMB.cargaDelegacionnNCat();
		for (DelegacionDTO dl : lista) {
			this.lstDelegacion.add(new SelectItem(dl.getCveDelegacion(), dl
					.getNombreDelegacion()));
		}
	}

	public void cargaDepartamentoNCat() throws AdmonUsuariosException {
		this.lstDepartamento.clear();
		List<DepartamentoDTO> lista = solicitudMB.cargaDepartamentoNCat();
		for (DepartamentoDTO dep : lista) {
			this.lstDepartamento.add(new SelectItem(dep.getCveSsodepto(), dep
					.getDesDepartamento()));
		}
		solicitudMB.setLstDepartamento(this.lstDepartamento);
	}

	public void cargaPuestoNCat() throws AdmonUsuariosException {
		this.lstPuesto.clear();
		List<PuestoDTO> lista = solicitudMB.cargaPuestoNCat();
		for (PuestoDTO pst : lista) {
			this.lstPuesto.add(new SelectItem(pst.getCvePuesto(), pst
					.getNombrePuesto()));
		}
		solicitudMB.setLstPuesto(this.lstPuesto);
	}

	public void cargaDelegacionNCat() throws AdmonUsuariosException {
		this.lstDelegacion.clear();
		List<DelegacionDTO> lista = solicitudMB.cargaDelegacionNCat();
		for (DelegacionDTO del : lista) {
			this.lstDelegacion.add(new SelectItem(del.getCveDelegacion(), del
					.getNombreDelegacion()));
		}
		solicitudMB.setLstDelegacion(this.lstDelegacion);
	}

	public void cargaSubDelegacionNCat() throws AdmonUsuariosException {
		this.lstSubDelegacion.clear();
		List<SubdelegacionDTO> lista = solicitudMB.cargaSubDelegacionNCat();
		for (SubdelegacionDTO sdel : lista) {
			this.lstSubDelegacion.add(new SelectItem(sdel.getCveSubelegacion(),
					sdel.getNombreSubelegacion()));
		}
		solicitudMB.setLstSubDelegacion(this.lstSubDelegacion);
	}

	public void cargaUMFNCat() throws AdmonUsuariosException {
		this.lstUMF.clear();
		List<UmfDTO> lista = solicitudMB.cargaUmfNCat();
		for (UmfDTO umf : lista) {
			this.lstUMF
					.add(new SelectItem(umf.getCveUmf(), umf.getNombreUmf()));
		}
		solicitudMB.setLstUMF(this.lstUMF);
	}

	public void listenerDepartamento(ValueChangeEvent ve) {
		lstDepartamento.clear();
		try {

			// claveAreaNormativaSub =
			// Integer.parseInt(ve.getNewValue().toString());
			int area = Integer.parseInt(ve.getNewValue().toString());
			solicitudMB.setCveAreaNorm(area);
			cargaDepartamentoByArea(ve.getNewValue().toString());

			// // 1 -- NIVEL CENTRAL
			// // 2 -- DELEGACION
			// // 3 -- SUBDELEGACION
			// // 4 -- UMF
			if ("1".equals(ve.getNewValue().toString())) {
				solicitudMB.setClaveDelegacion(-99); // Establece el nulo como
														// Delegacion
														// seleccionada
				flag1 = true;
				flag2 = true;
				flag3 = true;
			}

			if ("2".equals(ve.getNewValue().toString())) {
				flag1 = false;
				flag2 = true;
				flag3 = true;
			}

			if ("3".equals(ve.getNewValue().toString())) {
				flag1 = false;
				flag2 = false;
				flag3 = true;
			}

			if ("4".equals(ve.getNewValue().toString())) {
				flag1 = false;
				flag2 = false;
				flag3 = false;
			}
		} catch (Exception e) {
			System.out.println("Error al consultar el Departamento");
			log.error("Error al consultar el Departamento  ", e);
		}
	}

	public void listenerPuesto(ValueChangeEvent ve) {
		lstPuesto.clear();
		try {
			solicitudMB.setClaveDepartamento(Integer.parseInt(ve.getNewValue()
					.toString()));
			this.cargaPuestoByDepartamento(ve.getNewValue().toString());
		} catch (Exception e) {
			System.out.println("Error al consultar el Puesto");
			log.error("Error al consultar el Puesto ", e);
		}
	}

	public void cargaDepartamentoByArea(String cveArea)
			throws AdmonUsuariosException {
		this.lstDepartamento.clear();
		List<DepartamentoDTO> lista = solicitudMB
				.listarDepartamentosByAreaNormativa(cveArea);
		for (DepartamentoDTO an : lista) {
			this.lstDepartamento.add(new SelectItem(an.getCveSsodepto(), an
					.getDesDepartamento()));
		}
	}

	public void cargaPuestoByDepartamento(String cveDepartamento)
			throws AdmonUsuariosException {
		this.lstPuesto.clear();
		List<PuestoDTO> lista = solicitudMB
				.listarPuestoByDepartamento(cveDepartamento);
		for (PuestoDTO pt : lista) {
			this.lstPuesto.add(new SelectItem(pt.getCvePuesto(), pt
					.getNombrePuesto()));
		}
	}

	public int getClaveDepartamentoSub() {
		return claveDepartamentoSub;
	}

	public void setClaveDepartamentoSub(int claveDepartamentoSub) {
		this.claveDepartamentoSub = claveDepartamentoSub;
	}

	public int getClavePuestoSub() {
		return clavePuestoSub;
	}

	public void setClavePuestoSub(int clavePuestoSub) {
		this.clavePuestoSub = clavePuestoSub;
	}

	public int getClaveAreaNormativaSub() {
		return claveAreaNormativaSub;
	}

	public void setClaveAreaNormativaSub(int claveAreaNormativaSub) {
		this.claveAreaNormativaSub = claveAreaNormativaSub;
	}

	public void agregaaTablaUnoPrevio() {
		claveAreaNormativa = (int) filtrosConsulta.getClaveAreaNormativa();
		claveDepartamento = (int) filtrosConsulta.getClaveDepartamentoRolAdd();
		clavePuesto = clavePuestoSub;
		agregaaTablaUno("No");
	}

	public void agregaaTablaUno(String defaultRol) {
		System.out.println("----------inicia proceso de alta de perfil");
		if (registroUsuario == false) { // Validar si existe en el registro
										// actual
			filtrosConsulta
					.showMsg("NO PUEDE AGREGAR ROLES HASTA QUE REGISTRE AL USUARIO");
			return;
		}

		if (clavePuesto == -99) {
			filtrosConsulta
					.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
			return;
		}

		boolean res = DataTableData.validaPuestoExistente(
				(ArrayList) solicitudMB.getRolesData(), claveAreaNormativa,
				claveDepartamento, clavePuesto);
		try {
			if (res == false) {
				solicitudMB.setRolesData(DataTableData.addRoletoTableOne(
						(ArrayList) solicitudMB.getRolesData(),
						claveAreaNormativa, solicitudMB
								.buscaAreaNormativa(claveAreaNormativa),
						claveDepartamento, solicitudMB.buscaDepartamento(
								claveAreaNormativa, claveDepartamento),
						clavePuesto, solicitudMB.buscaPuesto(claveDepartamento,
								clavePuesto), defaultRol));

				PerfilDTO per = new PerfilDTO();
				per.setAreaNormDTO(new AreaNormativaDTO());
				per.getAreaNormDTO().setCveSsoareanorma(claveAreaNormativa);
				per.setDeptoDTO(new DepartamentoDTO());
				per.getDeptoDTO().setCveSsodepto(claveDepartamento);
				per.setDesDefault(defaultRol);
				per.setPuestoDTO(new PuestoDTO());
				per.getPuestoDTO().setCvePuesto(clavePuesto);
				per.setSolicitudDTO(new SolicitudDTO());
				per.getSolicitudDTO().setCveSsosolicitud(
						solicitudMB.getIdSolicitud());
				solicitudCriteria.agregaPerfil(per);

				bitacoraService.guardaPuestoBit(
						new Long(solicitudMB.getIdSolicitud()), usuario
								.getAprobadorSession().getCveIdAprobador(),
						new Long(clavePuesto), true);

				filtrosConsulta
						.showMsg("El grupo seleccionado se agrego a esta cuenta de usuario.");
			} else {
				filtrosConsulta
						.showMsg("El grupo seleccionado ya esta relaciondo a esta cuenta de usuario.");
			}
		} catch (Exception e) {
			System.out.println("agregaaTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
		System.out.println("----------termina proceso de alta de perfil");

	}

	public void agregaaTablaUnoDefault() {

		try {
			solicitudMB.setRolesData(DataTableData.addRoletoTableOne(
					(ArrayList) solicitudMB.getRolesData(), claveAreaNormativa,
					solicitudMB.buscaAreaNormativa(claveAreaNormativa),
					claveDepartamento, solicitudMB.buscaDepartamento(
							claveAreaNormativa, claveDepartamento),
					clavePuesto, solicitudMB.buscaPuesto(claveDepartamento,
							clavePuesto), "Si"));

			PerfilDTO per = new PerfilDTO();
			per.setAreaNormDTO(new AreaNormativaDTO());
			per.getAreaNormDTO().setCveSsoareanorma(claveAreaNormativa);
			per.setDeptoDTO(new DepartamentoDTO());
			per.getDeptoDTO().setCveSsodepto(claveDepartamento);
			per.setDesDefault("Si");
			per.setPuestoDTO(new PuestoDTO());
			per.getPuestoDTO().setCvePuesto(clavePuesto);
			per.setSolicitudDTO(new SolicitudDTO());
			per.getSolicitudDTO().setCveSsosolicitud(
					solicitudMB.getIdSolicitud());
			solicitudCriteria.agregaPerfil(per);
		} catch (Exception e) {
			System.out.println("agregaaTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void agregaaTablaUnoEditar() {
		// Validar si existe en el registro actual
		try {
			boolean res = DataTableData.validaPuestoExistente(
					(ArrayList) solicitudMB.getRolesData(), claveAreaNormativa,
					claveDepartamento, clavePuesto);
			if (res == false)
				solicitudMB.setRolesData(DataTableData.addRoletoTableOne(
						(ArrayList) solicitudMB.getRolesData(),
						claveAreaNormativa, solicitudMB
								.buscaAreaNormativa(claveAreaNormativa),
						claveDepartamento, solicitudMB.buscaDepartamento(
								claveAreaNormativa, claveDepartamento),
						clavePuesto, solicitudMB.buscaPuesto(claveDepartamento,
								clavePuesto), "Si"));
		} catch (Exception e) {
			System.out.println("agregaaTablaUnoEditar ");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void agregaaTablaUnoAp() {
		try {
			solicitudMB.setRolesData(DataTableData.addRoletoTableOne(
					(ArrayList) solicitudMB.getRolesData(), claveAreaNormativa,
					solicitudMB.buscaAreaNormativa(claveAreaNormativa),
					claveDepartamento, solicitudMB.buscaDepartamento(
							claveAreaNormativa, claveDepartamento),
					clavePuesto, solicitudMB.buscaPuesto(claveDepartamento,
							clavePuesto), "Si"));
			solicitudMB.setClaveAreaNormativa(claveAreaNormativa);
		} catch (Exception e) {
			System.out.println("agregaaTablaUnoAp ");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void agregaaTablaDos() {
		System.out.println("Agrega modulo 1");
		if (registroUsuario == false) {
			filtrosConsulta
					.showMsg("NO PUEDE AGREGAR MODULOS HASTA QUE REGISTRE AL USUARIO");
			return;
		}
		System.out.println("Agrega modulo 2");

		claveAreaNormativa = (int) filtrosConsulta.getClaveAreaNormativa();
		claveDepartamento = (int) filtrosConsulta.getClaveDepartamentoModAdd();
		System.out.println("Agrega modulo 3");
		try {
			System.out.println("Agrega modulo 4");
			if (claveModulo == -99) {
				filtrosConsulta
						.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
				return;
			}
			System.out.println("Agrega modulo 5");

			// Validar si existe en el registro actual
			boolean res = DataTableData.validaModuloExistente(
					(ArrayList) solicitudMB.getModulosData(),
					claveAreaNormativa, claveDepartamento, claveModulo);
			System.out.println("Agrega modulo 6");
			if (res == false) {
				System.out.println("Agrega modulo 7");
				solicitudMB.setModulosData(DataTableData.addRoletoTableTwoN(
						(ArrayList<ModuloDTO>) solicitudMB.getModulosData(),
						solicitudMB.buscaAreaNormativaObj(claveAreaNormativa),
						solicitudMB.buscaDepartamentoObj(claveAreaNormativa,
								claveDepartamento), claveModulo, solicitudMB
								.buscaModulo(claveDepartamento, claveModulo)));
				System.out.println("Agrega modulo 8");
				solicitudMB.registrarModulosSolicitudNVer(claveDepartamento,
						claveModulo);
				System.out.println("Agrega modulo 9");

				bitacoraService.guardaModulosBit(
						new Long(solicitudMB.getIdSolicitud()), usuario
								.getAprobadorSession().getCveIdAprobador(),
						new Long(claveModulo), true);
				System.out.println("Agrega modulo 10");

				filtrosConsulta
						.showMsg("El módulo seleccionado se agrego a esta cuenta de usuario.");

			} else {
				filtrosConsulta
						.showMsg("El módulo seleccionado ya esta relaciondo a esta cuenta de usuario.");
			}
			System.out.println("Agrega modulo 11");

			solicitudMB.setClaveAreaNormativa(claveAreaNormativa);
		} catch (Exception e) {
			System.out.println("agregaaTablaDos ");
			e.printStackTrace();
			log.error("  ", e);
		}
		System.out.println("Agrega modulo 12");
	}

	public void listenerModuloAdd(ValueChangeEvent ve) {
		try {
			claveModulo = new Integer(ve.getNewValue().toString()).intValue();
		} catch (Exception e) {
			System.out.println("Error al consultar el Puesto");
		}
	}

	public void agregaaTablaDosEditar() {
		// Validar si existe en el registro actual
		try {
			boolean res = DataTableData.validaModuloExistente(
					(ArrayList) solicitudMB.getModulosData(),
					claveAreaNormativa, claveDepartamento, claveModulo);
			if (res == false)
				// solicitudMB.setModulosData(DataTableData.addRoletoTableTwo((ArrayList)solicitudMB.getModulosData(),
				// claveAreaNormativa,
				// solicitudMB.buscaAreaNormativa(claveAreaNormativa),
				// claveDepartamento,
				// solicitudMB.buscaDepartamento(claveAreaNormativa,claveDepartamento),
				// claveModulo, solicitudMB.buscaModulo(claveDepartamento,
				// claveModulo)));
				solicitudMB.setClaveAreaNormativa(claveAreaNormativa);
		} catch (Exception e) {
			System.out.println("agregaaTablaDosEditar ");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void agregaaTablaDosAp() {
		// solicitudMB.setModulosData(DataTableData.addRoletoTableTwoN((ArrayList)solicitudMB.getModulosData(),
		// claveAreaNormativa,
		// solicitudMB.buscaAreaNormativaObj(claveAreaNormativa),
		// claveDepartamento,
		// solicitudMB.buscaDepartamentoObj(claveAreaNormativa,
		// claveDepartamento), claveModulo,
		// solicitudMB.buscaModulo(claveDepartamento, claveModulo)));
	}

	public void listenerModulo(ValueChangeEvent ve) {
		lstModulo.clear();
		try {
			List<ModuloDTO> listaModulos = solicitudMB
					.listarModulosByDepartamento(ve.getNewValue().toString());
			for (ModuloDTO md : listaModulos) {
				lstModulo.add(new SelectItem(md.getCveIdModulo(), md
						.getDesModulo()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String registraSolicitudInicialCambio()
			throws AdmonUsuariosException {

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
		flag4 = false;
		flagFirstSlide = false;
		flagSecondSlide = true;
		flagThirdSlide = true;
		claveAreaNormativa = -99;
		claveDepartamento = -99;
		clavePuesto = -99;
		claveModulo = -99;
		CargaTablaUno();
		CargaTablaDos();
		solicitudMB.setCurpNueva("");
		flagCambiaCurp = true;
		return "registraSolicitudCambio";
	}

	public String bajaDefinitivaUsuario() {
		try {
			obtenerSolicitudByFiltro();
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}

		return "bajaDefinitivaUsuario";
	}

	public void actualizarTabla() throws AdmonUsuariosException {
		javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		Enumeration<Object> en = req.getAttributeNames();
		Object attr = null;
		Object attrVal = null;
		while (en.hasMoreElements()) {
			try {
				attr = (Object) en.nextElement();
				if (!attr.toString().startsWith("SSO"))
					continue;

				System.out.print("attribute: " + attr.toString() + "\t");
				attrVal = (Object) req.getAttribute(attr.toString());

				if (attrVal instanceof String) {
					System.out.println("String: " + attrVal.toString());
				} else if (attrVal instanceof Number) {
					System.out.println("Number: "
							+ ((Integer) attrVal).toString());
				} else if (attrVal instanceof Set<?>) {
					System.out.println("tipo de Set:"
							+ attrVal.getClass().getName());
					Set tablaParam = null;

					tablaParam = (HashSet) attrVal;
					Iterator it = null;

					System.out.println("empty?" + tablaParam.isEmpty());
					System.out.println("size:" + tablaParam.size());
					it = tablaParam.iterator();
					if (it == null || !it.hasNext())
						System.out.println("no trae valor!!!!");
					else
						System.out.print("obteniendo el valor... \t");
					while (it.hasNext()) {
						Object v = it.next();

						if (v instanceof String) {
							System.out.println("String: " + v.toString());
						} else if (v instanceof Number) {
							System.out.println("Number: "
									+ ((Integer) v).toString());
						} else {
							System.out
									.println("tipo:" + v.getClass().getName());
						}
					}
				} else {
					System.out.println("ni string, ni número. tipo:"
							+ attrVal.getClass().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("  ", e);
			}
		}
		claveDelegacion = solicitudMB.getCveDeleg();
		claveSubDelegacion = solicitudMB.getClaveSubDelegacion();
		claveUMF = solicitudMB.getClaveUMF();
		this.usuarios = solicitudMB.actualizarTabla(claveDelegacion,
				claveSubDelegacion, claveUMF);
	}

	public SolicitudDTO llenaFiltro() {
		SolicitudDTO filtro = new SolicitudDTO();
		filtro.setDptoDTO(usuario.getAprobadorSession().getSolicitud()
				.getDptoDTO());
		filtro.setDelDTO(usuario.getAprobadorSession().getSolicitud()
				.getDelDTO());
		filtro.setSubdelDTO(usuario.getAprobadorSession().getSolicitud()
				.getSubdelDTO());
		filtro.setUmfDTO(usuario.getAprobadorSession().getSolicitud()
				.getUmfDTO());
		EstatusDTO st = new EstatusDTO();
		st.setCveSsoestatus(new Long(2));
		filtro.setEstatusDTO(st);
		return filtro;
	}

	public String actualizarTablaPre() {
		try {
			actualizarTabla();
		} catch (Exception e) {
			log.error("  ", e);
		}
		return "bajaDefinitivaUsuario";
	}

	public void selecionaSolicitud(SelectEvent event) {
		solSelect = (SolicitudDTO) event.getObject();
	}

	public void bajaDeUsuario(ActionEvent actionEvent) {
		if (solSelect != null) {
			if (solSelect.getDesUsrCurp() != null
					&& solSelect.getDesUsrCurp().length() > 0) {
				try {

					solicitudes.clear();
					solicitudMB.desactivarUsuario(solSelect.getDesUsrCurp());
				} catch (Exception e) {
					e.printStackTrace();
					filtrosConsulta
							.setDesc("No fue posible dar de baja la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
				try {
					solicitudMB.eliminarUsuario(solSelect.getDesUsrCurp(),
							ESTATUS_BAJA_SOLICITUD);
					aprobadoresService.bajaAprobador(solSelect.getDesUsrCurp());
					bitacoraService.guardaSolicitudBit(solSelect
							.getCveSsosolicitud(), usuario
							.getAprobadorSession().getCveIdAprobador(),
							Constantes.TIPO_MOV_BAJA);
					solSelect.setAprobador(usuario.getAprobadorSession());

					String titulo = "BAJA DE CUENTA DE USUARIO";
					String msg = "ha sido dada de baja";
					mensajeriaService.enviarCorreo(usuario
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(),
							solSelect.getRefCorreoElectronico(), msg, titulo,
							solSelect, "Baja de cuenta");
					mensajeriaService.enviarCorreoAprobador(usuario
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), usuario
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), msg, titulo, solSelect,
							"Baja de cuenta");

					filtrosConsulta
							.setDesc("Se realizo exitosamente la baja de la cuenta de usuario.");
					filtrosConsulta.setMsg(true);

				} catch (Exception e1) {
					filtrosConsulta
							.setDesc("No fue posible actualizar la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
				try {
					obtenerSolicitudByFiltro();
				} catch (AdmonUsuariosException e) {
					System.out
							.println("error al actualizar la lista de solicitudes");
					e.printStackTrace();
				}
			} else {
				filtrosConsulta
						.setDesc("La cuenta de usuario no cuenta con las caracteristicas necesarias para procesar la baja.");
				filtrosConsulta.setMsg(true);
			}
		} else {
			filtrosConsulta.setDesc("Seleccione una cuenta de usuario.");
			filtrosConsulta.setMsg(true);
		}
	}

	public void bajaDeUsuarioCambioCurp(ActionEvent actionEvent) {
		if (solSelect != null) {
			if (solSelect.getDesUsrCurp() != null
					&& solSelect.getDesUsrCurp().length() > 0) {
				try {
					solicitudes.clear();
					solicitudMB.desactivarUsuario(solSelect.getDesUsrCurp());
				} catch (Exception e) {
					e.printStackTrace();
					filtrosConsulta
							.setDesc("No fue posible dar de baja la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
				try {
					solicitudMB.eliminarUsuario(solSelect.getDesUsrCurp(),
							ESTATUS_BAJA_CURP_SOLICITUD);
					bitacoraService.guardaSolicitudBit(solSelect
							.getCveSsosolicitud(), usuario
							.getAprobadorSession().getCveIdAprobador(),
							Constantes.TIPO_MOV_BAJA_CURP);
					solSelect.setAprobador(usuario.getAprobadorSession());

					String titulo = "BAJA DE CUENTA DE USUARIO";
					String msg = " ha sido dada de baja";
					mensajeriaService.enviarCorreo(usuario
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(),
							solSelect.getRefCorreoElectronico(), msg, titulo,
							solSelect, "Baja de cuenta");
					mensajeriaService.enviarCorreoAprobador(usuario
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), usuario
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), msg, titulo, solSelect,
							"Baja de cuenta");

					filtrosConsulta
							.setDesc("Se realizo exitosamente la baja de la cuenta de usuario.");
					filtrosConsulta.setMsg(true);

				} catch (Exception e1) {
					e1.printStackTrace();

					filtrosConsulta
							.setDesc("No fue posible dar de baja la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
				try {
					obtenerSolicitudByFiltro();
				} catch (AdmonUsuariosException e) {
					System.out
							.println("error al actualizar la lista de solicitudes");
					e.printStackTrace();
				}
			} else {
				filtrosConsulta
						.setDesc("La cuenta de usuario no cuenta con las caracteristicas necesarias para procesar la baja.");
				filtrosConsulta.setMsg(true);
			}
		} else {
			filtrosConsulta.setDesc("Seleccione una cuenta de usuario.");
			filtrosConsulta.setMsg(true);
		}

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

	public void listenerSubDelegaciones(ValueChangeEvent ve) {
		lstSubDelegacion.clear();
		lstSubDelegacion = solicitudMB.listenerSubDelegaciones(ve.getNewValue()
				.toString());
	}

	public void listenerUMF(ValueChangeEvent ve) {
		lstUMF.clear();
		lstUMF = solicitudMB.listenerUMF(ve.getNewValue().toString());
	}

	public void listenerUnidadMedica() {
		this.lstUMF.clear();
		this.lstUMF = solicitudMB.listenerUnidadMedica();
	}

	public int getClaveDelegacion() {
		return claveDelegacion;
	}

	public void setClaveDelegacion(int claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public int getClaveSubDelegacion() {
		return claveSubDelegacion;
	}

	public void setClaveSubDelegacion(int claveSubDelegacion) {
		this.claveSubDelegacion = claveSubDelegacion;
	}

	public int getClaveUMF() {
		return claveUMF;
	}

	public void setClaveUMF(int claveUMF) {
		this.claveUMF = claveUMF;
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

	public int getClaveSubdelegacion() {
		return claveSubdelegacion;
	}

	public void setClaveSubdelegacion(int claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
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

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
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

	public boolean isMsg() {
		return msg;
	}

	public void setMsg(boolean msg) {
		this.msg = msg;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public boolean isMist() {
		return mist;
	}

	public void setMist(boolean mist) {
		this.mist = mist;
	}

	public MensajeriaSessionLocal getMensajeriaService() {
		return mensajeriaService;
	}

	public void setMensajeriaService(MensajeriaSessionLocal mensajeriaService) {
		this.mensajeriaService = mensajeriaService;
	}

	public BitacoraServiceLocal getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraServiceLocal bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	public AdmonUsuariosSessionLocal getAdmonUsuarios() {
		return admonUsuarios;
	}

	public void setAdmonUsuarios(AdmonUsuariosSessionLocal admonUsuarios) {
		this.admonUsuarios = admonUsuarios;
	}

	public AprobadoresServiceLocal getAprobadoresService() {
		return aprobadoresService;
	}

	public void setAprobadoresService(AprobadoresServiceLocal aprobadoresService) {
		this.aprobadoresService = aprobadoresService;
	}

	public SolicitudDTO getSolSelect() {
		return solSelect;
	}

	public void setSolSelect(SolicitudDTO solSelect) {
		this.solSelect = solSelect;
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

	public String getCurpValidado() {
		return curpValidado;
	}

	public void setCurpValidado(String curpValidado) {
		this.curpValidado = curpValidado;
	}

	
}
