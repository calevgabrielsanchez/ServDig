package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.awt.image.BufferedImage;

import javax.ejb.Remote;

@Remote
public interface GenerarCodigoQRServiceBusinessRemote {

	/**
	 * Este m&eacute;todo genera una imagen de datos (c&oacute;digo de barras bidimensional - 
	 * QR Code) con base a una URL proporcionada y determinando el ancho y alto de la imagen.
	 * 
	 * @param url 		
	 * @param tamanioWidth
	 * @param tamanioHeight
	 * @return BufferedImage
	 */
	BufferedImage generadorCodigoQrUrl(String url, int tamanioWidth, int tamanioHeight);
	
	/**
	 * Este m&eacute;todo genera una imagen de datos (c&oacute;digo de barras bidimensional - 
	 * QR Code) con base a una CADENA proporcionada y determinando el ancho y alto de la imagen.
	 * 
	 * @param cadena
	 * @param tamanioWidth
	 * @param tamanioHeight
	 * @return BufferedImage
	 */
	BufferedImage generadorCodigoQrCadena(String cadena, int tamanioWidth, int tamanioHeight);
	
}
