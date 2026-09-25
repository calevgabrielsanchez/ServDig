package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CGC_CATMOTIVOCANCELACION database table.
 * 
 */
@MappedSuperclass
public class AbstractCrcGastos extends AbstractModel{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_CVE_GASTO_GENERATOR", sequenceName="CRS_CVE_GASTOS")
	@GeneratedValue(generator="SEQ_CVE_GASTO_GENERATOR")
	@Column(name="CVE_GASTOS")	
	private Integer cveGasto;
	
	@Column(name="TX_GASTOS")
	private String txGasto;
	
	@Column(name="CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;

	@Transient
	private String folioCorreccion;
	
    public AbstractCrcGastos() {}

	public Integer getCveGasto() {
		return cveGasto;
	}

	public void setCveGasto(Integer cveGasto) {
		this.cveGasto = cveGasto;
	}

	public String getTxGasto() {
		return txGasto;
	}

	public void setTxGasto(String txGasto) {
		this.txGasto = txGasto;
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