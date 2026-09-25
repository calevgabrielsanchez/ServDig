package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.364-0600")
@StaticMetamodel(UsuarioMovimientos.class)
public class UsuarioMovimientos_ {
	public static volatile SingularAttribute<UsuarioMovimientos, Long> idMovimiento;
	public static volatile SingularAttribute<UsuarioMovimientos, Solicitudes> solicitud;
	public static volatile SingularAttribute<UsuarioMovimientos, Estatus> estatus;
	public static volatile SingularAttribute<UsuarioMovimientos, String> datosMovimiento;
	public static volatile SingularAttribute<UsuarioMovimientos, Aprobadores> aprobador;
	public static volatile SingularAttribute<UsuarioMovimientos, Date> fechaRegistro;
}
