package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;





/**
 * The persistent class for the DIT_TRAMITE_PERSONA_FISICA database table.
 * 
 */
/*TODO cambiar name query debido a los cambios de la base de datos
@NamedQueries({ 
	
	@NamedQuery(name = "findTramitesBySolicitud", 
			query = "select t from DitTramitePersonaFisica t where t.ditSolicitud.cveIdSolicitud=:idSolicitud")
})
*/

@Entity
@Table(name="DIT_TRAMITE_PERSONA_FISICA")

public class DitTramitePersonaFisica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "cveIdPersona", column = @Column(name = "CVE_ID_PERSONA", nullable = false)),
		@AttributeOverride(name = "cveIdTramite", column = @Column(name = "CVE_ID_TRAMITE", nullable = false))
	})
	private DitTramitePersonaFisicaPK id;
	
	//bi-directional one-to-one 
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TRAMITE",updatable=false,insertable=false)
	private DitTramite ditTramite;
	
	
	//bi-directional one-to-one 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA",updatable=false,insertable=false)
	private DitPersona ditPersona;

	public DitTramitePersonaFisicaPK getId() {
		return id;
	}

	public void setId(DitTramitePersonaFisicaPK id) {
		this.id = id;
	}

	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

    
}