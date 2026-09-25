package mx.gob.imss.cit.dacvass.servicios.rest.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosHistoriaLaboral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersonaFisica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersonaRenapo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersonaRepresentada;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ResolucionPension;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;

public class InfoPersonaMock {
	
	public static  DatosPersona getDatosPersonaMoc() {
		DatosPersona persona = new DatosPersona();
		DatosAsegurado aseg = new DatosAsegurado();
			DatosGrupoFamiliar gfAseg = new DatosGrupoFamiliar();
				gfAseg.setConsultorio("23");
				//gfAseg.setCveIdGrupoFamiliar(2342L);
				//gfAseg.setNssCabezaGrupoFamiliar("55443322112");
				//gfAseg.setParentesco("ASEGURADO");
				//gfAseg.setTurno("MATUTINO");
				//sseg.setUmf("23");
			aseg.setCveIdAsignacionNss(123L);
			aseg.setNss("55443322112");
			aseg.setDatosGrupoFamiliar(gfAseg);
				List<DatosGrupoFamiliar> lstGF = new ArrayList<DatosGrupoFamiliar>(); 
				lstGF.add(gfAseg);
				DatosGrupoFamiliar gfB = new DatosGrupoFamiliar();
				gfB= gfAseg;
				lstGF.add(gfB);
			//aseg.setDatosGrupoFamiliarBeneficiarios(lstGF);
			DatosHistoriaLaboral hist = new DatosHistoriaLaboral();
				hist.setActividadEmpresa("VENA DE LLANTAS");
				hist.setNombrePatron("SALAS SA");
				hist.setNumeroRegistroPatronal("Y5412345105");
				hist.setEntidadFederativa("MORELOS");
				List<DatosHistoriaLaboral> lstHL= new ArrayList<DatosHistoriaLaboral>();
				lstHL.add(hist);
			//aseg.setDatosHistoriaLaboral(lstHL);
			
				ResolucionPension pen = new ResolucionPension();
		//			pen.setAnioPension("2019");
		//			pen.setCuentaClabe("012345678998765412");
		//			pen.setTipoPension("VEJES");
			//aseg.setDatosPensionado(pen);
			
			persona.setCurp("CURP311280HDFLRN01");
			persona.setCveIdPersona(1231l);
			persona.setDatosAsegurado(aseg);
			persona.setDatosGrupoFamiliarBeneficiario(lstGF);
			DatosPersonaFisica objPersonaFisica = new DatosPersonaFisica();
				objPersonaFisica.setCveIdPersonaFisica(1123L);
				objPersonaFisica.setRfc("RFCA3103799G3");
				objPersonaFisica.setDomicilioFiscalSat("CERRADA DE SAN JUAN 23");
				DatosPersonaRepresentada pRep = new DatosPersonaRepresentada();
					pRep.setNrp("Y45123456101");
					pRep.setRfc("RFCT0101809T6");
					List<DatosPersonaRepresentada> lstRP= new ArrayList<DatosPersonaRepresentada>();
						lstRP.add(pRep);
						lstRP.add(pRep);
				objPersonaFisica.setDatosPersonaRepresentada(lstRP);
				objPersonaFisica.setTramiteRegistroPortalIMSS(false);
			persona.setDatosPersonaFisica(objPersonaFisica);
			DatosPersonaRenapo renapo = new DatosPersonaRenapo();
				renapo.setFoja("00000");
				renapo.setFolio("123123");
				renapo.setLibro("ASDF234S");
				renapo.setMunicipioRegistro("JUAREZ");
				renapo.setTomo("NA/234");
			persona.setDatosPersonaRenapo(renapo);
			EstadoCivil ecivil = new EstadoCivil();
					ecivil.setIdEstadoCivil(1);
					ecivil.setDescripcion("SOLTERO");
			persona.setEstadoCivil(ecivil);
			persona.setFechaDefuncion(null);
			persona.setFechaNacimiento(new Date());
			EntidadFederativa entidad = new EntidadFederativa();
				entidad.setClave("09");
				entidad.setNombre("CDMX");
			persona.setLugarNacimiento(entidad);
			persona.setNombre("MARIO FERNANDO");
			persona.setNss("55443322112");
			Pais pais = new Pais();
				pais.setDescripcion("MEXICO");
				pais.setIdPais(23);
			persona.setPais(pais);
			persona.setPrimerApellido("JUAREZ");
			persona.setSegundoApellido("ALFARO");
			Sexo sexo = new Sexo();
				sexo.setIdSexo(1L);
				sexo.setDescripcion("HOMBRE");
			persona.setSexo(sexo);
			
		return persona ;
	}


}
