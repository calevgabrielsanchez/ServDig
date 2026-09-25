package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Stateless(mappedName = "generarCodigoQRServiceUtility", name = "generarCodigoQRServiceUtility")
public class GenerarCodigoQRServiceUtility extends AbstractServiceUtility implements GenerarCodigoQRServiceUtilityLocal {

	@Override
	public BufferedImage generadorCodigoQrUrl(String url, int tamanioWidth, int tamanioHeight) {
		BufferedImage image=null;
        BitMatrix matrix;
        Writer writer = new QRCodeWriter();
        try { 
        	//Genera una matriz de bytes de la url.
            matrix = writer.encode(url, BarcodeFormat.QR_CODE, tamanioWidth, tamanioHeight);            
            //Genera la imagen de datos.
            image = new BufferedImage(tamanioWidth, tamanioHeight, BufferedImage.TYPE_INT_RGB);            
            //Itera la matriz y dibujar los pixeles de la imagen.
            for (int y = 0; y < tamanioHeight; y++) {
                for (int x = 0; x < tamanioWidth; x++) {
                    int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                    image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
                }
            }            	          		
        } catch (WriterException e) {
        	log.error(e);
        }
        return image;
	}

	@Override
	public BufferedImage generadorCodigoQrCadena(String cadena, int tamanioWidth, int tamanioHeight) {
		BufferedImage image = null;
		String iso88591charset = "ISO-8859-1";
    	try {    		
	    	Charset charset = Charset.forName(iso88591charset);
	        CharsetEncoder encoder = charset.newEncoder();
	        byte[] bytes = null;
	        ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(cadena));
	        bytes = bbuf.array();
	        String data = new String(bytes,iso88591charset);	        
	        QRCodeWriter writer = new QRCodeWriter();
	        //Genera una matriz de bytes de la cadena.
	        BitMatrix matrix = writer.encode(data, 
	        	BarcodeFormat.QR_CODE, tamanioWidth, tamanioHeight);
	        //Genera la imagen de datos.
	        image = new BufferedImage(tamanioWidth, tamanioHeight, BufferedImage.TYPE_INT_RGB);
	        //Itera la matriz y dibujar los pixeles de la imagen.
	        for (int y = 0; y < tamanioHeight; y++) {
	            for (int x = 0; x < tamanioWidth; x++) {
	                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
	                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
	            }
	        }			
    	} catch (WriterException e) {
    		log.error(e);
        } catch (CharacterCodingException e) {
        	log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
        return image;
	}

}
