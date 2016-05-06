package es.unex.shadoopMongo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@RestController
@RequestMapping(value = "/datos")
public class DatosController {

	private static final List<Datos> DUMMY_DIAS = new ArrayList<Datos>();
	
	static {
		DUMMY_DIAS.add(new Datos("rscat", 2016005, 2016005, "", 1300000));
		DUMMY_DIAS.add(new Datos("rscat", 2016006, 2016006, "", 1500000));
		DUMMY_DIAS.add(new Datos("rscat", 2016007, 2016007, "", 1700000));
		DUMMY_DIAS.add(new Datos("rscat", 2016008, 2016008, "", 1900000));
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public List<Datos> getDatosDia() {
//		System.out.println(" COMENZANDO --------------------------");
//		MongoController mc = new MongoController();
//		mc.generateCSVfromMongo("rscat", "2016005", "wind_speed");
		return DUMMY_DIAS;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void generarCSV(@RequestBody @Valid Datos datosRScat) {
		System.out.println(" COMENZANDO --------GENERAR --> " + datosRScat.getDatoAParsear());		
		System.out.println(" -Fecha Inicio " + datosRScat.getAnnoDiaIni() + " -Fecha Fin " + datosRScat.getAnnoDiaFin());
		Integer annoDiaIni = datosRScat.getAnnoDiaIni();
		Integer annoDiaFin = datosRScat.getAnnoDiaFin();		
		MongoController mc = new MongoController();
		mc.generateCSVfromMongo("rscat", annoDiaIni.toString(), annoDiaFin.toString(), datosRScat.getDatoAParsear());
		System.out.println(" COMENZANDO --------FINALIZADO");		
	}
	
}