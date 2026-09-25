package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CFT_REGISTROSPAT database table.
 * 
 */
@Entity
@Table(name="CFT_REGISTROSPAT")
public class CftRegistrospat implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CftRegistrospatPK id;

	//bi-directional many-to-one association to CftIntegrafiscaliza
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_TPOFISCALIZA", referencedColumnName="CVE_TPOFISCALIZA", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_EXPEDIENTE", referencedColumnName="NUM_EXPEDIENTE", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false)
		})
	private CftIntegrafiscaliza cftIntegrafiscaliza;

    public CftRegistrospat() {
    }

	public CftRegistrospatPK getId() {
		return this.id;
	}

	public void setId(CftRegistrospatPK id) {
		this.id = id;
	}
	
	public CftIntegrafiscaliza getCftIntegrafiscaliza() {
		return this.cftIntegrafiscaliza;
	}

	public void setCftIntegrafiscaliza(CftIntegrafiscaliza cftIntegrafiscaliza) {
		this.cftIntegrafiscaliza = cftIntegrafiscaliza;
	}
	
}