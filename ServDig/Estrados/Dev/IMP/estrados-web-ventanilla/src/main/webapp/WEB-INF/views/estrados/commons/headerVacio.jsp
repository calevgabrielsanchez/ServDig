<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<meta http-equiv="expires" content="0">
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
</head>
<html>

<script type="text/javascript" src="/delta/resources/js/jquery/jquery-ui.js"></script>

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
			
<!-- 	<div class="contenido" align="center"> -->
<!-- 	<div class="well" style="width: 1000px; background-color: white">  -->
     	
     	<!--Se comento la forma que valida el usuario  -->
     	<%-- <form action="<%=request.getContextPath()%>/registro/validaUsuarioInternet.do" method="post" id="LoginForm"> --%>
	<table>
		<tr>
			<td colspan="2">
				<table>
					<td>
	    				<div id="logo">
	    					<a target="_blank" title="Gobierno" href="http://www.imss.gob.mx/">
	    					<img src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/header.jpg"></a>
	    				</div>
    				</td>
    				<td>
    					<div id="redes">
							&nbsp;&nbsp;&nbsp;<a
							href="http://www.facebook.com/habitossaludablesimss"
							onmouseout="MM_swapImgRestore()"
							onmouseover="MM_swapImage('Image14','','http://www.imss.gob.mx/Pages/imagenes/redes/fb_over.png',1)"><img
							src="http://www.imss.gob.mx/Pages/imagenes/redes/fb.png"
							alt="Siguenos en Facebook" name="Image14" id="Image14"
							border="0" height="28" width="27"></a>&nbsp;&nbsp;&nbsp;<a
							href="http://twitter.com/tu_imss"
							onmouseout="MM_swapImgRestore()"
							onmouseover="MM_swapImage('Image15','','http://www.imss.gob.mx/Pages/imagenes/redes/tw_over.png',1)"><img
							src="http://www.imss.gob.mx/Pages/imagenes/redes/tw.png"
							alt="Siguenos en Twitter" name="Image15" id="Image15" border="0"
							height="28" width="27"></a>&nbsp;&nbsp;&nbsp;<a
							href="http://www.youtube.com/user/segurosocialimss"
							onmouseout="MM_swapImgRestore()"
							onmouseover="MM_swapImage('Image16','','http://www.imss.gob.mx/Pages/imagenes/redes/youtube_over.png',1)"><img
							src="http://www.imss.gob.mx/Pages/imagenes/redes/youtube.png"
							alt="Siguenos en Youtube" name="Image16" id="Image16" border="0"
							height="28" width="27"></a>&nbsp;&nbsp;&nbsp;<a
							href="https://www.facebook.com/JoseAntonioGonzalezAnaya"
							onmouseout="MM_swapImgRestore()"
							onmouseover="MM_swapImage('Image13','','http://www.imss.gob.mx/Pages/imagenes/redes/fbpp_over.png',1)"><img
							src="http://www.imss.gob.mx/Pages/imagenes/redes/fbpp.png"
							alt="Sigue al Director General en Facebook" name="Image13"
							id="Image13" border="0" height="28" width="105"></a>
						</div>
    				</td>
   				</table>
			</td>
  		</tr>
		<tr>
	  		<td colspan="2">
			  	<div id="menu">
					<div id="menu_principal" class="clear">
						<div class="menuboton">
							<a href="http://www.imss.gob.mx/">Inicio</a>
						</div>
						<div class="menuboton">
							<a href="http://www.imss.gob.mx/instituto/pages/index.aspx">Conoce
								al IMSS</a>
						</div>
						<div class="menuboton">
							<a href="http://portaltransparencia.gob.mx/pot/estructura/showOrganigrama.do?method=showOrganigrama&amp;_idDependencia=641">Organigrama</a>
						</div>
						<div class="menuboton">
							<a href="http://www.imss.gob.mx/directorio/pages/funcionarios.aspx">Directorio</a>
						</div>
						<div class="menuboton">
							<a href="http://www.imss.gob.mx/Pages/contacto.aspx">Contacto</a>
						</div>
					</div>
				</div>
  			</td>
		</tr>
<%-- 		<tr>
			<td colspan="2">
	  			<img src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/header_escritorio (2).png" >
			</td>
	  	</tr> --%>
  	</table>
	<br>
	<br>
	<h1 id="page-title" class="title">
          Notificaciones por estrados        </h1>
	<div style="width: 65%; float: left;"><p>Derivado de la modificación al artículo 139 del Código Fiscal de la Federación, las notificaciones por estrados deberán publicarse, además, en la página de internet:</p><p>"Las notificaciones por estrados se harán fijando durante quince días el documento que se pretenda notificar en un sitio abierto al público de la oficinas de la autoridad que efectúe la notificación y publicando además el documento citado, durante el mismo plazo, en la página electrónica que al efecto establezcan las autoridades fiscales; dicho plazo se contará a partir del día siguiente a aquél en que el documento fue fijado o publicado según corresponda; la autoridad dejará constancia de ello en el expediente respectivo. En estos casos, se tendrá como fecha de notificación la del décimo sexto día contando a partir del día siguiente a aquél en el que se hubiera fijado o publicado el documento".</p></div>
	<div style="width: 30%; text-align: center; margin-left: 5%; float: left;"><img src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/estrados.jpg"></div>
<!-- </div> -->
</body>
</html>