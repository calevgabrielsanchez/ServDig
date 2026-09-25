package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MOD_SEG_PREST_CONTRIB database table.
 * 
 */
@Entity
@Table(name="DIT_MOD_SEG_PREST_CONTRIB")
public class DitModSegPrestContrib implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOD_SEG_PREST_CONTRIB", nullable=false, precision=22)
	private long cveIdModSegPrestContrib;

	@Column(name="CUOTA_ESTADO", precision=15, scale=10)
	private BigDecimal cuotaEstado;

	@Column(name="CUOTA_PATRONAL", precision=15, scale=10)
	private BigDecimal cuotaPatronal;

	@Column(name="CUOTA_TRABAJADOR", precision=15, scale=10)
	private BigDecimal cuotaTrabajador;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTipoContribModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_CONTRIB_MODALIDAD")
	private DitTipoContribModalidad ditTipoContribModalidad;

	//bi-directional many-to-one association to DitModalidadSeguroPrest
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD_SEGURO_PREST")
	private DitModalidadSeguroPrest ditModalidadSeguroPrest;

    public DitModSegPrestContrib() {
    }

	public long getCveIdModSegPrestContrib() {
		return this.cveIdModSegPrestContrib;
	}

	public void setCveIdModSegPrestContrib(long cveIdModSegPrestContrib) {
		this.cveIdModSegPrestContrib = cveIdModSegPrestContrib;
	}

	public BigDecimal getCuotaEstado() {
		return this.cuotaEstado;
	}

	public void setCuotaEstado(BigDecimal cuotaEstado) {
		this.cuotaEstado = cuotaEstado;
	}

	public BigDecimal getCuotaPatronal() {
		return this.cuotaPatronal;
	}

	public void setCuotaPatronal(BigDecimal cuotaPatronal) {
		this.cuotaPatronal = cuotaPatronal;
	}

	public BigDecimal getCuotaTrabajador() {
		return this.cuotaTrabajador;
	}

	public void setCuotaTrabajador(BigDecimal cuotaTrabajador) {
		this.cuotaTrabajador = cuotaTrabajador;
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

	public DitTipoContribModalidad getDitTipoContribModalidad() {
		return this.ditTipoContribModalidad;
	}

	public void setDitTipoContribModalidad(DitTipoContribModalidad ditTipoContribModalidad) {
		this.ditTipoContribModalidad = ditTipoContribModalidad;
	}
	
	public DitModalidadSeguroPrest getDitModalidadSeguroPrest() {
		return this.ditModalidadSeguroPrest;
	}

	public void setDitModalidadSeguroPrest(DitModalidadSeguroPrest ditModalidadSeguroPrest) {
		this.ditModalidadSeguroPrest = ditModalidadSeguroPrest;
	}
	
}