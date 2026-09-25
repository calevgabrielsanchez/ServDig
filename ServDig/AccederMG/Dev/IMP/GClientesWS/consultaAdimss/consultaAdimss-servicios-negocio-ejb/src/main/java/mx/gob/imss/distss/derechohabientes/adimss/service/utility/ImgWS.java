package mx.gob.imss.distss.derechohabientes.adimss.service.utility;

import java.io.IOException;

public class ImgWS {

	public Object getImg(String id) throws IOException{
		String wsdl = "http://11.254.172.131:8050/wsadimss/ServADIMSSDocs?WSDL";
		if ((VerificaConnWS.checkWSDL(wsdl,Constantes.SEG_WSDL_TIME_OUT)) && (Validation.checkWSDL(wsdl))) {
			DataWS ws_= new DataWS(wsdl);
			beans.util.adimss.da.ctirss.imss.gob.mx.BeanFile fileBean = ws_.getImg(id);
			if((fileBean.getMsgError()!= null && ! fileBean.getMsgError().trim().equals( "" )) || fileBean == null) {
                return null;
	        }else{
	            return fileBean.getArchivo();
	        }
		}else{
			return null;
		}
	}
	
	public Object getImgByPath(String ruta) throws IOException{
		String wsdl = "http://11.254.172.131:8050/wsvangent/ImagenWS?WSDL";
		if((VerificaConnWS.checkWSDL(wsdl,Constantes.SEG_WSDL_TIME_OUT)) && (Validation.checkWSDL(wsdl))) {
			DataWS ws_= new DataWS(wsdl);
			vo.imss.gob.mx.BeanFile fileBean = ws_.getImgByPath(ruta);
			if((fileBean.getMsgError()!= null && ! fileBean.getMsgError().trim().equals( "" )) || fileBean == null) {
                return null;
	        }else{
	            return fileBean.getArchivo();
	        }
		}else{
			return null;
		}
	}
	
	public String getEnrolStationPath(int idEnrol){
		switch (idEnrol) {
		case 1:
			return "0000";
		case 2:
			return "000";
		case 3:
			return "00";
		case 4:
			return "0";
		default: 
			return "";
		}
	}
}
