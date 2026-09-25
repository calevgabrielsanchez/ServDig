package mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model;

/**
 * Clase concreta que permite realizar la utilizacian del modelo
 * que interactua con la tabla CRT_REVPAGOS.
 * 
 * @see AbstractCrtRevPagos
 * @author Marco Antonio Nieto Plett
 */
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.base.model.AbstractCrtRevPagos;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="CRT_REVPAGOS")
@OnSearchLlavePrimaria(atributos="cveRevpagos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtRevPagos extends AbstractCrtRevPagos{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Indica tipo de pago a travas de la cadula de revisian
	 */
	@Transient
	public static final Integer PAGO_CEDULA_REVISION = 1;
	
	/**
	 * Indica tipo de pago a travas de la cadula de validacian
	 */
	@Transient
	public static final Integer PAGO_CEDULA_VALIDACION = 2;
	
	/**
	 * Indica tipo de pago a travas de la promocian fase I
	 */
	@Transient
	public static final Integer PAGO_PROMOCION = 3;
	
	/**
	 * Atributo auxiliar que otorga la descripcian del
	 * registro patronal involucrado en el pago.
	 */
	@Transient
	private String registroPatronal;

	/**
	 * Atributo auxiliar que nos permite manejar la fecha en
	 * la pantalla.
	 */
	@Transient
	private String fechaPagoStr;

	
	
	/**
	 * Otorga el registro patronal asociado al pago
	 * a 10 posiciones.
	 * 
	 * @author Marco Antonio Nieto Plett	  
	 * @return Registro patronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * Permite ingresar el registro patronal asociado al 
	 * pago a 10 posiciones.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param registroPatronal
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * Permite recuperar la fecha en la que se realiza el pago
	 * desde la pantalla para posteriormente convertirla a un
	 * objeto tipo Date
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrtRevPagos
	 * @return DD-MM-YYYY
	 */
	public String getFechaPagoStr() {
		
		if(getFecFechapago()!=null && fechaPagoStr==null){
			this.fechaPagoStr = Functions.dateToString(getFecFechapago());
		}
		return fechaPagoStr;
	}

	/**
	 * Permite ingresar la fecha en formato de texto
	 * utilizando el formato DD-MM-YYYY
	 * @author Marco Antonio Nieto Plett
	 * @param fechaPagoStr
	 */
	public void setFechaPagoStr(String fechaPagoStr) {
		this.fechaPagoStr = fechaPagoStr;
	}
	

}
