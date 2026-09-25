<%@ include file="../../../general/taglibs.jsp"%>

<script>
$(document).ready(function(){
	$('#tbRiesgosTrabajo').dataTable({
		'bFilter' : false,
		'bDestroy' : true,
		'bLengthChange' : false,
		'bAutoWidth' : false,
		'bPaginate' : true,
		'bInfo' : true,
		'bSort' : false,
		'sPaginationType' : 'bootstrap'
	});
})
</script>
<div class="table-responsive">
    <table id="tbRiesgosTrabajo"
           class="table table-striped table-bordered table-condensed table-responsive"
           id="testTable" style="width: 100%" cellpandding="0" cellpancing="0"
           border="0">
        <thead>
            <tr>
                <th rowspan="2" style="text-align: center;"><strong>Consec</strong>
                    </td>
                <th rowspan="2" style="text-align: center;"><strong>N&uacute;mero
                        de Seguridad Social</strong></th>
                <th rowspan="2" style="text-align: center;"><strong>Clave
                        &Uacute;nica de Registro de Poblaci&oacute;n</strong></th>
                <th rowspan="2" style="text-align: center;"><strong>Nombre
                        del asegurado</strong>
                    </td>
                <th rowspan="2" style="text-align: center;"><strong>Reca&iacute;da
                        o revaluaci&oacute;n (*)</strong></th>
                <th colspan="3" style="text-align: center;"><strong>Fecha
                        del accidente o enfermedad de trabajo</strong></th>
                <th rowspan="2" style="text-align: center;"><strong>Tipo
                        de riesgo</strong></th>
                <th rowspan="2" style="text-align: center;"><strong>D&iacute;as
                        subsidiados</strong></th>
                <th rowspan="2" style="text-align: center;"><strong>Porcentaje
                        de incapacidad permanente o parcial</strong></th>
                <th rowspan="2" style="text-align: center;"><strong>Defunci&oacute;n
                        (D)</strong></th>
                <th colspan="3" style="text-align: center;"><strong>Fecha
                        de alta</strong></th>
        </tr>
        <tr>
            <td style="text-align: center;"><srong>A&ntilde;o</srong></td>
            <td style="text-align: center;"><srong>Mes</srong></td>
            <td style="text-align: center;"><srong>D&iacute;a</srong></td>
            <td style="text-align: center;"><srong>A&ntilde;o</srong></td>
            <td style="text-align: center;"><srong>Mes</srong></td>
            <td style="text-align: center;"><srong>D&iacute;a</srong></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${riesgosTrabajo}" var="riesgos">
            <tr>
                <td style="text-align: center;">${riesgos.consec}</td>
                <td style="text-align: center;">${riesgos.numSegSocial}</td>
                <td style="text-align: center;">${riesgos.curp}</td>
                <td style="text-align: center;">${riesgos.nombreAsegurado}</td>
                <td style="text-align: center;">${riesgos.recaidaRevaluacion}</td>
                <td style="text-align: center;"><fmt:formatDate
                value="${riesgos.fechaAccidente}" pattern="yyyy" /></td>
                <td style="text-align: center;"><fmt:formatDate
                value="${riesgos.fechaAccidente}" pattern="MM" /></td>
                <td style="text-align: center;"><fmt:formatDate
                value="${riesgos.fechaAccidente}" pattern="dd" /></td>
                <td style="text-align: center;">${riesgos.tipoRiesgo}</td>
                <td style="text-align: center;">${riesgos.diasSubsidiados}</td>
                <td style="text-align: center;">${riesgos.porcentajeIncapac}</td>
                <td style="text-align: center;">${riesgos.defuncion}</td>
                <td style="text-align: center;"><fmt:formatDate
                value="${riesgos.fechaAlta}" pattern="yyyy" /></td>
                <td style="text-align: center;"><fmt:formatDate
                value="${riesgos.fechaAlta}" pattern="MM" /></td>
                <td style="text-align: center;"><fmt:formatDate
                value="${riesgos.fechaAlta}" pattern="dd" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
