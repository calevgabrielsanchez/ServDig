/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

/**
 * @author Lucio Duran Silva
 * 
 */
public class PersonaSinNSSException extends AbstractException {

	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String(
			"La persona no cuenta con NSS");
	
	//Se agregan los datos básicos de la persona por requerimiento para servicios moviles
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private Sexo sexo; 
	private Date fechaNacimiento;
	
	/**
	 * Constructor por omision
	 */
	public PersonaSinNSSException() {
		super(SITUACION_BASE, CODIGO);
	}

	public PersonaSinNSSException(long idPersona) {
		super(SITUACION_BASE + " [idPersona : " + idPersona + "]", CODIGO);

	}
	
	public PersonaSinNSSException(String msg) {
		super(msg, CODIGO);
	}
	
	public PersonaSinNSSException(String nombre, String primerApellido, 
			String segundoApellido, Sexo sexo, Date fechaNacimiento) {
		super(SITUACION_BASE, CODIGO);
		this.nombre=nombre;
		this.primerApellido=primerApellido;
		this.segundoApellido=segundoApellido;
		this.sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
}
