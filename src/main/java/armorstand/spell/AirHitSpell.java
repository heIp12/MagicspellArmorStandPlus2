package armorstand.spell;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.events.SpellTargetLocationEvent;
import com.nisovin.magicspells.spells.TargetedEntityFromLocationSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;
import com.nisovin.magicspells.util.BoundingBox;
import com.nisovin.magicspells.util.ValidTargetList;

import addon.ArmorStandPlus;
import addon.util.ASTarget;
import armorstand.ASBase;

public class AirHitSpell extends ASBase{
	TargetedLocationSpell spell = null;
	
	int spelltick;
	
	@Override
	public void set(){
		super.set();
		String spellName = getValue("spell");
		Spell spell = MagicSpells.getSpellByInternalName(spellName);
		spelltick = (int)getDouble("spell-tick");
		if(spelltick == 0 && getValue("spell-tick") == null) {
			spelltick = 10;
		}
		
		
        if (spell != null && (spell instanceof TargetedLocationSpell)) {
        	this.spell = (TargetedLocationSpell)spell;
        } else {
        	spell = null;
        }
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		if(tick%spelltick == 0 && run) {
			float power = (float) getDouble("power");
			if(getValue("power") == null) power = 1.0f;
			final float fpower = power;
			
			Location loc = getSLoc().getLocation();
			Vector vt = getSLoc().getRotate();
			
			loc.setYaw((float) (vt.getY() +  getDouble("yaw")));
			loc.setPitch((float) (vt.getX() +  getDouble("pitch")));
			SpellTargetLocationEvent event = new SpellTargetLocationEvent((Spell) spell, armorstand.castPlayer, loc, power);
			Bukkit.getPluginManager().callEvent(event);
			Location spellloc = loc.clone();

			if(spell != null && !event.isCancelled()) spell.castAtLocation(armorstand.castPlayer,spellloc, fpower);
			
		}
		return run;
	}
}
