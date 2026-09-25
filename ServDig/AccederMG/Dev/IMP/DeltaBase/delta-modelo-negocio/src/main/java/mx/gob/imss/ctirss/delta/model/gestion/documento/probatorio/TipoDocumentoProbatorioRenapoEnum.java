/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Joaquin Ponte Diaz
 *  @Proyecto: delta
 *  @Archivo: TipoDocumentoProbatorioEnum.java 
 *  @Fecha: 11/Jun/2012
 */
public enum TipoDocumentoProbatorioRenapoEnum {
	ACTA_NACIMIENTO(1, "Acta de Nacimiento"),
	DOCUMENTO_MIGRATORIO(3, "Documento Migratorio"),
	CARTA_NATURALIZACION(4, "Carta de Naturalizaci\u00f3n"),
	NUMERO_UNICO_DE_EXTRANJERO(5, "N\u00famero \u00fanico de Extranjero"),
	CERTIFICADO_DE_NACIONALIDAD_MEXICANA(7, "Certificado de Nacionalidad Mexicana"),
	OFICIO_SOLICITANTE_DE_REFUGIADO(8, "Oficio Solicitante de Refugiado"),
	FORMA_MIGRATORIA_TURISTA(9, "Forma Migratoria Turista")	
	;
	
	private Integer codigo;
	private String descripcion;
	
	
	private TipoDocumentoProbatorioRenapoEnum(Integer valor, String descripcion){
		this.codigo=valor;
		this.descripcion = descripcion;
	}
	
	public Integer getValor(){
		return codigo;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
}
