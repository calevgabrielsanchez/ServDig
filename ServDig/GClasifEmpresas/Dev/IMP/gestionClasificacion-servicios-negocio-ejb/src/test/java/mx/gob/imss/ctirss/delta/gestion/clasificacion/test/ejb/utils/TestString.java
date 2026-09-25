package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;

public class TestString {

	public static void main(String[] args) {	
		TestString test = new TestString();
		
//		System.out.println("Folio cambiado: " + test.cambiaFolio("CE-39-54-14/04/2023/0000010-D", Constantes.CLEM_POSFIJO_SUBDELEGACIONAL.charAt(0)));
//		
//		System.out.println("Resultado: " + test.validar("3bTasd asd34sdadad wNHYluiyuirt 5345; rest; dosp: 3_ertY5á/¡¿?!@#$%&+{})"));
//		System.out.println("Resultado: " + test.validar("3bTasd asd34sdadad\nÁ@(\\/ _Ññ"));
//		System.out.println("Resultado: " + test.validaCaracteresPermitidos("VIERNES 14 DE ABRIL DEL 2023"));
//		System.out.println("Resultado: " + test.validaCaracteresPermitidos(""));
//		
		String cadena = "&#034;ABASTECEDORA ALMACENADORA D&#039;MENO&#034; SA DE CV, SA DE CV";
		System.out.println(cadena);
		cadena = test.covertirHTMLaString(cadena);
		System.out.println(cadena);
		
	}


	private String covertirHTMLaString(String texto) {
		if (texto == null) {
			return null;
		}
		String textoConvertido = "";
		String[][] caracteresHTML = { { "&Ntilde;", "Ñ" }, { "&ntilde;", "ñ" }, { "&aacute;", "á" },
				{ "&Aacute;", "Á" }, { "&eacute;", "é" }, { "&Eacute;", "É" }, { "&amp;", "&" }, { "&iacute;", "í" },
				{ "&Iacute;", "Í" }, { "&Oacute;", "Ó" }, { "&oacute;", "ó" }, { "&Uacute;", "Ú" }, { "&uacute;", "ú" },
				{ "&quot;", "\"" }, { "&#039;", "\"" }, { "&#034;", "\"" } };
		for (int i = 0; i < caracteresHTML.length; i++) {
			textoConvertido = texto.replace(caracteresHTML[i][0], caracteresHTML[i][1]);
		}
		return textoConvertido;
	}	
	
	
	private String cambiaFolio(String folio, char tipo) {
		
		StringBuilder fol = new StringBuilder(folio);
		//myName.setCharAt(4, 'x');
		fol.setCharAt(folio.length()-1, tipo);

		return fol.toString();
	}
	
	
    private boolean validar(String cad){
    	System.out.println("Validando: " + cad);
    	cad = cad.replaceAll(" ", "");
    	Pattern pat = Pattern.compile("[a-zA-Z0-9,.;:_áÁéÉíÍóÓúÚüñÑ\\n¡¿?!@#$%&+{}()/\\\\]*");       
    	Matcher mat = pat.matcher(cad);
        return mat.matches();
    }
    
	private boolean validaCaracteresPermitidos(String cadena){
    	System.out.println("::: Validando: " + cadena);
    	boolean res = false;
    	cadena = cadena.replaceAll(" ", "");
    	Pattern pat = Pattern.compile("[a-zA-Z0-9,.;:_áÁéÉíÍóÓúÚüñÑ\\n¡¿?!@#$%&+{}()/\\\\]*");       
    	Matcher mat = pat.matcher(cadena);
    	res = mat.matches();
    	System.out.println("::: Resultado: " + res);    	
        return res;
    }
    
//    private boolean validaCaracteres(String userName){
//     	System.out.println("Validando: " + userName);
//     // expresión regular que revisa si tiene alguno de los siguientes caracteres
//     	String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|" +
//     	"\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
//     	"+|\\{+|\\}+|\\^+|\\<+|\\>+|\\\";
//     	Pattern pattern = Pattern.compile(REG_EXP);
//     	Matcher matcher = pattern.matcher(cadena);
//     	System.out.println(matcher.find()); //imprime true si tiene alguno de los caracteres anteriores o false si no tiene ninguno
//    }
	
}
