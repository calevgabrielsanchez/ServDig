function DiasInhabilesAnio(date) {
	var diasInhabiles = [];
	
	var diasInhabiles2012 = [
	          	           		[2, 6], [3, 19], [5, 1],[5,10],[11,19],[12,25]
	          	       ];
	var diasInhabiles2013 = [
		          	           	[1, 1], [2, 4], [3, 18], [5, 1],[5, 10],[9, 16],[11, 18],[12, 25]
		          	       ];	
	var diasInhabiles2014 = [
		          	           	[1, 1], [2, 3], [3, 17], [5, 1],[5, 5], [9, 15],[9,16],[11, 17], [12,25]
		          	       ];
	
	if(date.getFullYear() == '2012'){
		diasInhabiles = diasInhabiles2012;
	}
	if(date.getFullYear() == '2013'){
		diasInhabiles = diasInhabiles2013;
	}
	if(date.getFullYear() == '2014'){
		diasInhabiles = diasInhabiles2014;
	}
	
		
    for (i = 0; i < diasInhabiles.length; i++) {
        if (date.getMonth() == diasInhabiles[i][0] - 1 && date.getDate() == diasInhabiles[i][1]) {
            return [false, diasInhabiles[i][2] + '_day'];
        }
    }
    return [true, ''];
}
    	

function inhabiles(date) {
    var noWeekend = $.datepicker.noWeekends(date);
    if (noWeekend[0]) {
        return DiasInhabilesAnio(date);
    } else {
        return noWeekend;
    }
}


function menos45dias(){
	var menos45dias =
		{
			dateFormat: 'dd-mm-yy',
			beforeShowDay: inhabiles,
			maxDate: getFechaServidor(),
			minDate: getFechaServidorMenos45Dias()
		}
	return menos45dias;
}

function maxFechaServer(){
	var maxFechaServer = {
			dateFormat: 'dd-mm-yy',
			beforeShowDay: inhabiles,
			maxDate: getFechaServidor()
	}
	return maxFechaServer;
}

function maxFechaServerConInhabiles(){
	var maxFechaServer = {
			dateFormat: 'dd-mm-yy',
			beforeShowDay: null,
			maxDate: getFechaServidor()
	}
	return maxFechaServer;
}

function fechaSeguimiento(){
	var fechaSeguimiento = {
			dateFormat: 'dd-mm-yy',
			beforeShowDay: inhabiles,
			maxDate: jsFechaMaxSeguimiento,
			minDate: jsFechaMinSeguimiento
	}
	return fechaSeguimiento;
}
