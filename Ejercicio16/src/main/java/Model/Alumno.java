package Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "alumnos")
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nia")
    private int nia;

    @Column(name = "genero")
    private char genero;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "ciclo")
    private String ciclo;

    @Column(name = "curso")
    private String curso;

    @Column(name = "fecha_nacimiento")
    private LocalDate fecha;

 
    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    
    public Alumno() {}

    public Alumno(int nia, String nombre, String apellidos, char genero,
                  LocalDate fecha, String ciclo, String curso) {
        this.nia = nia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fecha = fecha;
        this.ciclo = ciclo;
        this.curso = curso;
    }

    // getters y setters
    public int getNia() { return nia; }
    public void setNia(int nia) { this.nia = nia; }

    public char getGenero() { return genero; }
    public void setGenero(char genero) { this.genero = genero; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCiclo() { return ciclo; }
    public void setCiclo(String ciclo) { this.ciclo = ciclo; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

   
}
