package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_UMA")
public class DitUma implements Serializable {
	
	private static final long serialVersionUID = -4773893393519978680L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_UMA", nullable=false, precision=22)
	private long cveIdUma;
	
	@Column(name="UMA_DIARIO", precision=9, scale=2)
	private BigDecimal umaDiario;
	
	@Column(name="UMA_MENSUAL", precision=9, scale=2)
	private BigDecimal umaMensual;
	
	@Column(name="UMA_ANUAL", precision=9, scale=2)
	private BigDecimal umaAnual;
	
	@Column(name="ESTATUS_UMA", precision=22)
	private BigDecimal estatusUma;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_VIGENCIA")
	private Date fecInicioVigencia;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_VIGENCIA")
	private Date fecFinVigencia;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
    
    //bi-directional many-to-one association to DicCiclo
  	@ManyToOne(fetch=FetchType.LAZY)
  	@JoinColumn(name="CVE_ID_CICLO")
  	private DicCiclo dicCiclo;

	public long getCveIdUma() {
		return cveIdUma;
	}

	public void setCveIdUma(long cveIdUma) {
		this.cveIdUma = cveIdUma;
	}

	public BigDecimal getEstatusUma() {
		return estatusUma;
	}

	public void setEstatusUma(BigDecimal estatusUma) {
		this.estatusUma = estatusUma;
	}

	public BigDecimal getUmaDiario() {
		return umaDiario;
	}

	public void setUmaDiario(BigDecimal umaDiario) {
		this.umaDiario = umaDiario;
	}

	public BigDecimal getUmaMensual() {
		return umaMensual;
	}

	public void setUmaMensual(BigDecimal umaMensual) {
		this.umaMensual = umaMensual;
	}

	public BigDecimal getUmaAnual() {
		return umaAnual;
	}

	public void setUmaAnual(BigDecimal umaAnual) {
		this.umaAnual = umaAnual;
	}

	public Date getFecInicioVigencia() {
		return fecInicioVigencia;
	}

	public void setFecInicioVigencia(Date fecInicioVigencia) {
		this.fecInicioVigencia = fecInicioVigencia;
	}

	public Date getFecFinVigencia() {
		return fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public DicCiclo getDicCiclo() {
		return dicCiclo;
	}

	public void setDicCiclo(DicCiclo dicCiclo) {
		this.dicCiclo = dicCiclo;
	}
	
}
