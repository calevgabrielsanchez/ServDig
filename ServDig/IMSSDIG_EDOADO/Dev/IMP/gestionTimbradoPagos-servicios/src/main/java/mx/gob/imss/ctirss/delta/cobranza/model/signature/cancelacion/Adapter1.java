/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import  mx.gob.imss.ctirss.delta.cobranza.service.utility.Utilerias;

/**
 *
 * @author NOVUTECK1
 */
public class Adapter1 extends XmlAdapter<String, Date>
{
    public Date unmarshal(String value) {
        return (Utilerias.parseDateTime(value));
    }

    public String marshal(Date value) {
        return (Utilerias.printDateTime(value));
    }
}