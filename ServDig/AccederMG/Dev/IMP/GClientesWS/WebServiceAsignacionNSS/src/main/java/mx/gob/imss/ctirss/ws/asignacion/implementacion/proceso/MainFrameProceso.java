/*
 * Created on 2/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.proceso;

/**
 * @author juancho
 *
 */

import gob.imss.tecnologia.comunes.excepciones.ExcepcionIMSS;
import gob.imss.tecnologia.comunes.excepciones.ManejadorErrores;

import java.util.StringTokenizer;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.AsignacionNSSBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.CanaseBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.ResponseMainFrameBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.exception.SindoException;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.mainframe.OperacionSindo;

import org.apache.log4j.Logger;





public class MainFrameProceso{
	
	private static Logger log = Logger.getLogger(MainFrameProceso.class);
	
	
	private String usuario = null;
	private String password = null;
	
	public MainFrameProceso(String usuario, String password){
		this.usuario = usuario;
		this.password = password;
	}
	
	/*
	 * Este metodo llena los parametros necesarios para el Alta y obtiene  un NSS
	 */
	
	public ResponseMainFrameBean altaCanase(AsignacionNSSBean consultaBean )throws Exception{		
		log.debug("Voy a hacer un alta");
		ResponseMainFrameBean response = null;
		String strResultado = null;
		String resFormato = "";
		consultaBean.setTipoOperacion("ALTA");
		String [] arregloDescripciones = null;
		CanaseBean objCanaseBusqueda = new CanaseBean();
		setBeanTranzaccion(objCanaseBusqueda, consultaBean);
		OperacionSindo operacion = new OperacionSindo(usuario, password);
		response = operacion.transaccion(objCanaseBusqueda);
		strResultado = response.getStrResultado();
		log.debug("regrese de la transaccion");
		if(response.getErrorNumber()== 1){
			strResultado = strResultado.replace('|', '-');
			
			StringTokenizer resTok = new StringTokenizer(strResultado, "-");
			while(resTok.hasMoreTokens()){
				resFormato += resTok.nextToken(); 
			}
			String[] descripcion = new String[3];
			//se modifican los campos para que pueda trabajar con el sistema
			//			descripcion= BDCanase.obtieneDescBean(consultaBean.getSexo(), consultaBean.getMes(),consultaBean.getLugarNacimiento());
			//			consultaBean.setSexoDes(descripcion[0]);
			//			consultaBean.setMesDes(descripcion[1]);
			//			consultaBean.setLugarNacimientoDes(descripcion[2]);
			
			
			if (( consultaBean.getSerie().equals("97") || consultaBean.getSerie().equals("99")) && consultaBean.getSexo() == 0 )
			{
				consultaBean.setSexoDes("NO INDICADO");
			}
			else
			{
				consultaBean.setSexoDes(CanaseBean.getSexoDescripcion(consultaBean.getSexo()));    
			}
			
			if (( consultaBean.getSerie().equals("97") || consultaBean.getSerie().equals("99")) && consultaBean.getMes() == 0 )
			{
				consultaBean.setMesDes("SIN MES");
			}
			else
			{
				consultaBean.setMesDes(CanaseBean.getMesNacDescripcion(consultaBean.getMes()+""));    
			}
			
			
			
			if (( consultaBean.getSerie().equals("97") || consultaBean.getSerie().equals("99")) && consultaBean.getMes() == 0 )
			{	
				consultaBean.setLugarNacimientoDes("NO INDICADO");
			}
			else
			{
				//consultaBean.setLugarNacimientoDes(CanaseBean.getLugarNacDescripcion(Integer.toString(consultaBean.getLugarNacimiento())));
				arregloDescripciones = MainFrameProceso.obtieneDescBean(consultaBean.getSexo(), consultaBean.getMes(), consultaBean.getLugarNacimiento());
				if (arregloDescripciones != null) {
					consultaBean.setLugarNacimientoDes(arregloDescripciones[2]);
					log.debug("El lugar de nacimiento es:" + consultaBean.getLugarNacimientoDes());
				}
			}
			
			
			
			
			
			consultaBean.setSeguridadSocial(resFormato);
			//se modifican los valores para la presentacion en la pantalla
			log.debug("el mesage de regreso es" + response.getErrorMessage() + response.getErrorNumber());
			log.debug("el nss es ["+resFormato+"]");
			consultaBean.setTerminoOk(0);
		}
		else if(response.getErrorNumber()== 2){
			StringTokenizer resTok = new StringTokenizer(strResultado, "|");
			while(resTok.hasMoreTokens()){
				resFormato += resTok.nextToken(); 
			}
			consultaBean.setTerminoOk(response.getErrorNumber());
			consultaBean.setTipoOperacion(resFormato);
		}
		
		return response;
	}
	
	/*Metodo que obtiene las descripciones de los enteros del bean AsignacionNSSBean que requieren descripcion
	 * @autor : Julieta Acosta Trejo
	 * @Param : int sexo
	 * @Param : int mes
	 * @Param : int LugarNacimiento
	 * @return : sArray.
	 * @throws : ExcepcionIMSS en caso de encontrar una Falla.
	 **/
	
	public static String[] obtieneDescBean(int sexo, int mes, int lugarNacimiento)
	throws ExcepcionIMSS{
		
		try {
			
			String[] sArray = new String[3];
			
			//String[] sArrayMes = new String[12];
			
			//Se asigna al arreglo la descripción del sexo
			if(mes==0) {
				sArray[1]= " ";
			}//if
			
			if(lugarNacimiento<1||lugarNacimiento>32) {
				sArray[2]= " ";
			}//if
			
			if (sexo==1) {
				sArray[0] = "Hombre";
			}//if
			else {
				sArray[0] = "Mujer";
			}//else
			
			//Se asigna al arreglo la descripción del mes
			String[] sArrayMes= {"Enero","Febrero","Marzo", "Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
			int i=0;
			
			do{
				if(i==(mes-1)) {
					sArray[1]=sArrayMes[i];
				}//if
				i++;
			}
			while (i!=(mes) && i<12);
			
			
			//Se asigna al arreglo la descripción del lugar de nacimiento
			String[] sArrayEstado= {"Aguascalientes","Baja California Norte","Baja California Sur","Campeche",
					"Coahuila","Colima","Chiapas","Chihuahua","Distrito Federal","Durango","Guanajuato","Guerrero",
					"Hidalgo","Jalisco","Estado de México","Michoacán","Morelos","Nayarit","Nuevo León","Oaxaca","Puebla","Querétaro",
					"Quintana Roo","San Luís Potosí","Sinaloa","Sonora","Tabasco","Tamaulipas","Tlaxcala","Veracruz",
					"Yucatán","Zacatecas","Nacido en el Extranjero"};
			i=0;
			//do{
			for(i=0;i<33;i++) {
				if(i==(lugarNacimiento-1)) {
					sArray[2]=sArrayEstado[i];
					
					break;
				}//if
				//i++;
			}
			//while (i!=(lugarNacimiento) && (i<33));
			
			return sArray;
			
		} catch (Exception e) {
			throw ManejadorErrores.getExcepcionIMSS(e, "");
		}
		
		
		
	}//obtieneDescBean
	
	public boolean valiaLoginMainFrame()throws SindoException, Exception{
		boolean usuarioValido = false;
		CanaseBean objCanase = new CanaseBean();   
		objCanase.setStrOperacion("LOGINACCESO");
		OperacionSindo objConsulta = new OperacionSindo(this.usuario, this.password);
		ResponseMainFrameBean responseMain= objConsulta.transaccion(objCanase);
		
		if(responseMain.getStrResultado().equalsIgnoreCase("|true")){
			log.debug("el usuario es valido en racf");
			usuarioValido = true;
		}
		
		
		return usuarioValido;
	}
	
	/**---------------------------------------------------------------------------------------***/
	
	public void setBeanTranzaccion(CanaseBean canaseBean, AsignacionNSSBean input)throws Exception{
		//canaseBean.setApaterno(input.getApellidoPaterno());
		//canaseBean.setAmaterno(input.getApellidoMaterno());
		//canaseBean.setNombre(input.getNombre());
		if(input.getApellidoPaterno()!=null && input.getApellidoPaterno().length()>27)
			canaseBean.setApaterno(input.getApellidoPaterno().substring(0,27));
		else
			canaseBean.setApaterno(input.getApellidoPaterno());
		
		if(input.getApellidoMaterno()!=null && input.getApellidoMaterno().length()>27)
			canaseBean.setAmaterno(input.getApellidoMaterno().substring(0,27));
		else
			canaseBean.setAmaterno(input.getApellidoMaterno());
		
		if(input.getNombre()!=null && input.getNombre().length()>35)
			canaseBean.setNombre(input.getNombre().substring(0,35));
		else
			canaseBean.setNombre(input.getNombre());
		
		
		canaseBean.setStrOperacion(input.getTipoOperacion());
        canaseBean.setStrCurp(input.getCURP());
		
		if(input.getTipoOperacion() == "ALTA"){
			
			if ((input.getSerie().equals("97") || input.getSerie().equals("99")) && input.getLugarNacimiento() == 0)
			{ 
				canaseBean.setLugarNac("00");    
			}
			else
			{
				canaseBean.setLugarNac(input.getLugarNacimiento()+"");    
			}
			
			canaseBean.setStrDelegacion(input.getStrDelegacion()+"");
			canaseBean.setStrSubdelegacion(input.getStrSubDelegacion()+"");
            log.debug("el valor de la umf es: " + input.getUMF() + " y con descripcion " + input.getUMFDes() );
            if (input.getUMFDes() != null && input.getUMFDes().length() > 3) {
            	canaseBean.setUmf(input.getUMFDes().substring(0,3));
            	log.debug("Me asigno el valor cuando es por descrición" + canaseBean.getUmf());
            } else {
            	canaseBean.setUmf(Integer.toString(input.getUMF()));
            	log.debug("Me asigno el valor cuando es por entero" + canaseBean.getUmf());
            }
			    
			log.debug("setStrSerie en setBeanTranzaccion " + input.getSerie());
			canaseBean.setStrSerie(input.getSerie()+"");   
			
			if ((input.getSerie().equals("97") || input.getSerie().equals("99")) && input.getAnioIngreso() == 0)
			{    
				canaseBean.setStrAnioInsc("00");
			}
			else
			{
				canaseBean.setStrAnioInsc(input.getAnioIngreso()+"");
			}
			
			if ((input.getSerie().equals("97") || input.getSerie().equals("99")) && input.getAnio() == 0)
			{    
				canaseBean.setAnio("00");
			}
			else
			{
				canaseBean.setAnio(input.getAnio()+"");    
			}
			
			
			if ((input.getSerie().equals("97") || input.getSerie().equals("99")) && input.getSexo() == 0)
			{
				canaseBean.setSexo("0");
			}
			else
			{
				canaseBean.setSexo(input.getSexo()+"");    
			}
			
			canaseBean.setMesNac(input.getMes()+"");
			
			canaseBean.setStrCurp(input.getCURP()+"");
		}
	}	
}
