package Controlador;

import Dao.Dao;
import Grafica.InterfazGrafica;

public class ControladorHibernate {
	private Dao daoHibernate;
	private InterfazGrafica ig;

	public ControladorHibernate(Dao daoHibernate, InterfazGrafica ig) {
		this.daoHibernate = daoHibernate;

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
				mostrarAlumnosAPartirDeGrupo();
				break;
			case 6:
				mostrarAlumnoAPartirDeNia();
				break;
			case 7:
				modificarNombreAPartirDeNia();
				break;
			case 8:
				eliminarAlumnoPorNia();
				break;
			case 9:
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
		int result = daoHibernate.guardarAlumnoBd(ig.guardoAlumnoGrafica());
		if (result == -1) {
			ig.mostrarMensaje("Error de conexion");
		} else if (result == 0) {
			ig.mostrarMensaje("No se puede guardar este grupo");
		} else {
			ig.mostrarMensaje(result + " tablas afectadas");
		}
	}

	public void guardoGrupo() {
		int result = daoHibernate.guardarGrupo(ig.guardoGrupoGrafica());
		if (result == -1) {
			ig.mostrarMensaje("Error de conexion");
		} else if (result == 0) {
			ig.mostrarMensaje("No se puede guardar este grupo");
		} else {
			ig.mostrarMensaje(result + " tablas afectadas");
		}
	}

	public void aniadirAlumnoAGrupoPorNia() {
		ig.muestroAlumnosGrafica(daoHibernate.mostrarAlumnos(),true);
		int[] nia_grupo = ig.añadoAlumnoAGrupoGrafica();
		int nia = nia_grupo[0];
		int id = nia_grupo[1];
		int result = daoHibernate.añadirAlumnoAGrupoPorNia(nia, id);
		if (result == -1) {
			ig.mostrarMensaje("Error de conexion");
		} else if (result == 0) {
			ig.mostrarMensaje("No se puede guardar este grupo");
		} else {
			ig.mostrarMensaje(result + " tablas afectadas");
		}
	}

	public void mostrarAlumnos() {
		ig.muestroAlumnosGrafica(daoHibernate.mostrarAlumnos(),false); //no resumido
	}

	public void mostrarAlumnosAPartirDeGrupo() {
		ig.muestroAlumnosGrafica(daoHibernate.mostrarAlumnos(),true);
		ig.muestroAlumnosGrafica(daoHibernate.mostrarAlumnosPorGrupo(ig.muestroAlumnosAPArtirDeGrupoGrafica()),false);//no resumido
	}

	public void modificarNombreAPartirDeNia() {
		String[] nombre_nia = ig.modificarAlumnoIdGrafica();
		int nia = Integer.parseInt(nombre_nia[0]); // me he asegurado de que vaya a salir bien en la vistaControlador
		String nombre = nombre_nia[1];
		daoHibernate.modificarNombreAPartirDeNiaBD(nombre, nia);
	}

	public void eliminarAlumnoPorNia() {
		int nia = ig.eliminarAlumnoPorNia();
		if (nia == -1) {
			ig.mostrarMensaje("Eliminacion cancelada");
		} else {
			int result = daoHibernate.eliminarAlumnoPorNia(nia);
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

		int result = daoHibernate.eliminarAlumnoPorApellido(apellido);

		if (result == -1) {
			ig.mostrarMensaje("Error de conexión");
		} else if (result == 0) {
			ig.mostrarMensaje("No hay alumnos con ese apellido");
		} else {
			ig.mostrarMensaje(result + " alumnos eliminados");
		}
	}
	public void mostrarAlumnoAPartirDeNia() 
	{
	ig.muestroAlumnosGrafica(daoHibernate.mostrarUnAlumnoNia(ig.muestroAlumnoAPartirDeNia()), false);
	}

}
