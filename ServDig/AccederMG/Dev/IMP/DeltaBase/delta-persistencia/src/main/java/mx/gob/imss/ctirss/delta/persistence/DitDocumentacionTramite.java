package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
/*
@NamedQueries({ @NamedQuery(name = "DocumentosProvatoriosDocumentacionTramite", 
query = "select b from DitDocumentacionTramite a,DitDocumentoProbatorio b a.cveIdTramite=:idTramite and b.cveIdDocumentoProbatorio=a.cveIdDocumentoProbatorio")
		
})*/
/**
 * The persistent class for the DIT_DOCUMENTACION_TRAMITE database table.
 * 
 */
@Entity
@Table(name="DIT_DOCUMENTACION_TRAMITE")
public class DitDocumentacionTramite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@EmbeddedId

	private DitDocumentacionTramitePK id;
	
	//bi-directional one-to-one association to DitDocumentoProbatorio
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE",updatable=false,insertable=false)
	private DitTramite ditTramite;
	
	//bi-directional one-to-one association to DitDocumentoProbatorio
	@ManyToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO",updatable=false,insertable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
   

	public DitDocumentacionTramite() {
    }

	public DitDocumentacionTramitePK getId() {
		return this.id;
	}

	public void setId(DitDocumentacionTramitePK id) {
		this.id = id;
	}

	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}
	


	
}