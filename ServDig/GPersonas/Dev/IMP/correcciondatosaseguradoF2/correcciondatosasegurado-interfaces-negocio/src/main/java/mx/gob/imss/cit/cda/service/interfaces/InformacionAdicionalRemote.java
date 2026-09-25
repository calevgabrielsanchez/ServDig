package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;

@Remote
public interface InformacionAdicionalRemote {

    Solicitud actualizarCorreccionSolicitud(Solicitud solicitud)
            throws SolicitudNoValidaException,TramiteNoEncontradoException;
    
    void guardarDocumentosTramite(List<DocumentoProbatorio> documentos, Long idTramite)
            throws DocumentoProbatorioException,RegistrarDocumentoProbatorioException;

	void almacenarDatosAsegurado(TramiteCorreccionCurp tramite, String refCurp, TramiteCorreccionCurp tramiteRegistro) throws DocumentoProbatorioException;

	void almacenarDatosBeneficiario(TramiteCorreccionCurp tramite, String refCurp,TramiteCorreccionCurp tramiteRegistro)throws DocumentoProbatorioException;

	void almacenarDatosDetalleNss(TramiteCorreccionCurp tramitecurp, CorreccionNSS correccionNSS, String refCurp,TramiteCorreccionCurp tramiteRegistro, Long idTramite)throws DocumentoProbatorioException, TramiteNoEncontradoException, RegistrarDocumentoProbatorioException;
	
	Solicitud crearSolicitudInformacionAdicional(Solicitud solicitud, String usuario,String responsable) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
}
