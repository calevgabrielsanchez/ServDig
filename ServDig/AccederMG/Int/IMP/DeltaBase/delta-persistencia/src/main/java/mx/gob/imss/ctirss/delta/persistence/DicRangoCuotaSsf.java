package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIC_RANGO_CUOTA_SSF database table.
 * 
 */
@Entity
@Table(name="DIC_RANGO_CUOTA_SSF")
public class DicRangoCuotaSsf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_RANGO_CUOTA_SSF", nullable=false, precision=22)
	private long cveIdRangoCuotaSsf;

	@Column(name="DES_RANGO_CUOTA_SSF", length=50)
	private String desRangoCuotaSsf;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="MON_CUOTA_TOTAL", length=20)
	private String monCuotaTotal;

	@Column(name="NUM_ANIOS", precision=22)
	private BigDecimal numAnios;

	//bi-directional many-to-one association to DicCiclo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CICLO")
	private DicCiclo dicCiclo;

    public DicRangoCuotaSsf() {
    }

	public long getCveIdRangoCuotaSsf() {
		return this.cveIdRangoCuotaSsf;
	}

	public void setCveIdRangoCuotaSsf(long cveIdRangoCuotaSsf) {
		this.cveIdRangoCuotaSsf = cveIdRangoCuotaSsf;
	}

	public String getDesRangoCuotaSsf() {
		return this.desRangoCuotaSsf;
	}

	public void setDesRangoCuotaSsf(String desRangoCuotaSsf) {
		this.desRangoCuotaSsf = desRangoCuotaSsf;
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

	public String getMonCuotaTotal() {
		return this.monCuotaTotal;
	}

	public void setMonCuotaTotal(String monCuotaTotal) {
		this.monCuotaTotal = monCuotaTotal;
	}

	public BigDecimal getNumAnios() {
		return this.numAnios;
	}

	public void setNumAnios(BigDecimal numAnios) {
		this.numAnios = numAnios;
	}

	public DicCiclo getDicCiclo() {
		return this.dicCiclo;
	}

	public void setDicCiclo(DicCiclo dicCiclo) {
		this.dicCiclo = dicCiclo;
	}
	
}