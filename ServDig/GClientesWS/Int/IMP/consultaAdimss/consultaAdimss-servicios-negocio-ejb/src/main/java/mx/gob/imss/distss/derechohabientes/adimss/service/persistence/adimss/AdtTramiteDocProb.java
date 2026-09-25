package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ADT_TRAMITE_DOC_PROB database table.
 * 
 */
@Entity
@Table(name="ADT_TRAMITE_DOC_PROB")
@NamedQuery(name="AdtTramiteDocProb.findAll", query="SELECT a FROM AdtTramiteDocProb a")
public class AdtTramiteDocProb implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdtTramiteDocProbPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VIGENCIA_DOC_PROBATORIO")
	private Date fecVigenciaDocProbatorio;

	@Column(name="NUM_DOC_PROB")
	private String numDocProb;

	@Column(name="NUM_OID_DOC_PROBATORIO")
	private String numOidDocProbatorio;

	//bi-directional many-to-one association to AdcDocProb
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_DOC_PROBATORIO", referencedColumnName="CVE_DOC_PROBATORIO", insertable=false, updatable=false),
		@JoinColumn(name="CVE_TIPO_DOC_PROBATORIO", referencedColumnName="CVE_TIPO_DOC_PROBATORIO", insertable=false, updatable=false)
		})
	private AdcDocProb adcDocProb;

	public AdtTramiteDocProb() {
	}

	public AdtTramiteDocProbPK getId() {
		return this.id;
	}

	public void setId(AdtTramiteDocProbPK id) {
		this.id = id;
	}

	public Date getFecVigenciaDocProbatorio() {
		return this.fecVigenciaDocProbatorio;
	}

	public void setFecVigenciaDocProbatorio(Date fecVigenciaDocProbatorio) {
		this.fecVigenciaDocProbatorio = fecVigenciaDocProbatorio;
	}

	public String getNumDocProb() {
		return this.numDocProb;
	}

	public void setNumDocProb(String numDocProb) {
		this.numDocProb = numDocProb;
	}

	public String getNumOidDocProbatorio() {
		return this.numOidDocProbatorio;
	}

	public void setNumOidDocProbatorio(String numOidDocProbatorio) {
		this.numOidDocProbatorio = numOidDocProbatorio;
	}

	public AdcDocProb getAdcDocProb() {
		return this.adcDocProb;
	}

	public void setAdcDocProb(AdcDocProb adcDocProb) {
		this.adcDocProb = adcDocProb;
	}

}