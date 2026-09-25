<%@ include file="../general/taglibs.jsp"%>

<%-- <c:import url="http://desarrollo.imss.gob.mx/delta/resources/html/layout/footer.html"></c:import> --%>

<script>
	$(function(){
		$.ajax({
			url : '${staticResourcesPath}/html/layout/footer.html',
			dataType : 'html',
			success : function(html) {
				$('div#footerWrapper').html(html);
			}
		});
	});
</script>

<div id="footerWrapper"></div>