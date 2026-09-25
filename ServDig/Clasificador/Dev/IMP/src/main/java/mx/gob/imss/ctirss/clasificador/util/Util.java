package mx.gob.imss.ctirss.clasificador.util;

public class Util {

	



	
	private static final String REG_EX_ACENTOS = "^( \\u00E1)$";
	
	
	private static final String COMODIN= "%";
	
	
	/**
	 * COnvierte los acentos por comodines, para que la busqueda en base de datos sea indistinta 
	 * de los acentos.
	 * @param sSearch
	 * @return
	 */
	public static String cambiarAcentosPorComodin(String sSearch){
		if( sSearch != null && !sSearch.equals("")){
			sSearch.replaceAll("REG_EX_ACENTOS", COMODIN);
		} 
		return sSearch;
	}
	
	public static void main(String args[]){
		
		System.out.println(Util.cambiarAcentosPorComodin("máxico"));
		
	}

}
