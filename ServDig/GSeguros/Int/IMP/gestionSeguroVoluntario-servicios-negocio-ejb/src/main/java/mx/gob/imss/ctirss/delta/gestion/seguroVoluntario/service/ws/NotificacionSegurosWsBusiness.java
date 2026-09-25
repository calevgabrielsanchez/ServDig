package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.ws;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.NotificacionSegurosRemote;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

/**
 * Servicio para generar las cuotas a pagar por un trabajador en caso de querer adquirir un seguro
 * @author NOVUTECK1
 *
 */
@Stateless(name = "notificacionSegurosWsBusiness", mappedName = "notificacionSegurosWsBusiness")
@WebService(name = "notificacionSegurosService", portName = "notificacionSegurosServicePort", serviceName = "notificacionSegurosService",
        targetNamespace = "http://mx.gob.imss.ctirss.delta.seguroVoluntario/")
public class NotificacionSegurosWsBusiness implements NotificacionSegurosRemote {

    /**
     * Servicio para la cuota de servicios
     */
    @EJB(mappedName = "notificacionSegurosBusiness")
    private NotificacionSegurosRemote notificacionSegurosRemote;

    @WebMethod(exclude=true)
    public void notificaRenovacion() {
        // TODO Auto-generated method stub

    }


    @WebMethod(exclude=true)
    public void notificaProximoVencimiento() {
        // TODO Auto-generated method stub

    }


    @WebMethod(exclude=true)
    public void enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]> adjuntos) {
        // TODO Auto-generated method stub

    }


    @WebMethod(exclude=true)
    public void enviaCorreo(SeguroIvro seguro, int tipo) {
        // TODO Auto-generated method stub

    }


    @WebMethod(exclude=true)
    public void enviaCorreoTipoOperacion(SeguroIvro seguro, int tipoOperacion) {
        // TODO Auto-generated method stub

    }



    /**
     * Genera los calculos ed las cuotas a pagar por un empleado en un periodo de cobro
     * @param datosCalculoCuota DAtos a partir de los cuales se generan los calculos de cuotas
     * @return EL resultado de los calculos de cobreo para un empleado
     * @throws SUAException Error al generar los calculos de cuotas
     */
//    @WebMethod
//    @WebResult(name = "cotizacion", targetNamespace = IvroConstants.SER_VOL_NAMESSPACE)
//    public Cotizacion generaCotizacion(@WebParam(name = "datosCalculoCuota",
//    targetNamespace = IvroConstants.SER_VOL_NAMESSPACE) DatosCalculoCuota datosCalculoCuota)
//            throws IvroException {
//
//        return cuotaServiceRemote.generaCotizacion(datosCalculoCuota);
//
//    }

    @WebMethod(action="procesoEnviaCorreosDiario")
    @WebResult(name = "respuesta", targetNamespace = "http://mx.gob.imss.ctirss.delta.seguroVoluntario/")
    public String procesoEnviaCorreosDiario() {
		return notificacionSegurosRemote.procesoEnviaCorreosDiario();
    }

}