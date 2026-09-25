package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CFC_FISCALIZA database table.
 * 
 */
@Entity
@Table(name="CFC_FISCALIZA")
public class CfcFiscaliza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_FISCALIZA", nullable=false, precision=22)
	private long cveFiscaliza;

	@Column(name="DESC_FISCALIZA", length=20)
	private String descFiscaliza;

	//bi-directional many-to-one association to CfcTpofiscaliza
	@OneToMany(mappedBy="cfcFiscaliza")
	private List<CfcTpofiscaliza> cfcTpofiscalizas;

	//bi-directional many-to-one association to CfcUsuarioint
	@OneToMany(mappedBy="cfcFiscaliza")
	private List<CfcUsuarioint> cfcUsuarioints;

    public CfcFiscaliza() {
    }

	public long getCveFiscaliza() {
		return this.cveFiscaliza;
	}

	public void setCveFiscaliza(long cveFiscaliza) {
		this.cveFiscaliza = cveFiscaliza;
	}

	public String getDescFiscaliza() {
		return this.descFiscaliza;
	}

	public void setDescFiscaliza(String descFiscaliza) {
		this.descFiscaliza = descFiscaliza;
	}

	public List<CfcTpofiscaliza> getCfcTpofiscalizas() {
		return this.cfcTpofiscalizas;
	}

	public void setCfcTpofiscalizas(List<CfcTpofiscaliza> cfcTpofiscalizas) {
		this.cfcTpofiscalizas = cfcTpofiscalizas;
	}
	
	public List<CfcUsuarioint> getCfcUsuarioints() {
		return this.cfcUsuarioints;
	}

	public void setCfcUsuarioints(List<CfcUsuarioint> cfcUsuarioints) {
		this.cfcUsuarioints = cfcUsuarioints;
	}
	
}