package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-10-29T18:51:08.325-0600")
@StaticMetamodel(DicDelegacion.class)
public class DicDelegacion_ {
	public static volatile SingularAttribute<DicDelegacion, Long> cveIdDelegacion;
	public static volatile SingularAttribute<DicDelegacion, String> anioIniOper;
	public static volatile SingularAttribute<DicDelegacion, String> claveDelegacion;
	public static volatile SingularAttribute<DicDelegacion, BigDecimal> cveCiz;
	public static volatile SingularAttribute<DicDelegacion, String> desDeleg;
	public static volatile SingularAttribute<DicDelegacion, String> domicilioId;
	public static volatile SingularAttribute<DicDelegacion, Date> fecRegistroActualizado;
	public static volatile SingularAttribute<DicDelegacion, Date> fecRegistroAlta;
	public static volatile SingularAttribute<DicDelegacion, Date> fecRegistroBaja;
	public static volatile SingularAttribute<DicDelegacion, BigDecimal> tipDelegacion;
	public static volatile SetAttribute<DicDelegacion, SsoSolicitud> ssoSolicituds;
}
