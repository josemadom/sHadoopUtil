package es.unex.shadoopMongo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import com.opencsv.*;

public class MongoController {

	 
//	    /**
//	     * Main del proyecto.
//	     * @param args
//	     */
//	    public static void main(String[] args) {
//	        MongoController mc = new MongoController();
//	        MongoClient mongo = mc.crearConexion();
//	        DBCursor cur = null;
//	 
//	        if (mongo != null) {
//	 
//	            //Si no existe la base de datos la crea
//	            DB db = mongo.getDB(Constantes.NOMBRE_BASEDATOS);
//	            System.out.println();
//	 
//	            //Listar las tablas de la base de datos actual
////	            System.out.println("Lista de tablas de la base de datos: ");
////	            mc.getColecciones(db);
////	            System.out.println();
//	    		cur = mc.selectDatos(db, "rscat", "2016005");
//	            System.out.println();	
//	            copiarDatoCSV(cur,"wind_speed");
//	        } else {
//	            System.out.println("Error: Conexión no establecida");
//	        }
//	    }
	 	
	/**
	 * Este método se encarga de generar el CSV con los datos leidos desde la BBDD y con el parametro
	 * que se le indica 
	 * 
	 * @param coleccion indica la colección sobre la que vamos a realizar la consulta
	 * @param annoDia indica el anio y día en formato juliana
	 * @param parametroRapid indica que parametro que vamos a obtener de todos los posibles
	 * 
	 * @return devuelve el número de registros que se han insertado en el csv
	 */
	    public int generateCSVfromMongo(String coleccion, String annoDia, String parametroRapid) {
	        MongoController mc = new MongoController();
	        MongoClient mongo = mc.crearConexion();
	        DBCursor cur = null;
	        int numRegistros = 0;
	 
	        if (mongo != null) {
	 
	            //Si no existe la base de datos la crea
	            DB db = mongo.getDB(Constantes.NOMBRE_BASEDATOS);
	    		cur = mc.selectDatos(db, coleccion, annoDia);
	    		numRegistros = cur.count();
	            copiarDatoCSV(cur,parametroRapid);
	        } else {
	            System.out.println("Error: Conexión no establecida");
	        }
	        return numRegistros;
	    }
	    
//	    /**
//	     * Esta función devolverá el número de registros que tiene la BBDD de cada uno de los días
//	     * 
//	     * @param coleccion indica la colección sobre la que vamos a realizar la consulta
//	     * @param annoDia indica el anio y día en formato juliana
//	     * 
//	     * @return devuelve una lista con los datos de cada unos de los días
//	     */
//	    public List<Datos> getDatosPorDia(String coleccion, String annoDia) {
//	        MongoController mc = new MongoController();
//	        MongoClient mongo = mc.crearConexion();
//	        DBCursor cur = null;
//	        List<Datos> registros = new ArrayList<Datos>();
//	 
//	        if (mongo != null) {
//	 
//	            //Si no existe la base de datos la crea
//	            DB db = mongo.getDB(Constantes.NOMBRE_BASEDATOS);
//	    		cur = mc.selectDatos(db, coleccion);
//	    		numRegistros = cur.count();
//	            copiarDatoCSV(cur,parametroRapid);
//	        } else {
//	            System.out.println("Error: Conexión no establecida");
//	        }
//	        return registros;
//	    }	    

		/**
	     * Ejemplo para crear una conexión a MongoDB.
	     * @return MongoClient conexión
	     */
	    public MongoClient crearConexion() {
	        MongoClient mongo = null;
	        try {
	            mongo = new MongoClient(Constantes.IP_BASEDATOS, Constantes.PUERTO_BASEDATOS);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mongo;
	    }
	 
	 
	    /**
	     * Ejemplo que imprime por pantalla todas las colecciones / tablas de una base de datos.
	     * @param db de tipo DB
	     */
	    public Set<String> getColecciones(DB db) {
	        Set<String> tables = db.getCollectionNames();
	 
	        for(String coleccion : tables){
	            System.out.println(" - " + coleccion);
	        }
	        return tables;
	    }
	 
	    /**
	     * Ejemplo que inserta un registro dado un DB, nombre de tabla y campos de la tabla (id, nombre, apellidos y años).
	     * @param db
	     * @param tabla
	     * @param id
	     * @param nombre
	     * @param apellidos
	     * @param anyos
	     */
	    public void insertTrabajador(DB db, String tabla, String nombre, String apellidos, int anyos) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	 
	        //Crea un objecto básico y le añade elementos
	        BasicDBObject document = new BasicDBObject();
	        document.put("nombre", nombre);
	        document.put("apellidos", apellidos);
	        document.put("anyos", anyos);
	        document.put("fecha", new Date());
	 
	        //Inserta la tabla
	        table.insert(document);
	    }
	 
	    /**
	     * Ejemplo que modifica el campo anyos dado una DB, tabla e id de usuario.
	     * Hay varias formas de actualizar los registros, y ésta es una de ella.
	     * Otros tipos de actualización de registros aquí: http://www.mkyong.com/mongodb/java-mongodb-update-document/
	     *
	     * @param db
	     * @param tabla
	     * @param id
	     * @param nuevosAnyos
	     */
	    public void updateNombreTrabajador(DB db, String tabla, String nombre, int nuevosAnyos) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	 
	        //Prepara para insertar un nuevo campo
	        BasicDBObject updateAnyos = new BasicDBObject();
	        updateAnyos.append("$set", new BasicDBObject().append("anyos", nuevosAnyos));
	 
	        //Busca el/los registro/s con el nombre indicado
	        BasicDBObject searchById = new BasicDBObject();
	        searchById.append("nombre", nombre);
	 
	        //Realiza la actualización
	        table.updateMulti(searchById, updateAnyos);
	    }
	 
	    /**
	     * devuelve un cursor con todos los datos de la tabla que se pasa como parametro
	     * @param db
	     * @param tabla
	     */
	    public DBCursor selectDatos(DB db, String tabla, String fecha) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	        BasicDBObject whereQuery = new BasicDBObject();
	        whereQuery.put("time", Integer.parseInt(fecha));
	 
	        //Busca y muestra todos los datos de la tabla
	        DBCursor cur = table.find(whereQuery);
	        System.out.println("total resultados: " + cur.count());
//	        while (cur.hasNext()) {
//	            System.out.println(" - " + cur.next().get("time") + " - " + cur.next().get("loc") + " - ");
//	        }
	        return cur;
	    }
	    
	    /**
	     * Devuelve un cursor todos los datos de la tabla que se pasa como parametro
	     * @param db
	     * @param tabla
	     */
	    public DBCursor selectDatos(DB db, String tabla) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);

	        // for the $group operator
	        // note - the collection still has the field name "dolaznaStr"
	        // but, to we access "dolaznaStr" in the aggregation command, 
	        // we add a $ sign in the BasicDBObject 

//	        DBObject groupFields = new BasicDBObject
//
//	        // we use the $sum operator to increment the "count" 
//	        // for each unique dolaznaStr 
//	        groupFields.put("count", new BasicDBObject( "$sum", 1));
//	        DBObject group = new BasicDBObject("$group", groupFields );
//
//
//	        AggregationOutput output = table.aggregate(group, sort);
//
//	        System.out.println( output.getCommandResult() );	        
	        
	        
	        
	        
	        BasicDBObject whereQuery = new BasicDBObject();
	        whereQuery.put("time", Integer.parseInt("111"));
	 
	        //Busca y muestra todos los datos de la tabla
	        DBCursor cur = table.find(whereQuery);
	        System.out.println("total resultados: " + cur.count());
//	        while (cur.hasNext()) {
//	            System.out.println(" - " + cur.next().get("time") + " - " + cur.next().get("loc") + " - ");
//	        }
	        return cur;
	    }	    
	 
	    /**
	     * Ejemplo que imprime por pantalla todos los trabajadores con nombre indicado
	     * @param db
	     * @param tabla
	     * @param nombre
	     */
	    public void selectTablasWhere(DB db, String tabla, String nombre) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	 
	        //Creando el filtro en el campo "nombre"
	        BasicDBObject query = new BasicDBObject();
	        query.put("nombre", nombre);
	 
	        //Busca y muestra todos los datos de la tabla donde "nombre" sea el indicado
	        DBCursor cur = table.find(query);
	        while (cur.hasNext()) {
	            System.out.println(" - " + cur.next().get("nombre") + " " + cur.curr().get("apellidos") + " -- " + cur.curr().get("anyos") + " años (" + cur.curr().get("fecha") + ")");
	        }
	    }
	 
	    /**
	     * Ejemplo que elimina los trabajadores con nombre indicado
	     * @param db
	     * @param tabla
	     * @param nombre
	     */
	    public void deleteTrabajadorPorNombre(DB db, String tabla, String nombre) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	 
	        //Indica el campo y valor y lo elimina
	        table.remove(new BasicDBObject().append("nombre", nombre));
	    }
	 
	    /**
	     * Ejemplo que elimina los trabajadores con una edad mayor a la indicada
	     * @param db
	     * @param tabla
	     * @param anyos
	     */
	    public void deleteTrabajadorEdadMayorQue(DB db, String tabla, int anyos) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	 
	        //Indica el campo y valor que ha de ser mayor para eliminarlo
	        BasicDBObject query = new BasicDBObject();
	        query.put("anyos", new BasicDBObject("$gt", anyos));
	        table.remove(query);
	    }
	 
	    /**
	     * Ejemplo que elimina los trabajadores cuyos apellidos estén en una lista
	     * @param db
	     * @param tabla
	     * @param lista
	     */
	    private static void deleteTrabajadorEnLista(DB db, String tabla, List lista) {
	        //Recoge datos de la tabla
	        DBCollection table = db.getCollection(tabla);
	 
	        //Indica la lista de nombres que quiere eliminar
	        BasicDBObject query = new BasicDBObject();
	        query.put("apellidos", new BasicDBObject("$in", lista));
	        table.remove(query);
	    }
	    
	    /**	
	     * Esta funcion se encarga de copiar todos los datos a csv
	     * @param cur
	     */
	    private static void copiarDatoCSV(DBCursor cur, String datoAImportar) {
	        String outputFile = "D:/varios/csv/rscat"+ getCurrentTimeStamp() +".csv";
	        boolean alreadyExists = new File(outputFile).exists();
	         
	        if(alreadyExists){
	            File rScat = new File(outputFile);
	            rScat.delete();
	        }        
	         
	        try {
	 
	            BufferedWriter bfOutput = new BufferedWriter(new FileWriter(outputFile, true));
	            String nuevaLinea = System.getProperty("line.separator");
	            StringBuffer linea = new StringBuffer();
	            DBObject dbObj = null;
		        while (cur.hasNext()) {
		        	//System.out.println(" - " + cur.next().get("time") + " - " + cur.next().get("loc") + " - ");
		        	dbObj = cur.next();
		        	BasicDBObject location = (BasicDBObject) dbObj.get("loc");
		        	linea.append(Double.toString((Double)((BasicDBList) (location.get("coordinates"))).get(0)));
		        	linea.append(Constantes.CSV_SEPARATOR);
		        	linea.append(Double.toString((Double)((BasicDBList) (location.get("coordinates"))).get(1)));
		        	linea.append(Constantes.CSV_SEPARATOR);		        	
		        	linea.append(Double.toString((Double)dbObj.get(datoAImportar)));
		        	linea.append(nuevaLinea);
		        	bfOutput.write(linea.toString());
		        	//Ponemos en 0 el cursor del buffer
		        	linea.setLength(0);
		        }	            
	            
		        bfOutput.close();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}	

	    public static String getCurrentTimeStamp() {
	        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date now = new Date();
	        String strDate = sdfDate.format(now);
	        return strDate;
	    }
}
