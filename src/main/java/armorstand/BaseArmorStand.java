package armorstand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;

import addon.ArmorStandPlus;
import addon.types.ItemLoc;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.move.FristLocMove;
import heIpaddon.ArmorStandSpell;


public class BaseArmorStand {
	public LivingEntity caster;
	public Player castPlayer;
	public ArmorStand armorstand;

	public ItemStack head;
	public ItemStack Arm;
	public LocAndRotate startloc;
	public LocAndRotate standLoc;
	public int tick = 0;
	public int delay = 2;
	public boolean isAlive = false;

	public HashMap<String,List<ASBase>> action = null;
	public HashMap<String,List<ASBase>> repaction = null;
	public HashMap<String,Integer> sign = null;
	public List<BaseArmorStand> parts = null;
	public HashMap<BaseArmorStand,Location> tploc;
	
	public boolean caster_Armorstand = false;
	public BaseArmorStand owner = null;
	
	public Location nextLocation;
	public String name;
	long time;
	float drop = 0.01f;
	
	public BaseArmorStand(String name,LivingEntity caster,LivingEntity castPlayer,Location loc){
		this.name = name;
		this.action = new HashMap<>();
		this.sign = new HashMap<>();
		this.parts = new ArrayList<>();
		this.caster = caster;
		createArmorStand(loc);
		time = System.currentTimeMillis() + 90;
		isAlive = true;
		this.castPlayer = (Player)castPlayer;
		if(caster instanceof ArmorStand) {
			caster_Armorstand = true;
		}
		nextLocation = armorstand.getLocation();
	}
	
	public void run() {
		if(delay > 0) {
			delay--;
			return;
		}
		if(isAlive &&(armorstand == null || armorstand.isDead())
				|| (caster == null || caster.isDead())) {
			if(caster instanceof ArmorStand) {
				remove();
			} else {
				ArmorStandPlus.timeSystem.removeAdd(this);
			}
		}
		
		if(isAlive) {
			if(tick <= 0) {
				ArmorStandPlus.timeSystem.removeAdd(this);
				return;
			} else {
				for(BaseArmorStand bas : parts) {
					if(!bas.isAlive) {
						parts.remove(bas);
					}
				}
				tick--;
				Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
					if(!action.isEmpty()) {
						for(String actions : action.keySet()) {
							if(!action.get(actions).isEmpty()) {
								for(ASBase action : action.get(actions)) {
									if(action.run()) {
										break;
									}
								}
							}
						}
					}
					if(!sign.isEmpty()) {
						List<String> nosign = new ArrayList<String>();
						
						for(String s : sign.keySet()) {
							if(s.equals("remove")) {
								remove();
							}
							if(sign.get(s) != null && sign.get(s) > 0) {
								sign.put(s,sign.get(s)-1);
							} else if(sign.get(s) == null || sign.get(s) <= 0) {
								nosign.add(s);
							}
						}
						for(String s : nosign) {
							sign.remove(s);
						}
					}
					if(repaction != null) {
						action = repaction;
						repaction = null;
					}
					for(BaseArmorStand as : parts) as.run();
				});
				
				if(!caster_Armorstand) {
					tp();
				}
			}
		}
	}
	public void tp() {
		if(nextLocation != null ) {
			if(armorstand.hasGravity()) {
				if(armorstand.getPassengers().size() == 0) {
					Location loc = StandLoc.lookAt(armorstand.getLocation(),nextLocation,1);
					if(!armorstand.isOnGround()) {
						armorstand.setVelocity(loc.getDirection().multiply(armorstand.getLocation().distance(nextLocation)).setY(-0.5));
					} else {
						armorstand.setVelocity(loc.getDirection().multiply(armorstand.getLocation().distance(nextLocation)));
					}
					nextLocation = armorstand.getLocation().add(armorstand.getVelocity());
					standLoc.setLocation(nextLocation);
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
					tploc = new HashMap<>();
					for(BaseArmorStand as : parts)  tp(as);
					for(BaseArmorStand as : tploc.keySet()) {
						as.armorstand.teleport(tploc.get(as));
					}
				});
			} else {
				Bukkit.getScheduler().scheduleSyncDelayedTask(ArmorStandPlus.plugin, ()->{
					tploc = new HashMap<>();
					tp(this);
					
					for(BaseArmorStand as : tploc.keySet()) {
						as.armorstand.teleport(tploc.get(as));
					}
				});
			}
		}
		
	}
	
	public void tp(BaseArmorStand a) {
		for(BaseArmorStand as : a.parts) tp(as);
		
		if(a.nextLocation != null) {
			a.standLoc.setLocation(nextLocation);
			tploc.put(a,a.nextLocation);
		}
	}
	
	public void remove() {
		if(isAlive) {
			isAlive = false;
			sign.clear();
			if(!parts.isEmpty()) {
				for(BaseArmorStand as : parts) {
					as.remove();
					as.run();
				}
			}
			armorstand.remove();
		}
	}
	
	public void createArmorStand(Location arg0){
		armorstand = (ArmorStand)arg0.getWorld().spawnEntity(arg0, EntityType.ARMOR_STAND);
		armorstand.setCustomName("HEIPASSP");
		armorstand.setInvulnerable(true);
		armorstand.setGravity(false);
		armorstand.setVisible(false);
		armorstand.setArms(true);
		armorstand.setMarker(true);
		armorstand.setSilent(true);
		armorstand.setCanPickupItems(false);
	}
	
	public void createItem(ItemLoc loc,int id,int data) {
		ItemStack is = new ItemStack(id ,1,(short) data);
		ItemMeta im = is.getItemMeta();
		if(im != null) {
			im.setUnbreakable(true);
			is.setItemMeta(im);
			if(loc == ItemLoc.HEAD) {
				head = is;
				armorstand.setHelmet(head);
			}
			if(loc == ItemLoc.HAND) {
				Arm = is;
				armorstand.setItemInHand(Arm);
			}
		}
	}
	
	/*
	public void createItem(ItemLoc loc,Material id,int data) {
		ItemStack is = new ItemStack(id ,1,(short) data);
		ItemMeta im = is.getItemMeta();
		if(im != null) {
			im.setUnbreakable(true);
			is.setItemMeta(im);
			if(loc == ItemLoc.HEAD) {
				head = is;
				armorstand.setHelmet(head);
			}
			if(loc == ItemLoc.HAND) {
				Arm = is;
				armorstand.setItemInHand(Arm);
			}
		}
	}
	*/
	public void addParts(String name) {
		Spell spell = MagicSpells.getSpellByInternalName(name);
		if(spell != null && spell instanceof ArmorStandSpell) {
			BaseArmorStand bas = ((ArmorStandSpell)spell).create(armorstand,castPlayer, startloc.getLocation());
			bas.owner = this;
			parts.add(bas);
		}
	}
	
	public void removeParts(String name) {
		for(BaseArmorStand bas : parts) {
			if(bas.name.equals(name)) {
				bas.remove();
			}
		}
	}
	
	public void setSize(boolean small) {
		armorstand.setSmall(small);
	}

	public void setMaker(boolean maker) {
		armorstand.setMarker(maker);
	}

	public void setGravity(boolean gravity) {
		armorstand.setGravity(gravity);
	}
	
	public void setTick(int tick) {
		this.tick = tick;
	}
	
	public long getSpawnTime() {
		return time;
	}
	
	public LocAndRotate getLocation(ItemLoc itemloc) {
		Location loc = nextLocation;
		if(loc != null) {
			LocAndRotate lar = standLoc;
			Vector vt = new Vector(0,0,0);
			if(itemloc == ItemLoc.HAND) vt = StandLoc.toVector(armorstand.getRightArmPose());
			if(itemloc == ItemLoc.HEAD) vt = StandLoc.toVector(armorstand.getHeadPose());
			nextLocation.setPitch((float) vt.getX()/2);
			lar.setLocation(nextLocation);
			nextLocation.setPitch(0);
			return lar;
		}
		return standLoc;
	}
	
	public LocAndRotate getStandLocation(ItemLoc itemloc) {
		Location loc = armorstand.getLocation();
		LocAndRotate lar = new LocAndRotate(loc,itemloc);
		if(itemloc == ItemLoc.HAND) lar.setRotate(StandLoc.toVector(armorstand.getRightArmPose()));
		if(itemloc == ItemLoc.HEAD) lar.setRotate(StandLoc.toVector(armorstand.getHeadPose()));
		return new LocAndRotate(loc,itemloc);
	}
	
	public void teleport(LocAndRotate locs) {
		LocAndRotate loc = locs;
		if(loc.getLocation().getChunk().isLoaded()) {
			Location local = loc.getLocation();
			Vector vtr = loc.getRotate();
			local.setYaw((float) vtr.getY());
			local.setPitch((float) vtr.getZ());
			vtr.setY(0);
			setPose(loc.getItemloc(),vtr);
			nextLocation = local;
		} else {
			ArmorStandPlus.timeSystem.removeAdd(this);
		}
	}
	
	public void setPose(ItemLoc itemloc, Vector itemRotate) {
		if(itemloc == ItemLoc.HAND) armorstand.setRightArmPose(StandLoc.toEulerAngle(itemRotate));
		if(itemloc == ItemLoc.HEAD) armorstand.setHeadPose(StandLoc.toEulerAngle(itemRotate));
		standLoc.setRotate(itemRotate);
	}

	public void setAction(List<String> action) {
		HashMap<String, List<ASBase>> as = new HashMap<>();
		if(action != null) {
			for(String s : action) {
				s = s.toLowerCase();
				String name = s.split(":")[0];
				String type = s.split(":")[1];
				String code;
				if(s.split(":").length > 2) {
					code = s.split(":")[2];
				} else {
					code = "";
				}
				if(as.get(name) == null) as.put(name,  new ArrayList<ASBase>());

				if(ArmorStandPlus.asAction.getAction(type) != null) {
					Class<? extends ASBase> abbase = ArmorStandPlus.asAction.getAction(type.toLowerCase());
					try {
						ASBase ab = abbase.newInstance();
						ab.setBase(this,name);
						ab.setCode(code);
						as.get(name).add(ab);
					} catch (InstantiationException e) {
						System.out.println("[MS-StandAddon] No ASBase Type : " + type);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("[MS-StandAddon] Not Action Type : " + type);
				}
			}
			if(this.action.size() > 0) {
				repaction = as;
			} else {
				this.action = as;
			}
		}
	}
}
