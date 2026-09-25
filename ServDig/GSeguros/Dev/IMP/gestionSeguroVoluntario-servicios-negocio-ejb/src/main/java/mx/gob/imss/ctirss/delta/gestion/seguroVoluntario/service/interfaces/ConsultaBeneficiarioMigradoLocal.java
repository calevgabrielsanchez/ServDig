/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

/**
 * @author NOVUTECK1
 *
 */
@Local
public interface ConsultaBeneficiarioMigradoLocal {

    /**
     * Obtiene al beneficiario asociado a un seguro que se migro
     * @param seguro el seguro asociado al beneficiario
     * @return la persona beneficiada
     */
    Fisica buscaBeneficiarioSeguro(SeguroIvro seguro);
    
    /**
     * Obtiene la lista de personas asociadas a un seguro como beneficiario
     * esto solo por la migracion
     * @param seguro el seguro asociado a las personas
     * @return las personas asociadas al seguro
     */
    List<Fisica> buscaBeneficiariosSeguro(SeguroIvro seguro);
    
    /**
     * OBtiene a los trabajadores asociados a un seguro con su salario
     * esto es para los trabajadores 34 migrados
     * @param seguro el seguro paa asociar los trabajadores
     * @return la los trabajadores asociados con su salarios
     */
    Map<Fisica, BigDecimal> buscaBeneficiariosSalarioSeguro(SeguroIvro seguro);
    
    /**
     * Obtiene el nrp registrado al seguro
     * @param seguro el seguro asociado
     * @return el NRP
     */
    String obtenNrpPatron(SeguroIvro seguro);
}
