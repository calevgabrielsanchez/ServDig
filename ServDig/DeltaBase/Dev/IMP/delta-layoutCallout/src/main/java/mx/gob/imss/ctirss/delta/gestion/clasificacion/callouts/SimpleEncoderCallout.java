package mx.gob.imss.ctirss.delta.gestion.clasificacion.callouts;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SimpleEncoderCallout {
	public static String simpleEncode(byte[] outputBytes) {
		String encryptedValue = new BASE64Encoder().encode(outputBytes);

		encryptedValue = encryptedValue.replace('/', ',');
		encryptedValue = encryptedValue.replace('+', '_');
		encryptedValue = encryptedValue.replace('=', '.');

		return encryptedValue;
	}

	public static byte[] simpleDecode(String encryptedString) {
		encryptedString = encryptedString.replace(' ', '+');
		encryptedString = encryptedString.replace(',', '/');
		encryptedString = encryptedString.replace('_', '+');
		encryptedString = encryptedString.replace('.', '=');

		byte[] outputBytes;
		try {
			outputBytes = new BASE64Decoder().decodeBuffer(encryptedString);
		} catch (IOException e) {
			outputBytes = new byte[0];
		}

		return outputBytes;
	}
}
