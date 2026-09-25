package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DatosAdicionalesHistoriaLaboral implements Serializable{

	private static final long serialVersionUID = -1406016526056516907L;
	private DatosContacto datosContacto;
	private String observaciones;

	public DatosAdicionalesHistoriaLaboral() {

	}

	public DatosAdicionalesHistoriaLaboral(DatosContacto datosContacto,
			String observaciones) {
		super();
		this.datosContacto = datosContacto;
		this.observaciones = observaciones;
	}

	public DatosContacto getDatosContacto() {
		return datosContacto;
	}

	public void setDatosContacto(DatosContacto datosContacto) {
		this.datosContacto = datosContacto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public String toString() {
		return "DatosAdicionalesHistoriaLaboral [datosContacto=" + datosContacto
				+ ", observaciones=" + observaciones + "]";
	}
	
	

}
