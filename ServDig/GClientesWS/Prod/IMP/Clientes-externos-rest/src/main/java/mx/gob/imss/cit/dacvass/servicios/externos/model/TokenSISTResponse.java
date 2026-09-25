package mx.gob.imss.cit.dacvass.servicios.externos.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TokenSISTResponse implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1220915477743043695L;
	private String codE;
	private String msgE;
	private String jsonResultado;
	private String salt;
	
	public TokenSISTResponse() {
		
	}

	public String getCodE() {
		return codE;
	}

	public void setCodE(String codE) {
		this.codE = codE;
	}

	public String getMsgE() {
		return msgE;
	}

	public void setMsgE(String msgE) {
		this.msgE = msgE;
	}

	public String getJsonResultado() {
		return jsonResultado;
	}

	public void setJsonResultado(String jsonResultado) {
		this.jsonResultado = jsonResultado;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
}
