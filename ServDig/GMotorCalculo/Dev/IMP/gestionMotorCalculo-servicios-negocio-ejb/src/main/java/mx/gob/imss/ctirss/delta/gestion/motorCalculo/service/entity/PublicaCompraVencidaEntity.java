/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.PublicaCompraVencida;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para la publicacion de mensajes cn compras vencidas
 * @author NOVUTECK1
 *
 */
@Stateless(name = "publicaCompraVencida", mappedName = "publicaCompraVencida")
public class PublicaCompraVencidaEntity extends AbstractServiceBusiness implements PublicaCompraVencida {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicaCompraVencidaEntity.class);
    /**
     * Factory de conecciones
     */
    @Resource(name = "jms/CobranzaConnectionFactory")
    private transient ConnectionFactory factory;
    /**
     *  
     * Queue para encolar mensaje
     */
    @Resource(name = "jms/VencimientoComprasDistributedQueue")
    private transient Queue vencimientoComprasQueue;
    /**
     * Coneccion de jms
     */
    private transient Connection conn;
    /**
     * Sesiones para jms
     */
    private transient Session session;
    /**
     * Productor de mensajes jms
     */
    private transient MessageProducer producer;
    
    /**
     * Metodo para publicar las compras vencidas en una cola de mensajes
     * @param compras la lista de compras vencidas
     */
    public void publicaCompraVencida(ActualizacionCompra compras) {
        try {
            String resultXML = JaxbUtil.marshaller(compras);
            LOGGER.debug("encolando xml {}", resultXML);
            procesarMensaje(conn, session, producer, factory, vencimientoComprasQueue, resultXML);
            LOGGER.debug("finaliza Encolar compras vencidas");
        } catch (JAXBException e) {
            LOGGER.error("Error en la publicación de compras vencidas", e);
        }        
    }

}
