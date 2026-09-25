package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IDC_ESTATUS_RELACION database table.
 * 
 */
@Entity
@Table(name="IDC_ESTATUS_RELACION")
public class IdcEstatusRelacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTATUS_RELACION")
	private long cveEstatusRelacion;

	@Column(name="NUM_ESTATUS_RELACION")
	private BigDecimal numEstatusRelacion;

	@Column(name="REF_ESTATUS_RELACION")
	private String refEstatusRelacion;

	//bi-directional many-to-one association to IdrBloqueRegistro
	@OneToMany(mappedBy="idcEstatusRelacion")
	private List<IdrBloqueRegistro> idrBloqueRegistros;

	//bi-directional many-to-one association to IdtRepresentado
	@OneToMany(mappedBy="idcEstatusRelacion")
	private List<IdtRepresentado> idtRepresentados;

    public IdcEstatusRelacion() {
    }

	public long getCveEstatusRelacion() {
		return this.cveEstatusRelacion;
	}

	public void setCveEstatusRelacion(long cveEstatusRelacion) {
		this.cveEstatusRelacion = cveEstatusRelacion;
	}

	public BigDecimal getNumEstatusRelacion() {
		return this.numEstatusRelacion;
	}

	public void setNumEstatusRelacion(BigDecimal numEstatusRelacion) {
		this.numEstatusRelacion = numEstatusRelacion;
	}

	public String getRefEstatusRelacion() {
		return this.refEstatusRelacion;
	}

	public void setRefEstatusRelacion(String refEstatusRelacion) {
		this.refEstatusRelacion = refEstatusRelacion;
	}

	public List<IdrBloqueRegistro> getIdrBloqueRegistros() {
		return this.idrBloqueRegistros;
	}

	public void setIdrBloqueRegistros(List<IdrBloqueRegistro> idrBloqueRegistros) {
		this.idrBloqueRegistros = idrBloqueRegistros;
	}
	
	public List<IdtRepresentado> getIdtRepresentados() {
		return this.idtRepresentados;
	}

	public void setIdtRepresentados(List<IdtRepresentado> idtRepresentados) {
		this.idtRepresentados = idtRepresentados;
	}
	
}