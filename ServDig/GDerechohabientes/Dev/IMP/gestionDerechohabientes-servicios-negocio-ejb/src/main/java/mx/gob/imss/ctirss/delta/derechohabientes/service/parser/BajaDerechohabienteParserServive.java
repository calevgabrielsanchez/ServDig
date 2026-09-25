package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.persistence.DitBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicTipoBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;

@Stateless( name = "bajaDerechohabienteParserService", mappedName = "bajaDerechohabienteParserService")
public class BajaDerechohabienteParserServive extends AbstractServiceUtility
		implements BajaDerechohabienteParserServiceLocal {

	@Override
	public BajaDerechohabienteDto convertirTramiteBajaToBaja(
			TramiteBajaDerechohabiente tramiteBaja, Long bajaActiva) {
		BajaDerechohabienteDto dto = null;;
		
		if(tramiteBaja != null) {
			dto = new BajaDerechohabienteDto();
			Derechohabiente derechohabiente = (Derechohabiente) tramiteBaja.getPersona();
			
			dto.setCveIdTramite(tramiteBaja.getTramiteId());
			dto.setIndBajaActiva(bajaActiva);
			dto.setCveIdTipoBajaDer(this.getTipoBajaFromTipoTramite(tramiteBaja.getTipoTramite().getIdTipoTramite().longValue()));
			dto.setCveIdAsignacionNSS(derechohabiente.getAsignacionNSS().getIdAsignacionNSS());
			dto.setCveIdPersonaIntegrante(derechohabiente.getIdPersona());
			dto.setFecRegistroAlta(new Date());
			dto.setMotivo(tramiteBaja.getMotivo());
			dto.setMatricula(tramiteBaja.getMatricula());
			dto.setFundamentoLegal(tramiteBaja.getFundamentoLegal());
		}
		
		return dto;
	}

	
	@Override
	public DitBajaDerechohabiente convertirBajadtoToEntity(
			BajaDerechohabienteDto dto) {
		DitBajaDerechohabiente entity = null;
		
		if(dto != null) {
			entity = new DitBajaDerechohabiente();
			entity.setCveIdBaja(dto.getCveIdBaja());
			entity.setCveIdTramite(dto.getCveIdTramite());
			entity.setIndBajaActiva(dto.getIndBajaActiva());
			entity.setDicTipoBajaDerechohabiente(new DicTipoBajaDerechohabiente());
			entity.getDicTipoBajaDerechohabiente().setCveIdTipoBajaDer(dto.getCveIdTipoBajaDer());
			entity.setCveIdAsignacionNSS(dto.getCveIdAsignacionNSS());
			entity.setCveIdPersonaIntegrante(dto.getCveIdPersonaIntegrante());
			entity.setFecRegistroAlta(dto.getFecRegistroAlta());
			entity.setFecRegistroBaja(dto.getFecRegistroBaja());
			entity.setFecRegistroActualizaco(dto.getFecRegistroActualizaco());
			entity.setMotivo(dto.getMotivo());
			entity.setFundamentoLegal(dto.getFundamentoLegal());
			entity.setMatricula(dto.getMatricula());
		}
		
		return entity;
	}

	@Override
	public DitBajaDerechohabiente convertirTramiteBajaToEntity(
			TramiteBajaDerechohabiente tramiteBaja, Long bajaActiva) {
		DitBajaDerechohabiente entity = null;
		if(tramiteBaja != null) {
			entity = new DitBajaDerechohabiente();
			Derechohabiente derechohabiente = (Derechohabiente) tramiteBaja.getPersona();
			
			entity.setCveIdTramite(tramiteBaja.getTramiteId());
			entity.setIndBajaActiva(bajaActiva);
			entity.setDicTipoBajaDerechohabiente(new DicTipoBajaDerechohabiente());
			entity.getDicTipoBajaDerechohabiente().setCveIdTipoBajaDer(this.getTipoBajaFromTipoTramite(tramiteBaja.getTipoTramite().getIdTipoTramite().longValue()));
			entity.setCveIdAsignacionNSS(derechohabiente.getAsignacionNSS().getIdAsignacionNSS());
			entity.setCveIdPersonaIntegrante(derechohabiente.getIdPersona());
			entity.setFecRegistroAlta(new Date());
			entity.setMotivo(tramiteBaja.getMotivo());
			entity.setFundamentoLegal(tramiteBaja.getFundamentoLegal());
			entity.setMatricula(tramiteBaja.getMatricula());
		}
		return entity;
	}

	private Long getTipoBajaFromTipoTramite(Long idTipoTramite) {
		
		if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.DEFUNCION.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.DIVORCIO.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_AUTORIDAD_NORMATIVA.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.ADMINISTRATIVA.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_ADMINISTRATIVA.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.ADMIN.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.SUSPENCION_ADMINISTRATIVA.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.SUSPENCION.getId();
		} else if(idTipoTramite.equals(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL.getCodigo().longValue())) {
			return TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL.getId();
		}
		return null;
	}


	@Override
	public BajaDerechohabienteDto convertirEntityToBajaDto(DitBajaDerechohabiente entity) {
		BajaDerechohabienteDto dto = null;
		
		if(entity != null) {
			dto = new BajaDerechohabienteDto();
			
			dto.setCveIdBaja(entity.getCveIdBaja());
			dto.setCveIdTramite(entity.getCveIdTramite());
			dto.setIndBajaActiva(entity.getIndBajaActiva());
			dto.setCveIdTipoBajaDer(entity.getDicTipoBajaDerechohabiente().getCveIdTipoBajaDer());
			dto.setCveIdAsignacionNSS(entity.getCveIdAsignacionNSS());
			dto.setCveIdPersonaIntegrante(entity.getCveIdPersonaIntegrante());
			dto.setFecRegistroAlta(entity.getFecRegistroAlta());
			dto.setFecRegistroBaja(entity.getFecRegistroBaja());
			dto.setFecRegistroActualizaco(entity.getFecRegistroActualizaco());
			dto.setMotivo(entity.getMotivo());
			dto.setMatricula(entity.getMatricula());
			dto.setFundamentoLegal(entity.getFundamentoLegal());
		}
		
		return dto;
	}
}