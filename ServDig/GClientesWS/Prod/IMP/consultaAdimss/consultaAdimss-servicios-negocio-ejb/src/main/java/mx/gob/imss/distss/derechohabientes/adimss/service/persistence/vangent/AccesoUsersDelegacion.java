package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the ACCESO_USERS_DELEGACION database table.
 * 
 */
@Embeddable
@Table(name="ACCESO_USERS_DELEGACION")
@NamedQuery(name="AccesoUsersDelegacion.findAll", query="SELECT a FROM AccesoUsersDelegacion a")
public class AccesoUsersDelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Column(name="ID_USER")
	private BigDecimal idUser;

	@Column(name="USER_NAME")
	private String userName;

	public AccesoUsersDelegacion() {
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public BigDecimal getIdUser() {
		return this.idUser;
	}

	public void setIdUser(BigDecimal idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}