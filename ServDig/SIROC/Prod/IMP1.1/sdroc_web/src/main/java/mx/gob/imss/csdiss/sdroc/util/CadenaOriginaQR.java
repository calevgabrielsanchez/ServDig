package mx.gob.imss.csdiss.sdroc.util;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CadenaOriginaQR {
	
	/**
	 * logger EscritorioController
	 */
	private static final Logger log = Logger.getLogger(CadenaOriginaQR.class);

	public String formatCadenaOriginalQR(String cadenaOriginal, String numeroRegObra, String descTipoObra, String fechaFormato) {
        
       /*String str = cadenaOriginal;
		Pattern p = Pattern.compile("%NUM_REG_OBRA%", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		String result = m.replaceAll("|Número de registro de obra: ".concat(numeroRegObra));

		Pattern pe = Pattern.compile("%NOMBRE_TRAMITE%", Pattern.CASE_INSENSITIVE);
		Matcher me = pe.matcher(result);
		String cadenaOriginalQR = me.replaceAll("|Trámite: ".concat(descTipoObra));
		
		Pattern p1 = Pattern.compile("%FECHA_ACTUAL%", Pattern.CASE_INSENSITIVE);
		Matcher m1 = p1.matcher(cadenaOriginalQR);
		cadenaOriginalQR = m1.replaceAll("Fecha: ".concat(fechaFormato));
		System.out.println("cadenaOriginalQR FINAL = " + cadenaOriginalQR);
		return cadenaOriginalQR;*/
		
		 if (StringUtils.isEmpty(numeroRegObra)) {
		        numeroRegObra = "C0000000";
		    }

		    System.out.println("=== formatCadenaOriginalQR ===");
		    System.out.println("cadenaOriginal = " + cadenaOriginal);
		    System.out.println("numeroRegObra = " + numeroRegObra);
		    System.out.println("descTipoObra = " + descTipoObra);
		    System.out.println("fechaFormato = " + fechaFormato);

		    String cadenaOriginalQR = cadenaOriginal;

		    // Reemplazar trámite
		    cadenaOriginalQR = cadenaOriginalQR.replace(
		            "%NOMBRE_TRAMITE%",
		            "|Trámite: " + descTipoObra
		    );

		    // Separar la cadena
		    String[] partes = cadenaOriginalQR.split("\\|", -1);

		    /*
		     * Estructura:
		     *
		     * [0] ""
		     * [1] ""
		     * [2] Invocante
		     * [3] Trámite
		     * [4] Fecha anterior + número anterior
		     * [5] RFC
		     * [6] Nombre
		     * [7] Registro patronal
		     * ...
		     */

		    if (partes.length > 5) {

		        // Sustituir fecha anterior
		        partes[4] = "Fecha: " + fechaFormato;

		        StringBuilder sb = new StringBuilder();

		        for (int i = 0; i < partes.length; i++) {

		            if (i > 0) {
		                sb.append("|");
		            }

		            sb.append(partes[i]);

		            // Después de la fecha agregamos el número de registro
		            if (i == 4) {
		                sb.append("|Número de registro de obra: ");
		                sb.append(numeroRegObra);
		            }
		        }

		        cadenaOriginalQR = sb.toString();
		    }

		    System.out.println("cadenaOriginalQR FINAL = " + cadenaOriginalQR);

		    return cadenaOriginalQR;
		
	}

	/**
	 * Metodo encargado de construir la imagen QR
	 * 
	 * @param textoImagen
	 * @param largo
	 * @param ancho
	 * @return BufferedImage
	 */
	public BufferedImage construirQR(String textoImagen, int largo, int alto) {
		BufferedImage image = null;
		String iso88591charset = "ISO-8859-1";
    	try {    		
	    	Charset charset = Charset.forName(iso88591charset);
	        CharsetEncoder encoder = charset.newEncoder();
	        byte[] bytes = null;
	        ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(textoImagen));
	        bytes = bbuf.array();
	        String data = new String(bytes,iso88591charset);	        
	        QRCodeWriter writer = new QRCodeWriter();
	        //Genera una matriz de bytes de la cadena.
	        BitMatrix matrix = writer.encode(data, 
	        	BarcodeFormat.QR_CODE, largo, alto);
	        //Genera la imagen de datos.
	        image = new BufferedImage(largo, alto, BufferedImage.TYPE_INT_RGB);
	        //Itera la matriz y dibujar los pixeles de la imagen.
	        for (int y = 0; y < alto; y++) {
	            for (int x = 0; x < largo; x++) {
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
