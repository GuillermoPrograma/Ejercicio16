package Main;

import java.util.ArrayList;
import java.util.List;

public class Grupo {

	private static final long serialVersionUID = 123L;
	private int id;
	private String nombre;
	private List<Alumno> alumnos;

	public Grupo(int idGrupo, String nombreGrupo) {
		this.id = idGrupo;
		this.nombre = nombreGrupo;
		this.alumnos = new ArrayList<>();
	}

	public void agregarAlumno(Alumno alumno) {
		if (!alumnos.contains(alumno)) {
			alumnos.add(alumno);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
