import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Map<String, Point> deliveryPoints = readFile("../dataset/deliveryPoints.csv");
		Map<String, Point> stores = readFile("../dataset/stores.csv");
		
		System.out.println("Delivery Number : " + deliveryPoints.size());
		System.out.println("Store Number : " + stores.size());
		
		
	}
	
	public static Map<String, Point> readFile(String filePath) {
		BufferedReader reader;
		Point point;
		String[] values;
		Map<String, Point> points = new HashMap<String, Point>();
		String id;
		
		try {
			reader = new BufferedReader(new FileReader(
					filePath));
			String line = reader.readLine();
			
			while ((line = reader.readLine()) != null) {
				values = parseLine(line);
				
				id = values[0].toLowerCase().trim();
				point = new Point(Double.parseDouble(values[1]), Double.parseDouble(values[2]));
				
				points.put(id, point);
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return points;
	}
	
	public static String[] parseLine(String line) {
		String[] values = line.split(",");
		
		return values;
	}
}
