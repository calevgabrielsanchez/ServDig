package mx.gob.imss.csdiss.sdroc.orm.dao;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.entity.RotInformacionIncidencia;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface InformacionIncidenciaDao extends AbstractDao<RotInformacionIncidencia, Long> {
	
	public RotInformacionIncidencia findLastIncidenciaByTipoIncidenciaByCveInformacionObra(Long cveInformacionObra, Long cveTipoIncidencia);
	
	public int numReporteBimestralByCveInformacionObra(Long cveInformacionObra);
	
	public RotInformacionIncidencia findLastReporteBimestralByCveInformacionObra(Long cveInformacionObra);	

	public int numReporBimestralByCveInfoNoReportada(Long cveInformacionObra);
	
	public int numReporBimestralByCveInfoPresentadas(Long cveInformacionObra, Long cveBimCalendario, int annio);
	
	public RotInformacionIncidencia findLastReporteBimPresenByCveInfoObra(Long cveInformacionObra);

	public List<RotInformacionIncidencia> findAllByCveInformacionObra(String cveInformacionObra);
	
	public void eliminaReporteBimByCveInfoObra(Long cveInformacionObra, String fechaFinObra, int annio);

	public void eliminaReporteBimByCveInfoObraDeclarado(Long cveInformacionObra, String fechaFinObra, int annio);

	public void eliminaReportesBimestralesNoPresentados(Long cveInformacionObra);

}
