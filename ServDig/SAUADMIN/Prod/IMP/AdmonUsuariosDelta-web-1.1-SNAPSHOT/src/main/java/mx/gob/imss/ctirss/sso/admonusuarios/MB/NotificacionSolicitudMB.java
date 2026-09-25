package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.sso.admonusuarios.controller.ConsultaGenericaController;
import mx.gob.imss.ctirss.sso.admonusuarios.controller.ConsultaGenericaControllerAdmon;
import mx.gob.imss.ctirss.sso.admonusuarios.controller.ConsultaGenericaControllerDelegacion;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.NotificacionServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;

import org.icefaces.ace.event.DateSelectEvent;
import org.icefaces.ace.event.DateTextChangeEvent;
import org.icefaces.ace.event.SelectEvent;


@ManagedBean(name="notificarSolicitudMB")
@CustomScoped("#{window}")
public class NotificacionSolicitudMB {
	
	@ManagedProperty("#{usuarioMB}")	 
	private UsuarioMB usuarioMB;
	
	@ManagedProperty("#{solicitudMB}")	 
	private SolicitudMB solicitudMB;

	@EJB
	private NotificacionServiceLocal notificacionService;

	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;
	@ManagedProperty(value = "#{consultaGenericaAdmon}")
	private ConsultaGenericaControllerAdmon filtrosConsultaGenerico;
	@ManagedProperty(value = "#{consultaGenericaDeleg}")
	private ConsultaGenericaControllerDelegacion filtrosConsultaDelegacional;

	
	@EJB
	private MensajeriaSessionLocal mensajeriaService; 

	@EJB
	private BitacoraServiceLocal bitacoraService; 

	private List<ActivaCuentaDTO> lista = new ArrayList<ActivaCuentaDTO>();
	private int pagina = 2;
	private ActivaCuentaDTO activaCuentaSelect = null;
	private Date fechaVigencia = new Date(System.currentTimeMillis());
	private Date hoy = new Date(System.currentTimeMillis());
	private Properties props = new Properties();
	private SolicitudDTO sol = new SolicitudDTO();
	
   	public String inicializaConsultaNotificaciones(){
   		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
   		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
   		session.setAttribute("pagina", pagina);
		return "consultaNotificaciones";
	}
   	
    public void inicializaParametros() throws AdmonUsuariosException {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			obtenerNotificacionesByFiltro();
	    }
	}

    public void inicializaParametros2(AjaxBehaviorEvent e){
		try {
			obtenerNotificacionesByFiltro();
		} catch (AdmonUsuariosException e1) {
			e1.printStackTrace();
		}
	}

    public void cambiaTab(ValueChangeEvent event)  throws AdmonUsuariosException{
    }


	
	public void obtenerNotificacionesByFiltro() throws AdmonUsuariosException{

		if (usuarioMB.getAprobadorSession().getTipoAprobador() == 2 || usuarioMB.getAprobadorSession().getTipoAprobadorDpes() == 11332)
			sol = filtrosConsultaDelegacional.llenaFiltro();
		else if (usuarioMB.getAprobadorSession().getTipoAprobador() == 1 || usuarioMB.getAprobadorSession().getTipoAprobador() == 3)
			sol = filtrosConsultaGenerico.llenaFiltro();
		else
			sol = filtrosConsulta.llenaFiltro();

		lista = notificacionService.searchNotificaciones(sol);
	}
	
	public void selectListenerDetalle(SelectEvent event)  throws AdmonUsuariosException{
		activaCuentaSelect = (ActivaCuentaDTO)event.getObject();
		fechaVigencia = activaCuentaSelect.getFechaVigencia();
    }
	
    public void aplicaCambiosCuenta() throws AdmonUsuariosException{
    	try {
    			activaCuentaSelect.setFechaVigencia(fechaVigencia);
    			notificacionService.actualizaNotificaciones(activaCuentaSelect);
    			filtrosConsulta.setDesc("Se aplicaron exitosamente los cambios sobre la cuenta del usuario.");
				filtrosConsulta.setMsg(true);

			    inicializaParametros();
		} catch (Exception e) {
			filtrosConsulta.setDesc("Error al aplicar modificacion sobre la cuenta de usuario.");
			filtrosConsulta.setMsg(true);
			throw new AdmonUsuariosException("Error al aplicar modificacion sobre la cuenta de usuario");
		}
    }
    
    public void dateSelectListener(DateSelectEvent event) {
        this.fechaVigencia = event.getDate();
    }
    
    public void dateTextChangeListener(DateTextChangeEvent event){
        this.fechaVigencia = event.getDate();
    }


    private String generaLinkConfirmacion() throws AdmonUsuariosException{
    	try {
    		props.load(SolicitudMB.class.getResourceAsStream("/fqdn.properties"));
    		if(activaCuentaSelect!=null)
    			return props.getProperty("fqdn")+"/servlet/activaCuenta?cuenta="+activaCuentaSelect.getClaveMD5();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public void reenviarNotificacion() throws AdmonUsuariosException{
    	try {
			  	String msg = "se ha reenviado la notificaci&#243;n de la autorizaci&#243;n";
			  	String titulo = "AUTORIZACION Y REGISTRO DE CUENTA DE USUARIO";
			  	String link = generaLinkConfirmacion();
			  	System.out.println("el link completo es :" +link);
			  	activaCuentaSelect.getSolicitud().setAprobador(usuarioMB.getAprobadorSession());
			  	
			  	bitacoraService.guardaSolicitudBit(activaCuentaSelect.getSolicitud().getCveSsosolicitud(),usuarioMB.getAprobadorSession().getCveIdAprobador(),Constantes.TIPO_MOV_REENVIO_NOTIF);
			  	mensajeriaService.enviarCorreoConconfirmacion(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), activaCuentaSelect.getSolicitud().getRefCorreoElectronico(), msg, titulo,activaCuentaSelect.getSolicitud(),"Reenvio de confirmación de autorización",link);

			  	filtrosConsulta.showMsg("Se reenvió exitosamente la notificación de la autorización.");
			  	
			  	inicializaParametros();
		} catch (Exception e) {
			filtrosConsulta.showMsg("Error al aplicar modificación sobre la cuenta de usuario.");
			throw new AdmonUsuariosException("Error al aplicar modificacion sobre la cuenta de usuario");
		}
    }

	public SolicitudMB getSolicitudMB() {
		return solicitudMB;
	}

	public void setSolicitudMB(SolicitudMB solicitudMB) {
		this.solicitudMB = solicitudMB;
	}

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}

	public List<ActivaCuentaDTO> getLista() {
		return lista;
	}

	public void setLista(List<ActivaCuentaDTO> lista) {
		this.lista = lista;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public ActivaCuentaDTO getActivaCuentaSelect() {
		return activaCuentaSelect;
	}

	public void setActivaCuentaSelect(ActivaCuentaDTO activaCuentaSelect) {
		this.activaCuentaSelect = activaCuentaSelect;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public Date getHoy() {
		return hoy;
	}

	public void setHoy(Date hoy) {
		this.hoy = hoy;
	}

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
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
	
	
	
}
