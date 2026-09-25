package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_GUARDERIA database table.
 * 
 */
@Entity
@Table(name="DIC_GUARDERIA")
public class DicGuarderia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_GUARDERIA", nullable=false, precision=22)
	private long cveIdGuarderia;

	@Column(name="CVE_GUARDERIA", length=50)
	private String cveGuarderia;

	@Column(name="DES_GUARDERIA", length=100)
	private String desGuarderia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicClavePresupuestal
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CLAVE_PRESUPUESTAL")
	private DicClavePresupuestal dicClavePresupuestal;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

	//bi-directional many-to-one association to DitAseguradoGuarderia
	@OneToMany(mappedBy="dicGuarderia")
	private List<DitAseguradoGuarderia> ditAseguradoGuarderias;

    public DicGuarderia() {
    }

	public long getCveIdGuarderia() {
		return this.cveIdGuarderia;
	}

	public void setCveIdGuarderia(long cveIdGuarderia) {
		this.cveIdGuarderia = cveIdGuarderia;
	}

	public String getCveGuarderia() {
		return this.cveGuarderia;
	}

	public void setCveGuarderia(String cveGuarderia) {
		this.cveGuarderia = cveGuarderia;
	}

	public String getDesGuarderia() {
		return this.desGuarderia;
	}

	public void setDesGuarderia(String desGuarderia) {
		this.desGuarderia = desGuarderia;
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

	public DicClavePresupuestal getDicClavePresupuestal() {
		return this.dicClavePresupuestal;
	}

	public void setDicClavePresupuestal(DicClavePresupuestal dicClavePresupuestal) {
		this.dicClavePresupuestal = dicClavePresupuestal;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
	public List<DitAseguradoGuarderia> getDitAseguradoGuarderias() {
		return this.ditAseguradoGuarderias;
	}

	public void setDitAseguradoGuarderias(List<DitAseguradoGuarderia> ditAseguradoGuarderias) {
		this.ditAseguradoGuarderias = ditAseguradoGuarderias;
	}
	
}