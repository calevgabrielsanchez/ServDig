package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_COMBUSTIBLE database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_COMBUSTIBLE")
@OnSearchLlavePrimaria(atributos={"cveIdTipoCombustible"})
@ComponentComboCampoDescripcion(atributo="desTipoCombustible")
public class DicTipoCombustible implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_COMBUSTIBLE", nullable=false, precision=22)
	private long cveIdTipoCombustible;

	@Column(name="DES_TIPO_COMBUSTIBLE", length=255)
	private String desTipoCombustible;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitEquipoTransporte
	@OneToMany(mappedBy="dicTipoCombustible")
	private List<DitEquipoTransporte> ditEquipoTransportes;

    public DicTipoCombustible() {
    }

	public long getCveIdTipoCombustible() {
		return this.cveIdTipoCombustible;
	}

	public void setCveIdTipoCombustible(long cveIdTipoCombustible) {
		this.cveIdTipoCombustible = cveIdTipoCombustible;
	}

	public String getDesTipoCombustible() {
		return this.desTipoCombustible;
	}

	public void setDesTipoCombustible(String desTipoCombustible) {
		this.desTipoCombustible = desTipoCombustible;
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

	public List<DitEquipoTransporte> getDitEquipoTransportes() {
		return this.ditEquipoTransportes;
	}

	public void setDitEquipoTransportes(List<DitEquipoTransporte> ditEquipoTransportes) {
		this.ditEquipoTransportes = ditEquipoTransportes;
	}
	
}