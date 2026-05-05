// Paquete base donde vive la clase principal de Spring Boot.
package com.crud.users_crud;

// Importa el launcher de Spring Boot para arrancar el contexto.
import org.springframework.boot.SpringApplication;
// Importa la anotacion que activa auto-configuracion y escaneo de componentes.
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta clase como punto de arranque de Spring Boot (configuracion + escaneo + auto-config).
@SpringBootApplication
public class UsersCrudApplication {

	// Metodo main: punto de entrada de la JVM.
	public static void main(String[] args) {
		// Arranca la aplicacion y crea el ApplicationContext con todos los beans.
		SpringApplication.run(UsersCrudApplication.class, args);
	}

}
