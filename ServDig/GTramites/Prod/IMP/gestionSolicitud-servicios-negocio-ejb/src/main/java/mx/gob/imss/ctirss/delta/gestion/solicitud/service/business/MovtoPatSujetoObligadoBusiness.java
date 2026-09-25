package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.MovtoPatSujetoObligadoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovtoPatSujetoObligadoBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.MovtoPatSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovtoPatSujetoObligadoType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless(name = "movtoPatSujetoObligadoBusiness", mappedName = "movtoPatSujetoObligadoBusiness")
@TransactionManagement(TransactionManagementType.BEAN)
public class MovtoPatSujetoObligadoBusiness
    implements MovtoPatSujetoObligadoBusinessRemote {

    private static final Logger log = LoggerFactory.getLogger(MovtoPatSujetoObligadoBusiness.class);

    @Resource(name = "jms/GeneralConnectionFactory")
    private transient ConnectionFactory factory;
    @Resource(name = "jms/MovPatronalesDistributedQueue")
    private transient Queue queue;
    private transient Connection connection;
    @Resource //No transient!!!!!
    private SessionContext sessionContext;



    @EJB
    private MovtoPatSujetoObligadoEntityLocal movtoPatSujetoObligadoEntityLocal;

    @Resource
    private TimerService timerService;

    @Timeout
    public void timeout(Timer timer) {
        log.debug("executing timer: {}", timer.getInfo());

        List<MovtoPatSujetoObligado> movimientos = Collections.EMPTY_LIST;
        UserTransaction transaction = sessionContext.getUserTransaction();
        try {
            transaction.begin();
            movimientos = movtoPatSujetoObligadoEntityLocal.listMovtoPatSujetoObligadoCurrentDate();
        }
        catch(NotSupportedException e) {
            log.warn("{}", e.getMessage(), e);
        }
        catch(SystemException e) {
            log.warn("{}", e.getMessage(), e);
        }
        finally {
            manageTransaction(transaction);
        }
        for (MovtoPatSujetoObligado m:movimientos) {
            enqueueMovPatSujetoObligado(m);
        }
        log.debug("Queue:             {}", queue);
        log.debug("ConnecionFactory:  {}", factory);
        log.debug("Connection:        {}", connection);
    }

    public MovtoPatSujetoObligado getMovtoPatSujetoObligadoFromRegPatornal(String regPatronal) {
        return movtoPatSujetoObligadoEntityLocal.getMovtoPatSujetoObligadoFromRegPatornal(regPatronal);
    }

    private void enqueueMovPatSujetoObligado(MovtoPatSujetoObligado movimiento) {
        log.debug("Encolando metodo enqueueMovPatSujetoObligado");
        MovtoPatSujetoObligadoType movimientoType = new MovtoPatSujetoObligadoType();

        movimientoType.setCveIdMovtoPatSujOblig(new Long(movimiento.getCveIdMovtoPatSujOblig()).intValue());
        movimientoType.setCveIdPatronDestino(movimiento.getCveIdPatronDestino());
        movimientoType.setFecAvisoSat(movimiento.getFecAvisoSat());
        movimientoType.setFecInforme(movimiento.getFecInforme());
        movimientoType.setFecMovimiento(movimiento.getFecMovimiento());
        movimientoType.setFecOficio(movimiento.getFecOficio());
        movimientoType.setFecRegistroActualizado(movimiento.getFecRegistroActualizado());
        movimientoType.setFecRegistroAlta(movimiento.getFecRegistroAlta());
        movimientoType.setFolioMovimiento(movimiento.getFolioMovimiento());
        movimientoType.setNumeroOficio(movimiento.getNumeroOficio());
        movimientoType.setObservaciones(movimiento.getObservaciones());
        movimientoType.setCveIdTipoMovto(movimiento.getIdTipoMovimiento());
        movimientoType.setRegistroPatronal(movimiento.getRegistroPatronal());

        String xmlString = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movimientoType);
        log.debug("xmlstring: {}", xmlString);

        Session session = null;
        MessageProducer messageProducer = null;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            TextMessage message = session.createTextMessage(xmlString);
            messageProducer.send(message);
        }
        catch (JMSException e) {
            log.warn("JMSException!!!!!!!!!!\n {}", e.getMessage(), e);
            log.warn("Ignorando excepcion JMSException");
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                }
                catch (JMSException e) {
                    log.error("No se pudo cerrar el objeto Session: \n {}", e.getMessage(), e);
                }
            }
        }

    }

    private void manageTransaction(UserTransaction userTransaction) {
        if (userTransaction != null) {
            try {
                userTransaction.rollback();
            }
            catch(SystemException e) {
                log.warn("", e);
            }
        }
    }

    public void callQueue() {
        log.info("Adding message to queue");
        enqueueMovPatSujetoObligado(null);
    }

    public List<MovtoPatSujetoObligado> listMovtosCurrentDate() {
        return movtoPatSujetoObligadoEntityLocal.listMovtoPatSujetoObligadoCurrentDate();
    }

    public void schedule(Long timeout) {
        log.debug("timeout set to: {}", timeout);
        stopTimer();
        Timer t = timerService.createTimer(0L, 24L * 60L * 60000L, simpleName());
        log.info("Timer: " + t);
    }

    public void stopTimer() {
        log.debug("Trying to stop timers");
        Collection<Object> timers = timerService.getTimers();
        for(Object o:timers) {
            Timer t = (Timer) o;
            log.debug("timer name: {}", t.getInfo());
            if (simpleName().equals(t.getInfo())) {
                t.cancel();
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("PostConstruct      {}", MovtoPatSujetoObligadoBusiness.class.getSimpleName());
        log.debug("Queue:             {}", queue);
        log.debug("ConnecionFactory:  {}", factory);


        try {
            connection = factory.createConnection();
            connection.start();
        }
        catch(JMSException e) {
            throw new RuntimeException(e);
        }
        log.debug("Connection:        {}", connection);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("metodo preDestroy clase {}", MovtoPatSujetoObligadoBusiness.class.getSimpleName());
        try {
            connection.close();
        }
        catch (JMSException e) {
            log.warn("Problema cerrando conexion: \n{}\n", e.getMessage(), e);
        }
        queue = null;
        connection = null;
        factory = null;
    }

    private String simpleName() {
        return MovtoPatSujetoObligadoBusiness.class.getSimpleName();
    }

    public void setMovtoPatSujetoObligadoEntityLocal(MovtoPatSujetoObligadoEntityLocal movtoPatSujetoObligadoEntityLocal) {
        this.movtoPatSujetoObligadoEntityLocal = movtoPatSujetoObligadoEntityLocal;
    }
}

