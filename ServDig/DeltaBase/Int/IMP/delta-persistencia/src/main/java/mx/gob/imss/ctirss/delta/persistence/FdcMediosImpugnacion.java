package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDC_MEDIOS_IMPUGNACION database table.
 * 
 */
@Entity
@Table(name="FDC_MEDIOS_IMPUGNACION")
public class FdcMediosImpugnacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_IMPUGNACION", nullable=false, precision=22)
	private long idImpugnacion;

	@Column(name="DESC_IMPUGNACION", length=100)
	private String descImpugnacion;

	//bi-directional many-to-one association to FdtImpugnacion
	@OneToMany(mappedBy="fdcMediosImpugnacion")
	private List<FdtImpugnacion> fdtImpugnacions;

    public FdcMediosImpugnacion() {
    }

	public long getIdImpugnacion() {
		return this.idImpugnacion;
	}

	public void setIdImpugnacion(long idImpugnacion) {
		this.idImpugnacion = idImpugnacion;
	}

	public String getDescImpugnacion() {
		return this.descImpugnacion;
	}

	public void setDescImpugnacion(String descImpugnacion) {
		this.descImpugnacion = descImpugnacion;
	}

	public List<FdtImpugnacion> getFdtImpugnacions() {
		return this.fdtImpugnacions;
	}

	public void setFdtImpugnacions(List<FdtImpugnacion> fdtImpugnacions) {
		this.fdtImpugnacions = fdtImpugnacions;
	}
	
}