
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.*;


import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class novoTeste {

	public static Map<String, Object> jsonToMap (String str){
			Map<String, Object> map = new Gson().fromJson(
					str, new TypeToken<HashMap<String, Object>>() {}.getType());
			return map;
		} 
	
	public static void main(String[] args) throws IOException {
		

		URL api = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Joinville,br&units=metric&APPID=82005d27a116c2880c8f0fcb866998a0");
		BufferedReader in = new BufferedReader(new InputStreamReader(api.openStream()));
		String apiWeatherJson = in.readLine();
		JSONParser parser = new JSONParser(); 
		FileWriter writeFile =new FileWriter("JlleJson.json");
		JSONArray arrayJson = new JSONArray();
		try
		{ 
			//System.out.println("Reading JSON file from Java program");  
			JSONObject json = (JSONObject) parser.parse(apiWeatherJson); 
			JSONArray lstJson = (JSONArray) json.get("list");
			//System.out.println(lstJson);
			Iterator i = lstJson.iterator();
			int contador = 0;
			int cont = 1;
			
			JSONObject testeJson = new JSONObject();
			while(i.hasNext()) {

				JSONObject jsnObj = (JSONObject) i.next();
				ArrayList<Map<String, String>> weathers = (ArrayList<Map<String, String>>) jsnObj.get("weather");
				Map<String, Double> main = (Map<String, Double>) jsnObj.get("main");
				contador++;
				
//				objetoJson.put("temp",main.get("temp"));
//				objetoJson.put("Data", jsnObj.get("dt_txt"));
//				objetoJson.put("Descrição",weathers.get(0).get("description"));
				
				//Map<String, Object> climaMap = new HashMap<String, Object>();
				Map<String, Object> climaMap2 = new HashMap<String, Object>();
				//Map<String, Object> climaMap3 = new HashMap<String, Object>();
				climaMap2 .put("icon",weathers.get(0).get("icon"));
				climaMap2 .put("temp",main.get("temp"));
				climaMap2 .put("Data", jsnObj.get("dt_txt"));
				climaMap2 .put("Descrição",weathers.get(0).get("description"));
							
				if(cont==contador) {				
				//Gson aa = new Gson();
				// resposta += aa.toJson(climaMap)+aa.toJson(climaMap2)+aa.toJson(climaMap3);
					//arrayJson.add(climaMap);
					arrayJson.add(climaMap2);
				//	arrayJson.add(climaMap3);
					cont=contador+8;					
				}				
			}
			System.out.println(arrayJson);
			writeFile.write(arrayJson.toJSONString());
			writeFile.flush();
			writeFile.close();
		} 
		
		catch (Exception ex) { ex.printStackTrace(); }
		
	
	}


}