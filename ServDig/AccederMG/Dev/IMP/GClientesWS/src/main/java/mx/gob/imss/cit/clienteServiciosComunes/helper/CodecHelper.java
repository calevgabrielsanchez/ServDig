package mx.gob.imss.cit.clienteServiciosComunes.helper;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class CodecHelper {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public String encodeToBase64(final String toEncode) {
		Base64 base64Codec = new Base64();
		return new String(base64Codec.encode(toEncode == null ? new byte[] {}
				: toEncode.getBytes()), UTF8_CHARSET);
	}

	public String decodeFromBase64(final String toDecode) {
		return new String(decodeFromBase64AsByteArray(toDecode), UTF8_CHARSET);
	}

	public byte[] decodeFromBase64AsByteArray(final String toDecode) {
		Base64 base64Codec = new Base64();
		return base64Codec.decode(toDecode == null ? new byte[] {} : toDecode
				.getBytes());
	}

	public String encodeByteArrayToBase64(final byte[] data) {
		Base64 base64Codec = new Base64();
		return null == data ? "" : base64Codec.encodeToString(data);
	}

}
