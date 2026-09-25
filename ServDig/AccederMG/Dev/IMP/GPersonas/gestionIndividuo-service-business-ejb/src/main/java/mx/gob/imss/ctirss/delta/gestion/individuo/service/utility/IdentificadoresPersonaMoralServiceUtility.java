package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.math.BigDecimal;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.TipoIdentificadorEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DicTipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificadorMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

@Stateless(name = "identificadoresPersonaMoralServiceUtility", mappedName = "identificadoresPersonaMoralServiceUtility")
public class IdentificadoresPersonaMoralServiceUtility extends AbstractServiceUtility implements
		IdentificadoresPersonaMoralServiceUtilityLocal {

	@Override
	public Identificador transformarAModelo(
			DitIdentificadorMoral ditIdentificadorMoral) {
		
		Identificador identificador = new Identificador();
		
		identificador.setIdentificadora(ditIdentificadorMoral.getCveIdentificadora());
		identificador.setVigente(ditIdentificadorMoral.getIndVigente().longValue());
		
		TipoIdentificador tipoIdentificador = new TipoIdentificador();
		tipoIdentificador.setDesIdentificador(ditIdentificadorMoral.getDicTipoIdentificador().getDesIdentificador());
		tipoIdentificador.setIdTipoIdentificador(ditIdentificadorMoral.getDicTipoIdentificador().getCveIdTipoIdentificador());
		
		identificador.setIdIdentificador(ditIdentificadorMoral.getCveIdIdentificadorMoral());
		identificador.setTipoIdentificador(tipoIdentificador);
		
		return identificador;
	}

	@Override
	public DitIdentificadorMoral transformarAEntidad(
			Identificador identificador, Moral moral) {
		DitIdentificadorMoral ditIdentificadorMoral = null;
		DicTipoIdentificador dicTipoIdentificador = null;
		DitPersonaMoral ditPersonaMoral = null;

		if (identificador != null) {

			ditIdentificadorMoral = new DitIdentificadorMoral();

			String cveIidentificadora;
			if (identificador.getTipoIdentificador().getIdTipoIdentificador() == TipoIdentificadorEnum.RFC
					.getCodigo()) {
				cveIidentificadora = moral.getRfc();
			} else {
				cveIidentificadora = "";
			}

			ditIdentificadorMoral.setCveIdentificadora(cveIidentificadora);

			ditIdentificadorMoral.setIndVigente(new BigDecimal(identificador
					.getVigente()));

			ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(moral.getCveMoral());
			ditIdentificadorMoral.setDitPersonaMoral(ditPersonaMoral);

			dicTipoIdentificador = new DicTipoIdentificador();
			dicTipoIdentificador.setCveIdTipoIdentificador(identificador
					.getTipoIdentificador().getIdTipoIdentificador());
			dicTipoIdentificador.setDesIdentificador(identificador
					.getTipoIdentificador().getDesIdentificador());

			ditIdentificadorMoral.setDicTipoIdentificador(dicTipoIdentificador);

		}

		return ditIdentificadorMoral;
	}

}
