<div id=dgAgregaConceptosPAI title=" Anexo Conceptos Omitidos " >
<form action="" method="post" id="formCorre" >
<table style="width: 600px;">
	 	 <tr>
	 	 
    			<td>Concepto</td>
        		<td colspan="1" rowspan="1">Pagado</td>
        		<td colspan="1" rowspan="1" id="NoAclarado">No Aclarado</td>
        		<td></td>
         </tr>
       <tr>
                <td>
                <select id="cbxConceptoOmitidoPAI" name="cbxConceptoOmitidoPAI">
						   <option value="">--Por favor seleccione--</option>
					 </select><label for="cbxConceptoOmitidoPAI" >  </label>
				</td>
                <td><input type="checkbox" id="pagadoPAI" name="pagadoPAI" checked="checked" /></td>
                <td><input type="checkbox" id="noAclarado" name="noAclarado" checked="checked" /></td>
                <td colspan="1" rowspan="1"><input type="button" value="Agregar"  onclick="agregarConceptoOmitidoPAI('C');"/></td>
      </tr>
</table> 
<table id="dtAgregaConceptosPAI"  style="width: 600px" >
	<thead>
                <tr>
                   				 <th  colspan="1" rowspan="1"></th>
                   				<th colspan="1" rowspan="1">Concepto</th>
                   				<th colspan="1" rowspan="1">Pagado</th>
                   				<th colspan="1" rowspan="1">No Aclarado</th>
                   </tr>
                     
              </thead>
	</table>
	

	 </form>
</div>



