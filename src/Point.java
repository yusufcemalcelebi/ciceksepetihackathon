
public class Point {
	private Double coordinateX;
	private Double coordinateY;
	public String clusterId;
	
	public Point(Double x, Double y) {
		this.coordinateX = x;
		this.coordinateY = y;
		
		this.clusterId = "none";
	}

	public boolean isAssignedToCluster() {
		return !this.clusterId.equals("none");
	}
	
	public Double getCoordinateX() {
		return coordinateX;
	}
	

	public void setCoordinateX(Double coordinateX) {
		this.coordinateX = coordinateX;
	}

	public Double getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(Double coordinateY) {
		this.coordinateY = coordinateY;
	}
}
