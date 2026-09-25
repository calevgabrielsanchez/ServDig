/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Implementacion para la generacion de seguros.
 *
 * @author NOVUTECK1
 */
@Component
public class GeneradorComprobanteSeguroImpl implements GeneradorComprobanteSeguro {
    /** Logger de la clase. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorComprobanteSeguroImpl.class);

    /** The servicio comprobante. */
    @Autowired
    @Qualifier("webServiceComprobanteSeguro")
    private WebServiceTemplate servicioComprobante;
    
    /** The servicio cuestionario. */
    @Autowired
    @Qualifier("webServiceCuestionarioSeguro")
    private WebServiceTemplate servicioCuestionario;
    
    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte.
     * GneradorComprobanteSeguro
     * #generaComprobante(mx.gob.imss.digital.modelo.seguros.SeguroIvro)
     */
    @Override
    public DocumentoSeguro generaComprobante(SeguroIvro seguro, Long origen) {
        SegurosIvro seguros = new SegurosIvro();
        List<SeguroIvro> seg = new ArrayList<SeguroIvro>();
        seg.add(seguro);
        seguros.setSeguroIvro(seg.toArray(new SeguroIvro[seg.size()]));
        return generaComprobantes(seguros, origen);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte.
     * GneradorComprobanteSeguro
     * #generaComprobantes(mx.gob.imss.digital.modelo.seguros.SegurosIvro)
     */
    @Override
    public DocumentoSeguro generaComprobantes(SegurosIvro seguros, Long origen) {
        seguros.setOrigen(origen);
        DocumentoSeguro comprobantes = obtenDatosComprobantes(seguros);
        return comprobantes;
    }

        
    /**
     * Obten datos comprobantes.
     *
     * @param seguros the seguros
     * @return the comprobantes seguro reporte
     */
    private DocumentoSeguro obtenDatosComprobantes(SegurosIvro seguros) {
        DocumentoSeguro comprobantes = new DocumentoSeguro();
        try {
            comprobantes = realizaPeticion(seguros, DocumentoSeguro.class, servicioComprobante);
            //LOGGER.info("Parseo Objeto "+  ReflectionToStringBuilder.toString(comprobantes));
        } catch (Exception e) {            
            LOGGER.error("Error no controlado ", e);
        }
        return comprobantes;
    }
   
    @Override
    public DocumentoSeguro generaCuestionarios(SegurosIvro seguros) {
        DocumentoSeguro cuestionarios = new DocumentoSeguro();
        try {
            cuestionarios = realizaPeticion(seguros, DocumentoSeguro.class, servicioCuestionario);
        } catch (Exception e) {            
            LOGGER.error("Error no controlado ", e);
        }
        return cuestionarios;
    }
    
    /**
     * MEtodo utilitario que realiza las consultas de webservices
     * @param entrada xml de entrada para la consulta del web service
     * @param salida objeto resultante del webservice
     * @param template el template del webservice a utilizar
     * @return el objeto regresado por el webservice
     * @throws JAXBException  error  al parsear la respuesta
     */
    private <T> T realizaPeticion(SegurosIvro seguros, Class<T> salida, WebServiceTemplate template) throws JAXBException {
        String segurosXml = JaxbUtil.marshaller(seguros);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);        
        template.sendSourceAndReceiveToResult(new StreamSource(new StringReader(segurosXml)), 
                result);
        String comprobanesXml = writer.toString();
        return JaxbUtil.unmarshaller(comprobanesXml, salida);
    }
    
}
