package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDI_TP_SANCION database table.
 * 
 */
@Entity
@Table(name="FDI_TP_SANCION")
public class FdiTpSancion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TPO_SANCION", nullable=false, precision=22)
	private long idTpoSancion;

	@Column(name="DURACION_SANCION", precision=22)
	private BigDecimal duracionSancion;

	@Column(name="ID_PORDICTAMEN", precision=22)
	private BigDecimal idPordictamen;

	@Column(name="TX_DESC_CORTA_SANCION", length=100)
	private String txDescCortaSancion;

	@Column(name="TX_DESC_SANCION", nullable=false, length=220)
	private String txDescSancion;

	//bi-directional many-to-one association to FdiSancion
	@OneToMany(mappedBy="fdiTpSancion")
	private List<FdiSancion> fdiSancions;

	//bi-directional many-to-one association to FdcSancion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CLASE_SANCION")
	private FdcSancion fdcSancion;

    public FdiTpSancion() {
    }

	public long getIdTpoSancion() {
		return this.idTpoSancion;
	}

	public void setIdTpoSancion(long idTpoSancion) {
		this.idTpoSancion = idTpoSancion;
	}

	public BigDecimal getDuracionSancion() {
		return this.duracionSancion;
	}

	public void setDuracionSancion(BigDecimal duracionSancion) {
		this.duracionSancion = duracionSancion;
	}

	public BigDecimal getIdPordictamen() {
		return this.idPordictamen;
	}

	public void setIdPordictamen(BigDecimal idPordictamen) {
		this.idPordictamen = idPordictamen;
	}

	public String getTxDescCortaSancion() {
		return this.txDescCortaSancion;
	}

	public void setTxDescCortaSancion(String txDescCortaSancion) {
		this.txDescCortaSancion = txDescCortaSancion;
	}

	public String getTxDescSancion() {
		return this.txDescSancion;
	}

	public void setTxDescSancion(String txDescSancion) {
		this.txDescSancion = txDescSancion;
	}

	public List<FdiSancion> getFdiSancions() {
		return this.fdiSancions;
	}

	public void setFdiSancions(List<FdiSancion> fdiSancions) {
		this.fdiSancions = fdiSancions;
	}
	
	public FdcSancion getFdcSancion() {
		return this.fdcSancion;
	}

	public void setFdcSancion(FdcSancion fdcSancion) {
		this.fdcSancion = fdcSancion;
	}
	
}