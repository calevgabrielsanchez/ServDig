/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import mx.gob.imss.ctirss.correccion.model.CgcCatConceptoOmitido;
import mx.gob.imss.ctirss.correccion.model.CgcCatSituacionCO;

/**
 * The primary key class for the CGT_ANEXOCONCEPTOOMITIDO database table.
 * 
 */
@Embeddable
public class AbstractCgtAnexoConceptoOmitidoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_PROCESO")
	private long idProceso;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SITUACIONCO", insertable = true, updatable = true)
	private CgcCatSituacionCO cgcCatSituacionCO;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CONCEPTOOMITIDO", insertable = true, updatable = true)
	private CgcCatConceptoOmitido cgcCatConceptoOmitido;

	private String folio;

	public AbstractCgtAnexoConceptoOmitidoPK() {
	}

	public long getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}

	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public CgcCatSituacionCO getCgcCatSituacionCO() {
		return cgcCatSituacionCO;
	}

	public void setCgcCatSituacionCO(CgcCatSituacionCO cgcCatSituacionCO) {
		this.cgcCatSituacionCO = cgcCatSituacionCO;
	}

	public CgcCatConceptoOmitido getCgcCatConceptoOmitido() {
		return cgcCatConceptoOmitido;
	}

	public void setCgcCatConceptoOmitido(
			CgcCatConceptoOmitido cgcCatConceptoOmitido) {
		this.cgcCatConceptoOmitido = cgcCatConceptoOmitido;
	}

}