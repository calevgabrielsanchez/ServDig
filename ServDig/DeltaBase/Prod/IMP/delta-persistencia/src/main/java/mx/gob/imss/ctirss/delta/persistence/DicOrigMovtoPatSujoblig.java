package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ORIG_MOVTO_PAT_SUJOBLIG database table.
 * 
 */
@Entity
@Table(name="DIC_ORIG_MOVTO_PAT_SUJOBLIG")
public class DicOrigMovtoPatSujoblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ORIG_MOVTO_PAT_SUJOBLIG", nullable=false, precision=22)
	private long cveIdOrigMovtoPatSujoblig;

	@Column(name="DES_ORIGEN", length=255)
	private String desOrigen;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_ORIGEN", length=50)
	private String numOrigen;

	//bi-directional many-to-one association to DitMovtoPatSujOblig
	@OneToMany(mappedBy="dicOrigMovtoPatSujoblig")
	private List<DitMovtoPatSujOblig> ditMovtoPatSujObligs;

    public DicOrigMovtoPatSujoblig() {
    }

	public long getCveIdOrigMovtoPatSujoblig() {
		return this.cveIdOrigMovtoPatSujoblig;
	}

	public void setCveIdOrigMovtoPatSujoblig(long cveIdOrigMovtoPatSujoblig) {
		this.cveIdOrigMovtoPatSujoblig = cveIdOrigMovtoPatSujoblig;
	}

	public String getDesOrigen() {
		return this.desOrigen;
	}

	public void setDesOrigen(String desOrigen) {
		this.desOrigen = desOrigen;
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

	public String getNumOrigen() {
		return this.numOrigen;
	}

	public void setNumOrigen(String numOrigen) {
		this.numOrigen = numOrigen;
	}

	public List<DitMovtoPatSujOblig> getDitMovtoPatSujObligs() {
		return this.ditMovtoPatSujObligs;
	}

	public void setDitMovtoPatSujObligs(List<DitMovtoPatSujOblig> ditMovtoPatSujObligs) {
		this.ditMovtoPatSujObligs = ditMovtoPatSujObligs;
	}
	
}