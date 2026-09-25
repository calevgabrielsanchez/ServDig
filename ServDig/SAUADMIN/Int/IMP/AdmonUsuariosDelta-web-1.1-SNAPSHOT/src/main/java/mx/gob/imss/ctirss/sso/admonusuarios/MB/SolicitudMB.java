package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.sso.admonusuarios.controller.ConsultaGenericaController;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
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
import mx.gob.imss.ctirss.sso.admonusuarios.service.ActivaCuentaServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.SolicitudServiceImpl;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;
import mx.gob.imss.ctirss.sso.util.PasswordUtil;

import org.apache.log4j.Logger;
import org.icefaces.ace.event.SelectEvent;

@ManagedBean(name="solicitudMB")
@SessionScoped
public class SolicitudMB{


	private final static Logger logger = Logger.getLogger(SolicitudMB.class);
	
	@ManagedProperty("#{usuarioMB}")	 
	private UsuarioMB usuarioMB;
	
	
	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;

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

	private Properties props = new Properties();
	private List<SolicitudDTO> lista = new ArrayList<SolicitudDTO>();
	private List<SolicitudDTO> lista2 = new ArrayList<SolicitudDTO>();
	private SolicitudDTO solicitudExtPorModulo = new SolicitudDTO();
	private int idBoton = 0;
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>();

	private Long idDeptoGral;
	private SolicitudDTO filtro;
	
	private boolean disabledBotonAdd = false;
	private boolean disabledBotonAutorizar = true;
	private boolean disabledBotonRechazar = true;
	private boolean disabledBotonBorrar = true;
	private int pagina = 1;
	
	private Long clavePuesto;
	private Long claveModulo;
	public static long ESTATUS_SOLICITADO = 1;
	
	
	private boolean activoNomina = false;
	
	public void listenerPuesto(ValueChangeEvent ve){
		try{
			clavePuesto= new Long(ve.getNewValue().toString()).longValue();
		}
		catch(Exception e){
			logger.info("Error al consultar el Puesto");
		}
	}    

	
	public void listenerModulo(ValueChangeEvent ve){
		try{
			claveModulo= new Long(ve.getNewValue().toString()).longValue();
		}
		catch(Exception e){
			logger.info("Error al consultar el Puesto");
		}
	}    

	public String inicializaAprobarSolicitud() throws AdmonUsuariosException{
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
   		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
   		session.setAttribute("pagina", pagina);
		lista = null;
   		filtrosConsulta.obtieneDatosUsuario();
		return obtenerSolByFiltroS();
		
	}

	@PostConstruct
    public void inicializaParametros() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
//			lista = new ArrayList<SolicitudDTO>();
//			lista2 = new ArrayList<SolicitudDTO>();
			solicitudExtPorModulo = new SolicitudDTO();
	    }
	}
	
	public void obtenerSolExtByDepto(ActionEvent event)  throws AdmonUsuariosException{
		llenaFiltro();
		idDeptoGral = filtro.getDptoDTO().getCveSsodepto();
		lista = solicitudCriteria.solicitudesExternasByDepto(idDeptoGral, Constantes.ESTATUS.AUTORIZADO.getOpcion());
		idBoton = 1;
	}

	public String obtenerSolExtByDepto()  throws AdmonUsuariosException{
		llenaFiltro();
		idDeptoGral = filtro.getDptoDTO().getCveSsodepto();
		lista = solicitudCriteria.solicitudesExternasByDepto(idDeptoGral, Constantes.ESTATUS.AUTORIZADO.getOpcion());
		idBoton = 1;
		return "aprobacionSol";
	}
	
	public void obtenerSolByFiltro() throws AdmonUsuariosException{
		lista = new ArrayList<SolicitudDTO>();
//		lista = solicitudCriteria.solicitudesByFiltro(filtro, Constantes.ESTATUS.SOLICITADO.getOpcion(), true,null);
		llenaFiltro();
		filtro.setEstatusDTO(new EstatusDTO(ESTATUS_SOLICITADO));
		logger.info("Invoca search solicitudes - SolicitadMB");
		lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		idBoton = 2;
	}


	
	public String obtenerSolByFiltroS() throws AdmonUsuariosException{
		lista = new ArrayList<SolicitudDTO>();
//		lista = solicitudCriteria.solicitudesByFiltro(filtro, Constantes.ESTATUS.SOLICITADO.getOpcion(), true,null);
		llenaFiltro();
		filtro.setEstatusDTO(new EstatusDTO(ESTATUS_SOLICITADO));
		logger.info("Invoca search solicitudes - SolicitadMB");
		lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		idBoton = 2;
		return "aprobacionSol";
	}

	public void llenaFiltro(){
		filtro = new SolicitudDTO();
		
		filtro.setDptoDTO(usuarioMB.getAprobadorSession().getSolicitud().getDptoDTO());
		if(filtrosConsulta.getClaveDelegacion()!=0&&filtrosConsulta.getClaveDelegacion()!=-99)
		{
			DelegacionDTO del = new DelegacionDTO();
			del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
			filtro.setDelDTO(del);
		}
		else			
			filtro.setDelDTO(usuarioMB.getAprobadorSession().getSolicitud().getDelDTO());
		if(filtrosConsulta.getClaveSubdelegacion()!=0&&filtrosConsulta.getClaveSubdelegacion()!=-99)
		{
			SubdelegacionDTO subdel = new SubdelegacionDTO();
			subdel.setCveSubelegacion(filtrosConsulta.getClaveSubdelegacion());
			filtro.setSubdelDTO(subdel);
		}
		else
			filtro.setSubdelDTO(usuarioMB.getAprobadorSession().getSolicitud().getSubdelDTO());
		if(filtrosConsulta.getClaveUMF()!=0&&filtrosConsulta.getClaveUMF()!=-99)
		{
			UmfDTO umf = new UmfDTO();
			umf.setCveUmf(filtrosConsulta.getClaveUMF());
			filtro.setUmfDTO(umf);
			
		}
		else
			filtro.setUmfDTO(usuarioMB.getAprobadorSession().getSolicitud().getUmfDTO());
	}
	
	public void borraPefil(AjaxBehaviorEvent event){
		try 
		{
			Map<String, Object> attributes = event.getComponent().getAttributes();
			PerfilDTO perfil =(PerfilDTO)attributes.get("attPerfil");
			solicitudCriteria.borrarPerfil(perfil);
			obtenerSolByFiltro();
			admonRoles.revocaRolUsuario(solicitudExtPorModulo.getDesUsrCurp(), solicitudCriteria.getPerfilDesc(perfil.getPuestoDTO().getCvePuesto()));
			solicitudExtPorModulo.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitudExtPorModulo.getCveSsosolicitud()));
			bitacoraService.guardaPuestoBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), perfil.getPuestoDTO().getCvePuesto(), false);
			filtrosConsulta.setDesc("El grupo seleccionado a sido eliminado de la cuenta de usuario.");
			filtrosConsulta.setMsg(true);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}


	public void agregaPerfil()  {
		try 
		{
			
			if(clavePuesto!=null&&clavePuesto>0)
			{
					PerfilDTO perfil = new PerfilDTO();
					DepartamentoDTO deptoDTO = new DepartamentoDTO();
					PuestoDTO puestoDTO = new PuestoDTO();
					SolicitudDTO solDTO = new SolicitudDTO();
					deptoDTO.setCveSsodepto(filtrosConsulta.getClaveDepartamentoRolAdd());
					puestoDTO.setCvePuesto(clavePuesto);
					solDTO.setCveSsosolicitud(solicitudExtPorModulo.getCveSsosolicitud());
					perfil.setDeptoDTO(deptoDTO);
					perfil.setPuestoDTO(puestoDTO);
					perfil.setSolicitudDTO(solDTO);
				if(solicitudCriteria.agregaPerfil(perfil)){
					admonRoles.asignaRolUsuario(solicitudExtPorModulo.getDesUsrCurp(), solicitudCriteria.getPerfilDesc(clavePuesto));
					obtenerSolByFiltro();
					solicitudExtPorModulo.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitudExtPorModulo.getCveSsosolicitud()));
					bitacoraService.guardaPuestoBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), clavePuesto, true);
					filtrosConsulta.setDesc("El grupo seleccionado ha sido agregado  a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
					
				}else{
					filtrosConsulta.setDesc("El grupo seleccionado ya esta asociado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
			}
			else
			{
				filtrosConsulta.setDesc("El grupo seleccionado no es valido.");
				filtrosConsulta.setMsg(true);
			}
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
	}

	public void validaNomina()  {
		logger.info("##### Entrando al metodo de ValidarNomina en SolicitudMB #####");
		try {
			if (solicitudExtPorModulo != null) {
				logger.info("##### CURP a validar [ " + solicitudExtPorModulo.getDesUsrCurp() + " ] en SIAP #####");
				ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
				UsuarioNominaResponse response = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(solicitudExtPorModulo.getDesUsrCurp(), "");
				if (response != null) {
					logger.info("##### Se encontro la CURP en el servicio de nomina SIAP #####");
					solicitudExtPorModulo.setNssNom(response.getNss());
					solicitudExtPorModulo.setPuestoDescNom(response.getPuestoDesc());
					solicitudExtPorModulo.setDepartamentoDescNom(response.getDepartamentoDesc());
					solicitudExtPorModulo.setCveMatricula(response.getMatricula());
					solicitudExtPorModulo.setCveDelegacionNom(response.getDelegacionCve());
					solicitudExtPorModulo.setCveEstatusNom(new Long(response.getEstatus()).longValue());
					
					if (solicitudExtPorModulo.getCveEstatusNom() == 2)	{
						logger.info("##### La CURP se encuentra inactiva en nomina SIAP #####");
						filtrosConsulta.showMsg("La cuenta de usuario validada se encuentra inactiva en nómina.");
						activoNomina = false;						
					} else {
						logger.info("##### La curp se encuentra activa en nomina SIAP #####");
						filtrosConsulta.showMsg("La cuenta de usuario se valido correctamente.");
						activoNomina = true;
					}
					controlaComponentes();
				} else {
					logger.info("##### No se encontro la CURP en SIAP, se procede a validar en servicio TTDS #####");
					logger.info("##### CURP a validar [ " + solicitudExtPorModulo.getDesUsrCurp() + " ] en TTDS #####");
					ClienteConsultaCurpTTDS ccct = new ClienteConsultaCurpTTDS();
					UsuarioTTD ut = ccct.invocarServicioConsultaCurpTTDS(solicitudExtPorModulo.getDesUsrCurp(), "");
					if (ut != null) {
						logger.info("##### Se encontro la CURP en TTDS #####");
						solicitudExtPorModulo.setNssNom(ut.getNss());
						solicitudExtPorModulo.setPuestoDescNom(ut.getPuestoDesc());
						solicitudExtPorModulo.setDepartamentoDescNom(ut.getDepartamentoDesc());
						solicitudExtPorModulo.setCveMatricula(ut.getMatricula());
						solicitudExtPorModulo.setCveDelegacionNom(ut.getDelegacionCve());
						solicitudExtPorModulo.setCveEstatusNom(new Long(ut.getEstatus()).longValue());
						
						if (solicitudExtPorModulo.getCveEstatusNom() == 2)	{
							logger.info("##### La CURP se encuentra inactiva en nomina TTDS #####");
							filtrosConsulta.showMsg("La cuenta de usuario validada se encuentra inactiva en nómina.");
							activoNomina = false;							
						} else {
							logger.info("##### La CURP se encuentra activa en nomina TTDS #####");
							filtrosConsulta.showMsg("La cuenta de usuario se valido correctamente.");
							activoNomina = true;
						}
						controlaComponentes();
					}
				}
			} else {
				logger.info("##### La solicitud seleccionada es null.");
				filtrosConsulta.showMsg("Error al mostrar datos de la solicitud.");
				activoNomina = false;
				controlaComponentes();
			}
		} catch (Exception ex) {
			logger.error("##### Error al consultar el servicio de nomina: " + ex.getMessage());
			filtrosConsulta.showMsg("El servicio del IMSS no responde, el registro no se puede llevar a cabo. Favor de intentar mas tarde");
			activoNomina = false;
		} 
	}

	public void validaNomina2()  {
		logger.info("Se ejecuta el metodo");
	}

	public void borraModulo(AjaxBehaviorEvent event){
		try {
			Map<String, Object> attributes = event.getComponent().getAttributes();
			ModuloDTO modulo =(ModuloDTO)attributes.get("attModulo");
			solicitudCriteria.borrarModulo(modulo);
			obtenerSolByFiltro();
			solicitudExtPorModulo.setModulosDTO(solicitudCriteria.modulosBySol(solicitudExtPorModulo.getCveSsosolicitud()));
			bitacoraService.guardaModulosBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), modulo.getCveIdModulo(), false);

			filtrosConsulta.setDesc("El modulo seleccionado a sido eliminado de la cuenta de usuario.");
			filtrosConsulta.setMsg(true);

		} catch (Exception ex) {
			logger.error("Error::borraModulo " + ex.getMessage());
			logger.error("  ", ex);
		}
	}
	
	public void agregaModulo() {
		try {
			if (claveModulo != null && claveModulo > 0) {
				ModuloDTO modulo = new ModuloDTO();
				modulo.setCveIdModulo(claveModulo);
				DepartamentoDTO dto = new DepartamentoDTO();
				dto.setCveSsodepto(filtrosConsulta.getClaveDepartamentoModAdd());
				modulo.setDptoDTO(dto);
				if (solicitudCriteria.agregaModulo(modulo, solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador())) {
					obtenerSolByFiltro();
					solicitudExtPorModulo.setModulosDTO(solicitudCriteria.modulosBySol(solicitudExtPorModulo.getCveSsosolicitud()));
					bitacoraService.guardaModulosBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), claveModulo, true);
					filtrosConsulta.setDesc("El módulo ha sido agregado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				} else {
					filtrosConsulta.setDesc("El módulo seleccionado ya se encuentra asociado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
			} else {
				filtrosConsulta.setDesc("El módulo seleccionado no es valido.");
				filtrosConsulta.setMsg(true);
			}
		} catch (AdmonUsuariosException ex) {
			logger.error("Error::agregaModulo " + ex.getMessage());
			logger.error("  ", ex);
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

	
	public void selectListenerDetalle(SelectEvent event)  throws AdmonUsuariosException{
		logger.debug("******************Entrando a select listener detalle*****************************");
		solicitudExtPorModulo = (SolicitudDTO)event.getObject();
		logger.debug("::: El estatus de la solicitud seleccionada es: " + solicitudExtPorModulo.getEstatusDTO().getCveSsoestatus());
		filtrosConsulta.setClaveAreaNormativa(solicitudExtPorModulo.getDptoDTO().getAreaNormativa().getCveSsoareanorma());
		filtrosConsulta.seteaDepto(solicitudExtPorModulo.getDepartamentoId());
		activoNomina = false;
		controlaComponentes();
		limpiaCombos();
		logger.debug("******************Saliendo de select listener detalle*****************************");
    }

	public void selectListenerDetalle(){
		try {
			filtrosConsulta.setClaveAreaNormativa(solicitudExtPorModulo.getDptoDTO().getAreaNormativa().getCveSsoareanorma());
			filtrosConsulta.seteaDepto(solicitudExtPorModulo.getDepartamentoId());
			activoNomina = false;
			controlaComponentes();
			limpiaCombos();
		} catch (AdmonUsuariosException ex) {
			logger.error("Error::selectListenerDetalle " + ex.getMessage());
			logger.error("  ", ex);
		}
    }

	
    public void controlaComponentes() throws AdmonUsuariosException{
    	logger.info("::: El estatus en nomina del registro es: " + activoNomina);
		if (activoNomina) {
			logger.info("::: El estatus de la solicitud validada es: " + solicitudExtPorModulo
					.getEstatusDTO().getCveSsoestatus());
	    	if (solicitudExtPorModulo.getEstatusDTO().getCveSsoestatus() == Constantes.ESTATUS.SOLICITADO.getOpcion() ){
	    		logger.info("::: El valor de la solicitud es: SOLICITADO(1) :::");
	    		disabledBotonBorrar = false;
				disabledBotonAutorizar = false;
				disabledBotonRechazar = false;
				disabledBotonAdd = false;
	    	}
			else {
	    		logger.info("::: El valor de la solicitud es: " + solicitudExtPorModulo.getEstatusDTO().getCveSsoestatus());
				disabledBotonBorrar = true;
	    		disabledBotonAdd = true;
				disabledBotonAutorizar = true;
				disabledBotonRechazar = true;
			}
	    }
		else {
    		logger.info("::: La solicitud se encuentra inactiva en nomina...");
			disabledBotonBorrar = true;
    		disabledBotonAdd = true;
			disabledBotonAutorizar = true;
			disabledBotonRechazar = true;
		}
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
    
    
	public void autorizaSolicitud() throws AdmonUsuariosException {

		logger.info("########## INICIA AUTORIZACION DE SOLICITUD DE CUENTA ##########");

		try {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setApellidoMaterno(solicitudExtPorModulo.getNomMaterno());
			usuarioDTO.setApellidoPaterno(solicitudExtPorModulo.getNomPaterno());
			usuarioDTO.setNombres(solicitudExtPorModulo.getNomNombre());
			usuarioDTO.setPassword(PasswordUtil.getPassword(PasswordUtil.NUMEROS + PasswordUtil.MINUSCULAS + PasswordUtil.MAYUSCULAS + PasswordUtil.ESPECIALES,	8));
			usuarioDTO.setCurp(solicitudExtPorModulo.getDesUsrCurp());
			usuarioDTO.setCorreoElectronico(solicitudExtPorModulo.getRefCorreoElectronico());
			usuarioDTO.setIdBdtu("1");
			usuarioDTO.setNss("1");
			usuarioDTO.setClaveDepartamento(new Integer(solicitudExtPorModulo.getDptoDTO().getCveSsodepto() + ""));
			usuarioDTO.setMatricula(solicitudExtPorModulo.getCveMatricula());
			usuarioDTO.setNssNom(solicitudExtPorModulo.getNssNom());
			usuarioDTO.setPuestoDescNom(solicitudExtPorModulo.getPuestoDescNom());
			usuarioDTO.setDepartamentoDescNom(solicitudExtPorModulo.getDepartamentoDescNom());
			usuarioDTO.setDescripcionArea(solicitudExtPorModulo.getDptoDTO().getDesDepartamento());
			usuarioDTO.setCveDelegacionNom(solicitudExtPorModulo.getCveDelegacionNom());
			usuarioDTO.setCveSubdelegacionNom("");
			usuarioDTO.setCveUmfNom("");
			usuarioDTO.setCveEstatusNom(solicitudExtPorModulo.getCveEstatusNom());

			if (solicitudExtPorModulo.getDelDTO() != null) {
				Long idDelegacion = solicitudExtPorModulo.getDelDTO().getCveDelegacion();
				idDelegacion.intValue();
				usuarioDTO.setClaveDelegacion(idDelegacion.intValue());
			} else {
				usuarioDTO.setClaveDelegacion(Constantes.SIN_VALOR);
			}

			if (solicitudExtPorModulo.getSubdelDTO() != null) {
				Long idSubDel = solicitudExtPorModulo.getSubdelDTO().getCveSubelegacion();
				idSubDel.intValue();
				usuarioDTO.setClaveSubDelegacion(idSubDel.intValue());
			} else {
				usuarioDTO.setClaveSubDelegacion(Constantes.SIN_VALOR);
			}

			if (solicitudExtPorModulo.getUmfDTO() != null && solicitudExtPorModulo.getUmfDTO().getCveUmf() != null) {
				usuarioDTO.setClaveUMF(solicitudExtPorModulo.getUmfDTO().getCveUmf().intValue());
			} else {
				usuarioDTO.setClaveUMF(Constantes.SIN_VALOR);
			}

			if (solicitudExtPorModulo.getPerfilesDTO() != null && solicitudExtPorModulo.getPerfilesDTO().size() > 0) {
				if (solicitudExtPorModulo.getDesUsrCurp() != null && solicitudExtPorModulo.getDesUsrCurp().length() > 0) {
					try {
						solicitudExtPorModulo.setCveAprobador(usuarioMB.getAprobadorSession().getCveIdAprobador());
						usuarioDTO.setRolesData(solicitudCriteria.puestosByPerfiles(solicitudExtPorModulo.getPerfilesDTO()));
						usuarioDTO.setModulosData(solicitudExtPorModulo.getModulosDTO());
						usuarioDTO.setDescripcionCargo(solicitudExtPorModulo.getPuestoDTO().getNombrePuesto());
						usuarioDTO.setDescripcionArea(solicitudExtPorModulo.getDptoDTO().getDesDepartamento());
						
						UsuarioDTO user = admonUsuarios.obtenUsuario(usuarioDTO.getCurp());
						
						if (user == null) {
							admonUsuarios.agregarUsuarioConPerfiles(usuarioDTO);
						} else {
							solicitudCriteria.actualizaAreaAdsUser(solicitudExtPorModulo);
							solicitudCriteria.aplicaCambiosSolicitudLdap(solicitudExtPorModulo);
							solicitudExtPorModulo.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitudExtPorModulo.getCveSsosolicitud()));
							solicitudExtPorModulo.setModulosDTO(solicitudCriteria.modulosBySol(solicitudExtPorModulo.getCveSsosolicitud()));
						}
						admonUsuarios.desactivarUsuario(solicitudExtPorModulo.getDesUsrCurp());
						
						solicitudCriteria.actualizaStatusSol(solicitudExtPorModulo, Constantes.ESTATUS.AUTORIZADO.getOpcion());
						
						solicitudExtPorModulo.getEstatusDTO().setCveSsoestatus(Constantes.ESTATUS.AUTORIZADO.getOpcion());
						
						for (SolicitudDTO sol : lista2) {
							if (sol.getCveSsosolicitud() == solicitudExtPorModulo.getCveSsosolicitud()) {
								solicitudExtPorModulo = sol;
								break;
							}
						}
						
						logger.info("::: Inicia autorizacion y registro de cuenta de usuario...");
						
						for (ModuloDTO m : solicitudExtPorModulo.getModulosDTO()) {
							solicitudCriteria.actualizaStatusModulo(m, Constantes.ESTATUS.AUTORIZADO.getOpcion());
						}
						
						bitacoraService.guardaSolicitudBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_AUTORIZACION);

						// prepara los datos que ocupa el envio de la notificacion por correo electronico
						solicitudExtPorModulo.setAprobador(usuarioMB.getAprobadorSession());
						String msg = "ha sido registrada y autorizada";
						String titulo = "AUTORIZACION Y REGISTRO DE CUENTA DE USUARIO";
						String link = generaLinkConfirmacion(solicitudExtPorModulo);
						String remitente = "serviciosdigitales@imss.gob.mx";
						logger.info("*** Voy a mandar correo desde SolicitudMB ***");
						logger.info("*** Remitente SolicitudMB " + remitente + " ***");
						mensajeriaService.enviarCorreoConconfirmacion(
								remitente,
								solicitudExtPorModulo.getRefCorreoElectronico(), msg, titulo, solicitudExtPorModulo,
								"Autorización y registro de cuenta de usuario", link);
						mensajeriaService.enviarCorreoConconfirmacionAprobador(
								remitente,
								usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), msg, titulo,
								solicitudExtPorModulo, "Autorización y registro de cuenta de usuario", link);

						// termina bloque de correo electronico
						activoNomina = true;
						controlaComponentes();
						obtenerSolByFiltro();
						filtrosConsulta.setDesc("La cuenta de usuario ha sido autorizada con éxito y se ha notificado al dueño de la cuenta, Esta cuenta quedará inactiva hasta su activación.");
						filtrosConsulta.setMsg(true);
					} catch (AdmonUsuariosException ex) {
						logger.error("Error al actualizar la cuenta de usuario... " + ex.getMessage());
						logger.error("  ", ex);
						controlaComponentes();
						filtrosConsulta.setDesc("La cuenta de usuario no fue autorizada debido a un error interno de la aplicación. Pongase en contacto con el administrador del sistema.");
						filtrosConsulta.setMsg(true);
					}
				} else {
					filtrosConsulta.setDesc("La cuenta de usuario no cuenta con las caracteristicas necesarias para procesar la modificación de información");
					filtrosConsulta.setMsg(true);
				}
			} else {
				controlaComponentes();
				filtrosConsulta.setDesc("La cuenta de usuario no fue autorizada debido a un error interno de la aplicación. Pongase en contacto con el administrador del sistema.");
				filtrosConsulta.setMsg(true);
			}
			logger.info("########## Termina autorizacion de solicitud de cuenta ##########");
		} catch (Exception ex) {
			logger.error("Error::autorizaSolicitud " + ex.getMessage());
			logger.error("  ", ex);
		}
	}
    
    
    private String generaLinkConfirmacion(SolicitudDTO sol) throws AdmonUsuariosException{
    	try {
    		activaCuentaService.registraActivaCuenta(sol);
    		ActivaCuentaDTO dto = activaCuentaService.obtenActivaCuentaBySolicitud(sol);
    		props.load(SolicitudMB.class.getResourceAsStream("/fqdn.properties"));
    		if(dto!=null)
    			return props.getProperty("fqdn")+"/servlet/activaCuenta?cuenta="+dto.getClaveMD5();
		} catch (Exception ex) {
			logger.error("Error::generaLinkConfirmacion " + ex.getMessage());
			logger.error("  ", ex);
		}
		return null;
	}


	public void rechazaSolicitud() throws AdmonUsuariosException{
    	if(solicitudCriteria.actualizaStatusSol(solicitudExtPorModulo, Constantes.ESTATUS.RECHAZADO.getOpcion())){
    		solicitudExtPorModulo.getEstatusDTO().setCveSsoestatus(Constantes.ESTATUS.RECHAZADO.getOpcion());
    		actualizaLista(Constantes.ESTATUS.RECHAZADO.getOpcion());
    		for(SolicitudDTO sol:lista2){
    			if(sol.getCveSsosolicitud() == solicitudExtPorModulo.getCveSsosolicitud()){
    				solicitudExtPorModulo = sol;
    				break;
    			}
    		}
    	}
//    	solicitudCriteria.autorizaRechazaSolicitudBitacora("Rechaza", solicitudExtPorModulo, 181L, solicitudExtPorModulo.getEstatusDTO().getCveSsoestatus(),usuarioMB.getCorreoElectronico());
    	bitacoraService.guardaSolicitudBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_RECHAZO_CUENTA);

    	solicitudExtPorModulo.setAprobador(usuarioMB.getAprobadorSession());

	  	String msg = "-- SE RECHAZO SU CUENTA DE USUARIO --";
	  	String titulo = "RECHAZO DE CUENTA DE USUARIO";		
	  	mensajeriaService.enviarCorreoConconfirmacion(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), solicitudExtPorModulo.getRefCorreoElectronico(), msg, titulo,solicitudExtPorModulo,"Rechazo de cuenta de usuario",null);
	  	mensajeriaService.enviarCorreoConconfirmacionAprobador(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(),usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), msg, titulo,solicitudExtPorModulo,"Rechazo de cuenta de usuario",null);
	  	
	  	activoNomina = true;
    	controlaComponentes();
		obtenerSolByFiltro();

		filtrosConsulta.setDesc("La cuenta de usuario ha sido rechazada. Si desea recuperar la cuenta dirigase al modulo de recuperación de cuenta de usuario.");
		filtrosConsulta.setMsg(true);
    }
    
    public void cambiaTab(ValueChangeEvent event)  throws AdmonUsuariosException{
    	limpiaCombos();
    }
    
    public void limpiaCombos()  throws AdmonUsuariosException{
    }
    
	public List<SolicitudDTO> getLista() {
		return lista;
	}

	public void setLista(List<SolicitudDTO> lista) {
		this.lista = lista;
	}


	public SolicitudDTO getSolicitudExtPorModulo() {
		return solicitudExtPorModulo;
	}

	public void setSolicitudExtPorModulo(SolicitudDTO solicitudExtPorModulo) {
		this.solicitudExtPorModulo = solicitudExtPorModulo;
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

	

}
