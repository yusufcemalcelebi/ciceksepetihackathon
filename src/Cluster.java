import java.util.ArrayList;

public class Cluster {
	private String clusterId;
	private Point centroid;
	private Point currentPoint;
	private ArrayList<DeliveryPath> deliveryPaths;
	private int pathSize;
	public int minLimit;
	public int maxLimit;
	
	public Cluster(String clusterId, Point centroid, int pathSize, int minLimit, int maxLimit) {
		this.clusterId = clusterId;
		this.centroid = centroid;
		this.pathSize = pathSize;
		this.minLimit = minLimit;
		this.maxLimit = maxLimit;
		
		this.deliveryPaths = new ArrayList<DeliveryPath>();
	}
	
	public void addPoint(Point point) {
		int sizeOfPaths = deliveryPaths.size();
		DeliveryPath path;
		DeliveryPath lastPathOfCluster;
		
		if(sizeOfPaths == 0) {
			path = new DeliveryPath();
			path.addPoint(point);
			
			deliveryPaths.add(path);
		} else {
			lastPathOfCluster = deliveryPaths.get(sizeOfPaths - 1);
			
			if(lastPathOfCluster.size() == pathSize) {
				path = new DeliveryPath();
				path.addPoint(point);
				
				deliveryPaths.add(path);
			} else {
				deliveryPaths.get(sizeOfPaths - 1).addPoint(point);
			}
		}
	}
	
	public String getClusterId() {
		return clusterId;
	}

	public Point getCentroid() {
		return centroid;
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}
}
