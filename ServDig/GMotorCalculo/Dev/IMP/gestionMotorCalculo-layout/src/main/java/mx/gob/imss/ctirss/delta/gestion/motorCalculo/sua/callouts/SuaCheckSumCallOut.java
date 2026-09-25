/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.callouts;


/**
 * Call out para generar y agregar el checksum al archivo SUA
 * @author NOVUTECK1
 *
 */
public abstract class SuaCheckSumCallOut {

    /**
     * DAdo el archivo SUA le genera su CheckSum y lo agrega al achivo, 
     * regresando este con el checksum aladido
     * @param content El archivo sua al cual se le genera el checkSUm
     * @return El archivo sua con su checksum
     */
    public static final String generaYAgregaCheckSUm(byte[] content) {
        String archivo = new String(content);
        String checksum = SuaCheckSumUtil.generaCheckSum(archivo);
        return SuaCheckSumUtil.agregaCheckSumCadena(archivo, checksum);
    }
}
