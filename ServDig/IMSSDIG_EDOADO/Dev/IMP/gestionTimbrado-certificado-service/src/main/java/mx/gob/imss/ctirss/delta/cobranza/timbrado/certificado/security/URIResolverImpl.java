package mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.security;

import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public final class URIResolverImpl implements URIResolver {
  
  public Source resolve(String href, String base) {	  
    return new StreamSource(getClass().getResourceAsStream(href));
  }
}