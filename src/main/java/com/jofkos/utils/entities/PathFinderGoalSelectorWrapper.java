package com.jofkos.utils.entities;

import java.util.List;

import com.jofkos.utils.reflect.Reflect.FieldAccessor;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

import com.jofkos.utils.reflect.Reflect;

@SuppressWarnings("rawtypes")
public class PathFinderGoalSelectorWrapper {
	
	private static FieldAccessor b, c;
	
	static {
		Reflect.transferFields(PathfinderGoalSelector.class, PathFinderGoalSelectorWrapper.class);
	}
	
	private PathfinderGoalSelector selector;
	
	public PathFinderGoalSelectorWrapper(PathfinderGoalSelector selector) {
		this.selector = selector;
	}
	
	public List getListB() {
		return b.get(selector);
	}
	
	public List getListC() {
		return c.get(selector);
	}
	
	public void a(int priority, PathfinderGoal goal) {
		this.selector.a(priority, goal);
	}
	
	public PathfinderGoalSelector getPathfinderGoalSelector() {
		return selector;
	}
	
}
