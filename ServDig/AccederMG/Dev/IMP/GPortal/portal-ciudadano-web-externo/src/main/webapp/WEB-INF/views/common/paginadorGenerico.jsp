<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		$('#tooltipPaso${param.activo}').tooltip();
	});
</script>


<div class="row">
	<div class="col-sm-12 text-right">
		<ul class="pagination">
		
			<li class="disabled">
				<span>&laquo;</span>
			</li>
			<c:forTokens items="${param.pasos}" delims="," var="paso">
				<li id="li${paso}" class="${param.activo==paso?"active":"disabled"}">
					<span id="tooltipPaso${paso}" data-toggle="tooltip" data-placement="top" title="${param.mensaje}">${paso}</span>
				</li>
			</c:forTokens>
			<li class="disabled">
				<span>&raquo;</span>
			</li>
		</ul>
	</div>
</div>
