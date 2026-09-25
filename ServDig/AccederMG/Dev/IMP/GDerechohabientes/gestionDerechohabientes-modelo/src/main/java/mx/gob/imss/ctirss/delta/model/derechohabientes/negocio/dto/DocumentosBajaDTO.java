package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentosBajaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Reporte4305A> datos4305A;
	private List<Sav002DTO> datosSav002;
	
	public List<Reporte4305A> getDatos4305A() {
		return datos4305A;
	}
	public void setDatos4305A(List<Reporte4305A> datos4305a) {
		datos4305A = datos4305a;
	}
	public List<Sav002DTO> getDatosSav002() {
		return datosSav002;
	}
	public void setDatosSav002(List<Sav002DTO> datosSav002) {
		this.datosSav002 = datosSav002;
	}
	
}
