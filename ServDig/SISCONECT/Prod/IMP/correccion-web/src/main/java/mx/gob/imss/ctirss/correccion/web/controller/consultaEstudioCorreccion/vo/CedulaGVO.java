/**
 * Permite controlar los elemenentos visuales que involucran
 * a la cédula G  [C.O.P Mensuales]
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;

/**
 * Objeto visual el cual nos apoya al momento de consultar
 * las cédulas elaboradas por el patrón, en este caso
 * particular la "Cédula G" [C.O.P Mensuales].
 * 
 * @see ConsultaEstudioCorreccionVO
 * @author Marco Antonio Nieto Plett
 * @version 1.0.3
 *
 */
public class CedulaGVO {
	
	/**
	 * Registro Patronal Asociado a la C.O.P
	 */
	private String registroPatronal;
	
	/**
	 * Descripción del mes
	 */
	private String txMes;
	
	/**
	 * Cuota Fija pagada por el patrón
	 */
	private String imCuotaFija;
	
	/**
	 * Excedente pagado por el patrón
	 */
	private String imCuotaExced3SMGDF;
	
	/**
	 * Prestamos otorgados al patrón
	 */
	private String imCuotaPrestDinero;
	
	/**
	 * Gastos Médicos
	 */
	private String imCuotaGtosMedPen;
	
	/**
	 * Riesgo de Trabajo
	 */
	private String imCuotaRiesgosTrabajo;
	
	/**
	 * INvalidez y Vida
	 */
	private String imCuotaInvalidezVida;
	
	/**
	 * Base de las C.O.P
	 */
	private String imCuotaGuardPrest;
	
	/**
	 * RCV Retiro
	 */
	private String imCuotaRCVRetiro;
	
	/**
	 * RCV Vejez
	 */
	private String imCuotaRCVCesantia;
	
	/**
	 * Clave de base de datos del mes
	 */
	private Integer cveMes;
	
	/**
	 * Constructor por defecto
	 * @author Marco Antonio Nieto Plett
	 */
	public CedulaGVO(){}
	
	/**
     * Permite inicializar el objeto a través
     * de una consulta genérica SQL Ansi, en 
     * donde se le pasará un Obj tipo Object
     * y el constructor desdoblará la información.
     * 
     * @param obj
     * @see ConsultasEstudioCorreccion
     * @author Marco Antonio Nieto Plett
     */
	public CedulaGVO(Object[] obj){
		
		int i=0;
		
		setRegistroPatronal(String.valueOf(obj[i++]));
		setTxMes(String.valueOf(obj[i++]));
		setImCuotaFija(String.valueOf(obj[i++]));
		setImCuotaExced3SMGDF(String.valueOf(obj[i++]));
		setImCuotaPrestDinero(String.valueOf(obj[i++]));
		setImCuotaGtosMedPen(String.valueOf(obj[i++]));
		setImCuotaRiesgosTrabajo(String.valueOf(obj[i++]));
		setImCuotaInvalidezVida(String.valueOf(obj[i++]));
		setImCuotaGuardPrest(String.valueOf(obj[i++]));
		setImCuotaRCVRetiro(String.valueOf(obj[i++]));
		setImCuotaRCVCesantia(String.valueOf(obj[i++]));
		setCveMes(Integer.valueOf(String.valueOf(obj[i++])));
		
		
	}
	
	/**
	 * Otorga la descripción del mes 
	 * de enero a diciembre
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @return Cade con el mes
	 */
	public String getTxMes() {
		return txMes;
	}
	
	/**
	 * Permite ingresar la descripción del mes
	 * en formato enero a diciembre
	 * @author Marco Antonio Nieto Plett
	 * @param txMes
	 */
	public void setTxMes(String txMes) {
		this.txMes = txMes;
	}
	
	/**
	 * Otorga la cuota fija pagada por
	 * el patrón en el mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaFija() {
		return imCuotaFija;
	}
	
	/**
	 * Permite ingresar la cuota fija
	 * pagada por el patrón en el mes
	 * correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaFija
	 */
	public void setImCuotaFija(String imCuotaFija) {
		this.imCuotaFija = imCuotaFija;
	}
	
	/**
	 * Otorga los excedentes del patrón
	 * en el més correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaExced3SMGDF() {
		return imCuotaExced3SMGDF;
	}
	
	/**
	 * Permite ingresar la cuota excedente del patrón
	 * en el mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaExced3SMGDF
	 */
	public void setImCuotaExced3SMGDF(String imCuotaExced3SMGDF) {
		this.imCuotaExced3SMGDF = imCuotaExced3SMGDF;
	}
	
	/**
	 * Otorga las prestaciones en dinero que el
	 * patrón declara en el mes correspondeinte.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaPrestDinero() {
		return imCuotaPrestDinero;
	}
	
	/**
	 * Permite ingresar las prestaciones de
	 * dinero que el patrón generó en el 
	 * mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaPrestDinero
	 */
	public void setImCuotaPrestDinero(String imCuotaPrestDinero) {
		this.imCuotaPrestDinero = imCuotaPrestDinero;
	}
	
	/**
	 * Otorga los gastos médicos relalizados por el
	 * patrón en el més correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 * 
	 */
	public String getImCuotaGtosMedPen() {
		return imCuotaGtosMedPen;
	}
	
	/**
	 * Permite ingresar los gastos médicos relalizados por el
	 * patrón en el més correspondiente.
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaGtosMedPen
	 */
	public void setImCuotaGtosMedPen(String imCuotaGtosMedPen) {
		this.imCuotaGtosMedPen = imCuotaGtosMedPen;
	}
	
	/**
	 * Otorga las cuotas de riesgo de trabajo del patrón
	 * declaradas en el mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaRiesgosTrabajo() {
		return imCuotaRiesgosTrabajo;
	}
	
	/**
	 * Otorga las cuotas de riesgo de trabajo del patrón
	 * declaradas en el mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaRiesgosTrabajo
	 */
	public void setImCuotaRiesgosTrabajo(String imCuotaRiesgosTrabajo) {
		this.imCuotaRiesgosTrabajo = imCuotaRiesgosTrabajo;
	}
	
	/**
	 * Otorga las cuotas declaradas por el patron
	 * de Invalidez Vida en el mes correspondiente
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaInvalidezVida() {
		return imCuotaInvalidezVida;
	}
	
	/**
	 * Permite ingresar las cuotas declaradas por el patron
	 * de Invalidez Vida en el mes correspondiente
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaInvalidezVida
	 */
	public void setImCuotaInvalidezVida(String imCuotaInvalidezVida) {
		this.imCuotaInvalidezVida = imCuotaInvalidezVida;
	}
	
	/**
	 * Otorga las cuotas de Guarderías declaradas
	 * por el patrón en el mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaGuardPrest() {
		return imCuotaGuardPrest;
	}
	
	/**
	 * Permite ingresar las cuotas de Guarderías declaradas
	 * por el patrón en el mes correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaGuardPrest
	 */
	public void setImCuotaGuardPrest(String imCuotaGuardPrest) {
		this.imCuotaGuardPrest = imCuotaGuardPrest;
	}
	
	/**
	 * Otorga el RCV de retiro 
	 * generado en el periodo y bimestre
	 * correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaRCVRetiro() {
		return imCuotaRCVRetiro;
	}
	
	/**
	 * Permite ingresar el RCV de retiro
	 * generado en el periodo y bimestre
	 * correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaRCVRetiro
	 */
	public void setImCuotaRCVRetiro(String imCuotaRCVRetiro) {
		this.imCuotaRCVRetiro = imCuotaRCVRetiro;
	}
	
	/**
	 * Otorga las cuotas RCV de Cesantia
	 * y Vejes generadas en el bimestre 
	 * correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getImCuotaRCVCesantia() {
		return imCuotaRCVCesantia;
	}
	
	/**
	 * Permite ingresar las cuotas RCV de Cesantia
	 * y Vejes generadas en el bimestre 
	 * correspondiente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param imCuotaRCVCesantia
	 */
	public void setImCuotaRCVCesantia(String imCuotaRCVCesantia) {
		this.imCuotaRCVCesantia = imCuotaRCVCesantia;
	}

	/**
	 * Otorga al registro patronal asocioado
	 * al las C.O.P pagadas.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	
	/**
	 * Permite ingresar al registro patronal asocioado
	 * al las C.O.P pagadas.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param registroPatronal
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * Otorga la clave del mes asociada
	 * a la descripción del mes.
	 * 
	 * @see getTxMes()
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public Integer getCveMes() {
		return cveMes;
	}

	/**
	 * Permite Ingresar la clave del mes asociada
	 * a la descripción del mes.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param cveMes
	 */
	public void setCveMes(Integer cveMes) {
		this.cveMes = cveMes;
	}
	

}
