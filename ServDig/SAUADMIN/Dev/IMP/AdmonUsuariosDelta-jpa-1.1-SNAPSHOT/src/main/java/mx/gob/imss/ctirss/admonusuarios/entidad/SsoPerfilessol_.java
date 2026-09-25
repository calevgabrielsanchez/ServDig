package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-07T20:37:47.008-0600")
@StaticMetamodel(SsoPerfilessol.class)
public class SsoPerfilessol_ {
	public static volatile SingularAttribute<SsoPerfilessol, Long> cveSsoperfilessol;
	public static volatile SingularAttribute<SsoPerfilessol, SsoCatpuesto> ssoCatpuesto;
	public static volatile SingularAttribute<SsoPerfilessol, SsoSolicitud> ssoSolicitud;
	public static volatile SingularAttribute<SsoPerfilessol, Date> fecFechaRegistro;
	public static volatile SingularAttribute<SsoPerfilessol, String> desDefault;
}
