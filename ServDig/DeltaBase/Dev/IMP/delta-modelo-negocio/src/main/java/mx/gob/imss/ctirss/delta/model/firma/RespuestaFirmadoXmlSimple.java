package mx.gob.imss.ctirss.delta.model.firma;

public class RespuestaFirmadoXmlSimple extends RespuestaFirmadoSimple {

	private static final long serialVersionUID = 1L;

	private String numSerie;
	private String xmlFirmado;

	// Atributos de control para la respuesta
	private Integer exito;
	private Integer claveError;
	private String descripcion;

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getXmlFirmado() {
		return xmlFirmado;
	}

	public void setXmlFirmado(String xmlFirmado) {
		this.xmlFirmado = xmlFirmado;
	}

	public Integer getExito() {
		return exito;
	}

	public void setExito(Integer exito) {
		this.exito = exito;
	}

	public Integer getClaveError() {
		return claveError;
	}

	public void setClaveError(Integer claveError) {
		this.claveError = claveError;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
