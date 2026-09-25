/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Lucio Duran Silva
 * 
 */
@Stateless(name = "compararPersonaFisicaEntidadExternaUtility", mappedName = "compararPersonaFisicaEntidadExternaUtility")
public class CompararPersonaFisicaEntidadExternaUtility extends
		AbstractServiceUtility implements
		CompararPersonaFisicaEntidadExternaUtilityLocal {

	@EJB
	private PersonaFisicaServiceUtilityLocal personaFisicaServiceUtility;
	@EJB
	private CompararPersonasServiceUtilityLocal compararPersonasServiceUtility;
	
	@Override
	public Fisica compararPersonaFisicaConRENAPO(Fisica candidato,
			Fisica entidad, Fisica entrada)
			throws ErrorComparacionDatosRENAPOException {

		// 1. Comparamos los nombres de la entidad con los nombres de la entrada
		Boolean registroCoincide = this.comparaNombreDePersonaFisica(entidad, entrada);
		
		if (registroCoincide) {
			
			/**
			 * Volvemos a comparar los nombres de la entidad pero ahora
			 * contra los nombres que tenemos del candidato seleccionado.
			 */
			registroCoincide = this.comparaNombreDePersonaFisica(entidad, candidato);
			
			if(registroCoincide){

				/*
				 * Correccion si la comparacion entre la entrada y la entidad es
				 * positiva, entonces debemos de sobre escribir los otros datos (
				 * Lugar de nacimiento, fecha de nacimiento, etc)
				 */
				candidato.setCurp(entidad.getCurp());
				candidato.setLugarNacimiento(entidad.getLugarNacimiento());
				candidato.setFechaNacimiento(entidad.getFechaNacimiento());
				candidato.setSexo(entidad.getSexo());
				// TODO: Falta definir que otros datos.
				
				// 2.1 Debemos de obtener los documentos de la entidad
				this.log.warn("Lista de documentos de la entidad : " + entidad.getDocumentosProbatorios().size());
				
				candidato.setDocumentosProbatorios(entidad.getDocumentosProbatorios());
				this.personaFisicaServiceUtility.asignarDocumentosProbatorios(candidato, entidad.getDocumentosProbatorios());
				
				this.log.debug(" Agregando la calificacion de VALIDADO EN REANPO...");
				// 3. Agregamos la calificacion de RENAPO.
				Calificacion calificacion = new Calificacion();
				calificacion.setIdCalificacion(new Long(CalificacionPersona.VALIDADO_RENAPO));
				calificacion.setDescripcion(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);

				PersonaCalificacion calificacionRenapo = new PersonaCalificacion();
				calificacionRenapo.setCalificacion(calificacion);
				calificacionRenapo.setPersona(new Fisica());
				calificacionRenapo.getPersona().setIdPersona(candidato.getIdPersona());

				candidato.getPersonaCalificaciones().add(calificacionRenapo);
			}else {
				throw new ErrorComparacionDatosRENAPOException();
			}
			
		} else {
			throw new ErrorComparacionDatosRENAPOException();
		}

		this.log.debug(" regresando el candidato comparado en el RENAPO...");

		return candidato;
	}

	@Override
	public Fisica compararPersonaFisicaConSAT(Fisica candidato, Fisica entidad,
			Fisica entrada) throws ErrorComparacionDatosSATException {

		
		// 1. Comparamos los elementos del nombre, primer apellido y segundo apellido.
		Boolean registroCoincide = this.comparaNombreDePersonaFisica(entidad, entrada);
		this.log.debug("Los datos no coinciden con los del SAT----" + registroCoincide);
		if(registroCoincide){
			
			
			/*
			 * Volvemos a comparar con los nombres del candidato.
			 */
			this.log.debug("Iniciando la comparacion doble " + entidad);
			
			
			this.log.debug("Iniciando la comparacion doble " + candidato);
			
			registroCoincide = this.comparaNombreDePersonaFisica(entidad, candidato);
			
			this.log.debug("Los datos coinciden...");
			
			if(registroCoincide){
				candidato.setRfc(entidad.getRfc());
				
				//2. Agregamos la calificacion de SAT.
				Calificacion calificacion = new Calificacion();
				calificacion.setIdCalificacion(new Long(
						CalificacionPersona.VALIDADO_SAT));
				calificacion.setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);

				PersonaCalificacion calificacionSat = new PersonaCalificacion();
				calificacionSat.setCalificacion(calificacion);
				calificacionSat.setPersona(new Persona());
				calificacionSat.getPersona().setIdPersona(candidato.getIdPersona());

				candidato.getPersonaCalificaciones().add(calificacionSat);
			}else{
				this.log.error("Los datos de entrada no coinciden con los recuperados del SAT");
				throw new ErrorComparacionDatosSATException();
			}
			
		}else{
			this.log.error("Los datos de entrada no coinciden con los recuperados del SAT");
			throw new ErrorComparacionDatosSATException();
		}
		
		return candidato;
		
	}

	
	/**
	 * Compara el nombre, primer apellido y segundo apellido entre los datos de entrada
	 * y los de la entidad externa ( sat o renapo).
	 * 
	 * Nota. Se esta utilizando el metodo de comparacion y no equals para que en un futuro
	 * se pueda implementar un algoritmo que nos sirva para determinar 
	 * si los nombres no coinciden en un 100 % que probabilidades se tienen de que sean los mismos
	 * para poder solventar casos en que un nombre pueda ser el mismo pero escrito de manera diferente
	 * Karla , Carla, o Mariana y Marianna , etc.
	 * 
	 * @param entidad Datos de la entidad externa (SAT o RENAPO)
	 * @param entrada Datos de la persona fisica 
	 * @return TRUE en caso de que coincidan o FALSE si no coinciden.
	 */
	public Boolean comparaNombreDePersonaFisica(Fisica entidad, Fisica entrada) {
		
		
		this.log.debug("Dato de la entidad :" + entidad);
		
		this.log.debug("Dato de entrada : " + entrada);
		
		Boolean modificarRegistro = Boolean.FALSE;

		// 1. Comparamos los nombres de la persona.
		int c1 = entidad.getNombre().compareToIgnoreCase(entrada.getNombre());

		/*
		 * Validacion del primer apellido (opcional por datos de Migracion)
		 */
		
		int c2;
		this.log.debug("Comparando el primer apellido ...." + entidad.getPrimerApellido() + " --" + entrada.getPrimerApellido() );
		if (StringUtils.isBlank(entrada.getPrimerApellido())) {
			c2 = -1;
		} else {
			c2 = entidad.getPrimerApellido().compareToIgnoreCase(entrada.getPrimerApellido());
		}

		/*
		 * Validacion del segundo apellido opcional
		 */
		this.log.debug("Comparando el segundo apellido ...." + entidad.getSegundoApellido() + " --" + entrada.getSegundoApellido());
		int c3;

		if(StringUtils.isBlank(entidad.getSegundoApellido()) && StringUtils.isBlank(entrada.getSegundoApellido())) {
			c3 = 0;
		} else if(StringUtils.isBlank(entidad.getSegundoApellido()) || StringUtils.isBlank(entrada.getSegundoApellido())){
			c3 = -1;
		} else {
			c3 = entidad.getSegundoApellido().compareToIgnoreCase(entrada.getSegundoApellido());
		}
		
		this.log.debug("Componenets de la comparacion c1 :" + c1 + ", c2 :" + c2 + ", c3:" + c3);

		// Se suman los valores de las comparaciones, si la suma es 0 es que todos los registros coinciden.
		int cf = Math.abs(c1) + Math.abs(c2) + Math.abs(c3);

		if (cf != 0) {
			modificarRegistro = Boolean.FALSE;
		} else {
			modificarRegistro = Boolean.TRUE;
		}

		return modificarRegistro;
	}
	
	/**
	 * 
	 * @param personaRenapo
	 * @param personaSugerida
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 */
	public Boolean comparaDatosBasicosRENAPO(Fisica personaRenapo,
			Fisica personaSugerida) throws ErrorComparacionDatosRENAPOException {
		Boolean resultado = Boolean.FALSE;

		try {
			//int sumatoria = comparaDiferenciaDatosBasicosRENAPO(personaRenapo, personaSugerida);

//			if (sumatoria == 0) {
//				resultado = Boolean.TRUE;
//			}
			log.error("No hay RENAPO, asi que dejamos pasar");
			resultado = Boolean.TRUE;
		} catch (Exception e) {
			//this.log.error(e);
			//throw new ErrorComparacionDatosRENAPOException();
			log.error("Si si, hubo error pero no hay RENAPO, asi que dejamos pasar");
			resultado = Boolean.TRUE;
		}
		return resultado;
	}

	@Override
	public Integer comparaDiferenciaDatosBasicosRENAPO(Fisica personaRenapo,
			Fisica personaSugerida) throws ErrorComparacionDatosRENAPOException {
		String smallnTilde = new String("\u00F1");
		String capitalNTilde = new String("\u00D1");
		String tildeRara = new String("\u00A5");
		
		RuleBasedCollator spCollator = null;
		this.log.debug("Dato de la personaRenapo :" + personaRenapo);
		this.log.debug("Dato de parsonaSugerida : " + personaSugerida);
		
//		try {
//			String traditionalSpanishRules = ("< a,A < b,B < c,C "
//					+ "< ch, cH, Ch, CH " + "< d,D < e,E < f,F "
//					+ "< g,G < h,H < i,I < j,J < k,K < l,L "
//					+ "< ll, lL, Ll, LL " + "< m,M < n,N " + "< "
//					+ smallnTilde + "," + capitalNTilde + ", " + tildeRara
//					+ " " + "< o,O < p,P < q,Q < r,R "
//					+ "< s,S < t,T < u,U < v,V < w,W < x,X " + "< y,Y < z,Z");
//			spCollator = new RuleBasedCollator(traditionalSpanishRules);
//			spCollator.setStrength(Collator.PRIMARY);
//
//		} catch (Exception e) {
//			log.error("error al parser", e);
//
//		}
//
//		/*
//		 * Antes de empezar a comparar, se buscan los # y &, que en realidad
//		 * son "enies", pero que desde la carga inicial vienen mal
//		 */
//		if (StringUtils.isNotBlank(personaSugerida.getNombre())){
//			personaSugerida.setNombre(personaSugerida.getNombre().replaceAll(
//					"[#|&]", capitalNTilde));
//		}
//		
//		if (StringUtils.isNotBlank(personaSugerida.getPrimerApellido())){
//			personaSugerida.setPrimerApellido(personaSugerida
//					.getPrimerApellido().replaceAll("[#|&]", capitalNTilde));
//		}
//		
//		if (StringUtils.isNotBlank(personaSugerida.getSegundoApellido())){
//			personaSugerida.setSegundoApellido(personaSugerida
//					.getSegundoApellido().replaceAll("[#|&]", capitalNTilde));
//		}
//
//		// Validacion nombres de la persona
//		int c1 = spCollator.compare(personaSugerida.getNombre(),
//				personaRenapo.getNombre());
//		this.log.debug("Se compara nombre " + personaSugerida.getNombre()
//				+ " -- " + personaRenapo.getNombre());
//		this.log.debug("Dato c1 : " + c1);
//
//		// Validacion primer apellido (Opcional cuando se lee un patron, ya que solo el campo nombre tiene valor)
//		int c2 = 0;
//		if (StringUtils.isNotBlank(personaSugerida.getPrimerApellido()) 
//				&& StringUtils.isNotBlank(personaRenapo.getPrimerApellido())) {
//			c2 = spCollator.compare(personaSugerida.getPrimerApellido(),
//					personaRenapo.getPrimerApellido());
//
//			this.log.debug("Se compara primerApellido "
//					+ personaSugerida.getPrimerApellido() + " -- "
//					+ personaRenapo.getPrimerApellido());
//		} else if (StringUtils.isBlank(personaSugerida.getPrimerApellido())
//				&& StringUtils.isNotBlank(personaRenapo.getPrimerApellido())) {
//
//			c2 = 1;
//
//			this.log.debug("Se compara segundoApellido SIN_PRIMER_APELLIDO_IMSS(POSIBLE PATRON) "
//					+ " -- " + personaRenapo.getPrimerApellido());
//		} else if (StringUtils.isNotBlank(personaSugerida.getPrimerApellido())
//				&& StringUtils.isBlank(personaRenapo.getPrimerApellido())) {
//			
//			c2 = 1;
//			
//			this.log.debug("Se compara segundoApellido "
//					+ personaSugerida.getPrimerApellido() + " -- SIN_PRIMER_APELLIDO_RENAPO");
//		} else {
//			this.log.debug("Sin primer apellido");
//		}
//
//		this.log.debug("Dato c2 : " + c2);
//
//		// Validacion del segundo apellido opcional
//		int c3 = 0;
//		if (StringUtils.isNotBlank(personaSugerida.getSegundoApellido()) 
//				&& StringUtils.isNotBlank(personaRenapo.getSegundoApellido())) {
//			
//			c3 = spCollator.compare(personaSugerida.getSegundoApellido(),
//					personaRenapo.getSegundoApellido());
//
//			this.log.debug("Se compara segundoApellido "
//					+ personaSugerida.getSegundoApellido() + " -- "
//					+ personaRenapo.getSegundoApellido());
//			
//		} else if (StringUtils.isNotBlank(personaSugerida.getSegundoApellido())
//				&& StringUtils.isBlank(personaRenapo.getSegundoApellido())) {
//			
//			c3 = 1;
//			
//			this.log.debug("Se compara segundoApellido "
//					+ personaSugerida.getSegundoApellido() + " -- SIN_SEGUNDO_APELLIDO_RENAPO");
//		} else if (StringUtils.isBlank(personaSugerida.getSegundoApellido())
//				&& StringUtils.isNotBlank(personaRenapo.getSegundoApellido())) {
//			
//			c3 = 1;
//			
//			this.log.debug("Se compara segundoApellido SIN_SEGUNDO_APELLIDO_IMSS "
//					+ " -- " + personaRenapo.getSegundoApellido());
//		} else {
//			this.log.debug("Sin segundo apellido");
//		}
//		this.log.debug("Dato c3 : " + c3);
//
//		// Validacion de la fecha de nacimiento
//		int c4 = 0;
//		if (personaRenapo.getFechaNacimiento() != null
//				&& personaSugerida.getFechaNacimiento() != null) {
//
//			c4 = personaRenapo.getFechaNacimiento().compareTo(
//					personaSugerida.getFechaNacimiento());
//
//			this.log.debug("Se compara fechaNacimiento "
//					+ personaSugerida.getFechaNacimiento() + " -- "
//					+ personaRenapo.getFechaNacimiento());
//		} else {
//			this.log.warn("Alguna de las fechas de nacimiento es nula, por lo tanto, se encuentra diferencia");
//			c4 = 1;
//		}
//		this.log.debug("Dato c4 : " + c4);
//
//		// Valida el sexo
//		int c5 = 0;
//		if (personaRenapo.getSexo() != null
//				&& personaRenapo.getSexo().getIdSexo() != null
//				&& personaSugerida.getSexo() != null
//				&& personaSugerida.getSexo().getIdSexo() != null) {
//
//			c5 = personaRenapo.getSexo().getIdSexo()
//					.compareTo(personaSugerida.getSexo().getIdSexo());
//
//			this.log.debug("Se compara sexo "
//					+ personaSugerida.getSexo().getIdSexo() + " -- "
//					+ personaRenapo.getSexo().getIdSexo());
//		} else {
//			this.log.warn("Alguno de los sexos es nulo, por lo tanto, se encuentra diferencia");
//			c5 = 1;
//		}
//		this.log.debug("Dato c5 : " + c5);
//
//		// Valida entidad nacimiento
//		int c6 = 0;
//		if (personaRenapo.getLugarNacimiento() != null
//				&& StringUtils.isNotBlank(personaRenapo
//						.getLugarNacimiento().getClave())
//				&& personaSugerida.getLugarNacimiento() != null
//				&& StringUtils.isNotBlank(personaSugerida
//						.getLugarNacimiento().getClave())) {
//
//			c6 = personaRenapo
//					.getLugarNacimiento()
//					.getClave()
//					.compareToIgnoreCase(
//							personaSugerida.getLugarNacimiento().getClave());
//
//			this.log.debug("Se compara lugarNacimiento "
//					+ personaSugerida.getLugarNacimiento().getClave()
//					+ " -- "
//					+ personaRenapo.getLugarNacimiento().getClave());
//		} else {
//			this.log.warn("Alguno de los lugares de nacimiento es nulo, por lo tanto, se encuentra diferencia");
//			c6 = 1;
//		}
//		this.log.debug("Dato c6 : " + c6);

		/*
		 * Comprobar resultado (se utiliza valor absoluto para evitar suma
		 * de valores negativos y positivos con el mismo valor)
		 */
		//int sumatoria = Math.abs(c1) + Math.abs(c2) + Math.abs(c3)
		//		+ Math.abs(c4) + Math.abs(c5) + Math.abs(c6);
		
		
		return 0;
	}

	public Boolean comparaDatosBasicosSAT(Fisica personaSat,
			Fisica personaSugerida) throws ErrorComparacionDatosSATException {
		Boolean resultado = Boolean.FALSE;

		String smallnTilde = new String("\u00F1");
		String capitalNTilde = new String("\u00D1");
		String tildeRara = new String("\u00A5");

		try {
			RuleBasedCollator spCollator = null;
			this.log.debug("Dato de la personaSat :" + personaSat);
			this.log.debug("Dato de parsonaSugerida : " + personaSugerida);
			
			try {
				String traditionalSpanishRules = ("< a,A < b,B < c,C "
						+ "< ch, cH, Ch, CH " + "< d,D < e,E < f,F "
						+ "< g,G < h,H < i,I < j,J < k,K < l,L "
						+ "< ll, lL, Ll, LL " + "< m,M < n,N " + "< "
						+ smallnTilde + "," + capitalNTilde + ", " + tildeRara
						+ " " + "< o,O < p,P < q,Q < r,R "
						+ "< s,S < t,T < u,U < v,V < w,W < x,X " + "< y,Y < z,Z");
				spCollator = new RuleBasedCollator(traditionalSpanishRules);
				spCollator.setStrength(Collator.PRIMARY);

			} catch (Exception e) {
				log.error("error al parser", e);

			}

			/*
			 * Antes de empezar a comparar, se buscan los # y &, que en realidad
			 * son "enies", pero que desde la carga inicial vienen mal
			 */
			if (StringUtils.isNotBlank(personaSugerida.getNombre())){
				personaSugerida.setNombre(personaSugerida.getNombre().replaceAll(
						"[#|&]", capitalNTilde));
			}
			
			if (StringUtils.isNotBlank(personaSugerida.getPrimerApellido())){
				personaSugerida.setPrimerApellido(personaSugerida
						.getPrimerApellido().replaceAll("[#|&]", capitalNTilde));
			}
			
			if (StringUtils.isNotBlank(personaSugerida.getSegundoApellido())){
				personaSugerida.setSegundoApellido(personaSugerida
						.getSegundoApellido().replaceAll("[#|&]", capitalNTilde));
			}

			// Validacion nombres de la persona
			int c1 = spCollator.compare(personaSugerida.getNombre(),
					personaSat.getNombre());
			this.log.debug("Se compara nombre " + personaSugerida.getNombre()
					+ " -- " + personaSat.getNombre());
			this.log.debug("Dato c1 : " + c1);

			// Validacion primer apellido (Opcional cuando se lee un patron, ya que solo el campo nombre tiene valor)
			int c2 = 0;
			if (StringUtils.isNotBlank(personaSugerida.getPrimerApellido()) 
					&& StringUtils.isNotBlank(personaSat.getPrimerApellido())) {
				c2 = spCollator.compare(personaSugerida.getPrimerApellido(),
						personaSat.getPrimerApellido());

				this.log.debug("Se compara primerApellido "
						+ personaSugerida.getPrimerApellido() + " -- "
						+ personaSat.getPrimerApellido());
			} else if (StringUtils.isBlank(personaSugerida.getPrimerApellido())
					&& StringUtils.isNotBlank(personaSat.getPrimerApellido())) {

				c2 = 1;

				this.log.debug("Se compara segundoApellido SIN_PRIMER_APELLIDO_IMSS(POSIBLE PATRON) "
						+ " -- " + personaSat.getPrimerApellido());
			} else if (StringUtils.isNotBlank(personaSugerida.getPrimerApellido())
					&& StringUtils.isBlank(personaSat.getPrimerApellido())) {
				
				c2 = 1;
				
				this.log.debug("Se compara segundoApellido "
						+ personaSugerida.getPrimerApellido() + " -- SIN_PRIMER_APELLIDO_SAT");
			} else {
				this.log.debug("Sin primer apellido");
			}

			this.log.debug("Dato c2 : " + c2);

			// Validacion del segundo apellido opcional
			int c3 = 0;
			if (StringUtils.isNotBlank(personaSugerida.getSegundoApellido()) 
					&& StringUtils.isNotBlank(personaSat.getSegundoApellido())) {
				
				c3 = spCollator.compare(personaSugerida.getSegundoApellido(),
						personaSat.getSegundoApellido());

				this.log.debug("Se compara segundoApellido "
						+ personaSugerida.getSegundoApellido() + " -- "
						+ personaSat.getSegundoApellido());
				
			} else if (StringUtils.isNotBlank(personaSugerida.getSegundoApellido())
					&& StringUtils.isBlank(personaSat.getSegundoApellido())) {
				
				c3 = 1;
				
				this.log.debug("Se compara segundoApellido "
						+ personaSugerida.getSegundoApellido() + " -- SIN_APELLIDO_SAT");
			} else if (StringUtils.isBlank(personaSugerida.getSegundoApellido())
					&& StringUtils.isNotBlank(personaSat.getSegundoApellido())) {
				
				c3 = 1;
				
				this.log.debug("Se compara segundoApellido SIN_APELLIDO_IMSS "
						+ " -- " + personaSat.getSegundoApellido());
			} else {
				this.log.debug("Sin segundo apellido");
			}
			this.log.debug("Dato c3 : " + c3);

			int sumatoria = Math.abs(c1) + Math.abs(c2) + Math.abs(c3);

			if (sumatoria == 0) {
				resultado = Boolean.TRUE;
			}
		} catch (Exception e) {
			this.log.error(e);
			throw new ErrorComparacionDatosSATException();
		}

		return resultado;
	}
	
	/**
	 * Contiene la l&oacute;gica del caso de uso DST - 10 Comparar Persona
	 * 
	 * @param fisica - entidad base
	 * @param entidad - entidad externa
	 * @param mensajes - mapara para regresar los mensajes que resulten de la comparaci&oacute;n
	 * 
	 */
	public ICADatosRespuesta compararDosPersonasFisicas(Fisica fisica,
			Fisica entidad, Map<String, String> mensajes,
			boolean indConsultaRENAPO, boolean indConsultaSAT)
			throws ErrorComparacionDatosRENAPOException {
		
		int cambiosRenapo = 0;
		int cambiosSat = 0;
		
		ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
		
		Map<String, CambioComparacionEnum> diferencias = new TreeMap<String, CambioComparacionEnum>();
		
		if (indConsultaRENAPO) {
			
			cambiosRenapo += this.compararPersonasServiceUtility.compararDatosBasicosPersonaFisica(fisica, entidad, diferencias);
			
			/*
			 * COMPARACION DE DOCUMENTOS PROBATORIOS, se toma como base el objeto
			 * entidad, ya que en el ICA el objeto entidad representa la persona
			 * fisica devuelta por RENAPO y para la comparación se debe tomar como
			 * base el documento probatorio que regresa RENAPO
			 */
			cambiosRenapo += this.compararPersonasServiceUtility.compararDocumentosProbatorios(fisica, entidad, diferencias);
		}
		
		//Datos SAT
		if (indConsultaSAT) {
			
			Map<String, Object> respuesta = this.compararPersonasServiceUtility.compararDatosBasicosSatPersonaFisica(fisica, entidad, diferencias);
			cambiosSat += (Integer) respuesta.get("COUNT_DIFF");
			
			cambiosSat += this.compararPersonasServiceUtility.compararDomicilioFiscal(fisica, entidad, diferencias);
			
			cambiosSat += this.compararPersonasServiceUtility.compararMediosFiscales(fisica, entidad, diferencias);
			
			/*
			 * Se compara la situacion SAT - De ambas entidades se obtiene la
			 * posición 0 de la lista, ya que: el SAT sólo devuelve una situación; y
			 * en el IMSS se debe de tener sólo una situación SAT activa.
			 */
			SituacionSAT situacionSatImss = fisica.getSituacionesSAT() == null || fisica.getSituacionesSAT().isEmpty() ? null : fisica.getSituacionesSAT().get(0);
			SituacionSAT situacionSatEntidad = entidad.getSituacionesSAT() == null || entidad.getSituacionesSAT().isEmpty() ? null : entidad.getSituacionesSAT().get(0);
			cambiosSat += this.compararPersonasServiceUtility.compararSituacionesSAT(situacionSatImss, situacionSatEntidad, diferencias);
		}
		
		if (cambiosRenapo > 0) {
			mensajes.put("MSG01-RENAPO","Existen diferencias en datos de RENAPO");
		}
		if (cambiosSat > 0) {
			mensajes.put("MSG02-SAT", "Existen diferencias en datos de SAT");
		}
		
		icaDatosRespuesta.setCambios(diferencias);
		icaDatosRespuesta.setTraza(mensajes);
		
		this.log.debug("Las diferencias encontradas son: ");
		MapUtils.verbosePrint(System.out, "DIFERENCIAS", diferencias);
		this.log.debug("Las mensajes generados por la comparacion de personas fisica son: " + mensajes);
		
		return icaDatosRespuesta;
	}
	
	
	/**
	 * Metodo que compara los datos estadisticos entre 2 personas fisicas que recibe una con las reglas 
	 * de datos de renapo done la fisicaAsegurado puede tener o no fecha de nacimiento y se compara por mes y año
	 * @param personaRenapo
	 * @param personaAsegurado
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	@Override
	public Boolean comparaDatosBasicosAseguradoMesAnioNacRENAPO(Fisica personaRenapo,
			Fisica personaAsegurado) throws ErrorComparacionDatosRENAPOException {
		
		Boolean resultado = Boolean.FALSE;

		String smallnTilde = new String("\u00F1");
		String capitalNTilde = new String("\u00D1");
		String tildeRara = new String("\u00A5");
		
		try {
			RuleBasedCollator spCollator = null;
			
			try {
				String traditionalSpanishRules = ("< a,A < b,B < c,C "
						+ "< ch, cH, Ch, CH " + "< d,D < e,E < f,F "
						+ "< g,G < h,H < i,I < j,J < k,K < l,L "
						+ "< ll, lL, Ll, LL " + "< m,M < n,N " + "< "
						+ smallnTilde + "," + capitalNTilde + ", " + tildeRara
						+ " " + "< o,O < p,P < q,Q < r,R "
						+ "< s,S < t,T < u,U < v,V < w,W < x,X " + "< y,Y < z,Z");
				spCollator = new RuleBasedCollator(traditionalSpanishRules);
				spCollator.setStrength(Collator.PRIMARY);

			} catch (Exception e) {
				log.error("error al parser", e);
			}

			/*
			 * Antes de empezar a comparar, se buscan los # y &, que en realidad
			 * son "enies", pero que desde la carga inicial vienen mal
			 */
			if (StringUtils.isNotBlank(personaAsegurado.getNombre())){
				personaAsegurado.setNombre(personaAsegurado.getNombre().replaceAll(
						"[#|&]", capitalNTilde));
			}
			
			if (StringUtils.isNotBlank(personaAsegurado.getPrimerApellido())){
				personaAsegurado.setPrimerApellido(personaAsegurado
						.getPrimerApellido().replaceAll("[#|&]", capitalNTilde));
			}
			
			if (StringUtils.isNotBlank(personaAsegurado.getSegundoApellido())){
				personaAsegurado.setSegundoApellido(personaAsegurado
						.getSegundoApellido().replaceAll("[#|&]", capitalNTilde));
			}

			// Validacion nombres de la persona
			int c1 = spCollator.compare(personaAsegurado.getNombre(),
					personaRenapo.getNombre());
			this.log.debug("Se compara nombre " + personaAsegurado.getNombre()
					+ " -- " + personaRenapo.getNombre());
			

			// Validacion primer apellido (Opcional cuando se lee un patron, ya que solo el campo nombre tiene valor)
			int c2 = 0;
			
			if (StringUtils.isNotBlank(personaAsegurado.getPrimerApellido()) 
					&& StringUtils.isNotBlank(personaRenapo.getPrimerApellido())) {
				c2 = spCollator.compare(personaAsegurado.getPrimerApellido(),
						personaRenapo.getPrimerApellido());
				this.log.debug("Se compara primerApellido "
						+ personaAsegurado.getPrimerApellido() + " -- "
						+ personaRenapo.getPrimerApellido());
			} else if (StringUtils.isBlank(personaAsegurado.getPrimerApellido())
					&& StringUtils.isNotBlank(personaRenapo.getPrimerApellido())) {
				c2 = 1;
				this.log.debug("El asegurado no trae primer apellido "+ 
				" -- " + personaRenapo.getPrimerApellido());
			} else if (StringUtils.isNotBlank(personaAsegurado.getPrimerApellido())
					&& StringUtils.isBlank(personaRenapo.getPrimerApellido())) {
					this.log.debug("La persona de renapo no trae primer apellido ");
				c2 = 1;
			} else {
				this.log.debug("Sin primer apellido");
			}

	
			// Validacion del segundo apellido opcional
			int c3 = 0;
			if (StringUtils.isNotBlank(personaAsegurado.getSegundoApellido()) 
					&& StringUtils.isNotBlank(personaRenapo.getSegundoApellido())) {
				
				c3 = spCollator.compare(personaAsegurado.getSegundoApellido(),
						personaRenapo.getSegundoApellido());

				this.log.debug("Se compara segundoApellido "
						+ personaAsegurado.getSegundoApellido() + " -- "
						+ personaRenapo.getSegundoApellido());
				
			} else if (StringUtils.isNotBlank(personaAsegurado.getSegundoApellido())
					&& StringUtils.isBlank(personaRenapo.getSegundoApellido())) {
				c3 = 1;
				this.log.debug("Se compara segundoApellido "
						+ personaAsegurado.getSegundoApellido() + " -- SIN_SEGUNDO_APELLIDO_RENAPO");
			} else if (StringUtils.isBlank(personaAsegurado.getSegundoApellido())
					&& StringUtils.isNotBlank(personaRenapo.getSegundoApellido())) {
				c3 = 1;
				this.log.debug("Se compara segundoApellido SIN_SEGUNDO_APELLIDO_IMSS "
						+ " -- " + personaRenapo.getSegundoApellido());
			} else {
				this.log.debug("Sin segundo apellido");
			}
	
			// Validacion de la fecha de nacimiento
			log.debug("los datos de fecha del asegurado son fecha:" + personaAsegurado.getFechaNacimiento() 
					+" año " + personaAsegurado.getAnioRegistroNac() +" mes " + personaAsegurado.getMesRegistroNac() + 
					" y renapo " + personaRenapo.getFechaNacimiento());
			int c4 = 0;
			if (personaRenapo.getFechaNacimiento() != null
					&& personaAsegurado.getFechaNacimiento() != null) {
				c4 = personaRenapo.getFechaNacimiento().compareTo(
						personaAsegurado.getFechaNacimiento());
			} 
			else if(personaAsegurado.getFechaNacimiento() == null && personaRenapo.getFechaNacimiento() != null  
					&& personaAsegurado.getMesRegistroNac() != null && personaAsegurado.getAnioRegistroNac() != null){
				GregorianCalendar calendarRenapo = new GregorianCalendar();
				calendarRenapo.setTime(personaRenapo.getFechaNacimiento());
				SimpleDateFormat frmYYYY = new SimpleDateFormat("yy");
				SimpleDateFormat frmMM = new SimpleDateFormat("MM");
				int anioNac = Integer.parseInt(frmYYYY.format(personaRenapo.getFechaNacimiento()));
				int mesNac = Integer.parseInt(frmMM.format(personaRenapo.getFechaNacimiento()));
				this.log.debug("el año quedo como "+ anioNac +" y mes " + mesNac );
				if(anioNac != personaAsegurado.getAnioRegistroNac().intValue()  ||
						mesNac != personaAsegurado.getMesRegistroNac().intValue()){
					log.debug("no coinciden el mes y el año con renapo");
					c4=1;
				}
			}else{
				this.log.warn("Alguna de las fechas de nacimiento es nula, por lo tanto, se encuentra diferencia");
				c4 = 1;
			}
			

			// Valida el sexo
			int c5 = 0;
			if (personaRenapo.getSexo() != null
					&& personaRenapo.getSexo().getIdSexo() != null
					&& personaAsegurado.getSexo() != null
					&& personaAsegurado.getSexo().getIdSexo() != null) {

				c5 = personaRenapo.getSexo().getIdSexo()
						.compareTo(personaAsegurado.getSexo().getIdSexo());

				this.log.debug("Se compara sexo "
						+ personaAsegurado.getSexo().getIdSexo() + " -- "
						+ personaRenapo.getSexo().getIdSexo());
			} else {
				this.log.warn("Alguno de los sexos es nulo, por lo tanto, se encuentra diferencia");
				c5 = 1;
			}
			this.log.debug("Dato c5 : " + c5);

			// Valida entidad nacimiento
			int c6 = 0;
			if (personaRenapo.getLugarNacimiento() != null
					&& StringUtils.isNotBlank(personaRenapo
							.getLugarNacimiento().getClave())
					&& personaAsegurado.getLugarNacimiento() != null
					&& StringUtils.isNotBlank(personaAsegurado
							.getLugarNacimiento().getClave())) {

				c6 = personaRenapo
						.getLugarNacimiento()
						.getClave()
						.compareToIgnoreCase(
								personaAsegurado.getLugarNacimiento().getClave());

				this.log.debug("Se compara lugarNacimiento "
						+ personaAsegurado.getLugarNacimiento().getClave()
						+ " -- "
						+ personaRenapo.getLugarNacimiento().getClave());
			} else {
				this.log.warn("Alguno de los lugares de nacimiento es nulo, por lo tanto, se encuentra diferencia");
				c6 = 1;
			}
			this.log.debug("Dato c6 : " + c6);

			/*
			 * Comprobar resultado (se utiliza valor absoluto para evitar suma
			 * de valores negativos y positivos con el mismo valor)
			 */
			int sumatoria = Math.abs(c1) + Math.abs(c2) + Math.abs(c3)
					+ Math.abs(c4) + Math.abs(c5) + Math.abs(c6);

			if (sumatoria == 0) {
				resultado = Boolean.TRUE;
			}
		} catch (Exception e) {
			this.log.error(e);
			throw new ErrorComparacionDatosRENAPOException();
		}
		return resultado;
	}


}
