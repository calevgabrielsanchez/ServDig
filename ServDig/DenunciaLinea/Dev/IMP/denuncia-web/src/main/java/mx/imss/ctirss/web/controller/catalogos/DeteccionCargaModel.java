package mx.imss.ctirss.web.controller.catalogos;

import org.springframework.web.multipart.MultipartFile;

import mx.imss.ctirss.framework.base.model.AbstractModel;

public class DeteccionCargaModel extends AbstractModel{
	private static final long serialVersionUID = 1L;
	private MultipartFile archivo;
	
	public MultipartFile getArchivo() {
		return archivo;
	}
	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}
}
