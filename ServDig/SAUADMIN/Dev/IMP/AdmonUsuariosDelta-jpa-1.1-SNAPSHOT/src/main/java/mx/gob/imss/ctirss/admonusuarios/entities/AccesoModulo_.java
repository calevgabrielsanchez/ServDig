package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:51.271-0600")
@StaticMetamodel(AccesoModulo.class)
public class AccesoModulo_ {
	public static volatile SingularAttribute<AccesoModulo, Long> cveAccesoModulo;
	public static volatile SingularAttribute<AccesoModulo, Solicitudes> solicitud;
	public static volatile SingularAttribute<AccesoModulo, DeptoModulo> deptoModulo;
	public static volatile SingularAttribute<AccesoModulo, Estatus> estatus;
	public static volatile SingularAttribute<AccesoModulo, Date> registroRegistro;
}
