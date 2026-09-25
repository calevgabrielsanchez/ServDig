package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_DOCTO_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name = "DIT_DOCTO_ANALISIS_CE")
public class DitDoctoAnalisisCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = 1366356192040704541L;

	@Id
	@SequenceGenerator(name = "DIT_DOCTO_ANALISIS_CE_GENERATOR", sequenceName = "SEQ_DITDOCTOANALISISCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DOCTO_ANALISIS_CE_GENERATOR")
	@Column(name = "CVE_ID_DOCTO_ANALISIS_CE")
	private long cveIdDoctoAnalisisCe;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;

	@ManyToOne
	@JoinColumn(name = "CVE_TIPO_DOCTO_ANALISIS_CE")
	private DicTipoDoctoAnalisisCe dicTipoDoctoAnalisisCe;

	@Lob()
	@Column(name = "REF_DOCUMENTO")
	private byte[] refDocumento;

	public long getCveIdDoctoAnalisisCe() {
		return cveIdDoctoAnalisisCe;
	}

	public void setCveIdDoctoAnalisisCe(long cveIdDoctoAnalisisCe) {
		this.cveIdDoctoAnalisisCe = cveIdDoctoAnalisisCe;
	}

	public DitAnalisisCe getDitAnalisisCe() {
		return ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}

	public DicTipoDoctoAnalisisCe getDicTipoDoctoAnalisisCe() {
		return dicTipoDoctoAnalisisCe;
	}

	public void setDicTipoDoctoAnalisisCe(
			DicTipoDoctoAnalisisCe dicTipoDoctoAnalisisCe) {
		this.dicTipoDoctoAnalisisCe = dicTipoDoctoAnalisisCe;
	}

	public byte[] getRefDocumento() {
		return refDocumento;
	}

	public void setRefDocumento(byte[] refDocumento) {
		this.refDocumento = refDocumento != null ? refDocumento.clone() : null;
	}

}
