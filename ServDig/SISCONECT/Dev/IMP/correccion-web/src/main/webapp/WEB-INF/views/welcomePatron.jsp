<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@page import="mx.gob.imss.ctirss.correccion.session.ConstantesSession"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>	
<html lang="sp">
<head>


<!--  -->
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery-ui.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/dtable/jquery.dataTables.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/bootstrap/bootstrap.min.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/bootstrap/DT_bootstrap.js"></script> 
			<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/json.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/extJS/menuDesplegableVersion.js"></script>



				<!--Scripts para el cerrado de la sesssion -->
		
				<!-- Cometd -->
				<script type="text/javascript" src="/delta/resources/js/org/cometd.js"></script>
				<script type="text/javascript" src="/delta/resources/js/org/cometd/AckExtension.js"></script>
				<script type="text/javascript" src="/delta/resources/js/org/cometd/ReloadExtension.js"></script>
				<script type="text/javascript" src="/delta/resources/js/org/cometd/TimeStampExtension.js"></script>
				<script type="text/javascript" src="/delta/resources/js/org/cometd/TimeSyncExtension.js"></script>
				
				<!-- Cometd and Jquery-->
				<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cookie.js"></script>
				<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd.js"></script>
				<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-ack.js"></script>
				<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-reload.js"></script>
				<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-timestamp.js"></script>
				<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-timesync.js"></script>
				<script type="text/javascript" src="/delta/resources/js/delta/CometConector.js"></script>		
		
			<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/home.js"></script>

 
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/imss/ie.css" media="screen"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/extJS/css/stylePortal.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/imss/portal.css" media="screen"> 	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/font-awesome/css/font-awesome.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/jquery/ui-lightness/jquery-ui.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/extJS/css/jquery-ui-bootstrap.css" media="screen">	 --%>



</head>
	
<%
	 UserSession usrSession = request.getSession().getAttribute(ConstantesSession.USR_SESSION)!=null?
										(UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION):null;
	
%>
<body>

 <input type="hidden" id="contextoWeb" value="<%=request.getContextPath()%>"></input>
 <input type="hidden" id="regPatronalMenu" value="<%=usrSession.getRegistroPatronal()%>"></input>
 <input type="hidden" id="cveSolcorrAct" value="<%=request.getSession().getAttribute("cveSolcorr")%>"></input>
 
  <!--  <div id="panelPrincipalExtj" ></div> -->
<div  class="contenedor-portlet cell">
	<div class="portlet" id="idResumenFolioAs">
		<div class="contenedor">
			<div class="cuerpo">
				<div class="titulo">
					<span>Solicitudes de la correcci&oacute;n</span>
					<div class="controles" style="display: inline-block; float: right">
						<a class="widget-tool widget-resize" onclick="toggleCorrecciones()"> <i id="iconoCorreccion"
							class="icono-cerrar"></i>
						</a>
					</div>
				</div>
				<div class="descripcion">
					<p>Cuentas con las siguientes correcciones patronales: </p>
							<table   border="0">
							<tr>
								<td ><p>Registro Patronal:</p></td>
								<td ><p id="regPatronalPrincipal"><label id="regPatronalLog"></label></p></td>
							</tr>
							<tr>
								<td >&nbsp;&nbsp;&nbsp;</td>
								<td ><a href="<%=request.getContextPath()%>/solicitud/correcion.do"><font color="#3B6858">Ir a solicitud de correcci&oacute;n patronal</font></a></td>
							</tr>
						</table>
				</div>
				<br>
				<div class="contenido" id="tablaContenidoMenu" style="height:280px;">								
						<table id="listaSolCorreciones" style="width: 80%;"
						class="table table-striped table-bordered" cellpadding="0"
						cellspacing="0" border="0">
						</table>	
					</div>
				</div>
			</div> 
 		</div>



	 <div class="portlet" id="menuPrincipal">
		<div class="contenedor">
				<div class="cuerpo">
					<div class="titulo">					
						<span>Proceso de correcci&oacute;n </span>
					<div class="controles" style="display: inline-block; float: right;">
						<a class="widget-tool widget-resize" onclick="toggleMenu()"> <i id="iconoMenu"
							class="icono-cerrar"></i>
						</a>
					</div>
					</div>

				
					<div id="accordion" style="padding:15px" >
	     			</div>
     			</div>
		</div>
	</div>	
</div>
  
  
  <!-- Esto es el segudo portlet -->




</body>
</html>