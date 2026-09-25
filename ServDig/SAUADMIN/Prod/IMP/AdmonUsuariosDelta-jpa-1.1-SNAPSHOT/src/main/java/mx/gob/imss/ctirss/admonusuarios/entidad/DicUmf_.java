package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-06T16:34:07.666-0600")
@StaticMetamodel(DicUmf.class)
public class DicUmf_ {
	public static volatile SingularAttribute<DicUmf, Long> cveIdUmf;
	public static volatile SingularAttribute<DicUmf, BigDecimal> anoInicio;
	public static volatile SingularAttribute<DicUmf, BigDecimal> cveIdClavePresupuestal;
	public static volatile SingularAttribute<DicUmf, BigDecimal> cveIdNivelAtencion;
	public static volatile SingularAttribute<DicUmf, BigDecimal> cveIdTipoUmf;
	public static volatile SingularAttribute<DicUmf, String> domicilioId;
	public static volatile SingularAttribute<DicUmf, Date> fecHoraRegistro;
	public static volatile SingularAttribute<DicUmf, Date> fecRegistroActualizado;
	public static volatile SingularAttribute<DicUmf, Date> fecRegistroAlta;
	public static volatile SingularAttribute<DicUmf, Date> fecRegistroBaja;
	public static volatile SingularAttribute<DicUmf, BigDecimal> indGeneracionCita;
	public static volatile SingularAttribute<DicUmf, String> nomCorto;
	public static volatile SingularAttribute<DicUmf, String> nomUnidad;
	public static volatile SingularAttribute<DicUmf, BigDecimal> numConsultorio;
	public static volatile SingularAttribute<DicUmf, BigDecimal> numEconom;
	public static volatile SingularAttribute<DicUmf, String> numMatricula;
	public static volatile SingularAttribute<DicUmf, DicSubdelegacion> dicSubdelegacion;
}
