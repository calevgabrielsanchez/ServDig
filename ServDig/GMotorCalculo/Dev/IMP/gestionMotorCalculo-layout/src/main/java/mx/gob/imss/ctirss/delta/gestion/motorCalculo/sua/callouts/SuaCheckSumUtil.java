/**
 * mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.callouts
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.callouts;

/**
 * Clase utilitaria para genera el checksum de un archivo SUA.
 * @author NOVUTECK1
 */
public abstract class SuaCheckSumUtil {
    /**
     * Para una clase utilitaria no debe existir constructor publico
     */
    private SuaCheckSumUtil() {
        
    }

    /**
     * Número de caracteres que sobran en unarchivo sua para generar el checkSUm
     */
    private static final int CARACTERES_SOBRANTES = 175;
    /**
     * Longitud del check sum
     */
    private static final int TAMANO_CHECKSUM = 19;
    /**
     * Caracyer para el salto de linea
     */
    private static final String SALTO_LINEA = "\n";
    /**
     * Caracer de retorno de carro
     */
    private static final String RETORNO_CARRO = "\r";
    /**
     * Caracter vacio
     */
    private static final String VACIO = "";
    /**
     * Base 10 para las operaciones
     */
    private static final int BASE_10 = 10;
    /**
     * base nueve ara las operaciones
     */
    private static final int BASE_9 = 9;
    
    /**
     * Genera el checksum para un archivo SUA
     * @param archivo Cadena con el contenido del archivo, el cual se 
     * ocupa para generar el checksum
     * @return el checksum o codigo de verificacion del archivo
     */
    public static final String generaCheckSum(String archivo) {
        // QUitamos los caracteres de salto de lini si tuviera
        archivo = archivo.replace(SALTO_LINEA, VACIO);
        archivo = archivo.replace(RETORNO_CARRO, VACIO);

        String cadena = archivo.substring(0, archivo.length()
                - CARACTERES_SOBRANTES);
        int i = 0; // max 18
        int j = 0; // ++ if i cambia de 18
        int c = 0; // Ascii de caracteres
        int y = 0; // indice para la cadena entrante
        int r = 0; // valor del digito cuando j se incrementa
        int indice = 0; // indice para la posicion del valor en el checksum
        int[] checksum = new int[TAMANO_CHECKSUM]; // cadena inicial de cheksum
        while (y < cadena.length()) {
            // si no es digito o caracter en mayusculas se busca el lsiguiente
            // elemento
            while (!Character.isLetterOrDigit(cadena.charAt(y))
                    || Character.isLowerCase(cadena.charAt(y))) {
                y++;
            }
            // Obtenemos el ASCCI del caracter encontrado
            c = (int) cadena.charAt(y);

            indice = (i + c) % TAMANO_CHECKSUM;
            checksum[indice] = ((checksum[indice] + c) % BASE_10);
            // Asumimos que la version del SUA es diferente a W300, hay que
            // agregar aqui la bifurcacion
            // verificar por que el caso de uso dice
            // que diferente de W300 es 10 pero no funciona con 10 como default
            // si no con 9
            r = (r + c) % BASE_9;
            i = (i + 1) % TAMANO_CHECKSUM;
            if (i == 0) {
                checksum[j] = (checksum[j] + r) % BASE_10;
                j = (j + 1) % TAMANO_CHECKSUM;
            }
            y++;
        }
        StringBuffer checkSumString = new StringBuffer();
        for (int valor : checksum) {
            checkSumString.append(valor);
        }
        return checkSumString.toString();
    }
    
    /**
     * Agrega la cadena checksum en la correspondiente posicion de la cadena
     * original
     * @param cadena cadena original del archivo 
     * @param checkSum ca¿odigo de verficacion generado para esa cadena
     * @return la cadena del archivo con el checksum generado
     */
    public static final String agregaCheckSumCadena(String cadena,
            String checkSum) {
        StringBuffer nuevaCadena = new StringBuffer();
        nuevaCadena.append(cadena.substring(0, cadena.length()
                - CARACTERES_SOBRANTES));
        nuevaCadena.append(checkSum);
        nuevaCadena.append(cadena.substring(cadena.length()
                - CARACTERES_SOBRANTES + TAMANO_CHECKSUM));
        return nuevaCadena.toString();
    }
}
