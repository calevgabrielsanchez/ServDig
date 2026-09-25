package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

public class CampoTextoSIMEInt extends CampoTextoSIME<Integer> {

    private String formatString;

    public CampoTextoSIMEInt(Integer value, int longitud) {
        this();
        setValorCampo(value);
        setLongitud(longitud);
    }

    public CampoTextoSIMEInt() {
        formatString = "%d";
    }

    public void setLongitud(int longitud) {
        super.setLongitud(longitud);
        formatString = new StringBuilder()
            .append("%")
            .append(getLongitud())
            .append("d").toString();
    }

    public String generarValorFormateado() {
        Integer retvalue = 0;
        if (getValorCampo() != null)  {
            retvalue = recortarLongiudExcesiva();
        }
        String result = String.format(formatString, retvalue);
        result = reemplazarEspaciosAlPrincipio(result, "0");
        return result;
    }

    private int recortarLongiudExcesiva() {
        int retvalue = getValorCampo();
        String strretvalue = String.valueOf(retvalue);
        while (strretvalue.length() > getLongitud() 
                && getLongitud() > 0
                && retvalue > 0) {
            retvalue /= 10;
            strretvalue = String.valueOf(retvalue);
        }
        return retvalue;
    }
}
