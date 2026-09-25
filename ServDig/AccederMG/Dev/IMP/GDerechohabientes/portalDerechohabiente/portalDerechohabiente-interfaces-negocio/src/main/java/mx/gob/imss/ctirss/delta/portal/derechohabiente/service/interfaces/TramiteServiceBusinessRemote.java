package mx.gob.imss.ctirss.delta.portal.derechohabiente.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: IMSS Digital
 * @Archivo: TramiteServiceBusinessRemote.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.interfaces
 * @Fecha: 17:55:33
 */
@Remote
public interface TramiteServiceBusinessRemote {

	/**
	 * Obtiene los tramites asociados a la <Fisica> persona por medio del
	 * identificador.
	 * 
	 * @param persona
	 * @return
	 * @throws PersonaNoEncontradaException
	 */
	public List<Tramite> obtenerTramitesDePersona(Fisica persona)
			throws PersonaNoEncontradaException;

}
