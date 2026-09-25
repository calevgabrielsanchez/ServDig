package mx.gob.imss.cit.cda.web.constants;

public enum TipoDocumentoCDAEnum {

    CERTIFICADO("CERT"), ACUSE_INTERNET("AC_IN"), ACUSE_VENTANILLA("AC_VE");

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
