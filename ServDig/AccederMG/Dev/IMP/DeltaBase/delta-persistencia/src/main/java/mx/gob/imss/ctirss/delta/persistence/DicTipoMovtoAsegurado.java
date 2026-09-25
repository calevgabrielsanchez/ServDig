package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_MOVTO_ASEGURADO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_MOVTO_ASEGURADO")
public class DicTipoMovtoAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_MOVTO_ASEGURADO", nullable=false, precision=22)
	private long cveIdTipoMovtoAsegurado;

	@Column(name="DES_TIPO_MOVIMIENTO_ASEGURADO", length=100)
	private String desTipoMovimientoAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMovasegAjuste
	@OneToMany(mappedBy="dicTipoMovtoAsegurado")
	private List<DitMovasegAjuste> ditMovasegAjustes;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@OneToMany(mappedBy="dicTipoMovtoAsegurado")
	private List<DitMovimientoAsegurado> ditMovimientoAsegurados;
		
    public DicTipoMovtoAsegurado() {
    }

	public long getCveIdTipoMovtoAsegurado() {
		return this.cveIdTipoMovtoAsegurado;
	}

	public void setCveIdTipoMovtoAsegurado(long cveIdTipoMovtoAsegurado) {
		this.cveIdTipoMovtoAsegurado = cveIdTipoMovtoAsegurado;
	}

	public String getDesTipoMovimientoAsegurado() {
		return this.desTipoMovimientoAsegurado;
	}

	public void setDesTipoMovimientoAsegurado(String desTipoMovimientoAsegurado) {
		this.desTipoMovimientoAsegurado = desTipoMovimientoAsegurado;
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

	public List<DitMovasegAjuste> getDitMovasegAjustes() {
		return this.ditMovasegAjustes;
	}

	public void setDitMovasegAjustes(List<DitMovasegAjuste> ditMovasegAjustes) {
		this.ditMovasegAjustes = ditMovasegAjustes;
	}
	
	public List<DitMovimientoAsegurado> getDitMovimientoAsegurados() {
		return this.ditMovimientoAsegurados;
	}

	public void setDitMovimientoAsegurados(List<DitMovimientoAsegurado> ditMovimientoAsegurados) {
		this.ditMovimientoAsegurados = ditMovimientoAsegurados;
	}
	
}