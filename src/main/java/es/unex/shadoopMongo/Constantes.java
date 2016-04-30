package es.unex.shadoopMongo;

/**
Colección de constantes a usar dentro de la aplicacion
*/
public class Constantes {

  
	  public static final String NOMBRE_BASEDATOS = "tfg";
	  public static final String IP_BASEDATOS = "localhost";
	  public static final int PUERTO_BASEDATOS = 27017;

	  // PRIVADO //

	  /**
             Se crea el constructor, pero se lanza un error, para que no se puedan crear instancias de esta clase.	  
      */
	  private Constantes(){
	    throw new AssertionError();
	  }
}
