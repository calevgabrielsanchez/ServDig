/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: IMSS Digital
 * @Archivo: TramiteServiceUtility.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.utility
 * @Fecha: 09:42:23
 */
@Stateless
public class ExpedienteServiceUtility extends AbstractServiceUtility implements
        ExpedienteServiceUtilityLocal {

    @Override
    public TipoTramite convertirEntidadAModelo(DicTipoTramite entidad) {

        TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(entidad.getCveIdTipoTramite().intValue());
        tipoTramite.setDescripcion(entidad.getDesTipoTramite());

        return tipoTramite;
    }

}

