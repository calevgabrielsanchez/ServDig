package mx.gob.imss.ctirss.idse.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.idse.exception.RegistroPatronalIdseException;
import mx.gob.imss.ctirss.idse.model.Persona;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.persistencia.IdrBloqueRegistro;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasFisica;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasMorale;
import mx.gob.imss.ctirss.idse.persistencia.IdtHistoricoMovimiento;
import mx.gob.imss.ctirss.idse.persistencia.IdtRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IdtRepresentado;

@Local
public interface RegistroPatronalIdseServiceUtilityLocal {

	void validarDatosPatron(Persona persona) throws RegistroPatronalIdseException;
	void validarTipoPersonaPatron(int tipoPersona) throws RegistroPatronalIdseException;
	void validarDatosRepresentanteLegal(Persona persona) throws RegistroPatronalIdseException;
	void validarRepresentanteLegalRequerido(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException;
	void validarNRPRequerido(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException;
	void validarRegistroPatronalExistenteEnIdse(Long cveRegistroPatronal) throws RegistroPatronalIdseException;	
	void validarPersonaFisicaMoralPatronExistenteEnIdse(Long cvePersona) throws RegistroPatronalIdseException;
	void validarPersonaFisicaRLEnIdse(Long cvePersona) throws RegistroPatronalIdseException;
	void validarRepresentantesLegales(List<Persona> representantesLegales) throws RegistroPatronalIdseException;
	
	Persona transformarPersonaFisicaEntityToModel(IdrPersonasFisica entity);
	
	Persona transformarPersonaMoralEntityToModel(IdrPersonasMorale entity);
	
	IdrPersonasFisica prepararAltaPersonaFisica(Persona persona);
	
	IdrPersonasMorale prepararAltaPersonaMoral(Persona persona);
	
	IdtRegistrosPatronale prepararAltaRegistroPatronal(RegistroPatronal registroPatronal, String usuarioAfecta);
	
	IdtRepresentado prepararAltaRepresentado(RegistroPatronal registroPatronal);
	
	IdrBloqueRegistro prepararAltaBloqueRegistro(RegistroPatronal registroPatronal, Long cveRepresentado);
	
	IdtHistoricoMovimiento prepararHistoricoMovimientoManual(RegistroPatronal registroPatronal, String usuarioAfecta);
	
}
