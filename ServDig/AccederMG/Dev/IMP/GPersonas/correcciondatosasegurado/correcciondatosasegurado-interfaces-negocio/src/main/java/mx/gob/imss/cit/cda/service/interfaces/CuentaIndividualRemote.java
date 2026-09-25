package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

@Remote
public interface CuentaIndividualRemote {
	
	List<PeriodoMovimientoAfiliatorio> consultarMovimientosCuentaIndividual(String nss);
	
	List<PeriodoMovimientoAfiliatorio> consultarUltimoMovimientoCuentaIndividual(String nss);
	
	List<CuentaIndividualVO> consultarInformacionMovimientosCuentaIndividual(String nss);

}
