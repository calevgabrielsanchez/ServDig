package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the DIT_HIST_ESTATUS_ANALISIS database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_ESTATUS_ANALISIS")
public class DitHistEstatusAnalisis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_HIST_ESTATUS_ANALISIS_GENERATOR", sequenceName = "SEQ_DITHISTESTATUSANALISIS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_HIST_ESTATUS_ANALISIS_GENERATOR")
    @Column(name="CVE_HIST_ESTATUS_ANALISIS")
	private long cveHistEstatusAnalisis;

	@Column(name="STP_HIST_ESTATUS_ANALISIS")
	private Timestamp stpHistEstatusAnalisis;
	
	@Column(name="NUM_PRIMA_ANT")
	private BigDecimal numPrimaAnt;

	@Column(name="NUM_PRIMA_DEC")
	private BigDecimal numPrimaDec;

	@Column(name="NUM_PRIMA_PRO")
	private BigDecimal numPrimaPro;

	//bi-directional many-to-one association to DicEstatusAnalisisCe
    @ManyToOne
	@JoinColumn(name="CVE_ID_ESTATUS_ANALISIS")
	private DicEstatusAnalisisCe dicEstatusAnalisisCe;

	//bi-directional many-to-one association to DitAnalisisCe
    @ManyToOne
	@JoinColumn(name="CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;
    
  //bi-directional many-to-one association to DicFraccion
    @ManyToOne
	@JoinColumn(name="CVE_ID_FRACCION_ANT")
	private DicFraccion dicFraccionAnt;

	//bi-directional many-to-one association to DicFraccion
    @ManyToOne
	@JoinColumn(name="CVE_ID_FRACCION_PRO")
	private DicFraccion dicFraccionPro;

	//bi-directional many-to-one association to DicFraccion
    @ManyToOne
	@JoinColumn(name="CVE_ID_FRACCION_DEC")
	private DicFraccion dicFraccionDec;
    
    @Column(name="DES_COMENTARIO")
	private String desComentario;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;
	
//	@ManyToOne
//	@JoinColumn(name = "CVE_ID_USUARIO")
//	private DitUsuario ditUsuario;

	@Column(name="CVE_USUARIO_SSO")
	private String cveIdUsuarioSso;

    public DitHistEstatusAnalisis() {
    }

	public long getCveHistEstatusAnalisis() {
		return this.cveHistEstatusAnalisis;
	}

	public void setCveHistEstatusAnalisis(long cveHistEstatusAnalisis) {
		this.cveHistEstatusAnalisis = cveHistEstatusAnalisis;
	}

	public Timestamp getStpHistEstatusAnalisis() {
		return this.stpHistEstatusAnalisis;
	}

	public void setStpHistEstatusAnalisis(Timestamp stpHistEstatusAnalisis) {
		this.stpHistEstatusAnalisis = stpHistEstatusAnalisis;
	}

	public DicEstatusAnalisisCe getDicEstatusAnalisisCe() {
		return this.dicEstatusAnalisisCe;
	}

	public void setDicEstatusAnalisisCe(DicEstatusAnalisisCe dicEstatusAnalisisCe) {
		this.dicEstatusAnalisisCe = dicEstatusAnalisisCe;
	}
	
	public DitAnalisisCe getDitAnalisisCe() {
		return this.ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}
	
	public String getDesComentario(){
		return this.desComentario;
	}
	
	public void setDesComentario(String desComentario){
		this.desComentario = desComentario;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public BigDecimal getNumPrimaAnt() {
		return numPrimaAnt;
	}

	public void setNumPrimaAnt(BigDecimal numPrimaAnt) {
		this.numPrimaAnt = numPrimaAnt;
	}

	public BigDecimal getNumPrimaDec() {
		return numPrimaDec;
	}

	public void setNumPrimaDec(BigDecimal numPrimaDec) {
		this.numPrimaDec = numPrimaDec;
	}

	public BigDecimal getNumPrimaPro() {
		return numPrimaPro;
	}

	public void setNumPrimaPro(BigDecimal numPrimaPro) {
		this.numPrimaPro = numPrimaPro;
	}

	public DicFraccion getDicFraccionAnt() {
		return dicFraccionAnt;
	}

	public void setDicFraccionAnt(DicFraccion dicFraccionAnt) {
		this.dicFraccionAnt = dicFraccionAnt;
	}

	public DicFraccion getDicFraccionPro() {
		return dicFraccionPro;
	}

	public void setDicFraccionPro(DicFraccion dicFraccionPro) {
		this.dicFraccionPro = dicFraccionPro;
	}

	public DicFraccion getDicFraccionDec() {
		return dicFraccionDec;
	}

	public void setDicFraccionDec(DicFraccion dicFraccionDec) {
		this.dicFraccionDec = dicFraccionDec;
	}

//	public DitUsuario getDitUsuario() {
//		return ditUsuario;
//	}
//
//	public void setDitUsuario(DitUsuario ditUsuario) {
//		this.ditUsuario = ditUsuario;
//	}

	public String getCveIdUsuarioSso() {
		return cveIdUsuarioSso;
	}

	public void setCveIdUsuarioSso(String cveIdUsuarioSso) {
		this.cveIdUsuarioSso = cveIdUsuarioSso;
	}

}