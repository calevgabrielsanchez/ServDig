package mx.gob.imss.ctirss.delta.derechohabientes.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;




public class DeltaUtils {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(GrupoFamiliarUtil.class);
	}
	
	/**
	 * Pasa los parametros de las listas de Bean con el mismo nombre creando 
	 * una nueva lista de la clase que se asa como parametro
	 * @param <T>
	 * @param <X>
	 * @param origList
	 * @param classDest
	 * @return
	 */
	public static <T, X> List<X> beanListcopyProperties(Collection<T> origList, Class<X> classDest) {

		List<X> dest = new ArrayList<X>();

		X objDest = null;

		for (T objOrig : origList) {
			try {
				objDest = classDest.newInstance();
			} catch (Exception e) {

				e.printStackTrace();
			}
			BeanUtils.copyProperties(objOrig, objDest);

			dest.add(objDest);
		}

		return dest;
	}

	/**
	 * Crea una lista de objetos Hijos de una Lista de Objetos Padre La clase
	 * Hija debe extender del padre
	 * 
	 * @param <T>
	 * @param <X>
	 * @param padList
	 * @param classSon
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends T> Collection<X> beanListcopyPadreHijo(Collection<T> padList, Class<X> classSon) {

		Collection<X> dest = new ArrayList<X>();

		X objSon = null;
		for (T objPad : padList) {
			try {
				objSon = classSon.newInstance();
			} catch (Exception e) {

				e.printStackTrace();
			}
			objSon = (X) objPad;
			dest.add(objSon);
		}

		return dest;
	}

	/**
	 * Crea una lista de objetos Padre de una Lista de Objetos Hijos La clase
	 * Hija debe extender del padre
	 * 
	 * @param <T>
	 * @param <X>
	 * @param padList
	 * @param classSon
	 * @return
	 */
	public static <X, T extends X> Collection<X> beanListcopyHijoPadre(Collection<T> sonList, Class<X> classPad) {

		Collection<X> dest = new ArrayList<X>();

		X objPad = null;

		for (T objSon : sonList) {
			try {
				objPad = classPad.newInstance();
			} catch (Exception e) {

				e.printStackTrace();
			}

			objPad = objSon;
			dest.add(objPad);
		}

		return dest;
	}

	public Object getValorCampodeObjeto(Object obj, String sPropiedad)
			throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		if (!(obj != null && sPropiedad != null && !sPropiedad.equals("")))
			throw new IllegalArgumentException("El objeto o la propiedad (campo) estan vacias.");

		Object objRes = obj;
		Field fRes = null;

		String[] asObjetosPropiedad = sPropiedad.split("\\.");
		if (asObjetosPropiedad.length > 1) {
			for (int iCont = 0; iCont < asObjetosPropiedad.length - 1; iCont++) {
				Field f = objRes.getClass().getSuperclass().getDeclaredField(asObjetosPropiedad[iCont]);
				boolean bModificarAcceso = false;
				if (!f.isAccessible()) {
					f.setAccessible(true);
					bModificarAcceso = true;
				}
				objRes = f.get(objRes);
				if (bModificarAcceso)
					f.setAccessible(false);
			} // for
		} // if(asObjetosPropiedad.length
		fRes = objRes.getClass().getField(asObjetosPropiedad[asObjetosPropiedad.length - 1]);
		objRes = fRes.get(objRes);
		return objRes;
	}// getCampodeObjeto

	/**
	 * Calcula el agregado de identidad
	 * 
	 * @param calidad
	 * @param sexo
	 * @param fechaNacimiento
	 * @return
	 */
	public static String getAgregadoIdentidad(int calidad, int sexo, Date fechaNacimiento, Integer anioNacimiento) {

		String agregadoIdentidad = "";

		int anioAg = fechaNacimiento != null ? new Integer(DateUtils.dateFormatCustom(fechaNacimiento, "yy")): anioNacimiento;

		// ------------------------------------------------------------------
		// Si el a�o se env�a con 2 posiciones, se ajusta a 4
		// ------------------------------------------------------------------
		if ((anioNacimiento != null) && (anioNacimiento < 1900))
			anioNacimiento = formatAnioNac(anioNacimiento);

		int anioNac = fechaNacimiento != null ? new Integer(DateUtils.dateFormatCustom(fechaNacimiento, "yyyy"))
				: anioNacimiento;

		agregadoIdentidad = stringXPosiciones("" + calidad, 2) + sexo + anioNac
				+ getDigitoVerificadorAgregadoIdentidad(sexo, calidad, getAnio(anioAg), anioNac);

		return agregadoIdentidad;
	}

	/**
	 * Obtiene el digito verificador para el el agregado de identidad
	 * 
	 * @param sexo
	 * @param calidad
	 * @param anio
	 * @return
	 */
	public static int getDigitoVerificadorAgregadoIdentidad(int sexo, int calidad, int anio, int anioNac) {
		int digito = 0;
		int calidadEquivalente = 0;


		// Calcula la calidad equivalente
		calidadEquivalente = (calidad / 10) * 2 + (calidad % 10);

		if (calidadEquivalente > 1) {
			calidad = calidadEquivalente;
		}

		// Formula
		digito = (9 - (1 + calidad)) - (anio / 10);

		if (digito < 0) {
			digito = digito + 10;
		}
 
		if (sexo == 2) {
			digito = digito - 2;
		}
		
		//para sexo no binario 
		if (sexo == 3) {
			digito = digito - 4;
		}

		if (digito < 0) {
			digito = digito + 10;
		}
		
		if(anioNac>1999){
			digito = digito -2;
		}
		
		if (digito < 0) {
			digito = digito + 10;
		}
		
		return digito;

	}

	/**
	 * Calcula el anio para el digito verificador del agregado de udentidad
	 * 
	 * @param anio
	 * @return
	 */
	public static int getAnio(int anio) {
		int anioFinal = anio;

		if (anioFinal % 10 >= 5) {
			anioFinal = anioFinal + 5;
		}

		while (anioFinal % 10 != 0) {
			anioFinal = anioFinal + 19;
		}

		if (anioFinal > 100) {
			anioFinal = anioFinal - 100;
		}
		return anioFinal;
	}

	/**
	 * Rellena una cadena con ceros a la izquierda
	 * 
	 * @param original
	 * @param tam
	 * @return
	 */
	public static String stringXPosiciones(String original, int tam) {
		String resultado = original;

		while (resultado.length() < tam) {
			resultado = "0" + resultado;
		}

		return resultado;
	}

	/**
	 * Metodo para calcular el agregado medico, recibe lo siguiente
	 * 
	 * @param tipoAsegurado
	 *            - El id del parentesco de la cabeza de grupo fimiliar ya sea
	 *            asegurado(5) o pensionado(6)
	 * @param calidadIntegrante
	 *            - La calidad del integrante al que se esta registrando o
	 *            modificando
	 * @param sexoIntegrante
	 *            - El id del sexo del integrante que se registra o modifica,
	 *            hombre(1), mujer (2) o no binario (3)
	 * @param fechaNacimiento
	 *            - Fecha de nacimiento del integrante a registrar o modificar
	 * @param anioNacimiento
	 *            - anio de nacimiento de la persona a registrar o modificar
	 * @param patrones
	 *            - Lista de patrones activos, deben contener al menos los
	 *            atributos modalidad.numModalidad,
	 *            modalidad.siglaAgregadoMedico, numeroRegistroPatronal y
	 *            digVerificador
	 * @return String - agregadoMedico
	 */
	public static String getAgregadoMedico(long tipoAsegurado, int calidadIntegrante, int sexoIntegrante,
			Date fechaNacimiento, Integer anioNacimiento, List<SujetoObligado> patrones) {
		String agregadoMedico = "";

		// se calcula el anio que se pondra en el agregado
		int anioNac = fechaNacimiento != null ? new Integer(DateUtils.dateFormatCustom(fechaNacimiento, "yyyy"))
				: anioNacimiento;

		// Se verifica que el parentesco no sea pensionado, que en dado caso
		// tendria que ser asegurado
		if (tipoAsegurado != ParentescoEnum.PENSIONADO.getId()) {
			// Se verifica la calidad del integrante para en base a ella se
			// ponga el digito
			if (calidadIntegrante == 1) {
				agregadoMedico += "1";
			} else if (calidadIntegrante >= 2 && calidadIntegrante <= 10) {
				agregadoMedico += "2";
			} else if (calidadIntegrante >= 11 && calidadIntegrante <= 12) {
				agregadoMedico += "4";
			} else if (calidadIntegrante >= 13 && calidadIntegrante <= 39) {
				agregadoMedico += "3";
			} else if (calidadIntegrante >= 40 && calidadIntegrante <= 45) {
				agregadoMedico += "2";
			}
		} else {
			// si es pensionado
			if (calidadIntegrante == 1) {
				agregadoMedico += "5";
			} else if (calidadIntegrante >= 2 && calidadIntegrante <= 10) {
				agregadoMedico += "6";
			} else if (calidadIntegrante == 11 || calidadIntegrante == 12) {
				agregadoMedico += "6";
			} else if (calidadIntegrante >= 13 && calidadIntegrante <= 39) {
				agregadoMedico += "6";
			} else if (calidadIntegrante >= 40 && calidadIntegrante <= 45) {
				agregadoMedico += "6";
			}
		}

		// En base al id del sexo se pone la segunda posicion del agregado
		// medico
		if (SexoEnum.HOMBRE.getId() == sexoIntegrante) {
			agregadoMedico += "H";
		} else if  (SexoEnum.MUJER.getId() == sexoIntegrante) {
			agregadoMedico += "M";
		} else if  (SexoEnum.NO_BINARIO.getId() == sexoIntegrante) {
			agregadoMedico += "X";
		}

		// se verifica la longitud del anio de nacimiento, en caso de que sea
		// menor a 1900,
		// es decir, que solo traiga dos posiciones se le sumaran 1900
		anioNac = ("" + anioNac).length() == 2 ? (anioNac + 1900) : anioNac;

		// se agrega el a�o de nacimiento
		agregadoMedico += anioNac;

		// verificamos el parentesco para poner los ultimos digitos del agregado
		// mediso
		if (tipoAsegurado == ParentescoEnum.PENSIONADO.getId()) {
			// en caso de que sea pensionado las ultimas dos posiciones seran PE
			agregadoMedico += "PE";
		} else if (patrones != null && patrones.size() > 0) { // en otro
																// casoverificamos
																// que los
																// patrones no
																// sean nulos ni
																// vacios
			// Verificamos si algun patron trae OR, en ese caso prevalece OR
			for (SujetoObligado patron : patrones) {
				if ("OR".equals(patron.getModalidad().getSiglaAgregadoMedico())) {
					return agregadoMedico += patron.getModalidad().getSiglaAgregadoMedico();
				}
			}

			String siglaModalidad = "";
			// Recorremos los patrones para verificar cuales son los ultimos
			// digitos del agregado,
			// en caso de que ninguna contenga OR
			// TODO se comenta el ciclo ya que siempre se tomaba el primero, y
			// no hay regla que especifique lo contrario
			// for(SujetoObligado patron : patrones){
			// se obtiene al primer patron, ya que la regla no especifica que
			// hacer en caso de que se tenga mas de un patron activo
			SujetoObligado patron = patrones.get(0);
			// contruimos todos el registro patronal a 10 posiciones ya que la
			// lista de patrones de CFE es sin digito verificador
			String registroPatronal = patron.getNumeroRegistroPatronal() + patron.getModalidad().getNumModalidad();
			LOG.debug("El registro patronal es: " + registroPatronal);
			// obtenemos las ultimas 5 posiciones del registro patronal a 8
			// posiciones
			String subregistro = patron.getNumeroRegistroPatronal().substring(3, 8);
			LOG.debug("Las ultimas 5 posiciones del registro son : " + subregistro);
			// obtenemos la modalidad del patron
			Modalidad modalidad = patron.getModalidad();
			// obtenemos el numero de la modalidad
			String numModalidad = modalidad.getNumModalidad();
			LOG.debug("La modalidad del patron es: " + modalidad);
			// Verifiamos si es modalidad 32
			if (numModalidad.equals("32")) {
				// si la modalidad es 32 y el rp a 8 posiciones termina en
				// alguna de las siguientes combinaciones
				if ((subregistro.equals("99990") || subregistro.equals("99991") || subregistro.equals("99992")
						|| subregistro.equals("99993"))) {
					// si no es un registro patronal de CFE
					if (!patronListadoMod32CFE(registroPatronal)) {
						siglaModalidad += "ES";
					} else {
						// si es un registro patronal se cfe
						siglaModalidad += "SA";
					}
				} else {// De lo contrario si no termina en las combinaciones
						// anteriores
					if (patronListadoMod32CFE(registroPatronal)) {
						siglaModalidad += "SA";
					} else {
						siglaModalidad += "ES";
					}
				}
			} // verificamos si la modalidad es 33 y termina con 99999 en ese
				// caso los dos ultimos digitos can a ser ME
			else if (numModalidad.equals("33")) {
				if (subregistro.equals("99999")) {
					siglaModalidad += "ME";
				} else {
					siglaModalidad += "SF";
				}
			} else {// si no es la modalidad 32 ni 33 con registro que termina
					// en 99999, se ponen las siglas de acuerdo a la modalidad
				if (numModalidad.equals("37")) {
					siglaModalidad += "OR";
				} else {
					siglaModalidad += modalidad.getSiglaAgregadoMedico();
				}
			}
			// break;
			// }
			// concatenamos la sigla de la modalidad
			agregadoMedico += siglaModalidad;
		}

		return agregadoMedico;
	}

	/**
	 * Metodo para validar la modalidad de el patr�n si es de estudiantes
	 * 
	 * @param registroPatronal
	 * @param modalidades
	 * @return
	 */
	public static boolean validaModalidadEstudiante(String registroPatronal, List<Modalidad> modalidades,
			long tipoAsegurado) {
		// Posicion A
		boolean esEStudiante = false;
		String subregistro = registroPatronal.substring(3, 8);
		String seccionBMod = null;
		if (tipoAsegurado == ParentescoEnum.PENSIONADO.getId()) {
			return esEStudiante = false;
		}

		if (modalidades != null && modalidades.size() > 0) {

			for (Modalidad modalidad : modalidades) {
				if ("OR".equals(modalidad.getSiglaAgregadoMedico())) {
					return esEStudiante = false;

				} else if ("PE".equals(modalidad.getSiglaAgregadoMedico())) {
					return esEStudiante = false;
				} else if (modalidad.getNumModalidad().equals("32")) {
					if ((subregistro.equals("99990") || subregistro.equals("99991") || subregistro.equals("99992")
							|| subregistro.equals("99993"))) {
						if (!patronListadoMod32CFE(registroPatronal)) {
							seccionBMod = "ES";
						} else {
							seccionBMod = "SA";
						}
					} else {
						if (patronListadoMod32CFE(registroPatronal)) {
							seccionBMod = "SA";
						}
					}
				} else {
					return esEStudiante = false;
				}
			}
		}
		if (seccionBMod.equals("ES")) {
			return true;
		}
		return esEStudiante;
	}

	public static Integer formatAnioNac(Integer anioNac) {
		if (anioNac != null) {
			return anioNac + 1900;
		} else {
			return null;
		}
	}

	private static boolean patronListadoMod32CFE(String strPatron) {
		Properties prop = new Properties();
		boolean isPatCFE = false;
		String strNRP = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("patronesMod32CFE.properties");
			prop.load(input);
			strNRP = prop.getProperty(strPatron.substring(0, 10));
			if (strNRP != null && !strNRP.isEmpty()) {
				isPatCFE = true;
			}
		} catch (Exception e) {
			System.out.println("error al buscar en el properties" + e);
		}
		return isPatCFE;
	}

	public static void main(String[] args) {
		Modalidad modalidad = new Modalidad();
		modalidad.setSiglaAgregadoMedico("32");
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		modalidades.add(modalidad);

		System.out.println(DeltaUtils.getAgregadoIdentidad(1, 2, null, 1984));
		// System.out.println(DeltaUtils.getAgregadoMedico(6, 4, 2, new Date(),
		// null, "D549998930", modalidades));
	}

	public static Integer generaDigitoVerificador(String nss) {
		int suma = 0;
		int resultado = 0;
		for (int i = 1; i <= nss.length(); i++) {
			if (i % 2 == 0) {
				int multiplicacion = (Integer.parseInt(nss.charAt(i - 1) + "")) * 2;
				if (multiplicacion > 9) {
					suma = suma + ((multiplicacion - 10) + 1);
				} else {
					suma = suma + multiplicacion;
				}
			} else {
				suma = suma + (Integer.parseInt(nss.charAt(i - 1) + ""));
			}
		}
		int modulo = suma % 10;
		if (modulo == 0) {
			resultado = 0;
		} else if (modulo < 10) {
			resultado = 10 - modulo;
		}
		return resultado;
	}

	/**
	 * Este m�todo genera el d�gito verificador de un String con NRP
	 * conformado paritr de 3 posiciones municipio 5 posiciones serie 2
	 * modalidad
	 * 
	 * @param nrp
	 *            El NRP correspondiente
	 * @return El d�gito verificador para ese NRP
	 */
	public static int generaDigitoVerificadorRP(String nrp) throws Exception {

		// System.out.println("Entre al calculo del Digito NRP " + nrp );
		int factorDeConversion = 10;
		int digitoVerificador = 0;
		int paso3 = 0;
		boolean bandera = true;
		String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String clave = "";
		try {
			int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
			if (primeraLetra != -1) {
				clave = (primeraLetra + factorDeConversion) + nrp.substring(1, nrp.length());
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
			// System.out.println("sali del Digito ver: " + digitoVerificador);

		} catch (Exception e) {
			throw new Exception("ERROR AL CALCULAR EL DIGITO VERIFICADOR", e);
		}
		return digitoVerificador;
	}

}
