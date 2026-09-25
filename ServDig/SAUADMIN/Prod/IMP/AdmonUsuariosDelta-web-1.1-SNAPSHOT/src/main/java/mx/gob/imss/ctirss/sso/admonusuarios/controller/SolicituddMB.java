package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.bean.SessionScoped;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.component.UIComponent;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.util.PasswordUtil;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UnidadMedicaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.SolicitudCuentaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;

import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.event.UnselectEvent;
import org.icefaces.ace.model.table.RowStateMap;

import java.util.Collection;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ManagedBean(name="solicituddMB")
@SessionScoped
public class SolicituddMB {
	
	@EJB
	private SolicitudServiceLocal solicitudCriteria;
	@EJB
	private AdmonUsuariosSessionLocal admonUsuariosService;
	@EJB
	private AdmonPerfilesSessionLocal admonsPerfilesService;
	@EJB
	private SolicitudCuentaSessionLocal solicitudCuentaService;
	@EJB
	private MensajeriaSessionLocal mensajeriaService; 
	@EJB
	private CatalogoServiceLocal catalogoService;  
	@EJB
	private AdmonUsuariosSessionLocal admonUsuarios;
	
	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuario;	
	
	private static Log log = LogFactory.getLog(SolicituddMB.class);
	
	private List<SolicitudDTO> listaSolExt = new ArrayList<SolicitudDTO>();
	private List<SolicitudDTO> lista = new ArrayList<SolicitudDTO>();
	private SolicitudDTO solicitudDTO = new SolicitudDTO();
	private SolicitudDTO solicitudPorModulo = new SolicitudDTO();
	private SolicitudDTO solicitudExtPorModulo = new SolicitudDTO();
	private RowStateMap stateMap = new RowStateMap();
	private int idSolicitud = 0;
	private String errorGeneralDatos = "";
	private String errorAviso = "";
	private String errorMatricula = "";
	private String errorEmail = "";
	private String errorTelefono = "";
	private String resultCurp = "";
	private String curpAeliminar = "";
	private boolean flagThirdSlide = false;
	private boolean GeneralErrorPersist = false;
	private boolean flagEliminacion = false;
	private int testing = 1;
	private int cveDelegacion = -99;
	private int cveSubelegacion = -99;
	private int cveUMF = -99;
	private int claveAreaNormativa = -99;
	private int claveDepartamento = -99;
	private int clavePuesto = -99;
	private int claveDelegacion = -99;
	private int claveSubDelegacion = -99;
	private int claveUMF = -99;
	private int cveAreaNorm = -99;
	private boolean isResult = false;
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();
	private List<SelectItem> lstDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMF = new ArrayList<SelectItem>(); 
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>();
	private Long claveDepartamentoPerfil;
	private Long clavePuestoPerfil;
	private Long claveAreaNormativaPerfil;
	private Long claveDepartamentoModulo;
	private Long claveAreaNormativaModulo;
	private Long claveModulo;
	private Long idDeptoGral;
	private SolicitudDTO filtro;
	private String curpNueva = "";
	private int cveDeleg;
	
	private String nssNom;
	private String puestoDescNom;
	private String departamentoDescNom;
	private String cveDelegacionNom;
	private String cveSubdelegacionNom;
	private String cveUmfNom;
	private long cveEstatusNom;
	
    private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
	@PostConstruct
	public void init() throws AdmonUsuariosException{
		System.out.println("init SolicitudMB");		
		
	}
	
	public SolicituddMB() {		
	}
	
    public void obtenerSolExtByDepto(ActionEvent event)throws AdmonUsuariosException{
		Map<String, Object> attributes = event.getComponent().getAttributes();
		idDeptoGral = Long.parseLong((String)attributes.get("idDepto"));
		lista = solicitudCriteria.solicitudesExternasByDepto(idDeptoGral,new Long(1));
		isResult = true;
	}

    public void obtenerSolByFiltro(ActionEvent event)throws AdmonUsuariosException{
		SolicitudDTO filtro = new SolicitudDTO();
		DepartamentoDTO depto = new DepartamentoDTO();
		DelegacionDTO del = new DelegacionDTO();
		SubdelegacionDTO sub = new SubdelegacionDTO();
		filtro.setDptoDTO(depto);
		filtro.setDelDTO(del);
		filtro.setSubdelDTO(sub);
		filtro.getDelDTO().setCveDelegacion(9);
		filtro.getSubdelDTO().setCveSubelegacion(-99);
		filtro.getDptoDTO().setCveSsodepto(12);
		lista = solicitudCriteria.solicitudesByFiltro(filtro,new Long(1),false,null);
		isResult = true;
	}

       public void borraPefil(AjaxBehaviorEvent event)throws AdmonUsuariosException{
		Map<String, Object> attributes = event.getComponent().getAttributes();
		PerfilDTO perfil =(PerfilDTO)attributes.get("attPerfil");
		solicitudCriteria.borrarPerfil(perfil);
		lista = solicitudCriteria.solicitudesByFiltro(filtro,new Long(1),false,null);
		solicitudExtPorModulo.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitudExtPorModulo.getCveSsosolicitud()));
	}
	
	public void agregaPerfil()throws AdmonUsuariosException{
		System.out.println("---En Agrega Perfil 1-----");
		PerfilDTO perfil = new PerfilDTO();
		System.out.println("---En Agrega Perfil 2-----");
		DepartamentoDTO deptoDTO = new DepartamentoDTO();
		System.out.println("---En Agrega Perfil 3-----");
		PuestoDTO puestoDTO = new PuestoDTO();
		System.out.println("---En Agrega Perfil 4-----");
		SolicitudDTO solDTO = new SolicitudDTO();
		System.out.println("---En Agrega Perfil 5-----");
		deptoDTO.setCveSsodepto(claveDepartamentoPerfil);
		System.out.println("---En Agrega Perfil 6-----");
		puestoDTO.setCvePuesto(clavePuestoPerfil);
		System.out.println("---En Agrega Perfil 7-----");
		solDTO.setCveSsosolicitud(solicitudExtPorModulo.getCveSsosolicitud());
		System.out.println("---En Agrega Perfil 8-----");
		perfil.setDeptoDTO(deptoDTO);
		System.out.println("---En Agrega Perfil 9-----");
		perfil.setPuestoDTO(puestoDTO);
		System.out.println("---En Agrega Perfil 10-----");
		perfil.setSolicitudDTO(solDTO);
		System.out.println("---En Agrega Perfil 11-----");
		solicitudCriteria.agregaPerfil(perfil);
		System.out.println("---En Agrega Perfil 12-----");
		solicitudExtPorModulo.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitudExtPorModulo.getCveSsosolicitud()));
		System.out.println("---En Agrega Perfil 13-----");
	}
	
	public void borraModulo(AjaxBehaviorEvent event)throws AdmonUsuariosException{
		Map<String, Object> attributes = event.getComponent().getAttributes();
		ModuloDTO modulo =(ModuloDTO)attributes.get("attModulo");
		solicitudCriteria.borrarModulo(modulo);
		lista = solicitudCriteria.solicitudesByFiltro(filtro,new Long(1),false,null);
		solicitudExtPorModulo.setModulosDTO(solicitudCriteria.modulosBySol(solicitudExtPorModulo.getCveSsosolicitud()));
	}
	
	public void selectListener(SelectEvent event) {
            solicitudExtPorModulo = (SolicitudDTO)event.getObject();
    }
	
	public void selectListenerDeptosExt(SelectEvent event) {
		solicitudExtPorModulo = (SolicitudDTO)event.getObject();
    }

    public void deselectListener(UnselectEvent event) {
//        prueba = "Deseleccionado ";
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
    }
    
    public void obtieneModuloListener(ValueChangeEvent ve) throws AdmonUsuariosException{
    	this.claveModulo = (Long)ve.getNewValue();
    }
    
    public void obtienePerfilListener(ValueChangeEvent ve) throws AdmonUsuariosException{
    	this.clavePuestoPerfil = (Long)ve.getNewValue();
    }
        	
	public List<SolicitudDTO> getLista() {
		return lista;
	}

	public void setLista(List<SolicitudDTO> lista) {
		this.lista = lista;
	}

	public SolicitudDTO getSolicitudPorModulo() {
		return solicitudPorModulo;
	}

	public void setSolicitudPorModulo(SolicitudDTO solicitudPorModulo) {
		this.solicitudPorModulo = solicitudPorModulo;
	}

	public RowStateMap getStateMap() {
		return stateMap;
	}

	public void setStateMap(RowStateMap stateMap) {
		this.stateMap = stateMap;
	}

	public SolicitudDTO getSolicitudExtPorModulo() {
		return solicitudExtPorModulo;
	}

	public void setSolicitudExtPorModulo(SolicitudDTO solicitudExtPorModulo) {
		this.solicitudExtPorModulo = solicitudExtPorModulo;
	}

	public SolicitudDTO getSolicitudDTO() 
	{
		return solicitudDTO;
	}

	public void setSolicitudDTO(SolicitudDTO solicitudDTO) 
	{
		this.solicitudDTO = solicitudDTO;
	}
	public String getMatricula()
	{
		return solicitudDTO.getCveMatricula();
	}
	
	public void setModulosData(List<ModuloDTO> listaModulos)
	{
		solicitudDTO.setModulosDTO(listaModulos);
	}
	
    public void setRolesData(List<PuestoDTO> listaRoles)
    {
    	solicitudDTO.setPuestosDTO(listaRoles);
    }
    
    public void setCurp(String curp)
    {
    	solicitudDTO.setDesUsrCurp(curp);
    }
    
    public String getCurp()
    {
    	return solicitudDTO.getDesUsrCurp();
    }
    
    public String buscaEstatusSolicitud(String curp)
    {
    	String res = "";
    	try{
          res = solicitudCuentaService.buscaEstatusSolicitud(curp);
    	}
    	catch(Exception e)
    	{
    		log.error("  ", e);
    	}
    	return res;
    }
    
    public String buscaCurpExistente(String curp)
    {
    	String res = "";
    	try{
    	res= solicitudCuentaService.buscaCurpExistente(curp);
    	}
    	catch(Exception e)
    	{
    		log.error("  ", e);
    	}
    	return res;
    }
    
    public void setNombre(String nombre)
    {
    	solicitudDTO.setNomNombre(nombre);
    }
    
    public void setNombrePaterno(String nombrePaterno)
    {
    	solicitudDTO.setNomPaterno(nombrePaterno);
    }
    
    public void setNombreMaterno(String nombreMaterno)
    {
    	solicitudDTO.setNomMaterno(nombreMaterno);
    }
    
    public void setMatricula(String matricula){
    	this.solicitudDTO.setCveMatricula(matricula);
    }
       
    public String registrarPerfilesSolicitudNVer(int cvePuesto, String defaultRol) throws AdmonUsuariosException {
	    solicitudCuentaService.registrarPerfiles(Long.parseLong(idSolicitud+""), cvePuesto, defaultRol); 	   	     
		return "inicializada";
     }

    public String registrarModulosSolicitudNVer(int cveDepartamento, int cveModulo) throws AdmonUsuariosException { 
	    solicitudCuentaService.registrarModulos(Long.parseLong(idSolicitud+""), cveDepartamento, cveModulo,1); 
        return "inicializada";
     }

     public String eliminarPerfilesSolicitudNVer(int cvePuesto) throws AdmonUsuariosException {
        solicitudCuentaService.eliminarPerfiles(Long.parseLong(idSolicitud+""),cvePuesto); 	   	     
	    return "inicializada";
     }

     public String eliminarModulosSolicitudNVer(int cveDepartamento, int cveModulo) throws AdmonUsuariosException { 
	    solicitudCuentaService.eliminarModulos(Long.parseLong(idSolicitud+""), cveDepartamento, cveModulo); 
        return "inicializada";
     }

     public String eliminarModulosById(int cveModulo) throws AdmonUsuariosException { 
	    solicitudCuentaService.eliminarModulosId(cveModulo); 
        return "inicializada";
     }

   //Datos para registrar al usuario
		public String editarSolicitudNVer() throws AdmonUsuariosException {
			this.flagThirdSlide = true;
			Locale defloc = Locale.getDefault();
			solicitudDTO.setDesUsrCurp(solicitudDTO.getDesUsrCurp().toUpperCase(defloc));
		  //solicitudDTO.setClaveDepartamento(1);  //depto
		  //solicitudDTO.setClavePuesto(1); //puesto
			//eliminaPerfilesLDAPEdicion(solicitudDTO.getCurp());
		  //  Long idSol = solicitudCuentaService.obtenerIDSolicitud(solicitudDTO.getCurp());
		    UsuarioDTO usuario = new UsuarioDTO();
		    //eliminaPerfilesModulosBD(idSol,solicitudDTO);
		   // solicitudCuentaService.actualizarSolicitudBD(idSol, usuario);
		    //Roles
			for(PuestoDTO rolint : solicitudDTO.getPuestosDTO()){
			  // solicitudCuentaService.registrarPerfiles(idSol,rolint.getCvePuesto()); 
			}
			//Modulos
			for(ModuloDTO modint : solicitudDTO.getModulosDTO()){
			   //	Long cveAprobador = solicitudCuentaService.registrarAprobadorNVer(usuario, idSol, modint.getCveModulo());
			   //	solicitudCuentaService.registrarModulos(idSol, modint.getCveDepartamento(), modint.getCveModulo(),1); 
			   	}  
		      resultCurp = "";
		      return "exitoGuardar";
		}
		
		public void guardaMovimientosUsuario(String idSolicitud, int estatus, String desMovimiento, List<PuestoDTO> listaRoles, List<ModuloDTO> listaModulos, Long idAprobador) throws AdmonUsuariosException {
		    try{ 
			solicitudCriteria.guardaMovimientosUsuario(idSolicitud, estatus, desMovimiento, listaRoles, listaModulos, idAprobador);
		    }
		    catch(Exception e){
		    	System.out.println("guardaMovimientosUsuario");
		    	e.printStackTrace();
		    	log.error("  ", e);
		    }
		}
	
	   //Datos para registrar al usuario
	   public String aprobarSolicitudNVer() throws AdmonUsuariosException {
		    UsuarioDTO usuario = new UsuarioDTO();
			this.flagThirdSlide = true;	

			solicitudDTO.setPassword(PasswordUtil.getPassword(PasswordUtil.NUMEROS + PasswordUtil.MINUSCULAS +
						                                              PasswordUtil.MAYUSCULAS+PasswordUtil.ESPECIALES,8));
				
			//Almacenar movimiento
			guardaMovimientosUsuario(String.valueOf(solicitudDTO.getCveSsosolicitud()), 2, "APROBACION DE SOLICITUD ", solicitudDTO.getPuestosDTO(), solicitudDTO.getModulosDTO(), 987654321L); 
			return "exito";	
		}
	   
	    public String rechazarSolicitudNVer() throws AdmonUsuariosException {
		    UsuarioDTO usuario = new UsuarioDTO();  
	        //	solicitudCuentaService.actualizarSolicitudRechazadaBD(Long.parseLong(solicitudDTO.getIdSolicitud()), usuario);
		    //Almacenar movimiento
		    guardaMovimientosUsuario(String.valueOf(solicitudDTO.getCveSsosolicitud()), 3, "RECHAZO DE SOLICITUD ", solicitudDTO.getPuestosDTO(), solicitudDTO.getModulosDTO(), 987654321L); 
		    return "exito";	
	  }
	  public List<String> consultaRolesPerfilesLDAP(String curp)
	  {
		  List<String> roles = new ArrayList<String>();
		  try{
		//  roles = this.admonsPerfilesService.consultaRolesPerfilesLDAP(curp);
		  }
		  catch(Exception e)
		  {
			  log.error("  ", e);	  
		  }
		  return roles;
	  }
	  public String getNombre()
	  {
		  return solicitudDTO.getNomNombre();
	  }
	  public String getNombrePaterno()
	  {
		  return solicitudDTO.getNomPaterno();
	  }
	  public String getNombreMaterno()
	  {
		  return solicitudDTO.getNomMaterno();
	  }  
	  public String getCorreoElectronico()
	  {
		 return solicitudDTO.getRefCorreoElectronico(); 
	  }
	  public int getClavePuesto()
	  {
		 return this.clavePuesto; 
	  }
	  public int getClaveDepartamento()
	  {
		 return this.claveDepartamento; 
	  }
	  public boolean isResult() {
		return isResult;
	  }
	  
	  public void registrarPerfiles(long idSol, int cvePuesto)
	  {
		try{  
		//solicitudCuentaService.registrarPerfiles(idSol,cvePuesto);
		}
		catch(Exception e)
		{
			
		}
	  }

	  public List<PuestoDTO> getRolesData()
	  {
		 return solicitudDTO.getPuestosDTO();  
	  }
	  public List<ModuloDTO> getModulosData()
	  {
		 return solicitudDTO.getModulosDTO();  
	  }
		
	  public void setTelefono(String telefono)
	  {
		solicitudDTO.setDesTelefonoOfi(telefono);
	  }
		
	  public void setClaveDepartamento(int cveDep)
	  {
		this.claveDepartamento = cveDep; 
	  }
		
	  public void setClavePuesto(int cvePuesto)
	  {
		 this.clavePuesto = cvePuesto; 
	  }
		
	  public void setCorreoElectronico(String correoElectronico)
	  {
		 solicitudDTO.setRefCorreoElectronico(correoElectronico);
	   }
		
		public void setClaveDelegacion(int cveDelegacion)
		{
			this.cveDelegacion = cveDelegacion;
		}
		
		public List<DepartamentoDTO> listarDepartamentosByAreaNormativa(String cveArea)
		{
			List<DepartamentoDTO> lista = null;
			try{
			lista = catalogoService.listarDepartamentosByAreaNormativa(cveArea);
			}
			catch(Exception e)
			{
				log.error("  ", e);
			}
			return lista;
		}
		
		public List<PuestoDTO> listarPuestoByDepartamento(String cveDepartamento)
		{
			List<PuestoDTO> lista = null;
			try{
				lista = catalogoService.listarPuestoByDepartamento(Long.parseLong(cveDepartamento));
			}
			catch(Exception e)
			{
				log.error("  ", e);
			}
			return lista;
		}
		
		public UsuarioDTO cambiaValores(SolicitudDTO solicitud)
		{
			UsuarioDTO usuario = new UsuarioDTO();
			String des= solicitud.getDesUsrCurp();
			usuario.setCurp(des);
			usuario.setNombres(solicitud.getNomNombre());
			usuario.setApellidoPaterno(solicitud.getNomPaterno());
			usuario.setApellidoMaterno(solicitud.getNomMaterno());
			usuario.setCorreoElectronico(solicitud.getRefCorreoElectronico());
			usuario.setMatricula(solicitud.getCveMatricula());
			usuario.setClaveAreaNormativa((int)solicitud.getAreaNormativaId());
			usuario.setClaveDepartamento((int)solicitud.getDepartamentoId());
			usuario.setClavePuesto((int)solicitud.getPuestoId());
			usuario.setDescripcionCargo(solicitud.getPuestoDTO().getNombrePuesto());

			
			if(solicitud.getDelegacionId()>0)usuario.setClaveDelegacion((int)solicitud.getDelegacionId());
			if(solicitud.getSubdelegacionId()>0)usuario.setClaveSubDelegacion((int)solicitud.getSubdelegacionId());
			if(solicitud.getUmfId()>0)usuario.setClaveUMF((int)solicitud.getUmfId()); 
			
			usuario.setTelefono(solicitud.getDesTelefonoOfi());
			
			usuario.setNssNom(solicitud.getNssNom());
			usuario.setPuestoDescNom(solicitud.getPuestoDescNom());
			usuario.setDepartamentoDescNom(solicitud.getDepartamentoDescNom());
			usuario.setCveDelegacionNom(solicitud.getCveDelegacionNom());
			usuario.setCveSubdelegacionNom("");
			usuario.setCveUmfNom("");
			usuario.setCveEstatusNom(solicitud.getCveEstatusNom());
			
			return usuario;
		}
		
		public boolean validarEmail(String email)  {
			 errorEmail  = "";
			 errorAviso= "";
			 if (email == null || "".equals(email)) {
				 errorEmail= "EMAIL INVALIDO!";
				 errorAviso="AVISOS: ";
		        }
		         
			boolean res =email.matches(EMAIL_PATTERN);
			if(res==false){
				errorEmail= "EMAIL INVALIDO!";
				errorAviso="AVISOS: ";
			}
		    return res;
		 }
		 
		 public boolean validarMatricula(String matricula)  {
			 errorMatricula  = "";
			 errorAviso="";
			 if (matricula == null || "".equals(matricula)) {
				 errorMatricula = "MATRICULA INVALIDA!";
				 errorAviso="AVISOS: ";
				 return true;
		        }
			 return false;
		 }
		 
		 public String getApellidoPaterno()
		 {
			return solicitudDTO.getNomPaterno(); 
		 }    
		 
		 public String getApellidoMaterno()
		 {
			 return solicitudDTO.getNomMaterno();
		 }
		 
		 public String getNombres()
		 {
			 return solicitudDTO.getNomNombre();
		 }
		 
		 public SolicitudDTO cambiaValoresSolicitud(UsuarioDTO usuario)
		 {
			 SolicitudDTO solicitud =  new SolicitudDTO();
			 solicitud.setCveMatricula(usuario.getMatricula());
			 solicitud.setNomNombre(usuario.getNombres());
			 solicitud.setNomPaterno(usuario.getApellidoPaterno());
			 solicitud.setNomMaterno(usuario.getApellidoMaterno());
			 return solicitud;
		 }

		public int getIdSolicitud() {
			return idSolicitud;
		}

		public void setIdSolicitud(int idSolicitud) {
			this.idSolicitud = idSolicitud;
		}

		public String getErrorGeneralDatos() {
			return errorGeneralDatos;
		}

		public void setErrorGeneralDatos(String errorGeneralDatos) {
			this.errorGeneralDatos = errorGeneralDatos;
		}

		public String getErrorAviso() {
			return errorAviso;
		}

		public void setErrorAviso(String errorAviso) {
			this.errorAviso = errorAviso;
		}

		public String getErrorMatricula() {
			return errorMatricula;
		}

		public void setErrorMatricula(String errorMatricula) {
			this.errorMatricula = errorMatricula;
		}

		public String getErrorEmail() {
			return errorEmail;
		}

		public void setErrorEmail(String errorEmail) {
			this.errorEmail = errorEmail;
		}

		public String getErrorTelefono() {
			return errorTelefono;
		}

		public void setErrorTelefono(String errorTelefono) {
			this.errorTelefono = errorTelefono;
		}

		public String getResultCurp() {
			return resultCurp;
		}

		public void setResultCurp(String resultCurp) {
			this.resultCurp = resultCurp;
		}

        public void setResult(boolean isResult) {
		    this.isResult = isResult;
	    }

		public String getCurpAeliminar() {
			 return curpAeliminar;
		}

       	public List<SelectItem> getLstAreaNormativa() {
		    return lstAreaNormativa;
        }

		public void setCurpAeliminar(String curpAeliminar) {
			this.curpAeliminar = curpAeliminar;
		}

		public boolean isFlagThirdSlide() {
			return flagThirdSlide;
		}

		public void setFlagThirdSlide(boolean flagThirdSlide) {
			this.flagThirdSlide = flagThirdSlide;
		}
		
		public void setLstAreaNormativa(List<SelectItem> lstAreaNormativa) {
		     this.lstAreaNormativa = lstAreaNormativa;
	    }
		
		public List<AreaNormativaDTO> cargaAreaNormativaNCat()
		{
			List<AreaNormativaDTO> lista = null;
			try{
		       lista=  catalogoService.listarAreaNormativaPrincipal(); 
			}
			catch(Exception e){
				System.out.println("cargaAreaNormativaNCat");
				e.printStackTrace();
				log.error("  ", e);
			}
			return lista;
		}
		
		public List<DepartamentoDTO> cargaDepartamentoNCat()
		{
			List<DepartamentoDTO> lista = null;
			try{
		       lista=  catalogoService.listarDepartamentoPrincipal(); 
			}
			catch(Exception e){
				System.out.println("cargaDepartamentoNCat");
				e.printStackTrace();
				log.error("  ", e);
			}
			return lista;
		}
		
		public List<PuestoDTO> cargaPuestoNCat()
		{
			List<PuestoDTO> lista = null;
			try{
		       lista=  catalogoService.listarPuestoPrincipal(); 
			}
			catch(Exception e){
				System.out.println("cargaPuestoNCat");
				e.printStackTrace();
				log.error("  ", e);
			}
			return lista;
		}
		 
		public List<DelegacionDTO> cargaDelegacionnNCat()
		{
			List<DelegacionDTO> lista = null;
			try{
		       lista=  catalogoService.listarDelegacionPrincipal(); 
			}
			catch(Exception e){
				System.out.println("cargaDelegacionNCat");
				e.printStackTrace();
				log.error("  ", e);
			}
			return lista;
		}
		
		public List<SubdelegacionDTO> cargaSubDelegacionNCat()
		{
			List<SubdelegacionDTO> lista = null;
			try{
		       lista=  catalogoService.listarSubDelegacionPrincipal(); 
			}
			catch(Exception e){
				System.out.println("cargaSubDelegacionNCat");
				e.printStackTrace();
				log.error("  ", e);
			}
			return lista;
		}
		
		public List<UmfDTO> cargaUmfNCat()
		{
			List<UmfDTO> lista = null;
			try{
		       lista=  catalogoService.listarUMFPrincipal(); 
			}
			catch(Exception e){
				System.out.println("cargaUmfNCat");
				e.printStackTrace();
				log.error("  ", e);
			}
			return lista;
		}
		
		public List<SelectItem> getLstDepartamento() {
	         	return lstDepartamento;
	       } 
		 
		public List<DelegacionDTO> cargaDelegacionNCat()
		{
			List<DelegacionDTO> lista = null;
			try{
			   lista = catalogoService.listarDelegacion();  
			}
			catch(Exception e){}
			return lista;
		}

		public int getCveDelegacion() {
			return cveDelegacion;
		}

		public void setCveDelegacion(int cveDelegacion) {
			this.cveDelegacion = cveDelegacion;
		}

		public int getCveSubelegacion() {
			return cveSubelegacion;
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

		public void setCveSubelegacion(int cveSubelegacion) {
			this.cveSubelegacion = cveSubelegacion;
		}

		public int getCveUMF() {
			return cveUMF;
		}

		public void setCveUMF(int cveUMF) {
			this.cveUMF = cveUMF;
		}

		public int getClaveAreaNormativa() {
			return claveAreaNormativa;
		}

		public Long getClaveDepartamentoPerfil() {
		     return claveDepartamentoPerfil;
	    }

	    public void setClaveDepartamentoPerfil(Long claveDepartamento) {
		    this.claveDepartamentoPerfil = claveDepartamento;
	    }

		public void setClaveAreaNormativa(int claveAreaNormativa) {
			this.claveAreaNormativa = claveAreaNormativa;
		} 
		
		//Datos para registrar al usuario
		public String registrarSolicitudCuentaNVer() throws AdmonUsuariosException {
			this.flagThirdSlide = true;
			boolean res2;
			boolean res3;
			Locale defloc = Locale.getDefault();
			solicitudDTO.setDesUsrCurp(solicitudDTO.getDesUsrCurp().toUpperCase(defloc));
			  //Validaciones para regresar un error sino estan bien los datos
			  errorTelefono = "";
			  errorEmail = "";
			  errorMatricula = "";
			  errorGeneralDatos = "";
			  claveDelegacion = cveDelegacion;
			  claveSubDelegacion = cveSubelegacion;
			  claveUMF = cveUMF;
			
			  idSolicitud =solicitudCriteria.registrarSolicitudCuentaNVer(cambiaValores(solicitudDTO));
			  List sols =  solicitudCriteria.searchSolCurp(solicitudDTO, new Long("1"));
			  if(sols!=null && sols.size()>0)
			  {
				  idSolicitud = (int)((SolicitudDTO)sols.get(0)).getCveSsosolicitud();
			  }
			  solicitudCuentaService.registrarNotificacionCuentaNVer(cambiaValores(solicitudDTO), Long.parseLong(idSolicitud+""));
			  solicitudDTO.setAprobador(usuario.getAprobadorSession());

//			  String msg = "-- SE REGISTRO SU CUENTA USUARIO --";
//			  String titulo = "CREACION DE CUENTA";		
//			  mensajeriaService.enviarCorreo(usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), solicitudDTO.getRefCorreoElectronico(), msg, titulo,solicitudDTO,"Registro de cuenta");
			return "inicializada";
		}
   
		 public void registrarNotificacionCuentaNVer()
		 {
			 try{
			 solicitudCuentaService.registrarNotificacionCuentaNVer(cambiaValores(solicitudDTO), Long.parseLong(idSolicitud+""));
			 }
			 catch(Exception e)
			 {
				 log.error("  ", e);
			 }
		 }
		 
		 
		 public Long getClavePuestoPerfil() {
	        return clavePuestoPerfil;
	     }
		 
		 public void eliminaPerfilesLDAP(String perfiles){
			String [] arregloPerfilesAeliminar = perfiles.split(" ");
			int j=0;
			try{
				admonUsuariosService.eliminaUsuario(curpAeliminar);  //Elimina los registros 
				}
			catch(Exception e){
					System.out.println("Error cuando se eliminaba el usuario");
					log.error("  ", e);
			 }
		 }
			
		public Long obtenerIDSolicitud(String curp)
		{
			Long idSolicitud =0L;
			try{
			   idSolicitud = solicitudCuentaService.obtenerIDSolicitud(curpAeliminar);
			 }
			catch(Exception e){}
			 return idSolicitud;
			}
		 
		public void eliminaPerfilesModulosBD(Long idSolicitud, SolicitudDTO usuario){
			try{
				 solicitudCuentaService.eliminaPerfilesModulosBD(idSolicitud, cambiaValores(usuario));  
				}
			catch(Exception e){
				System.out.println("Error cuando se eliminaban los perfiles y modulos del usuario");
				log.error("  ", e);
				}
		}
			
		public void actualizarSolicitudBD(Long idSolicitud)
		{
			try{
				solicitudCuentaService.actualizarSolicitudBD(idSolicitud, cambiaValores(solicitudDTO));
				}
				catch(Exception e){}
		}
			
		public void registrarModulos(Long idSolicitud, int cveDepartamento, int cveModulo, int estatus){
			try{
				solicitudCuentaService.registrarModulos(idSolicitud, cveDepartamento, cveModulo,estatus);
				}
				catch(Exception e){}
		}
		public void agregarClaves(AreaNormativaDTO an, DepartamentoDTO dep, PuestoDTO puesto){
			solicitudDTO.setAreaNorm(an);
			solicitudDTO.setDptoDTO(dep);
			solicitudDTO.setPuestoDTO(puesto);
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

		public int getClaveDelegacion() {
			return claveDelegacion;
		}
			
		public void setClavePuestoPerfil(Long clavePuesto) {
		    this.clavePuestoPerfil = clavePuesto;
	    }

	    public Long getClaveAreaNormativaPerfil() {
		   return claveAreaNormativaPerfil;
	    }

    	public void setClaveAreaNormativaPerfil(Long claveAreaNormativa) {
	     	this.claveAreaNormativaPerfil = claveAreaNormativa;
	    }
			
		public String buscaAreaNormativa(int clave)
		{
			String res = "";
			String claveArea = String.valueOf(clave);
			try{
			List<AreaNormativaDTO> lista=this.catalogoService.listarAreaNormativaPrincipal( );			
			for (AreaNormativaDTO an : lista){
				if(an.getCveSsoareanorma() == Long.parseLong(String.valueOf(clave)))
					 res = an.getDesAreanorma();
			 }
			}
			catch(Exception e){
				System.out.println("Error al buscar el area Normativa");
				log.error("  ", e);
			}
			return res;	    	
		}
			
		public AreaNormativaDTO buscaAreaNormativaObj(int clave)
		{
			String claveArea = String.valueOf(clave);
			AreaNormativaDTO res = null; 
			try{
				res=this.catalogoService.listarAreaNormativaPrincipalPorClave(clave);
			}
			catch(Exception e){
				System.out.println("Error al buscar el area Normativa");
				log.error("  ", e);
			  }
			return res;	
		}
			
		public String buscaDepartamento(int claveArea, int claveDep)
		{
			String res = "";
			String clvArea = String.valueOf(claveArea);
			String clvDep = String.valueOf(claveDep);
			try{
				List<DepartamentoDTO> listdep=this.catalogoService.listarDepartamentosByAreaNormativa(clvArea);
				for(DepartamentoDTO dep : listdep){
					if((int)dep.getCveSsodepto() == claveDep)
						 res = dep.getDesDepartamento();
				   }
				}
			catch(Exception e){
					System.out.println("Error al buscar el Departamento");
					log.error("  ", e);
			 }	
			 return res;   	
		}
			
		public DepartamentoDTO buscaDepartamentoObj(int claveArea, int claveDep)
		{
		  DepartamentoDTO res = null;
		  String clvArea = String.valueOf(claveArea);
		  String clvDep = String.valueOf(claveDep);
		  try{
			 List<DepartamentoDTO> listdep=this.catalogoService.listarDepartamentosByAreaNormativa(clvArea);
			 for(DepartamentoDTO dep : listdep){
				if((int)dep.getCveSsodepto() == claveDep)
					 res = dep;
			     }
			  }
		  catch(Exception e){
			   System.out.println("Error al buscar el Departamento");
			   log.error("  ", e);
			  }
			 return res;	
			}
			
		   public Long getClaveDepartamentoModulo() {
	              return claveDepartamentoModulo;
	       }
			
		   public String buscaPuesto(int claveDep,int clavePuesto) throws Exception
		   {
			 String res = "";
			 String clvDep = String.valueOf(claveDep);
			 String clvPuesto = String.valueOf(clavePuesto);
			 try{
				List<PuestoDTO> listapuesto=this.catalogoService.listarPuestoByDepartamentoPrincipal(clvDep);
				for(PuestoDTO pt: listapuesto){
					     if(pt.getCvePuesto()==clavePuesto)
					    	 res = pt.getNombrePuesto();
				    }
				
				if("".equals(res)){
					PuestoDTO puesto = this.catalogoService.listarPuestoByClave(clavePuesto);
					res = puesto.getNombrePuesto();
				}
				
				}
				catch(Exception e){
					log.error("  ", e);
					System.out.println("Error al buscar el puesto");
					throw new Exception("NO SE ENCONTRO EL PUESTO");
				}
				
				return res;
			}
			
			public void setClaveDepartamentoModulo(Long claveDepartamentoModulo) {
		                this.claveDepartamentoModulo = claveDepartamentoModulo;
                 	}
			
			public String buscaModulo(int claveDep, int claveModulo)
			{
				String res = "";
				String clvDep = String.valueOf(claveDep);
				try{
				List<ModuloDTO> listamod=this.catalogoService.listarModulosByDepartamento(clvDep);
				for(ModuloDTO md : listamod){
					   if((int)md.getCveIdModulo() == claveModulo)
						   res= md.getDesModulo();
				   }
				}
				catch(Exception e){
					System.out.println("Error al buscar el Modulo");
					log.error("  ", e);
				}
				
				return res;  	
			}
			
			public Long getClaveAreaNormativaModulo() {
		             return claveAreaNormativaModulo;
                 	}
			
			public List<ModuloDTO> listarModulosByDepartamento(String cveDep)
			{
				List<ModuloDTO> lista = null;
				try{
					lista = this.catalogoService.listarModulosByDepartamento(cveDep);
				}
				catch(Exception e){
					log.error("  ", e);
				}
				return lista;
			}

            public void setClaveAreaNormativaModulo(Long claveAreaNormativaModulo) {
	                this.claveAreaNormativaModulo = claveAreaNormativaModulo;
	        }

	        public Long getClaveModulo() {
		           return claveModulo;
	        }

			public int getCveAreaNorm() {
				return cveAreaNorm;
			}

			public void setCveAreaNorm(int cveAreaNorm) {
				this.cveAreaNorm = cveAreaNorm;
			}

			public String getCurpNueva() {
				return curpNueva;
			}

			public void setCurpNueva(String curpNueva) {
				this.curpNueva = curpNueva;
			}
			
		 	public void setClaveModulo(Long claveModulo) {
	               this.claveModulo = claveModulo;
	        }
			
			public Collection<UsuarioDTO> actualizarTabla(int delegacion, int subdelegacion, int umf)
			{
				Collection<UsuarioDTO> usuarios = null ;
				try{
				usuarios = this.admonUsuariosService.listaUsuarios(cveDeleg, subdelegacion, umf);
				}
				catch(Exception e)
				{
					log.error("  ", e);
				}
				return usuarios;
			}
			
			public Collection<UsuarioDTO> actualizarTabla( )
			{
				Collection<UsuarioDTO> usuarios = null ;
				try{
				usuarios = this.admonUsuariosService.listaUsuario( );
				}
				catch(Exception e)
				{
					log.error("  ", e);
				}
				return usuarios;
			}
				
			public void desactivarUsuario(String curp)
			{
				try{
			     this.admonUsuariosService.desactivarUsuario(curp);
				 String titulo ="Desactivación de Cuenta";
				 String mensaje = "Su cuenta con el id de Usuario: " + curp + " se ha Desactivado ";					
				}catch(Exception e){
					log.error("  ", e);
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
			
			public void eliminaValoresDefault()
			{
				this.solicitudDTO = null;
				this.solicitudDTO = new SolicitudDTO();
				solicitudDTO.setPuestosDTO(new ArrayList<PuestoDTO>());
				solicitudDTO.setModulosDTO(new ArrayList<ModuloDTO>());
				claveAreaNormativa = -99;
				cveDelegacion = -99;
				cveSubelegacion = -99;
				cveUMF = -99;
			}

			public void eliminaValoresDefaultSinCurp()
			{
				String curp = solicitudDTO.getDesUsrCurp();
				this.solicitudDTO = null;
				this.solicitudDTO = new SolicitudDTO();
				this.solicitudDTO.setDesUsrCurp(curp);
				claveAreaNormativa = 99;
				cveDelegacion = 99;
				cveSubelegacion = 99;
				cveUMF = 99;
			}

			public List<SelectItem> listenerSubDelegaciones(String cveDeleg){
				return listarSubDelegaciones(cveDeleg);
		    }
			
			public List<SelectItem> listenerUMF(String cveSubDeleg){
				return listarUMF(cveSubDeleg);
		    }
			
			public List<SelectItem> listarSubDelegaciones(String cveDeleg)
			{
				//this.lstSubDelegacion.clear();
				List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
				try{
				List<SubdelegacionDTO> listaSubDelg = this.catalogoService.listarSubdelegacion(Long.parseLong(cveDeleg));			
				for(SubdelegacionDTO sb: listaSubDelg){
					lstSubDelegacion.add(new SelectItem(sb.getCveSubelegacion(),sb.getNombreSubelegacion()));
				  }	
				}
				catch(Exception e){
					System.out.println("Error al inicializar la lista de SubDelegaciones");
					log.error("  ", e);
				}
				return lstSubDelegacion;
			}
			
			public List<SelectItem> getLstModulo() {
	              return lstModulo;
	        }  
			
			public List<SelectItem> listarUMF(String cveSubDeleg)
			{
				List<SelectItem> lstUMF = new ArrayList<SelectItem>();
				try{
				List<UnidadMedicaDTO> listaUMF = this.catalogoService.listarUnidadMedica(Long.parseLong(cveSubDeleg));			
				for(UnidadMedicaDTO um: listaUMF){
					lstUMF.add(new SelectItem(um.getCveUmf(),um.getNombreUmf()));
				  }	
				}
				catch(Exception e){
					System.out.println("Error al inicializar la lista de UMF's");
					log.error("  ", e);
				}
				return lstUMF;
			}
			
			public List<SelectItem> listenerUnidadMedica(){
				//selectUMF = null;
				//this.lstUMF.clear();	
				List<SelectItem> lstUMF = new ArrayList<SelectItem>();
					try{
						List<UnidadMedicaDTO> listaUMF =  this.catalogoService.listarUnidadMedica(Long.parseLong(String.valueOf(cveSubelegacion)));
						for(UnidadMedicaDTO um: listaUMF){
							lstUMF.add(new SelectItem(um.getCveUmf(),um.getNombreUmf()));
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					return lstUMF;
		    }
			
			public List<SolicitudDTO> listarSolicitudesRecuperacion()
			{
				List<SolicitudDTO> listaSolicitudes = null;
				try{
					claveDelegacion = cveDeleg; 
					listaSolicitudes =  this.catalogoService.listarSolicitudesRecuperacion(claveDelegacion, claveSubDelegacion, claveUMF);
				}
				catch(Exception e){
					System.out.println("No se pudo obtener el listado de solicitudes");
					log.error("  ", e);
				}
				return listaSolicitudes;
			}
			
			public String recuperarCuenta(long idSolicitudRecuperar, int delegacion, int subdelegacion, int umf)
			{
				try{
				solicitudCuentaService.actualizarSolicitudBDRecuperacion(idSolicitudRecuperar, delegacion, subdelegacion, umf);
				}
				catch(Exception e){
					log.error("  ", e);
				}
			    return "exitoGuardar";
			}
			
			public boolean getFlagEliminacion()
			{
				return flagEliminacion;
			}
			
			public void setFlagEliminacion(boolean flagEliminacion)
			{
				this.flagEliminacion = flagEliminacion;
			}
			
			public List<SelectItem> getLstSubDelegacion() {
				return lstSubDelegacion;
			}

			public void setLstSubDelegacion(List<SelectItem> lstSubDelegacion) {
				this.lstSubDelegacion = lstSubDelegacion;
			}
			
		   public List<SelectItem> getLstDelegacion() {
				return lstDelegacion;
			}

			public void setLstDelegacion(List<SelectItem> lstDelegacion) {
				this.lstDelegacion = lstDelegacion;
			}
			
			public List<SelectItem> getLstUMF() {
				return lstUMF;
			}

			public void setLstUMF(List<SelectItem> lstUMF) {
				this.lstUMF = lstUMF;
			}
			
			public int getCveDeleg()
			{
				return cveDeleg;
			}
			
	        public void setCveDeleg(int cveDeleg)
	        {
	        	this.cveDeleg = cveDeleg;
	        }

			public UsuarioMB getUsuario() {
				return usuario;
			}

			public void setUsuario(UsuarioMB usuario) {
				this.usuario = usuario;
			}

			public SolicitudServiceLocal getSolicitudCriteria() {
				return solicitudCriteria;
			}

			public void setSolicitudCriteria(SolicitudServiceLocal solicitudCriteria) {
				this.solicitudCriteria = solicitudCriteria;
			}

			public AdmonUsuariosSessionLocal getAdmonUsuariosService() {
				return admonUsuariosService;
			}

			public void setAdmonUsuariosService(
					AdmonUsuariosSessionLocal admonUsuariosService) {
				this.admonUsuariosService = admonUsuariosService;
			}

			public AdmonPerfilesSessionLocal getAdmonsPerfilesService() {
				return admonsPerfilesService;
			}

			public void setAdmonsPerfilesService(
					AdmonPerfilesSessionLocal admonsPerfilesService) {
				this.admonsPerfilesService = admonsPerfilesService;
			}

			public SolicitudCuentaSessionLocal getSolicitudCuentaService() {
				return solicitudCuentaService;
			}

			public void setSolicitudCuentaService(
					SolicitudCuentaSessionLocal solicitudCuentaService) {
				this.solicitudCuentaService = solicitudCuentaService;
			}

			public MensajeriaSessionLocal getMensajeriaService() {
				return mensajeriaService;
			}

			public void setMensajeriaService(MensajeriaSessionLocal mensajeriaService) {
				this.mensajeriaService = mensajeriaService;
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

			public List<SolicitudDTO> getListaSolExt() {
				return listaSolExt;
			}

			public void setListaSolExt(List<SolicitudDTO> listaSolExt) {
				this.listaSolExt = listaSolExt;
			}

			public boolean isGeneralErrorPersist() {
				return GeneralErrorPersist;
			}

			public void setGeneralErrorPersist(boolean generalErrorPersist) {
				GeneralErrorPersist = generalErrorPersist;
			}

			public int getTesting() {
				return testing;
			}

			public void setTesting(int testing) {
				this.testing = testing;
			}

			public Long getIdDeptoGral() {
				return idDeptoGral;
			}

			public void setIdDeptoGral(Long idDeptoGral) {
				this.idDeptoGral = idDeptoGral;
			}

			public SolicitudDTO getFiltro() {
				return filtro;
			}

			public void setFiltro(SolicitudDTO filtro) {
				this.filtro = filtro;
			}

			public void setLstModulo(List<SelectItem> lstModulo) {
				this.lstModulo = lstModulo;
			}

			public String getNssNom() {
				return nssNom;
			}

			public void setNssNom(String nssNom) {
				this.solicitudDTO.setNssNom(nssNom);
			}

			public String getPuestoDescNom() {
				return puestoDescNom;
			}

			public void setPuestoDescNom(String puestoDescNom) {
				this.solicitudDTO.setPuestoDescNom(puestoDescNom);
			}

			public String getDepartamentoDescNom() {
				return departamentoDescNom;
			}

			public void setDepartamentoDescNom(String departamentoDescNom) {
				this.solicitudDTO.setDepartamentoDescNom(departamentoDescNom);
			}

			public String getCveDelegacionNom() {
				return cveDelegacionNom;
			}

			public void setCveDelegacionNom(String cveDelegacionNom) {
				this.solicitudDTO.setCveDelegacionNom(cveDelegacionNom);
			}

			public String getCveSubdelegacionNom() {
				return cveSubdelegacionNom;
			}

			public void setCveSubdelegacionNom(String cveSubdelegacionNom) {
				this.solicitudDTO.setCveSubdelegacionNom(cveSubdelegacionNom);
			}

			public String getCveUmfNom() {
				return cveUmfNom;
			}

			public void setCveUmfNom(String cveUmfNom) {
				this.solicitudDTO.setCveUmfNom(cveUmfNom);
			}

			public long getCveEstatusNom() {
				return cveEstatusNom;
			}

			public void setCveEstatusNom(long cveEstatusNom) {
				this.solicitudDTO.setCveEstatusNom(cveEstatusNom);
			}
	        
	        
}
