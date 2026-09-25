package mx.gob.imss.ctirss.delta.framework.base.utility;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Cipher {
	private static final String CRYPTOGRAPHY_ALGO_DES = "DES";
	public static final String DES_ENCRYPTION_KEY = "I0M1S3S5DGT";
	
	private static Cipher cipher = null;
	private static DESKeySpec keySpec = null;
	private static SecretKeyFactory keyFactory = null;
	
	
	public static synchronized String cifrar(String inputString) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		return encrypta(inputString, DES_ENCRYPTION_KEY);
	}
	
	public static synchronized String descrifrar(String cipherString) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		return decrypta(cipherString, DES_ENCRYPTION_KEY);
	}
	
	
	public String cipher(String inputString) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		return encrypt(inputString, DES_ENCRYPTION_KEY);
	}
	
	public String uncipher(String cipherString) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		return decrypt(cipherString, DES_ENCRYPTION_KEY);
	}
	
	public String encrypt(String inputString, String commonKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {

		String encryptedValue = "";
		SecretKey key = getSecretKey(commonKey);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] inputBytes = inputString.getBytes();
		byte[] outputBytes = cipher.doFinal(inputBytes);
		encryptedValue = new BASE64Encoder().encode(outputBytes);
		encryptedValue = encryptedValue.replace('/', ',');
		encryptedValue = encryptedValue.replace('+', '_');
		encryptedValue = encryptedValue.replace('=', '.');
		return encryptedValue;
	}

	public String decrypt(String encryptedString, String commonKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		String decryptedValue = "";
		encryptedString = encryptedString.replace(' ', '+');
		encryptedString = encryptedString.replace(',', '/');
		encryptedString = encryptedString.replace('_', '+');
		encryptedString = encryptedString.replace('.', '=');
		SecretKey key = getSecretKey(commonKey);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] recoveredBytes = cipher.doFinal(new BASE64Decoder()
				.decodeBuffer(encryptedString));
		decryptedValue = new String(recoveredBytes);
		return decryptedValue;
	}
	
	
	public static synchronized String encrypta(String inputString, String commonKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {

		String encryptedValue = "";
		SecretKey key = getLlaveSecreta(commonKey);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] inputBytes = inputString.getBytes();
		byte[] outputBytes = cipher.doFinal(inputBytes);
		encryptedValue = new BASE64Encoder().encode(outputBytes);
		encryptedValue = encryptedValue.replace('/', ',');
		encryptedValue = encryptedValue.replace('+', '_');
		encryptedValue = encryptedValue.replace('=', '.');
		return encryptedValue;
	}

	public static synchronized String decrypta(String encryptedString, String commonKey)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		String decryptedValue = "";
		encryptedString = encryptedString.replace(' ', '+');
		encryptedString = encryptedString.replace(',', '/');
		encryptedString = encryptedString.replace('_', '+');
		encryptedString = encryptedString.replace('.', '=');
		SecretKey key = getLlaveSecreta(commonKey);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] recoveredBytes = cipher.doFinal(new BASE64Decoder()
				.decodeBuffer(encryptedString));
		decryptedValue = new String(recoveredBytes);
		return decryptedValue;
	}

	private SecretKey getSecretKey(String secretPassword) {
		SecretKey key = null;
		try {
			cipher = Cipher.getInstance(CRYPTOGRAPHY_ALGO_DES);
			keySpec = new DESKeySpec(secretPassword.getBytes("UTF8"));
			keyFactory = SecretKeyFactory.getInstance(CRYPTOGRAPHY_ALGO_DES);
			key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in generating the secret Key");
		}
		return key;
	}
	
	private static synchronized SecretKey getLlaveSecreta(String secretPassword) {
		SecretKey key = null;
		try {
			cipher = Cipher.getInstance(CRYPTOGRAPHY_ALGO_DES);
			keySpec = new DESKeySpec(secretPassword.getBytes("UTF8"));
			keyFactory = SecretKeyFactory.getInstance(CRYPTOGRAPHY_ALGO_DES);
			key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in generating the secret Key");
		}
		return key;
	}
	
	public static void main(String[] args) {
		try{
//		String input  = "jhxV7rGWOyh1Hre2boSyLHjTRBywhXdg";
		String input  = "138973873168313473";
//		Base64Cipher cipher = new Base64Cipher();
//		String encrypted = cipher.cipher(input);
//		System.out.println(encrypted);
//		
//		String decrypted = cipher.uncipher(encrypted);
		
		String encrypted = Base64Cipher.cifrar(input);
		String decrypted = Base64Cipher.descrifrar(encrypted);
		System.out.println(decrypted);
		}catch(Exception e){
			System.out.println("Exception" + e);
		}
	}

}
