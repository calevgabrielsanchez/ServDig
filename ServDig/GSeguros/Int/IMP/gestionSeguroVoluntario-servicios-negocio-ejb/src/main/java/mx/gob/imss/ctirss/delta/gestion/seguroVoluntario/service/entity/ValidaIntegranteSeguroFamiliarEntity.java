package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaIntegranteSeguroFamiliarLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "validaIntegranteSeguroFamiliarEntity", mappedName = "validaIntegranteSeguroFamiliarEntity")
public class ValidaIntegranteSeguroFamiliarEntity implements
		ValidaIntegranteSeguroFamiliarLocal {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValidaIntegranteSeguroFamiliarEntity.class);

	@EJB
	private ConsultaSeguroIvroLocal consultaSeguroIvro;

	private static final String MENSAJE = "El trabajador ya cuenta con un seguro contratado.";

	@Override
	public RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(
			Long idEmpleador, String nss) {
		RespuestaValidacionTrabajador validacion = new RespuestaValidacionTrabajador();
		validacion.setValido(Boolean.TRUE);
		Long idPersona = idEmpleador != null ? idEmpleador : 0L;
		LOGGER.error("idEmpleador: " + idPersona);

		if (idPersona != null && !idPersona.equals(0L)) {
			Persona persona = new Persona();
			persona.setIdPersona(idPersona);
			List<SeguroIvro> seguros = consultaSeguroIvro.buscaSegurosPersona(
					persona,
					IvroFactory.generaModalidades(IvroFactory.MOD_FAMILIAR),
					IvroFactory.estadosSeguro(true));
			for (SeguroIvro seguro : seguros) {
				if (!trabajadorValido(nss, seguro)) {
					validacion.setValido(Boolean.FALSE);
					validacion.setMensajeValidacion(MENSAJE);
					break;
				}
			}
		}

		return validacion;
	}

	private Boolean trabajadorValido(String nss, SeguroIvro seguro) {
		Boolean valido = Boolean.TRUE;

		if (seguro.getTramite() != null && seguro.getTramite().getBeneficiarios() != null) {
			Fisica trabajador = seguro.getTramite().getBeneficiarios()[0];
			// si el trabajador ya tiene un seguro verificamos que no este en
			// renovacion
			if (StringUtils.equals(nss, StringUtils.trimToEmpty(trabajador.getNss()))) {
				Calendar hoy = Calendar.getInstance();
				Calendar fechaFin = Calendar.getInstance();
				fechaFin.setTime(seguro.getFechaFin());
				valido = hoy.get(Calendar.MONTH) == fechaFin.get(Calendar.MONTH);
			}
		}

		return valido;
	}
}
