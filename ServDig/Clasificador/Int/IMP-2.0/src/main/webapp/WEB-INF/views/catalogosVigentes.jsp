





<br>
<div align="center" id="msgZone">
	<div id="wrapperMsgNotificacion" class="alert-info" style="width: 700px !important;" >
		<div class="alert alert-info">
		<p>
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<strong >Nota:</strong>
<!-- 		Para iniciar deber&aacute; proporcionar los siguientes datos: -->
		Para iniciar deber&aacute; proporcionar los siguientes datos:
		</p>
		</div>
	</div>
	
	<br/>
	
</div>

<div id="wrapperFilters">


	<div class="titulo_sistema">
		<ul>
			<li><a style="color: white !important; line-height: 3em;">B&uacute;squeda
					de Fracción por Elementos del Catálogo</a></li>
		</ul>
	</div>



	<table  style="width: 900px;"  >
 <tr valign="middle">
  <td align="center" width="900px">
  
  <table style="width: 900px;" class="tablagobmx">
  
 <tbody>
 
   <tr valign="top">
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
   </tr>
   
   <tr valign="top" class="impar">

    <td align="right" width="200px" >
     Divisi&oacute;n Econ&oacute;mica :
    </td>
   
    <td align="center">
     <select style="  width:500px;" name="id" id="divisionSelect">
     	<option value="-1" >--Por favor seleccione--</option>
     </select>
    </td>
   
   </tr>
   <tr valign="top">
    <td align="left">&nbsp;</td>
    <td align="left">&nbsp;</td>
   </tr>
   
   <tr valign="top" class="impar">
    <td align="right" width="200px">
     Grupo:
    </td>
    <td align="center">
     <select style="width:500px;" name="id" id="grupoSelect">
     	<option value="-1">--Por favor seleccione--</option>
     </select>
    </td>
   </tr>   
   <tr valign="top">
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
  <!-- Se elimina el botn de ayuda 
  <td align="center" width="100px">
  	<a href="#"><img src="resources/images/btn_ayuda.png" border="0" onclick="muestraAyuda();" /></a>
  </td>
  -->
  <td align="center" width="100px">
  	<a href="#"><img src="resources/images/btn_buscar.png" border="0" onclick="fraccionCtrl.buscarFracciones();" /></a>
  </td>
  <td align="center" width="100px">
  	<a href="#"><img src="resources/images/btn_nuevaBusqueda.png" border="0" onclick="limpiarFormularioCatalogo();" /></a>
  </td>
 </tr>
</table>
<br>
<div id="resultados" >
		
		
		<jsp:include page="resultadosBusqueda.jsp"/>
		

</div>

