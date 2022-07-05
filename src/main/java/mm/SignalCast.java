package mm;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.Spell.PostCastAction;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;

import addon.ArmorStandPlus;
import addon.util.NpcPlayer;
import armorstand.BaseArmorStand;
import heIpaddon.ArmorStandSpell;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.IDummySkill;
import io.lumine.xikage.mythicmobs.skills.INoTargetSkill;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.ITargetedLocationSkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;

public class SignalCast extends SkillMechanic implements INoTargetSkill{
	protected String signal;
	protected String target = "";
	protected int tick = 0;
	
    public SignalCast(String skill, MythicLineConfig mlc)  {
        super(skill, mlc);
    	this.target = config.getString(new String[] {"target", "tg"}, "all");
        this.signal = config.getString(new String[] {"signal", "s"}, "move");
        this.tick = config.getInteger(new String[] {"tick","t"}, 200);

	}

    public Player caster(SkillMetadata data) {
    	Entity caster = data.getCaster().getEntity().getBukkitEntity();
    	if(caster instanceof Player) {
    		return (Player) caster;
    	}
    	AbstractLocation target = data.getCaster().getLocation();
    	Location loc = new Location(Bukkit.getWorld(target.getWorld().getName()),target.getX(),target.getY(),target.getZ());
    	Player cast = NpcPlayer.npc(loc);
    	return cast;
    }

	@Override
	public boolean cast(SkillMetadata data) {
		Player p = caster(data);
		for(BaseArmorStand a : ArmorStandPlus.timeSystem.armorstands) {
			if(target.equals("owner") || target.equals("all")) {
				if(a.castPlayer == p) {
					a.sign.put(signal, tick);
					
				}
			}
			if(target.equals("parts") || target.equals("all")) {
				if(a.castPlayer == p) {
					parts(a);
				}
			}
		}
		return false;
	}
	
	void parts(BaseArmorStand b){
		if(b.parts != null && !b.parts.isEmpty()) {
			for(BaseArmorStand parts : b.parts) {
				b.sign.put(signal, tick);
				parts(parts);
			}
		}
	}


}
