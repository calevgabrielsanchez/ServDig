package mx.gob.imss.csdiss.sdroc.util;

public enum ExtensionEnum {

	PDF(".pdf", "application/pdf"), XLS(".xls", "application/vnd.ms-excel"), JRXML(".jrxml"), JASPER(".jasper");
	
	private String extension;
	private String contentType;
	
	ExtensionEnum(String extension) {
		this.extension = extension;
	}
	
	ExtensionEnum(String extension, String contentType) {
		this.extension = extension;
		this.contentType = contentType;
	}

	public String getExtension() {
		return extension;
	}
	
	public String getContentType() {
		return contentType;
	}
	
}
