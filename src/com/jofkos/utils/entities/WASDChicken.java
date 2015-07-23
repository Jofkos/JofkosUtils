package com.jofkos.utils.entities;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.jofkos.utils.entities.EntityWrapper;
import com.jofkos.utils.reflect.Reflect;

public class WASDChicken extends EntityChicken {

	private static final Field jump = Reflect.getField(EntityLiving.class, "aY"); // https://github.com/Bukkit/mc-dev/blob/master/net/minecraft/server/EntityLiving.java#L1343-L1355

	private EntityWrapper<WASDChicken> wrapper;

	public WASDChicken(Location loc) {
		this(((CraftWorld) loc.getWorld()).getHandle());
		
		this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	public WASDChicken(World world) {
		super(world);
		
		wrapper = new EntityWrapper<WASDChicken>(this);

		wrapper.getGoalSelector().getListB().clear();
		wrapper.getGoalSelector().getListC().clear();

		wrapper.getTargetSelector().getListB().clear();
		wrapper.getTargetSelector().getListC().clear();

		/*
		 * this.goalSelector.a(0, new PathfinderGoalFloat(this));
		 * this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.4D));
		 * this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
		 * this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.0D,
		 * Items.WHEAT_SEEDS, false)); this.goalSelector.a(4, new
		 * PathfinderGoalFollowParent(this, 1.1D)); this.goalSelector.a(5, new
		 * PathfinderGoalRandomStroll(this, 1.0D)); this.goalSelector.a(6, new
		 * PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
		 * this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
		 */
	}
	
	public void spawn() {
		this.world.addEntity(this, SpawnReason.CUSTOM);
	}
	
	@Override
	public void g(float sideMot, float forMot) {
		if (this.passenger != null && this.passenger instanceof EntityHuman) {
			
			this.lastYaw = this.yaw = this.passenger.yaw;
			this.pitch = this.passenger.pitch * 0.5F;
			
			this.setYawPitch(this.yaw, this.pitch);
			this.aK = this.aI = this.yaw;
			
			this.S = 1.0F; // Climb 1 block high
			
			sideMot = ((EntityLiving) this.passenger).aZ * 0.5F;
			forMot = ((EntityLiving) this.passenger).ba;
			
			if (forMot <= 0.0F) {
				forMot *= 0.25F; // slower backwards
			}
			
			sideMot *= 0.75F; // slower sideways
			
			this.k(0.35F); // Walk speed
			
			super.g(sideMot, forMot);
			
			boolean jump = Reflect.get(WASDChicken.jump, this.passenger);
			
			if (jump && this.onGround) {
				this.motY = 1.0D; // Jumpheight
			}
			
//			if (this.onGround && this.br == 0.0F) {
//				sideMot = 0.0F;
//				forMot = 0.0F;
//			}

		} else {
			this.S = 0.5F;
			this.aM = 0.02F;
			super.g(sideMot, forMot);
		}

	}

}
