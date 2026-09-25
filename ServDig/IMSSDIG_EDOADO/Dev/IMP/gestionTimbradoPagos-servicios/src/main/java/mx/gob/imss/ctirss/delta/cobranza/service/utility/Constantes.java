package mx.gob.imss.ctirss.delta.cobranza.service.utility;

public class Constantes {
	
	// Version 3.2
//    public final static String PAIS = "MEXICO";
//    public final static String VERSION_COMPROBANTE = "3.2";
//    public final static String FORMA_PAGO = "PAGO EN UNA SOLA EXHIBICION";
//    public final static String TIPO_COMPROBANTE_INGRESO = "ingreso";
//    public final static String METODO_PAGO = "99 Otros";
//    public final static String LUGAR_EXPEDICION = "MEXICO";
//    public final static String NOMBRE_IMSS = "INSTITUTO MEXICANO DEL SEGURO SOCIAL";
    
    // Version 3.3
    public final static String PAIS = "MEXICO";
    public final static String VERSION_COMPROBANTE = "3.3";
    public final static String FORMA_PAGO_EFECTIVO = "01";
    public final static String FORMA_PAGO_TRANSFERENCIA_ELECTRONICA = "03";
    public final static String TIPO_COMPROBANTE_INGRESO = "I";
    public final static String METODO_PAGO = "PUE";
    public final static String LUGAR_EXPEDICION = "06600";
    public final static String MONEDA = "MXN";
    public final static String SERIE = "A";
    
    public final static String NOMBRE_IMSS = "INSTITUTO MEXICANO DEL SEGURO SOCIAL";
    public final static String REGIMEN_FISCAL="603";
    
    public final static String USO_CFDI = "P01";
    
//    *********** Produccion ***********
//    public final static String RFC_IMSS = "IMS421231I45";
    
//    *********** Desarrollo y QA ***********
    public final static String RFC_IMSS = "WAL99092955A";
   	
//    public final static String CALLE_IMSS = "REFORMA";
//    public final static String NO_EXTERIOR_IMSS = "476";
//    public final static String COLONIA_IMSS = "JUAREZ";
//    public final static String MUNICIPIO_IMSS = "CUAUHTEMOC";
//    public final static String ESTADO_IMSS = "DISTRITO FEDERAL";
//    public final static String CP_IMSS = "06600";
//    public final static String REGIMEN_SIMPLIFICADO_IMSS = "PERSONA MORAL CON FINES NO LUCRATIVOS";
    
    // Version 3.2
    //public final static String CONCEPTO_UNIDAD = "No Aplica";
    //public final static String CONCEPTO_CANTIDAD = "1.0";
    
    //Version 3.3
    public final static String CLAVE_UNIDAD = "E48";
    public final static String CLAVE_PROD_SERV = "85101701";
    public final static String CONCEPTO_CANTIDAD = "1";
    
    public final static String IMPUESTO_IVA = "IVA";
    public final static String IMPUESTO_ISR = "ISR";
    
    public final static String VERSION_NOMINA = "1.1";
    public final static String PERIODICIDAD_PAGO_MENSUAL = "MENSUAL";
    public final static String CVE_IMPUESTO_039 = "039";
    public final static String CONCEPTO_IMPUESTO_039 = "JUBILACIONES, PENSIONES O HABERES DE RETIRO";
    
    public final static String XML_EXTENSION = ".xml";
    public final static String ZIP_EXTENSION = ".zip";   
    
    /**Descripcion de los concptos que se van a crear en los XML**/
    public final static String CONCEPTO_DESCRIPCION = "PAGO DE NOMINA";    
    public final static String CONCEPTO_DESCRIPCION_SubTotIMSS = "Cuotas IMSS ";
    public final static String CONCEPTO_DESCRIPCION_SubTotRCV = "Cuotas RCV ";
    public final static String CONCEPTO_DESCRIPCION_ActIMSS = "Actualizacion de Cuotas IMSS ";
    public final static String CONCEPTO_DESCRIPCION_ActRCV = "Actualizacion de cuotas RCV ";
    public final static String CONCEPTO_DESCRIPCION_RecIMSS = "Recargos de Cuotas IMSS ";
    public final static String CONCEPTO_DESCRIPCION_RecRCV = "Recargos de cuotas RCV ";
    
    public final static int TAMANIO_ARCHIVO_XML = 1;
    public final static String PREFIJO_NOMBRE_ARCHIVO_XML = "CFDI";
    
    /**Elementos de cancelacion **/
    public final static String ELEMENTO_CANCELACION = "Cancelacion";
    public final static String ELEMENTO_CANCELACION_FECHA = "Fecha";
    public final static String ELEMENTO_CANCELACION_RFC_EMISOR = "RfcEmisor";
        
    public final static String ELEMENTO_CANCELACION_FOLIOS = "Folios";
    public final static String ELEMENTO_CANCELACION_UUID = "UUID";
       
    public final static String ELEMENTO_CANCELACION_XMLNS_XSI = "xmlns:xsi";
    public final static String ELEMENTO_CANCELACION_XMLNS_XSI_URL = "http://www.w3.org/2001/XMLSchema-instance";
    public final static String ELEMENTO_CANCELACION_XMLNS_XSD = "xmlns:xsd";
    public final static String ELEMENTO_CANCELACION_XMLNS_XSD_URI = "http://www.w3.org/2001/XMLSchema";
    public final static String ELEMENTO_CANCELACION_XMLNS = "xmlns";
    public final static String ELEMENTO_CANCELACION_XMLNS_URI = "http://cancelacfd.sat.gob.mx";
    
    public final static String ELEMENTO_TRANSFORMACION ="http://www.w3.org/2000/09/xmldsig#enveloped-signature";
    public final static String ELEMENTO_DIGEST_VALUE ="http://www.w3.org/2000/09/xmldsig#sha1";
    public final static String ELEMENTO_SIGNATURE_METHOD ="http://www.w3.org/2000/09/xmldsig#rsa-sha1";
    
    public final static String ELEMENTO_CANONIZACION = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
    public final static String ELEMENTO_SIGNATURE_INFO_XMLNS ="http://www.w3.org/2000/09/xmldsig#";
    public final static String ELEMENTO_SIGNATURE_INFO_XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public final static String ELEMENTO_SIGNATURE_INFO_XSD ="http://www.w3.org/2001/XMLSchema";
    
    public final static String ELEMENTO_SIGNATURE_TYPE ="http://www.w3.org/2000/09/xmldsig#";
    
    public final static String CODIGO_RESPUESTA_CANCELACION_EXITOSO ="201";
    public final static String CODIGO_RESPUESTA_CANCELACION_EN_ODI = "900";
    
}