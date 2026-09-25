package mx.gob.imss.ctirss.delta.persistence;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;


/**
 * The persistent class for the DIV_POBLACION_UMF_TURNO_CONS database table.
 * 
 */
@Entity
@Immutable
@Table(name="DIV_POBLACION_UMF_TURNO_CONS")
public class DivPoblacionUmfTurnoCons implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_ID_UMF_CONS_TURNO", insertable = false, updatable = false)
	private Long cveIdUmfConsTurno;

	@Column(name="NUM_POBLACION")
  	private Long numPoblacion;
  	
  	
	public DivPoblacionUmfTurnoCons(){
	}


	public Long getCveIdUmfConsTurno() {
		return cveIdUmfConsTurno;
	}


	public void setCveIdUmfConsTurno(Long cveIdUmfConsTurno) {
		this.cveIdUmfConsTurno = cveIdUmfConsTurno;
	}


	public Long getNumPoblacion() {
		return numPoblacion;
	}


	public void setNumPoblacion(Long numPoblacion) {
		this.numPoblacion = numPoblacion;
	}
	
	
	
	
}