<%@ include file="../general/taglibs.jsp"%>

<%-- <c:import url="http://deltaqa.imss.gob.mx/delta/resources/html/layout/menu-institucional.html"></c:import> --%>

<script>
	$(function(){
		$.ajax({
			url : '${staticResourcesPath}/html/layout/menu-institucional.html',
			dataType : 'html',
			success : function(html) {
				$('div#menuInstWrapper').html(html);
			}
		});
	});
</script>

<div id="menuInstWrapper"></div>

