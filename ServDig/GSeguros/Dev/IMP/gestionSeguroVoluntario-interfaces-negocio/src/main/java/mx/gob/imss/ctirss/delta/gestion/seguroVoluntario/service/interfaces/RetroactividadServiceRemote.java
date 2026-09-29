package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoPagosRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.CalculoPagosResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaConsultaResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadResponse;

@Remote
public interface RetroactividadServiceRemote {
	
	ValidaRetroactividadResponse obtenerInfoInicialRetroactividad(ValidaRetroactividadRequest request);
	
	CalculoPagosResponse calculoPagosRetroactividad(CalculoPagosRequest request);
	
	GeneracionMultilineaResponse generaMultilineaRetroactividad(GeneracionMultilineaRequest request);
	
	GeneracionMultilineaConsultaResponse consultaMultilineaRetroactividad(String nss);

}
