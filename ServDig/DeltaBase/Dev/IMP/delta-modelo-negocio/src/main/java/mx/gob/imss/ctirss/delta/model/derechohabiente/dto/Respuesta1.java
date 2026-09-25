package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.util.List;

public class Respuesta1 {

private String CODIGO;
private List<Table> Table;




/**
 * @return the table
 */
public List<Table> getTable() {
	return Table;
}
/**
 * @param table the table to set
 */
public void setTable(List<Table> table) {
	Table = table;
}
/**
 * @return the cODIGO
 */
public String getCODIGO() {
	return CODIGO;
}
/**
 * @param cODIGO the cODIGO to set
 */
public void setCODIGO(String cODIGO) {
	CODIGO = cODIGO;
}

}
