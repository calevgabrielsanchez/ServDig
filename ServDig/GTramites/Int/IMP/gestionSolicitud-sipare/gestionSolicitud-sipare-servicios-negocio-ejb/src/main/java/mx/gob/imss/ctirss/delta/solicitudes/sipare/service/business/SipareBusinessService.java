package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.entity.SipareServiceEntityLocal;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.interfaces.SipareBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model.GraficaEnum;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model.GraficaSolicitudesWrapper;

import org.springframework.beans.BeanUtils;

@Stateless(mappedName = "sipareBusinessService")
public class SipareBusinessService extends AbstractServiceBusiness implements
		SipareBusinessServiceRemote {

	@EJB
	private SipareServiceEntityLocal sipareServiceEntity;

	@Override
	public List<GraficaSolicitudesWrapper> obtenerGraficas(String identificador,
			FiltroSolicitud filtros) {

		List<GraficaSolicitudesWrapper> graficas = new ArrayList<GraficaSolicitudesWrapper>();
		
		graficas.add(this.obtenerGrafica(GraficaEnum.TOTAL_PATRONES_SIPARE_MES.getIdentificador(), filtros));
		graficas.add(this.obtenerGrafica(GraficaEnum.TOTAL_PATRONES_SIPARE_DIA.getIdentificador(), filtros));

		return graficas;
	}

	@Override
	public GraficaSolicitudesWrapper obtenerGrafica(String identificador,
			FiltroSolicitud filtros) {

		GraficaSolicitudesWrapper wrapper = new GraficaSolicitudesWrapper();

		if (identificador.equals(GraficaEnum.TOTAL_PATRONES_SIPARE_MES.getIdentificador())) {
			copyProperties(wrapper, GraficaEnum.TOTAL_PATRONES_SIPARE_MES);
			filtros.setAgruparPorMes(true);
			wrapper.setData(this.sipareServiceEntity
					.obtenerUsuariosSipare(filtros));
		} else if (identificador.equals(GraficaEnum.TOTAL_PATRONES_SIPARE_DIA.getIdentificador())) {
			copyProperties(wrapper, GraficaEnum.TOTAL_PATRONES_SIPARE_DIA);
			filtros.setAgruparPorMes(false);

			Date fechaActual = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActual);
			calendar.set(Calendar.DATE, 1);

			filtros.setFechaInicioPresentacion(calendar.getTime());
			filtros.setFechaFinPresentacion(fechaActual);
			wrapper.setData(this.sipareServiceEntity
					.obtenerUsuariosSipare(filtros));
		}

		return wrapper;
	}

	private GraficaSolicitudesWrapper copyProperties(
			GraficaSolicitudesWrapper wrapper, GraficaEnum source) {

		BeanUtils.copyProperties(source, wrapper, new String[] { "yKeys",
				"labels" });

		wrapper.setyKeys(Arrays.asList(source.getyKeys()));
		wrapper.setLabels(Arrays.asList(source.getLabels()));

		return wrapper;
	}
}
