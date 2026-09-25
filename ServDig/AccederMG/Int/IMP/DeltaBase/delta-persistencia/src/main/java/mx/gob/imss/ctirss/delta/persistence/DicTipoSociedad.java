package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the DIC_TIPO_SOCIEDAD database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_SOCIEDAD")
@OnSearchLlavePrimaria(atributos="cveIdTipoSociedad")
@ComponentComboCampoDescripcion(atributo="desTipoSociedadAbrev")
public class DicTipoSociedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_SOCIEDAD", nullable=false, precision=22)
	private Integer cveIdTipoSociedad;

	@Column(name="DES_TIPO_SOCIEDAD", length=255)
	private String desTipoSociedad;

	@Column(name="DES_TIPO_SOCIEDAD_ABREV", length=100)
	private String desTipoSociedadAbrev;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPersonaMoral
	@OneToMany(mappedBy="dicTipoSociedad")
	private List<DitPersonaMoral> ditPersonaMorals;

    public DicTipoSociedad() {
    }

	public Integer getCveIdTipoSociedad() {
		return this.cveIdTipoSociedad;
	}

	public void setCveIdTipoSociedad(Integer cveIdTipoSociedad) {
		this.cveIdTipoSociedad = cveIdTipoSociedad;
	}

	public String getDesTipoSociedad() {
		return this.desTipoSociedad;
	}

	public void setDesTipoSociedad(String desTipoSociedad) {
		this.desTipoSociedad = desTipoSociedad;
	}

	public String getDesTipoSociedadAbrev() {
		return this.desTipoSociedadAbrev;
	}

	public void setDesTipoSociedadAbrev(String desTipoSociedadAbrev) {
		this.desTipoSociedadAbrev = desTipoSociedadAbrev;
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

	public List<DitPersonaMoral> getDitPersonaMorals() {
		return this.ditPersonaMorals;
	}

	public void setDitPersonaMorals(List<DitPersonaMoral> ditPersonaMorals) {
		this.ditPersonaMorals = ditPersonaMorals;
	}
	
}