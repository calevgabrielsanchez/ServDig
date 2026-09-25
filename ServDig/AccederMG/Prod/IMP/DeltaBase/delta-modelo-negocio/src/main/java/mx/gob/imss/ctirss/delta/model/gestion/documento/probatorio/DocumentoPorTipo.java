package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;

public class DocumentoPorTipo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7679617085570835897L;
	private TipoDocumentoProbatorio tipoDocumentoProbatorio;
	private Documento documento;
	private Long idDocumentoPorTipo;

	private String idDocumentoPorTipoHashed;

	public TipoDocumentoProbatorio getTipoDocumentoProbatorio() {
		return tipoDocumentoProbatorio;
	}

	public void setTipoDocumentoProbatorio(
			TipoDocumentoProbatorio tipoDocumentoProbatorio) {
		this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Long getIdDocumentoPorTipo() {
		return idDocumentoPorTipo;
	}

	public void setIdDocumentoPorTipo(Long idDocumentoPorTipo) {
		this.idDocumentoPorTipo = idDocumentoPorTipo;
		
		if (idDocumentoPorTipo != null) {
			try {
				this.idDocumentoPorTipoHashed = Base64Cipher.cifrar(idDocumentoPorTipo.toString());
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
	}

	public String getIdDocumentoPorTipoHashed() {
		return idDocumentoPorTipoHashed;
	}

	public void setIdDocumentoPorTipoHashed(String idDocumentoPorTipoHashed) {
		this.idDocumentoPorTipoHashed = idDocumentoPorTipoHashed;
	}

}
