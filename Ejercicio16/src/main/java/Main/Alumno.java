package Main;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alumno implements Serializable {

	private static final long serialVersionUID = 123L;

	private int nia;

	private char genero;

	private String nombre, apellidos, ciclo, curso;

	private LocalDate fecha;

	private Grupo grupo; //creo que el 12 le tengo mal ya que tiene sentido que un alumno solo esté en un grupo y no en varios

	public Alumno(int nia, String nombre, String apellidos, char genero, LocalDate fecha_Nac, String ciclo,
			String curso)

	{
		this.fecha = fecha_Nac;
		this.nia = nia;
		this.nombre = nombre;
		this.genero = genero;
		this.apellidos = apellidos;
		this.ciclo = ciclo;
		this.curso = curso;

	}

	public void agregarGrupo(Grupo g) {
		this.grupo = g;
	}

	

	public int getNia() {
		return nia;
	}

	public void setNia(int nia) {
		this.nia = nia;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	

	public Grupo getGrupo() {
		return grupo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "alumno [nia=" + nia + ", genero=" + genero + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", ciclo=" + ciclo + ", curso=" + curso + ", grupo= " + grupo + " fecha=" + fecha + "]";
	}

}
