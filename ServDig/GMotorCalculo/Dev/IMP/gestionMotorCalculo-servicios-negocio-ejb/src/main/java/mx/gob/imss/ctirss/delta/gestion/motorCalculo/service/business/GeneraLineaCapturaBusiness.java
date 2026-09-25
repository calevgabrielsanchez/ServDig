/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneraLineaCapturaRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.ws.GeneraLineaCapturaWs;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Pagos;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementacion del servio para el proceso SUA
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "generaLineaCapturaBusiness", mappedName = "generaLineaCapturaBusiness")
public class GeneraLineaCapturaBusiness implements GeneraLineaCapturaRemote {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneraLineaCapturaBusiness.class);
    /**
     * Terminacion de una url para ws
     */
    private static final String URL_END = "?wsdl";
    /**
     * Terminacion de una url para ws
     */
    private static final String URL_END_U = "?WSDL";
    /**
     * nombre del servio para el proceso su en el ws
     */
    private static final String SERVICE_NAME = "generaLCPagoServicePortBindingQSService";
    
    /**
     * Servicio para generar la linea de captura dados los pagos del servicio
     * @param pagos los datos el pago a realizar su linea de captura
     * @param urlServicio url donde se encuentra el web services del proceso sua
     * @return las lineas de captura generadas
     * @throws SUAException Errores generados en la ejecucion del servicios
     */
    public Pago[] generaLineasCaptura(Pago[] pagos, String urlServicio)
            throws SUAException {

        URL url = getUrlServicio(urlServicio);

        // Primer argumento uri especificada en el wsld
        // Segundo argumento el nombre del servicio especificado en el wsdl
        QName qname = new QName(SUAConstants.SUA_NAMESSPACE, SERVICE_NAME);
        Service service = Service.create(url, qname);
        GeneraLineaCapturaWs proceso = service.getPort(GeneraLineaCapturaWs.class);
        try {
            Pagos pagosLC = new Pagos();
            pagosLC.setPago(pagos);
            return proceso.generaLineaPago(pagosLC).getPago();
        } catch (SOAPFaultException e) {
            LOGGER.error("Error al consumir el servicio que genera las lineas de captura", e);
            throw new SUAException(SUAConstants.COD_WS_PROCESO, e.getMessage());
        }

    }

    /**
     * Obtiene la URL donde se encuentra el web service
     * 
     * @param urlServicio
     *            la url que indica el usuario
     * @return el objeto URL generado
     * @throws SUAException
     *             Errores con la url, es vacio o nula o no se encuenta en el
     *             formato esperado
     */
    private URL getUrlServicio(String urlServicio) throws SUAException {
        if (StringUtils.trimToNull(urlServicio) == null) {
            throw new SUAException(SUAConstants.COD_NO_URL, SUAConstants.MSG_NO_URL);
        }
        if (!(urlServicio.endsWith(URL_END) || urlServicio.endsWith(URL_END_U))) {
            urlServicio = urlServicio.concat(URL_END);
        }
        try {
            return new URL(urlServicio);
        } catch (MalformedURLException e) {
            throw new SUAException(SUAConstants.COD_MAL_URL, SUAConstants.MSG_MAL_URL);
        }
    }

}
