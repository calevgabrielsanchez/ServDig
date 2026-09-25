package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_MENU_ACCION database table.
 * 
 */
@Entity
@Table(name="DIC_MENU_ACCION")
public class DicMenuAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MENU_ACCION", nullable=false, precision=22)
	private long cveIdMenuAccion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicAccion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ACCION")
	private DicAccion dicAccion;

	//bi-directional many-to-one association to DicMenu
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MENU")
	private DicMenu dicMenu;

	//bi-directional many-to-one association to DitPerfilAccion
	@OneToMany(mappedBy="dicMenuAccion")
	private List<DitPerfilAccion> ditPerfilAccions;

    public DicMenuAccion() {
    }

	public long getCveIdMenuAccion() {
		return this.cveIdMenuAccion;
	}

	public void setCveIdMenuAccion(long cveIdMenuAccion) {
		this.cveIdMenuAccion = cveIdMenuAccion;
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

	public DicAccion getDicAccion() {
		return this.dicAccion;
	}

	public void setDicAccion(DicAccion dicAccion) {
		this.dicAccion = dicAccion;
	}
	
	public DicMenu getDicMenu() {
		return this.dicMenu;
	}

	public void setDicMenu(DicMenu dicMenu) {
		this.dicMenu = dicMenu;
	}
	
	public List<DitPerfilAccion> getDitPerfilAccions() {
		return this.ditPerfilAccions;
	}

	public void setDitPerfilAccions(List<DitPerfilAccion> ditPerfilAccions) {
		this.ditPerfilAccions = ditPerfilAccions;
	}
	
}