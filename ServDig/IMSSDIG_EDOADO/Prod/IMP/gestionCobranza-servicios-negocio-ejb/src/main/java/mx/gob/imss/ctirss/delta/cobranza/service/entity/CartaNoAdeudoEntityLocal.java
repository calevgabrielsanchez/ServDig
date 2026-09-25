package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.AdeudoFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface CartaNoAdeudoEntityLocal {

	List<AdeudoFiscal> obtenerAdeudosPorRP(SujetoObligado sujetoObligado);

	List<SujetoObligado> obtenerPatronesParaCartaNoAdeudo(Persona persona);

	Boolean validaJuicioEnProceso(String rfc);

    Boolean validaAuditoriaEnProceso(String rfc);
    
    Boolean validaConvenioEnProceso(String rfc);

}
