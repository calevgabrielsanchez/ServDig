<%@ include file="../general/taglibs.jsp" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!-- This is the main page -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<!-- Javascripts -->
<script>
	var context_path = '<%= request.getContextPath()%>';
	var server_scheme = '<%= request.getScheme()%>';
	var server_port = '<%= request.getServerPort()%>';
	var server_name = '<%= request.getServerName()%>';
	
	$(document).ready(function(){
		
		
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
		
		if(server_port != '80') {
			server_name = server_scheme + '://' + server_name + ':' + server_port + '/';
		} else {
			server_name = server_scheme + '://' + server_name + '/';
		}
		
		
		$('iframe').attr("frameBorder", "0");
		
		
	});
	
</script>	


<style>
    .u1st .accessibilityClass {
        position: fixed !important;
        top: 55px;
        left: -50px;
        transform: rotate(90deg);
        _position: absolute !important;
       _top: expression(document.compatMode && document.compatMode = 'CSS1Compat' ? documentElement.scrollTop : document.body.scrollTop);
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
		<div class="container">
			<tiles:insertAttribute name="contenido" />
		</div>
	</main>
	
	<!-- GobMx -->
	<script src="https://framework-gb.cdn.gob.mx/gobmx.js"></script>
</body>
</html>
