/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.entity;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity.GeneradorDatosTrabajadorEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util.JaxbUtilT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Test;

/**
 * @author NOVUTECK1
 *
 */
public class GeneradorDatosTrabajadorEntityTest {

    /**
     * Xml para obtener el objeto calculo cuota
     */
    private static final String XML = "src/test/resources/CalculoCuotaTest.xml";
    
    @Test
    public void testGeneraTrabajador() throws Exception {
        
        GeneradorDatosTrabajadorEntity generadorTrabajador = new GeneradorDatosTrabajadorEntity();
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        EmpleadoCuota empleado = calculo.getEmpleados()[0];
        Trabajador trabajador = generadorTrabajador.generaTrabajador(empleado, getPersona(), 
                empleado.getPeriodos()[0], 0L);
        Assert.assertNotNull("El trabajador no puede ser nulo", trabajador);
        Assert.assertEquals("En el periodo 1 deben ser 31 dias", 
                trabajador.getDiasCotizadosEnElBimestre(), 31);
        Assert.assertEquals("En el periodo 1 no hay ausentismo", 
                trabajador.getDiasDeAusentismoEnElBimestre(), 0);
        Assert.assertEquals("Debe existir el movimiento de alta", 
                trabajador.getMovimientos().length, 1);
        
        trabajador = generadorTrabajador.generaTrabajador(empleado, getPersona(), 
                empleado.getPeriodos()[2], 0L);
        System.out.println(ReflectionToStringBuilder.toString(trabajador.getDiasCotizadosEnElMes()));
    }
    
    @Test
    public void testGeneraTrabajador_NoMov() throws Exception {
        
        GeneradorDatosTrabajadorEntity generadorTrabajador = new GeneradorDatosTrabajadorEntity();
        CalculoCuota calculo = JaxbUtilT.unmarshaller(XML, CalculoCuota.class);
        EmpleadoCuota empleado = calculo.getEmpleados()[0];
        Trabajador trabajador = generadorTrabajador.generaTrabajador(empleado, getPersona(), 
                empleado.getPeriodos()[1], 0L);
        Assert.assertNotNull("El trabajador no puede ser nulo", trabajador);
        Assert.assertEquals("En el periodo 2 deben ser 59 dias", 
                trabajador.getDiasCotizadosEnElBimestre(), 59);
        Assert.assertEquals("En el periodo 2 no hay ausentismo", 
                trabajador.getDiasDeAusentismoEnElBimestre(), 0);
        Assert.assertEquals("Debe existir el movimiento default", 
                trabajador.getMovimientos().length, 1);
        
    }
    
    private Fisica getPersona() {
        Fisica persona = new Fisica();
        persona.setNombre("Pepe");
        persona.setPrimerApellido("Pecas");
        persona.setSegundoApellido("Pecas");
        return persona;
    }
}
