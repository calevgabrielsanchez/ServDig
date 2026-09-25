package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_FRACCION database table.
 * 
 */
@Entity
@Table(name="DIC_FRACCION")
public class DicFraccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_FRACCION", nullable=false, precision=22)
	private long cveIdFraccion;

	@Column(name="DES_ACTIVIDAD", length=3000)
	private String desActividad;

	@Column(name="DES_FRACCION", length=500)
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

	@Column(name="NUM_FRACCION", length=255)
	private String numFraccion;

	//bi-directional many-to-one association to DicGrupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_GRUPO")
	private DicGrupo dicGrupo;

	//bi-directional many-to-one association to DicClase
//    @ManyToOne
//	@JoinColumn(name="CVE_ID_CLASE")
//	private DicClase dicClase;
	
	//bi-directional many-to-one association to DitClasificacion
	
	//bi-directional many-to-one association to DicFraccionClase
	@OneToMany(mappedBy="dicFraccion")
	private List<DicFraccionClase> dicFraccions;


    public DicFraccion() {
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

	public DicGrupo getDicGrupo() {
		return this.dicGrupo;
	}

	public void setDicGrupo(DicGrupo dicGrupo) {
		this.dicGrupo = dicGrupo;
	}

	public List<DicFraccionClase> getDicFraccionClases() {
		return dicFraccions;
	}

	public void setDicFraccionClases(List<DicFraccionClase> dicFraccionClases) {
		this.dicFraccions = dicFraccionClases;
	}
	
	
	
//	public List<DitClasificacion> getDitClasificacions() {
//		return this.ditClasificacions;
//	}
//
//	public void setDitClasificacions(List<DitClasificacion> ditClasificacions) {
//		this.ditClasificacions = ditClasificacions;
//	}

//	public DicClase getDicClase() {
//		return dicClase;
//	}
//
//	public void setDicClase(DicClase dicClase) {
//		this.dicClase = dicClase;
//	}
	
}