package Main;

import Controlador.Controlador;
import Dao.Dao;
import Dao.DaoBinarioimpl;
import Dao.DaoJsonimpl;
import Dao.DaoSqlimpl;
import Grafica.VistaControlador;
import Grafica.InterfazGrafica;
public class App {
	public static void main(String[] args) {
		
		 Dao daoSql = new DaoSqlimpl();
		 Dao daoBin = new DaoBinarioimpl();
		 Dao daoJson = new DaoJsonimpl();
		 InterfazGrafica ig = new VistaControlador();   
		
		Controlador controlador = new Controlador(daoSql,daoBin,daoJson,ig);
		controlador.Init();
	}
}
