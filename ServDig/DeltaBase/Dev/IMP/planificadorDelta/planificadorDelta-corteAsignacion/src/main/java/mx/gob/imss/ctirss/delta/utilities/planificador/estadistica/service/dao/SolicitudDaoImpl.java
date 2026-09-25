package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.service.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.EstadisticasAsegurados;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.EstadisticasAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.FiltroEstadisticaAsignacion;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public class SolicitudDaoImpl implements SolicitudDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticasAsignacion> findEstadisticaAsignacion(FiltroEstadisticaAsignacion filtro) {
		Session session = sessionFactory.getCurrentSession();

		String strFechaSolicitudInicial;
		String strFechaSolicitudFinal;

		StringBuffer bfrsubQueryVent = new StringBuffer();
		bfrsubQueryVent.append("select new mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.EstadisticasAsignacion(");
		bfrsubQueryVent.append("sol.dicOrigenSolicitud.cveIdOrigenSolicitud, tram.dicTipoTramite.cveIdTipoTramite, count(sol) ");
		bfrsubQueryVent.append(")");
		bfrsubQueryVent.append(" from DitTramite tram");
		bfrsubQueryVent.append(" inner join tram.ditSolicitud as sol");
		bfrsubQueryVent.append(" left outer join sol.dicSubdelegacion as subdeleg");
		bfrsubQueryVent.append(" left outer join subdeleg.dicDelegacion as deleg");

		if (filtro.getIndicadorBusquedaRango()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			strFechaSolicitudInicial = sdf.format(filtro.getFechaSolicitudInicial());
			strFechaSolicitudFinal = sdf.format(filtro.getFechaSolicitudFinal());
			bfrsubQueryVent.append(" where sol.fecSolicitud between TO_DATE(:fechaSolicitudInicial ,'yyyy-MM-dd HH24:MI') and TO_DATE(:fechaSolicitudFinal , 'yyyy-MM-dd HH24:MI') ");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strFechaSolicitudInicial = sdf.format(filtro.getFechaSolicitudInicial());
			strFechaSolicitudFinal = sdf.format(filtro.getFechaSolicitudFinal());
			bfrsubQueryVent.append(" where trunc( sol.fecSolicitud ) between TO_DATE(:fechaSolicitudInicial ,'yyyy-MM-dd') and TO_DATE(:fechaSolicitudFinal , 'yyyy-MM-dd') ");
		}
		bfrsubQueryVent.append(" and sol.dicTipoSolicitud.cveIdTipoSolicitud = :cveTiposSol");
		bfrsubQueryVent.append(" and sol.dicEstadoSolicitud.cveIdEstadoSolicitud = :cveEstadoSol");
		bfrsubQueryVent.append(" group by sol.dicOrigenSolicitud.cveIdOrigenSolicitud, tram.dicTipoTramite.cveIdTipoTramite");

		bfrsubQueryVent.append(" having sol.dicOrigenSolicitud.cveIdOrigenSolicitud in( :listIdOrigenSol)");
		bfrsubQueryVent.append(" and tram.dicTipoTramite.cveIdTipoTramite in ( :listIdTipoTramite) ");

		Query querySol = session.createQuery(bfrsubQueryVent.toString());

		querySol.setParameter("fechaSolicitudInicial", strFechaSolicitudInicial);
		querySol.setParameter("fechaSolicitudFinal", strFechaSolicitudFinal);
		querySol.setParameter("cveTiposSol", filtro.getTipoSolicitud().getValor());
		querySol.setParameter("cveEstadoSol", filtro.getEstadoSolicitud().getId());

		List<Long> listIdOrigenSol = new ArrayList<Long>();
		for (OrigenSolicitudEnum origenSolicitud : filtro.getListOrigenSolicitud()) {
			listIdOrigenSol.add(origenSolicitud.getId());
		}
		querySol.setParameterList("listIdOrigenSol", listIdOrigenSol);

		List<Integer> listIdTipoTramite = new ArrayList<Integer>();
		for (TipoTramiteEnum tipoTramite : filtro.getListTipoTramite()) {
			listIdTipoTramite.add(tipoTramite.getCodigo());
		}
		querySol.setParameterList("listIdTipoTramite", listIdTipoTramite);

		List<EstadisticasAsignacion> list = querySol.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadisticasAsegurados> findEstadisticasAsegurados(
			FiltroEstadisticaAsignacion filtro) {
		Session session = sessionFactory.getCurrentSession();

		StringBuffer bfrsubQueryVent = new StringBuffer();
		bfrsubQueryVent.append("select new mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.EstadisticasAsegurados(");
		bfrsubQueryVent.append("asig.numNss, sol.refFolio, sol.fecSolicitud, sol.fecConclusion");
		bfrsubQueryVent.append(")");
		bfrsubQueryVent.append(" from DitTramite tram");
		bfrsubQueryVent.append(" inner join tram.ditSolicitud as sol");
		bfrsubQueryVent.append(" left outer join sol.dicSubdelegacion.dicDelegacion as deleg");
		bfrsubQueryVent.append(" inner join tram.ditTramitePersonaFisica as tramfis");
		bfrsubQueryVent.append(" inner join tramfis.ditPersona.ditAsignacionNsses as asig");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strFechaSolicitudInicial = sdf.format(filtro.getFechaSolicitudInicial());
		String strFechaSolicitudFinal = sdf.format(filtro.getFechaSolicitudFinal());
		bfrsubQueryVent.append(" where sol.fecSolicitud between TO_DATE(:fechaSolicitudInicial ,'yyyy-MM-dd HH24:MI') and TO_DATE(:fechaSolicitudFinal , 'yyyy-MM-dd HH24:MI') ");
		bfrsubQueryVent.append(" and sol.dicTipoSolicitud.cveIdTipoSolicitud = :cveTiposSol");
		bfrsubQueryVent.append(" and sol.dicEstadoSolicitud.cveIdEstadoSolicitud = :cveEstadoSol");

		bfrsubQueryVent.append(" and sol.dicOrigenSolicitud.cveIdOrigenSolicitud in( :listIdOrigenSol)");
		bfrsubQueryVent.append(" and tram.dicTipoTramite.cveIdTipoTramite in ( :listIdTipoTramite) ");
		bfrsubQueryVent.append(" order by sol.cveIdSolicitud asc ");

		Query querySol = session.createQuery(bfrsubQueryVent.toString());

		querySol.setParameter("fechaSolicitudInicial", strFechaSolicitudInicial);
		querySol.setParameter("fechaSolicitudFinal", strFechaSolicitudFinal);
		querySol.setParameter("cveTiposSol", filtro.getTipoSolicitud().getValor());
		querySol.setParameter("cveEstadoSol", filtro.getEstadoSolicitud().getId());

		List<Long> listIdOrigenSol = new ArrayList<Long>();
		for (OrigenSolicitudEnum origenSolicitud : filtro.getListOrigenSolicitud()) {
			listIdOrigenSol.add(origenSolicitud.getId());
		}
		querySol.setParameterList("listIdOrigenSol", listIdOrigenSol);

		List<Integer> listIdTipoTramite = new ArrayList<Integer>();
		for (TipoTramiteEnum tipoTramite : filtro.getListTipoTramite()) {
			listIdTipoTramite.add(tipoTramite.getCodigo());
		}
		querySol.setParameterList("listIdTipoTramite", listIdTipoTramite);

		List<EstadisticasAsegurados> listAsegurados = querySol.list();
		return listAsegurados;
	}
}
