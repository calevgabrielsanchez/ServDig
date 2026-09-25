<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="${staticResourcesPath}/readabler/js/readabler.js"></script>
<script>
    // Readabler initialization on load
    window.addEventListener('DOMContentLoaded', () => {
        console.log("It's OK")
        try {
            new Readabler( {
                path: '${staticResourcesPath}/readabler/',
                accessibilityStatementLink: '${staticResourcesPath}/readabler/accessibility-statement.html',
            } );
        } catch (e) {
            console.warn( e );
        }
    });
</script>
