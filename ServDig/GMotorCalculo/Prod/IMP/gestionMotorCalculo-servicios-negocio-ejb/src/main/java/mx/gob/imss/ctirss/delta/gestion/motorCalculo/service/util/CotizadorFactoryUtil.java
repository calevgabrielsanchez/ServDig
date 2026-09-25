/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util;

import java.util.Date;

import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ConceptoEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicConcepto;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCotizacion;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitDetalleCotizacion;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory, paar armar cotizaciones xml a persistentes y viceversa
 * @author NOVUTECK1
 *
 */
public abstract class CotizadorFactoryUtil {

    /**
     * LOGGEr de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CotizadorFactoryUtil.class);
    /**
     * MEtodo utilitario para construir entidades de cotizacion ya sea del modelo xml o del persistente
     * @param cotizacion la cotizacion del modelo xml a ser transformada en un modelo persistente
     * @return la cotizacion en su forma persistente
     * @throws SUAException Error al generar ls parseos de la informacion
     */
    public static final DitCotizacion generaDeModeloXml(Cotizacion cotizacion) throws SUAException {
        Date hoy = new Date();
        
        DitDetalleCotizacion ditDetalleCotizacion = new DitDetalleCotizacion();
        ditDetalleCotizacion.setFecRegistroActualizado(hoy);
        ditDetalleCotizacion.setFecRegistroAlta(cotizacion.getFecha());
        try {
            ditDetalleCotizacion.setRefDetalleXml(JaxbUtil.marshaller(cotizacion.getDetalle()));
        } catch (Exception e) {
            LOGGER.error("Error al generar el detalle de la cotizacion", e);
            throw new SUAException(SUAConstants.COD_JAXB, SUAConstants.MSG_JAXB);
        }
        DitCotizacion ditCotizacion = new DitCotizacion();
        ditCotizacion.setCveIdCotizacion(cotizacion.getIdCotizacion());
        ditCotizacion.setDitDetalleCotizacion(ditDetalleCotizacion);
        ditCotizacion.setFechaInicio(cotizacion.getFecha());
        ditCotizacion.setFecRegistroActualizado(hoy);
        ditCotizacion.setFecVigencia(cotizacion.getFechaVigencia());
        ditCotizacion.setNumTotal(cotizacion.getCuotaTotal());
        DicConcepto dicConcepto = new DicConcepto();
        //Si tra el id de un concepto se agrega el default de seguro Ivro
        if (cotizacion.getConcepto() != null) {            
            dicConcepto.setCveIdConcepto(cotizacion.getConcepto());            
        } else {
            dicConcepto.setCveIdConcepto(ConceptoEnum.SEGURO_IVRO.getId());
        }
        ditCotizacion.setDicConcepto(dicConcepto);
        return ditCotizacion;
    }
    
    /**
     * Construlle una cotizacion del modelo xml a partir de una cotizacion del modelo persitente
     * @param ditCotizacion la cotizacion persistente
     * @return la cotizacion en el modelo xml
     * @throws SUAException Errores al transformar el datalle de la cotizacion
     */
    public static final Cotizacion generaDeModeloPersitente(DitCotizacion ditCotizacion) throws SUAException {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(ditCotizacion.getCveIdCotizacion());
        cotizacion.setCuotaTotal(ditCotizacion.getNumTotal());
        cotizacion.setFecha(ditCotizacion.getFechaInicio());
        cotizacion.setFechaVigencia(ditCotizacion.getFecVigencia());
        if (ditCotizacion.getDitDetalleCotizacion() != null) {
            DitDetalleCotizacion ditDetalle = ditCotizacion.getDitDetalleCotizacion();
            try {
                cotizacion.setDetalle(JaxbUtil.unmarshaller(ditDetalle.getRefDetalleXml(), 
                        CalculoCuota.class));
                cotizacion.setRenovacion(cotizacion.getDetalle().getRenovacion());
            } catch (JAXBException e) {
                throw new SUAException(SUAConstants.COD_JAXB, SUAConstants.MSG_JAXB);
            }
            
        }
        if (ditCotizacion.getDicConcepto() != null) {
            cotizacion.setConcepto(ditCotizacion.getDicConcepto().getCveIdConcepto());
        }
        return cotizacion;
    }
}
