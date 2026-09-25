package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CRC_GRUPOCATEGORIA database table.
 * 
 */
@MappedSuperclass
public class AbstractCrcGrupoCategoria extends AbstractModel{

	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_CVE_GRUPOCATEGORIA_GENERATOR", sequenceName="CRS_CVE_GRUPOCATEGORIA")
	@GeneratedValue(generator="SEQ_CVE_GRUPOCATEGORIA_GENERATOR")
	@Column(name="CVE_GRUPOCATEGORIA")	
	private Integer cveGrupoCategoria;
	
	@Column(name="TX_GRUPOCATEGORIA")
	private String txGrupoCategoria;
	
	@Column(name="CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;

	@Transient
	private String folioCorreccion;

	public Integer getCveGrupoCategoria() {
		return cveGrupoCategoria;
	}

	public void setCveGrupoCategoria(Integer cveGrupoCategoria) {
		this.cveGrupoCategoria = cveGrupoCategoria;
	}

	public String getTxGrupoCategoria() {
		return txGrupoCategoria;
	}

	public void setTxGrupoCategoria(String txGrupoCategoria) {
		this.txGrupoCategoria = txGrupoCategoria;
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
	
	 public AbstractCrcGrupoCategoria() {}
}
