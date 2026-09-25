/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.motorCalculo.service.entity;

import java.util.Calendar;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity.MotorBeneficiosBusinessEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;

import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOVUTECK1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MotorBeneficiosBusinessEntity.class)
public class MotorBeneficiosBusinessTest {
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MotorBeneficiosBusinessTest.class);

    
    private static final String NSS_PRUEBA = "12345678912";
    private static final String NPR_PRUEBA = "12345678";
    
    private static final Calendar FECHA = Calendar.getInstance();
    
    MotorBeneficiosBusinessEntity motor = new MotorBeneficiosBusinessEntity();
    
    
    @Test
    @Ignore
    public void testBuscarBeneficioTrabajadorNss() throws Exception{
        LOGGER.debug("Probando la consulta de benficio, con mock ");                
        BeneficioRissServiceBusinessRemote beneficioRissService = EasyMock.createMock(
                BeneficioRissServiceBusinessRemote.class);
        EasyMock.expect(beneficioRissService.obtenerBeneficioPorNSS(
                NSS_PRUEBA, FECHA.getTime(), FECHA.getTime())).andReturn(new Beneficio());
        Whitebox.setInternalState(motor, "beneficioRissServiceBusinessRemote", beneficioRissService);
               
        EasyMock.replay(beneficioRissService);
        ValoresCalculoEmpleado valores = getValoresCalculo(22);
        Beneficio beneficio = motor.buscarBeneficioTrabajador(valores);
        Assert.assertNotNull("Se debe encontrar beneficio para la modalidad", beneficio);
        // para una modaldad desconocida no hay beneficio
        valores.setModalidad(2);
        beneficio = motor.buscarBeneficioTrabajador(valores);
        Assert.assertNull("Se debe encontrar beneficio para la modalidad", beneficio);
                
        EasyMock.verify(beneficioRissService); 
    }
    
    @Test
    public void testBuscarBeneficioTrabajadorNss_EX() throws Exception{
                
        BeneficioRissServiceBusinessRemote beneficioRissService = EasyMock.createMock(
                BeneficioRissServiceBusinessRemote.class);
        EasyMock.expect(beneficioRissService.obtenerBeneficioPorNSS(
                NSS_PRUEBA, FECHA.getTime(), FECHA.getTime())).andThrow(new BeneficioRissException("Error desde mock"));
        Whitebox.setInternalState(motor, "beneficioRissServiceBusinessRemote", beneficioRissService);
               
        EasyMock.replay(beneficioRissService);
        ValoresCalculoEmpleado valores = getValoresCalculo(22);
        Beneficio beneficio = motor.buscarBeneficioTrabajador(valores);
        Assert.assertNull("Si el servicio de benefcios regresa error el beneficio es nulo", beneficio);
                      
        EasyMock.verify(beneficioRissService); 
    }
    
    @Test
    public void testBuscaBeneficiosTrabajadorNPR() throws Exception {
        
        ValoresCalculoEmpleado valores = getValoresCalculo(1);                
        BeneficioRissServiceBusinessRemote beneficioRissService = EasyMock.createMock(
                BeneficioRissServiceBusinessRemote.class);
        EasyMock.expect(beneficioRissService.obtenerBeneficioPorNRP(
                NPR_PRUEBA, FECHA.getTime(), FECHA.getTime())).andReturn(new Beneficio());
        Whitebox.setInternalState(motor, "beneficioRissServiceBusinessRemote", beneficioRissService);
        
        EasyMock.replay(beneficioRissService);
        
        Beneficio beneficio = motor.buscarBeneficioTrabajador(valores);
        Assert.assertNotNull("Se debe encontrar beneficio para la modalidad", beneficio);
                        
        EasyMock.verify(beneficioRissService);        
    }
    
    @Test
    public void testBuscaBeneficiosTrabajadorNPR_EX() throws Exception {
        
        ValoresCalculoEmpleado valores = getValoresCalculo(1);     
        BeneficioRissServiceBusinessRemote beneficioRissService = EasyMock.createMock(
                BeneficioRissServiceBusinessRemote.class);
        EasyMock.expect(beneficioRissService.obtenerBeneficioPorNRP(
                NPR_PRUEBA, FECHA.getTime(), FECHA.getTime())).andThrow(new BeneficioRissException("Error generado en el test"));
        Whitebox.setInternalState(motor, "beneficioRissServiceBusinessRemote", beneficioRissService);
        
        EasyMock.replay(beneficioRissService);
        
        Beneficio beneficio = motor.buscarBeneficioTrabajador(valores);
        Assert.assertNull("Sel servicio de beneficio generara un error el neneficio es nulo", beneficio);
                        
        EasyMock.verify(beneficioRissService);        
    }
    
    private ValoresCalculoEmpleado getValoresCalculo(long idModalidad) {
        ValoresCalculoEmpleado valores = new ValoresCalculoEmpleado();
        valores.setFechaInicioCalculo(FECHA);
        valores.setFechaFinCalculo(FECHA);
        valores.setModalidad(idModalidad);
        valores.setEmpleado(new DatosEmpleado());
        valores.getEmpleado().setNumeroSeguridadSocial(NSS_PRUEBA);
        valores.setNumeroRegistroPatronal(NPR_PRUEBA);
        return valores;
    }
}
