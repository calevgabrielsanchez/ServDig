package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;

@Component
public class TransformerRegistroUtils {

    public static DocumentoProbatorio voToModelDocumentoProbatorio(
            mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio datDocumentoProbatorio) {
        TipoDocumentoProbatorio tipoDoc = new TipoDocumentoProbatorio();
        tipoDoc.setIdTipoDocumentoProbatorio(datDocumentoProbatorio
                .getTipoDocumento());

        Documento doc = new Documento();
        doc.setDesDocumento(datDocumentoProbatorio.getDesDocumento());
        doc.setCveIdDocumento(datDocumentoProbatorio.getCveIdDocumento());

        DocumentoPorTipo tipoDocumento = new DocumentoPorTipo();
        tipoDocumento.setIdDocumentoPorTipo(datDocumentoProbatorio
                .getIdDocumentoPorTipo());
        tipoDocumento.setIdDocumentoPorTipoHashed(String
                .valueOf(datDocumentoProbatorio.getIdDocumentoPorTipo()));
        tipoDocumento.setTipoDocumentoProbatorio(tipoDoc);
        tipoDocumento.setDocumento(doc);

        DocumentoProbatorio documentoProbatorio = new DocumentoProbatorio();
        documentoProbatorio.setDocumentoPorTipo(tipoDocumento);
        documentoProbatorio.setNomNombreDocumento(datDocumentoProbatorio
                .getNombre());
        documentoProbatorio.setBovedaDocId(datDocumentoProbatorio
                .getIdDocBoveda());
        return documentoProbatorio;
    }
    
    public static List<DocumentoProbatorio> voToModelDocumentoProbatorio(
            List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> documentoProbatorio) {
        List<DocumentoProbatorio> documentos = new ArrayList<DocumentoProbatorio>();
        mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio documento = new mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio();

        for (Iterator<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> iterator = documentoProbatorio.iterator(); iterator.hasNext();) {
            documentos.add( voToModelDocumentoProbatorio(iterator.next())); 
            
        }
        return documentos;
    }

    public static mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio modelToVoDocumentoProbatorio(
            DocumentoProbatorio documentoProbatorio) {
        mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio documento = new mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio();

        documento.setIdDocBoveda(documentoProbatorio.getBovedaDocId());
        documento.setNombre(documentoProbatorio.getNomNombreDocumento());
        documento.setCveIdDocumento(documentoProbatorio.getDocumentoPorTipo()
                .getDocumento().getCveIdDocumento());
        documento.setDesDocumento(documentoProbatorio.getDocumentoPorTipo()
                .getDocumento().getDesDocumento());
        documento.setTipoDocumento(documentoProbatorio.getDocumentoPorTipo()
                .getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
        documento.setIdDocumentoPorTipo(documentoProbatorio
                .getDocumentoPorTipo().getIdDocumentoPorTipo());
        return documento;
    }

    public static HistoriaLaboralVO modelToVoHistorialLaboral(
            DocumentoProbatorio documentoProbatorio) {
        return null;
    }

    public static DatosLaborales voToModelHistorialLaboral(
            HistoriaLaboralVO historialLaboral) {
        DatosLaborales dato = new DatosLaborales();
        dato.setNombrePatron(historialLaboral.getNombrePatron());
        EntidadFederativa entidad = new EntidadFederativa();
        entidad.setNombre(historialLaboral.getEntidadFederativa());
        entidad.setClave(historialLaboral.getClaveEntidad());
        dato.setEntidadFederativa(entidad);
        dato.setFechaInscripcion(historialLaboral.getFechaInscripcion());
        dato.setFechaBaja(historialLaboral.getFechaBaja());
        dato.setNrp(historialLaboral.getNumeroRegistroPatronal());
        dato.setDomicilio(historialLaboral.getDomicilioEmpresa());
        dato.setActividad(historialLaboral.getActividadEmpresa());
        return dato;
    }
}
