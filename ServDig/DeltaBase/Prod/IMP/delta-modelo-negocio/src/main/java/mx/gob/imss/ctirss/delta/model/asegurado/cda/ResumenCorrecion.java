package mx.gob.imss.ctirss.delta.model.asegurado.cda;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * 
 * @author erik.ramirez
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)



public class ResumenCorrecion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353876711389682367L;

	private OrigenInformacion renapo;

   	
	List<DetalleNss> certificador;
	private Map<String, DetalleCorreccionNss>  asociados;
	List<DetalleNss> noCorresponde;
	
	
	public OrigenInformacion getRenapo() {
		return renapo;
	}
	public void setRenapo(OrigenInformacion renapo) {
		this.renapo = renapo;
	}
	public List<DetalleNss> getCertificador() {
		return certificador;
	}
	public void setCertificador(List<DetalleNss> certificador) {
		this.certificador = certificador;
	}
	
	public Map<String, DetalleCorreccionNss> getAsociados() {
		return asociados;
	}
	public void setAsociados(Map<String, DetalleCorreccionNss> detalleAsociado) {
		this.asociados = detalleAsociado;
	}
	public List<DetalleNss> getNoCorresponde() {
		return noCorresponde;
	}
	public void setNoCorresponde(List<DetalleNss> noCorresponde) {
		this.noCorresponde = noCorresponde;
	}
	
	
	
	
	

}
