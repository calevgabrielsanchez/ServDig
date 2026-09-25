package mx.gob.imss.ctirss.delta.model.gestion.integracion.common;

public class OperacionesRegistros {

    /* 
     * Cambiar &ntilde; de por '#' en la cadena pasada como parametro
     * y devolver el resultado
     * @param cadena
     */
    public static final String limpiarCadena(String cadena) {
        return cadena.replaceAll("[\u00D1\u00F1]", "#");
    }

    /*
     * Genera la cadena de nombre para que corresponda al formato de texto
     * plano manejado por el IMSS letra &ntilde; cambia por # y como
     * separador de nombre y apellidos usa $
     * @param nombre ver m&eacute;todo
     * @param primerApellido ver m&eacute;todo
     * @param segundoApellido ver m&eacute;todo
     *
     */
    public static final String generarNombre(String nombre,
            String primerApellido, 
            String segundoApellido) {

        return limpiarCadena(String.format("%s$%s$%s"
                , primerApellido
                , segundoApellido
                , nombre));
    }

	/**
     * Usar cadena vacia cuando 'val' es nulo
     * @param val cadena para revisar si es nula
     */
    public static final String safeNull(String val) {
        String value = val;
        if (val == null) {
            value = "";
        }
        return value;
    }

}
