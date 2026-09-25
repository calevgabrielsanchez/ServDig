package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_ESCRITO_DESACUERDO")
public class DitEscritoDesacuerdo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_ESCRITO_DESACUERDO_GENERATOR", sequenceName = "SEQ_DITESCRITODESACUERDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ESCRITO_DESACUERDO_GENERATOR")
	@Column(name = "CVE_ID_ESCRITO_DESACUERDO", nullable = false)
	private long cveIdDesacuerdo;
	
	@Column(name = "REF_FOLIO_RECEPCION", nullable = false)
	private String refFolioRecepcion;
	
	@Column(name = "REF_FOLIO_INPUGNADO", nullable = false)
	private String refFolioInpugnado;
	
	@Column(name = "REF_MOTIVO_DESACUERDO")
	private String refMotivos;
	
	@Column(name = "IND_REGISTRO_PROCESADO", nullable = false)
	private Long indRegistroProcesado;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA", nullable = false)
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_CAUSA_DESACUERDO", nullable = false)
	private DicCausaDesacuerdo dicCausaDesacuerdo;
	
	@OneToOne
	@JoinColumn(name="CVE_ID_TRAMITE", nullable = false)
	private DitTramite ditTramite;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CVE_ID_PATRON_SUJETO_OBLIGADO", nullable = false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;
	
	@Column(name = "CVE_ID_MOTIVO_DESACUERDO")
	private long dicMotivoDesacuerdo;

	@Column(name = "IND_AN_VIGENCIA")
	private long indAnVigencia;

	@Column(name = "REF_CLASE_ANTERIOR")
	private String refClaseAnterior;

	@Column(name = "REF_FRACCION_ANTERIOR")
	private String refFraccAnterior;

	@Column(name = "REF_PRIMA_ANTERIOR")
	private String refPrimaAnterior;

	@Column(name = "REF_TRABAJADOR_PROMEDIO")
	private String refTrabPromedio;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_NOTIFICACION_RESOL")
	private Date fecNotificaResol;

	@Column(name = "REF_MOTIVO_DESACUERDO1")
	private String refMotivos1;

	@Column(name = "REF_MOTIVO_DESACUERDO2")
	private String refMotivos2;

	@Column(name = "REF_MOTIVO_DESACUERDO3")
	private String refMotivos3;

	@Column(name = "REF_MOTIVO_DESACUERDO4")
	private String refMotivos4;

	@Column(name = "REF_MOTIVO_DESACUERDO5")
	private String refMotivos5;

	@Column(name = "REF_MOTIVO_DESACUERDO6")
	private String refMotivos6;

	@Column(name = "REF_MOTIVO_DESACUERDO7")
	private String refMotivos7;

	@Column(name = "REF_MOTIVO_DESACUERDO8")
	private String refMotivos8;

	@Column(name = "REF_MOTIVO_DESACUERDO9")
	private String refMotivos9;

	public long getCveIdDesacuerdo() {
		return cveIdDesacuerdo;
	}

	public void setCveIdDesacuerdo(long cveIdDesacuerdo) {
		this.cveIdDesacuerdo = cveIdDesacuerdo;
	}

	public DicCausaDesacuerdo getDicCausaDesacuerdo() {
		return dicCausaDesacuerdo;
	}

	public void setDicCausaDesacuerdo(DicCausaDesacuerdo dicCausaDesacuerdo) {
		this.dicCausaDesacuerdo = dicCausaDesacuerdo;
	}
	
	public long getDicMotivoDesacuerdo() {
		return dicMotivoDesacuerdo;
	}

	public void setDicMotivoDesacuerdo(long dicMotivoDesacuerdo) {
		this.dicMotivoDesacuerdo = dicMotivoDesacuerdo;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}

	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public String getRefMotivos() {
		return refMotivos;
	}

	public void setRefMotivos(String refMotivos) {
		this.refMotivos = refMotivos;
	}

	public String getRefFolioRecepcion() {
		return refFolioRecepcion;
	}

	public void setRefFolioRecepcion(String refFolioRecepcion) {
		this.refFolioRecepcion = refFolioRecepcion;
	}

	public String getRefFolioInpugnado() {
		return refFolioInpugnado;
	}

	public void setRefFolioInpugnado(String refFolioInpugnado) {
		this.refFolioInpugnado = refFolioInpugnado;
	}

	public Long getIndRegistroProcesado() {
		return indRegistroProcesado;
	}

	public void setIndRegistroProcesado(Long indRegistroProcesado) {
		this.indRegistroProcesado = indRegistroProcesado;
	}

	public static long getSerialVersionUID() {return serialVersionUID;}

	public long getIndAnVigencia() {return indAnVigencia;}

	public void setIndAnVigencia(long indAnVigencia) {this.indAnVigencia = indAnVigencia;}

	public String getRefClaseAnterior() {return refClaseAnterior;}

	public void setRefClaseAnterior(String refClaseAnterior) {this.refClaseAnterior = refClaseAnterior;}

	public String getRefFraccAnterior() {return refFraccAnterior;}

	public void setRefFraccAnterior(String refFraccAnterior) {this.refFraccAnterior = refFraccAnterior;}

	public String getRefPrimaAnterior() {return refPrimaAnterior;}

	public void setRefPrimaAnterior(String refPrimaAnterior) {this.refPrimaAnterior = refPrimaAnterior;}

	public String getRefTrabPromedio() {return refTrabPromedio;}

	public void setRefTrabPromedio(String refTrabPromedio) {this.refTrabPromedio = refTrabPromedio;}

	public Date getFecNotificaResol() {return fecNotificaResol;}

	public void setFecNotificaResol(Date fecNotificaResol) {this.fecNotificaResol = fecNotificaResol;}

	public String getRefMotivos1() {return refMotivos1;}

	public void setRefMotivos1(String refMotivos1) {this.refMotivos1 = refMotivos1;}

	public String getRefMotivos2() {return refMotivos2;}

	public void setRefMotivos2(String refMotivos2) {this.refMotivos2 = refMotivos2;}

	public String getRefMotivos3() {return refMotivos3;}

	public void setRefMotivos3(String refMotivos3) {this.refMotivos3 = refMotivos3;}

	public String getRefMotivos4() {return refMotivos4;}

	public void setRefMotivos4(String refMotivos4) {this.refMotivos4 = refMotivos4;}

	public String getRefMotivos5() {return refMotivos5;}

	public void setRefMotivos5(String refMotivos5) {this.refMotivos5 = refMotivos5;}

	public String getRefMotivos6() {return refMotivos6;}

	public void setRefMotivos6(String refMotivos6) {this.refMotivos6 = refMotivos6;}

	public String getRefMotivos7() {return refMotivos7;}

	public void setRefMotivos7(String refMotivos7) {this.refMotivos7 = refMotivos7;}

	public String getRefMotivos8() {return refMotivos8;}

	public void setRefMotivos8(String refMotivos8) {this.refMotivos8 = refMotivos8;}

	public String getRefMotivos9() {return refMotivos9;}

	public void setRefMotivos9(String refMotivos9) {this.refMotivos9 = refMotivos9;}
}
