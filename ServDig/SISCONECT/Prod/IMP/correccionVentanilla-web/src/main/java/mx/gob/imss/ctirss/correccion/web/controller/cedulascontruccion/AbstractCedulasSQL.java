package mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion;

public abstract class AbstractCedulasSQL {
	
	public static String OBTENER_PERIODOS_CORRECCION ="SELECT ejercicio.CVE_EJERCICIO from CRT_RP_EJERCICIO ejercicio,"
              										+ " CRT_SOLICITUDCORR solicitud,"
              										+ " CRT_ANEXOSOLCORRPAT anexo"
              										+ " WHERE solicitud.NU_FOLIO = '{1}'"
              										+ " AND   solicitud.CVE_SOLICITUDCORR = anexo.CVE_SOLICITUDCORR"
              										+ " AND   ejercicio.CVE_ANEXOSOLCORRPAT IN (SELECT CVE_ANEXOSOLCORRPAT" 
              										+ " 										FROM CRT_ANEXOSOLCORRPAT innerAnexo" 
              										+ "								     		WHERE solicitud.CVE_SOLICITUDCORR = innerAnexo.CVE_SOLICITUDCORR"
              										+ "											)" 
              										+ " GROUP BY ejercicio.CVE_EJERCICIO"; 

}
