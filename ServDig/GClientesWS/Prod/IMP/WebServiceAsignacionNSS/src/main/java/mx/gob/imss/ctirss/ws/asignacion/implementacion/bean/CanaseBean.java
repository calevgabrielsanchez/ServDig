/*
 * Created on 2/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.bean;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.util.CatalogoLugarNacimiento;

import java.util.ResourceBundle;
import java.util.StringTokenizer;






public class CanaseBean {
        
	private String nombre;
	private String apaterno;
	private String amaterno;
	private String mesNac;
	private String lugarNac;
	private String sexo;
	private String anio;
	private String nss;
	private String umf;
	/**
	 * @return Returns the umf.
	 */
	public String getUmf() {
		return umf;
	}
	/**
	 * @param umf The umf to set.
	 */
	public void setUmf(String umf) {
		this.umf = umf;
	}
	/**
	 * @return Returns the strDelegacion.
	 */
	public String getStrDelegacion() {
		return strDelegacion;
	}
	/**
	 * @param strDelegacion The strDelegacion to set.
	 */
	public void setStrDelegacion(String strDelegacion) {
		this.strDelegacion = strDelegacion;
	}
	/**
	 * @return Returns the strOperacion.
	 */
	public String getStrOperacion() {
		return strOperacion;
	}
	/**
	 * @param strOperacion The strOperacion to set.
	 */
	public void setStrOperacion(String strOperacion) {
		this.strOperacion = strOperacion;
	}
	/**
	 * @return Returns the strSubdelegacion.
	 */
	public String getStrSubdelegacion() {
		return strSubdelegacion;
	}
	/**
	 * @param strSubdelegacion The strSubdelegacion to set.
	 */
	public void setStrSubdelegacion(String strSubdelegacion) {
		this.strSubdelegacion = strSubdelegacion;
	}
	private String strDelegacion    = "";
	private String strSubdelegacion = "";
	private String strSerie     = "";
	private String strAnioInsc      = "";
	private String strCurp = ""; 
	private String strOperacion = "";
	
	public CanaseBean() {
	}
	
	public CanaseBean(String nombre,
			String apaterno,
			String amaterno,
			String mesNac,
			String lugarNac,
			String sexo,
			String anio,
			String nss){
		
		this.nombre = nombre;
		this.apaterno = apaterno;
		this.amaterno = amaterno;
		this.mesNac = mesNac;
		this.lugarNac = lugarNac;
		this.sexo = sexo;
		this.anio = anio;
		this.nss = nss;
		
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getNombreCics() {
		// conversion del apaterno-amaterno-nombre a formato CICS
		String nombreCics = translate4CICS(
				apaterno.toUpperCase().trim()+ "-" +
				amaterno.toUpperCase().trim()+ "-" +
				nombre.toUpperCase().trim());
		//---
		System.out.println("nombre traducido para el CICS: " + nombreCics);
		return nombreCics;
	}
	
	/**
	 * Método que transforma en '#' cualquier caracter dentro de la cadena recibida
	 * que pertenece al basic latin Unicode
	 * @param s cadena a transformar
	 * @return cadena transformada
	 */
	public String translate4CICS(String s) {
		
		char[] cadena = s.toCharArray();
		for (int i = 0; i < cadena.length; i++ ) {
			if (cadena[i] > '\u007f') {
				cadena[i] = '\u0023';
			}
		}
		return new String(cadena);
		
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApaterno() {
		return apaterno;
	}
	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}
	public String getAmaterno() {
		return amaterno;
	}
	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}
	public String getMesNac() {
		return mesNac;
	}
	
	public String getMesNacCics() {
        StringTokenizer strToken = new StringTokenizer(ResourceBundle.getBundle("MesNacimientoCics").getString(mesNac),"|");
        return strToken.nextToken();
        
	}
	
	public void setMesNac(String mesNac) {
		this.mesNac = mesNac;
	}
	
	/*public void setMesCics(String mesNacCics) { PENDIENTE
	 this.mesNac = mesNac;
	 }*/
	
	public String getLugarNac() {
		return lugarNac;
	}
	
	public String getLugarNacCics() {
	    //log.debug("Lugar NAcimientoCICS " + lugarNac);
		CatalogoLugarNacimiento lugarNacimiento = CatalogoLugarNacimiento.getInstance();
		LabelValueBean beanLugar = lugarNacimiento.obtenLugarNacimientoPorIndice(lugarNac);		
		return beanLugar.getDescCorta();
	}
	
	public void setLugarNac(String lugarNac) {
		this.lugarNac = lugarNac;
	}
	public String getSexo() {
		return sexo;
	}
	
	public String getSexoCics() {
	    
	    if (sexo != null && sexo.equals("1"))
	        sexo = "M";
	    if (sexo != null && sexo.equals("2"))
	        sexo = "F";
	    if (sexo != null && sexo.equals("0"))
	        sexo = "*";	    
		
	    
	    return sexo;
		        
		        //sexo != null && sexo.equals("1")) ? "M" : "F";//segun el bean, contiene hombre=1, mujer=2
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getAnio() {
		return anio;
	}
	
	public String getAnioCics() {
		return (anio != null && anio.length() == 4) ? anio.substring(2,4) : "00";//REVISAR
	}
	
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	
	public String getNssdl() {
		String nssdl = nss.substring(0,2);
		return nssdl;
	}
	
	public String getNssas() {
		String nssas = nss.substring(3,5);
		return nssas;
	}
	
	public String getNssan() {
		String nssan = nss.substring(6,8);
		return nssan;
	}
	
	public String getNsssec() {
		String nsssec = nss.substring(9,13);
		return nsssec;
	}
	
	public String getNssdv() {
		String nssdv = nss.substring(13,15);
		return nssdv;
	}
	
	public String getSexoCicsToWeb(String sexoCics){
	    if (sexoCics.equals("M"))
	           sexoCics = "1";
	    if (sexoCics.equals("F"))
	           sexoCics = "2";
	    if (sexoCics.equals("*"))
	           sexoCics = "0";
	    
		return sexoCics;//sexoCics != null && sexoCics.equals("M")) ? "1" : "0"; 
	}
	
	public String getLugarNacCicsToWeb(String lugarNacCics) {
		CatalogoLugarNacimiento lugarNacimiento = CatalogoLugarNacimiento.getInstance();
		LabelValueBean beanLugar = lugarNacimiento.obtenLugarNacimientoPorIndice(lugarNacCics);		
		return beanLugar.getDescCorta();
	}
	
	public String getMesNacCicsToWeb(String nesNacSics) {
	   // log.debug("nesNacSics nesNacSics nesNacSicsnesNacSics " + nesNacSics);
		return ResourceBundle.getBundle("MesNacimientoCics").getString(nesNacSics).trim();
	}
	
	/**
	 * Regresa un string con el valor de sexo formateado para la pantalla web
	 * @param sexoCics
	 * @return String con el atributo sexo
	 */
	public static String getSexoCicsToWebDescripcion(String sexoCics){
		String sexo = "";
		if (sexoCics.equalsIgnoreCase("M")) {
			sexo = "Masculino";
		}//if
		
		if (sexoCics.equalsIgnoreCase("F")){
			sexo = "Femenino";
		}
		
		if (sexoCics.equalsIgnoreCase("*"))		
		{
		    sexo = "*";
		}
		
		return sexo;
	}
    /**
     * Regresa un string con el valor de sexo formateado para la pantalla web
     * @param sexoCics
     * @return String con el atributo sexo
     */
    public static String getSexoDescripcion(int sexoCics){
        String sexo = "";
        if (sexoCics== 1) {
            sexo = "Masculino";
        }//if
        else if (sexoCics == 2){
            sexo = "Femenino";
        }
        return sexo;
    }
	/**
	 * Regresa la descripcion larga de un mes a parir del formato de mes del canase
	 * @param lugarNacCics
	 * @return Return String con la descripcion del mes
	 */
	public static String getMesNacDescripcion(String mes) {
		String strDesCortaLarga = ResourceBundle.getBundle("MesNacimientoCics").getString(mes).trim();
        StringTokenizer descMes = new StringTokenizer(strDesCortaLarga, "|");
        descMes.nextToken();
        return descMes.nextToken();
	 }

	
	public static String getLugarNacDescripcion(String lugarNacimiento) {
	    String descripcionLugar = "";
	    if (!lugarNacimiento.equals("0"))
	    {    
	        CatalogoLugarNacimiento lugarDesc = CatalogoLugarNacimiento.getInstance();
	        LabelValueBean infoBean = lugarDesc.obtenLugarNacimientoPorIndice(lugarNacimiento);
	       // log.debug(" infoBean en getLugarNacDescripcion "+infoBean.getLabel());
	        descripcionLugar = infoBean.getLabel();
	    }    
	    
        return descripcionLugar;
	 }
	
    /**
     * Regresa la descripcion larga de un mes a parir del formato de mes del canase
     * @param lugarNacCics
     * @return Return String con la descripcion del mes
     */
    public static String getMesNacCicsToWebDescriocion(String lugarNacCics) {
        String mes = ResourceBundle.getBundle("MesNacimientoCics").getString(lugarNacCics).trim();
        String strDesCortaLarga = ResourceBundle.getBundle("MesNacimientoCics").getString(mes).trim();
        StringTokenizer descMes = new StringTokenizer(strDesCortaLarga, "|");
        descMes.nextToken();
        return descMes.nextToken();
        
    }
	
	/**
     * Regresa la descripcion del estado de nacimiento a a partir del valor de estado del canase
	 * @param nesNacSics
	 * @return return  String con descripcion de lugar de nacimiento.
	 */
	public static String getLugarNacCicsToWebDescripcion(String nesNacSics) {
	    
		//CatalagoLugarNacimiento lugarNacimiento = CatalagoLugarNacimiento.getInstance();
		//LabelValueBean beanLugar = lugarNacimiento.obtenLugarNacimientoPorIndice(nesNacSics);		
		//return beanLugar.getDescCorta();
       //  log.debug(" nesNacSics nesNacSics " + nesNacSics);
        CatalogoLugarNacimiento lugarDesc = CatalogoLugarNacimiento.getInstance();
        LabelValueBean infoBean = lugarDesc.obtenLugarNacimientoPorDescCorta(nesNacSics);
       // log.debug(" infoBean en getLugarNacDescripcion "+infoBean.getValue());
        return infoBean.getValue();
        

	}
	/**
	 * @return Returns the strSerie.
	 */
	public String getStrSerie() {
		return strSerie;
	}
	/**
	 * @param strSerie The strSerie to set.
	 */
	public void setStrSerie(String strSerie) {
		this.strSerie = strSerie;
	}
	/**
	 * @return Returns the strAnioInsc.
	 */
	public String getStrAnioInsc() {
		return strAnioInsc;
	}
	/**
	 * @param strAnioInsc The strAnioInsc to set.
	 */
	public void setStrAnioInsc(String strAnioInsc) {
		this.strAnioInsc = strAnioInsc;
	}
	/**
	 * @return Returns the strCurp.
	 */
	public String getStrCurp() {
		return strCurp;
	}
	/**
	 * @param strCurp The strCurp to set.
	 */
	public void setStrCurp(String strCurp) {
		this.strCurp = strCurp;
	}
}