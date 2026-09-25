package mx.imss.ctirss.constantes;

public abstract class QuerysCaratula {

	private static final String WHERE_REPORTES =" AND fechasituacion BETWEEN TO_DATE('{2}','{4}') AND TO_DATE('{3}','{4}')";
	public static final String CCP = "SELECT folio,rp,fechasituacion,situacion from v_prueba WHERE ID_SUBDELEGACION={1}"+ WHERE_REPORTES;	
	//public static final String RFPPC = "SELECT * from V_RFPPC WHERE ID_SUBDELEGACION={1}"; 
	public static final String RFPPC = "SELECT * from V_RFPPC"; //NO FUNCIONA
	public static final String RFCPC = "SELECT * from V_RFCPC WHERE ID_SUBDELEGACION={1}";
	public static final String RFCPA = "SELECT * from V_RFCPA WHERE ID_SUBDELEGACION={1}"; 
	public static final String RR = "";// no HAY QUERY
	public static final String CSC = "SELECT * from V_CSC WHERE ID_SUBDELEGACION={1}";
	public static final String CP = "SELECT * from V_CP WHERE ID_SUBDELEGACION={1}";
	public static final String CFCE = "SELECT * from V_CFCEO WHERE ID_SUBDELEGACION={1}";
	public static final String CFCCE = "SELECT * from V_CFCEC WHERE ID_SUBDELEGACION={1}";
	public static final String CFCI = "";//V_CFCCI PENDIENTE
	public static final String CFPO = "SELECT * from V_CFPO WHERE ID_SUBDELEGACION={1}";
	public static final String CFSATICA = "SELECT * from V_CFS WHERE ID_SUBDELEGACION={1}";
	public static final String CFPC = "SELECT * from V_CFPC WHERE ID_SUBDELEGACION={1}";
	public static final String PD = "SELECT * from V_RPR WHERE ID_SUBDELEGACION={1}"; 
	public static final String TD = "SELECT * from V_RT WHERE ID_SUBDELEGACION={1}"; 
	public static final String CORRECCION_DETALLE = "";
	public static final String CORRECCION_RESUMEN = "SELECT * from V_CTCPCD";
	public static final String PROMOCION_DETALLE = "";
	public static final String PROMOCION_RESUMEN = "";
	
	public static String getCompleteMonth(int month){
		if(month<10) return "0"+(month+1);
		else return String.valueOf((month+1));
	}
	
	
}
