package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.implementacion.ClienteWebserviceCurp;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.MensajeriaSession;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.event.UnselectEvent;
import org.icefaces.ace.model.table.RowStateMap;

import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.util.DataTableData;
import mx.gob.imss.ctirss.sso.util.PasswordUtil;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.component.UIComponent;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.controller.SolicituddMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@ManagedBean(name="recuperaSolicitudMB")
@CustomScoped("#{window}")
public class RecuperaSolicitudMB {
	
    @ManagedProperty("#{solicituddMB}")	 
	private SolicituddMB solicitudMB;   
    
	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuario;

    @ManagedProperty(value="#{catalogoMB}")	 
	private CatalogosMB catalogoMB;
    
    @ManagedProperty(value="#{recuperaSolicitudOtraAdscripcionMB}")	 
	private RecuperaSolicitudOtraAdscripcionMB recuperaSolicitudOtraAdscripcionMB;

    @ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;

    @EJB
	private SolicitudServiceLocal solicitudCriteria;

    
	@EJB
	private AdmonUsuariosSessionLocal admonUsuariosService;
	@EJB
	private MensajeriaSessionLocal mensajeriaService; 

	@EJB
	private AdmonRolesSessionLocal admonRoles;
	@EJB
	private BitacoraServiceLocal bitacoraService;

	private static Log log = LogFactory.getLog(RecuperaSolicitudMB.class);
	
	private static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private boolean flagFirstSlide= false;
	private boolean flagSecondSlide;
	private boolean flagThirdSlide;
	private boolean modificacion = true;
	private boolean success = true;
	private boolean fgUmfOtraAdscripcion = true;


	private String msgDesc;
	private boolean msg;
	
	private int clavePuestoDelete;
	private int claveModuloDelete;
	private int claveAccesoModuloDelete;

	
	private int clavePuesto;
	private int claveModulo;

	
	private SolicitudDTO solicitud;
	private String curpConsulta;
	private String telefonosol = "";
	private String correo = "";
	
	
	private RowStateMap stateMapFromTableOne = new RowStateMap();
	private RowStateMap stateMapFromTableTwo = new RowStateMap();

    
    private List<SolicitudDTO> usuarios;
    private RowStateMap stateMap = new RowStateMap();
    private boolean dblClick = false;
    private boolean instantUpdate = true;
    private String selectionMode = AVAILABLE_MODES[0].getValue().toString();
	private static final SelectItem[] AVAILABLE_MODES = { new SelectItem("single", "Single Row"),
		  new SelectItem("multiple", "Multiple Rows"),
		  new SelectItem("singlecell", "Single Cell")};
	
	private String solicitudRecuperar = "";
	private long idSolicitudRecuperar =  0L;
    
	private HtmlDataTable tabla; 
	
	public void lisTelefono(ValueChangeEvent event) throws AdmonUsuariosException {
		telefonosol = (String) event.getNewValue();
	}

	public void lisCorreo(ValueChangeEvent event) throws AdmonUsuariosException {
		correo = (String) event.getNewValue();
	}

	public void lisCurp(ValueChangeEvent event) throws AdmonUsuariosException {
		curpConsulta = (String) event.getNewValue();
	}
	
	public void listenerPuesto(ValueChangeEvent ve){
		try{
			clavePuesto= new Integer(ve.getNewValue().toString()).intValue();
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}
	
	public void listenerModulo(ValueChangeEvent ve){
		try{
			claveModulo= new Integer(ve.getNewValue().toString()).intValue();
		}
		catch(Exception e){
			System.out.println("Error al consultar el Puesto");
		}
	}
	
	
	public RecuperaSolicitudMB()
	{
		
	}
	
	public String consultaSolicitudes()
	{
		limpia();
		filtrosConsulta.obtieneDatosUsuario();
		solicitudMB.setClaveAreaNormativa((int)usuario.getAreaNormativa());
		solicitudMB.setClaveDepartamento((int)usuario.getAprobadorSession().getSolicitud().getDepartamentoId());
		solicitudMB.setClavePuesto((int)usuario.getAprobadorSession().getSolicitud().getPuestoId());
		solicitudMB.setClaveDelegacion((int)usuario.getAprobadorSession().getSolicitud().getDelegacionId());
		solicitudMB.setCveDeleg((int)usuario.getAprobadorSession().getSolicitud().getDelegacionId());
		solicitudMB.setClaveSubDelegacion ((int)usuario.getAprobadorSession().getSolicitud().getSubdelegacionId());
		solicitudMB.setClaveUMF ((int)usuario.getAprobadorSession().getSolicitud().getUmfId());
		solicitudMB.setFlagEliminacion(true);
		filtrosConsulta.obtieneDatosUsuario();
		filtrosConsulta.setClaveDepartamentoModAdd(filtrosConsulta.getClaveDepartamento());
		filtrosConsulta.setClaveDepartamentoRolAdd(filtrosConsulta.getClaveDepartamento());
		idSolicitudRecuperar =  0L;
		return "recuperaCuentas";
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SolicitudDTO> getMultiRow() { 
		return (ArrayList<SolicitudDTO>) stateMap.getSelected(); 
	}
	
	public boolean isDblClick() {
		return dblClick;
	}

	public void setDblClick(boolean dblClick) {
		this.dblClick = dblClick;
	}

	public boolean getInstantUpdate() {
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

	public List<SolicitudDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<SolicitudDTO> usuarios) {
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
	
	public void handleSelectUsuario(SelectEvent se) throws AdmonUsuariosException{
		idSolicitudRecuperar = this.getMultiRow().get(0).getCveSsosolicitud();
		solicitudRecuperar = this.getMultiRow().get(0).getDesUsrCurp();
	}
	
	public SolicituddMB getSolicitudMB( )
	{
		return solicitudMB;
	}
	
	public void setSolicitudMB(SolicituddMB solicitudMB)
	{
		this.solicitudMB = solicitudMB;
	}
	
	public void actualizarTabla() throws AdmonUsuariosException{						
		javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest)FacesContext
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
					System.out.println("tipo de Set:"+ attrVal.getClass().getName());
					Set tablaParam = null;
					
					tablaParam = (HashSet) attrVal;
					Iterator it = null;
					
					System.out.println("empty?"+tablaParam.isEmpty());
					System.out.println("size:"+tablaParam.size());
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
							System.out.println("Number: " + ((Integer) v).toString());
						} else {
							System.out.println("tipo:" + v.getClass().getName());
						}
					}
				} else {
					System.out.println("ni string, ni número. tipo:" + attrVal.getClass().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
	public String recuperarCuentaS()
	{
		boolean res = validarEmail(correo+"@imss.gob.mx");
		if(res)
		{
			if(solicitud.getPuestosDTO()==null||solicitud.getPuestosDTO().size()==0)
				filtrosConsulta.showMsg("Se debe agregar por lo menos un grupo a la definición de la solicitud");
			else
			{
				boolean puestoNuevo = true;
				for(PuestoDTO p : solicitud.getPuestosDTO())
				{
					if(!p.isNuevoReg())
					{
						puestoNuevo = false;
						break;
					}
				}
				if(puestoNuevo)
				{

					boolean moduloNuevo = true;
					for(ModuloDTO m : solicitud.getModulosDTO())
					{
						if(!m.isNuevoReg())
						{
							moduloNuevo = false;
							break;
						}
					}
					if(moduloNuevo)
					{
						boolean modsyPtosIguales = validaPuestosModulosMismoDepto(solicitud,(int)filtrosConsulta.getClaveDepartamento());
						if(modsyPtosIguales)
						{
							try {
								solicitud.setDelDTO(usuario.getAprobadorSession().getSolicitud().getDelDTO());
								solicitud.setSubdelDTO(usuario.getAprobadorSession().getSolicitud().getSubdelDTO());
								solicitud.setUmfDTO(usuario.getAprobadorSession().getSolicitud().getUmfDTO());
								
								DepartamentoDTO depto = new DepartamentoDTO();
								depto.setCveSsodepto(filtrosConsulta.getClaveDepartamento());
								solicitud.setDptoDTO(depto);
								
								PuestoDTO puesto = new PuestoDTO();
								puesto.setCvePuesto(filtrosConsulta.getClavePuesto());
								solicitud.setPuestoDTO(puesto);
								
								solicitud.setDesTelefonoOfi(telefonosol);
								solicitud.setRefCorreoElectronico(correo+"@imss.gob.mx");
								solicitud.setPuestosDTO(agregaPuestoDefault(solicitud.getPuestosDTO()));
								
								solicitud.setCveAprobador(usuario.getAprobadorSession().getCveIdAprobador());

								solicitudCriteria.reactivaSolicitud(solicitud);
								
								bitacoraService.guardaSolicitudBit(solicitud.getCveSsosolicitud(), usuario.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_RECUPERACION);
								
								solicitud.setAprobador(usuario.getAprobadorSession());
								
//								String titulo = "REACTIVACIÓN DE CUENTA";		
//								String msg = "ha sido recuperada";
//								mensajeriaService.enviarCorreo(usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), solicitud.getRefCorreoElectronico(), msg, titulo,solicitud,"Recuperación de cuenta");
//								mensajeriaService.enviarCorreoAprobador(usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(),usuario.getAprobadorSession().getSolicitud().getRefCorreoElectronico(), msg, titulo,solicitud,"Recuperación de cuenta");
								
								//activarUsuarioLdap(solicitud);
								//modificaModulosLdap(solicitud);
								//modificaPerfilesLdap(solicitud);
								
								limpia();
								
								filtrosConsulta.showMsg("Se reactivo la cuenta satisfactoriamente,Esta cuenta quedará inactiva hasta su autorización");
								success = true;
							} catch (AdmonUsuariosException e) {
								log.error("Error al reacitvar la solicitud de :"+ solicitud.getNombreCompleto());
								filtrosConsulta.showMsg("Error al reacitvar la solicitud");
								e.printStackTrace();
							}
						}
						else
							filtrosConsulta.showMsg("Se requiere que los perfiles y modulos de asignados a esta solicitud correspondan al mismo departamento");
					}
					else
						filtrosConsulta.showMsg("Borre todos los módulos de la solicitud y vuelva a agregar nuevos antes de continuar con la reactivación");
				}
				else
					filtrosConsulta.showMsg("Borre todos los grupos de la solicitud y vuelva a agregar nuevos antes de continuar con la reactivación");
			}
		}
		return "recuperaCuentas";
	}


	public void recuperarCuenta()
	{
		boolean res = validarEmail(correo+"@imss.gob.mx");
		if(res)
		{
			if(solicitud.getPuestosDTO()==null||solicitud.getPuestosDTO().size()==0)
				filtrosConsulta.showMsg("Se debe agregar por lo menos un grupo a la definición de la solicitud");
			else
			{
				boolean puestoNuevo = true;
				for(PuestoDTO p : solicitud.getPuestosDTO())
				{
					if(!p.isNuevoReg())
					{
						puestoNuevo = false;
						break;
					}
				}
				if(puestoNuevo)
				{

					boolean moduloNuevo = true;
					for(ModuloDTO m : solicitud.getModulosDTO())
					{
						if(!m.isNuevoReg())
						{
							moduloNuevo = false;
							break;
						}
					}
					if(moduloNuevo)
					{
						boolean modsyPtosIguales = validaPuestosModulosMismoDepto(this.solicitud, (int)this.filtrosConsulta.getClaveDepartamento());
						
						if(modsyPtosIguales)
						{
							try {
								solicitud.setDelDTO(usuario.getAprobadorSession().getSolicitud().getDelDTO());
								solicitud.setSubdelDTO(usuario.getAprobadorSession().getSolicitud().getSubdelDTO());
								
								//Recuperacion de la subdelegacion a una UMF para los usuarios de Pensiones
								solicitud.setUmfDTO(usuario.getAprobadorSession().getSolicitud().getUmfDTO());
								
								DepartamentoDTO depto = new DepartamentoDTO();
								depto.setCveSsodepto(filtrosConsulta.getClaveDepartamento());
								solicitud.setDptoDTO(depto);
								
								PuestoDTO puesto = new PuestoDTO();
								puesto.setCvePuesto(filtrosConsulta.getClavePuesto());
								solicitud.setPuestoDTO(puesto);
								
								solicitud.setDesTelefonoOfi(telefonosol);
								solicitud.setRefCorreoElectronico(correo+"@imss.gob.mx");
								solicitud.setPuestosDTO(agregaPuestoDefault(solicitud.getPuestosDTO()));
								
								solicitud.setCveAprobador(usuario.getAprobadorSession().getCveIdAprobador());
							    
								boolean regenera = admonUsuariosService.regeneraPassword(solicitud.getDesUsrCurp(), 
										PasswordUtil.getPassword(PasswordUtil.NUMEROS + PasswordUtil.MINUSCULAS +PasswordUtil.MAYUSCULAS+PasswordUtil.ESPECIALES,8));
							    
								if(regenera)
								{
									solicitudCriteria.reactivaSolicitud(solicitud);
									
									bitacoraService.guardaSolicitudBit(solicitud.getCveSsosolicitud(), usuario.getAprobadorSession().getCveIdAprobador(), Constantes.TIPO_MOV_RECUPERACION);
									
									solicitud.setAprobador(usuario.getAprobadorSession());
									
									limpia();
									
									filtrosConsulta.showMsg("Se reactivo la cuenta satisfactoriamente,Esta cuenta quedará inactiva hasta su autorización");
									success = true;
								}
								else
									filtrosConsulta.showMsg("No se reactivo la cuenta correctamente,Error al regenerar la contraseña.");
							} catch (AdmonUsuariosException e) {
								log.error("Error al reacitvar la solicitud de :"+ solicitud.getNombreCompleto());
								filtrosConsulta.showMsg("Error al reacitvar la solicitud");
								e.printStackTrace();
							}
						}
						else
							filtrosConsulta.showMsg("Se requiere que los perfiles y modulos de asignados a esta solicitud correspondan al mismo departamento");
					}
					else
						filtrosConsulta.showMsg("Borre todos los módulos de la solicitud y vuelva a agregar nuevos antes de continuar con la reactivación");
				}
				else
					filtrosConsulta.showMsg("Borre todos los grupos de la solicitud y vuelva a agregar nuevos antes de continuar con la reactivación");
			}
		}
	}

	public void recuperarCuentaOtraAdscripcion() {
		boolean emailValido = validarEmail(correo + "@imss.gob.mx");
		if (emailValido) {
			if (solicitud.getPuestosDTO() == null || solicitud.getPuestosDTO().size() == 0) {
				filtrosConsulta.showMsg("Se debe agregar por lo menos un grupo a la definición de la solicitud");
			} else {
				boolean puestoNuevo = true;
				for (PuestoDTO puestoDTO : solicitud.getPuestosDTO()) {
					if (!puestoDTO.isNuevoReg()) {
						puestoNuevo = false;
						break;
					}
				}
				if (puestoNuevo) {
					boolean moduloNuevo = true;
					for (ModuloDTO m : solicitud.getModulosDTO()) {
						if (!m.isNuevoReg()) {
							moduloNuevo = false;
							break;
						}
					}
					if (moduloNuevo) {
						try {
							solicitud.setDelDTO(usuario.getAprobadorSession().getSolicitud().getDelDTO());
							solicitud.setSubdelDTO(usuario.getAprobadorSession().getSolicitud().getSubdelDTO());
							
							UmfDTO umfDTO = new UmfDTO();
							umfDTO.setCveUmf(recuperaSolicitudOtraAdscripcionMB.getCveUmfOtraAdscripcion());
							solicitud.setUmfDTO(umfDTO);

							DepartamentoDTO depto = new DepartamentoDTO();
							depto.setCveSsodepto(recuperaSolicitudOtraAdscripcionMB.getCveDeptoOtraAdscripcion());
							solicitud.setDptoDTO(depto);

							PuestoDTO puesto = new PuestoDTO();
							puesto.setCvePuesto(recuperaSolicitudOtraAdscripcionMB.getCvePuestoOtraAdscripcion());
							solicitud.setPuestoDTO(puesto);

							solicitud.setDesTelefonoOfi(telefonosol);
							solicitud.setRefCorreoElectronico(correo + "@imss.gob.mx");
							solicitud.setCveAprobador(usuario.getAprobadorSession().getCveIdAprobador());

							boolean regenera = admonUsuariosService.regeneraPassword(solicitud.getDesUsrCurp(),PasswordUtil.getPassword(PasswordUtil.NUMEROS + PasswordUtil.MINUSCULAS+ PasswordUtil.MAYUSCULAS + PasswordUtil.ESPECIALES, 8));

							if (regenera) {
								solicitudCriteria.reactivaSolicitud(solicitud);

								bitacoraService.guardaSolicitudBit(solicitud.getCveSsosolicitud(),usuario.getAprobadorSession().getCveIdAprobador(),Constantes.TIPO_MOV_RECUPERACION);

								solicitud.setAprobador(usuario.getAprobadorSession());

								limpia();

								filtrosConsulta.showMsg("Se reactivo la cuenta satisfactoriamente,Esta cuenta quedará inactiva hasta su autorización");
								success = true;
							} else {
								filtrosConsulta.showMsg("No se reactivo la cuenta correctamente,Error al regenerar la contraseña.");
							}
							
						} catch (AdmonUsuariosException e) {
							log.error("Error al reacitvar la solicitud de :" + solicitud.getNombreCompleto());
							filtrosConsulta.showMsg("Error al reacitvar la solicitud");
							e.printStackTrace();
						}
					} else {
						filtrosConsulta.showMsg("Borre todos los módulos de la solicitud y vuelva a agregar nuevos antes de continuar con la reactivación");
					}
				} else {
					filtrosConsulta.showMsg("Borre todos los grupos de la solicitud y vuelva a agregar nuevos antes de continuar con la reactivación");
				}
			}
		}
	}
	
	private List<PuestoDTO> agregaPuestoDefault(List<PuestoDTO> puestosDTO) throws AdmonUsuariosException 
	{
		List result = new ArrayList<PuestoDTO>();
		boolean agrega=true;
		int anor = (int)filtrosConsulta.getClaveAreaNormativa();
		int depto = (int)filtrosConsulta.getClaveDepartamento();
		int puesto = (int)filtrosConsulta.getClavePuesto();
		
		try {
			if(puestosDTO !=null && puestosDTO.size()>0)
			{
				for(PuestoDTO p : puestosDTO)
				{
					if(p.getCvePuesto()==puesto)
					{
						p.setDefaultRol("Si");
						result.add(p);
						agrega = false;
					}
					else
					{
						result.add(p);
					}
				}
			}
			if(agrega)
			{
				result = DataTableData.addRoletoTableOne((ArrayList) result,anor, solicitudMB.buscaAreaNormativa(anor),depto, solicitudMB.buscaDepartamento(anor,depto),
						puesto, solicitudMB.buscaPuesto(depto,puesto), "Si");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean validaPuestosModulosMismoDepto(SolicitudDTO sol,int depto) {

		for(PuestoDTO p : sol.getPuestosDTO())
		{
			if(p.getCveDepartamento().intValue()!=depto)
			{
				return false;
			}
		}
		for(ModuloDTO m : sol.getModulosDTO())
		{
			if((int)m.getDptoDTO().getCveSsodepto()!=depto)
			{
				return false;
			}
		}

		return true;
	}

	public void activarUsuarioLdap(SolicitudDTO sol)
	{
		try{
	     this.admonUsuariosService.activarUsuario(sol.getDesUsrCurp());
	     
	     String mods = "";
	     
		 String titulo ="Reactivación de Cuenta";
		 String mensaje = "Su cuenta con el id de Usuario: " + sol.getDesUsrCurp() + " se ha reactivado ";
		 
		}catch(Exception e){
			log.error("  ", e);
		}		
	}

	
	public void modificaModulosLdap(SolicitudDTO sol)
	{
		try{
	     
	     String mods = "";
	     
	    for(ModuloDTO mod : sol.getModulosDTO())
		{
	    	mods = mods+mod.getDesModulo()+",";
		}
	    if(mods!=null&&mods.trim().length()>0)
	    	mods = mods.substring(0, mods.length()-1);
	    else
	    	mods = "";
	     this.admonUsuariosService.modificaModuloAprobador(sol.getDesUsrCurp(),mods);
	     
		 String titulo ="Reactivación de Cuenta";
		 String mensaje = "Su cuenta con el id de Usuario: " + sol.getDesUsrCurp() + " se ha reactivado ";
		 
		}catch(Exception e){
			log.error("  ", e);
		}		
	}

	public void modificaPerfilesLdap(SolicitudDTO sol)
	{
		try{
	     
	     
	     String pers = "";
	     
	    List<PerfilDTO> perfilesOriginales = solicitudCriteria.perfilesBySol(sol.getCveSsosolicitud());
	    if(perfilesOriginales!=null && perfilesOriginales.size()>0)
	    {
	    	for(PerfilDTO p : perfilesOriginales)
	    	{
	    		admonRoles.revocaRolUsuario(sol.getDesUsrCurp(), p.getPuestoDTO().getNombrePuesto());
	    	}
	    }
	     
	    for(PuestoDTO mod : sol.getPuestosDTO())
		{
	    	admonRoles.asignaRolUsuario(sol.getDesUsrCurp(),mod.getNombrePuesto());
	    	pers = pers+mod.getNombrePuesto()+",";
		}
	    pers = pers.substring(0, pers.length()-1);
	     
	    this.admonUsuariosService.modificaPerfilAprobador(sol.getDesUsrCurp(),pers);
	    
		 String titulo ="Reactivación de Cuenta";
		 String mensaje = "Su cuenta con el id de Usuario: " + sol.getDesUsrCurp() + " se ha reactivado ";
		 
		}catch(Exception e){
			log.error("  ", e);
		}		
	}

	public void limpia()
	{
		solicitudMB.setSolicitudDTO(new SolicitudDTO());
		success = false;
		curpConsulta = "";
		modificacion = true;
		telefonosol = "";
		correo = "";
		curpConsulta = "";
		solicitud = new SolicitudDTO();
		msgDesc = "";
		msg = false;
	}

	public void validaryCargarDatos(ActionEvent event)
	{
		List<SolicitudDTO> solicitudes = null;
		try {
			long ESTATUS_SOLICITUD_BAJA =4;
			long ESTATUS_SOLICITUD_RECHAZADAS =3;
			msgDesc = "";
			msg = false;
			SolicitudDTO filtro = new SolicitudDTO();
			filtro.setDesUsrCurp(curpConsulta.toUpperCase());
			// Buscar las solicitudes
			solicitudes = solicitudCriteria.searchSolCurp(filtro, ESTATUS_SOLICITUD_BAJA);
			List<SolicitudDTO> solsrech = solicitudCriteria.searchSolCurp(filtro, ESTATUS_SOLICITUD_RECHAZADAS);
			if(solsrech!=null&&solsrech.size()>0)
			{
				for(SolicitudDTO sol : solsrech )
				{
					if(solicitudes==null)
						solicitudes = new ArrayList<SolicitudDTO>();
					solicitudes.add(sol);
				}
			}
			if(solicitudes!=null&&solicitudes.size()>0)
			{
				
				solicitud = (SolicitudDTO)solicitudes.get(0);
				solicitud.setPuestosDTO(new ArrayList<PuestoDTO>());
				solicitud.setModulosDTO(new ArrayList<ModuloDTO>());
				solicitudMB.setSolicitudDTO(solicitud);
				solicitudMB.setIdSolicitud((int)solicitud.getCveSsosolicitud());
				telefonosol = solicitud.getDesTelefonoOfi();
				correo = solicitud.getRefCorreoElectronico().replace("@imss.gob.mx", "");

				try {
					ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
					Fisica fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo(solicitud.getDesUsrCurp());

					solicitud.setNomNombre(fisica.getNombre());
					solicitud.setNomPaterno(fisica.getPrimerApellido());
					solicitud.setNomMaterno(fisica.getSegundoApellido());
				}

				catch (ClienteWebserviceRenapoCurpException wsr) {
					log.error("Error::cargaDatosUsuario "+ wsr.getMessage());
					log.error("  ", wsr);
				}

				catch (Exception e) {
					log.error("Error::cargaDatosUsuario "+ e.getMessage());
					log.error("  ", e);
				}

				try {
					ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
					UsuarioNominaResponse usImss = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(solicitud.getDesUsrCurp(),"");
					if(usImss !=null)
					{
						solicitudMB.setMatricula(usImss.getMatricula());
						solicitudMB.setNssNom(usImss.getNss());
						solicitudMB.setPuestoDescNom(usImss.getPuestoDesc());
						solicitudMB.setDepartamentoDescNom(usImss.getDepartamentoDesc());
						solicitudMB.setCveDelegacionNom(usImss.getDelegacionCve());
						solicitudMB.setCveSubdelegacionNom("");
						solicitudMB.setCveUmfNom("");
						if(usImss.getEstatus()!=null)
							solicitudMB.setCveEstatusNom(new Long(usImss.getEstatus()).longValue());
						else
							solicitudMB.setCveEstatusNom(0);
						if(usImss.getEstatus()!=null&&usImss.getEstatus().equals("2"))
						{
							filtrosConsulta.showMsg("No se puede recuperar la cuenta de usuario debido a que este CURP no se encuentra activo en la nomina");
							success = true;
						}
						else
						{
							success = false;
						}
					}
				} catch (Exception e) {
					log.error("  ", e);
				}
				modificacion = false;
				success = false;
			}
			else
			{
				limpia();
				success = true;
				filtrosConsulta.showMsg("La cuenta consultada no se ha encontrado o no cuenta con el estatus necesario");
			}
		} catch (AdmonUsuariosException e) {
			e.printStackTrace();
		}
		 
	}
	
	public String validaryCargarDatosS()
	{
		List<SolicitudDTO> solicitudes = null;
		try {
			long ESTATUS_SOLICITUD_BAJA =4;
			long ESTATUS_SOLICITUD_RECHAZADAS =3;
			msgDesc = "";
			msg = false;
			SolicitudDTO filtro = new SolicitudDTO();
			filtro.setDesUsrCurp(curpConsulta.toUpperCase());
			// Buscar las solicitudes
			solicitudes = solicitudCriteria.searchSolCurp(filtro, ESTATUS_SOLICITUD_BAJA);
			List<SolicitudDTO> solsrech = solicitudCriteria.searchSolCurp(filtro, ESTATUS_SOLICITUD_RECHAZADAS);
			if(solsrech!=null&&solsrech.size()>0)
			{
				for(SolicitudDTO sol : solsrech )
				{
					if(solicitudes==null)
						solicitudes = new ArrayList<SolicitudDTO>();
					solicitudes.add(sol);
				}
			}
			if(solicitudes!=null&&solicitudes.size()>0)
			{
				
				solicitud = (SolicitudDTO)solicitudes.get(0);
				solicitud.setPuestosDTO(new ArrayList<PuestoDTO>());
				solicitud.setModulosDTO(new ArrayList<ModuloDTO>());
				solicitudMB.setSolicitudDTO(solicitud);
				solicitudMB.setIdSolicitud((int)solicitud.getCveSsosolicitud());
				telefonosol = solicitud.getDesTelefonoOfi();
				correo = solicitud.getRefCorreoElectronico().replaceAll("@imss.gob.mx","");

				try {
					ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
					Fisica fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo(solicitud.getDesUsrCurp());

					solicitud.setNomNombre(fisica.getNombre());
					solicitud.setNomPaterno(fisica.getPrimerApellido());
					solicitud.setNomMaterno(fisica.getSegundoApellido());
				}

				catch (ClienteWebserviceRenapoCurpException wsr) {
					log.error("Error::cargaDatosUsuario "+ wsr.getMessage());
					log.error("  ", wsr);
				}

				catch (Exception e) {
					log.error("Error::cargaDatosUsuario "+ e.getMessage());
					log.error("  ", e);
				}

				try {
					ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
					UsuarioNominaResponse usImss = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(solicitud.getDesUsrCurp(),"");
					if(usImss !=null)
					{
						solicitudMB.setMatricula(usImss.getMatricula());
						solicitudMB.setNssNom(usImss.getNss());
						solicitudMB.setPuestoDescNom(usImss.getPuestoDesc());
						solicitudMB.setDepartamentoDescNom(usImss.getDepartamentoDesc());
						solicitudMB.setCveDelegacionNom(usImss.getDelegacionCve());
						solicitudMB.setCveSubdelegacionNom("");
						solicitudMB.setCveUmfNom("");
						if(usImss.getEstatus()!=null)
							solicitudMB.setCveEstatusNom(new Long(usImss.getEstatus()).longValue());
						else
							solicitudMB.setCveEstatusNom(0);
						if(usImss.getEstatus()!=null&&usImss.getEstatus().equals("2"))
						{
							filtrosConsulta.showMsg("No se puede recuperar la cuenta de usuario debido a que este CURP no se encuentra activo en la nomina");
							success = true;
						}
						else
						{
							success = false;
						}
					}
				} catch (Exception e) {
					log.error("  ", e);
				}
				modificacion = false;
			}
			else
			{
				limpia();
				success = true;
				filtrosConsulta.showMsg("La cuenta consultada no se ha encontrado o no cuenta con el estatus necesario");
			}
		} catch (AdmonUsuariosException aue) {
			log.error("  ", aue);
		}
		return "recuperaCuentas";
	}

	
	public void validaryCargarDatosS2()
	{
		List<SolicitudDTO> solicitudes = null;
		try {
			long ESTATUS_SOLICITUD_BAJA =4;
			long ESTATUS_SOLICITUD_RECHAZADAS =3;
			long ESTATUS_CERO = 0L;
			int EMPKEYPRO_DIEZ = 10;
			int ESTATUS_UNO = 1;
			msgDesc = "";
			msg = false;
			SolicitudDTO filtro = new SolicitudDTO();
			filtro.setDesUsrCurp(curpConsulta.toUpperCase());
			// Buscar las solicitudes
			solicitudes = solicitudCriteria.searchSolCurp(filtro, ESTATUS_SOLICITUD_BAJA);
			List<SolicitudDTO> solsrech = solicitudCriteria.searchSolCurp(filtro, ESTATUS_SOLICITUD_RECHAZADAS);
			if(solsrech!=null&&solsrech.size()>0)
			{
				for(SolicitudDTO sol : solsrech )
				{
					if(solicitudes==null)
						solicitudes = new ArrayList<SolicitudDTO>();
					solicitudes.add(sol);
				}
			}
			
			if(solicitudes!=null&&solicitudes.size()>0)
			{
				log.info("<-----> Solicitudes size -> " + solicitudes.size() + "<----->");
				
				solicitud = (SolicitudDTO)solicitudes.get(0);
				solicitud.setPuestosDTO(new ArrayList<PuestoDTO>());
				solicitud.setModulosDTO(new ArrayList<ModuloDTO>());
				solicitudMB.setSolicitudDTO(solicitud);
				solicitudMB.setIdSolicitud((int)solicitud.getCveSsosolicitud());
				telefonosol = solicitud.getDesTelefonoOfi();
				correo = solicitud.getRefCorreoElectronico().replaceAll("@imss.gob.mx","");

				try {
					ClienteWebserviceCurp cliente = new ClienteWebserviceCurp();
					Fisica fisica = cliente.buscarPersonaFisicaPorCurpEnRenapo(solicitud.getDesUsrCurp());

					solicitud.setNomNombre(fisica.getNombre());
					solicitud.setNomPaterno(fisica.getPrimerApellido());
					solicitud.setNomMaterno(fisica.getSegundoApellido());
				}

				catch (ClienteWebserviceRenapoCurpException wsr) {
					log.error("Error::cargaDatosUsuario "+ wsr.getMessage());
					log.error("  ", wsr);
					log.error("Entre aqui por wsr");
				}

				catch (Exception e) {
					log.error("Error::cargaDatosUsuario "+ e.getMessage());
					log.error("  ", e);
					log.error("Entre aqui por exc");
				}

				try {
					ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
					log.info("***** Voy a ir a validar la CURP [ " + solicitud.getDesUsrCurp() + " ] en SIAP *****");
					UsuarioNominaResponse usImss = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(solicitud.getDesUsrCurp(),"");
					if(usImss != null)
					{
						System.out.println("*** El usuario esta en el SIAP ***");
						solicitudMB.setMatricula(usImss.getMatricula());
						solicitudMB.setNssNom(usImss.getNss());
						solicitudMB.setPuestoDescNom(usImss.getPuestoDesc());
						solicitudMB.setDepartamentoDescNom(usImss.getDepartamentoDesc());
						solicitudMB.setCveDelegacionNom(usImss.getDelegacionCve());
						solicitudMB.setCveSubdelegacionNom("");
						solicitudMB.setCveUmfNom("");
						if(usImss.getEstatus()!=null)
							solicitudMB.setCveEstatusNom(new Long(usImss.getEstatus()).longValue());
						else
							solicitudMB.setCveEstatusNom(ESTATUS_CERO);
						
						System.out.println("*** EMP KEY PRO [ " + usImss.getTipoContratacion() + " ] ***");
						
						int empKeyPro = Integer.parseInt(usImss.getTipoContratacion());
						int estatus = Integer.parseInt(usImss.getEstatus());
						
						System.out.println("*** Voy a validar la EMP_KEYPRO [ " + empKeyPro + " ] y el ESTATUS [ " + estatus + " ] en recuperación ***");
						
						if(!(empKeyPro < EMPKEYPRO_DIEZ) || !(estatus == ESTATUS_UNO)) {
							filtrosConsulta.showMsg("No se puede activar un usuario jubilado o pensionado y debe estar activo en SIAP");
							success = true;
							fgUmfOtraAdscripcion = true;
						}else if(usImss.getEstatus()!=null&&usImss.getEstatus().equals("2"))
						{
							filtrosConsulta.showMsg("No se puede recuperar la cuenta de usuario debido a que este CURP no se encuentra activo en la nomina");
							success = true;
							fgUmfOtraAdscripcion = true;
						}
						else
						{
							filtrosConsulta.showMsg("La cuenta de usuario se válido correctamente");
							success = false;
							fgUmfOtraAdscripcion = false;
						}
					}else {
						ClienteConsultaCurpTTDS ccct = new ClienteConsultaCurpTTDS();
						log.info("***** Voy a ir a validar la CURP [ " + solicitud.getDesUsrCurp() + " ] en TTD's *****");
						UsuarioTTD ut = ccct.invocarServicioConsultaCurpTTDS(solicitud.getDesUsrCurp(), "");
						if(ut != null) {
							System.out.println("*** El usuario esta en TTD's ***");
							solicitudMB.setMatricula(ut.getMatricula());
							solicitudMB.setNssNom(ut.getNss());
							solicitudMB.setPuestoDescNom(ut.getPuestoDesc());
							solicitudMB.setDepartamentoDescNom(ut.getDepartamentoDesc());
							solicitudMB.setCveDelegacionNom(ut.getDelegacionCve());
							solicitudMB.setCveSubdelegacionNom("");
							solicitudMB.setCveUmfNom("");
							if(!ut.getEstatus().equals(null)) {
								solicitudMB.setCveEstatusNom(new Long(ut.getEstatus()).longValue());
							}else {
								solicitudMB.setCveEstatusNom(ESTATUS_CERO);
							}
							
							log.info("*** EMP KEY PRO [ " + ut.getTipoContratacion() + " ] ***");
							
							int empKeyProTTD = Integer.parseInt(ut.getTipoContratacion());
							int estatusTTD = Integer.parseInt(ut.getEstatus());
							
							log.info("*** Voy a validar la EMP_KEYPRO [ " + empKeyProTTD + " ] y el ESTATUS [ " + estatusTTD + " ] en recuperación ***");
							
							if(!(empKeyProTTD < EMPKEYPRO_DIEZ) || !(estatusTTD == ESTATUS_UNO)) {
								filtrosConsulta.showMsg("No se puede activar un usuario jubilado o pensionado y debe estar activo en SIAP");
								success = true;
								fgUmfOtraAdscripcion = true;
							}else if(ut.getEstatus() != null && ut.getEstatus().equals("2")) {
								filtrosConsulta.showMsg("No se puede recuperar la cuenta de usuario debido a que este CURP no se encuentra activo en la nomina");
								success = true;
								fgUmfOtraAdscripcion = true;
							}else {
								filtrosConsulta.showMsg("La cuenta de usuario se válido correctamente");
								success = false;
								fgUmfOtraAdscripcion = false;
							}
						}else {
							/*filtrosConsulta.showMsg("La cuenta de usuario se válido correctamente 3.1");
							success = false;
							fgUmfOtraAdscripcion = false;*/
							log.info("*** El usuario no esta en SIAP ni TTD's ***");
							filtrosConsulta.showMsg("No se puede recuperar la cuenta de usuario debido a que este CURP no se encuentra activo en la nomina");
							success = true;
							fgUmfOtraAdscripcion = true;
						}
					}
				}catch (Exception exc) {
					log.error("Erorr al obtener información en SIAP y TTD's");
					log.error("*** Inicio del error ***");
					log.error(exc.getCause());
					log.error(exc.getMessage());
					log.error(exc.getStackTrace());
					log.error("*** Fin del error ***");
				}
				modificacion = false;
			}else {
				limpia();
				success = true;
				filtrosConsulta.showMsg("La cuenta consultada no se ha encontrado o no cuenta con el estatus necesario");
			}
		} catch (AdmonUsuariosException exc) {
			log.error("Erorr al obtener información en SIAP y TTD's");
			log.error("*** Inicio del error ***");
			log.error(exc.getCause());
			log.error(exc.getMessage());
			log.error(exc.getStackTrace());
			log.error("*** Fin del error ***");
		} catch (Exception exc) {
			log.error("Error en la información del usuario en la BD");
			log.error("*** Inicio error ***");
			log.error(exc.getCause());
			log.error(exc.getMessage());
			log.error(exc.getStackTrace());
			log.error("*** Fin error ***");
			if(correo.equals(null) || correo.equals("")) {
				filtrosConsulta.showMsg("Información inconsistente del usuario en la base de datos. Favor de validar el correo electrónico.");
			}else {
				filtrosConsulta.showMsg("Información inconsistente del usuario en la base de datos. Favor de validar.");
			}
			success = true;
			fgUmfOtraAdscripcion = true;
		}
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

	public String getSolicitudRecuperar() {
		return solicitudRecuperar;
	}

	public void setSolicitudRecuperar(String solicitudRecuperar) {
		this.solicitudRecuperar = solicitudRecuperar;
	}

	public long getIdSolicitudRecuperar() {
		return idSolicitudRecuperar;
	}

	public void setIdSolicitudRecuperar(long idSolicitudRecuperar) {
		this.idSolicitudRecuperar = idSolicitudRecuperar;
	}

	public boolean isFlagFirstSlide() {
		return flagFirstSlide;
	}

	public void setFlagFirstSlide(boolean flagFirstSlide) {
		this.flagFirstSlide = flagFirstSlide;
	}

	public SolicitudDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}

	public String getCurpConsulta() {
		return curpConsulta;
	}

	public void setCurpConsulta(String curpConsulta) {
		this.curpConsulta = curpConsulta;
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

	public AdmonUsuariosSessionLocal getAdmonUsuariosService() {
		return admonUsuariosService;
	}

	public void setAdmonUsuariosService(
			AdmonUsuariosSessionLocal admonUsuariosService) {
		this.admonUsuariosService = admonUsuariosService;
	}

	public MensajeriaSessionLocal getMensajeriaService() {
		return mensajeriaService;
	}

	public void setMensajeriaService(MensajeriaSessionLocal mensajeriaService) {
		this.mensajeriaService = mensajeriaService;
	}

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		RecuperaSolicitudMB.log = log;
	}
	
	public void agregaaTablaUnoPrevio() 
	{
		agregaaTablaUno("No");
	}
	
	public void agregaaTablaUnoPrevioOtraAdscripcion() {
		if (solicitud.getPuestosDTO().isEmpty() && solicitud.getPuestosDTO() != null && solicitud.getPuestosDTO().size() < 1) {
			agregaaTablaUno("Si");
		} else {
			agregaaTablaUno("No");
		}
		
	}

	public void agregaaTablaUno(String defaultRol) 
	{
		int anor = (int)filtrosConsulta.getClaveAreaNormativa();
		int depto = (int)filtrosConsulta.getClaveDepartamentoRolAdd();

		
		if (clavePuesto == -99) {
			filtrosConsulta.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
			return;
		}

		boolean res = DataTableData.validaPuestoExistente((ArrayList<PuestoDTO>)solicitud.getPuestosDTO(), anor,depto, clavePuesto);
		try {
			if (res == false) {
				solicitud.setPuestosDTO(DataTableData.addRoletoTableOne(
						(ArrayList) solicitud.getPuestosDTO(),anor, solicitudMB
								.buscaAreaNormativa(anor),depto, solicitudMB.buscaDepartamento(anor,depto),
						clavePuesto, solicitudMB.buscaPuesto(depto,clavePuesto), defaultRol));
			}
			else
			{
				filtrosConsulta.showMsg("El perfil seleccionado ya se encuentra relacionado a la cuenta del usuario");
			}
		} catch (Exception e) {
			System.out.println("agregaaTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void eliminaDeTablaUno() {
		int anor = (int)filtrosConsulta.getClaveAreaNormativa();
		int depto = (int)filtrosConsulta.getClaveDepartamentoRolAdd();
		try {
//			boolean isDefault = DataTableData.consultaModuloDefault(
//					(ArrayList) catalogoMB.cargaPuestoCat(new Long(depto)),anor, depto,
//					clavePuestoDelete);
//	
//			if (isDefault == true) {
//				errorRoles = "NO SE PUEDE ELIMINAR EL PERFIL POR DEFAULT";
//				errorAviso = "AVISOS: ";
//				return;
//			}
			solicitudMB.setRolesData(DataTableData.removeRoleFromTableOne(clavePuestoDelete, (ArrayList) solicitudMB.getRolesData()));
			filtrosConsulta.showMsg("El grupo seleccionado se elimino correctamente de la cuenta del usuario");
		
		} catch (Exception e) {
			System.out.println("eliminaDeTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}

	public void eliminaDeTablaUnoOtraAdscripcion() {
		try {
			List<PuestoDTO> listPuestoDto = DataTableData.removeRoleFromTableOne(clavePuestoDelete, (ArrayList) solicitudMB.getRolesData());
			
			if (!listPuestoDto.isEmpty() && listPuestoDto != null && listPuestoDto.size() == 1) {
				listPuestoDto.get(0).setDefaultRol("Si");
			}
			
			solicitudMB.setRolesData(listPuestoDto);
			filtrosConsulta.showMsg("El grupo seleccionado se elimino correctamente de la cuenta del usuario");
		
		} catch (Exception e) {
			System.out.println("eliminaDeTablaUno");
			e.printStackTrace();
			log.error("  ", e);
		}
	}
	
	public void agregaaTablaDos() {
		int anor = (int)filtrosConsulta.getClaveAreaNormativa();
		int depto = (int)filtrosConsulta.getClaveDepartamentoModAdd();

		try {
			if (claveModulo == -99) {
				filtrosConsulta.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
				return;
			}

			// Validar si existe en el registro actual
			boolean res = DataTableData.validaModuloExistente((ArrayList) solicitud.getModulosDTO(),anor, depto, claveModulo);
			if (res == false) {
				solicitud.setModulosDTO(DataTableData.addRoletoTableTwoN(
						(ArrayList<ModuloDTO>) solicitud.getModulosDTO(),
						solicitudMB.buscaAreaNormativaObj(anor),
						solicitudMB.buscaDepartamentoObj(anor,
								depto), claveModulo, solicitudMB
								.buscaModulo(depto, claveModulo)));
			}
			else
			{
				filtrosConsulta.showMsg("El módulo seleccionado ya se encuentra relacionado a la cuenta del usuario");
			}

		} catch (Exception e) {
			System.out.println("agregaaTablaDos ");
			e.printStackTrace();
			log.error("  ", e);
		}
	}
	
	public void agregaaTablaDosOtraAdscripcion() {
		int anor = (int)recuperaSolicitudOtraAdscripcionMB.getCveAreaNormativaOtraAdscripcion();
		int depto = (int)recuperaSolicitudOtraAdscripcionMB.getCveDeptoModAddOtraAdscripcion();

		try {
			if (claveModulo == -99) {
				filtrosConsulta.showMsg("DEBE SELECCIONAR TODOS LOS VALORES PARA AGREGAR PERFILES / MODULOS");
				return;
			}

			// Validar si existe en el registro actual
			boolean res = DataTableData.validaModuloExistente((ArrayList) solicitud.getModulosDTO(),anor, depto, claveModulo);
			if (res == false) {
				solicitud.setModulosDTO(DataTableData.addRoletoTableTwoN(
						(ArrayList<ModuloDTO>) solicitud.getModulosDTO(),
						solicitudMB.buscaAreaNormativaObj(anor),
						solicitudMB.buscaDepartamentoObj(anor,
								depto), claveModulo, solicitudMB
								.buscaModulo(depto, claveModulo)));
			}
			else
			{
				filtrosConsulta.showMsg("El módulo seleccionado ya se encuentra relacionado a la cuenta del usuario");
			}

		} catch (Exception e) {
			log.error("agregaaTablaDos---" + e.getMessage());
			log.error("  ", e);
		}
	}

	public void eliminaDeTablaDos() {
		
		try {
			solicitudMB.setModulosData(DataTableData.removeModuleFromTableTwo(claveModuloDelete, (ArrayList) solicitud.getModulosDTO()));
			filtrosConsulta.showMsg("El módulo seleccionado se elimino correctamente de la cuenta del usuario");
//			solicitudMB.eliminarModulosById(claveAccesoModuloDelete);
		} catch (Exception e) {
			log.error("eliminaDeTablaDos---" + e.getMessage());
			log.error("  ", e);
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
	public void handleSelectUsuarioFromTableOne(SelectEvent se)throws AdmonUsuariosException {
		PuestoDTO pue = (PuestoDTO)se.getObject();
		clavePuestoDelete = (int) pue.getCvePuesto();
	}

	// Es el manejador del grid dos
	public void handleSelectUsuarioFromTableTwo(SelectEvent se)throws AdmonUsuariosException 
	{
		ModuloDTO mod = (ModuloDTO)se.getObject();
		claveModuloDelete = (int) mod.getCveIdModulo();
		claveAccesoModuloDelete = (int) mod.getCveAccesoModulo();
	}

	public int getClavePuestoDelete() {
		return clavePuestoDelete;
	}

	public void setClavePuestoDelete(int clavePuestoDelete) {
		this.clavePuestoDelete = clavePuestoDelete;
	}

	public int getClaveModuloDelete() {
		return claveModuloDelete;
	}

	public void setClaveModuloDelete(int claveModuloDelete) {
		this.claveModuloDelete = claveModuloDelete;
	}

	public CatalogosMB getCatalogoMB() {
		return catalogoMB;
	}

	public void setCatalogoMB(CatalogosMB catalogoMB) {
		this.catalogoMB = catalogoMB;
	}

	public RecuperaSolicitudOtraAdscripcionMB getRecuperaSolicitudOtraAdscripcionMB() {
		return recuperaSolicitudOtraAdscripcionMB;
	}

	public void setRecuperaSolicitudOtraAdscripcionMB(RecuperaSolicitudOtraAdscripcionMB recuperaSolicitudOtraAdscripcionMB) {
		this.recuperaSolicitudOtraAdscripcionMB = recuperaSolicitudOtraAdscripcionMB;
	}

	public int getClaveAccesoModuloDelete() {
		return claveAccesoModuloDelete;
	}

	public void setClaveAccesoModuloDelete(int claveAccesoModuloDelete) {
		this.claveAccesoModuloDelete = claveAccesoModuloDelete;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFgUmfOtraAdscripcion() {
		return fgUmfOtraAdscripcion;
	}

	public void setFgUmfOtraAdscripcion(boolean fgUmfOtraAdscripcion) {
		this.fgUmfOtraAdscripcion = fgUmfOtraAdscripcion;
	}

	public boolean isModificacion() {
		return modificacion;
	}

	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}

	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefonosol() {
		return telefonosol;
	}

	public void setTelefonosol(String telefonosol) {
		this.telefonosol = telefonosol;
	}

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
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

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public boolean isMsg() {
		return msg;
	}

	public void setMsg(boolean msg) {
		this.msg = msg;
	}
	

}
