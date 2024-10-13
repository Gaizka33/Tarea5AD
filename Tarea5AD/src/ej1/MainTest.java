package ej1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainTest {

	private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static File testFile;

	@BeforeAll
	public static void setup() {
		// Creación de un archivo temporal para las pruebas
		testFile = new File("testFile.dat");
	}

	@AfterAll
	public static void cleanup() {
		// Eliminar el archivo después de la prueba
		if (testFile.exists()) {
			testFile.delete();
		}
	}

	@Test
	public void testCrearAlumno() {
		// Simulamos la entrada del usuario con Scanner
		String input = "01-01-2000\nJuan\nPerez\nInformatica\n1\nA\n12345\nM\n";
		Scanner scanner = new Scanner(input);

		// Invocamos al método que queremos probar
		Alumno alumno = Main.crearAlumno(scanner);

		// Verificamos que el alumno se ha creado correctamente
		assertEquals("Juan", alumno.getNombre());
		assertEquals("Perez", alumno.getApellidos());
		assertEquals("Informatica", alumno.getCiclo());
		assertEquals("1", alumno.getCurso());
		assertEquals("A", alumno.getGrupo());
		assertEquals(12345, alumno.getNia());
		assertEquals('M', alumno.getGenero());
		assertEquals(LocalDate.parse("01-01-2000", formato), alumno.getFechadenacimiento());

		scanner.close();
	}

	@Test
	public void testEscrituraYLecturaArchivo() throws IOException, ClassNotFoundException {
		// Creamos un archivo temporal para escribir/leer
		FileOutputStream fos = new FileOutputStream(testFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		// Creamos un alumno de prueba y lo escribimos en el archivo
		Alumno alumno = new Alumno("Ana", "Garcia", "Informatica", "2", "B", 54321, 'F',LocalDate.parse("15-06-1999", formato));
		oos.writeObject(alumno);
		oos.close();

		// Leemos el archivo y verificamos el contenido
		FileInputStream fis = new FileInputStream(testFile);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Alumno alumnoLeido = (Alumno) ois.readObject();
		assertEquals(alumno.getNombre(), alumnoLeido.getNombre());
		assertEquals(alumno.getApellidos(), alumnoLeido.getApellidos());
		assertEquals(alumno.getCiclo(), alumnoLeido.getCiclo());
		assertEquals(alumno.getCurso(), alumnoLeido.getCurso());
		assertEquals(alumno.getGrupo(), alumnoLeido.getGrupo());
		assertEquals(alumno.getNia(), alumnoLeido.getNia());
		assertEquals(alumno.getGenero(), alumnoLeido.getGenero());
		assertEquals(alumno.getFechadenacimiento(), alumnoLeido.getFechadenacimiento());

		ois.close();
	}

}
