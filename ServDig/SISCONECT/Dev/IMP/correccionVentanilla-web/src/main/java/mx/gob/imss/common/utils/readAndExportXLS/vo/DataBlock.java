/**
 * RBGSoftware clean service
 * 
 * 2013.07.23
 */

package mx.gob.imss.common.utils.readAndExportXLS.vo;

import java.util.ArrayList;
import java.util.List;

public class DataBlock {
	public static final Integer COLUM = Integer.valueOf(1);
	public static final Integer ROW = Integer.valueOf(2);
	private Location initialLocation;
	private Location endLocation;
	private List<ResourceDataXLS> elements;
	private Integer topLimitBlock;
	private Integer bottomLimitBlock;
	private Integer firstCellInBlockRow;
	private Integer firstCellInBlockColumn;
	private Integer lastCellInBlockRow;
	private Integer lastCellInBlockColumn;
	private Integer blockType;

	public DataBlock(Integer topLimitBlock, Integer bottomLimitBlock,
			Integer firstCellInBlockRow, Integer firstCellInBlockColumn,
			Integer lastCellInBlockRow, Integer lastCellInBlockColumn,
			Integer blockType) throws Exception {
		if ((blockType.intValue() != ROW.intValue())
				&& (blockType.intValue() != COLUM.intValue())) {
			throw new Exception("The value of the blockType its invalid");
		}
		this.initialLocation = new Location();
		this.initialLocation.setRow(firstCellInBlockRow);
		this.initialLocation.setColumn(firstCellInBlockColumn);

		this.endLocation = new Location();
		this.endLocation.setRow(lastCellInBlockRow);
		this.endLocation.setColumn(lastCellInBlockColumn);

		this.topLimitBlock = topLimitBlock;
		this.bottomLimitBlock = bottomLimitBlock;

		this.firstCellInBlockRow = firstCellInBlockRow;
		this.firstCellInBlockColumn = firstCellInBlockColumn;

		this.lastCellInBlockRow = lastCellInBlockRow;
		this.lastCellInBlockColumn = lastCellInBlockColumn;

		this.blockType = blockType;

		this.elements = new ArrayList<ResourceDataXLS>();
	}

	public Integer getBockType() {
		return this.blockType;
	}

	public Boolean validateData(String[] data) {
		if ((this.blockType.intValue() == ROW.intValue())
				&& (data.length == this.lastCellInBlockRow.intValue()
						- this.firstCellInBlockRow.intValue() + 1)) {
			return Boolean.valueOf(true);
		}

		if ((this.blockType.intValue() == COLUM.intValue())
				&& (data.length == this.lastCellInBlockColumn.intValue()
						- this.firstCellInBlockColumn.intValue() + 1)) {
			return Boolean.valueOf(true);
		}

		return Boolean.valueOf(false);
	}

	public void setElemento(String[] data, String sheet) {
		ResourceDataXLS rsd = null;
		Location cordenada = new Location(this.initialLocation.getRow(),
				this.initialLocation.getColumn());

		for (int i = 0; i < data.length; i++) {
			rsd = new ResourceDataXLS();

			rsd.setData(data[i]);
			rsd.setType("String");
			rsd.setLocation(cordenada);
			rsd.setSheetName(sheet);
			rsd.setLocked(Boolean.valueOf(true));
			rsd.setIsCatalogo(Boolean.valueOf(false));

			this.elements.add(rsd);

			cordenada = new Location(cordenada.getRow(), cordenada.getColumn());

			nextCoordenada(cordenada);
		}

		nextRowColumn();
	}

	public List<ResourceDataXLS> getElementos() {
		return this.elements;
	}

	private void nextCoordenada(Location cordenada) {
		if (this.blockType.intValue() == ROW.intValue())
			cordenada.setColumn(Integer.valueOf(cordenada.getColumn()
					.intValue() + 1));
		else
			cordenada
					.setRow(Integer.valueOf(cordenada.getRow().intValue() + 1));
	}

	private void nextRowColumn() {
		if (this.blockType.intValue() == ROW.intValue())
			this.initialLocation.setRow(Integer.valueOf(this.initialLocation
					.getRow().intValue() + 1));
		else
			this.initialLocation.setRow(Integer.valueOf(this.initialLocation
					.getColumn().intValue() + 1));
	}

	public Integer getTopLimitBlock() {
		return this.topLimitBlock;
	}

	public Integer getBottomLimitBlock() {
		return this.bottomLimitBlock;
	}

	public Location getInitialLocation() {
		return this.initialLocation;
	}

	public Location getEndLocation() {
		return this.endLocation;
	}
}
