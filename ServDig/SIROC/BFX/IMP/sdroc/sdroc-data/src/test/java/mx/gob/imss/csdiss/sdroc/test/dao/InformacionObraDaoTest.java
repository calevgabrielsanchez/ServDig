package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocEstatusObra;
import mx.gob.imss.csdiss.sdroc.entity.RocSubdelegacion;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoObra;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPatron;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPersona;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionPatron;
import mx.gob.imss.csdiss.sdroc.entity.RotUbicacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionPatronDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;

public class InformacionObraDaoTest extends BaseDaoTest {
	
	@Autowired
	private InformacionObraDao informacionObraDao;
	
	@Test
	public void InstanciaInformacionObraDaoTest(){   
		Assert.assertNotNull(informacionObraDao);
	}
	
	@Test
	public void consultarInformacionObrasTest(){
		
		List<RotInformacionObra> listaInformacionObras = null;
		
		listaInformacionObras = (List<RotInformacionObra>) informacionObraDao.findAll();
		Assert.assertFalse(listaInformacionObras.isEmpty());		

	}
	
	@Test
	public void consultarInformacionObraPorCveRegistroObraTest(){
		
		RotInformacionObra rotInformacionObra = null;
		
		rotInformacionObra = informacionObraDao.findByCveRegistroObra(new String("160101110001"));
		Assert.assertNotNull(rotInformacionObra);		

	}
	
	@Test
	public void consultarInformacionObraPorCveRfcyCveRegPatronalTest(){
		
		List<RotInformacionObra> listRotInformacionObra = null;
		
		listRotInformacionObra = informacionObraDao.findByRfcAndRp("HEGB820907l48", "Y00890912");
		Assert.assertNotNull(listRotInformacionObra);		

	}
	
	@Test
	public void consultarNumInformacionObraByCveRfcAndAnioTest(){
		
		int numInfomacionObras;
		
		numInfomacionObras = informacionObraDao.numInformacionObraByCveRfcAndAnio("IMS421231I45", "2016");
		Assert.assertNotNull(numInfomacionObras);		

	}
	
	@Test
	public void consultarformacionObraByCveRfcAndAnioTest(){
		
		List<RotInformacionObra> listRotInformacionObra = null;
		
		listRotInformacionObra = informacionObraDao.findInformacionObraByCveRfcAndAnio("IMS421231I45", "2016");
		Assert.assertNotNull(listRotInformacionObra);		

	}
	
	@Test
	public void consultarformacionObraAgrupadaByCveRfc(){
		
		List<Object[]> listRotInformacionObra = null;
		
		listRotInformacionObra = informacionObraDao.findAllByCveRfcGroupByCveRegPatronalAndSubdelegacionAndDelegacion("IMS421231I45");
		Assert.assertNotNull(listRotInformacionObra);		

	}	
	
	@Test
	public void consultarInformacionObraPorCveRfcPrincipalTest(){
		
		List<RotInformacionObra> listRotInformacionObra = null;
		
		listRotInformacionObra = informacionObraDao.findAllByCveRegistroObraPrincipal(new Long("160101110001"));
		Assert.assertNotNull(listRotInformacionObra);		

	}
	
	@Test
	public void insertarInformacionObraTest(){
		RotInformacionObra rotInformacionObra = new RotInformacionObra();
		RotInformacionPatron rotInformacionPatron = new RotInformacionPatron();
		RotUbicacionObra rotUbicacionObra = new RotUbicacionObra();
		
		Long IdInformacionObra = null;
		
		
//		rotUbicacionObra.setCalle("Tokio");
//		rotUbicacionObra.setCodigoPostal("06600");
//		rotUbicacionObra.setNumExterior("80");
//		rotUbicacionObra.setNumInterior("2do Piso");
		
		rotInformacionPatron.setCveRegPatronal("Y00890912");
		rotInformacionPatron.setCveRfc("HEGB820907l48");
		rotInformacionPatron.setNomPatron("Brian");
		rotInformacionPatron.setRefApellidoMaterno("Garcia");
		rotInformacionPatron.setRefApellidoPaterno("Hernandez");
		
		RocTipoPatron rocTipoPatron = new RocTipoPatron();
		RocTipoPersona rocTipoPersona = new RocTipoPersona();
		
		rocTipoPatron.setCveTipoPatron(1L);
		rocTipoPersona.setCveTipoPersona(1L);
		
		rotInformacionPatron.setRocTipoPatron(rocTipoPatron);
		rotInformacionPatron.setRocTipoPersona(rocTipoPersona);
		
		rotInformacionObra.setCveRegistroObra(new String("160101110001"));
		rotInformacionObra.setImpContratado(new Double("23.4"));
		rotInformacionObra.setRefObservacion("lsdjkasldkañ");
		
		RocEstatusObra rocEstatusObra = new RocEstatusObra();
		rocEstatusObra.setCveEstatusObra(new Long(1));
		
		rotInformacionObra.setRocEstatusObra(rocEstatusObra);
		
		RocSubdelegacion rocSubdelegacion = new RocSubdelegacion();
		rocSubdelegacion.setCveSubdelegacion(1L);
		
		rotInformacionObra.setRocSubdelegacion(rocSubdelegacion);
		
		RocTipoObra rocTipoObra = new RocTipoObra();
		rocTipoObra.setCveTipoObra(1L);
				
		rotInformacionObra.setRocTipoObra(rocTipoObra);
		rotInformacionObra.setRotInformacionPatron(rotInformacionPatron);
		
		rotInformacionObra.setRotUbicacionObras(rotUbicacionObra);
		
		
		IdInformacionObra = informacionObraDao.save(rotInformacionObra);
		Assert.assertNotNull(IdInformacionObra);
		
	}
	
	@Test
	public void consultarCveRegistroObraTest(){
		
		Object cveRegistroObra  = null;
		
		cveRegistroObra = (Object) informacionObraDao.getCveRegistroObra();
		
		Assert.assertNotNull(cveRegistroObra);

	}
	
	@Test
	public void consultarReporteInformacionObra(){
		
		List<Object[]> resultado  = null;
		
		resultado = (List<Object[]>) informacionObraDao.findAllInformacionObraForReport("OERC8710217B9","2016");
		
		Assert.assertNotNull(resultado);

	}
	
	@Test
	public void consultarReporteInformacionObraByCveRegPatronal(){
		
		List<Object[]> resultado  = null;
		
		resultado = (List<Object[]>) informacionObraDao.findAllInformacionObraForReportByCvRegPatronal("C3555810100");
		
		Assert.assertNotNull(resultado);

	}	
}
