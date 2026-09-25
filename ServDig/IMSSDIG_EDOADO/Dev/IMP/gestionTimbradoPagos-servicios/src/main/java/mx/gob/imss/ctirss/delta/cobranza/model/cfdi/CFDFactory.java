package mx.gob.imss.ctirss.delta.cobranza.model.cfdi;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public abstract class CFDFactory {
  
  protected static String getVersion(byte[] data) throws Exception {
	  
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();    
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(data));
    XPathFactory xfactory = XPathFactory.newInstance();
    XPath xpath = xfactory.newXPath();
    return (String) xpath.evaluate("/Comprobante/@version", doc);
  }
}