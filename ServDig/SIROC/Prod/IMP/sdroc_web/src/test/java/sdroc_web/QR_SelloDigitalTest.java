/**
 * 
 */
package sdroc_web;

import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;

/**
 * @author joel.fuentes
 *
 */
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class QR_SelloDigitalTest {

	@Autowired
	static
	GenerarCodigoQRServiceBusinessRemote generarCodigoQRServiceBusiness;
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "||Invocante:portalimssdigital%NOMBRE_TRAMITE%|Fecha: Wed May 17 2017 13:35:05 GMT-0500 (Central Standard Time (Mexico))%NUM_REG_OBRA%|RFC: OISD661006P5A|Nombre o razón social: DANIEL ORTIZ SOLORZANO|Registro patronal: Y6467489107||";
		Pattern p = Pattern.compile("%NUM_REG_OBRA%", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		String result = m.replaceAll("|Número de registro de obra: ".concat("C0000345"));
		
		Pattern pe = Pattern.compile("%NOMBRE_TRAMITE%", Pattern.CASE_INSENSITIVE);
		Matcher me = pe.matcher(result);
		String cadenaOriginalQR = me.replaceAll("|Trámite: ".concat("Registro de obra de construcción"));
		
		BufferedImage image_qr = generarCodigoQRServiceBusiness.generadorCodigoQrCadena(
				cadenaOriginalQR, 400, 400);
		
	}
}
