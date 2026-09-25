<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">

	<jsp:include page="main/head.jsp" />
	<script>
		showVigente= false;
	</script>	
	
	
	
	
		
<body>

<div class="site_position_center">
<div id="cuerpo_principal" class="main_wrap"> 


  
    <jsp:include page="main/menu_up_principal.jsp" />

  <div id="cuerpo">

  <div align="left">
  	    <span class="tituloB">Opciones de búsqueda:<br>&nbsp;</span>
  </div>

	<div id="tabs">
			<ul>
				<li><a href="#tabs-1" ><span class = "descripcion">Elementos del Cat&aacute;logo</span></a></li>
				<li><a href="#tabs-2"><span class = "descripcion">B&uacute;squeda en Cat&aacute;logo</span></a></li>				
			</ul>
			<!-- Pestaña de Elementos del Catálogo -->
			<div id="tabs-1">
				<jsp:include page="catalogosVigentes.jsp" />
			</div>	
			<div id="tabs-2">
				<jsp:include page="enCatalogoVigente.jsp" />
			</div>			
		</div>
    
  </div>

	<jsp:include page="main/menu_down.jsp"/>

</div>

<div id="dgFraccion" title=" Confirmar selecci&oacute;n" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">

<div style="float: right;">
	 <a id="imprimir" href="#">Imprimir</a> 
</div>

<div id="wrapperDialog" style="background-color: white; ">

</br>

	<div class="titulo_sistema">
		<ul>
			<li><a style="color: white !important; line-height: 3em;">Datos de la Fracci&oacute;n </a></li>
		</ul>
	</div>

	<table class="tablagobmx" align="center" style="width: 574px;">
  
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



<!-- Se quita el boton de ayuda por solicitud del usuario 
<div id="dgAyuda" title=" Ayuda" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
<div id="wrapperDialog" style="background-color: #f2fff2; ">
	<jsp:include page="ayuda/ayuda.jsp" />
</div>
</div>
-->

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