package mx.gob.imss.ctirss.correccion.constantes;

public abstract class QuerysCaratula {

//	private static final String WHERE_REPORTES =" AND fechasituacion BETWEEN TO_DATE('{2}','{4}') AND TO_DATE('{3}','{4}')";
	public static final int NUMERO_ANIOS_FISCALES = 10;
	public static final String CCP = "SELECT folio,rp,fechasituacion,cast(situacion as VARCHAR2(3)) from V_CCP WHERE ID_SUBDELEGACION={1}";	
	public static final String RFPPC = "SELECT * from V_RFPPC WHERE ID_SUBDELEGACION={1}"; 
	public static final String RFCPC = "SELECT * from V_RFCPC WHERE ID_SUBDELEGACION={1}"; 
	public static final String RFCPA = "SELECT * from V_RFCPA WHERE ID_SUBDELEGACION={1}"; 
	public static final String RR = "";// no HAY QUERY
	public static final String CSC = "SELECT * from V_CSC WHERE ID_SUBDELEGACION={1}";
	public static final String CP = "SELECT * from V_CP WHERE ID_SUBDELEGACION={1}";
	public static final String CFCE = "SELECT * from V_CFCEO WHERE ID_SUBDELEGACION={1}";
	public static final String CFCCE = "SELECT * from V_CFCEC WHERE ID_SUBDELEGACION={1}";
	public static final String CFCI = "select * from v_cfccpi WHERE ID_SUBDELEGACION={1}";
	public static final String CFPO = "SELECT * from V_CFPO WHERE ID_SUBDELEGACION={1}";
	public static final String CFSATICA = "SELECT * from V_CFS WHERE ID_SUBDELEGACION={1}";
	public static final String CFPC = "SELECT * from V_CFPC WHERE ID_SUBDELEGACION={1}";
	public static final String PD = "SELECT * from V_RPR WHERE ID_SUBDELEGACION={1}"; 
	public static final String TD = "SELECT * from V_RT WHERE ID_SUBDELEGACION={1}"; 
	public static final String CORRECCION_DETALLE = "SELECT * from V_CTCPCD WHERE ID_SUBDELEGACION={1}";
	public static final String CORRECCION_RESUMEN = "SELECT RC.Descripcion," + 
			"		Count(RC.Descripcion) AS TotTipo," + 
			"		Sum(RC.QtyRP) AS TotQtyRP," + 
			"		Sum(RC.tACOPConvSP) As TotACOPConvSP," + 
			"		Sum(RC.TACOPSP) As TotTACOPSP," + 
			"		Sum(RC.TACOPAct) As TotTACOPAct," + 
			"		Sum(RC.TACOPRec) As TotTACOPRec," + 
			"		Sum(RC.TACOPTotal) As TotTACOPTotal," + 
			"		Sum(RC.TACOPMultas) As TotTACOPMultas," + 
			"		Sum(RC.tATrabRev) As TotATrabRevisados," + 
			"		Sum(RC.tATrabajadoresOmisos) AS TotATrabOmisos," + 
			"		Sum(RC.tATrabajadoresSubdeclarados) AS TotATrabSub," + 
			"		Sum(RC.TACOPSPPagada) As TotTACOPSPPagada," +
			"		Sum(RC.TACOPPendientePago) As TotTACOPPendientePago," + 
			"		Sum(RC.tARCVConvSP) As TotARCVConvSP," + 
			"		Sum(RC.TARCVSP) As TotTARCVSP," + 
			"		Sum(RC.TARCVAct) As TotTARCVAct," + 
			"		Sum(RC.TARCVRec) As TotTARCVRec," + 
			"		Sum(RC.TARCVTotal) As TotTARCVTotal," + 
			"		Sum(RC.TARCVMultas) As TotTARCVMultas," + 
			"		Sum(RC.tRCOPConvSP) As TotRCOPConvSP," + 
			"		Sum(RC.TRCOPSP) As TotTRCOPSP," + 
			"		Sum(RC.TRCOPAct) As TotTRCOPAct," + 
			"		Sum(RC.TRCOPRec) As TotTRCOPRec," + 
			"		Sum(RC.TRCOPTotal) As TotTRCOPTotal," + 
			"		Sum(RC.TRCOPMultas) As TotTRCOPMultas," + 
			"		Sum(RC.tRTrabRev) AS TotRTrabRev," + 
			"		Sum(RC.tRTrabajadoresOmisos) AS TotRTrabOmisos," + 
			"		Sum(RC.tRTrabajadoresSubdeclarados) AS TotRTrabSub," + 
			"		Sum(RC.TRCOPSPPagada) As TotTRCOPSPPagada," +
			"		Sum(RC.TRCOPPendientePago) As TotTRCOPPendientePago," + 
			"		Sum(RC.tRRCVConvSP) As TotRRCVConvSP," + 
			"		Sum(RC.TRRCVSP) As TotTRRCVSP," + 
			"		Sum(RC.TRRCVAct) As TotTRRCVAct," + 
			"		Sum(RC.TRRCVRec) As TotTRRCVRec," + 
			"		Sum(RC.TRRCVTotal) As TotTRRCVTotal," + 
			"		Sum(RC.TRRCVMultas) As TotTRRCVMultas " + 
			"	FROM v_ctcpcd RC where ID_SUBDELEGACION={1} Group By RC.Descripcion";
	public static final String PROMOCION_DETALLE = "SELECT * from V_CPPD WHERE ID_SUBDELEGACION={1}";
	public static final String PROMOCION_RESUMEN = "SELECT 	RP.Descripcion," +
			"		Count(RP.Descripcion) As TotTipo," +
			"		Sum(RP.COPConvSP) As  TotCOPConvSP," + 
			"		Sum(RP.TCOPSP) As TotTCOPSP," + 
			"		Sum(RP.TCOPAct) As TotTCOPAct," + 
			"		Sum(RP.TCOPRec) As TotTCOPRec," + 
			"		Sum(RP.TCOPTotal) As TotTCOPTotal," + 
			"		Sum(RP.TCOPMultas) As TotTCOPMultas," + 
			"		Sum(RP.TrabRevisados) As TotTrabRevisados," + 
			"		Sum(RP.TrabOmisos) As TotTrabOmisos," + 
			"		Sum(RP.TrabSubddeclarados) As TotTrabSub," + 
			"		Sum(RP.TCOPSPPagada) As TotTCOPSPPagada," +
			"		Sum(RP.TCOPPendientePago) As TotTCOPPendientePago," + 
			"		Sum(RP.RCVConvSP) As TotRCVConvSP," + 
			"		Sum(RP.TRCVSP) As TotTRCVSP," + 
			"		Sum(RP.TRCVAct) As TotTRCVAct," + 
			"		Sum(RP.TRCVRec) As TotTRCVRec," + 
			"		Sum(RP.TRCVTotal) As TotTRCVTotal" +
			"	FROM V_CPPD  RP where ID_SUBDELEGACION={1} Group By RP.Descripcion";
	
	public static final String RESUMEN_RESULTADOS_COP="select sum(ap.COPSP), sum(ap.COPACT), sum(ap.COPREC) from cgt_anexopagos ap, cgt_correccion c"
														+" where ap.ID_PROCESO = 1 and ap.FOLIO = c.FOLIO and c.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} "
														+" union all"
														+" select sum(ap.COPSP), sum(ap.COPACT), sum(ap.COPREC) from cgt_anexopagos ap, cgt_correccion c"
														+" where ap.ID_PROCESO = 2 and ap.FOLIO = c.FOLIO and c.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} "
														+" union all"
														+" select sum(ap.COPSP), sum(ap.COPACT), sum(ap.COPREC) from cgt_anexopagos ap, cgt_promocion p"
														+" where ap.ID_PROCESO = 3 and ap.FOLIO = p.FOLIO and p.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} "
														+" union all"
														+" select sum(ap.COPSP), sum(ap.COPACT), sum(ap.COPREC) from cgt_anexopagos ap, cgt_promocion p"
														+" where ap.ID_PROCESO = 4 and ap.FOLIO = p.FOLIO and p.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} ";
														
	
	public static final String RESUMEN_RESULTADOS_RCV="select sum(ap.RCVSP), sum(ap.RCVACT), sum(ap.RCVREC) from cgt_anexopagos ap, cgt_correccion c"
											+" where ap.ID_PROCESO = 1 and ap.FOLIO = c.FOLIO and c.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} "
											+" union all"
											+" select sum(ap.RCVSP), sum(ap.RCVACT), sum(ap.RCVREC) from cgt_anexopagos ap, cgt_correccion c"
											+" where ap.ID_PROCESO = 2 and ap.FOLIO = c.FOLIO and c.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} "
											+" union all"
											+" select sum(ap.RCVSP), sum(ap.RCVACT), sum(ap.RCVREC) from cgt_anexopagos ap, cgt_promocion c"
											+" where ap.ID_PROCESO = 3 and ap.FOLIO = c.FOLIO and c.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} "
											+" union all"
											+" select sum(ap.RCVSP), sum(ap.RCVACT), sum(ap.RCVREC) from cgt_anexopagos ap, cgt_promocion c"
											+" where ap.ID_PROCESO = 4 and ap.FOLIO = c.FOLIO and c.ID_SUBDELEGACION = {1} and ap.fechapago BETWEEN {2} AND {3} ";
	
	
	public static final String RESUMEN_RESULTADOS_NUM_TRABAJADORES="select   sum(ATRABREVISADOS), sum(ATRABOMISOS), sum(ATRABSUBDECLARADOS) from cgt_anexorp arp, cgt_correccion c"
																+" where arp.FOLIO = c.folio and c.id_subdelegacion = {1} AND ((AACP BETWEEN {2} AND {3}) OR( AAPP BETWEEN {2} AND {3}))"
																+" union all"
																+" select sum(RTRABREVISADOS), sum(RTRABOMISOS), sum(RTRABSUBDECLARADOS) from cgt_anexorp arp, cgt_correccion c"
																+" where arp.FOLIO = c.folio and c.id_subdelegacion = {1} AND ((AACP BETWEEN {2} AND {3}) OR( AAPP BETWEEN {2} AND {3}))"
																+" union all"
																+" select sum(trabrevisados), sum(trabomisos), sum(TRABSUBDDECLARADOS) from cgt_promocion where id_tipo = 6 and id_subdelegacion = {1} AND PR BETWEEN {2} AND {3}"
																+" union all"
																+" select sum(trabrevisados), sum(trabomisos), sum(TRABSUBDDECLARADOS) from cgt_promocion where id_tipo = 7 and id_subdelegacion = {1} AND PR BETWEEN {2} AND {3}";
	
	public static String getCompleteMonth(int month){
		if(month<10) return "0"+(month+1);
		else return String.valueOf((month+1));
	}
	
	
}
