package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_PAT_SUJ_OBLIG_CONVENIO database table.
 * 
 */
@Entity
@Table(name="DIT_PAT_SUJ_OBLIG_CONVENIO")
public class DitPatSujObligConvenio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PAT_SUJ_OBLIG_CONVENIO", nullable=false, precision=22)
	private long cveIdPatSujObligConvenio;

	@Column(name="CAN_ASEGURADO", precision=22)
	private BigDecimal canAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CELEBRACION")
	private Date fecCelebracion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_VIGENCIA")
	private Date fecFinVigencia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INCORPORACION")
	private Date fecIncorporacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicConvenio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CONVENIO")
	private DicConvenio dicConvenio;

	//bi-directional many-to-one association to DitContratoAseguramiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CONTRATO_ASEGURAMIENTO")
	private DitContratoAseguramiento ditContratoAseguramiento;

    public DitPatSujObligConvenio() {
    }

	public long getCveIdPatSujObligConvenio() {
		return this.cveIdPatSujObligConvenio;
	}

	public void setCveIdPatSujObligConvenio(long cveIdPatSujObligConvenio) {
		this.cveIdPatSujObligConvenio = cveIdPatSujObligConvenio;
	}

	public BigDecimal getCanAsegurado() {
		return this.canAsegurado;
	}

	public void setCanAsegurado(BigDecimal canAsegurado) {
		this.canAsegurado = canAsegurado;
	}

	public Date getFecCelebracion() {
		return this.fecCelebracion;
	}

	public void setFecCelebracion(Date fecCelebracion) {
		this.fecCelebracion = fecCelebracion;
	}

	public Date getFecFinVigencia() {
		return this.fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public Date getFecIncorporacion() {
		return this.fecIncorporacion;
	}

	public void setFecIncorporacion(Date fecIncorporacion) {
		this.fecIncorporacion = fecIncorporacion;
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

	public DicConvenio getDicConvenio() {
		return this.dicConvenio;
	}

	public void setDicConvenio(DicConvenio dicConvenio) {
		this.dicConvenio = dicConvenio;
	}
	
	public DitContratoAseguramiento getDitContratoAseguramiento() {
		return this.ditContratoAseguramiento;
	}

	public void setDitContratoAseguramiento(DitContratoAseguramiento ditContratoAseguramiento) {
		this.ditContratoAseguramiento = ditContratoAseguramiento;
	}
	
}