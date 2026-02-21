package Main;


import Controlador.ControladorHibernate;
import Dao.Dao;
import Dao.DaoHibernateImpl;
import Dao.DaoSqlimpl;
import Grafica.InterfazGrafica;
import Grafica.VistaControlador;

public class AppHibernate {
	public static void main(String[] args) {
		
		 Dao daoHibernate = new DaoHibernateImpl();
		 InterfazGrafica ig = new VistaControlador();   
		
		 ControladorHibernate controladorHibernate = new ControladorHibernate(daoHibernate,ig);
		 controladorHibernate.Init();
	}
}
