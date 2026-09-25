package org.tempuri;

public class WsConsultaCURPSoapProxy implements org.tempuri.WsConsultaCURPSoap {
  private String _endpoint = null;
  private org.tempuri.WsConsultaCURPSoap wsConsultaCURPSoap = null;
  
  public WsConsultaCURPSoapProxy() {
    _initWsConsultaCURPSoapProxy();
  }
  
  public WsConsultaCURPSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWsConsultaCURPSoapProxy();
  }
  
  private void _initWsConsultaCURPSoapProxy() {
    try {
      wsConsultaCURPSoap = (new org.tempuri.WsConsultaCURPLocatorTTD()).getwsConsultaCURPSoap();
      if (wsConsultaCURPSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wsConsultaCURPSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wsConsultaCURPSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wsConsultaCURPSoap != null)
      ((javax.xml.rpc.Stub)wsConsultaCURPSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.WsConsultaCURPSoap getWsConsultaCURPSoap() {
    if (wsConsultaCURPSoap == null)
      _initWsConsultaCURPSoapProxy();
    return wsConsultaCURPSoap;
  }
  
  public org.tempuri.ConsultaCURPResponseConsultaCURPResult consultaCURP(java.lang.String CURP, java.lang.String matricula) throws java.rmi.RemoteException{
    if (wsConsultaCURPSoap == null)
      _initWsConsultaCURPSoapProxy();
    return wsConsultaCURPSoap.consultaCURP(CURP, matricula);
  }
  
  
}