package mx.gob.imss.ctirss.idse.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.idse.model.Pkcs7IETCBean;
import mx.gob.imss.ctirss.idse.model.RegistroPatronalIETCBean;
import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;
import mx.gob.imss.ctirss.idse.persistencia.IfrCartasIdseFiel;
import mx.gob.imss.ctirss.idse.persistencia.IfrCartasIdseFielPK;
import mx.gob.imss.ctirss.idse.persistencia.IfrCertificadoReq;
import mx.gob.imss.ctirss.idse.persistencia.IfrCertificadoReqPK;
import mx.gob.imss.ctirss.idse.persistencia.IftDatosCertificado;
import mx.gob.imss.ctirss.idse.persistencia.IftRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IftRegistrosPatronalePK;

@Stateless(mappedName="ietcUtility", name="ietcUtility")
public class IETCUtility implements IETCUtilityLocal {

	@Override
	public IftDatosCertificado convertirPkcs7AIftDatosCertificado(
			RequerimientoIETCBean reqIetcBean, Long cveRequerimiento) {
		Pkcs7IETCBean model = reqIetcBean.getPkcs7Bean();
		RegistroPatronalIETCBean registroPatronal = reqIetcBean.getRegPatronBean();
		
		IftDatosCertificado certEntity = new IftDatosCertificado();
		certEntity.setCveSerial(model.getClaveSerial());
		certEntity.setIdRolSolicitante(new BigDecimal(model.getIdRol()));
		certEntity.setNomNombreCompleto(model.getNombreCompleto());
		certEntity.setNumEstatus(new BigDecimal( model.getEstatusFiel() ) );
		certEntity.setRefCorreoElectronico(model.getCorreoElectronico());
		certEntity.setRefCurp(model.getCurpFiel());
		certEntity.setRefNombreUsuario(model.getNombreUsuario());
		certEntity.setRefRfcAsociado(model.getRfcAsociado());
		certEntity.setStpFechaValidoFin(model.getFechaValidaFin());
		certEntity.setStpFechaValidoInicio(model.getFechaValidaInicio());
		certEntity.setTelefono(model.getTelefono());
		
		
		IfrCartasIdseFielPK keyCartasIdse = new IfrCartasIdseFielPK();
		keyCartasIdse.setCveSerial(model.getClaveSerial());
		keyCartasIdse.setCveRequerimiento(cveRequerimiento);
		IfrCartasIdseFiel cartasFiel = new IfrCartasIdseFiel();
		cartasFiel.setId(keyCartasIdse);
		
		List<IftRegistrosPatronale> listaRegPatronales = new ArrayList<IftRegistrosPatronale>();
		IftRegistrosPatronalePK keyRP = new IftRegistrosPatronalePK();
		keyRP.setCveRequerimiento(cveRequerimiento);
		keyRP.setCveSerial(model.getClaveSerial());
		keyRP.setRefRegistroPatronal(registroPatronal.getRegistroPatronal());
		IftRegistrosPatronale regPatron = new IftRegistrosPatronale();
		regPatron.setCveEstatus(new BigDecimal( registroPatronal.getEstatusRP()));
		regPatron.setCveTipoPersona(new BigDecimal(registroPatronal.getTipoPersona()));
		regPatron.setId(keyRP);
		regPatron.setRefRazonSocial(registroPatronal.getRazonSocial());
		regPatron.setRefRfcRegistroPatronal(registroPatronal.getRfcRegistroPatronal());
		regPatron.setRefUsuarioSubdelAutoriza(registroPatronal.getUsuarioSubDel());
		regPatron.setStpFechaActivacion(registroPatronal.getFechaActivacion());
		regPatron.setStpFechaRecepcion(registroPatronal.getFechaRecepcion());
		listaRegPatronales.add(regPatron);
		
		
		List<IfrCertificadoReq> listaReqs = new ArrayList<IfrCertificadoReq>();
		IfrCertificadoReqPK pk = new IfrCertificadoReqPK();
		pk.setCveRequerimiento(cveRequerimiento);
		pk.setCveSerial(model.getClaveSerial());
		IfrCertificadoReq certReq = new IfrCertificadoReq();
		certReq.setPk(pk);
		
		certReq.setIfrCartasIdseFiel(cartasFiel);
		certReq.setIftRegistrosPatronales(listaRegPatronales);
		
		listaReqs.add(certReq);
		
		
		certEntity.setIfrCertificadoReqs(listaReqs);
		
		return certEntity;
	}

	@Override
	public IftDatosCertificado combinarInfoCertificado(
			IftDatosCertificado source, IftDatosCertificado target) {
		target.setCveSerial(source.getCveSerial());
		target.setIdRolSolicitante(source.getIdRolSolicitante());
		target.setNomNombreCompleto(source.getNomNombreCompleto());
		target.setNumEstatus(source.getNumEstatus() );
		target.setRefCorreoElectronico(source.getRefCorreoElectronico());
		target.setRefCurp(source.getRefCurp());
		target.setRefNombreUsuario(source.getRefNombreUsuario());
		target.setRefRfcAsociado(source.getRefRfcAsociado());
		target.setStpFechaValidoFin(source.getStpFechaValidoFin());
		target.setStpFechaValidoInicio(source.getStpFechaValidoInicio());
		target.setTelefono(source.getTelefono());

		return target;
	}
	
	
	
	
}
