package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import org.springframework.web.multipart.MultipartFile;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class DeteccionCargaModel extends AbstractModel{
	private static final long serialVersionUID = 1L;
	private MultipartFile archivo;
	private String msg;
	
	public MultipartFile getArchivo() {
		return archivo;
	}
	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}
	/**
	 * Metodo que obtiene el valor del atributo  msg
	 * @return  msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * Metodo que asigna un valor al atributo msg
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
