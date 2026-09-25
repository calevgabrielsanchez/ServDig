package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_PAGO_MODALIDAD database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PAGO_MODALIDAD")
public class DicTipoPagoModalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PAGO_MODALIDAD", nullable=false, precision=22)
	private long cveIdTipoPagoModalidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DicTipoPago
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_PAGO")
	private DicTipoPago dicTipoPago;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@OneToMany(mappedBy="dicTipoPagoModalidad")
	private List<DitPatronSujetoObligado> ditPatronSujetoObligados;

    public DicTipoPagoModalidad() {
    }

	public long getCveIdTipoPagoModalidad() {
		return this.cveIdTipoPagoModalidad;
	}

	public void setCveIdTipoPagoModalidad(long cveIdTipoPagoModalidad) {
		this.cveIdTipoPagoModalidad = cveIdTipoPagoModalidad;
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

	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public DicTipoPago getDicTipoPago() {
		return this.dicTipoPago;
	}

	public void setDicTipoPago(DicTipoPago dicTipoPago) {
		this.dicTipoPago = dicTipoPago;
	}
	
	public List<DitPatronSujetoObligado> getDitPatronSujetoObligados() {
		return this.ditPatronSujetoObligados;
	}

	public void setDitPatronSujetoObligados(List<DitPatronSujetoObligado> ditPatronSujetoObligados) {
		this.ditPatronSujetoObligados = ditPatronSujetoObligados;
	}
	
}