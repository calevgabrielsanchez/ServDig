package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;

@Remote
public interface ReporteBusinessRemote {
    byte[] getComprobanteOperacion(Long folioSolicitud, String sNombreUsuarioReporte) throws SolicitudNoEncontradaException;
}
