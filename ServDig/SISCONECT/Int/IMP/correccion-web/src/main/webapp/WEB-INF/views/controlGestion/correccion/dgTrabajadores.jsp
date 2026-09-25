<div id=dgAgregaTrabajadoresA  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formCorre" >
	<table style="width: 600px;">
	 	 <tr>
    			<td></td>
        		<td colspan="1" rowspan="1">Revisados</td>
        		<td colspan="1" rowspan="1">Omisos</td>
            	<td colspan="1" rowspan="1">Subdeclarados</td>
            	<td colspan="1" rowspan="1"></td>
         </tr>
       <tr>
                   				<td></td>
                   				<td colspan="1" rowspan="1"><input type="text" size="20" maxlength="11" id="revisados" onkeyup="validaCampo('PermiteSoloNumeros','revisados','formCorre')"/></td>
                   				<td colspan="1" rowspan="1"><input type="text" size="20" maxlength="11" id="omisos" onkeyup="validaCampo('PermiteSoloNumeros','omisos','formCorre')"/></td>
                   				<td colspan="1" rowspan="1"><input type="text" size="20" maxlength="11" id="subdeclarados" onkeyup="validaCampo('PermiteSoloNumeros','subdeclarados','formCorre')"/></td>
                   				<td colspan="1" rowspan="1"><input type="button" value="Agregar"  onclick="agregarTrabajadores('A');"/></td>
                   </tr>
</table> 
 
	<table id="dtAgregaTrabajadoresA"  style="width: 600px" >
	
			 <thead>
                    <tr>
                   				<th colspan="1" rowspan="2"></th>
                   				<th colspan="1" rowspan="2">RP</th>
                                <th colspan="4" rowspan="1">Trabajadores</th>
                   </tr>
                   <tr>
                                <th colspan="1" rowspan="1">Revisados</th>
                                <th colspan="1" rowspan="1">Omisos</th>
                                <th colspan="1" rowspan="1">Subdeclarados</th>
                                <th colspan="1" rowspan="1">Regularizados</th>
                   </tr>            
              </thead>
	</table>
	

	 </form>
</div>



