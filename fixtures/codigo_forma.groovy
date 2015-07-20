import cl.adexus.isl.spm.*

fixture {
	
	CodigoForma.findByCodigo(11)?:(new CodigoForma([codigo:'11',descripcion: 'Caídas de personas con desnivelación [caídas desde alturas (árboles, edificios, andamios, escaleras, máquinas de trabajo, vehículos) y en profundidades (pozos, fosos, excavaciones, aberturas en el suelo)].'])).save(flush:true)
	CodigoForma.findByCodigo(12)?:(new CodigoForma([codigo:'12',descripcion: 'Caídas de personas que ocurren al mismo nivel.'])).save(flush:true)
	CodigoForma.findByCodigo(21)?:(new CodigoForma([codigo:'21',descripcion: 'Derrumbe (caídas de masas de tierra, de rocas, de piedras, de nieve).'])).save(flush:true)
	CodigoForma.findByCodigo(22)?:(new CodigoForma([codigo:'22',descripcion: 'Desplome (de edificios, de muros, de andamios, de escaleras, de pilas de mercancías).'])).save(flush:true)
	CodigoForma.findByCodigo(23)?:(new CodigoForma([codigo:'23',descripcion: 'Caídas de objetos en curso de manutención manual.'])).save(flush:true)
	CodigoForma.findByCodigo(24)?:(new CodigoForma([codigo:'24',descripcion: 'Otras caídas de objetos.'])).save(flush:true)
	CodigoForma.findByCodigo(31)?:(new CodigoForma([codigo:'31',descripcion: 'Pisadas sobre objetos.'])).save(flush:true)
	CodigoForma.findByCodigo(32)?:(new CodigoForma([codigo:'32',descripcion: 'Choques contra objetos inmóviles (a excepción de choques debidos a una caída anterior).'])).save(flush:true)
	CodigoForma.findByCodigo(33)?:(new CodigoForma([codigo:'33',descripcion: 'Choque contra objetos móviles.'])).save(flush:true)
	CodigoForma.findByCodigo(34)?:(new CodigoForma([codigo:'34',descripcion: 'Golpes por objetos móviles (comprendidos los fragmentos volantes y las partículas), a excepción de los golpes por objetos que caen.'])).save(flush:true)
	CodigoForma.findByCodigo(41)?:(new CodigoForma([codigo:'41',descripcion: 'Atrapada por un objeto.'])).save(flush:true)
	CodigoForma.findByCodigo(42)?:(new CodigoForma([codigo:'42',descripcion: 'Atrapada entre un objeto inmóvil y un objeto móvil.'])).save(flush:true)
	CodigoForma.findByCodigo(43)?:(new CodigoForma([codigo:'43',descripcion: 'Atrapada entre dos objetos móviles (a excepción de los objetos volantes o que caen).'])).save(flush:true)
	CodigoForma.findByCodigo(51)?:(new CodigoForma([codigo:'51',descripcion: 'Esfuerzos físicos excesivos al levantar objetos.'])).save(flush:true)
	CodigoForma.findByCodigo(52)?:(new CodigoForma([codigo:'52',descripcion: 'Esfuerzos físicos excesivos al empujar objetos o tirar de ellos.'])).save(flush:true)
	CodigoForma.findByCodigo(53)?:(new CodigoForma([codigo:'53',descripcion: 'Esfuerzos físicos excesivos al manejar o lanzar objetos.'])).save(flush:true)
	CodigoForma.findByCodigo(54)?:(new CodigoForma([codigo:'54',descripcion: 'Falsos movimientos.'])).save(flush:true)
	CodigoForma.findByCodigo(61)?:(new CodigoForma([codigo:'61',descripcion: 'Exposición al calor (de la atmósfera o del ambiente de trabajo).'])).save(flush:true)
	CodigoForma.findByCodigo(62)?:(new CodigoForma([codigo:'62',descripcion: 'Exposición al frío (de la atmósfera o del ambiente de trabajo).'])).save(flush:true)
	CodigoForma.findByCodigo(63)?:(new CodigoForma([codigo:'63',descripcion: 'Contacto con sustancias u objetos ardientes.'])).save(flush:true)
	CodigoForma.findByCodigo(64)?:(new CodigoForma([codigo:'64',descripcion: 'Contacto con sustancias u objetos muy fríos.'])).save(flush:true)
	CodigoForma.findByCodigo(7)?:(new CodigoForma([codigo:'7',descripcion: 'Exposición a, o contacto con, la corriente eléctrica'])).save(flush:true)
	CodigoForma.findByCodigo(81)?:(new CodigoForma([codigo:'81',descripcion: 'Contacto por inhalación, por ingestión o por absorción con sustancias nocivas.'])).save(flush:true)
	CodigoForma.findByCodigo(82)?:(new CodigoForma([codigo:'82',descripcion: 'Exposición a radiaciones ionizantes.'])).save(flush:true)
	CodigoForma.findByCodigo(83)?:(new CodigoForma([codigo:'83',descripcion: 'Exposición a otras radiaciones.'])).save(flush:true)
	CodigoForma.findByCodigo(91)?:(new CodigoForma([codigo:'91',descripcion: 'Otras formas de accidente, no clasificadas bajo otros epígrafes.'])).save(flush:true)
	CodigoForma.findByCodigo(92)?:(new CodigoForma([codigo:'92',descripcion: 'Accidentes no clasificados por falta de datos suficientes.'])).save(flush:true)
	
}
