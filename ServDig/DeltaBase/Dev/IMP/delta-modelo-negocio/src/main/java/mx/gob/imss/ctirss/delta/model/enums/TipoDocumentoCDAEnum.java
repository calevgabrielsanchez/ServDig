package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoDocumentoCDAEnum {
	
	CERTIFICADO("CERT"), ACUSE("AC");	
	
	private String prefijo;	

	private TipoDocumentoCDAEnum() {
	}
	
	private TipoDocumentoCDAEnum(String prefijo) {
		this.prefijo = prefijo;
	}



	public String getPrefijo() {
		return prefijo;
	}
	
	
	
	

}
