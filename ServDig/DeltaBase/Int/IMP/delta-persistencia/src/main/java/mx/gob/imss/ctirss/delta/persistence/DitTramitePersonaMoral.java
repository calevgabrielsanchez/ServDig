package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the DIT_TRAMITE_PERSONA_MORAL database table.
 * 
 */
@Entity
@Table(name="DIT_TRAMITE_PERSONA_MORAL")
public class DitTramitePersonaMoral implements Serializable {
	private static final long serialVersionUID = 1L;


	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "cveIdPersonaMoral", column = @Column(name = "CVE_ID_PERSONA_MORAL", nullable = false)),
		@AttributeOverride(name = "cveIdTramite", column = @Column(name = "CVE_ID_TRAMITE", nullable = false))
	})
	private DitTramitePersonaMoralPK id;
	
	
	//bi-directional one-to-one 
	@ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_MORAL",updatable=false,insertable=false)
	private DitPersonaMoral ditPersonaMoral;

	//bi-directional one-to-one 
	@OneToOne
	@JoinColumn(name="CVE_ID_TRAMITE",updatable=false,insertable=false)
	private DitTramite ditTramite;

	public DitTramitePersonaMoralPK getId() {
		return id;
	}

	public void setId(DitTramitePersonaMoralPK id) {
		this.id = id;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}
	
	
	
	
	
}