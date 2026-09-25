package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the DIT_CIRCUNSCRIPCION_FORANEA database table.
 * 
 */
@Entity
@Table(name="DIT_CIRCUNSCRIPCION_FORANEA")
public class DitCircunscripcionForanea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_DITCIRCUNSCRIPCIONFORANEA", sequenceName = "SEQ_DITCIRCUNSCRIPCIONFORANEA")
    @GeneratedValue(generator = "SEQ_DITCIRCUNSCRIPCIONFORANEA")
	@Column(name="CVE_ID_CIRCUNSCRIPCION")
	private long cveIdCircunscripcion;

    

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_CIRCUNSCRIPCION")
	private Date fecFinCircunscripcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_CIRCUNSCRIPCION")
	private Date fecInicioCircunscripcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="IND_CIRCUNSCRIPCION_ACTIVA")
	private Integer indCircunscripcionActiva;

	@Column(name="DOMICILIO_ID_ORIGEN")
	private Long domicilioIdOrigen;
	
	@Column(name="DOMICILIO_ID_DESTINO")
	private Long domicilioIdDestino;
	
	
	//bi-directional many-to-one association to DitUmfConsTurnoMedico
    @ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED_D")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedico1;

	//bi-directional many-to-one association to DitUmfConsTurnoMedico
    @ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED_O")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedico2;
	

    

	
	//bi-directional many-to-one association to DitTramite
    @OneToOne
	@JoinColumn(name="CVE_ID_TRAMITE")
	private DitTramite ditTramite;

	//bi-directional many-to-one association to DitTramite
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_SUSPENSION")
	private DitTramite ditTramiteSuspension;

    public DitCircunscripcionForanea() {
    }

	
    public long getCveIdCircunscripcion() {
		return cveIdCircunscripcion;
	}



	public void setCveIdCircunscripcion(long cveIdCircunscripcion) {
		this.cveIdCircunscripcion = cveIdCircunscripcion;
	}

	public Date getFecFinCircunscripcion() {
		return this.fecFinCircunscripcion;
	}

	public void setFecFinCircunscripcion(Date fecFinCircunscripcion) {
		this.fecFinCircunscripcion = fecFinCircunscripcion;
	}

	public Date getFecInicioCircunscripcion() {
		return this.fecInicioCircunscripcion;
	}

	public void setFecInicioCircunscripcion(Date fecInicioCircunscripcion) {
		this.fecInicioCircunscripcion = fecInicioCircunscripcion;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Integer getIndCircunscripcionActiva() {
		return indCircunscripcionActiva;
	}

	public void setIndCircunscripcionActiva(Integer indCircunscripcionActiva) {
		this.indCircunscripcionActiva = indCircunscripcionActiva;
	}
	
	public Long getDomicilioIdOrigen() {
		return domicilioIdOrigen;
	}

	public void setDomicilioIdOrigen(Long domicilioIdOrigen) {
		this.domicilioIdOrigen = domicilioIdOrigen;
	}

	public Long getDomicilioIdDestino() {
		return domicilioIdDestino;
	}

	public void setDomicilioIdDestino(Long domicilioIdDestino) {
		this.domicilioIdDestino = domicilioIdDestino;
	}


	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public DitTramite getDitTramiteSuspension() {
		return ditTramiteSuspension;
	}

	public void setDitTramiteSuspension(DitTramite ditTramiteSuspension) {
		this.ditTramiteSuspension = ditTramiteSuspension;
	}
	
	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedico1() {
		return this.ditUmfConsTurnoMedico1;
	}

	public void setDitUmfConsTurnoMedico1(DitUmfConsTurnoMedico ditUmfConsTurnoMedico1) {
		this.ditUmfConsTurnoMedico1 = ditUmfConsTurnoMedico1;
	}
	
	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedico2() {
		return this.ditUmfConsTurnoMedico2;
	}

	public void setDitUmfConsTurnoMedico2(DitUmfConsTurnoMedico ditUmfConsTurnoMedico2) {
		this.ditUmfConsTurnoMedico2 = ditUmfConsTurnoMedico2;
	}
	
}