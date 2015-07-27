package com.jofkos.utils.entities;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

import com.jofkos.utils.reflect.FieldUtils;
import com.jofkos.utils.reflect.Reflect;

@SuppressWarnings("rawtypes")
public class PathFinderGoalSelectorWrapper {
	
	private static Field b, c;
	
	static {
		FieldUtils.transferFields(PathfinderGoalSelector.class, PathFinderGoalSelectorWrapper.class);
	}
	
	private PathfinderGoalSelector selector;
	
	public PathFinderGoalSelectorWrapper(PathfinderGoalSelector selector) {
		this.selector = selector;
	}
	
	public List getListB() {
		return Reflect.get(b, selector);
	}
	
	public List getListC() {
		return Reflect.get(c, selector);
	}
	
	public void a(int priority, PathfinderGoal goal) {
		this.selector.a(priority, goal);
	}
	
	public PathfinderGoalSelector getPathfinderGoalSelector() {
		return selector;
	}
	
}
