package mx.gob.imss.distss.delta.rtt.service.entity;

import java.util.List;
import javax.ejb.Local;
import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

@Local
public interface GenerarReportesRiesgosTrabajoLocal {

    String ERROR_SOLICITUD="Lo sentimos ha ocurrido un error inesperado, intente m&aacute;s tarde.";

        
    /**
     * Genera el PDF
     *
     * @param solicitud
     * @param patron
     * @param listaRiesgosT
     * @throws RiesgosTrabajoException
     * @return
     */
    byte[] generarDocumentoPDF(Solicitud solicitud, PatronRiesgosTrabajo patron, List<RiesgoTrabajo> listaRiesgosT)
            throws RiesgosTrabajoException;

    /**
     * Genera el Excel
     *
     * @param listaRiesgosT
     * @throws RiesgosTrabajoException
     * @return
     */
    byte[] generarDocumentoXLS(List<RiesgoTrabajo> listaRiesgosT)
            throws RiesgosTrabajoException;

    /**
     * Genera el Excel
     *
     * @param listaRiesgosT
     * @throws RiesgosTrabajoException
     * @return
     */
    byte[] generarDocumentoXLSxRFC(List<RiesgoTrabajo> listaRiesgosT, boolean xNSS) throws RiesgosTrabajoException;

    /**
     * Genera XML
     * 
     * @param listaRiesgosT
     * @return
     * @throws RiesgosTrabajoException
     */
    byte[] generarDocumentoXML( List<RiesgoTrabajo> listaRiesgosT) throws RiesgosTrabajoException;

    /**
     * 
     * @param solicitud
     * @param patron
     * @return
     * @throws RiesgosTrabajoException 
     */
	byte[] generarTerminosCondicionesPDF(Solicitud solicitud, PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException;

    /**
     *
     * @param solicitud
     * @return
     * @throws RiesgosTrabajoException
     */
    byte[] generarAcuseEscritoDesacuerdoPDF(Solicitud solicitud) throws RiesgosTrabajoException;

    byte[] generarDocumentoPDFxRFC(Solicitud solicitud, PatronRiesgosTrabajo patron, List<RiesgoTrabajo> listaRiesgosT, String rfc)
            throws RiesgosTrabajoException;
}
