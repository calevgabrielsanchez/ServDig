package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

@Remote
public interface PreguntaServiceRemote {
	
	void dummy();
	/*
	public Object generaCuestionario(Long idPersona,Long idSolicitud,Long idParentesco);
	public void updateCuestionario(BigInteger calificacion,Long idTramite,Usuario usuario)  throws DerechohabientesBusinessException;
	List<RespuestaCuestionario> findCuestionario(Long idSolicitud,Long idTramite,Long idPersona) ;
	
	List<Pregunta> findPreguntas(Long idSolicitud,Long idTramite,Long idPersona) throws DerechohabientesBusinessException;
	void saveCuestionario(Long idSolicitud,Long idTramite,Long idPersona,Long idParentesco)  throws DerechohabientesBusinessException;
	*/
}
