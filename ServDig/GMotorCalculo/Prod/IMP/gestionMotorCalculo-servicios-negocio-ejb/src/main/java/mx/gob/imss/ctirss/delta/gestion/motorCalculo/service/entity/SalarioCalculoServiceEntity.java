package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SalarioCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.SalarioUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para la obtencion de los salarios con los cuales se generan los
 * calculos
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "salarioCalculoServiceEntity", mappedName = "salarioCalculoServiceEntity")
public class SalarioCalculoServiceEntity implements SalarioCalculoServiceLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SalarioCalculoServiceEntity.class);
    /**
     * Lista de modalidades que aplican zona salarial A para su minimo, no
     * importando la zona donde sean registrados
     */
    private static final ModalidadEnum[] ENUM_IVRO = { ModalidadEnum.TREINTAYCINCO,
            ModalidadEnum.CUARENTAYTRES, ModalidadEnum.CUARENTAYCUATRO };
    /**
     * Zona salarial A
     */
    private static final String ZONA_A = "A";

    private static final String CLAVE_ENTIDAD_CDMX = "09";

    private static final String CLAVE_MUNICIPIO_CDMX = "015";

    /**
     * A�o que entro en vigor la uma
     */
    private static final Integer ANIO_INICIO_UMA = 2016;

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Agrega los distintos valores de salario para un empleado dependiendo de
     * su ZOna salarial y modalidad Dado que entre modalidades puede haber
     * salarios para cuota fija, excedente y el resto de cuoas
     * 
     * @param valores
     *            Valores para consultar y agregar los salarios
     * @return Regresa los valores de calculo con los salarios correpondientes
     * @throws SUAException
     *             Si se genera un error al consultar el salario de la persona
     */
    @Override
    public ValoresCalculoEmpleado   agregaSalariosCalculo(ValoresCalculoEmpleado valores)
            throws SUAException {
        ModalidadEnum modalidadEnum = ModalidadEnum.fromId(valores.getModalidad());

		Calendar fechaCalculoSalario = valores.getFechaInicioCalculo();

		LOGGER.info("Zona Salarial: "+valores.getZonaSalarial());
        String zonaSalarialCompra;
        String zonaSalarialCapturada;

        LOGGER.info("Modalidad: {}",modalidadEnum.getId());
        if (modalidadEnum.getId() == ModalidadEnum.TREINTAYCINCO.getId()||
                modalidadEnum.getId() == ModalidadEnum.CUARENTAYTRES.getId()||
                modalidadEnum.getId() == ModalidadEnum.CUARENTAYCUATRO.getId()){
            fechaCalculoSalario = fechaConsultaIvro(fechaCalculoSalario);
            LOGGER.info("Fecha de calculo Salario IVRO: {} ",DateUtils.truncate(fechaCalculoSalario.getTime(), Calendar.DATE));
        }else{
            LOGGER.info("No es un IVRO, la fecha de calculo se mantiene");
        }

        LOGGER.info("FechaInicioCalculo: "+fechaCalculoSalario.toString());
        LOGGER.info("UltimoSalarioReg: "+valores.getUltimoSalarioCotizado());

        BigDecimal salarioMinimo = new BigDecimal(0);

        if(modalidadEnum.getId() == ModalidadEnum.CUARENTA.getId()) {
            if(valores.getZonaSalarial().contains(";")){
                LOGGER.info("Es mod 40 y la zonaSalarial es: "+valores.getZonaSalarial());
                String zonasSalariales[] = valores.getZonaSalarial().split(";");
                zonaSalarialCapturada = zonasSalariales[0];
                zonaSalarialCompra = zonasSalariales[1];
                boolean validaSalarioMod40 = this.validaSalarioMod40(valores.getFechaFinCalculo(),fechaCalculoSalario);
                LOGGER.info("validaSalario para zonaSalarial: "+validaSalarioMod40);
                if(validaSalarioMod40){
                    LOGGER.info("Se manda la zona salarial que capturo en pantalla: "+zonaSalarialCapturada);
                    salarioMinimo = getSalarioMinimoActual(zonaSalarialCapturada, modalidadEnum,
                            PeriodoUtil.truncaFecha(fechaCalculoSalario));

                }else{
                    LOGGER.info("Se manda la zona salarial de la compra: "+zonaSalarialCompra);
                    salarioMinimo = getSalarioMinimoActual(zonaSalarialCompra, modalidadEnum,
                            PeriodoUtil.truncaFecha(fechaCalculoSalario));

                }
            }else{
                LOGGER.info("La zona salarial es: "+valores.getZonaSalarial());
                salarioMinimo = getSalarioMinimoActual(valores.getZonaSalarial(), modalidadEnum,
                        PeriodoUtil.truncaFecha(fechaCalculoSalario));
            }

        }else{
            LOGGER.info("NO es mod 40 y La zona salarial es: "+valores.getZonaSalarial());
            salarioMinimo = getSalarioMinimoActual(valores.getZonaSalarial(), modalidadEnum,
                    PeriodoUtil.truncaFecha(fechaCalculoSalario));
        }

        LOGGER.info("salarioMinimo: {}",salarioMinimo);
        BigDecimal salarioEmpl = valores.getEmpleado().getSalario();
        salarioEmpl = salarioEmpl != null ? salarioEmpl : salarioMinimo;
        LOGGER.info("salarioEmpl: {}",salarioEmpl);
        // Para cuota fija siempre se calcula con un salario minimo vigente del df
        BigDecimal salarioGVZonaA;
        try {

//        	salarioGVZonaA=getSalarioMinimoDeZona(PeriodoUtil.truncaFecha(fechaCalculoSalario));
            salarioGVZonaA = getSalarioMinimoCDMX(PeriodoUtil.truncaFecha(fechaCalculoSalario));
        } catch (SUAException e) {
//        	salarioGVZonaA=getSalarioMinimoDeZona(PeriodoUtil.truncaFecha(Calendar.getInstance()));
            salarioGVZonaA = getSalarioMinimoCDMX(PeriodoUtil.truncaFecha(Calendar.getInstance()));
        } 

        valores.setSalarioCuotaFija(salarioGVZonaA);
        LOGGER.info("setSalarioCuotaFija: {}",valores.getSalarioCuotaFija());
        valores.setSalarioExedente(SalarioUtil.getSalarioExcedente(salarioEmpl, salarioGVZonaA,
                modalidadEnum));
        LOGGER.info("setSalarioExedente: {}",valores.getSalarioExedente());
        //Se obtiene el valor de la UMA dado el periodo a calcular

        BigDecimal uma = null;
        LOGGER.info("Modalidad: {}",modalidadEnum.getId());
        if (modalidadEnum.getId() == ModalidadEnum.TREINTAYCINCO.getId()||
                modalidadEnum.getId() == ModalidadEnum.CUARENTAYTRES.getId()||
                modalidadEnum.getId() == ModalidadEnum.CUARENTAYCUATRO.getId()){
            LOGGER.info("si es IVRO se toma la fecha actual");
            uma = getUma(fechaCalculoSalario.getTime());
        }else{
            LOGGER.info("si NO es IVRO se toma la fecha de inicio del calculo");
            uma = getUma(valores.getFechaInicioCalculo().getTime());
        }

        valores.setSalarioCuotaFijaUma(uma);
        LOGGER.info("setSalarioCuotaFijaUma: {}",valores.getSalarioCuotaFijaUma());
        valores.setSalarioExedenteUma(SalarioUtil.getSalarioExcedente(salarioEmpl, uma, modalidadEnum));
        LOGGER.info("setSalarioExedenteUma: {}",valores.getSalarioExedenteUma());
        if(modalidadEnum.getId() == ModalidadEnum.TREINTAYCUATRO.getId()) {
        	valores.setSalarioCalculo(SalarioUtil.getSalarioCuotasDomestico(salarioEmpl, uma, salarioMinimo));
		} else if(modalidadEnum.getId() == ModalidadEnum.CUARENTA.getId()) {

            BigDecimal salario = fechaCalculoSalario.get(Calendar.YEAR) < ANIO_INICIO_UMA  ? salarioMinimo : uma;
            LOGGER.info("salario fechaCalculo: {}",salario);
			valores.setSalarioCalculo(SalarioUtil.getSalarioCuotasCvro(salarioEmpl, salario, salarioMinimo));
            LOGGER.info("salario getSalarioCalculo: {}",valores.getSalarioCalculo());
		} else {
        	valores.setSalarioCalculo(SalarioUtil.getSalarioCuotas(salarioEmpl, salarioMinimo,modalidadEnum));
            LOGGER.info("salario getSalarioCalculo: {}",valores.getSalarioCalculo());
		}

        return valores;
    }

    /**
     * MEtodo que buscara el salario minimo dependiendo de la zona salarial
     * 
     * @param zonaSalarial
     *            Zona a la que pertenece el salario a buscar
     * @param modalidad
     *            LA modalidad sociada al empleador del trabajador
     * @param fecha
     *            fecha para consltar el salario vigente
     * @return El salario encontrado
     * @throws SUAException
     *             Error al no encontrar salarios para esa zona salarial
     */
    private BigDecimal getSalarioMinimo(String zonaSalarial, ModalidadEnum modalidad, Calendar fecha)
            throws SUAException {
        String zona = ArrayUtils.contains(ENUM_IVRO, modalidad) ? ZONA_A : zonaSalarial;

        AreaGeograficaEnum area = AreaGeograficaEnum.geFromClave(zona);

        StringBuilder q = new StringBuilder();
        if (ArrayUtils.contains(ENUM_IVRO, modalidad)
        		|| modalidad.equals(ModalidadEnum.CUARENTA)) {
            q.append("select salario.salarioMinimo ");
        } else {
            q.append("select salario.salarioMinIntegrado ");
        }

        q.append("From DitSalarioGeneral salario ")
        .append("join salario.dicAreaGeografica  areaGeografica ")
        .append("where areaGeografica.cveIdAreaGeografica = :idArea ")
        .append("and salario.fecInicioVigencia <= :fecha ")
        .append("and salario.fecFinVigencia >= :fecha ");

        Date fechaConsulta = null;
        try {

            fechaConsulta = DateUtils.truncate(fecha.getTime(), Calendar.DATE);
            LOGGER.debug("Fecha de consulta  {}", fechaConsulta);

            TypedQuery<BigDecimal> query = entityManager.createQuery(q.toString(), BigDecimal.class)
                    .setParameter("idArea", (long) area.getId())
                    .setParameter("fecha", fechaConsulta, TemporalType.DATE);

            BigDecimal salario = query.getSingleResult();
            if(salario == null) {                 
                throw new NoResultException();
            }
            return salario;
        } catch (NoResultException e) {
            LOGGER.error("No se encontro Salario para el area  {} y fecha {}", area.getClave(),
                    fechaConsulta);
            throw new SUAException(SUAConstants.COD_NO_SALARIO, SUAConstants.MSG_NO_SALARIO);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de un salario para el area {} y fecha {}",
                    area.getClave(), fechaConsulta);
            throw new SUAException(SUAConstants.COD_MULTIPLE_SALARIO,
                    SUAConstants.MSG_MULTIPLE_SALARIO);
        } catch (Exception ex){
            LOGGER.error("Error para el area  {} y fecha {}", area.getClave(),
                    fechaConsulta);
            throw new SUAException("Error: "+ ex.getMessage());
        }
    }

    /**
     * MEtodo que buscara el salario minimo dependiendo de la zona salarial
     *
     * @param fecha
     *            fecha para consltar el salario vigente
     * @return El salario encontrado
     * @throws SUAException
     *             Error al no encontrar salarios para esa zona salarial
     */
    private BigDecimal getSalarioMinimoCDMX(Calendar fecha)
            throws SUAException {

        ModalidadEnum modalidad = ModalidadEnum.CUARENTAYCUATRO;
        String cveEnt = CLAVE_ENTIDAD_CDMX;
        String cveMun = CLAVE_MUNICIPIO_CDMX;
        return getSalarioMinimo(modalidad,fecha,cveEnt,cveMun);

    }

    private BigDecimal getSalarioMinimo(ModalidadEnum modalidad, Calendar fecha, String cveEnt, String cveMun)
            throws SUAException {
        LOGGER.info("cveEnt: {} cveMun: {} ",cveEnt,cveMun);

        StringBuilder q = new StringBuilder();

        if (modalidad.equals(ModalidadEnum.CUARENTA)) {
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
        } else {
            q.append("SELECT distinct SG.SALARIO_MINIMO ")
                    .append("FROM DIT_MUNICIPIOIMSS_SALARIO               MIS ")
                    .append("INNER JOIN DIC_MUNICIPIO_IMSS                MI  ON MIS.CVE_ID_MUNICIPIO_IMSS = MI.CVE_ID_MUNICIPIO_IMSS ")
                    .append("INNER JOIN DIT_SALARIO_GENERAL               SG  ON MIS.CVE_ID_SALARIO_GENERAL = SG.CVE_ID_SALARIO_GENERAL ")
                    .append("INNER JOIN DIT_MUNICIPIO_IMSS_INEGI          MII ON MI.CVE_ID_MUNICIPIO_IMSS = MII.CVE_ID_MUNICIPIO_IMSS ")
                    .append("WHERE MII.CVE_ENT = '09' AND MII.CVE_MUN = '015' ")
                    .append("AND MIS.FEC_INI_VIGENCIA <= :fecha ")
                    .append("AND MIS.FEC_FIN_VIGENCIA >= :fecha ");
        }

        Date fechaConsulta = DateUtils.truncate(fecha.getTime(), Calendar.DATE);
        LOGGER.debug("Fecha de consulta  {}", fechaConsulta);

        try {

            LOGGER.info("QUERY: "+q.toString());
            Query query = null;
            if (modalidad.equals(ModalidadEnum.CUARENTA)) {
                query = entityManager.createNativeQuery(q.toString())
                        .setParameter("cveEnt", cveEnt)
                        .setParameter("cveMun", cveMun)
                        .setParameter("fecha", fechaConsulta, TemporalType.DATE);
            }else{
                query = entityManager.createNativeQuery(q.toString())
                        .setParameter("fecha", fechaConsulta, TemporalType.DATE);
            }

            List list = query.getResultList();
            BigDecimal salario= new BigDecimal(0);
            if(list == null || list.size()==0) {
                throw new NoResultException();
            }else{
                Object resultado  =  list.get(0);
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
        }catch (Exception e){
            LOGGER.error("Error para el cveEnt "+cveEnt+" cveMun "+cveMun+"y fecha {}",fechaConsulta);
            LOGGER.error("ERROR: ",e);
            throw new SUAException("Error para el cveEnt "+cveEnt+" cveMun "+cveMun,
                    e.getMessage());
        }
    }
    
    /**
     * MEtodo que buscara el salario minimo dependiendo de la zona salarial
     * 
     * @param zonaSalarial
     *            Zona a la que pertenece el salario a buscar
     * @param modalidad
     *            LA modalidad sociada al empleador del trabajador
     * @param fecha
     *            fecha para consltar el salario vigente
     * @return El salario encontrado
     * @throws SUAException
     *             Error al no encontrar salarios para esa zona salarial
     */
    private BigDecimal getSalarioMinimoActual(String zonaSalarial, ModalidadEnum modalidad,
            Calendar fecha) throws SUAException {
        BigDecimal salario;
        try {

            if (zonaSalarial.contains(":")){
                String[] claves = zonaSalarial.split(":");
                String cveEnt = claves[0];
                String cveMun = claves[1];
                salario = getSalarioMinimo(modalidad,fecha,cveEnt,cveMun);
            }else {
                salario = getSalarioMinimo(zonaSalarial, modalidad, fecha);
            }
        } catch (SUAException e) {
            // Si no hay salario con la fecha indicada buscamos el actual
//            salario = getSalarioMinimo(zonaSalarial, modalidad, Calendar.getInstance());
            if (zonaSalarial.contains(":")){
                String[] claves = zonaSalarial.split(":");
                String cveEnt = claves[0];
                String cveMun = claves[1];
                salario = getSalarioMinimo(modalidad,Calendar.getInstance(),cveEnt,cveMun);
            }else {
                salario = getSalarioMinimo(zonaSalarial, modalidad, Calendar.getInstance());
            }

        }
        return salario;
    }
    
    public BigDecimal getUma(Date fecha) throws SUAException {
    	try {
	    	Date dateFecha = null;
	
	    	String q = "select ditUma.umaDiario " +
                        "From DitUma ditUma " +
                        "where ditUma.fecInicioVigencia <= :fecha " +
                        "and ditUma.fecFinVigencia >= :fecha ";
	        
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        String strFecha = simpleDateFormat.format(fecha);
	        
	        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	        try {
				dateFecha = simpleDateFormatDate.parse(strFecha + " 00:00:00");
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
	        
	        TypedQuery<BigDecimal> query = entityManager.createQuery(q, BigDecimal.class)
	        		.setParameter("fecha", dateFecha);
	        
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
    public String obtenZonaSalarialOriginal(String zonaSalarial, ModalidadEnum modalidad, Date fechaConsulta) {

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

                LOGGER.info("999999 IF CVRO salario");

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

                LOGGER.info("999999 ELSE IVRO salario");

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

                Query query = null;
                if (modalidad.equals(ModalidadEnum.CUARENTA)) {
                    query = entityManager.createNativeQuery(q.toString())
                            .setParameter("cveEnt", cveEnt)
                            .setParameter("cveMun", cveMun)
                            .setParameter("fecha", fechaConsulta, TemporalType.DATE);
                }else{
                    query = entityManager.createNativeQuery(q.toString());
                }

//
//                Query query = entityManager.createNativeQuery(q.toString());
//                query.setParameter("cveEnt", cveEnt);
//                query.setParameter("cveMun", cveMun);
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


    public boolean validaSalarioMod40(Calendar fechaFinCalculo,Calendar fechaInicialPeriodo){
        int mesFechaFin =fechaFinCalculo.get(Calendar.MONTH);
        int mesFechaIniPeriodo =fechaInicialPeriodo.get(Calendar.MONTH);

        LOGGER.info("Mes Fecha Fin Calculo: "+mesFechaFin);
        LOGGER.info("Mes Fecha Inicio Periodo: "+mesFechaIniPeriodo);

        if(mesFechaFin==mesFechaIniPeriodo){
            return true;
        }else{
            return false;
        }
    }

    private Calendar fechaConsultaIvro(Calendar fechaInicioTramite){
        Calendar fechaActual = Calendar.getInstance();

        int anioInicio = fechaInicioTramite.get(Calendar.YEAR);
        int anioActual = fechaActual.get(Calendar.YEAR);

        if(anioInicio>anioActual){
            return fechaActual;
        }

        int mesInicio = fechaInicioTramite.get(Calendar.MONTH);
        int mesActual = fechaActual.get(Calendar.MONTH);

        if(mesInicio>mesActual){
            return fechaActual;
        }

        return fechaInicioTramite;
    }

    @Override
    public BigDecimal getFactorCYVPatronalCVRO(String zonaSalarial, BigDecimal salarioEmpleado, Date fechaInicioPeriodo, Date fechaFinPeriodo){
        LOGGER.info("zonaSalarial: {} salarioEmpleado: {} ",zonaSalarial,salarioEmpleado);

        StringBuilder q = new StringBuilder();
        q.append("SELECT art.fac_por FROM MGC_ART168 art ");
        q.append("WHERE art.tip_zona_geografica = :zonaSalarial ");
        q.append("AND art.fec_ini_per <= TO_DATE ( :fechaInicial, 'yyyy/MM/dd') ");
        q.append("AND art.fec_fin_per >= TO_DATE ( :fechaFinal, 'yyyy/MM/dd') ");
        q.append("AND art.imp_ini <= :salarioEmpleado ");
        q.append("AND art.imp_fin >= :salarioEmpleado ");

//        Date fechaConsulta = DateUtils.truncate(new Date(), Calendar.DATE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String fechaInicial = simpleDateFormat.format(fechaInicioPeriodo);
        String fechaFinal = simpleDateFormat.format(fechaFinPeriodo);

        LOGGER.info("Fecha de consulta MGC_ART168 {}", fechaInicial);
        BigDecimal factorCYV= BigDecimal.ZERO;


        try {

            LOGGER.info("QUERY: "+q.toString());

            Query query = entityManager.createNativeQuery(q.toString())
                    .setParameter("zonaSalarial", zonaSalarial)
                    .setParameter("salarioEmpleado", salarioEmpleado)
                    .setParameter("fechaInicial", fechaInicial)
                    .setParameter("fechaFinal", fechaFinal);

            List list = query.getResultList();

            if(list == null || list.size()==0) {
                throw new NoResultException();
            }else{
                Object resultado  =  list.get(0);
                factorCYV = (BigDecimal)resultado;
                LOGGER.info("factorCYV: "+factorCYV);
            }


        } catch (NoResultException e) {
            LOGGER.error("No se encontro factorCYV para la zonaSalarial: "+zonaSalarial + " salarioEmpleado: "+salarioEmpleado+ " fechaInicial: "+fechaInicial + " fechaFinal: "+fechaFinal);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de un factorCYV para la zonaSalarial: "+zonaSalarial + " salarioEmpleado: "+salarioEmpleado+ " fechaInicial: "+fechaInicial + " fechaFinal: "+fechaFinal);

        }catch (Exception e){
            LOGGER.error("Error para el factorCYV para la zonaSalarial: "+zonaSalarial + " salarioEmpleado: "+salarioEmpleado+ " fechaInicial: "+fechaInicial+ " fechaFinal: "+fechaFinal );
            LOGGER.error("ERROR: ",e);

        }

        return factorCYV;
    }

    /**
     * Verifica si el seguro requiere un ajuste por la ley 168 mediante el idCotizacion
     * @param nss
     * @return
     */
    public BigDecimal verificaSeguroLey168(String nss){


        StringBuffer q = new StringBuffer();
        q.append("SELECT NVL(SUM(IMP_DIFERENCIA),0)DIFERENCIA FROM MGPBDTU9X.DIT_SEGURO_DIF_CYV WHERE NSS = :nss");


        try {
            LOGGER.info("Ejecutando el query: " + q.toString());

            Query query = entityManager.createNativeQuery(q.toString());
            query.setParameter("nss", nss);

            List resultList = query.getResultList();
            Object resultado = resultList.get(0);
            if (resultado instanceof BigDecimal) {
                LOGGER.info("El resultado es " + resultado);
                return (BigDecimal) resultado;
            } else {
                LOGGER.info("El resultado no es BigDecimal");
            }

        } catch (NoResultException e) {
            LOGGER.error("++-- Sin resultados al realizar la busqueda verificaSeguroLey168: ", e);

        }catch (Exception e) {
            LOGGER.error("++-- Error al realizar la busqueda verificaSeguroLey168: ", e);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Verifica si el seguro requiere un porcentaje al ajuste por la ley 168 mediante el idCotizacion
     * @param nss
     * @return
     */
    public BigDecimal recuperaPorcentajeSeguroLey168(String nss){

        StringBuffer q = new StringBuffer();
        q.append("SELECT NVL(SUM(IMPRECARGO),0)RECARGO FROM MGPBDTU9X.DIT_SEGURO_DIF_CYV WHERE NSS = :nss");


        try {
            LOGGER.info("Ejecutando el query del porcentaje: " + q.toString());

            Query query = entityManager.createNativeQuery(q.toString());
            query.setParameter("nss", nss);

            List resultList = query.getResultList();
            Object resultado = resultList.get(0);
            if (resultado instanceof BigDecimal) {
                LOGGER.info("El resultado es " + resultado);
                return (BigDecimal) resultado;
            } else {
                LOGGER.info("El resultado no es BigDecimal");
            }

        } catch (NoResultException e) {
            LOGGER.error("++-- Sin resultados al realizar la busqueda del porcentaje verificaSeguroLey168: ", e);

        }catch (Exception e) {
            LOGGER.error("++-- Error al realizar la busqueda del porcentaje  verificaSeguroLey168: ", e);
        }
        return BigDecimal.ZERO;
    }


    /**
     * Verifica si el seguro requiere un porcentaje de actualización al ajuste por la ley 168 mediante el nss
     * @param nss
     * @return
     */
    public BigDecimal recuperaActualizacionSeguroLey168(String nss){

        StringBuffer q = new StringBuffer();
        q.append("SELECT NVL(SUM(IMPACTUALIZACION),0)RECARGO FROM MGPBDTU9X.DIT_SEGURO_DIF_CYV WHERE NSS = :nss");


        try {
            LOGGER.info("Ejecutando el query de la actualizacion: " + q.toString());

            Query query = entityManager.createNativeQuery(q.toString());
            query.setParameter("nss", nss);

            List resultList = query.getResultList();
            Object resultado = resultList.get(0);
            if (resultado instanceof BigDecimal) {
                LOGGER.info("La actualizacion es " + resultado);
                return (BigDecimal) resultado;
            } else {
                LOGGER.info("El resultado no es BigDecimal");
            }

        } catch (NoResultException e) {
            LOGGER.error("++-- Sin resultados al realizar la busqueda de la actualizacion verificaSeguroLey168: ", e);

        }catch (Exception e) {
            LOGGER.error("++-- Error al realizar la busqueda de la actualizacion  verificaSeguroLey168: ", e);
        }
        return BigDecimal.ZERO;
    }
}
