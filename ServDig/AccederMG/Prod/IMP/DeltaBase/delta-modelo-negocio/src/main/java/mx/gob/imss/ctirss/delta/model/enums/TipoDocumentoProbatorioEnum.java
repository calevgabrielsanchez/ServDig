package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoDocumentoProbatorioEnum {
	IDENTIFICACION(1),
	ACTAS(2),
	COMPROBANTES_DE_DOMICILIO(3),
	OTROS(4),
	EXTENSION_VIGENCIA(5),
	RESULTANTE(6),
	RENAPO(7),
	CONSTANCIAS(9),
	DOCUMENTOS_CON_NSS(11),
	DOCUMENTO_PROBATORIO_JUDICIAL(14),
	DOCUMENTO_PROBATORIO_DEL_REPRESENTANTE_LEGAL(17),
	DOCUMENTO_PROBATORIO_REGISTRO_CPA(15),
	FORMATOS(16);
	
	private long id;

	TipoDocumentoProbatorioEnum(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
}
