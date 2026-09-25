package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmDet;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;

/**
 * Objeto visual el cual nos apoya al momento de consultar las cédulas
 * elaboradas por el patrón, en este caso particular la "Cédula R" [Cotizacion Omitida].
 * 
 * @see ConsultaEstudioCorreccionVO
 * @author Jorge Hernandez Almazan
 * @version 1.0.0
 * 
 */
public class CedulaRVO {
	
	/**
	 * Contiene la clave principal cotizacion omitida
	 */
	public String claveDetBaseCot;
	/**
	 * Contiene el numero de Anexo
	 */
	public String anexoSolCorrpat;
	/**
	 * Contiene el ejercicio correspondiente
	 */
	public String ejercicio;
	/**
	 * Contiene el sueldo de balanza de comprobacion.
	 */
	public String sueldoBalanzaComp;
	/**
	 * Contiene el sueldo anual del ISR
	 */
	public String sueldoAnualISR;
	/**
	 * Contiene la variable mas del sexto bimestre.
	 */
	public String masSextoBimestre;
	/**
	 * Contiene la variable menos del sexto bimestre
	 */
	public String menosSextoBimestre;
	
	/**
	 * Contiene la fecha de Registro
	 */
	public Date fechaReg;
	/**
	 * Contiene la clave de usuario correspondiente
	 */
	public String claveUsuario;
	/**
	 * Contiene la razon social
	 */
	public String razonSocial;
	/**
	 * Contiene la lista de detalles asiganados a la cotizacion imitida
	 */
	List<CrtDetBaseCotOmDet> detalles;

	
	/**
	 * Permite inicializar el objeto a través de una consulta genérica SQL Ansi,
	 * en donde se le pasará un Obj tipo Object y el constructor desdoblará la
	 * información.
	 * 
	 * @param obj
	 * @see ConsultasEstudioCorreccion
	 * @author Jorge Hernandez Almazan
	 */

	 public CedulaRVO(Object[] obj){
		 	int i = 0;
		 	setClaveDetBaseCot(String.valueOf(obj[i++]));
	    	setAnexoSolCorrpat(String.valueOf(obj[i++]));
	    	setEjercicio(String.valueOf(obj[i++]));
	    	setSueldoBalanzaComp(String.valueOf(obj[i++]));
	    	setSueldoAnualISR(String.valueOf(obj[i++]));
	    	setMasSextoBimestre(String.valueOf(obj[i++]));
	    	setMenosSextoBimestre(String.valueOf(obj[i++]));
	    	setFechaReg((Date) (obj[i++]));
	    	setClaveUsuario(String.valueOf(obj[i++]));
	    	setRazonSocial(String.valueOf(obj[i++]));
	    	detalles=new ArrayList<CrtDetBaseCotOmDet>();
	 }
	
	
	 
	public String getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	public String getClaveUsuario() {
		return claveUsuario;
	}
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}


	public String getAnexoSolCorrpat() {
		return anexoSolCorrpat;
	}


	public void setAnexoSolCorrpat(String anexoSolCorrpat) {
		this.anexoSolCorrpat = anexoSolCorrpat;
	}


	public String getSueldoBalanzaComp() {
		return sueldoBalanzaComp;
	}


	public void setSueldoBalanzaComp(String sueldoBalanzaComp) {
		this.sueldoBalanzaComp = sueldoBalanzaComp;
	}


	public String getSueldoAnualISR() {
		return sueldoAnualISR;
	}


	public void setSueldoAnualISR(String sueldoAnualISR) {
		this.sueldoAnualISR = sueldoAnualISR;
	}

	public String getMasSextoBimestre() {
		return masSextoBimestre;
	}

	public void setMasSextoBimestre(String masSextoBimestre) {
		this.masSextoBimestre = masSextoBimestre;
	}


	public String getMenosSextoBimestre() {
		return menosSextoBimestre;
	}


	public void setMenosSextoBimestre(String menosSextoBimestre) {
		this.menosSextoBimestre = menosSextoBimestre;
	}


	public List<CrtDetBaseCotOmDet> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<CrtDetBaseCotOmDet> detalles) {
		this.detalles = detalles;
	}


	public String getClaveDetBaseCot() {
		return claveDetBaseCot;
	}

	public void setClaveDetBaseCot(String claveDetBaseCot) {
		this.claveDetBaseCot = claveDetBaseCot;
	}



	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}



	public Date getFechaReg() {
		return fechaReg;
	}



	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}
	
	

}
