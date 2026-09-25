package mx.gob.imss.cit.dacvass.servicios.externos.model.general;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponseBean  implements Serializable{
	
	private static final long serialVersionUID = -3443479268786538129L;
	
	
	private String code;
	private String contactEmail;
	private String description;
	private String businessMessage;
	private String reasonPhrase;
	private String uri;
	private String timestamp;
	
	/*codigos de error  y tipo de error por defaul*/
	public static final String codigo200 = "200";
	public static final String codigo201 = "201";
	public static final String codigo202 = "202";
	public static final String codigo204 = "204";
	public static final String codigo206 = "206";
	
	public static final String codigo400 = "400";
	public static final String codigo404 = "404";
	public static final String codigo409 = "409";
	public static final String codigo410 = "410";
	public static final String codigo500 = "500";
	public static final String codigo503 = "503";
	public static final String codigo504 = "504";
	
	public static final String codigo200Descripcion = "OK";
	public static final String codigo201Descripcion = "Created";
	public static final String codigo202Descripcion = "Accepted";
	public static final String codigo204Descripcion = "No Content";
	public static final String codigo206Descripcion = "Partial Content";       
	public static final String codigo400Descripcion = "Bad Request";
	public static final String codigo404Descripcion = "Not Found";
	public static final String codigo409Descripcion = "Conflict";
	public static final String codigo410Descripcion = "Gone";
	public static final String codigo500Descripcion = "Internal Server Error";
	public static final String codigo503Descripcion = "Service Unavailable";
	public static final String codigo504Descripcion = "Gateway Timeout";

	
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBusinessMessage() {
		return businessMessage;
	}
	public void setBusinessMessage(String businessMessage) {
		this.businessMessage = businessMessage;
	}
	
	
	
	public String getReasonPhrase() {
		return reasonPhrase;
	}
	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public ErrorResponseBean(String code, String contactEmail, String description, String businessMessage,
			String reasonPhrase, String uri, String timestamp) {
		super();
		this.code = code;
		this.contactEmail = contactEmail;
		this.description = description;
		this.businessMessage = businessMessage;
		this.reasonPhrase = reasonPhrase;
		this.uri = uri;
		this.timestamp = timestamp;
	}
	
	public ErrorResponseBean(String code,  String description, String businessMessage,
			String reasonPhrase ) {
		super();
		this.code = code;
		this.description = description;
		this.businessMessage = businessMessage;
		this.reasonPhrase = reasonPhrase;
		this.timestamp = new Date().toString();
	}
	
	public ErrorResponseBean() {
		
	}
	
	

}
