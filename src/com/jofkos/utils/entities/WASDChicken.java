package com.jofkos.utils.entities;

import com.jofkos.utils.reflect.Reflect.FieldAccessor;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.jofkos.utils.reflect.Reflect;

public class WASDChicken extends EntityChicken {

	private static final FieldAccessor jump = Reflect.getField(EntityLiving.class, "aY"); // https://github.com/Bukkit/mc-dev/blob/master/net/minecraft/server/EntityLiving.java#L1343-L1355

	private EntityWrapper<WASDChicken> wrapper;

	public WASDChicken(Location loc) {
		this(((CraftWorld) loc.getWorld()).getHandle());
		
		this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	public WASDChicken(World world) {
		super(world);
		
		wrapper = new EntityWrapper<>(this);

		wrapper.getGoalSelector().getListB().clear();
		wrapper.getGoalSelector().getListC().clear();

		wrapper.getTargetSelector().getListB().clear();
		wrapper.getTargetSelector().getListC().clear();
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
			
			boolean jump = WASDChicken.jump.get(this.passenger);
			
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
