package mx.gob.imss.ctirss.delta.cobranza.respuesta;

import java.io.Serializable;

public class AcuseRespuestaServicio implements Serializable {
	
	public int codeStatus;
	protected String descriptionError;
	
	public int getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(int localCodeStatus) {
		this.codeStatus = localCodeStatus;
	}
	public String getDescriptionError() {
		return descriptionError;
	}
	public void setDescriptionError(String localDescriptionError) {
		this.descriptionError = localDescriptionError;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AcuseRespuestaServicio [localCodeStatus=" + codeStatus
				+ ", localDescriptionError=" + descriptionError + "]";
	}		
}
