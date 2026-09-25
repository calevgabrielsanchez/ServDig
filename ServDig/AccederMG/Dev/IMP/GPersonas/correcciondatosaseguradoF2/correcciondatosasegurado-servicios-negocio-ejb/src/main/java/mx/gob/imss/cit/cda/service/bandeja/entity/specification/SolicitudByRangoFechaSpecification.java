package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import org.hibernate.SQLQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ErikPM
 */
public class SolicitudByRangoFechaSpecification extends BaseSpecification {
  private final String dias;
  public SolicitudByRangoFechaSpecification(String dias){
    this.dias = dias;
  }
  
  @Override
  public String prepareSQL(){
    return " ( sol.FEC_REGISTRO_ACTUALIZADO BETWEEN TO_DATE(:fechaActualizacionInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionFinal,'dd/MM/YYYY HH24:mi:ss') ) ";
  }

  @Override
  public void setParameter(SQLQuery query) {

    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, -Integer.parseInt(this.dias));

    query.setParameter("fechaActualizacionInicial", new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()) + " 00:00:00");
    query.setParameter("fechaActualizacionFinal", new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " 23:59:59");
  }

  
}
