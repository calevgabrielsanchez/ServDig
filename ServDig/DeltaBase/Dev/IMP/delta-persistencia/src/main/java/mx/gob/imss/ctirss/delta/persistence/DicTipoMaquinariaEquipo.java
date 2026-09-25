package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_MAQUINARIA_EQUIPO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_MAQUINARIA_EQUIPO")
@OnSearchLlavePrimaria(atributos={"cveIdTipoMaquinariaEquipo"})
@ComponentComboCampoDescripcion(atributo="desTipoMaquinariaEquipo")
public class DicTipoMaquinariaEquipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_MAQUINARIA_EQUIPO", nullable=false, precision=22)
	private long cveIdTipoMaquinariaEquipo;

	@Column(name="DES_TIPO_MAQUINARIA_EQUIPO", length=255)
	private String desTipoMaquinariaEquipo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMaquinariaEquipo
	@OneToMany(mappedBy="dicTipoMaquinariaEquipo")
	private List<DitMaquinariaEquipo> ditMaquinariaEquipos;

    public DicTipoMaquinariaEquipo() {
    }

	public long getCveIdTipoMaquinariaEquipo() {
		return this.cveIdTipoMaquinariaEquipo;
	}

	public void setCveIdTipoMaquinariaEquipo(long cveIdTipoMaquinariaEquipo) {
		this.cveIdTipoMaquinariaEquipo = cveIdTipoMaquinariaEquipo;
	}

	public String getDesTipoMaquinariaEquipo() {
		return this.desTipoMaquinariaEquipo;
	}

	public void setDesTipoMaquinariaEquipo(String desTipoMaquinariaEquipo) {
		this.desTipoMaquinariaEquipo = desTipoMaquinariaEquipo;
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

	public List<DitMaquinariaEquipo> getDitMaquinariaEquipos() {
		return this.ditMaquinariaEquipos;
	}

	public void setDitMaquinariaEquipos(List<DitMaquinariaEquipo> ditMaquinariaEquipos) {
		this.ditMaquinariaEquipos = ditMaquinariaEquipos;
	}
	
}