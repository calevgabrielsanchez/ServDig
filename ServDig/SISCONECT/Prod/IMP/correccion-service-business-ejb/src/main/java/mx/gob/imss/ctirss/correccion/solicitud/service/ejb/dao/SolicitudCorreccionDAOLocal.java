package mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.SacMunicipio;


/**
 * 
 * @author Jorge Castorena
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/10/2011
 */

@Local
public interface SolicitudCorreccionDAOLocal<T extends AbstractModel> extends ISolicitudCorrecionDAO<T>{


}
