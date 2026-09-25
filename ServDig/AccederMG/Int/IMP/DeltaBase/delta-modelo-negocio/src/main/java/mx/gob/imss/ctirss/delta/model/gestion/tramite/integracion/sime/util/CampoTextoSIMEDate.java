package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import java.util.Date;
import static org.apache.commons.lang.StringUtils.repeat;


public class CampoTextoSIMEDate extends CampoTextoSIME<Date> {

    private static final int LONGITUD  = 8;

    public CampoTextoSIMEDate(Date date) {
        setValorCampo(date);
    }

    public CampoTextoSIMEDate() {
    }

    public int getLongitud() {
        return LONGITUD;
    }

    public void setLongitud(int longitud) {
        throw new RuntimeException("Longitud para fechas es fija a 8 posiciones");
    }

    public String generarValorFormateado() {
        String retvalue = repeat("0", getLongitud());

        if (getValorCampo() != null) {
            retvalue = String.format("%1$td%1$tm%1$tY", getValorCampo());
        }

        return retvalue;
    }
}
