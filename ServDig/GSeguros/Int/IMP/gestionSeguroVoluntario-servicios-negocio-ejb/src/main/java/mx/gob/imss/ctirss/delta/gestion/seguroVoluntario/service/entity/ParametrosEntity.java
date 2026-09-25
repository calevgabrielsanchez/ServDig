/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "parametrosEntity", mappedName = "parametrosEntity")
public class ParametrosEntity implements ParametrosEntityLocal {

    private static final String CLAVE_ENTIDAD_CDMX = "09";

    private static final String CLAVE_MUNICIPIO_CDMX = "015";

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosEntity.class);

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;
    
    /**
     * Obtieen la lista de dias feriados
     * 
     * @return la lista de dias feriados
     */
    public List<Date> getDiasFeriados() {
        String sqlQuery = "select dias.fecDiaFestivo from DicDiasFestivo dias";
        TypedQuery<Date> query = entityManager.createQuery(sqlQuery, Date.class);
        return query.getResultList();
    }

    /**
     * Obtiene el salario minimo asociado a una zona salarial al dia actual
     * @param zonaSalarial zona salarial
     * @return el salario minimo asociado a al zona zalarial
     */
    public BigDecimal getSalarioMinimoIVRO(String zonaSalarial) throws SUAException {
        BigDecimal salario;
        if (zonaSalarial.contains(":")){
            String[] claves = zonaSalarial.split(":");
            String cveEnt = claves[0];
            String cveMun = claves[1];
            salario = getSalarioMinimoIVRO(new Date(),cveEnt,cveMun);
        }else {
            salario = getSalarioMinimoAreaGeo(zonaSalarial);
        }
        return salario;
    }

    /**
     * Obtiene el salario minimo asociado a una zona salarial al dia actual
     * @param cveEnt Clave de la Entidad Federativa
     * @param cveMun Clave del municipio
     * @return el salario minimo asociado a la zona
     */
    public BigDecimal getSalarioMinimoIVRO(Date fecha, String cveEnt, String cveMun) throws SUAException {
        StringBuilder q = new StringBuilder();

        q.append("SELECT distinct SG.SALARIO_MINIMO ")
        .append("FROM DIT_MUNICIPIOIMSS_SALARIO MIS ")
        .append("INNER JOIN DIC_MUNICIPIO_IMSS MI ON MIS.CVE_ID_MUNICIPIO_IMSS = MI.CVE_ID_MUNICIPIO_IMSS ")
        .append("INNER JOIN DIT_SALARIO_GENERAL SG ON MIS.CVE_ID_SALARIO_GENERAL = SG.CVE_ID_SALARIO_GENERAL ")
        .append("INNER JOIN DIC_AREA_GEOGRAFICA AG ON SG.CVE_ID_AREA_GEOGRAFICA = AG.CVE_ID_AREA_GEOGRAFICA ")
        .append("INNER JOIN DIC_CICLO CIC ON SG.CVE_ID_CICLO = CIC.CVE_ID_CICLO ")
        .append("INNER JOIN DIT_MUNICIPIO_IMSS_INEGI MII ON MI.CVE_ID_MUNICIPIO_IMSS = MII.CVE_ID_MUNICIPIO_IMSS ")
        .append("INNER JOIN DG_CAT_MUNICIPIO MUN ON MII.CVE_ENT = MUN.CVE_ENT AND MII.CVE_MUN = MUN.CVE_MUN ")
        .append("WHERE MUN.CVE_ENT = :cveEnt AND MUN.CVE_MUN = :cveMun ")
        .append("AND MIS.FEC_INI_VIGENCIA <= :fecha ")
        .append("AND MIS.FEC_FIN_VIGENCIA >= :fecha ");

        if(fecha==null){
            fecha=new Date();
        }

        try {

            fecha = DateUtils.truncate(fecha, Calendar.DATE);

            LOGGER.info("QUERY: "+q.toString() + " cveEnt: "+cveEnt+ " cveMun: "+cveMun+ " fecha: "+fecha);
            Query query = entityManager.createNativeQuery(q.toString())
                    .setParameter("cveEnt", cveEnt)
                    .setParameter("cveMun", cveMun)
                    .setParameter("fecha", fecha, TemporalType.DATE);

            List list = query.getResultList();
            BigDecimal salario;
            if(list == null || list.size()==0) {
                throw new NoResultException();
            }else{
                Object resultado  = list.get(0);
                LOGGER.info("El resultado es BigDecimal");
                salario = (BigDecimal)resultado;
                LOGGER.info("Salario: "+salario);
            }
            return salario;
        } catch (NoResultException e) {
            LOGGER.error("No se encontro Salario para el cveEnt "+cveEnt+" cveMun "+cveMun+"y fecha {}",fecha);
            throw new SUAException(SUAConstants.COD_NO_SALARIO, SUAConstants.MSG_NO_SALARIO);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de un salario para el cveEnt "+cveEnt+" cveMun "+cveMun+"y fecha {}",fecha);
            throw new SUAException(SUAConstants.COD_MULTIPLE_SALARIO,
                    SUAConstants.MSG_MULTIPLE_SALARIO);
        } catch (Exception e) {
            LOGGER.error("ERROR al ejecutar el QUERY: ",e);
            throw new SUAException(e.getLocalizedMessage(),
                    e.getMessage());
        }
    }


    /**
     * Obtiene el salario minimo asociado a una zona salarial al dia actual
     * @param zonaSalarial zona salarial
     * @return el salario minimo asociado a al zona zalarial
     */
    public BigDecimal getSalarioMinimoCRVO(String zonaSalarial) throws SUAException {
        BigDecimal salario;
        if (zonaSalarial.contains(":")){
            String[] claves = zonaSalarial.split(":");
            String cveEnt = claves[0];
            String cveMun = claves[1];
            salario = getSalarioMinimoCVRO(cveEnt,cveMun);
        }else {
            salario = getSalarioMinimoAreaGeo(zonaSalarial);
        }
        return salario;
    }

    /**
     * Obtiene el salario minimo asociado a una zona salarial al dia actual
     * @param cveEnt Clave de la Entidad Federativa
     * @param cveMun Clave del municipio
     * @return el salario minimo asociado a la zona
     */
    public BigDecimal getSalarioMinimoCVRO(String cveEnt, String cveMun) throws SUAException {
        StringBuilder q = new StringBuilder();

        q.append("SELECT distinct SG.SALARIO_MINIMO ")
                .append("FROM DIT_MUNICIPIO_IMSS_INEGI           MII   ")
                .append("INNER JOIN DIC_MUNICIPIO_IMSS           MI    ON MI.CVE_ID_MUNICIPIO_IMSS           = MII.CVE_ID_MUNICIPIO_IMSS ")
                .append("INNER JOIN DIT_MUNICIPIO_SUBDELEGACION  MS    ON MS.CVE_ID_MUNICIPIO_IMSS           = MI.CVE_ID_MUNICIPIO_IMSS ")
                .append("INNER JOIN DIC_REG_PAT_CONVENCIONAL     RPC   ON MS.CVE_ID_SUBDELEGACION            = RPC.CVE_ID_SUBDELEGACION AND RPC.CVE_ID_MODALIDAD = 20 ")
                .append("INNER JOIN DIT_LLAVE_PATRON             LLAV  ON LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO = RPC.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append("INNER JOIN DIT_MUNICIPIO_PAT_SUJ_OBLIG  MPA   ON MPA.CVE_ID_PATRON_SUJETO_OBLIGADO  = LLAV.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append("INNER JOIN DIT_MUNICIPIOIMSS_SALARIO    MIS   ON MIS.CVE_ID_MUNICIPIO_IMSS          = MPA.CVE_ID_MUNICIPIO_IMSS ")
                .append("INNER JOIN DIT_SALARIO_GENERAL          SG    ON MIS.CVE_ID_SALARIO_GENERAL         = SG.CVE_ID_SALARIO_GENERAL ")
                .append("WHERE MII.CVE_ENT = :cveEnt AND MII.CVE_MUN = :cveMun ")
                .append("AND MIS.FEC_INI_VIGENCIA <= :fecha ")
                .append("AND MIS.FEC_FIN_VIGENCIA >= :fecha ");

        Date fechaConsulta = null;

        try {

            fechaConsulta = DateUtils.truncate(new Date(), Calendar.DATE);

            LOGGER.info("QUERY: "+q.toString() + " cveEnt: "+cveEnt+ " cveMun: "+cveMun);
            Query query = entityManager.createNativeQuery(q.toString())
                    .setParameter("cveEnt", cveEnt)
                    .setParameter("cveMun", cveMun)
                    .setParameter("fecha", fechaConsulta, TemporalType.DATE);

            List list = query.getResultList();
            BigDecimal salario;
            if(list == null || list.size()==0) {
                throw new NoResultException();
            }else{
                Object resultado  = list.get(0);
                LOGGER.info("El resultado es BigDecimal");
                salario = (BigDecimal)resultado;
                LOGGER.info("Salario: "+salario);
            }
            return salario;
        } catch (NoResultException e) {
            LOGGER.error("No se encontro Salario para el cveEnt "+cveEnt+" cveMun "+cveMun+"y fecha {}",fechaConsulta);
            throw new SUAException(SUAConstants.COD_NO_SALARIO, SUAConstants.MSG_NO_SALARIO);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de un salario para el cveEnt "+cveEnt+" cveMun "+cveMun+"y fecha {}",fechaConsulta);
            throw new SUAException(SUAConstants.COD_MULTIPLE_SALARIO,
                    SUAConstants.MSG_MULTIPLE_SALARIO);
        } catch (Exception e) {
            LOGGER.error("ERROR al ejecutar el QUERY: ",e);
            throw new SUAException(e.getLocalizedMessage(),
                    e.getMessage());
        }
    }

    /**
     * Obtiene el salario minimo asociado a una zona salarial al dia actual 
     * @param zona zona salarial
     * @return el salario minimo asociado a la zona
     */
    public BigDecimal getSalarioMinimoAreaGeo(String zona) throws SUAException {
        AreaGeograficaEnum area = AreaGeograficaEnum.geFromClave(zona);

        StringBuilder q = new StringBuilder("select salario.salarioMinIntegrado ")
                .append("From DitSalarioGeneral salario ")
                .append("join salario.dicAreaGeografica  areaGeografica ")
                .append("where areaGeografica.cveIdAreaGeografica = :idArea ")
                .append("and salario.fecInicioVigencia <= :fecha ")
                .append("and salario.fecFinVigencia >= :fecha ");

        Date fechaConsulta = DateUtils.truncate(new Date(), Calendar.DATE);
        
        TypedQuery<BigDecimal> query = entityManager
                .createQuery(q.toString(), BigDecimal.class)
                .setParameter("idArea", (long) area.getId())
                .setParameter("fecha", fechaConsulta, TemporalType.DATE);
        
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.error("No se encontro Salario para el area  {} y fecha {}", area.getClave(),
                    fechaConsulta);
            throw new SUAException(SUAConstants.COD_NO_SALARIO, SUAConstants.MSG_NO_SALARIO);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de un salario para el area {} y fecha {}",
                    area.getClave(), fechaConsulta);
            throw new SUAException(SUAConstants.COD_MULTIPLE_SALARIO,
                    SUAConstants.MSG_MULTIPLE_SALARIO);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getSalarioMinimoCDMX(String zonaSalarial, Calendar fecha)
            throws SUAException {
        BigDecimal salario;

            String cveEnt = CLAVE_ENTIDAD_CDMX;
            String cveMun = CLAVE_MUNICIPIO_CDMX;

            Date fechaEnviar = fecha.getTime();

        try {
                salario = getSalarioMinimoIVRO(fechaEnviar, cveEnt,cveMun);
        } catch (SUAException e) {
            // Si no hay salario con la fecha indicada buscamos el actual
                salario = getSalarioMinimoIVRO(new Date(),cveEnt,cveMun);
        }
        return salario;
    }

    public BigDecimal getUma(String fecha) throws SUAException {
    	Date dateFecha = null;
    	
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ditUma.umaDiario ");
        stringBuilder.append("From DitUma ditUma ");
        stringBuilder.append("where ");
        stringBuilder.append("ditUma.fecInicioVigencia <= :fecha ");
        stringBuilder.append("and ditUma.fecFinVigencia >= :fecha ");

//        Date fechaConsulta = DateUtils.truncate(fecha.getTime(), Calendar.DATE);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        
        try {
			dateFecha = sdf.parse(fecha + " 00:00:00");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        TypedQuery<BigDecimal> query = entityManager.createQuery(stringBuilder.toString(), BigDecimal.class)
        		.setParameter("fecha", dateFecha);
        
        try {
            BigDecimal uma = query.getSingleResult();
            if (uma == null) {
            	throw new NoResultException();
            }
            return uma;
        } catch (NoResultException e) {
            throw new SUAException(SUAConstants.COD_NO_UMA, SUAConstants.MSG_NO_UMA);
        } catch (NonUniqueResultException n) {
            throw new SUAException(SUAConstants.COD_MULTIPLE_UMA,
                    SUAConstants.MSG_MULTIPLE_UMA);
        }
    }


    @Override
    public String obtenZonaSalarialOriginal(String zonaSalarial, ModalidadEnum modalidad) {

        String cveEnt = null;
        String cveMun = null;

        if(zonaSalarial!=null&&zonaSalarial.contains(";")){
            String zonasSalariales[] = zonaSalarial.split(";");
            zonaSalarial = zonasSalariales[0];
        }

        if (zonaSalarial!=null&&zonaSalarial.contains(":")) {
            String[] claves = zonaSalarial.split(":");
            cveEnt = claves[0];
            cveMun = claves[1];

            StringBuffer q = new StringBuffer();

            if(modalidad.getId()==ModalidadEnum.CUARENTA.getId()){

                LOGGER.info("999999 IF CVRO cotizacion");

                q.append("SELECT DISTINCT DECODE(SG.CVE_ID_AREA_GEOGRAFICA,1,'A',2,'B',3,'C',4,'D','I')AREA_GEOGRAFICA ")
                        .append("FROM DIT_MUNICIPIO_IMSS_INEGI           MII ")
                        .append("INNER JOIN DIT_MUNICIPIO_SUBDELEGACION  MS ON MII.CVE_ID_MUNICIPIO_IMSS = MS.CVE_ID_MUNICIPIO_IMSS ")
                        .append("INNER JOIN DIC_REG_PAT_CONVENCIONAL     RPC ON MS.CVE_ID_SUBDELEGACION = RPC.CVE_ID_SUBDELEGACION AND RPC.CVE_ID_MODALIDAD = 20 ")
                        .append("INNER JOIN DIT_MUNICIPIO_PAT_SUJ_OBLIG  MPAT ON RPC.CVE_ID_PATRON_SUJETO_OBLIGADO = MPAT.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                        .append("INNER JOIN DIT_MUNICIPIOIMSS_SALARIO MIS ON MPAT.CVE_ID_MUNICIPIO_IMSS = MIS.CVE_ID_MUNICIPIO_IMSS ")
                        .append("INNER JOIN DIT_SALARIO_GENERAL SG ON MIS.CVE_ID_SALARIO_GENERAL = SG.CVE_ID_SALARIO_GENERAL ")
                        .append("WHERE MII.CVE_ENT = :cveEnt ")
                        .append("AND MII.CVE_MUN = :cveMun ")
                        .append("AND MIS.FEC_INI_VIGENCIA <= :fecha ")
                        .append("AND MIS.FEC_FIN_VIGENCIA >= :fecha ");
            }else{

                LOGGER.info("999999 ELSE IVRO cotizacion");

                q.append("SELECT DISTINCT DECODE(SG.CVE_ID_AREA_GEOGRAFICA,1,'A',2,'B',3,'C',4,'D','I')AREA_GEOGRAFICA ")
                        .append("FROM DIT_MUNICIPIO_IMSS_INEGI   MII ")
                        .append("INNER JOIN DIT_MUNICIPIOIMSS_SALARIO MIS ON MII.CVE_ID_MUNICIPIO_IMSS = MIS.CVE_ID_MUNICIPIO_IMSS ")
                        .append("INNER JOIN DIT_SALARIO_GENERAL SG ON MIS.CVE_ID_SALARIO_GENERAL = SG.CVE_ID_SALARIO_GENERAL ")
                        .append("WHERE MII.CVE_ENT = '09' ")
                        .append("AND MII.CVE_MUN = '015' ")
                        .append("AND TRUNC(MIS.FEC_INI_VIGENCIA) <= TRUNC(SYSDATE) ")
                        .append("AND TRUNC(MIS.FEC_FIN_VIGENCIA) >= TRUNC(SYSDATE) ");
            }

            try {

//                Query query = entityManager.createNativeQuery(q.toString());
//                query.setParameter("cveEnt", cveEnt);
//                query.setParameter("cveMun", cveMun);
//                List zonaSalList = query.getResultList();

                Query query = null;
                if (modalidad.equals(ModalidadEnum.CUARENTA)) {
                    query = entityManager.createNativeQuery(q.toString())
                            .setParameter("cveEnt", cveEnt)
                            .setParameter("cveMun", cveMun)
                            .setParameter("fecha", new Date(), TemporalType.DATE);
                }else{
                    query = entityManager.createNativeQuery(q.toString());
                }
                List zonaSalList = query.getResultList();
                Object resultado = (Object) zonaSalList.get(0);
                if (resultado instanceof String) {
                    LOGGER.info("El resultado es " + resultado);
                    return (String) resultado;
                } else {
                    LOGGER.info("El resultado no es String");
                }
            } catch (NoResultException e) {
                LOGGER.error("No se encontro Zona Salarial para el cveEnt " + cveEnt + " cveMun " + cveMun);
            } catch (NonUniqueResultException n) {
                LOGGER.error("Se encontro mas de una Zona Salarial para el cveEnt " + cveEnt + " cveMun " + cveMun);
            } catch (Exception e) {
                LOGGER.error("ERROR al ejecutar el QUERY: ", e);
            }
        }
        return zonaSalarial;
    }
}
