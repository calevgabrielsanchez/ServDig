package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CONVENIO database table.
 * 
 */
@Entity
@Table(name="DIC_CONVENIO")
public class DicConvenio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CONVENIO", nullable=false, precision=22)
	private long cveIdConvenio;

	@Column(name="CONTRIBUCION_GOB_FED", precision=15, scale=5)
	private BigDecimal contribucionGobFed;

	@Column(name="CUOTA_ASEGURADO", precision=15, scale=5)
	private BigDecimal cuotaAsegurado;

	@Column(name="CUOTA_SUJETO_OBLIGADO", precision=15, scale=5)
	private BigDecimal cuotaSujetoObligado;

	@Column(name="DES_PROCEDIMIENTO_INSCRIPCION", length=2000)
	private String desProcedimientoInscripcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NOM_CONVENIO", length=255)
	private String nomConvenio;

	@Column(name="POR_DESCUENTO", precision=18, scale=15)
	private BigDecimal porDescuento;

	@Column(name="POR_PRIMA_FINANCIAMIENTO", precision=18, scale=15)
	private BigDecimal porPrimaFinanciamiento;

	@Column(name="POR_REVERSION", precision=18, scale=15)
	private BigDecimal porReversion;

	//bi-directional many-to-one association to DicTipoConvenio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_CONVENIO")
	private DicTipoConvenio dicTipoConvenio;

	//bi-directional many-to-one association to DicModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DitPatSujObligConvenio
	@OneToMany(mappedBy="dicConvenio")
	private List<DitPatSujObligConvenio> ditPatSujObligConvenios;

    public DicConvenio() {
    }

	public long getCveIdConvenio() {
		return this.cveIdConvenio;
	}

	public void setCveIdConvenio(long cveIdConvenio) {
		this.cveIdConvenio = cveIdConvenio;
	}

	public BigDecimal getContribucionGobFed() {
		return this.contribucionGobFed;
	}

	public void setContribucionGobFed(BigDecimal contribucionGobFed) {
		this.contribucionGobFed = contribucionGobFed;
	}

	public BigDecimal getCuotaAsegurado() {
		return this.cuotaAsegurado;
	}

	public void setCuotaAsegurado(BigDecimal cuotaAsegurado) {
		this.cuotaAsegurado = cuotaAsegurado;
	}

	public BigDecimal getCuotaSujetoObligado() {
		return this.cuotaSujetoObligado;
	}

	public void setCuotaSujetoObligado(BigDecimal cuotaSujetoObligado) {
		this.cuotaSujetoObligado = cuotaSujetoObligado;
	}

	public String getDesProcedimientoInscripcion() {
		return this.desProcedimientoInscripcion;
	}

	public void setDesProcedimientoInscripcion(String desProcedimientoInscripcion) {
		this.desProcedimientoInscripcion = desProcedimientoInscripcion;
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

	public String getNomConvenio() {
		return this.nomConvenio;
	}

	public void setNomConvenio(String nomConvenio) {
		this.nomConvenio = nomConvenio;
	}

	public BigDecimal getPorDescuento() {
		return this.porDescuento;
	}

	public void setPorDescuento(BigDecimal porDescuento) {
		this.porDescuento = porDescuento;
	}

	public BigDecimal getPorPrimaFinanciamiento() {
		return this.porPrimaFinanciamiento;
	}

	public void setPorPrimaFinanciamiento(BigDecimal porPrimaFinanciamiento) {
		this.porPrimaFinanciamiento = porPrimaFinanciamiento;
	}

	public BigDecimal getPorReversion() {
		return this.porReversion;
	}

	public void setPorReversion(BigDecimal porReversion) {
		this.porReversion = porReversion;
	}

	public DicTipoConvenio getDicTipoConvenio() {
		return this.dicTipoConvenio;
	}

	public void setDicTipoConvenio(DicTipoConvenio dicTipoConvenio) {
		this.dicTipoConvenio = dicTipoConvenio;
	}
	
	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public List<DitPatSujObligConvenio> getDitPatSujObligConvenios() {
		return this.ditPatSujObligConvenios;
	}

	public void setDitPatSujObligConvenios(List<DitPatSujObligConvenio> ditPatSujObligConvenios) {
		this.ditPatSujObligConvenios = ditPatSujObligConvenios;
	}
	
}