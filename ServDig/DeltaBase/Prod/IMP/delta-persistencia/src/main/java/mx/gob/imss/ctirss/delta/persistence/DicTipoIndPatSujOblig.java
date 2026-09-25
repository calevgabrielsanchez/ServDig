package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_IND_PAT_SUJ_OBLIG database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_IND_PAT_SUJ_OBLIG")
public class DicTipoIndPatSujOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_IND_PAT_SUJ_OBLIG", nullable=false, precision=22)
	private long cveIdTipoIndPatSujOblig;

	@Column(name="DES_TIPO_IND_PAT_SUJ_OBLIG", length=20)
	private String desTipoIndPatSujOblig;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitIndicadorPatSujOblig
	@OneToMany(mappedBy="dicTipoIndPatSujOblig")
	private List<DitIndicadorPatSujOblig> ditIndicadorPatSujObligs;

    public DicTipoIndPatSujOblig() {
    }

	public long getCveIdTipoIndPatSujOblig() {
		return this.cveIdTipoIndPatSujOblig;
	}

	public void setCveIdTipoIndPatSujOblig(long cveIdTipoIndPatSujOblig) {
		this.cveIdTipoIndPatSujOblig = cveIdTipoIndPatSujOblig;
	}

	public String getDesTipoIndPatSujOblig() {
		return this.desTipoIndPatSujOblig;
	}

	public void setDesTipoIndPatSujOblig(String desTipoIndPatSujOblig) {
		this.desTipoIndPatSujOblig = desTipoIndPatSujOblig;
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

	public List<DitIndicadorPatSujOblig> getDitIndicadorPatSujObligs() {
		return this.ditIndicadorPatSujObligs;
	}

	public void setDitIndicadorPatSujObligs(List<DitIndicadorPatSujOblig> ditIndicadorPatSujObligs) {
		this.ditIndicadorPatSujObligs = ditIndicadorPatSujObligs;
	}
	
}