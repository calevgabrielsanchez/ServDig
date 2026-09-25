/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.ws.cuentaindividualhistorico.cliente;

import mx.gob.imss.cit.cda.support.config.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WSCuentaIndividualHistoricoTest extends BaseTest{
  
    public WSCuentaIndividualHistoricoTest(){}
    
    @Autowired
    WSHistoricoCentralSISEC service;
    @Test
    public void pruebaWSCuentaIndividualHistoricoTest() {
        
      
      
        RespuestaHistoricoCuentaIndividual respHistoricoCuentaIndividual = service.getHistoricoCentralSISEC("123");
        System.out.println("Obteniendo respuesta");
        
        if (respHistoricoCuentaIndividual != null) {
            if (respHistoricoCuentaIndividual.getHistcuentaIndividual() != null) {
                System.out.println("tamaño respuestaHistorico : " + respHistoricoCuentaIndividual.getHistcuentaIndividual().size());
            }
        }
    }
  
}
