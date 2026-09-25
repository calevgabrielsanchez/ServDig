package mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model;

/**
 * Contiene todos los querys ANSI ejecutados
 * en el porceso de pagos CRT_REVPAGOS
 * @version 1.1.0
 * @author Marco Antonio Nieto Plett
 *
 */
public abstract class PagosConsultasSQL {

	/**
	 * Permite obtener todos los regstros patronales
	 * asociados a la presentacian de la correccian
	 * a travas del folio generado en la solicitud
	 * de la correccian. Se realiza el filtro <>F
	 * ya que ese patron solo es de control y no de 
	 * operacian.
	 */
	public static String SQL_OBTENER_RPS = "SELECT anexo.CVE_ANEXOSOLCORRPAT, SUBSTR(patron.NUM_REGISTROPATRONAL,1,10) " +
										   " FROM CRT_ANEXOSOLCORRPAT anexo," +
										       " CRT_SOLICITUDCORR solicitud,SAT_PATRON patron" +
										   " WHERE anexo.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR" +
										   " AND patron.CVE_PK = anexo.CVE_FK_PATRON" +
										   " AND anexo.IN_TP_PATRON <> 'F'" +
										   " AND solicitud.NU_FOLIO = '{1}'";
	
	
	/**
	 * Permite obtener los pagos realizados al momento de
	 * generar el estudio de correccian (Cadulas XLS)
	 * y transladarlo a la pantalla de pagos de seguimiento.
	 * Esto solo aplica para Cadula de la revisian y solo
	 * una vez.
	 */
	public static String CONSULTA_COP_AUTODETERMINACIION = "Select anexo.CVE_ANEXOSOLCORRPAT, NVL(cuotas.NUM_FOLIOSUA,0),"
			+ " NVL(cuotas.NUM_ORDENINGRESO,' '), NVL(cuotas.NUM_CREDITO,' '), NVL(TO_CHAR(cuotas.FEC_FECHAPAGO,'dd/mm/yyyy'),' '),"
			+ " NVL(cuotas.ID_TIPODOCTO,0),NVL(cuotas.NU_TRABREGU,0), NVL(cuotas.NUM_PERIODO,0), NVL(cuotas.IMP_COP,0),"
			+ " NVL(cuotas.IMP_COPACT,0), NVL(cuotas.IMP_COPREC,0), NVL(cuotas.IMP_COPTOT,0),"
			+ " NVL(cuotas.NUM_PERIODO,0),NVL(cuotas.IMP_RCV,0), NVL(cuotas.IMP_RCVACT,0), "
			+ " NVL(cuotas.IMP_RCVREC,0), NVL(cuotas.IMP_RCVTOT,0)"
			+ " from CRT_SOLICITUDCORR solicitud, CRT_COPPAGADAS cuotas,"
			+ " CRT_ANEXOSOLCORRPAT anexo,SAT_PATRON patron"
			+ " WHERE cuotas.CVE_ANEXOSOLCORRPAT=anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND anexo.CVE_SOLICITUDCORR=solicitud.CVE_SOLICITUDCORR"
			+ " AND patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND solicitud.nu_folio='{1}'"
			+ " AND cuotas.CVE_EJERCICIO={2}"
			+ " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
			+ " AND solicitud.CVE_FK_SUBDELEGACION={4}";
	
	/**
	 * Permite obtener todos los pagos de la correccian
	 * seleccionada agrupados por registro patronal.
	 */
	public static String SUM_PAGOS_REV_RPS_INSCRITOS ="SELECT patron.NUM_REGISTROPATRONAL,"
		       + " NVL(SUM(cops.IMP_COPSP),0),NVL(SUM(cops.IMP_COPACT),0),NVL(SUM(cops.IMP_COPREC),0),NVL(SUM(cops.IMP_COPMULTA),0),NVL(SUM(cops.IMP_COPTOT),0),"
		       + " NVL(SUM(cops.IMP_RCVSP),0),NVL(SUM(cops.IMP_RCVACT),0),NVL(SUM(cops.IMP_RCVREC),0),NVL(SUM(cops.IMP_RCVMULTA),0),NVL(SUM(cops.IMP_RCVTOT),0),"
		       +  "NVL(SUM(cops.NUM_TRABREGULA),0), NVL(SUM(cops.NUM_ALTAS),0),NVL(SUM(cops.NUM_BAJAS),0),NVL(SUM(cops.NUM_MODIFSALARIO),0)"
		+ " FROM CRT_REVPAGOS cops,"
		     + " CRT_ANEXOSOLCORRPAT anexo,"
		     + " SAT_PATRON patron"
		+ " WHERE cops.CVE_PRESENTACORR= {1}"
		+ " AND IND_TIPOPAGO={2}"     
		+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
		+ " AND   cops.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
		+ " GROUP BY patron.NUM_REGISTROPATRONAL"
		+ " ORDER BY patron.NUM_REGISTROPATRONAL";
	
	/**
	 * Permite obtener el sumarizado total de 
	 * los pagos generados en una correccian
	 */
	public static String SUM_PAGOS_REV_TOTAL ="SELECT NVL(SUM(IMP_COPSP),0),NVL(SUM(IMP_COPACT),0),NVL(SUM(IMP_COPREC),0),NVL(SUM(IMP_COPMULTA),0),NVL(SUM(IMP_COPTOT),0),"
			    + " NVL(SUM(IMP_RCVSP),0),NVL(SUM(IMP_RCVACT),0),NVL(SUM(IMP_RCVREC),0),NVL(SUM(IMP_RCVMULTA),0),NVL(SUM(IMP_RCVTOT),0),"
			    + " NVL(SUM(NUM_TRABREGULA),0), NVL(SUM(NUM_ALTAS),0),NVL(SUM(NUM_BAJAS),0),NVL(SUM(NUM_MODIFSALARIO),0)"
		+ " FROM (SELECT patron.NUM_REGISTROPATRONAL,"
		       +  " SUM(cops.IMP_COPSP) IMP_COPSP,SUM(cops.IMP_COPACT) IMP_COPACT,SUM(cops.IMP_COPREC) IMP_COPREC,"
		       +  " SUM(cops.IMP_COPMULTA) IMP_COPMULTA,SUM(cops.IMP_COPTOT) IMP_COPTOT,"
		       +  " SUM(cops.IMP_RCVSP) IMP_RCVSP,SUM(cops.IMP_RCVACT) IMP_RCVACT,SUM(cops.IMP_RCVREC) IMP_RCVREC,"
		       +  " SUM(cops.IMP_RCVMULTA) IMP_RCVMULTA, SUM(cops.IMP_RCVTOT) IMP_RCVTOT,"
		       +  " SUM(cops.NUM_TRABREGULA) NUM_TRABREGULA, SUM(cops.NUM_ALTAS) NUM_ALTAS,SUM(cops.NUM_BAJAS) NUM_BAJAS,SUM(cops.NUM_MODIFSALARIO) NUM_MODIFSALARIO"
		       	   + " FROM CRT_REVPAGOS cops,"
		               + " CRT_ANEXOSOLCORRPAT anexo,"
		               + " CRT_SOLICITUDCORR solicitud,"
		               + " SAT_PATRON patron"
		           + " WHERE solicitud.NU_FOLIO = '{1}'"
		           + " AND   cops.IND_TIPOPAGO={2}"    
		           + " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
		           + " AND   cops.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
		           + " AND   solicitud.CVE_SOLICITUDCORR=anexo.CVE_SOLICITUDCORR"
		           + " GROUP BY patron.NUM_REGISTROPATRONAL"
		           + " ORDER BY patron.NUM_REGISTROPATRONAL)";
	
	/**
	 * Permite obtener el sumarizado total de 
	 * los pagos generados en una promocian
	 */
	public static String SUM_PAGOS_PROMO_TOTAL ="SELECT"
				   +" NVL(SUM(cops.IMP_COPSP),0) IMP_COPSP, NVL(SUM(cops.IMP_COPACT),0) IMP_COPACT, NVL(SUM(cops.IMP_COPREC),0) IMP_COPREC,"
				   +" NVL(SUM(cops.IMP_COPMULTA),0) IMP_COPMULTA, NVL(SUM(cops.IMP_COPTOT),0) IMP_COPTOT,"
				   +" NVL(SUM(cops.IMP_RCVSP),0) IMP_RCVSP, NVL(SUM(cops.IMP_RCVACT),0) IMP_RCVACT, NVL(SUM(cops.IMP_RCVREC),0) IMP_RCVREC,"
				   +" NVL(SUM(cops.IMP_RCVMULTA),0) IMP_RCVMULTA, NVL(SUM(cops.IMP_RCVTOT),0) IMP_RCVTOT,"
				   +" NVL(SUM(cops.NUM_TRABREGULA),0) NUM_TRABREGULA, NVL(SUM(cops.NUM_ALTAS),0) NUM_ALTAS,NVL(SUM(cops.NUM_BAJAS),0) NUM_BAJAS,"
				   +" NVL(SUM(cops.NUM_MODIFSALARIO),0) NUM_MODIFSALARIO"
	       	    + " FROM CRT_REVPAGOS cops"
	            + " WHERE cops.CVE_REGULAPAGOS = {1}"
	            + " AND   cops.IND_TIPOPAGO = {2}"
	            + " GROUP BY cops.CVE_REGULAPAGOS";
	
	
	/**
	 * Obtiene el registro patronal asociado a la promocian
	 * a travas de la PK CVE_REGULAPAGOS de la tabla
	 * CRT_REGULAPAGOS.
	 * 
	 * anicamente aplica para IND_TP_PAGO 3 [Promocian]
	 */
	public static String OBTENER_RP_ASOCIADO_PROMOCION = " SELECT PATRON.CVE_PK,PATRON.NUM_REGISTROPATRONAL"
			                                           + " FROM CRT_REGULAPAGOS REGPAG," 
			                                           + " CRT_PROMOCION PROM,"
		                                               + " SAT_PATRON PATRON"
		                                               + " WHERE REGPAG.CVE_PROMOCION = PROM.CVE_PROMOCION"
		                                               + " AND PATRON.CVE_PK = PROM.CVE_FK_PATRON"
		                                               + " AND CVE_REGULAPAGOS = {1}";
		
}