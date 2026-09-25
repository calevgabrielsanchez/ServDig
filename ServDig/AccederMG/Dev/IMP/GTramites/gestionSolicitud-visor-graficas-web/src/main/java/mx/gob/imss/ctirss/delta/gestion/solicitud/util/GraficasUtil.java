package mx.gob.imss.ctirss.delta.gestion.solicitud.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class GraficasUtil {

	public static List<Map<String, Object>> getJson(GraficaRequest request,
			List<GraficaResponse> datos) {

		List<Map<String, Object>> jsonData = null;

		if (request.getTipoGrafica() == 1) {
			jsonData = getJsonGraficaBarras(request, datos);
		} else if (request.getTipoGrafica() == 2) {
			jsonData = getJsonGraficaLineas(request, datos);
		}

		return jsonData;
	}

	private static List<Map<String, Object>> getJsonGraficaBarras(
			GraficaRequest request, List<GraficaResponse> datos) {

		String[] labelsEjeX = StringUtils.isNotBlank(request.getLabelsEjeX()) ? request
				.getLabelsEjeX().split("\\|") : null;
		String[] condicionesEjeX = StringUtils.isNotBlank(request
				.getCondicionesEjeX()) ? request.getCondicionesEjeX().split(
				"\\|") : null;

		String[] condicionesEjeY = StringUtils.isNotBlank(request
				.getCondicionesEjeY()) ? request.getCondicionesEjeY().split(
				"\\|") : null;

		ExpressionParser parser = new SpelExpressionParser();

		Map<String, Map<String, Object>> wrapper = new LinkedHashMap<String, Map<String, Object>>();

		for (int i = 0; i < labelsEjeX.length; i++) {
			String labelX = labelsEjeX[i];

			for (GraficaResponse dato : datos) {
				Map<String, Object> data = null;

				if (wrapper.containsKey(labelX)) {
					data = wrapper.get(labelX);
				} else {
					data = new HashMap<String, Object>();
				}

				Expression exp = parser.parseExpression(condicionesEjeX[i]);
				boolean result = exp.getValue(dato, Boolean.class);

				if (result) {
					data.put(request.getLlaveEjeX(), labelX);

					if (condicionesEjeY != null && condicionesEjeY.length > 0) {
						for (String condicionY : condicionesEjeY) {
							String tmp[] = condicionY.split("@");
							exp = parser.parseExpression(tmp[1]);
							result = exp.getValue(dato, Boolean.class);

							if (result) {
								long total = dato.getTotal();

								if (data.containsKey(tmp[0])) {
									total += (Long) data.get(tmp[0]);
								}

								data.put(tmp[0], total);
							} else {
								if (!data.containsKey(tmp[0])) {
									data.put(tmp[0], 0L);
								}
							}
						}
					} else {
						long total = dato.getTotal();

						if (data.containsKey(request.getLlaveEjeY())) {
							total += (Long) data.get(request.getLlaveEjeY());
						}

						data.put(request.getLlaveEjeY(), total);
					}

					wrapper.put(labelX, data);
				}
			}
		}

		return new ArrayList<Map<String, Object>>(wrapper.values());
	}

	private static List<Map<String, Object>> getJsonGraficaLineas(
			GraficaRequest request, List<GraficaResponse> list) {

		List<Map<String, Object>> response = null;

		if (!request.getTipoAgrupacion().equals(
				"diaHistoricoTotalTramtiesAgrupados")) {

			DateFormat df = new SimpleDateFormat("yyyy-MM");
			ExpressionParser parser = new SpelExpressionParser();

			Map<String, Map<String, Object>> agrupado = new LinkedHashMap<String, Map<String, Object>>();

			for (GraficaResponse element : list) {
				Map<String, Object> data = null;
				if (agrupado.containsKey(element.getFecha())) {
					data = agrupado.get(element.getFecha());
				} else {
					data = new LinkedHashMap<String, Object>();
				}

				if (!data.containsKey(element.getFecha())) {
					if (request.getTipoAgrupacion().equals("mesHistorico")) {
						try {
							data.put(request.getLlaveEjeX(), DateUtils
									.dateToStringConFormato(
											df.parse(element.getFecha()),
											"yyyy-MM-dd"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						data.put(request.getLlaveEjeX(), element.getFecha());
					}
				}

				for (String condicionAux : request.getCondicionesEjeY().split(
						"\\|")) {
					String tmp[] = condicionAux.split("@");
					Expression exp = parser.parseExpression(tmp[1]);
					boolean result = exp.getValue(element, Boolean.class);

					if (result) {
						long total = element.getTotal();

						if (data.containsKey(tmp[0])) {
							total += (Long) data.get(tmp[0]);
						}

						data.put(tmp[0], total);
						break;
					}
				}

				for (String condicionAux : request.getCondicionesEjeY().split(
						"\\|")) {
					String tmp[] = condicionAux.split("@");

					if (!data.containsKey(tmp[0])) {
						data.put(tmp[0], 0L);
					}
				}

				agrupado.put(element.getFecha(), data);
			}

			response = new ArrayList<Map<String, Object>>(agrupado.values());
		} else {
			response = new ArrayList<Map<String, Object>>(list.size());
			Map<String, Object> data = null;

			for (GraficaResponse element : list) {
				data = new LinkedHashMap<String, Object>();
				data.put(request.getLlaveEjeX(), element.getFecha());
				data.put(request.getLlaveEjeY(), element.getTotal());
				response.add(data);
			}
		}

		return response;

	}
}
