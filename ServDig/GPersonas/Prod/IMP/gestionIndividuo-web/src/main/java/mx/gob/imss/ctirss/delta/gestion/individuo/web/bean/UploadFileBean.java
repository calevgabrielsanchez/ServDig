package mx.gob.imss.ctirss.delta.gestion.individuo.web.bean;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4219573910154958470L;
	
	protected CommonsMultipartFile fileData;
	 
	 public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
}
