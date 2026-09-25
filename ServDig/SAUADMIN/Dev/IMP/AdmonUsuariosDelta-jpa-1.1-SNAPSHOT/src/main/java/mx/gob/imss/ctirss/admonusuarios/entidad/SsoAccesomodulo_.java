package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-06T11:30:53.255-0600")
@StaticMetamodel(SsoAccesomodulo.class)
public class SsoAccesomodulo_ {
	public static volatile SingularAttribute<SsoAccesomodulo, Long> cveSsoaccesomodulo;
	public static volatile SingularAttribute<SsoAccesomodulo, Date> fecFechareg;
	public static volatile SingularAttribute<SsoAccesomodulo, SsoAprobador> ssoAprobador;
	public static volatile SingularAttribute<SsoAccesomodulo, SsoCatdeptomodulo> ssoCatdeptomodulo;
	public static volatile SingularAttribute<SsoAccesomodulo, SsoCatestatus> ssoCatestatus;
	public static volatile SingularAttribute<SsoAccesomodulo, SsoSolicitud> ssoSolicitud;
}
