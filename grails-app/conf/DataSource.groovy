hibernate {
	cache.use_second_level_cache = false
	cache.use_query_cache = false
	//transaction.auto_close_session = true
	connection.autocommit = true

	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	flush.mode = 'COMMIT'
}
hibernate.connection.release_mode = 'after_transaction'

// environment specific settings
environments {
	desarrollo_isl {
		println("| Inicializando datasource en entorno desarrollo_isl")
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
			url="jdbc:sqlserver://172.16.7.25:1433;DatabaseName=spmdesa"
			pooled = true
			driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
			username = "spmdesa_user"
			password = "d5YCQrTEVB"
			dialect = "org.hibernate.dialect.SQLServerDialect"

			properties {
				validationQuery="SELECT 1"
			}
			
			/*pooled = true
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
			dialect="org.hibernate.dialect.H2Dialect"*/
			
		}


		dataSource_auth {
			dbCreate = "validate"
			url =  "jdbc:sqlserver://172.16.7.25:1433;databaseName=Usuarios_ISL_desa2"//"jdbc:sqlserver://172.16.7.25:1433;databaseName=spmtestQA"
			username = "Usuarios_ISL_desa2_user"//"carga_etl_qa"
			password = "UckMmCtA" //"yQzwMsmE"
			pooled = true
			driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			properties {
				maxActive = -1
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=true
				validationQuery="SELECT 1"
			}
		}

		//hibernate.transaction.manager_lookup_class=org.hibernate.transaction.JBossTransactionManagerLookup
	}
	
	
	development {
		println "| Inicializando datasource en entorno development"
		dataSource {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/spm_desarrollo"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}

		dataSource_auth {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/usuarios_desa"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}
	}


	/*
	 * Para generar WAR en desarrollo
	 * 
	 * */
	test_isl {
		println "| Inicializando datasource en entorno test_isl"
		dataSource {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/spmtest34" //utilizado para generar war de qa y testearlo desde desarrollo
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}

		dataSource_auth {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/usuarios_desa" //para deployar version q.a en desarrollo
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}
		//hibernate.transaction.manager_lookup_class=org.hibernate.transaction.JBossTransactionManagerLookup
	}

	test {
		println "| Inicializando datasource en entorno test"
		dataSource {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/spmqa34" //para rama "treintaycuatro"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}

		dataSource_auth {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/usuarios"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}
		//hibernate.transaction.manager_lookup_class=org.hibernate.transaction.JBossTransactionManagerLookup
	}

	production {
		println "| Inicializando datasource en entorno production"
		dataSource {
			dbCreate = "validate"  //Solo valida si la base no ha cambiado
			jndiName = "java:jboss/datasources/spm"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}

		dataSource_auth {
			dbCreate = "validate"
			jndiName = "java:jboss/datasources/usuarios"
			dialect = "org.hibernate.dialect.SQLServerDialect"
			pooled = true
		}
		//hibernate.transaction.manager_lookup_class=org.hibernate.transaction.JBossTransactionManagerLookup
	}
}