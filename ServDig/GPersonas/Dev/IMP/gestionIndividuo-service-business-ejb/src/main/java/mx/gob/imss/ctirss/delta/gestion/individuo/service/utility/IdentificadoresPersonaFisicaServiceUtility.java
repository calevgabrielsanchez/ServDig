package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.math.BigDecimal;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.TipoIdentificadorEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DicTipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Stateless(name="identificadoresPersonaFisicaServiceUtility", mappedName="identificadoresPersonaFisicaServiceUtility")
public class IdentificadoresPersonaFisicaServiceUtility extends AbstractServiceUtility 
	implements IdentificadoresPersonaFisicaServiceUtilityLocal {

	@Override
	public Identificador transformarAModelo(DitIdentificador ditIdentificador) {
		
		Identificador identificador = new Identificador();
		
		identificador.setIdentificadora(ditIdentificador.getCveIdentificadora());
		identificador.setVigente(ditIdentificador.getIndVigente().longValue());
		
		TipoIdentificador tipoIdentificador = new TipoIdentificador();
		tipoIdentificador.setDesIdentificador(ditIdentificador.getDicTipoIdentificador().getDesIdentificador());
		tipoIdentificador.setIdTipoIdentificador(ditIdentificador.getDicTipoIdentificador().getCveIdTipoIdentificador());
		
		identificador.setIdIdentificador(ditIdentificador.getCveIdIdentificador());	
		identificador.setTipoIdentificador(tipoIdentificador);
		
		return identificador;
	}

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a uno de entidad
	 * @param identificador
	 * @return
	 */
	@Override
	public DitIdentificador transformarAEntidad(Identificador identificador, Fisica fisica) {
		
		DitIdentificador ditIdentificador = null;
		DicTipoIdentificador dicTipoIdentificador = null;
		DitPersona ditPersona = null;
		
		if(identificador != null){
			
			ditIdentificador = new DitIdentificador();
			
    		String cveIidentificadora;
    		if(identificador.getTipoIdentificador().getIdTipoIdentificador() == TipoIdentificadorEnum.CURP.getCodigo()){
    			cveIidentificadora = fisica.getCurp();
    		}else if(identificador.getTipoIdentificador().getIdTipoIdentificador() == TipoIdentificadorEnum.RFC.getCodigo()){
    			cveIidentificadora = fisica.getRfc();
    		}else{
    			cveIidentificadora = "";
    		}
    		ditIdentificador.setCveIdentificadora(cveIidentificadora);
    		
    		ditIdentificador.setIndVigente(new BigDecimal(identificador.getVigente()));
			
			ditPersona = new DitPersona();
			ditPersona.setCveIdPersona(fisica.getIdPersona());
			ditIdentificador.setDitPersona(ditPersona);
			
			dicTipoIdentificador = new DicTipoIdentificador();
			dicTipoIdentificador.setCveIdTipoIdentificador(identificador.getTipoIdentificador().getIdTipoIdentificador());
			dicTipoIdentificador.setDesIdentificador(identificador.getTipoIdentificador().getDesIdentificador());
			
			ditIdentificador.setDicTipoIdentificador(dicTipoIdentificador);
			
		}
		
		return ditIdentificador;
		
	}	
}