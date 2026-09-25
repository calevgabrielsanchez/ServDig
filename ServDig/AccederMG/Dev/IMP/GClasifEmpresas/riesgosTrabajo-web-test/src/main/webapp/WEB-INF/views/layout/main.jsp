<%@ include file="../general/taglibs.jsp"%>


<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp">
	<jsp:param value="true" name="isGobMxIncluded"/>
</jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
	history.go(1);
	$(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});
	});
	
	/**
	 * Evita que el boton back regrese a la pagina anterior
	 * 
	 */
	function blockBackButton(){
		window.location.hash="ciudadano";
		window.location.hash="ciudadano";
		window.location.hash="ciudadano";
		window.onhashchange=function(){window.location.hash="ciudadano";};
	}
</script>
<script type="text/javascript" src="https://framework-gb.cdn.gob.mx/gobmx.js"></script>

<style>
    .u1st .accessibilityClass {
        position: fixed !important;
        top: 55px;
        left: -50px;
        transform: rotate(90deg);
        _position: absolute !important;
        z-index: 1000000;
        cursor: pointer;
        text-align: center;
        padding: 3px 0;
        border-radius: 4px;
        font: bold 15px Arial !important;
        background:  #F39200 !important;
        color: #3B3835 !important;
        padding: 7px 20px 7px 20px;

    }
</style>
<script type="text/javascript" id="User1st_Settings" >

	window['User1st'] = {
		Web : {
			settings : {
				accessibilityBtn : {
					useMyClass : 'accessibilityClass'
				}
			}
		}
	};
</script>


<script type="text/javascript" id="User1st_Loader" src="https://fe.user1st.info/Loader/head"></script>

</head>

<body>	
	<!-- Contenido -->
	<main class="page">
		<div class="container top-buffer">
			<tiles:insertAttribute name="navegacion" />
			<tiles:insertAttribute name="contenido" />
			<div id="dialogMensajesCtrl"></div>
		</div>
	</main>
			
	<div style="display: none;">
		<form id="formCerrarSesion"
			action="${pageContext.servletContext.contextPath}/j_spring_security_logout"
			method="get"></form>
	</div>
	
	<div id="dialogEndOfSession" style="display: none"
		title="Sesi&oacute;n terminada por inactividad">
		<span>Su sesi&oacute;n se ha desactivado debido a inactividad.</span>
	</div>
	
	<div>
		<!-- GobMx -->
		<jsp:include page="footer.jsp"/>
	</div>
	
</body>
</html>