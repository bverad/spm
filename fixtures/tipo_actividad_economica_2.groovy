import cl.adexus.isl.spm.*

fixture {
	TipoActividadEconomica.findByCodigo('242200')?:(new TipoActividadEconomica([codigo: '242200', descripcion: 'Fabricación de pinturas barnices y productos de revestimiento similares; tintas de imprenta y masillas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('242300')?:(new TipoActividadEconomica([codigo: '242300', descripcion: 'Fabricación de productos farmacéuticos, sustancias químicas medicinales y productos botánicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('242400')?:(new TipoActividadEconomica([codigo: '242400', descripcion: 'Fabricaciones de jabones y detergentes, preparados para limpiar y pulir, perfumes y preparados de tocador'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('242910')?:(new TipoActividadEconomica([codigo: '242910', descripcion: 'Fabricación de explosivos  y productos de pirotecnia'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('242990')?:(new TipoActividadEconomica([codigo: '242990', descripcion: 'Fabricación del resto de los demás productos químicos n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('243000')?:(new TipoActividadEconomica([codigo: '243000', descripcion: 'Fabricación de fibras manufacturadas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('251110')?:(new TipoActividadEconomica([codigo: '251110', descripcion: 'Fabricación de cubiertas y cámaras de caucho'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('251120')?:(new TipoActividadEconomica([codigo: '251120', descripcion: 'Recauchado y renovación de cubiertas de caucho'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('251900')?:(new TipoActividadEconomica([codigo: '251900', descripcion: 'Fabricación de otros productos de caucho'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('252010')?:(new TipoActividadEconomica([codigo: '252010', descripcion: 'Fabricación de planchas, láminas, cintas, tiras de plástico'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('252020')?:(new TipoActividadEconomica([codigo: '252020', descripcion: 'Fabricación de tubos, mangueras para la construcción'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('252090')?:(new TipoActividadEconomica([codigo: '252090', descripcion: 'Fabricación de otros artículos de plástico'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('261010')?:(new TipoActividadEconomica([codigo: '261010', descripcion: 'Fabricación, manipulado y transformación  de vidrio plano'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('261020')?:(new TipoActividadEconomica([codigo: '261020', descripcion: 'Fabricación de vidrio hueco'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('261030')?:(new TipoActividadEconomica([codigo: '261030', descripcion: 'Fabricación de fibras de vidrio'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('261090')?:(new TipoActividadEconomica([codigo: '261090', descripcion: 'Fabricación de artículos de vidrio n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269101')?:(new TipoActividadEconomica([codigo: '269101', descripcion: 'Fabricación de productos de cerámica no refractaria para uso no estructural con fines ornamentales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269109')?:(new TipoActividadEconomica([codigo: '269109', descripcion: 'Fabricación de productos de cerámica no refractaria para uso no estructural n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269200')?:(new TipoActividadEconomica([codigo: '269200', descripcion: 'Fabricación de productos de cerámicas refractaria'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269300')?:(new TipoActividadEconomica([codigo: '269300', descripcion: 'Fabricación de  productos de arcilla y cerámicas no refractarias para uso estructural'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269400')?:(new TipoActividadEconomica([codigo: '269400', descripcion: 'Fabricación de cemento, cal y yeso'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269510')?:(new TipoActividadEconomica([codigo: '269510', descripcion: 'Elaboración de hormigón, artículos de hormigón y mortero (mezcla para construcción)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269520')?:(new TipoActividadEconomica([codigo: '269520', descripcion: 'Fabricación de productos de fibrocemento y asbestocemento'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269530')?:(new TipoActividadEconomica([codigo: '269530', descripcion: 'Fabricación de paneles de yeso para la construcción'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269590')?:(new TipoActividadEconomica([codigo: '269590', descripcion: 'Fabricación de artículos de cemento y yeso n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269600')?:(new TipoActividadEconomica([codigo: '269600', descripcion: 'Corte, tallado y acabado de la piedra'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269910')?:(new TipoActividadEconomica([codigo: '269910', descripcion: 'Fabricación de mezclas bituminosas a base de asfalto o de betunes naturales, de betún de petróleo, de alquitrán mineral o de brea de alquitrán mineral (por ejemplo: mastiques bituminosos, "cut backs")'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('269990')?:(new TipoActividadEconomica([codigo: '269990', descripcion: 'Fabricación de otros productos minerales no metálicos n.c.p'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('271000')?:(new TipoActividadEconomica([codigo: '271000', descripcion: 'Industrias básicas de hierro y acero'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('272010')?:(new TipoActividadEconomica([codigo: '272010', descripcion: 'Elaboración de productos de cobre en formas primarias. '])).save(flush:true)
	TipoActividadEconomica.findByCodigo('272020')?:(new TipoActividadEconomica([codigo: '272020', descripcion: 'Elaboración de productos de aluminio en formas primarias'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('272090')?:(new TipoActividadEconomica([codigo: '272090', descripcion: 'Fabricación de productos primarios de metales preciosos y de otros metales no ferrosos n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('273100')?:(new TipoActividadEconomica([codigo: '273100', descripcion: 'Fundición de hierro y acero'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('273200')?:(new TipoActividadEconomica([codigo: '273200', descripcion: 'Fundición de metales no ferrosos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('281100')?:(new TipoActividadEconomica([codigo: '281100', descripcion: 'Fabricación de productos metálicos de uso estructural '])).save(flush:true)
	TipoActividadEconomica.findByCodigo('281211')?:(new TipoActividadEconomica([codigo: '281211', descripcion: 'Fabricación de recipientes de gas comprimido o licuado'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('281219')?:(new TipoActividadEconomica([codigo: '281219', descripcion: 'Fabricación de tanques, depósitos y recipientes de metal n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('281280')?:(new TipoActividadEconomica([codigo: '281280', descripcion: 'Reparación de tanques,  depósitos y recipientes de metal'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('281310')?:(new TipoActividadEconomica([codigo: '281310', descripcion: 'Fabricación de generadores de vapor, excepto calderas de agua caliente para calefacción'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('281380')?:(new TipoActividadEconomica([codigo: '281380', descripcion: 'Reparación de generadores de vapor,  excepto calderas de agua caliente para calefacción central'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('289100')?:(new TipoActividadEconomica([codigo: '289100', descripcion: 'Forja, prensado, estampado y laminado de metal; incluye pulvimetalurgia'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('289200')?:(new TipoActividadEconomica([codigo: '289200', descripcion: 'Tratamientos y revestimientos de metales; obras de ingeniería mecánica en general realizadas a cambio de una retribución o contrata'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('289310')?:(new TipoActividadEconomica([codigo: '289310', descripcion: 'Fabricación de artículos de cuchillería'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('289320')?:(new TipoActividadEconomica([codigo: '289320', descripcion: 'Fabricación de herramientas de mano y artículos de ferretería'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('289910')?:(new TipoActividadEconomica([codigo: '289910', descripcion: 'Fabricación de cables, alambres y productos de alambre'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('289990')?:(new TipoActividadEconomica([codigo: '289990', descripcion: 'Fabricación del resto de los  productos elaborados de metal n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291110')?:(new TipoActividadEconomica([codigo: '291110', descripcion: 'Fabricación de motores y  turbinas, excepto para aeronaves vehículos automotores y motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291180')?:(new TipoActividadEconomica([codigo: '291180', descripcion: 'Reparación de motores y turbinas,  excepto motores para aeronaves,  vehículos automotores y motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291210')?:(new TipoActividadEconomica([codigo: '291210', descripcion: 'Fabricación de bombas, grifos, válvulas, compresores, sistemas hidráulicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291280')?:(new TipoActividadEconomica([codigo: '291280', descripcion: 'Reparación de bombas,  compresores, sistemas hidráulicos, válvulas y artículos de grifería'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291310')?:(new TipoActividadEconomica([codigo: '291310', descripcion: 'Fabricación de cojinetes, engranajes, trenes de engranajes y piezas  de transmisión'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291380')?:(new TipoActividadEconomica([codigo: '291380', descripcion: 'Reparación de cojinetes,  engranajes,  trenes de engranajes y piezas de transmisión'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291410')?:(new TipoActividadEconomica([codigo: '291410', descripcion: 'Fabricación de hornos, hogares y quemadores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291480')?:(new TipoActividadEconomica([codigo: '291480', descripcion: 'Reparación de hornos, hogares y quemadores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291510')?:(new TipoActividadEconomica([codigo: '291510', descripcion: 'Fabricación de equipo de elevación y manipulación'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291580')?:(new TipoActividadEconomica([codigo: '291580', descripcion: 'Reparación de equipo de elevación y manipulación'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291910')?:(new TipoActividadEconomica([codigo: '291910', descripcion: 'Fabricación de otro tipo de maquinarias de uso general'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('291980')?:(new TipoActividadEconomica([codigo: '291980', descripcion: 'Reparación otros tipos de maquinaria y equipos de uso general'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292110')?:(new TipoActividadEconomica([codigo: '292110', descripcion: 'Fabricación de maquinaria agropecuaria y forestal'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292180')?:(new TipoActividadEconomica([codigo: '292180', descripcion: 'Reparación de maquinaria agropecuaria y forestal'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292210')?:(new TipoActividadEconomica([codigo: '292210', descripcion: 'Fabricación de máquinas herramientas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292280')?:(new TipoActividadEconomica([codigo: '292280', descripcion: 'Reparación de máquinas herramientas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292310')?:(new TipoActividadEconomica([codigo: '292310', descripcion: 'Fabricación de maquinaria metalúrgica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292380')?:(new TipoActividadEconomica([codigo: '292380', descripcion: 'Reparación de maquinaria para la industria metalúrgica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292411')?:(new TipoActividadEconomica([codigo: '292411', descripcion: 'Fabricación de maquinaria para minas y canteras y para obras de construcción'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292412')?:(new TipoActividadEconomica([codigo: '292412', descripcion: 'Fabricación de partes para maquinas de sondeo o perforación'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292480')?:(new TipoActividadEconomica([codigo: '292480', descripcion: 'Reparación de maquinaria para la explotación del petróleo, minas y canteras, y para obras de construcción'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292510')?:(new TipoActividadEconomica([codigo: '292510', descripcion: 'Fabricación de maquinaria para la elaboración de alimentos, bebidas y tabacos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292580')?:(new TipoActividadEconomica([codigo: '292580', descripcion: 'Reparación de maquinaria para la elaboración de alimentos, bebidas y tabacos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292610')?:(new TipoActividadEconomica([codigo: '292610', descripcion: 'Fabricación de maquinaria para la elaboración de prendas textiles, prendas de vestir y cueros'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292680')?:(new TipoActividadEconomica([codigo: '292680', descripcion: 'Reparación de maquinaria para la industria textil,  de la confección,  del cuero y del calzado'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292710')?:(new TipoActividadEconomica([codigo: '292710', descripcion: 'Fabricación de armas y municiones'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292780')?:(new TipoActividadEconomica([codigo: '292780', descripcion: 'Reparación de armas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292910')?:(new TipoActividadEconomica([codigo: '292910', descripcion: 'Fabricación de otros tipos de maquinarias de uso especial'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('292980')?:(new TipoActividadEconomica([codigo: '292980', descripcion: 'Reparación de otros tipos de maquinaria de uso especial'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('293000')?:(new TipoActividadEconomica([codigo: '293000', descripcion: 'Fabricación  de aparatos de uso domestico n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('300010')?:(new TipoActividadEconomica([codigo: '300010', descripcion: 'Fabricación y armado de computadores y hardware en general'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('300020')?:(new TipoActividadEconomica([codigo: '300020', descripcion: 'Fabricación de maquinaria de oficina, contabilidad, n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('311010')?:(new TipoActividadEconomica([codigo: '311010', descripcion: 'Fabricación de motores, generadores y transformadores eléctricos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('311080')?:(new TipoActividadEconomica([codigo: '311080', descripcion: 'Reparación de motores, generadores y transformadores eléctricos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('312010')?:(new TipoActividadEconomica([codigo: '312010', descripcion: 'Fabricación de aparatos de distribución y control de la energía eléctrica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('312080')?:(new TipoActividadEconomica([codigo: '312080', descripcion: 'Reparación de aparatos de distribución y control de la energía eléctrica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('313000')?:(new TipoActividadEconomica([codigo: '313000', descripcion: 'Fabricación de hilos y cables aislados'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('314000')?:(new TipoActividadEconomica([codigo: '314000', descripcion: 'Fabricación de acumuladores de pilas y baterías  primarias'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('315010')?:(new TipoActividadEconomica([codigo: '315010', descripcion: 'Fabricación de lámparas y equipo de iluminación'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('315080')?:(new TipoActividadEconomica([codigo: '315080', descripcion: 'Reparación de equipo de iluminación'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('319010')?:(new TipoActividadEconomica([codigo: '319010', descripcion: 'Fabricación de otros tipos de equipo eléctrico n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('319080')?:(new TipoActividadEconomica([codigo: '319080', descripcion: 'Reparación de otros tipos de equipo eléctrico n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('321010')?:(new TipoActividadEconomica([codigo: '321010', descripcion: 'Fabricación de componentes electrónicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('321080')?:(new TipoActividadEconomica([codigo: '321080', descripcion: 'Reparación de componentes electrónicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('322010')?:(new TipoActividadEconomica([codigo: '322010', descripcion: 'Fabricación de transmisores de radio  y televisión de aparatos para telefonía y telegrafía con hilos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('322080')?:(new TipoActividadEconomica([codigo: '322080', descripcion: 'Reparación de transmisores de radio y televisión y de aparatos para telefonía y telegrafía con hilos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('323000')?:(new TipoActividadEconomica([codigo: '323000', descripcion: 'Fabricación de receptores de radio y televisión, aparatos de grabación y reproducción de sonidos y vídeo, y productos conexos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331110')?:(new TipoActividadEconomica([codigo: '331110', descripcion: 'Fabricación de equipo medico y quirúrgico y de aparatos ortopédicos.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331120')?:(new TipoActividadEconomica([codigo: '331120', descripcion: 'Laboratorios dentales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331180')?:(new TipoActividadEconomica([codigo: '331180', descripcion: 'Reparación de equipo médico y quirúrgico y de aparatos ortopédicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331210')?:(new TipoActividadEconomica([codigo: '331210', descripcion: 'Fabricación de instrumentos y aparatos para medir, verificar, ensayar, navegar y otros fines, excepto el equipo de control de procesos industriales.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331280')?:(new TipoActividadEconomica([codigo: '331280', descripcion: 'Reparación de instrumentos y aparatos para medir, verificar, ensayar, navegar y otros fines, excepto el equipo de control de procesos industriales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331310')?:(new TipoActividadEconomica([codigo: '331310', descripcion: 'Fabricación de equipos de control de procesos industriales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('331380')?:(new TipoActividadEconomica([codigo: '331380', descripcion: 'Reparación de equipo de control de procesos industriales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('332010')?:(new TipoActividadEconomica([codigo: '332010', descripcion: 'Fabricación y/o reparación de lentes y artículos oftalmológicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('332020')?:(new TipoActividadEconomica([codigo: '332020', descripcion: 'Fabricación de instrumentos de óptica n.c.p. y equipos fotográficos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('332080')?:(new TipoActividadEconomica([codigo: '332080', descripcion: 'Reparación de instrumentos de óptica n.c.p y equipo fotográficos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('333000')?:(new TipoActividadEconomica([codigo: '333000', descripcion: 'Fabricación de relojes'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('341000')?:(new TipoActividadEconomica([codigo: '341000', descripcion: 'Fabricación de vehículos automotores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('342000')?:(new TipoActividadEconomica([codigo: '342000', descripcion: 'Fabricación de carrocerías para vehículos automotores; fabricación de remolques y semi remolques'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('343000')?:(new TipoActividadEconomica([codigo: '343000', descripcion: 'Fabricación de partes y accesorios para vehículos automotores y sus motores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('351110')?:(new TipoActividadEconomica([codigo: '351110', descripcion: 'Construcción  y reparación de buques; astilleros'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('351120')?:(new TipoActividadEconomica([codigo: '351120', descripcion: 'Construcción de embarcaciones menores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('351180')?:(new TipoActividadEconomica([codigo: '351180', descripcion: 'Reparación de embarcaciones menores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('351210')?:(new TipoActividadEconomica([codigo: '351210', descripcion: 'Construcción  de embarcaciones de recreo y deporte'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('351280')?:(new TipoActividadEconomica([codigo: '351280', descripcion: 'Reparación de embarcaciones (recreo y deportes)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('352000')?:(new TipoActividadEconomica([codigo: '352000', descripcion: 'Fabricación de locomotoras y de material rodante para ferrocarriles y tranvías'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('353010')?:(new TipoActividadEconomica([codigo: '353010', descripcion: 'Fabricación de aeronaves y naves espaciales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('353080')?:(new TipoActividadEconomica([codigo: '353080', descripcion: 'Reparación de aeronaves y naves espaciales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('359100')?:(new TipoActividadEconomica([codigo: '359100', descripcion: 'Fabricación de motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('359200')?:(new TipoActividadEconomica([codigo: '359200', descripcion: 'Fabricación de bicicletas y de sillones de ruedas para inválidos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('359900')?:(new TipoActividadEconomica([codigo: '359900', descripcion: 'Fabricación de otros equipos de transporte n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('361010')?:(new TipoActividadEconomica([codigo: '361010', descripcion: 'Fabricación de muebles principalmente de madera'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('361020')?:(new TipoActividadEconomica([codigo: '361020', descripcion: 'Fabricación de otros muebles n.c.p., incluso colchones'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369100')?:(new TipoActividadEconomica([codigo: '369100', descripcion: 'Fabricación de joyas y productos conexos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369200')?:(new TipoActividadEconomica([codigo: '369200', descripcion: 'Fabricación de instrumentos de música'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369300')?:(new TipoActividadEconomica([codigo: '369300', descripcion: 'Fabricación de artículos de deporte'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369400')?:(new TipoActividadEconomica([codigo: '369400', descripcion: 'Fabricación de juegos y juguetes'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369910')?:(new TipoActividadEconomica([codigo: '369910', descripcion: 'Fabricación de plumas y lápices de toda clase y artículos de escritorio en general'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369920')?:(new TipoActividadEconomica([codigo: '369920', descripcion: 'Fabricación de brochas, escobas y cepillos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369930')?:(new TipoActividadEconomica([codigo: '369930', descripcion: 'Fabricación de fósforos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('369990')?:(new TipoActividadEconomica([codigo: '369990', descripcion: 'Fabricación de artículos de otras industrias n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('371000')?:(new TipoActividadEconomica([codigo: '371000', descripcion: 'Reciclamiento de desperdicios y desechos metálicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('372010')?:(new TipoActividadEconomica([codigo: '372010', descripcion: 'Reciclamiento de papel'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('372020')?:(new TipoActividadEconomica([codigo: '372020', descripcion: 'Reciclamiento de vidrio'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('372090')?:(new TipoActividadEconomica([codigo: '372090', descripcion: 'Reciclamiento de otros desperdicios y desechos no metálicos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('401011')?:(new TipoActividadEconomica([codigo: '401011', descripcion: 'Generación hidroeléctrica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('401012')?:(new TipoActividadEconomica([codigo: '401012', descripcion: 'Generación en centrales termoeléctrica de ciclos combinados'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('401013')?:(new TipoActividadEconomica([codigo: '401013', descripcion: 'Generación en otras centrales termoeléctricas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('401019')?:(new TipoActividadEconomica([codigo: '401019', descripcion: 'Generación en otras centrales n.c.p.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('401020')?:(new TipoActividadEconomica([codigo: '401020', descripcion: 'Transmisión de energía eléctrica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('401030')?:(new TipoActividadEconomica([codigo: '401030', descripcion: 'Distribución de energía eléctrica'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('402000')?:(new TipoActividadEconomica([codigo: '402000', descripcion: 'Fabricación de gas; distribución de combustibles gaseosos por tuberías'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('403000')?:(new TipoActividadEconomica([codigo: '403000', descripcion: 'Suministro de vapor y agua caliente'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('410000')?:(new TipoActividadEconomica([codigo: '410000', descripcion: 'Captación, depuración y distribución de agua'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('451010')?:(new TipoActividadEconomica([codigo: '451010', descripcion: 'Preparación del terreno, excavaciones y movimientos de tierras'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('451020')?:(new TipoActividadEconomica([codigo: '451020', descripcion: 'Servicios de demolición y el derribo de edificios y otras estructuras'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('452010')?:(new TipoActividadEconomica([codigo: '452010', descripcion: 'Construcción de edificios completos o de partes de edificios'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('452020')?:(new TipoActividadEconomica([codigo: '452020', descripcion: 'Obras de ingeniería'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('453000')?:(new TipoActividadEconomica([codigo: '453000', descripcion: 'Acondicionamiento de edificios.'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('454000')?:(new TipoActividadEconomica([codigo: '454000', descripcion: 'Obras menores en construcción (albañiles, carpinteros)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('455000')?:(new TipoActividadEconomica([codigo: '455000', descripcion: 'Alquiler de  equipo de construcción o demolición dotado de operarios'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('501010')?:(new TipoActividadEconomica([codigo: '501010', descripcion: 'Venta al por mayor de vehículos automotores (Importación, distribución) excepto motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('501020')?:(new TipoActividadEconomica([codigo: '501020', descripcion: 'Venta al por menor de vehículos automotores nuevos o usados (incluye compraventa) excepto motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('502010')?:(new TipoActividadEconomica([codigo: '502010', descripcion: 'Servicio de lavado de vehículos automotores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('502020')?:(new TipoActividadEconomica([codigo: '502020', descripcion: 'Servicios de remolque de vehículos (grúas)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('502080')?:(new TipoActividadEconomica([codigo: '502080', descripcion: 'Mantenimiento y reparación de vehículos automotores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('503000')?:(new TipoActividadEconomica([codigo: '503000', descripcion: 'Venta de partes, piezas y accesorios de vehículos automotores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('504010')?:(new TipoActividadEconomica([codigo: '504010', descripcion: 'Venta de motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('504020')?:(new TipoActividadEconomica([codigo: '504020', descripcion: 'Venta de piezas y accesorios de motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('504080')?:(new TipoActividadEconomica([codigo: '504080', descripcion: 'Reparación de motocicletas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('505000')?:(new TipoActividadEconomica([codigo: '505000', descripcion: 'Venta al por menor de combustible  para automotores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('511010')?:(new TipoActividadEconomica([codigo: '511010', descripcion: 'Corretaje de productos agrícolas'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('511020')?:(new TipoActividadEconomica([codigo: '511020', descripcion: 'Corretaje de ganado (ferias de ganado)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('511030')?:(new TipoActividadEconomica([codigo: '511030', descripcion: 'Otros tipos de corretajes o remates n.c.p. (excepto servicios de martillero)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512110')?:(new TipoActividadEconomica([codigo: '512110', descripcion: 'Venta al por mayor de animales vivos '])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512120')?:(new TipoActividadEconomica([codigo: '512120', descripcion: 'Venta al por mayor de productos pecuarios (lanas, pieles, cueros sin procesar); excepto alimentos'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512130')?:(new TipoActividadEconomica([codigo: '512130', descripcion: 'Venta al por mayor de materias primas agrícolas (granos, frutas oleaginosas, tabaco en bruto, flores y plantas)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512210')?:(new TipoActividadEconomica([codigo: '512210', descripcion: 'Mayorista de frutas y verduras'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512220')?:(new TipoActividadEconomica([codigo: '512220', descripcion: 'Mayoristas de carnes '])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512230')?:(new TipoActividadEconomica([codigo: '512230', descripcion: 'Mayoristas de productos del mar (pescado, mariscos, algas)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512240')?:(new TipoActividadEconomica([codigo: '512240', descripcion: 'Mayoristas de vinos y licores'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512250')?:(new TipoActividadEconomica([codigo: '512250', descripcion: 'Venta al por mayor de confites'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512260')?:(new TipoActividadEconomica([codigo: '512260', descripcion: 'Venta al por mayor  de alimentos para animales'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('512290')?:(new TipoActividadEconomica([codigo: '512290', descripcion: 'Venta al por mayor de huevos, leche,  abarrotes,  y otros alimentos n.c.p.)'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('513100')?:(new TipoActividadEconomica([codigo: '513100', descripcion: 'Venta al por mayor de productos textiles, prendas de vestir y calzado'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('513910')?:(new TipoActividadEconomica([codigo: '513910', descripcion: 'Venta al por mayor de muebles'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('513920')?:(new TipoActividadEconomica([codigo: '513920', descripcion: 'Venta al por mayor de artículos eléctricos y electrónicos para el hogar'])).save(flush:true)
	TipoActividadEconomica.findByCodigo('513930')?:(new TipoActividadEconomica([codigo: '513930', descripcion: 'Venta al por mayor de artículos de perfumería, cosméticos, jabones y productos de limpieza'])).save(flush:true)

}