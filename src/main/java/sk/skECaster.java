package sk;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import addon.event.DamageSpellEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class skECaster extends SimpleExpression<Player>{

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(Event arg0, boolean arg1) {
		return "s[pell][-]caster";
	}

	@Override
	protected Player[] get(Event event) {
		if (!(event instanceof DamageSpellEvent)) return null;
		DamageSpellEvent e = (DamageSpellEvent)event;
		if (!(e.getAttaker() instanceof Player)) return null;
		return new Player[] { (Player)e.getAttaker() };
	}

}
