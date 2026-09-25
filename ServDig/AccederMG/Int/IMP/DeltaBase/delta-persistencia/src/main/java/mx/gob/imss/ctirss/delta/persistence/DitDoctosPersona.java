package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_DOCTOS_PERSONA database table.
 * 
 */
@Entity
@Table(name="DIT_DOCTOS_PERSONA")
public class DitDoctosPersona implements Serializable  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitDoctosPersonaPK id;

	//bi-directional many-to-one association to DitDocumentoProbatorio
    @ManyToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, insertable=false, updatable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;

    public DitDoctosPersona() {
    }

	public DitDoctosPersonaPK getId() {
		return this.id;
	}

	public void setId(DitDoctosPersonaPK id) {
		this.id = id;
	}
	
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return this.ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}
	
}