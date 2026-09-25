package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_NATURALEZA database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_NATURALEZA")
public class DicTipoNaturaleza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_NATURALEZA", nullable=false, precision=22)
	private long cveIdTipoNaturaleza;

	@Column(name="DES_ORIGEN_MOVTO_AJUSTE", length=20)
	private String desOrigenMovtoAjuste;

	@Column(name="DES_TIPO_NATURALEZA", length=20)
	private String desTipoNaturaleza;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicOrigenMovtoAjuste
	@OneToMany(mappedBy="dicTipoNaturaleza")
	private List<DicOrigenMovtoAjuste> dicOrigenMovtoAjustes;

    public DicTipoNaturaleza() {
    }

	public long getCveIdTipoNaturaleza() {
		return this.cveIdTipoNaturaleza;
	}

	public void setCveIdTipoNaturaleza(long cveIdTipoNaturaleza) {
		this.cveIdTipoNaturaleza = cveIdTipoNaturaleza;
	}

	public String getDesOrigenMovtoAjuste() {
		return this.desOrigenMovtoAjuste;
	}

	public void setDesOrigenMovtoAjuste(String desOrigenMovtoAjuste) {
		this.desOrigenMovtoAjuste = desOrigenMovtoAjuste;
	}

	public String getDesTipoNaturaleza() {
		return this.desTipoNaturaleza;
	}

	public void setDesTipoNaturaleza(String desTipoNaturaleza) {
		this.desTipoNaturaleza = desTipoNaturaleza;
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

	public List<DicOrigenMovtoAjuste> getDicOrigenMovtoAjustes() {
		return this.dicOrigenMovtoAjustes;
	}

	public void setDicOrigenMovtoAjustes(List<DicOrigenMovtoAjuste> dicOrigenMovtoAjustes) {
		this.dicOrigenMovtoAjustes = dicOrigenMovtoAjustes;
	}
	
}