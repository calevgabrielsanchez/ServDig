package mx.gob.imss.common.utils.readAndExportXLS.process;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Location;
import mx.gob.imss.common.utils.readAndExportXLS.vo.ResourceDataXLS;

public class ProcessResourceDataXLS {
	public static ResourceDataXLS process(String data, String dataType,
			String sheetName, Location location) {
		ResourceDataXLS rdXLS = new ResourceDataXLS();

		rdXLS.setData(data);
		rdXLS.setType(dataType);
		rdXLS.setLocation(location);
		rdXLS.setLocked(Boolean.valueOf(true));
		rdXLS.setSheetName(sheetName);
		rdXLS.setIsCatalogo(Boolean.valueOf(false));

		return rdXLS;
	}
}