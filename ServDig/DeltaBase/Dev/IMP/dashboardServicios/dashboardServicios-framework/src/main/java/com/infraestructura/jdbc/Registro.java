package com.infraestructura.jdbc;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Date;

/**
 * Permite el intercambio de información entre objetos, encapsulando en un solo objeto
 * todos los parámetros que se requieran intercambiar, evitando de esta forma la
 * utilización de numerosos métodos get/set que hacen más complicado la lectura del
 * código y que generan más congestionamiento de información a través de la red.
 */
public class Registro implements Serializable {

	/** Conjunto de campos relacionados con un registro en particular.*/
	public HashMap Campos = new HashMap();

	/**
	 * Función que agrega un nombre de campo y un valor al mismo.
	 * @param oLlave Identificador del campo, se recomienda sólo utilizar Cadenas(String).
	 * @param oValor Valor para un campo, puede ser un objeto cualquiera.
	 * @return <code>true</code> si se agregó correctamente la definición y valor de un campo,
	 * <code>false</code> si no se agregó correctamente la definición y valor.
	 * <p><b>Ejemplo de código Clase DAO:</b></p>
	 * <p><code>
	 * <BR>&nbsp;sSql = " SELECT SUCUR.*, PAIS.* FROM CORP_SUCURSAL SUCUR, CORP_PAIS PAIS  WHERE "+
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;"    SUCUR.CVE_ENTIDAD = '"+sIdEntidad+"'  "+
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;"    AND                                   "+
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;"    SUCUR.ID_SUCURSAL = "+<b>registro.getDefCampo("ID_SUCURSAL")</b> +
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;"    AND                                   "+
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;"    PAIS.CVE_PAIS = SUCUR.CVE_PAIS        ";
	 * <BR>&nbsp;ejecutaSql();
	 * <BR>&nbsp;if (rs.next()){
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;<B>registro.addDefCampo("ID_SUCURSAL",rs.getString("ID_SUCURSAL"));</B>
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;registro.addDefCampo("CVE_PAIS",rs.getString("CVE_PAIS"));
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;registro.addDefCampo("DESC_SUCURSAL",rs.getString("DESC_SUCURSAL"));
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp;registro.addDefCampo("DESC_PAIS",rs.getString("DESC_PAIS"));
	 * <BR>&nbsp;}
	 * <BR>
	 * </code></p>
	 * <BR>
	 * <BR>
	 * <p><b>Ejemplo de Clase CON para búsquedas aproximadas:</b></p>
	 * <p><code>
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp; <B>parametros.addDefCampo("DESC_PAIS_BUS",request.getParameter("DescPaisBus"));</B>
	 * <BR>&nbsp;&nbsp;&nbsp;&nbsp; parametros.addDefCampo("DESC_SUCURSAL_BUS",request.getParameter("DescSucursalBus")==null?"":ValidaCadena.SinComillas(request.getParameter("DescSucursalBus")));
	 * </code></p>
	 */
	public boolean addDefCampo(Object oLlave, Object oValor) {
		try {
			//System.out.println("nuevo vaciado sobrecarga a dos");
			Campo campo = new Campo();
			campo.addDefPropiedad("VALOR_CAMPO", oValor);
			campo.addDefPropiedad("CAMPO_LLAVE", "F");
			//Campos.put(oLlave,oValor);
			Campos.put(oLlave, campo);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean addDefCampo(Object oLlave, Object oValor, boolean bLlave) {
		try {
			//System.out.println("nuevo vaciado sobrecarga a tres");
			Campo campo = new Campo();
			campo.addDefPropiedad("VALOR_CAMPO", oValor);
			campo.addDefPropiedad("CAMPO_LLAVE", (bLlave == true ? "V" : "F"));
			//Campos.put(oLlave,oValor);
			Campos.put(oLlave, campo);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Obtiene el valor del campo requerido.
	 * @param oLlave Identificador del campo.
	 * @return Valor en tipo objeto. En caso de las cadenas de caracteres,
	 * no es necesario hacer la conversión de dato.
	 *
	 * <p><b>Ejemplo de código Clase DAO:</b></p>
	 * <p><code>
	 * <BR> sSql = "INSERT INTO CORP_SUCURSAL ( "+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "   CVE_ENTIDAD,                 "+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "   ID_SUCURSAL,                 "+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "   CVE_PAIS,                    "+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "   DESC_SUCURSAL                "+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "   ) VALUES (                   "+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "'" + this.sIdEntidad  + "',"+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   " " + <B>registro.getDefCampo("ID_SUCURSAL")</B> + "," +
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "'" + registro.getDefCampo("CVE_PAIS") + "',"+
	 * <BR> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                   "'" + registro.getDefCampo("DESC_SUCURSAL") + "')";
	 * </code></p>
	 */
	public Object getDefCampo(Object oLlave) {
		try {
			String sLlave = (String) oLlave;
			//System.out.println("la llave es :/"+(sLlave)+"//");
			return ((Campo) Campos.get(sLlave)).getDefPropiedad("VALOR_CAMPO");
		}
		catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	public boolean isCampoLlave(Object oLlave) {
		return ((String) ((Campo) Campos.get(oLlave)).getDefPropiedad("CAMPO_LLAVE")).equals("F") ? false : true;
	}

	/**
	 * Retorna el conjunto de campos asociados al registro actual, para una
	 * manipulación especial por parte del programador.
	 * @return conjunto de Campos.
	 */
	public HashMap getCampos() {
		return Campos;
	}

	/**
	 * Obtiene el número de campos que comprenden el registro actual.
	 * @return Número de campos.
	 */
	public int getTamanioCampos() {
		return Campos.size();
	}

	/**
	 * Elimina todos los campos del registro actual.
	 */
	public void limpiaCampos() {
		Campos.clear();
	}

	/**
	 * Verifica si se encuentra vacío el conjunto de campos.
	 * @return <code>true</code> está vacio,
	 * <code>false</code> tiene por lo menos un campo.
	 */
	public boolean sinCampos() {
		return Campos.isEmpty();
	}

	/**
	 * Desecha el campo cuyo identificador de campo cumpla con la definición del
	 * parámetro.
	 * @param oBorrar Identificador de campo.
	 */
	public void eliminaCampo(Object oBorrar) {
		Campos.remove(oBorrar);
	}

	/**
	 * Define la existencia de un campo dentro del arreglo.
	 * @param oCampo Identificador de campo.
	 * @return <code>true</code> existe el campo,
	 * <code>false</code> no existe el campo.
	 */
	public boolean existeCampo(Object oCampo) {
		return Campos.containsKey(oCampo);
	}

	/**
	 * Define la existencia de un valor dentro de la galería de campos.
	 * @param oValor Objeto para buscar.
	 * @return <code>true</code> existe el valor,
	 * <code>false</code> no existe el valor.
	 */
	public boolean existeValorCampo(Object oValor) {
		return Campos.containsValue(oValor);
	}

	/**
	 * Verifica la igualdad entre los valores de dos registros, el actual y el
	 * que se pasa como parámetro.
	 * @param registroEmpate Objeto de tipo registro para verificación de igualdad
	 * @return <code>true</code> con iguales,
	 * <code>false</code> son diferentes.
	 */
	public boolean igual(Registro registroEmpate) {
		boolean bResultado = true;
		try {
			//INICIALIZA UNA CLASE Registro A PARTIR DE LA INSTANCIA ACTUAL
			Class cClase = this.getClass();

			//OBTIENE EL CONJUNTO DE CAMPOS
			Field[] campos = cClase.getFields();

			//BARRE TODOS LOS CAMPOS Y OBTIENE SOLO EL LLAMADO "Campos".
			for (int i = 0; i < (campos.length); i++) {

				Field campo = campos[i];
				//System.out.println("->>>>"+campo.getName());
				if (campo.getName().equals("Campos")) {
					//System.out.println("SI FUE cAMPOS ***************************************");
					//VERIFICA QUE LOS OBJETOS DE TIPO REGISTRO NO SEAN NULOS
					if ((campo.get(this) == null) || (campo.get(registroEmpate) == null)) { //Validacion de nulos en ambos lados
						bResultado = false;
					}
					else {
						//System.out.println("NO SON NULOS ***************************************");
						//OBTIENE LAS INSTANCIAS DE LOS CAMPOS DE AMBOS REGISTROS
						HashMap camposTempo = (HashMap) (campo.get(this));
						HashMap camposParam = (HashMap) (campo.get(registroEmpate));

						//SE OBTIENEN LOS IDENTIFICADORES DE CADA REGISTRO
						Set llavesTempo = camposTempo.keySet();
						Set llavesParam = camposParam.keySet();

						//CON RESPECTO AL REGISTRO ACTUAL SE HACE LA
						//ITERACIÓN PARA OBTENER LAS COINCIDENCIAS EN EL SEGUNDO REGISTRO
						Iterator it = llavesTempo.iterator();
						//System.out.println("TOTAL DE LLAVES UNO ************************************"+llavesTempo.size());
						//System.out.println("TOTAL DE LLAVES DOS ************************************"+llavesParam.size());

						while (it.hasNext()) {
							Object oLlave = it.next();
							//VERIFICANDO LA EXISTENCIA DE EL IDENTIFICADOR DE CAMPO EN AMBOS REGISTROS
							if (llavesTempo.contains(oLlave) && llavesParam.contains(oLlave)) {
								//SOLO EVALUA LOS CAMPOS DONDE EL VALOR SEA DE TIPO STRING Y LOS COMPARA
									   /*
								if(camposTempo.get((String)oLlave) instanceof String){
								String valorTempo = (String)camposTempo.get((String)oLlave);
								String valorParam = (String)camposParam.get((String)oLlave);
								//System.out.println("Llave: "+(String)oLlave+" ->UNO: '"+valorTempo+"' ->DOS: '"+valorParam+"'");
								if(!valorTempo.equals(valorParam)){
								bResultado = false;
								break;
								}
								}
								 */
								Object oTemp = camposTempo.get((String) oLlave);
								Object oPara = camposParam.get((String) oLlave);
								if (((Campo) oTemp).propiedades.get("VALOR_CAMPO") instanceof String) {
									String valorTempo = (String) ((Campo) oTemp).propiedades.get("VALOR_CAMPO");
									String valorParam = (String) ((Campo) oPara).propiedades.get("VALOR_CAMPO");
									//System.out.println("Llave: "+(String)oLlave+" ->UNO: '"+valorTempo+"' ->DOS: '"+valorParam+"'");
									if (!valorTempo.equals(valorParam)) {
										bResultado = false;
										break;
									}
								}
							}
							else {
								/* En caso de error detecta cual es el campo que no puede comparar */
								Object oTemp = camposTempo.get((String) oLlave);
								System.out.println("Esta Llave no tiene par: " + (String) oLlave + " ->UNO: '" + (String) ((Campo) oTemp).propiedades.get("VALOR_CAMPO"));
								/***/
								bResultado = false;
								break;
							}
						}
					}
				}
			}
			return bResultado;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * devuelve el mconjunto de campos que son llave
	 * @return Conjunto de nombres de campo llave,
	 */
	public HashMap getCamposLlave() {
		HashMap hashCamposLlave = new HashMap();
		try {
			Set llavesTempo = Campos.keySet();
			//CON RESPECTO AL REGISTRO ACTUAL SE HACE LA BUSQUEDA DE VALORES DE LLAVE PRIMARIA
			Iterator it = llavesTempo.iterator();
			while (it.hasNext()) {
				Object oLlave = it.next();
				Object oTemp = Campos.get((String) oLlave);
				if (((Campo) oTemp).propiedades.get("CAMPO_LLAVE").equals("V")) {
					hashCamposLlave.put((String) oLlave, (String) oLlave);
				}
			}
		}
		catch (Exception e) {
			hashCamposLlave.clear();
			e.printStackTrace();
		}
		return hashCamposLlave;
	}

	/**
	 * Copia la definición de campos y valores del registro actual al registro
	 * marcado como parametro.
	 * @param registroEmpate Registro receptor de los valores.
	 */
	public void copiaRegistro(Registro registroEmpate) {
		try {
			//INICIALIZA UNA CLASE Registro A PARTIR DE LA INSTANCIA ACTUAL
			Class cClase = this.getClass();

			//OBTIENE EL CONJUNTO DE CAMPOS
			Field[] campos = cClase.getFields();

			//BARRE TODOS LOS CAMPOS Y OBTIENE SOLO EL LLAMADO "Campos".
			for (int i = 0; i < (campos.length); i++) {
				Field campo = campos[i];
				//CUANDO EL VALOR DE LA PROPIEDAD DEL OBJETO ES "Campos"
				if (campo.getName().equals("Campos")) {
					//OBTIENE LAS INSTANCIAS DE LOS CAMPOS DE AMBOS REGISTROS
					HashMap camposTempo = (HashMap) (campo.get(this));
					HashMap camposParam = (HashMap) (campo.get(registroEmpate));
					//SE OBTIENEN LOS IDENTIFICADORES DE CADA REGISTRO
					Set llavesTempo = camposTempo.keySet();
					Set llavesParam = camposParam.keySet();
					//ITERA SOBRE LOS IDENTIFICADORES DEL REGISTRO ACTUAL
					Iterator it = llavesTempo.iterator();
					while (it.hasNext()) {
						Object oLlave = it.next();
						//VERIFICA LA EXISTENCIA DE LA LLAVE Y LA COPIA CON SUS VALORES
						if (llavesParam.contains(oLlave)) {
							camposParam.remove((String) oLlave);
							camposParam.put((String) oLlave, camposTempo.get((String) oLlave));
						}
						else {
							camposParam.put((String) oLlave, camposTempo.get((String) oLlave));
						}
					}
					//campo.set(registroEmpate,camposTempo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getXml() {
		StringBuffer sSalidaXml = new StringBuffer();
		Registro registroActual = this;
		Iterator camposRegistro = null;
		String sNombreCampo = "";

		HashMap campos = registroActual.getCampos();
		camposRegistro = campos.keySet().iterator();
		sSalidaXml.append("<registro>");
		while (camposRegistro.hasNext()) {
			sNombreCampo = (String) camposRegistro.next();
			sSalidaXml.append("<" + sNombreCampo + ">" + (String) registroActual.getDefCampo(sNombreCampo) + "</" + sNombreCampo + ">");
		}
		sSalidaXml.append("</registro>");
		return sSalidaXml.toString();
	}

	public static String difTimes(Date FechaMinuendo, Date FechaSustraendo, String sFormato) {

		long Horas = 0;
		long lMinutos = 0;
		long lSegundos = 0;
		double diferencia = FechaMinuendo.getTime() - FechaSustraendo.getTime();
		// DIFERENCIA EN MILISEGUNDOS
		if (sFormato.equals("MILIS")) {
			return String.valueOf(diferencia);
		}
		else if (sFormato.equals("SEGUNDOS")) {
			return String.valueOf((diferencia / 1000));
		}
		else if (sFormato.equals("MINUTOS")) {
			return String.valueOf(Math.floor(((diferencia / 1000) / 60)));
		}
		else if (sFormato.equals("HORAS")) {
			return String.valueOf(Math.floor(((diferencia / 1000) / 60) / 60));
		}
		else if (sFormato.equals("HMS")) { // FORMATO DE IMPRESION EN CONSULTAS
			Horas = (long) Math.floor((((diferencia / 1000) / 60) / 60));
			double minutos = (((diferencia / 1000) / 60) / 60) - (Math.floor((((diferencia / 1000) / 60) / 60)));
			lMinutos = (long) Math.floor((minutos * 60));
			double segundos = (minutos * 60) - (Math.floor(minutos * 60));
			lSegundos = (long) Math.floor((segundos * 60));
		}
		return String.valueOf(Horas) + "H " + String.valueOf(lMinutos) + "m " + String.valueOf(lSegundos) + "s";
	}
}
