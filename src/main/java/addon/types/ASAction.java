package addon.types;

import java.util.HashMap;

import armorstand.ASBase;
import armorstand.AddParts;
import armorstand.Delay;
import armorstand.ItemSet;
import armorstand.Remove;
import armorstand.Riding;
import armorstand.Signal;
import armorstand.condition.CasterRadius;
import armorstand.condition.Distance;
import armorstand.condition.EntityCount;
import armorstand.condition.IsMove;
import armorstand.condition.IsParts;
import armorstand.condition.IsSignal;
import armorstand.move.CasterLocMove;
import armorstand.move.CasterMove;
import armorstand.move.FristLocMove;
import armorstand.move.AddLocMove;
import armorstand.move.BFSMove;
import armorstand.move.TargetLocMove;
import armorstand.move.VelocityMove;
import armorstand.spell.AirHitSpell;
import armorstand.spell.ChangeAction;
import armorstand.spell.HitSpell;
import armorstand.spell.SelfSpell;

public class ASAction {
	HashMap<String,Class<? extends ASBase>> action;
	
	public ASAction(){
		action = new HashMap<String,Class<? extends ASBase>>();
		setAction();
		
	}
	public Class<? extends ASBase> getAction(String name){
		return action.get(name);
	}
	
	public void addAction(String name,Class<? extends ASBase> action){
		this.action.put(name,action);
	}
	
	public void setAction() {
		action.put("delay", Delay.class);
		action.put("itemset", ItemSet.class);
		action.put("action", ChangeAction.class);
		action.put("remove", Remove.class);
		action.put("signal", Signal.class);
		action.put("riding", Riding.class);
		action.put("addparts", AddParts.class);
		
		//move
		action.put("fristlocmove", FristLocMove.class);
		action.put("addlocmove", AddLocMove.class);
		action.put("castermove", CasterMove.class);
		action.put("targetlocmove", TargetLocMove.class);
		action.put("casterlocmove", CasterLocMove.class);
		action.put("velocitymove", VelocityMove.class);
		action.put("bfsmove", BFSMove.class);
		
		//spell
		action.put("hitspell", HitSpell.class);
		action.put("airhitspell", AirHitSpell.class);
		action.put("selfspell", SelfSpell.class);
		
		//condition
		action.put("entitycount", EntityCount.class);
		action.put("distance", Distance.class);
		action.put("casterradius", CasterRadius.class);
		action.put("issignal", IsSignal.class);
		action.put("ismove", IsMove.class);
		action.put("isparts", IsParts.class);
	}
}
