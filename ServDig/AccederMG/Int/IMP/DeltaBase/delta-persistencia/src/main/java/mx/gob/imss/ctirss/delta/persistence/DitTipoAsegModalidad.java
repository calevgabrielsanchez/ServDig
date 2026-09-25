package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_TIPO_ASEG_MODALIDAD database table.
 * 
 */
@Entity
@Table(name="DIT_TIPO_ASEG_MODALIDAD")
public class DitTipoAsegModalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_ASEG_MODALIDAD", nullable=false, precision=22)
	private long cveIdTipoAsegModalidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMovasegAjusteAbierto
	@OneToMany(mappedBy="ditTipoAsegModalidad")
	private List<DitMovasegAjusteAbierto> ditMovasegAjusteAbiertos;

	//bi-directional many-to-one association to DitMovtoAsegAbierto
	@OneToMany(mappedBy="ditTipoAsegModalidad")
	private List<DitMovtoAsegAbierto> ditMovtoAsegAbiertos;

	//bi-directional many-to-one association to DicModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DicTipoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_ASEGURADO")
	private DicTipoAsegurado dicTipoAsegurado;

    public DitTipoAsegModalidad() {
    }

	public long getCveIdTipoAsegModalidad() {
		return this.cveIdTipoAsegModalidad;
	}

	public void setCveIdTipoAsegModalidad(long cveIdTipoAsegModalidad) {
		this.cveIdTipoAsegModalidad = cveIdTipoAsegModalidad;
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

	public List<DitMovasegAjusteAbierto> getDitMovasegAjusteAbiertos() {
		return this.ditMovasegAjusteAbiertos;
	}

	public void setDitMovasegAjusteAbiertos(List<DitMovasegAjusteAbierto> ditMovasegAjusteAbiertos) {
		this.ditMovasegAjusteAbiertos = ditMovasegAjusteAbiertos;
	}
	
	public List<DitMovtoAsegAbierto> getDitMovtoAsegAbiertos() {
		return this.ditMovtoAsegAbiertos;
	}

	public void setDitMovtoAsegAbiertos(List<DitMovtoAsegAbierto> ditMovtoAsegAbiertos) {
		this.ditMovtoAsegAbiertos = ditMovtoAsegAbiertos;
	}
	
	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public DicTipoAsegurado getDicTipoAsegurado() {
		return this.dicTipoAsegurado;
	}

	public void setDicTipoAsegurado(DicTipoAsegurado dicTipoAsegurado) {
		this.dicTipoAsegurado = dicTipoAsegurado;
	}
	
}