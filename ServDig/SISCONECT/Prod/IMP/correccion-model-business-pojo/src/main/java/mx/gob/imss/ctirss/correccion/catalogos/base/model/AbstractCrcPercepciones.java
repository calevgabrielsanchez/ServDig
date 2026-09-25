package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CGC_CATMOTIVOCANCELACION database table.
 * 
 */
@MappedSuperclass
public class AbstractCrcPercepciones extends AbstractModel{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_CVE_PERCEPCION_GENERATOR", sequenceName="CRS_CVE_PERCEPCIONES")
	@GeneratedValue(generator="SEQ_CVE_PERCEPCION_GENERATOR")
	@Column(name="CVE_PERCEPCION")	
	private Integer cvePercepcion;
	
	@Column(name="TX_REMUNERACION")
	private String txRemuneracion;

	@Column(name="CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;

	@Transient
	private String folioCorreccion;
	
    public AbstractCrcPercepciones() {}

	public Integer getCvePercepcion() {
		return cvePercepcion;
	}

	public void setCvePercepcion(Integer cvePercepcion) {
		this.cvePercepcion = cvePercepcion;
	}

	public String getTxRemuneracion() {
		return txRemuneracion;
	}

	public void setTxRemuneracion(String txRemuneracion) {
		this.txRemuneracion = txRemuneracion;
	}

	public Integer getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}

	public void setCveSolicitudCorr(Integer cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}

	public String getFolioCorreccion() {
		return folioCorreccion;
	}

	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}

	
}