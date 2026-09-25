package mx.gob.imss.ctirss.delta.framework.base.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import sun.misc.BASE64Decoder;


public class MailUtility implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 926272596184784198L;
	
	public static byte[] decode(String base64binary) 
            throws IOException 
    { 
		System.err.println("Decoding pdf::::: ");
        BASE64Decoder decoder = new BASE64Decoder(); 
        
        
        byte[] fileBytes = decoder.decodeBuffer(base64binary); 
        OutputStream bos = new FileOutputStream("C:\\amsrtNuevoByte.pdf");
        System.err.println("Writing pdf::::: ");
        bos.write(fileBytes);
        bos.flush();
        bos.close(); 
        System.err.println("Finishing writing pdf:::: ");
        return fileBytes;
    }
	
}
