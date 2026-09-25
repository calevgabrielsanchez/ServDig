/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * @author cesarAgustin
 *
 */
public enum CambioComparacionEnum {
	CAMBIO(1L),
	ELIMINADO(2L),
	NUEVO(3L),
	NINGUNO(4L);
	
	private Long id;
	
	CambioComparacionEnum(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return this.id;
	}
}
