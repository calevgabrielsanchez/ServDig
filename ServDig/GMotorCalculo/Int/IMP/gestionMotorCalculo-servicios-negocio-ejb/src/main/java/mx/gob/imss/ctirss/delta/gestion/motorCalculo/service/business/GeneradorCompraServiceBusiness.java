/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneradorCompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SuaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.FormaPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "generadorCompraServiceBusiness", mappedName = "generadorCompraServiceBusiness")
public class GeneradorCompraServiceBusiness implements GeneradorCompraServiceRemote {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorCompraServiceBusiness.class);

    /**
     * servicio para el manejo de cotizaciones
     */
    @EJB
    private CotizadorEntityLocal cotizadorEntityLocal;
    /**
     * Servicio para generar los SUas
     */
    @EJB(mappedName = "suaServiceBusiness")
    private SuaServiceRemote suaServiceRemote;
    /**
     * Servicio paa el manejo de compras a nivel persistencia
     */
    @EJB
    private CompraServiceLocal compraServiceLocal;
    /**
     * Servicio para generar compras de seguros a partir de su cotizacion
     * @param cotizacion la cotizacion a convertirse en compra
     * @return la compra generada para la cotizacion
     * @throws SUAException Errores al generar la compra
     */
    public Compra generaCompra(Cotizacion cotizacion) throws SUAException {
        
        Cotizacion cotizacionCompra;
        // Obtenemos la cotizacion guardada para evitar cambios
        // Si es nueva la guardamos para poderla asociar despues a la compra
        if (cotizacion.getIdCotizacion() != null 
                && cotizacion.getIdCotizacion().longValue() > 0L) {
            LOGGER.debug("BUScando cotizacion");
            cotizacionCompra = cotizadorEntityLocal.findCotizacion(
                    cotizacion.getIdCotizacion());
        } else {
            cotizacionCompra = cotizadorEntityLocal.guardaCotizacion(cotizacion);
            LOGGER.debug("GUardando  cotizacion");
        }

        if (isNRPCotizacionNUll(cotizacionCompra) &&  isNRPCotizacionNUll(cotizacion)) {
            throw new SUAException(SUAConstants.MSG_NRP_NULL, SUAConstants.MSG_NRP_NULL);
        } else if (isNRPCotizacionNUll(cotizacionCompra) &&  !isNRPCotizacionNUll(cotizacion)) {
            cotizacionCompra.getDetalle().setNumeroRegistroPatronal(
                    cotizacion.getDetalle().getNumeroRegistroPatronal());
            LOGGER.debug("Actualizando cotizacion");
            cotizadorEntityLocal.actualizaCotizacion(cotizacionCompra);
        }else{
        	if(cotizacionCompra.getDetalle()!=null
        			&& cotizacionCompra.getDetalle().getModalidad()==ModalidadEnum.TREINTAYCUATRO.getId()
        			&& cotizacion.getDetalle()!=null){
        		
        		cotizacionCompra.getDetalle().setNumeroRegistroPatronal(
                        cotizacion.getDetalle().getNumeroRegistroPatronal());
                LOGGER.debug("Actualizando cotizacion");
                cotizadorEntityLocal.actualizaCotizacion(cotizacionCompra);
        	}
        }
        // Si al cotizacion de compra su renovacion es nula le pegamos el valor 
        //de lo que este en la peticion para permitir indicar una renovacion al momento de hacer 
        //la cotizacion o la compra
        if (cotizacionCompra.getRenovacion() == null && cotizacion.getRenovacion() != null) {
            cotizacionCompra.setRenovacion(cotizacion.getRenovacion());
        } else if (cotizacionCompra.getRenovacion() == null) {
            cotizacionCompra.setRenovacion(cotizacion.getDetalle().getRenovacion());
        }
        LOGGER.debug("generando pagos");
        SUAPago[] suas = suaServiceRemote.generaDatosSua(cotizacionCompra.getDetalle(), 
                cotizacion.getErrorFormGeneral());
        Date hoy = new Date(); 
        Compra compra = new Compra();
        compra.setFechaCompra(hoy);
        compra.setIdCotizacion(cotizacionCompra.getIdCotizacion());
        List<Pago> pagos = creaPagos(Arrays.asList(suas)); 
        compra.setPagos(pagos.toArray(new Pago[pagos.size()]));
        compra.setMonto(cotizacionCompra.getCuotaTotal());
        compra.setFechaLimite(getFechaLimiteCompra(pagos));
        boolean beneficio = cotizacionCompra.getDetalle().getConBeneficio() != null 
                && cotizacionCompra.getDetalle().getConBeneficio().booleanValue();
        boolean recargos = cotizacionCompra.getDetalle().getConRecargos() != null 
                && cotizacionCompra.getDetalle().getConRecargos().booleanValue();
		if (cotizacionCompra.getDetalle().getModalidad() == ModalidadEnum.CUARENTA.getId()) {
			compra.setFormaPago(FormaPagoEnum.MENSUAL.getId());
		} else if (cotizacionCompra.getDetalle().getModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
			compra.setFormaPago(FormaPagoEnum.ANUAL_ANTICIPADO.getId());
		} else {
        	compra.setFormaPago(beneficio || recargos ? FormaPagoEnum.BIMESTRAL.getId() : FormaPagoEnum.ANUAL.getId());
        }
        Compra compraPersistida = compraServiceLocal.guardaCompra(compra);
        LOGGER.debug("Se genero y persistio la compra, regresandola como resultado final del servicio");
        return compraPersistida;         
    }
    
    /**
     * Genera la lista de pagos a partir de la lista de suas generados
     * @param suas los suas que contiene la informacion del pago
     * @return la lista de pagos generadas
     */
    private List<Pago> creaPagos(List<SUAPago> suas) {
        List<Pago> pagos = new ArrayList<Pago>();
        for (SUAPago sua : suas) {
            Pago pago = new Pago();            
            pago.setFechaInicioPeriodo(sua.getFechaInicio());
            pago.setFechaFinPeriodo(sua.getFechaFin());
            pago.setFechaLimitePago(sua.getRegistroValidacion().getFechaLimiteDePago());
            pago.setSuaPago(sua);
            pago.setMonto(sua.getMonto());
            pagos.add(pago);
        }
        return pagos;        
    }
    
    /**
     * Obtiene la fecha limite inicial de la compra, que es la fecha mas baja de 
     * las fechas limites de cada pago
     * @param pagos la lista de pagos para obtener las fechas limites
     * @return lamenor fecha limite de pago
     */
    private Date getFechaLimiteCompra(List<Pago> pagos) {
        Date fechaLimite = null;
        for (Pago pago : pagos) {
            if (fechaLimite == null) {
                fechaLimite = pago.getFechaLimitePago();
            } else {
                fechaLimite = fechaLimite.before(pago.getFechaLimitePago()) ? fechaLimite 
                        : pago.getFechaLimitePago();
            }
        }
        return fechaLimite;
    }

    /**
     * MEtodo utilitario que indica si el numero de registro patronal es nulo en la cotizacion
     * @param cotizacion cotizacion a validar si es nulo su nrp
     * @return true si es nulo o vacio el nrp de la cotizacion
     */
    private boolean isNRPCotizacionNUll(Cotizacion cotizacion) {
        return StringUtils.trimToNull(cotizacion.getDetalle().getNumeroRegistroPatronal()) == null;
    }   
    
}
