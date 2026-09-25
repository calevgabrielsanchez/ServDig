package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_PAGO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PAGO")
public class DicTipoPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PAGO", nullable=false, precision=22)
	private long cveIdTipoPago;

	@Column(name="DES_TIPO_PAGO", length=100)
	private String desTipoPago;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_TIPO_PAGO", length=50)
	private String numTipoPago;

	//bi-directional many-to-one association to DicTipoPagoModalidad
	@OneToMany(mappedBy="dicTipoPago")
	private List<DicTipoPagoModalidad> dicTipoPagoModalidads;

    public DicTipoPago() {
    }

	public long getCveIdTipoPago() {
		return this.cveIdTipoPago;
	}

	public void setCveIdTipoPago(long cveIdTipoPago) {
		this.cveIdTipoPago = cveIdTipoPago;
	}

	public String getDesTipoPago() {
		return this.desTipoPago;
	}

	public void setDesTipoPago(String desTipoPago) {
		this.desTipoPago = desTipoPago;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getNumTipoPago() {
		return this.numTipoPago;
	}

	public void setNumTipoPago(String numTipoPago) {
		this.numTipoPago = numTipoPago;
	}

	public List<DicTipoPagoModalidad> getDicTipoPagoModalidads() {
		return this.dicTipoPagoModalidads;
	}

	public void setDicTipoPagoModalidads(List<DicTipoPagoModalidad> dicTipoPagoModalidads) {
		this.dicTipoPagoModalidads = dicTipoPagoModalidads;
	}
	
}