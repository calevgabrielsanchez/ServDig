package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T18:32:57.505-0600")
@StaticMetamodel(SsoUserExternos.class)
public class SsoUserExternos_ {
	public static volatile SingularAttribute<SsoUserExternos, Long> cveSssoUsrExternos;
	public static volatile SingularAttribute<SsoUserExternos, String> desUsrCurp;
	public static volatile SingularAttribute<SsoUserExternos, String> nomNombre;
	public static volatile SingularAttribute<SsoUserExternos, String> nomPaterno;
	public static volatile SingularAttribute<SsoUserExternos, String> nomMaterno;
	public static volatile SingularAttribute<SsoUserExternos, String> refCorreoElectronico;
	public static volatile SingularAttribute<SsoUserExternos, String> cveNss;
	public static volatile SingularAttribute<SsoUserExternos, Integer> cveSsoEstatus;
	public static volatile SingularAttribute<SsoUserExternos, String> cveDelegacionImss;
	public static volatile SingularAttribute<SsoUserExternos, String> cveSubDelegacionImss;
	public static volatile SingularAttribute<SsoUserExternos, String> cveUmfImss;
	public static volatile SingularAttribute<SsoUserExternos, Integer> cvePersonaBdtu;
	public static volatile SingularAttribute<SsoUserExternos, String> desPerfil;
	public static volatile SingularAttribute<SsoUserExternos, Date> fecRegistroAlta;
	public static volatile SingularAttribute<SsoUserExternos, Date> fecRegistroBaja;
	public static volatile SingularAttribute<SsoUserExternos, Date> fecRegistroActualizado;
}
