package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Adimss extends DocumentoProbatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String folio;
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}
