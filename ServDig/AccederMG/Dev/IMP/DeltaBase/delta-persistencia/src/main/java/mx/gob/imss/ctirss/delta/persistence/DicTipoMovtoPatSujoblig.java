package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_MOVTO_PAT_SUJOBLIG database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_MOVTO_PAT_SUJOBLIG")
public class DicTipoMovtoPatSujoblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG", nullable=false, precision=22)
	private long cveIdTipoMovtoPatSujoblig;

	@Column(name="DES_TIPO_MOVIMIENTO", length=100)
	private String desTipoMovimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMovtoPatSujOblig
	@OneToMany(mappedBy="dicTipoMovtoPatSujoblig")
	private List<DitMovtoPatSujOblig> ditMovtoPatSujObligs;

    public DicTipoMovtoPatSujoblig() {
    }

	public long getCveIdTipoMovtoPatSujoblig() {
		return this.cveIdTipoMovtoPatSujoblig;
	}

	public void setCveIdTipoMovtoPatSujoblig(long cveIdTipoMovtoPatSujoblig) {
		this.cveIdTipoMovtoPatSujoblig = cveIdTipoMovtoPatSujoblig;
	}

	public String getDesTipoMovimiento() {
		return this.desTipoMovimiento;
	}

	public void setDesTipoMovimiento(String desTipoMovimiento) {
		this.desTipoMovimiento = desTipoMovimiento;
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

	public List<DitMovtoPatSujOblig> getDitMovtoPatSujObligs() {
		return this.ditMovtoPatSujObligs;
	}

	public void setDitMovtoPatSujObligs(List<DitMovtoPatSujOblig> ditMovtoPatSujObligs) {
		this.ditMovtoPatSujObligs = ditMovtoPatSujObligs;
	}
	
}