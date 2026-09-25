package mx.gob.imss.ctirss.sso.admonusuarios.dto;

public class AnalistaDictamenDTO extends AbstractResponseExterno {

	private static final long serialVersionUID = -3839813557230320469L;

	private String desUsrCurp;
	private String nomNombre;
	private String nomPaterno;
	private String nomMaterno;
	private String desPuesto;
	private String cveMatricula;
	private String refCorreoElectronico;
	private long cveSsoEstatus;
	private String desEstatus;

	public String getDesUsrCurp() {
		return desUsrCurp;
	}

	public void setDesUsrCurp(String desUsrCurp) {
		this.desUsrCurp = desUsrCurp;
	}

	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNomMaterno() {
		return nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getDesPuesto() {
		return desPuesto;
	}

	public void setDesPuesto(String desPuesto) {
		this.desPuesto = desPuesto;
	}

	public String getCveMatricula() {
		return cveMatricula;
	}

	public void setCveMatricula(String cveMatricula) {
		this.cveMatricula = cveMatricula;
	}

	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	public long getCveSsoEstatus() {
		return cveSsoEstatus;
	}

	public void setCveSsoEstatus(long cveSsoEstatus) {
		this.cveSsoEstatus = cveSsoEstatus;
	}

	public String getDesEstatus() {
		return desEstatus;
	}

	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}

}