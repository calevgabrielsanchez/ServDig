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

    <table id="tbRiesgosTrabajo" class="table table-striped table-bordered table-condensed table-responsive" id="testTable" style="width: 100%" cellpandding="0" cellpancing="0" border="0">
        <thead>
        <tr>
            <th style="text-align: center;"><strong>Documento Generado</strong></th>
            <th style="text-align: center;"><strong>Fecha Creaci&oacute;n</strong></th>
            <th style="text-align: center;"><strong>Estado</strong></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="text-align: center;"><c:if test="${reportObtain[0] != null}">${reportObtain[0]}</c:if></td>
            <td style="text-align: center;"><c:if test="${reportObtain[1] != null}"><fmt:formatDate value="${reportObtain[1]}" pattern="dd-MM-yyyy"/></c:if></td>
            <td style="text-align: center;"><c:if test="${reportObtain[2] != null}">${reportObtain[2]}</c:if></td>
        </tr>
        </tbody>
    </table>
    <c:if test="${reportObtain != null}">
        <input type="hidden" id="obtainRfc" name="obtainRfc" value="${reportObtain[0]}"/>
    </c:if>

</div>