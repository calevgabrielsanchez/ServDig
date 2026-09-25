package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_UMF_COD_POS database table.
 * 
 */
@Entity
@Table(name="DIT_UMF_COD_POS")
public class DitUmfCodPo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_DITUMFCODPOS", sequenceName = "SEQ_DITUMFCODPOS")
    @GeneratedValue(generator = "SEQ_DITUMFCODPOS")
	@Column(name="CVE_ID_UMF_COD_POS", nullable=false, precision=22)
	private long cveIdUmfCodPos;;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

  //bi-directional many-to-one association to DitPersona
    @ManyToOne
	@JoinColumn(name="CVE_ID_UMF")
	private DicUmf dicUmf;
    
	public DicUmf getDicUmf() {
		return dicUmf;
	}

	public void setDicUmf(DicUmf dicUmf) {
		this.dicUmf = dicUmf;
	}

	//bi-directional many-to-one association to DgCodigosPostale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CODIGO", referencedColumnName="CODIGO"),
		@JoinColumn(name="CVE_ASEN", referencedColumnName="CVE_ASEN"),
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT"),
		/*
		@JoinColumn(name="CVE_LOC", referencedColumnName="CVE_LOC"),
		@JoinColumn(name="CVE_PERIODO", referencedColumnName="CVE_PERIODO")
		*/
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN")
		
		})
	private DgCodigosPostale dgCodigosPostale;

    public DitUmfCodPo() {
    }

	public long getCveIdUmfCodPos() {
		return this.cveIdUmfCodPos;
	}

	public void setCveIdUmfCodPos(long cveIdUmfCodPos) {
		this.cveIdUmfCodPos = cveIdUmfCodPos;
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

	public DgCodigosPostale getDgCodigosPostale() {
		return this.dgCodigosPostale;
	}

	public void setDgCodigosPostale(DgCodigosPostale dgCodigosPostale) {
		this.dgCodigosPostale = dgCodigosPostale;
	}
	
}