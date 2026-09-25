package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIC_MODALIDAD database table.
 * 
 */
@Entity
@Table(name="DIC_MODALIDAD")
public class DicModalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MODALIDAD", nullable=false, precision=22)
	private long cveIdModalidad;

	@Column(name="DES_MODALIDAD", length=255)
	private String desModalidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_CONSERV_DER_ATN_MED", precision=22)
	private Long indConservDerAtnMed;

	@Column(name="IND_SEM_REC", precision=22)
	private Long indSemRec;

	@Column(name="NUM_MODALIDAD", length=255)
	private String numModalidad;
	
	@Column(name="REF_SIGLA_AGREGADO_MED", length=5)
	private String siglaAgregadoMedico;

	//bi-directional many-to-one association to DicConvenio
	@OneToMany(mappedBy="dicModalidad")
	private List<DicConvenio> dicConvenios;

	//bi-directional many-to-one association to DicRegimen
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_REGIMEN")
	private DicRegimen dicRegimen;

	//bi-directional many-to-one association to DicTipoPagoModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DicTipoPagoModalidad> dicTipoPagoModalidads;

	//bi-directional many-to-one association to DitModalidadLeySeguro
	@OneToMany(mappedBy="dicModalidad")
	private List<DitModalidadLeySeguro> ditModalidadLeySeguros;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@OneToMany(mappedBy="dicModalidad")
	private List<DitPatronSujetoObligado> ditPatronSujetoObligados;

	//bi-directional many-to-one association to DitTipoAsegModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DitTipoAsegModalidad> ditTipoAsegModalidads;

	//bi-directional many-to-one association to DitTipoContribModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DitTipoContribModalidad> ditTipoContribModalidads;

	//bi-directional many-to-one association to DitTipoJornadaModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DitTipoJornadaModalidad> ditTipoJornadaModalidads;

	//bi-directional many-to-one association to DitTipoSalarioModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DitTipoSalarioModalidad> ditTipoSalarioModalidads;

	//bi-directional many-to-one association to DitTipoSemanaModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DitTipoSemanaModalidad> ditTipoSemanaModalidads;

	//bi-directional many-to-one association to DitTipoTrabModalidad
	@OneToMany(mappedBy="dicModalidad")
	private List<DitTipoTrabModalidad> ditTipoTrabModalidads;
	
	//bi-directional many-to-one association to DicFactorModalidadRama
	@OneToMany(mappedBy="dicModalidad")
	private List<DicFactorModalidadRama> dicFactorModalidadRamas;

	//bi-directional many-to-one association to DitSeguroIvro
	@OneToMany(mappedBy="dicModalidad")
	private List<DitSeguroIvro> ditSeguroIvros;

	@Column(name = "DES_NOM_MODALIDAD_CORTO", length = 100)
	private String desNomModalidadCorto;
	
    public DicModalidad() {
    }

	public long getCveIdModalidad() {
		return this.cveIdModalidad;
	}

	public void setCveIdModalidad(long cveIdModalidad) {
		this.cveIdModalidad = cveIdModalidad;
	}

	public String getDesModalidad() {
		return this.desModalidad;
	}

	public void setDesModalidad(String desModalidad) {
		this.desModalidad = desModalidad;
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

	public Long getIndConservDerAtnMed() {
		return this.indConservDerAtnMed;
	}

	public void setIndConservDerAtnMed(Long indConservDerAtnMed) {
		this.indConservDerAtnMed = indConservDerAtnMed;
	}

	public Long getIndSemRec() {
		return this.indSemRec;
	}

	public void setIndSemRec(Long indSemRec) {
		this.indSemRec = indSemRec;
	}

	public String getNumModalidad() {
		return this.numModalidad;
	}

	public void setNumModalidad(String numModalidad) {
		this.numModalidad = numModalidad;
	}

	public List<DicConvenio> getDicConvenios() {
		return this.dicConvenios;
	}

	public void setDicConvenios(List<DicConvenio> dicConvenios) {
		this.dicConvenios = dicConvenios;
	}
	
	public DicRegimen getDicRegimen() {
		return this.dicRegimen;
	}

	public void setDicRegimen(DicRegimen dicRegimen) {
		this.dicRegimen = dicRegimen;
	}
	
	public List<DicTipoPagoModalidad> getDicTipoPagoModalidads() {
		return this.dicTipoPagoModalidads;
	}

	public void setDicTipoPagoModalidads(List<DicTipoPagoModalidad> dicTipoPagoModalidads) {
		this.dicTipoPagoModalidads = dicTipoPagoModalidads;
	}
	
	public List<DitModalidadLeySeguro> getDitModalidadLeySeguros() {
		return this.ditModalidadLeySeguros;
	}

	public void setDitModalidadLeySeguros(List<DitModalidadLeySeguro> ditModalidadLeySeguros) {
		this.ditModalidadLeySeguros = ditModalidadLeySeguros;
	}
	
	public List<DitPatronSujetoObligado> getDitPatronSujetoObligados() {
		return this.ditPatronSujetoObligados;
	}

	public void setDitPatronSujetoObligados(List<DitPatronSujetoObligado> ditPatronSujetoObligados) {
		this.ditPatronSujetoObligados = ditPatronSujetoObligados;
	}
	
	public List<DitTipoAsegModalidad> getDitTipoAsegModalidads() {
		return this.ditTipoAsegModalidads;
	}

	public void setDitTipoAsegModalidads(List<DitTipoAsegModalidad> ditTipoAsegModalidads) {
		this.ditTipoAsegModalidads = ditTipoAsegModalidads;
	}
	
	public List<DitTipoContribModalidad> getDitTipoContribModalidads() {
		return this.ditTipoContribModalidads;
	}

	public void setDitTipoContribModalidads(List<DitTipoContribModalidad> ditTipoContribModalidads) {
		this.ditTipoContribModalidads = ditTipoContribModalidads;
	}
	
	public List<DitTipoJornadaModalidad> getDitTipoJornadaModalidads() {
		return this.ditTipoJornadaModalidads;
	}

	public void setDitTipoJornadaModalidads(List<DitTipoJornadaModalidad> ditTipoJornadaModalidads) {
		this.ditTipoJornadaModalidads = ditTipoJornadaModalidads;
	}
	
	public List<DitTipoSalarioModalidad> getDitTipoSalarioModalidads() {
		return this.ditTipoSalarioModalidads;
	}

	public void setDitTipoSalarioModalidads(List<DitTipoSalarioModalidad> ditTipoSalarioModalidads) {
		this.ditTipoSalarioModalidads = ditTipoSalarioModalidads;
	}
	
	public List<DitTipoSemanaModalidad> getDitTipoSemanaModalidads() {
		return this.ditTipoSemanaModalidads;
	}

	public void setDitTipoSemanaModalidads(List<DitTipoSemanaModalidad> ditTipoSemanaModalidads) {
		this.ditTipoSemanaModalidads = ditTipoSemanaModalidads;
	}
	
	public List<DitTipoTrabModalidad> getDitTipoTrabModalidads() {
		return this.ditTipoTrabModalidads;
	}

	public void setDitTipoTrabModalidads(List<DitTipoTrabModalidad> ditTipoTrabModalidads) {
		this.ditTipoTrabModalidads = ditTipoTrabModalidads;
	}

	public String getSiglaAgregadoMedico() {
		return siglaAgregadoMedico;
	}

	public void setSiglaAgregadoMedico(String siglaAgregadoMedico) {
		this.siglaAgregadoMedico = siglaAgregadoMedico;
	}

	public List<DicFactorModalidadRama> getDicFactorModalidadRamas() {
		return dicFactorModalidadRamas;
	}

	public void setDicFactorModalidadRamas(
			List<DicFactorModalidadRama> dicFactorModalidadRamas) {
		this.dicFactorModalidadRamas = dicFactorModalidadRamas;
	}

	public List<DitSeguroIvro> getDitSeguroIvros() {
		return ditSeguroIvros;
	}

	public void setDitSeguroIvros(List<DitSeguroIvro> ditSeguroIvros) {
		this.ditSeguroIvros = ditSeguroIvros;
	}

	public String getDesNomModalidadCorto() {
		return desNomModalidadCorto;
	}

	public void setDesNomModalidadCorto(String desNomModalidadCorto) {
		this.desNomModalidadCorto = desNomModalidadCorto;
	}

}