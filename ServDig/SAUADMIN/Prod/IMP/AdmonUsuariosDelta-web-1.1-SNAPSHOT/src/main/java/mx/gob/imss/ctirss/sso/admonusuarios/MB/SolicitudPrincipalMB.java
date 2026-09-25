package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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
import mx.gob.imss.ctirss.sso.admonusuarios.controller.RecuperaSolicitudMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.ActivaCuentaServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.SolicitudCuentaSessionLocal;

import org.apache.log4j.Logger;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.model.table.RowStateMap;

@ManagedBean(name="solicitudPrincipalMB")
@CustomScoped("#{window}")
public class SolicitudPrincipalMB{

	private final static Logger logger = Logger.getLogger(SolicitudPrincipalMB.class);
	
	@ManagedProperty("#{usuarioMB}")	 
	private UsuarioMB usuarioMB;
	@ManagedProperty("#{modificarSolicitudMB}")
	private ModificarSolicitudMB modificarSolicitudMB;
	@ManagedProperty("#{solicitudMB}")
	private SolicitudMB solicitudMB;
	@ManagedProperty("#{recuperaSolicitudMB}")
	private RecuperaSolicitudMB recuperaSolicitudMB;

	
	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;
	@ManagedProperty(value="#{consultaGenericaDeleg}")	 
	private ConsultaGenericaControllerDelegacion filtrosConsultaDeleg;
	@ManagedProperty(value="#{consultaGenericaAdmon}")	 
	private ConsultaGenericaControllerAdmon filtrosConsultaAdmon;

	
	
	@EJB
	private MensajeriaSessionLocal mensajeriaService; 
	@EJB
	private SolicitudServiceLocal solicitudCriteria;
	@EJB
	private ActivaCuentaServiceLocal activaCuentaService;
	@EJB
	private CatalogoServiceLocal catalogoService;
	@EJB
	private AdmonUsuariosSessionLocal admonUsuarios;
	@EJB
	private AdmonRolesSessionLocal admonRoles;
	@EJB
	private BitacoraServiceLocal bitacoraService;
	@EJB
	private SolicitudCuentaSessionLocal solicitudCuentaService;
	@EJB
	private AprobadoresServiceLocal aprobadoresService;
	
	private boolean flagLoad;
	private Properties props = new Properties();
	private List<SolicitudDTO> lista = new ArrayList<SolicitudDTO>();
	private List<SolicitudDTO> lista2 = new ArrayList<SolicitudDTO>();
	private int idBoton = 0;
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>();
	private Long idSolicitud = 0L;

	private Long idDeptoGral;
	private SolicitudDTO filtro;
	
	private int claveDepartamentoSub;
	private int clavePuestoSub;

	private boolean disabledBotonAdd = false;
	private boolean disabledBotonAutorizar = true;
	private boolean disabledBotonRechazar = true;
	private boolean disabledBotonBorrar = true;
	private int pagina = 1;
	
	private Long clavePuesto;
	private Long claveModulo;
	public static long ESTATUS_SOLICITADO = 1;

	private String correo = "";
	private String telefono = "";

	private String cuentaSeleccionada="";
	
	private boolean flgMod = false;
	
	private boolean activoNomina = false;
	private boolean flagFirstSlide= false;
	
	private SolicitudDTO solicitud;
	
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	//private static String CURP_PATTERN = "([A-Z-a-z]{4})([0-9]{6})([A-Z-a-z]{6})([A-Z-a-z-0-9]{1})([0-9]{1})";

	private String curp=""; 
	private boolean botonAdd = true;

	private String curpValidado;
	private String curpAeliminar;
	
	private boolean errorFlag;
	private boolean usuarioInterno;
	private boolean inactiveEmployeeFlag;
	private boolean registroUsuario = false;

	private boolean flag4;
	private boolean flag3;
	private boolean flag2;
	private boolean flag1;
	private boolean flagSecondSlide;
	private boolean flagThirdSlide;
	private boolean flagCambiaCurp;
	
	private boolean msg = false;
	private String msgDesc = "";

	private RowStateMap stateMapFromTableOne = new RowStateMap();
	private RowStateMap stateMapFromTableTwo = new RowStateMap();
	
	private static int ESTATUS_BAJA_SOLICITUD = 4;
	private static int ESTATUS_BAJA_CURP_SOLICITUD = 6;
	
	public String regresaPantallaPrincipal()
	{
		limpiaMensajes();
		if(usuarioMB.getAprobadorSession().getTipoAprobador()==2 || usuarioMB.getAprobadorSession().getTipoAprobadorDpes() == 11332)
		{
			refreshDel();
			return "consultaDelegacional";
		}
		else
		{
			if(usuarioMB.getAprobadorSession().getTipoAprobador()==1)
			{
				refreshGenerico();
				return "consultaGenerica";
			}
			else
			{
				refresh();
				return "consultaPrincipal";
			}
		}
	}
	
	public void limpiaMensajes(){
		filtrosConsulta.hide();
		filtrosConsultaDeleg.hide();
		filtrosConsultaAdmon.hide();
	}

	
	public void borraCuenta(AjaxBehaviorEvent e){
		  String cta = (String)e.getComponent().getAttributes().get("cuenta");
		  logger.info("###### La cuenta seleccionada a borrar es: " + cta);
		  solicitud = obtenCuenta(cta);
	}   

	
	public void borraCuentaCurp(AjaxBehaviorEvent e){
		  String cta = (String)e.getComponent().getAttributes().get("cuenta");
		  logger.info("###### La cuenta seleccionada a borrrar es: " + cta);
		  solicitud = obtenCuenta(cta);
	}   

	public void autorizarCuenta(AjaxBehaviorEvent e){
		  String cta = (String)e.getComponent().getAttributes().get("cuenta");
		  logger.info("########## LA CUENTA SELECCIONADA PARA AUTORIZAR ES [" + cta + "] ##########");
		  solicitudMB.setSolicitudExtPorModulo(obtenCuenta(cta));
		  solicitudMB.selectListenerDetalle();
	}   

	public void modificarCuenta(AjaxBehaviorEvent e){
		  String cta = (String)e.getComponent().getAttributes().get("cuenta");
		  logger.info("########## LA CUENTA SELECCIONADA PARA MODIFICAR ES [" + cta + "] EN LA CLASE SOLICITUD PRINCIPAL MB ##########");
		  solicitud = obtenCuenta(cta);
		  modificarSolicitudMB.setSolicitud(solicitud);
		  
		  if(solicitud.getRefCorreoElectronico()!=null)
			  modificarSolicitudMB.setCorreo(solicitud.getRefCorreoElectronico().replace("@imss.gob.mx", ""));
		  modificarSolicitudMB.setTelefono(solicitud.getDesTelefonoOfi());
		  modificarSolicitudMB.selectListenerDetalle();
	}   

	public void recuperarCuenta(AjaxBehaviorEvent e){
//		  String cta = (String)e.getComponent().getAttributes().get("cuenta");
//		  System.out.println("La cuenta seleccionada es :"+cta);
//		  SolicitudDTO sol = obtenCuenta(cta);
//		  recuperaSolicitudMB.limpia();
//		  recuperaSolicitudMB.setSolicitud(obtenCuenta(cta));
//		  recuperaSolicitudMB.setTelefonosol(sol.getDesTelefonoOfi());
//		  recuperaSolicitudMB.setCorreo(sol.getRefCorreoElectronico().replaceAll("@imss.gob.mx", ""));
//		  recuperaSolicitudMB.getSolicitud().setPuestosDTO(new ArrayList<PuestoDTO>());
//		  recuperaSolicitudMB.getSolicitud().setModulosDTO(new ArrayList<ModuloDTO>());
		  
		  filtrosConsulta.obtieneDatosUsuario();
	}   


	public String inicializaAprobarSolicitud() throws AdmonUsuariosException{
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
   		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
   		session.setAttribute("pagina", pagina);
		lista = null;
   		filtrosConsulta.obtieneDatosUsuario();
		return "aprobacionSolPrin";
		
	}

	private SolicitudDTO obtenCuenta(String cta) {
		if(lista!=null&&lista.size()>0)
		{
			for(SolicitudDTO sol : lista)
			{
				if(sol.getDesUsrCurp().equals(cta))
					return sol;
			}
		}
		return null;
	}
	
    public void borraPefil(AjaxBehaviorEvent event)  throws AdmonUsuariosException
    {
		Map<String, Object> attributes = event.getComponent().getAttributes();
		PerfilDTO perfil =(PerfilDTO)attributes.get("attPerfil");
		solicitud.getPerfilesDTO().remove(perfil);
		filtrosConsulta.setDesc("El Perfil fue eliminado de esta cuenta de usuario.");
		filtrosConsulta.setMsg(true);
	}


	public void lisTel(ValueChangeEvent event)  throws AdmonUsuariosException{
		telefono = (String)event.getNewValue();
    }
	
	public void lisMail(ValueChangeEvent event)  throws AdmonUsuariosException{
		correo = (String)event.getNewValue();
    }

	
	public void selectListenerDetalle(SelectEvent event)  throws AdmonUsuariosException{
		logger.info("******************entra a select listener detalle*****************************");
		solicitud = (SolicitudDTO)event.getObject();
		logger.info("el estatus de la solicitud seleccionada es: "+solicitud.getEstatusDTO().getCveSsoestatus());
		logger.info("el curp de la solicitud seleccionada es: "+solicitud.getDesUsrCurp());
		logger.info("******************sale de select listener detalle*****************************");
    }

	
	public String obtenerSolicitudes(){
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltro();
		filtro.setEstatusDTO(null);
		try {
			lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::obtenerSolicitudes " + aue.getMessage());
			logger.error("  ", aue);
		}
		idBoton = 2;
		return "consultaPrincipal";
	}

	public String obtenerSolicitudesDelegacional(){
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltroDelegacional();
		filtro.setEstatusDTO(null);
		try {
			lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::obtenerSolicitudesDelegacional " + aue.getMessage());
			logger.error("  ", aue);
		}
		idBoton = 2;
		return "consultaDelegacional";
	}


	public String obtenerSolicitudesGenerico(){
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltroGenerico();
		filtro.setEstatusDTO(null);
		try {
			lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::obtenerSolicitudesGenerico " + aue.getMessage());
			logger.error("  ", aue);
		}
		idBoton = 2;
		return "consultaGenerica";
	}


	public void llenaFiltro(){
		filtro = new SolicitudDTO();
		filtro.setDptoDTO(usuarioMB.getAprobadorSession().getSolicitud().getDptoDTO());
		// Delegacion
		if (filtrosConsulta.getClaveDelegacion() !=0 && filtrosConsulta.getClaveDelegacion() != -99) {
			DelegacionDTO del = new DelegacionDTO();
			del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
			filtro.setDelDTO(del);
		} else {			
			filtro.setDelDTO(null);
		}
		// Subdelegacion
		if (filtrosConsulta.getClaveSubdelegacion() !=0 && filtrosConsulta.getClaveSubdelegacion() != -99) {
			SubdelegacionDTO subdel = new SubdelegacionDTO();
			subdel.setCveSubelegacion(filtrosConsulta.getClaveSubdelegacion());
			filtro.setSubdelDTO(subdel);
		} else {
			filtro.setSubdelDTO(null);
		}
		// UMF
		if (filtrosConsulta.getClaveUMF() !=0 && filtrosConsulta.getClaveUMF() != -99) {
			UmfDTO umf = new UmfDTO();
			umf.setCveUmf(filtrosConsulta.getClaveUMF());
			filtro.setUmfDTO(umf);
		} else {
			filtro.setUmfDTO(null);
		}
		// Departamento
		if (filtrosConsultaAdmon.getClaveDepartamento() > 0) {
			DepartamentoDTO depto = new DepartamentoDTO();
			depto.setCveSsodepto(filtrosConsultaAdmon.getClaveDepartamento());
			filtro.setDptoDTO(depto);
		} else {
			filtro.setDptoDTO(null);
		}
	}


	public void llenaFiltroDelegacional(){
		filtro = new SolicitudDTO();
		
		filtro.setDptoDTO(usuarioMB.getAprobadorSession().getSolicitud().getDptoDTO());
		if(filtrosConsultaDeleg.getClaveDelegacion()!=0&&filtrosConsultaDeleg.getClaveDelegacion()!=-99)
		{
			DelegacionDTO del = new DelegacionDTO();
			del.setCveDelegacion(filtrosConsultaDeleg.getClaveDelegacion());
			filtro.setDelDTO(del);
		}
		else			
			filtro.setDelDTO(null);
		if(filtrosConsultaDeleg.getClaveSubdelegacion()!=0&&filtrosConsultaDeleg.getClaveSubdelegacion()!=-99)
		{
			SubdelegacionDTO subdel = new SubdelegacionDTO();
			subdel.setCveSubelegacion(filtrosConsultaDeleg.getClaveSubdelegacion());
			filtro.setSubdelDTO(subdel);
		}
		else
			filtro.setSubdelDTO(null);
		if(filtrosConsultaDeleg.getClaveUMF()!=0&&filtrosConsultaDeleg.getClaveUMF()!=-99)
		{
			UmfDTO umf = new UmfDTO();
			umf.setCveUmf(filtrosConsultaDeleg.getClaveUMF());
			filtro.setUmfDTO(umf);
			
		}
		else
			filtro.setUmfDTO(null);
	}

	public void llenaFiltroGenerico(){
		filtro = new SolicitudDTO();
		
		filtro.setDptoDTO(usuarioMB.getAprobadorSession().getSolicitud().getDptoDTO());
		if(filtrosConsultaAdmon.getClaveDelegacion()!=0&&filtrosConsultaAdmon.getClaveDelegacion()!=-99)
		{
			DelegacionDTO del = new DelegacionDTO();
			del.setCveDelegacion(filtrosConsultaAdmon.getClaveDelegacion());
			filtro.setDelDTO(del);
		}
		else			
			filtro.setDelDTO(null);
		if(filtrosConsultaAdmon.getClaveSubdelegacion()!=0&&filtrosConsultaAdmon.getClaveSubdelegacion()!=-99)
		{
			SubdelegacionDTO subdel = new SubdelegacionDTO();
			subdel.setCveSubelegacion(filtrosConsultaAdmon.getClaveSubdelegacion());
			filtro.setSubdelDTO(subdel);
		}
		else
			filtro.setSubdelDTO(null);
		if(filtrosConsultaAdmon.getClaveUMF()!=0&&filtrosConsultaAdmon.getClaveUMF()!=-99)
		{
			UmfDTO umf = new UmfDTO();
			umf.setCveUmf(filtrosConsultaAdmon.getClaveUMF());
			filtro.setUmfDTO(umf);
			
		}
		else
			filtro.setUmfDTO(null);
		if(filtrosConsultaAdmon.getClaveDepartamento()>0)
		{
			DepartamentoDTO depto = new DepartamentoDTO();
			depto.setCveSsodepto(filtrosConsultaAdmon.getClaveDepartamento());
			filtro.setDptoDTO(depto);
			
		}
		else
			filtro.setDptoDTO(null);
	}

	public void listenerPuesto(ValueChangeEvent ve){
		try{
			clavePuesto= new Long(ve.getNewValue().toString()).longValue();
		}
		catch(Exception e1){
			logger.error("Error::listenerPuesto " + e1.getMessage());
			logger.error("  ", e1);
		}
	}
	
	public void listenerModulo(ValueChangeEvent ve){
		try{
			claveModulo= new Long(ve.getNewValue().toString()).longValue();
		}
		catch(Exception e1){
			logger.error("Error::listenerModulo " + e1.getMessage());
			logger.error("  ", e1);
		}
	}    

	public String eliminarUsuario(String curp, int tipoBaja) throws AdmonUsuariosException {	
		Locale defloc = Locale.getDefault();
		String curpDel = curp.toUpperCase(defloc);
	    Long idSol = solicitudCuentaService.obtenerIDSolicitud(curpDel);
	    UsuarioDTO usuario = new UsuarioDTO();
	    solicitudCuentaService.actualizarSolicitudBDEliminacion(idSol, usuario, tipoBaja);
	    return "exitoGuardar";
	}
	
	public void bajaDeUsuario() {
		if (solicitud != null) {
			if (solicitud.getDesUsrCurp() != null && solicitud.getDesUsrCurp().length() > 0) {
				try {
					admonUsuarios.desactivarUsuario(solicitud.getDesUsrCurp());
				} catch (Exception e1) {
					logger.error("Error::bajaDeUsuario "+ e1.getMessage());
					logger.error("  ", e1);

					filtrosConsulta.setDesc("No fue posible dar de baja la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
				try {
					eliminarUsuario(solicitud.getDesUsrCurp(),ESTATUS_BAJA_SOLICITUD);
					aprobadoresService.bajaAprobador(solicitud.getDesUsrCurp());
					bitacoraService.guardaSolicitudBit(solicitud.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(),Constantes.TIPO_MOV_BAJA);
					solicitud.setAprobador(usuarioMB.getAprobadorSession());

					String titulo = "BAJA DE CUENTA DE USUARIO";
					String msg = "ha sido dada de baja";
					mensajeriaService.enviarCorreo(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(),solicitud.getRefCorreoElectronico(), msg, titulo,
							solicitud, "Baja de cuenta");
					mensajeriaService.enviarCorreoAprobador(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), usuarioMB.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), msg, titulo, solicitud,
							"Baja de cuenta");

					filtrosConsulta.setDesc("Se realizo exitosamente la baja de la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
					
				} catch (Exception e2) {
					logger.error("Error::bajaDeUsuario " + e2.getMessage());
					logger.error("  ", e2);

					filtrosConsulta.setDesc("No fue posible actualizar la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
			} else {
				filtrosConsulta.setDesc("La cuenta de usuario no cuenta con las caracteristicas necesarias para procesar la baja.");
				filtrosConsulta.setMsg(true);
			}
		} else {
			filtrosConsulta.setDesc("Seleccione una cuenta de usuario.");
			filtrosConsulta.setMsg(true);
		}
	}

	public void bajaDeUsuarioCambioCurp() {
		if (solicitud != null) {
			if (solicitud.getDesUsrCurp() != null&& solicitud.getDesUsrCurp().length() > 0) {
				try {
					admonUsuarios.desactivarUsuario(solicitud.getDesUsrCurp());
				} catch (Exception e1) {
					logger.error("Error::bajaDeUsuarioCambioCurp "+ e1.getMessage());
					logger.error("  ", e1);

					filtrosConsulta.setDesc("No fue posible dar de baja la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
				try {
					eliminarUsuario(solicitud.getDesUsrCurp(),ESTATUS_BAJA_CURP_SOLICITUD);
					bitacoraService.guardaSolicitudBit(solicitud
							.getCveSsosolicitud(), usuarioMB
							.getAprobadorSession().getCveIdAprobador(),
							Constantes.TIPO_MOV_BAJA_CURP);
					solicitud.setAprobador(usuarioMB.getAprobadorSession());

					String titulo = "BAJA DE CUENTA DE USUARIO";
					String msg = " ha sido dada de baja";
					mensajeriaService.enviarCorreo(usuarioMB
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(),
							solicitud.getRefCorreoElectronico(), msg, titulo,
							solicitud, "Baja de cuenta");
					mensajeriaService.enviarCorreoAprobador(usuarioMB
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), usuarioMB
							.getAprobadorSession().getSolicitud()
							.getRefCorreoElectronico(), msg, titulo, solicitud,
							"Baja de cuenta");

					filtrosConsulta.setDesc("Se realizo exitosamente la baja de la cuenta de usuario.");
					filtrosConsulta.setMsg(true);

				} catch (Exception e2) {
					logger.error("Error::bajaDeUsuarioCambioCurp "+ e2.getMessage());
					logger.error("  ", e2);

					filtrosConsulta.setDesc("No fue posible dar de baja la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
			} else {
				filtrosConsulta.setDesc("La cuenta de usuario no cuenta con las caracteristicas necesarias para procesar la baja.");
				filtrosConsulta.setMsg(true);
			}
		} else {
			filtrosConsulta.setDesc("Seleccione una cuenta de usuario.");
			filtrosConsulta.setMsg(true);
		}

	}

	public void actualizaLista(Long idEstatus)  throws AdmonUsuariosException{
		if(idBoton==1){
			lista = solicitudCriteria.solicitudesExternasByDepto(idDeptoGral, Constantes.ESTATUS.SOLICITADO.getOpcion());
			lista2 = solicitudCriteria.solicitudesExternasByDepto(idDeptoGral, idEstatus);
		}else if(idBoton==2){
			lista = solicitudCriteria.solicitudesByFiltro(filtro, Constantes.ESTATUS.SOLICITADO.getOpcion(), true,null);
			lista2 = solicitudCriteria.solicitudesByFiltro(filtro,idEstatus, true,null);
		}
	}

    public void controlaComponentes() throws AdmonUsuariosException{
    	logger.info("::: El estatus en nomina del registro es: " + activoNomina);
		if(activoNomina)
		{
			logger.info("::: El estatus de la solicitud validada es: " + solicitud.getEstatusDTO().getCveSsoestatus());
	    	if(solicitud.getEstatusDTO().getCveSsoestatus()==Constantes.ESTATUS.SOLICITADO.getOpcion()){
	    		logger.info("::: El valor de la solicitud es: " + solicitud.getEstatusDTO().getCveSsoestatus());
	    		disabledBotonBorrar = false;
				disabledBotonAutorizar = false;
				disabledBotonRechazar = false;
				disabledBotonAdd = false;
	    	}
			else{
				logger.info("::: El valor de la solicitud es: " + solicitud.getEstatusDTO().getCveSsoestatus());
				disabledBotonBorrar = true;
	    		disabledBotonAdd = true;
				disabledBotonAutorizar = true;
				disabledBotonRechazar = true;
			}
	    }
		else
		{
			logger.info("::: La solicitud se encuentra inactiva en nomina...");
			disabledBotonBorrar = true;
    		disabledBotonAdd = true;
			disabledBotonAutorizar = true;
			disabledBotonRechazar = true;
		}
    }
    
    public void refresh()
    {
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltro();
		filtro.setEstatusDTO(null);
		try {
			lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::refresh "+ aue.getMessage());
			logger.error("  ", aue);
		}
		idBoton = 2;
    }

    public void refreshDel()
    {
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltroDelegacional();
		filtro.setEstatusDTO(null);
		try {
			lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::refreshDel "+ aue.getMessage());
			logger.error("  ", aue);
		}
		idBoton = 2;
    }

    public void refreshGenerico()
    {
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltroGenerico();
		filtro.setEstatusDTO(null);
		try {
			lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		} catch (AdmonUsuariosException aue) {
			logger.error("Error::refreshGenerico "+ aue.getMessage());
			logger.error("  ", aue);
		}
		idBoton = 2;
    }

    public void deptosByAreaListener(ValueChangeEvent ve) throws AdmonUsuariosException{
		this.lstDepartamento.clear();
		Long idAreaNorm = (Long)ve.getNewValue();
		List<DepartamentoDTO> lista = new ArrayList<DepartamentoDTO>();
		if(idAreaNorm!=-99){
			lista = catalogoService.listarDepartamentosByAreaNormativa(idAreaNorm);
			for(DepartamentoDTO dl : lista){
				this.lstDepartamento.add(new SelectItem(dl.getCveSsodepto(), dl.getDesDepartamento()));
			}
		}
		disabledBotonAdd=true;
	}
    
    public void puestosByDeptoListener(ValueChangeEvent ve) throws AdmonUsuariosException{
		this.lstPuesto.clear();
		Long idDepto = (Long)ve.getNewValue();
		List<PuestoDTO> lista = new ArrayList<PuestoDTO>();
		if(idDepto!=-99){
			lista = catalogoService.listarPuestoByDepartamento(idDepto);
			for(PuestoDTO dl : lista){
				this.lstPuesto.add(new SelectItem(dl.getCvePuesto(), dl.getNombrePuesto()));
			}
		}
		disabledBotonAdd=true;
	}

    public void modulosByDeptoListener(ValueChangeEvent ve) throws AdmonUsuariosException{
    	this.lstModulo.clear();
    	Long idDepto = (Long)ve.getNewValue();
    	List<ModuloDTO> lista = new ArrayList<ModuloDTO>();
    	if(idDepto!=-99){
    		lista = solicitudCriteria.listarModulosByDepartamento(idDepto);
    		for(ModuloDTO dl : lista){
				this.lstModulo.add(new SelectItem(dl.getCveIdModulo(), dl.getDesModulo()));
			}
    	}
    	disabledBotonAdd=true;
    }
    
	
    public MensajeriaSessionLocal getMensajeriaService() {
		return mensajeriaService;
	}


	public void setMensajeriaService(MensajeriaSessionLocal mensajeriaService) {
		this.mensajeriaService = mensajeriaService;
	}


	public SolicitudServiceLocal getSolicitudCriteria() {
		return solicitudCriteria;
	}


	public void setSolicitudCriteria(SolicitudServiceLocal solicitudCriteria) {
		this.solicitudCriteria = solicitudCriteria;
	}


	public ActivaCuentaServiceLocal getActivaCuentaService() {
		return activaCuentaService;
	}


	public void setActivaCuentaService(ActivaCuentaServiceLocal activaCuentaService) {
		this.activaCuentaService = activaCuentaService;
	}


	public CatalogoServiceLocal getCatalogoService() {
		return catalogoService;
	}


	public void setCatalogoService(CatalogoServiceLocal catalogoService) {
		this.catalogoService = catalogoService;
	}


	public AdmonUsuariosSessionLocal getAdmonUsuarios() {
		return admonUsuarios;
	}


	public void setAdmonUsuarios(AdmonUsuariosSessionLocal admonUsuarios) {
		this.admonUsuarios = admonUsuarios;
	}


	public AdmonRolesSessionLocal getAdmonRoles() {
		return admonRoles;
	}


	public void setAdmonRoles(AdmonRolesSessionLocal admonRoles) {
		this.admonRoles = admonRoles;
	}


	public BitacoraServiceLocal getBitacoraService() {
		return bitacoraService;
	}


	public void setBitacoraService(BitacoraServiceLocal bitacoraService) {
		this.bitacoraService = bitacoraService;
	}


	public Properties getProps() {
		return props;
	}


	public void setProps(Properties props) {
		this.props = props;
	}


	public List<SolicitudDTO> getLista2() {
		return lista2;
	}


	public void setLista2(List<SolicitudDTO> lista2) {
		this.lista2 = lista2;
	}


	public int getIdBoton() {
		return idBoton;
	}


	public void setIdBoton(int idBoton) {
		this.idBoton = idBoton;
	}


	public Long getIdDeptoGral() {
		return idDeptoGral;
	}


	public void setIdDeptoGral(Long idDeptoGral) {
		this.idDeptoGral = idDeptoGral;
	}


	public boolean isFlagFirstSlide() {
		return flagFirstSlide;
	}


	public void setFlagFirstSlide(boolean flagFirstSlide) {
		this.flagFirstSlide = flagFirstSlide;
	}


	public void cambiaTab(ValueChangeEvent event)  throws AdmonUsuariosException{
    	limpiaCombos();
    }
    
    public void cambiaTab2(ValueChangeEvent event)  throws AdmonUsuariosException{
    }

    public void limpiaCombos()  throws AdmonUsuariosException{
    }
    
	public List<SolicitudDTO> getLista() {
		return lista;
	}

	public void setLista(List<SolicitudDTO> lista) {
		this.lista = lista;
	}

	public List<SelectItem> getLstAreaNormativa() {
		return lstAreaNormativa;
	}


	public void setLstAreaNormativa(List<SelectItem> lstAreaNormativa) {
		this.lstAreaNormativa = lstAreaNormativa;
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

	public boolean isDisabledBotonAdd() {
		return disabledBotonAdd;
	}

	public void setDisabledBotonAdd(boolean disabledBotonAdd) {
		this.disabledBotonAdd = disabledBotonAdd;
	}

	public boolean isDisabledBotonAutorizar() {
		return disabledBotonAutorizar;
	}

	public void setDisabledBotonAutorizar(boolean disabledBotonAutorizar) {
		this.disabledBotonAutorizar = disabledBotonAutorizar;
	}

	public boolean isDisabledBotonRechazar() {
		return disabledBotonRechazar;
	}

	public void setDisabledBotonRechazar(boolean disabledBotonRechazar) {
		this.disabledBotonRechazar = disabledBotonRechazar;
	}

	public boolean isDisabledBotonBorrar() {
		return disabledBotonBorrar;
	}

	public void setDisabledBotonBorrar(boolean disabledBotonBorrar) {
		this.disabledBotonBorrar = disabledBotonBorrar;
	}

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	public SolicitudDTO getFiltro() {
		return filtro;
	}

	public void setFiltro(SolicitudDTO filtro) {
		this.filtro = filtro;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}

	public Long getClavePuesto() {
		return clavePuesto;
	}

	public void setClavePuesto(Long clavePuesto) {
		this.clavePuesto = clavePuesto;
	}

	public Long getClaveModulo() {
		return claveModulo;
	}

	public void setClaveModulo(Long claveModulo) {
		this.claveModulo = claveModulo;
	}


	public boolean isActivoNomina() {
		return activoNomina;
	}


	public void setActivoNomina(boolean activoNomina) {
		this.activoNomina = activoNomina;
	}


	public String getCuentaSeleccionada() {
		return cuentaSeleccionada;
	}


	public void setCuentaSeleccionada(String cuentaSeleccionada) {
		this.cuentaSeleccionada = cuentaSeleccionada;
	}


	public boolean isFlgMod() {
		return flgMod;
	}


	public void setFlgMod(boolean flgMod) {
		this.flgMod = flgMod;
	}


	public SolicitudDTO getSolicitud() {
		return solicitud;
	}


	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getCurp() {
		return curp;
	}


	public void setCurp(String curp) {
		this.curp = curp;
	}


	public boolean isFlagLoad() {
		return flagLoad;
	}


	public void setFlagLoad(boolean flagLoad) {
		this.flagLoad = flagLoad;
	}


	public Long getIdSolicitud() {
		return idSolicitud;
	}


	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}


	public boolean isBotonAdd() {
		return botonAdd;
	}


	public void setBotonAdd(boolean botonAdd) {
		this.botonAdd = botonAdd;
	}


	public String getCurpValidado() {
		return curpValidado;
	}


	public void setCurpValidado(String curpValidado) {
		this.curpValidado = curpValidado;
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


	public boolean isUsuarioInterno() {
		return usuarioInterno;
	}


	public void setUsuarioInterno(boolean usuarioInterno) {
		this.usuarioInterno = usuarioInterno;
	}


	public boolean isInactiveEmployeeFlag() {
		return inactiveEmployeeFlag;
	}


	public void setInactiveEmployeeFlag(boolean inactiveEmployeeFlag) {
		this.inactiveEmployeeFlag = inactiveEmployeeFlag;
	}


	public boolean isRegistroUsuario() {
		return registroUsuario;
	}


	public void setRegistroUsuario(boolean registroUsuario) {
		this.registroUsuario = registroUsuario;
	}


	public boolean isFlag4() {
		return flag4;
	}


	public void setFlag4(boolean flag4) {
		this.flag4 = flag4;
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
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
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
	public boolean isFlagCambiaCurp() {
		return flagCambiaCurp;
	}
	public void setFlagCambiaCurp(boolean flagCambiaCurp) {
		this.flagCambiaCurp = flagCambiaCurp;
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
	public ModificarSolicitudMB getModificarSolicitudMB() {
		return modificarSolicitudMB;
	}
	public void setModificarSolicitudMB(ModificarSolicitudMB modificarSolicitudMB) {
		this.modificarSolicitudMB = modificarSolicitudMB;
	}
	public void setSolicitudMB(SolicitudMB solicitudMB) {
		this.solicitudMB = solicitudMB;
	}
	public SolicitudMB getSolicitudMB() {
		return solicitudMB;
	}
	public RecuperaSolicitudMB getRecuperaSolicitudMB() {
		return recuperaSolicitudMB;
	}
	public void setRecuperaSolicitudMB(RecuperaSolicitudMB recuperaSolicitudMB) {
		this.recuperaSolicitudMB = recuperaSolicitudMB;
	}
	public SolicitudCuentaSessionLocal getSolicitudCuentaService() {
		return solicitudCuentaService;
	}
	public void setSolicitudCuentaService(
			SolicitudCuentaSessionLocal solicitudCuentaService) {
		this.solicitudCuentaService = solicitudCuentaService;
	}
	public AprobadoresServiceLocal getAprobadoresService() {
		return aprobadoresService;
	}
	public void setAprobadoresService(AprobadoresServiceLocal aprobadoresService) {
		this.aprobadoresService = aprobadoresService;
	}
	public ConsultaGenericaControllerDelegacion getFiltrosConsultaDeleg() {
		return filtrosConsultaDeleg;
	}
	public void setFiltrosConsultaDeleg(
			ConsultaGenericaControllerDelegacion filtrosConsultaDeleg) {
		this.filtrosConsultaDeleg = filtrosConsultaDeleg;
	}
	public ConsultaGenericaControllerAdmon getFiltrosConsultaAdmon() {
		return filtrosConsultaAdmon;
	}
	public void setFiltrosConsultaAdmon(
			ConsultaGenericaControllerAdmon filtrosConsultaAdmon) {
		this.filtrosConsultaAdmon = filtrosConsultaAdmon;
	}

}
