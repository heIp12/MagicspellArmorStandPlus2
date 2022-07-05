package armorstand.move;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.util.BlockUtils;

import addon.util.ASTarget;
import addon.util.Holo;
import addon.util.LocAndRotate;
import addon.util.StandLoc;
import armorstand.ASBase;

public class BFSMove extends ASBase{
	boolean lockyaw = false;
	boolean lockpitch = false;
	boolean debug = false;
	
	LivingEntity target = null;
	Location bfsloc = null;
	int c[][][] = null;
	Vector lastloc;
	
	@Override
	public void set() {
		super.set();
		lockyaw = getBoolean("lyaw");
		lockpitch = getBoolean("lpitch");
		debug = getBoolean("debug");
	}
	
	@Override
	public boolean run() {
		boolean run = super.run();
		if(run) {
			double size = getDouble("size");
			double vsize = getDouble("vsize");
			if(getValue("size") == null) size = 20.0f;
			if(getValue("vsize") == null) vsize = size;
			
			List<LivingEntity> e = ASTarget.Cbox(armorstand.armorstand, armorstand.castPlayer, new Vector(size,vsize,size));
			if(e == null || e.isEmpty()) run = false;
			if(!e.isEmpty()) target = e.get(0);
			if(e.isEmpty()) c = null;
			
			if(run) {
				if(tick%20 == 0 || bfsloc == null) {
					c = null;
				}
				
				Block farst = armorstand.armorstand.getLocation().clone().getBlock();
				Block last = target.getLocation().clone().getBlock();
				bfsloc = bfs((int)(size*2.5),farst,last);
				
				LocAndRotate lar = getLoc();
				Vector vtr = lar.getRotate();
				Location loc = StandLoc.lookAt(lar.getLocation(),bfsloc,getDouble("p",1));
				
				if(lockyaw) {
					loc.setYaw((float) getDouble("yaw"));
				} else {
					loc.setYaw((float) (loc.getYaw() + getDouble("yaw")));
				}
				if(lockpitch) {
					loc.setPitch((float) getDouble("pitch"));
				} else {
					loc.setPitch((float) (loc.getPitch() + getDouble("pitch")));
				}
				loc = loc.add(loc.getDirection().multiply(getDouble("speed")));
				vtr = new Vector(loc.getPitch(),loc.getYaw(),vtr.getZ());
				lar.setLoc(loc, vtr, type);
				armorstand.teleport(lar);
				
			}
	
		}
		return run;
	}
	
	boolean isRep(int[][][] c,Location l,int i,int j,int k){
		Location lc = l.clone();
		if(BlockUtils.isPathable(lc.add(i,j,k).clone().getBlock()) && c[i][j][k] == -1) {
			return true;
		}
		return false;
	}
	
	private Location bfs(int bsize,Block farst,Block last) {
		int c[][][];
		
		boolean ok = true;
		int ysize = 0;
		
		if(getValue("vsize") == null) {
			ysize = bsize;
		} else {
			ysize = (int) getDouble("vsize");
		}
		
		if(this.c != null) {
			c = this.c;
		} else {
			c = new int[bsize][ysize][bsize];
			Long time = System.currentTimeMillis();
			for(int i=0;i<bsize;i++) {
				for(int j=0;j<ysize;j++) {
					for(int k=0;k<bsize;k++) {
						c[i][j][k] = -1;
					}
				}
			}
			
			c[bsize/2][ysize/2][bsize/2] = 0;
			lastloc = new Vector(0,0,0);
			
			int maxcount =0;
			int mc = 0;
	
			while(ok) {
				boolean isok = true;
				for(int i=0;i<bsize;i++) {
					for(int j=0;j<ysize;j++) {
						for(int k=0;k<bsize;k++) {
							if(c[i][j][k] == -1) {
								isok = false;
							}
						}
					}
				}
				for(int i=0;i<bsize;i++) {
					for(int j=0;j<ysize;j++) {
						for(int k=0;k<bsize;k++) {
							Location l = farst.getLocation().clone().add(-bsize/2,-ysize/2,-bsize/2);
							
							if(!BlockUtils.isPathable(l.clone().add(i,j,k).getBlock()) && c[i][j][k] == -1){
								c[i][j][k] = 100000;
							} else
							if(c[i][j][k] != -1 && c[i][j][k] < 99999) {
								int s[] = {i,j,k};
								int p[][] = {{1,0,0},{0,1,0},{0,0,1}};
								int sz[] = {bsize,ysize,bsize};
								
								for(int o=0;o<3;o++) {
									l = farst.getLocation().clone().add(-bsize/2,-ysize/2,-bsize/2);
									if(s[o]-1>=0 && isRep(c,l,i-p[o][0],j-p[o][1],k-p[o][2]) ) {
										c[i-p[o][0]][j-p[o][1]][k-p[o][2]] = c[i][j][k]+1;
										if(l.clone().add(i-p[o][0],j-p[o][1],k-p[o][2]).getBlock().equals(last)) {
											isok = true;
											lastloc = new Vector(i-p[o][0],j-p[o][1],k-p[o][2]);
											i=j=k=bsize;
											break;
										}
									} else if(s[o]-1>=0 && c[i-p[o][0]][j-p[o][1]][k-p[o][2]] == -1) {
										c[i-p[o][0]][j-p[o][1]][k-p[o][2]] = 100000;
									}
									
									if(s[o]+1<sz[o] && isRep(c,l,i+p[o][0],j+p[o][1],k+p[o][2])) {
										c[i+p[o][0]][j+p[o][1]][k+p[o][2]] = c[i][j][k]+1; 
										if(l.clone().add(i+p[o][0],j+p[o][1],k+p[o][2]).getBlock().equals(last)) {
											isok = true;
											lastloc = new Vector(i+p[o][0],j+p[o][1],k+p[o][2]);
											i=j=k=bsize;
											break;
										}
									} else if(s[o]+1<sz[o] && c[i+p[o][0]][j+p[o][1]][k+p[o][2]] == -1){
										c[i+p[o][0]][j+p[o][1]][k+p[o][2]] = 100000;
									}
									
								}
							}
							if(!isok && c[i][j][k] < 100000 && c[i][j][k] > mc) {
								mc = c[i][j][k];
							}
						}
					}
				}
				
				if(isok) {
					ok = false;
				}
				if(maxcount > bsize*ysize*bsize) ok = false;
				maxcount++;
			}
			this.c = c;
		}
		int max = c[lastloc.getBlockX()][lastloc.getBlockY()][lastloc.getBlockZ()];
		
		ok = true;
		Vector lv = lastloc;
		int m = max;
		
		int maxcount = 0;
		
		while(ok) {
			int i,j,k;

			int p[][] = {{1,0,0},{0,1,0},{0,0,1},{-1,0,0},{0,-1,0},{0,0,-1}};
			
			for(int o = 0; o<6;o++) {
				i = lv.getBlockX()+p[o][0];
				j = lv.getBlockY()+p[o][1];
				k = lv.getBlockZ()+p[o][2];
				if(i < 0 || j <0 || k < 0 || i >=bsize|| j >=ysize|| k >=bsize) continue;
				
				if(c[i][j][k] == m-1 && m > 2) {
					m--;
					lv = new Vector(i,j,k);
					if(debug) {
						Location l = farst.getLocation().clone().add(-bsize/2,-ysize/2,-bsize/2);
						Holo.create(l.add(i+0.5,j-1,k+0.5), "§a"+c[i][j][k],4, new Vector(0,0,0));
					}
				}

			}
			if(m == 2) {
				ok = false;
			}
			maxcount++;
			if(maxcount > bsize*ysize*bsize) ok= false;
		}
		Location l = farst.getLocation().clone().add(-bsize/2,-ysize/2,-bsize/2);
		
		return l.add(lv.getX()+0.5,lv.getY(),lv.getZ()+0.5);
	}
	
}

