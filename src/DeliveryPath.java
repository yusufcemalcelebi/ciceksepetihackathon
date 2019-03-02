import java.util.ArrayList;

public class DeliveryPath {
	private ArrayList<Point> path;
	
	public DeliveryPath() {
		this.path = new ArrayList<Point>();
	}
	
	public int size() {
		return path.size();
	}
	
	public void addPoint(Point point) {
		path.add(point);
	}
}
