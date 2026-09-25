package com.infraestructura.jdbc;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.ResultSetMetaData;
import java.sql.Clob;
import java.util.LinkedList;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.Context;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ConexionJdbc implements Serializable {

	public static final boolean CLOB_PROCESA = true;
	public static final boolean CLOB_NO_PROCESA = true;
	public static final boolean BLOB_PROCESA = true;
	public static final boolean BLOB_NO_PROCESA = true;
	public static final boolean CLOB_TRUNCA_DATOS = true;
	public static boolean CLOB_TRUNCADO = false;
	public static final boolean CLOB_SINTRUNCAR_DATOS = false;
	/**
	 * Bandera que indica si la clase tiene una conexión a la base de datos heredada por otra clase.
	 */
	private boolean bConexionHeredada = false;
	/**
	 * Esta variable se utilizará para construir las consultas a la base de datos.
	 */
	public String sSql;
	/**
	 * Resultset a la base de datos. El Resultset se podrá utilizar en cualquier clase que herede esta.
	 * El Resultset se abrirá automaticamente cuando la clase que hereda llame al método abreConexion().
	 */
	public ResultSet rs = null;
	/**
	 * Statement a la base de datos. El Statement se podrá utilizar en cualquier clase que herede esta.
	 * El Statemente se abrirá automaticamente cuando la clase que hereda llame al método abreConexion().
	 */
	public Statement st = null;
	/**
	 * Statement a la base de datos para procedimientos almacenados. El Statement se podrá utilizar en cualquier clase que herede esta.
	 * El Statemente se abrirá automaticamente cuando la clase que hereda llame al método abreConexion().
	 */
	protected CallableStatement sto = null;
	/**
	 * La conexión a la base de datos
	 */
	public Connection conn;

	/**
	 * Obtiene directamente la conexion a la base de datos que se esta utilizando
	 *
	 * @return Apuntador a la base de datos
	 */
	public Connection getConexion() {
		return conn;
	}

	/**
	 * Asigna la conexion a la base de datos tomando una abierta por otro objeto
	 *
	 * @param cconn Conexión a la base da datos
	 */
	public void setConexion(Connection cconn) {
		try {
			this.conn = cconn;
			bConexionHeredada = true;
			//CREA EL STATEMENT
			st = conn.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abreConexion(String sDriver, String sUrl, String sUsuario, String sPassword) throws EJBException {
		try {
			Class.forName(sDriver);
			conn = DriverManager.getConnection(sUrl, sUsuario, sPassword);
		}
		catch (Exception e) {
			System.out.println("******************************************************");
			System.out.println("Error al abrir la conexion a la base de datos");
			System.out.println("");
			System.out.println("Descripcion del error:" + e.getMessage());
			System.out.println("******************************************************");
			e.printStackTrace();
			throw new EJBException();
		}
	}

	/**
	 * Abre una conexion a la base de datos utilizando la referencia de un DataSource.
	 *
	 * @param sEnvReference Nombre de la referencia del DataSource
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void abreConexion(String sEnvReference) throws EJBException {
		try {
			Context jndiCntx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource) jndiCntx.lookup(sEnvReference);
			conn = ds.getConnection();
		}
		catch (Exception e) {
			System.out.println("******************************************************");
			System.out.println("Error al abrir la conexion a la base de datos utilizando la referencia de ambiente");
			System.out.println("");
			System.out.println("Descripcion del error:" + e.getMessage());
			System.out.println("******************************************************");
			e.printStackTrace();
			throw new EJBException();
		}
	}

	/**
	 * Inhabilita el modo Auto-commit.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void inhabilitaAutoCommit() throws EJBException {
		try {
			conn.setAutoCommit(false);			
		}
		catch (Exception e) {
			throw new EJBException();
		}
	}

	/**
	 * Cierra la transacción.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void commit() throws EJBException {
		try {
			conn.commit();		}
		catch (Exception e) {
			throw new EJBException();
		}		
	}

	/**
	 * Restablece la transacción.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void rollback() throws EJBException {
		try {
			conn.rollback();
		}	
		catch (Exception e) {
			e.printStackTrace();
			throw new EJBException();
		}
	}

	/**
	 * Cierra la conexion a la base de datos. Si la conexion fue abierta por otro objeto y
	 * pasada a la clase por el metodo setConexion(), entonces solo se cerrarán los objetos statement y
	 * resulset
	 */
	public void cierraConexion() {
		try {
			//VERIFICA SI ESTA ABIERTO UN RESULTSET
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}

			//VERIFICA SI LA CONEXION NO FUE HEREDADA POR OTRO OBJETO
			if (!bConexionHeredada) {
				//CIERRA LA CONEXION A LA BASE DE DATOS
				conn.close();
				conn = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		bConexionHeredada = false;
	}

	/**
	 * Verifica si la clase tiene hecha una conexion a la base de datos
	 * @return Verdadero - Si la clase tiene una conexión a la base de datos.
	 */
	public boolean isConectado() {
		if (conn != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Ejecuta un procedimiento almacenado. La llamada a la base de datos se construye en la
	 * variable sSql y los carácteres que se utilizan para indicar la naturaleza de los
	 * parametros son los siguientes:
	 * <ul type=disc>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'><span lang=ES-MX
	 *     style='mso-ansi-language:ES-MX'>C= Char, VarChar<o:p></o:p></span></li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'><span lang=ES-MX
	 *     style='mso-ansi-language:ES-MX'>R= Cursor<o:p></o:p></span></li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>I= Integer</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>F= Float</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>B= Double</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'>D= Date</li>
	 * <li class=MsoNormal style='mso-margin-top-alt:auto;mso-margin-bottom-alt:auto;
	 *     mso-list:l1 level1 lfo2;tab-stops:list 36.0pt'><span lang=ES-MX
	 *     style='mso-ansi-language:ES-MX'>T= Time<o:p></o:p></span></li>
	 *</ul>
	 *
	 * La ejecución del procedimiento almacenado se deposita en la variable sto. La variable sSql
	 * se modifica y se eliminan los caracteres utilizados para identificar la naturaleza de los
	 * parámetros
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void ejecutaSp() throws EJBException {
		int iIndice = 0;
		int[] iTipos = new int[10];
		String sTipo = "";
		String sTemp = "";
		int iTipo = 0;
		int iParametros = 0;
		int iTamaño = 0;

		try{
			//OBTIENE LA LISTA DE PARAMETROS DE SALIDA DE LA CADNA sSQL
			iTamaño = sSql.length();
			iIndice = sSql.indexOf("?");
			while (iIndice != -1) {
				sTipo = sSql.substring(iIndice + 1, iIndice + 2);

				/*VERIFICA QUE TIPO DE DATO ES EL DEL PARAMETRO. LOS PARAMETROS IMPLEMTADOS SON LOS
				SIGUIENTES:
				C= Char, VarChar
				R= Cursor
				I= Integer
				F= Float
				B= Double
				D= Date
				T= Time
				 */
				if (sTipo.equals("C")) {
					iTipo = java.sql.Types.CHAR;
				}
				else {
					if (sTipo.equals("R")) {
						iTipo = java.sql.Types.OTHER;
					}
					else {
						if (sTipo.equals("I")) {
							iTipo = java.sql.Types.INTEGER;
						}
						else {
							if (sTipo.equals("F")) {
								iTipo = java.sql.Types.FLOAT;
							}
							else {
								if (sTipo.equals("B")) {
									iTipo = java.sql.Types.DOUBLE;
								}
								else {
									if (sTipo.equals("T")) {
										iTipo = java.sql.Types.TIME;
									}
									else {
										iTipo = java.sql.Types.OTHER;
									}
								}
							}
						}
					}
				}
				sTemp = sTemp + sSql.substring(0, iIndice + 1);
				sSql = sSql.substring(iIndice + 2, iTamaño);

				iTipos[iParametros] = iTipo;
				iParametros++;

				iTamaño = sSql.length();
				iIndice = sSql.indexOf("?");
			}

			//COMPLETA LA LLAMADA AL PROCEDIMIENTO ALMACENADO
			if (iParametros > 0) {
				sSql = sTemp + sSql;
			}


			//VERIFICA SI EL STATEMENT ESTA ABIERTO
			if (sto != null) {
				//CIERRA EL STATEMENT
				sto.close();
				sto = null;
			}

			//PREPARA LA LLAMADA AL PROCEDIMIENTO ALMACENADO
			sto = conn.prepareCall(sSql);

			//REGISTRA LOS PARAMETROS DE SALIDA
			for (int iParametro = 0; iParametro < iParametros; iParametro++) {
				sto.registerOutParameter(iParametro + 1, iTipos[iParametro]);
			}
			//EJECUTA EL PROCEDIMIENTO ALMACENADO
			sto.execute();			
		}
		catch(Exception e){
			throw new EJBException();
		}
	}

	/**
	 * Ejecuta un procedimiento almacenado. A diferencia del método anterior este recibe como
	 * parámetro la llamada al procedimiento almacenado. Una vez ejecutado el método la variable
	 * sSql queda con la cadena que se utilizó para llamar al procedimiento almacenado.
	 *
	 * @param sProcedimiento Cadena que representa la llamada al procedimiento almacenado.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void ejecutaSp(String sProcedimiento) throws EJBException {
		sSql = sProcedimiento;
		ejecutaSp();
	}

	/**
	 * Ejecuta una setencia sql. Toma el valor de la variable sSql y ejecuta la consulta sobre la
	 * base de datos. El resultado de la consulta se podrá procesar a través de la variable rs, la
	 * cual es de tipo ResultSet. Este método emplea la variable st que es de tipo Statement para
	 * realizar la conexion a la base de datos.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void ejecutaSql() throws EJBException {
		try {
			//VERIFICA SI HAY ALGUNA CONSULTA ABIERTA
			if (rs != null) {
				//CIERRA LA CONSULTA
				rs.close();
				rs = null;
			}

			//VERIFICA SI EL STATEMENT ESTA ABIERTO
			if (st != null) {
				//CIERRA EL STATEMENT
				st.close();
				st = null;
			}
			//ABRE UN STATEMENT
			st = conn.createStatement();
			rs = st.executeQuery(sSql);
		}
		catch (SQLException e) {
			System.out.println("******************************************************");
			System.out.println("Error al ejecutar la siguiente consulta:");
			System.out.println(sSql);
			System.out.println("");
			System.out.println("Descripcion del error:" + e.getMessage());
			System.out.println("******************************************************");
			throw new EJBException();
		}
	}

	/**
	 * Ejecuta una sentencia sql. A diferencia del método anterior este recibe como parámetro
	 * la consulta SQL.
	 *
	 * @param sConsulta Consulta SQL.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 */
	public void ejecutaSql(String sConsulta) throws Exception {
		try {
			//VERIFICA SI HAY ALGUNA CONSULTA ABIERTA
			if (rs != null) {
				//CIERRA LA CONSULTA
				rs.close();
				rs = null;
			}

			//VERIFICA SI EL STATEMENT ESTA ABIERTO
			if (st != null) {
				//CIERRA EL STATEMENT
				st.close();
				st = null;
			}
			//ABRE UN STATEMENT
			st = conn.createStatement();
			rs = st.executeQuery(sConsulta);
		}
		catch (SQLException e) {
			System.out.println("******************************************************");
			System.out.println("Error al ejecutar la siguiente consulta:");
			System.out.println(sSql);
			System.out.println("");
			System.out.println("Descripcion del error:" + e.getMessage());
			System.out.println("******************************************************");
			throw new Exception();
		}
	}


	/**
	 * Ejecuta una setencia sql de INSERT, UPDATE o DELETE. Toma el valor de la variable sSql y ejecuta la consulta sobre la
	 * base de datos. El resultado de la consulta será el número de registros procesados por las
	 * sentencias INSERT, UPDATE o DELETE, o 0 para una setentencia que no regresa algún valor
	 * cual es de tipo ResultSet. Este método emplea la variable st que es de tipo Statement para
	 * realizar la conexion a la base de datos.
	 * @throws SQLException Si se genera algún error al accesar la base de datos.
	 * @return La cantidad de registros que fueron afectados.
	 */
	public int ejecutaUpdate() throws EJBException {
		int iNumRegistros = 0;
		try {
			//VERIFICA SI HAY ALGUNA CONSULTA ABIERTA
			if (rs != null) {
				//CIERRA LA CONSULTA
				rs.close();
				rs = null;
			}

			//VERIFICA SI EL STATEMENT ESTA ABIERTO
			if (st != null) {
				//CIERRA EL STATEMENT
				st.close();
				st = null;
			}

			//ABRE UN STATEMENT
			st = conn.createStatement();

			iNumRegistros = st.executeUpdate(sSql);
		}
		catch (SQLException e) {
			System.out.println("******************************************************");
			System.out.println("Error al ejecutar la siguiente actualizacion de datos:");
			System.out.println(sSql);
			System.out.println("");
			System.out.println("Descripcion del error:" + e.getMessage());
			System.out.println("******************************************************");
			/*
			StackTraceElement[] lista = e.getStackTrace();
			int iElemento = 0;
			for (StackTraceElement element: lista){
				System.out.println(element.getClassName() + " - " + element.getMethodName() + " - " + element.getLineNumber());
				if (iElemento == 5){
					break;
				}
			}
			*/
			throw new EJBException(e.getMessage());
		}
		return iNumRegistros;
	}

	public Registro getConsultaRegistro(boolean bClobProcesa, boolean bClobTruncado, int iNumMaxRenglones, int iNumMaxCaracteresRenglon, boolean bBlobProcesa) throws EJBException {
		Registro registro = null;

		try {
			if (rs.next()) {
				registro = getDatosRegistro(rs, rs.getMetaData(), bClobProcesa, bClobTruncado, iNumMaxRenglones, iNumMaxCaracteresRenglon, bBlobProcesa);
			}
		}	
		catch (Exception e) {
			e.printStackTrace();
			throw new EJBException();
		}		
		return registro;
	}

	public Registro getConsultaRegistro() throws EJBException {
		return getConsultaRegistro(false, false, 0, 0, false);
	}


	public LinkedList getConsultaLista() throws EJBException {
		return getConsultaLista(false, false, 0, 0, false);
	}

	public LinkedList getConsultaLista(boolean bClobProcesa, boolean bClobTruncado, int iNumMaxRenglones, int iNumMaxCaracteresRenglon, boolean bBlobProcesa) throws EJBException {
		//RECORRE LA LISTA DE REGISTROS QUE COMPONEN EL RESULTADO DE LA CONSULTA
		ResultSetMetaData informacionConsulta = null;
		boolean bInicio = true;
		LinkedList lista = new LinkedList();
		
		try {
			int iNumLinea = 1;
			while (rs.next()) {
				//OBTIENE EL NUMERO DE LAS COLUMNAS DE LA CONSULTA E INICIALIZA LA LISTA DE REGISTRO DE SALIDA
				if (bInicio) {
					informacionConsulta = rs.getMetaData();
					bInicio = false;
				}
				Registro registro = getDatosRegistro(rs, informacionConsulta, bClobProcesa, bClobTruncado, iNumMaxRenglones, iNumMaxCaracteresRenglon, bBlobProcesa);
				if (iNumLinea % 2 == 0) {
					registro.addDefCampo("REGISTRO_PAR", "");
				}
				else {
					registro.addDefCampo("REGISTRO_IMPAR", "");
				}
				registro.addDefCampo("NUMERO_REGISTRO_LISTA", String.valueOf(iNumLinea));
				lista.add(registro);
				iNumLinea++;
			}
		}	
		catch (Exception e) {
			e.printStackTrace();
			throw new EJBException();
		}		
		
		return lista;
	}

	private Registro getDatosRegistro(ResultSet rs, ResultSetMetaData informacionConsulta, boolean bClobProcesa, boolean bClobTruncado, int iNumMaxRenglones, int iNumMaxCaracteresRenglon, boolean bBlobProcesa) throws EJBException {
		String sColumnaNombre = "";
		String sColumnaValor = "";

		SimpleDateFormat formatoFechaSimple = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat formatoFechaHoraMilisegundos = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formatoHoraMilisegundos = new SimpleDateFormat("HH:mm:ss.SSS");

		Registro registro = new Registro();
		
		try {
			int iTotColumnas = informacionConsulta.getColumnCount();
			//AGREGA LAS COLUMNAS DEL REGISTRO AL OBJETO REGISTRO
			for (int iColumna = 1; iColumna <= iTotColumnas; iColumna++) {
				sColumnaNombre = informacionConsulta.getColumnName(iColumna);

				int iTipoColumna = informacionConsulta.getColumnType(iColumna);

				//VERIFICA EL TIPO DE COLUMNA DE LA CONSULTA
				switch (iTipoColumna) {
					case java.sql.Types.CLOB:

						//VERIFICA SI SE DEBE PROCESAR ESTE TIPO DE CAMPO
						if (bClobProcesa) {
							// SI EL TIPO DE DATOS ES CLOB SE TRANSFORMA A STRING
							Clob clob = (Clob) rs.getClob(iColumna);
							if (clob != null) {

								//OBTENEMOS EL CAMPOS CLOB COMO STRING
								long lTamanio = clob.length();
								StringBuffer buf = new StringBuffer();
								String sXml = clob.getSubString(1, (int) lTamanio);
								buf.append(sXml);
								sColumnaValor = buf.toString();

								//VERIFICAMOS SI TRUNCAMOS EL VALOR DEL CLOB
								if (bClobTruncado) {
									sColumnaValor = procesaClob(sColumnaValor, iNumMaxRenglones, iNumMaxCaracteresRenglon);

									/*///FORMATEAMOS EL CAMPO STRING DEL CLOB
									Scanner scanner = new Scanner(sColumnaValor);
									StringBuffer sCampoFormateado = new StringBuffer();

									int iRenglon = 0;
									while (scanner.hasNext()) {
										String sRenglon = scanner.nextLine();
										String sRenglonFormateado = "";
										int iLongitud = sRenglon.length();
										if (iLongitud > 0) {
											if (iLongitud > iNumMaxCaracteresRenglon) {
												bInformaciónCortada = true;
												sRenglonFormateado = sRenglon.substring(0, iNumMaxCaracteresRenglon);
											}
											else {
												sRenglonFormateado = sRenglon.substring(0, iLongitud);
											}
										}
										else {
											sRenglonFormateado = sRenglon;
										}
										sCampoFormateado.append(sRenglonFormateado);
										sCampoFormateado.append("\n");
										iRenglon++;
										if (iRenglon >= iNumMaxRenglones) {
											bInformaciónCortada = true;
											break;
										}
									}
									sColumnaValor = sCampoFormateado.toString();*/
								}//VERIFICAMOS SI TRUNCAMOS EL VALOR DEL CLOB

								registro.addDefCampo(sColumnaNombre, sColumnaValor);
								sColumnaValor = sColumnaValor.replace("<", "&lt;");
								sColumnaValor = sColumnaValor.replace(">", "&gt;");
								sColumnaValor = sColumnaValor.replace(" ", "&nbsp;");
								sColumnaValor = sColumnaValor.replace("\n", "<br>");
								registro.addDefCampo(sColumnaNombre + "_HTML", sColumnaValor);
								if (CLOB_TRUNCADO) {
									registro.addDefCampo(sColumnaNombre + "_TRUNCADO", "");
								}
							}
							else {
								registro.addDefCampo(sColumnaNombre, "");
							}

						}//VERIFICA SI SE DEBE PROCESAR ESTE TIPO DE CAMPO
						break;

					case java.sql.Types.BLOB:
						byte[] columnaValor = rs.getBytes(iColumna);
						if (columnaValor != null) {
							registro.addDefCampo(sColumnaNombre, columnaValor);
						}
						else {
							registro.addDefCampo(sColumnaNombre, "");
						}
						break;

					case java.sql.Types.TIMESTAMP:
						java.sql.Timestamp fechaHora = rs.getTimestamp(iColumna);
						if (fechaHora != null) {
							java.util.Date fecha = new java.util.Date(fechaHora.getTime());
							registro.addDefCampo(sColumnaNombre, fecha);
							registro.addDefCampo(sColumnaNombre + "_DDMMAAAA", formatoFechaSimple.format(fecha));
							registro.addDefCampo(sColumnaNombre + "_DDMMAAAA24Hrs", formatoFechaHora.format(fecha));
							registro.addDefCampo(sColumnaNombre + "_DDMMAAAA24HrsMilis", formatoFechaHoraMilisegundos.format(fecha));
							registro.addDefCampo(sColumnaNombre + "24Hrs", formatoHora.format(fecha));
							registro.addDefCampo(sColumnaNombre + "24HrsMilis", formatoHoraMilisegundos.format(fecha));
						}
						else {
							registro.addDefCampo(sColumnaNombre, "");
						}
						break;

					case java.sql.Types.DATE:
						// SI EL TIPO DE DATOS ES CLOB SE TRANSFORMA A STRING
						java.sql.Date fechaSql = rs.getDate(iColumna);
						if (fechaSql != null) {
							java.util.Date fecha = new java.util.Date(fechaSql.getTime());
							registro.addDefCampo(sColumnaNombre, fecha);
							registro.addDefCampo(sColumnaNombre + "_DDMMAAAA", formatoFechaSimple.format(fecha));
							registro.addDefCampo(sColumnaNombre + "_DDMMAAAA24Hrs", formatoFechaHora.format(fecha));
							registro.addDefCampo(sColumnaNombre + "_DDMMAAAA24HrsMilis", formatoFechaHoraMilisegundos.format(fecha));
							registro.addDefCampo(sColumnaNombre + "24Hrs", formatoHora.format(fecha));
							registro.addDefCampo(sColumnaNombre + "24HrsMilis", formatoHoraMilisegundos.format(fecha));

						}
						else {
							registro.addDefCampo(sColumnaNombre, "");
						}
						break;

					//CASO DEFAULT
					default:

						sColumnaValor = rs.getString(iColumna);
						if (sColumnaValor != null) {

							//VERIFICAMOS SI TRUNCAMOS EL VALOR DEL CLOB SOLO SI ES EL CAMPO INDICADO
							if ((bClobTruncado) && ((sColumnaNombre.equals("DOC_WS")) || (sColumnaNombre.equals("SIGERXML")))) {
								sColumnaValor = procesaClob(sColumnaValor, iNumMaxRenglones, iNumMaxCaracteresRenglon);
								/*//FORMATEAMOS EL CAMPO STRING DEL CLOB
								Scanner scanner = new Scanner(sColumnaValor);
								StringBuffer sCampoFormateado = new StringBuffer();
								int iRenglon = 0;
								while (scanner.hasNext()) {
									String sRenglon = scanner.nextLine();
									String sRenglonFormateado = "";
									int iLongitud = sRenglon.length();
									if (iLongitud > 0) {
										if (iLongitud > iNumMaxCaracteresRenglon) {
											bInformaciónCortada = true;
											sRenglonFormateado = sRenglon.substring(0, iNumMaxCaracteresRenglon);
										}
										else {
											sRenglonFormateado = sRenglon.substring(0, iLongitud);
										}
									}
									else {
										sRenglonFormateado = sRenglon;
									}
									sCampoFormateado.append(sRenglonFormateado);
									sCampoFormateado.append("\n");
									iRenglon++;
									if (iRenglon >= iNumMaxRenglones) {
										bInformaciónCortada = true;
										break;
									}
								}
								sColumnaValor = sCampoFormateado.toString();*/
							}//VERIFICAMOS SI TRUNCAMOS EL VALOR DEL CLOB

							registro.addDefCampo(sColumnaNombre, sColumnaValor);
							sColumnaValor = sColumnaValor.replace("<", "&lt;");
							sColumnaValor = sColumnaValor.replace(">", "&gt;");
							sColumnaValor = sColumnaValor.replace(" ", "&nbsp;");
							sColumnaValor = sColumnaValor.replace("\n", "<br>");
							registro.addDefCampo(sColumnaNombre + "_HTML", sColumnaValor);

							if (CLOB_TRUNCADO) {
								registro.addDefCampo(sColumnaNombre + "_TRUNCADO", "");
							}
						}
						else {
							registro.addDefCampo(sColumnaNombre, "");
						}

						break;
				}//SWITCH
			}//AGREGA LAS COLUMNAS AL OBJETO REGISTRO
		}	
		catch (Exception e) {
			e.printStackTrace();
			throw new EJBException();
		}		
		return registro;
	}

	public String procesaClob(String clob, int iNumMaxRenglones, int iNumMaxCaracteresRenglon) {
		CLOB_TRUNCADO = false;

		Scanner scanner = new Scanner(clob);
		StringBuffer sCampoFormateado = new StringBuffer();

		int iRenglon = 0;
		while (scanner.hasNext()) {
			String sRenglon = scanner.nextLine();
			String sRenglonFormateado = "";
			int iLongitud = sRenglon.length();
			if (iLongitud > 0) {
				if (iLongitud > iNumMaxCaracteresRenglon) {
					CLOB_TRUNCADO = true;
					sRenglonFormateado = sRenglon.substring(0, iNumMaxCaracteresRenglon);
				}
				else {
					sRenglonFormateado = sRenglon.substring(0, iLongitud);
				}
			}
			else {
				sRenglonFormateado = sRenglon;
			}
			sCampoFormateado.append(sRenglonFormateado);
			sCampoFormateado.append("\n");
			iRenglon++;
			if (iRenglon >= iNumMaxRenglones) {
				CLOB_TRUNCADO = true;
				break;
			}
			clob = sCampoFormateado.toString();
		}//VERIFICAMOS SI TRUNCAMOS EL VALOR DEL CLOB

		return clob;
	}
}
