/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * @author guillermo.hernandezd
 *
 */
public enum ServiciosPrestacionesEnum {
	

	SERVICIO_MEDICO(1),
	REGISTRO_BENEFICIARIOS(2),
	EXPEDICION_INCAPACIDAD_TRABAJO(3),
	SERVICIO_GUARDERIA(4);
	
	private long id;
	
	ServiciosPrestacionesEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
