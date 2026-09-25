package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import mx.gob.imss.ctirss.delta.exception.individuo.carga.masiva.RegistroInvalidoException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargaMasivaValidator {
	
    private static final Logger LOG = LoggerFactory.getLogger(CargaMasivaValidator.class);
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
	private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u0026]{3})\\d{6}([\\w]{3})$";
	private static final String REGEX_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";
	private static final int LENGTH_CURP  = 18;
	private static final int LENGTH_RFC_FISICA  = 13;
	private static final int LENGTH_RFC_MORAL  = 12;
	private static final int LENGTH_SEXO = 1;
	private static final int LENGTH_FECHA = 10;
	
    private static final Map<String, Integer> FROM_DESC_TO_CVE_ENT_FED_NAC_MAP = new HashMap<String, Integer>();
    private static final String[] ABREVIATURAS_ENTIDADES = new String[] { "", "AS", "BC", "BS", "CC", "CL", "CM", "CS", "CH", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TS", "TL", "VZ", "YN", "ZS", "NE", "SE" };

    private static final String ERROR_REGISTRO_EN_BLANCO = "Registro en blanco";
    private static final String ERROR_REGISTRO_LAYOUT = "Registro en blanco/error en layout";
    
    static {
        Integer cveIndex = 0;
        // CONVERSION ENTIDAD FEDERATIVA
        for (String abreviaturaEntidad : ABREVIATURAS_ENTIDADES) {
        	if(abreviaturaEntidad.equals("NE")){
        		FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(abreviaturaEntidad, 35); // para que coincida con los ids de la taba DG_CAT_ESTADO
        	}if(abreviaturaEntidad.equals("SE")){
        		FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(abreviaturaEntidad, 99); // para que coincida con los ids de la taba DG_CAT_ESTADO
        	}else{
        		FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(abreviaturaEntidad, cveIndex++);
        	}
        }
    }
	
//	private static final int TAMAï¿½O_REGISTRO = 8; // es decir la cantidad de elementos contenidos en los pipes
	
	/**
	 * Metodo que valida la estructura de un registro del archivo de personas
	 * @param strRegistro
	 * @param numRegistro
	 * @return una lista de 2 elementos: el primero es el objeto resultante de la validacion, mientras que el segundo es el registro del archivo tal cual
	 */
	public static List<Fisica> validarRegistroArchivoFisica(String strRegistro, int numRegistro){
	    LOG.debug("Entro el metodo validaRegistroArchivoFisica para validar la linea del archivo[" + numRegistro + "] --> " + strRegistro);
	    
	    List<Fisica> personas = new ArrayList<Fisica>(2);
	    
		Fisica objPersonaFisica = new Fisica(); // este objeto sera el resultado de las validaciones
		Fisica registroOriginal = new Fisica(); // y este otro objeto sera la linea del archivo original vaciada a un objeto PersonaFisica
		
		PersonaCalificacion personaCalificacion_opf = new PersonaCalificacion();
		personaCalificacion_opf.setCalificacion(new Calificacion());
		objPersonaFisica.getPersonaCalificaciones().add(personaCalificacion_opf);

		PersonaCalificacion personaCalificacion_ro = new PersonaCalificacion();
		personaCalificacion_ro.setCalificacion(new Calificacion());
		registroOriginal.getPersonaCalificaciones().add(personaCalificacion_ro);

		try{
			
//			if(strRegistro == null || strRegistro.trim().equals("")){
//				throw new RegistroInvalidoException(ERROR_REGISTRO_EN_BLANCO);
//			}else{
				
				String[] arrayDatos = strRegistro.split("|");
				StringTokenizer tokens = new StringTokenizer(strRegistro, "|", false);
	
//	            objPersonaFisica.setNumeroLineaArchivo(numRegistro); //columna del datatable '# linea' que representara el numero de la linea del archivo
//	            registroOriginal.setNumeroLineaArchivo(numRegistro); //columna del datatable '# linea' que representara el numero de la linea del archivo
	
				int sizeArray = 0;
				String curp = "", rfc = "", nombre = "", primerApellido = "", segundoApellido = "", sexo = "", fechaNacimiento = "", entidadNacimiento = "";
				String mensajesError[] = {"CURP vacia", "RFC vacio", "NOMBRE(s) vacio", "PRIMER APELLIDO vacio", "", "SEXO vacio", "FECHA DE NACIMIENTO vacia", "ENTIDAD DE NACIMIENTO vacia"};
				StringBuffer mensajeError = new StringBuffer("");
	
				boolean layoutValido = validarLayoutBasicoLineaArchivoPF(strRegistro);
				if(layoutValido){
				
				    while(tokens.hasMoreTokens()){
				        
				        String token = tokens.nextToken();
						arrayDatos[sizeArray] = token.equals("-") ? "" : token;
						LOG.debug("arrayDatos[" + sizeArray + "] --> '" + arrayDatos[sizeArray] + "'");
						
						switch(sizeArray){
						    case 0:
						        curp = arrayDatos[0];
						        if(curp.length() != LENGTH_CURP || !curp.matches(REGEX_CURP_FISICA)){
				                    mensajesError[0] = "CURP";
						        }else{
						            mensajesError[0] = "";
						            objPersonaFisica.setCurpRenapo(curp);
						        }
						        registroOriginal.setCurpRenapo(curp);
						        break;
						        
						    case 1:
						        rfc = arrayDatos[1];
		                        if(rfc.length() != LENGTH_RFC_FISICA || !rfc.matches(REGEX_RFC_FISICA)){
		                            mensajesError[1] = "RFC";
		                        }else{
		                            mensajesError[1] = "";
		                            objPersonaFisica.setRfc(rfc);
		                        }
		                        registroOriginal.setRfc(rfc);
						        break;
						    case 2:
						        nombre = arrayDatos[2];
		                        if(nombre.length() == 0){
		                            mensajesError[2] = "NOMBRE(s)";
		                        }else{
		                            mensajesError[2] = "";
		                            objPersonaFisica.setNombre(nombre);
		                        }
		                        registroOriginal.setNombre(nombre);
						        break;
		                    case 3:
		                        primerApellido = arrayDatos[3];
		                        if(primerApellido.length() == 0){
		                            mensajesError[3] = "PRIMER APELLIDO";
		                        }else{
		                            mensajesError[3] = "";
		                            objPersonaFisica.setPrimerApellido(primerApellido);
		                        }
		                        registroOriginal.setPrimerApellido(primerApellido);
		                        break;
		                    case 4:
		                        segundoApellido = arrayDatos[4];
		                        objPersonaFisica.setSegundoApellido(segundoApellido);
		                        //este campo es opcional, por lo tanto no se valida
		                        registroOriginal.setSegundoApellido(segundoApellido);
		                        break;
		                    case 5:
		                        sexo = arrayDatos[5];
		                        if(sexo.length() != LENGTH_SEXO || (!sexo.equals("1") && !sexo.equals("2"))){
		                            mensajesError[5] = "SEXO";
		                        }else{
		                            mensajesError[5] = "";
		                            objPersonaFisica.getSexo().setIdSexo(Integer.parseInt(sexo));
		                            objPersonaFisica.getSexo().setDescripcion(objPersonaFisica.getSexo().getIdSexo() == 1 ? "HOMBRE" : "MUJER");
		
		                            registroOriginal.getSexo().setIdSexo(Integer.parseInt(sexo)); // este valor va al XML
		                            registroOriginal.getSexo().setDescripcion(sexo); // tiene que ser el campo descripcion y no Id porque el data teibol pinta las descripciones, no los Ids
		
		                        }
		                        break;
		                    case 6:
		                        fechaNacimiento = arrayDatos[6];
		                        Date fechaGringa = null;
		                        if(fechaNacimiento.length() != LENGTH_FECHA || !fechaNacimiento.matches(REGEX_FECHA)){
		                            mensajesError[6] = "FECHA DE NACIMIENTO";
		                        }else{
		                            mensajesError[6] = "";
		                            // el metodo valueOf de abajo requiere una cadena con formato aaaa-mm-dd
		                            //     19/01/1982
		                            //     0123456789
		                            fechaGringa = Date.valueOf(fechaNacimiento.substring(6) + "-" + fechaNacimiento.substring(3, 5) + "-" + fechaNacimiento.substring(0, 2)); 
		                            objPersonaFisica.setFechaNacimientoFormateada(fechaNacimiento);
		                            objPersonaFisica.setFechaNacimiento(fechaGringa);
		                        }
		                        registroOriginal.setFechaNacimientoFormateada(fechaNacimiento);
		                        registroOriginal.setFechaNacimiento(fechaGringa);
		                        break;
		                    case 7:
		                        entidadNacimiento = arrayDatos[7];
		                        int i = 0, llave = 0;
		                        for(Entry<String, Integer> entry : FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.entrySet()) {
		                            if(entry.getKey().equals(entidadNacimiento)) {
		                                llave = i;
		                                break;
		                            }
		                            i ++;
		                        }
		                        if(i == FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.size()) {
		                            mensajesError[7] = "ENTIDAD DE NACIMIENTO";
		                        }else{
		                            mensajesError[7] = "";
		                            objPersonaFisica.getLugarNacimiento().setClave(Integer.valueOf(llave - 1).toString());
		                            
		                            registroOriginal.getLugarNacimiento().setNombre(entidadNacimiento); // tiene que ser el campo descripcion y no Id porque el data table pinta las 
		                                                                                                // descripciones, no los Ids
		                            registroOriginal.getLugarNacimiento().setClave(Integer.valueOf(llave - 1).toString()); // este valor va en el XML
		                        }
		                        
		                        break;
		              
						}
						
						sizeArray++;
					}
				
				}
	
				// Como los datos basicos se consideran un bloque, en caso de que alguno tenga error, debemos borrar todos los datos basicos para que no se 
				// haga la busqueda ni en IMSS ni en RENAPO
				if(!(StringUtils.isWhitespace(mensajesError[2]) && StringUtils.isWhitespace(mensajesError[3]) && StringUtils.isWhitespace(mensajesError[5]) && StringUtils.isWhitespace(mensajesError[6]) && StringUtils.isWhitespace(mensajesError[7]))){
	                objPersonaFisica.setNombre(null);
	                objPersonaFisica.setPrimerApellido(null);
	                objPersonaFisica.setSegundoApellido(null);
	                objPersonaFisica.getSexo().setIdSexo(null);
	                objPersonaFisica.setFechaNacimiento(null);
	                objPersonaFisica.getLugarNacimiento().setClave(null);           
	            }
	            
				if(!layoutValido){
					mensajeError.append("3"); // 3 --> registro invalido ya que la linea completa esta en blanco o el layout es incorrecto
					mensajeError.append(ERROR_REGISTRO_LAYOUT);
				}else{
		            // Ahora pondremos en el primer caracter del mensaje de error un 0, 1 o 2 que indicaran si el registro completo es valido o no
					if(StringUtils.isWhitespace(mensajesError[2]) && StringUtils.isWhitespace(mensajesError[3]) && StringUtils.isWhitespace(mensajesError[5]) && StringUtils.isWhitespace(mensajesError[6]) && StringUtils.isWhitespace(mensajesError[7])){
		                mensajeError.append("0"); // 0 --> registro valido ya que el bloque de DATOS BASICOS es valido
		            }else if(StringUtils.isWhitespace(mensajesError[0]) || StringUtils.isWhitespace(mensajesError[1])){
		                mensajeError.append("1"); // 1 --> registro valido ya que al menos RFC y/o CURP son validos
		            }else{
		                mensajeError.append("2"); // 2 --> registro invalido ya que los 3 bloques contienen errores
		            }
		            
		            int i = 0;
		            for(String error: mensajesError){
		                LOG.debug("mensajesError[" + i + "] --> " + error);
		                if(!error.equals("")){
		                    mensajeError.append(error + ", ");
		                }
		                i ++;
		            }
		            		            	            
	            }
				
				// Ahora se setea el mensaje de error
                objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion( ((mensajeError.lastIndexOf(", ") == (mensajeError.length() - 2)) && (mensajeError.lastIndexOf(", ") != -1)) ? mensajeError.substring(0, mensajeError.lastIndexOf(", ")) + " son inv\u00e1lidos/vac\u00edos" : mensajeError.toString() );
	                          
			}
                
//		}catch(RegistroInvalidoException e){
//			LOG.error(ERROR_REGISTRO_EN_BLANCO);
//			objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(ERROR_REGISTRO_EN_BLANCO);
//		}
	    catch(Exception e){
			LOG.error("error al parserar el registro[" + numRegistro + "] valor [" +strRegistro+"] a causa de: \n" + e.getMessage());
			objPersonaFisica.setCurpRenapo(strRegistro);
			objPersonaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(e.getMessage());
		}finally{
            objPersonaFisica.setNumeroLineaArchivo(numRegistro); //columna del datatable '# linea' que representara el numero de la linea del archivo
            registroOriginal.setNumeroLineaArchivo(numRegistro); //columna del datatable '# linea' que representara el numero de la linea del archivo
		}
		
		personas.add(registroOriginal);
		personas.add(objPersonaFisica);
		
		return personas;
	}
	
	/**
	 * Metodo que valida la estructura de un regisrtro del archivo de personas
	 * @param strRegistro String con la linea a leer
	 * @param numRegistro numero de linea
	 * @return Persona Moral  con el rfc validados y setados
	 */
	public static Moral validaRegistroArchivoMoral(String strRegistro, int numRegistro){
		Moral objPersonaMoral = new Moral();
		
		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
		personaCalificacion.setCalificacion(new Calificacion());
		objPersonaMoral.getPersonaCalificaciones().add(personaCalificacion);
		
		try{
			
			if(strRegistro == null || strRegistro.trim().equals("")){
				throw new RegistroInvalidoException(ERROR_REGISTRO_EN_BLANCO);
			}else{	
				
				String[] arrayDatos = strRegistro.split("|");
				StringTokenizer tokens = new StringTokenizer(strRegistro,"|");
				
//				objPersonaMoral.setNumeroLineaArchivo(numRegistro); //columna del datatable '# linea' que representara el numero de la linea del archivo
				
				int sizeArray=0;
				while(tokens.hasMoreTokens()){
					 arrayDatos[sizeArray] = tokens.nextToken();
					 LOG.debug("el valor del array ["+arrayDatos[sizeArray]+"]");
					 sizeArray++;
				}
				 
				LOG.debug("el tamaño de la lista es " + sizeArray);
				if(sizeArray > 0 && sizeArray <=1){
					LOG.debug("el valor del split es [dato1[" + arrayDatos[0]+"]");
					objPersonaMoral.setRfc(arrayDatos[0]);
					if((arrayDatos[0].length() != LENGTH_RFC_MORAL || !arrayDatos[0].matches(REGEX_RFC_MORAL)) && arrayDatos[0].length() != 0){
						objPersonaMoral.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion("Formato RFC inv\u00e1lido");
					}
				}else{
					objPersonaMoral.setRfc(strRegistro);
					objPersonaMoral.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion("Registro con formato inv\u00e1lido");
				}
				
			}
			
		}catch(RegistroInvalidoException e){
			LOG.error(ERROR_REGISTRO_EN_BLANCO);
			objPersonaMoral.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(ERROR_REGISTRO_EN_BLANCO);
		}catch(Exception e){
			LOG.error("error al parserar el registro[" + numRegistro + "] valor [" +strRegistro+"]");
			objPersonaMoral.setRfc(strRegistro);
			objPersonaMoral.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion("Error al leer el registro: " + e.getMessage());
		}finally{
			objPersonaMoral.setNumeroLineaArchivo(numRegistro); //columna del datatable '# linea' que representara el numero de la linea del archivo
		}
		
		return objPersonaMoral;
	}
	
	/**
	 * 191807 241012
	 * Este metodo valida el layout basico de cada linea del archivo de persona fisica, es decir, que al menos cumpla con los 9 pipes y los 8 sub-bloques
	 * @param linea
	 * @return
	 */
	public static boolean validarLayoutBasicoLineaArchivoPF(String linea){
		
		int indice = 0, contador = -1; // el contador tiene que ser -1 porque el do while hace una iteracion mas
		
		do{
			indice = linea.indexOf('|');
			linea = linea.substring(indice + 1);
			contador ++;
				
		}while(indice != -1);
		
		return (contador == 9);
		
	}

}
