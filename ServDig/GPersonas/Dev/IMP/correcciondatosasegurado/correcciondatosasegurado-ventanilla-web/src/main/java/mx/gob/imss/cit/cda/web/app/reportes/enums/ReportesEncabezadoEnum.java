package mx.gob.imss.cit.cda.web.app.reportes.enums;


public enum ReportesEncabezadoEnum {

	ENCABEZADO_EXCEL("Reporte CDA", new String[]{
			/* 0 */			"Folio",
			/* 1 */			"CURP",
			/* 2 */		    "NSS involucrados",
			/* 3 */		    "Vencida",
			/* 4 */		    "Delegaci\u00f3n",
			/* 5 */		    "Subdelegaci\u00f3n",
			/* 6 */		    "Autoriz\u00f3",
			/* 7 */		    "Responsable",
			/* 8 */		    "Origen",
			/* 9 */		    "Tipo de tr\u00e1mite",
			/* 10 */		    "Fecha de solicitud",
			/* 11 */		    "Fecha de finalizaci\u00f3n",
			/* 12 */		    "\u00daltima actualizaci\u00f3n",
			/* 13 */		    "Estado"
	}),
	
	//Aun no utilizado, solo se exporta el grid a excel
	ENCABEZADO_PDF("Reporte CDA", new String[]{
			/* 0 */			"Folio",
			/* 1 */			"CURP",
			/* 2 */		    "NSS involucrados",
			/* 3 */		    "Vencida",
			/* 4 */		    "Delegaci\u00f3n",
			/* 5 */		    "Subdelegaci\u00f3n",
			/* 6 */		    "Autoriz\u00f3",
			/* 7 */		    "Responsable",
			/* 8 */		    "Origen",
			/* 9 */		    "Tipo de tr\u00e1mite",
			/* 10 */		    "Fecha de solicitud",
			/* 11 */		    "Fecha de finalizaci\u00f3n",
			/* 12 */		    "\u00daltima actualizaci\u00f3n",
			/* 13 */		    "Estado"
	});
    
    private ReportesEncabezadoEnum(String titulo, String[] encabezado) {
        this.encabezado = encabezado;
        this.titulo = titulo;
    }
    
    private String titulo;
    private String[] encabezado;

    
    public String[] getEncabezado() {
        return encabezado;
    }


	public String getTitulo() {
		return titulo;
	}

}
