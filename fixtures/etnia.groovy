import cl.adexus.isl.spm.*

fixture {
	
	Etnia.findByCodigo('0')?:(new Etnia([codigo:'0', descripcion: 'Ninguna'])).save(flush:true)
	Etnia.findByCodigo('1')?:(new Etnia([codigo:'1', descripcion: 'Alacalufe'])).save(flush:true)
	Etnia.findByCodigo('2')?:(new Etnia([codigo:'2', descripcion: 'Atacameño'])).save(flush:true)
	Etnia.findByCodigo('3')?:(new Etnia([codigo:'3', descripcion: 'Aimara'])).save(flush:true)
	Etnia.findByCodigo('4')?:(new Etnia([codigo:'4', descripcion: 'Colla'])).save(flush:true)
	Etnia.findByCodigo('5')?:(new Etnia([codigo:'5', descripcion: 'Diaguita'])).save(flush:true)
	Etnia.findByCodigo('6')?:(new Etnia([codigo:'6', descripcion: 'Mapuche'])).save(flush:true)
	Etnia.findByCodigo('7')?:(new Etnia([codigo:'7', descripcion: 'Quechua'])).save(flush:true)
	Etnia.findByCodigo('8')?:(new Etnia([codigo:'8', descripcion: 'Rapa Nui'])).save(flush:true)
	Etnia.findByCodigo('9')?:(new Etnia([codigo:'9', descripcion: 'Yámana (Yagan)'])).save(flush:true)
	Etnia.findByCodigo('10')?:(new Etnia([codigo:'10', descripcion: 'Otro'])).save(flush:true)
			
}