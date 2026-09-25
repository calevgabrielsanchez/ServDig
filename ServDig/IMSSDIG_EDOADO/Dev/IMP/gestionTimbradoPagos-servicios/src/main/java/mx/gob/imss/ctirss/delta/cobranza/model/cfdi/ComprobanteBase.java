
package mx.gob.imss.ctirss.delta.cobranza.model.cfdi;

import java.util.List;

import org.w3c.dom.Element;

public interface ComprobanteBase {
  
  public boolean hasComplemento();
  
  public List<Object> getComplementoGetAny();
  
  public String getSello();
  
  public void setComplemento(Element e);
  
  public Object getComprobante();
}