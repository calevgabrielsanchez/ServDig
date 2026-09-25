package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRT_ERROR_CARGA_CED database table.
 * 
 */
@Entity
@Table(name="CRT_ERROR_CARGA_CED")
public class CrtErrorCargaCed  extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ERRORCARGACED")
	private long cveErrorcargaced;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

	@Column(name="TX_ERROR")
	private String txError;

	//bi-directional many-to-one association to CrtControlFlujoCedula
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_CEDULA", referencedColumnName="CVE_CEDULA"),
		@JoinColumn(name="CVE_SOLICITUDCORR", referencedColumnName="CVE_SOLICITUDCORR")
		})
	private CrtControlFlujoCedula crtControlFlujoCedula;

    public CrtErrorCargaCed() {
    }

	public long getCveErrorcargaced() {
		return this.cveErrorcargaced;
	}

	public void setCveErrorcargaced(long cveErrorcargaced) {
		this.cveErrorcargaced = cveErrorcargaced;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getTxError() {
		return this.txError;
	}

	public void setTxError(String txError) {
		this.txError = txError;
	}

	public CrtControlFlujoCedula getCrtControlFlujoCedula() {
		return this.crtControlFlujoCedula;
	}

	public void setCrtControlFlujoCedula(CrtControlFlujoCedula crtControlFlujoCedula) {
		this.crtControlFlujoCedula = crtControlFlujoCedula;
	}
	
}