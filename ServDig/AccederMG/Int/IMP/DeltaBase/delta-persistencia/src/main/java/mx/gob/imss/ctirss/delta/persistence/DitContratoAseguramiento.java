package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_CONTRATO_ASEGURAMIENTO database table.
 * 
 */
@Entity
@Table(name="DIT_CONTRATO_ASEGURAMIENTO")
public class DitContratoAseguramiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CONTRATO_ASEGURAMIENTO", nullable=false, precision=22)
	private long cveIdContratoAseguramiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_ASEGURAMIENTO")
	private Date fecFinAseguramiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_ASEGURAMIENTO")
	private Date fecInicioAseguramiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_CONVENIO", precision=22)
	private BigDecimal indConvenio;

	@Column(name="NUM_POLIZA_ASEGURAMIENTO", length=50)
	private String numPolizaAseguramiento;

	@Column(name="SBC_CUOTA_PAGAR", precision=15, scale=5)
	private BigDecimal sbcCuotaPagar;

	@Column(name="TIP_SALARIO", precision=22)
	private BigDecimal tipSalario;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DicTipoContratacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_CONTRATACION")
	private DicTipoContratacion dicTipoContratacion;

	//bi-directional many-to-one association to DitPatSujObligConvenio
	@OneToMany(mappedBy="ditContratoAseguramiento")
	private List<DitPatSujObligConvenio> ditPatSujObligConvenios;

    public DitContratoAseguramiento() {
    }

	public long getCveIdContratoAseguramiento() {
		return this.cveIdContratoAseguramiento;
	}

	public void setCveIdContratoAseguramiento(long cveIdContratoAseguramiento) {
		this.cveIdContratoAseguramiento = cveIdContratoAseguramiento;
	}

	public Date getFecFinAseguramiento() {
		return this.fecFinAseguramiento;
	}

	public void setFecFinAseguramiento(Date fecFinAseguramiento) {
		this.fecFinAseguramiento = fecFinAseguramiento;
	}

	public Date getFecInicioAseguramiento() {
		return this.fecInicioAseguramiento;
	}

	public void setFecInicioAseguramiento(Date fecInicioAseguramiento) {
		this.fecInicioAseguramiento = fecInicioAseguramiento;
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

	public BigDecimal getIndConvenio() {
		return this.indConvenio;
	}

	public void setIndConvenio(BigDecimal indConvenio) {
		this.indConvenio = indConvenio;
	}

	public String getNumPolizaAseguramiento() {
		return this.numPolizaAseguramiento;
	}

	public void setNumPolizaAseguramiento(String numPolizaAseguramiento) {
		this.numPolizaAseguramiento = numPolizaAseguramiento;
	}

	public BigDecimal getSbcCuotaPagar() {
		return this.sbcCuotaPagar;
	}

	public void setSbcCuotaPagar(BigDecimal sbcCuotaPagar) {
		this.sbcCuotaPagar = sbcCuotaPagar;
	}

	public BigDecimal getTipSalario() {
		return this.tipSalario;
	}

	public void setTipSalario(BigDecimal tipSalario) {
		this.tipSalario = tipSalario;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicTipoContratacion getDicTipoContratacion() {
		return this.dicTipoContratacion;
	}

	public void setDicTipoContratacion(DicTipoContratacion dicTipoContratacion) {
		this.dicTipoContratacion = dicTipoContratacion;
	}
	
	public List<DitPatSujObligConvenio> getDitPatSujObligConvenios() {
		return this.ditPatSujObligConvenios;
	}

	public void setDitPatSujObligConvenios(List<DitPatSujObligConvenio> ditPatSujObligConvenios) {
		this.ditPatSujObligConvenios = ditPatSujObligConvenios;
	}
	
}