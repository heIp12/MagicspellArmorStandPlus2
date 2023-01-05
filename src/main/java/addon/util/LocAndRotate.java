package addon.util;

import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import addon.types.ItemLoc;
import addon.types.MoveType;

public class LocAndRotate {
	Location loc;
	Location loc_only;
	boolean isQuaternion = false;
	
	Vector rotate = new Vector(0,0,0); // x = pitch y = yaw z = roll
	ItemLoc itemloc;
	
	public LocAndRotate(Location loc,ItemLoc itemloc){
		setLocation(loc);
		this.itemloc = itemloc;
	}
	
	public LocAndRotate(Location loc,Vector vtr,ItemLoc itemloc){
		loc.setYaw((float) vtr.getY());
		setLocation(loc);
		setRotate(vtr);
		this.itemloc = itemloc;
	}
	
	
	public Location getLocation() {
		Location loc2 = loc.clone();
		loc2.setPitch((float) rotate.getX());
		return loc2;
	}
	
	public Vector getRotate() {
		Vector vtr = rotate.clone();
		vtr.setY(loc.getYaw());
		return vtr;
	}
	
	public ItemLoc getItemloc() {
		return itemloc;
	}
	
	public void setLocation(Location loc) {
		rotate.setX(loc.getPitch());
		this.loc = loc;
		this.loc_only = loc;
	}
	
	public void setQuater(boolean isQuaternion) {
		this.isQuaternion = isQuaternion;
	}
	
	public void setRotate(Vector vtr) {
		loc.setYaw((float) vtr.getY());
		rotate = vtr;
	}
	
	public void setLoc(Location loc, Vector vtr, MoveType move) {
		if(move == MoveType.ALL || move == MoveType.MOVE){
			setLocation(loc);
		}
		if(move == MoveType.ALL || move == MoveType.ROTATE){
			setRotate(vtr);
		}
	}
}
