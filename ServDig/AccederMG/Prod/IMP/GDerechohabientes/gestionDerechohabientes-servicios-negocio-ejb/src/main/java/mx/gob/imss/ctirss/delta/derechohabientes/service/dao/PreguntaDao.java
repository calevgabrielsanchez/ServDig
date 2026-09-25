package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

@Stateless(name="preguntaDao", mappedName="preguntaDao")
public class PreguntaDao  implements PreguntaDaoLocal{
	
	private static final Logger logger = Logger.getLogger(PreguntaDao.class);

	@Override
	public void dummy() {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
	@PersistenceContext(unitName = "deltaPersistenceUnit")
	EntityManager em;
	
	@EJB
	private transient RespuestaCuestionarioParserLocal respuestaCuestionarioParser;

	@SuppressWarnings("unchecked")
	@Override
	public List<Pregunta> findPreguntas(Long idCategoriaPregunta) throws DerechohabientesBusinessException,Exception {
		List<Pregunta> preguntas = null;
		try {
			Query query= em.createNamedQuery("findPreguntasCategoria");
			query.setParameter("idCategoriaPregunta", idCategoriaPregunta);
			List<DitPregunta> ditPreguntas=query.getResultList();
			preguntas = PreguntaParser.persisToModelList(ditPreguntas);
		} catch (Exception e) {
			logger.error("Error -findPreguntas", e);
			throw e;
		}
		
		
		return preguntas;
	}
	
	@Override
	public List<Respuesta> findRespuestasCuestionario(Long idCategoriaPregunta) throws Exception {
		List<Respuesta> respuestas =null;
		try {
			Query query= em.createNamedQuery("findRespuestasCuestionarioCategoria");
			query.setParameter("idCategoriaPregunta", idCategoriaPregunta);
			//List<DitRespuesta> ditRespuestas = query.getResultList();
			
			// RespuestaParser.persisToModelList(ditRespuestas);
		} catch (Exception e) {
			logger.error("Error - findRespuestasCuestionario", e);
			throw e;
		}
				
		return respuestas;
	}
	
	
	
	@Override
	public void saveRespuestaCuestionario(RespuestaCuestionario respuesta) throws DerechohabientesBusinessException, Exception {
		DitRespuestaCuestionario dato = respuestaCuestionarioParser.modelToPersist(respuesta);
		
		try {
			em.persist(dato);
			em.flush();
		} catch (Exception e) {
			logger.error("saveRespuestaCuestionario", e);
			throw e;
		}
		
		
	}

	@Override
	public void updateCuestionario(RespuestaCuestionario respuesta) throws DerechohabientesBusinessException,Exception {
		DitRespuestaCuestionario ditRespuesta =  respuestaCuestionarioParser.modelToPersist(respuesta);
		try {
			em.merge(ditRespuesta);
		} catch (Exception e) {
			logger.error("updateCuestionario", e);
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RespuestaCuestionario> findCuestionario(Long idTramite) throws DerechohabientesBusinessException,Exception{
		List<RespuestaCuestionario> datos = null;
		try {
			Query query = em.createNamedQuery("findCuestionario");
			query.setParameter("idTramite", idTramite);
			List<DitRespuestaCuestionario> base =query.getResultList();
			datos = respuestaCuestionarioParser.persisToModelList(base);
		} catch (Exception e) {
			logger.error("findCuestionario", e);
			throw e;
		}
			
		return datos;
	}
	*/ 
	
}
