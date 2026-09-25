package mx.gob.imss.ctirss.delta.gestion.asegurado.web.utils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;

public class Cifrar {

	private static String semilla = "S1ST3M4P3C443R31NG3N13R14";
	
	public static String cifrar(final String textoClaro){
		try{
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, generarLlave());
			byte[] claveEncriptadaBytes = cipher.doFinal( textoClaro.getBytes() );
			return new BASE64Encoder().encode( claveEncriptadaBytes );
		}catch (Exception e) {
			return "";
		}
	}

	public static String descifrar(final String textoCifrado){
		System.out.println("TextoCifrado:"+textoCifrado);
		try{
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, generarLlave());
	
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(textoCifrado);
			byte[] utf8 = cipher.doFinal(dec); // Decode using utf-8
			return new String(utf8, "UTF8");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	private static SecretKeySpec generarLlave(){
		return new SecretKeySpec(new String((semilla.trim()).substring(0, 8)).getBytes(), "DES");
	}

	public static String generarPassword() {
		Random random = new Random();
		String pass = "";
		for(int i=0; i<4; i++){
			random = new Random();
			int caracterInt = (65+random.nextInt(57));
			if(caracterInt>=91 && caracterInt<=96){
				caracterInt = caracterInt+6;
			}
			char caracter = (char)caracterInt;
			pass = pass+caracter;
		}
		random = new Random();
		pass = pass +random.nextInt(10);
		pass = pass +random.nextInt(10);
		pass = pass +random.nextInt(10);
		pass = pass +random.nextInt(10);
		return Cifrar.cifrar(pass);
	}
	
	public static void main(String[] args) {
		Cifrar cifrar = new Cifrar();
		String x = "5/j46CGMy KWHsAkfRWE g==";
		x = x.replace(" ", "+");
		System.out.println(cifrar.descifrar(x));
	}
}
