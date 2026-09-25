package mx.gob.imss.ctirss.idse.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.enums.OrigenMovimientoEnum;
import mx.gob.imss.ctirss.idse.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.idse.persistencia.IdrBloqueRegistro;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasFisica;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasMorale;
import mx.gob.imss.ctirss.idse.persistencia.IdtCartasEmpresa;
import mx.gob.imss.ctirss.idse.persistencia.IdtHistoricoMovimiento;
import mx.gob.imss.ctirss.idse.persistencia.IdtRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IdtRepresentado;

@Local
public interface RegistroPatronalIdseServiceEntityLocal {

	IdrPersonasFisica obtenerPersonaFisica(String rfc, String serial);
	
	IdrPersonasMorale obtenerPersonaMoral(String rfc, String serial);
	
	IdtRegistrosPatronale obtenerRegistroPatronal(String nrp,
		TipoPersonaEnum tipoPersonaPatron, Long cvePatron);
	
	IdtRepresentado obtenerRepresentanteLegal(Long cvePersonaFisica, 
		TipoPersonaEnum tipoPersonaPatron, Long cvePatron);
	
	List<IdtRepresentado> obtenerRepresentantesLegalesAsociados(
		TipoPersonaEnum tipoPersonaPatron, Long cvePatron, Long cvePFRepresentante);
	
	IdrBloqueRegistro obtenerBloqueRegistro(Long cveRegistroPatronal, 
		Long cveRepresentado);
	
	IdrPersonasFisica guardarPersonaFisicia(IdrPersonasFisica idrPersonasFisica);
			
	IdrPersonasMorale guardarPersonaMoral(IdrPersonasMorale idrPersonasMoral);
	
	IdtRegistrosPatronale guardarRegistroPatronal(IdtRegistrosPatronale idtRegistroPatronal);
	
	IdtRepresentado guardarRepresentado(IdtRepresentado idtRepresentado);
	
	IdrBloqueRegistro guardarBloqueRegistro(IdrBloqueRegistro idrBloqueRegistro);
		
	IdtHistoricoMovimiento guardarHistoricoMovimiento(IdtHistoricoMovimiento idtHistoricoMovimiento);
	
	IdtCartasEmpresa guardarCartasEmpresa(IdtCartasEmpresa idtCartasEmpresa);
	
	void desasociarRegistroPatronal(RegistroPatronal registroPatronal);
	
	void desasociarRepresentanteLegal(Long cvePersonaFisica, TipoPersonaEnum tipoPersonaPatron, 
		Long cvePatron, String usuarioAfecta, OrigenMovimientoEnum origen);
	
	void asociarRepresentanteLegal(TipoPersonaEnum tipoPersonaPatron, Long cvePatron, 
		Long cveRepresentado, String usuarioAfecta, OrigenMovimientoEnum origen);
	
}
