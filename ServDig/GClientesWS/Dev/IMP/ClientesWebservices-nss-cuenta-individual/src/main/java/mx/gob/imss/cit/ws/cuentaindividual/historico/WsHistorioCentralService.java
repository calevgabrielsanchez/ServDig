/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.ws.cuentaindividual.historico;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;

/**
 *
 * @author antonio
 */
public class WsHistorioCentralService extends WSHistoricoCentral_Service{
  
  private final static int SERVICE_TIME_OUT = 30000;
  private final static int CONNECT_TIME_OUT = 5000;
  
  @Override
  public WSHistoricoCentral getWSHistoricoCentralPort() {
    WSHistoricoCentral ws = super.getWSHistoricoCentralPort();
    setTimeOuts(ws);
    return ws;
  }
  
  @Override
  public WSHistoricoCentral getWSHistoricoCentralPort(WebServiceFeature... features) {
    WSHistoricoCentral ws = super.getWSHistoricoCentralPort(features);
    setTimeOuts(ws);
    return ws;
  }
  
  private WSHistoricoCentral setTimeOuts(WSHistoricoCentral ws){
    ((BindingProvider) ws).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", SERVICE_TIME_OUT);
    ((BindingProvider) ws).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", CONNECT_TIME_OUT);
    ((BindingProvider) ws).getRequestContext().put("com.sun.xml.ws.request.timeout", SERVICE_TIME_OUT);
    ((BindingProvider) ws).getRequestContext().put("com.sun.xml.ws.connect.timeout", CONNECT_TIME_OUT);
    return ws;
  }
}
