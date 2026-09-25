/**
 * Constantes de consulta para el estudio de correccian
 */
package mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model;

/**
 * Nos permite realizar consultas genaricas utilizando un SQL ANSI
 * el cual sera ejecutado por los servicios genaricos de la aplicacian.
 * @author Marco Antonio Nieto Plett
 * @version 1.0.2
 * @see ICatalogoService
 */
public abstract class ConsultasEstudioCorreccion {

	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Marco Antonio Nieto Plett
	 * @see CedulaAVO
	 */
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_A = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)"
															+" FROM CRT_BALANZACOMP cedulaA, "
															     +" CRT_ANEXOSOLCORRPAT anexo," 
															     +" CRT_SOLICITUDCORR solicitud,"
															     +" SAT_PATRON patron"
															+" WHERE solicitud.NU_FOLIO = '{1}'"
															+" AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
															+" AND   cedulaA.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
															+" AND   anexo.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR"
															+" AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
															+" AND   cedulaA.CVE_EJERCICIO = {2}"
															+" GROUP BY patron.NUM_REGISTROPATRONAL"
															+" ORDER BY patron.NUM_REGISTROPATRONAL";
	
	/**
	 * Permite consultar todos los datos relacionados en la
	 * Cadula A [Balanza de Comprobacian]. Cuenta con dos parametros:
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * {3} Hace referencia al registro patronal
	 * @author Marco Antonio Nieto Plett
	 * @see CedulaAVO
	 */
	public static String CONSULTA_CEDULA_A = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10),"
													+ " percepciones.TX_REMUNERACION,"
												    + " gastos.TX_GASTOS,"
												    + " cedulaA.IM_REMUNERACION,"
												    + " cedulaA.IM_AUXILIARNOM,"
												    + " cedulaA.IN_INTEGRA_SALARIO"       
											+ " FROM CRT_BALANZACOMP cedulaA," 
												   +"CRT_ANEXOSOLCORRPAT anexo," 
												   +"CRT_SOLICITUDCORR solicitud,"
												   +"CRC_GASTOS gastos,"
												   +"CRC_PERCEPCIONES percepciones,"
												   +"SAT_PATRON patron"
											+ " WHERE solicitud.NU_FOLIO = '{1}'"
											+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
											+ " AND   cedulaA.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
											+ "	AND   cedulaA.CVE_GASTOS = gastos.CVE_GASTOS"
											+ " AND   anexo.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR"
											+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
											+ "	AND   cedulaA.CVE_PERCEPCION = percepciones.CVE_PERCEPCION"
											+ "	AND   cedulaA.CVE_EJERCICIO = {2}"
											+ " AND   SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
											+ " AND   solicitud.CVE_FK_SUBDELEGACION={4}"
											+ " ORDER BY patron.NUM_REGISTROPATRONAL, percepciones.CVE_PERCEPCION, gastos.CVE_GASTOS";
	public static String CONSULTA_CEDULA_A_NORMATIVO = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10),"
			+ " percepciones.TX_REMUNERACION,"
		    + " gastos.TX_GASTOS,"
		    + " cedulaA.IM_REMUNERACION,"
		    + " cedulaA.IM_AUXILIARNOM,"
		    + " cedulaA.IN_INTEGRA_SALARIO"       
	+ " FROM CRT_BALANZACOMP cedulaA," 
		   +"CRT_ANEXOSOLCORRPAT anexo," 
		   +"CRT_SOLICITUDCORR solicitud,"
		   +"CRC_GASTOS gastos,"
		   +"CRC_PERCEPCIONES percepciones,"
		   +"SAT_PATRON patron"
	+ " WHERE solicitud.NU_FOLIO = '{1}'"
	+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
	+ " AND   cedulaA.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
	+ "	AND   cedulaA.CVE_GASTOS = gastos.CVE_GASTOS"
	+ " AND   anexo.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR"
	+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
	+ "	AND   cedulaA.CVE_PERCEPCION = percepciones.CVE_PERCEPCION"
	+ "	AND   cedulaA.CVE_EJERCICIO = {2}"
	+ " AND   SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
	+ " ORDER BY patron.NUM_REGISTROPATRONAL, percepciones.CVE_PERCEPCION, gastos.CVE_GASTOS";

	
	
	
	
	
	/**
	 * Permite consultar las percepciones relacionados en la
	 * Cadula A [Balanza de Comprobacian]. Cuenta con dos parametros:
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * {3} Hace referencia al registro patronal
	 * @author Jorge Hernandez Almazan
	 */
	public static String CONSULTA_CEDULA_A_PERCEPCIONES = "	SELECT percepciones.CVE_PERCEPCION, percepciones.TX_REMUNERACION,SUM(cedulaA.IM_REMUNERACION ),anexo.CVE_ANEXOSOLCORRPAT "
															+"FROM CRT_BALANZACOMP cedulaA,CRT_ANEXOSOLCORRPAT anexo,CRT_SOLICITUDCORR solicitud,CRC_GASTOS gastos, "
															+"CRC_PERCEPCIONES percepciones,SAT_PATRON patron "
															+"WHERE solicitud.NU_FOLIO = '{1}' AND   "
															+"solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR AND  "
															+"cedulaA.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT	"
															+"AND  cedulaA.CVE_GASTOS = gastos.CVE_GASTOS AND   "
															+"anexo.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR AND  "
															+"patron.CVE_PK = anexo.CVE_FK_PATRON	"
															+"AND   cedulaA.CVE_PERCEPCION = percepciones.CVE_PERCEPCION	AND   "
															+"cedulaA.CVE_EJERCICIO = {2} AND   " 
															+"SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)= SUBSTR('{3}',1,10) AND "
															+"solicitud.CVE_FK_SUBDELEGACION={4} "
															+"GROUP by percepciones.CVE_PERCEPCION,percepciones.TX_REMUNERACION,anexo.CVE_ANEXOSOLCORRPAT";
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Permite consultar todos los datos relacionados en la
	 * Cadula G [COPS Pagadas Mensual]. Cuenta con dos parametros:
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (Obligatorio)
	 * {3} Hace referencia a un registro patronal
	 * @author Marco Antonio Nieto Plett
	 * @see CedulaGVO
	 */
	public static String CONSULTA_CEDULA_G = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)," 
											     + " mes.TX_MES,cedulaG.IM_CUOTA_FIJA,"
												 + " cedulaG.IM_CUOTA_EXCED_3_SMGDF,"
												 + " cedulaG.IM_CUOTA_PREST_DINERO,"
											     + " cedulaG.IM_CUOTA_GTOS_MED_PEN,"
											     + " cedulaG.IM_CUOTA_RIESGO_TRABAJO," 
											     + " cedulaG.IM_CUOTA_INVALIDEZ_VIDA,"
											     + " cedulaG.IM_CUOTA_GUARD_PREST,"
											     + " cedulaG.IM_CUOTA_RCV_RETIRO,"
											     + " cedulaG.IM_CUOTA_RCV_CESANTIA,"
											     + " mes.CVE_MES"
											+ " FROM CRT_COPPAGADASMENSUAL cedulaG,"
												+ " CRT_ANEXOSOLCORRPAT anexo, "
											    + " CRT_SOLICITUDCORR solicitud,"
											    + " CRC_MES mes,"
											    +"  SAT_PATRON patron"
											+ " WHERE solicitud.NU_FOLIO = '{1}'"
											+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
											+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
											+ " AND   cedulaG.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
											+ " AND   cedulaG.CVE_EJERCICIO = {2}"
											+ " AND   SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
											+ " AND   cedulaG.CVE_MES = mes.CVE_MES"
											+ " AND   solicitud.CVE_FK_SUBDELEGACION = {4}"
											+ " ORDER BY patron.NUM_REGISTROPATRONAL, mes.CVE_MES";

	
	public static String CONSULTA_CEDULA_G_NORMATIVO = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)," 
		     + " mes.TX_MES,cedulaG.IM_CUOTA_FIJA,"
			 + " cedulaG.IM_CUOTA_EXCED_3_SMGDF,"
			 + " cedulaG.IM_CUOTA_PREST_DINERO,"
		     + " cedulaG.IM_CUOTA_GTOS_MED_PEN,"
		     + " cedulaG.IM_CUOTA_RIESGO_TRABAJO," 
		     + " cedulaG.IM_CUOTA_INVALIDEZ_VIDA,"
		     + " cedulaG.IM_CUOTA_GUARD_PREST,"
		     + " cedulaG.IM_CUOTA_RCV_RETIRO,"
		     + " cedulaG.IM_CUOTA_RCV_CESANTIA,"
		     + " mes.CVE_MES"
		+ " FROM CRT_COPPAGADASMENSUAL cedulaG,"
			+ " CRT_ANEXOSOLCORRPAT anexo, "
		    + " CRT_SOLICITUDCORR solicitud,"
		    + " CRC_MES mes,"
		    +"  SAT_PATRON patron"
		+ " WHERE solicitud.NU_FOLIO = '{1}'"
		+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
		+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
		+ " AND   cedulaG.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
		+ " AND   cedulaG.CVE_EJERCICIO = {2}"
		+ " AND   SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
		+ " AND   cedulaG.CVE_MES = mes.CVE_MES"
		+ " ORDER BY patron.NUM_REGISTROPATRONAL, mes.CVE_MES";

	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Marco Antonio Nieto Plett
	 * @see CedulaGVO
	 */
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_G ="SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)"
															  +" FROM CRT_COPPAGADASMENSUAL cedulaG,"
															     +" CRT_ANEXOSOLCORRPAT anexo,"
															     +" CRT_SOLICITUDCORR solicitud,"
															     +" SAT_PATRON patron"
															  +" WHERE solicitud.NU_FOLIO = '{1}'"
															     +" AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
															     +" AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
															     +" AND   cedulaG.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
															     +" AND   cedulaG.CVE_EJERCICIO = {2}"
															     +" GROUP BY patron.NUM_REGISTROPATRONAL"
															     +" ORDER BY patron.NUM_REGISTROPATRONAL";
	
	
	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Marco Antonio Nieto Plett
	 * @see CedulaIVO
	 */
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_I="SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10) FROM CRT_EJERTRABAJADOR trabajador,"
															 + " CRT_SOLICITUDCORR solicitud,"
															 + " CRT_ANEXOSOLCORRPAT anexo,"
															 + " CRT_RP_EJERCICIO ejercicio,"
															 + " SAT_PATRON patron"
															+ " WHERE trabajador.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
															+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
															+ " AND trabajador.CVE_EJERCICIO = ejercicio.CVE_EJERCICIO"
															+ " AND trabajador.CVE_ANEXOSOLCORRPAT = ejercicio.CVE_ANEXOSOLCORRPAT"
															+ " AND patron.CVE_PK = anexo.CVE_FK_PATRON"
															+ " AND trabajador.CVE_EJERCICIO = {2}"
															+ " AND solicitud.NU_FOLIO = '{1}'"
															+ " AND trabajador.IND_EXCSALTOP = 1 "
															+ " GROUP BY patron.NUM_REGISTROPATRONAL"
															+ " ORDER BY patron.NUM_REGISTROPATRONAL desc";
	
	/**
	 * Permite consultar todos los datos relacionados en la
	 * Cadula I [Excedente de Salarios Topados]. Cuenta con dos parametros:
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (Obligatorio)
	 * {3} Hace referencia a un registro patronal
	 * @author Marco Antonio Nieto Plett
	 * @see CedulaIVO
	 */
	
	public static String CONSULTA_CEDULA_I_ANTERIOR = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10),"
										      +" mes.TX_MES,"
										      +" percepciones.TX_REMUNERACION," 
										      +" salarios.IMP_REMUNERACION,"
										      +"  nvl(("
										      +"       select valor as totalIntegra from (SELECT mes2.CVE_MES mes,trabajadores2.CVE_TRABAJADOR cveTrabajador,"
										      +"                                                 SUBSTR(patron2.NUM_REGISTROPATRONAL,1,10) registroPatronal2,"
										      +"                                                 solicitud2.NU_FOLIO folio2,"
										      +"                                                 ejercicioTrabajador2.CVE_EJERCICIO ejercicio2,"
										      +"        SUM(salarios2.IMP_REMUNERACION) valor"
										      +"        FROM CRT_EXED_SAL_TOPADOS salarios2,"
										      +"                CRT_EJERTRABAJADOR ejercicioTrabajador2,"
										      +"                CRT_ANEXOSOLCORRPAT anexo2,"
										      +"                CRT_SOLICITUDCORR solicitud2,"
										      +"                SAT_PATRON patron2,"
										      +"                CRC_MES mes2,"
										      +"                CRT_TRABAJADORES trabajadores2,"
										      +"                CRC_PERCEPCIONES percepciones2"
										      +"       WHERE salarios2.CVE_EJERTRAB = ejercicioTrabajador2.CVE_EJERTRAB"
										      +"           AND   ejercicioTrabajador2.CVE_EJERCICIO={2}"
										      +"           AND   ejercicioTrabajador2.CVE_ANEXOSOLCORRPAT = anexo2.CVE_ANEXOSOLCORRPAT"
										      +"           AND   solicitud2.CVE_SOLICITUDCORR = anexo2.CVE_SOLICITUDCORR"
										      +"           AND   patron2.CVE_PK = anexo2.CVE_FK_PATRON"
										      +"           AND   mes2.CVE_MES = salarios2.CVE_MES"
										      +"           AND   percepciones2.CVE_PERCEPCION = salarios2.CVE_PERCEPCION"
										      +"           AND   ejercicioTrabajador2.CVE_TRABAJADOR = trabajadores2.CVE_TRABAJADOR"
										      +"           AND   salarios2.IN_INTEGRA = 1"
										      +"  GROUP BY mes2.CVE_MES,trabajadores2.CVE_TRABAJADOR,patron2.NUM_REGISTROPATRONAL,solicitud2.NU_FOLIO,"
										      +"          ejercicioTrabajador2.CVE_EJERCICIO"
										      +"  ORDER BY  mes2.CVE_MES asc) tabla"
										      +"  WHERE tabla.mes = mes.CVE_MES"
										      +"  AND tabla.cveTrabajador = trabajadores.CVE_TRABAJADOR"
										      +"  AND tabla.registroPatronal2 = SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)"
										      +"  AND tabla.folio2 = solicitud.NU_FOLIO"
										      +"  AND tabla.ejercicio2= ejercicioTrabajador.CVE_EJERCICIO"
										      +"  ),0) as valorTotalIntegra,"
										      +"  nvl(("
										      +"       select valor as totalIntegra from (SELECT mes2.CVE_MES mes,trabajadores2.CVE_TRABAJADOR cveTrabajador,"
										      +"                                                 SUBSTR(patron2.NUM_REGISTROPATRONAL,1,10) registroPatronal2,"
										      +"                                                 solicitud2.NU_FOLIO folio2,"
										      +"                                                 ejercicioTrabajador2.CVE_EJERCICIO ejercicio2,"
										      +"        										 SUM(salarios2.IMP_REMUNERACION) valor"
										      +"        FROM CRT_EXED_SAL_TOPADOS salarios2,"
										      +"                CRT_EJERTRABAJADOR ejercicioTrabajador2,"
										      +"                CRT_ANEXOSOLCORRPAT anexo2,"
										      +"                CRT_SOLICITUDCORR solicitud2,"
										      +"                SAT_PATRON patron2,"
										      +"                CRC_MES mes2,"
										      +"                CRT_TRABAJADORES trabajadores2,"
										      +"                CRC_PERCEPCIONES percepciones2"
										      +"       WHERE salarios2.CVE_EJERTRAB = ejercicioTrabajador2.CVE_EJERTRAB"
										      +"           AND   ejercicioTrabajador2.CVE_EJERCICIO={2}"
										      +"           AND   ejercicioTrabajador2.CVE_ANEXOSOLCORRPAT = anexo2.CVE_ANEXOSOLCORRPAT"
										      +"           AND   solicitud2.CVE_SOLICITUDCORR = anexo2.CVE_SOLICITUDCORR"
										      +"           AND   patron2.CVE_PK = anexo2.CVE_FK_PATRON"
										      +"           AND   mes2.CVE_MES = salarios2.CVE_MES"
										      +"           AND   percepciones2.CVE_PERCEPCION = salarios2.CVE_PERCEPCION"
										      +"           AND   ejercicioTrabajador2.CVE_TRABAJADOR = trabajadores2.CVE_TRABAJADOR"
										      +"           AND   salarios2.IN_INTEGRA = 2"
										      +"  GROUP BY mes2.CVE_MES,trabajadores2.CVE_TRABAJADOR,patron2.NUM_REGISTROPATRONAL,solicitud2.NU_FOLIO,"
										      +"          ejercicioTrabajador2.CVE_EJERCICIO"
										      +"  ORDER BY  mes2.CVE_MES asc) tabla"
										      +"  WHERE tabla.mes = mes.CVE_MES"
										      +"  AND tabla.cveTrabajador = trabajadores.CVE_TRABAJADOR"
										      +"  AND tabla.registroPatronal2 = SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)"
										      +"  ),0) as valorTotalNOIntegra,"
										      +" salarios.IN_INTEGRA,"
										      +" trabajadores.NOMBRE_ASEGURADO,"
										      +" trabajadores.AP_PATERNO_ASEGURADO,"
										      +" trabajadores.AP_MATERNO_ASEGURADO,"
										      +" salarios.NU_DIASSALDEVENGADO"       
										    +" FROM CRT_EXED_SAL_TOPADOS salarios,"
										    +"          CRT_EJERTRABAJADOR ejercicioTrabajador,"
										    +"          CRT_ANEXOSOLCORRPAT anexo,"
										    +"          CRT_SOLICITUDCORR solicitud,"
										    +"          SAT_PATRON patron,"
										    +"          CRC_MES mes,"
										    +"          CRT_TRABAJADORES trabajadores,"
										    +"          CRC_PERCEPCIONES percepciones"
										    +" WHERE salarios.CVE_EJERTRAB = ejercicioTrabajador.CVE_EJERTRAB"
										    +"     AND   ejercicioTrabajador.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
										    +"     AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
										    +"     AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
										    +"     AND   mes.CVE_MES = salarios.CVE_MES"
										    +"     AND   percepciones.CVE_PERCEPCION = salarios.CVE_PERCEPCION"
										    +"     AND   ejercicioTrabajador.CVE_TRABAJADOR = trabajadores.CVE_TRABAJADOR"
										    +"     AND   solicitud.NU_FOLIO = '{1}'"
										    +"     AND   ejercicioTrabajador.CVE_EJERCICIO = {2}"
										    +"     AND   SUBSTR(patron.NUM_REGISTROPATRONAL,1,10) = '{3}'"
										    +"     AND   solicitud.CVE_FK_SUBDELEGACION = {4}"
										+" ORDER BY  patron.NUM_REGISTROPATRONAL,  trabajadores.CVE_TRABAJADOR,mes.CVE_MES,salarios.IN_INTEGRA asc";
	
	
	
	public static String CONSULTA_CEDULA_I ="SELECT SUBSTR (PATRON.NUM_REGISTROPATRONAL, 1, 10) NUM_REGISTROPATRONAL,"
										     +"    MES.TX_MES,"
										     +"    PERCEPCIONES.TX_REMUNERACION,"
										     +"    SALARIOS.IMP_REMUNERACION,"
										     +"   VTI.VALORTOTALINTEGRA,"
										     +"    VTI.VALORTOTALNOINTEGRA,"
										     +"    SALARIOS.IN_INTEGRA,"
										     +"    TRABAJADORES.NOMBRE_ASEGURADO,"
										     +"    TRABAJADORES.AP_PATERNO_ASEGURADO,"
										     +"    TRABAJADORES.AP_MATERNO_ASEGURADO,"
										     +"    SALARIOS.NU_DIASSALDEVENGADO"
										    +" FROM CRT_SOLICITUDCORR SOLICITUD,"
										      +"   CRT_ANEXOSOLCORRPAT ANEXO,"
										      +"   CRT_EJERTRABAJADOR EJERCICIOTRABAJADOR,"
										      +"   CRT_EXED_SAL_TOPADOS SALARIOS,"
										      +"   CRT_TRABAJADORES TRABAJADORES,"
										      +"   CRC_PERCEPCIONES PERCEPCIONES,"
										      +"   SAT_PATRON PATRON,"
										      +"   CRC_MES MES,"
										      +"   (  SELECT MES2.CVE_MES CVE_MES,"
										      +"             TRABAJADORES2.CVE_TRABAJADOR CVE_TRABAJADOR,"
										      +"             SUBSTR (PATRON2.NUM_REGISTROPATRONAL, 1, 10) NUM_REGISTROPATRONAL,"
										      +"             SOLICITUD2.NU_FOLIO NU_FOLIO,"
										      +"             EJERCICIOTRABAJADOR2.CVE_EJERCICIO CVE_EJERCICIO,"
										      +"             CASE WHEN SALARIOS2.IN_INTEGRA = 1 THEN SUM(SALARIOS2.IMP_REMUNERACION) ELSE 0 END VALORTOTALINTEGRA,"
										      +"             CASE WHEN SALARIOS2.IN_INTEGRA = 2 THEN SUM(SALARIOS2.IMP_REMUNERACION) ELSE 0 END VALORTOTALNOINTEGRA,"
										      +"             SALARIOS2.IN_INTEGRA"
										      +"        FROM CRT_EXED_SAL_TOPADOS SALARIOS2,"
										      +"             CRT_EJERTRABAJADOR EJERCICIOTRABAJADOR2,"
										      +"             CRT_ANEXOSOLCORRPAT ANEXO2,"
										      +"             CRT_SOLICITUDCORR SOLICITUD2,"
										      +"             SAT_PATRON PATRON2,"
										      +"             CRC_MES MES2,"
										      +"             CRT_TRABAJADORES TRABAJADORES2,"
										      +"             CRC_PERCEPCIONES PERCEPCIONES2"
										      +"       WHERE     SALARIOS2.CVE_EJERTRAB = EJERCICIOTRABAJADOR2.CVE_EJERTRAB"
										      +"             AND EJERCICIOTRABAJADOR2.CVE_ANEXOSOLCORRPAT ="
										      +"                    ANEXO2.CVE_ANEXOSOLCORRPAT"
										      +"             AND SOLICITUD2.CVE_SOLICITUDCORR = ANEXO2.CVE_SOLICITUDCORR"
										      +"             AND PATRON2.CVE_PK = ANEXO2.CVE_FK_PATRON"
										      +"             AND MES2.CVE_MES = SALARIOS2.CVE_MES"
										      +"             AND PERCEPCIONES2.CVE_PERCEPCION = SALARIOS2.CVE_PERCEPCION"
										      +"             AND EJERCICIOTRABAJADOR2.CVE_TRABAJADOR = TRABAJADORES2.CVE_TRABAJADOR"
										      +"    GROUP BY MES2.CVE_MES,"
										      +"             TRABAJADORES2.CVE_TRABAJADOR,"
										      +"             PATRON2.NUM_REGISTROPATRONAL,"
										      +"             SOLICITUD2.NU_FOLIO,"
										      +"             EJERCICIOTRABAJADOR2.CVE_EJERCICIO,"
										      +"             SALARIOS2.IN_INTEGRA) VTI   WHERE     1 = 1"
										      +"   AND SOLICITUD.CVE_SOLICITUDCORR = ANEXO.CVE_SOLICITUDCORR"
										      +"   AND ANEXO.CVE_FK_PATRON = PATRON.CVE_PK"
										      +"   AND EJERCICIOTRABAJADOR.CVE_ANEXOSOLCORRPAT = ANEXO.CVE_ANEXOSOLCORRPAT"
										      +"   AND EJERCICIOTRABAJADOR.CVE_EJERTRAB = SALARIOS.CVE_EJERTRAB"
										      +"   AND EJERCICIOTRABAJADOR.CVE_TRABAJADOR = TRABAJADORES.CVE_TRABAJADOR"
										      +"   AND SALARIOS.CVE_PERCEPCION = PERCEPCIONES.CVE_PERCEPCION"
										      +"   AND SALARIOS.CVE_MES = MES.CVE_MES"
										      +"   AND SOLICITUD.NU_FOLIO = '{1}'"   
										      +"   AND SOLICITUD.CVE_FK_SUBDELEGACION = {4}"
										      +"   AND SUBSTR (PATRON.NUM_REGISTROPATRONAL, 1, 10) = '{3}'"
										      +"   AND EJERCICIOTRABAJADOR.CVE_EJERCICIO = {2}"
										      +"   AND VTI.CVE_MES = SALARIOS.CVE_MES"
										      +"   AND VTI.CVE_TRABAJADOR = EJERCICIOTRABAJADOR.CVE_TRABAJADOR"
										      +"   AND VTI.NUM_REGISTROPATRONAL = SUBSTR (PATRON.NUM_REGISTROPATRONAL, 1, 10)"
										      +"   AND VTI.NU_FOLIO = SOLICITUD.NU_FOLIO"
										      +"   AND VTI.CVE_EJERCICIO = EJERCICIOTRABAJADOR.CVE_EJERCICIO"
										      +"   AND SALARIOS.IN_INTEGRA=VTI.IN_INTEGRA"
										      
										+"  ORDER BY PATRON.NUM_REGISTROPATRONAL,"
										      +"   TRABAJADORES.CVE_TRABAJADOR,"
										      +"   MES.CVE_MES,"
										      +"   SALARIOS.IN_INTEGRA ASC,"
    										  +"   PERCEPCIONES.TX_REMUNERACION ASC";
	
	
	public static String CONSULTA_CEDULA_I_NORMATIVO ="SELECT SUBSTR (PATRON.NUM_REGISTROPATRONAL, 1, 10) NUM_REGISTROPATRONAL,"
		     +"    MES.TX_MES,"
		     +"    PERCEPCIONES.TX_REMUNERACION,"
		     +"    SALARIOS.IMP_REMUNERACION,"
		     +"   VTI.VALORTOTALINTEGRA,"
		     +"    VTI.VALORTOTALNOINTEGRA,"
		     +"    SALARIOS.IN_INTEGRA,"
		     +"    TRABAJADORES.NOMBRE_ASEGURADO,"
		     +"    TRABAJADORES.AP_PATERNO_ASEGURADO,"
		     +"    TRABAJADORES.AP_MATERNO_ASEGURADO,"
		     +"    SALARIOS.NU_DIASSALDEVENGADO"
		    +" FROM CRT_SOLICITUDCORR SOLICITUD,"
		      +"   CRT_ANEXOSOLCORRPAT ANEXO,"
		      +"   CRT_EJERTRABAJADOR EJERCICIOTRABAJADOR,"
		      +"   CRT_EXED_SAL_TOPADOS SALARIOS,"
		      +"   CRT_TRABAJADORES TRABAJADORES,"
		      +"   CRC_PERCEPCIONES PERCEPCIONES,"
		      +"   SAT_PATRON PATRON,"
		      +"   CRC_MES MES,"
		      +"   (  SELECT MES2.CVE_MES CVE_MES,"
		      +"             TRABAJADORES2.CVE_TRABAJADOR CVE_TRABAJADOR,"
		      +"             SUBSTR (PATRON2.NUM_REGISTROPATRONAL, 1, 10) NUM_REGISTROPATRONAL,"
		      +"             SOLICITUD2.NU_FOLIO NU_FOLIO,"
		      +"             EJERCICIOTRABAJADOR2.CVE_EJERCICIO CVE_EJERCICIO,"
		      +"             CASE WHEN SALARIOS2.IN_INTEGRA = 1 THEN SUM(SALARIOS2.IMP_REMUNERACION) ELSE 0 END VALORTOTALINTEGRA,"
		      +"             CASE WHEN SALARIOS2.IN_INTEGRA = 2 THEN SUM(SALARIOS2.IMP_REMUNERACION) ELSE 0 END VALORTOTALNOINTEGRA,"
		      +"             SALARIOS2.IN_INTEGRA"
		      +"        FROM CRT_EXED_SAL_TOPADOS SALARIOS2,"
		      +"             CRT_EJERTRABAJADOR EJERCICIOTRABAJADOR2,"
		      +"             CRT_ANEXOSOLCORRPAT ANEXO2,"
		      +"             CRT_SOLICITUDCORR SOLICITUD2,"
		      +"             SAT_PATRON PATRON2,"
		      +"             CRC_MES MES2,"
		      +"             CRT_TRABAJADORES TRABAJADORES2,"
		      +"             CRC_PERCEPCIONES PERCEPCIONES2"
		      +"       WHERE     SALARIOS2.CVE_EJERTRAB = EJERCICIOTRABAJADOR2.CVE_EJERTRAB"
		      +"             AND EJERCICIOTRABAJADOR2.CVE_ANEXOSOLCORRPAT ="
		      +"                    ANEXO2.CVE_ANEXOSOLCORRPAT"
		      +"             AND SOLICITUD2.CVE_SOLICITUDCORR = ANEXO2.CVE_SOLICITUDCORR"
		      +"             AND PATRON2.CVE_PK = ANEXO2.CVE_FK_PATRON"
		      +"             AND MES2.CVE_MES = SALARIOS2.CVE_MES"
		      +"             AND PERCEPCIONES2.CVE_PERCEPCION = SALARIOS2.CVE_PERCEPCION"
		      +"             AND EJERCICIOTRABAJADOR2.CVE_TRABAJADOR = TRABAJADORES2.CVE_TRABAJADOR"
		      +"    GROUP BY MES2.CVE_MES,"
		      +"             TRABAJADORES2.CVE_TRABAJADOR,"
		      +"             PATRON2.NUM_REGISTROPATRONAL,"
		      +"             SOLICITUD2.NU_FOLIO,"
		      +"             EJERCICIOTRABAJADOR2.CVE_EJERCICIO,"
		      +"             SALARIOS2.IN_INTEGRA) VTI   WHERE     1 = 1"
		      +"   AND SOLICITUD.CVE_SOLICITUDCORR = ANEXO.CVE_SOLICITUDCORR"
		      +"   AND ANEXO.CVE_FK_PATRON = PATRON.CVE_PK"
		      +"   AND EJERCICIOTRABAJADOR.CVE_ANEXOSOLCORRPAT = ANEXO.CVE_ANEXOSOLCORRPAT"
		      +"   AND EJERCICIOTRABAJADOR.CVE_EJERTRAB = SALARIOS.CVE_EJERTRAB"
		      +"   AND EJERCICIOTRABAJADOR.CVE_TRABAJADOR = TRABAJADORES.CVE_TRABAJADOR"
		      +"   AND SALARIOS.CVE_PERCEPCION = PERCEPCIONES.CVE_PERCEPCION"
		      +"   AND SALARIOS.CVE_MES = MES.CVE_MES"
		      +"   AND SOLICITUD.NU_FOLIO = '{1}'"   
		      +"   AND SUBSTR (PATRON.NUM_REGISTROPATRONAL, 1, 10) = '{3}'"
		      +"   AND EJERCICIOTRABAJADOR.CVE_EJERCICIO = {2}"
		      +"   AND VTI.CVE_MES = SALARIOS.CVE_MES"
		      +"   AND VTI.CVE_TRABAJADOR = EJERCICIOTRABAJADOR.CVE_TRABAJADOR"
		      +"   AND VTI.NUM_REGISTROPATRONAL = SUBSTR (PATRON.NUM_REGISTROPATRONAL, 1, 10)"
		      +"   AND VTI.NU_FOLIO = SOLICITUD.NU_FOLIO"
		      +"   AND VTI.CVE_EJERCICIO = EJERCICIOTRABAJADOR.CVE_EJERCICIO"
		      +"   AND SALARIOS.IN_INTEGRA=VTI.IN_INTEGRA"
		      
		+"  ORDER BY PATRON.NUM_REGISTROPATRONAL,"
		      +"   TRABAJADORES.CVE_TRABAJADOR,"
		      +"   MES.CVE_MES,"
		      +"   SALARIOS.IN_INTEGRA ASC,"
			  +"   PERCEPCIONES.TX_REMUNERACION ASC";
																					

	public static String CONSULTA_TITULOS_VARIABLES_CEDULA_I= "SELECT DISTINCT percepciones.TX_REMUNERACION"
															  +"  FROM     CRT_EXED_SAL_TOPADOS salarios,"
															  +"           CRT_EJERTRABAJADOR ejercicioTrabajador,"
															  +"           CRT_ANEXOSOLCORRPAT anexo,"
															  +"           CRT_SOLICITUDCORR solicitud,"
															  +"           CRC_PERCEPCIONES percepciones"
															  +"  WHERE     salarios.CVE_EJERTRAB = ejercicioTrabajador.CVE_EJERTRAB"
															  +"      AND   ejercicioTrabajador.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
															  +"      AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
															  +"      AND   percepciones.CVE_PERCEPCION = salarios.CVE_PERCEPCION"
															  +"      AND   solicitud.NU_FOLIO = '{1}'"
															  +"      AND   ejercicioTrabajador.CVE_EJERCICIO = {2}"
															  +"      AND   salarios.IN_INTEGRA = {3}"
															  + "     AND   solicitud.CVE_FK_SUBDELEGACION = {4} order by percepciones.TX_REMUNERACION";

	
	public static String CONSULTA_TITULOS_VARIABLES_CEDULA_I_NORMATIVO= "SELECT DISTINCT percepciones.TX_REMUNERACION"
			  +"  FROM     CRT_EXED_SAL_TOPADOS salarios,"
			  +"           CRT_EJERTRABAJADOR ejercicioTrabajador,"
			  +"           CRT_ANEXOSOLCORRPAT anexo,"
			  +"           CRT_SOLICITUDCORR solicitud,"
			  +"           CRC_PERCEPCIONES percepciones"
			  +"  WHERE     salarios.CVE_EJERTRAB = ejercicioTrabajador.CVE_EJERTRAB"
			  +"      AND   ejercicioTrabajador.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
			  +"      AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
			  +"      AND   percepciones.CVE_PERCEPCION = salarios.CVE_PERCEPCION"
			  +"      AND   solicitud.NU_FOLIO = '{1}'"
			  +"      AND   ejercicioTrabajador.CVE_EJERCICIO = {2}"
			  +"      AND   salarios.IN_INTEGRA = {3}"
			  + "     order by percepciones.TX_REMUNERACION";

	
	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Oscar Beltran Ortega
	 */
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_H=" SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10) " +
			" FROM CRT_EJERTRABAJADOR trabajador," +
			" CRT_SOLICITUDCORR solicitud," +
			" CRT_ANEXOSOLCORRPAT anexo, " +
			" CRT_RP_EJERCICIO ejercicio, " +
			" SAT_PATRON patron " +
			" WHERE trabajador.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT " +
			" AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR " +
			" AND trabajador.CVE_EJERCICIO = ejercicio.CVE_EJERCICIO " +
			" AND trabajador.CVE_ANEXOSOLCORRPAT = ejercicio.CVE_ANEXOSOLCORRPAT " +
			" AND patron.CVE_PK = anexo.CVE_FK_PATRON " +
			" AND solicitud.NU_FOLIO = '{1}'" +
			" AND trabajador.CVE_EJERCICIO = {2} " +
			" AND IND_PRUEBASEL = 1 " +
			" GROUP BY patron.NUM_REGISTROPATRONAL " +
			" ORDER BY patron.NUM_REGISTROPATRONAL desc";

	
	/**
	 * Permite consultar todos los datos relacionados en la
	 * Cadula H [Prueba selectiva trabajadores]. Cuenta con dos parametros:
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (Obligatorio)
	 * {3} Hace referencia a un registro patronal
	 * @author Oscar Beltran Ortega
	 */
	public static String CONSULTA_CEDULA_H=" SELECT " +
			//"trabajadores.nu_nss, " +
			"CAST(trabajadores.nu_nss as varchar2(20)) , " +
			"(SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)) , " +
			"trabajadores.nombre_asegurado, " +
			"trabajadores.ap_paterno_asegurado, " +
			"trabajadores.ap_materno_asegurado ," +
			"ejercicio.nu_antiguedad_anios, " +
			"CAST(gpoCategoria.TX_GRUPOCATEGORIA as varchar2(20)) , " +
			"mes.tx_mes, " +
			"percepcion.tx_remuneracion , " +
			"cedulah.imp_remuneracion ," +
			"cedulah.in_tpo_percepcion, " + 
			"cedulah.cve_mes, " +
			"pruebaselectiva.nu_diassaldev, " +
			"pruebaselectiva.imp_percepvardiaria, " +
			"pruebaselectiva.imp_cotizo "+
			" FROM " +
				" crt_pselpercepcion cedulaH ," +
				" CRT_EJERTRABAJADOR ejercicio , " +
				" CRT_SOLICITUDCORR solicitud , " +
				" CRT_ANEXOSOLCORRPAT anexo  , " +
				" SAT_PATRON patron , " +
				" CRT_TRABAJADORES trabajadores, " +
				" CRC_PERCEPCIONES percepcion, " +
				" crt_pruebaselectiva pruebaselectiva, " +
				" CRC_MES mes, " + " CRC_GRUPOCATEGORIA gpoCategoria " +
						" WHERE cedulah.cve_ejertrab = ejercicio.cve_ejertrab " +
						" AND ejercicio.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT " +
						" AND solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR " +
						" AND patron.CVE_PK = anexo.CVE_FK_PATRON " +
						" AND trabajadores.cve_solicitudcorr = solicitud.CVE_SOLICITUDCORR " +
						" AND trabajadores.cve_trabajador = ejercicio.cve_trabajador " +
						" AND pruebaselectiva.cve_ejertrab = ejercicio.cve_ejertrab " +
						" AND percepcion.CVE_PERCEPCION = cedulah.cve_percepcion " +
						" AND cedulaH.CVE_MES = mes.CVE_MES " +
						" AND gpocategoria.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR " +
						" AND gpocategoria.CVE_GRUPOCATEGORIA = ejercicio.CVE_GRUPOCATEGORIA " +
						" AND pruebaselectiva.CVE_MES = cedulaH.CVE_MES " +
						" AND solicitud.nu_folio = '{1}' " +
						" AND ejercicio.cve_ejercicio = {2} " +
						" AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}' " +
						" AND ejercicio.IND_PRUEBASEL = 1 " +
						" AND solicitud.CVE_FK_SUBDELEGACION = {4} " +
							" ORDER BY patron.NUM_REGISTROPATRONAL,cedulah.imp_remuneracion";
	
	public static String CONSULTA_CEDULA_H_NORMATIVO=" SELECT " +
			//"trabajadores.nu_nss, " +
			"CAST(trabajadores.nu_nss as varchar2(20)) , " +
			"(SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)) , " +
			"trabajadores.nombre_asegurado, " +
			"trabajadores.ap_paterno_asegurado, " +
			"trabajadores.ap_materno_asegurado ," +
			"ejercicio.nu_antiguedad_anios, " +
			"CAST(gpoCategoria.TX_GRUPOCATEGORIA as varchar2(20)) , " +
			"mes.tx_mes, " +
			"percepcion.tx_remuneracion , " +
			"cedulah.imp_remuneracion ," +
			"cedulah.in_tpo_percepcion, " + 
			"cedulah.cve_mes, " +
			"pruebaselectiva.nu_diassaldev, " +
			"pruebaselectiva.imp_percepvardiaria, " +
			"pruebaselectiva.imp_cotizo "+
			" FROM " +
				" crt_pselpercepcion cedulaH ," +
				" CRT_EJERTRABAJADOR ejercicio , " +
				" CRT_SOLICITUDCORR solicitud , " +
				" CRT_ANEXOSOLCORRPAT anexo  , " +
				" SAT_PATRON patron , " +
				" CRT_TRABAJADORES trabajadores, " +
				" CRC_PERCEPCIONES percepcion, " +
				" crt_pruebaselectiva pruebaselectiva, " +
				" CRC_MES mes, " + " CRC_GRUPOCATEGORIA gpoCategoria " +
						" WHERE cedulah.cve_ejertrab = ejercicio.cve_ejertrab " +
						" AND ejercicio.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT " +
						" AND solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR " +
						" AND patron.CVE_PK = anexo.CVE_FK_PATRON " +
						" AND trabajadores.cve_solicitudcorr = solicitud.CVE_SOLICITUDCORR " +
						" AND trabajadores.cve_trabajador = ejercicio.cve_trabajador " +
						" AND pruebaselectiva.cve_ejertrab = ejercicio.cve_ejertrab " +
						" AND percepcion.CVE_PERCEPCION = cedulah.cve_percepcion " +
						" AND cedulaH.CVE_MES = mes.CVE_MES " +
						" AND gpocategoria.CVE_SOLICITUDCORR = solicitud.CVE_SOLICITUDCORR " +
						" AND gpocategoria.CVE_GRUPOCATEGORIA = ejercicio.CVE_GRUPOCATEGORIA " +
						" AND pruebaselectiva.CVE_MES = cedulaH.CVE_MES " +
						" AND solicitud.nu_folio = '{1}' " +
						" AND ejercicio.cve_ejercicio = {2} " +
						" AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}' " +
						" AND ejercicio.IND_PRUEBASEL = 1 " +
							" ORDER BY patron.NUM_REGISTROPATRONAL,cedulah.imp_remuneracion";

	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * para la Cadula Q [Analisis de Honorarios].
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Gerardo Salazar Vega
	 */	
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_Q = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)"
			+ " FROM CRT_EJERTRABAJADOR cedulaQ,"
			+ " CRT_ANEXOSOLCORRPAT anexo,"
			+ " CRT_SOLICITUDCORR solicitud,"
			+ " SAT_PATRON patron"
			+ " WHERE solicitud.NU_FOLIO = '{1}'"
			+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
			+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND   cedulaQ.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND   cedulaQ.CVE_EJERCICIO = {2}"
			+ " GROUP BY patron.NUM_REGISTROPATRONAL"
			+ " ORDER BY patron.NUM_REGISTROPATRONAL";
	
	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * para la Cadula O [Tiempo Extra].
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Enrique Duran Jimenez
	 */	
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_O = "SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)"
			+ " FROM CRT_EJERTRABAJADOR cedulaO,"
			+ " CRT_ANEXOSOLCORRPAT anexo,"
			+ " CRT_SOLICITUDCORR solicitud,"
			+ " SAT_PATRON patron"
			+ " WHERE solicitud.NU_FOLIO = '{1}'"
			+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
			+ " AND   patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND   cedulaO.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND   cedulaO.CVE_EJERCICIO = {2}"
			+ " GROUP BY patron.NUM_REGISTROPATRONAL"
			+ " ORDER BY patron.NUM_REGISTROPATRONAL";

	/**
	 * Permite consultar todos los datos relacionados en la
	 * Cadula Q [Analisis de Honorarios]. Cuenta con tres parametros:
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (Obligatorio)
	 * {3} Hace referencia a un registro patronal
	 * @author Gerardo Salazar Vega
	 */
	public static String CONSULTA_CEDULA_Q = "select distinct NVL(trabajadores.NOMBRE_ASEGURADO,' '), NVL(trabajadores.AP_PATERNO_ASEGURADO,' '),"
			+ " NVL(trabajadores.AP_MATERNO_ASEGURADO,' '), NVL(CAST(trabajadores.TX_RFC AS VARCHAR2(13)),' '),"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=1),0) Enero,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=2),0) Febrero,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=3),0) Marzo,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=4),0) Abril,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=5),0) Mayo,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=6),0) Junio,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=7),0) Julio,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=8),0) Agosto,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=9),0) Septiembre,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=10),0) Octubre,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=11),0) Noviembre,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=12),0) Diciembre"
			+ " from    CRT_SOLICITUDCORR solicitud, "
			+ " CRT_TRABAJADORES trabajadores,"
			+ " CRT_EJERTRABAJADOR ejercicio,"
			+ " CRT_ANEXOSOLCORRPAT anexo,"
			+ " SAT_PATRON patron, "
			+ " CRT_ANALISIS_HONORARIOS cedulaQ"
			+ " where"
			+ " solicitud.CVE_SOLICITUDCORR=trabajadores.CVE_SOLICITUDCORR"
			+ " AND trabajadores.CVE_TRABAJADOR=ejercicio.CVE_TRABAJADOR"
			+ " AND patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND ejercicio.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND solicitud.nu_folio='{1}'"
			+ " AND ejercicio.CVE_EJERCICIO={2}"
			+ " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
			+ " AND solicitud.CVE_FK_SUBDELEGACION={4}"
			+ " AND cedulaQ.cve_ejertrab = ejercicio.CVE_EJERTRAB"
			+ " ORDER BY 1, 2, 3";
	
	public static String CONSULTA_CEDULA_Q_NORMATIVO = "select distinct NVL(trabajadores.NOMBRE_ASEGURADO,' '), NVL(trabajadores.AP_PATERNO_ASEGURADO,' '),"
			+ " NVL(trabajadores.AP_MATERNO_ASEGURADO,' '), NVL(CAST(trabajadores.TX_RFC AS VARCHAR2(13)),' '),"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=1),0) Enero,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=2),0) Febrero,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=3),0) Marzo,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=4),0) Abril,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=5),0) Mayo,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=6),0) Junio,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=7),0) Julio,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=8),0) Agosto,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=9),0) Septiembre,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=10),0) Octubre,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=11),0) Noviembre,"
			+ " NVL((select honorarios.IMP_HONORARIOS from CRT_ANALISIS_HONORARIOS honorarios "
			+ " where ejercicio.CVE_EJERTRAB=honorarios.CVE_EJERTRAB AND honorarios.CVE_MES=12),0) Diciembre"
			+ " from    CRT_SOLICITUDCORR solicitud, "
			+ " CRT_TRABAJADORES trabajadores,"
			+ " CRT_EJERTRABAJADOR ejercicio,"
			+ " CRT_ANEXOSOLCORRPAT anexo,"
			+ " SAT_PATRON patron, "
			+ " CRT_ANALISIS_HONORARIOS cedulaQ"
			+ " where"
			+ " solicitud.CVE_SOLICITUDCORR=trabajadores.CVE_SOLICITUDCORR"
			+ " AND trabajadores.CVE_TRABAJADOR=ejercicio.CVE_TRABAJADOR"
			+ " AND patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND ejercicio.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND solicitud.nu_folio='{1}'"
			+ " AND ejercicio.CVE_EJERCICIO={2}"
			+ " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
			+ " AND cedulaQ.cve_ejertrab = ejercicio.CVE_EJERTRAB"
			+ " ORDER BY 1, 2, 3";

/**
 * Permite consultar todos los datos relacionados en la
 * Cadula O [Analisis de Tiempo Extra]. Cuenta con tres parametros:
 * {1} Hace referencia al namero de folio (obligatorio)
 * {2} Hace referencia a un periodo YYYY (Obligatorio)
 * {3} Hace referencia a un registro patronal
 * @author Enrique Duran Jimenez
 */
public static String CONSULTA_CEDULA_O = " SELECT distinct trabajadores.NOMBRE_ASEGURADO, " +
		                                 "         trabajadores.AP_PATERNO_ASEGURADO, " +
		                                 "         trabajadores.AP_MATERNO_ASEGURADO,  " +
										 "         CAST(trabajadores.TX_RFC AS VARCHAR2(13)), " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 1 ) Enero, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 2 ) Febrero, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 3 ) Marzo, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 4 ) Abril, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 5 ) Mayo, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 6 ) Junio, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 7 ) Julio, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 8 ) Agosto, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 9 ) Septiembre, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 10 ) Octubre, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 11 ) Noviembre, " +
										 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 12 ) Diciembre  " +
										 " FROM CRT_SOLICITUDCORR solicitud," +
										 "      CRT_TRABAJADORES trabajadores, " +
										 "      CRT_EJERTRABAJADOR ejercicio,  " +
										 "      CRT_ANEXOSOLCORRPAT anexo, " +
										 "      SAT_PATRON patron, " + " CRT_ANALISIS_TIEMPOEXT cedulaO " +
										 " WHERE solicitud.CVE_SOLICITUDCORR=trabajadores.CVE_SOLICITUDCORR " +
										 " AND trabajadores.CVE_TRABAJADOR=ejercicio.CVE_TRABAJADOR " +
										 " AND patron.CVE_PK = anexo.CVE_FK_PATRON " +
										 " AND ejercicio.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT " +
										 " AND solicitud.nu_folio='{1}' " +
										 " AND ejercicio.CVE_EJERCICIO= '{2}' " +
										 " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}' " +
										 " AND solicitud.CVE_FK_SUBDELEGACION={4} "+
										 " AND cedulaO.cve_ejertrab = ejercicio.CVE_EJERTRAB "+
										 " ORDER BY 1, 2, 3 ";

public static String CONSULTA_CEDULA_O_NORMATIVO = " SELECT distinct trabajadores.NOMBRE_ASEGURADO, " +
        "         trabajadores.AP_PATERNO_ASEGURADO, " +
        "         trabajadores.AP_MATERNO_ASEGURADO,  " +
		 "         CAST(trabajadores.TX_RFC AS VARCHAR2(13)), " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 1 ) Enero, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 2 ) Febrero, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 3 ) Marzo, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 4 ) Abril, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 5 ) Mayo, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 6 ) Junio, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 7 ) Julio, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 8 ) Agosto, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 9 ) Septiembre, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 10 ) Octubre, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 11 ) Noviembre, " +
		 "         ( select te.IMP_TIEMPOEX from CRT_ANALISIS_TIEMPOEXT te where ejercicio.CVE_EJERTRAB=te.CVE_EJERTRAB AND te.CVE_MES = 12 ) Diciembre  " +
		 " FROM CRT_SOLICITUDCORR solicitud," +
		 "      CRT_TRABAJADORES trabajadores, " +
		 "      CRT_EJERTRABAJADOR ejercicio,  " +
		 "      CRT_ANEXOSOLCORRPAT anexo, " +
		 "      SAT_PATRON patron, " + " CRT_ANALISIS_TIEMPOEXT cedulaO " +
		 " WHERE solicitud.CVE_SOLICITUDCORR=trabajadores.CVE_SOLICITUDCORR " +
		 " AND trabajadores.CVE_TRABAJADOR=ejercicio.CVE_TRABAJADOR " +
		 " AND patron.CVE_PK = anexo.CVE_FK_PATRON " +
		 " AND ejercicio.CVE_ANEXOSOLCORRPAT = anexo.CVE_ANEXOSOLCORRPAT " +
		 " AND solicitud.nu_folio='{1}' " +
		 " AND ejercicio.CVE_EJERCICIO= '{2}' " +
		 " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}' " +
		 " AND cedulaO.cve_ejertrab = ejercicio.CVE_EJERTRAB "+
		 " ORDER BY 1, 2, 3 ";




	/**
	 * Permite consultar todos los datos relacionados en la COP [Cuotas Obrero
	 * Patronales]. Cuenta con tres parametros: {1} Hace referencia al namero de
	 * folio (obligatorio) {2} Hace referencia a un periodo YYYY (Obligatorio)
	 * {3} Hace referencia a un registro patronal
	 * 
	 * @author Gerardo Salazar Vega
	 */
	public static String CONSULTA_COP = "Select NVL(SUBSTR(patron.NUM_REGISTROPATRONAL,1,10),' '),NVL(cuotas.NUM_FOLIOSUA,0),"
			+ " NVL(cuotas.NUM_ORDENINGRESO,' '), NVL(cuotas.NUM_CREDITO,' '), NVL(TO_CHAR(cuotas.FEC_FECHAPAGO,'dd/mm/yyyy'),' '),"
			+ " NVL(cuotas.ID_TIPODOCTO,0),NVL(cuotas.NUM_TRABREGULA,0), NVL(cuotas.NUM_PERIODO,0), NVL(cuotas.IMP_COP,0),"
			+ " NVL(cuotas.IMP_COPACT,0), NVL(cuotas.IMP_COPREC,0), NVL(cuotas.IMP_COPTOT,0),"
			+ " NVL(cuotas.NUM_PERIODO,0),NVL(cuotas.IMP_RCV,0), NVL(cuotas.IMP_RCVACT,0), "
			+ " NVL(cuotas.IMP_RCVREC,0), NVL(cuotas.IMP_RCVTOT,0),NVL(cuotas.NUM_ALTAS,0),NVL(cuotas.NUM_BAJAS,0),NVL(cuotas.NUM_MODIFSALARIO,0)"
			+ " from CRT_SOLICITUDCORR solicitud, CRT_COPPAGADAS cuotas,"
			+ " CRT_ANEXOSOLCORRPAT anexo,SAT_PATRON patron"
			+ " WHERE cuotas.CVE_ANEXOSOLCORRPAT=anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND anexo.CVE_SOLICITUDCORR=solicitud.CVE_SOLICITUDCORR"
			+ " AND patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND solicitud.nu_folio='{1}'"
			+ " AND cuotas.CVE_EJERCICIO={2}"
			+ " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'"
			+ " AND solicitud.CVE_FK_SUBDELEGACION={4}";
	
	public static String CONSULTA_COP_NORMATIVO = "Select NVL(SUBSTR(patron.NUM_REGISTROPATRONAL,1,10),' '),NVL(cuotas.NUM_FOLIOSUA,0),"
			+ " NVL(cuotas.NUM_ORDENINGRESO,' '), NVL(cuotas.NUM_CREDITO,' '), NVL(TO_CHAR(cuotas.FEC_FECHAPAGO,'dd/mm/yyyy'),' '),"
			+ " NVL(cuotas.ID_TIPODOCTO,0),NVL(cuotas.NUM_TRABREGULA,0), NVL(cuotas.NUM_PERIODO,0), NVL(cuotas.IMP_COP,0),"
			+ " NVL(cuotas.IMP_COPACT,0), NVL(cuotas.IMP_COPREC,0), NVL(cuotas.IMP_COPTOT,0),"
			+ " NVL(cuotas.NUM_PERIODO,0),NVL(cuotas.IMP_RCV,0), NVL(cuotas.IMP_RCVACT,0), "
			+ " NVL(cuotas.IMP_RCVREC,0), NVL(cuotas.IMP_RCVTOT,0),NVL(cuotas.NUM_ALTAS,0),NVL(cuotas.NUM_BAJAS,0),NVL(cuotas.NUM_MODIFSALARIO,0)"
			+ " from CRT_SOLICITUDCORR solicitud, CRT_COPPAGADAS cuotas,"
			+ " CRT_ANEXOSOLCORRPAT anexo,SAT_PATRON patron"
			+ " WHERE cuotas.CVE_ANEXOSOLCORRPAT=anexo.CVE_ANEXOSOLCORRPAT"
			+ " AND anexo.CVE_SOLICITUDCORR=solicitud.CVE_SOLICITUDCORR"
			+ " AND patron.CVE_PK = anexo.CVE_FK_PATRON"
			+ " AND solicitud.nu_folio='{1}'"
			+ " AND cuotas.CVE_EJERCICIO={2}"
			+ " AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'";
	
	
	
	/**
	 * Permite obtener todos los registros patronales
	 * asociados a un folio de correccian y a un periodo
	 * 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * @author Jorge Hernandez Almazan
	 */
	public static String CONSULTA_RP_PERIODO_FOLIO_CEDULA_R="SELECT SUBSTR(patron.NUM_REGISTROPATRONAL,1,10) FROM 	"
			+"CRT_DETBASECOT_OMITIDA om,"
			+"CRT_ANEXOSOLCORRPAT anexo,"
			+"CRT_SOLICITUDCORR solici,"
			+"SAT_PATRON patron "
			+"where anexo.CVE_ANEXOSOLCORRPAT=om.CVE_ANEXOSOLCORRPAT and "
			+"anexo.CVE_SOLICITUDCORR=solici.CVE_SOLICITUDCORR and "
			+"anexo.CVE_FK_PATRON=patron.CVE_PK and "
			+"solici.NU_FOLIO='{1}' and "
			+"om.CVE_EJERCICIO={2}";
			
	
	/**
	 * Permite obtener la informacion asociada para la cedula R 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * {2} Hace referencia a un regustri patronal(obligatorio)
	 * @author Jorge Hernandez Almazan
	 * @see CedulaRVO
	 */
	public static String CONSULTA_CEDULA_R =" SELECT om.*,anexo.TX_RAZON_SOCIAL FROM 	"
											+"CRT_DETBASECOT_OMITIDA om,"
											+"CRT_ANEXOSOLCORRPAT anexo ,"
											+"CRT_SOLICITUDCORR solici,"
											+"SAT_PATRON patron "
											+"where anexo.CVE_ANEXOSOLCORRPAT=om.CVE_ANEXOSOLCORRPAT and "
											+"anexo.CVE_SOLICITUDCORR=solici.CVE_SOLICITUDCORR and "
											+"anexo.CVE_FK_PATRON=patron.CVE_PK and "
											+"anexo.IN_TP_PATRON='C' and "
											+"solici.NU_FOLIO='{1}' and "
											+"om.CVE_EJERCICIO={2}  and "
											+"SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}' ";
	
	

	/**
	 * Permite oobtner el detalle de la cedula R	 
	 * {1} Hace referencia al namero de folio (obligatorio)
	 * {2} Hace referencia a un periodo YYYY (obligatorio)
	 * {2} Hace referencia a un regustri patronal(obligatorio)
	 * @author Jorge Hernandez Almazan
	 * @see CedulaRVO
	 */
	public static String CONSULTA_DETALLE_CEDULA_R="SELECT det.*,per.TX_REMUNERACION FROM CRT_DETBASECOT_OM_DET det," +
													"CRC_PERCEPCIONES per " +
													"where det.CVE_PERCEPCION=per.CVE_PERCEPCION and " +
													"det.CVE_DETBASECOTOMIT={1}";
	
	public static String CONSULTA_SALARIO_MINIMO = " select A.IMP_ZONAGEO_a from CRC_CATSALARIOMINIMOS A WHERE A.CVE_EJERCICIO =";
}
