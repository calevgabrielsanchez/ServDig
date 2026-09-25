package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

public abstract class CampoTextoSIME<K extends Object> {

    private K valorCampo;
    private int longitud;

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }



    public K getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(K valorCampo) {
        this.valorCampo = valorCampo;
    }

    public abstract String generarValorFormateado();

    protected String reemplazarEspaciosAlPrincipio(String cadenaSubject, String replacement) {
        if (replacement.matches("\\s")
                || replacement.length() > 1) {
            return cadenaSubject;
        }
        String result = cadenaSubject;
        String pattern = new StringBuilder("^(")
                .append(replacement)
                .append("*)\\s")
                .toString();
        String replaceString = new StringBuilder("$1")
            .append(replacement)
            .toString();

        String matchPattern = new StringBuilder(pattern).append( ".*").toString();

        while(result.matches(matchPattern)) {
            result = result.replaceFirst(pattern, replaceString);
        }
        return result;
    }


}
