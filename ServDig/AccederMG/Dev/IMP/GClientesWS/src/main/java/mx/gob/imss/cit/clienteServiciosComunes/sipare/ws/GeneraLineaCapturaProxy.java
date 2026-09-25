package mx.gob.imss.cit.clienteServiciosComunes.sipare.ws;

public class GeneraLineaCapturaProxy implements mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCaptura {
  private String _endpoint = null;
  private mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCaptura generaLineaCaptura = null;
  
  public GeneraLineaCapturaProxy() {
    _initGeneraLineaCapturaProxy();
  }
  
  public GeneraLineaCapturaProxy(String endpoint) {
    _endpoint = endpoint;
    _initGeneraLineaCapturaProxy();
  }
  
  private void _initGeneraLineaCapturaProxy() {
    try {
      generaLineaCaptura = (new mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaServiceLocator()).getGeneraLineaCaptura();
      if (generaLineaCaptura != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)generaLineaCaptura)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)generaLineaCaptura)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (generaLineaCaptura != null)
      ((javax.xml.rpc.Stub)generaLineaCaptura)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCaptura getGeneraLineaCaptura() {
    if (generaLineaCaptura == null)
      _initGeneraLineaCapturaProxy();
    return generaLineaCaptura;
  }
  
  public double multiplica(double num1, double num2) throws java.rmi.RemoteException{
    if (generaLineaCaptura == null)
      _initGeneraLineaCapturaProxy();
    return generaLineaCaptura.multiplica(num1, num2);
  }
  
  public mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.bean.Formato5 getFormato5(java.lang.String suaLine, int tipoPatron) throws java.rmi.RemoteException{
    if (generaLineaCaptura == null)
      _initGeneraLineaCapturaProxy();
    return generaLineaCaptura.getFormato5(suaLine, tipoPatron);
  }
  
  
}