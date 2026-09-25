<%@ include file="../../general/taglibs.jsp" %>
<!-- This is the main page -->

<!DOCTYPE html>
<html lang="es">
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="../staticResources.jsp"></jsp:include>

<!-- ESTILOS DEL PORTAL OFICIAL IMSS -->
<link rel="stylesheet" href="http://www.encuentra.gob.mx/css/apf.css" type="text/css" />
	
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
				
<!-- JS de la integracion con portal Institucional -->
<script src="http://www.encuentra.gob.mx/api/gobmxWidgetAPI-min.js" type="text/javascript"></script>
<script src="http://www.encuentra.gob.mx/properties/gobmxWidgetAPI-conf.js" type="text/javascript"></script>
<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>	

<script src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=xa-4fdf8cc94940859d" type="text/javascript"></script>	

</head>
<body onload="MM_preloadImages(
			'http://www.imss.gob.mx/sites/all/statics/logoFB.png',
			'http://www.imss.gob.mx/sites/all/statics/fb.png',
			'http://www.imss.gob.mx/sites/all/statics/tw.png',
			'http://www.imss.gob.mx/sites/all/statics/youtube.png')">

	<div class="site_position_center_fixed">
		<div id="cuerpo_principal" class="main_wrap">
			<div id="encabezado">
				<tiles:insertAttribute name="encabezado" />
			</div>
			<div id="subencabezado">
				<tiles:insertAttribute name="subencabezado" />
			</div>
			<div id="espacio"></div>
			<div id="menu" >
				<tiles:insertAttribute name="menu" />
			</div>
			<div id="submenu" >
				<tiles:insertAttribute name="submenu" />
			</div>
			<div id="herramientas" >
				<tiles:insertAttribute name="breadcrumbs" />
			</div>

			<div id="espacio"></div>
			<div id="espacio"></div>
			
			<div id="content-wrapper">
				<div class="row">
					<div class="col-xs-12">
						<div id="cuerpo">
							<tiles:insertAttribute name="contenido" />
						</div>
					</div>
				</div>
			</div>

			<div id="pie">
				<tiles:insertAttribute name="pie" />
			</div>

		</div>
	</div>

	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form id="formCerrarSesion"
			action="${pageContext.servletContext.contextPath}/j_spring_security_logout"
			method="get"></form>
	</div>
</body>
</html>