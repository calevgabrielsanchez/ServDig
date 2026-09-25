package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitReactivacionDerechohab;

@Local
public interface ReactivacionParserLocal {

	DitReactivacionDerechohab tramiteReactivacionToDitReactivacion(TramiteReactivacionDerechohab tramite);
}
