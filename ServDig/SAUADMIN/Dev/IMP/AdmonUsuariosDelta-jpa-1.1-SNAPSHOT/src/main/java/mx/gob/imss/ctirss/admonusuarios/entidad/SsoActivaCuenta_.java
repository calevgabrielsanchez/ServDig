package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-25T20:51:10.219-0600")
@StaticMetamodel(SsoActivaCuenta.class)
public class SsoActivaCuenta_ {
	public static volatile SingularAttribute<SsoActivaCuenta, Long> ssoClaveActiva;
	public static volatile SingularAttribute<SsoActivaCuenta, SsoSolicitud> ssoSolicitud;
	public static volatile SingularAttribute<SsoActivaCuenta, SsoCatestatus> ssoEstatus;
	public static volatile SingularAttribute<SsoActivaCuenta, String> claveMD5;
	public static volatile SingularAttribute<SsoActivaCuenta, Date> fechaRegistro;
	public static volatile SingularAttribute<SsoActivaCuenta, Date> fechaVigencia;
	public static volatile SingularAttribute<SsoActivaCuenta, Date> fechaActiva;
}
