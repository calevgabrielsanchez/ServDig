/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.ExpedienteServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: delta
 * @Archivo: TramiteServiceEntity.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.entity
 * @Fecha: 17:57:55
 */
@Stateless
public class ExpedienteServiceEntity extends AbstractServiceEntity implements
        ExpedienteServiceEntityLocal {

    @EJB
    private ExpedienteServiceUtilityLocal expedienteServiceUtility;

    @SuppressWarnings("unchecked")
    @Override
    public DatosSalidaPaginador<TipoTramite> obtenerCatalogoTipoTramite(
            DatosEntradaPaginador<TipoTramite> input, FiltroSolicitud filtro) {

        Integer totalResultados = null;
        Query queryCount = this.em.createQuery("select count(*) from DicTipoTramite");
        totalResultados = ((Long) queryCount.getSingleResult()).intValue();

        Query query = this.em.createQuery("from DicTipoTramite");
        query.setFirstResult(input.getiDisplayStart());
        query.setMaxResults(input.getiDisplayLength());

        List<DicTipoTramite> results = query.getResultList();

        List<TipoTramite> tiposTramite = new ArrayList<TipoTramite>(results.size());
        for (DicTipoTramite entity : results) {
            tiposTramite.add(this.expedienteServiceUtility.convertirEntidadAModelo(entity));
        }

        DatosSalidaPaginador<TipoTramite> output = new DatosSalidaPaginador<TipoTramite>();
        output.setAaData(tiposTramite);
        output.setiTotalRecords(totalResultados == null ? 0 : totalResultados);
        output.setiTotalDisplayRecords(totalResultados == null ? 0 : totalResultados);

        return output;
    }

}
