/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.util;

/**
 * @author francisco.rodriguez
 * 
 */
public enum IncidenciasEnum {

	CANCELACION(1, "Cancelacion"), 
	SUSPENSION(2, "Suspension"), 
	TERMINACION(3, "Terminacion"), 
	ACTUALIZACION(4, "Actualizacion"), 
	REANUDACION(5,"Reanudacion");

	private int valor;
	private String nombre;

	IncidenciasEnum(int valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}

	public int getValor() {
		return valor;
	}

	public String getNombre() {
		return nombre;
	}

}
