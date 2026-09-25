package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;

@Local
public interface SolicitudCitaEntityLocal {
	
	
	/**
	 * Metodo que actualiza las solicitudes que se encuentren en es un asentamiento poblacional a una nueva unidad medica
	 * @param asentamiento
	 * @param medicoEnTurno
	 * @param fechaCita
	 * @return
	 */
	Long updateCitaSolicitudesPorCambioMasivoClinica(Asentamiento asentamiento,MedicoEnTurno medicoEnTurno,Date fechaCita);
	
	/**
	 * Metodo que cuenta el numero de citas asociadas a una UMF
	 * @param cita
	 * @return
	 * @throws Exception
	 */
	Long countSolicitudesFechaTurnoUmf(CitaSolicitud cita) throws Exception;

}
