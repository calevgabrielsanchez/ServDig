package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.asegurado.EstadoAsignacion;

import org.apache.commons.lang.StringUtils;

@Stateless
public class AsignacionMasivaHandler implements
		AsignacionMasivaHandlerLocal {
	private static final String CHANNEL_PUBLICAR_FIN_PROC = "/comet/delta/publicarFinProcesamiento";
	private static final String CHANNEL_PUBLICAR_RES_PARCIAL = "/comet/delta/publicarResultadoParcial";
	private static final String CHANNEL_PUBLICAR_REG_INDIVIDUAL = "/comet/delta/publicarRegistroIndividual";

	@Override
	public void publicarFinProcesamiento(final String idRegistroPatronal,
			final Long cveIdUsuario, final String usuario) throws IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("idRegistroPatronal", idRegistroPatronal);
		data.put("cveIdUsuario", cveIdUsuario);
		data.put("usuario", usuario);

//		simplePublish(CHANNEL_PUBLICAR_FIN_PROC, data);
	}

	@Override
	public void publicarResultadoParcialProcesamiento(final Integer numExitos,
			final Integer numErrores, final String idRegistroPatronal,
			final Long cveIdUsuario, final String usuario) throws IOException {
//		log.info("--->Init publicarResultadoParcialProcesamiento");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("numExitos", numExitos);
		data.put("numErrores", numErrores);
		data.put("idRegistroPatronal", idRegistroPatronal);
		data.put("cveIdUsuario", cveIdUsuario);
		data.put("usuario", usuario);

//		simplePublish(CHANNEL_PUBLICAR_RES_PARCIAL, data);
	}

	@Override
	public void publicarRegistroIndividualProcesamiento(
			final EstadoAsignacion estado, final String idRegistroPatronal,
			final Long cveIdUsuario, final String usuario) throws IOException {
		
//		log.info("--->Init publicarRegistroIndividualProcesamiento");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("nombreCompleto", getNombreCompleto(estado));
		data.put("curp", estado.getCurp());
		data.put("exitoAlta", estado.isExitoAlta());
		data.put("nssAsignado", estado.getNssAsignado());
		data.put("mensajeError", estado.getMensajeError());
		data.put("idRegistroPatronal", idRegistroPatronal);
		data.put("cveIdUsuario", cveIdUsuario);
		data.put("usuario", usuario);

//		simplePublish(CHANNEL_PUBLICAR_REG_INDIVIDUAL, data);
	}

	private String getNombreCompleto(EstadoAsignacion estado) {
		StringBuffer sbNombre = new StringBuffer();

		if (StringUtils.isNotBlank(estado.getNombre())) {
			sbNombre.append(estado.getNombre());
		}
		if (StringUtils.isNotBlank(estado.getApellidoPaterno())) {
			sbNombre.append(" ").append(estado.getApellidoPaterno());
		}
		if (StringUtils.isNotBlank(estado.getApellidoMaterno())) {
			sbNombre.append(" ").append(estado.getApellidoMaterno());
		}

		return sbNombre.toString();
	}
}
