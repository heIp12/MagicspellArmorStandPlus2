package addon.util;

import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class StandLoc {
	public static EulerAngle toEulerAngle(Vector vetor) {
		EulerAngle ea = new EulerAngle(Math.toRadians(vetor.getX()), Math.toRadians(vetor.getY()), Math.toRadians(vetor.getZ()));
		return ea;
	}
	
	public static Vector toVector(EulerAngle eulerAngle) {
		Vector vt = new Vector(Math.toDegrees(eulerAngle.getX()),Math.toDegrees(eulerAngle.getY()),Math.toDegrees(eulerAngle.getZ()));
		return vt;
	}
	
	public static Location getRelativeOffset(Location loc,Vector v) {
    	loc = loc.clone();
    	v = v.clone();
    	float startXOffset = (float) v.getX();
		float startYOffset = (float) v.getY();
		float startZOffset = (float) v.getZ();
		Vector startDirection = loc.getDirection().normalize();
		Vector horizOffset = new Vector(-startDirection.getZ(), 0.0, startDirection.getX()).normalize();
		loc.add(horizOffset.multiply(startZOffset)).getBlock().getLocation();
		loc.add(loc.getDirection().multiply(startXOffset));
		loc.setY(loc.getY() + startYOffset);

    	return loc;
	}
	
	public static Location lookAt(Location loc,Location lookat,double p) {
		Location floc = loc.clone();
		
		Vector vt = lookat.toVector().subtract(loc.toVector());
		loc = loc.setDirection(vt);
		double yaws = loc.getYaw() - floc.getYaw();
		double pitch = loc.getPitch() - floc.getPitch();
		if(p < 1) {
			double maxrotate = 360.0*p;
			for(;(yaws >= 180 || yaws <= -180);) {
				if(yaws >= 180) {
					yaws -= 180;
					yaws *= -1;
				}
				if(yaws <= -180) {
					yaws += 180;
					yaws *= -1;
				}
			}
			if(yaws > maxrotate) yaws = maxrotate;
			if(yaws < -maxrotate) yaws = -maxrotate;
			if(pitch > maxrotate/2) pitch = maxrotate/2;
			if(pitch < -maxrotate/2) pitch = -maxrotate/2;
		}
		loc.setPitch((float) (floc.getPitch() + pitch)%360);
		loc.setYaw((float) (floc.getYaw() + yaws)%360);
		return loc;
	}
    
    public static Location getRelativeLocation(Location point, Vector offset, EulerAngle angle) {

		offset = rotatePitch(offset, (float) Math.toDegrees(angle.getX()));
		offset = rotateYaw(offset, (float) Math.toDegrees(angle.getY()));
		offset = rotateRoll(offset, (float) Math.toDegrees(angle.getZ()));
		point.add(offset);

		return point;
		
	}

    private static Vector rotateRoll(Vector vec, float roll) {
		
		double rRoll = Math.toRadians(roll);
		double x = vec.getX();
		double y = vec.getY();
		
		vec.setX((x * Math.cos(rRoll)) + (y * Math.sin(rRoll)));
		vec.setY(-(x * Math.sin(rRoll)) + (y * Math.cos(rRoll)));
		
		return vec;
		
	}

	private static Vector rotatePitch(Vector vec, float pitch) {
		
		double rPitch = Math.toRadians(pitch);
		double y = vec.getY();
		double z = vec.getZ();
		
		vec.setY((y * Math.cos(rPitch)) - (z * Math.sin(rPitch)));
		vec.setZ((y * Math.sin(rPitch)) + (z * Math.cos(rPitch)));
		
		return vec;
		
	}

	private static Vector rotateYaw(Vector vec, float yaw) {
		
		double rYaw = Math.toRadians(yaw);
		double x = vec.getX();
		double z = vec.getZ();
		
		vec.setX((x * Math.cos(rYaw)) - (z * Math.sin(rYaw)));
		vec.setZ((x * Math.sin(rYaw)) + (z * Math.cos(rYaw)));
		
		return vec;
			
	}


}
