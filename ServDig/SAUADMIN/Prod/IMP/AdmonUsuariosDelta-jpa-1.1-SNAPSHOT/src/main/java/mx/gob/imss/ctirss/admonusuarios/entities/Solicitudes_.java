package mx.gob.imss.ctirss.admonusuarios.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-04T16:55:00.336-0600")
@StaticMetamodel(Solicitudes.class)
public class Solicitudes_ {
	public static volatile SingularAttribute<Solicitudes, Long> idSolicitud;
	public static volatile SingularAttribute<Solicitudes, String> nomNombre;
	public static volatile SingularAttribute<Solicitudes, String> nomPaterno;
	public static volatile SingularAttribute<Solicitudes, String> nomMaterno;
	public static volatile SingularAttribute<Solicitudes, String> nomCorreoElectonico;
	public static volatile SingularAttribute<Solicitudes, Date> registroAlta;
	public static volatile SingularAttribute<Solicitudes, Date> registroBaja;
	public static volatile SingularAttribute<Solicitudes, Date> registroActualizado;
	public static volatile SingularAttribute<Solicitudes, String> nomMatricula;
	public static volatile SingularAttribute<Solicitudes, Delegacion> delegacion;
	public static volatile SingularAttribute<Solicitudes, Subdelegacion> subdelegacion;
	public static volatile SingularAttribute<Solicitudes, UnidadMedicaFamiliar> unidadMedicaFamiliar;
	public static volatile SingularAttribute<Solicitudes, Long> estatus;
	public static volatile SingularAttribute<Solicitudes, String> curp;
	public static volatile SingularAttribute<Solicitudes, Date> fechaNacimiento;
	public static volatile SingularAttribute<Solicitudes, Departamento> departamento;
	public static volatile SingularAttribute<Solicitudes, Puesto> puesto;
	public static volatile SingularAttribute<Solicitudes, Long> estado;
	public static volatile SingularAttribute<Solicitudes, String> telefono;
}
