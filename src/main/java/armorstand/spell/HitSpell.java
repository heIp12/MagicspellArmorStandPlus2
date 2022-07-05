package armorstand.spell;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.util.BoundingBox;
import com.nisovin.magicspells.util.ValidTargetList;

import addon.util.ASTarget;
import armorstand.ASBase;

public class HitSpell extends ASBase{
	TargetedEntitySpell spell;
	ValidTargetList targetList;
	boolean c = false;
	int target;
	List<LivingEntity> targets;
	private int spelltick;
	
	@Override
	public void set(){
		super.set();
		String spellName = getValue("spell");
		Spell spell = MagicSpells.getSpellByInternalName(spellName);
		target = (int)getDouble("max-target");
		c = getBoolean("c");
		if(getValue("max-target") == null) {
			target = 9999999;
		}
		spelltick = (int)getDouble("spell-tick");
		if(spelltick == 0 && getValue("spell-tick") == null) {
			spelltick = 1;
		}
		
        if (spell != null && (spell instanceof TargetedEntitySpell)) {
        	this.spell = (TargetedEntitySpell)spell;
        } else {
        	spell = null;
        }
        targets = new ArrayList<LivingEntity>();
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(tick%spelltick == 0 && run) {
			float power = (float) getDouble("power");
			if(getValue("power") == null) power = 1.0f;
			
			double size = getDouble("size");
			double vsize = getDouble("vsize");
			if(getValue("size") == null) size = 1.0f;
			if(getValue("vsize") == null) vsize = size;
			
			List<LivingEntity> entitys = ASTarget.box(armorstand.armorstand, armorstand.castPlayer, new Vector(size,vsize,size));
			if(entitys != null && !entitys.isEmpty()) {
				for(LivingEntity entity : entitys) {
					SpellTargetEvent event = new SpellTargetEvent((Spell) spell, armorstand.castPlayer, entity, power);
					Bukkit.getPluginManager().callEvent(event);
					if(target > 0 && !targets.contains(entity) && entity != null && !event.isCancelled()) {
						spell.castAtEntity((Player) armorstand.castPlayer,entity, power);
						if(!c) targets.add(entity);
						target--;
					}
				}
			}
		}
		return run;
	}
}
