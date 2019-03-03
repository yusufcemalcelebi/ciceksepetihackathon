import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	
	private static int[] clusterMinLimits;
	private static int[] clusterMaxLimits;
	private static int deliveryPathSize = 3;
 
	public static void main(String[] args) {
		Map<String, Point> deliveryPoints = readFile("../dataset/deliveryPoints.csv");
		Map<String, Point> stores = readFile("../dataset/stores.csv");
		
		// Create cluster limit array 
		clusterMinLimits = new int[] {20, 35, 20};
		clusterMaxLimits = new int[] {30, 50, 80};
		
		ArrayList<Cluster> clusters = createClusters(stores);
		
		deliveryDistributionToClusters(clusters, deliveryPoints);
		
		System.out.println("Delivery Number : " + deliveryPoints.size());
		System.out.println("Store Number : " + stores.size());
		
		System.out.println(clusters.get(0).getClusterId() + " delivery Count: " + clusters.get(0).deliveryCount);
		System.out.println(clusters.get(1).getClusterId() + " delivery Count: " + clusters.get(1).deliveryCount);
		System.out.println(clusters.get(2).getClusterId() + " delivery Count: " + clusters.get(2).deliveryCount);
		
		writeToTextFile(clusters, deliveryPoints);
		
		/*
		 //Write paths for first cluster
		System.out.println("\nFor " + clusters.get(0).getClusterId());
		 clusters.get(0).getDeliveryPaths().forEach((path -> {
			 ArrayList<String> myPath = path.getPath();
			 
			 Collections.sort(myPath);
			 myPath.forEach(key -> {
				 System.out.println(key);
			 });
		 }));
		 
		System.out.println("\nFor " + clusters.get(1).getClusterId());
		clusters.get(1).getDeliveryPaths().forEach((path -> {
			ArrayList<String> myPath = path.getPath();
				 
			Collections.sort(myPath);
			myPath.forEach(key -> {
				System.out.println(key);
			});
		}));
		
		System.out.println("\nFor " + clusters.get(2).getClusterId());
		clusters.get(2).getDeliveryPaths().forEach((path -> {
			ArrayList<String> myPath = path.getPath();
				 
			Collections.sort(myPath);
			myPath.forEach(key -> {
				System.out.println(key);
			});
		}));
		*/
		
	}
	
	public static Map<String, Point> readFile(String filePath) {
		BufferedReader reader;
		Point point;
		String[] values;
		Map<String, Point> points = new HashMap<String, Point>();
		String id;
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine(); //ignore column names
			
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
	
	public static ArrayList<Cluster> createClusters(Map<String, Point> stores) {
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		AtomicInteger counter = new AtomicInteger(0);
		
		stores.forEach((key, value) -> {
			Cluster cluster = new Cluster(key, value, deliveryPathSize,
					clusterMinLimits[counter.get()] , clusterMaxLimits[counter.get()]);
			
			clusters.add(cluster);
			
			counter.getAndIncrement();
		});
		
		return clusters;
	}
	
	public static void deliveryDistributionToClusters(ArrayList<Cluster> clusters, Map<String,
			Point> deliveryPoints) {
		
		AtomicInteger counter = new AtomicInteger(0);
		
		while(counter.get() < deliveryPoints.size()) {
			clusters.forEach((cluster) -> {
				if (cluster.isThereEnoughCapacity()) {
					String closestDeliveryKey = findClosestDeliveryPoint(cluster, deliveryPoints);
					Point deliveryPoint = deliveryPoints.get(closestDeliveryKey);
					
					deliveryPoint.clusterId = cluster.getClusterId();
					cluster.addPoint(closestDeliveryKey, deliveryPoint);
					
					counter.incrementAndGet();
				}
			});
		}
	}
	
	private static String findClosestDeliveryPoint(Cluster cluster, Map<String, Point> deliveryPoints) {
		double minDistance = 99999999999.99;
		String minDistanceKey = "0";
		double currentDistance;
		
		for (Map.Entry<String, Point> entry : deliveryPoints.entrySet()){
		    currentDistance = calculateDistance(cluster.getCurrentPoint(), entry.getValue());
		    
		    if(currentDistance < minDistance && !entry.getValue().isAssignedToCluster()) {
		    	
		    	minDistance = currentDistance;
		    	
		    	minDistanceKey = entry.getKey();
		    }
		}
		
		return minDistanceKey;
	}

	public static double calculateDistance(Point a, Point b) {
		double x1 = a.getCoordinateX();
		double y1 = a.getCoordinateY();
		double x2 = b.getCoordinateX();
		double y2 = b.getCoordinateY();
	   
	    return Math.hypot(Math.abs(y2 - y1), Math.abs(x2 - x1));
	}
	
	public static void writeToTextFile(ArrayList<Cluster> clusters, Map<String, Point> deliveryPoints) {
		Map<String, String> dictionary = new HashMap<>();
	    
	    dictionary.put("k?rm?z?", "RED");
	    dictionary.put("ye?il", "GREEN");
	    dictionary.put("mavi", "BLUE");
	    
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
			
			clusters.forEach(cluster -> {
				cluster.getDeliveryPaths().forEach(deliveryPath -> {
			   		deliveryPath.getPath().forEach(deliveryPointKey -> {
		    			try {
		    				Point deliveryPoint = deliveryPoints.get(deliveryPointKey);
		    				
							writer.write(deliveryPointKey + "," + deliveryPoint.getCoordinateX() + "," + deliveryPoint.getCoordinateY() + "," + dictionary.get(cluster.getClusterId()));
							writer.newLine();
		    			} catch (IOException e) {
							e.printStackTrace();
						}
			    	});
			    });
			});
		    
		    writer.close();
		    
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	}
}