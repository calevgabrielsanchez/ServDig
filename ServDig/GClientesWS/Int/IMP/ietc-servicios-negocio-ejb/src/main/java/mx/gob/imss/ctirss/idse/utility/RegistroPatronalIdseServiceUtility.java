package mx.gob.imss.ctirss.idse.utility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.idse.exception.RegistroPatronalIdseException;
import mx.gob.imss.ctirss.idse.model.Certificado;
import mx.gob.imss.ctirss.idse.model.Persona;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.enums.AccionMovimientosEnum;
import mx.gob.imss.ctirss.idse.model.enums.EstadoFielEnum;
import mx.gob.imss.ctirss.idse.model.enums.EstadoRegistroPatronalEnum;
import mx.gob.imss.ctirss.idse.model.enums.EstadoRelacionEnum;
import mx.gob.imss.ctirss.idse.model.enums.OrigenMovimientoEnum;
import mx.gob.imss.ctirss.idse.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.idse.persistencia.IdcAccionMovimiento;
import mx.gob.imss.ctirss.idse.persistencia.IdcEstatusFiel;
import mx.gob.imss.ctirss.idse.persistencia.IdcEstatusRegPat;
import mx.gob.imss.ctirss.idse.persistencia.IdcEstatusRelacion;
import mx.gob.imss.ctirss.idse.persistencia.IdrBloqueRegistro;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasFisica;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasMorale;
import mx.gob.imss.ctirss.idse.persistencia.IdtCartasEmpresa;
import mx.gob.imss.ctirss.idse.persistencia.IdtDatosCertificado;
import mx.gob.imss.ctirss.idse.persistencia.IdtDatosCertificadoPK;
import mx.gob.imss.ctirss.idse.persistencia.IdtHistoricoMovimiento;
import mx.gob.imss.ctirss.idse.persistencia.IdtRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IdtRepresentado;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "registroPatronalIdseServiceUtility", name = "registroPatronalIdseServiceUtility")
public class RegistroPatronalIdseServiceUtility	
	implements RegistroPatronalIdseServiceUtilityLocal{

	
	public void validarDatosPatron(Persona persona) throws RegistroPatronalIdseException{	
		if(persona==null)
			throw new RegistroPatronalIdseException("El patron es requerido para su lozalización en IDSE.");
		
		validarTipoPersonaPatron(persona.getTipoPersona());
		
		if(!valorNoVacio(persona.getRfc()))
			throw new RegistroPatronalIdseException("RFC del patron requerido para su lozalización en IDSE.");
		
		if(persona.getCertificado()==null || persona.getCertificado().getClaveSerial()==null)
			throw new RegistroPatronalIdseException("Serial del patron requerido para su lozalización en IDSE.");
		
	}
	
	public void validarTipoPersonaPatron(int tipoPersona) throws RegistroPatronalIdseException{
		TipoPersonaEnum tipoPatron = TipoPersonaEnum.parse(tipoPersona);
		if(tipoPatron==null)
			throw new RegistroPatronalIdseException("Debe indicar si es patron fisico o moral.");
		
	}
	
	public void validarDatosRepresentanteLegal(Persona persona) throws RegistroPatronalIdseException{
		if(persona==null)
			throw new RegistroPatronalIdseException("El representante legal es requerido para su lozalización en IDSE.");
		
		if(!valorNoVacio(persona.getRfc()))
			throw new RegistroPatronalIdseException("RFC del representante legal requerido para su lozalización en IDSE.");
		
		if(persona.getCertificado()==null || persona.getCertificado().getClaveSerial()==null)
			throw new RegistroPatronalIdseException("Serial del representante legal requerido para su lozalización en IDSE.");					
	}
	
	public void validarRepresentanteLegalRequerido(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException{
		if(registroPatronal.getRepresentanteLegal()==null)
			throw new RegistroPatronalIdseException("El alta de Registro Patronal persona moral no cuenta con representante legal.");
	}
	
	public void validarNRPRequerido(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException{
		if(registroPatronal.getNrp()==null)
			throw new RegistroPatronalIdseException("NRP requerido para lozalizar el registro patronal en IDSE.");
	}
	
	public void validarRegistroPatronalExistenteEnIdse(Long cveRegistroPatronal) throws RegistroPatronalIdseException{
		if(cveRegistroPatronal==null)
			throw new RegistroPatronalIdseException("No existe el Registro Patronal o esta inactivo para desasociar en IDSE.");
	}
	
	public void validarPersonaFisicaMoralPatronExistenteEnIdse(Long cvePersona) throws RegistroPatronalIdseException{
		if(cvePersona==null)
			throw new RegistroPatronalIdseException("No existe el patron o esta inactivo en IDSE.");
	}
	
	public void validarPersonaFisicaRLEnIdse(Long cvePersona) throws RegistroPatronalIdseException{
		if(cvePersona==null)
			throw new RegistroPatronalIdseException("No existe la persona fisica representante legal o esta inactivo en IDSE.");
	}
		
	public void validarRepresentantesLegales(List<Persona> representantesLegales) throws RegistroPatronalIdseException{
		if(CollectionUtils.isEmpty(representantesLegales))
			throw new RegistroPatronalIdseException("Debe indicar los Representantes Legales para su disosiación.");
	}
	
	
	public Persona transformarPersonaFisicaEntityToModel(IdrPersonasFisica entity){
		Persona persona = trasnformarDatosCertificadoToModel(entity.getIdtDatosCertificado());
		persona.setCvePersona(entity.getCvePersonaFisica());
		persona.setTipoPersona(TipoPersonaEnum.FISICA.getId());
		
		return persona;
	}
	
	public Persona transformarPersonaMoralEntityToModel(IdrPersonasMorale entity){
		Persona persona = trasnformarDatosCertificadoToModel(entity.getIdtDatosCertificado());
		persona.setCvePersona(entity.getCvePersonaMoral());
		persona.setTipoPersona(TipoPersonaEnum.MORAL.getId());
		
		return persona;
	}
	
	public IdrPersonasFisica prepararAltaPersonaFisica(Persona persona){
		IdrPersonasFisica pf = new IdrPersonasFisica();
		if(persona.getCvePersona()!=null){
			//Ya existe, recuperar ID
			pf.setCvePersonaFisica(persona.getCvePersona());
		}else{
			//No existe, preparar información
			pf.setIdtDatosCertificado(prepararDatosCertificado(persona));
		}
		return pf;
	}
	
	public IdrPersonasMorale prepararAltaPersonaMoral(Persona persona){
		IdrPersonasMorale pm = new IdrPersonasMorale();
		if(persona.getCvePersona()!=null){
			//Ya existe, recuperar ID
			pm.setCvePersonaMoral(persona.getCvePersona());
		}else{
			//No existe, preparar información
			pm.setIdtDatosCertificado(prepararDatosCertificado(persona));			
		}
		return pm;
	}
	
	public IdtRegistrosPatronale prepararAltaRegistroPatronal(
			RegistroPatronal registroPatronal, String usuarioAfecta){
		IdtRegistrosPatronale rp = new IdtRegistrosPatronale();
		rp.setIdcEstatusRegPat(new IdcEstatusRegPat());
		rp.getIdcEstatusRegPat().setCveEstatusRegPat(EstadoRegistroPatronalEnum.ACTIVADO.getId());
		rp.setRefDomicilioCentroTrab(registroPatronal.getDomicilioCentroTrabajo());
		rp.setRefRazonSocial(registroPatronal.getRazonSocial());
		rp.setRefRegistroPatronal(registroPatronal.getNrp());
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum
			.parse(registroPatronal.getPatron().getTipoPersona());
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			rp.setIdrPersonasFisica(new IdrPersonasFisica());
			rp.getIdrPersonasFisica().setCvePersonaFisica(
				registroPatronal.getPatron().getCvePersona());			
			rp.setIdrPersonasMorale(null);
		}else{
			rp.setIdrPersonasMorale(new IdrPersonasMorale());
			rp.getIdrPersonasMorale().setCvePersonaMoral(
				registroPatronal.getPatron().getCvePersona());
			rp.setIdrPersonasFisica(null);
		}
		rp.setIdtHistoricoMovimientos(null);
		rp.setIdtCartasEmpresa(null);
		//Asociar Movimiento
		IdtHistoricoMovimiento movimiento = prepararHistoricoMovimiento(registroPatronal, usuarioAfecta);
		movimiento.setIdtRegistrosPatronale(rp);
		rp.setIdtHistoricoMovimientos(new ArrayList<IdtHistoricoMovimiento>());	
		rp.getIdtHistoricoMovimientos().add(movimiento);
		//Asociar Carta Empresa
		IdtCartasEmpresa carta = prepararCartasEmpresa(registroPatronal);
		rp.setIdtCartasEmpresa(carta);
		
		return rp;
	}
	
	public IdtRepresentado prepararAltaRepresentado(RegistroPatronal registroPatronal){
		IdtRepresentado representado = new IdtRepresentado();
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum
			.parse(registroPatronal.getPatron().getTipoPersona());
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			representado.setPatronFisica(new IdrPersonasFisica());
			representado.getPatronFisica().setCvePersonaFisica(
				registroPatronal.getPatron().getCvePersona());
			representado.setPatronMoral(null);
		}else{
			representado.setPatronMoral(new IdrPersonasMorale());
			representado.getPatronMoral().setCvePersonaMoral(
				registroPatronal.getPatron().getCvePersona());
			representado.setPatronFisica(null);
		}		
		representado.setRepresentanteLegal(new IdrPersonasFisica());
		representado.getRepresentanteLegal()
			.setCvePersonaFisica(registroPatronal.getRepresentanteLegal().getCvePersona());		
		representado.setIdcEstatusRelacion(new IdcEstatusRelacion());
		representado.getIdcEstatusRelacion().setCveEstatusRelacion(EstadoRelacionEnum.ACTIVA.getId());		
		return representado;
	}
	
	public IdrBloqueRegistro prepararAltaBloqueRegistro(RegistroPatronal registroPatronal, Long cveRepresentado){
		IdrBloqueRegistro bloque = new IdrBloqueRegistro();
		bloque.setIdcEstatusRelacion(new IdcEstatusRelacion());
		bloque.getIdcEstatusRelacion().setCveEstatusRelacion(EstadoRelacionEnum.ACTIVA.getId());
		bloque.setIdtRegistrosPatronale(new IdtRegistrosPatronale());
		bloque.getIdtRegistrosPatronale().setCveRegistroPatronal(registroPatronal.getCveRegistroPatronal());
		bloque.setIdtRepresentado(new IdtRepresentado());
		bloque.getIdtRepresentado().setCveRepresentados(cveRepresentado);		
		return bloque;
	}
	
	public IdtHistoricoMovimiento prepararHistoricoMovimientoManual(
			RegistroPatronal registroPatronal, String usuarioAfecta){
		IdtHistoricoMovimiento movimiento = prepararHistoricoMovimiento(registroPatronal, usuarioAfecta);
		movimiento.setIdtRegistrosPatronale(new IdtRegistrosPatronale());
		movimiento.getIdtRegistrosPatronale().setCveRegistroPatronal(registroPatronal.getCveRegistroPatronal());
		return movimiento;
	}
	
	
	
	
	private IdtHistoricoMovimiento prepararHistoricoMovimiento(
			RegistroPatronal registroPatronal, String usuarioAfecta){
		IdtHistoricoMovimiento movimiento = new IdtHistoricoMovimiento();
		movimiento.setRefUsuarioAfecta("PDigIMSS");
		movimiento.setStpFechaMovimiento(new Timestamp(new Date().getTime()));
		movimiento.setIdcAccionMovimiento(new IdcAccionMovimiento());
		
		OrigenMovimientoEnum origen = OrigenMovimientoEnum.parse(registroPatronal.getIdOrigenSolicitud());
		if(origen.equals(OrigenMovimientoEnum.INTERNET)){
			movimiento.getIdcAccionMovimiento()
				.setCveAccionMovimiento(AccionMovimientosEnum.ADHESION_REGISTRO_PATRONAL.getId());			
		}else{
			movimiento.getIdcAccionMovimiento()
				.setCveAccionMovimiento(AccionMovimientosEnum.ADHESION_REGISTRO_PATRONAL_VENTANILLA.getId());
		}
		
		return movimiento;
	}
	
	private Persona trasnformarDatosCertificadoToModel(IdtDatosCertificado entity){
		Persona persona = new Persona();		
		persona.setRfc(entity.getId().getRefRfcAsociado());
		persona.setCertificado(new Certificado());
		persona.getCertificado().setClaveSerial(entity.getId().getCveSerialFiel());
		persona.getCertificado().setEstatus(entity.getIdcEstatusFiel().getCveEstatusFiel());
		persona.setNombreRazonSocial(entity.getNomNombreCompleto());
		persona.setNombreUsuario(entity.getRefNombreUsuario());
		persona.setCurp(entity.getRefCurp());
		persona.setDomicilioFiscal(entity.getRefDomicilioFiscal());
		persona.setCorreoElectronico(entity.getRefCorreoElectronico());		
		return persona;
	}
	
	private IdtDatosCertificado prepararDatosCertificado(Persona persona){
		IdtDatosCertificado datos = new IdtDatosCertificado();
		datos.setIdcEstatusFiel(new IdcEstatusFiel());
		datos.getIdcEstatusFiel().setCveEstatusFiel(EstadoFielEnum.ACTIVA.getId());
		datos.setId(new IdtDatosCertificadoPK());
		datos.getId().setCveSerialFiel(persona.getCertificado().getClaveSerial());
		datos.getId().setRefRfcAsociado(persona.getRfc());		
		datos.setNomNombreCompleto(persona.getNombreRazonSocial());
		datos.setRefCorreoElectronico(persona.getCorreoElectronico());
		datos.setRefCurp(persona.getCurp());
		datos.setRefDomicilioFiscal(persona.getDomicilioFiscal());
		datos.setRefNombreUsuario(persona.getNombreUsuario());		
		return datos;
	}
	
	private IdtCartasEmpresa prepararCartasEmpresa(
			RegistroPatronal registroPatronal){
		IdtCartasEmpresa cartasEmpresa = new IdtCartasEmpresa();		
		return cartasEmpresa;
	}
	
	private boolean valorNoVacio(String valor){
		if(StringUtils.isNotEmpty(valor) && StringUtils.isNotBlank(valor)){
			return true;
		}else{
			return false;
		}
	}
	
}
