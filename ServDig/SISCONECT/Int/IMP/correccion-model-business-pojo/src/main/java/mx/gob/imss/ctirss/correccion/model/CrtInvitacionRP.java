package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@Table(name="CRT_INVITACION_RP")
public class CrtInvitacionRP extends AbstractModel{

	private Long cveInvitacion;
	private Long cveInvitacionRP;
	private Long cveFkPatron;
	/**
	 * @return the cveInvitacion
	 */
	@Column(name = "CVE_INVITACION")
	public Long getCveInvitacion() {
		return cveInvitacion;
	}
	/**
	 * @param cveInvitacion the cveInvitacion to set
	 */
	public void setCveInvitacion(Long cveInvitacion) {
		this.cveInvitacion = cveInvitacion;
	}
	/**
	 * @return the cveInvitacionRP
	 */
	@Id
	@SequenceGenerator(name="CVE_INVITACIONRP_GENERATOR", sequenceName="CRS_CVE_INVITACIONRP")
	@GeneratedValue(generator="CVE_INVITACIONRP_GENERATOR")
	@Column(name = "CVE_INVITACIONRP")
	public Long getCveInvitacionRP() {
		return cveInvitacionRP;
	}
	/**
	 * @param cveInvitacionRP the cveInvitacionRP to set
	 */
	public void setCveInvitacionRP(Long cveInvitacionRP) {
		this.cveInvitacionRP = cveInvitacionRP;
	}
	/**
	 * @return the cveFkPatron
	 */
	@Column(name = "CVE_FK_PATRON")
	public Long getCveFkPatron() {
		return cveFkPatron;
	}
	/**
	 * @param cveFkPatron the cveFkPatron to set
	 */
	public void setCveFkPatron(Long cveFkPatron) {
		this.cveFkPatron = cveFkPatron;
	}
	

}
