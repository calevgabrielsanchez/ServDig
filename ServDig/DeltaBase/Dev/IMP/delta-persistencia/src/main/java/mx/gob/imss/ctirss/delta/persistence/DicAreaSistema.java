package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_AREA_SISTEMA database table.
 * 
 */
@Entity
@Table(name="DIC_AREA_SISTEMA")
public class DicAreaSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_AREA_SISTEMA", nullable=false, precision=22)
	private long cveIdAreaSistema;

	@Column(name="DES_AREA_SISTEMA", length=20)
	private String desAreaSistema;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitParametroArea
	@OneToMany(mappedBy="dicAreaSistema")
	private List<DitParametroArea> ditParametroAreas;

    public DicAreaSistema() {
    }

	public long getCveIdAreaSistema() {
		return this.cveIdAreaSistema;
	}

	public void setCveIdAreaSistema(long cveIdAreaSistema) {
		this.cveIdAreaSistema = cveIdAreaSistema;
	}

	public String getDesAreaSistema() {
		return this.desAreaSistema;
	}

	public void setDesAreaSistema(String desAreaSistema) {
		this.desAreaSistema = desAreaSistema;
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

	public List<DitParametroArea> getDitParametroAreas() {
		return this.ditParametroAreas;
	}

	public void setDitParametroAreas(List<DitParametroArea> ditParametroAreas) {
		this.ditParametroAreas = ditParametroAreas;
	}
	
}