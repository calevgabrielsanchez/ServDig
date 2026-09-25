package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo;

import java.util.List;

public class InfoPagoObject {

	private String claveAplicativo;
	
	private List<DatosLinea> datosLinea;
	
	public InfoPagoObject() {}

	public String getClaveAplicativo() {
		return claveAplicativo;
	}

	public void setClaveAplicativo(String claveAplicativo) {
		this.claveAplicativo = claveAplicativo;
	}

	public List<DatosLinea> getDatosLinea() {
		return datosLinea;
	}

	public void setDatosLinea(List<DatosLinea> datosLinea) {
		this.datosLinea = datosLinea;
	}

	@Override
	public String toString() {
		return "InfoPagoObject [claveAplicativo=" + claveAplicativo + ", datosLinea=" + datosLinea + "]";
	}
		
}
