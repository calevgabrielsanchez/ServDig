package com.infraestructura.jdbc;

import java.io.Serializable;
import java.util.HashMap;

public class Campo implements Serializable{
/** Conjunto de propiedades relacionadas con un campo en particular.*/
	public HashMap propiedades = new HashMap();

	/**
	 * Función que agrega un nombre de propiedad y un valor al mismo.
	 * @param oLlave Identificador del propiedad, se recomienda sólo utilizar Cadenas(String).
	 * @param oValor Valor para una propiedad, puede ser un objeto cualquiera.
	 * @return <code>true</code> si se agregó correctamente la definición y valor de una propiedad,
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
	public boolean addDefPropiedad(Object oLlave,Object oValor){
		 try{
			propiedades.put(oLlave,oValor);
			return true;
		 }catch (Exception e){
			return false;
		 }
	}

	/**
	 * Obtiene el valor de la propiedad requerida.
	 * @param oLlave Identificador de propiedad.
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
	public Object getDefPropiedad(Object oLlave){
		  return propiedades.get(oLlave);
	}

	/**
	 * Retorna el conjunto de propiedades asociadas al campo actual, para una
	 * manipulación especial por parte del programador.
	 * @return conjunto de propiedades de campo.
	 */
	public HashMap getPropiedades(){
		  return propiedades;
	}

	/**
	 * Obtiene el número de propiedades que comprenden el campo actual.
	 * @return Número de propiedades.
	 */
	public int getTamanioPropiedades(){
		  return propiedades.size();
	}

	/**
	 * Elimina todas las propiedades del campos actual.
	 */
	public void limpiaPropiedades(){
		  propiedades.clear();
	}

	/**
	 * Verifica si se encuentra vacío el conjunto de propiedades.
	 * @return <code>true</code> está vacio,
	 * <code>false</code> tiene por lo menos una propiedad.
	 */
	public boolean sinPropiedades(){
		  return propiedades.isEmpty();
	}

	/**
	 * Desecha la propiedad del campo cuyo identificador de propiedad cumpla con la definición del
	 * parámetro.
	 * @param oBorrar Identificador de propiedad.
	 */
	public void eliminaPropiedad(Object oBorrar){
		  propiedades.remove(oBorrar);
	}

	/**
	 * Define la existencia de una propiedad dentro del arreglo.
	 * @param oCampo Identificador de propiedad.
	 * @return <code>true</code> existe la propiedad,
	 * <code>false</code> no existe la propiedad.
	 */
	public boolean existePropiedad(Object oCampo){
		  return propiedades.containsKey(oCampo);
	}

	/**
	 * Define la existencia de un valor dentro de la galería de propiedades.
	 * @param oValor Objeto para buscar.
	 * @return <code>true</code> existe el valor,
	 * <code>false</code> no existe el valor.
	 */
	public boolean existeValorPropiedad(Object oValor){
		  return propiedades.containsValue(oValor);
	}

	public String toString(){
		return (String)getDefPropiedad("VALOR_CAMPO");
	}
}