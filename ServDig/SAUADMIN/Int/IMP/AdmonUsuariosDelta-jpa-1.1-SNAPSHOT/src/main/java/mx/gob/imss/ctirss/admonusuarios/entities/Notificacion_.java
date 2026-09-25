package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.229-0600")
@StaticMetamodel(Notificacion.class)
public class Notificacion_ {
	public static volatile SingularAttribute<Notificacion, Long> idNotificacion;
	public static volatile SingularAttribute<Notificacion, Long> tipoNotificcion;
	public static volatile SingularAttribute<Notificacion, Solicitudes> solicitud;
	public static volatile SingularAttribute<Notificacion, Date> fechaNotificacion;
}
