package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-12T13:37:15.936-0600")
@StaticMetamodel(SsoNotificacion.class)
public class SsoNotificacion_ {
	public static volatile SingularAttribute<SsoNotificacion, Long> cveIdSsoNotificacion;
	public static volatile SingularAttribute<SsoNotificacion, Date> fecFechaNotificacion;
	public static volatile SingularAttribute<SsoNotificacion, SsoSolicitud> ssoSolicitud;
	public static volatile SingularAttribute<SsoNotificacion, SsoCatestatus> ssoCatestatus;
}
