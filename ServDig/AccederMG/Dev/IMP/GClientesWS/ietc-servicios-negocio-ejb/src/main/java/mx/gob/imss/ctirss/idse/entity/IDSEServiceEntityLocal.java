package mx.gob.imss.ctirss.idse.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.persistencia.Patrones;

@Local
public interface IDSEServiceEntityLocal {
	
	Patrones consultarRegistroPatronal(String nrp);
	void insertarNuevoRegistroPatronal(RegistroPatronal registroPatronal);
	void insertarNuevoRegistroPatronal(RequerimientoIDSEBean parametros);
}
