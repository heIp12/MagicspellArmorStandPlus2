package addon.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.spells.targeted.PainSpell;
import com.nisovin.magicspells.util.MagicConfig;

import addon.ArmorStandPlus;
import armorstand.BaseArmorStand;
public class ASTarget {
	static MagicConfig mc = new MagicConfig((MagicSpells)ArmorStandPlus.MagicSpell);
	//static MagicConfig mc = new MagicConfig();
	
	static public boolean isTarget(Entity target,Entity caster,Entity casterPlayer) {
		if(target.isDead() || !target.isValid()|| target == null) return false;
		if(!(target instanceof LivingEntity)) return false;
		if(target == caster) return false;
		if(target instanceof Player && ((Player) target).getGameMode() == GameMode.SPECTATOR) return false;
		if(target instanceof ArmorStand) return false;
		if(!(casterPlayer instanceof Player)) return false;
		SpellTargetEvent event = new SpellTargetEvent(new PainSpell(mc, "AddonTarget"), (Player)casterPlayer, (LivingEntity) target, 1);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled()) return false;
		return true;
	}
	
	static public List<LivingEntity> box(Entity loc, Entity caster, Vector vt) {
		List<LivingEntity> entity = new ArrayList<LivingEntity>();
		List<Entity> en = new ArrayList<Entity>();
		
		try {
			en = loc.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		} catch (Exception e) {
			return null;
		}
		
		for(Entity e : en) {
			if(isTarget(e, loc,caster) && e != caster){
				entity.add((LivingEntity) e);
			}
		} 

		return entity;
	}
	
	static public List<LivingEntity> Cbox(Entity loc, Entity caster, Vector vt) {
		List<LivingEntity> entity = new ArrayList<LivingEntity>();
		List<Entity> en = new ArrayList<Entity>();
		
		try {
			en = loc.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		} catch (Exception e) {
			return null;
		}
		
		for(Entity e : en) {
			if(isTarget(e, loc,caster) && e != caster){
				entity.add((LivingEntity) e);
			}
		}
	
		Comparator<LivingEntity> comparator = new Comparator<LivingEntity>() {

			@Override
			public int compare(LivingEntity o1, LivingEntity o2) {
				// TODO Auto-generated method stub
				return (int) (o1.getLocation().distance(loc.getLocation()) - o2.getLocation().distance(loc.getLocation()))*100;
			}
        }; 
        Collections.sort(entity,comparator);
		return entity;
	}
	
	static public List<BaseArmorStand> getStand(Location loc, float size){
		List<BaseArmorStand> stands = new ArrayList<BaseArmorStand>();
		for(BaseArmorStand b : ArmorStandPlus.timeSystem.armorstands) {
			if(b.armorstand.getLocation().distance(loc) < size) {
				stands.add(b);
			}
		}	
		return stands;
	}
	
}

