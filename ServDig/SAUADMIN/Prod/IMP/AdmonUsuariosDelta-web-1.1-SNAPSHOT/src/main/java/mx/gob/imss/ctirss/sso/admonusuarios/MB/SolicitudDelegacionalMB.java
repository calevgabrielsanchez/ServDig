package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.util.ArrayList;
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

import mx.gob.imss.ctirss.sso.admonusuarios.controller.ConsultaGenericaControllerAdmon;
import mx.gob.imss.ctirss.sso.admonusuarios.controller.ConsultaGenericaControllerDelegacion;
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
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;
import mx.gob.imss.ctirss.sso.util.PasswordUtil;

import org.icefaces.ace.event.SelectEvent;

@ManagedBean(name="solicitudDelegMB")
@CustomScoped("#{window}")
public class SolicitudDelegacionalMB {
	
	@ManagedProperty("#{usuarioMB}")	 
	private UsuarioMB usuarioMB;
	
	
	@ManagedProperty(value="#{consultaGenericaDeleg}")	 
	private ConsultaGenericaControllerDelegacion filtrosConsulta;

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
	
   	private static int AREA_NIVEL_CENTRAL = 1;
   	private static int AREA_NIVEL_DELEGACION = 2;
   	private static int AREA_NIVEL_SUBDELEGACION = 3;
   	private static int AREA_NIVEL_UMF = 4;

	public void listenerPuesto(ValueChangeEvent ve){
		try{
			clavePuesto= new Long(ve.getNewValue().toString()).longValue();
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}    

	
	public void listenerModulo(ValueChangeEvent ve){
		try{
			claveModulo= new Long(ve.getNewValue().toString()).longValue();
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}    

	public String inicializaAprobarSolicitud() throws AdmonUsuariosException{
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
   		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
   		session.setAttribute("pagina", pagina);
   		filtrosConsulta.obtieneDatosUsuario();
		return obtenerSolByFiltroSInicial();
		
	}

	@PostConstruct
    public void inicializaParametros() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			solicitudExtPorModulo = new SolicitudDTO();
	    }
	}
	
	
	public void obtenerSolByFiltro() throws AdmonUsuariosException{
		lista = new ArrayList<SolicitudDTO>();
		llenaFiltro();
		filtro.setEstatusDTO(new EstatusDTO(ESTATUS_SOLICITADO));
		System.out.println("Invoca search solicitudes - SolicitarDelegacionalMB");
		lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		idBoton = 2;
	}

	public String obtenerSolByFiltroSInicial() throws AdmonUsuariosException{
		lista = new ArrayList<SolicitudDTO>();
		idBoton = 2;
		return "aprobacionSolDeleg";
	}

	public String obtenerSolByFiltroS() throws AdmonUsuariosException{
		lista = new ArrayList<SolicitudDTO>();
		if(filtrosConsulta.getClaveDelegacion()<1)
		{
			filtrosConsulta.showMsg("Se requiere seleccionar una delegación en los filtros de la busqueda.");
			return "aprobacionSolDeleg";
		}
		else
		{
			if(filtrosConsulta.getClaveSubdelegacion()<1)
			{
				filtrosConsulta.showMsg("Se requiere seleccionar una subdelegación en los filtros de la busqueda.");
				return "aprobacionSolDeleg";
			}
			else
			{
				if(filtrosConsulta.getClaveUMF()<1)
				{
					filtrosConsulta.showMsg("Se requiere seleccionar una UMF en los filtros de la busqueda.");
					return "aprobacionSolDeleg";
				}
			}
		}
		
		
//		lista = solicitudCriteria.solicitudesByFiltro(filtro, Constantes.ESTATUS.SOLICITADO.getOpcion(), true,null);
		llenaFiltro();
		filtro.setEstatusDTO(new EstatusDTO(ESTATUS_SOLICITADO));
		System.out.println("Invoca search solicitudes - SolicitarDelegacionalMB");
		lista = solicitudCriteria.searchSolicitudes(filtro, usuarioMB.getAprobadorSession().getSolicitud().getDesUsrCurp());
		idBoton = 2;
		return "aprobacionSolDeleg";
	}

	public void llenaFiltro(){
		filtro = new SolicitudDTO();
		
		filtro.setDptoDTO(new DepartamentoDTO(filtrosConsulta.getClaveDepartamento()));
		if(filtrosConsulta.getClaveDelegacion()>0)
		{
			DelegacionDTO del = new DelegacionDTO();
			del.setCveDelegacion(filtrosConsulta.getClaveDelegacion());
			filtro.setDelDTO(del);
		}
		else
			filtro.setDelDTO(null);
		if(filtrosConsulta.getClaveSubdelegacion()>0)
		{
			SubdelegacionDTO subdel = new SubdelegacionDTO();
			subdel.setCveSubelegacion(filtrosConsulta.getClaveSubdelegacion());
			filtro.setSubdelDTO(subdel);
		}
		else
			filtro.setSubdelDTO(null);
		if(filtrosConsulta.getClaveUMF()>0)
		{
			UmfDTO umf = new UmfDTO();
			umf.setCveUmf(filtrosConsulta.getClaveUMF());
			filtro.setUmfDTO(umf);
			
		}
		else
			filtro.setUmfDTO(null);
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
			filtrosConsulta.showMsg("El grupo seleccionado a sido eliminado de la cuenta de usuario.");
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
		try {
			System.out.println("##### Entre al try de ValidarNomina en DelegacionalMB #####");
			if(solicitudExtPorModulo!=null)	{
				System.out.println("##### Voy a ir a validar la CURP [ " + solicitudExtPorModulo.getDesUsrCurp() + " ] en SIAP #####");
				ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
				UsuarioNominaResponse rest = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(solicitudExtPorModulo.getDesUsrCurp(), "");
				if(rest!=null){
					System.out.println("##### Encontre la CURP en SIAP #####");
					System.out.println("El curp se encontro en el servicio de nomina SIAP.");
					solicitudExtPorModulo.setNssNom(rest.getNss());
					solicitudExtPorModulo.setPuestoDescNom(rest.getPuestoDesc());
					solicitudExtPorModulo.setDepartamentoDescNom(rest.getDepartamentoDesc());
					solicitudExtPorModulo.setCveMatricula(rest.getMatricula());
					solicitudExtPorModulo.setCveDelegacionNom(rest.getDelegacionCve());
					solicitudExtPorModulo.setCveEstatusNom(new Long(rest.getEstatus()).longValue());
					
					if(solicitudExtPorModulo.getCveEstatusNom()==2)	{
						System.out.println("El curp se encuentra inactiva en nomina SIAP.");
						filtrosConsulta.showMsg("La cuenta de usuario validada se encuentra inactiva en nómina.");
						activoNomina = false;						
					}else {
						System.out.println("El curp se encuentra activa en nomina SIAP");
						filtrosConsulta.showMsg("La cuenta de usuario se valido correctamente.");
						activoNomina = true;						
					}
					controlaComponentes();
				}else {
					System.out.println("***** No encontre la CURP en SIAP, voy a ir a TTDS *****");
					System.out.println("##### Voy a ir a validar la CURP [ " + solicitudExtPorModulo.getDesUsrCurp() + " ] en TTDS #####");
					ClienteConsultaCurpTTDS ccct = new ClienteConsultaCurpTTDS();
					UsuarioTTD ut = ccct.invocarServicioConsultaCurpTTDS(solicitudExtPorModulo.getDesUsrCurp(), "");
					if(ut != null) {
						System.out.println("##### Encontre la CURP en TTDS #####");
						System.out.println("El curp se encontro en el servicio de nomina TTDS.");
						solicitudExtPorModulo.setNssNom(ut.getNss());
						solicitudExtPorModulo.setPuestoDescNom(ut.getPuestoDesc());
						solicitudExtPorModulo.setDepartamentoDescNom(ut.getDepartamentoDesc());
						solicitudExtPorModulo.setCveMatricula(ut.getMatricula());
						solicitudExtPorModulo.setCveDelegacionNom(ut.getDelegacionCve());
						solicitudExtPorModulo.setCveEstatusNom(new Long(ut.getEstatus()).longValue());
						
						if(solicitudExtPorModulo.getCveEstatusNom()==2)	{
							System.out.println("El curp se encuentra inactiva en nomina TTDS.");
							filtrosConsulta.showMsg("La cuenta de usuario validada se encuentra inactiva en nómina.");
							activoNomina = false;							
						}else {
							System.out.println("El curp se encuentra activa en nomina TTDS.");
							filtrosConsulta.showMsg("La cuenta de usuario se valido correctamente.");
							activoNomina = true;							
						}
						controlaComponentes();
					}					
				}
			}else {
				System.out.println("La solicitud seleccionada es null");
				filtrosConsulta.showMsg("Error al mostrar datos de la solicitud.");
				activoNomina = false;
				controlaComponentes();
			}
		}catch (Exception e) {
			System.out.println("Error al consultar servicio de nomina");
			filtrosConsulta.showMsg("El servicio del IMSS no responde, el registro no se puede llevar a cabo. Favor de intentar mas tarde");
			activoNomina = false;
			e.printStackTrace();
		}
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregaModulo() {
		try {
			if(claveModulo!=null&&claveModulo>0)
			{
				ModuloDTO modulo = new ModuloDTO();
				modulo.setCveIdModulo(claveModulo);
				DepartamentoDTO dto = new DepartamentoDTO();
				dto.setCveSsodepto(filtrosConsulta.getClaveDepartamentoModAdd());
				modulo.setDptoDTO(dto);
				if(solicitudCriteria.agregaModulo(modulo, solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador())){
					obtenerSolByFiltro();
					solicitudExtPorModulo.setModulosDTO(solicitudCriteria.modulosBySol(solicitudExtPorModulo.getCveSsosolicitud()));
					bitacoraService.guardaModulosBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), claveModulo, true);
					filtrosConsulta.setDesc("El módulo ha sido agregado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}else{
					filtrosConsulta.setDesc("El módulo seleccionado ya se encuentra asociado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
			}
			else
			{
				filtrosConsulta.setDesc("El módulo seleccionado no es valido.");
				filtrosConsulta.setMsg(true);
			}
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
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
		solicitudExtPorModulo = (SolicitudDTO)event.getObject();
		filtrosConsulta.setClaveAreaNormativa(solicitudExtPorModulo.getDptoDTO().getAreaNormativa().getCveSsoareanorma());
		filtrosConsulta.seteaDepto(solicitudExtPorModulo.getDepartamentoId());
		activoNomina = false;
		controlaComponentes();
		limpiaCombos();
    }

	
    public void controlaComponentes() throws AdmonUsuariosException{
		if(activoNomina)
		{
	    	if(solicitudExtPorModulo.getEstatusDTO().getCveSsoestatus()==Constantes.ESTATUS.SOLICITADO.getOpcion()){
	    		disabledBotonBorrar = false;
				disabledBotonAutorizar = false;
				disabledBotonRechazar = false;
				disabledBotonAdd = false;
	    	}
			else{
				disabledBotonBorrar = true;
	    		disabledBotonAdd = true;
				disabledBotonAutorizar = true;
				disabledBotonRechazar = true;
			}
	    }
		else
		{
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
    
    
    public void autorizaSolicitud() throws AdmonUsuariosException{
    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	usuarioDTO.setApellidoMaterno(solicitudExtPorModulo.getNomMaterno());
    	usuarioDTO.setApellidoPaterno(solicitudExtPorModulo.getNomPaterno());
    	usuarioDTO.setNombres(solicitudExtPorModulo.getNomNombre());
    	usuarioDTO.setPassword(PasswordUtil.getPassword(PasswordUtil.NUMEROS + PasswordUtil.MINUSCULAS +PasswordUtil.MAYUSCULAS+PasswordUtil.ESPECIALES,8));
    	usuarioDTO.setCurp(solicitudExtPorModulo.getDesUsrCurp());
    	usuarioDTO.setCorreoElectronico(solicitudExtPorModulo.getRefCorreoElectronico());
    	usuarioDTO.setIdBdtu("1");
    	usuarioDTO.setNss("1");
    	usuarioDTO.setClaveDepartamento(new Integer(solicitudExtPorModulo.getDptoDTO().getCveSsodepto()+""));
    	
    	usuarioDTO.setNssNom(solicitudExtPorModulo.getNssNom());
    	usuarioDTO.setPuestoDescNom(solicitudExtPorModulo.getPuestoDescNom());
    	usuarioDTO.setDepartamentoDescNom(solicitudExtPorModulo.getDepartamentoDescNom());
    	usuarioDTO.setCveDelegacionNom(solicitudExtPorModulo.getCveDelegacionNom());
    	usuarioDTO.setCveSubdelegacionNom("");
    	usuarioDTO.setCveUmfNom("");
    	usuarioDTO.setCveEstatusNom(solicitudExtPorModulo.getCveEstatusNom());

    	if(solicitudExtPorModulo.getDelDTO()!=null){
	    	Long idDelegacion = solicitudExtPorModulo.getDelDTO().getCveDelegacion();
	    	idDelegacion.intValue();
	    	usuarioDTO.setClaveDelegacion(idDelegacion.intValue());
    	}else{
    		usuarioDTO.setClaveDelegacion(Constantes.SIN_VALOR);
    	}
    	if(solicitudExtPorModulo.getSubdelDTO()!=null){
	    	Long idSubDel = solicitudExtPorModulo.getSubdelDTO().getCveSubelegacion();
	    	idSubDel.intValue();
	    	usuarioDTO.setClaveSubDelegacion(idSubDel.intValue());
    	}else{
    		usuarioDTO.setClaveSubDelegacion(Constantes.SIN_VALOR);
    	}
    	if(solicitudExtPorModulo.getUmfDTO()!=null && solicitudExtPorModulo.getUmfDTO().getCveUmf()!=null){
	    	usuarioDTO.setClaveUMF(solicitudExtPorModulo.getUmfDTO().getCveUmf().intValue());
    	}else{
    		usuarioDTO.setClaveUMF(Constantes.SIN_VALOR);
    	}
    	if(solicitudExtPorModulo.getPerfilesDTO()!=null&&solicitudExtPorModulo.getPerfilesDTO().size()>0)
    	{
        	try {
        		solicitudExtPorModulo.setCveAprobador(usuarioMB.getAprobadorSession().getCveIdAprobador());
            	usuarioDTO.setRolesData(solicitudCriteria.puestosByPerfiles(solicitudExtPorModulo.getPerfilesDTO()));
            	usuarioDTO.setModulosData(solicitudExtPorModulo.getModulosDTO());
            	usuarioDTO.setDescripcionCargo(solicitudExtPorModulo.getPuestoDTO().getNombrePuesto());
            	usuarioDTO.setDescripcionArea(solicitudExtPorModulo.getDptoDTO().getDesDepartamento());
            	
            	UsuarioDTO user =  admonUsuarios.obtenUsuario(usuarioDTO.getCurp());
            	if(user==null)
            	{
            		admonUsuarios.agregarUsuarioConPerfiles(usuarioDTO);
            		admonUsuarios.desactivarUsuario(solicitudExtPorModulo.getDesUsrCurp());
            	}
            	else
            	{

            		solicitudCriteria.actualizaAreaAdsUser(solicitudExtPorModulo);
            		solicitudCriteria.aplicaCambiosSolicitudLdap(solicitudExtPorModulo);
            		solicitudExtPorModulo.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitudExtPorModulo.getCveSsosolicitud()));
            		solicitudExtPorModulo.setModulosDTO(solicitudCriteria.modulosBySol(solicitudExtPorModulo.getCveSsosolicitud()));
            	}

            	solicitudCriteria.actualizaStatusSol(solicitudExtPorModulo,Constantes.ESTATUS.AUTORIZADO.getOpcion());
            	solicitudExtPorModulo.getEstatusDTO().setCveSsoestatus(Constantes.ESTATUS.AUTORIZADO.getOpcion());

        		for(SolicitudDTO sol:lista2){
        			if(sol.getCveSsosolicitud() == solicitudExtPorModulo.getCveSsosolicitud()){
        				solicitudExtPorModulo = sol;
        				break;
        			}
        		}
            	for(ModuloDTO m :solicitudExtPorModulo.getModulosDTO()){
            		solicitudCriteria.actualizaStatusModulo(m, Constantes.ESTATUS.AUTORIZADO.getOpcion());
            	}
            	
            	bitacoraService.guardaSolicitudBit(solicitudExtPorModulo.getCveSsosolicitud(), usuarioMB.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_AUTORIZACION);
            	
            	//prepara los datos que ocupa el envio de la notificacion por correo electronico
            	solicitudExtPorModulo.setAprobador(usuarioMB.getAprobadorSession());
  			  	String msg = "ha sido registrada y autorizada";
  			  	String titulo = "AUTORIZACION Y REGISTRO DE CUENTA DE USUARIO";
  			  	String link = generaLinkConfirmacion(solicitudExtPorModulo);
  			  	System.out.println("el link completo es :" +link);
  			  	mensajeriaService.enviarCorreoConconfirmacion(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), solicitudExtPorModulo.getRefCorreoElectronico(), msg, titulo,solicitudExtPorModulo,"Autorización y registro de cuenta de usuario",link);
  			  	mensajeriaService.enviarCorreoConconfirmacionAprobador(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), msg, titulo,solicitudExtPorModulo,"Autorización y registro de cuenta de usuario",link);
  			  	//termina bloque de correo electronico
  			  	
  			  	activoNomina = true;
            	controlaComponentes();

            	obtenerSolByFiltro();
            	
				filtrosConsulta.setDesc("La cuenta de usuario ha sido autorizada con éxito y se ha notificado al dueño de la cuenta, Esta nueva cuenta quedará inactiva hasta su autorización.");
				filtrosConsulta.setMsg(true);

    		} catch (AdmonUsuariosException e) {
    	    	controlaComponentes();

    	    	filtrosConsulta.setDesc("La cuenta de usuario no fue autorizada debido a un error interno de la aplicación. Pongase en contacto con el administrador del sistema.");
				filtrosConsulta.setMsg(true);
    		}
    	}
    	else
    	{
	    	controlaComponentes();
	    	filtrosConsulta.setDesc("La cuenta de usuario no fue autorizada debido a un error interno de la aplicación. Pongase en contacto con el administrador del sistema.");
			filtrosConsulta.setMsg(true);
    	}
    	
    }
    
    
    private String generaLinkConfirmacion(SolicitudDTO sol) throws AdmonUsuariosException{
    	try {
    		activaCuentaService.registraActivaCuenta(sol);
    		ActivaCuentaDTO dto = activaCuentaService.obtenActivaCuentaBySolicitud(sol);
    		props.load(SolicitudDelegacionalMB.class.getResourceAsStream("/fqdn.properties"));
    		if(dto!=null)
    			return props.getProperty("fqdn")+"/servlet/activaCuenta?cuenta="+dto.getClaveMD5();
		} catch (Exception e) {
			e.printStackTrace();
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
    	solicitudCriteria.autorizaRechazaSolicitudBitacora("Rechaza", solicitudExtPorModulo, 181L, solicitudExtPorModulo.getEstatusDTO().getCveSsoestatus(),usuarioMB.getCorreoElectronico());

    	solicitudExtPorModulo.setAprobador(usuarioMB.getAprobadorSession());

	  	String msg = "-- SE RECHAZO SU CUENTA DE USUARIO --";
	  	String titulo = "RECHAZO DE CUENTA DE USUARIO";		
	  	mensajeriaService.enviarCorreo(usuarioMB.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), solicitudExtPorModulo.getRefCorreoElectronico(), msg, titulo,solicitudExtPorModulo,"Rechazo de cuenta de usuario");
	  	
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

	public ConsultaGenericaControllerDelegacion getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaControllerDelegacion filtrosConsulta) {
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
