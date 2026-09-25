package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Nacimiento")
public class Nacimiento extends Acta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer anio;
	private String crip;
	

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getCrip() {
		return crip;
	}

	public void setCrip(String crip) {
		this.crip = crip;
	}


	@Override
	public String toString() {
		return "Nacimiento [anio=" + anio + ", crip=" + crip
				+ ", noJuzgado=" + this.getNoJuzgado() + "]";
	}

}
