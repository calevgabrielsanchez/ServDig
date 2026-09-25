package mx.gob.imss.csdiss.sdroc.test.ejb;


import java.util.List;

import javax.ejb.EJB;

import org.junit.Assert;
import org.junit.Test;

import mx.gob.imss.csdiss.sdroc.dto.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;

public class TipoObraServiceEJBTest{
	
	@EJB
	private TipoObraService tipoObraServiceEBJ;

	@Test
	public void tipoObraServicioDependenciaTest(){
		Assert.assertNotNull(tipoObraServiceEBJ);
	}
	
	@Test
	public void consultarTiposDesObrasEJBTest(){
		
		List<TipoObraDTO> listaTiposObras = tipoObraServiceEBJ.consultarTiposDeObras();
		Assert.assertFalse(listaTiposObras.isEmpty());
	}
}
