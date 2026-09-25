package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_MENU database table.
 * 
 */
@Entity
@Table(name="DIC_MENU")
public class DicMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MENU", nullable=false, precision=22)
	private long cveIdMenu;

	@Column(name="DES_DESCRIPCION", length=100)
	private String desDescripcion;

	@Column(name="DES_VINCULO", length=100)
	private String desVinculo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_DESPLEGABLE", precision=22)
	private BigDecimal indDesplegable;

	@Column(name="REF_DESPLIEGUE", precision=22)
	private BigDecimal refDespliegue;

	//bi-directional many-to-one association to DicMenuAccion
	@OneToMany(mappedBy="dicMenu")
	private List<DicMenuAccion> dicMenuAccions;

    public DicMenu() {
    }

	public long getCveIdMenu() {
		return this.cveIdMenu;
	}

	public void setCveIdMenu(long cveIdMenu) {
		this.cveIdMenu = cveIdMenu;
	}

	public String getDesDescripcion() {
		return this.desDescripcion;
	}

	public void setDesDescripcion(String desDescripcion) {
		this.desDescripcion = desDescripcion;
	}

	public String getDesVinculo() {
		return this.desVinculo;
	}

	public void setDesVinculo(String desVinculo) {
		this.desVinculo = desVinculo;
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

	public BigDecimal getIndDesplegable() {
		return this.indDesplegable;
	}

	public void setIndDesplegable(BigDecimal indDesplegable) {
		this.indDesplegable = indDesplegable;
	}

	public BigDecimal getRefDespliegue() {
		return this.refDespliegue;
	}

	public void setRefDespliegue(BigDecimal refDespliegue) {
		this.refDespliegue = refDespliegue;
	}

	public List<DicMenuAccion> getDicMenuAccions() {
		return this.dicMenuAccions;
	}

	public void setDicMenuAccions(List<DicMenuAccion> dicMenuAccions) {
		this.dicMenuAccions = dicMenuAccions;
	}
	
}