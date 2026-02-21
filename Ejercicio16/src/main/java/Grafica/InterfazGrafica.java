package Grafica;

import java.util.List;

import Model.Alumno;
import Model.Grupo;

public interface InterfazGrafica {
	int mostrarMenu(); 
	void mostrarMensaje(String msg);
	
	 Alumno guardoAlumnoGrafica();
	 Grupo guardoGrupoGrafica();
	 int[] añadoAlumnoAGrupoGrafica();
	 public void muestroAlumnosGrafica(List<Alumno> alumnos, boolean resumido);
	 String[] modificarAlumnoIdGrafica();
	 int eliminarAlumnoPorNia();
	 String eliminarAlumnoPorApellido();
	 int muestroAlumnosAPArtirDeGrupoGrafica();
	 int muestroAlumnoAPartirDeNia();
}
