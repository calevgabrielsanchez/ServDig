package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Remote
public interface AgregarNssRemote {
    
    TramiteCorreccionCurp crearTramite(Solicitud solicitud, Usuario usuarioResponsable,
            Long origen, String nss, List<DocumentoProbatorio> doumentoDocumentoProbatorios,String observacion) throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException,TareaInicialException, TereaSinUsuarioAsignadoException;
    
    void actualizarDocumentosNss(Tramite tramite, List<DocumentoProbatorio> doumentoDocumentoProbatorios,
            List<DocumentoProbatorio> doumentoDocumentoProbatoriosEliminados)
            throws DocumentoProbatorioException;
    
    void agregarNss(TramiteCorreccionCurp tramite, CorreccionNSS correccionNSS) throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException;

        public List<CorreccionNSS> elminarDocumento(String folio, String nss, String idBoveda) 
            throws SolicitudNoEncontradaException, BovedaCDAException,
                TramiteNoEncontradoException;
        
        
}
