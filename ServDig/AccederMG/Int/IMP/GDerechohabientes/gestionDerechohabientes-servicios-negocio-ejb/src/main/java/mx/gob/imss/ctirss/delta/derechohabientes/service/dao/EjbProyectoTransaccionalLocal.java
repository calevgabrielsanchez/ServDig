package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabientes.AltaTransaccional;


@Local
public interface EjbProyectoTransaccionalLocal {

	public void alta(final AltaTransaccional altaTransaccional);
	
}
