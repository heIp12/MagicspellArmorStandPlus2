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

public class SelfSpell extends ASBase{
	TargetedEntitySpell spell;
	ValidTargetList targetList;
	boolean c = false;
	int target;
	List<LivingEntity> targets = new ArrayList<LivingEntity>();
	private int spelltick;
	
	@Override
	public void set(){
		super.set();
		String spellName = getValue("spell");
		Spell spell = MagicSpells.getSpellByInternalName(spellName);
		
		spelltick = (int)getDouble("spell-tick");
		if(spelltick == 0 && getValue("spell-tick") == null) {
			spelltick = 10;
		}
		
        if (spell != null && (spell instanceof TargetedEntitySpell)) {
        	this.spell = (TargetedEntitySpell)spell;
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
			spell.castAtEntity((Player) armorstand.castPlayer,armorstand.armorstand, power);
		}
		return run;
	}
}
