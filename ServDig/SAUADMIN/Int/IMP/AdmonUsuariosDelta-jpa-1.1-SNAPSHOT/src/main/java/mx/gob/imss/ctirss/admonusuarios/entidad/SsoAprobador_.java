package mx.gob.imss.ctirss.admonusuarios.entidad;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-18T12:51:17.173-0600")
@StaticMetamodel(SsoAprobador.class)
public class SsoAprobador_ {
	public static volatile SingularAttribute<SsoAprobador, Long> cveIdAprobador;
	public static volatile SingularAttribute<SsoAprobador, String> cveMatricula;
	public static volatile SingularAttribute<SsoAprobador, SsoSolicitud> ssoSolicitud;
	public static volatile SingularAttribute<SsoAprobador, SsoCatestatus> ssoCatestatus;
	public static volatile SingularAttribute<SsoAprobador, Long> admingral;
}
