/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CotizadorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCotizacion;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;

/**
 * Servicio para el manejo de cotizaciones a nivel persistencia principalmente
 *
 * @author NOVUTECK1
 *
 */
@Stateless(name = "cotizadorEntity", mappedName = "cotizadorEntity")
public class CotizadorEntity implements CotizadorEntityLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CotizadorEntity.class);
    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * GEnera una cotizacion y la persiste a partir de los datos del calculo
     * realizados
     *
     * @param calculos los datos de calculo para la cotizacion
     * @return Una cotizacion en el modelo xml
     * @throws SUAException Errores en la generacion o persistencia de una
     * cotizacion
     */
    public Cotizacion generaYGuardaCotizacion(CalculoCuota calculos) throws SUAException {
        if (calculos == null) {
            throw new SUAException(SUAConstants.COD_NO_DATOS_CALCULO, SUAConstants.MSG_NO_DATOS_CALCULO);
        }
        Date hoy = new Date();
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCuotaTotal(calculos.getCuotaTotal());
        cotizacion.setFecha(hoy);
        // POr el momento la vigencia de una cotizacion sera el mismo dia
        cotizacion.setFechaVigencia(hoy);
        cotizacion.setDetalle(calculos);
        cotizacion.setConcepto(calculos.getConcepto());
        if (calculos.getModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
            cotizacion.setAplicaCuestionario(calculos.getEmpleados()[0].getAplicaCuestionario());
        }
        return guardaCotizacion(cotizacion);
    }

    /**
     * Busca las cotizacion asociada al id ingresado como parametro
     *
     * @param cveIdCotizacion Id de la cotizacion persitente
     * @return LA cotizacion encontrada
     * @throws SUAException Error al no encontrar una cotizacion
     */
    public Cotizacion findCotizacion(long cveIdCotizacion) throws SUAException {
        DitCotizacion ditCotizacion = entityManager.find(DitCotizacion.class, new Long(cveIdCotizacion));
        if (ditCotizacion == null) {
            throw new SUAException(SUAConstants.COD_COTIZACION_NOT_FOUND, SUAConstants.MSG_COTIZACION_NOT_FOUND);
        }
        return CotizadorFactoryUtil.generaDeModeloPersitente(ditCotizacion);
    }

    /**
     * Guarda una cotizacion en la BD a partir de los datos de la cotizacion
     *
     * @param cotizacion los datos de la cotizacion a ser guardados
     * @return la cotizacion ya persistida
     * @throws SUAException Error al guardar la cotizacion
     */
    public Cotizacion guardaCotizacion(Cotizacion cotizacion) throws SUAException {
        DitCotizacion ditCotizacion = CotizadorFactoryUtil.generaDeModeloXml(cotizacion);
        guardaDitCotizacion(ditCotizacion);
        return CotizadorFactoryUtil.generaDeModeloPersitente(ditCotizacion);
    }

    /**
     * Persiste una cotizacion en BD
     *
     * @param ditCotizacion la cotizaion a persitir
     */
    private void guardaDitCotizacion(DitCotizacion ditCotizacion) {
        entityManager.persist(ditCotizacion);
        if (ditCotizacion.getDitDetalleCotizacion() != null) {
            // Si tiene detalle guardamos el detalle asociandole la cotizacion
            ditCotizacion.getDitDetalleCotizacion().setDitCotizacion(ditCotizacion);
            entityManager.persist(ditCotizacion.getDitDetalleCotizacion());
        }
    }

    /**
     * Actualiza una cotizacion
     *
     * @param cotizacion la cotizacion a actualizar
     * @return la cotizacion actualizada
     * @throws SUAException errores en la actualizacion
     */
    public Cotizacion actualizaCotizacion(Cotizacion cotizacion) throws SUAException {
        DitCotizacion ditCotizacion = CotizadorFactoryUtil.generaDeModeloXml(cotizacion);
        if (ditCotizacion.getDitDetalleCotizacion() != null) {
            DitCotizacion coti = entityManager.find(DitCotizacion.class, ditCotizacion.getCveIdCotizacion());

            ditCotizacion.getDitDetalleCotizacion().setDitCotizacion(ditCotizacion);
            ditCotizacion.getDitDetalleCotizacion().setCveIdDetalle(coti.getDitDetalleCotizacion().getCveIdDetalle());
            LOGGER.debug("Objeto a actualizar {}", ReflectionToStringBuilder.toString(
                    ditCotizacion.getDitDetalleCotizacion()));
            entityManager.merge(ditCotizacion.getDitDetalleCotizacion());
        }
        return CotizadorFactoryUtil.generaDeModeloPersitente(ditCotizacion);
    }

}
