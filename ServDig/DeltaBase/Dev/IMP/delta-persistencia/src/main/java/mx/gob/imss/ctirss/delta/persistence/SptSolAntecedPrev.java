package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptPension;

import java.util.Date;


/**
 * The persistent class for the SPT_SOL_ANTECED_PREV database table.
 * 
 */
@Entity
@Table(name="SPT_SOL_ANTECED_PREV")
public class SptSolAntecedPrev implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_SOL_ANTECED_PREV")
	private long cveIdSolAntecedPrev;

	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptPension
    @ManyToOne
	@JoinColumn(name="CVE_ID_PENSION")
	private SptPension sptPension;

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

    public SptSolAntecedPrev() {
    }

	public long getCveIdSolAntecedPrev() {
		return this.cveIdSolAntecedPrev;
	}

	public void setCveIdSolAntecedPrev(long cveIdSolAntecedPrev) {
		this.cveIdSolAntecedPrev = cveIdSolAntecedPrev;
	}

	public String getCveCuentaUsuario() {
		return this.cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
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

	public SptPension getSptPension() {
		return this.sptPension;
	}

	public void setSptPension(SptPension sptPension) {
		this.sptPension = sptPension;
	}
	
	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
}