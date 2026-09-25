/**
 * 
 */
package mx.gob.imss.ctirss.reing.patrones;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Lucio Duran Silva
 *
 */
@WebService
@Stateless(name="CalculoNRPService")
public class CalculoNRPService implements CalculoNRPRemote {
	
	
	
	
	@PersistenceContext(unitName = "reingPersistenceUnit")
	protected EntityManager em;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.reing.patrones.CalculoNRPRemote#generaNRP(java.lang.String, java.lang.Long)
	 */
	@Override
	public String generaNRP(String cveMunicipio, Long modalidad) {
		
		String registroPatronal = null;
		String rp = null;
		String consulta = null;
		
		System.out.println("Se esta consultando el secuencual para generar registro patronal:  "+ cveMunicipio);

		consulta = "SELECT NRP_SSPA_" + cveMunicipio
				+ ".NEXTVAL as folio from DUAL";
		
		System.out.println("El valor de la consulta al secuencual es el siguiente:  " + consulta);
		 
		
		// obtiene la clave del municipio con base el cp
		Query query = this.em.createNativeQuery(consulta);

		List<Object[]> result = query.getResultList();

		Iterator it = result.iterator();

		String valorSecuencia = it.next().toString();
		
		valorSecuencia = rellenaConCeros(valorSecuencia);

		System.out.println("Valor final del secuencial para crear el rp :  " + valorSecuencia);
		
		rp = cveMunicipio + valorSecuencia + modalidad.toString();
		
		System.out.println("Registro Patronal generado :  " + rp);

		int digitoVerificador = generaDigitoVerificador(rp);

		registroPatronal = rp + digitoVerificador;

		return registroPatronal;
	}

	 /**
     * Rellena con ceros a la izquierda cuando el numero de la secuencia es menos de 5 caracteres
     * @param cad numero actual de la secuencia del municipio
     * @return El numero de secuencia a 5 caracteres
     */

    private String rellenaConCeros(String cad) {

        final StringBuffer strCont = new StringBuffer(cad);
        final StringBuffer strRev = strCont.reverse();
        final int cap = strRev.length();
        final int missingDigits = 5 - cap;

        for (int i = 0; i < missingDigits; i++) {
            strRev.append('0');
        }
        final StringBuffer strFinalFilled = strRev.reverse();
        return strFinalFilled.toString();
    }
    
	 /**
     * Este metodo genera el digito verificador de un String con NRP conformado partir de 3 posiciones municipio, 5 posiciones serie 2 modalidad
     * @param nrp El NRP correspondiente
     * @return El digito verificador para ese NRP
     */
    @Override
	public int generaDigitoVerificador(String nrp) {

		int factorDeConversion = 10;
		int digitoVerificador = 0;
		int paso3 = 0;
		boolean bandera = true;
		String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String clave = "";
		try {
			int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
			if (primeraLetra != -1) {
				clave = (primeraLetra + factorDeConversion)
						+ nrp.substring(1, nrp.length());
			} else {
				clave = nrp;
			}
			int i = clave.length() - 1;

			while (i >= 0) {
				if (bandera) {
					int porDos = Integer.parseInt("" + clave.charAt(i)) * 2;
					if (porDos > 9)// si el resultado es un numero de dos
									// cifras, es necesario tratar estas por
									// separado.
					{
						paso3 += (porDos % 10) + (porDos / 10);
					} else {
						paso3 += porDos;
					}
					bandera = false;
				} else {
					paso3 += Integer.parseInt("" + clave.charAt(i));
					bandera = true;
				}
				i--;
			}
			digitoVerificador = 10 - (paso3 % 10);
			if (digitoVerificador > 9) {
				digitoVerificador = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return digitoVerificador;
	}

}
