package mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion;



public class DescargaCedula {
	private String folioCorreccion;
	private Integer idFolioCorreccion;
	private Integer idArchivoDescarga;
	private String periodo;
	private String msg;
	
	
	 
	public DescargaCedula(){
		setMsg("");
	
	}
	public String getFolioCorreccion() {
		return folioCorreccion;
	}
	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}
	public Integer getIdFolioCorreccion() {
		return idFolioCorreccion;
	}
	public void setIdFolioCorreccion(Integer idFolioCorreccion) {
		this.idFolioCorreccion = idFolioCorreccion;
	}
	public Integer getIdArchivoDescarga() {
		return idArchivoDescarga;
	}
	public void setIdArchivoDescarga(Integer idArchivoDescarga) {
		this.idArchivoDescarga = idArchivoDescarga;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
