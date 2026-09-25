package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

public class CampoTextoSIMEString extends CampoTextoSIME<String> {

    private String formatString;

    public CampoTextoSIMEString(String value, int longitud) {
        this();
        setLongitud(longitud);
        setValorCampo(value);
    }

    public CampoTextoSIMEString() {
        formatString = "%10s";
    }

    @Override
    public void setLongitud(int longitud) {
        super.setLongitud(longitud);
        formatString = new StringBuilder("%-")
            .append(longitud)
            .append("s")
            .toString();
    }

    @Override
    public String generarValorFormateado() {
        String retval = "";

        if (getValorCampo() != null) {
            retval = getValorCampo();
            if (retval.length() > getLongitud()) {
                retval = retval.substring(0, getLongitud());
            }
        }
        return String.format(formatString, retval);
    }

}
