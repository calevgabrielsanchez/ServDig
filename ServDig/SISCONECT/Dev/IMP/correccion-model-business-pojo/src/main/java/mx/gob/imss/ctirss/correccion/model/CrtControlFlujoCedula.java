package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;



/**
 * The persistent class for the CRT_CONTROL_FLUJO_CEDULAS database table.
 * 
 */
@Entity
@Table(name="CRT_CONTROL_FLUJO_CEDULAS")
public class CrtControlFlujoCedula extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CrtControlFlujoCedulaPK id;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

	//bi-directional many-to-one association to CrtEstatusFlujoCedula
    @ManyToOne
	@JoinColumn(name="CVE_ESTATUS")
	private CrtEstatusFlujoCedula crtEstatusFlujoCedula;

	//bi-directional many-to-one association to CrtNombreCedula
    @ManyToOne
	@JoinColumn(name="CVE_CEDULA", updatable=false, insertable=false)
	private CrtNombreCedula crtNombreCedula;

	//bi-directional many-to-one association to CrtSolicitudcorr
    @ManyToOne
	@JoinColumn(name="CVE_SOLICITUDCORR", updatable=false, insertable=false)
	private CrtSolicitudcorr crtSolicitudcorr;

    /**
	//bi-directional many-to-one association to CrtErrorCargaCed
	@OneToMany(mappedBy="crtControlFlujoCedula")
	private Set<CrtErrorCargaCed> crtErrorCargaCeds;
*/
    
    @Column(name="CTL_AVANCE_CARGA")
    private Float porcentajeAvance;
    
    public CrtControlFlujoCedula() {
    }

	public CrtControlFlujoCedulaPK getId() {
		return this.id;
	}

	public void setId(CrtControlFlujoCedulaPK id) {
		this.id = id;
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

	public CrtEstatusFlujoCedula getCrtEstatusFlujoCedula() {
		return this.crtEstatusFlujoCedula;
	}

	public void setCrtEstatusFlujoCedula(CrtEstatusFlujoCedula crtEstatusFlujoCedula) {
		this.crtEstatusFlujoCedula = crtEstatusFlujoCedula;
	}
	
	public CrtNombreCedula getCrtNombreCedula() {
		return this.crtNombreCedula;
	}

	public void setCrtNombreCedula(CrtNombreCedula crtNombreCedula) {
		this.crtNombreCedula = crtNombreCedula;
	}
	
	public CrtSolicitudcorr getCrtSolicitudcorr() {
		return this.crtSolicitudcorr;
	}

	public void setCrtSolicitudcorr(CrtSolicitudcorr crtSolicitudcorr) {
		this.crtSolicitudcorr = crtSolicitudcorr;
	}

	public Float getPorcentajeAvance() {
		if(porcentajeAvance==null){
			return 0.0f;
		}else{
			return porcentajeAvance;			
		}
		
	}

	public void setPorcentajeAvance(Float porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}


	
	
	/**
	
	public Set<CrtErrorCargaCed> getCrtErrorCargaCeds() {
		return this.crtErrorCargaCeds;
	}

	public void setCrtErrorCargaCeds(Set<CrtErrorCargaCed> crtErrorCargaCeds) {
		this.crtErrorCargaCeds = crtErrorCargaCeds;
	}
	**/
}