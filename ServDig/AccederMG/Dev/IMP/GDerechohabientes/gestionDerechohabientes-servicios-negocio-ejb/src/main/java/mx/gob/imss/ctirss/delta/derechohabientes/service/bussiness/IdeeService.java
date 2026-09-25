package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.IdeeServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.IdsIntegranteGrupo;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

@Stateless(name = "ideeService", mappedName = "ideeService")
public class IdeeService extends AbstractServiceEntity implements IdeeServiceLocal, IdeeServiceRemote {
	
	/**
	 * Metodo para generar los idees de un listado de derechohabientes
	 * el objeto IdsIntegranteGrupo debe contener al menos:
	 * 		-idAsignacionNSS : el id del nss (opcional siempre y cuando venga el nss)
	 * 		-nss : el nss (opcional siempre y cuando venga el nss)
	 * 		-idPersona:  el integrante del grupo familiar (obligatorio)
	 * 		-idPersonaDerechohabiente: el id de la tabla DIT_PERSONA_DERECHOHABIENTE (opcional, sino viene se actualizara o se insertara uno nuevo segun sea
	 * 			el caso, en caso de que existiara mas de un registor en la tabla no se actualizara a menos que en este atributo venga cual se quiere actualizar)
	 * 		-estudianteCL3: si es o no estudiante y se encuentra en la tabla CL3 (en caso de que venga nulo se tomara como false)
	 * @param datos
	 * @return
	 */
	@Override
	public List<IdsIntegranteGrupo> generarIdees(List<IdsIntegranteGrupo> datos) {
		List<IdsIntegranteGrupo> resultado = null;
		
		if(datos != null && !datos.isEmpty()) {
			resultado = new ArrayList<IdsIntegranteGrupo>();
			
			for(IdsIntegranteGrupo integrante : datos) {
				try{
				String idee = this.guardarOActualizarIdeeFromNssIdPersona(integrante.getIdAsignacionNSS(), integrante.getNss(), 
						integrante.getIdPersona(), integrante.getIdPersonaDerechohabiente(), integrante.getEstudianteCL3(), null);
				integrante.setIdee(idee);
				resultado.add(integrante);
				} catch(IllegalArgumentException e) {
					log.error("Ocurrio un error al crear el idee " + integrante, e);
				}
			}
		}
		
		return resultado;
	}

	/**
	 * Metodo para generar los idees de una lista de nss de estudiantes, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	@Override
	public Map<String, String> generarYGuardarIDEEPorNSsCL3(List<String> nsssCl3) {
		Map<String, String> ideesGenerados = null;

		if(nsssCl3 != null && !nsssCl3.isEmpty()) {
			ideesGenerados = new HashMap<String, String>();

			for(String nss: nsssCl3) {
				String idee = this.generarYGuardarIDEEParaNSSCL3(nss);
				if(idee != null) {
					ideesGenerados.put(nss, idee);
				}
			}
		}

		return ideesGenerados;
	}

	/**
	 * Metodo para generar los idees de una lista de ids de nss de estudiantes, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	@Override
	public Map<Long, String> generarYGuardarIDEEsPorIdAsignacionCL3(
			List<Long> idsNssCl3) {	
		Map<Long, String> ideesGenerados = null;
		
		if(idsNssCl3 != null && !idsNssCl3.isEmpty()) {
			ideesGenerados = new HashMap<Long, String>();
			
			for(Long idNss: idsNssCl3) {
				String idee = this.generarYGuardarIDEEPorIdNssCL3(idNss);
				if(idee != null) {
					ideesGenerados.put(idNss, idee);
				}
			}
		}
		
		return ideesGenerados;
	}

	/**
	 * Metodo para generar los idees de una lista de nss, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	@Override
	public Map<String, String> generarYGuardarIDEEPorNSs(List<String> nsss) {
		Map<String, String> ideesGenerados = null;
		
		if(nsss != null && !nsss.isEmpty()) {
			ideesGenerados = new HashMap<String, String>();
			
			for(String nss: nsss) {
				String idee = this.generarYGuardarIDEEParaNSS(nss);
				if(idee != null) {
					ideesGenerados.put(nss, idee);
				}
			}
		}
		
		return ideesGenerados;
	}
	
	/**
	 * Metodo para generar los idees de una lista de ids de nss, solo se generara para el 
	 * asegurado o pensionado, en caso de que haya mas de un integrante, no se
	 * generara nada
	 * @param nss
	 * @return
	 */
	@Override
	public Map<Long, String> generarYGuardarIDEEsPorIdAsignacion(
			List<Long> idsNss) {
		Map<Long, String> ideesGenerados = null;
		
		if(idsNss != null && !idsNss.isEmpty()) {
			ideesGenerados = new HashMap<Long, String>();
			
			for(Long idNss: idsNss) {
				String idee = this.generarYGuardarIDEEPorIdNss(idNss);
				if(idee != null) {
					ideesGenerados.put(idNss, idee);
				}
			}
		}
		
		return ideesGenerados;
	}


	/**
	 * Metodo para generar el idee solo para el dueño del nss
	 * de estudiante en caso de que haya mas de un integrante 
	 * no se actualizara el idee
	 */
	@Override
	public String generarYGuardarIDEEPorIdNssCL3(Long idAsignacionNSS) {
		return this.generarYGuardarIdeePorNss(idAsignacionNSS, null, true);
	}

	@Override
	public String generarYGuardarIDEEParaNSSCL3(String nss) {
		return this.generarYGuardarIdeePorNss(null, nss, true);
	}

	@Override
	public String generarYGuardarIDEEPorIdNss(Long idAsignacionNSS) {
		
		return this.generarYGuardarIdeePorNss(idAsignacionNSS, null, false);
	}

	@Override
	public String generarYGuardarIDEEParaNSS(String nss) {
		return this.generarYGuardarIdeePorNss(null, nss, false);
	}

	private String generarYGuardarIdeePorNss(Long idNss, String nss, Boolean estudianteCL3) {
		
		return this.guardarOActualizarIdeeFromNssIdPersona(idNss, nss, null, null, estudianteCL3, true);
	}
	
	/**
	 * Metodo para guardar o actualizar el IDEE de un estudiante a partir del id del nss
	 * @param idNss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	@Override
	public String generarYGuardarOActualizarIdeePorIdNssCL3(Long idNss,
			Long idPersona, Long idPersonaDerechohabiente) {
		return this.guardarOActualizarIdeeFromNssIdPersona(idNss, null, idPersona, idPersonaDerechohabiente, true,false);
	}

	/**
	 * Metodo para guardar o actualizar el IDEE de un estudiante a partir del numero nss
	 * @param nss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	@Override
	public String generarYGuardarOActualizarIdeePorNSSCL3(String nss,
			Long idPersona, Long idPersonaDerechohabiente) {
		return this.guardarOActualizarIdeeFromNssIdPersona(null, nss, idPersona, idPersonaDerechohabiente, true,false);
	}

	/**
	 * Metodo para guardar o actualizar el IDEE para algun integrante de un grupo familiar donde el nss no pertenece a un estudiante
	 * @param nss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	@Override
	public String generarYGuardarOActualizarIdeePorIdNss(Long idNss,
			Long idPersona, Long idPersonaDerechohabiente) {
		return this.guardarOActualizarIdeeFromNssIdPersona(idNss, null, idPersona, idPersonaDerechohabiente, false,false);
	}

	/**
	 *  Metodo para guardar o actualizar el IDEE para algun integrante de un grupo familiar donde el nss no pertenece a un estudiante
	 * @param nss
	 * @param idPersona
	 * @param idPersonaDerechohabiente
	 */
	@Override
	public String generarYGuardarOActualizarIdeePorNSS(String nss,
			Long idPersona, Long idPersonaDerechohabiente) {
		return this.guardarOActualizarIdeeFromNssIdPersona(null, nss, idPersona, idPersonaDerechohabiente, false,false);
	}

	@Override
	public Boolean actualizarIDEETablaAux(Integer numeroFilas) {
		log.debug("Entro a hacer la consulta de los IDEES");
		Boolean seguirConsultando = true;
		int errores= 0;
		
		Session session = this.getSession();
		String query ="select NOM_NOMBRE,NOM_PRIMER_APELLIDO,NOM_SEGUNDO_APELLIDO,FEC_NACIMIENTO,NUM_ANIO_NAC_REG,NUM_MES_NAC_REG,"+
		"CVE_ID_PERSONA,IDEE_ACTUAL, MAX(SUBSTR(NUM_NSS, 0,10) || '|' || NUM_CALIDAD) from AUX_PD_IDEEMENOR18_ACTUALIZA where "+
		"IDEE_NUEVO is null and rownum <= "+numeroFilas+" "+
		"group by NOM_NOMBRE,NOM_PRIMER_APELLIDO,NOM_SEGUNDO_APELLIDO,FEC_NACIMIENTO,NUM_ANIO_NAC_REG,NUM_MES_NAC_REG,"+
		"CVE_ID_PERSONA,IDEE_ACTUAL";

		SQLQuery queryIDEES = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			seguirConsultando = true;
			Integer numeroRegistros = resultado.size();
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaran");
			for(Object[] datosIDEE: resultado) {
					
				log.debug("se creara el ide para la persona con id: " + datosIDEE[6] + ", fecha de nacimiento: " + datosIDEE[3] + ", mes de nacimiento " + datosIDEE[5] +
						" y anio " + datosIDEE[4]);
				String nombre = (String) datosIDEE[0];
				String primerApellido = (String) datosIDEE[1];
				String segundoApellido = (String) datosIDEE[2];
				Date fechaNacimiento = (Date) datosIDEE[3];
				Integer anioNacimiento = datosIDEE[4] != null ? ((BigDecimal)datosIDEE[4]).intValue() : null;
				Integer mesNacimiento =	datosIDEE[5] != null ? ((BigDecimal)datosIDEE[5]).intValue() : null;
				Long idPersona= ((BigDecimal) datosIDEE[6]).longValue();
				String ideeActual = (String) datosIDEE[7];
				String guia = (String) datosIDEE[8];
				
				Boolean calcular = nombre != null && primerApellido != null && (fechaNacimiento != null || (anioNacimiento != null && mesNacimiento != null));
				
				try {
					if(calcular) {
						
						String[] nssCal = guia.split("|"); 
						
						String idee = this.generarIDEE(nssCal[0], new Integer(nssCal[1]), nombre, primerApellido, segundoApellido, fechaNacimiento, mesNacimiento, anioNacimiento);
						
						
							String querySolicitud = "";
							log.debug("Se actualiza el idee " + idee + ", con idee anterior "+ ideeActual+" para la persona " + idPersona + " tiene 18 caracteres? " + (idee.length() == 18));
							//de lo contrario si no existe la relacion se creara
							querySolicitud += "update AUX_PD_IDEEMENOR18_ACTUALIZA set IDEE_NUEVO='"+idee+"' where CVE_ID_PERSONA="+idPersona;

							this.getSession().createSQLQuery(querySolicitud).executeUpdate();
						
					} else {
						log.error("No fue posible calcular el idee porque algun dato no viene idPersona " + idPersona + ", nombre: "
								+ nombre + ", primer apellido: " + primerApellido + ", fechaNacimiento: " + fechaNacimiento + ", mes NAcimiento: " + mesNacimiento +
								", anio Nacimiento: " + anioNacimiento);
						errores++;  
					}
				} catch(Exception e) {
					errores++;
					e.printStackTrace();
					log.error("Ocurrio unu error al actualizar el idee a la persona " + idPersona);
				}
			}
			log.debug("Se encontraron " + numeroRegistros + " IDEES y se actualizaron " + (numeroRegistros-errores));
		} else {
			log.debug("No se encontraron ideess");
			seguirConsultando = false;
		}
		
		return seguirConsultando;
	}
	/**
	 * Metodo para generar el idee a partir del nss o id asignacion, y el id de la persona
	 * para gerarlo se requiere al menos del id del nss o el nss(cualquiera de los dos, si vinieran los dos se toma el id)
	 * el id de la persona
	 * @param idAsignacionNss - el id del nss (opcional siempre y cuando venga el numero nss)
	 * @param nss - el numero nss  (opcional siempre y cuando venga el id dle nss)
	 * @param idPersona - el id de la persona afectrada (no necesaria si  la bandera por nss esta en true)
	 * @param idPersonaDerechohabiente - opcional, en caso de que se quiera actualizar cierto IDEE, si este valor viniera
	 * nulo se buscara en DIT_PERSONA_DERECHOHABIENTE con el id de persona y se actualizaran todos los que se encuentren relacionados a la persona
	 * en caso de no encontrar nada, se insertara un nuevo valor
	 * @param estudianteCL3 - bandera para indicar si el idee se generara a partir del clon de estudiantes cl3
	 * @param porNSS - si la bandera va en true, se generara el nss solo para el asegurado o pensionado, siempre y cuando solo este el asegurado o pensionado,
	 * si existe algun otro integrante no se generará el idee
	 * @return
	 * @throws IllegalArgumentException
	 */
	private String guardarOActualizarIdeeFromNssIdPersona(Long idAsignacionNss, String nss, Long idPersona, Long idPersonaDerechohabiente, Boolean estudianteCL3, Boolean porNSS) throws IllegalArgumentException{
		
		String idee = null;
		estudianteCL3 = estudianteCL3 == null ? false : estudianteCL3;
		porNSS = porNSS == null ? false : porNSS;
		
		//se verifica que vengan todos los parametros necesarios
		if((idAsignacionNss == null && (nss == null || nss.trim().isEmpty()))) {
			throw new IllegalArgumentException("Los parametros no son suficientes");
		}
		
		if(porNSS != null && !porNSS && idPersona == null){
			throw new IllegalArgumentException("Los parametros no son suficientes");
		}
		
		String cadenaCL3 = estudianteCL3 ? "_cl3":"";
		String query = "select nss.NUM_NSS num_nss, grupo.NUM_CALIDAD numCalidad,"+
		"der.NOM_NOMBRE nombre, der.NOM_PRIMER_APELLIDO primerAp, der.NOM_SEGUNDO_APELLIDO segundoApe,"+
		"der.FEC_NACIMIENTO fec_nac, der.NUM_MES_NAC_REG mes, der.NUM_ANIO_NAC_REG anio,"+
		"personader.CVE_ID_PER_DERECHOHAB,"+
		"personader.CVE_EXPEDIENTE_ELECTRONICO "+
		"from dit_asignacion_nss"+cadenaCL3+ " nss "+
		"inner join dit_grupo_familiar"+cadenaCL3+" grupo on nss.CVE_ID_ASIGNACION_NSS = grupo.CVE_ID_ASIGNACION_NSS "+
		"inner join dit_persona der on grupo.CVE_ID_PERSONA_INTEGRANTE = der.CVE_ID_PERSONA "+
		"left outer join DIT_PERSONA_DERECHOHABIENTE personader on der.CVE_ID_PERSONA = personader.CVE_ID_PERSONA "+
		"where ";
		
		if(idAsignacionNss != null) {
			query += "nss.CVE_ID_ASIGNACION_NSS = " + idAsignacionNss;
		} else if(nss != null && !nss.trim().isEmpty()){
			query += "nss.NUM_NSS = '" + nss+"'";
		}
		
		if(idPersona != null) {
			query += " and grupo.CVE_ID_PERSONA_INTEGRANTE=" + idPersona;
		}
		
		Session session = this.getSession();
		SQLQuery queryIDEES = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			
			if(porNSS) {
				if(resultado.size() == 0) {
					log.error("No fue posible generar el idee ya que no esta registrado dentro de un grupo");
					return null;
				} else if(resultado.size() > 1) {
					log.error("No fue posible generar el idee ya que existe mas de un integrante en el grupo");
					return null;
				}
			} else {
				//si el resultado no es uno y no se nos proporciona cual actualizar
				if(resultado.size() !=1 && idPersonaDerechohabiente == null) {
					log.debug("No se generó el idee ya que se encontraron mas de un registro en dit_persona_derechohabiente para la persona " + idPersona+ " es estudiante? " + estudianteCL3);
					return null;
				}
			}
			
			Object[] integrante = resultado.get(0);

			String numNss = (String)integrante[0];
			Integer calidad = ((BigDecimal) integrante[1]).intValue();
			String nombre = (String) integrante[2];
			String primerApellido = (String) integrante[3];
			String segundoApellido = (String) integrante[4];
			Date fechaNacimiento = (Date) integrante[5];
			Integer mesNacimiento = integrante[6] != null ? ((BigDecimal)integrante[6]).intValue(): null;
			Integer anioNacimiento = integrante[7] != null ? ((BigDecimal)integrante[7]).intValue(): null;
			Long cveIdPerDer = integrante[8] != null ? ((BigDecimal) integrante[8]).longValue() : null;
			String ideeAnt = (String) integrante[9];

			idee = this.generarIDEE(numNss, calidad, nombre, primerApellido, segundoApellido, fechaNacimiento, mesNacimiento, anioNacimiento);
			String querySolicitud = "";

			if(idPersonaDerechohabiente == null) {
				//En dado caso de que se haya encontrado ya registrado en dit_persona_derechohabiente, solo actualizamos el idee
				if(cveIdPerDer != null) {
					log.debug("Se actualizar el idee anterior: " + ideeAnt + " por el idee: " + idee + " para la persona: " + idPersona + " y idPersonaDerechohabiente: " + cveIdPerDer
							+ " es estudiante? " + estudianteCL3);
					querySolicitud = "update DIT_PERSONA_DERECHOHABIENTE set CVE_EXPEDIENTE_ELECTRONICO  = '" + idee + "', FEC_REGISTRO_ACTUALIZADO = sysdate" ;
					querySolicitud += " where CVE_ID_PER_DERECHOHAB = " + cveIdPerDer;
				} else {
					log.debug("Se procede a insertar el idee " + idee + " para la persona " + idPersona+ " es estudiante? " + estudianteCL3);
					//en caso de que no hayamos encontrado registro, procedemos a insertar el idee
					querySolicitud = "insert into DIT_PERSONA_DERECHOHABIENTE (CVE_ID_PER_DERECHOHAB,FEC_REGISTRO_ALTA," +
							"CVE_EXPEDIENTE_ELECTRONICO,CVE_ID_PERSONA,IND_REG_ACTIVO) "+ 
							"values (SEQ_DITPERSONADERECHOHABIENTE.nextval,sysdate,'"+idee+"',"+idPersona+",1)";
				}
			} else {//si nos dan el id de la relacion lo actulizaremos
				log.debug("Se actualizar el idee anterior: " + ideeAnt + " por el idee: " + idee + " para la persona: " + idPersona + " y idPersonaDerechohabiente que fue pasado"
						+ " como parametro : " + idPersonaDerechohabiente+ " es estudiante? " + estudianteCL3);
				querySolicitud = "update DIT_PERSONA_DERECHOHABIENTE set CVE_EXPEDIENTE_ELECTRONICO  = '" + idee + "', FEC_REGISTRO_ACTUALIZADO = sysdate" ;
				querySolicitud += " where CVE_ID_PER_DERECHOHAB = " + idPersonaDerechohabiente;
			}

			if(!querySolicitud.isEmpty()) {
				//Se ejecuta el query
				this.getSession().createSQLQuery(querySolicitud).executeUpdate();
			}
		} else {
			log.error("No fue posible generar el IDEE ya que la conbinacion nss("+idAsignacionNss+","+nss+") con la persona " + idPersona + " no se encuentra, es estudiante? " + estudianteCL3);
		}
		
		return idee;
	}
	
	

	/**
	 * Metodo para solo generar el IDEE (Identificador De Expediente Electronico) recibiendo los siguientes parametros 
	 * @param nss - El nss del grupo familiar al que pertenece la persona (obligatorio)
	 * @param numCalidad - el numero de calidad del integrante dentro del grupo familiar (obligatorio)
	 * @param primerApellido - El apellido paterno del derechohabiente (obligatorio)
	 * @param segundoApellido - El apellido materno del derechohabiente  (opcional)
	 * @param nombre - el nombre del derechohabiente (obligatorio)
	 * @param fechaNacimiento - La fecha de nacimiento del derechohabiente (opcional si se cuenta con el mes y anio de nacimiento)
	 * @param mes - el mes de nacimiento de la persona a calcular el idee (opcional siempre y cuando se cuente con la fecha de nacimiento)
	 * @param anio - el anio de nacimiento de la persona a calcular el idee (opcional siempre y cuando se cuente con la fecha de nacimiento)
	 * @throws IllegalArgumentException - cuando falta algun dato para el calcula 
	 * @return String - IDEE
	 */
	@Override
	public String generarIDEE(String nss, Integer numCalidad,String nombre,String primerApellido, String segundoApellido,Date fechaNacimiento, Integer mes, Integer anio)
	throws IllegalArgumentException{
		log.debug("Entro al servicio del idee a generarlo");
		if(nss == null || nss.trim().isEmpty() || numCalidad == null || numCalidad.intValue() == 0 || primerApellido == null || primerApellido.trim().isEmpty()
				|| nombre == null || nombre.trim().isEmpty() || (fechaNacimiento== null && (mes == null && anio == null))) {
			throw new IllegalArgumentException("Los parametros para el calculo del IDEE no son correctos");
		}
		
		String idee ="";
		
		/**
		 * En dado caso de que la fecha de nacimiento venga nula, se construira una fecha nueva con el mes y el anio de nacimiento
		 * que se proporciones poniendo como dia el 1
		 */
		if(fechaNacimiento == null) {
			Calendar fechaNac = Calendar.getInstance();
			//se setea el dia 1
			fechaNac.set(Calendar.DATE, 1);
			//se agrega el mes de naicmiento
			fechaNac.set(Calendar.MONTH, (mes > 0 ? (mes-1) : mes));
			//se pone el anio de nacimiento
			fechaNac.set(Calendar.YEAR, anio);
			//se obtiene la fecha de nacimiento creada
			fechaNacimiento = fechaNac.getTime();
		} 
		
		//Se convierte a String la fecha de nacimiento
		String fecha = (new SimpleDateFormat("yyMMdd")).format(fechaNacimiento);
		
		//remplaza caracteres primer apellido
		primerApellido = reemplazarCaracter(primerApellido);
		
		if(primerApellido.isEmpty()){
			primerApellido="XXXXX";
		}
		
		if(segundoApellido != null && !segundoApellido.trim().isEmpty()) {
			//remplaza caracteres primer apellido
			segundoApellido = reemplazarCaracter(segundoApellido);
		} else {
			segundoApellido="XXXXX";
		}
		//quitamos cadenas invalidas de nombre y apellidos compuestos
		primerApellido = this.validarNombreApellidoCompuesto(primerApellido, true);
		segundoApellido = this.validarNombreApellidoCompuesto(segundoApellido, true);
		nombre = this.validarNombreApellidoCompuesto(nombre, false);
		
		//obtiene la primera letra del apellido paterno
		idee = PosCurp(primerApellido, 1, 2);
		//obtiene la primer consonante del apellido
		idee +=PosCurp(primerApellido, 1, 0);
		
		//obtiene la primera letra del apellido materno
		idee += PosCurp(segundoApellido, 1, 2);
		
		//obtiene la primer letra del nombre
		idee += PosCurp(nombre, 1, 2);
		//se agrega la fecha de nacimiento
		idee += fecha;
		
		//El algoritmo se compone de NSS y Agregado de Afiliación
		idee+= EDVC(nss, numCalidad);
		
		log.debug("el idee generado es: " + idee);
		
		return idee;
	}
	
	/**
	 * Metodo que divide el nss para ir generando las ultimas 7 posiciones del agregado medico
	 * @param guia
	 * @return
	 */
	private String EDVC(String nss, Integer numCalidad){
		
		String posiciones10A18="";
		
		nss = nss.length() == 10 ? nss : nss.substring(0, 10);
		//Se convierte el nss a numero para dividirlo
		Long regBase26=new Long(nss);
		//Se obtienen los modulos de la division del nss entre 36
		List<Long> valCode=base26(regBase26);
		
		for(int idxGenerado =0;idxGenerado<valCode.size();idxGenerado++){
			int i=0;
			
			if(idxGenerado==0){
				i=1;
			}
			//se obtiene la letra
			posiciones10A18+=valorLetra(valCode.get(idxGenerado).intValue(), i);
			
		}
		
		//una vez que se tienen las posiciones se concatena el ultimo valor de acuerdo a la calidad
		if(regBase26.toString().length()==9){
			posiciones10A18+=valorLetra(0, 1)+getCodigo(numCalidad);
		}else{
			if(posiciones10A18.length() == 7) {
				posiciones10A18+=getCodigo(numCalidad);
			} else {
				posiciones10A18+=valorLetra(0, 1)+getCodigo(numCalidad);
			}
		}
		
		return posiciones10A18;
	}
	
	private String valorLetra(int valAsc,int IDEE_POS){
		char valorLetra='a';
		
		if(valAsc<26){
			if(valAsc == 7 && IDEE_POS != 0){
				valorLetra=95;
			} else
				if(valAsc == 12 && IDEE_POS != 0){
					valorLetra = 36;
				}else{
					valorLetra = (char)(64 +(valAsc+1));
				}
		}else
			if(valAsc>=26){
				valorLetra = (char)(48 + (35 - valAsc));
			}else{
				valorLetra = 65;
			}
		return ""+valorLetra;
	}
	
	/**
	 * Metodo para rremplazar
	 * @param strCadena
	 * @return
	 */
	private String reemplazarCaracter(String cadena){
		int lintCont =0;
		String lstrCaracter = "";
		int codigoAscii=0;
		int longitudCadena = cadena.length();
		
		for(lintCont=0;lintCont<longitudCadena;lintCont++){
			lstrCaracter=""+cadena.charAt(lintCont);
			if(lstrCaracter=="Ñ"){
				cadena.toCharArray()[lintCont]='X';
			}
		}
		
		if(longitudCadena > 1) {
			for(lintCont=1;lintCont<longitudCadena;lintCont++){
				codigoAscii=cadena.charAt(lintCont);
				if(!(codigoAscii>=65 && codigoAscii<=90)){
					cadena.toCharArray()[lintCont]=' ';
				}
			}
		}
		
		return cadena;
	}
	
	/**
	 * 
	 * @param strValor
	 * @param BuscaPos
	 * @param intTipo
	 * @return
	 */
	private String PosCurp(String strValor,int BuscaPos,int intTipo ){
		int lintCode =0;
		String resultado="X";
		
		strValor=strValor.replace(" ", "");
		strValor=strValor.toUpperCase();
		
		for (int i=1;i<strValor.length(); i++) {
			lintCode = strValor.charAt(i);
			//regresa la primera vocal que encuentra
			if(intTipo==0){
				if((lintCode==65 || lintCode==69 ||lintCode==73 || lintCode==79 || lintCode==85)){
					resultado=""+strValor.charAt(i);
					break;
				}
			}else
				if(intTipo==1){
					//regresa la primera consonante 
					if(!(lintCode==65 || lintCode==69 ||lintCode==73 || lintCode==79 || lintCode==85)){
						resultado=""+strValor.charAt(i);
						break;
					}
				}else
					if(intTipo==2){
						resultado=""+strValor.charAt(0);
						break;
					}
		}
		return resultado;
	}
	
	/**
	 * Obtiene los valores asciss de las letras resutlado
	 * de la division del nss entre 36
	 * @param valorBase
	 * @return
	 */
	public static List<Long> base26(Long numNSS){
		int i =1;
		List<Long> vectOut=new ArrayList<Long>();
		Long mod26 =0L;
		long int26 =0;
		
		int26 = numNSS/36;
		mod26= numNSS -(36 *int26);	
		vectOut.add(mod26);
		
		do{
			mod26= int26 % 36;
			int26 = int26/36;
			vectOut.add(mod26);
			i+=1;
		}while ( int26>0  || i<=2); 
		
		return vectOut;
	}
	
	/**
	 * Se obtiene el ultimo digito del IDEE
	 * a partir del numero de calidad
	 * @param llave
	 * @return
	 */
	private String getCodigo(int llave){
		Map<Integer,String> matCalidad =  new HashMap<Integer,String>();
			matCalidad.put(1, "1"); 
		    matCalidad.put(2, "2"); 
		    matCalidad.put(3, "2");
		    matCalidad.put(4, "2");
		    matCalidad.put(5, "2");
		    matCalidad.put(6, "2");
		    matCalidad.put(7, "2");
		    matCalidad.put(8, "2");
		    matCalidad.put(9, "2");
		    matCalidad.put(10, "2");
		    matCalidad.put(11, "3");
		    matCalidad.put(12, "4");
		    matCalidad.put(13, "5");
		    matCalidad.put(14, "6");
		    matCalidad.put(15, "7");
		    matCalidad.put(16, "8");
		    matCalidad.put(17, "9");
		    matCalidad.put(18, "0");
		    matCalidad.put(19, "A");
		    matCalidad.put(20, "B");
		    matCalidad.put(21, "C");
		    matCalidad.put(22, "D");
		    matCalidad.put(23, "E");
		    matCalidad.put(24, "F");
		    matCalidad.put(25, "G");
		    matCalidad.put(26, "H");
		    matCalidad.put(27, "I");
		    matCalidad.put(28, "J");
		    matCalidad.put(29, "K");
		    matCalidad.put(30, "L");
		    matCalidad.put(31, "M");
		    matCalidad.put(32, "N");
		    matCalidad.put(33, "O");
		    matCalidad.put(34, "P");
		    matCalidad.put(35, "Q");
		    matCalidad.put(36, "R");
		    matCalidad.put(37, "S");
		    matCalidad.put(38, "T");
		    matCalidad.put(39, "U");
		    
		   String calidad= matCalidad.get(llave);
		   
		   return calidad;
	}
	
	/**
	 * Metodo para tomar las palabras correctas de un apellido o nombre compuesto
	 * @param nombreApellido
	 * @param apellido
	 * @return
	 */
	private String validarNombreApellidoCompuesto(String nombreApellido, Boolean apellido) {
		//separamos el nombre o apellido por espacios
		String[] cadena = nombreApellido.split(" ");
		//En caso de que se valide apellido obtenemos las cadenas invalidad para apellidos
		List<String> letrasExclusion = apellido ? this.letrasApellidos() : this.letrasNombres();
		//esta variable sera la primera cadena valida que se encuemtre en el nombre o apellido compuesto
		String cadenaReturn = null;
		//si el apellido tiene mas de una cadena
		if(cadena.length > 1) {
			//recorrremos todas las cadenas del apellido o nombre compuesto
			for(int i=0; i< cadena.length ; i++) {
				//cuando encontremos la primera cadena valida la tomaremos como apellido o nombre
				if(!this.esCadenaCorrecta(cadena[i], letrasExclusion) && cadena[i].trim().length() > 0) {
					cadenaReturn = cadena[i];
					break;
				} 
			}
		} else {
			//De lo contrario si solo hay una, no importara
			//si es cadena invalida aun asi se toma
			cadenaReturn = cadena[0];
		}
		
		//en dado caso de que todas las cadenas del apellido no sean permitidas
		//se toma el apellido tal cual
		if(cadenaReturn == null) {
			cadenaReturn = nombreApellido;
		}
		
		return cadenaReturn;
	}
	
	/**
	 * Metodo para saber si una palabra es permitida para el calculo del idee
	 * en el caso de nombres y apellidos compuestos
	 * @param palabra
	 * @param letrasIniciales
	 * @return
	 */
	private Boolean esCadenaCorrecta(String palabra, List<String> letrasIniciales) {

		for(String letrasInicial : letrasIniciales) {
			if(palabra.trim().equals(letrasInicial)) {
				return true;
			}
		}
	
		return false;
	}
	
	/**
	 * letras y palabras que se deben de omitir en un nombre compuesto
	 * @return
	 */
	private List<String> letrasNombres() {
		
		List<String> letras = new ArrayList<String>();
		
		letras.add("MARIA");
		letras.add("MA");
		letras.add("M");
		letras.add("JOSE");
		letras.add("J");
		letras.add("DA");
		letras.add("DAS");
		letras.add("DE");
		letras.add("DEL");
		letras.add("DER");
		letras.add("DI");
		letras.add("DIE");
		letras.add("DD");
		letras.add("EL");
		letras.add("LA");
		letras.add("LOS");
		letras.add("LAS");
		letras.add("LE");
		letras.add("LES");
		letras.add("MAC");
		letras.add("MC");
		letras.add("VAN");
		letras.add("VON");
		letras.add("Y");
		
		return letras;
		
	}
	
	/**
	 * Lista de letras y palabras con las que no debe de empezar un apellido
	 * compuesto
	 * @return
	 */
	private List<String> letrasApellidos() {

		List<String> letras = new ArrayList<String>();

		letras.add("DA");
		letras.add("DAS");
		letras.add("DE");
		letras.add("DEL");
		letras.add("DER");
		letras.add("DI");
		letras.add("DIE");
		letras.add("DD");
		letras.add("EL");
		letras.add("LA");
		letras.add("LOS");
		letras.add("LAS");
		letras.add("LE");
		letras.add("LES");
		letras.add("MAC");
		letras.add("MC");
		letras.add("VAN");
		letras.add("VON");
		letras.add("Y");

		return letras;

	}

}
