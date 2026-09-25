package mx.gob.imss.ctirss.delta.gestion.asegurado.web.bean;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileBean extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private CommonsMultipartFile fileData;
	private String filename;
	private String erpName;
	private String nombreComercialERP;
	private List<CorreoElectronico> correosContacto;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getErpName() {
		return erpName;
	}

	public void setErpName(String erpName) {
		this.erpName = erpName;
	}

	public String getNombreComercialERP() {
		return nombreComercialERP;
	}

	public void setNombreComercialERP(String nombreComercialERP) {
		this.nombreComercialERP = nombreComercialERP;
	}

	public List<CorreoElectronico> getCorreosContacto() {
		return correosContacto;
	}

	public void setCorreosContacto(List<CorreoElectronico> correosContacto) {
		this.correosContacto = correosContacto;
	}

}
