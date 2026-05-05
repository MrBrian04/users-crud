// Paquete de tests para la aplicacion.
package com.crud.users_crud;

// Importa la anotacion de test de JUnit 5.
import org.junit.jupiter.api.Test;
// Importa la anotacion que levanta el contexto de Spring Boot.
import org.springframework.boot.test.context.SpringBootTest;

// Arranca el contexto completo para verificar que la app carga.
@SpringBootTest
class UsersCrudApplicationTests {

	// Test vacio que solo verifica que el contexto inicia sin errores.
	@Test
	void contextLoads() {
	}

}
