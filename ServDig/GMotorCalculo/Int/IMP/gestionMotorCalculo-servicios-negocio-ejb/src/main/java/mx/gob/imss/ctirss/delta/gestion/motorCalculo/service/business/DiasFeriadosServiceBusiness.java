package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.DiasFeriadosServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.ParametrosEntityLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "diasFeriadosServiceBusiness", mappedName = "diasFeriadosServiceBusiness")
public class DiasFeriadosServiceBusiness implements DiasFeriadosServiceRemote {
    
    private static final Logger LOGGERBPM = LoggerFactory.getLogger(DiasFeriadosServiceBusiness.class);
    
    @EJB
    private ParametrosEntityLocal parametrosEntityLocal;

    /**
     * Obtiene la lista de dias feriados
     *
     * @return la lista de dias feriados
     */
    @Override
    public List<Date> getDiasFeriados() {
        LOGGERBPM.info("Se obtienen los dias feriados");
        return parametrosEntityLocal.getDiasFeriados();
    }
}
