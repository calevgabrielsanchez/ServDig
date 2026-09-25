package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_DESCUENTO_RISS")
public class DicDescuentoRiss implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DESCUENTO_RISS", nullable=false, precision=22)
	private Long cveIdDescuentoRiss;
	
	@Column(name="NUM_ANIO")
	private int numAnio;
	
	@Column(name="NUM_PORCENTAJE", precision=5, scale=2)
	private BigDecimal numPorcentaje;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;	  
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	
	public DicDescuentoRiss(){		
	}
	
	public DicDescuentoRiss(int numAnio, BigDecimal numPorcentaje,
			Date fecRegistroAlta, Date fecRegistroBaja,
			Date fecRegistroActualizado) {
		super();
		this.numAnio = numAnio;
		this.numPorcentaje = numPorcentaje;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	
	public Long getCveIdDescuentoRiss() {
		return cveIdDescuentoRiss;
	}

	public void setCveIdDescuentoRiss(Long cveIdDescuentoRiss) {
		this.cveIdDescuentoRiss = cveIdDescuentoRiss;
	}

	public int getNumAnio() {
		return numAnio;
	}

	public void setNumAnio(int numAnio) {
		this.numAnio = numAnio;
	}

	public BigDecimal getNumPorcentaje() {
		return numPorcentaje;
	}

	public void setNumPorcentaje(BigDecimal numPorcentaje) {
		this.numPorcentaje = numPorcentaje;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
		
}
