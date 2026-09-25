/**
 * .
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.callouts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.apache.commons.io.IOUtils;

/**
 * Clase que genera un archivo sua, esto es agrega el checksum al archivo y lo
 * guarda fisicamene
 * @author NOVUTECK1
 */
public abstract class SuaLayoutCallOut {
    /**
     * Constructor privado para clases utilitarias
     */
    private SuaLayoutCallOut() {
        
    }

    /**
     *  GEnera el layout para un archivo SUA
     * @param fileName El nombre del archivo SUA (Este nombre cumple con una estructura definida, 
     * si se invoca desde osb podri quedar de la siguiente manera :
     * fn:concat("/home/", fn:substring($body/SUAMessage/RegistroValidacion/versionSUA,1,1),
     * fn:substring($body/SUAMessage/SUAPatron/periodoDePago ,3), 
     * fn:substring($body/SUAMessage/SUAPatron/registroPatronalIMSS,3,1),
     * fn:substring($body/SUAMessage/SUAPatron/registroPatronalIMSS,7,1), 
     * fn:substring($body/SUAMessage/SUAPatron/registroPatronalIMSS,9,1), ".SUA"))
     * @param content el conenido del archivo
     * @return la cadena contenida del archivo, ya son el checksum del archivo
     * @throws IOException excepcion al generar o manipular los archivos
     */
    public static String generateLayout(String fileName, byte[] content)
            throws IOException {
        FileOutputStream outputStream = new FileOutputStream(
                new File(fileName), true);
        FileChannel channel = outputStream.getChannel();
        FileLock lock = obtainLock(channel);
        try {
            String str = new String(content);
            str = str.replaceFirst("(?s)\n$", "");
            String checksum = SuaCheckSumUtil.generaCheckSum(str);
            String strRegister = SuaCheckSumUtil.agregaCheckSumCadena(str,
                    checksum);
            channel.write(ByteBuffer.wrap(strRegister.getBytes()));
            return strRegister;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.release();
            channel.close();
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * MArca el archivo para que nadie mas pueda escribir sobre el 
     * @param channel el archivo a ser bloqueado
     * @return El archivo bloqueado
     * @throws IOException Error al manejar los archivos
     */
    private static FileLock obtainLock(FileChannel channel)
            throws IOException {
        FileLock lock = null;
        do {
            try {
                lock = channel.tryLock();
            } catch (OverlappingFileLockException lockException) {
                // si se genera un erro al proteger el archivo lo volvemoa a
                // intentar
            }
        } while (lock == null);
        return lock;
    }
}
