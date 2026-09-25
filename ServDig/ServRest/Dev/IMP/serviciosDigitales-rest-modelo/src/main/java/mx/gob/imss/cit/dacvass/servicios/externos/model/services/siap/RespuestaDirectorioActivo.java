package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap;

import java.io.Serializable;

public class RespuestaDirectorioActivo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3618900727240284693L;
	private String distinguishedName;
	private String givenName;
	private String sn;
	private String mail;
	private String CN;
	private String name;
	private String userPrincipalName;
	private String displayName;
	private String description;
	private String matricula;
	private String department;
	private String employeeID;
	private String employedID;
	private String cuentaMetro;

	public String toString(){
		return    "CN=" + CN + "\n" 
				+ "name=" + name + "\n" 
				+ "mail=" + mail + "\n" 
				+ "userPrincipalName:" + userPrincipalName + "\n"
				+ "displayName:" + displayName + "\n"
				+ "givenName:" + givenName + "\n"
				+ "sn:" + sn + "\n"
				+ "description:" + description + "\n"
				+ "distinguishedName:" + distinguishedName;}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCN() {
		return CN;
	}

	public void setCN(String cN) {
		CN = cN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployedID() {
		return employedID;
	}

	public void setEmployedID(String employedID) {
		this.employedID = employedID;
	}

	public String getCuentaMetro() {
		return cuentaMetro;
	}

	public void setCuentaMetro(String cuentaMetro) {
		this.cuentaMetro = cuentaMetro;
	}

	public String getLastName() {
		return sn.split(" ")[0];
	}

	public String getMotherLastName() {
		String s = "";
		for(int i=1;sn!=null&&i<sn.split(" ").length;i++) {
			s+=sn.split(" ")[i] + " " ;
		}

		if(s.length()>0)s=s.substring(0,s.length()-1);
		return s;
	}



}
