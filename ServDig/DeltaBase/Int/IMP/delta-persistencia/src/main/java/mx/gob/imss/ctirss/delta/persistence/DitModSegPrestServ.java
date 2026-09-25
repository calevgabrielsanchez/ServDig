package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MOD_SEG_PREST_SERV database table.
 * 
 */
@Entity
@Table(name="DIT_MOD_SEG_PREST_SERV")
public class DitModSegPrestServ implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOD_SEG_PREST_SERV", nullable=false, precision=22)
	private long cveIdModSegPrestServ;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicServicio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SERVICIO")
	private DicServicio dicServicio;

	//bi-directional many-to-one association to DitModalidadSeguroPrest
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD_SEGURO_PREST")
	private DitModalidadSeguroPrest ditModalidadSeguroPrest;

    public DitModSegPrestServ() {
    }

	public long getCveIdModSegPrestServ() {
		return this.cveIdModSegPrestServ;
	}

	public void setCveIdModSegPrestServ(long cveIdModSegPrestServ) {
		this.cveIdModSegPrestServ = cveIdModSegPrestServ;
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

	public DicServicio getDicServicio() {
		return this.dicServicio;
	}

	public void setDicServicio(DicServicio dicServicio) {
		this.dicServicio = dicServicio;
	}
	
	public DitModalidadSeguroPrest getDitModalidadSeguroPrest() {
		return this.ditModalidadSeguroPrest;
	}

	public void setDitModalidadSeguroPrest(DitModalidadSeguroPrest ditModalidadSeguroPrest) {
		this.ditModalidadSeguroPrest = ditModalidadSeguroPrest;
	}
	
}