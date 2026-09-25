/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MultipleCotizacionServiceLocal;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

/**
 * Implementacion de los servicio para generar multiples cotizaciones a partir
 * de una con varios trabajadores
 *
 * @author NOVUTECK1
 *
 */
@Stateless(name = "multipleCotizacionServiceEntity", mappedName = "multipleCotizacionServiceEntity")
public class MultipleCotizacionServiceEntity implements MultipleCotizacionServiceLocal {

    /**
     * Servicio para el manejo de las cotizaciones
     */
    @EJB
    private CotizadorEntityLocal cotizadorLocal;
    /**
     * Servicio para el calculo de cotizaciones
     */
    @EJB
    private CuotaServiceLocal cuotaServiceLocal;

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MultipleCotizacionServiceLocal#generaCotizaciones(mx.gob.imss.digital.modelo.cobranza.Cotizacion)
     */
    @Override
    public List<Cotizacion> generaCotizaciones(Cotizacion cotizacion) throws SUAException {
        List<Cotizacion> cotizaciones = new ArrayList<Cotizacion>();
        cotizacion = cotizadorLocal.findCotizacion(cotizacion.getIdCotizacion());
        if (cotizacion.getDetalle().getEmpleados().length > 1) {
            for (EmpleadoCuota empleado : cotizacion.getDetalle().getEmpleados()) {
                DatosCalculoCuota datosCalculo = generaDatosCalculo(cotizacion, empleado);
                Cotizacion cotizacionNueva = cuotaServiceLocal.generaCotizacion(datosCalculo);
                cotizaciones.add(cotizacionNueva);
            }
        } else {
            cotizaciones.add(cotizacion);
        }
        return cotizaciones;
    }

    /**
     * GEnera los datos de calculo para cada empleado de la cotizacion
     *
     * @param cotizacion la cotizacion a copiar valores
     * @param empleado el empleado a generarle sus nuevos datos
     * @return los datos de calculo para el empleado indicado
     */
    private DatosCalculoCuota generaDatosCalculo(Cotizacion cotizacion, EmpleadoCuota empleado) {
        DatosCalculoCuota datosCalculo = new DatosCalculoCuota();
        datosCalculo.setAplicaCuestionario(cotizacion.getAplicaCuestionario());
        datosCalculo.setConcepto(cotizacion.getConcepto());
        datosCalculo.setFechaFinCalculo(cotizacion.getDetalle().getFechaFinCalculo());
        datosCalculo.setFechaInicioCalculo(cotizacion.getDetalle().getFechaInicioCalculo());
        datosCalculo.setModalidad(cotizacion.getDetalle().getModalidad());
        datosCalculo.setNumeroRegistroPatronal(cotizacion.getDetalle().getNumeroRegistroPatronal());
        datosCalculo.setRenovacion(cotizacion.getRenovacion());
        datosCalculo.setZonaSalarial(cotizacion.getDetalle().getZonaSalarial());
        datosCalculo.setRecargos(cotizacion.getDetalle().getConRecargos());
        DatosEmpleado datosEmpleado = new DatosEmpleado();
        datosEmpleado.setNumeroSeguridadSocial(empleado.getNumeroSeguridadSocial());
        datosEmpleado.setSalario(empleado.getSalario());
        if (empleado.getParentesco() != null) {
            datosEmpleado.setParentesco(empleado.getParentesco()
                    .getIdParentesco());
        }

        if (empleado.getEdad() != null) {
            datosEmpleado.setEdad(empleado.getEdad());
        }
        if (StringUtils.isNotBlank(empleado.getCurp())) {
            datosEmpleado.setCurp(empleado.getCurp());
        }
        if (datosCalculo.getModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
            datosEmpleado.setAplicaCuestionario(empleado.getAplicaCuestionario());
            datosEmpleado.setInscripcion(empleado.getInscripcion());
            datosEmpleado.setIndividual(empleado.getIndividual());
            datosCalculo.setAplicaCuestionario(empleado.getAplicaCuestionario());
            datosCalculo.setRenovacion(!empleado.getInscripcion());
        }
        datosCalculo.setEmpleados(new DatosEmpleado[]{datosEmpleado});
        return datosCalculo;
    }

}
