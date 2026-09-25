package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoCompraEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.FormaPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoPago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicFormaPago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCotizacion;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitDetallePago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.EstadoCompra;
import mx.gob.imss.digital.modelo.cobranza.EstadoPago;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FActory para transformar pagos y compras del modelo xml al persistente
 * @author NOVUTECK1
 *
 */
public abstract class CompraFactoryUtil {
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompraFactoryUtil.class);

    /**
     * Metodo utlitario para transformar una compra del modelo xml al una compra del modelo persistencia
     * Esta tansformacion no toma en cuenta los pagos
     * @param compra la compra a ser transformada
     * @return una compra en el modelo persistente
     */
    public static final DitCompra generaDitCompra(Compra compra) {
        Date hoy = new Date();
        DitCompra ditCompra = new DitCompra();
        ditCompra.setDicEstadoCompra(getEstadoCompraNueva());
        
        DitCotizacion ditCotizacion = new DitCotizacion();
        ditCotizacion.setCveIdCotizacion(compra.getIdCotizacion());        
        ditCompra.setDitCotizacion(ditCotizacion);
        
        ditCompra.setFecLimitePago(compra.getFechaLimite());
        ditCompra.setFecRegistroActualizado(hoy);
        ditCompra.setFecRegistroAlta(hoy);
        ditCompra.setNumMonto(compra.getMonto());
        
        DicFormaPago dicFormaPago = new DicFormaPago();
        dicFormaPago.setCveIdFormaPago(compra.getFormaPago() != null 
                ? compra.getFormaPago() : FormaPagoEnum.ANUAL.getId());
        ditCompra.setDicFormaPgo(dicFormaPago);
        return ditCompra;
    }
    
    /**
     * Genera una lista de pagos persitentes a partir de una lista de pagos en modelo xml
     * @param pagos la lista de pagos a ser transformada
     * @param ditCompra la compra a asociar al pago
     * @return la lista de pagos persistentes
     * @throws SUAException Errores en la generacion de los pagos
     */
    public static final List<DitPago> generaDitPagos(List<Pago> pagos, DitCompra ditCompra) throws SUAException {
        List<DitPago> ditPagos = new ArrayList<DitPago>();
        for (Pago pago : pagos) {
            ditPagos.add(generaDitPago(pago, ditCompra));
        }
        return ditPagos;
    }
    
    /**
     * Obtiene un pago persistente a partir del pago del modelo xml
     * @param pago El pago a ser transformado
     * @param ditCompra la compra a asociar al pago
     * @return el pago generado
     * @throws SUAException errore en el parseo del detalle
     */
    private static final DitPago generaDitPago(Pago pago, DitCompra ditCompra) throws SUAException {
        Date hoy = new Date();
        DitPago ditPago = new DitPago();
        DitDetallePago ditDetallePago = new DitDetallePago();
        //ditDetallePago.setDitPago(ditPago);
        ditDetallePago.setFecRegistroActualizado(hoy);
        ditDetallePago.setFecRegistroAlta(hoy);
        try {
            ditDetallePago.setRefDetalleXml(JaxbUtil.marshaller(pago.getSuaPago()));
        } catch (JAXBException e) {
            throw new SUAException(SUAConstants.COD_JAXB, SUAConstants.MSG_JAXB);            
        }
        ditPago.setDitDetallePago(ditDetallePago);
        ditPago.setDicEstadoPago(getEstadoPagoInicial());
        ditPago.setDitCompra(ditCompra);        
        ditPago.setFecRegistroActualizado(hoy);
        ditPago.setFecRegistroAlta(hoy);
        ditPago.setDesLineaCaptura(pago.getLineaCaptura());
        ditPago.setFecFinPeriodo(pago.getFechaFinPeriodo());
        ditPago.setFecIniPeriodo(pago.getFechaInicioPeriodo());
        ditPago.setFecLimitePago(pago.getFechaLimitePago());
        if (pago.getIdPago() != null &&  pago.getIdPago() > 0) {
            ditPago.setCveIdPago(pago.getIdPago());
        }        
        return ditPago;
    }
    
    /**
     * Genera una compra a partir de su modelo persisente
     * @param ditCompra la compra a ser transformada
     * @return la compra en su modelo xml
     * @throws SUAException Errore s en la transformacion del objeto
     */
    public static final Compra generaCompra(DitCompra ditCompra) throws SUAException {
        LOGGER.debug("Transformando una compra persistida en su modelo xml");
        Compra compra = new Compra();
        compra.setFechaCompra(ditCompra.getFecRegistroAlta());
        compra.setFechaLimite(ditCompra.getFecLimitePago());
        compra.setIdCompra(ditCompra.getCveIdCompra());
        compra.setMonto(ditCompra.getNumMonto());
        compra.setIdCotizacion(ditCompra.getDitCotizacion() != null 
                ? ditCompra.getDitCotizacion().getCveIdCotizacion() : null);
        compra.setEstadoCompra(new EstadoCompra());
        if(ditCompra.getDicFormaPgo() != null) {
                compra.setFormaPago(ditCompra.getDicFormaPgo().getCveIdFormaPago());
            
        }
        boolean activa = false;
        if (ditCompra.getDicEstadoCompra() != null) {
            long idEstado = ditCompra.getDicEstadoCompra().getCveIdEstadoCompra();
            compra.getEstadoCompra().setIdEstadoCompra(idEstado);
            compra.getEstadoCompra().setDescripcion(ditCompra.getDicEstadoCompra().getDesEstadoCompra());
            activa = idEstado == EstadoCompraEnum.PAGADO.getId() || idEstado == EstadoCompraEnum.POR_PAGAR.getId();
        }
        boolean beneficio = false;
        if(ditCompra.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.BIMESTRAL.getId()) {
            beneficio = true;
        }
        compra.setPagos(generaPagos(ditCompra.getDitPagos(), activa, beneficio));
        return compra;
    }
    
    /**
     * Genera una lista de pagos en modelo xml a partir de los pagos persistentes
     * @param ditPagos la lista de pagos persitentes
     * @return la lista de pagos en modelo xml
     * @throws SUAException Errore s en la transformacion del objeto
     */
    private static final Pago[] generaPagos(List<DitPago> ditPagos, boolean compraActiva, boolean beneficio) throws SUAException {
        List<Pago> pagos = new ArrayList<Pago>();
        if (ditPagos != null && !ditPagos.isEmpty()) {
        	List<DitPago> lstDitPagos = ordenarListaPagos(ditPagos);

            for (DitPago ditPago : lstDitPagos) {
                pagos.add(generaPago(ditPago, compraActiva, primrPago(ditPago, ditPagos), beneficio));
            }            
        }        

        return pagos.toArray(new Pago[pagos.size()]);
    }
    
    private static List<DitPago> ordenarListaPagos(List<DitPago> pagosSeguroIvro){
		Collections.sort(pagosSeguroIvro, new Comparator<DitPago>() {
			@Override
			public int compare(DitPago pago1, DitPago pago2) {
				return pago2.getFecLimitePago().compareTo(pago1.getFecLimitePago());
			}
		}
		);
		return pagosSeguroIvro;
	}
    
    private static final boolean primrPago(DitPago ditPago, List<DitPago> ditPagos) {        
        for(DitPago pago : ditPagos ) {
            if(pago.getCveIdPago().longValue() != ditPago.getCveIdPago().longValue() 
                    && ditPago.getFecIniPeriodo().after(pago.getFecIniPeriodo())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Genera un pago en modelo xml a partir de uno en modelo persistente
     * @param ditPago el pago a ser transformado
     * @return el objeto pago generado
     * @throws SUAException Errore s en la transformacion del objeto
     */
    public  static final Pago generaPago(DitPago ditPago, boolean compraAtiva, boolean primerPago, boolean beneficio) throws SUAException {
        Pago pago = new Pago();        
        SUAPago suaPago = null;
        if (ditPago.getDitDetallePago() != null && ditPago.getDitDetallePago().getRefDetalleXml() != null) {
            try {            
                suaPago = JaxbUtil.unmarshaller(ditPago.getDitDetallePago().getRefDetalleXml(), 
                        SUAPago.class);
            } catch (JAXBException e) {
                throw new SUAException(SUAConstants.COD_JAXB, SUAConstants.MSG_JAXB);
            }
        }        
        pago.setConBeneficio(beneficio);
        pago.setFechaFinPeriodo(ditPago.getFecFinPeriodo());
        pago.setFechaInicioPeriodo(ditPago.getFecIniPeriodo());
        pago.setFechaLimitePago(ditPago.getFecLimitePago());
        pago.setLineaCaptura(ditPago.getDesLineaCaptura());
        pago.setPdf(ditPago.getRefPdfLc());
        pago.setMonto(suaPago != null ? suaPago.getMonto() : BigDecimal.ZERO);     
        
        suaPago = actualizaNombre(suaPago);
        
        pago.setSuaPago(suaPago);
        pago.setIdPago(ditPago.getCveIdPago());
        pago.setEstadoPago(new EstadoPago());
        pago.setImprimible(false);
        if (ditPago.getDicEstadoPago() != null) {
            pago.getEstadoPago().setIdEstadoPago(ditPago.getDicEstadoPago().getCveIdEstadoPago());
            pago.getEstadoPago().setDescripcion(ditPago.getDicEstadoPago().getDesEstadoPago());
            Calendar hoy = Calendar.getInstance();
            Calendar limite = Calendar.getInstance();
            limite.setTime(pago.getFechaLimitePago());
            if(((hoy.get(Calendar.MONTH) == limite.get(Calendar.MONTH) 
            		&& hoy.get(Calendar.YEAR) == limite.get(Calendar.YEAR)) || primerPago)
                    && pago.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.POR_PAGAR.getId()
                    && compraAtiva) {
                LOGGER.debug("Pago imprimible ");
                pago.setImprimible(true);
            }
        }        
        LOGGER.debug("Regresando pago transformado");
        return pago;
    }
        
    
    /**
     * Obtiene el estado de la compra inicial 
     * @return el estado de compra inicial
     */
    private static DicEstadoCompra getEstadoCompraNueva() {
        return getEstadoCompra(EstadoCompraEnum.POR_PAGAR.getId());
    }
    
    /**
     * Obtiene el estado de paco inicial
     * @return el estado de paga inicial
     */
    private static DicEstadoPago getEstadoPagoInicial() {
        return getEstadoPago(EstadoPagoEnum.POR_PAGAR.getId());
    }
    
    /**
     * Genera un estado de pago con el id indicado
     * @param id el id del estado de pago a generar
     * @return el estado de pago generado
     */
    public static DicEstadoPago getEstadoPago(Long id) {
        DicEstadoPago dicEstadoPago = new DicEstadoPago();
        dicEstadoPago.setCveIdEstadoPago(id);
        return dicEstadoPago;
    }
    
    /**
     * Genera un estado de compra con el id indicado
     * @param id el id del estado de compra a generar
     * @return el estado de compra generado
     */
    public static DicEstadoCompra getEstadoCompra(Long id) {
        DicEstadoCompra dicEstadoCompra = new DicEstadoCompra();
        dicEstadoCompra.setCveIdEstadoCompra(id);
        return dicEstadoCompra;
    }
    
    
    private static SUAPago actualizaNombre(SUAPago suaPago) {
    	if(suaPago==null) {
    		return null;
    	}
    	
    	if ( suaPago.getTrabajadores()!= null && suaPago.getTrabajadores().length>=1) {
    		Trabajador trabajador = suaPago.getTrabajadores()[0];
    		
    		if(trabajador.getNombreTrabajador()!=null &&trabajador.getNombreTrabajador().contains("-")) {
    			trabajador.setNombreTrabajador(trabajador.getNombreTrabajador().replace("-", " "));
    		}
    		
    		if(trabajador.getApellidoPaternoTrabajador()!=null &&trabajador.getApellidoPaternoTrabajador().contains("-")) {
    			trabajador.setApellidoPaternoTrabajador(trabajador.getApellidoPaternoTrabajador().replace("-", " "));
    		}
    		
    		if(trabajador.getApellidoMaternoTrabajador()!=null &&trabajador.getApellidoMaternoTrabajador().contains("-")) {
    			trabajador.setApellidoMaternoTrabajador(trabajador.getApellidoMaternoTrabajador().replace("-", " "));
    		}
    		
    		suaPago.getTrabajadores()[0] = trabajador;
    	}
    	
    	return suaPago;
    }
}
