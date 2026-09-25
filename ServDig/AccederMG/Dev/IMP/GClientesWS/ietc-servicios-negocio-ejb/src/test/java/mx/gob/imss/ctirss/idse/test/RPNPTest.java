package mx.gob.imss.ctirss.idse.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import mx.gob.imss.ctirss.idse.model.Pkcs7IETCBean;
import mx.gob.imss.ctirss.idse.model.RegistroPatronalIETCBean;
import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;
import mx.gob.imss.ctirss.idse.service.interfaces.RPNPServiceRemote;

import org.junit.Test;

public class RPNPTest {
	
	@Test
	public void test() {
		
	RPNPServiceRemote ejb = EJBLocator.getRPNPServiceBusiness();
		
		RequerimientoIETCBean reqIETC = new RequerimientoIETCBean();
		RequerimientoIDSEBean reqIdse = new RequerimientoIDSEBean();

		File archivoIDSE = null;
		BufferedReader br = null;
		String line = null;
		String registro[] = null;

		try {
			archivoIDSE = new File("C:\\IMSS\\sincronizaIDSE.txt");
			br = new BufferedReader(new FileReader(archivoIDSE));

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			while ((line = br.readLine()) != null) {
				registro = line.split("\\|");

				Pkcs7IETCBean pkcs7 = new Pkcs7IETCBean();
				pkcs7.setClaveSerial(registro[0]);
				pkcs7.setNombreCompleto(registro[1]);
				pkcs7.setEstatusFiel("1");
				pkcs7.setCorreoElectronico(registro[3]);
				pkcs7.setCurpFiel(registro[4]);
				pkcs7.setFechaValidaFin(df.parse(registro[8]));
				pkcs7.setFechaValidaInicio(df.parse(registro[7]));
				pkcs7.setIdRol(Integer.parseInt(registro[10]));
				pkcs7.setNombreUsuario(registro[5]);
				pkcs7.setRfcAsociado(registro[6]);
				pkcs7.setTelefono(registro[9]);

				RegistroPatronalIETCBean rpIetc = new RegistroPatronalIETCBean();
				rpIetc.setFechaActivacion(df.parse(registro[27]));
				rpIetc.setFechaRecepcion(df.parse(registro[26]));
				rpIetc.setRazonSocial(registro[15]);
				rpIetc.setRegistroPatronal(registro[13]+ registro[14]);
				rpIetc.setEstatusRP(2);
				rpIetc.setRfcRegistroPatronal(registro[18]);
				rpIetc.setTipoPersona(Integer.parseInt(registro[28]));
				rpIetc.setUsuarioSubDel("PDigIMSS");

				reqIETC.setPkcs7Bean(pkcs7);
				reqIETC.setRegPatronBean(rpIetc);

				reqIdse.setActividad(registro[21]);
				reqIdse.setClaseRt(Integer.parseInt(registro[23]));
				reqIdse.setCveDel(Integer.parseInt(registro[11]));
				reqIdse.setCveSub(Integer.parseInt(registro[12]));
				reqIdse.setDomicilio(registro[19]);
				reqIdse.setEmail(registro[24]);
				reqIdse.setFraccion(Integer.parseInt(registro[22]));
				reqIdse.setLocalidad(registro[20]);
				reqIdse.setMunicipio(registro[16]);
				reqIdse.setRazonSocial(registro[15]);
				reqIdse.setRegPatron(registro[13]);
				reqIdse.setRegPatronDig(Integer.parseInt(registro[14]));
				reqIdse.setRepLegal(registro[25]);
				reqIdse.setRfc(registro[18]);
				reqIdse.setSector(Integer.parseInt(registro[17]));

				System.out.println(reqIETC.toString());
				System.out.println(reqIdse.toString());
				System.out.println("");

			ejb.registrarAltaPatronalIDSE(reqIETC, reqIdse);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
