/**
 * GeneraLineaCapturaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.imss.cit.clienteServiciosComunes.sipare.ws;

public class GeneraLineaCapturaServiceLocator extends org.apache.axis.client.Service implements mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaService {

    public GeneraLineaCapturaServiceLocator() {
    }


    public GeneraLineaCapturaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GeneraLineaCapturaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GeneraLineaCaptura
    private java.lang.String GeneraLineaCaptura_address = "http://172.26.18.30:10100/GeneraFormato5WService/services/GeneraLineaCaptura";

    public java.lang.String getGeneraLineaCapturaAddress() {
        return GeneraLineaCaptura_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GeneraLineaCapturaWSDDServiceName = "GeneraLineaCaptura";

    public java.lang.String getGeneraLineaCapturaWSDDServiceName() {
        return GeneraLineaCapturaWSDDServiceName;
    }

    public void setGeneraLineaCapturaWSDDServiceName(java.lang.String name) {
        GeneraLineaCapturaWSDDServiceName = name;
    }

    public mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCaptura getGeneraLineaCaptura() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GeneraLineaCaptura_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGeneraLineaCaptura(endpoint);
    }

    public mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCaptura getGeneraLineaCaptura(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaSoapBindingStub _stub = new mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaSoapBindingStub(portAddress, this);
            _stub.setPortName(getGeneraLineaCapturaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGeneraLineaCapturaEndpointAddress(java.lang.String address) {
        GeneraLineaCaptura_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCaptura.class.isAssignableFrom(serviceEndpointInterface)) {
                mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaSoapBindingStub _stub = new mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaSoapBindingStub(new java.net.URL(GeneraLineaCaptura_address), this);
                _stub.setPortName(getGeneraLineaCapturaWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GeneraLineaCaptura".equals(inputPortName)) {
            return getGeneraLineaCaptura();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://generacion.sipare.imss.gob.mx", "GeneraLineaCapturaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://generacion.sipare.imss.gob.mx", "GeneraLineaCaptura"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GeneraLineaCaptura".equals(portName)) {
            setGeneraLineaCapturaEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
