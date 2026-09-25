package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractSatObra;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.utils.Functions;

@Entity
@Table(name="SAT_OBRA")
public class SatObra extends AbstractSatObra{
	
	@Transient
	private SatUbicacion ubicacion;

	public SatUbicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(SatUbicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getFechaInicial()
	{
		return Functions.dateToString(this.getFecFechainicioFc());
	}

	public String getFechaFinal()
	{
		return Functions.dateToString(this.getFecFechaterminoFc());
	}
	
	public String getFechaRegistro()
	{
		return Functions.dateToString(this.getFecFecharegistroFc());
	}
	
}
