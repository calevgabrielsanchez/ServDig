package mx.gob.imss.ctirss.delta.model.gestion.integracion.common;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EmailDataWrapper extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String mailServer;
	private String username;
	private String password;
	private String fromAddress;
	private String toAddress;
	private String ccAddress;
	private String bccAddress;
	private String asunto;
	private String mensaje;
	private List<String> nombreArchivosAdjuntos;
	private List<byte[]> archivosAdjuntos;
	private List<String> contentTypeAchivosAdjuntos;

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getNombreArchivosAdjuntos() {
		return nombreArchivosAdjuntos;
	}

	public void setNombreArchivosAdjuntos(List<String> nombreArchivosAdjuntos) {
		this.nombreArchivosAdjuntos = nombreArchivosAdjuntos;
	}

	public List<byte[]> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void setArchivosAdjuntos(List<byte[]> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}

	public List<String> getContentTypeAchivosAdjuntos() {
		return contentTypeAchivosAdjuntos;
	}

	public void setContentTypeAchivosAdjuntos(
			List<String> contentTypeAchivosAdjuntos) {
		this.contentTypeAchivosAdjuntos = contentTypeAchivosAdjuntos;
	}

}
