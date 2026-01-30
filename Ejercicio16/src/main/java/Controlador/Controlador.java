package Controlador;

import Dao.Dao;
import Grafica.InterfazGrafica;

public class Controlador {
	private Dao daoSql;
	private Dao daoBinario;
	private Dao daoJson;
	private InterfazGrafica ig;
	
	public Controlador(Dao daoSql, Dao daoBinario , Dao daoJson , InterfazGrafica ig) 
	{
		 this.daoSql = daoSql;
	     this.daoBinario = daoBinario;
	     this.daoJson = daoJson;
	     this.ig = ig;
	}
	
	public void Init() 
	{
		ig.init();
		
	}
	
	
}
