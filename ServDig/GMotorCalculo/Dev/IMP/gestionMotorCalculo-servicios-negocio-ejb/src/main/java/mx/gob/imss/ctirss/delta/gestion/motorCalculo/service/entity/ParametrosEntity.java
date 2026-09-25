/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.service.interfaces.ParametrosServiceRemote;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para la consulta de parametros, factores y entidades de uso para
 * ivro que no requieren definirse en un unico servici
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "parametrosEntity", mappedName = "parametrosEntity")
public class ParametrosEntity implements ParametrosEntityLocal {
    
    /**
     * Looger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosEntity.class);
    
    /**
     * Numero de dias despues de una fecha de vencimiento para sea valida realizar el vencimiento
     */
    private static final Integer DIAS_VENCIMIENTO = 5;
    /**
     * CLave del catalogo para obtener los dias
     */
    private static final String CLAVE_DIAS = "NUM_DIAS_VIG";
    
    /**
     * Clave para obtener e valor del porcentaje a cobrar en ivro por recargo   
     */
    private static final String CLAVE_RECARGO = "NUM_RECARGO_IVRO";
    
    private static final String CLAVE_RECARGO_CVRO = "NUM_RECARGO_CVRO";

    /**
     * Porcentaje de recargo default para la cotizacion de un seguro ivro
     */
    private static final BigDecimal PORCENTAJE_RECARGO = BigDecimal.TEN;
    
    private static final BigDecimal PORCENTAJE_RECARGO_CVRO = new BigDecimal("1.13");

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Servicio de consulta para parametros
     */
    @EJB(mappedName = "parametrosServiceComun", name = "parametrosServiceComun")
    private ParametrosServiceRemote parametrosServiceComun;
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
     * Obtiene el numero de dias posteriores a su vencimiento es vlido
     * @return El numero de dias validos despues de un vencimiento es valido
     */
    public Integer getDiasVEncimiento() {
        String valor = parametrosServiceComun.obtenValorPorLlave(CLAVE_DIAS);
        int dias = DIAS_VENCIMIENTO;
        if (StringUtils.trimToNull(valor) != null) {
            try {
                dias = Integer.valueOf(valor);
            } catch (NumberFormatException e) {
                LOGGER.info("La clave de la entidad no es numerica   es = {}", valor);
            }            
        }
        return -dias;
    }
    
    /**
     * Obtiene el porcentaje de recargo a cobrar en un seguro ivro
     * @return porcentaje a cobrar de recargo
     */
    public BigDecimal getPorcentajeRecargo() {
        String valor = parametrosServiceComun.obtenValorPorLlave(CLAVE_RECARGO);
        BigDecimal porcentaje  = PORCENTAJE_RECARGO;
        if (StringUtils.trimToNull(valor) != null) {
            try {
                porcentaje = new BigDecimal(valor);
            } catch (Exception e) {
                LOGGER.info("La clave de la entidad no es numerica   es = {}", valor);
            }            
        }
        return porcentaje;
    }

	public BigDecimal getPorcentajeRecargoCvro() {
		String valor = parametrosServiceComun.obtenValorPorLlave(CLAVE_RECARGO_CVRO);
		BigDecimal porcentaje = PORCENTAJE_RECARGO_CVRO;

		if (StringUtils.isNotBlank(valor)) {
			try {
				porcentaje = new BigDecimal(valor);
			} catch (Exception e) {
				LOGGER.info("La clave de la entidad no es numerica   es = {}", valor);
			}
		}

		return porcentaje;
	}

}
