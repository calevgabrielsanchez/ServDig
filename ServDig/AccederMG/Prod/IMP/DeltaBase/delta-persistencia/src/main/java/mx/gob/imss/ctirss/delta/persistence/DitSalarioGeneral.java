package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_SALARIO_GENERAL database table.
 * 
 */

@NamedQueries({
	@NamedQuery(name = "salarioMinimoPorDelegacion", 
			query =" select distinct sg.salarioMinimo" + 
				   " from DicDelegacion d, DicSubdelegacion s, DitMunicipioSubdelegacion ms," + 
					" DicMunicipioImss mi, DicAreaGeografica ag, DitSalarioGeneral sg" +
					" where s.dicDelegacion.cveIdDelegacion =:idDelegacion" +
					" and s.cveIdSubdelegacion = ms.dicSubdelegacion.cveIdSubdelegacion" +
					" and s.dicDelegacion.cveIdDelegacion = d.cveIdDelegacion" +
					" and mi.cveIdMunicipioImss = ms.dicMunicipioImss.cveIdMunicipioImss" +
					" and ag.cveIdAreaGeografica = mi.dicAreaGeografica.cveIdAreaGeografica" +
					" and sg.dicAreaGeografica.cveIdAreaGeografica = ag.cveIdAreaGeografica " 
				)
})

@Entity
@Table(name="DIT_SALARIO_GENERAL")
public class DitSalarioGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_SALARIO_GENERAL", nullable=false, precision=22)
	private long cveIdSalarioGeneral;

	@Column(name="ESTATUS_SALARIO", precision=22)
	private BigDecimal estatusSalario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_VIGENCIA")
	private Date fecFinVigencia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_VIGENCIA")
	private Date fecInicioVigencia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO")
	private Date fecRegistro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="PORCENTAJE_INCREMENTO", precision=9, scale=5)
	private BigDecimal porcentajeIncremento;

	@Column(name="SALARIO_MIN_INTEGRADO", precision=9, scale=2)
	private BigDecimal salarioMinIntegrado;

	@Column(name="SALARIO_MINIMO", precision=9, scale=2)
	private BigDecimal salarioMinimo;

	//bi-directional many-to-one association to DicAreaGeografica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_AREA_GEOGRAFICA")
	private DicAreaGeografica dicAreaGeografica;

	//bi-directional many-to-one association to DicCiclo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CICLO")
	private DicCiclo dicCiclo;

    public DitSalarioGeneral() {
    }

	public long getCveIdSalarioGeneral() {
		return this.cveIdSalarioGeneral;
	}

	public void setCveIdSalarioGeneral(long cveIdSalarioGeneral) {
		this.cveIdSalarioGeneral = cveIdSalarioGeneral;
	}

	public BigDecimal getEstatusSalario() {
		return this.estatusSalario;
	}

	public void setEstatusSalario(BigDecimal estatusSalario) {
		this.estatusSalario = estatusSalario;
	}

	public Date getFecFinVigencia() {
		return this.fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public Date getFecInicioVigencia() {
		return this.fecInicioVigencia;
	}

	public void setFecInicioVigencia(Date fecInicioVigencia) {
		this.fecInicioVigencia = fecInicioVigencia;
	}

	public Date getFecRegistro() {
		return this.fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public BigDecimal getPorcentajeIncremento() {
		return this.porcentajeIncremento;
	}

	public void setPorcentajeIncremento(BigDecimal porcentajeIncremento) {
		this.porcentajeIncremento = porcentajeIncremento;
	}

	public BigDecimal getSalarioMinIntegrado() {
		return this.salarioMinIntegrado;
	}

	public void setSalarioMinIntegrado(BigDecimal salarioMinIntegrado) {
		this.salarioMinIntegrado = salarioMinIntegrado;
	}

	public BigDecimal getSalarioMinimo() {
		return this.salarioMinimo;
	}

	public void setSalarioMinimo(BigDecimal salarioMinimo) {
		this.salarioMinimo = salarioMinimo;
	}

	public DicAreaGeografica getDicAreaGeografica() {
		return this.dicAreaGeografica;
	}

	public void setDicAreaGeografica(DicAreaGeografica dicAreaGeografica) {
		this.dicAreaGeografica = dicAreaGeografica;
	}
	
	public DicCiclo getDicCiclo() {
		return this.dicCiclo;
	}

	public void setDicCiclo(DicCiclo dicCiclo) {
		this.dicCiclo = dicCiclo;
	}
	
}