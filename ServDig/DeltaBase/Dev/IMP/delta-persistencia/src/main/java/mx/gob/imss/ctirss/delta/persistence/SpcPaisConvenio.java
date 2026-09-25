package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPC_PAIS_CONVENIO database table.
 * 
 */
@Entity
@Table(name="SPC_PAIS_CONVENIO")
@NamedQuery(name="SpcPaisConvenio.findAll", query="SELECT s FROM SpcPaisConvenio s")
public class SpcPaisConvenio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_PAIS_CONVENIO_IDPAIS_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_PAIS_CONVENIO_IDPAIS_GENERATOR")
	@Column(name="ID_PAIS")
	private String idPais;

	@Column(name="DES_CORTA_PAIS")
	private String desCortaPais;

	@Column(name="DES_FUNDAMENTO_LEGAL")
	private String desFundamentoLegal;

	@Column(name="DES_PAIS")
	private String desPais;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_CONVENIO")
	private Date fecInicioConvenio;

	@Column(name="IND_CONVENIO_SEMANAS_RECONOCI")
	private String indConvenioSemanasReconoci;

	@Column(name="IND_PAGO_EXTRANJERO")
	private String indPagoExtranjero;

	@Column(name="IND_PAIS_CONVENIO")
	private String indPaisConvenio;

	public SpcPaisConvenio() {
	}

	public String getIdPais() {
		return this.idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getDesCortaPais() {
		return this.desCortaPais;
	}

	public void setDesCortaPais(String desCortaPais) {
		this.desCortaPais = desCortaPais;
	}

	public String getDesFundamentoLegal() {
		return this.desFundamentoLegal;
	}

	public void setDesFundamentoLegal(String desFundamentoLegal) {
		this.desFundamentoLegal = desFundamentoLegal;
	}

	public String getDesPais() {
		return this.desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

	public Date getFecInicioConvenio() {
		return this.fecInicioConvenio;
	}

	public void setFecInicioConvenio(Date fecInicioConvenio) {
		this.fecInicioConvenio = fecInicioConvenio;
	}

	public String getIndConvenioSemanasReconoci() {
		return this.indConvenioSemanasReconoci;
	}

	public void setIndConvenioSemanasReconoci(String indConvenioSemanasReconoci) {
		this.indConvenioSemanasReconoci = indConvenioSemanasReconoci;
	}

	public String getIndPagoExtranjero() {
		return this.indPagoExtranjero;
	}

	public void setIndPagoExtranjero(String indPagoExtranjero) {
		this.indPagoExtranjero = indPagoExtranjero;
	}

	public String getIndPaisConvenio() {
		return this.indPaisConvenio;
	}

	public void setIndPaisConvenio(String indPaisConvenio) {
		this.indPaisConvenio = indPaisConvenio;
	}

}