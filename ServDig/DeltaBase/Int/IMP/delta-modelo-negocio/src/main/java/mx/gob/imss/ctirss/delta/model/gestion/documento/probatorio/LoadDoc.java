package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

public class LoadDoc {
	//documento capturado digital y por formulario
	private DocumentoProbatorioCaptura documentoProbatorioCaptura=new DocumentoProbatorioCaptura();
	//descripcion deldocumento
	private TramiteDocumentacionModel tramiteDocumentacionModel;
	//documento especifico que pertenece
	private Documento documento;
	
	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public TramiteDocumentacionModel getTramiteDocumentacionModel() {
		return tramiteDocumentacionModel;
	}
	public void setTramiteDocumentacionModel(
			TramiteDocumentacionModel tramiteDocumentacion) {
		this.tramiteDocumentacionModel = tramiteDocumentacion;
	}
	public DocumentoProbatorioCaptura getDocumentoProbatorioCaptura() {
		return documentoProbatorioCaptura;
	}
	public void setDocumentoProbatorioCaptura(
			DocumentoProbatorioCaptura documentoProbatorioCaptura) {
		this.documentoProbatorioCaptura = documentoProbatorioCaptura;
	}



}
