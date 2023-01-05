package addon.types;

import java.util.HashMap;

import armorstand.ASBase;
import armorstand.condition.CasterRange;
import armorstand.condition.Distance;
import armorstand.condition.EntityCount;
import armorstand.condition.EntityDistance;
import armorstand.condition.IsMove;
import armorstand.condition.IsParts;
import armorstand.condition.IsSignal;
import armorstand.condition.OnBlock;
import armorstand.condition.SingnalDistance;
import armorstand.move.CasterLocMove;
import armorstand.move.CasterLockMove;
import armorstand.move.CasterMove;
import armorstand.move.FristLocMove;
import armorstand.move.SignalMove;
import armorstand.move.AddLocMove;
import armorstand.move.BFSMove;
import armorstand.move.TargetLocMove;
import armorstand.option.AddParts;
import armorstand.option.Delay;
import armorstand.option.GravitySet;
import armorstand.option.ItemSet;
import armorstand.option.MiniSize;
import armorstand.option.PlaySound;
import armorstand.option.Remove;
import armorstand.option.Signal;
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
		action.put("addparts", AddParts.class);
		action.put("minisize", MiniSize.class);
		action.put("gravity", GravitySet.class);
		action.put("sound", PlaySound.class);
		
		//move
		//현재 바라보는 것 기준으로 더하기
		action.put("addlocmove", AddLocMove.class);
		//시작 지점 기준으로 더하기
		action.put("fristlocmove", FristLocMove.class);
		//플레이가 바라보는것 기준으로 더하기
		action.put("casterlockmove", CasterLockMove.class);
		//주변 대상에게 이동
		action.put("targetlocmove", TargetLocMove.class);
		//플레이어에게 이동
		action.put("casterlocmove", CasterLocMove.class);
		//시전자에게 고정
		action.put("castermove", CasterMove.class);
		//입력받은 지점으로 이동
		action.put("signalmove", SignalMove.class);
		//
		//
		
		//주변 지형을 피해 적을 추적
		//action.put("bfsmove", BFSMove.class);
		
		//spell
		//주변에 대상에게 시전
		action.put("hitspell", HitSpell.class);
		//자신의 위치에 시전
		action.put("airhitspell", AirHitSpell.class);
		//아머스탠드에 시전
		action.put("selfspell", SelfSpell.class);
		
		//condition
		//주변 몬스터수 
		action.put("entitycount", EntityCount.class);
		//?거리내 가장 가까운 몬스터와의 거리
		action.put("entitydistance", EntityDistance.class);
		//시전자와의 거리
		action.put("distance", Distance.class);
		//받은 신호
		action.put("issignal", IsSignal.class);
		//움직이고 있을때
		action.put("ismove", IsMove.class);
		//파츠가 있을떄
		action.put("isparts", IsParts.class);
		//블럭 위에 있을때
		action.put("onblock", OnBlock.class);
		//플레이어와의 거리가 가까울때
		action.put("casterrange", CasterRange.class);
		//신호와 가까울때
		action.put("signaldistance", SingnalDistance.class);
	}
}
