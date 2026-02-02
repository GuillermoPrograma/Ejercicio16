package Controlador;

import Dao.Dao;
import Grafica.InterfazGrafica;

public class Controlador {
	private Dao daoSql;
	private InterfazGrafica ig;

	public Controlador(Dao daoSql, InterfazGrafica ig) {
		this.daoSql = daoSql;

		this.ig = ig;
	}

	public void Init() {
		int result;

		do {

			result = ig.mostrarMenu();
			switch (result) {
			case 1:
				guardoAlumno();
				break;
			case 2:
				guardoGrupo();
				break;
			case 3:
				aniadirAlumnoAGrupoPorNia();
				break; // NUEVA OPCIÓN
			case 4:
				mostrarAlumnos();
				break;
			case 5:
				modificarNombreAPartirDeNia();
				break;
			case 6:
				eliminarAlumnoPorNia();
				break;
			case 7:
				eliminarAlumnoPorApellido();
				break;
			case 0:
				System.out.println("\nSaliendo del programa...\n");
				break;
			default:
				System.err.println("\nNúmero no válido");
			}

		} while (result != 0);

	}

	public void guardoAlumno() {
		int result = daoSql.guardarAlumnoBd(ig.guardoAlumnoGrafica());
		if (result == -1) {
			ig.mostrarMensaje("Error de conexion");
		} else if (result == 0) {
			ig.mostrarMensaje("No se puede guardar este grupo");
		} else {
			ig.mostrarMensaje(result + " tablas afectadas");
		}
	}

	public void guardoGrupo() {
		int result = daoSql.guardarGrupo(ig.guardoGrupoGrafica());
		if (result == -1) {
			ig.mostrarMensaje("Error de conexion");
		} else if (result == 0) {
			ig.mostrarMensaje("No se puede guardar este grupo");
		} else {
			ig.mostrarMensaje(result + " tablas afectadas");
		}
	}

	public void aniadirAlumnoAGrupoPorNia() {
		int[] nia_grupo = ig.añadoAlumnoAGrupoGrafica();
		int nia = nia_grupo[0];
		int id = nia_grupo[1];
		int result = daoSql.añadirAlumnoAGrupoPorNia(nia, id);
		if (result == -1) {
			ig.mostrarMensaje("Error de conexion");
		} else if (result == 0) {
			ig.mostrarMensaje("No se puede guardar este grupo");
		} else {
			ig.mostrarMensaje(result + " tablas afectadas");
		}
	}

	public void mostrarAlumnos() {
		ig.muestroAlumnosGrafica(daoSql.mostrarAlumnos());
	}

	public void modificarNombreAPartirDeNia() {
		String[] nombre_nia = ig.modificarAlumnoIdGrafica();
		int nia = Integer.parseInt(nombre_nia[0]); // me he asegurado de que vaya a salir bien en la vistaControlador
		String nombre = nombre_nia[1];
		daoSql.modificarNombreAPartirDeNiaBD(nombre, nia);
	}

	public void eliminarAlumnoPorNia() {
		 int nia = ig.eliminarAlumnoPorNia();
		if (nia == -1) {
			ig.mostrarMensaje("Eliminacion cancelada");
		} else {
			int result = daoSql.eliminarAlumnoPorNia(nia);
			if (result == -1) {
				ig.mostrarMensaje("Error de conexion");
			} else if (result == 0) {
				ig.mostrarMensaje("No existe este alumno");
			} else {
				ig.mostrarMensaje(result + " tablas afectadas");
			}
		}

	}
	public void eliminarAlumnoPorApellido() {
	    String apellido = ig.eliminarAlumnoPorApellido();

	    if (apellido == null) {
	        ig.mostrarMensaje("Eliminación cancelada");
	        return;
	    }

	    int result = daoSql.eliminarAlumnoPorApellido(apellido);

	    if (result == -1) {
	        ig.mostrarMensaje("Error de conexión");
	    } else if (result == 0) {
	        ig.mostrarMensaje("No hay alumnos con ese apellido");
	    } else {
	        ig.mostrarMensaje(result + " alumnos eliminados");
	    }
	}


}
