package mx.gob.imss.ctirss.correccion.web.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SessionHbtFactory {

	
	private static SessionHbtFactory sf;
	
	private SessionHbtFactory() {
		// 
	}
	
	public static SessionHbtFactory getInstance() {
		if(sf == null) {
			sf = new SessionHbtFactory();
		}
		return sf;
	}
	
	public String findOne(String sql) {
		String back = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getSession();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs != null) {
//				ResultSetMetaData md = rs.getMetaData();				
				rs.next();
				back = rs.getString(1);
//				for (;rs.next();) {
//					for (int i = 0; i < md.getColumnCount(); i++) {
//					} 
//				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();				
			} catch (Exception e2) {
				
			}
		}
		return back;
	}
	
	private Connection getSession() {
		
		
		Connection back = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("ds_ora_delta_inc");
			back = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return back;
	}
}