package mx.gob.imss.ctirss.ws.asignacion.implementacion.mainframe;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.CanaseBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.ResponseMainFrameBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.exception.SindoException;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.util.EvaluaRegistrosUtil;
import ibi.telnet.Terminal;
import ibi.telnet.api.Filter;
import ibi.telnet.api.ScreenDesc;
import ibi.telnet.api.Session;
import ibi.telnet.api.TnDriver;
import ibi.telnet.api.TnDriverException;

/**
 * 
 * @author JCSH
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * Mod:04-Enero-2006
 * IS:JCSH
 * Modificacion en curp ya que no esta activo en campo en la aplicacions sics
 */
public class TransaccionMainFrame {
	
	private static Logger log = Logger.getLogger(TransaccionMainFrame.class);
	
	
	private String usuario;
	private String clave;
	private String nodo;
	private int puerto;
    private boolean activaDebug;
    private int tiempoRespuesta;
	private String ciz_cics;
	private Session session;
	private ScreenDesc [] pantallas;
	private final String LOGIN_COMMAND ="cesn";
	private final String PANTALLA_BIENVENIDA = "CENTRO NACIONAL DE TECNOLOGIAS DE LA INFORMACION";
	private final String PANTALLA_POSTBIENVENIDA = "TRANSACTION SERVER";
	private final String USER = "Userid . . . .";
	private final String PASS = "Password . . .";
	private final String LOGIN_SUCCESS = "Sign-on is complete (Language ENU).";
	private final String LOGIN_REVOKED = "Your signon userid has been revoked. Signon is terminated.";
	private final String TRANSACTION_DISABLED = "has been disabled and cannot be used.";
	private final String LOGIN_IN_USE = "DFHCE3588 You are already signed on at another terminal. Signon cannot be";
	private final String LOGIN_UNAUTHORIZED = "You are not authorized to use transaction ";
	private final String PASSWORD_EXPIRED = "DFHCE3525 Your password has expired. Please type your new password.";
	private final String PASSWORD_INCORRECT = "Your password is invalid. Please retype.";
	private final String SINDO_OFFLINE = "POR FAVOR REPORTE A TP *SESSION NOT BOUND*";
	
	private final String DEL_REQUERIDA = "E24.-LA <DELEG> Y/O <SUBDELEG> ES REQUERIDA .";
	private final String USER_NO_AUTO = "E64.-USUARIO NO AUT. PARA ASIG. NSS EN ESTA DEL.Y SUB.";
    private final String USER_NO_AUTO2 = "E63.-USUARIO NO AUTORIZADO PARA ASIGNAR NSS"; 
    
    
	private final String ANIO_NO_VAL="E74.-EL <A O DE INCRIPCION ES INVALIDO>";
	private final String ANIO_NAC_NO_VAL="E12.-EL <A O DE NACIMIENTO ES INVALIDO>";
	private final String EXISTEN_IGUALES="W48";
	private final String EXISTE_NSS="S45.-EL <NUMERO DE SEG.SOCIAL> YA EXISTE EN <CANASE>";
	//validaciones para el login
	private final String USER_INVALID = "DFHCE3530 Your userid is invalid. Please retype.";
	private final String SIN_CURP = "E75.-TECLEAR LA C.U.R.P. ES REQUERIDA";
	
	public TransaccionMainFrame(String nodo, int puerto, String usuario, String pass, String ciz, boolean debug , int tiempoRespuesta)throws Exception{
		//log.debug("estoy  en el constructor de mainfrane");
		//log.debug("user: "+usuario);
		//log.debug("pass: "+pass);
		this.usuario=usuario;
		this.clave=pass;
		this.nodo=nodo;
		this.puerto=puerto;
		this.ciz_cics=ciz;
        this.activaDebug = debug;
        this.tiempoRespuesta = tiempoRespuesta;
		inicializaFiltros();
        //log.debug("sali del constructor");
	}
	
	private void inicializaFiltros()throws Exception{
		//log.debug("estoy inicializando filtros");
		Filter pantallaBienvenida = new Filter(1,PANTALLA_BIENVENIDA,Filter.MATCHOP_CONTAINS);
		Filter pantallaPostBienvenida = new Filter(7,PANTALLA_POSTBIENVENIDA,Filter.MATCHOP_CONTAINS);
		Filter user = new Filter(9,USER , Filter.MATCHOP_CONTAINS);
		Filter pass = new Filter(15,PASS , Filter.MATCHOP_CONTAINS);
		Filter userOK  = new Filter(1,LOGIN_SUCCESS  , Filter.MATCHOP_CONTAINS);
		Filter userRevoked = new Filter(1,LOGIN_REVOKED , Filter.MATCHOP_CONTAINS);
		Filter transactionDisabled = new Filter(1,TRANSACTION_DISABLED , Filter.MATCHOP_CONTAINS);
		Filter loginInUse = new Filter(1,LOGIN_IN_USE , Filter.MATCHOP_STARTSWITH);
		Filter loginUnauthorized = new Filter(1,LOGIN_UNAUTHORIZED , Filter.MATCHOP_CONTAINS);
		Filter passwordExpired = new Filter(27,PASSWORD_EXPIRED , Filter.MATCHOP_STARTSWITH);
		Filter passwordIncorrect = new Filter(27,PASSWORD_INCORRECT , Filter.MATCHOP_CONTAINS);
		Filter sindoOffline = new Filter(0,SINDO_OFFLINE , Filter.MATCHOP_CONTAINS);
		
		Filter delrequerida = new Filter(33, DEL_REQUERIDA, Filter.MATCHOP_CONTAINS);
		Filter usernoauto = new Filter(33, USER_NO_AUTO, Filter.MATCHOP_CONTAINS);
        Filter usernoauto2 = new Filter(33, USER_NO_AUTO2, Filter.MATCHOP_CONTAINS);
		Filter anionoval=new Filter(33,ANIO_NO_VAL,Filter.MATCHOP_CONTAINS);
		Filter anionacnoval=new Filter(40, ANIO_NAC_NO_VAL, Filter.MATCHOP_CONTAINS);
		Filter existeniguales = new Filter(40,EXISTEN_IGUALES , Filter.MATCHOP_STARTSWITH);
		Filter existenss= new Filter(40, EXISTE_NSS, Filter.MATCHOP_CONTAINS);
		Filter curp= new Filter(40, SIN_CURP, Filter.MATCHOP_STARTSWITH);
		
		Filter loginInvalid =  new Filter(27, USER_INVALID, Filter.MATCHOP_CONTAINS); 
		
		this.pantallas= new ScreenDesc[38];
		this.pantallas[0]=new ScreenDesc();
		this.pantallas[0].addFilter(pantallaBienvenida);
		this.pantallas[1]=new ScreenDesc();
		this.pantallas[1].addFilter(pantallaPostBienvenida);
		this.pantallas[2]=new ScreenDesc();
		this.pantallas[2].addFilter(passwordExpired);
		this.pantallas[3]=new ScreenDesc();
		this.pantallas[3].addFilter(passwordIncorrect);
		this.pantallas[4]=new ScreenDesc();
		this.pantallas[4].addFilter(userOK);
		this.pantallas[5]=new ScreenDesc();
		this.pantallas[5].addFilter(userRevoked);
		this.pantallas[6]=new ScreenDesc();
		this.pantallas[6].addFilter(transactionDisabled);
		this.pantallas[7]=new ScreenDesc();
		this.pantallas[7].addFilter(loginInUse);
		this.pantallas[8]=new ScreenDesc();
		this.pantallas[8].addFilter(loginUnauthorized);
		this.pantallas[9]=new ScreenDesc();
		this.pantallas[9].addFilter(user);
		this.pantallas[9].addFilter(pass);
		this.pantallas[10]=new ScreenDesc();
		this.pantallas[10].addFilter(sindoOffline);
		
		this.pantallas[11]=new ScreenDesc();
		this.pantallas[11].addFilter(delrequerida);
		this.pantallas[12]=new ScreenDesc();
		this.pantallas[12].addFilter(usernoauto);
		this.pantallas[13]=new ScreenDesc();
		this.pantallas[13].addFilter(anionoval);
		this.pantallas[14]=new ScreenDesc();
		this.pantallas[14].addFilter(anionacnoval);
		this.pantallas[15]=new ScreenDesc();
		this.pantallas[15].addFilter(existeniguales);
		this.pantallas[16]=new ScreenDesc();
		this.pantallas[16].addFilter(existenss);
		
		
		
		
		// Filtros para la consulta de NSS
		Filter transaccionSC01a = new Filter(1, "SISTEMA INTEGRAL DE DERECHOS Y OBLIGACIONES",
				Filter.MATCHOP_CONTAINS);
		Filter transaccionSC01b = new Filter(10, "CONSULTA DE ASEGURADOS POR NUM. DE SEGURIDAD",
				Filter.MATCHOP_CONTAINS);
		
		//* llenando los campos de la consulta
		Filter menuSC01a = new Filter(8, "APELLIDO PATERNO:",
				Filter.MATCHOP_CONTAINS);
		Filter menuSC01b = new Filter(1, "CONSULTA ALFABETICA DEL CATALOGO NACIONAL",
				Filter.MATCHOP_STARTSWITH);		
		
		// BUSCAR COINCIDENCIA!!!
		//Entrando a la consulta
		this.pantallas[17]=new ScreenDesc();
		this.pantallas[17].addFilter(transaccionSC01a);
		this.pantallas[17].addFilter(transaccionSC01b);
		//Ingresa datos de la consulta
		this.pantallas[18]=new ScreenDesc();
		this.pantallas[18].addFilter(menuSC01a);
		this.pantallas[18].addFilter(menuSC01b);
		
		//		* para alta de NSS
		Filter menuNSSAlta = new Filter(33,"I23.-DIGITE DEL., SUBD., Y EN SU CASO NSS-CONV,NSS-HOM O NSS-MOD45", Filter.MATCHOP_CONTAINS);
		Filter menuNSSc = new Filter(33, "E10.-COMPLETE DATOS P/<HOMON.> O <MOD-45> Y PULSE PF1", Filter.MATCHOP_CONTAINS);
		Filter menuNSSd = new Filter(33, "I23.-PULSE <PF1> P/CONFIRMAR <DELEG> Y <SUBDELEG> O CORRIJA DATOS", Filter.MATCHOP_CONTAINS);
		//Filter menuNSSe = new Filter(25, "A O DE INSCRIP. TRABAJ.   :", Filter.MATCHOP_CONTAINS);
		Filter menuNSSerror = new Filter(33, "E74.-EL <AñO DE INCRIPCION ES INVALIDO>", Filter.MATCHOP_CONTAINS);
		// insertar datos del asegurado
		Filter datosNSSa = new Filter(40, "I44.-INICIE CAPTURA. APELLIDOS PATERNO,MATERNO Y NOMBRE(S) S/ABREVIAR", Filter.MATCHOP_CONTAINS);	     
		
		// confirmacion de datos  S45.-EL <NUMERO DE SEG.SOCIAL> YA EXISTE EN <CANASE>
		
		Filter altaNSSa = new Filter(40, "I37.-PULSE <PF1> PARA CONF. ALTA O ENTER PARA CORREGIR", Filter.MATCHOP_CONTAINS);
		//I38 Es la cve del mensaje que indica que se generó un nuevo NSS
		Filter altaNSSb = new Filter(40, "I38.-<NSS> ASIGNADO. PULSE <ENTER> PARA CONTINUAR", Filter.MATCHOP_CONTAINS);
		Filter sinSerie = new Filter(40, "I47.-LA <SERIE> ESTA AGOTADA. NO ES POSIBLE DAR <ALTA>", Filter.MATCHOP_CONTAINS);
		Filter nacInv = new Filter(40, "E12.-EL <AñO DE NACIMIENTO ES INVALIDO>", Filter.MATCHOP_CONTAINS);
        Filter erroCanaseNat = new Filter(20, "FAVOR DE TOMAR NOTA Y DAR AVISO A LA", Filter.MATCHOP_CONTAINS);
        Filter errorCanaseMesNac = new Filter(40, "E34.-EL <MES DE NACIMIENTO> ES INVALIDO. PULSE PF9",Filter.MATCHOP_CONTAINS);
        Filter errorCanaseLugarNac = new Filter(40, "E35.-EL <LUGAR DE NACIMIENTO> ES INVALIDO. PULSE PF9",Filter.MATCHOP_CONTAINS);
        Filter consultaCurp = new Filter(1, "CONSULTA DE ASEGURADOS POR C.U.R.P.", Filter.MATCHOP_CONTAINS);
        Filter noEncuentraCurp= new Filter(29, "E76.-NO EXISTE <CLAVE UNICA DE REG. POBL.> REINTENTE", Filter.MATCHOP_CONTAINS);
        Filter recuperaNSSCurp = new Filter(29,"I17.-CONSULTA EFECTUADA", Filter.MATCHOP_CONTAINS);
        Filter serieNoExiste = new Filter(29,"E30.-LA SERIE DE ASIGNACION NO EXISTE. VERIFIQUE", Filter.MATCHOP_CONTAINS);
		
		this.pantallas[19]=new ScreenDesc();
		this.pantallas[19].addFilter(menuNSSAlta);
		
		this.pantallas[20]=new ScreenDesc();
		this.pantallas[20].addFilter(menuNSSc);
		//this.pantallas[20].addFilter(menuNSSe);
		
		this.pantallas[21]=new ScreenDesc();
		this.pantallas[21].addFilter(menuNSSd);     
		
		
		this.pantallas[22]=new ScreenDesc();
		this.pantallas[22].addFilter(datosNSSa);		
		
		this.pantallas[23]=new ScreenDesc();
		this.pantallas[23].addFilter(altaNSSa);
		
		this.pantallas[24]=new ScreenDesc();
		this.pantallas[24].addFilter(altaNSSb);	
		
		this.pantallas[25]= new ScreenDesc();
		this.pantallas[25].addFilter(menuNSSerror);
		
		this.pantallas[26]= new ScreenDesc();
		this.pantallas[26].addFilter(sinSerie);
		
		this.pantallas[27]= new ScreenDesc();
		this.pantallas[27].addFilter(nacInv);
		
		this.pantallas[28] = new ScreenDesc();
		this.pantallas[28].addFilter(loginInvalid);
		this.pantallas[28].addFilter(user);
		this.pantallas[28].addFilter(pass);
        
        this.pantallas[29] = new ScreenDesc();
        this.pantallas[29].addFilter(erroCanaseNat);
        
        this.pantallas[30] = new ScreenDesc();
        this.pantallas[30].addFilter(errorCanaseMesNac);
        this.pantallas[31] = new ScreenDesc();
        this.pantallas[31].addFilter(errorCanaseLugarNac);
        this.pantallas[32]=new ScreenDesc();
        this.pantallas[32].addFilter(usernoauto2);
        
        this.pantallas[33]=new ScreenDesc();
        this.pantallas[33].addFilter(consultaCurp);
        this.pantallas[34]=new ScreenDesc();
        this.pantallas[34].addFilter(consultaCurp);
        this.pantallas[34].addFilter(recuperaNSSCurp);
        this.pantallas[35]=new ScreenDesc();
        this.pantallas[35].addFilter(consultaCurp);
        this.pantallas[35].addFilter(noEncuentraCurp);
        this.pantallas[36]= new ScreenDesc();
        this.pantallas[36].addFilter(serieNoExiste);
        
        this.pantallas[37]= new ScreenDesc();
        this.pantallas[37].addFilter(curp);
       
        
        
        
		//log.debug("termine de inicilizar filtros");
		
	}
	
	private void conexion(String nodo,int puerto) throws TnDriverException {
		/*TnDriver driver = new TnDriver();
		 driver.setHost(nodo);
		 driver.setPort(puerto);
		 driver.setEmulation(Terminal.TN_3270);
		 //TODO: false en TraceOn para quitar el modo debug a consola de las pantallas entregadas por CICS
		  driver.setTraceOn(true);
		  driver.setExtendedAttributes(true);
		  driver.setLanguage("Cp037");
		  driver.setTimerOn(true);
		  driver.setTimerTimeout(15);*/
		
		//prueba de jc
		TnDriver driver = new TnDriver();
		driver.setHost(nodo);
		driver.setPort(puerto);
		driver.setEmulation(Terminal.TN_3270);
		// Setting trace on for debugging purposes only
		//driver.setTraceOn(true);
        driver.setTraceOn(activaDebug);
		driver.setExtendedAttributes(true);
		driver.setLanguage("Cp037");
		
		// Setting timeout for 1 seconds
		driver.setTimerOn(true);
		driver.setTimerTimeout(this.tiempoRespuesta);
		this.session = driver.getSession();
		
		
		
		
	}
	
	private void exit() {
		try {
			//session.attention(Session.AID_CLEAR);
			//session.setFieldContent(0,LOGOUT_COMMAND);
			//session.attention(Session.AID_F6);
			//session.attention(Session.AID_F3);
			//session.attention(Session.AID_F3);
			session.attention(Session.AID_F12);
			session.close();
		} catch(Exception e){e.printStackTrace();}
	}
	
	
	
	private int identificaPantalla() throws Exception {
		int result = -1;
		try {		    
			result = session.waitFor(pantallas);
		}
		catch(Exception e){
            log.error("ERROR AL LEER PANTALLAS", e);
            throw e;
       }
		return result;
	}
	
	/**
	 * TODO: Completar el flujo para consumir la transacción
	 * El return type puede ser un Bean que represente los campos de la respuesta de SINDO
	 * El recieve type puede ser un Bean que represente los campos del request que se hará a SINDO
	 *
	 */
	public ResponseMainFrameBean getTransaction (CanaseBean req) throws SindoException{
		ResponseMainFrameBean resp = new ResponseMainFrameBean();
		SindoException e = null;
        boolean pasoXcurp = false;
        boolean esperaAsignacion = true;
		try{
			conexion(nodo,puerto);
			boolean canContinue =false;
			do
				switch (identificaPantalla()) {
				//Automatización de entrada a SINDO
				case 0: //pantalla principal
					log.debug("caso 0");
					session.setFieldContent(2, this.ciz_cics);
					session.enter();
					break;
				case 1: //pantalla post-principal
					log.debug("caso 1");
					session.enter();
					session.waitFor();
					session.setContent(LOGIN_COMMAND);
					session.enter();
					break;
				case 9: //pantalla login
					log.debug("caso 9");
					String strMensajeLogin = session.getFieldContent(27).trim();
					log.debug("mensaje login : " + strMensajeLogin);
					if(strMensajeLogin.equalsIgnoreCase(USER_INVALID)){
						e = new SindoException("La Cuenta " + usuario + " no tiene permisos de acceso a la aplicacion");
						e.setCodigoError(4);
						throw e;
					}else{
						log.debug("Estoy entrando con la cuenta de usuario " + this.usuario);
						session.setFieldContent(10, this.usuario);
						session.setFieldContent(16, this.clave);
						session.enter();
					}
					break;
				case 4: //pantalla resultado del login
					log.debug("caso 4");
					session.attention(Session.AID_CLEAR);
					session.waitFor();
                    if(req.getStrOperacion().equalsIgnoreCase("ALTA")){
                        session.setContent("PC04");
                    }else {
                        session.setContent("PC01");
                    }
					session.enter();
					//TODO: COMPLETAR CON EL CODIGO DE CONSUMO DE LA TRANSACCION
					canContinue=false;
					break;
					//Procesos de la busqueda de  NSS					
				case 17: //caso para entrar a Busqueda o signacion
					log.debug("caso 17 : operacion == "+req.getStrOperacion());
					if (req.getStrOperacion().equalsIgnoreCase("BUSQUEDA")){
						if(req.getStrCurp()!= null && req.getStrCurp().length()>0 && !pasoXcurp    ){
							log.debug("Session.AID_F4 == "+Session.AID_F4);
							session.attention(Session.AID_F4);
                            pasoXcurp= true;
                        }
                        else{
                        	log.debug("Session.AID_F1 == "+Session.AID_F1);
                        	session.attention(Session.AID_F1);
                        }
					}else if(req.getStrOperacion().equalsIgnoreCase("LOGINACCESO")){
						session.attention(Session.AID_F12);
						log.debug("el login es valido");
						resp.setStrResultado("true");
						resp.setErrorMessage("usuario valido");
						resp.setErrorNumber(1);
						canContinue = true;                
					}
					break;					
				case 18: //caso de consulta y/o ciclo de comparacion.
					log.debug("caso 18 ");
					String strNumPagina = session.getFieldContent(5).trim();
					EvaluaRegistrosUtil objEvalua = new EvaluaRegistrosUtil();
					if(strNumPagina.equals("")){
						log.debug("el nobmre es :" + req.getApaterno() + " "+req.getAmaterno() +" " +req.getNombre());
						session.setFieldContent(9,req.getApaterno());//apellido paterno
						session.setFieldContent(12,req.getAmaterno());
						session.setFieldContent(15,req.getNombre());
                        //session.setFieldContent(16,req.getNombre());
                        
						session.enter();
					}
					else{
						boolean salirPantalla = false; //*indica si no existen coincidencias dentro de la pantalla actual de respuesta de canase
						boolean salirXconsidencia = false;
						log.debug("Empieza ciclo de comparación------------\n");							
						for(int i = 31; i<=43;i++){  // dentro de la pantalla del mainframe, se dan 13 renglones de respuestas
							String linea = session.getFieldContent(i).trim(); // se toma la linea de respuesta de canase
							log.debug("la linea trae [" + linea +"]");
							if(!linea.trim().equals("") ) {
								if (!objEvalua.compareLikeCICS(EvaluaRegistrosUtil.getNombreCics(req.getNombre(),
										req.getApaterno(),req.getAmaterno()),linea.substring(0, 51).trim())){
									if (salirXconsidencia){
										salirPantalla = true;	
										log.debug("Ya no existen considencias");
										resp.setErrorMessage("no existen coinsidencias para la busqueda");
										resp.setErrorNumber(1);
										canContinue=true;
										break;
									}
									salirXconsidencia = true;
								}else{
									salirXconsidencia = false;
									resp.setStrResultado(linea);//*Checar con Juan Carlos								
								}
							}
						}						
						if(!salirPantalla){ 
							session.attention(Session.AID_F8); // pasa a la siguiente pantalla de respuesta, en caso de haber mas homonimias
						}else{
							resp.setErrorNumber(1);
							canContinue = true;
						}
					}
					String strSinRegistros = session.getFieldContent(46).trim();
					log.debug("el renglon es " + strSinRegistros);
					if(strSinRegistros.equalsIgnoreCase("I15.-FIN DE CONSULTA. PULSE TECLA DE FUNCION VALIDA")){
						resp.setErrorNumber(1);
						resp.setErrorMessage("No hay mas registros");
						resp.setStrResultado("");
						canContinue = true;
					}
					break;
					
					//procesos para el Alta de NSS, ordinario, series 99,97,36,76
				case 19: 
					log.debug("caso 19");
					log.debug("la delegacion y subdelegacion son:" + req.getStrDelegacion() +"y sub " + req.getStrSubdelegacion());
					session.setFieldContent(9, req.getStrDelegacion());
					session.setFieldContent(13,req.getStrSubdelegacion());
                    //session.setFieldContent(14,req.getStrSubdelegacion());
					if (req.getStrSerie().equals("97")||req.getStrSerie().equals("99"))
						session.setFieldContent(17,req.getStrSerie());
					if (req.getStrSerie().equals("36")||req.getStrSerie().equals("76"))
						session.setFieldContent(21,req.getStrSerie());
					session.enter();
					break;      
					
				case 20: // Datos de autenticacion para AsignacionNSS
					log.debug("caso 20");
					log.debug("estoy en el año" + req.getStrAnioInsc());
					log.debug("estoy en el año nac" + req.getAnio());
					session.setFieldContent(26,req.getStrAnioInsc().substring(2));
					session.attention(Session.AID_F1);//confirma entrada a alta
					break;
		
                case 21: // Datos de autenticacion para AsignacionNSS
                	log.debug("caso 21");
					log.debug("stoy confirmando tranzaccion");
					session.attention(Session.AID_F1);//confirma entrada a alta
					break; 
					
				case 22: //Se llena la solicitud de certificación de derechos
					log.debug("caso 22");
					log.debug("voy a insertar campos pat" + req.getApaterno()+ "mat " + req.getAmaterno()+ "nombre " +req.getNombre());
					//System.out.println("voy a insertar campos sexo" + req.getSexo()+ "mes " + req.getMesNac()+ "lugar " +req.getLugarNac());
					//System.out.println("el anio es:" + req.getAnio().substring(2) + " y la serie " + req.getStrSerie() );
					if (req.getStrSerie().equals("7")||req.getStrSerie().equals("36")||req.getStrSerie().equals("76")){
						//System.out.println("estoy en el año" + req.getStrAnioInsc());
						//System.out.println("estoy en el año nac" + req.getAnio());
						session.setFieldContent(8, req.getAnio().substring(2));//año de nac
					} else {						
						session.setFieldContent(8, req.getAnio().substring(2));//año de nacimiento
						log.debug("Ya puse el año que era lo que faltaba" + req.getAnio().substring(2));
					}

					session.setFieldContent(18, req.getApaterno());//apaterno
					session.setFieldContent(21, req.getAmaterno());//amaterno
					session.setFieldContent(24, req.getNombre());//nombre
					session.setFieldContent(27, req.getSexo());//sexo
					session.setFieldContent(30, req.getMesNac());//mes
					session.setFieldContent(33, req.getLugarNac());//lugar
                    session.setFieldContent(11, req.getUmf());
                    session.setFieldContent(36, req.getStrCurp());
                    
					session.enter();
					break;
					
				case 23: //NSSAsignado
					log.debug("caso 23");
					if(esperaAsignacion){
						log.debug("se confirman los datos");
						session.attention(Session.AID_F1);
						esperaAsignacion=false;
						}
					break;
					
				case 15: //Aunque existe el NSS se asigna uno nuevo
					log.debug("caso 15");
					log.debug("otro mas");
					session.attention(Session.AID_F1);
					break;           	
					
				case 24: //NSSAsignado
					log.debug("caso 24");
					log.debug("se asigno el num con exito");
					resp.setStrResultado(session.getFieldContent(15).trim());
					resp.setErrorMessage("Se asigno el NSS con exito");
					resp.setErrorNumber(1);
					session.enter();// limpia la pantalla
					canContinue = true;        	       
					break;
                case 33:
                	
                    log.debug("caso 33 : estoy consultando por curp con valor " + req.getStrCurp());
                    session.setFieldContent(6,req.getStrCurp());
                    session.enter();
                    session.waitFor();
                    log.debug("si encontro registro " +  session.getFieldContent(9).trim()+ session.getFieldContent(10).trim());
                    if(session.getFieldContent(9).trim().length()>0){
                    	log.debug("es > 0");
                    	//log.debug("si encontro registro " +  session.getFieldContent(9).trim()+ session.getFieldContent(10).trim());
                        resp.setStrResultado(session.getFieldContent(9).trim()+ session.getFieldContent(10).trim());
                        resp.setStrResultado(session.getFieldContent(12).trim());
                        resp.setStrResultado(session.getFieldContent(14).trim());
                        resp.setStrResultado(session.getFieldContent(16).trim());
                        resp.setStrResultado(session.getFieldContent(18).trim());
                        resp.setStrResultado(session.getFieldContent(19).trim());
                        //numero de error cuando se encontro el nss por curp
                        resp.setErrorNumber(7);
                        canContinue = true;
                        log.debug("##### 1000");
                        break; 
                    }else{
	               	    log.debug("##### 2000 , sesion "+Session.AID_F1);
						session.attention(Session.AID_F1);
	               	    
                    }
                    break;
				case 2: 
					log.debug("caso 2");
					resp.setErrorNumber(50);
					resp.setErrorMessage("El usuario debe cambiar su contraseña");
					canContinue = true;
					break;
				case 3: 
					log.debug("caso 3");
					resp.setErrorNumber(51);
					resp.setErrorMessage("Alguno de los datos de acceso esta incorrecto");
					canContinue = true;
					break;
				case 5: 
					log.debug("caso 5");
					resp.setErrorNumber(52);
					resp.setErrorMessage("La cuenta de usuario esta revocada");
					canContinue = true;
					break;
				case 6: 
					log.debug("caso 6");
					throw new SindoException("Error detectado en el proceso interno de CANASE: [Transaccion deshabilitada]");
				case 7: 
					log.debug("caso 7");
					resp.setErrorNumber(53);
					resp.setErrorMessage("La clave del Usuario " + usuario + " esta firmada en otra sesion");
					log.debug("La clave del Usuario " + usuario + " esta firmada en otra sesion");
					canContinue = true;
					break;
				case 8: 
					log.debug("caso 8");
					resp.setErrorNumber(54);
					resp.setErrorMessage("Credenciales sin permisos de acceso");
					canContinue = true;
					break;
                case 16: 
                    log.debug("Error al asignar el NSS ya existe.");
                    resp.setStrResultado("El NSS a asignar ya existe en CANASE.");
                    resp.setErrorMessage("El NSS a asignar ya existe en canase.");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
				case 25:
					log.debug("caso 25");
					log.debug("Error el año de inscripcion no es valido");
					resp.setStrResultado("El año de Inscripcion es Invalido para asignar en esta serie.");
					resp.setErrorMessage("El año de Inscripcion es Invalido para asignar en esta serie.");
					resp.setErrorNumber(2);
					e =  new SindoException(resp.getErrorMessage());
					e.setCodigoError(2);
					throw e;
				case 26:
					log.debug("caso 26");
					log.debug("Error en la serie esta agotada.");
					resp.setStrResultado("No es posible asignar NSS por que la serie esta agotada.");
					resp.setErrorMessage("No es posible asignar NSS por que la serie esta agotada.");
					resp.setErrorNumber(2);
					e =  new SindoException(resp.getErrorMessage());
					e.setCodigoError(2);
					throw e;
				case 27:
					log.debug("caso 27");
					log.debug("Error anio de nacimiento invalido");
					resp.setStrResultado("No es posible asignar NSS por que el año de Nacimiento es Invalido.");
					resp.setErrorMessage("No es posible asignar NSS por que el año de Nacimiento es Invalido.");
					resp.setErrorNumber(2);
					e =  new SindoException(resp.getErrorMessage());
					e.setCodigoError(2);
					throw e;
                case 30:
                	log.debug("caso 30");
                    log.debug("Error el mes  de nacimiento invalido");
                    resp.setStrResultado("No es posible asignar NSS por que el mes de Nacimiento es Invalido.");
                    resp.setErrorMessage("No es posible asignar NSS por que el mes de Nacimiento es Invalido.");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
                
                
                case 37:
                	log.debug("caso 37");
                	log.debug("entramo en el transaccion 37 ");
                    log.debug("Error CURP es obligatoria");
                    resp.setStrResultado("No es posible asignar NSS por que la curp es Obligatoria.");
                    resp.setErrorMessage("No es posible asignar NSS por que la curp es Obligatoria.");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
                
                case 12: 
                	log.debug("caso 12");
                    log.debug("E64.-USUARIO NO AUT. PARA ASIG. NSS EN ESTA DEL.Y SUB.");
                    resp.setStrResultado("E64.-USUARIO NO AUT. PARA ASIG. NSS EN ESTA DEL.Y SUB.");
                    resp.setErrorMessage("E64.-USUARIO NO AUT. PARA ASIG. NSS EN ESTA DEL.Y SUB.");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
                case 31:
                	log.debug("caso 31");
                    log.debug("Error lugar de nacimiento invalido");
                    resp.setStrResultado("No es posible asignar NSS por que el lugar de Nacimiento es Invalido.");
                    resp.setErrorMessage("No es posible asignar NSS por que el lugar de Nacimiento es Invalido.");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
                case 32: 
                	log.debug("caso 32");
                    log.debug("E63.-USUARIO NO AUTORIZADO PARA ASIGNAR NSS");
                    resp.setStrResultado("E63.-USUARIO NO AUTORIZADO PARA ASIGNAR NSS");
                    resp.setErrorMessage("E63.-USUARIO NO AUTORIZADO PARA ASIGNAR NSS");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
                case 36:
                	log.debug("caso 36");
                    log.debug("E30 Error: La serie asignacion no existe.");
                    resp.setStrResultado("E30 Error: La serie asignacion no existe..");
                    resp.setErrorMessage("No es posible asignar NSS por que la serie de asignacion no existe.");
                    resp.setErrorNumber(2);
                    e =  new SindoException(resp.getErrorMessage());
                    e.setCodigoError(2);
                    throw e;
				case 10:

					//throw new SindoException("Error detectado en el proceso interno de CANASE: [MainFrame fuera de línea]");
					
				case 11: 
					log.debug("caso 11");
					throw new SindoException("Error detectado en el proceso interno de CANASE: [Debe ingresar delegacion y subdelegacion]");
				
                case 13: 
                	log.debug("caso 13");
					throw new SindoException("Error detectado en el proceso interno de CANASE: [A de Inscripcion no valido]");
				case 14: 
					log.debug("caso 14");
					throw new SindoException("Error detectado en el proceso interno de CANSE: [A de nacimiento no valido]");
				case 28: 
					log.debug("caso 28");
					throw new SindoException("La Cuenta " + usuario + " no tiene permisos de acceso a la aplicacion");
				case 29: 
					log.debug("caso 29");
                    throw new SindoException("Error detectado en el proceso interno de CANASE: [ERROR EN SISTEMA CICS] avise al administrador");
				
                default:
                	log.debug("defauilt");
					throw new SindoException("Error detectado en el proceso interno de CANASE: [No identifica pantalla correcta]");
				}
			while (!canContinue);
		}catch(SindoException ex){
			
			log.error("SINDO EXCEPTION un error." + ex.getMensaje());
			log.error("error en el proceso de mainframe esperado " , ex);
			resp.setErrorMessage(ex.getMensaje());
			resp.setStrResultado(ex.getMensaje());
           throw ex;
			
		} catch (TnDriverException ex) {
            log.error("CICS errores ." + ex);
            log.error("ERROR ", ex); 
            if(ex.getMessage().matches("(.*)Read timed out(.*)")){
                resp.setErrorMessage("Existen problemas de comunicación con el equipo Central (CANASE). \n" +
                        " Favor de intentarlo mas tarde, es posible que el sistema responda lento. \n" +
                        " Si persiste esta situación, favor de reportarlo al área de informática.");
            }else {
                resp.setErrorMessage("Error de comunicación con CANASE" + ex.getMessage() +"\n avise al Area de Sistemas");
                resp.setStrResultado("Error de comunicación con CANASE" + ex.getMessage() +"\n avise al Area de Sistemas");

            }
                try {
                	session.enter();
                }catch(Exception exr) {
                    log.error(exr);
                }
			resp.setErrorNumber(5);
		} 
		catch (Exception ex){
			log.error("Otros errores ." + ex);
			log.error("Otros errores en la consulta al CANASE" , ex);
			resp.setErrorMessage("Error en la aplicacion");
			resp.setErrorNumber(6);
			resp.setStrResultado("Error en la aplicacion consultar con el administrador");
		}
		finally{
			exit();
		}
		log.debug("ya sali de transacion");
		return resp;
	}
	
}