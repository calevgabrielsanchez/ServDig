package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the DIC_TIPO_SERIE database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_SERIE")
@OnSearchLlavePrimaria(atributos="cveIdTipoSerie")
@ComponentComboCampoDescripcion(atributo = "desTipoSerie")
public class DicTipoSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_TIPO_SERIE_CVEIDTIPOSERIE_GENERATOR", sequenceName="SEQ_DICTIPOSERIE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_TIPO_SERIE_CVEIDTIPOSERIE_GENERATOR")
	@Column(name="CVE_ID_TIPO_SERIE")
	private long cveIdTipoSerie;

	@Column(name="DES_TIPO_SERIE")
	private String desTipoSerie;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicSeriesNss
	@OneToMany(mappedBy="dicTipoSerie")
	private List<DicSeriesNss> dicSeriesNsses;

    public DicTipoSerie() {
    }

	public long getCveIdTipoSerie() {
		return this.cveIdTipoSerie;
	}

	public void setCveIdTipoSerie(long cveIdTipoSerie) {
		this.cveIdTipoSerie = cveIdTipoSerie;
	}

	public String getDesTipoSerie() {
		return this.desTipoSerie;
	}

	public void setDesTipoSerie(String desTipoSerie) {
		this.desTipoSerie = desTipoSerie;
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

	public List<DicSeriesNss> getDicSeriesNsses() {
		return this.dicSeriesNsses;
	}

	public void setDicSeriesNsses(List<DicSeriesNss> dicSeriesNsses) {
		this.dicSeriesNsses = dicSeriesNsses;
	}
	
}