/**
 * WsConsultaCURPLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

import java.util.ResourceBundle;

import mx.gob.imss.ctirss.sso.admonusuarios.ttds.util.ResourceBundleConfiguration;

public class WsConsultaCURPLocatorTTD extends org.apache.axis.client.Service implements org.tempuri.WsConsultaCURP {

    public WsConsultaCURPLocatorTTD() {
    	ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceBundle();
        this.wsConsultaCURPSoap_address = resourceBundle.getString("end.point.address.ttds");
    }


    public WsConsultaCURPLocatorTTD(org.apache.axis.EngineConfiguration config) {
        super(config);
        ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceBundle();
        this.wsConsultaCURPSoap_address = resourceBundle.getString("end.point.address.ttds");
    }

    public WsConsultaCURPLocatorTTD(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
        ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceBundle();
        this.wsConsultaCURPSoap_address = resourceBundle.getString("end.point.address.ttds");
    }

    // Use to get a proxy class for wsConsultaCURPSoap
    private java.lang.String wsConsultaCURPSoap_address;

    public java.lang.String getwsConsultaCURPSoapAddressTTD() {
        return wsConsultaCURPSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wsConsultaCURPSoapWSDDServiceName = "wsConsultaCURPSoap";

    public java.lang.String getwsConsultaCURPSoapWSDDServiceName() {
        return wsConsultaCURPSoapWSDDServiceName;
    }

    public void setwsConsultaCURPSoapWSDDServiceName(java.lang.String name) {
        wsConsultaCURPSoapWSDDServiceName = name;
    }

    public org.tempuri.WsConsultaCURPSoap getwsConsultaCURPSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wsConsultaCURPSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwsConsultaCURPSoap(endpoint);
    }

    public org.tempuri.WsConsultaCURPSoap getwsConsultaCURPSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.WsConsultaCURPSoapStubTTD _stub = new org.tempuri.WsConsultaCURPSoapStubTTD(portAddress, this);
            _stub.setPortName(getwsConsultaCURPSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwsConsultaCURPSoapEndpointAddress(java.lang.String address) {
        wsConsultaCURPSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.WsConsultaCURPSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.WsConsultaCURPSoapStubTTD _stub = new org.tempuri.WsConsultaCURPSoapStubTTD(new java.net.URL(wsConsultaCURPSoap_address), this);
                _stub.setPortName(getwsConsultaCURPSoapWSDDServiceName());
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
        if ("wsConsultaCURPSoap".equals(inputPortName)) {
            return getwsConsultaCURPSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "wsConsultaCURP");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "wsConsultaCURPSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("wsConsultaCURPSoap".equals(portName)) {
            setwsConsultaCURPSoapEndpointAddress(address);
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
