function parseDate(a) {
    var ukDatea = a.split('/');
    return (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
}

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "date-uk-pre": function ( a ) {
        return parseDate(a);
    },

    "date-uk-asc": function ( a, b ) {
        a = parseDate(a);
        b = parseDate(b);
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "date-uk-desc": function ( a, b ) {
        a = parseDate(a);
        b = parseDate(b);
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});