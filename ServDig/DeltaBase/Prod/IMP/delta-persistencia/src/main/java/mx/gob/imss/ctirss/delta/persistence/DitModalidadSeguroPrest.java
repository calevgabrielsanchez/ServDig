package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_MODALIDAD_SEGURO_PREST database table.
 * 
 */
@Entity
@Table(name="DIT_MODALIDAD_SEGURO_PREST")
public class DitModalidadSeguroPrest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MODALIDAD_SEGURO_PREST", nullable=false, precision=22)
	private long cveIdModalidadSeguroPrest;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitModalidadLeySeguro
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD_SEGURO")
	private DitModalidadLeySeguro ditModalidadLeySeguro;

	//bi-directional many-to-one association to DicPrestacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PRESTACION")
	private DicPrestacion dicPrestacion;

	//bi-directional many-to-one association to DitModSegPrestContrib
	@OneToMany(mappedBy="ditModalidadSeguroPrest")
	private List<DitModSegPrestContrib> ditModSegPrestContribs;

	//bi-directional many-to-one association to DitModSegPrestServ
	@OneToMany(mappedBy="ditModalidadSeguroPrest")
	private List<DitModSegPrestServ> ditModSegPrestServs;

    public DitModalidadSeguroPrest() {
    }

	public long getCveIdModalidadSeguroPrest() {
		return this.cveIdModalidadSeguroPrest;
	}

	public void setCveIdModalidadSeguroPrest(long cveIdModalidadSeguroPrest) {
		this.cveIdModalidadSeguroPrest = cveIdModalidadSeguroPrest;
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

	public DitModalidadLeySeguro getDitModalidadLeySeguro() {
		return this.ditModalidadLeySeguro;
	}

	public void setDitModalidadLeySeguro(DitModalidadLeySeguro ditModalidadLeySeguro) {
		this.ditModalidadLeySeguro = ditModalidadLeySeguro;
	}
	
	public DicPrestacion getDicPrestacion() {
		return this.dicPrestacion;
	}

	public void setDicPrestacion(DicPrestacion dicPrestacion) {
		this.dicPrestacion = dicPrestacion;
	}
	
	public List<DitModSegPrestContrib> getDitModSegPrestContribs() {
		return this.ditModSegPrestContribs;
	}

	public void setDitModSegPrestContribs(List<DitModSegPrestContrib> ditModSegPrestContribs) {
		this.ditModSegPrestContribs = ditModSegPrestContribs;
	}
	
	public List<DitModSegPrestServ> getDitModSegPrestServs() {
		return this.ditModSegPrestServs;
	}

	public void setDitModSegPrestServs(List<DitModSegPrestServ> ditModSegPrestServs) {
		this.ditModSegPrestServs = ditModSegPrestServs;
	}
	
}