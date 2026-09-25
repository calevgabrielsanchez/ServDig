package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocMotivo;
import mx.gob.imss.csdiss.sdroc.entity.RocMotivoIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoIncidencia;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionIncidencia;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionIncidenciaDao;

public class InformacionIncidenciaDaoTest extends BaseDaoTest {
	
	@Autowired
	private InformacionIncidenciaDao informacionIncidenciaDao;
	
	@Test
	public void InstanciaInformacionIncidenciaDaoTest(){   
		Assert.assertNotNull(informacionIncidenciaDao);
	}
	
	
	
	@Test
	public void consultarInformacionIncidenciasTest(){
		

		List<RotInformacionIncidencia> listaInformacionIncidencias = null;
		
		listaInformacionIncidencias = (List<RotInformacionIncidencia>) informacionIncidenciaDao.findAll();
		for (RotInformacionIncidencia rotInformacionIncidencia : listaInformacionIncidencias) {
			rotInformacionIncidencia.getRocMotivoTipoIncidencia();
		}
		
		Assert.assertFalse(listaInformacionIncidencias.isEmpty());
		

	}
	
	
	@Test
	public void insertarInformacionIncidenciaTest(){
		try {
			RotInformacionIncidencia incidencia = new RotInformacionIncidencia();
			
			
			RocMotivoIncidencia rocMotivoTipoIncidencia = new RocMotivoIncidencia();
			rocMotivoTipoIncidencia.setCveMotivoTipoIncidencia(1L);
			
			RocMotivo motivo = new RocMotivo();
			motivo.setCveMotivo(1L);
			rocMotivoTipoIncidencia.setRocMotivo(motivo);
			RocTipoIncidencia tipoIncidencia = new RocTipoIncidencia();
			tipoIncidencia.setCveTipoIncidencia(new Long(1));
			rocMotivoTipoIncidencia.setRocTipoIncidencia(tipoIncidencia);
			
			RocTipoRegistro tipoRegistro = new RocTipoRegistro();
			tipoRegistro.setCveTipoRegistro(2L);
			
			incidencia.setCveInformacionIncidencia(1L);
			incidencia.setCveInformacionObra(60L );
			incidencia.setRocMotivoTipoIncidencia(rocMotivoTipoIncidencia);
			incidencia.setRocTipoRegistro(tipoRegistro);
//			incidencia.setStpRegIncidencia(stpRegIncidencia);
//			incidencia.setFecFinObra(fecFinObra);
//			incidencia.setImpObra(impObra);
//			incidencia.setRefSupConstruccion(refSupConstruccion);
//			incidencia.setImpEjercido(impEjercido);
//			incidencia.setRefMotivoAct(refMotivoAct);
//			incidencia.setRefAcuseInc(refAcuseInc);
			
			informacionIncidenciaDao.save(incidencia);
		} catch (Exception e) {
			e.printStackTrace();
//			 TODO: handle exception
		}
		
	}
	
	@Test
	public void numReporBimestralByCveInfoNoReportadaTest(){

		int resultado = 0;
		
		resultado = informacionIncidenciaDao.numReporBimestralByCveInfoNoReportada(new Long(2658));
		
		Assert.assertNotEquals(resultado, 0);
		
	}
}
