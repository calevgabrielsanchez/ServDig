package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDC_SANCION database table.
 * 
 */
@Entity
@Table(name="FDC_SANCION")
public class FdcSancion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CLASE_SANCION", nullable=false, precision=22)
	private long idClaseSancion;

	@Column(length=20)
	private String sancion;

	//bi-directional many-to-one association to FdiTpSancion
	@OneToMany(mappedBy="fdcSancion")
	private List<FdiTpSancion> fdiTpSancions;

    public FdcSancion() {
    }

	public long getIdClaseSancion() {
		return this.idClaseSancion;
	}

	public void setIdClaseSancion(long idClaseSancion) {
		this.idClaseSancion = idClaseSancion;
	}

	public String getSancion() {
		return this.sancion;
	}

	public void setSancion(String sancion) {
		this.sancion = sancion;
	}

	public List<FdiTpSancion> getFdiTpSancions() {
		return this.fdiTpSancions;
	}

	public void setFdiTpSancions(List<FdiTpSancion> fdiTpSancions) {
		this.fdiTpSancions = fdiTpSancions;
	}
	
}