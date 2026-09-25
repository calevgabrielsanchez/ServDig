package mx.gob.imss.ctirss.delta.cobranza.model;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Utilerias;

public class Adapter1 extends XmlAdapter<String, Date>
{
    public Date unmarshal(String value) {
        return (Utilerias.parseDateTime(value));
    }

    public String marshal(Date value) {
        return (Utilerias.printDateTime(value));
    }
}
