import cl.adexus.isl.spm.*

fixture {
	Banco.findByCodigo('001')?:(new Banco([codigo:'001', descripcion: 'Banco de Chile / Edwards / Citi / Atlas y Credichile'])).save(flush:true)
	Banco.findByCodigo('027')?:(new Banco([codigo:'027', descripcion: 'Corpbanca / Condell'])).save(flush:true)
	Banco.findByCodigo('016')?:(new Banco([codigo:'016', descripcion: 'BCI / TBANK / Banco Nova'])).save(flush:true)
	Banco.findByCodigo('043')?:(new Banco([codigo:'043', descripcion: 'Banco de la Nación Argentina'])).save(flush:true)
	Banco.findByCodigo('012')?:(new Banco([codigo:'012', descripcion: 'Banco del Estado de Chile'])).save(flush:true)
	Banco.findByCodigo('017')?:(new Banco([codigo:'017', descripcion: 'Banco do Brasil S.A.'])).save(flush:true)
	Banco.findByCodigo('037')?:(new Banco([codigo:'037', descripcion: 'Banco Santander-Chile / Banefe'])).save(flush:true)
	Banco.findByCodigo('504')?:(new Banco([codigo:'504', descripcion: 'BBVA / BBVA Express'])).save(flush:true)
	Banco.findByCodigo('028')?:(new Banco([codigo:'028', descripcion: 'Banco BICE'])).save(flush:true)
	Banco.findByCodigo('009')?:(new Banco([codigo:'009', descripcion: 'Banco Internacional'])).save(flush:true)
	Banco.findByCodigo('051')?:(new Banco([codigo:'051', descripcion: 'Banco Falabella'])).save(flush:true)
	Banco.findByCodigo('014')?:(new Banco([codigo:'014', descripcion: 'Scotiabank Chile'])).save(flush:true)
	Banco.findByCodigo('049')?:(new Banco([codigo:'049', descripcion: 'Banco Security'])).save(flush:true)
	Banco.findByCodigo('045')?:(new Banco([codigo:'045', descripcion: 'The Bank of Tokyo-Mitsubishi UFJ, Ltd.'])).save(flush:true)
	Banco.findByCodigo('041')?:(new Banco([codigo:'041', descripcion: 'JP Morgan Chase Bank, N.A.'])).save(flush:true)
	Banco.findByCodigo('039')?:(new Banco([codigo:'039', descripcion: 'Banco Itaú Chile'])).save(flush:true)
	Banco.findByCodigo('052')?:(new Banco([codigo:'052', descripcion: 'Deutsche Bank (Chile)'])).save(flush:true)
	Banco.findByCodigo('057')?:(new Banco([codigo:'057', descripcion: 'Banco Ripley'])).save(flush:true)
	Banco.findByCodigo('054')?:(new Banco([codigo:'054', descripcion: 'Rabobank Chile'])).save(flush:true)
	Banco.findByCodigo('055')?:(new Banco([codigo:'055', descripcion: 'Banco Consorcio'])).save(flush:true)
	Banco.findByCodigo('031')?:(new Banco([codigo:'031', descripcion: 'HSBC Bank (Chile)'])).save(flush:true)
	Banco.findByCodigo('056')?:(new Banco([codigo:'056', descripcion: 'Banco Penta'])).save(flush:true)
	Banco.findByCodigo('057')?:(new Banco([codigo:'057', descripcion: 'Banco París'])).save(flush:true)

}