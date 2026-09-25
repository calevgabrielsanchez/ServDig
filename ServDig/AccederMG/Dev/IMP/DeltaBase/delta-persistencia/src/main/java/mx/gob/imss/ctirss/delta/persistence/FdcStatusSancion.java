package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDC_STATUS_SANCION database table.
 * 
 */
@Entity
@Table(name="FDC_STATUS_SANCION")
public class FdcStatusSancion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_STATUS", nullable=false, precision=22)
	private long idStatus;

	@Column(name="DESC_STATUS_SANCION", length=50)
	private String descStatusSancion;

	//bi-directional many-to-one association to FdiSancion
	@OneToMany(mappedBy="fdcStatusSancion")
	private List<FdiSancion> fdiSancions;

    public FdcStatusSancion() {
    }

	public long getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
	}

	public String getDescStatusSancion() {
		return this.descStatusSancion;
	}

	public void setDescStatusSancion(String descStatusSancion) {
		this.descStatusSancion = descStatusSancion;
	}

	public List<FdiSancion> getFdiSancions() {
		return this.fdiSancions;
	}

	public void setFdiSancions(List<FdiSancion> fdiSancions) {
		this.fdiSancions = fdiSancions;
	}
	
}