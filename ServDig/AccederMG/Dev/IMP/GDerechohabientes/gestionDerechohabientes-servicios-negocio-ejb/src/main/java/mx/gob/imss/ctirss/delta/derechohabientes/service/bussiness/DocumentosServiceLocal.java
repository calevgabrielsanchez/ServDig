package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface DocumentosServiceLocal {

	Object getAcuseRegistroMoviles(Solicitud solicitud) throws Exception;
	Object getAcuseCambioClinicaMoviles(Solicitud solicitud) throws Exception;
    Object generarComprobanteTramiteARCO(Solicitud solicitud) throws Exception;
}
