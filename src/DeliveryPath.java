import java.util.ArrayList;

public class DeliveryPath {
	private ArrayList<String> path;
	
	public DeliveryPath() {
		this.path = new ArrayList<String>();
	}
	
	public int size() {
		return path.size();
	}
	
	public void addPoint(String key) {
		path.add(key);
	}
	
	public ArrayList<String> getPath() {
		return path;
	}
}
