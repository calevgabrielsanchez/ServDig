package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MUNICIPIO_SUBDELEGACION database table.
 * 
 */
@Entity
@Table(name="DIT_MUNICIPIO_SUBDELEGACION")
public class DitMunicipioSubdelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MUNICIPIO_SUBDELEGACION", nullable=false, precision=22)
	private long cveIdMunicipioSubdelegacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

	//bi-directional many-to-one association to DicMunicipioImss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MUNICIPIO_IMSS")
	private DicMunicipioImss dicMunicipioImss;

    public DitMunicipioSubdelegacion() {
    }

	public long getCveIdMunicipioSubdelegacion() {
		return this.cveIdMunicipioSubdelegacion;
	}

	public void setCveIdMunicipioSubdelegacion(long cveIdMunicipioSubdelegacion) {
		this.cveIdMunicipioSubdelegacion = cveIdMunicipioSubdelegacion;
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

	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
	public DicMunicipioImss getDicMunicipioImss() {
		return this.dicMunicipioImss;
	}

	public void setDicMunicipioImss(DicMunicipioImss dicMunicipioImss) {
		this.dicMunicipioImss = dicMunicipioImss;
	}
	
}