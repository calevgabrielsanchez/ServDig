package mx.imss.ctirss.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.model.DltDenuncia;

/**
 * The primary key class for the DLT_MOTIVODENUNCIA database table.
 * 
 */
@Embeddable
public class AbstractDltMotivodenunciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_FOLIODENUNCIA",nullable=false)
	private Long cveFoliodenuncia;

	@Column(name="CVE_MOTIVODENUNCIA",nullable=false)
	private Long cveMotivodenuncia;
	
	@Transient
	private String valorInicial;
	
	@Transient
	private String valorFinal;

    public AbstractDltMotivodenunciaPK() {
    }
        
	public Long getCveFoliodenuncia() {
		return this.cveFoliodenuncia;
	}
    
	public void setCveFoliodenuncia(Long cveFoliodenuncia) {
		this.cveFoliodenuncia = cveFoliodenuncia;
	}
	
	public Long getCveMotivodenuncia() {
		return this.cveMotivodenuncia;
	}
	
	public void setCveMotivodenuncia(Long cveMotivodenuncia) {
		this.cveMotivodenuncia = cveMotivodenuncia;
	}
	
	

	public String getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(String valorInicial) {
		this.valorInicial = valorInicial;
	}

	public String getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(String valorFinal) {
		this.valorFinal = valorFinal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractDltMotivodenunciaPK)) {
			return false;
		}
		AbstractDltMotivodenunciaPK castOther = (AbstractDltMotivodenunciaPK)other;
		return 
			(this.cveFoliodenuncia == castOther.cveFoliodenuncia)
			&& (this.cveMotivodenuncia == castOther.cveMotivodenuncia);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveFoliodenuncia ^ (this.cveFoliodenuncia >>> 32)));
		hash = hash * prime + ((int) (this.cveMotivodenuncia ^ (this.cveMotivodenuncia >>> 32)));
		
		return hash;
    }
}