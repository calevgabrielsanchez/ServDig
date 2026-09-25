package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.awt.image.BufferedImage;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.GenerarCodigoQRServiceUtilityLocal;


@Stateless(name = "generarCodigoQRServiceBusiness", mappedName = "generarCodigoQRServiceBusiness")
public class GenerarCodigoQRServiceBusiness extends AbstractServiceBusiness implements GenerarCodigoQRServiceBusinessRemote {

	@EJB
	private GenerarCodigoQRServiceUtilityLocal generarCodigoQRServiceUtility;

	@Override
	public BufferedImage generadorCodigoQrUrl(String url, int tamanioWidth, int tamanioHeight) {
		return generarCodigoQRServiceUtility.generadorCodigoQrUrl(url, tamanioWidth, tamanioHeight);
	}
	
	@Override
	public BufferedImage generadorCodigoQrCadena(String cadena, int tamanioWidth, int tamanioHeight) {
		return generarCodigoQRServiceUtility.generadorCodigoQrCadena(cadena, tamanioWidth, tamanioHeight);
	}


}
