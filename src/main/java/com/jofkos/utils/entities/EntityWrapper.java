package com.jofkos.utils.entities;

import net.minecraft.server.v1_8_R3.EntityInsentient;

public class EntityWrapper<E extends EntityInsentient> {
	
	private E entity;
	
	private PathFinderGoalSelectorWrapper goalSelector, targetSelector;
	
	public EntityWrapper(E entity) {
		this.entity = entity;
	}
	
	public PathFinderGoalSelectorWrapper getGoalSelector() {
		if (this.goalSelector == null)
			this.goalSelector = new PathFinderGoalSelectorWrapper(entity.goalSelector);
		return this.goalSelector;
	}
	
	public PathFinderGoalSelectorWrapper getTargetSelector() {
		if (this.targetSelector == null)
			this.targetSelector = new PathFinderGoalSelectorWrapper(entity.targetSelector);
		return this.targetSelector;
	}
	
	public E getEntity() {
		return this.entity;
	}
	
}
