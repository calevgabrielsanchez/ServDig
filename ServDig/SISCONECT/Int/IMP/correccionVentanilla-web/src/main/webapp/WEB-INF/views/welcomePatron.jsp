<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@page import="mx.gob.imss.ctirss.correccion.session.ConstantesSession"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>	
<html lang="sp">
<head>


<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/extJS/menuDesplegableVersion.js"></script>




<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/bootstrap/bootstrap.min.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/imss/ie.css" media="screen"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/extJS/css/stylePortal.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/imss/portal.css" media="screen"> 	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/font-awesome/css/font-awesome.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/estilos/jquery/ui-lightness/jquery-ui.css" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/extJS/css/jquery-ui-bootstrap.css" media="screen">	



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
					<div class="controles" style="display: inline-block; float: left;">
						<a class="widget-tool widget-resize" onclick="toggleCorrecciones()"> <i id="iconoCorreccion"
							class="icono-cerrar"></i>
						</a>
					</div>
					<span>Solicitudes de la Corrección</span>
				</div>
				<div class="titulo">
					<p>Cuentas con las siguientes correcciones patronales: </p>
							<table   border="0">
							<tr>
								<td ><p>Registro Patronal:</p></td>
								<td ><p id="regPatronalPrincipal"><label id="regPatronalLog"></label></p></td>
							</tr>
							<tr>
								<td >&nbsp;&nbsp;&nbsp;</td>
								<td ><a href="<%=request.getContextPath()%>/solicitud/correcion.do"><font color="#3B6858">Ir a Solicitud de Corrección Patronal</font></a></td>
							</tr>
						</table>
				</div>
				<br>
				<div class="contenido">								
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
					<div class="controles" style="display: inline-block; float: left;">
						<a class="widget-tool widget-resize" onclick="toggleMenu()"> <i id="iconoMenu"
							class="icono-cerrar"></i>
						</a>
					</div>
						<span>Proceso de Corrección </span>
					</div>
					<div class="descripcion">
				    </div>
				
					<div id="accordion"   style="width: 90%;" class="portlet" >
	     			</div>
     			</div>
		</div>
	</div>	
</div>
  
  
  <!-- Esto es el segudo portlet -->




</body>
</html>