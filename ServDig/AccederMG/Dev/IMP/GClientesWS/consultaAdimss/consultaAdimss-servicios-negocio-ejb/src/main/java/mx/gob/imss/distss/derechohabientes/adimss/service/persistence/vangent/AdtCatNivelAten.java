package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_NIVEL_ATEN database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_NIVEL_ATEN")
@NamedQuery(name="AdtCatNivelAten.findAll", query="SELECT a FROM AdtCatNivelAten a")
public class AdtCatNivelAten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_NIVEL_ATENCION")
	private long numNivelAtencion;

	@Column(name="DESC_NIVEL_ATENCION")
	private String descNivelAtencion;

	public AdtCatNivelAten() {
	}

	public long getNumNivelAtencion() {
		return this.numNivelAtencion;
	}

	public void setNumNivelAtencion(long numNivelAtencion) {
		this.numNivelAtencion = numNivelAtencion;
	}

	public String getDescNivelAtencion() {
		return this.descNivelAtencion;
	}

	public void setDescNivelAtencion(String descNivelAtencion) {
		this.descNivelAtencion = descNivelAtencion;
	}

}