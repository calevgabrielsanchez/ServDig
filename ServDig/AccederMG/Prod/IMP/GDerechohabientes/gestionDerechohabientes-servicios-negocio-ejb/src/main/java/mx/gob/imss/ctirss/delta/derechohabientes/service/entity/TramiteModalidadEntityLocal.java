package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;

@Local
public interface TramiteModalidadEntityLocal {

	Boolean tramitePermitidoPorModalidades(Long idTipoTramite, List<Long> idModalidades, Boolean isPensionado);
	Boolean tramitePermitidoPorModalidad(Long idTipoTramite, Long idModalidad, Boolean isPensionado);
	Boolean tramitePermitidoPorModalidad(Long idTipoTramite, Modalidad modalidad, Boolean isPensionado);
	Boolean tramitePermitidoPorNumModalidad(Long idTipoTramite, String numModalidad, Boolean isPensionado);
	Boolean tramitePermitidoPorNumModalidades(Long idTipoTramite, List<String> numModalidad, Boolean isPensionado);
	Boolean tramitePermitidoParaPensionado(Long idTipoTramite);
}
