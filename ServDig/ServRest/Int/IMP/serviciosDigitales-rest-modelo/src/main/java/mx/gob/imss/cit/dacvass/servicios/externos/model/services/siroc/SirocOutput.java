package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SirocOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Obraoutput obra;
	UbicacionOutput ubicacion;
	DatosPatronOutput datosPatron;
	CumplimientOutput cumplimiento;
	List<IncidenciaOutput> incidencias;
	public Obraoutput getObra() {
		return obra;
	}
	public void setObra(Obraoutput obra) {
		this.obra = obra;
	}
	public UbicacionOutput getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(UbicacionOutput ubicacion) {
		this.ubicacion = ubicacion;
	}
	public DatosPatronOutput getDatosPatron() {
		return datosPatron;
	}
	public void setDatosPatron(DatosPatronOutput datosPatron) {
		this.datosPatron = datosPatron;
	}
	public CumplimientOutput getCumplimiento() {
		return cumplimiento;
	}
	public void setCumplimiento(CumplimientOutput cumplimiento) {
		this.cumplimiento = cumplimiento;
	}
	public List<IncidenciaOutput> getIncidencias() {
		return incidencias;
	}
	public void setIncidencias(List<IncidenciaOutput> incidencias) {
		this.incidencias = incidencias;
	}
	
	
	
	
}
