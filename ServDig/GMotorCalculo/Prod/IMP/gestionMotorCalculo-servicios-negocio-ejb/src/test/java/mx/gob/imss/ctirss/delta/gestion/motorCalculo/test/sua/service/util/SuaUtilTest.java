package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SuaUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Clase de pruebas para las utilerias SUA
 * @author NOVUTECK1
 *
 */
public class SuaUtilTest {

    /**
     * Cantidad de control para la codificacion
     */
    private static final BigDecimal CANTIDAD = new BigDecimal("587.99");

    /**
     * Cadena de control para la codificacion SUE
     */
    private static final String CADENA = "FIN";
    
    @Test
    public void testGetDiasPeriodo() {
        new SuaUtil() {};
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFinal = Calendar.getInstance();
        fechaIni.add(Calendar.DAY_OF_MONTH, 1);
        fechaFinal.add(Calendar.DAY_OF_MONTH, 15);
        int diasPeriodo = SuaUtil.getDiasPeriodo(fechaIni.getTime(), fechaFinal.getTime());
        Assert.assertEquals("El periodo de tiempo debe ser 15 dias", 15, diasPeriodo);
    }
    
    @Test
    public void testFechaHabil() {
        Calendar fecha = Calendar.getInstance();
        fecha.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        
        Date fechaHabil = SuaUtil.getFechaHabil(fecha, new ArrayList<Date>());
        Calendar fecHabil = Calendar.getInstance();
        fecHabil.setTime(fechaHabil);
        Assert.assertEquals("LA fecha habil debe ser viernes", Calendar.FRIDAY, 
                fecHabil.get(Calendar.DAY_OF_WEEK));
        
        fecha.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        List<Date> diasFeriados = new ArrayList<Date>();
        diasFeriados.add(((Calendar) fecha.clone()).getTime());
        
        fechaHabil = SuaUtil.getFechaHabil(fecha, diasFeriados);
        fecHabil.setTime(fechaHabil);
        Assert.assertEquals("LA fecha habil debe ser viernes", Calendar.FRIDAY, 
                fecHabil.get(Calendar.DAY_OF_WEEK));
        
    }
    @Test
    public void testCodificaValores(){
        String codificado = SuaUtil.codificaValores(CANTIDAD);
        Assert.assertEquals("El valor codificado debe coincidir con el de control", codificado, CADENA);
        
        Assert.assertEquals("El valor codificado debe ser 000", SuaUtil.codificaValores(null), "000");
    }
    @Test
    public void testCadenaNoNula(){
        Assert.assertNotNull("LA cadena regresada no puede ser nula", 
                SuaUtil.getCadenaNoNula(null));
        Assert.assertEquals("LA cadena regresada debe ser igual", 
                SuaUtil.getCadenaNoNula("CAD"), "CAD");
    }
    @Test
    public void testClaveEntidad() {
        Assert.assertEquals("LA clave regresada debe ser igual", 
                SuaUtil.getClaveEntidad("25"), 25);
        
        Assert.assertEquals("LA clave debe ser cero", 
                SuaUtil.getClaveEntidad("CAD"), 0);
    }
    
    @Test
    @Ignore
    public void testGeneraFolioSUA() {
        PeriodoSUA periodo = new PeriodoSUA();
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.set(2014, 1, 1);
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.set(2104, 1, 20);
        periodo.setFechaInicio(fechaIni);
        periodo.setFechaFin(fechaFin);
        periodo.setPatron(new Patron());
        periodo.getPatron().setRegistroPatronalIMSS("Y5846422102");
        periodo.setTrabajadores(new ArrayList<Trabajador>());
        Trabajador trabajador = new Trabajador();
        trabajador.setNssTrabajador("01004200216");
        periodo.getTrabajadores().add(trabajador);
//        System.out.println(SuaUtil.generaFolioSUA(periodo));
        Assert.assertEquals("Debe generar el mismo folio", 943957, SuaUtil.generaFolioSUA(periodo));
    }
    
}
