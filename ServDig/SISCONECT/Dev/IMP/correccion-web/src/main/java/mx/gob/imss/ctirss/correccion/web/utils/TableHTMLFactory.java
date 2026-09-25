package mx.gob.imss.ctirss.correccion.web.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TableHTMLFactory {

	private static TableHTMLFactory sf;
	private String tableClass = "tabla";
		
	private TableHTMLFactory() {
		//
	}

	public static TableHTMLFactory getInstance() {
		if (sf == null) {
			sf = new TableHTMLFactory();
		}
		return sf;
	}

	private Connection getConnection() {

		Connection back = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("ds_ora_delta_inc");
			back = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return back;
	}

	public String getTableSQL(String sql, String _$title, String[] nameFields)
			throws Exception {
		String back = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			
				con = getConnection();
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData();
				back = "<table class='" +tableClass + "' border=1> <tr><thead> <td colspan="
						+ md.getColumnCount() + "><center>" + _$title + "</center></thead></td></tr>\n";

				back = back + createTableSubTitles(md, nameFields);
				back = back
						+ createTableResource(md.getColumnCount(), rs);

				back = back + "</table>\n";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
//				if (con != null)
//					con.close();
			} catch (Exception e2) {

			}
		}
		return back;
	}
	
	public String getTableSQLURL(String sql, String _$title, String[] nameFields, String rp) throws Exception {
		String back = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			
			con = getConnection();
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData();
				back = "<table class='" +tableClass + "' border=1> <tr><thead> <td colspan="
						+ md.getColumnCount() + "><center>" + _$title + "</center></thead></td></tr>\n";

				back = back + createTableSubTitles(md, nameFields);
				back = back + createTableResourceURL(md.getColumnCount(), rs, rp);

				back = back + "</table>\n";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
//				if (con != null)
//					con.close();
			} catch (Exception e2) {

			}
		}
		return back;
	}
	

	private String createTableSubTitles(ResultSetMetaData md,
			String[] nameFields) throws Exception {
		String back = "<tr align='center'>";
		 
		if (nameFields != null) {
			for (int i = 0; i < nameFields.length; i++) {				
				back = back + "<td>" + nameFields[i] + "</td>";
			}
		} else {
			for (int i = 1; i <= md.getColumnCount(); i++) {
				back = back + "<td>" + md.getColumnName(i) + "</td>";
			}
		}
		return back + "</tr>";
	}

	private String createTableResource(int columnCount, ResultSet rs)
			throws Exception {
		String back = "";
		
		boolean flag = false;
		String bgColor = "";
		
		for (; rs.next();) {
			
			if (flag){
				bgColor = "par";
				flag = false;
			} else {
				bgColor = "impar";
				flag = true;
			}
			
			back = back + "<tr class='" + bgColor  + "' align=center>";
			for (int i = 1; i <= columnCount; i++) {
				back = back + "<td>" + rs.getString(i) + "&nbsp;</td>";
			}
			back = back + "</tr>\n";
		}

		return back;
	}

	
	private String createTableResourceURL(int columnCount, ResultSet rs, String rp)
			throws Exception {
		String back = "";
		
		boolean flag = false;
		String bgColor = "";
		
		for (; rs.next();) {
			
			if (flag){
				bgColor = "par";
				flag = false;
			} else {
				bgColor = "impar";
				flag = true;
			}
			
			back = back + "<tr class='" + bgColor  + "' align=center>";
			for (int i = 1; i <= columnCount; i++) {
				String val = rs.getString(i);
				if (i == 2) {
					back = back + "<td><a href=\"javascript:verDetalleMov('" + val + "', '"+ rp +"')\"> <font size=\"3\" color=\"red\">" + val + "&nbsp;</a></td>";
				} else {
					back = back + "<td>" + val + "&nbsp;</td>";
				}
				
			}
			back = back + "</tr>\n";
		}

		return back;
	}
	
	
	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

	
}