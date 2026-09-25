package mx.gob.imss.ctirss.sso.generator;


import java.io.Serializable;
import java.util.*;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;

public class RolesGenerator implements Serializable {

	/**
	 * 
	 */
	private static   long serialVersionUID = 1L;    

    public RolesGenerator() 
    {

    }
        
    public ArrayList<PuestoDTO> addRoleToTableOne( ArrayList<PuestoDTO> list, Integer cveArea, String desArea, Integer cveDepartamento, String desDepartamento, Integer cvePuesto, String desPuesto, String defaultRol)
    {
    	PuestoDTO rol = new PuestoDTO(cveArea,desArea, cveDepartamento, desDepartamento, cvePuesto, desPuesto, defaultRol);
        list.add(rol);
        return list;
    }
    
    public ArrayList<ModuloDTO> addRoleToTableTwo( ArrayList<ModuloDTO> list, Integer cveArea, String desArea, Integer cveDepartamento, String desDepartamento, Integer cveModulo, String desModulo)
    {
    	ModuloDTO modulo = new ModuloDTO(cveArea,desArea, cveDepartamento, desDepartamento, cveModulo, desModulo);
        list.add(modulo);
        return list;
    }
    
    public ArrayList<ModuloDTO> addRoleToTableTwo(ArrayList<ModuloDTO> list, AreaNormativaDTO an, DepartamentoDTO dep, Integer cveModulo, String desModulo)
    {
    	ModuloDTO modulo = new ModuloDTO( );
    	modulo.setAreaNorm(an);
    	modulo.setDptoDTO(dep);
    	modulo.setCveIdModulo(cveModulo);
    	modulo.setDesModulo(desModulo);
        list.add(modulo);
        return list;
    }
    
    public ArrayList<PuestoDTO> removeCarsFromList(int quantityToRemove, ArrayList<PuestoDTO> list)
    {
        return new ArrayList<PuestoDTO>(list.subList(0, list.size()-quantityToRemove-1));
    }
     
    
    public ArrayList<PuestoDTO> removeRoleFromTableOne(int cve, ArrayList<PuestoDTO> list)
    {
    	ArrayList<PuestoDTO> listRemoved = new  ArrayList<PuestoDTO>();
    		for(PuestoDTO rol: list){
    		        if(rol.getCvePuesto()!=cve)
    		        	     listRemoved.add(rol);
    		   }
    	return listRemoved;
    }
     
    public ArrayList<ModuloDTO> removeRoleFromTableTwo(int cve, ArrayList<ModuloDTO> list)
    {
    	ArrayList<ModuloDTO> listRemoved = new  ArrayList<ModuloDTO>();
    		for(ModuloDTO modulo: list){
    		        if(modulo.getCveIdModulo()!=cve)
    		        	     listRemoved.add(modulo);
    		   }
    	return listRemoved;
    }
    
    public ArrayList<PuestoDTO> getPredefinedRoles()
    {
        ArrayList<PuestoDTO> listWithRoles = new ArrayList<PuestoDTO>();
        return listWithRoles;
    }
    
    public ArrayList<ModuloDTO> getPredefinedModulos()
    {
        ArrayList<ModuloDTO> listWithModulos = new ArrayList<ModuloDTO>();
        return listWithModulos;
    }
   
    public boolean validaPuestoExistente(int cveArea, int cveDep, int cvePuesto, ArrayList<PuestoDTO> list)
    {
    	 boolean respuesta = false; 
    	
    	 for(PuestoDTO rol: list){
		        if(rol.getCveArea() == cveArea && rol.getCveDepartamento() == cveDep && rol.getCvePuesto() == cvePuesto){
		        	respuesta = true;
		        	break;
		        }
		   } 
    	 return respuesta;
    }
    
    public boolean validaModuloExistente(int cveArea, int cveDep, int cveModulo, ArrayList<ModuloDTO> list)
    {
    	 boolean respuesta = false; 
    	
    	 for(ModuloDTO mod: list){
		        if(mod.getAreaNormDTO().getCveSsoareanorma() == cveArea && mod.getDptoDTO().getCveSsodepto() == cveDep && mod.getCveIdModulo() == cveModulo){
		        	respuesta = true;
		        	break;
		        }
		   } 
    	 return respuesta;
    }
    
    public boolean consultaModuloDefault(int cveArea, int cveDep, int cvePuesto, ArrayList<PuestoDTO> list)
    {
    	 boolean respuesta = false; 
    	
    	 for(PuestoDTO rol: list){
		        if(rol.getCveArea() == cveArea && rol.getCveDepartamento() == cveDep && rol.getCvePuesto() == cvePuesto){
		        	if("Si".equals(rol.getDefaultRol()))
		        	    respuesta = true;
		        	break;
		        }
		   } 
    	 return respuesta;
     }
    
  }
