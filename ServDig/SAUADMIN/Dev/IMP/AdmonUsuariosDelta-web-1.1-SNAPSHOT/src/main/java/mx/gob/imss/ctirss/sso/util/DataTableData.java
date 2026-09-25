package mx.gob.imss.ctirss.sso.util;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.generator.RolesGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTableData implements Serializable {
    
        public static   String BEAN_NAME = "tableData";

        public DataTableData() 
        {   
        	
        }

        public static List<PuestoDTO> getDefaultData() {
        	RolesGenerator generator = new RolesGenerator();
            return generator.getPredefinedRoles();
        }
        
        public static List<ModuloDTO> getDefaultDataTD() {
        	RolesGenerator generator = new RolesGenerator();
            return generator.getPredefinedModulos();
        }
        
        public static ArrayList<PuestoDTO> addRoletoTableOne(ArrayList<PuestoDTO> list, Integer cveArea, String desArea, Integer cveDepartamento, String desDepartamento, Integer cvePuesto, String desPuesto, String defaultRol)
        {
        	RolesGenerator generator = new RolesGenerator();
        	return generator.addRoleToTableOne(list, cveArea, desArea, cveDepartamento, desDepartamento, cvePuesto, desPuesto, defaultRol);
        }
        
        public static ArrayList<ModuloDTO> addRoletoTableTwo(ArrayList<ModuloDTO> list, Integer cveArea, String desArea, Integer cveDepartamento, String desDepartamento, Integer cveModulo, String desModulo)
        {
        	RolesGenerator generator = new RolesGenerator();
        	return generator.addRoleToTableTwo(list, cveArea, desArea, cveDepartamento, desDepartamento, cveModulo, desModulo);
        }
        
        public static ArrayList<ModuloDTO> addRoletoTableTwoN(ArrayList<ModuloDTO> list, AreaNormativaDTO an, DepartamentoDTO dep, int cveModulo, String desModulo)
        {
        	RolesGenerator generator = new RolesGenerator();
        	return generator.addRoleToTableTwo(list, an, dep, cveModulo, desModulo);
        }
        
        public static ArrayList<PuestoDTO> removeRoleFromTableOne(int cve, ArrayList<PuestoDTO> list)
        {
        	RolesGenerator generator = new RolesGenerator();  
        	return generator.removeRoleFromTableOne(cve, list);
        }
        
        public static ArrayList<ModuloDTO> removeModuleFromTableTwo(int cve, ArrayList<ModuloDTO> list)
        {
        	RolesGenerator generator = new RolesGenerator();  
        	return generator.removeRoleFromTableTwo(cve, list);
        }
        
        public static boolean validaPuestoExistente(ArrayList<PuestoDTO> list, Integer cveArea, Integer cveDepartamento,  Integer cvePuesto)
        {
        	RolesGenerator generator = new RolesGenerator();  
        	return generator.validaPuestoExistente(cveArea, cveDepartamento, cvePuesto, list);
        }
        
        public static boolean validaModuloExistente(ArrayList<ModuloDTO> list, Integer cveArea, Integer cveDepartamento,  Integer cveModulo)
        {
        	RolesGenerator generator = new RolesGenerator();  
        	return generator.validaModuloExistente(cveArea, cveDepartamento, cveModulo, list);
        }
        
        public static boolean consultaModuloDefault(ArrayList<PuestoDTO> list, Integer cveArea, Integer cveDepartamento,  Integer cveModulo)
        {
        	RolesGenerator generator = new RolesGenerator();  
        	return generator.consultaModuloDefault(cveArea, cveDepartamento, cveModulo, list);
        }
        
}