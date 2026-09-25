package mx.gob.imss.ctirss.delta.model.firma;

public class PeticionFirmadoXmlSimple extends PeticionFirmadoSimple {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String xmls;
	private String numSerie;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getXmls() {
		return xmls;
	}

	public void setXmls(String xmls) {
		this.xmls = xmls;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
}