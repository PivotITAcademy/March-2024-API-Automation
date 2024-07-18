package utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonUtils {

	
	public static Map<String,Object> getJsonDataFromFile(String filePath) throws StreamReadException, DatabindException, IOException {
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		 Map<String,Object> data = mapper.readValue(new File(filePath),HashMap.class);
		
		 return data;
	}

}
