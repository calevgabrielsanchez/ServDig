package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_CONTRO_FLUJO_ANEXOS database table.
 * 
 */
@Entity
@Table(name="FDT_CONTRO_FLUJO_ANEXOS")
public class FdtControFlujoAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtControFlujoAnexoPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FECHACARGA")
	private Date fhFechacarga;

	//bi-directional many-to-one association to FdtEstatusFlujoAnexo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTATUS", nullable=false)
	private FdtEstatusFlujoAnexo fdtEstatusFlujoAnexo;

	//bi-directional many-to-one association to FdtNombreAnexo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ANEXO", nullable=false, insertable=false, updatable=false)
	private FdtNombreAnexo fdtNombreAnexo;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO", nullable=false, insertable=false, updatable=false)
	private FdtAviso fdtAviso;

	//bi-directional many-to-one association to FdtErrorCargaAnexo
	@OneToMany(mappedBy="fdtControFlujoAnexo")
	private List<FdtErrorCargaAnexo> fdtErrorCargaAnexos;

    public FdtControFlujoAnexo() {
    }

	public FdtControFlujoAnexoPK getId() {
		return this.id;
	}

	public void setId(FdtControFlujoAnexoPK id) {
		this.id = id;
	}
	
	public Date getFhFechacarga() {
		return this.fhFechacarga;
	}

	public void setFhFechacarga(Date fhFechacarga) {
		this.fhFechacarga = fhFechacarga;
	}

	public FdtEstatusFlujoAnexo getFdtEstatusFlujoAnexo() {
		return this.fdtEstatusFlujoAnexo;
	}

	public void setFdtEstatusFlujoAnexo(FdtEstatusFlujoAnexo fdtEstatusFlujoAnexo) {
		this.fdtEstatusFlujoAnexo = fdtEstatusFlujoAnexo;
	}
	
	public FdtNombreAnexo getFdtNombreAnexo() {
		return this.fdtNombreAnexo;
	}

	public void setFdtNombreAnexo(FdtNombreAnexo fdtNombreAnexo) {
		this.fdtNombreAnexo = fdtNombreAnexo;
	}
	
	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
	public List<FdtErrorCargaAnexo> getFdtErrorCargaAnexos() {
		return this.fdtErrorCargaAnexos;
	}

	public void setFdtErrorCargaAnexos(List<FdtErrorCargaAnexo> fdtErrorCargaAnexos) {
		this.fdtErrorCargaAnexos = fdtErrorCargaAnexos;
	}
	
}