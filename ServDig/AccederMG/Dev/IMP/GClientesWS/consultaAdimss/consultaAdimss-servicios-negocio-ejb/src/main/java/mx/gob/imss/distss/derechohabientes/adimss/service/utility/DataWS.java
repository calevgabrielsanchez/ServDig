package mx.gob.imss.distss.derechohabientes.adimss.service.utility;

import java.rmi.RemoteException;

import mx.gob.imss.ctirss.wsnece.WSNeceSoapPortBindingStub;

import org.apache.axis.AxisFault;
import org.openuri.www.EntradaBean;
import org.openuri.www.RespuestaBean;
import org.openuri.www.WSDatosTrabajadorCanaseSoapStub;

import ws.ImagenWSServiceSoapBindingStub;
import ws.ServADIMSSDocsServiceSoapBindingStub;
import bean.wsnece.ctirss.imss.gob.mx.Asegurado;

public class DataWS {

	
	private String wsdl;
	
	public DataWS(String wsdl){
		this.wsdl = wsdl;
	}
	
	/**
	 * Metodo que obtiene los datos del asegurado
	 * 
	 * @param nss : numero de seguro social
	 * @param wsdl : servicio web que se solicitara
	 * @return Asegurado
	 */
	public Asegurado getDataAsegurado( String nss ) {
		try {
			if(this.wsdl!=null){
				WSNeceSoapPortBindingStub wsNeceSoapStub = new WSNeceSoapPortBindingStub();
				wsNeceSoapStub._setProperty(WSNeceSoapPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, this.wsdl);
				return wsNeceSoapStub.getInfo( nss );
			}
		} catch ( AxisFault e ) {
			e.printStackTrace();
		} catch ( RemoteException e ) {
			e.printStackTrace();
		} return null;
	}
	
	public RespuestaBean getDataWScanase(String nss, String usr, String pass) {
		try {
			if(this.wsdl!=null){
				WSDatosTrabajadorCanaseSoapStub wsCanase = new WSDatosTrabajadorCanaseSoapStub();
				wsCanase._setProperty(WSDatosTrabajadorCanaseSoapStub.ENDPOINT_ADDRESS_PROPERTY, this.wsdl);
				return wsCanase.getDatosAsegurado(new EntradaBean(nss,"","","",usr,pass));
			}
		} catch ( AxisFault e ) {
			e.printStackTrace();
		} catch ( RemoteException e ) {
			e.printStackTrace();
		} return null;
	}
	
	public beans.util.adimss.da.ctirss.imss.gob.mx.BeanFile getImg(String id){
		try {
			if(this.wsdl!=null){
				ServADIMSSDocsServiceSoapBindingStub ws = new ServADIMSSDocsServiceSoapBindingStub();
				ws._setProperty(ServADIMSSDocsServiceSoapBindingStub.ENDPOINT_ADDRESS_PROPERTY, this.wsdl);
				return ws.consultaImagen(id);
			}
		} catch ( AxisFault e ) {
			e.printStackTrace();
		} catch ( RemoteException e ) {
			e.printStackTrace();
		} return null;
	}
	
	public vo.imss.gob.mx.BeanFile getImgByPath(String ruta){
		try {
			if(this.wsdl!=null){
				ImagenWSServiceSoapBindingStub ws = new ImagenWSServiceSoapBindingStub();
				ws._setProperty(ImagenWSServiceSoapBindingStub.ENDPOINT_ADDRESS_PROPERTY, this.wsdl);
				return ws.consultarImagen(ruta);
			}
		} catch ( AxisFault e ) {
			e.printStackTrace();
		} catch ( RemoteException e ) {
			e.printStackTrace();
		} return null;
	}
}
