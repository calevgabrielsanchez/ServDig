package mx.gob.imss.ctirss.delta.gestion.clasificacion.callouts;

import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.IOUtils;

public class LayoutCallOut {

    private static final int MAX_RETRY = 1000;

    public static String generateLayout(String fileName, byte[] content) throws IOException {
        
        FileOutputStream outputStream = new FileOutputStream(new File(fileName), true);
        FileChannel channel = outputStream.getChannel();
        FileLock lock = obtainLock(channel);
        try {
            String str = new String(content);
            str = str.replaceFirst("(?s)\n$", "");
            String strRegister = String.format("%s\r\n", str);
            channel.write(ByteBuffer.wrap(strRegister.getBytes()));
            return str;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            lock.release();
            channel.close();
            IOUtils.closeQuietly(outputStream);
        }
    }
    
    public static String generateLayoutByPipes(String fileName, byte[] content) throws IOException {
        
        FileOutputStream outputStream = new FileOutputStream(new File(fileName), true);
        FileChannel channel = outputStream.getChannel();
        FileLock lock = obtainLock(channel);
        try {
            String str = new String(content);
            str = str.replaceFirst("(?s)\n$", "");
            String strRegister = str.replaceAll("\\|", "\r\n");
            channel.write(ByteBuffer.wrap(strRegister.getBytes()));
            return str;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            lock.release();
            channel.close();
            IOUtils.closeQuietly(outputStream);
        }
    }

    private static final FileLock obtainLock(FileChannel channel) throws OverlappingFileLockException, IOException {
        int retries = 0;
        FileLock lock = null;
        do {
            try {
                retries++;
                lock = channel.tryLock();
            }
            catch(OverlappingFileLockException lockException) {
                //if (retries > MAX_RETRY) {
                    //throw lockException;
                //}
            }
        }
        while (lock == null);
        return lock;
    }

}
