package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_PORCENTAJES_CEDULA database table.
 * 
 */
@Entity
@Table(name="FDT_PORCENTAJES_CEDULA")
public class FdtPorcentajesCedula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CAMBIO", nullable=false, precision=22)
	private long idCambio;

    @Temporal( TemporalType.DATE)
	@Column(name="HF_CAMBIO")
	private Date hfCambio;

	@Column(name="PC_RAZONABILIDAD_1_10", precision=22)
	private BigDecimal pcRazonabilidad110;

	@Column(name="PC_RAZONABILIDAD_101_300", precision=22)
	private BigDecimal pcRazonabilidad101300;

	@Column(name="PC_RAZONABILIDAD_11_50", precision=22)
	private BigDecimal pcRazonabilidad1150;

	@Column(name="PC_RAZONABILIDAD_301_500", precision=22)
	private BigDecimal pcRazonabilidad301500;

	@Column(name="PC_RAZONABILIDAD_501_MAS", precision=22)
	private BigDecimal pcRazonabilidad501Mas;

	@Column(name="PC_RAZONABILIDAD_51_100", precision=22)
	private BigDecimal pcRazonabilidad51100;

    public FdtPorcentajesCedula() {
    }

	public long getIdCambio() {
		return this.idCambio;
	}

	public void setIdCambio(long idCambio) {
		this.idCambio = idCambio;
	}

	public Date getHfCambio() {
		return this.hfCambio;
	}

	public void setHfCambio(Date hfCambio) {
		this.hfCambio = hfCambio;
	}

	public BigDecimal getPcRazonabilidad110() {
		return this.pcRazonabilidad110;
	}

	public void setPcRazonabilidad110(BigDecimal pcRazonabilidad110) {
		this.pcRazonabilidad110 = pcRazonabilidad110;
	}

	public BigDecimal getPcRazonabilidad101300() {
		return this.pcRazonabilidad101300;
	}

	public void setPcRazonabilidad101300(BigDecimal pcRazonabilidad101300) {
		this.pcRazonabilidad101300 = pcRazonabilidad101300;
	}

	public BigDecimal getPcRazonabilidad1150() {
		return this.pcRazonabilidad1150;
	}

	public void setPcRazonabilidad1150(BigDecimal pcRazonabilidad1150) {
		this.pcRazonabilidad1150 = pcRazonabilidad1150;
	}

	public BigDecimal getPcRazonabilidad301500() {
		return this.pcRazonabilidad301500;
	}

	public void setPcRazonabilidad301500(BigDecimal pcRazonabilidad301500) {
		this.pcRazonabilidad301500 = pcRazonabilidad301500;
	}

	public BigDecimal getPcRazonabilidad501Mas() {
		return this.pcRazonabilidad501Mas;
	}

	public void setPcRazonabilidad501Mas(BigDecimal pcRazonabilidad501Mas) {
		this.pcRazonabilidad501Mas = pcRazonabilidad501Mas;
	}

	public BigDecimal getPcRazonabilidad51100() {
		return this.pcRazonabilidad51100;
	}

	public void setPcRazonabilidad51100(BigDecimal pcRazonabilidad51100) {
		this.pcRazonabilidad51100 = pcRazonabilidad51100;
	}

}