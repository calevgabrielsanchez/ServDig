package mx.gob.imss.ctirss.admonusuarios.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:51.388-0600")
@StaticMetamodel(Aprobadores.class)
public class Aprobadores_ {
	public static volatile SingularAttribute<Aprobadores, Long> idAprobador;
	public static volatile SingularAttribute<Aprobadores, String> matricula;
	public static volatile SingularAttribute<Aprobadores, Solicitudes> solicitud;
	public static volatile SingularAttribute<Aprobadores, Modulo> modulo;
}
