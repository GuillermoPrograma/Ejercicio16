package Dao;

import java.util.List;

import Main.Alumno;
import Main.Grupo;



public interface Dao {
	
	
	int insertoAlumno(Alumno a);
	int insertoGrupo(Grupo g);
	int añadoAlumnoAGrupo(int nia, int id);
	int eliminoAlumnoPorApellido(String apellido);
	int eliminoAlumnoPorNia(int nia);
	int ModificoNombrePorNia(String nombre,int nia);
	void InsertoListaGrupos(List <Grupo> muestrolista);
	List<Alumno> muestroAlumnos();
	

}
