package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-10-29T18:54:42.083-0600")
@StaticMetamodel(DicSubdelegacion.class)
public class DicSubdelegacion_ {
	public static volatile SingularAttribute<DicSubdelegacion, Long> cveIdSubdelegacion;
	public static volatile SingularAttribute<DicSubdelegacion, String> anioIniOper;
	public static volatile SingularAttribute<DicSubdelegacion, String> claveSubdelegacion;
	public static volatile SingularAttribute<DicSubdelegacion, DicDelegacion> dicDelegacion;
	public static volatile SingularAttribute<DicSubdelegacion, String> desSubdelegacion;
	public static volatile SingularAttribute<DicSubdelegacion, String> domicilioId;
	public static volatile SingularAttribute<DicSubdelegacion, Date> fecRegistroActualizado;
	public static volatile SingularAttribute<DicSubdelegacion, Date> fecRegistroAlta;
	public static volatile SingularAttribute<DicSubdelegacion, Date> fecRegistroBaja;
	public static volatile SetAttribute<DicSubdelegacion, DicUmf> dicUmfs;
	public static volatile SetAttribute<DicSubdelegacion, SsoSolicitud> ssoSolicituds;
}
