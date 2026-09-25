package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.333-0600")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Long> idUsuario;
	public static volatile SingularAttribute<Usuario, String> nombreSistema;
	public static volatile SingularAttribute<Usuario, String> refPassword;
	public static volatile SingularAttribute<Usuario, String> nombre;
	public static volatile SingularAttribute<Usuario, String> paterno;
	public static volatile SingularAttribute<Usuario, String> materno;
	public static volatile SingularAttribute<Usuario, Long> idPersona;
	public static volatile SingularAttribute<Usuario, Long> tipUsuario;
	public static volatile SingularAttribute<Usuario, Long> idPerfil;
	public static volatile SingularAttribute<Usuario, Date> registroAlta;
	public static volatile SingularAttribute<Usuario, Date> registroBaja;
	public static volatile SingularAttribute<Usuario, Date> registroActualizado;
	public static volatile SingularAttribute<Usuario, String> curp;
}
