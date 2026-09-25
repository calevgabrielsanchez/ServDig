/**
 * Formato5.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.bean;

public class Formato5  implements java.io.Serializable {
	
	private static final long serialVersionUID = -8112429064840735378L;

	private java.lang.String[] excepciones;

    private java.lang.String lineaCaptura;

    private byte[] pdf;

    public Formato5() {
    }

    public Formato5(
           java.lang.String[] excepciones,
           java.lang.String lineaCaptura,
           byte[] pdf) {
           this.excepciones = excepciones;
           this.lineaCaptura = lineaCaptura;
           this.pdf = pdf;
    }


    /**
     * Gets the excepciones value for this Formato5.
     * 
     * @return excepciones
     */
    public java.lang.String[] getExcepciones() {
        return excepciones;
    }


    /**
     * Sets the excepciones value for this Formato5.
     * 
     * @param excepciones
     */
    public void setExcepciones(java.lang.String[] excepciones) {
        this.excepciones = excepciones;
    }


    /**
     * Gets the lineaCaptura value for this Formato5.
     * 
     * @return lineaCaptura
     */
    public java.lang.String getLineaCaptura() {
        return lineaCaptura;
    }


    /**
     * Sets the lineaCaptura value for this Formato5.
     * 
     * @param lineaCaptura
     */
    public void setLineaCaptura(java.lang.String lineaCaptura) {
        this.lineaCaptura = lineaCaptura;
    }


    /**
     * Gets the pdf value for this Formato5.
     * 
     * @return pdf
     */
    public byte[] getPdf() {
        return pdf;
    }


    /**
     * Sets the pdf value for this Formato5.
     * 
     * @param pdf
     */
    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Formato5)) return false;
        Formato5 other = (Formato5) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.excepciones==null && other.getExcepciones()==null) || 
             (this.excepciones!=null &&
              java.util.Arrays.equals(this.excepciones, other.getExcepciones()))) &&
            ((this.lineaCaptura==null && other.getLineaCaptura()==null) || 
             (this.lineaCaptura!=null &&
              this.lineaCaptura.equals(other.getLineaCaptura()))) &&
            ((this.pdf==null && other.getPdf()==null) || 
             (this.pdf!=null &&
              java.util.Arrays.equals(this.pdf, other.getPdf())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getExcepciones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExcepciones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExcepciones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLineaCaptura() != null) {
            _hashCode += getLineaCaptura().hashCode();
        }
        if (getPdf() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPdf());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPdf(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Formato5.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.sipare.imss.gob.mx", "Formato5"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excepciones");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.sipare.imss.gob.mx", "excepciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generacion.sipare.imss.gob.mx", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineaCaptura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.sipare.imss.gob.mx", "lineaCaptura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.sipare.imss.gob.mx", "pdf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
