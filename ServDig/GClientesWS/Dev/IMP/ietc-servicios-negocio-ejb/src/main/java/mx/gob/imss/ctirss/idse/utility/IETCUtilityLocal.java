package mx.gob.imss.ctirss.idse.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;
import mx.gob.imss.ctirss.idse.persistencia.IftDatosCertificado;

@Local
public interface IETCUtilityLocal {
	IftDatosCertificado convertirPkcs7AIftDatosCertificado(RequerimientoIETCBean reqIetcBean,Long cveRequerimiento);
	
	IftDatosCertificado combinarInfoCertificado(IftDatosCertificado source, IftDatosCertificado target);
}
