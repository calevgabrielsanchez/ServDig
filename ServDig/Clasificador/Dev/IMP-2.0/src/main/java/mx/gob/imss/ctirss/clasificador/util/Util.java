package mx.gob.imss.ctirss.clasificador.util;

public class Util
{
  private static final String REG_EX_ACENTOS = "^( \\u00E1)$";
  private static final String COMODIN = "%";
  
  public static String cambiarAcentosPorComodin(String sSearch) {
    if (sSearch != null && !sSearch.equals("")) {
      sSearch.replaceAll("REG_EX_ACENTOS", "%");
    }
    return sSearch;
  }

  
  public static void main(String[] args) {
    System.out.println(cambiarAcentosPorComodin("m·xico"));
  }
}
