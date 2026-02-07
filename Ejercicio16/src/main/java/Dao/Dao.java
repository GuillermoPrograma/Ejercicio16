package Dao;

import java.util.List;

import Main.Alumno;
import Main.Grupo;



public interface Dao {
	
	
	int guardarAlumnoBd(Alumno a); //GuardarAlumno
	int guardarGrupo(Grupo g);
	int añadirAlumnoAGrupoPorNia(int nia, int id);
	int eliminarAlumnoPorApellido(String apellido);
	int eliminarAlumnoPorNia(int nia);
	int modificarNombreAPartirDeNiaBD(String nombre,int nia);//nombreAlumnoAPArtirDelNia
	void insertarListaGrupo(List <Grupo> muestrolista);
	List<Alumno> mostrarAlumnos();
	List<Alumno> mostrarAlumnosPorGrupo(int num);
	List<Alumno> mostrarUnAlumnoNia(int num);
	

}
