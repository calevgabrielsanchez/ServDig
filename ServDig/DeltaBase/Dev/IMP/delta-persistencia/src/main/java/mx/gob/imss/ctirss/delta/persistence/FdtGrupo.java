package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDT_GRUPOS database table.
 * 
 */
@Entity
@Table(name="FDT_GRUPOS")
public class FdtGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CV_GRUPO", nullable=false, precision=22)
	private long cvGrupo;

	@Column(name="TX_NOMBRE", nullable=false, length=250)
	private String txNombre;

	//bi-directional many-to-many association to FdtPeriodo
	@ManyToMany(mappedBy="fdtGrupos")
	private List<FdtPeriodo> fdtPeriodos;

    public FdtGrupo() {
    }

	public long getCvGrupo() {
		return this.cvGrupo;
	}

	public void setCvGrupo(long cvGrupo) {
		this.cvGrupo = cvGrupo;
	}

	public String getTxNombre() {
		return this.txNombre;
	}

	public void setTxNombre(String txNombre) {
		this.txNombre = txNombre;
	}

	public List<FdtPeriodo> getFdtPeriodos() {
		return this.fdtPeriodos;
	}

	public void setFdtPeriodos(List<FdtPeriodo> fdtPeriodos) {
		this.fdtPeriodos = fdtPeriodos;
	}
	
}