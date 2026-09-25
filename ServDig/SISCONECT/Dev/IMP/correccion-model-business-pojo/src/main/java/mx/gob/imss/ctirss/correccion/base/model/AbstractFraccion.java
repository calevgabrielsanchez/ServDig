package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.Clase;
import mx.gob.imss.ctirss.correccion.model.Grupo;


/**
 * The persistent class for the DIC_FRACCION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractFraccion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_FRACCION_CVEIDFRACCION_GENERATOR", sequenceName="SEQ_DIC_FRACCION")
	@GeneratedValue(generator="DIC_FRACCION_CVEIDFRACCION_GENERATOR")
	@Column(name="CVE_ID_FRACCION")
	private long cveIdFraccion;

	@Column(name="DES_ACTIVIDAD")
	private String desActividad;

	@Column(name="DES_FRACCION")
	private String desFraccion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_FRACCION")
	private String numFraccion;

	//bi-directional many-to-one association to Clase
    @ManyToOne
	@JoinColumn(name="CVE_ID_CLASE")
	private Clase dicClase;

	//bi-directional many-to-one association to Grupo
    @ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO")
	private Grupo dicGrupo;

    public AbstractFraccion() {
    }

	public long getCveIdFraccion() {
		return this.cveIdFraccion;
	}

	public void setCveIdFraccion(long cveIdFraccion) {
		this.cveIdFraccion = cveIdFraccion;
	}

	public String getDesActividad() {
		return this.desActividad;
	}

	public void setDesActividad(String desActividad) {
		this.desActividad = desActividad;
	}

	public String getDesFraccion() {
		return this.desFraccion;
	}

	public void setDesFraccion(String desFraccion) {
		this.desFraccion = desFraccion;
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

	public String getNumFraccion() {
		return this.numFraccion;
	}

	public void setNumFraccion(String numFraccion) {
		this.numFraccion = numFraccion;
	}

	public Clase getDicClase() {
		return this.dicClase;
	}

	public void setDicClase(Clase dicClase) {
		this.dicClase = dicClase;
	}
	
	public Grupo getDicGrupo() {
		return this.dicGrupo;
	}

	public void setDicGrupo(Grupo dicGrupo) {
		this.dicGrupo = dicGrupo;
	}
	
}