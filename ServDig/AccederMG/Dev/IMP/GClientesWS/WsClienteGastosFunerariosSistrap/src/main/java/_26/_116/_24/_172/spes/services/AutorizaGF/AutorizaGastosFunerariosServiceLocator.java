/**
 * AutorizaGastosFunerariosServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package _26._116._24._172.spes.services.AutorizaGF;

public class AutorizaGastosFunerariosServiceLocator extends org.apache.axis.client.Service implements _26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerariosService {

    public AutorizaGastosFunerariosServiceLocator() {
    }


    public AutorizaGastosFunerariosServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AutorizaGastosFunerariosServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AutorizaGF
    private java.lang.String AutorizaGF_address = "http://10.100.6.75:10001/OperacionesSistrap/AutorizaGF";

    public java.lang.String getAutorizaGFAddress() {
        return AutorizaGF_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AutorizaGFWSDDServiceName = "AutorizaGF";

    public java.lang.String getAutorizaGFWSDDServiceName() {
        return AutorizaGFWSDDServiceName;
    }

    public void setAutorizaGFWSDDServiceName(java.lang.String name) {
        AutorizaGFWSDDServiceName = name;
    }

    public _26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerarios getAutorizaGF() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AutorizaGF_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAutorizaGF(endpoint);
    }

    public _26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerarios getAutorizaGF(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            _26._116._24._172.spes.services.AutorizaGF.AutorizaGFSoapBindingStub _stub = new _26._116._24._172.spes.services.AutorizaGF.AutorizaGFSoapBindingStub(portAddress, this);
            _stub.setPortName(getAutorizaGFWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAutorizaGFEndpointAddress(java.lang.String address) {
        AutorizaGF_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (_26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerarios.class.isAssignableFrom(serviceEndpointInterface)) {
                _26._116._24._172.spes.services.AutorizaGF.AutorizaGFSoapBindingStub _stub = new _26._116._24._172.spes.services.AutorizaGF.AutorizaGFSoapBindingStub(new java.net.URL(AutorizaGF_address), this);
                _stub.setPortName(getAutorizaGFWSDDServiceName());
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
        if ("AutorizaGF".equals(inputPortName)) {
            return getAutorizaGF();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.100.6.75:10000/spes/services/AutorizaGF", "AutorizaGastosFunerariosService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.100.6.75:10000/spes/services/AutorizaGF", "AutorizaGF"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AutorizaGF".equals(portName)) {
            setAutorizaGFEndpointAddress(address);
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
