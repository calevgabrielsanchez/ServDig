<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<meta http-equiv="expires" content="0">
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
</head>
<html>

<script type="text/javascript" src="/delta/resources/js/jquery/jquery-ui.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/timerSession.js"></script>

<script type="text/javascript">
	function MM_swapImgRestore() { //v3.0
		var i, x, a = document.MM_sr;
		for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
			x.src = x.oSrc;
	}
	
	function MM_preloadImages() { //v3.0
		var d = document;
		if (d.images) {
			if (!d.MM_p)
				d.MM_p = new Array();
			var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
			for (i = 0; i < a.length; i++)
				if (a[i].indexOf("#") != 0) {
					d.MM_p[j] = new Image;
					d.MM_p[j++].src = a[i];
				}
		}
	}

	function MM_findObj(n, d) { //v4.01
		var p, i, x;
		if (!d)
			d = document;
		if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
			d = parent.frames[n.substring(p + 1)].document;
			n = n.substring(0, p);
		}
		if (!(x = d[n]) && d.all)
			x = d.all[n];
		for (i = 0; !x && i < d.forms.length; i++)
			x = d.forms[i][n];
		for (i = 0; !x && d.layers && i < d.layers.length; i++)
			x = MM_findObj(n, d.layers[i].document);
		if (!x && d.getElementById)
			x = d.getElementById(n);
		return x;
	}

	function MM_swapImage() { //v3.0
		var i, j = 0, x, a = MM_swapImage.arguments;
		document.MM_sr = new Array;
		for (i = 0; i < (a.length - 2); i += 3)
			if ((x = MM_findObj(a[i])) != null) {
				document.MM_sr[j++] = x;
				if (!x.oSrc)
					x.oSrc = x.src;
				x.src = a[i + 2];
			}
	}
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>"/>

<body onload="MM_preloadImages('http://www.imss.gob.mx/Pages/imagenes/redes/faceover.png',
			'http://www.imss.gob.mx/Pages/imagenes/redes/twover.png',
			'http://www.imss.gob.mx/Pages/imagenes/redes/fbpp_over.png',
			'http://www.imss.gob.mx/Pages/imagenes/redes/fb_over.png',
			'http://www.imss.gob.mx/Pages/imagenes/redes/tw_over.png',
			'http://www.imss.gob.mx/Pages/imagenes/redes/youtube_over.png',
			'http://www.imss.gob.mx/Pages/imagenes/banners/declaranet.jpg',
			'http://www.imss.gob.mx/Pages/imagenes/banners/pot.jpg',
			'http://www.imss.gob.mx/Pages/imagenes/banners/accionvol.jpg',
			'http://www.imss.gob.mx/Pages/imagenes/banners/pnd.jpg')">
			
	<div id="encabezado" style="width:1010px;">
	    <!--inicia Encabezado-->
		<div class="header_top " style="width:1010px;">
			<div class="top_version cell">
				<span> Versión</span>:
				<span> 1.2.0</span>
			</div>
			<div class="top_nav cell">
				<ul>
					<li>
						<a title="Portal IMSS" href="http://www.imss.gob.mx">Visita el portal oficial del Instituto Mexicano del Seguro Social
						</a>
					</li>
				</ul>
			</div>
		</div>
		
		<!--inicio logo-->
		<img width="471" height="83" title="Portal IMSS" src="<%=request.getContextPath()%>/resources/imagenes/bannerImss.gif">
		<!--Termino logo-->
		
		<div class="top_search">
			<!--inicio busqueda-->
			<form action="http://www.imss.gob.mx/buscador/resultado.html" id="cse-search-box">
				<input type="hidden" value="002360038649913767611:zxhajmgbjye" name="cx">
				<input type="hidden" value="FORID:11" name="cof">
				<input type="hidden" value="ISO-8859-1" name="ie">
				<!-- <div>
					<input type="text" size="15" name="q" id="s">
					<input type="submit" value="Buscar" id="searchsubmit">
				</div> -->
			</form>
		</div>
	<!-- Termina header -->
	</div>
	
	<div class="separadorseccion texto-centrado" style="width:1010px;">
		<span> NOTIFICACIONES POR ESTRADOS ELECTR&Oacute;NICOS </span>
	</div>
	<div class="breadcrumb" style="width:1010px;">
		<div class="row-fluid">
			<div class="span8">
				<table style="width: 100%;">
					<tbody>
     	 				<tr>		
        					<td width="450px">
        						<label id="usuario">
        							<b>Usuario: </b>
        						</label>
        					</td>
       						<td>
       							<label id="fecha">
       								<b>Fecha: </b>
       							</label>
       						</td>
						</tr>
        				<tr>      
      						<td colspan="2"><label id="areaNorma"><b>&Aacute;rea: </b></label> </td>      
						</tr>
						<tr>      
      						<td>
      							<label id="delegacion">
      								<b>Delegación:</b>
      							</label>
   							</td>
	      					<td>
	      						<label id="subdelegacion">
	      							<b>Subdelegación: </b>
	      						</label>
	      					</td>
						</tr>
     					<tr>      
      						<td colspan="2">
      							<label id="departamento"><b>Departamento: </b></label>
    						</td>
      						<td align="right">
      							<a onclick="logOut();" href="#">
									<img title="Salir" src="<%=request.getContextPath()%>/resources/imagenes/systemlogout.png">&nbsp;&nbsp;
								</a>
								<a onclick="salirAplicacion()" href="#">
									<img title="Inicio" src="<%=request.getContextPath()%>/resources/imagenes/gohome.png">&nbsp;&nbsp;
								</a>
								<a onclick="descargaManualUsuario()" href="#">
									<img title="Manual de Usuario" src="<%=request.getContextPath()%>/resources/imagenes/pdf_icon.gif" height="22" width="22">
								</a>
      						</td>
      					</tr>
					</tbody>
  				</table>
		
<!-- 			<ul style="float: left !important;">
				<li><span style="padding: 5px !important;" class="etiqueta">
						<label id="fecha">Fecha</label>
				</span></li>
				<li><span style="padding: 5px !important;" class="etiqueta">
						<label id="usuario">Usuario</label>
				</span></li> 
			</ul>-->
			</div>
		</div>
		<div class="row-fluid">
		<!-- <div class="span12">
			<table>
			<tr>
			<td><label id="subdelegacion">Subdelegacion</label></td>
			<td><label id="delegacion">Delegacion</label> </td>
			</tr>
			</table>
				<ul style="float: left !important;">
					<li>	<span style="padding: 5px !important;" class="etiqueta">
							<label id="subdelegacion">Subdelegacion</label>
						</span>
					</li>
					<li><span style="padding: 5px !important;" class="etiqueta">
							<label id="delegacion">Delegacion</label> 
					</span></li>
				</ul>
			</div> -->
		</div>
	</div>
<div id="divMensajeSession"></div>
</body>
</html>