package mx.gob.imss.ctirss.support.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class OrderToNumber extends Order
{
  private static final String SQL_TO_NUMBER_SENTENCE = " to_number";
  private String propertyName;
  private boolean ascending;
  private static final long serialVersionUID = 1L;
  
  protected OrderToNumber(String propertyName, boolean ascending) {
    super(propertyName, ascending);
    this.propertyName = propertyName;
    this.ascending = ascending;
  }
  
  public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    String columnName = criteriaQuery.getColumn(criteria, this.propertyName);
    StringBuffer bfr = new StringBuffer();
    bfr.append(" to_number");
    bfr.append("(");
    bfr.append(columnName);
    bfr.append(") ");
    bfr.append(this.ascending ? "asc" : "desc");
    return bfr.toString();
  }
  
  public static Order toNumberAsc(String propertyName) {
    return new mx.gob.imss.ctirss.support.dao.hibernate.OrderToNumber(propertyName, true);
  }
 
  public static Order toNumberDesc(String propertyName) {
    return new mx.gob.imss.ctirss.support.dao.hibernate.OrderToNumber(propertyName, false);
  }
}

