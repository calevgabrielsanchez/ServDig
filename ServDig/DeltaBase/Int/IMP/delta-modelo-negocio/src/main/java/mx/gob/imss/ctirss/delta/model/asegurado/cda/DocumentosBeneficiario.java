package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

public class DocumentosBeneficiario extends AbstractModel implements Serializable{
	
	private static final long serialVersionUID = 1L;	
	private List <Integer> idDocumento;
	private String tipoBeneficiario;
	
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}
	public List<Integer> getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(List <Integer> idDocumento) {
		this.idDocumento = idDocumento;
	}
}
