package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

public class CampoTextoSIMELong extends CampoTextoSIME<Long> {


    private String formatString = "%d";

    public CampoTextoSIMELong(Long value, int longitud) {
        setValorCampo(value);
        setLongitud(longitud);
    }

    public CampoTextoSIMELong() {
    }

    @Override
    public void setLongitud(int longitud) {
        super.setLongitud(longitud);
        formatString = new StringBuilder("%")
                .append(getLongitud())
                .append("d")
                .toString();
    }

    public String generarValorFormateado() {
        Long retvalue = 0L;
        if (getValorCampo() != null) {
            retvalue = recortarLongiudExcesiva();
        }

        String result =  String.format(formatString, retvalue);
        result = reemplazarEspaciosAlPrincipio(result, "0");
        return result;
    }

    private long recortarLongiudExcesiva() {
        long retvalue = getValorCampo();
        String strretvalue = String.valueOf(retvalue);
        while(strretvalue.length() > getLongitud()
                && getLongitud() > 0L
                && retvalue > 0L) {
            retvalue /= 10L;
            strretvalue = String.valueOf(retvalue);
        }
        return retvalue;
    }
}
