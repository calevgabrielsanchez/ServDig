package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import org.icefaces.ace.model.table.RowStateMap;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.event.UnselectEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.ace.model.table.RowStateMap;

@ManagedBean(name="mantenimientoCatModulosController")
@CustomScoped("#{window}")
public class MantenimientoCatModulosController {
	
    @ManagedProperty(value="#{catalogoMB}")	 
	private CatalogosMB catalogoMB;  
    
    @ManagedProperty("#{solicituddMB}")	 
	private SolicituddMB solicitudMB;   
	
    @ManagedProperty(value="#{usuarioMB}")	 
	private UsuarioMB usuario;

	/** Log de la clase */
    private static final Log log = LogFactory.getLog(MantenimientoCatModulosController.class);
    
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();

	private List<SelectItem> lstDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMF = new ArrayList<SelectItem>();
	
	private List<ModuloDTO> modulosTodos;
	private List<ModuloDTO> modulosPertenecientes;
	
	private RowStateMap stateMapFromModulosTodos = new RowStateMap();
	private RowStateMap stateMapFromModulosPertenecientes = new RowStateMap();
	
    private static final SelectItem[] POSITION_AVAILABLE = { new SelectItem("bottom", "Bottom"),
    	new SelectItem("top", "Top"),
        new SelectItem("both", "Both") };

   	private String position = POSITION_AVAILABLE[0].getValue().toString();
   	
   	private long claveAreaNormativa = 1;
   	private long claveDepartamento = 1;
   	private long clavePuesto = 1;
   	private long claveDelegacion = 1;
   	private long claveSubdelegacion = 1;
   	private long claveUMF = 1;
	
	private int claveModuloAgregar;
	private String desModuloAgregar;
	private int claveModuloEliminar;
	private String desModuloEliminar;
	
	private boolean flagInhabilitar = false;
   	 	
	@PostConstruct
	public void init() throws AdmonUsuariosException{
		modulosTodos = new ArrayList<ModuloDTO>();
		modulosPertenecientes = new ArrayList<ModuloDTO>();
		solicitudMB.setClaveAreaNormativa((int)usuario.getAreaNormativa());
		solicitudMB.setClaveDepartamento((int)usuario.getAprobadorSession().getSolicitud().getDepartamentoId());
		solicitudMB.setClavePuesto((int)usuario.getAprobadorSession().getSolicitud().getPuestoId());
		solicitudMB.setClaveDelegacion((int)usuario.getAprobadorSession().getSolicitud().getDelegacionId()); //Valores que deben de ser tomados de la sesion.
		solicitudMB.setCveDeleg((int)usuario.getAprobadorSession().getSolicitud().getDelegacionId());
		solicitudMB.setClaveSubDelegacion ((int)usuario.getAprobadorSession().getSolicitud().getSubdelegacionId());
		solicitudMB.setClaveUMF((int)usuario.getAprobadorSession().getSolicitud().getUmfId());
		cargaAreaNormativaNCat();
		cargaDepartamentoNCat();
		cargaPuestoNCat();
		cargaDelegacionnNCat();
		cargaSubDelegacionNCat();
		cargaUMFNCat();
		//Cargar los catalogos en los grids
		try{
		cargaModulosExistentes();
		}
		catch(Exception e1){
			System.out.println("Error");
			e1.printStackTrace();
		}
		try{
		cargaModulosRelacionados();
		}
		catch(Exception e){
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public String inicializaDatosMantenimieto()
	{
		flagInhabilitar = true;
		catalogoMB.setErrorAviso("");
		catalogoMB.setErrorGeneralDatos("");
		return "mantenimientoModulos";
	}
	
	public void cargaAreaNormativaNCat() throws AdmonUsuariosException{
		this.lstAreaNormativa.clear();
		List<AreaNormativaDTO> lista = solicitudMB.cargaAreaNormativaNCat();
		for(AreaNormativaDTO an : lista){
			this.lstAreaNormativa.add(new SelectItem(an.getCveSsoareanorma(), an.getDesAreanorma()));
		}
		solicitudMB.setLstAreaNormativa(this.lstAreaNormativa);
	}
	
	public void cargaDelegacionnNCat() throws AdmonUsuariosException{
		this.lstDelegacion.clear();
		List<DelegacionDTO> lista = solicitudMB.cargaDelegacionnNCat();
		for(DelegacionDTO dl : lista){
			this.lstDelegacion.add(new SelectItem(dl.getCveDelegacion(), dl.getNombreDelegacion()));
		}
		solicitudMB.setLstDelegacion(this.lstDelegacion);
	}
	
	public void cargaDepartamentoNCat() throws AdmonUsuariosException{
		this.lstDepartamento.clear();
		List<DepartamentoDTO> lista = solicitudMB.cargaDepartamentoNCat();
		for(DepartamentoDTO dep : lista){
			this.lstDepartamento.add(new SelectItem(dep.getCveSsodepto(), dep.getDesDepartamento()));
		}
		solicitudMB.setLstDepartamento(this.lstDepartamento);
	}
	
	public void cargaPuestoNCat() throws AdmonUsuariosException{
		this.lstPuesto.clear();
		List<PuestoDTO> lista = solicitudMB.cargaPuestoNCat();
		for(PuestoDTO pst : lista){
			this.lstPuesto.add(new SelectItem(pst.getCvePuesto(), pst.getNombrePuesto()));
		}
		solicitudMB.setLstPuesto(this.lstPuesto);
	}
	
	public void cargaDelegacionNCat() throws AdmonUsuariosException{
		this.lstDelegacion.clear();
		List<DelegacionDTO> lista = solicitudMB.cargaDelegacionNCat();
		for(DelegacionDTO del : lista){
			this.lstDelegacion.add(new SelectItem(del.getCveDelegacion(), del.getNombreDelegacion()));
		}
		solicitudMB.setLstDelegacion(this.lstDelegacion);
	}
	
	public void cargaSubDelegacionNCat() throws AdmonUsuariosException{
		this.lstSubDelegacion.clear();
		List<SubdelegacionDTO> lista = solicitudMB.cargaSubDelegacionNCat();
		for(SubdelegacionDTO sdel : lista){
			this.lstSubDelegacion.add(new SelectItem(sdel.getCveSubelegacion(), sdel.getNombreSubelegacion()));
		}
		solicitudMB.setLstSubDelegacion(this.lstSubDelegacion);
	}
	
	public void cargaUMFNCat() throws AdmonUsuariosException{
		this.lstUMF.clear();
		List<UmfDTO> lista = solicitudMB.cargaUmfNCat();
		for(UmfDTO umf : lista){
			this.lstUMF.add(new SelectItem(umf.getCveUmf(), umf.getNombreUmf()));
		}
		solicitudMB.setLstUMF(this.lstUMF);
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
	
	public List<SelectItem> getLstDelegacion() {
		return lstDelegacion;
	}

	public void setLstDelegacion(List<SelectItem> lstDelegacion) {
		this.lstDelegacion = lstDelegacion;
	}

	public SolicituddMB getSolicitudMB( )
	{
		return solicitudMB;
	}
	
	public void setSolicitudMB(SolicituddMB solicitudMB)
	{
		this.solicitudMB = solicitudMB;
	}
	
	public CatalogosMB getCatalogoMB( )
	{
		return catalogoMB;
	}
	
	public void setCatalogoMB(CatalogosMB catalogoMB)
	{
		this.catalogoMB = catalogoMB;
	}
	
	public List<ModuloDTO> getModulosTodos()
	{
		return modulosTodos;
	}
	
	public void setModulosTodos(List<ModuloDTO> modulosTodos)
	{
		this.modulosTodos = modulosTodos;
	}
	
	public List<ModuloDTO> getModulosPertenecientes()
	{
		return modulosPertenecientes;
	}
	
	public void setModulosPertenecientes(List<ModuloDTO> modulosPertenecientes)
	{
		this.modulosTodos = modulosPertenecientes;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ModuloDTO> getMultiRowFromModulosTodos() { 
		return (ArrayList<ModuloDTO>) stateMapFromModulosTodos.getSelected(); 
	}
	
	public void handleSelectUsuarioFromModulosTodos(SelectEvent se) throws AdmonUsuariosException{
		claveModuloAgregar = (int)this.getMultiRowFromModulosTodos().get(0).getCveIdModulo();
		desModuloAgregar = this.getMultiRowFromModulosTodos().get(0).getDesModulo();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ModuloDTO> getMultiRowFromModulosPertenecientes() { 
		return (ArrayList<ModuloDTO>) stateMapFromModulosPertenecientes.getSelected(); 
	}
	
	public void handleSelectUsuarioFromModulosPertenecientes(SelectEvent se) throws AdmonUsuariosException{
		claveModuloEliminar = (int)this.getMultiRowFromModulosPertenecientes().get(0).getCveIdModulo();
		desModuloEliminar = this.getMultiRowFromModulosPertenecientes().get(0).getDesModulo();
	}
	
	public RowStateMap getStateMapFromModulosTodos() {
		return stateMapFromModulosTodos;
	}

	public void setStateMapFromModulosTodos(RowStateMap stateMapFromModulosTodos) {
		this.stateMapFromModulosTodos = stateMapFromModulosTodos;
	}
	
	public RowStateMap getStateMapFromModulosPertenecientes() {
		return stateMapFromModulosPertenecientes;
	}

	public void setStateMapFromModulosPertenecientes(RowStateMap stateMapFromModulosPertenecientes) {
		this.stateMapFromModulosPertenecientes = stateMapFromModulosPertenecientes;
	}
	
	public boolean getFlagInhabilitar()
	{
		return flagInhabilitar;
	}
	
	public void setFlagInhabilitar(boolean flagInhabilitar)
	{
		this.flagInhabilitar = flagInhabilitar;
	}
	
	public void cargaModulosExistentes() throws AdmonUsuariosException{
		this.modulosTodos.clear();
		modulosTodos = catalogoMB.cargaModulosExistentes();
		return;
	}
	public void cargaModulosRelacionados() throws AdmonUsuariosException{
		this.modulosPertenecientes.clear();
		modulosPertenecientes = catalogoMB.cargaModulosRelacionados(solicitudMB.getClaveDepartamento());
		return;
	}
	
	public String agregarModuloAlDep(){
		if(ModuloExistente()){
			catalogoMB.setErrorAviso("AVISO: ");
			catalogoMB.setErrorGeneralDatos("El módulo ya existe y no se puede agregar");
			return "mantenimientoModulos";
		}
		else{
			catalogoMB.setErrorAviso("");
			catalogoMB.setErrorGeneralDatos("");
		    catalogoMB.agregaModuloADep(solicitudMB.getClaveDepartamento(), claveModuloAgregar);
		    modulosPertenecientes.clear();
		    try{
		        modulosPertenecientes = catalogoMB.cargaModulosRelacionados(solicitudMB.getClaveDepartamento());
		      }
		    catch(Exception e){}
		  }
		return "mantenimientoModulos";
	}
	
	public String eliminarModuloDelDep()
	{
		if(ValidarDependencias()){
			catalogoMB.setErrorAviso("AVISO: ");
			catalogoMB.setErrorGeneralDatos("El módulo tiene dependencias y no se puede eliminar");
			return "mantenimientoModulos";
		}
		else{
			catalogoMB.setErrorAviso("");
			catalogoMB.setErrorGeneralDatos("");
		    catalogoMB.eliminaModuloADep(solicitudMB.getClaveDepartamento(), claveModuloEliminar);
		    modulosPertenecientes.clear();
		    try{
		        modulosPertenecientes = catalogoMB.cargaModulosRelacionados(solicitudMB.getClaveDepartamento());
		      }
		    catch(Exception e){}
		}
		return "mantenimientoModulos";
	}
	
	public boolean ModuloExistente( )
	{
		boolean res = false;
		for(ModuloDTO mod : modulosPertenecientes){
			if(mod.getCveIdModulo() == claveModuloAgregar){
				res = true;
				break;
			}
		}
		return res;
	}
	
	public boolean ValidarDependencias()
	{
		return catalogoMB.ValidarDependencias(solicitudMB.getClaveDepartamento(), claveModuloEliminar);
	}

	public UsuarioMB getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioMB usuario) {
		this.usuario = usuario;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public long getClaveAreaNormativa() {
		return claveAreaNormativa;
	}

	public void setClaveAreaNormativa(long claveAreaNormativa) {
		this.claveAreaNormativa = claveAreaNormativa;
	}

	public long getClaveDepartamento() {
		return claveDepartamento;
	}

	public void setClaveDepartamento(long claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}

	public long getClavePuesto() {
		return clavePuesto;
	}

	public void setClavePuesto(long clavePuesto) {
		this.clavePuesto = clavePuesto;
	}

	public long getClaveDelegacion() {
		return claveDelegacion;
	}

	public void setClaveDelegacion(long claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public long getClaveSubdelegacion() {
		return claveSubdelegacion;
	}

	public void setClaveSubdelegacion(long claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
	}

	public long getClaveUMF() {
		return claveUMF;
	}

	public void setClaveUMF(long claveUMF) {
		this.claveUMF = claveUMF;
	}

	public int getClaveModuloAgregar() {
		return claveModuloAgregar;
	}

	public void setClaveModuloAgregar(int claveModuloAgregar) {
		this.claveModuloAgregar = claveModuloAgregar;
	}

	public String getDesModuloAgregar() {
		return desModuloAgregar;
	}

	public void setDesModuloAgregar(String desModuloAgregar) {
		this.desModuloAgregar = desModuloAgregar;
	}

	public int getClaveModuloEliminar() {
		return claveModuloEliminar;
	}

	public void setClaveModuloEliminar(int claveModuloEliminar) {
		this.claveModuloEliminar = claveModuloEliminar;
	}

	public String getDesModuloEliminar() {
		return desModuloEliminar;
	}

	public void setDesModuloEliminar(String desModuloEliminar) {
		this.desModuloEliminar = desModuloEliminar;
	}
	
	
}
