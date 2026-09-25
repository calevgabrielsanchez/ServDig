package mx.gob.imss.ctirss.idse.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;
import mx.gob.imss.ctirss.idse.persistencia.IftDatosCertificado;

@Local
public interface IETCServiceEntityLocal {
	
	IftDatosCertificado consultarCertificado(String cveSerial);
	void insertaCertificado(RequerimientoIETCBean certificado);
	
}
