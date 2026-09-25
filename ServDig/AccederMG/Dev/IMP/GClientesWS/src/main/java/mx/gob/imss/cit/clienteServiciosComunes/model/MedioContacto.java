package mx.gob.imss.cit.clienteServiciosComunes.model;

public class MedioContacto {

	private String tipoContacto;
	private String desFormaContacto;
	private String rfc;	

	public MedioContacto(String tipoContacto, String desFormaContacto, String rfc) {
		super();
		this.tipoContacto = tipoContacto;
		this.desFormaContacto = desFormaContacto;
		this.rfc = rfc;
	}

	public String getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(String tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public String getDesFormaContacto() {
		return desFormaContacto;
	}

	public void setDesFormaContacto(String desFormaContacto) {
		this.desFormaContacto = desFormaContacto;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

}
