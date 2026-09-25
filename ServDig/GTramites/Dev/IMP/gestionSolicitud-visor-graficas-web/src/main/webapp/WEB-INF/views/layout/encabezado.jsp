<%@ include file="../general/taglibs.jsp" %>

<%-- <c:import url="http://deltaqa.imss.gob.mx/delta/resources/html/layout/header.html"></c:import> --%>

<script>
	$(function(){
		$.ajax({
			url : '${staticResourcesPath}/html/layout/header.html',
			dataType : 'html',
			success : function(html) {
				$('div#headerWrapper').html(html);
			}
		});
	});
</script>

<div id="headerWrapper"></div>