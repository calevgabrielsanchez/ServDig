package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PAT_SUJ_OBLIG_DOMICILIO database table.
 * 
 */
@Entity
@Table(name="DIT_PAT_SUJ_OBLIG_DOMICILIO")
public class DitPatSujObligDomicilio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PAT_SUJ_OBLIG_DOMICILIO_GENERATOR", sequenceName = "SEQ_DITPATSUJOBLIGDOMICILIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PAT_SUJ_OBLIG_DOMICILIO_GENERATOR")
	@Column(name="CVE_ID_PAT_SUJ_OBLIG_DOMICILIO", nullable=false, precision=22)
	private long cveIdPatSujObligDomicilio;

	@Column(name="DES_SECTOR_NOTIFICACION", length=20)
	private String desSectorNotificacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DicTipoDomicilio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_DOMICILIO")
	private DicTipoDomicilio dicTipoDomicilio;

	//bi-directional many-to-one association to DicMedioDistribucion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MEDIO_DISTRIBUCION")
	private DicMedioDistribucion dicMedioDistribucion;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

    public DitPatSujObligDomicilio() {
    }

	public long getCveIdPatSujObligDomicilio() {
		return this.cveIdPatSujObligDomicilio;
	}

	public void setCveIdPatSujObligDomicilio(long cveIdPatSujObligDomicilio) {
		this.cveIdPatSujObligDomicilio = cveIdPatSujObligDomicilio;
	}

	public String getDesSectorNotificacion() {
		return this.desSectorNotificacion;
	}

	public void setDesSectorNotificacion(String desSectorNotificacion) {
		this.desSectorNotificacion = desSectorNotificacion;
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

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicTipoDomicilio getDicTipoDomicilio() {
		return this.dicTipoDomicilio;
	}

	public void setDicTipoDomicilio(DicTipoDomicilio dicTipoDomicilio) {
		this.dicTipoDomicilio = dicTipoDomicilio;
	}
	
	public DicMedioDistribucion getDicMedioDistribucion() {
		return this.dicMedioDistribucion;
	}

	public void setDicMedioDistribucion(DicMedioDistribucion dicMedioDistribucion) {
		this.dicMedioDistribucion = dicMedioDistribucion;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
}