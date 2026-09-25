package mx.gob.imss.ctirss.correccion.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractSatUbicacion;

@Entity
@Table(name="SAT_UBICACION")
public class SatUbicacion extends AbstractSatUbicacion{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Transient
	private SacMunicipio municipio;
	
	@Transient
	private SatPatron patron;
	

	public SatPatron getPatron() {
		return patron;
	}

	@Transient
	public void setPatron(SatPatron patron) {
		this.patron = patron;
	}

	public SacMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(SacMunicipio municipio) {
		this.municipio = municipio;
	}
	
	
}
