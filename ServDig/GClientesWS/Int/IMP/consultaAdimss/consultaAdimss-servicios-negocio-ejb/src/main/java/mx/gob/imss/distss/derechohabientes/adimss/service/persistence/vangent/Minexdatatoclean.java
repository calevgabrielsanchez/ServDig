package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the MINEXDATATOCLEAN database table.
 * 
 */
@Embeddable
@NamedQuery(name="Minexdatatoclean.findAll", query="SELECT m FROM Minexdatatoclean m")
public class Minexdatatoclean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String createddate;

	@Column(name="OBJECT_ID")
	private String objectId;

	@Column(name="OBJECT_NAME")
	private String objectName;

	private String username;

	public Minexdatatoclean() {
	}

	public String getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}