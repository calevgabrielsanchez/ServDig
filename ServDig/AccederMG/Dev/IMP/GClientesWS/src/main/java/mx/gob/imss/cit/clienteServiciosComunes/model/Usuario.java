package mx.gob.imss.cit.clienteServiciosComunes.model;

public class Usuario {

	private String tipoIdUsr;
	private String idUsr;
	private boolean isOwner;

	public String getTipoIdUsr() {
		return tipoIdUsr;
	}

	public void setTipoIdUsr(String tipoIdUsr) {
		this.tipoIdUsr = tipoIdUsr;
	}

	public String getIdUsr() {
		return idUsr;
	}

	public void setIdUsr(String idUsr) {
		this.idUsr = idUsr;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

}
