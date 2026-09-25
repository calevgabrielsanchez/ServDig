package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_HIST_OMISIONES database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_OMISIONES")
public class DitHistOmisiones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_HIST_OMISIONES_GENERATOR", sequenceName = "SEQ_DITHISTOMISIONES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_HIST_OMISIONES_GENERATOR")
    @Column(name="CVE_ID_HIST_OMISIONES")
	private long cveHistOmisiones;
	
	@Column(name="JUSTIFICACION")
	private String justificacion;
	
	@Column(name="PAGO")
	private String pago;
	
	@Column(name="CVE_ID_ANALISIS")
	private long cveIdAnalisis;	
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_SURTE_EFECTO")
	private Date fecSurteEfecto;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_ANALISIS")
	private Date fecAnilisis;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_RECHAZO_ANALISIS")
	private Date fecRechazoAnilisis;

	//bi-directional many-to-one association to DicEstatusAnalisisCe
    @ManyToOne
	@JoinColumn(name="CVE_ID_OMISIONES_DETECTADAS")
	private DicOmisionesDetectadas dicOmisionesDetectadas;

	public long getCveHistOmisiones() {
		return cveHistOmisiones;
	}

	public void setCveHistOmisiones(long cveHistOmisiones) {
		this.cveHistOmisiones = cveHistOmisiones;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}


	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public Date getFecSurteEfecto() {
		return fecSurteEfecto;
	}

	public void setFecSurteEfecto(Date fecSurteEfecto) {
		this.fecSurteEfecto = fecSurteEfecto;
	}

	public Date getFecAnilisis() {
		return fecAnilisis;
	}

	public void setFecAnilisis(Date fecAnilisis) {
		this.fecAnilisis = fecAnilisis;
	}

	public Date getFecRechazoAnilisis() {
		return fecRechazoAnilisis;
	}

	public void setFecRechazoAnilisis(Date fecRechazoAnilisis) {
		this.fecRechazoAnilisis = fecRechazoAnilisis;
	}
	
	public long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public DicOmisionesDetectadas getDicOmisionesDetectadas() {
		return dicOmisionesDetectadas;
	}

	public void setDicOmisionesDetectadas(DicOmisionesDetectadas dicOmisionesDetectadas) {
		this.dicOmisionesDetectadas = dicOmisionesDetectadas;
	}
}