package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_ASIGNACION_SERIES database table.
 * 
 */
@Entity
@Table(name="DIT_ASIGNACION_SERIES")
public class DitAsignacionSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_ASIGNACION_SERIES_CVEIDASIGNACIONSERIES_GENERATOR", sequenceName="SEQ_DITASIGNACIONSERIES")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_ASIGNACION_SERIES_CVEIDASIGNACIONSERIES_GENERATOR")
	@Column(name="CVE_ID_ASIGNACION_SERIES")
	private long cveIdAsignacionSeries;

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
    @ManyToOne
	@JoinColumn(name="CVE_ID_SERIE")
	private DicSeriesNss dicSeriesNss;

	//bi-directional many-to-one association to DicDelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_DELEGACION")
	private DicDelegacion dicDelegacion;

	//bi-directional many-to-one association to DicSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

	public long getCveIdAsignacionSeries() {
		return this.cveIdAsignacionSeries;
	}

	public void setCveIdAsignacionSeries(long cveIdAsignacionSeries) {
		this.cveIdAsignacionSeries = cveIdAsignacionSeries;
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

	public DicSeriesNss getDicSeriesNss() {
		return this.dicSeriesNss;
	}

	public void setDicSeriesNss(DicSeriesNss dicSeriesNss) {
		this.dicSeriesNss = dicSeriesNss;
	}
	
	public DicDelegacion getDicDelegacion() {
		return this.dicDelegacion;
	}

	public void setDicDelegacion(DicDelegacion dicDelegacion) {
		this.dicDelegacion = dicDelegacion;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
}