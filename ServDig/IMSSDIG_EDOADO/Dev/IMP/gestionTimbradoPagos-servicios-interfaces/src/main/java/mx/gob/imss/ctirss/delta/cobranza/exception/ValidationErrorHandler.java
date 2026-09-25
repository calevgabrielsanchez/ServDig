package mx.gob.imss.ctirss.delta.cobranza.exception;

import java.util.List;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList;

public final class ValidationErrorHandler extends DefaultHandler {

  private List<SAXParseException> errors = Lists.newArrayList();

  public void error(SAXParseException e)  {
    errors.add(e);
  }
  
  public List<SAXParseException> getErrors() {
    return ImmutableList.copyOf(errors);
  }
}