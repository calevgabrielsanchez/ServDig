package mx.gob.imss.csdiss.sdroc.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.entity.RocEstatusObra;
import mx.gob.imss.csdiss.sdroc.entity.RocSubdelegacion;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoObra;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPatron;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPersona;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.entity.RotAvisoObra;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.csdiss.sdroc.entity.RotInformacionPatron;
import mx.gob.imss.csdiss.sdroc.entity.RotUbicacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.AvisoObraDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionPatronDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;

public class AvisoObraDaoTest extends BaseDaoTest {
	
	@Autowired
	private AvisoObraDao avisoObraDao;
	@Autowired
	private InformacionObraDao informacionObraDao;
	
	@Test
//	@Ignore
	public void consultarAvisoTest(){
		String cveAvisoObra = "41335174";
		RotAvisoObra aviso = avisoObraDao.findByCveRegistroAvisoObra(cveAvisoObra);
		Assert.assertFalse(aviso.equals(null));		
	}

}
