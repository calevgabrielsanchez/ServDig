<form id="datosClasificadorForm" class="form-horizontal" role="form">
	<table class="table table-striped table-bordered" >
		<thead>
			<tr>
				<th colspan="2" style="text-align:center">
					B&uacute;squeda de actividades
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td> <label class="control-label" for="txtNumEnAnt">Por n&uacute;mero de fracci&oacute;n:</label></td>
				<td><input class="form-control input-sm" id="txtNumEnAnt" name="txtNumEnAnt" type="text" placeholder="000"></td>
			</tr>
			<tr>
				<td>  <label class="control-label" for="txtPalabraEnAnt">Por palabra (s) clave (s):</label></td>
				<td><input class="form-control input-sm" id="txtPalabraEnAnt" name="txtPalabraEnAnt" type="text"></td>
			</tr>
		</tbody>
	</table>
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-9">
            <button id="findClasif" type="button" class="btn btn-primary pull-right">Buscar</button>
        </div>
    </div>
	<div class="row">
		<div class="col-sm-12">
			<p align="justify">Selecciona la fracci&oacute;n que corresponda a tu actividad o realiza una nueva b&uacute;squeda.</p>
		</div>
	</div>
    <div class="row">
    	<div class="col-sm-12">
        <table id="resultados" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th></th>
                <th>Fracci&oacute;n</th>
                <th>Actividad</th>
                <th>Descripci&oacute;n</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
		</div>
    </div>
</form>