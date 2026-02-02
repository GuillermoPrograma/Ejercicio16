package Main;

import Controlador.Controlador;
import Dao.Dao;
import Dao.DaoSqlimpl;
import Grafica.VistaControlador;
import Grafica.InterfazGrafica;
public class App {
	public static void main(String[] args) {
		
		 Dao daoSql = new DaoSqlimpl();
		 InterfazGrafica ig = new VistaControlador();   
		
		Controlador controlador = new Controlador(daoSql,ig);
		controlador.Init();
	}
}
