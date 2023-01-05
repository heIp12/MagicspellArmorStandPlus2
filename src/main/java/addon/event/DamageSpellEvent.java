package addon.event;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DamageSpellEvent extends Event{
	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isCancelled;
	
	LivingEntity attaker;
	LivingEntity target;
	float damage;
	String[] type;
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	public DamageSpellEvent(LivingEntity attaker,LivingEntity target,float damage,String type) {
        this.attaker = attaker;
        this.target = target;
        this.damage = damage;
        this.type = type.split(",");
        this.isCancelled = false;
    }
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return HANDLERS;
	}

	public boolean isCancelled() { return this.isCancelled; }
	public void setCancelled(boolean isCancelled) { this.isCancelled = isCancelled; }
	
	public LivingEntity getAttaker() { return attaker; }
	public LivingEntity getTarget() { return target; }
	
	public float getDamage() { return damage; }
	public void setDamage(float damage) { this.damage = damage; }
	public void addDamage(float damage) { this.damage += damage; }
	
	public boolean getType(String type) {
		for(String t : this.type) {
			if(t.toUpperCase().contains(type.toUpperCase())) {
				return true;
			}
		}
		return false;	
	}
	public String[] getType() {
		return type;
	}
}
