package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_HIST_TRAMITE_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_HIST_TRAMITE_PENSION")
public class SptHistTramitePension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTHISTTRAMITEPENSION", sequenceName = "SEQ_SPTHISTTRAMITEPENSION")
	@GeneratedValue(generator = "SEQ_SPTHISTTRAMITEPENSION")	
	@Column(name="CVE_HIST_TRAMITE_PENSION")
	private Long cveHistTramitePension;
	
	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

	@Column(name="CVE_ID_ESTADO_TRAMITE")
	private BigDecimal cveIdEstadoTramite;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_CAMBIO")
	private BigDecimal numCambio;

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

    public SptHistTramitePension() {
    }

	public Long getCveHistTramitePension() {
		return this.cveHistTramitePension;
	}

	public void setCveHistTramitePension(Long cveHistTramitePension) {
		this.cveHistTramitePension = cveHistTramitePension;
	}

	public String getCveCuentaUsuario() {
		return this.cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
	}

	public BigDecimal getCveIdEstadoTramite() {
		return this.cveIdEstadoTramite;
	}

	public void setCveIdEstadoTramite(BigDecimal cveIdEstadoTramite) {
		this.cveIdEstadoTramite = cveIdEstadoTramite;
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

	public BigDecimal getNumCambio() {
		return this.numCambio;
	}

	public void setNumCambio(BigDecimal numCambio) {
		this.numCambio = numCambio;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
}
