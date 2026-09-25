package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

@Local
public interface FactorCostosEntityLocal {
	List<RamaCalculo> buscarCostoSeguro(int edad, long parentesco);
}
