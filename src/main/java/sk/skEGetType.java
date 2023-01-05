package sk;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class skEGetType extends SimpleExpression<String>{
	private Expression<String> type = null;

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(Event arg0, boolean arg1) {
		return "attack type %string%";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected String[] get(Event event) {
		if (!(event instanceof DamageSpellEvent)) return null;
		DamageSpellEvent e = (DamageSpellEvent)event;
		return e.getType();
	}
}
