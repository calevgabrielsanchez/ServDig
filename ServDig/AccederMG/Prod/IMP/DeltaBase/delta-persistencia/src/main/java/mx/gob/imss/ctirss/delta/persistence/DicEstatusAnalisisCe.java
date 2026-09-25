package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ESTATUS_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name="DIC_ESTATUS_ANALISIS_CE")
@OnSearchLlavePrimaria(atributos="cveIdEstatusAnalisis")
@ComponentComboCampoDescripcion(atributo="desCausasAnalisis")
public class DicEstatusAnalisisCe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ESTATUS_ANALISIS", nullable=false, precision=22)
	private long cveIdEstatusAnalisis;

	@Column(name="DES_CAUSAS_ANALISIS", nullable=false, length=255)
	private String desCausasAnalisis;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitAnalisisCe
	@OneToMany(mappedBy="dicEstatusAnalisisCe")
	private List<DitAnalisisCe> ditAnalisisCes;


	//bi-directional many-to-one association to DitHistEstatusAnalisi
	@OneToMany(mappedBy="dicEstatusAnalisisCe")
	private List<DitHistEstatusAnalisis> ditHistEstatusAnalisis;

    public DicEstatusAnalisisCe() {
    }

	public long getCveIdEstatusAnalisis() {
		return this.cveIdEstatusAnalisis;
	}

	public void setCveIdEstatusAnalisis(long cveIdEstatusAnalisis) {
		this.cveIdEstatusAnalisis = cveIdEstatusAnalisis;
	}

	public String getDesCausasAnalisis() {
		return this.desCausasAnalisis;
	}

	public void setDesCausasAnalisis(String desCausasAnalisis) {
		this.desCausasAnalisis = desCausasAnalisis;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public List<DitAnalisisCe> getDitAnalisisCes() {
		return this.ditAnalisisCes;
	}

	public void setDitAnalisisCes(List<DitAnalisisCe> ditAnalisisCes) {
		this.ditAnalisisCes = ditAnalisisCes;
	}
	
	public List<DitHistEstatusAnalisis> getDitHistEstatusAnalisis() {
		return this.ditHistEstatusAnalisis;
	}

	public void setDitHistEstatusAnalisis(List<DitHistEstatusAnalisis> ditHistEstatusAnalisis) {
		this.ditHistEstatusAnalisis = ditHistEstatusAnalisis;
	}
	
}