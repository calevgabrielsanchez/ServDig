/**
 * 
 */
package mx.gob.imss.ctirss.correccion.web.controller.firma;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.web.controller.login.AbsractSeguridadController;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.SerialNumber;

/**
 * @author Alberto Cortes
 * @author Vladimir Aguirre Piedragil
 * 
 * 
 */
@Controller
@RequestMapping(value="/firma")
public class FirmaElectronicaController extends AbsractSeguridadController {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(FirmaElectronicaController.class);

	/**
	 * Valida que firma digital (<code>pkcs7</code>) haya sido creada con el
	 * certificado que el usuario ven’a arrastrando en la sesi—n.
	 * 
	 * @param pkcs7
	 *            Firma
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "validaSerial", method=RequestMethod.GET)
	public  @ResponseBody boolean validaSerial(@RequestParam String pkcs7, HttpServletRequest request) {
		boolean regreso = false;
		try {
			X509Certificate certificate = getCertificateByPKCS7(pkcs7);
			String serialPkcs7 = getSerialCert(certificate);
			final String serial = super.getUsuarioFirmado(request)
					.getNumeroSerialCertificadoPatron();
			logger.debug("Serial original :: " + serial);
			logger.debug("Serial pkcs7 ::::: " + serialPkcs7);
			if (serial != null && !"".equals(serial)
					&& serial.equals(serialPkcs7)) {
				regreso = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return regreso;
	}

	/**
	 * 
	 * @param certificate
	 * @return
	 * @throws Exception
	 */
	private String getSerialCert(X509Certificate certificate) throws Exception {
		String serial = null;
		CertificateSerialNumber certificateSerialNumber = new CertificateSerialNumber(
				certificate.getSerialNumber());
		SerialNumber serialNumber = (SerialNumber) certificateSerialNumber
				.get(CertificateSerialNumber.NUMBER);
		serial = serialNumber.toString().replaceAll("[^0123456789]", "");
		return arregalSerial(serial);
	}

	/**
	 * 
	 * @param serial
	 * @return
	 * @throws Exception
	 */
	private String arregalSerial(String serial) throws Exception {
		String serialCorregido = serial.trim();
		if (serial.length() > 22) {
			serialCorregido = "";
			for (int i = 0; i < serial.length(); ++i) {
				if (i % 2 == 1) {
					serialCorregido = serialCorregido + serial.charAt(i);
				}
			}
		}
		return serialCorregido;
	}

	/**
	 * 
	 * @param pkcs7
	 * @return
	 * @throws Exception
	 */
	private X509Certificate getCertificateByPKCS7(String pkcs7)
			throws Exception {
		sun.security.pkcs.PKCS7 pkcs7Obj;
		X509Certificate certificate = null;
		try {
			pkcs7Obj = new sun.security.pkcs.PKCS7(
					new BASE64Decoder().decodeBuffer(pkcs7));
			X509Certificate certificates[] = pkcs7Obj.getCertificates();
			for (int i = 0; i < certificates.length; i++) {
				certificate = certificates[i];
			}
		} catch (Exception e) {
			logger.equals(e.getMessage());
			throw e;
		}
		return certificate;
	}

}
