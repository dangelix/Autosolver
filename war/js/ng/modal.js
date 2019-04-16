app.service('modalService', function() {
	this.aver = function(mostrar) {
		var modals = [ {
			modal : 'showModalGrupo',
			show : false
		},
		{
			modal : 'showModalEvento',
			show : false
		},
		{
			modal : 'showModalEvidencias',
			show : false
		},{
			modal : 'modalImprimir',
			show : false
		}];
		for (var i = 0; i < modals.length; i++) {
			modals[i].show
			modals[i].show = false;
			if (modals[i].modal == mostrar) {
				modals[i].show = true;
			}
		}
//		console.log(modals);
		return modals;
	}
});