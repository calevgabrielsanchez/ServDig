/*
 * Created on 31/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.util;

import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;


/**
 * @author juancho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EvaluaRegistrosUtil {
	 /**
     * Valida de la forma en que funciona la regla de negocio que se utiliza en el mainframe,la comparación de
     * nombres
     * @param nombreSolicitud String Nombre como aparece en la solicitud
     * @param nombreCICS String Nombre como se encuentra definifo en canese mainframe
     * @return boolean <code>true</code> si los nombres son equivalente, y false en caso contrario.
     */
    public  boolean compareLikeCICS(String nombreSolicitud,
                                           String nombreCICS) throws Exception{
      /**
       * Este algoritmo compara justo como lo hace CANASE al buscar las ocurrencias de un asegurado. Lo hace por nombres y apellidos.
       * La regla es:
       * 			- Una cadena es válida si se encuentran menos de 4 diferencias discontinuas, y menos de 3 discontinuas.
       * 			- La validación se hace en tripletes de apellido paterno, materno y nombre.
       * 			- Una diferencia continua es aquella que tiene otra adyacente de cualquier lado.
       * 			- Una diferencia discontinua es aquella que no tiene diferencias adyacentes.
       * 			- Las diferencias continuas y discontinuas no estan relacionadas entre sí; por ejemplo, dos diferencias continuas
       * 				no cuentan como una discontinua con respecto a otra para el conteo de discontinuas.
       */
      //Arreglos de strings donde se va a separar el formato de CANASE 'apellidopaterno-apellidomaterno-nombre',
      //donde el primer elemento es el apellido paterno, el segundo elemento es el materno, y el tercero es el nombre.
      //Esta estructura es fija y asegura el funcionamiento del alrogirmo. Si en alguna de ambas cadenas de los argumentos
      //el orden de tales datos estan cambiados, no se garantiza el funcionamiento del algoritmo.
      String[] deSolicitud = new String[3];
      String[] deCICS = new String[3];

      //Inicialización de separadores de la cadena con separador '-' y cadenas tanto del CICS como lo armado de la solicitud.
      StringTokenizer tok1 = new StringTokenizer(nombreSolicitud, "-");
      StringTokenizer tok2 = new StringTokenizer(nombreCICS, "-");

      //Inicialización de contadores.
      int k = 0;
      int l = 0;

      //Separación de cadenas y vaciado del resultado en los arreglos inicializados arriba.
      while (tok1.hasMoreTokens() && k <= 2) {
        deSolicitud[k] = tok1.nextToken();
        k++;
      }
      while (tok2.hasMoreTokens() && l <= 2) {
        deCICS[l] = tok2.nextToken();
        l++;
      }
      if(k != l){
      	System.out.println("no coinside el arreglo");
      	return false;
      }
      //La comparación se hace iterando caracter por caracter de cada uno de los 3
      //elementos del arreglo. Con esto aseguramos que de ambos, el primero es el ap. paterno,
      //el segundo es el ap. materno, y el tercero el nombre. El algoritmo calcula de un par,
      //la longitud máxima (guardada en arrLen), e itera la cadena sacada del arreglo.
      int arrLen = 0;

      //Contador de diferencias continuas
      int continuousDiff = 0;
      //Contador de diferencias discontinuas
      int discreteDiff = 0;

      //Holder booleano para ver si en la iteración ||||||||||||||||anterior hubo diferencias.
      boolean previousDiff = false;

      //Holders temporales.
      String tempSolicitud = null;
      String tempCICS = null;

      //Se iteran hasta 3 para procesar cada uno de los TRES elementos del arreglo.
      for (int i = 0; i < k; i++) {
        //De esa posición en el arreglo, se calcula la longitud máxima de cualquiera de ambos.
        arrLen = Math.max(deSolicitud[i].length(), deCICS[i].length());
        //Se rellena con blancos la cadena con menos posiciones que el máximo.
        tempSolicitud = StringUtils.rightPad(deSolicitud[i], arrLen);
        tempCICS = StringUtils.rightPad(deCICS[i], arrLen);
        //Se iteran los caracteres de ambos arreglos hasta el máximo de la cadena mayor.
        for (int j = 0; j < arrLen; j++) {
          //Se compara la primer posición...
          if (tempSolicitud.charAt(j) != tempCICS.charAt(j)) {
            //... y se verifica si ya había una diferencia anterior.
            if (!previousDiff) {
              //si no hubo diferencia anterior, entonces ésta se marca como anterior...
              previousDiff = previousDiff || true;
            }
            //...y se cuenta por defecto como diferencia continua.
            continuousDiff++;
          }
          //de lo contrario, si hubo diferencia anterior...
          else if (previousDiff) {
            //...significa que ésta diferencia es discontinua y se decrementa el default...
            continuousDiff--;
            //...y se aumenta las diferencias discontinuas.
            discreteDiff++;
            //Como hubo diferencias discontinuas, entonces no hay previa.
            previousDiff = false;
          }
          //Finalmente validamos la regla de negocio, y se invalida la cadena si el conteo
          //de diferencias es de mayor de 2 continuas, o mayor de 3 discontinuas.
          if (continuousDiff > 2 | discreteDiff > 3) {
            return false;
          }
        }
      }
      System.out.println("ya sali con true");
      return true;
    }
    
    public static String getNombreCics(String strNombre, String strApePat, String strApeMat) throws Exception{
        // conversion del apaterno-amaterno-nombre a formato CICS
          String nombreCics = translate4CICS(strApePat.toUpperCase().trim()+ "-" +
          										strApeMat.toUpperCase().trim()+ "-" +
												strNombre.toUpperCase().trim());
          System.out.println("nombre traducido para el CICS: " + nombreCics);
          return nombreCics;
      }
    
    /**
     * Método que transforma en '#' cualquier caracter dentro de la cadena recibida
     * que pertenece al basic latin Unicode
     * @param s cadena a transformar
     * @return cadena transformada
     */
    public static String translate4CICS(String s)throws Exception {

        char[] cadena = s.toCharArray();
        for (int i = 0; i < cadena.length; i++ ) {
            if (cadena[i] > '\u007f') {
                cadena[i] = '\u0023';
            }
        }
        return new String(cadena);

    }
    
    
    /** metodo para obtener la subdelegacion y los parametros necesarios en caso de serie convencional
     * @author bere
     *
     * TODO To change the template for this generated type comment go to
     * Window - Preferences - Java - Code Style - Code Templates
     */
   // public static 
}
