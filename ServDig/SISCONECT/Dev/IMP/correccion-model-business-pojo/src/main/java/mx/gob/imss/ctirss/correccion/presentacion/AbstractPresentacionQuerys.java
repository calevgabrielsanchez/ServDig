package mx.gob.imss.ctirss.correccion.presentacion;

public class AbstractPresentacionQuerys {
	
	public static String PAGO_COPS_RPS_INSCRITOS ="SELECT patron.NUM_REGISTROPATRONAL,"
														       + " SUM(cops.IMP_COP),SUM(cops.IMP_COPACT),SUM(cops.IMP_COPREC),SUM(cops.IMP_COPTOT),"
														       + " SUM(cops.IMP_RCV),SUM(cops.IMP_RCVACT),SUM(cops.IMP_RCVREC),SUM(cops.IMP_RCVTOT)"
														+ " FROM CRT_COPPAGADAS cops,"
														     + " CRT_ANEXOSOLCORRPAT anexo,"
														     + " SAT_PATRON patron"
														+ " WHERE anexo. CVE_SOLICITUDCORR = {1}"
														+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
														+ " AND   cops.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
														+ " GROUP BY patron.NUM_REGISTROPATRONAL"
														+ " ORDER BY patron.NUM_REGISTROPATRONAL";
	
	
	public static String PAGO_TOTAL_COPS_RPS_INSCRITOS ="SELECT "
		       + " SUM(cops.IMP_COP),SUM(cops.IMP_COPACT),SUM(cops.IMP_COPREC),SUM(cops.IMP_COPTOT),"
		       + " SUM(cops.IMP_RCV),SUM(cops.IMP_RCVACT),SUM(cops.IMP_RCVREC),SUM(cops.IMP_RCVTOT)"
		+ " FROM CRT_COPPAGADAS cops,"
		     + " CRT_ANEXOSOLCORRPAT anexo,"
		     + " SAT_PATRON patron"
		+ " WHERE anexo. CVE_SOLICITUDCORR = {1}"
		+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
		+ " AND   cops.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT";
	

}
