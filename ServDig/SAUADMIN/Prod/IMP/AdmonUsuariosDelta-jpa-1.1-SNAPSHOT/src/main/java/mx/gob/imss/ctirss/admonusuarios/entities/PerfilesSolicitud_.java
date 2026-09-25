package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.245-0600")
@StaticMetamodel(PerfilesSolicitud.class)
public class PerfilesSolicitud_ {
	public static volatile SingularAttribute<PerfilesSolicitud, Long> idPerfilesSolicitud;
	public static volatile SingularAttribute<PerfilesSolicitud, Solicitudes> solicitud;
	public static volatile SingularAttribute<PerfilesSolicitud, Puesto> puesto;
	public static volatile SingularAttribute<PerfilesSolicitud, Date> fechaRegistro;
	public static volatile SingularAttribute<PerfilesSolicitud, String> defaultRol;
}
