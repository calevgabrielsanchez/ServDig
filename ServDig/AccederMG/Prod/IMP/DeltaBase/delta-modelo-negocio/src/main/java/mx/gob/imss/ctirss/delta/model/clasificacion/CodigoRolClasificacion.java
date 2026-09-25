package mx.gob.imss.ctirss.delta.model.clasificacion;

public enum CodigoRolClasificacion {
	JEFE_DEPTO_DEL(10L), JEFE_DEPTO_SUBDEL(11L), JEFE_OFICINA_DEL(3L),
	JEFE_OFICINA_SUBDEL(4L), VENTANILLA_CLASIF_DEL(6L), VENTANILLA_CLASIF_SUBDEL(5L),
	NORMATIVO_CENTRAL(9L), NORMATIVO_DEL(12L), NORMATIVO_SUBDEL(13L),	
	
	// De acuerdo al requerimiento de Mm 4487140/WO1677390 para los siguientes perfiles aplica:
	// El delegado solo puede tener nivel delegacion
	// El subdelegado y jefe de oficina para cobros solo pueden ser de nivel subdelegacional

//	DELEGADO_SUBDEL(15L),
//	SUBDELEGADO_DEL(16L),
//	JEFE_OFICINA_COBROS_DEL(18L),

	DELEGADO_DEL(14L), 
	SUBDELEGADO_SUBDEL(17L),
	JEFE_OFICINA_COBROS_SUBDEL(19L);

	private Long codigo;

	private CodigoRolClasificacion(Long valor) {
		this.codigo = valor;
	}

	public Long getCodigo() {
		return codigo;
	}
}
