package mx.gob.imss.ctirss.admonusuarios.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:51.434-0600")
@StaticMetamodel(Departamento.class)
public class Departamento_ {
	public static volatile SingularAttribute<Departamento, Long> cveDepartamento;
	public static volatile SingularAttribute<Departamento, String> nombreDepto;
	public static volatile SingularAttribute<Departamento, AreaNormativa> areaNormativa;
	public static volatile SingularAttribute<Departamento, Departamento> departamentoPadre;
}
