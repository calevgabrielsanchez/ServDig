package mx.gob.imss.cit.cda.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BitacoraMovimientosSINDOEntity extends AbstractServiceEntity
        implements BitacoraMovimientosSINDOLocal {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, List<BitacoraMovimientoSindoCDA>> obtenerBitacoraMovimientos(
            Date fechaMovimientos) {
        // leer tabla y generar cadenas
        Map<String, List<BitacoraMovimientoSindoCDA>> bitacora = new HashMap<String, List<BitacoraMovimientoSindoCDA>>();
        StringBuffer jpaQuery = new StringBuffer();
        Query query = null;
        List<Object[]> resultList = null;
        logger.debug("obtener bitacora del dia {}", fechaMovimientos);

        jpaQuery.append("SELECT DISTINCT ");
        jpaQuery.append("bitacora.REF_Observacion, ");
        jpaQuery.append("bitacora.REF_FOLIO_SOLICITUD, ");
        jpaQuery.append("bitacora.NUM_NSS, ");
        jpaQuery.append("bitacora.CVE_ID_ORIGEN_RES_MVTO, ");
        jpaQuery.append("bitacora.CVE_ID_RESULTADO ");
        jpaQuery.append("FROM DIT_BITACORA_PROC_SINDO_CDA bitacora ");
        jpaQuery.append("WHERE ");
        jpaQuery.append("bitacora.FEC_REGISTRO_ALTA >= :fechaMovimientos ");
        jpaQuery.append("GROUP BY ");
        jpaQuery.append("bitacora.REF_FOLIO_SOLICITUD, ");
        jpaQuery.append("bitacora.CVE_ID_ORIGEN_RES_MVTO, ");
        jpaQuery.append("bitacora.NUM_NSS, ");
        jpaQuery.append("bitacora.CVE_ID_RESULTADO, ");
        jpaQuery.append("bitacora.REF_Observacion ");
        jpaQuery.append("ORDER BY ");
        jpaQuery.append("bitacora.REF_FOLIO_SOLICITUD, ");
        jpaQuery.append("bitacora.REF_Observacion ");
        jpaQuery.append("DESC");

        logger.debug("bitacora query [{}]", jpaQuery.toString());
        query = this.em.createNativeQuery(jpaQuery.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaMovimientos);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        query.setParameter("fechaMovimientos", calendar.getTime());
        resultList = query.getResultList();
        for (Object[] result : resultList) {
            BitacoraMovimientoSindoCDA movimiento = getMovimientoSindoCDA(result);
            if (bitacora.containsKey(result[1])) {
                bitacora.get(result[1]).add(movimiento);
            } else {
                List<BitacoraMovimientoSindoCDA> registro = new ArrayList<BitacoraMovimientoSindoCDA>();
                registro.add(movimiento);
                bitacora.put(result[1].toString(), registro);
            }
        }
        return bitacora;
    }

    private BitacoraMovimientoSindoCDA getMovimientoSindoCDA(Object[] result) {

        BitacoraMovimientoSindoCDA movimiento = new BitacoraMovimientoSindoCDA();

        movimiento.setFolio(result[1].toString());
        movimiento.setNss(result[2].toString());
        movimiento.setObservacion(result[0] != null ? result[0].toString()
                : null);
        movimiento.setOrigen(Integer.valueOf(result[3].toString()));
        movimiento.setResultado(result[4].toString());

        return movimiento;
    }
    

    @SuppressWarnings("unchecked")
       @Override
       public Map<String, List<BitacoraMovimientoSindoCDA>> obtenerBitacoraMovimientosProceadosYErroresSINDO(
               Date fechaMovimientos){
       	
       	 Map<String, List<BitacoraMovimientoSindoCDA>> bitacora = new HashMap<String, List<BitacoraMovimientoSindoCDA>>();
            StringBuffer jpaQuery = new StringBuffer();
            Query query = null;
            List<Object[]> resultList = null;
            logger.debug("obtener bitacora del dia {}", fechaMovimientos);

            jpaQuery.append("select distinct null as REF_OBSERVACION, sol.ref_folio, nss.NUM_NSS, '1' as CVE_ID_ORIGEN_RES_MVTO, '000' as CVE_ID_RESULTADO  ");
            jpaQuery.append("from dit_tramite tram, dit_solicitud sol, DIT_CORRECCION_DATOS_ASEG cor, DIT_DETALLE_NSS_CDA nss ");
            jpaQuery.append("where trunc(tram.fec_registro_actualizado) = trunc(:fechaMovimientos) ");
            jpaQuery.append("and  tram.CVE_ID_TIPO_TRAMITE = 139 ");
            jpaQuery.append("and tram.CVE_ID_ESTADO_TRAMITE in (88) ");
            jpaQuery.append("and sol.CVE_ID_SOLICITUD = tram.CVE_ID_SOLICITUD ");
            jpaQuery.append("and tram.CVE_ID_TRAMITE = cor.CVE_ID_TRAMITE ");
            jpaQuery.append("and cor.CVE_ID_CORRECCION_DATOS_ASEG = nss.CVE_ID_CORRECCION_DATOS_ASEG ");
            jpaQuery.append("and nss.CVE_ID_TIPO_NSS = 1 ");
            jpaQuery.append("union ");
            jpaQuery.append("select distinct bit.REF_OBSERVACION, sol.REF_FOLIO, bit.NUM_NSS, bit.CVE_ID_ORIGEN_RES_MVTO, bit.CVE_ID_RESULTADO ");
            jpaQuery.append("from DIT_BITACORA_PROC_MOV06_CDA bit, DIT_BIT_FLUJO_ARCH_MOV06CDA flu,  ");
            jpaQuery.append("    dit_tramite tram, dit_solicitud sol, DIT_CORRECCION_DATOS_ASEG cor, DIT_DETALLE_NSS_CDA nss, ");
            jpaQuery.append("    DIT_MOV_ACLARACION_NSS_CDA mov ");
            jpaQuery.append("where bit.CVE_ID_TRAMITE = flu.CVE_ID_TRAMITE ");
            jpaQuery.append("and sol.CVE_ID_SOLICITUD = tram.CVE_ID_SOLICITUD ");
            jpaQuery.append("and tram.CVE_ID_TRAMITE = cor.CVE_ID_TRAMITE ");
            jpaQuery.append("and cor.CVE_ID_CORRECCION_DATOS_ASEG = nss.CVE_ID_CORRECCION_DATOS_ASEG ");
            jpaQuery.append("and nss.CVE_ID_DETALLE_NSS_CDA = mov.CVE_ID_DETALLE_NSS_CDA ");
            jpaQuery.append("and mov.CVE_ID_MOV_ACLARACION_NSS = flu.CVE_ID_TRAMITE ");
            jpaQuery.append("and flu.IND_VALIDA =1 ");
            jpaQuery.append("and flu.REF_ESTADO_TRAMITE = 85 ");
            jpaQuery.append("and trunc(flu.FEC_REGISTRO_ACTUALIZADO) = trunc (:fechaMovimientos) ");
            jpaQuery.append("and bit.CVE_ID_RESULTADO <> '000' ");
            jpaQuery.append("and trunc(bit.FEC_REGISTRO_ALTA) = trunc(:fechaMovimientos) ");
            jpaQuery.append("and tram.CVE_ID_ESTADO_TRAMITE = 85 ");
            jpaQuery.append("and tram.CVE_ID_TIPO_TRAMITE = 139 ");
            jpaQuery.append("union ");
            jpaQuery.append("select distinct bit.REF_OBSERVACION, sol.REF_FOLIO, bit.NUM_NSS, bit.CVE_ID_ORIGEN_RES_MVTO, bit.CVE_ID_RESULTADO ");
            jpaQuery.append("from DIT_BITACORA_PROC_MOV05_CDA bit, DIT_BIT_FLUJO_ARCH_MOV05CDA flu,  ");
            jpaQuery.append("    dit_tramite tram, dit_solicitud sol, DIT_CORRECCION_DATOS_ASEG cor, DIT_DETALLE_NSS_CDA nss, ");
            jpaQuery.append("    DIT_MOV_ACLARACION_NSS_CDA mov ");
            jpaQuery.append("where bit.CVE_ID_TRAMITE = flu.CVE_ID_TRAMITE ");
            jpaQuery.append("and sol.CVE_ID_SOLICITUD = tram.CVE_ID_SOLICITUD ");
            jpaQuery.append("and tram.CVE_ID_TRAMITE = cor.CVE_ID_TRAMITE ");
            jpaQuery.append("and cor.CVE_ID_CORRECCION_DATOS_ASEG = nss.CVE_ID_CORRECCION_DATOS_ASEG ");
            jpaQuery.append("and nss.CVE_ID_DETALLE_NSS_CDA = mov.CVE_ID_DETALLE_NSS_CDA ");
            jpaQuery.append("and mov.CVE_ID_MOV_ACLARACION_NSS = flu.CVE_ID_TRAMITE ");
            jpaQuery.append("and flu.IND_VALIDA =1 ");
            jpaQuery.append("and flu.REF_ESTADO_TRAMITE = 85 ");
            jpaQuery.append("and trunc(flu.FEC_REGISTRO_ACTUALIZADO) = trunc (:fechaMovimientos) ");
            jpaQuery.append("and bit.CVE_ID_RESULTADO <> '000' ");
            jpaQuery.append("and trunc(bit.FEC_REGISTRO_ALTA) = trunc(:fechaMovimientos) ");
            jpaQuery.append("and tram.CVE_ID_ESTADO_TRAMITE = 85 ");
            jpaQuery.append("and tram.CVE_ID_TIPO_TRAMITE = 139 ");
            jpaQuery.append("union ");
            jpaQuery.append("select  distinct bit.REF_OBSERVACION, sol.REF_FOLIO, bit.NSS_ORIG|| bit.DV_NSS_ORIG , substr(bit.NOMBRE_ARCHIVO, 20,1 ), bit.IDENTIF_ERROR ");
            jpaQuery.append("from DIT_BITACORA_PROC_MOVCI_CDA bit, DIT_BIT_FLUJO_ARCH_MOVCICDA flu,  ");
            jpaQuery.append("    dit_tramite tram, dit_solicitud sol, DIT_CORRECCION_DATOS_ASEG cor, DIT_DETALLE_NSS_CDA nss, ");
            jpaQuery.append("    DIT_MOV_ACLARACION_NSS_CDA mov, DIT_CORRECCION_CTA_IND_CDA cta ");
            jpaQuery.append("where bit.CVE_ID_TRAMITE = flu.CVE_ID_TRAMITE ");
            jpaQuery.append("and sol.CVE_ID_SOLICITUD = tram.CVE_ID_SOLICITUD ");
            jpaQuery.append("and tram.CVE_ID_TRAMITE = cor.CVE_ID_TRAMITE ");
            jpaQuery.append("and cor.CVE_ID_CORRECCION_DATOS_ASEG = nss.CVE_ID_CORRECCION_DATOS_ASEG ");
            jpaQuery.append("and nss.CVE_ID_DETALLE_NSS_CDA = mov.CVE_ID_DETALLE_NSS_CDA ");
            jpaQuery.append("and mov.CVE_ID_MOV_ACLARACION_NSS = cta.CVE_ID_MOV_ACLARACION_NSS ");
            jpaQuery.append("and cta.CVE_ID_CORRECCION_CTA_IND_CDA = flu.CVE_ID_TRAMITE ");
            jpaQuery.append("and flu.IND_VALIDA =1 ");
            jpaQuery.append("and flu.REF_ESTADO_TRAMITE = 85 ");
            jpaQuery.append("and trunc(flu.FEC_REGISTRO_ACTUALIZADO) = trunc (:fechaMovimientos) ");
            jpaQuery.append("and bit.IDENTIF_ERROR <> '000' ");
            jpaQuery.append("and trunc(bit.FEC_REGISTRO_ALTA) = trunc(:fechaMovimientos) ");
            jpaQuery.append("and tram.CVE_ID_ESTADO_TRAMITE = 85 ");
            jpaQuery.append("and tram.CVE_ID_TIPO_TRAMITE = 139 ");
            jpaQuery.append("order by REF_FOLIO, REF_OBSERVACION desc ");

            logger.debug("bitacora query [{}]", jpaQuery.toString());
            query = this.em.createNativeQuery(jpaQuery.toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaMovimientos);
            query.setParameter("fechaMovimientos", calendar.getTime());
            resultList = query.getResultList();
            for (Object[] result : resultList) {
                BitacoraMovimientoSindoCDA movimiento = getMovimientoSindoCDA(result);
                if (bitacora.containsKey(result[1])) {
                    bitacora.get(result[1]).add(movimiento);
                } else {
                    List<BitacoraMovimientoSindoCDA> registro = new ArrayList<BitacoraMovimientoSindoCDA>();
                    registro.add(movimiento);
                    bitacora.put(result[1].toString(), registro);
                }
            }
            return bitacora;

       }

}
