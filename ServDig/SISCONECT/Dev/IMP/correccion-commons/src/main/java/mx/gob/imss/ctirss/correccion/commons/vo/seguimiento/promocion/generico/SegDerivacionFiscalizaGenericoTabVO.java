/**
 * SegDerivacionFiscalizaGenericoTabVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;



/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion Generica para Derivar a Fiscalizacion
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
public class SegDerivacionFiscalizaGenericoTabVO extends ControlTabs implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String referenciaDerFisca;
	private String fecDerivacionGenerica;
	private String funcionarioDerGenerico;
	/**
	 * Retorna el valor referenciaDerFisca
	 * @return  referenciaDerFisca
	 */
	public String getReferenciaDerFisca() {
		return referenciaDerFisca;
	}
	/**
	 * Asigna el valor del referenciaDerFisca al atributo referenciaDerFisca
	 * @param referenciaDerFisca 
	 */
	public void setReferenciaDerFisca(String referenciaDerFisca) {
		this.referenciaDerFisca = referenciaDerFisca;
	}
	/**
	 * Retorna el valor fecDerivacionGenerica
	 * @return  fecDerivacionGenerica
	 */
	public String getFecDerivacionGenerica() {
		return fecDerivacionGenerica;
	}
	/**
	 * Asigna el valor del fecDerivacionGenerica al atributo fecDerivacionGenerica
	 * @param fecDerivacionGenerica 
	 */
	public void setFecDerivacionGenerica(String fecDerivacionGenerica) {
		this.fecDerivacionGenerica = fecDerivacionGenerica;
	}
	/**
	 * Retorna el valor funcionarioDerGenerico
	 * @return  funcionarioDerGenerico
	 */
	public String getFuncionarioDerGenerico() {
		return funcionarioDerGenerico;
	}
	/**
	 * Asigna el valor del funcionarioDerGenerico al atributo funcionarioDerGenerico
	 * @param funcionarioDerGenerico 
	 */
	public void setFuncionarioDerGenerico(String funcionarioDerGenerico) {
		this.funcionarioDerGenerico = funcionarioDerGenerico;
	}
	
	
	

}
