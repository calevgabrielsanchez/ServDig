package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.MensajeriaSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.model.table.RowStateMap;

@ManagedBean(name = "mantenimientoAprobadoresMB")
@CustomScoped("#{window}")
public class MantenimientoAprobadoresDialogMB {

	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuario;

	@ManagedProperty(value = "#{consultaGenerica}")
	private ConsultaGenericaController filtrosConsulta;

	@ManagedProperty(value = "#{consultaGenericaAdmon}")
	private ConsultaGenericaControllerAdmon filtrosConsultaGenerico;

	@ManagedProperty(value = "#{consultaGenericaDeleg}")
	private ConsultaGenericaControllerDelegacion filtrosConsultaDelegacional;

	@EJB
	private SolicitudServiceLocal solicitudCriteria;
	@EJB
	private AprobadoresServiceLocal aprobadoresService;
	@EJB
	private BitacoraServiceLocal bitacoraService;
	@EJB
	private MensajeriaSessionLocal mensajeriaService;

	/** Log de la clase */
	private static final Log logger = LogFactory
			.getLog(MantenimientoAprobadoresDialogMB.class);

	private static long ESTATUS_AUTORIZADO = 2;
	private static long ESTATUS_BAJA = 4;

	private static final SelectItem[] POSITION_AVAILABLE = {
			new SelectItem("bottom", "Bottom"), new SelectItem("top", "Top"),
			new SelectItem("both", "Both") };

	private boolean flag = true;
	private String position = POSITION_AVAILABLE[0].getValue().toString();
	private int rows = 10;
	private int startPage = 1;

	private String curp;
	private String matricula;

	private List<AprobadorDTO> solicitudes = new ArrayList<AprobadorDTO>();
	private List<AprobadorDTO> aprobadores = new ArrayList<AprobadorDTO>();

	private SolicitudDTO sol = new SolicitudDTO();

	private AprobadorDTO usuarioAdd = null;
	private AprobadorDTO aprobadorDel = null;

   	private RowStateMap apr = new RowStateMap();
   	private RowStateMap usr = new RowStateMap();

	public String inicializaConsultaAprobadores() {

		solicitudes = new ArrayList<AprobadorDTO>();
		aprobadores = new ArrayList<AprobadorDTO>();
		buscaCambios();
		return "buscaAprobadores";
	}





	public void inicializaRegistroAdmin(AjaxBehaviorEvent e) {
		try {
			if (usuario.getAprobadorSession().getTipoAprobador() == 2 || usuario.getAprobadorSession().getTipoAprobadorDpes() == 11332)
				sol = filtrosConsultaDelegacional.llenaFiltro();
			else if (usuario.getAprobadorSession().getTipoAprobador() == 1 || usuario.getAprobadorSession().getTipoAprobador() == 3)
				sol = filtrosConsultaGenerico.llenaFiltro();
			else
				sol = filtrosConsulta.llenaFiltro();
			refresh();
		} catch (AdmonUsuariosException e1) {
			e1.printStackTrace();
		}
	}

	public String actualiza() throws AdmonUsuariosException {
		solicitudes = new ArrayList<AprobadorDTO>();
		aprobadores = new ArrayList<AprobadorDTO>();
		buscaCambios();
		return "buscaAprobadores";
	}

	public void refresh() throws AdmonUsuariosException {
		solicitudes = new ArrayList<AprobadorDTO>();
		aprobadores = new ArrayList<AprobadorDTO>();
		buscaCambios();
	}

	private void buscaCambios() {
		try {
			logger.info("::: Invoca search solicitudes - buscaCambios");
			List<SolicitudDTO> sols = solicitudCriteria.searchSolicitudes(sol, " ");
			if (sols != null && sols.size() > 0) {
				aprobadores = aprobadoresService
						.consultaAprobadoresBySolicitud(sols);
				solicitudes = getSolicitudesListAprobadores(sols);
			} else
				solicitudes = new ArrayList<AprobadorDTO>();
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::getDeptoDTO " + aue.getMessage());
			logger.error("  ", aue);
		}
	}

	private List<SolicitudDTO> addSolsMismoNivel(List<SolicitudDTO> sols)
			throws AdmonUsuariosException {
		List<SolicitudDTO> result = new ArrayList<SolicitudDTO>();
		for (SolicitudDTO s : sols) {
			result.add(s);
		}
		SolicitudDTO filtro = getfiltroAreaAds(usuario.getAprobadorSession()
				.getSolicitud());
		logger.info("::: Invoca search solicitudes - addSolsMismoNivel");
		List<SolicitudDTO> sols2 = solicitudCriteria.searchSolicitudes(filtro,
				usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
		for (SolicitudDTO s2 : sols2) {
			result.add(s2);
		}
		return result;
	}

	private SolicitudDTO getfiltroAreaAds(SolicitudDTO sol) {
		SolicitudDTO result = new SolicitudDTO();
		result.setDelDTO(sol.getDelDTO());
		result.setSubdelDTO(sol.getSubdelDTO());
		result.setUmfDTO(sol.getUmfDTO());
		result.setDptoDTO(sol.getDptoDTO());
		result.setPuestoDTO(sol.getPuestoDTO());
		EstatusDTO es = new EstatusDTO();
		es.setCveSsoestatus(ESTATUS_AUTORIZADO);
		result.setEstatusDTO(es);
		return result;
	}

	private List<AprobadorDTO> getSolicitudesListAprobadores(
			List<SolicitudDTO> sols) {
		List<AprobadorDTO> result = new ArrayList<AprobadorDTO>();
		for (SolicitudDTO sol : sols) {
			boolean agrega = true;
			AprobadorDTO dto = null;
			if (aprobadores != null && aprobadores.size() > 0) {
				for (AprobadorDTO ap : aprobadores) {
					if (ap.getSolicitud().getCveSsosolicitud() == sol
							.getCveSsosolicitud()) {
						if (ap.getEstatus().getCveSsoestatus() == ESTATUS_AUTORIZADO) {
							agrega = false;
							dto = ap;
							break;
						} else {
							dto = ap;
							break;
						}
					}
				}
			}
			if (agrega) {
				if (dto == null)
					dto = new AprobadorDTO();
				else
					aprobadores.remove(dto);
				dto.setMatricula(sol.getCveMatricula());
				dto.setSolicitud(sol);
				result.add(dto);
			}
		}
		return result;
	}

	public void agregaAprobador(SelectEvent event) {
		usuarioAdd = (AprobadorDTO) event.getObject();
	}

	public void agregaUsuario(SelectEvent event) {
		aprobadorDel = (AprobadorDTO) event.getObject();
	}

	public String guardaCambios() throws AdmonUsuariosException {
		System.out.println("si entra");
		return inicializaConsultaAprobadores();
	}

	public String otogarPermisos() throws AdmonUsuariosException {

		if (aprobadores != null && aprobadores.size() < 1) {
			if (usuarioAdd.getSolicitud().getCveMatricula() != null) {
				aprobadoresService.addAprobador(usuarioAdd);
				solicitudes.remove(usuarioAdd);
				aprobadores.add(usuarioAdd);
				bitacoraService.guardaAprobadorBit(usuarioAdd.getSolicitud()
						.getCveSsosolicitud(), usuario.getAprobadorSession()
						.getCveIdAprobador(), true);
				filtrosConsulta
						.showMsg("Se le asignaron  permisos  de aprobador al usuario: "
								+ usuarioAdd.getSolicitud().getNombreCompleto());
				enviaCorreoInformativo(usuarioAdd.getSolicitud(),
						"Mantenimiento de aprobadores",
						"se le otorgaron permisos de aprobador");
			} else {
				filtrosConsulta
						.showMsg("El usuario no pertenece a la nomina IMSS: "
								+ usuarioAdd.getSolicitud().getNombreCompleto());
			}
		} else {
			filtrosConsulta
					.showMsg("Solo se permite otorgar los permisos de administrador a un solo usuario por nivel de adscripción");
		}
		return "buscaAprobadores";
	}

	public void otogarPermisos2() {

		try {
//			if (aprobadores != null && aprobadores.size() < 1) {
				if (usuarioAdd.getSolicitud().getCveMatricula() != null) {
					Long tipoAprobador = null;
					
					if (usuario.getAprobadorSession().getTipoAprobador() == 1 || usuario.getAprobadorSession().getTipoAprobador() == 11) {
						tipoAprobador = 11L;
					} else if (usuario.getAprobadorSession().getTipoAprobador() == 3 || usuario.getAprobadorSession().getTipoAprobador() == 33) {
						tipoAprobador = 33L;
					}
					
					if (tipoAprobador != null) {
						aprobadoresService.addAprobadorDirDpes(usuarioAdd, tipoAprobador.longValue());
					} else {
						aprobadoresService.addAprobador(usuarioAdd);
					}
					
					solicitudes.remove(usuarioAdd);
					aprobadores.add(usuarioAdd);
					bitacoraService.guardaAprobadorBit(usuarioAdd
							.getSolicitud().getCveSsosolicitud(), usuario
							.getAprobadorSession().getCveIdAprobador(), true);
					filtrosConsulta
							.showMsg("Se le asignaron  permisos  de aprobador al usuario: "
									+ usuarioAdd.getSolicitud()
											.getNombreCompleto());
					enviaCorreoInformativo(usuarioAdd.getSolicitud(),
							"Mantenimiento de aprobadores",
							"se le otorgaron permisos de aprobador");
				} else {
					filtrosConsulta
							.showMsg("El usuario no pertenece a la nomina IMSS: "
									+ usuarioAdd.getSolicitud()
											.getNombreCompleto());
				}
//			} else {
//				filtrosConsulta
//						.showMsg("Solo se permite otorgar los permisos de administrador a un solo usuario por nivel de adscripción");
//			}
		} catch (AdmonUsuariosException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void buscaCuentas(SelectEvent ev) throws AdmonUsuariosException {
		System.out.println("si entra");
	}

	public String revocarPermisos() throws AdmonUsuariosException {

		if (aprobadores != null && aprobadores.size() >= 1) {
			aprobadoresService.delAprobador(aprobadorDel);
			aprobadores.remove(aprobadorDel);
			solicitudes.add(aprobadorDel);
			bitacoraService.guardaAprobadorBit(aprobadorDel.getSolicitud()
					.getCveSsosolicitud(), usuario.getAprobadorSession()
					.getCveIdAprobador(), false);
			filtrosConsulta
					.showMsg("Se le revocaron  permisos  de aprobador al usuario: "
							+ aprobadorDel.getSolicitud().getNombreCompleto());
			enviaCorreoInformativo(aprobadorDel.getSolicitud(),
					"Mantenimiento de aprobadores",
					"se le revocaron permisos de aprobador");
		} else {
			filtrosConsulta
					.showMsg("Se requiere seleccionar un usuario valido.");
		}

		return "buscaAprobadores";
	}

	public void revocarPermisos2() {

		try {
			if (aprobadores != null && aprobadores.size() >= 1) {
				aprobadoresService.delAprobador(aprobadorDel);
				aprobadores.remove(aprobadorDel);
				solicitudes.add(aprobadorDel);
				bitacoraService.guardaAprobadorBit(aprobadorDel.getSolicitud()
						.getCveSsosolicitud(), usuario.getAprobadorSession()
						.getCveIdAprobador(), false);
				filtrosConsulta
						.showMsg("Se le revocaron  permisos  de aprobador al usuario: "
								+ aprobadorDel.getSolicitud()
										.getNombreCompleto());
				enviaCorreoInformativo(aprobadorDel.getSolicitud(),
						"Mantenimiento de aprobadores",
						"se le revocaron permisos de aprobador");
			} else {
				filtrosConsulta
						.showMsg("Se requiere seleccionar un usuario valido.");
			}
		} catch (AdmonUsuariosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void enviaCorreoInformativo(SolicitudDTO sol, String titulo,
			String msg) {
		sol.setAprobador(usuario.getAprobadorSession());
		String remitente = "serviciosdigitales@imss.gob.mx";
		System.out.println("Voy a enviar correo desde el nuevo remitente DialogMB [ " + remitente + " ]");
		try {
			mensajeriaService.enviarCorreo(remitente,
					sol.getRefCorreoElectronico(), msg, titulo, sol,
					"Modificación de permisos de aprobador");
			mensajeriaService.enviarCorreoAprobador(remitente, usuario.getAprobadorSession()
					.getSolicitud().getRefCorreoElectronico(), msg, titulo,
					sol, "Modificación de permisos de aprobador");
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
			System.out
					.println("Error al enviar correo informativo de modificacion de perfiles de aprobador- MantenimientoAprobadores");
		}
	}

	public boolean isPaginator() {
		return flag;
	}

	public void setPaginator(boolean flag) {
		this.flag = flag;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public UsuarioMB getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioMB usuario) {
		this.usuario = usuario;
	}

	public List<AprobadorDTO> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<AprobadorDTO> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public List<AprobadorDTO> getAprobadores() {
		return aprobadores;
	}

	public void setAprobadores(List<AprobadorDTO> aprobadores) {
		this.aprobadores = aprobadores;
	}

	public SolicitudServiceLocal getSolicitudCriteria() {
		return solicitudCriteria;
	}

	public void setSolicitudCriteria(SolicitudServiceLocal solicitudCriteria) {
		this.solicitudCriteria = solicitudCriteria;
	}

	public AprobadoresServiceLocal getAprobadoresService() {
		return aprobadoresService;
	}

	public void setAprobadoresService(AprobadoresServiceLocal aprobadoresService) {
		this.aprobadoresService = aprobadoresService;
	}

	public AprobadorDTO getUsuarioAdd() {
		return usuarioAdd;
	}

	public void setUsuarioAdd(AprobadorDTO usuarioAdd) {
		this.usuarioAdd = usuarioAdd;
	}

	public AprobadorDTO getAprobadorDel() {
		return aprobadorDel;
	}

	public void setAprobadorDel(AprobadorDTO aprobadorDel) {
		this.aprobadorDel = aprobadorDel;
	}

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}

	public SolicitudDTO getSol() {
		return sol;
	}

	public void setSol(SolicitudDTO sol) {
		this.sol = sol;
	}

	public ConsultaGenericaControllerAdmon getFiltrosConsultaGenerico() {
		return filtrosConsultaGenerico;
	}

	public void setFiltrosConsultaGenerico(
			ConsultaGenericaControllerAdmon filtrosConsultaGenerico) {
		this.filtrosConsultaGenerico = filtrosConsultaGenerico;
	}

	public ConsultaGenericaControllerDelegacion getFiltrosConsultaDelegacional() {
		return filtrosConsultaDelegacional;
	}

	public void setFiltrosConsultaDelegacional(
			ConsultaGenericaControllerDelegacion filtrosConsultaDelegacional) {
		this.filtrosConsultaDelegacional = filtrosConsultaDelegacional;
	}

	public RowStateMap getApr() {
		return apr;
	}

	public void setApr(RowStateMap apr) {
		this.apr = apr;
	}

	public RowStateMap getUsr() {
		return usr;
	}

	public void setUsr(RowStateMap usr) {
		this.usr = usr;
	}

	
}
