package mx.gob.imss.ctirss.delta.model.externo.vndi;


import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
public class VndiAccesoPortalWeb extends AbstractModel{

	private static final long serialVersionUID = 686283767401125723L;
	
	private String messageId;
	private String trackId;
	private String recordId;
	private String tramite;
	private String modalidad;
	private String llaveId;
	private String rfc;
	
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getTramite() {
		return tramite;
	}
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getLlaveId() {
		return llaveId;
	}
	public void setLlaveId(String llaveId) {
		this.llaveId = llaveId;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	

}
