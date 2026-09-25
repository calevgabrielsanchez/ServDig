<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<div id="dgFoliosPromocion"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formCorrePromo" >
<table>
	<tr>
		<td>
			Clasificación
		</td>
		
		<td>
			Fuente
		</td>
		
		<td>
			Folio
		</td>
		<td>
			Registro Patronal
		</td>
		<td>
			Nombre
		</td>
		<td>
		 	AFIL15
		</td>
		
	</tr>
	<tr>
	<td>
	<combo:creaCombo  
  								entidad="mx.gob.imss.ctirss.correccion.model.CgcCatTipo" 
  								idHtml="findClasificacion"
  								idHtmlContenedor="formCorrePromo" param="idTipo" paramValue="5,6,7,8"
  								 />
	<td>
	<select id="findFuente" autofocus="autofocus"  name="findFuente"  style="width:250px; font-family: Verdana;	font-size: 9px;	color: #000000;	letter-spacing : 0px;	line-height : 12px;	font-weight: bold;" >
    						<option value="">--Por favor seleccione--</option>
    					</select>
		</td>
		<td> 
			<input type="text" id="findFolio" name="findFolio" size="20"> 
		</td>
		<td> 
			<input type="text" id="findRP" name="findRP" size="20"> 
		</td>
		<td> 
			<input type="text" id="findNombre" name="findNombre" size="20">
		</td>
		<td> 
			<input type="text" id="findAfil" name="findAfil" size="20"> 
		</td>
		<td>
			<input type="button" onclick="enviaAPaginarFolios();" value="Buscar">
		</td>
	</tr>


</table>
<table id="dtFoliosPromocion"  style="width: 980px" >
	
			 <thead>
                   <tr>
                   				<th colspan="1" rowspan="1"></th>
                   				<th colspan="1" rowspan="1">Clasificación</th>
                   				<th colspan="1" rowspan="1">Fuente</th>
                                <th colspan="1" rowspan="1">Folio</th>
                                <th colspan="1" rowspan="1">RP</th>
                                <th colspan="1" rowspan="1">Nombre</th>
                                <th colspan="1" rowspan="1">AFIL15</th>
                                <th colspan="1" rowspan="1">SP</th>
                                <th colspan="1" rowspan="1">OI</th>
                                <th colspan="1" rowspan="1">PR</th>
                   </tr>
                     
              </thead>
	</table>
	<table align="center">
		<tr >
			
			<td valign="top">
				<table border ="1" width="100px">
					<tr>
						<td colspan="1"><input type="button" value="Aceptar" size="100px" id="btnEliminar" width="40px" onclick="llenaDatosPromocion();"></td>
						<td colspan="1"><input type="button" value="Cancelar" size="100px" id="btnGuardar" width="40px" onclick="cancelar();" ></td>
					</tr>
					
				</table>
				
			</td>
		<tr>
	
	</table>

	 </form>
</div>



