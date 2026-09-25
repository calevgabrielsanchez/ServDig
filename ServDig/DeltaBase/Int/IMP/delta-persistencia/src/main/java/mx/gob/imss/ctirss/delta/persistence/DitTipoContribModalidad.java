package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_TIPO_CONTRIB_MODALIDAD database table.
 * 
 */
@Entity
@Table(name="DIT_TIPO_CONTRIB_MODALIDAD")
public class DitTipoContribModalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_CONTRIB_MODALIDAD", nullable=false, precision=22)
	private long cveIdTipoContribModalidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitModSegPrestContrib
	@OneToMany(mappedBy="ditTipoContribModalidad")
	private List<DitModSegPrestContrib> ditModSegPrestContribs;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@OneToMany(mappedBy="ditTipoContribModalidad")
	private List<DitPatronSujetoObligado> ditPatronSujetoObligados;

	//bi-directional many-to-one association to DicModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DicTipoContribucion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_CONTRIBUCION")
	private DicTipoContribucion dicTipoContribucion;

    public DitTipoContribModalidad() {
    }

	public long getCveIdTipoContribModalidad() {
		return this.cveIdTipoContribModalidad;
	}

	public void setCveIdTipoContribModalidad(long cveIdTipoContribModalidad) {
		this.cveIdTipoContribModalidad = cveIdTipoContribModalidad;
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

	public List<DitModSegPrestContrib> getDitModSegPrestContribs() {
		return this.ditModSegPrestContribs;
	}

	public void setDitModSegPrestContribs(List<DitModSegPrestContrib> ditModSegPrestContribs) {
		this.ditModSegPrestContribs = ditModSegPrestContribs;
	}
	
	public List<DitPatronSujetoObligado> getDitPatronSujetoObligados() {
		return this.ditPatronSujetoObligados;
	}

	public void setDitPatronSujetoObligados(List<DitPatronSujetoObligado> ditPatronSujetoObligados) {
		this.ditPatronSujetoObligados = ditPatronSujetoObligados;
	}
	
	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public DicTipoContribucion getDicTipoContribucion() {
		return this.dicTipoContribucion;
	}

	public void setDicTipoContribucion(DicTipoContribucion dicTipoContribucion) {
		this.dicTipoContribucion = dicTipoContribucion;
	}
	
}