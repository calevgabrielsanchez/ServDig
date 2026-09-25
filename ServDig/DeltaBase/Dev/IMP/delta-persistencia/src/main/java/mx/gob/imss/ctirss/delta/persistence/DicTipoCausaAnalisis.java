package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the DIC_TIPO_CAUSA_ANALISIS database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_CAUSA_ANALISIS")
public class DicTipoCausaAnalisis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_CAUSA")
	private long cveIdTipoCausa;

	@Column(name="DES_CAUSA")
	private String desCausa;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @Column(name="TIP_PROCESO")
	private Long tipoProceso;

	//bi-directional many-to-one association to DitAnalisisCe
	@OneToMany(mappedBy="dicTipoCausaAnalisi")
	private List<DitAnalisisCe> ditAnalisisCes;
	
	//bi-directional many-to-one association to DicTipoTramite
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_TRAMITE")
	private DicTipoTramite dicTipoTramite;
    
  //bi-directional many-to-one association to DitHistTipoCausa
	@OneToMany(mappedBy="dicTipoCausaAnalisis")
	private Set<DitHistTipoCausa> ditHistTipoCausas;

    public DicTipoCausaAnalisis() {
    }

	public long getCveIdTipoCausa() {
		return this.cveIdTipoCausa;
	}

	public void setCveIdTipoCausa(long cveIdTipoCausa) {
		this.cveIdTipoCausa = cveIdTipoCausa;
	}

	public String getDesCausa() {
		return this.desCausa;
	}

	public void setDesCausa(String desCausa) {
		this.desCausa = desCausa;
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

	public List<DitAnalisisCe> getDitAnalisisCes() {
		return this.ditAnalisisCes;
	}

	public void setDitAnalisisCes(List<DitAnalisisCe> ditAnalisisCes) {
		this.ditAnalisisCes = ditAnalisisCes;
	}

	public DicTipoTramite getDicTipoTramite() {
		return dicTipoTramite;
	}

	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
	}

	public Long getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(Long tipoProceso) {
		this.tipoProceso = tipoProceso;
	}
	public Set<DitHistTipoCausa> getDitHistTipoCausas() {
		return this.ditHistTipoCausas;
	}

	public void setDitHistTipoCausas(Set<DitHistTipoCausa> ditHistTipoCausas) {
		this.ditHistTipoCausas = ditHistTipoCausas;
	}
	
}