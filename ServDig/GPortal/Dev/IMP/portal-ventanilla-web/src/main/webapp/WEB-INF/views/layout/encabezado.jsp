<%@ include file='../general/taglibs.jsp'%>

<script>
	$(function(){
		$('div#headerWrapper').load('${staticResourcesPath}/html/layout/header-interno.html', function(){
			if ($('div#headerSecondaryNavBar').length > 0) {
				
				$('div#info-usuario-alt', '#cuerpo').appendTo($('div#headerSecondaryNavBar'));
				
				$('div#headerSecondaryNavBar').on('affixed.bs.affix', function(){
			        $('div#headerSecondaryNavBar div#gobMxSecondaryNavBar').hide();
			        $('div#headerSecondaryNavBar div#info-usuario-alt').show();
			    });
				
				$('#headerSecondaryNavBar').on('affix-top.bs.affix', function(){
			        $('div#headerSecondaryNavBar div#gobMxSecondaryNavBar').show();
			        $('div#headerSecondaryNavBar div#info-usuario-alt').hide();
			    });
			}
		});
	});
</script>

<div id="headerWrapper"></div>