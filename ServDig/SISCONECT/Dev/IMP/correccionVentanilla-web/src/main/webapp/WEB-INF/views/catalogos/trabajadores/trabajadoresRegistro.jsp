<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="dgTrabajadoresRegistro"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;"  title="Detalle del Trabajador">
		<div id="wrapperDialogRegistro" style="background-color: #f2fff2;">
		<form  action="/catalogo/trabajadores/agregar.do" method="post" id="trabajadoresFormRegistro">
		  <input type="hidden" name="cveTrabajador" id="cveTrabajador"/>
						<fieldset>
			<table style="width: 900px" align="center">
			<tr>
			<td align="center" width="900px" valign="top">
				
							
    <table class="tablaverde2" style="width: 100%" >
    <tbody>
    <tr valign="top" class="impar">
		<td align="left" colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td width="25%" align="left" class="etiqueta2">Tipo Trabajador:</td>
		<td width="25%" align="left" class="etiqueta2">
			<input type="radio" name="tipoTrabajador" value="1" checked>Permanente<br>
			<input type="radio" name="tipoTrabajador" value="2">Honorarios </td>
		<td width="25%" align="left" class="etiqueta2"></td>
		<td width="25%" align="left" class="etiqueta2"></td>
	</tr>	
      <tr>
        <td width="25%" align="left" class="etiqueta2"><span class="etiquetaError">*</span>Folio de Corrección:<label for="folioCorreccion"></label></td>
        <td width="25%" align="left"><input type="text" id="folioCorreccion" name="folioCorreccion" size="35" maxlength="18" onblur="this.value=(this.value).toUpperCase();" 
			 onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion','trabajadoresFormRegistro');"/></td>
        <td width="25%" align="left" class="etiqueta2"><label><span class="etiquetaError">*</span>Nombre Asegurado:</label><label for="nombreAsegurado"></label></td>
        <td width="25%"  align="left">
        		<input type="text" id="nombreAsegurado" name="nombreAsegurado" onkeypress="return KeyPressed(this,event,'validchar', null, 'uppercase=yes', null, 'no');" size="35" maxlength="30" >
        </td>
      </tr>
 <!--     </table>-->
 <!--       <table width="100%" border="0" cellspacing="8" cellpadding="0">-->
  
  
  
  <tr>
    <td width="25%" align="left" class="etiqueta2"><label>Apellido Paterno:</label><label for="apPaternoAsegurado"></label></td>
    <td valign="top" width="25%"  align="left">
      <input id="apPaternoAsegurado" name="apPaternoAsegurado" onkeypress="return KeyPressed(this,event,'validchar', null, 'uppercase=yes', null, 'no');" size="35" maxlength="30" />
    </td>
    <td width="25%" align="left" class="etiqueta2"><label>Apellido Materno:</label></td>
    <td width="25%"  align="left">
      <input id="apMaternoAsegurado" name="apMaternoAsegurado" onkeypress="return KeyPressed(this,event,'validchar', null, 'uppercase=yes', null, 'no');" size="35" maxlength="30" />
   </td>
   
  </tr>
  <tr> 
    <td width="25%" align="left" class="etiqueta2"><label>N&uacute;mero de Seguro Social:</label><label for="nuNss"></label></td>
    <td width="25%"  align="left">
      <input type="text" name="nuNss" id="nuNss" size="35" maxlength="11"
      	onkeyup="validaCampo('PermiteSoloNumeros','nuNss','trabajadoresFormRegistro');"
       />
    </td>
    <td width="25%" align="left" class="etiqueta2"><label>RFC:</label></td>
    <td width="25%"  align="left">
      <input type="text" name="txRfc" id="txRfc" size="35" maxlength="13" 
      	onkeyup="validaCampo('noCaracteresEspeciales','txRfc','trabajadoresFormRegistro');"
      />
    </td>
  </tr>

  <tr valign="top" class="impar">
		<td align="left" colspan="4" width="100%">&nbsp;</td>
	</tr>
  </tbody>
  </table>
					

				</td>
				</tr>
			</table>
				</fieldSet>
				</form>
		</div>
	</div>