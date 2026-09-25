package mx.gob.imss.ctirss.delta.gestion.beneficio.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaSinBeneficiosException;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DicDescuentoRiss;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaBeneficio;

@Local
public interface BeneficioServiceEntityLocal {

	Beneficio guardarBeneficio(Beneficio beneficio, Date fechaActual, Date fechaSatRif, 
		Date FechaInicioRifImss) throws BeneficioRissException;

	List<Beneficio> obtenerBeneficiosPersona(Fisica fisica,
		List<Integer> estadosBeneficio);
	
	List<Beneficio> obtenerBeneficiosPorIdsPersonas(List<Long> getIdsPersonasFisicas,
			List<Integer> estadosBeneficio);

	List<Beneficio> obtenerBeneficiosSujetoObligado(SujetoObligado sujetoObligado,
		List<Integer> estadosBeneficio);
	
	List<Beneficio> obtenerBeneficiosPorIdsSujetosObligados(List<Long> getIdsSujetosObligados,
		List<Integer> estadosBeneficio);

	List<Beneficio> obtenerBeneficiosPersonaPorTipo(Fisica fisica,
		List<Integer> tiposBeneficio, List<Integer> estadosBeneficio)
		throws PersonaSinBeneficiosException;

	List<Beneficio> obtenerBeneficiosSujetoObligadoPorTipo(SujetoObligado sujetoObligado, 
		List<Integer> tiposBeneficio, List<Integer> estadosBeneficio)
		throws PersonaSinBeneficiosException;
	
	void cancelarBeneficiosRiss(List<Beneficio> listaBeneficios, List<Integer> estadosBeneficio, 
		Date fechaBaja, MotivoCancelacionBeneficioEnum motivoCancelacion, int claveEstadoCancelado);
	
	List<DicDescuentoRiss> obtenerDicDescuentosRiss();
	
	Long obtenerIdSolicitudPatron(List<Long> idsSujetosObligados);
	
	Long solicitudEnProcesoPatron(
		List<Long> idsSujetosObligados, OrigenSolicitudEnum origenSolicitud);
	
	Long obtenerIdSolicitudFisicas(Long idPersona);
	
	Long solicitudEnProcesoFisicas(Long idPersona,
		OrigenSolicitudEnum origenSolicitud);
	
	List<MovimientoRissType> prepararLayoutMovimientoBajaRiss(List<Beneficio> listaBeneficios, 
		int motivoBaja, Date fechaBaja, String patronGeneral) throws BeneficioRissException;
	
	List<DitPatSujObligBeneficio> obtenerBeneficioActivoPatrones(Long idPersona);
	
	List<DitPatSujObligBeneficio> obtenerBeneficioPatronxIdBeneficio(Long idBeneficio);
	
	List<DitPersonaBeneficio> obtenerBeneficioActivoPersona(Long idPersona);
	
	void guardarDitPatSujObligBeneficio(DitPatSujObligBeneficio entity);

	
}