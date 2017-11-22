package ctr;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.MapModel;

@ManagedBean
@ViewScoped
public class AddMarkerView implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static double lat;
    private static double lng;
    private static String location;
    private MapModel emptyModel;
	public static double getLat() {
		return lat;
	}
	public static void setLat(double lat) {
		AddMarkerView.lat = lat;
	}
	public static double getLng() {
		return lng;
	}
	public static void setLng(double lng) {
		AddMarkerView.lng = lng;
	}
	public static String getLocation() {
		return location;
	}
	public static void setLocation(String location) {
		AddMarkerView.location = location;
	}
	public MapModel getEmptyModel() {
		return emptyModel;
	}
	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}
}
