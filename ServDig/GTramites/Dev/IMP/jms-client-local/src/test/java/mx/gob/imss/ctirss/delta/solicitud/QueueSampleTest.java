package mx.gob.imss.ctirss.delta.solicitud;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.JaxbUtil;
import mx.gob.imss.ctirss.delta.solicitud.MovimientoPatronalType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueSampleTest {

    private static final Logger log = LoggerFactory.getLogger(QueueSampleTest.class);

    private InitialContext initialContext;
    private QueueConnectionFactory queueConnectionFactory;
    private QueueConnection connection;
    private Session session;
    private Queue queue;
    private MessageProducer producer;
    private JaxbUtil jaxbUtil;

    @Before
    public void setUp() throws NamingException, JMSException {
        log.info("setup test");
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://localhost:5001/");
        initialContext = new InitialContext(env);
        queueConnectionFactory = (QueueConnectionFactory) initialContext.lookup("jms.deltaSindoConnFactory");
        connection = queueConnectionFactory.createQueueConnection();
        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = (Queue)initialContext.lookup("jms.sampleQueue");
        producer = session.createProducer(queue);
        jaxbUtil = new JaxbUtil(new Class[]{MovimientoPatronalType.class});
        
    }

    @Test
    public void testSendToQueue() throws JMSException {
      log.info("testSendToQueue");
      MovimientoPatronalType type = new MovimientoPatronalType();
      String objectToXml = jaxbUtil.objectToXml(type);
      TextMessage textMessage = session.createTextMessage(objectToXml);
      producer.send(textMessage);
      
      log.info("message sent");
    }

    @After
    public void tearDown() {
        log.info("tearing down");
    }
}

