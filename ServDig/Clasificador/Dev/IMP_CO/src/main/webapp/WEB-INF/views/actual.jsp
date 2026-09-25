<br>


<!-- Mensaje de Instrucciones -->

<div align="center" id="msgZone">

	<div id="wrapperIntsAnterior" class="ui-widget" style="width: 700px !important;" >
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .5em;">
		<p>
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Instrucciones:</strong>
		Para iniciar capture la fracci&oacute;n o la palabra clave (sin acentos) que desea buscar:
		</p>
		</div>
	</div>
</div>


<br>

<!-- Seccion de filtros de catalogo actual -->


<div id="wrapperFiltersActual" style="background-color: #f2fff2; ">


<table  style="width: 900px;"  >
 <tr valign="middle">
  <td align="center" width="900px">
  
  <table class="tablaverde2" style="width: 900px;" >
  
  <thead>
  	<tr >
  		<td colspan="2">B&uacute;squeda en Cat&aacute;logo Actual </td>
  	</tr>
  </thead>
  
  
 <tbody>
 
   <tr valign="top" class="impar">
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
   </tr>
   
   <tr valign="top" class="par">

    <td align="right" width="200px">
    	Por N&uacute;mero de Fracci&oacute;n :
    </td>
   
    <td align="center">
    
     <input type="text" id="txtNumActual" name="txtNumActual"
     	maxlength="7" size="80"  class="numerico" />
    </td>
   
   </tr>
   <tr valign="top" class="impar">
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
   </tr>
   
   <tr valign="top" class="par">
    <td align="right" width="200px">
    	Por Palabra(s) Clave :
    
     
    </td>
    <td align="center">
     
     <!-- Input text de palabra clave anterior. -->
     <input type="text" id="txtPalabraActual" name="txtPalabraActual"
     	maxlength="100" size="80"/>
     
     
     
    </td>
   </tr>   
   <tr valign="top" class="impar">
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
   </tr>
   </tbody>
  </table>
  </td>
 </tr>
</table>

</div>




<br>
<!-- Tabla del menú -->

<table width="810px" border="0px" align="center">
 <tr valign="middle">
  <td align="center" width="100px">
<a href="#"><img src="resources/images/btn_ayuda.png" border="0" onclick="muestraAyuda();" /></a>
  </td>
  <td align="center" width="100px">
  	<a href="#"><img src="resources/images/btn_buscar.png" border="0" onclick="fraccionCtrl.buscarFraccionesByCatalogoActual();" /></a>
  </td>
  <td align="center" width="100px">
  	<a href="#"><img src="resources/images/btn_nuevaBusqueda.png" border="0" onclick="limpiarFormularioActual();" /></a>
  </td>
 </tr>
</table>
<br>



<div id="resultadosActual"  >
		
	
<div id="wrapperMsgResultsActual" align="center" class="hiddenElement">
	<div id="wrapperMsgNotificacionActual" class="ui-widget" style="width: 700px !important; font-size: 1.5 em !important;" >
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .5em;">
		<p>
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Mensaje:</strong>
		<span> Se encontraron </span> <span id="msgResultados"> </span>
		 <span> Fracciones ( Favor de seleccionar la que le corresponda a su actividad o realizar una nueva b&uacute;squeda). </span> 			<span id="msgTotal" style="display:none;"></span>   
		
		</p>
			<p>
						<strong> Importante:</strong>
						 <span>La informaci&oacute;n aqu&iacute; presentada es de car&aacute;cter enunciativa para efectos de apoyo,  por lo cual no es limitativa ni constituye propuesta por parte del Instituto, para la autoclasificaci&oacute;n que  deber&aacute;  realizar conforme al Cat&aacute;logo de Actividades  establecido en el art&iacute;culo 196 del Reglamento de la Ley del Seguro Social en Materia de Afiliaci&oacute;n, Clasificaci&oacute;n de Empresas, Recaudaci&oacute;n y Fiscalizaci&oacute;n.</span>
		</p>
		</div>
	</div>
</div>
<div id="wrapperMsgResultsActualToo" align="center" class="hiddenElement">
	<div id="wrapperMsgNotificacionAnt" class="ui-widget" style="width: 700px !important;" >
		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .5em;">
		<p>
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong>Mensaje:</strong>
			<span> Su b&uacute;squeda encontr&oacute; </span> <span id="msgResultados"> </span>  
			 <span> Fracciones ( Favor de proporcionar criterios m&aacute;s espec&iacute;ficos para la b&uacute;squeda).</span> 			
			 <span id="msgTotal" style="display:none;"></span>
		</p>
		<p>
						<strong> Recomendaci&oacute;n:</strong>
						 <span>Evite utilizar s&oacute;lo art&iacute;culos, pronombres o conjunciones. </span>
		</p>
		
		
		</div>
	</div>
</div>
<br>

<div id="wrapperResultsActual"
	style="background-color: white !important;">

	<table id="dtResultadosActual"  style="width: 900px">
		<thead>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
</div>

