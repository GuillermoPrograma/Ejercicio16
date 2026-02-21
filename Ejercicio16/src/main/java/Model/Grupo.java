package Model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos")
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    
    @OneToMany(mappedBy = "grupo")
    private List<Alumno> alumnos = new ArrayList<>();

   
    public Grupo() {}

    public Grupo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    
    public void agregarAlumno(Alumno alumno) {
        alumnos.add(alumno);
        alumno.setGrupo(this);
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Alumno> getAlumnos() { return alumnos; }
    public void setAlumnos(List<Alumno> alumnos) { this.alumnos = alumnos; }
}