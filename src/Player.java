import java.util.ArrayList;

import org.newdawn.slick.Input;

public class Player {

	int resources;
	int unitCost = 5;
	int tankCost = 25;
	
	double unitCooldown = 0;
	double unitCooldownMax = 0.5;
	double tankCooldown = 0;
	double tankCooldownMax = 2;
	
	boolean control;
	
	Vector3d basePos;
	Vector3d otherBasePos;
	
	public Player(Vector3d basePos, Vector3d otherBasePos, boolean control){
		this.basePos = basePos;
		this.otherBasePos = otherBasePos;
		
		this.resources = 100;
		
		this.control = control;
		
	}
	
	public void update(ArrayList<Entity> entities, double delta, Input input){
		if (this.resources < 200)
			this.resources += delta;
		if (this.resources > 200)
			this.resources = 200;
		
		tankCooldown -= delta;
		if (tankCooldown < 0) tankCooldown = 0;
		
		unitCooldown -= delta;
		if (unitCooldown < 0) unitCooldown = 0;
		
		
		// AI makes it's decisions
		if (!control)
			makeDecisions(entities);
		else
			checkInput(entities, input);
	}
	
	// What the player does
	private void checkInput(ArrayList<Entity> entities, Input input){
		
	}
	
	// What the AI does
	private void makeDecisions(ArrayList<Entity> entities){
		EntityInfo RU = EntityInfo.RedUnit;
		EntityInfo RT = EntityInfo.RedTank;
		
		boolean wantToMakeUnit = false;
		boolean wantToMakeTank = false;
		
		int numUnits = 0;
		int numTanks = 0;
		for (Entity e : entities){
			if (e.controllable == control && e instanceof Unit)
				numUnits += 1;
			
			if (e.controllable == control && e instanceof Tank)
				numTanks += 1;
		}
		
		if (numUnits < 5)
			wantToMakeUnit = true;
		
		if (numTanks < 2)
			wantToMakeTank = true;
		
		if (wantToMakeUnit && resources > unitCost && unitCooldown <= 0){
			//Make Unit
			Entity toAdd = RU.generateEntity(basePos);
			toAdd.destination = otherBasePos.clone();
			
			entities.add(toAdd);
			
			resources -= unitCost;
			unitCooldown = unitCooldownMax;
		}
		
		if (wantToMakeTank && resources > tankCost && tankCooldown <= 0){
			//Make Tank
			Entity toAdd = RT.generateEntity(basePos);
			toAdd.destination = otherBasePos.clone();
			
			entities.add(toAdd);
			
			resources -= tankCost;
			tankCooldown = tankCooldownMax;
			
		}
		
	}
	
	
	
}
