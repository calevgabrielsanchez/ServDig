package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;
 
public class Pool {
	
	private static Connection conexion = null;
	
	
	public static ArrayList<SubdelegacionesConcentrado> obtieneSubdelegaciones(int cveDel) {
//		System.out.println("::: Ejecutando consulta para la subdelegacion: " + cveDel);
		
		ArrayList<SubdelegacionesConcentrado> lista = new ArrayList<SubdelegacionesConcentrado>();
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select d.cve_Id_Delegacion, sd.cve_Id_Subdelegacion, d.des_Deleg, sd.des_Subdelegacion ");
		sb2.append("  from Dic_Delegacion d, Dic_Subdelegacion sd");
		sb2.append(" where d.cve_Id_Delegacion = sd.cve_Id_Delegacion ");		
		sb2.append(" and d.fec_Registro_Baja is null ");		
		sb2.append(" and sd.fec_Registro_Baja is null ");		
		sb2.append(" and d.clave_Delegacion = " + cveDel );		
		sb2.append(" order by sd.clave_Subdelegacion ");
		
		String SQL_SELECT_BUSQUEDA_SUBDELEGACIONES = sb2.toString();
		SubdelegacionesConcentrado e = null;
		
	    try {
	    		if(conexion == null) {
	    			initConexion();
	    		}
	            Statement stmt = conexion.createStatement();
	            ResultSet rs = stmt.executeQuery(SQL_SELECT_BUSQUEDA_SUBDELEGACIONES);
	            while (rs.next()) {
	            	
	    			e = new SubdelegacionesConcentrado();

	    			e.setIdDel( rs.getInt("cve_Id_Delegacion") );
	    			e.setIdSubDel( rs.getInt("cve_Id_Subdelegacion") );
	    			e.setDel( rs.getString("des_Deleg") );
	    			e.setSubDel( rs.getString("des_Subdelegacion") );				

	    			lista.add(e);
	            	
	            }
	         } catch (SQLException ex) {
	            ex.printStackTrace();
	            lista = null;
	         } 
		return lista;
	}
		
	public static void initConexion() {
		System.out.println("::: Abriendo conexion");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.8.166:1630:bdtu2","MGPBDTU1","T3mp0ra1");
            if(conexion != null) {
            	System.out.println("::: Si me pude conectar");
            }else{
            	System.out.println("::: NO me pude conectar");
            }
        } catch (SQLException ex) {
            System.out.println("A - Error en la conexión de la base de datos");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("B - Error en la conexión de la base de datos");
            ex.printStackTrace();
        } 
	}
	
	public static void cerrarConexion() {
		System.out.println("::: Cerrando conexion");
		if(conexion != null) {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
				conexion = null;
			}
			conexion = null;
		}
		
	}
	
//    public static void main(String[] args) {
//    	Connection conexion = null;
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conexion = DriverManager.getConnection("jdbc:oracle:thin:@172.16.8.166:1630:bdtu2","MGPBDTU1","T3mp0ra1");
//            if(conexion != null) {
//            	System.out.println("::: Si me pude conectar");
//            }else{
//            	System.out.println("::: NO me pude conectar");
//            }
//        } catch (SQLException ex) {
//            System.out.println("A - Error en la conexión de la base de datos");
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            System.out.println("B - Error en la conexión de la base de datos");
//            ex.printStackTrace();
//        }finally {
//			if(conexion != null) {
//				try {
//					conexion.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//					conexion = null;
//				}
//				conexion = null;
//			}
//		}
//         
//    }
    
}