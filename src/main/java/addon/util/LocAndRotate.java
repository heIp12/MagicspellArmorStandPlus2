package addon.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import addon.types.ItemLoc;
import addon.types.MoveType;

public class LocAndRotate {
	Location loc;
	Vector rotate;
	ItemLoc itemloc;
	
	public LocAndRotate(Location loc, Vector v,ItemLoc itemloc){
		this.loc = loc;
		rotate = v;
		rotate.add(new Vector(loc.getPitch(),loc.getYaw(),0));
		this.itemloc = itemloc;
	}
	
	public Location getLocation() {
		return loc.clone();
	}
	public Vector getRotate() {
		return rotate.clone();
	}
	public ItemLoc getItemloc() {
		return itemloc;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	public void setRotate(Vector vtr) {
		this.rotate = vtr;
	}
	
	public void setLoc(Location loc, Vector vtr, MoveType move) {
		if(move == MoveType.ALL || move == MoveType.MOVE){
			loc.setPitch(0);
			loc.setYaw(0);
			this.loc = loc;
		}
		if(move == MoveType.ALL || move == MoveType.ROTATE){
			this.rotate = vtr;
		}
	}
}
