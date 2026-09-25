package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

@Local
public interface SolicitudParserLocal {
	DitSolicitud modelToPersist(Solicitud entrada) throws DerechohabientesBusinessException;
	Solicitud persisToModelSimple(DitSolicitud entrada) throws DerechohabientesBusinessException;
	List<Solicitud> PersistToModelList(List<DitSolicitud> entradaList) throws DerechohabientesBusinessException;
	List<Solicitud> PersistToModelListPartial(List<DitSolicitud> entradaList) throws DerechohabientesBusinessException;
	List<SolicitudNssDto> PersistToModelListPartialNss(List<DitSolicitud> entradaList) throws DerechohabientesBusinessException;
	SolicitudNssDto persisToModelPartialNss(DitSolicitud entrada) throws DerechohabientesBusinessException;
	Solicitud persisToModelPartial(DitSolicitud entrada) throws DerechohabientesBusinessException;
	Solicitud persisToModel(DitSolicitud entrada) throws DerechohabientesBusinessException;
}
