package armorstand.spell;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.util.BoundingBox;
import com.nisovin.magicspells.util.ValidTargetList;

import addon.util.ASTarget;
import armorstand.ASBase;
import heIpaddon.ActionSpell;

public class ChangeAction extends ASBase{
	TargetedEntitySpell spell;

	@Override
	public void set(){
		super.set();
		String spellName = getValue("spell");
		Spell spell = MagicSpells.getSpellByInternalName(spellName);
		if(spell instanceof ActionSpell) {
			armorstand.setAction(((ActionSpell) spell).getAction());
		}
	}
	
	@Override
	public boolean run(){
		boolean run = super.run();
		return run;
	}
}
