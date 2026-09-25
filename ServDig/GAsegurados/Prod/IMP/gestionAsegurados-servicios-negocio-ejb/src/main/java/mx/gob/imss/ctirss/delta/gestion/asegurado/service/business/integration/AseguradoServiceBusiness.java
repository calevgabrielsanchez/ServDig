package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.integration.AseguradoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility.FtpUploader;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.Empleado;
import mx.gob.imss.ctirss.delta.model.asegurado.EmpleadoEmpresa;
import mx.gob.imss.ctirss.delta.model.enums.ParametroSistemaEnum;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author NOVUTEK101
 *
 */
@Stateless(name = "aseguradoServiceBusiness", mappedName = "aseguradoServiceBusiness")
public class AseguradoServiceBusiness implements AseguradoServiceBusinessRemote{
	
	@EJB
	ParametrosServiceBusinessRemote parametrosServiceBusiness;
	
	@Override
	public AltaDatosAsignacionNSSType[] cargarArchivoSIE(String fileName) {
		
		InputStream stream = null;
		EmpleadoEmpresa empleadoEmpresa = null;
		try {
			stream = leerArchivo(fileName);
			System.err.println("Archivo leido: "+stream.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Error de lectura en el archivo");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error de lectura en el archivo");
			return null;
		}

		try {
			JAXBContext jc = JAXBContext.newInstance(EmpleadoEmpresa.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			System.err.println("Se transformara inputstream a objetos");
			empleadoEmpresa = (EmpleadoEmpresa) unmarshaller
				.unmarshal(stream);
			System.err.println("Se genero el objeto de negocio a partir del archivo");
			System.err.println("Archivo: "+empleadoEmpresa);
		} catch (JAXBException e) {
			System.err.println("algo fallo");
			e.printStackTrace();
		}
			
		return parseEmpleados(empleadoEmpresa.getEmpleado());
	}
	
		
	private InputStream leerArchivo(String fileName) throws IOException{
		String fsSIE=parametrosServiceBusiness.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FILE_SYSTEM_SIE.getCodigo());
		FtpUploader ftpService = new FtpUploader();
		String canonicalFilePath = new StringBuffer().append(fsSIE).append(fileName).toString();
		System.err.println("Path para leer archivo: "+canonicalFilePath);
		String server = parametrosServiceBusiness.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FTP_SIE_SERVER.getCodigo());
		String strPort = parametrosServiceBusiness.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FTP_SIE_PORT.getCodigo());
		String user = parametrosServiceBusiness.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FTP_SIE_USER.getCodigo());
		String pass = parametrosServiceBusiness.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FTP_SIE_PWD.getCodigo());
		
		ftpService.connect(server, strPort, user, pass);
	    return ftpService.readRemoteFile(canonicalFilePath);
	}
	
	private AltaDatosAsignacionNSSType[] parseEmpleados(List<Empleado> empleados){
		List<AltaDatosAsignacionNSSType> empleadosFinales = new ArrayList<AltaDatosAsignacionNSSType>();
		for(Empleado empleado : empleados){
			AltaDatosAsignacionNSSType nuevoEmpleado = new AltaDatosAsignacionNSSType();
			int anioNac = StringUtils.isNotEmpty(empleado.getAnioNacimiento()) ?   Integer.valueOf(empleado.getAnioNacimiento()) : 0;
			int diaNac = StringUtils.isNotEmpty(empleado.getDiaNacimiento()) ?   Integer.valueOf(empleado.getDiaNacimiento()) : 0;
			int lugarNac = StringUtils.isNotEmpty(empleado.getLugarNacimiento()) ?   Integer.valueOf(empleado.getLugarNacimiento()) : 0;
			int mesNac = StringUtils.isNotEmpty(empleado.getMesNacimiento()) ?   Integer.valueOf(empleado.getMesNacimiento()) : 0;
			int sexo = StringUtils.isNotEmpty(empleado.getSexo()) ?   Integer.valueOf(empleado.getSexo()) : 0;
			
			nuevoEmpleado.setAnioNacimiento(anioNac);
			nuevoEmpleado.setApellidoMaterno(empleado.getApellidoMaterno());
			nuevoEmpleado.setApellidoPaterno(empleado.getApellidoPaterno());
			nuevoEmpleado.setCodigoPostal(empleado.getCodigoPostal());
			nuevoEmpleado.setCurp(empleado.getCurp());
			nuevoEmpleado.setDiaNacimiento(diaNac);
			nuevoEmpleado.setLugarNacimiento(lugarNac);
			nuevoEmpleado.setMesNacimiento(mesNac);
			nuevoEmpleado.setNombre(empleado.getNombre());
			nuevoEmpleado.setRegistroPatronal(empleado.getCveRegPatron());
			nuevoEmpleado.setSexo(sexo);
			empleadosFinales.add(nuevoEmpleado);
		}
		return empleadosFinales.toArray(new AltaDatosAsignacionNSSType[empleadosFinales.size()]);
	}
	
}
