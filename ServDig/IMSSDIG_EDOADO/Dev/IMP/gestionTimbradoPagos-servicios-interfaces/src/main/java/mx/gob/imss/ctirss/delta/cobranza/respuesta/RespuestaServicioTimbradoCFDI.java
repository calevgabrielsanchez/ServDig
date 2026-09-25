package mx.gob.imss.ctirss.delta.cobranza.respuesta;

import java.io.Serializable;

public class RespuestaServicioTimbradoCFDI implements Serializable{
	
	public String xmlTimbrado;	
	public AcuseRespuestaServicio acuseRespuestaTimbrado;
	
	public String getXmlTimbrado() {
		return xmlTimbrado;
	}

	public void setXmlTimbrado(String xmlTimbrado) {
		this.xmlTimbrado = xmlTimbrado;
	}

	public AcuseRespuestaServicio getAcuseRespuestaTimbrado() {
		return acuseRespuestaTimbrado;
	}

	public void setAcuseRespuestaTimbrado(
			AcuseRespuestaServicio acuseRespuestaTimbrado) {
		this.acuseRespuestaTimbrado = acuseRespuestaTimbrado;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RespuestaServicioTimbradoCFDI [xmlTimbrado=" + xmlTimbrado
				+ ", acuseRespuestaTimbrado=" + acuseRespuestaTimbrado + "]";
	}
}
