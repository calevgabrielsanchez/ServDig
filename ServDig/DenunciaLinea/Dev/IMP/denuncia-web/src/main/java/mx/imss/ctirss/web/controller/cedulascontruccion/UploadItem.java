package mx.imss.ctirss.web.controller.cedulascontruccion;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem {
	
	private String folioCorreccion;
	private String idArchivoCarga;
	private CommonsMultipartFile fileData;
	
	
	
	public String getFolioCorreccion() {
		return folioCorreccion;
	}

	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}
	
	public String getIdArchivoCarga() {
		return idArchivoCarga;
	}

	public void setIdArchivoCarga(String idArchivoCarga) {
		this.idArchivoCarga = idArchivoCarga;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	
}
