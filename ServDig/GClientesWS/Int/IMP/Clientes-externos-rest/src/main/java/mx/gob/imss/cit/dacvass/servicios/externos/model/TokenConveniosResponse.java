package mx.gob.imss.cit.dacvass.servicios.externos.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TokenConveniosResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3747080867701182144L;

	private String tokenInfo;
	
	public TokenConveniosResponse() {
		
	}

	public String getTokenInfo() {
		return tokenInfo;
	}

	public void setTokenInfo(String tokenInfo) {
		this.tokenInfo = tokenInfo;
	}


	
}
