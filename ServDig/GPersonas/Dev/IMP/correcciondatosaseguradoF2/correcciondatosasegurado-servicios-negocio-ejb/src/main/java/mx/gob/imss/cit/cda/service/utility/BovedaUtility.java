package mx.gob.imss.cit.cda.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.model.DocumentoBovedaDTO;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.Atributo;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.Documento;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaAlta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaBaja;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaConsulta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaAlta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaBaja;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaConsulta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.LegadoPort;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.LegadoPortService;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Stateless(name = "bovedaUtility", mappedName = "bovedaUtility")
public class BovedaUtility implements BovedaUtilityLocal {

    private final Logger log = LoggerFactory.getLogger(BovedaUtility.class);

    @Override
    public String createDocument(DocumentoBovedaDTO documentoBovedaDTO) throws BovedaCDAException {

        log.error("CDA - Entrando a guardar en la boveda " + documentoBovedaDTO.getNombre() + " folio " + documentoBovedaDTO.getFolio());
        EntradaAlta entradaAlta = new EntradaAlta();
        entradaAlta.setTipoDocumental(BovedaUtilityLocal.TIPO_DOCUMENTAL);
        entradaAlta.setTipo(documentoBovedaDTO.getExt());
        entradaAlta.setRuta(BovedaUtilityLocal.RUTA);
        entradaAlta.setDocumento(documentoBovedaDTO.getDocumento());

        //Lista de atributos
        List<Atributo> atributos = new ArrayList<Atributo>();

        //Atributo folio del tramite
        Atributo atributo = new Atributo();
        atributo.setNombre(BovedaUtilityLocal.FOLIO_TRAMITE);
        atributo.setValor(documentoBovedaDTO.getFolio());
        atributos.add(atributo);
        //Atributo nombre fisico del documento
        atributo = new Atributo();
        atributo.setNombre(BovedaUtilityLocal.NAME);
        atributo.setValor(documentoBovedaDTO.getNombre());
        atributos.add(atributo);

        //Se setan todos atributos del documento
        entradaAlta.getAtributo().addAll(atributos);
        entradaAlta.setIdentificador(BovedaUtilityLocal.IDENTIFICADOR);

        SalidaAlta salidaAlta;
        try {
            LegadoPort port = new LegadoPortService().getLegadoPortSoap11();
            salidaAlta = port.altaDocumento(entradaAlta);
        } catch (Exception e) {
            log.error("CDA - Ocurrio un error al guardar el documento, folio " + documentoBovedaDTO.getFolio(), e);
            throw new BovedaCDAException("Ocurrio un error al guardar el documento.");
        }

        if (!salidaAlta.isExito()) {
            log.error("CDA -  El servicio respondio con error " + salidaAlta.getClave() + "-" + salidaAlta.getDescripcion() + ", folio "
                    + documentoBovedaDTO.getFolio());
            throw new BovedaCDAException(salidaAlta.getClave() + "-" + salidaAlta.getDescripcion());
        } else {
            log.error(
                    "CDA - El servicio respondio con exito, idDocumento " + salidaAlta.getIdDocumento() + ", folio " + documentoBovedaDTO.getFolio());
            if (salidaAlta.getIdDocumento() == null) {
                log.error("CDA - " + salidaAlta.getDescripcion());
            }
            return salidaAlta.getIdDocumento();
        }
    }

    @Override
    public DocumentoBovedaDTO getDocument(String idDocumentoBoveda) throws BovedaCDAException {

        log.debug("CDA -Entrando a BovedaService:recuperarDocumento: " + idDocumentoBoveda);
        DocumentoBovedaDTO documentoBoveda = new DocumentoBovedaDTO();
        SalidaConsulta documentRespuesta;
        EntradaConsulta entradaConsulta = new EntradaConsulta();
        try {
            LegadoPort port = new LegadoPortService().getLegadoPortSoap11();
            Atributo atributo = new Atributo();
            atributo.setNombre(BovedaUtilityLocal.ID);
            atributo.setValor(idDocumentoBoveda);
            entradaConsulta.setTipoDocumental(BovedaUtilityLocal.TIPO_DOCUMENTAL);
            entradaConsulta.setIdentificador(BovedaUtilityLocal.IDENTIFICADOR);
            entradaConsulta.getAtributo().add(atributo);
            documentRespuesta = port.consultaDocumento(entradaConsulta);
            if (documentRespuesta.isExito() && documentRespuesta.getDocumento() != null && !documentRespuesta.getDocumento().isEmpty()) {
                Documento documentoSalida = documentRespuesta.getDocumento().get(0);
                documentoBoveda.setDocumento(documentoSalida.getContenido());
                documentoBoveda.setNombre(documentoSalida.getNombre());
            } else {
                throw new BovedaCDAException(
                        "Error al cosultar el documento con id [" + documentoBoveda.getId() + "] respuesta del servicio :" + documentRespuesta
                                .getDescripcion() + "  clave respuesta: " + documentRespuesta.getClave());
            }
        } catch (Exception e) {
            log.error("CDA -Ocurrio un error al consular el documento error: ", e);
            throw new BovedaCDAException("Ocurrio un error al consular el documento con id " + documentoBoveda.getId());
        }
        return documentoBoveda;
    }

    @Override
    public String deleteDocument(String idDocumentoBoveda) throws BovedaCDAException {

        log.error("CDA -Entrando a eliminar el documento con idDocumento: " + idDocumentoBoveda);
        EntradaBaja entradaBaja = new EntradaBaja();
        SalidaBaja salidaBaja;
        try {
            LegadoPort port = new LegadoPortService().getLegadoPortSoap11();
            entradaBaja.setIdDocumento(idDocumentoBoveda);
            entradaBaja.setIdentificador(BovedaUtilityLocal.IDENTIFICADOR);
            salidaBaja = port.bajaDocumento(entradaBaja);

            if (!salidaBaja.isExito()) {
                log.error("CDA - Respuesda de la boveda: " + salidaBaja.getClave() + " - " + salidaBaja.getDescripcion());
                throw new BovedaCDAException(salidaBaja.getClave() + " - " + salidaBaja.getDescripcion());
            } else {
                log.error("CDA - El documento se elimino con exito " + salidaBaja.getClave() + " - " + salidaBaja.getDescripcion());
                return salidaBaja.getClave() + " - " + salidaBaja.getDescripcion();
            }
        } catch (Exception e) {
            log.error("CDA -El documento no pudo ser eliminado: " + e.getMessage());
            throw new BovedaCDAException("Ocurrio un error al eliminar el documento con id " + idDocumentoBoveda);
        }
    }
}
