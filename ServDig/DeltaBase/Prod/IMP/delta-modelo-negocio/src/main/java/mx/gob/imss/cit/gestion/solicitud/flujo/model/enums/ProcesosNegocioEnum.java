package mx.gob.imss.cit.gestion.solicitud.flujo.model.enums;

/**
 * @author cesarAgustin
 *
 */
public enum ProcesosNegocioEnum {
	CDA(1L),
	SEMANAS_COTIZADAS(2L);

	private Long id;

	ProcesosNegocioEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}
}
