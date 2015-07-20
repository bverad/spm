import cl.adexus.isl.spm.*

fixture {
	
	CodigoAgenteAccidente.findByCodigo(111)?:(new CodigoAgenteAccidente([codigo:'111',descripcion: 'Máquinas de vapor.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(112)?:(new CodigoAgenteAccidente([codigo:'112',descripcion: 'Máquinas de combustión interna.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(119)?:(new CodigoAgenteAccidente([codigo:'119',descripcion: 'Otros'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(121)?:(new CodigoAgenteAccidente([codigo:'121',descripcion: 'Arboles de transmisión.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(122)?:(new CodigoAgenteAccidente([codigo:'122',descripcion: 'Correas, cables, poleas, cadenas, engranajes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(129)?:(new CodigoAgenteAccidente([codigo:'129',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(131)?:(new CodigoAgenteAccidente([codigo:'131',descripcion: 'Prensas mecánicas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(132)?:(new CodigoAgenteAccidente([codigo:'132',descripcion: 'Tornos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(133)?:(new CodigoAgenteAccidente([codigo:'133',descripcion: 'Fresadoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(134)?:(new CodigoAgenteAccidente([codigo:'134',descripcion: 'Rectificadoras y muelas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(135)?:(new CodigoAgenteAccidente([codigo:'135',descripcion: 'Cizallas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(136)?:(new CodigoAgenteAccidente([codigo:'136',descripcion: 'Forjadoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(137)?:(new CodigoAgenteAccidente([codigo:'137',descripcion: 'Laminadoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(139)?:(new CodigoAgenteAccidente([codigo:'139',descripcion: 'Otras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(141)?:(new CodigoAgenteAccidente([codigo:'141',descripcion: 'Sierras circulares.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(142)?:(new CodigoAgenteAccidente([codigo:'142',descripcion: 'Otras sierras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(143)?:(new CodigoAgenteAccidente([codigo:'143',descripcion: 'Máquinas de moldurar.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(144)?:(new CodigoAgenteAccidente([codigo:'144',descripcion: 'Cepilladoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(149)?:(new CodigoAgenteAccidente([codigo:'149',descripcion: 'Otras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(151)?:(new CodigoAgenteAccidente([codigo:'151',descripcion: 'Segadoras, incluso segadoras-trilladoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(152)?:(new CodigoAgenteAccidente([codigo:'152',descripcion: 'Trilladoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(159)?:(new CodigoAgenteAccidente([codigo:'159',descripcion: 'Otras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(161)?:(new CodigoAgenteAccidente([codigo:'161',descripcion: 'Máquinas de rozar.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(169)?:(new CodigoAgenteAccidente([codigo:'169',descripcion: 'Otras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(191)?:(new CodigoAgenteAccidente([codigo:'191',descripcion: 'Máquinas para desmontes, excavaciones, etc., a excepción de los medios de transporte.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(192)?:(new CodigoAgenteAccidente([codigo:'192',descripcion: 'Máquinas de hilar, de tejer y otras máquinas para la industria textil.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(193)?:(new CodigoAgenteAccidente([codigo:'193',descripcion: 'Máquinas para la manufactura de productos alimenticios y bebidas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(194)?:(new CodigoAgenteAccidente([codigo:'194',descripcion: 'Máquinas para la fabricación del papel.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(195)?:(new CodigoAgenteAccidente([codigo:'195',descripcion: 'Máquinas de imprenta.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(199)?:(new CodigoAgenteAccidente([codigo:'199',descripcion: 'Otras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(211)?:(new CodigoAgenteAccidente([codigo:'211',descripcion: 'Grúas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(212)?:(new CodigoAgenteAccidente([codigo:'212',descripcion: 'Ascensores, montacargas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(213)?:(new CodigoAgenteAccidente([codigo:'213',descripcion: 'Cabrestantes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(214)?:(new CodigoAgenteAccidente([codigo:'214',descripcion: 'Poleas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(219)?:(new CodigoAgenteAccidente([codigo:'219',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(221)?:(new CodigoAgenteAccidente([codigo:'221',descripcion: 'Ferrocarriles interurbanos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(222)?:(new CodigoAgenteAccidente([codigo:'222',descripcion: 'Equipos de transporte por vía férrea utilizados en las minas, las galerías, las canteras, los establecimientos industriales, los muelles, etc.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(229)?:(new CodigoAgenteAccidente([codigo:'229',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(231)?:(new CodigoAgenteAccidente([codigo:'231',descripcion: 'Tractores.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(232)?:(new CodigoAgenteAccidente([codigo:'232',descripcion: 'Camiones.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(233)?:(new CodigoAgenteAccidente([codigo:'233',descripcion: 'Carretillas motorizadas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(234)?:(new CodigoAgenteAccidente([codigo:'234',descripcion: 'Vehículos motorizados no clasificados bajo otros epígrafes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(235)?:(new CodigoAgenteAccidente([codigo:'235',descripcion: 'Vehículos de tracción animal.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(236)?:(new CodigoAgenteAccidente([codigo:'236',descripcion: 'Vehículos accionados por la fuerza del hombre.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(239)?:(new CodigoAgenteAccidente([codigo:'239',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(24)?:(new CodigoAgenteAccidente([codigo:'24',descripcion: 'Medios de transporte por aire.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(251)?:(new CodigoAgenteAccidente([codigo:'251',descripcion: 'Medios de transporte por agua con motor.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(252)?:(new CodigoAgenteAccidente([codigo:'252',descripcion: 'Medios de transporte por agua sin motor.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(261)?:(new CodigoAgenteAccidente([codigo:'261',descripcion: 'Transportadores aéreos por cable.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(262)?:(new CodigoAgenteAccidente([codigo:'262',descripcion: 'Transportadores mecánicos a excepción de los transportadores aéreos por cable.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(269)?:(new CodigoAgenteAccidente([codigo:'269',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(311)?:(new CodigoAgenteAccidente([codigo:'311',descripcion: 'Calderas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(312)?:(new CodigoAgenteAccidente([codigo:'312',descripcion: 'Recipientes de presión sin fogón.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(313)?:(new CodigoAgenteAccidente([codigo:'313',descripcion: 'Cañerías y accesorios de presión.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(314)?:(new CodigoAgenteAccidente([codigo:'314',descripcion: 'Cilindros de gas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(315)?:(new CodigoAgenteAccidente([codigo:'315',descripcion: 'Cajones de aire comprimido, equipo de buzo.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(319)?:(new CodigoAgenteAccidente([codigo:'319',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(321)?:(new CodigoAgenteAccidente([codigo:'321',descripcion: 'Altos hornos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(322)?:(new CodigoAgenteAccidente([codigo:'322',descripcion: 'Hornos de refinería.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(323)?:(new CodigoAgenteAccidente([codigo:'323',descripcion: 'Otros hornos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(324)?:(new CodigoAgenteAccidente([codigo:'324',descripcion: 'Estufas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(325)?:(new CodigoAgenteAccidente([codigo:'325',descripcion: 'Fogones.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(33)?:(new CodigoAgenteAccidente([codigo:'33',descripcion: 'Plantas refrigeradoras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(341)?:(new CodigoAgenteAccidente([codigo:'341',descripcion: 'Máquinas giratorias.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(342)?:(new CodigoAgenteAccidente([codigo:'342',descripcion: 'Conductores y cables eléctricos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(343)?:(new CodigoAgenteAccidente([codigo:'343',descripcion: 'Transformadores.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(344)?:(new CodigoAgenteAccidente([codigo:'344',descripcion: 'Aparatos de mando y de control.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(349)?:(new CodigoAgenteAccidente([codigo:'349',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(35)?:(new CodigoAgenteAccidente([codigo:'35',descripcion: 'Herramientas eléctricas manuales.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(361)?:(new CodigoAgenteAccidente([codigo:'361',descripcion: 'Herramientas manuales accionadas mecánicamente a excepción de las herramientas eléctricas manuales.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(362)?:(new CodigoAgenteAccidente([codigo:'362',descripcion: 'Herramientas manuales no accionadas mecánicamente.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(369)?:(new CodigoAgenteAccidente([codigo:'369',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(37)?:(new CodigoAgenteAccidente([codigo:'37',descripcion: 'Escaleras, rampas móviles.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(38)?:(new CodigoAgenteAccidente([codigo:'38',descripcion: 'Andamios.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(39)?:(new CodigoAgenteAccidente([codigo:'39',descripcion: 'Otros aparatos no clasificados bajo otros epígrafes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(41)?:(new CodigoAgenteAccidente([codigo:'41',descripcion: 'Explosivos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(421)?:(new CodigoAgenteAccidente([codigo:'421',descripcion: 'Polvos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(422)?:(new CodigoAgenteAccidente([codigo:'422',descripcion: 'Gases, vapores, humos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(423)?:(new CodigoAgenteAccidente([codigo:'423',descripcion: 'Líquidos no clasificados bajo otros epígrafes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(424)?:(new CodigoAgenteAccidente([codigo:'424',descripcion: 'Productos químicos no clasificados bajo otros epígrafes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(429)?:(new CodigoAgenteAccidente([codigo:'429',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(43)?:(new CodigoAgenteAccidente([codigo:'43',descripcion: 'Fragmentos volantes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(441)?:(new CodigoAgenteAccidente([codigo:'441',descripcion: 'Radiaciones ionizantes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(449)?:(new CodigoAgenteAccidente([codigo:'449',descripcion: 'Radiaciones de otro tipo.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(49)?:(new CodigoAgenteAccidente([codigo:'49',descripcion: 'Otros materiales y sustancias no clasificados bajo otros epígrafes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(511)?:(new CodigoAgenteAccidente([codigo:'511',descripcion: 'Condiciones climáticas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(512)?:(new CodigoAgenteAccidente([codigo:'512',descripcion: 'Superficies de tránsito y de trabajo.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(513)?:(new CodigoAgenteAccidente([codigo:'513',descripcion: 'Agua.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(519)?:(new CodigoAgenteAccidente([codigo:'519',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(512)?:(new CodigoAgenteAccidente([codigo:'512',descripcion: 'Pisos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(522)?:(new CodigoAgenteAccidente([codigo:'522',descripcion: 'Espacios exiguos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(523)?:(new CodigoAgenteAccidente([codigo:'523',descripcion: 'Escaleras.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(524)?:(new CodigoAgenteAccidente([codigo:'524',descripcion: 'Otras superficies de tránsito y de trabajo.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(525)?:(new CodigoAgenteAccidente([codigo:'525',descripcion: 'Aberturas en el suelo y en las paredes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(526)?:(new CodigoAgenteAccidente([codigo:'526',descripcion: 'Factores que crean el ambiente (alumbrado, ventilación, temperatura, ruidos, etc.).'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(529)?:(new CodigoAgenteAccidente([codigo:'529',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(531)?:(new CodigoAgenteAccidente([codigo:'531',descripcion: 'Tejados y revestimientos de galerías, de túneles, etc.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(532)?:(new CodigoAgenteAccidente([codigo:'532',descripcion: 'Pisos de galerías, de túneles, etc.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(533)?:(new CodigoAgenteAccidente([codigo:'533',descripcion: 'Frentes de minas, túneles, etc.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(534)?:(new CodigoAgenteAccidente([codigo:'534',descripcion: 'Pozos de minas.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(535)?:(new CodigoAgenteAccidente([codigo:'535',descripcion: 'Fuego.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(536)?:(new CodigoAgenteAccidente([codigo:'536',descripcion: 'Agua.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(539)?:(new CodigoAgenteAccidente([codigo:'539',descripcion: 'Otros.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(611)?:(new CodigoAgenteAccidente([codigo:'611',descripcion: 'Animales vivos.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(612)?:(new CodigoAgenteAccidente([codigo:'612',descripcion: 'Productos de animales.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(69)?:(new CodigoAgenteAccidente([codigo:'69',descripcion: 'Otros agentes no clasificados bajo otros epígrafes.'])).save(flush:true)
	CodigoAgenteAccidente.findByCodigo(7)?:(new CodigoAgenteAccidente([codigo:'7',descripcion: 'Agentes no clasificados por falta de datos suficientes'])).save(flush:true)
	
}
