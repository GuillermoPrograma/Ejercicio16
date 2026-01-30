package Dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Main.Alumno;
import Main.Grupo;

public class DaoBinarioimpl implements Dao {
	
	
	public DaoBinarioimpl() {}
	

	static String ruta = "alumnosEjercicio15.dat";

	@Override
	public int insertoAlumno(Alumno a) { // aqui voy metiendo alumno a alumno en un fichero binario
		File archivo = new File(ruta);

		try (FileOutputStream salida = new FileOutputStream(archivo, true);
				ObjectOutputStream salidaDatos = new ObjectOutputStream(salida);) {

			salidaDatos.writeObject(a);
			return 1;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return -1; //hago una especie de resultset a mano(?) no sé si estrá bien pero asi quien lo maneje tiene la misma respuesta
		}

	}

	@Override
	public int insertoGrupo(Grupo g) {
		// TODO: Se creará en otro momento
		return -1;
	}

	@Override
	public int eliminoAlumnoPorApellido(String apellido) {
		// TODO: Se creará en otro momento
		return -1;
	}

	@Override
	public int eliminoAlumnoPorNia(int nia) {
		// TODO: Se creará en otro momento
		return -1;
	}

	@Override
	public int ModificoNombrePorNia(String nombre,int nia) {
		return -1;
		// TODO: Se creará en otro momento

	}

	@Override
	public List<Alumno> muestroAlumnos() { // Aqui leo el binario
		List <Alumno> alumnos = new ArrayList<>();
		try (ObjectInputStream entradaDatos = new ObjectInputStream(new FileInputStream(ruta))) {

			while (true) // Bucle infinito para que salga por el catch, no sé si hay otrta manera
			{
				try {
					Alumno a = (Alumno) entradaDatos.readObject();

					alumnos.add(a);
				}

				catch (EOFException eof) {
					// SALE POR AQUI
					return alumnos;
					
				}
			}
		} catch (Exception e) {
			System.out.println("Archivo no inicializado");
		}
		return alumnos;
	}

	@Override
	public void InsertoListaGrupos(List<Grupo> muestrolista) {
		// TODO: Se creará en otro momento
		
	}

	@Override
	public int añadoAlumnoAGrupo(int nia, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
