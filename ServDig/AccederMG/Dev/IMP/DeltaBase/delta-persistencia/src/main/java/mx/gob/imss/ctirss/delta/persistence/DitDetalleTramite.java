package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_DETALLE_TRAMITE database table.
 * 
 */
@Entity
@Table(name="DIT_DETALLE_TRAMITE")
public class DitDetalleTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//    @SequenceGenerator(name = "DIT_DETALLE_TRAMITE_CVEIDTRAMITE_GENERATOR", sequenceName = "SEQ_DITDETALLETRAMITE", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DETALLE_TRAMITE_CVEIDTRAMITE_GENERATOR")
	@Column(name="CVE_ID_TRAMITE")
	private Long cveIdTramite;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Lob()
	@Column(name="REF_DATOS_TRAMITE_XML")
	private String refDatosTramiteXml;

	//bi-directional one-to-one association to DitTramite
	@OneToOne
	@JoinColumn(name="CVE_ID_TRAMITE")
	private DitTramite ditTramite;

    public DitDetalleTramite() {
    }

	public Long getCveIdTramite() {
		return this.cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
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

	public String getRefDatosTramiteXml() {
		return this.refDatosTramiteXml;
	}

	public void setRefDatosTramiteXml(String refDatosTramiteXml) {
		this.refDatosTramiteXml = refDatosTramiteXml;
	}

	public DitTramite getDitTramite() {
		return this.ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}
	
}