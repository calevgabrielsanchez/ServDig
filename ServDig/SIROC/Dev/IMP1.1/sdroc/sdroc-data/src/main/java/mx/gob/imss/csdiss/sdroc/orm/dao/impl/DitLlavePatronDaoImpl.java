package mx.gob.imss.csdiss.sdroc.orm.dao.impl;

import java.util.ArrayList;

import mx.gob.imss.csdiss.sdroc.entity.DitLlavePatron;
import mx.gob.imss.csdiss.sdroc.orm.dao.DitLlavePatronDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("DitLlavePatronDao")
@Transactional
public class DitLlavePatronDaoImpl extends AbstractDaoImpl<DitLlavePatron, Long> implements DitLlavePatronDao {



	public int findbyRpAndCp(String cveRp, String cveCodigoPostal) {
		
		Long resultado;
		
		String sql = "SELECT COUNT (DISTINCT dgcp.codigo) "
				+ " from DitLlavePatron llpat, DitPatronSujetoObligado pso, DitDelSubPatSujObligado dspso, RocSubdelegacion sdel, "
				+ " DitMunicipioSubdelegacion mpiosd, DicMunicipioImss mpioimss, DitMunicipioImssInegi mpioimsing, DgCodigosPostales dgcp "
				+ " where llpat.cveIdPatronSujetoObligado = pso.cveIdPatronSujetoObligado "
				+ " and pso.cveIdPatronSujetoObligado = dspso.cveIdPatronSujetoObligado "
				+ " and dspso.cveidsubdelegacion = sdel.cveSubdelegacion "
				+ " and sdel.cveSubdelegacion = mpiosd.cveIdSubdelegacion "
				+ " and mpiosd.cveIdMunicipioImss = mpioimss.cveIdMunicipioImss "
				+ " and mpioimss.cveIdMunicipioImss = mpioimsing.cveIdMunicipioImss "
				+ " and mpioimsing.cveMun = dgcp.cveMun "
				+ " and mpioimsing.cveEnt = dgcp.cveEnt "
				+ " and llpat.refBusca = :cveRp "
				+ " and dgcp.codigo = :cveCodigoPostal";


		
		resultado = (Long) this.getSession().createQuery(sql).setParameter("cveRp", cveRp).setParameter("cveCodigoPostal", cveCodigoPostal).uniqueResult();
		
		return resultado.intValue();
	}

	@Override
	public int validCircunscripcionCP(String codigoPostal, Long idDelegacion,Long idSubdelegacion) {
		Long resultado = 0L;
		boolean consultarMacroCirc= false;
		Long[] idsMacro = {39L, 40L};
		idDelegacion = idDelegacion != null && idDelegacion.intValue() != 0 ? idDelegacion : null; 
		idSubdelegacion = idSubdelegacion != null && idSubdelegacion.intValue() != 0 ? idSubdelegacion : null; 

		System.out.println("Voy a validar la correspondencia entre el codigoPostal " + codigoPostal + ", delegacion: " + idDelegacion + " y subdelegacion " + idSubdelegacion);
		String sql = "SELECT COUNT (DISTINCT dgcp.codigo) "
				+ " from RocSubdelegacion sdel, "
				+ " DitMunicipioSubdelegacion mpiosd, DicMunicipioImss mpioimss, DitMunicipioImssInegi mpioimsing, DgCodigosPostales dgcp "
				+ " inner join sdel.rocDelegacion delegacion"
				+ " where sdel.cveSubdelegacion = mpiosd.cveIdSubdelegacion "
				+ " and mpiosd.cveIdMunicipioImss = mpioimss.cveIdMunicipioImss "
				+ " and mpioimss.cveIdMunicipioImss = mpioimsing.cveIdMunicipioImss "
				+ " and mpioimsing.cveMun = dgcp.cveMun "
				+ " and mpioimsing.cveEnt = dgcp.cveEnt "
				+ " and dgcp.codigo = :cveCodigoPostal";

		consultarMacroCirc = idDelegacion != null && (idDelegacion.equals(39L) || idDelegacion.equals(40L) );
		if(consultarMacroCirc) {
			sql += " and delegacion.cveDelegacion in (:delegaciones)";
		} else {
			sql += " and sdel.cveSubdelegacion = :idSubdelegacion";
		}

		Query query = this.getSession().createQuery(sql);
		query.setParameter("cveCodigoPostal", codigoPostal);

		if(consultarMacroCirc) {
			query.setParameterList("delegaciones", idsMacro);
		} else {
			query.setParameter("idSubdelegacion", idSubdelegacion);
		}

		resultado = (Long) query.uniqueResult();
			
		return resultado.intValue();
	}


}
