package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PreguntaServiceRemote;

@Stateless(name="preguntaService",mappedName="preguntaService")
public class PreguntaService implements PreguntaServiceRemote {

	@Override
	public void dummy() {
		// TODO Auto-generated method stub
		
	}

	
	/*
	@EJB PreguntaDaoLocal preguntaDao;
	@EJB private ManejadorReportesLocal manejadorReportes;
	@EJB private DerechohabienteDaoLocal derechohabienteDao;
	@EJB PersonaInteresadaSolDaoLocal personaInteresadaDao;
	@EJB TramitePersonaFisicaDaoLocal tramiteDao;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness") SolicitudBusinessRemote solicitudBusinessRemote;
	private static final Logger log = Logger.getLogger(PreguntaService.class);
  
	
	@Override
	public void saveCuestionario(Long idSolicitud,Long idTramite,Long idPersona,Long idParentesco) throws DerechohabientesBusinessException {
		boolean tieneCuestionario= false;
		PersonaInteresadaSolicitud personaInteresada = null;
		try {
			personaInteresadaDao.getPersonaInteresada(idPersona, idSolicitud);
		}catch(Exception e) {
			log.error("", e);
		}
		/*Habilitar para validar si tiene un cuestionario asociado
		if(cuestionario!=null && cuestionario.size()>0){
			throw new DerechohabientesBusinessException("");
		}*/
		
	/*
	     List<Pregunta> datos= null;
	     if(idParentesco == ParentescoEnum.PADRES.getId()){
	    	 try {
				datos = preguntaDao.findPreguntas(CategoriaPreguntaEnum.ASCENDIENTE.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("No se encontraron preguntas para padres", e);
			}
	     }
	     else
	    	 if(idParentesco == ParentescoEnum.CONCUBINARIO.getId()){
	    		 try {
					datos = preguntaDao.findPreguntas(CategoriaPreguntaEnum.CONCUBINARIO.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("No se encontraron preguntas para concubina", e);
				}
	    	 }
	     	
			RespuestaCuestionario datoRC = null;
					
			for (Pregunta ditPregunta : datos) {
				datoRC = new RespuestaCuestionario();
				
				datoRC.setPregunta(ditPregunta);
			
				datoRC.setPersona(new Fisica());
				datoRC.getPersona().setIdPersona(idPersona);
				datoRC.setRegistroDerechohabiente(new TramiteRegistroDerechohabiente());
				datoRC.getRegistroDerechohabiente().setFisica(datoRC.getPersona());
				/*
				datoRC.setTramite(new Tramite());
				datoRC.getTramite().setTramiteId(idTramite);*/
				/*datoRC.getRegistroDerechohabiente().getTramite().setSolicitud(new Solicitud());
				datoRC.getRegistroDerechohabiente().getTramite().getSolicitud().setIdSolicitud(idSolicitud);
				datoRC.getRegistroDerechohabiente().getTramite().setTipoTramite(new TipoTramite());
				datoRC.getRegistroDerechohabiente().getTramite().getTipoTramite().setIdTipoTramite(TipoTramiteEnum.REGISTRO_DERECHOHABIENTE.getCodigo().longValue());
				*/
	/*
				datoRC.setPersonaInteresadaSol(personaInteresada);
				
				if(!tieneCuestionario){
					try {
						preguntaDao.saveRespuestaCuestionario(datoRC);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("No se pudieron guardar las respuestas", e);
					}
				}
			}
		
	}
	
	
	@Override
	public Object generaCuestionario(Long idPersona,Long idSolicitud,Long idParentesco) {
	   
	     List<String> plantillas= new ArrayList<String>(); 
	     List<Pregunta> datos= null;
	     if(idParentesco == ParentescoEnum.PADRES.getId()){
	    	 try {
				datos = preguntaDao.findPreguntas(CategoriaPreguntaEnum.ASCENDIENTE.getId());
			} catch (DerechohabientesBusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 plantillas.add("CuestionarioAseguradoPensionado.jrxml");
	    	 plantillas.add("padre.jrxml");
	     }
	     else
	    	 if(idParentesco == ParentescoEnum.CONCUBINARIO.getId()){
	    		 try {
					datos = preguntaDao.findPreguntas(CategoriaPreguntaEnum.CONCUBINARIO.getId());
				} catch (DerechohabientesBusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		 plantillas.add("CuestionarioAseguradoConcubinario.jrxml");
		    	 plantillas.add("CuestionarioBeneficiarioConcubina.jrxml");
	    	 }
	     	
			List<PreguntaReporte> datosReporte = new ArrayList<PreguntaReporte>();
			PreguntaReporte datoReporte = null;
			int numRespuesta=1;
			int i=1;
					
			for (Pregunta ditPregunta : datos) {
				numRespuesta=1;
				for (Respuesta respuesta : ditPregunta.getRespuestas()) {
					datoReporte = new PreguntaReporte();
					datoReporte.setPreguntaPrimeraPersona(ditPregunta.getPreguntaPrimeraPersona());
					datoReporte.setPreguntaTerceraPersona(ditPregunta.getPreguntaTerceraPersona());
					datoReporte.setNumPregunta(""+i);
					datoReporte.setRespuesta(numRespuesta+".-"+respuesta.getDescripcion());
					datosReporte.add(datoReporte);
					numRespuesta++;
				}
				
				if(ditPregunta.getRespuestas().size()<9){
					colocarRespuestasBlanco(datosReporte,ditPregunta.getRespuestas().size(),ditPregunta.getPreguntaPrimeraPersona(),ditPregunta.getPreguntaTerceraPersona());
				}
				i++;
			}
			
			Map<String,Object> parametros = new  HashMap<String, Object>();
			ByteArrayOutputStream repo=null;
			try {
				repo=manejadorReportes.ejecutaReportePlantillas(parametros, datosReporte, plantillas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return repo.toByteArray();
	}

	public void colocarRespuestasBlanco(List<PreguntaReporte> datos,int cantidad,String pregunta1,String pregunta2){
		PreguntaReporte datoReporte=null; 
		
		for (int i = cantidad; i <9; i++) {
			datoReporte = new PreguntaReporte();
			datoReporte.setPreguntaPrimeraPersona(pregunta1);
			datoReporte.setPreguntaTerceraPersona(pregunta2);
			datos.add(datoReporte);
		}		
	}

	@Override
	public void updateCuestionario(BigInteger calificacion,Long idTramite,Usuario usuario) throws DerechohabientesBusinessException {
		//Se actualiza el registro derechohbaiente, colocando la calificacion obteniada en el cuestionario
		
		//Se actualiza el estado del tramite
		try {
			log.debug("Id del tramite es: " + idTramite);
			TramiteRegistroDerechohabiente registro = derechohabienteDao.getRegistroDerechohabiente(idTramite);
			log.debug("El tramite de registro es: " + registro);
			registro.setEvaluacionCuestionario(calificacion); 
			registro.setFechaRegistroActualizacion(new Date());
			registro.setUsuario(usuario);
			
			log.debug("La razon de registro de es : " + registro.getRazonRegistro().getIdRazonRegistro());
			derechohabienteDao.updateRegistroDerechohabiente(registro);
			
			Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteRegistroDerechohabiente){
					TramiteRegistroDerechohabiente tramiteRegistro = (TramiteRegistroDerechohabiente) tramite;
					tramiteRegistro.setEstadoTramite(new EstadoTramite());
					tramiteRegistro.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getValor());
					tramiteRegistro.setEvaluacionCuestionario(calificacion);
					tramiteRegistro.setUsuario(usuario);
					tramiteRegistro.setFechaRegistroActualizacion(new Date());
				}
				
			}
			
			solicitudBusinessRemote.actualizarEstados(solicitud);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public List<RespuestaCuestionario> findCuestionario(Long idSolicitud,Long idTramite,Long idPersona) {
		List<RespuestaCuestionario> cuestionario = null;
		try {
			cuestionario = preguntaDao.findCuestionario(idTramite);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cuestionario;
	}
	
	@Override
	public List<Pregunta> findPreguntas(Long idSolicitud,Long idTramite,Long idPersona) throws DerechohabientesBusinessException {
		/*
		List<RespuestaCuestionario> datos = null;
		try {
			datos = preguntaDao.findCuestionario(idTramite);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(datos==null || datos.size()==0){
			throw new DerechohabientesBusinessException("msg05"); 
		}
		
		
		List<Pregunta> preguntas = new ArrayList<Pregunta>();
		for (RespuestaCuestionario respuestaCuestionario : datos) {
			respuestaCuestionario.getPregunta().setCveIdCuestionario(respuestaCuestionario.getCveIdCuestionario());
			preguntas.add(respuestaCuestionario.getPregunta());
		}
		
		
		return preguntas;
		
		return null;
	}
*/
}
