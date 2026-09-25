<%@ include file="../general/taglibs.jsp" %>

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