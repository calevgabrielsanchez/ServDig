package mx.gob.imss.ctirss.admonusuarios.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.303-0600")
@StaticMetamodel(Subdelegacion.class)
public class Subdelegacion_ {
	public static volatile SingularAttribute<Subdelegacion, Long> idSubdelegacion;
	public static volatile SingularAttribute<Subdelegacion, Delegacion> delegacion;
	public static volatile SingularAttribute<Subdelegacion, String> descripcionSubelegacion;
	public static volatile SingularAttribute<Subdelegacion, String> anoIniOperacion;
	public static volatile SingularAttribute<Subdelegacion, String> claveSubdelegacion;
	public static volatile SingularAttribute<Subdelegacion, Integer> idDomicilio;
}
