package pool;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MyDataSource {
	private static HikariConfig config = new HikariConfig(); //objeto que permite todos los elementos de config del pool
	private static HikariDataSource datasource; //el pool en si que nos permite conexiones
	
	static 
	{
		config.setJdbcUrl("jdbc:mysql://localhost/modelo15?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false");
		config.setUsername("user");
		config.setPassword("Manager");
		config.addDataSourceProperty("maximumPoolSize", 1);
		config.addDataSourceProperty("cachePrepStmts","true") ; //para utilizar consulta varias veces
		config.addDataSourceProperty("prepStmtCacheSize","250"); //cuantas consultas aguantan en cache
		config.addDataSourceProperty("prepStmtCacheSqlLimit","2048"); //maximo de caracteres por consulta
	
		
		datasource = new HikariDataSource(config);
	}
	
	private MyDataSource() {}
	
	public static Connection getConnection() throws SQLException
	{
		return datasource.getConnection(); //el unico metodo que se ve
	}
}

