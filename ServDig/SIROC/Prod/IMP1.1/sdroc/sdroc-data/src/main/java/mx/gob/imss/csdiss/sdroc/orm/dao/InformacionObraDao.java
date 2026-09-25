package mx.gob.imss.csdiss.sdroc.orm.dao;

import java.util.List;
import java.util.Map;

import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface InformacionObraDao extends AbstractDao<RotInformacionObra, Long> {
	
	SujetoObligado getInfoPatron(String rp);
	
	Map<String, Object> findObrasByRfcAndRp(String rfc, String rp, Long inicio, Long fin);
	
	public List<RotInformacionObra> findByRfcAndRp(String cveRfc, String cvRegPatronal);
	
	public List<RotInformacionObra> findByCveRfc(String cveRfc);
	
	public List<RotInformacionObra> findByCvRegPatronal(String cvRegPatronal);
	
	public RotInformacionObra findByCveRegistroObra(String cveRegistroObra);
	
	public List<RotInformacionObra> findAllByCveRegistroObraPrincipal(Long cveRegistroObraPrincipal);
	
	public List<Object[]> findAllByCveRfcGroupByCveRegPatronalAndSubdelegacionAndDelegacion(String cveRfc);
	
	public void updateInformacionObran(Long cveInformacionObra,Long cveEstatusObra, String cadImpEjercido);
	
	public int numInformacionObraByCveRfcAndAnio(String cveRfc, String anio);
	
	public List<RotInformacionObra> findInformacionObraByCveRfcAndAnio(String cveRfc, String anio);

	public RotInformacionObra findByCveInformacionObra(Long cveInformacionObra);
	
	public 	List<Object[]> findAllInformacionObraForReport(String cveRfc, String anio);
	
	public 	List<Object[]> findAllInformacionObraForReportByCvRegPatronal(String cvRegPatronal);
	
	public String getCveRegistroObra();

	public RotInformacionObra consultaBloqueoRegistroObra(String cveRegistroObra);
	
	public void updateBloqueo(String cveIdUsuarioBloqueo,String cveIdInformacionObra);
	
	public void liberaObras(String cveIdUsuarioBloqueo);
}
