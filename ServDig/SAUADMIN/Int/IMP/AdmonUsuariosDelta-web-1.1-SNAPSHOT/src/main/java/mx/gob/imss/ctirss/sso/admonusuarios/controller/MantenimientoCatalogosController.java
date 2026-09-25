package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.CatalogosMB;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@ManagedBean(name="mantenimientoCatalogos")
@CustomScoped("#{window}")

public class MantenimientoCatalogosController {
	
    @ManagedProperty(value="#{catalogoMB}")	 
	private CatalogosMB catalogoMB;
	
	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;
   
	
	/** Log de la clase */
    private static final Log log = LogFactory.getLog(MantenimientoCatalogosController.class);
    
	private List<SelectItem> lstAreaNormativa = new ArrayList<SelectItem>();
	private List<SelectItem> lstDepartamento = new ArrayList<SelectItem>();
	private List<SelectItem> lstPuesto = new ArrayList<SelectItem>();

	private List<SelectItem> lstDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSubDelegacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstUMF = new ArrayList<SelectItem>();
	
	private List<SelectItem> lstPerfiles = new ArrayList<SelectItem>();
	private List<SelectItem> lstModulo = new ArrayList<SelectItem>(); 
	
	private List<SolicitudDTO> resultados = new ArrayList<SolicitudDTO>();
	
	private List<SolicitudDTO> solicitudesByDepto = new ArrayList<SolicitudDTO>();
	
    private static final SelectItem[] POSITION_AVAILABLE = { new SelectItem("bottom", "Bottom"),
    	new SelectItem("top", "Top"),
        new SelectItem("both", "Both") };

   	private boolean flag = true;
   	private String position = POSITION_AVAILABLE[0].getValue().toString();
   	private int rows = 10;
   	private int startPage = 1;
   	
   	private String curp;
   	private String matricula;
   	
   	
   	

	@PostConstruct
	public void init() throws AdmonUsuariosException{		
		filtrosConsulta.obtieneDatosUsuario();
	}
	
	

	

	public String inicializaConsultaPuestos()
	{
		filtrosConsulta.obtieneDatosUsuario();
		return "catalogoPuestos";
	}

	public void obtenerSolByDepto(ActionEvent event){
//		solicitudCriteria.solicitudesByDepto();
	}
	
	public void consultaAprobadores(ActionEvent event){
		resultados = new ArrayList<SolicitudDTO>();
	}
	
	
	//--------------------- seccion gets
	





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


	public List<SelectItem> getLstDelegacion() {
		return lstDelegacion;
	}


	public void setLstDelegacion(List<SelectItem> lstDelegacion) {
		this.lstDelegacion = lstDelegacion;
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


	public List<SelectItem> getLstPerfiles() {
		return lstPerfiles;
	}


	public void setLstPerfiles(List<SelectItem> lstPerfiles) {
		this.lstPerfiles = lstPerfiles;
	}


	public List<SelectItem> getLstModulo() {
		return lstModulo;
	}


	public void setLstModulo(List<SelectItem> lstModulo) {
		this.lstModulo = lstModulo;
	}


	public List<SolicitudDTO> getResultados() {
		return resultados;
	}


	public void setResultados(List<SolicitudDTO> resultados) {
		this.resultados = resultados;
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


	public List<SolicitudDTO> getSolicitudesByDepto() {
		return solicitudesByDepto;
	}


	public void setSolicitudesByDepto(List<SolicitudDTO> solicitudesByDepto) {
		this.solicitudesByDepto = solicitudesByDepto;
	}


	public CatalogosMB getCatalogoMB() {
		return catalogoMB;
	}


	public void setCatalogoMB(CatalogosMB catalogoMB) {
		this.catalogoMB = catalogoMB;
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





	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}





	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}

	


}
