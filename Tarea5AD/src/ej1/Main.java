package ej1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
	private static File ficherosolicitado;
	private static FileOutputStream conexionescribir;
	private static FileInputStream conexionleer;
	private static ObjectOutputStream escribir;
	private static ObjectInputStream leer;
	private static Scanner abielto = new Scanner(System.in);
	private static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {

		try {
			System.out.println("Cual es el fichero que quieres leer?");
			ficherosolicitado = new File(abielto.next());
			conexionescribir = new FileOutputStream(ficherosolicitado);
			escribir = new ObjectOutputStream(conexionescribir);
			for (int i = 0; i < 5; i++) {
				// Solicita la fecha de nacimiento del alumno
				System.out.println("Dame la fecha de nacimiento del alumno (dd-MM-yyyy): ");
				String fechaNacimientoString = abielto.next();
				LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoString, formato); // Parsea la fecha

				// Solicita los datos del alumno
				System.out.println(
						"Ahora Dame los datos del alumno (Nombre, Apellidos, Ciclo, Curso, Grupo, NIA y Genero): ");

				String nombre = abielto.next(); // Nombre del alumno
				String apellidos = abielto.next(); // Apellidos del alumno
				String ciclo = abielto.next(); // Ciclo que cursa
				String curso = abielto.next(); // Curso actual
				String grupo = abielto.next(); // Grupo al que pertenece
				int nia = abielto.nextInt(); // Número de identificación del alumno
				char genero = abielto.next().charAt(0); // Género del alumno

				// Crea un objeto Alumno con los datos recogidos
				Alumno alumno = new Alumno(nombre, apellidos, ciclo, curso, grupo, nia, genero, fechaNacimiento);
				escribir.writeObject(alumno);
			}
			abielto.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Cierra el DataOutputStream
			try {
				if (escribir != null) {
					escribir.close();
				}
			} catch (Exception e) {
				e.printStackTrace(); // Imprime la traza de la excepción al cerrar
			}
		}

		try {
			System.out.println("Ahora vamos a leer lo que pone en el archivo");
			conexionleer = new FileInputStream(ficherosolicitado);
			leer = new ObjectInputStream(conexionleer);

			while (conexionleer.available() > 0) {
				Alumno a = (Alumno) leer.readObject();
				System.out.println("Los alumnos son: /n" + a.toString() + "/n");
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Cierra el DataOutputStream
			try {
				if (leer != null) {
					leer.close();
				}
			} catch (Exception e) {
				e.printStackTrace(); // Imprime la traza de la excepción al cerrar
			}
		}

	}

}
