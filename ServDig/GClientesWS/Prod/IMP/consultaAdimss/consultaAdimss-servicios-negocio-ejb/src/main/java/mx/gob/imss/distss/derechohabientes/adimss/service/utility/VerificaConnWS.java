package mx.gob.imss.distss.derechohabientes.adimss.service.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class VerificaConnWS {
	public static boolean checkWSDL(String wsdl, int segOut) {
		int timeout = segOut * 1000;
		try {
			URL testUrl = new URL(wsdl);
			StringBuilder answer = new StringBuilder(100000);

			long start = System.nanoTime();
			URLConnection testConnection = testUrl.openConnection();
			testConnection.setConnectTimeout(timeout);
			testConnection.setReadTimeout(timeout);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					testConnection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				answer.append(inputLine);
				answer.append("\n");
			}
			in.close();

			long nanoseg = System.nanoTime() - start;
			// si la respuesta de la pagina contiene resultadaos
			if (answer.length() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SocketTimeoutException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

}
