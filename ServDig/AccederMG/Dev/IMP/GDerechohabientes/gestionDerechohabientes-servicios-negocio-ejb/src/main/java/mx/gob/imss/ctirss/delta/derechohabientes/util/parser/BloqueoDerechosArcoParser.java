package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.GenericDerechohabientesException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;
import mx.gob.imss.ctirss.delta.persistence.DitBloqueoDerechosArco;
import org.apache.log4j.Logger;

import java.util.Date;

public class BloqueoDerechosArcoParser {

    private static final Logger logger = Logger.getLogger(BloqueoDerechosArcoParser.class);

    public static DitBloqueoDerechosArco modelToPersist(BloqueoDerechosArco entrada) throws GenericDerechohabientesException {

        DitBloqueoDerechosArco salida;

        try {
            Date fechaActual =  new Date();
            salida = new DitBloqueoDerechosArco();
            salida.setCveIdAsignacionNss(entrada.getIdAsignacionNss());
            salida.setCveIdDelegacion(entrada.getIdDelegacion());
            salida.setCveIdSubdelegacion(entrada.getIdSubdelegacion());
            salida.setCveIdTipoTramite(entrada.getIdTipoTramite());
            salida.setCveIdUsuario(entrada.getIdUsuario());
            salida.setFecRegistroActualizado(fechaActual);
            salida.setFecRegistroAlta(entrada.getFecRegistroAlta());
            salida.setFecRegistroBaja(entrada.getFecRegistroBaja());
            salida.setIndBloqueo(entrada.isIndBloqueo());
            salida.setRefObservacion(entrada.getMotivos());
        } catch (Exception e) {
            logger.error("Ocurrio un error al convertir el modelo de negocio BloqueoDerechosArco al modelo de persistencia: " + e);
            throw new GenericDerechohabientesException(e);
        }

        return salida;
    }

    public static BloqueoDerechosArco persistToModel(DitBloqueoDerechosArco entrada) throws GenericDerechohabientesException {

        BloqueoDerechosArco salida;

        try {
            salida = new BloqueoDerechosArco();
            salida.setIdAsignacionNss(entrada.getCveIdAsignacionNss());
            salida.setIdDelegacion(entrada.getCveIdDelegacion());
            salida.setIdSubdelegacion(entrada.getCveIdSubdelegacion());
            salida.setIdTipoTramite(entrada.getCveIdTipoTramite());
            salida.setIdUsuario(entrada.getCveIdUsuario());
            salida.setIndBloqueo(entrada.isIndBloqueo());
            salida.setMotivos(entrada.getRefObservacion());
            salida.setFecRegistroAlta(entrada.getFecRegistroAlta());
            salida.setFecRegistroActualizado(entrada.getFecRegistroActualizado());
            salida.setFecRegistroBaja(entrada.getFecRegistroBaja());

        }catch (Exception e) {
            logger.error("Ocurrio un error al convertir el modelo de persistencia DitBloqueoDerechosArco al modelo de negocio: " + e);
            throw new GenericDerechohabientesException(e);
        }

        return salida;
    }

}
