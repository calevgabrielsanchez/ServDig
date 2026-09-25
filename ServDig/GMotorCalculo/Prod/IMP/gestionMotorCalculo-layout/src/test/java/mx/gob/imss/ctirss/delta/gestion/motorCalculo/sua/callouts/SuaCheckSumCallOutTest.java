/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.callouts;

import junit.framework.Assert;

import org.junit.Test;

/**
 * CLase de prueba parala generacion de checksum
 * @author NOVUTECK1
 *
 */
public class SuaCheckSumCallOutTest {
    
    /**
     * CAdena con el contenido de un archivo de prueba
     */
    private static final String archivoPrueba = "02A8199999435IMSS970701   201504479330DELICIAS                                          SUBDELEGACION DEL IMSS                  DELICIAS                                0800000               0000000000000INCORPORACION VOLUNTARIA DEL CAMPO      0805C00N3000003100000000101000000000                03A8199999435IMSS970701   20150411111111115RISS700101A81                                    01VOLUNTARIA$DEL CAMPO$PRUEBA RISS                  000672943310000004255400000000000396000297200000000004954000000000000003100000004172000000000065710002347000000000000000000000000000000001g0Cc0L20brA8104A8199999435111111111150020150301        000006729                                                                                                                                                                                                                                                    05A8199999435IMSS970701   20150447933000000000000000000000000042554000000000000000396000002972000000000000004954000000000000000508760000000000000000000000041720000089180000001309000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000               06A8199999435IMSS970701   201504479330000000000000000014752014033101E3950000000508760000000130900000000000000000000000004859862039320881256000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000                                 000                     ";
    
    
    @Test
    public void test(){
        
        String archivo = SuaCheckSumCallOut.generaYAgregaCheckSUm(archivoPrueba.getBytes());
        Assert.assertEquals("La cadena generada debe ser igual a la de prueba", archivo, archivoPrueba);
        
    }
}
