package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_PAIS database table.
 * 
 */
@Entity
@Table(name="DIC_PAIS")
@OnSearchLlavePrimaria(atributos={"cveIdPais"})
@ComponentComboCampoDescripcion(atributo="desNacionalidad")
public class DicPai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PAIS", nullable=false, precision=22)
	private long cveIdPais;

	@Column(name="DES_NACIONALIDAD", length=255)
	private String desNacionalidad;

	@Column(name="DES_PAIS", length=255)
	private String desPais;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(length=50)
	private String siglas;

	//bi-directional many-to-one association to DgCatEstado
	@OneToMany(mappedBy="dicPai")
	private List<DgCatEstado> dgCatEstados;

	//bi-directional many-to-one association to DitPersona
	@OneToMany(mappedBy="dicPai")
	private List<DitPersona> ditPersonas;

    public DicPai() {
    }

	public long getCveIdPais() {
		return this.cveIdPais;
	}

	public void setCveIdPais(long cveIdPais) {
		this.cveIdPais = cveIdPais;
	}

	public String getDesNacionalidad() {
		return this.desNacionalidad;
	}

	public void setDesNacionalidad(String desNacionalidad) {
		this.desNacionalidad = desNacionalidad;
	}

	public String getDesPais() {
		return this.desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
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

	public String getSiglas() {
		return this.siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public List<DgCatEstado> getDgCatEstados() {
		return this.dgCatEstados;
	}

	public void setDgCatEstados(List<DgCatEstado> dgCatEstados) {
		this.dgCatEstados = dgCatEstados;
	}
	
	public List<DitPersona> getDitPersonas() {
		return this.ditPersonas;
	}

	public void setDitPersonas(List<DitPersona> ditPersonas) {
		this.ditPersonas = ditPersonas;
	}
	
}