/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.CotizadorEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.CuotaServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.DicFactorModalidadRamaEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.GeneradorPeriodosCobroEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.MotorBeneficiosBusinessEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.MotorCalculoServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.SalarioCalculoServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.DicFactorModalidadRamaEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneradorPeriodosCobroLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorBeneficiosBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SalarioCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoAportacion;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PRuebas unitarias
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CuotaServiceEntity.class, SalarioCalculoServiceEntity.class})
public class CuotaServiceBusinessTest {
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CuotaServiceBusinessTest.class);
    /**
     * Archivo con el xml que representa el objeto datoscalculo
     */
    private static final String XML = "src/test/resources/DatosCuotaTest.xml";
    /**
     * Instancia del servicio de pruebas 
     */
    CuotaServiceEntity cuotaService = new CuotaServiceEntity();
    /**
     * Motor de beneficioaMock
     */
    MotorBeneficiosBusinessLocal motorBeneficio;
    /**
     * Servicio salario calculo Mock
     */
    SalarioCalculoServiceLocal salarioCalculo;
    /**
     * Servicio para consulta de cuotas Mock
     */
    DicFactorModalidadRamaEntityLocal dicFactor;
    /**
     * Servicio de persistencia cotizacion mock
     */
    CotizadorEntityLocal cotizadorEntity;
    /**
     * Servicio de generacion de periodos
     */
    GeneradorPeriodosCobroLocal periodoCobro = new GeneradorPeriodosCobroEntity();
    /**
     * Servicio para los calculos
     */
    MotorCalculoServiceLocal motorCalculo = new MotorCalculoServiceEntity();

    /**
     * Prueba del calculo de cuotas
     * @throws Exception
     */
    @Test
    @Ignore
    public void testCalcularDatosCuota() throws Exception {
        LOGGER.debug("Prueba completa del calculo de cuotas");
        DatosCalculoCuota datos = JaxbUtilT.unmarshaller(XML, DatosCalculoCuota.class);
        iniciaMock(datos);
        Cotizacion cotizacion = cuotaService.generaCotizacion(datos);
        CalculoCuota calculo = cotizacion.getDetalle();
//        LOGGER.debug("Respuesta  {}", JaxbUtilT.marshaller(calculo));
        Assert.assertNotNull("El calculo de cuota no puede ser nulo", calculo);
        EmpleadoCuota[] empleados = calculo.getEmpleados();
        Assert.assertNotNull("Debe contener empleados", empleados);
        Assert.assertEquals("Solo debe existir un empledo ", 1, empleados.length);
        EmpleadoCuota empleado = empleados[0];
        PeriodoCuota[] periodos = empleado.getPeriodos();
        Assert.assertNotNull("Debe contener periodos de cobro", periodos);
        Assert.assertEquals("Dado el rango de fechas debe generar 7 periodos ", 7, periodos.length);
        terminaMock();
    }
    
    @Test(expected=SUAException.class)
    public void testCalcularDatosCuota_fechasMal() throws SUAException {
        DatosCalculoCuota datos = new DatosCalculoCuota();
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.add(Calendar.DATE, -1);
        datos.setFechaInicioCalculo(fechaIni);
        datos.setFechaFinCalculo(fechaFin);
        cuotaService.generaCotizacion(datos);
    }
    
    
    /**
     * Inicializa los valores de mocks 
     * @param datos
     * @throws Exception
     */
    private void iniciaMock(DatosCalculoCuota datos) throws Exception {
        
        motorBeneficio = EasyMock.createMock(MotorBeneficiosBusinessEntity.class);                
        EasyMock.expect(motorBeneficio.buscarBeneficioTrabajador(EasyMock.anyObject(
                ValoresCalculoEmpleado.class))).andReturn(null);        
        Whitebox.setInternalState(cuotaService, "motorBeneficiosBusinessLocal", motorBeneficio);
        
        salarioCalculo = PowerMock.createPartialMock(
                SalarioCalculoServiceEntity.class, "getSalarioMinimo");        
        PowerMock.expectPrivate(salarioCalculo, "getSalarioMinimo", 
                datos.getZonaSalarial(), ModalidadEnum.fromId(datos.getModalidad()), 
                PeriodoUtil.truncaFecha(datos.getFechaInicioCalculo())).andReturn(BigDecimal.TEN);
                        
        Whitebox.setInternalState(cuotaService, "salarioCalculoServiceLocal", salarioCalculo);
        Whitebox.setInternalState(cuotaService, "generadorPeriodosCobroLocal", periodoCobro);
        
        dicFactor = EasyMock.createMock(DicFactorModalidadRamaEntity.class);
        EasyMock.expect(dicFactor.buscarRamasCalculo(datos.getModalidad(), 
                datos.getNumeroRegistroPatronal())).andReturn(getRamasMock());
        Whitebox.setInternalState(cuotaService, "dicFactorModalidadRamaEntityLocal", dicFactor);
        
        Whitebox.setInternalState(cuotaService, "motorCalculoServiceLocal", motorCalculo);
        
        cotizadorEntity = EasyMock.createMock(CotizadorEntity.class);
        final Capture<CalculoCuota> calculoC = new Capture<CalculoCuota>();
        EasyMock.expect(cotizadorEntity.generaYGuardaCotizacion(EasyMock.capture(calculoC))).andAnswer(
                new IAnswer<Cotizacion>() {
                    @Override
                    public Cotizacion answer() throws Throwable {
                        Cotizacion cotizacionR = new Cotizacion();
                        cotizacionR.setDetalle(calculoC.getValue());
                        return cotizacionR;
                    }                    
        });
        Whitebox.setInternalState(cuotaService, "cotizadorEntity", cotizadorEntity);
        
        PowerMock.replay(motorBeneficio, salarioCalculo, dicFactor, cotizadorEntity);
    }
    
    /**
     * Termina los mocks 
     */
    private void terminaMock() {
        PowerMock.verify(motorBeneficio, salarioCalculo, dicFactor, cotizadorEntity);
    }
    
    /**
     * Obtiene las ramas de calculo para las pruebas
     * @return
     */
    private List<RamaCalculo> getRamasMock() {
        RamaCalculo ramaRiesgoTrabajo = new RamaCalculo("NRP", "NRP", BigDecimal.ONE, 
                Integer.valueOf(ClavesRama.RIESGOS_TRABAJO), Integer.valueOf(ClavesTipoAportacion.PATRONAL));
        RamaCalculo ramaCuotaFija = new RamaCalculo("NRP", "NRP", BigDecimal.ONE, 
                Integer.valueOf(ClavesRama.CUOTA_FIJA), Integer.valueOf(ClavesTipoAportacion.PATRONAL));
        List<RamaCalculo> ramasMock = new ArrayList<RamaCalculo>();
        ramasMock.add(ramaRiesgoTrabajo);
        ramasMock.add(ramaCuotaFija);
        return ramasMock;
    }
}
