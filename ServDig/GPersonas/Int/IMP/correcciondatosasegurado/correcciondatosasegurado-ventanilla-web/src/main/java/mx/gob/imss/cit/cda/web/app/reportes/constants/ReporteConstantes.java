package mx.gob.imss.cit.cda.web.app.reportes.constants;

public interface ReporteConstantes {	
	
	//VSRIABLES DE SESION
	public static final String SES_DATA_REPORTE_GRID = "SES_DATA_REPORTE_GRID";
	
    // COMUNES
	public static final String PARAMETRO_SUBREPORTE_DIR = "SUBREPORT_DIR";
	public static final String PARAMETRO_RUTA_IMAGEN = "RUTA_IMAGEN";
	public static final String IMAGENES_DIR = "images/";

	public static final String TITULO = "TITULO REPORTE";
    
    
    // RUTA BASE REPORTES
    public static final String RUTA_BASE_REPORTES = "/reportes/";
    public static final String REPORTE_CDA_JASPER = RUTA_BASE_REPORTES + "reporteGenerado.jasper";
    public static final String SUBREPORTE_CDA_JASPER = RUTA_BASE_REPORTES + "subReporteGenerado.jasper";
//    public static final String REPORTE_CDA_JASPER = RUTA_BASE_REPORTES + "reporteGenerado.jasper";
    
    // SUB REPORTE   
    public static final String SUBREPORTE_CDA_DIR = "/reportes/";
}
