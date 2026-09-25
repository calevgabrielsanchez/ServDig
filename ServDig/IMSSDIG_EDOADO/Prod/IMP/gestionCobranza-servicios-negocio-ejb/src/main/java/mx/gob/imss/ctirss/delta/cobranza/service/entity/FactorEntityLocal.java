package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;

@Local
public interface FactorEntityLocal {

	List<Factor> findFactorAllManMapping();
	
	Factor getFactorByPeriodo(String periodo);
	
}
