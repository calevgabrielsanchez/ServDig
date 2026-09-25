package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-11-05T19:00:56.288-0600")
@StaticMetamodel(SolicitudesExternos.class)
public class SolicitudesExternos_ {
	public static volatile SingularAttribute<SolicitudesExternos, Long> idUsrExternos;
	public static volatile SingularAttribute<SolicitudesExternos, String> curp;
	public static volatile SingularAttribute<SolicitudesExternos, String> nomNombres;
	public static volatile SingularAttribute<SolicitudesExternos, String> nomPaterno;
	public static volatile SingularAttribute<SolicitudesExternos, String> nomMaterno;
	public static volatile SingularAttribute<SolicitudesExternos, String> correoElectonico;
	public static volatile SingularAttribute<SolicitudesExternos, String> nss;
	public static volatile SingularAttribute<SolicitudesExternos, Integer> estatus;
	public static volatile SingularAttribute<SolicitudesExternos, String> cveDelegacion;
	public static volatile SingularAttribute<SolicitudesExternos, String> cveSubDelegacion;
	public static volatile SingularAttribute<SolicitudesExternos, String> cveUmf;
	public static volatile SingularAttribute<SolicitudesExternos, Integer> cvePersonaBdtu;
	public static volatile SingularAttribute<SolicitudesExternos, String> desPerfil;
	public static volatile SingularAttribute<SolicitudesExternos, Date> registroAlta;
	public static volatile SingularAttribute<SolicitudesExternos, Date> registroBaja;
	public static volatile SingularAttribute<SolicitudesExternos, Date> registroActualizado;
}
