<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">

	<jsp:include page="main/head.jsp" />

<body>
<div class="site_position_center">
<div id="cuerpo_principal" class="main_wrap"> 
  
    <jsp:include page="main/menu_up_principal.jsp" />

  <div id="cuerpo">

  <div align="left">
  	    <span class="greenBold">Opciones de búsqueda:<br>&nbsp;</span>
  </div>

	<div id="tabs">
			<ul>
				<li><a href="#tabs-1">Elementos del Cat&aacute;logo </a></li>
				<li><a href="#tabs-2">B&uacute;squeda por Cat&aacute;logo Anterior</a></li>
				<li><a href="#tabs-3">B&uacute;squeda en Cat&aacute;logo Actual</a></li>
				<li><a href="#tabs-4">B&uacute;squeda en Cat&aacute;logo Anterior</a></li>
				<li><a href="#tabs-5">Descarga de Cat&aacute;logo</a></li>
			</ul>
			<!-- Pestaña de Elementos del Catálogo -->
			<div id="tabs-1">
				<jsp:include page="catalogos.jsp" />
			</div>
			<!-- Pestaña de Búsqueda Catálogo Anterior -->
			<div id="tabs-2">
				<jsp:include page="anterior.jsp"/>
			</div>
			<!-- Pestaña de Búsqueda Catálogo Actual -->
			<div id="tabs-3">
				<jsp:include page="actual.jsp" />
			</div>
			<!-- Pestaña de Descarga de Catálogo -->
			<div id="tabs-4">
				<jsp:include page="enAnterior.jsp" />
			</div>
			<!-- Pestaña de Descarga de Catálogo -->
			<div id="tabs-5">
				<jsp:include page="descarga.jsp" />
			</div>
		</div>
    
  </div>

	<jsp:include page="main/menu_down.jsp"/>

</div>

<div id="dgFraccion" title=" Confirmar selecci&oacute;n" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
<div id="wrapperDialog" style="background-color: #f2fff2; ">


<div style="float: right;">
	 <a id="imprimir" href="#">Imprimir</a> 
</div>
</br>

	<table class="tablaverde2" align="center" style="width: 500px;">
		 <thead>
  	<tr >
  		<td colspan="2">Datos de la Fracci&oacute;n  </td>
  		
  	</tr>
  </thead>
  
		<tr class="impar">
			<td style="font-weight: bold !important; width: 25%; "> Clave : </td>
			<td class="claveFraccion"> <span id="dgCveFraccion"> </span>  </td>
		</tr>
		
		<tr class="par">
			<td style="font-weight: bold !important; width: 25%;"> Actividad : </td>
			<td  class="txtJustify"> <span id="dgActividad" > </span> </td>
		</tr>
		<tr class="impar">
			<td style="font-weight: bold !important;width: 25%; "> Descripcion : </td>
			<td  class="txtJustify"> <span id="dgDescripcion" > </span> </td>
		</tr>
		<tr class="par" style="display: none;">
			<td style="font-weight: bold !important;width: 25%; "> Prima Media : </td>
			<td> <span id="dgPrima"> </span> </td>
		</tr>
		<tr class="impar">
			<td style="font-weight: bold !important; width: 25%;"> Clase : </td>
			<td> <span id="dgClase"> </span> </td>
		</tr>
	</table>
</div>
</div>

<div id="dgAyuda" title=" Ayuda" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
<div id="wrapperDialog" style="background-color: #f2fff2; ">
	<jsp:include page="ayuda/ayuda.jsp" />
</div>
</div>

<div id="dgConfirma" title="¿Esta Ud. seguro de los datos seleccionados?">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span> La fracción seleccionada sera asignada a su registro patronal. ¿Esta Ud. seguro?</p>
</div>

<div id="dgFaltaDatos" title="Datos incompletos">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span> Favor de capturar  los datos requeridos:</p>
	
	<p><ul id="listaCamposInvalidos"></ul></p>
</div>

</div>
</body>
</html>