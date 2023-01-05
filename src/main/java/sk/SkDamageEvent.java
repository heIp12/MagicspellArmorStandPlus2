package sk;

import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class SkDamageEvent extends SkriptEvent {

	@Override
	public String toString(Event arg0, boolean arg1) {
		return "";
	}

	@Override
	public boolean check(Event arg0) {
		return true;
	}

	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		// TODO Auto-generated method stub
		return true;
	}

}
