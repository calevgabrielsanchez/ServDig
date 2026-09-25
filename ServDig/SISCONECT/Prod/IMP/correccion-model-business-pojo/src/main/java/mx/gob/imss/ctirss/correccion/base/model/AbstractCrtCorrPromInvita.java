package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;

@MappedSuperclass
public class AbstractCrtCorrPromInvita extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CVE_SOLICITUD_PROMINVITA", sequenceName = "CRS_CVE_CORRPROMINVITA")
	@GeneratedValue(generator = "CVE_SOLICITUD_PROMINVITA")
	@Column(name = "CVE_CORRPROMINVITA")
	private Integer cvePK;

	@ManyToOne
	@JoinColumn(name = "CVE_SOLICITUDCORR")
	private CrtSolicitudcorr crtSolicitudcorr;

	@ManyToOne
	@JoinColumn(name = "CVE_PROMOCION")
	private CrtPromocion crtPromocion;

	@ManyToOne
	@JoinColumn(name = "CVE_INVITACION")
	private CrtInvitacion crtInvitacion;

	public Integer getCvePK() {
		return cvePK;
	}

	public void setCvePK(Integer cvePK) {
		this.cvePK = cvePK;
	}

	public CrtInvitacion getCrtInvitacion() {
		return crtInvitacion;
	}

	public void setCrtInvitacion(CrtInvitacion crtInvitacion) {
		this.crtInvitacion = crtInvitacion;
	}

	public CrtSolicitudcorr getCrtSolicitudcorr() {
		return crtSolicitudcorr;
	}

	public void setCrtSolicitudcorr(CrtSolicitudcorr crtSolicitudcorr) {
		this.crtSolicitudcorr = crtSolicitudcorr;
	}

	public CrtPromocion getCrtPromocion() {
		return crtPromocion;
	}

	public void setCrtPromocion(CrtPromocion crtPromocion) {
		this.crtPromocion = crtPromocion;
	}

}
