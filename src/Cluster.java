import java.util.ArrayList;

public class Cluster {
	private String clusterId; /* Store id */
	private Point centroid; /* Store coordinates */
	private Point currentPoint; /* We will use this point when we calculated
	 distance between cluster and delivery points*/
	private ArrayList<DeliveryPath> deliveryPaths;
	private int pathSizeLimit;
	public int minLimit;
	public int maxLimit;
	
	public ArrayList<DeliveryPath> getDeliveryPaths() {
		return deliveryPaths;
	}

	public void setDeliveryPaths(ArrayList<DeliveryPath> deliveryPaths) {
		this.deliveryPaths = deliveryPaths;
	}

	public int deliveryCount = 0;
	
	public Cluster(String clusterId, Point centroid, int pathSize, int minLimit, int maxLimit) {
		this.clusterId = clusterId;
		this.centroid = centroid;
		this.pathSizeLimit = pathSize;
		this.minLimit = minLimit;
		this.maxLimit = maxLimit;
		
		this.currentPoint = centroid;
		this.deliveryPaths = new ArrayList<DeliveryPath>();
	}
	
	public void addPoint(String key, Point point) {
		int sizeOfPaths = deliveryPaths.size();
		DeliveryPath path;
		
		if(sizeOfPaths == 0) {
			path = new DeliveryPath();
			path.addPoint(key);
			
			deliveryPaths.add(path);
		} else {			
			if(isLastPathFull()) {
				path = new DeliveryPath();
				path.addPoint(key);
				
				deliveryPaths.add(path);
			} else {
				deliveryPaths.get(sizeOfPaths - 1).addPoint(key);
			}
		}
		
		if(isLastPathFull()) {
			setCurrentPoint(this.centroid);
		} else {
			setCurrentPoint(point);
		}
		
		deliveryCount++;
	}
	
	public boolean isLastPathFull() {
		int sizeOfPaths = deliveryPaths.size();
		DeliveryPath lastPathOfCluster;
		
		lastPathOfCluster = deliveryPaths.get(sizeOfPaths - 1);
		
		return (lastPathOfCluster.size() == this.pathSizeLimit);
	}
	
	public boolean isThereEnoughCapacity() {
		return this.maxLimit > this.deliveryCount;
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
