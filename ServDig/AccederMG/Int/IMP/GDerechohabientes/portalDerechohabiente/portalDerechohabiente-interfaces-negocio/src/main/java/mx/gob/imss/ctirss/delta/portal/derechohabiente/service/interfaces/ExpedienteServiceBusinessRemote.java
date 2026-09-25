package mx.gob.imss.ctirss.delta.portal.derechohabiente.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: IMSS Digital
 * @Archivo: TramiteServiceBusinessRemote.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.interfaces
 * @Fecha: 17:55:33
 */
@Remote
public interface ExpedienteServiceBusinessRemote {

	DatosSalidaPaginador<TipoTramite> listarTipoTramitesDummy(
			DatosEntradaPaginador<TipoTramite> input, FiltroSolicitud filtro);

}
