/**
 * 
 *  En Teor’a de la informaci—n y Ciencias de la Computaci—n se llama Distancia de Levenshtein, distancia de edici—n, o distancia entre palabras, al nœmero m’nimo de operaciones requeridas para transformar una cadena de caracteres en otra. Se entiende por operaci—n, bien una inserci—n, eliminaci—n o la sustituci—n de un car‡cter. Esta distancia recibe ese nombre en honor al cient’fico ruso Vladimir Levenshtein, quien se ocupara de esta distancia en 1965. Es œtil en programas que determinan cu‡n similares son dos cadenas de caracteres, como es el caso de los correctores de ortograf’a.
 *
 *	Por ejemplo, la distancia de Levenshtein entre "casa" y "calle" es de 3 porque se necesitan al menos tres ediciones elementales para cambiar uno en el otro.
 *
 *    casa - cala (sustituci—n de 's' por 'l')
 *    cala - calla (inserci—n de 'l' entre 'l' y 'a')
 *    calla - calle (sustituci—n de 'a' por 'e')
 *  
 *  
 * @author Vladimir Levenshtein
 *
 */
package mx.gob.imss.ctirss.delta.framework.util;

/**
 * @author Vladimir Levenshtein
 *
 */
public class LevenshteinDistance {

	

	private static int minimum(int a, int b, int c) {
        if(a<=b && a<=c)
        {
            return a;
        }
        if(b<=a && b<=c)
        {
            return b;
        }
        return c;
    }

    public static int computeLevenshteinDistance(String str1, String str2) {
        return computeLevenshteinDistance(str1.toCharArray(),
                                          str2.toCharArray());
    }

    private static int computeLevenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        for(int i=0;i<=str1.length;i++)
        {
                distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++)
        {
                distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++)
        {
            for(int j=1;j<=str2.length;j++)
            {
                  distance[i][j]= minimum(distance[i-1][j]+1,
                                        distance[i][j-1]+1,
                                        distance[i-1][j-1]+
                                        ((str1[i-1]==str2[j-1])?0:1));
            }
        }
        return distance[str1.length][str2.length];

    }
	
	
}
