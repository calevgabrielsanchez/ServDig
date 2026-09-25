package mx.gob.imss.ctirss.delta.framework.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Hasher {
	private static final String CODE="LJ#RN$MPZ07Z";
	
	
	public static String cipher(String data) throws Exception{
		return cifrar(data, CODE);
	}
	
	
	public static String uncipher(String encryptedData) throws Exception{
		return descifrar(encryptedData, CODE);
	}
	
	public static String cifrar(String txt, String clave) throws Exception
	{
	SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
	DESKeySpec kspec = new DESKeySpec(clave.getBytes());
	SecretKey ks = skf.generateSecret(kspec);

	Cipher cipher = Cipher.getInstance("DES");
	cipher.init(Cipher.ENCRYPT_MODE, ks);

	String s1 = null;
	String s2 = "";

try {
		s1 = new String(cipher.update(txt.getBytes()), "ISO-8859-1");
		s2 = new String(cipher.doFinal(), "ISO-8859-1");
	}
	catch (Exception e) {
		System.err.println("Excepcion controlada cifrando: " + e.toString());
	} 

	return (s1+s2);
	}



	public static String descifrar(String textoCifrado, String clave) throws Exception
	{
		
		byte[]  bufferCifrado = textoCifrado.getBytes("ISO-8859-1");
		
	SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
	DESKeySpec kspec = new DESKeySpec(clave.getBytes());
	SecretKey ks = skf.generateSecret(kspec);

	Cipher cifrado = Cipher.getInstance("DES");
	cifrado.init(Cipher.DECRYPT_MODE, ks);

	String s1 = null;
	String s2 = "";

	try {
		s1 = new String(cifrado.update(bufferCifrado), "ISO-8859-1");
		s2 = new String(cifrado.doFinal(), "ISO-8859-1");
	}
	catch (Exception e) {
		System.err.println("Excepcion controlada descifrando: " + e.toString());
	} 

	return s1 + s2;
	}



	public static void main(String [] args)
	{
	try {
//	String texto = "En un lugar de la mancha de cuyo nombre no quiero acordarme...";
	String texto = "4563";
	
	String bb = cipher(texto);
	System.err.println("Cifrado: "+bb);
// for (byte b: bb) {
// System.out.print(b+" ");
// }
// System.out.println();

	String txt = uncipher(bb);

	System.out.println(txt);
	}
	catch (Exception e) {
	e.printStackTrace();
	}
	}


}
