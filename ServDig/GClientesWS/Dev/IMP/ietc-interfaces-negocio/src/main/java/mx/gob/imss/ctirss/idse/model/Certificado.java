package mx.gob.imss.ctirss.idse.model;

public class Certificado implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String claveSerial;	
	private long estatus;
	
	
	
	public String getClaveSerial() {
		return claveSerial;
	}
	public void setClaveSerial(String claveSerial) {
		this.claveSerial = claveSerial;
	}
	public long getEstatus() {
		return estatus;
	}
	public void setEstatus(long estatus) {
		this.estatus = estatus;
	}

}
