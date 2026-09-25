/**
 * RBGSoftware setting java code convention measurements 
 * 2013.07.23
 */
package mx.gob.imss.common.utils.readAndExportXLS.vo;

public class Location {
	private Integer column;
	private Integer row;

	public Location() {
	}

	public Location(Integer row, Integer column) {
		setRow(row);
		setColumn(column);
	}

	public static void main(String[] args) {
		Location l = new Location(Integer.valueOf(1), "Az");
		l.getRow();
	}

	public Location(Integer row, String columnLeter) {
		Character c = null;
		int literalDecimal = 0;
		if (columnLeter.length() <= 1) {
			c = Character.valueOf(columnLeter.toUpperCase().charAt(0));
			literalDecimal = c.charValue();
			literalDecimal -= 65;
		} else {
			boolean primeraEjecucion = true;
			for (int i = 0; i < columnLeter.length(); i++) {
				c = Character.valueOf(columnLeter.toUpperCase().charAt(i));
				literalDecimal = c.charValue();
				if (primeraEjecucion) {
					literalDecimal -= 65;
					primeraEjecucion = false;
				} else {
					literalDecimal = literalDecimal - 65 + 26;
				}

			}

		}

		setColumn(Integer.valueOf(literalDecimal));
		setRow(Integer.valueOf(row.intValue() - 1));
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getColumn() {
		return column;
	}

	public void setRow(Integer rowIn) {
		row = rowIn;
	}

	public Integer getRow() {
		return row;
	}
}
