package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the IETC_ESTATUS database table.
 * 
 */
@Entity
@Table(name="IETC_ESTATUS")
public class IetcEstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTATUS", nullable=false, precision=22)
	private long cveEstatus;

	@Column(name="DESC_ESTATUS", length=50)
	private String descEstatus;

	//bi-directional many-to-one association to IetcRegistrosContador
	@OneToMany(mappedBy="ietcEstatus")
	private List<IetcRegistrosContador> ietcRegistrosContadors;

    public IetcEstatus() {
    }

	public long getCveEstatus() {
		return this.cveEstatus;
	}

	public void setCveEstatus(long cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public String getDescEstatus() {
		return this.descEstatus;
	}

	public void setDescEstatus(String descEstatus) {
		this.descEstatus = descEstatus;
	}

	public List<IetcRegistrosContador> getIetcRegistrosContadors() {
		return this.ietcRegistrosContadors;
	}

	public void setIetcRegistrosContadors(List<IetcRegistrosContador> ietcRegistrosContadors) {
		this.ietcRegistrosContadors = ietcRegistrosContadors;
	}
	
}