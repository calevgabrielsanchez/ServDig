package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDI_ASOC_COLEGIOS database table.
 * 
 */
@Entity
@Table(name="FDI_ASOC_COLEGIOS")
public class FdiAsocColegio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ASOCIACION", nullable=false, precision=22)
	private long idAsociacion;

	@Column(name="TX_ASOCIACION", nullable=false, length=100)
	private String txAsociacion;

	//bi-directional many-to-one association to FdiColegio
	@OneToMany(mappedBy="fdiAsocColegio")
	private List<FdiColegio> fdiColegios;

    public FdiAsocColegio() {
    }

	public long getIdAsociacion() {
		return this.idAsociacion;
	}

	public void setIdAsociacion(long idAsociacion) {
		this.idAsociacion = idAsociacion;
	}

	public String getTxAsociacion() {
		return this.txAsociacion;
	}

	public void setTxAsociacion(String txAsociacion) {
		this.txAsociacion = txAsociacion;
	}

	public List<FdiColegio> getFdiColegios() {
		return this.fdiColegios;
	}

	public void setFdiColegios(List<FdiColegio> fdiColegios) {
		this.fdiColegios = fdiColegios;
	}
	
}