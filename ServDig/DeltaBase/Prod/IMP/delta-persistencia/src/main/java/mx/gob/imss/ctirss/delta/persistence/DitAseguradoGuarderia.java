package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_ASEGURADO_GUARDERIA database table.
 * 
 */
@Entity
@Table(name="DIT_ASEGURADO_GUARDERIA")
public class DitAseguradoGuarderia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ASEGURADO_GUARDERIA", nullable=false, precision=22)
	private long cveIdAseguradoGuarderia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_SERV")
	private Date fecFinServ;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_SERV")
	private Date fecIniServ;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicGuarderia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_GUARDERIA")
	private DicGuarderia dicGuarderia;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

    public DitAseguradoGuarderia() {
    }

	public long getCveIdAseguradoGuarderia() {
		return this.cveIdAseguradoGuarderia;
	}

	public void setCveIdAseguradoGuarderia(long cveIdAseguradoGuarderia) {
		this.cveIdAseguradoGuarderia = cveIdAseguradoGuarderia;
	}

	public Date getFecFinServ() {
		return this.fecFinServ;
	}

	public void setFecFinServ(Date fecFinServ) {
		this.fecFinServ = fecFinServ;
	}

	public Date getFecIniServ() {
		return this.fecIniServ;
	}

	public void setFecIniServ(Date fecIniServ) {
		this.fecIniServ = fecIniServ;
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

	public DicGuarderia getDicGuarderia() {
		return this.dicGuarderia;
	}

	public void setDicGuarderia(DicGuarderia dicGuarderia) {
		this.dicGuarderia = dicGuarderia;
	}
	
	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
}