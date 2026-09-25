package mx.gob.imss.ctirss.sso.admonusuarios.MB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
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
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;

import org.icefaces.ace.event.SelectEvent;


@ManagedBean(name="modificarSolicitudMB")
@CustomScoped("#{window}")
public class ModificarSolicitudMB {
	
	@ManagedProperty("#{solicitudMB}")	 
	private SolicitudMB solicitudMB;
	
//	@ManagedProperty("#{consultaGenerica}")	 
//	private ConsultaGenericaController consultaGenerica;
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@EJB
	private SolicitudServiceLocal solicitudCriteria;
	
	@EJB
	private CatalogoServiceLocal catalogoService;
	
    @ManagedProperty(value="#{usuarioMB}")	 
	private UsuarioMB usuario;

	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;

	@EJB
	private MensajeriaSessionLocal mensajeriaService; 

	@EJB
	private BitacoraServiceLocal bitacoraService; 

	private List<SolicitudDTO> lista = new ArrayList<SolicitudDTO>();
	private SolicitudDTO solicitud = new SolicitudDTO();
	private int pagina = 2;
	private boolean disabledBotonAdd = false;
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();
	private Long clavePuesto;
	private Long claveModulo;
	
	private boolean disabledBotonAutorizar = true;
	private boolean disabledBotonRechazar = true;
	private boolean disabledBotonBorrar = true;

	private String correo = "";
	private String telefono = "";
	
	
	

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

	public String inicializaModificarPerfil()throws AdmonUsuariosException{
   		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
   		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
   		session.setAttribute("pagina", pagina);
		if (!FacesContext.getCurrentInstance().isPostback()) {
			filtrosConsulta.obtieneDatosUsuario();
			obtenerSolicitudByFiltro();
			solicitud = new SolicitudDTO();
	    }
		return "modificarPerfil";
	}
   	

	public void inicializaParametros()throws AdmonUsuariosException
	{
		if (!FacesContext.getCurrentInstance().isPostback()) {
			filtrosConsulta.obtieneDatosUsuario();
			obtenerSolicitudByFiltro();
			solicitud = new SolicitudDTO();
	    }
	}
	
    public void controlaComponentes() throws AdmonUsuariosException{
    	if(solicitud.getEstatusDTO().getCveSsoestatus()==Constantes.ESTATUS.AUTORIZADO.getOpcion()){
    		disabledBotonBorrar = false;
			disabledBotonAutorizar = false;
			disabledBotonRechazar = false;
			disabledBotonAdd = false;
		}else{
			disabledBotonBorrar = true;
    		disabledBotonAdd = true;
			disabledBotonAutorizar = true;
			disabledBotonRechazar = true;
		}
    }

    public void cambiaTab(ValueChangeEvent event)  throws AdmonUsuariosException{
    }


	
	public void obtenerSolicitudByFiltro() throws AdmonUsuariosException{
		solicitudMB.llenaFiltro();
		EstatusDTO s = new EstatusDTO();
		s.setCveSsoestatus(Constantes.ESTATUS.AUTORIZADO.getOpcion());
		solicitudMB.getFiltro().setEstatusDTO(s);
		System.out.println("Invoca search solicitudes - ModificarSolicitudMB");
		lista = solicitudCriteria.searchSolicitudes(solicitudMB.getFiltro(), usuario.getAprobadorSession().getSolicitud().getDesUsrCurp());
	}
	
	public void selectListenerDetalle(SelectEvent event)  throws AdmonUsuariosException{
		solicitud = (SolicitudDTO)event.getObject();
		correo = solicitud.getRefCorreoElectronico().replace("@imss.gob.mx", "");
		telefono = solicitud.getDesTelefonoOfi();
		filtrosConsulta.seteaDepto(solicitud.getDepartamentoId());
		controlaComponentes();
    }
	
	
	public void selectListenerDetalle(){
		try {
			correo = solicitud.getRefCorreoElectronico().replace("@imss.gob.mx", "");
			telefono = solicitud.getDesTelefonoOfi();
			filtrosConsulta.setClaveAreaNormativa(solicitud.getDptoDTO().getAreaNormativa().getCveSsoareanorma());
			filtrosConsulta.seteaDepto(solicitud.getDepartamentoId());
			controlaComponentes();
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
    }

	public void lisTel(ValueChangeEvent event)  throws AdmonUsuariosException{
		telefono = (String)event.getNewValue();
    }
	
	public void lisMail(ValueChangeEvent event)  throws AdmonUsuariosException{
		correo = (String)event.getNewValue();
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

    public void obtienePerfilListener(ValueChangeEvent ve) throws AdmonUsuariosException{
    	this.clavePuesto = (Long)ve.getNewValue();
    	if(clavePuesto!=null){
    		disabledBotonAdd=false;
    	}else{
    		disabledBotonAdd=true;
    	}
    }
    
    
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

	
    public void agregaPerfil(){
    	try 
    	{
        	if(clavePuesto!=null  && clavePuesto>0)
        	{
        		PerfilDTO perfil = new PerfilDTO();
        		DepartamentoDTO deptoDTO = solicitud.getDptoDTO();
        		PuestoDTO puestoDTO = new PuestoDTO();
        		SolicitudDTO solDTO = new SolicitudDTO();
        		puestoDTO.setCvePuesto(clavePuesto);
        		puestoDTO.setNombrePuesto(solicitudCriteria.getPerfilDesc(clavePuesto));
        		solDTO.setCveSsosolicitud(solicitud.getCveSsosolicitud());
        		perfil.setAreaNormDTO(solicitud.getDptoDTO().getAreaNormativa());
        		perfil.setDeptoDTO(deptoDTO);
        		perfil.setPuestoDTO(puestoDTO);
        		perfil.setSolicitudDTO(solicitud);
        		perfil.setDesDefault("No");
        		if(!validaPerfilExistente(solicitud.getPerfilesDTO(), perfil))
        		{
        			solicitud.getPerfilesDTO().add(perfil);
					filtrosConsulta.setDesc("El Perfil fue agregado  a esta cuenta de usuario.");
					filtrosConsulta.setMsg(true);
        		}else{
					filtrosConsulta.setDesc("El Perfil ya existe para esta cuenta de usuario.");
					filtrosConsulta.setMsg(true);
        		}
        	}
        	else
        	{
				filtrosConsulta.setDesc("El Perfil seleccionado no es valido.");
				filtrosConsulta.setMsg(true);
        	}
		} catch (AdmonUsuariosException e) {
			System.out.println(" Modificar Solicitud Agrega perfil 24");
			e.printStackTrace();
		}
	}
    
	public boolean validarEmail(String email) {
		if (email == null || "".equals(email)) {
			filtrosConsulta.showMsg("El correo electrónico es incorrecto, favor de verificar");
		}

		boolean res = email.matches(EMAIL_PATTERN);
		if (res == false) {
			filtrosConsulta.showMsg("El correo electrónico es incorrecto, favor de verificar");
		}
		return res;
	}
    
    public void aplicaCambiosCuenta(){
    	boolean res = validarEmail(correo+"@imss.gob.mx");
    	if(res)
    	{
    		if(solicitud.getDesUsrCurp()!=null&&solicitud.getDesUsrCurp().length()>0)
    		{
    	    	try {
    		    	  solicitud.setCveAprobador(usuario.getAprobadorSession().getCveIdAprobador());
    		    	  solicitud.setRefCorreoElectronico(correo+"@imss.gob.mx");
    		    	  solicitud.setDesTelefonoOfi(telefono);
    		    	  
    		    	  System.out.println("########## LA CUENTA SELECCIONADA PARA MODIFICAR ES ["+solicitud.getDesUsrCurp()+"] EN LA CLASE MODIFICAR SOLICITUD MB ##########");
    		    	  
    		    	  System.out.println("########## AreaNormativaId ["+solicitud.getAreaNormativaId()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveDelegacionNom ["+solicitud.getCveDelegacionNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveEstatusNom ["+solicitud.getCveEstatusNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveIdEntidad ["+solicitud.getCveIdEntidad()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveMatricula ["+solicitud.getCveMatricula()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveSsosolicitud ["+solicitud.getCveSsosolicitud()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveSubdelegacionNom ["+solicitud.getCveSubdelegacionNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveUmfNom ["+solicitud.getCveUmfNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## DelegacionId ["+solicitud.getDelegacionId()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## DepartamentoDescNom ["+solicitud.getDepartamentoDescNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## DepartamentoId ["+solicitud.getDepartamentoId()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## DesTelefonoOfi ["+solicitud.getDesTelefonoOfi()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## DesUsrCurp ["+solicitud.getDesUsrCurp()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## Estatus ["+solicitud.getEstatus()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## EstatusNom ["+solicitud.getEstatusNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## GruposDesc ["+solicitud.getGruposDesc()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## ModulosDesc ["+solicitud.getModulosDesc()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NombreAprobador ["+solicitud.getNombreAprobador()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NombreCompleto ["+solicitud.getNombreCompleto()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NomMaterno ["+solicitud.getNomMaterno()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NomNombre ["+solicitud.getNomNombre()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NomPaterno ["+solicitud.getNomPaterno()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NssNom ["+solicitud.getNssNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NumModulos ["+solicitud.getNumModulos()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NumPerfiles ["+solicitud.getNumPerfiles()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## Password ["+solicitud.getPassword()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## PuestoDescNom ["+solicitud.getPuestoDescNom()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## PuestoId ["+solicitud.getPuestoId()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## RefCorreoElectronico ["+solicitud.getRefCorreoElectronico()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## SubdelegacionId ["+solicitud.getSubdelegacionId()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## UmfId ["+solicitud.getUmfId()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CveAprobador ["+solicitud.getCveAprobador()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## FecUsrNacimiento ["+solicitud.getFecUsrNacimiento()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## NombrePuesto ["+solicitud.getPuestoDTO().getNombrePuesto()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  System.out.println("########## CvePuesto ["+solicitud.getPuestoDTO().getCvePuesto()+"] del usuario "+solicitud.getDesUsrCurp()+" ##########");
    				  
    				  for (PerfilDTO perfilDTO : solicitud.getPerfilesDTO()) {
    					  System.out.println("########## PERFIL A MODFICIAR ["+perfilDTO.getPuestoDTO().getNombrePuesto()+"] DEL USUARIO "+solicitud.getDesUsrCurp()+" ##########");
    				  }
    				  
    		    	  solicitudCriteria.aplicaCambiosSolicitud(solicitud);
    		          
    		          bitacoraService.guardaSolicitudBit(solicitud.getCveSsosolicitud(), usuario.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_MODIFICACION);
    		          solicitud.setAprobador(usuario.getAprobadorSession());
    		
//    	        	  String msg = "ha sido modificada";
//    				  String titulo = "APROBACION Y ACTUALIZACION DE CUENTA";		
//    				  mensajeriaService.enviarCorreo(usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), solicitud.getRefCorreoElectronico(), msg, titulo,solicitud,"Aprobación y actualización de cuenta");
//    				  mensajeriaService.enviarCorreoAprobador(usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(),usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), msg, titulo,solicitud,"Aprobación y actualización de cuenta");

    				  filtrosConsulta.setDesc("Se aplicaron exitosamente los cambios sobre la cuenta del usuario.");
    				  filtrosConsulta.setMsg(true);


    				  solicitud.getEstatusDTO().setCveSsoestatus(Constantes.ESTATUS.SOLICITADO.getOpcion());

    				  inicializaParametros();
    				  controlaComponentes();
    				  obtenerSolicitudByFiltro();
    			} catch (Exception e) {
    				e.printStackTrace();
    				filtrosConsulta.setDesc("Error al aplicar modificacion sobre la cuenta de usuario.");
    				filtrosConsulta.setMsg(true);
    			}
    		}
    		else
    		{
    			filtrosConsulta.setDesc("La cuenta de usuario no cuenta con las caracteristicas necesarias para procesar la modificación de información");
    			filtrosConsulta.setMsg(true);
    		}    
    	}
    }

    public void cancelaCambiosCuenta(){
    	try {
			solicitud.setPerfilesDTO(solicitudCriteria.perfilesBySol(solicitud.getCveSsosolicitud()));
	    	solicitud.setModulosDTO(solicitudCriteria.modulosBySol(solicitud.getCveSsosolicitud()));
	    	correo = solicitud.getRefCorreoElectronico().replace("@imss.gob.mx", "");
	    	telefono = solicitud.getDesTelefonoOfi();
	    	filtrosConsulta.setDesc("Se cancelaron los cambios sobre la cuenta del usuario.");
			filtrosConsulta.setMsg(true);
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
    }

    public boolean validaPerfilExistente(List<PerfilDTO> perfiles, PerfilDTO perfil)
    {
    	if(perfiles!=null&&perfiles.size()>0)
    	{
    		for(PerfilDTO per : perfiles)
    		{
    			if(per.getPuestoDTO().getCvePuesto()==perfil.getPuestoDTO().getCvePuesto())
    			{
    				return true;
    			}
    		}
    	}
    	return  false;
    }
    
    
    public void borraPefil(AjaxBehaviorEvent event)  throws AdmonUsuariosException
    {
    	System.out.println(" Modificar Solicitud Borra perfil 0");
		Map<String, Object> attributes = event.getComponent().getAttributes();
		System.out.println(" Modificar Solicitud Borra perfil 1");
		PerfilDTO perfil =(PerfilDTO)attributes.get("attPerfil");
		System.out.println(" Modificar Solicitud Borra perfil 2");
		solicitud.getPerfilesDTO().remove(perfil);
		System.out.println(" Modificar Solicitud Borra perfil 3");
		filtrosConsulta.setDesc("El Perfil fue eliminado de esta cuenta de usuario.");
		System.out.println(" Modificar Solicitud Borra perfil 4");
		filtrosConsulta.setMsg(true);
		System.out.println(" Modificar Solicitud Borra perfil 5");
	}

	public void agregaModulo() {
		try {
			if(claveModulo!=null&&claveModulo>0)
			{
				ModuloDTO modulo = new ModuloDTO();
				modulo.setCveIdModulo(claveModulo);
				DepartamentoDTO dto = solicitud.getDptoDTO();
				modulo.setAreaNorm(solicitud.getDptoDTO().getAreaNormativa());
				modulo.setDptoDTO(dto);
				modulo.setDesModulo(solicitudCriteria.getModuloDesc(claveModulo));
				if(!validaModuloExistente(solicitud.getModulosDTO(), modulo))
				{
					solicitud.getModulosDTO().add(modulo);

					filtrosConsulta.setDesc("El módulo fue agregado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);

				}else{
					filtrosConsulta.setDesc("El módulo seleccionado ya se encuentra asociado a la cuenta de usuario.");
					filtrosConsulta.setMsg(true);
				}
			}
			else				
			{
				filtrosConsulta.setDesc("Favor de seleccionar un módulo valido.");
				filtrosConsulta.setMsg(true);
			}
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean validaModuloExistente(List<ModuloDTO> modulos,ModuloDTO modulo)
	{
		if(modulo!=null&&modulos.size()>0)
		{
			for(ModuloDTO mod : modulos)
			{
				if(mod.getCveIdModulo()==modulo.getCveIdModulo())
				{
					return true;
				}
			}
		}
		return false;
	}

	public void borraModulo(AjaxBehaviorEvent event){
		Map<String, Object> attributes = event.getComponent().getAttributes();
		ModuloDTO modulo =(ModuloDTO)attributes.get("attModulo");
		solicitud.getModulosDTO().remove(modulo);

        filtrosConsulta.setDesc("El modulo seleccionado fue eliminado de la cuenta de usuario.");
		filtrosConsulta.setMsg(true);
	}

	
    
	public SolicitudMB getSolicitudMB() {
		return solicitudMB;
	}

	public void setSolicitudMB(SolicitudMB solicitudMB) {
		this.solicitudMB = solicitudMB;
	}

	public List<SolicitudDTO> getLista() {
		return lista;
	}

	public void setLista(List<SolicitudDTO> lista) {
		this.lista = lista;
	}

	public SolicitudDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}

	
	public boolean isDisabledBotonAdd() {
		return disabledBotonAdd;
	}

	public void setDisabledBotonAdd(boolean disabledBotonAdd) {
		this.disabledBotonAdd = disabledBotonAdd;
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

	public Long getClavePuesto() {
		return clavePuesto;
	}

	public void setClavePuesto(Long clavePuesto) {
		this.clavePuesto = clavePuesto;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public UsuarioMB getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioMB usuario) {
		this.usuario = usuario;
	}

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
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

	public Long getClaveModulo() {
		return claveModulo;
	}

	public void setClaveModulo(Long claveModulo) {
		this.claveModulo = claveModulo;
	}
	
	

}
