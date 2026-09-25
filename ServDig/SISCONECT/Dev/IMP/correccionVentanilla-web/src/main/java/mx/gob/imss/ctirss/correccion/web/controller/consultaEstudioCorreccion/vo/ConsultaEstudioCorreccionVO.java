/**
 * Objeto visual que controla la pantalla de la consulta
 * del estudio de corrección.
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.ConsultaEstudioCorreccionController;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaA;


/**
 * Permite controlar las búsquedas por sus campos obligatorios:
 * Folio de Corrección, Periodo; y sus campos opcionales RP.
 * Del estudio de corrección.
 * 
 * Todas las Cédulas son cargadas por medio de request
 * y en una sola llamada.
 *  
 * @see ConsultaEstudioCorreccionController
 * @version 1.3.1 
 * @author Marco Antonio Nieto Plett
 * @author Gerardo Salazar Vega
 *
 */
public class ConsultaEstudioCorreccionVO extends ControlTabs{
	
	/**
	 * Folio asignado al estudio de corrección
	 */
	private String folioCorreccion;
	
	/**
	 * Periodo asociado al estudio de corrección
	 */
	private String periodo;
	
	/**
	 * Registro Patronal asociado al estudio
	 * de corrección
	 */
	private String registroPatronal;

	/**
	 * Subdelegacion a la que pertenece el usuario
	 */	
	private String idSubDelegacion;

	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la cédula A
	 */
	private StringBuffer tablaCedulaA;
	
	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la cédula G
	 */
	private StringBuffer tablaCedulaG;
	
	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la cédula I
	 */
	private StringBuffer tablaCedulaI;
	
	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la cédula H
	 */
	private StringBuffer tablaCedulaH;
	
	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la cédula Q
	 */
	private StringBuffer tablaCedulaQ;
	
	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la cédula O
	 */
	private StringBuffer tablaCedulaO;

	/**
	 * Contiene la tabla que se muestra en la 
	 * capa visual (JSP) de la tabla COP
	 */
	private StringBuffer tablaCop;
	
	
	private StringBuffer tablaCedulaR;
	

	/**
	 * Texto constante para mostrar una tabla con mensaje cuando no hay datos.
	 */
	public final static String tablaSinDatos = "<table width='900'  border='0' cellspacing='0' cellpadding='0'>\n"
			+ "<tr>\n"
			+ "<td class='fondoGeneralTabla'><table width='900' border='0' cellspacing='1' cellpadding='1'>\n"
			+ "<tr>\n"
			+ "<td><table width='100%'  border='0' cellspacing='0' cellpadding='0'>\n"
			+ "<tr class='header'>\n"
			+ "<td colspan='2' align='center'>No hay informaci&oacute;n para el n&uacute;mero de folio solicitado </td>\n"
			+ "</tr>\n" + "</table></td>\n" + "</tr>\n"
			+ "</table></td>\n</tr>\n"
			+ "</table>";
	
	/**
	 * Permite obtener el folio del 
	 * estudio de corrección
	 * 
	 * @see ConsultaEstudioCorreccionController
	 * @return Folio 10 Posiciones
	 * @author Marco Antonio Nieto Plett
	 */
	public String getFolioCorreccion() {
		return folioCorreccion;
	}
	
	/**
	 * Permite ingresar el folio del
	 * estudio de corrección a 10 posiciones
	 * 
	 * @param folioCorreccion
	 * @see ConsultaEstudioCorreccionController
	 * @author Marco Antonio Nieto Plett
	 */
	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}

	/**
	 * Permite obtener el periodo del estudio
	 * de corrección a 4 posiciones (YYYY)
	 * @see ConsultaEstudioCorreccionController
	 * @return YYYY
	 * @author Marco Antonio Nieto Plett
	 */
	public String getPeriodo() {
		return periodo;
	}
	
	/**
	 * Permite ingresar el periodo del estudio de
	 * corrección a 4 posiciones YYYY
	 * @param periodo
	 * @see ConsultaEstudioCorreccionController
	 * @author Marco Antonio Nieto Plett
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * Permite obtener el registro patronal
	 * del estudio de corrección de la JSP
	 * de búsqueda.
	 * @see ConsultaEstudioCorreccionController
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * Permite ingresar el registro patronal por
	 * el cual se va a realiar la búsqueda
	 * desde la JSP.
	 * @param registroPatronal
	 * @see ConsultaEstudioCorreccionController
	 * @author Marco Antonio Nieto Plett
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).
	 * @return
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaA
	 * @author Marco Antonio Nieto Plett
	 */
	public StringBuffer getTablaCedulaA() {
		return tablaCedulaA;
	}

	/**
	 * Permite ingresar la tabla generada de la 
	 * cédula A
	 * @param tablaCedulaA
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaA
	 * @author Marco Antonio Nieto Plett
	 */
	public void setTablaCedulaA(StringBuffer tablaCedulaA) {
		this.tablaCedulaA = tablaCedulaA;
	}

	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).
	 * @return
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaA
	 * @author Marco Antonio Nieto Plett
	 */
	public StringBuffer getTablaCedulaG() {
		return tablaCedulaG;
	}

	/**
	 * Permite ingresar la tabla generada de la 
	 * cédula G
	 * @param tablaCedulaG
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaG
	 * @author Marco Antonio Nieto Plett
	 */
	public void setTablaCedulaG(StringBuffer tablaCedulaG) {
		this.tablaCedulaG = tablaCedulaG;
	}

	/**
	 * Metodo que obtiene el valor del atributo  tablaCedulaH
	 * @return  tablaCedulaH
	 */
	public StringBuffer getTablaCedulaH() {
		return tablaCedulaH;
	}

	/**
	 * Metodo que asigna un valor al atributo tablaCedulaH
	 * @param tablaCedulaH the tablaCedulaH to set
	 */
	public void setTablaCedulaH(StringBuffer tablaCedulaH) {
		this.tablaCedulaH = tablaCedulaH;
	}


	
	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).
	 * @return
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaI
	 * @author Marco Antonio Nieto Plett
	 */
	public StringBuffer getTablaCedulaI() {
		return tablaCedulaI;
	}

	/**
	 * Permite ingresar la tabla generada de la 
	 * cédula I
	 * @param tablaCedulaI
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaI
	 * @author Marco Antonio Nieto Plett
	 */
	public void setTablaCedulaI(StringBuffer tablaCedulaI) {
		this.tablaCedulaI = tablaCedulaI;
	}

	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).
	 * @return
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaQ
	 * @author Gerardo Salazar Vega
	 */	
	public StringBuffer getTablaCedulaQ() {
		return tablaCedulaQ;
	}

	/**
	 * Permite ingresar la tabla generada de la 
	 * cédula Q
	 * @param tablaCedulaQ
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaQ
	 * @author Gerardo Salazar Vega
	 */
	public void setTablaCedulaQ(StringBuffer tablaCedulaQ) {
		this.tablaCedulaQ = tablaCedulaQ;
	}

	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).
	 * @return
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaO
	 * @author Enrique Duran Jimenez
	 */	
	public StringBuffer getTablaCedulaO() {
		return tablaCedulaO;
	}

	/**
	 * Permite ingresar la tabla generada de la 
	 * cédula O
	 * @param tablaCedulaO
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCedulaO
	 * @author Enrique Duran Jimenez
	 */
	public void setTablaCedulaO(StringBuffer tablaCedulaO) {
		this.tablaCedulaO = tablaCedulaO;
	}
	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).
	 * @return
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCop
	 * @author Gerardo Salazar Vega
	 */	
	public StringBuffer getTablaCop() {
		return tablaCop;
	}

	/**
	 * Permite ingresar la tabla generada de la 
	 * consulta COP
	 * @param tablaCop
	 * @see ConsultaEstudioCorreccionController
	 * @see GeneraTablaCop
	 * @author Gerardo Salazar Vega
	 */
	public void setTablaCop(StringBuffer tablaCop) {
		this.tablaCop = tablaCop;
	}

	
	/**
	 * Devuelve la subdelegacion a la que pertenece el usuario.
	 * 
	 * @return idSubDelegacion
	 * @author Gerardo Salazar Vega
	 */
	public String getIdSubDelegacion() {
		return idSubDelegacion;
	}

	/**
	 * Asigna la subdelegacion a la que pertenece el usuario.
	 * 
	 * @param idSubDelegacion 
	 * @author Gerardo Salazar Vega
	 */
	public void setIdSubDelegacion(String idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	
	
	/**
	 * Permite obtener un string el cual contiene
	 * todos los datos recuperados a través de la
	 * búsqueda realizada en pantalla en un formato
	 * tipo tabla con hoja de estilo (CSS).Para la cedula R
	 * @return Tabla R
	 * @see ConsultaEstudioCorreccionController
	 * @author Jorge Hernandez Almazan
	 */	
	public StringBuffer getTablaCedulaR() {
		return tablaCedulaR;
	}


	/**
	 * Permite ingresar la tabla generada de la cedula R
	 * @param tablaCedulaR
	 */
	
	public void setTablaCedulaR(StringBuffer tablaCedulaR) {
		this.tablaCedulaR = tablaCedulaR;
	}	
	
	
}
