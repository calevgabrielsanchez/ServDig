package mx.gob.imss.ctirss.delta.model.xmlAdapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

public class DateAdapter extends XmlAdapter<String, Date> {	
	
	// yyyy-MM-dd'T'HH:mm:ss.SSSZ - Formato original usuado por JAXB
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public String marshal(Date date) throws Exception {
		if (date != null) {
			return df.format(date);
		} else {
			return null;
		}
	}

	@Override
	public Date unmarshal(String date) throws Exception {
		if (StringUtils.isNotBlank(date)) {
			return df.parse(date);
		} else {
			return null;
		}
		
	}

}
