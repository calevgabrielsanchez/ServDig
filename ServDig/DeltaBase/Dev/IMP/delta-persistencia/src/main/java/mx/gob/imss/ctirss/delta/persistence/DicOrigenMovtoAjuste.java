package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ORIGEN_MOVTO_AJUSTE database table.
 * 
 */
@Entity
@Table(name="DIC_ORIGEN_MOVTO_AJUSTE")
public class DicOrigenMovtoAjuste implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ORIGEN_MOVTO_AJUSTE", nullable=false, precision=22)
	private long cveIdOrigenMovtoAjuste;

	@Column(name="DES_ORIGEN_MOVTO_AJUSTE", length=20)
	private String desOrigenMovtoAjuste;

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

	//bi-directional many-to-one association to DicTipoNaturaleza
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_NATURALEZA")
	private DicTipoNaturaleza dicTipoNaturaleza;

	//bi-directional many-to-one association to DitMovasegAjuste
	@OneToMany(mappedBy="dicOrigenMovtoAjuste")
	private List<DitMovasegAjuste> ditMovasegAjustes;

    public DicOrigenMovtoAjuste() {
    }

	public long getCveIdOrigenMovtoAjuste() {
		return this.cveIdOrigenMovtoAjuste;
	}

	public void setCveIdOrigenMovtoAjuste(long cveIdOrigenMovtoAjuste) {
		this.cveIdOrigenMovtoAjuste = cveIdOrigenMovtoAjuste;
	}

	public String getDesOrigenMovtoAjuste() {
		return this.desOrigenMovtoAjuste;
	}

	public void setDesOrigenMovtoAjuste(String desOrigenMovtoAjuste) {
		this.desOrigenMovtoAjuste = desOrigenMovtoAjuste;
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

	public DicTipoNaturaleza getDicTipoNaturaleza() {
		return this.dicTipoNaturaleza;
	}

	public void setDicTipoNaturaleza(DicTipoNaturaleza dicTipoNaturaleza) {
		this.dicTipoNaturaleza = dicTipoNaturaleza;
	}
	
	public List<DitMovasegAjuste> getDitMovasegAjustes() {
		return this.ditMovasegAjustes;
	}

	public void setDitMovasegAjustes(List<DitMovasegAjuste> ditMovasegAjustes) {
		this.ditMovasegAjustes = ditMovasegAjustes;
	}
	
}