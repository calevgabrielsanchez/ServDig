package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_HIST_TIPO_CAUSA database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_TIPO_CAUSA")
public class DitHistTipoCausa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_HIST_TIPO_CAUSA_CVEIDHISTTIPOCAUSA_GENERATOR", sequenceName="SEQ_DITHISTTIPOCAUSA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_HIST_TIPO_CAUSA_CVEIDHISTTIPOCAUSA_GENERATOR")
	@Column(name="CVE_ID_HIST_TIPO_CAUSA")
	private long cveIdHistTipoCausa;

	@Column(name="STMP_FECHA_ACTUALIZACION")
	private Timestamp stmpFechaActualizado;
    
	//bi-directional many-to-one association to DitAnalisisCe
    @ManyToOne
	@JoinColumn(name="CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;

	//bi-directional many-to-one association to DicTipoCausaAnalisis
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_CAUSA")
	private DicTipoCausaAnalisis dicTipoCausaAnalisis;

    public DitHistTipoCausa() {
    }

	public long getCveIdHistTipoCausa() {
		return this.cveIdHistTipoCausa;
	}

	public void setCveIdHistTipoCausa(long cveIdHistTipoCausa) {
		this.cveIdHistTipoCausa = cveIdHistTipoCausa;
	}

	public Timestamp getStmpFechaActualizado() {
		return this.stmpFechaActualizado;
	}

	public void setStmpFechaActualizado(Timestamp stmpFechaActualizado) {
		this.stmpFechaActualizado = stmpFechaActualizado;
	}

	public DitAnalisisCe getDitAnalisisCe() {
		return this.ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}
	
	public DicTipoCausaAnalisis getDicTipoCausaAnalisis() {
		return this.dicTipoCausaAnalisis;
	}

	public void setDicTipoCausaAnalisis(DicTipoCausaAnalisis dicTipoCausaAnalisis) {
		this.dicTipoCausaAnalisis = dicTipoCausaAnalisis;
	}
}