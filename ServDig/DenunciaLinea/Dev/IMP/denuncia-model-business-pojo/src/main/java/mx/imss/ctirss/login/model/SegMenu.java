package mx.imss.ctirss.login.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SEG_MENU database table.
 * 
 */
@Entity
@Table(name="SEG_MENU")
public class SegMenu extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_MENU")
	private Long cveIdMenu;

	@Column(name="DES_DESCRIPCION")
	private String desDescripcion;

	@Column(name="DES_VINCULO")
	private String desVinculo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_DESPLEGABLE")
	private BigDecimal indDesplegable;

	@Column(name="NUM_ORDEN")
	private Integer numOrden;

	@Column(name="REF_DESPLIEGUE")
	private BigDecimal refDespliegue;

	//bi-directional many-to-one association to SegMenu
    @ManyToOne
	@JoinColumn(name="CVE_FK_MENU")
	private SegMenu segMenu;

	//bi-directional many-to-one association to SegMenu
	@OneToMany(mappedBy="segMenu")
	private Set<SegMenu> segMenus;

	//bi-directional many-to-one association to SegMenusistema
	@OneToMany(mappedBy="segMenu")
	private Set<SegMenusistema> segMenusistemas;
	
	/*------------------------- tansitorios ---------------------- */
	@Transient
	private Long idUsuario;
	@Transient
	private Long idPerfil;
	
	//bi-directional many-to-one association to SegMenuAccion
//	@OneToMany(mappedBy="segMenu")
//	private Set<SegMenuAccion> segMenuAccions;

    public SegMenu() {
    }

	public Long getCveIdMenu() {
		return this.cveIdMenu;
	}

	public void setCveIdMenu(Long cveIdMenu) {
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

	public Integer getNumOrden() {
		return this.numOrden;
	}

	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}

	public BigDecimal getRefDespliegue() {
		return this.refDespliegue;
	}

	public void setRefDespliegue(BigDecimal refDespliegue) {
		this.refDespliegue = refDespliegue;
	}

	public SegMenu getSegMenu() {
		return this.segMenu;
	}

	public void setSegMenu(SegMenu segMenu) {
		this.segMenu = segMenu;
	}
	
	public Set<SegMenu> getSegMenus() {
		return this.segMenus;
	}

	public void setSegMenus(Set<SegMenu> segMenus) {
		this.segMenus = segMenus;
	}
	
	public Set<SegMenusistema> getSegMenusistemas() {
		return this.segMenusistemas;
	}

	public void setSegMenusistemas(Set<SegMenusistema> segMenusistemas) {
		this.segMenusistemas = segMenusistemas;
	}

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the idPerfil
	 */
	public Long getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
//	public Set<SegMenuAccion> getSegMenuAccions() {
//		return this.segMenuAccions;
//	}
//
//	public void setSegMenuAccions(Set<SegMenuAccion> segMenuAccions) {
//		this.segMenuAccions = segMenuAccions;
//	}
	
}