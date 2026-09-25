package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileUploadVB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SES_NAME="fileUploadSVB";
	//lista de los tramites requeridos repetidos en tipo tramite
	private List<DoctoReqTramite> doctoReqTramiteList;
	

	//Lista de todos los doctosProbatorios del Tramite para mostrar
	private List<TipoDocumentoProbatorio> tipoDocumentoProbatorioList;

	//Lista de todos los doctosProbatorios del Tramite reuqeridos
	private List<TipoDocumentoProbatorio> tipoDocumentoProbatorioReqList;
	
	private String idTipoTramite;
	private String error;
	//se cargo el documento en archivo
	private boolean loadedBytes;
	//se cargo el documento en formulario
	private boolean loadedDocform;
	//documento capturado
	private Object captura;
	
	private DocumentoProbatorio documentoProbatorio;
	//la info del tipo tramite y documento seleccionado
	private DoctoReqTramite doctoReqTramite;
	
	//el di del tramite a guardar
	private Long idTramite;
	
	private Boolean necesarios;
	
	public Boolean getNecesarios() {
		return necesarios;
	}
	public void setNecesarios(Boolean necesarios) {
		this.necesarios = necesarios;
	}
	//la lista de documentos a guardar con su documentocaptura y tipoDocumentoProbatorio
	private List<DocumentoProbatorioCaptura> documenProbatorioCapturaList=new ArrayList<DocumentoProbatorioCaptura>(); 

	
	public Long getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}
	
	
	public List<DocumentoProbatorioCaptura> getDocumenProbatorioCapturaList() {
		return documenProbatorioCapturaList;
	}
	public void setDocumenProbatorioCapturaList(
			List<DocumentoProbatorioCaptura> documenProbatorioCapturaList) {
		this.documenProbatorioCapturaList = documenProbatorioCapturaList;
	}
	public boolean isLoadedBytes() {
		return loadedBytes;
	}
	public void setLoadedBytes(boolean loadedBytes) {
		this.loadedBytes = loadedBytes;
	}
	public boolean isLoadedDocform() {
		return loadedDocform;
	}
	public void setLoadedDocform(boolean loadedDocform) {
		this.loadedDocform = loadedDocform;
	}
	public Object getCaptura() {
		return captura;
	}
	public void setCaptura(Object captura) {
		this.captura = captura;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public String getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(String idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}
	public List<DoctoReqTramite> getDoctoReqTramiteList() {
		return doctoReqTramiteList;
	}
	public void setDoctoReqTramiteList(List<DoctoReqTramite> doctoReqTramiteList) {
		this.doctoReqTramiteList = doctoReqTramiteList;
	}

	public List<TipoDocumentoProbatorio> getTipoDocumentoProbatorioList() {
		return tipoDocumentoProbatorioList;
	}
	public void setTipoDocumentoProbatorioList(
			List<TipoDocumentoProbatorio> tipoDocumentoProbatorioList) {
		this.tipoDocumentoProbatorioList = tipoDocumentoProbatorioList;
	}
	public DocumentoProbatorio getDocumentoProbatorio() {
		return documentoProbatorio;
	}
	public void setDocumentoProbatorio(DocumentoProbatorio documentoProbatorio) {
		this.documentoProbatorio = documentoProbatorio;
	}
	public DoctoReqTramite getDoctoReqTramite() {
		return doctoReqTramite;
	}
	public void setDoctoReqTramite(DoctoReqTramite doctoReqTramite) {
		this.doctoReqTramite = doctoReqTramite;
	}
	public List<TipoDocumentoProbatorio> getTipoDocumentoProbatorioReqList() {
		return tipoDocumentoProbatorioReqList;
	}
	public void setTipoDocumentoProbatorioReqList(
			List<TipoDocumentoProbatorio> tipoDocumentoProbatorioReqList) {
		this.tipoDocumentoProbatorioReqList = tipoDocumentoProbatorioReqList;
	}




}
