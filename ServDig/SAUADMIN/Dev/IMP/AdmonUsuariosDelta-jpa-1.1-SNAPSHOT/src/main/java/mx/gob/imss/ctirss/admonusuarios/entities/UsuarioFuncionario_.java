package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.348-0600")
@StaticMetamodel(UsuarioFuncionario.class)
public class UsuarioFuncionario_ {
	public static volatile SingularAttribute<UsuarioFuncionario, Long> idUsuarioFuncionario;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> idUsuario;
	public static volatile SingularAttribute<UsuarioFuncionario, String> descripcionCargo;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> idDelegacion;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> idSuddelegacion;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> centroTrabajo;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> numeroLada;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> numeroTelefonico;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> numExtension;
	public static volatile SingularAttribute<UsuarioFuncionario, String> correoElectronico;
	public static volatile SingularAttribute<UsuarioFuncionario, Date> registroAlta;
	public static volatile SingularAttribute<UsuarioFuncionario, Date> registroBaja;
	public static volatile SingularAttribute<UsuarioFuncionario, Date> registroActualizado;
	public static volatile SingularAttribute<UsuarioFuncionario, Long> idUMF;
}
