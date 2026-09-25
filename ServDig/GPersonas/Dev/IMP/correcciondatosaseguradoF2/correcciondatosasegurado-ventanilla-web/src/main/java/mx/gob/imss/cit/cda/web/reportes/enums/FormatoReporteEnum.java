package mx.gob.imss.cit.cda.web.reportes.enums;

public enum FormatoReporteEnum {

    PDF("pdf", "Portable Document Format (PDF)", "application/pdf"), EXCEL(
            "xls", "Excel (XLS)", "application/vnd.ms-excel"), HTML("html",
                    "HyperText Markup Language (HTML)", "text/html"), CSV("csv",
                            "Comma-separated values (CSV)",
                            "text/plain"), RTF("rtf", "Rich-Text Format (RTF)",
                                    "application/msword"), TXT("txt",
                                            "Plain text (TXT)",
                                            "text/plain"), XLSX("xlsx", "XLSX",
                                                    "aapplication/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), ZIP(
                                                            "zip",
                                                            "Compression format (ZIP)",
                                                            "application/zip");

    private String id;
    private String description;
    private String contentType;

    FormatoReporteEnum(final String id, final String description,
            final String contentType) {
        this.id = id;
        this.description = description;
        this.contentType = contentType;
    }

    public String getId() {

        return id;
    }

    public String getDescription() {

        return description;
    }

    public String getContentType() {

        return contentType;
    }

}
