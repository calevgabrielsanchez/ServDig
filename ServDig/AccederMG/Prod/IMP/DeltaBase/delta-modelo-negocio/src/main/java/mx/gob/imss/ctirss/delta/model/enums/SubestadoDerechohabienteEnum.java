/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * @author ghdolores
 *
 */
public enum SubestadoDerechohabienteEnum {
TEMPORAL(1),FALLECIMIENTO(2),PERMANENTE(3), BAJA_ADMINISTRATIVA(5), SUSPENCION_ADMINISTRATIVA(6), LAUDO(7), ACUERDO(8);
	
	private long id;

	SubestadoDerechohabienteEnum(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
}
