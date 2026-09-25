package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CLAVE_PRESUPUESTAL database table.
 * 
 */
@Entity
@Table(name="DIC_CLAVE_PRESUPUESTAL")
public class DicClavePresupuestal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CLAVE_PRESUPUESTAL", nullable=false, precision=22)
	private long cveIdClavePresupuestal;

	@Column(name="CVE_PRESUPUESTAL", length=50)
	private String cvePresupuestal;

	@Column(name="CVE_PRESUPUESTAL_RECORTADA", length=10)
	private String cvePresupuestalRecortada;

	@Column(name="DES_CLAVE_PRESUPUESTAL", length=100)
	private String desClavePresupuestal;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicGuarderia
	@OneToMany(mappedBy="dicClavePresupuestal")
	private List<DicGuarderia> dicGuarderias;

	//bi-directional many-to-one association to DicUmf
	@OneToMany(mappedBy="dicClavePresupuestal")
	private List<DicUmf> dicUmfs;

    public DicClavePresupuestal() {
    }

	public long getCveIdClavePresupuestal() {
		return this.cveIdClavePresupuestal;
	}

	public void setCveIdClavePresupuestal(long cveIdClavePresupuestal) {
		this.cveIdClavePresupuestal = cveIdClavePresupuestal;
	}

	public String getCvePresupuestal() {
		return this.cvePresupuestal;
	}

	public void setCvePresupuestal(String cvePresupuestal) {
		this.cvePresupuestal = cvePresupuestal;
	}

	public String getCvePresupuestalRecortada() {
		return this.cvePresupuestalRecortada;
	}

	public void setCvePresupuestalRecortada(String cvePresupuestalRecortada) {
		this.cvePresupuestalRecortada = cvePresupuestalRecortada;
	}

	public String getDesClavePresupuestal() {
		return this.desClavePresupuestal;
	}

	public void setDesClavePresupuestal(String desClavePresupuestal) {
		this.desClavePresupuestal = desClavePresupuestal;
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

	public List<DicGuarderia> getDicGuarderias() {
		return this.dicGuarderias;
	}

	public void setDicGuarderias(List<DicGuarderia> dicGuarderias) {
		this.dicGuarderias = dicGuarderias;
	}
	
	public List<DicUmf> getDicUmfs() {
		return this.dicUmfs;
	}

	public void setDicUmfs(List<DicUmf> dicUmfs) {
		this.dicUmfs = dicUmfs;
	}
	
}