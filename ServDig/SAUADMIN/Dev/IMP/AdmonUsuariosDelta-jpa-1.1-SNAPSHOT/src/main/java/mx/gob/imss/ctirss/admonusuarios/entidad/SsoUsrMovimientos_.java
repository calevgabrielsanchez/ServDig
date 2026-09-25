package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-08T11:46:59.958-0600")
@StaticMetamodel(SsoUsrMovimientos.class)
public class SsoUsrMovimientos_ {
	public static volatile SingularAttribute<SsoUsrMovimientos, Long> cveSsoUsrMovto;
	public static volatile SingularAttribute<SsoUsrMovimientos, SsoSolicitud> ssoSolicitud;
	public static volatile SingularAttribute<SsoUsrMovimientos, String> desDatosMovimientos;
	public static volatile SingularAttribute<SsoUsrMovimientos, SsoAprobador> ssoAprobador;
	public static volatile SingularAttribute<SsoUsrMovimientos, Date> fechaRegistro;
	public static volatile SingularAttribute<SsoUsrMovimientos, SsoCatestatus> ssoCatEstatus;
}
