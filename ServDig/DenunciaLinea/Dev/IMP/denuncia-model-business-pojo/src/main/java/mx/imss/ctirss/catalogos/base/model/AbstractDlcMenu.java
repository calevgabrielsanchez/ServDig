package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.catalogos.model.DlcMenu;
import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DLC_MENU database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcMenu extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_MENU")
	private long cveIdMenu;

	//bi-directional many-to-one association to SegMenu
    @ManyToOne
	@JoinColumn(name="CVE_FK_MENU")
	private DlcMenu dlcMenu;

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

	public DlcMenu getDlcMenu() {
		return dlcMenu;
	}

	public void setDlcMenu(DlcMenu dlcMenu) {
		this.dlcMenu = dlcMenu;
	}

	public Integer getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}

}