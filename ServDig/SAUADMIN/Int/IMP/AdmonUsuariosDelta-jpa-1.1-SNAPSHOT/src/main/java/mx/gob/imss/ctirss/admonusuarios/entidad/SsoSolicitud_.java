package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T12:14:53.592-0500")
@StaticMetamodel(SsoSolicitud.class)
public class SsoSolicitud_ {
	public static volatile SingularAttribute<SsoSolicitud, Long> cveSsosolicitud;
	public static volatile SingularAttribute<SsoSolicitud, String> cveIdEntidad;
	public static volatile SingularAttribute<SsoSolicitud, String> cveMatricula;
	public static volatile SingularAttribute<SsoSolicitud, String> desUsrCurp;
	public static volatile SingularAttribute<SsoSolicitud, Date> fecRegistroActualizado;
	public static volatile SingularAttribute<SsoSolicitud, Date> fecRegistroAlta;
	public static volatile SingularAttribute<SsoSolicitud, Date> fecRegistroBaja;
	public static volatile SingularAttribute<SsoSolicitud, Date> fecUsrNacimiento;
	public static volatile SingularAttribute<SsoSolicitud, String> nomMaterno;
	public static volatile SingularAttribute<SsoSolicitud, String> nomNombre;
	public static volatile SingularAttribute<SsoSolicitud, String> nomPaterno;
	public static volatile SingularAttribute<SsoSolicitud, String> refCorreoElectronico;
	public static volatile SingularAttribute<SsoSolicitud, String> desTelefonoOfi;
	public static volatile SetAttribute<SsoSolicitud, SsoAccesomodulo> ssoAccesomodulos;
	public static volatile SetAttribute<SsoSolicitud, SsoAprobador> ssoAprobadors;
	public static volatile SetAttribute<SsoSolicitud, SsoPerfilessol> ssoPerfilessols;
	public static volatile SingularAttribute<SsoSolicitud, DicDelegacion> dicDelegacion;
	public static volatile SingularAttribute<SsoSolicitud, DicSubdelegacion> dicSubdelegacion;
	public static volatile SingularAttribute<SsoSolicitud, DicUmf> dicUmf;
	public static volatile SingularAttribute<SsoSolicitud, SsoCatdepartamento> ssoCatdepartamento;
	public static volatile SingularAttribute<SsoSolicitud, SsoCatestatus> ssoCatestatus;
	public static volatile SingularAttribute<SsoSolicitud, SsoCatpuesto> ssoCatpuesto;
	public static volatile SingularAttribute<SsoSolicitud, String> nss;
	public static volatile SingularAttribute<SsoSolicitud, String> puesto;
	public static volatile SingularAttribute<SsoSolicitud, String> departamento;
	public static volatile SingularAttribute<SsoSolicitud, String> cveDelegacion;
	public static volatile SingularAttribute<SsoSolicitud, String> cveSubdelegacion;
	public static volatile SingularAttribute<SsoSolicitud, Long> estatus;
	public static volatile SingularAttribute<SsoSolicitud, String> cveUmf;
}
