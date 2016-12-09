import java.util.ArrayList;

import org.newdawn.slick.Input;

public class Player {

	double resources;
	int unitCost = 5;
	int tankCost = 25;
	
	int health = 100;
	
	double unitCooldown = 0;
	double unitCooldownMax = 0.5;
	double tankCooldown = 0;
	double tankCooldownMax = 2;
	
	boolean control;
	
	Vector3d basePos;
	Vector3d otherBasePos;
	Vector3d generatePos;
	
	public Player(Vector3d basePos, Vector3d otherBasePos, boolean control){
		this.basePos = basePos;
		this.otherBasePos = otherBasePos;
		generatePos = basePos.add(basePos.fromOther(otherBasePos).normalize().mul(150));
		
		this.resources = 100;
		
		this.control = control;
		
	}
	
	public void update(ArrayList<Entity> entities, double delta, Input input){
		if (resources < 200)
			resources += delta * 2;
		if (resources > 200)
			resources = 200;
		
		tankCooldown -= delta;
		if (tankCooldown < 0) tankCooldown = 0;
		
		unitCooldown -= delta;
		if (unitCooldown < 0) unitCooldown = 0;
		
		
		// If you aren't in control, the AI makes it's decisions
		if (!control)
			makeDecisions(entities);
		else
			// Player makes his decisions
			checkInput(entities, input);
	}
	
	// What the player does
	private void checkInput(ArrayList<Entity> entities, Input input){
		EntityInfo GU = EntityInfo.GreenUnit;
		EntityInfo GT = EntityInfo.GreenTank;
		
		// Press the T key, and as long as you've got the resources a tank pops out.
		if (input.isKeyDown(input.KEY_T) && tankCooldown <= 0 && resources >= tankCost){
			Entity toAdd = GT.generateEntity(generatePos);
			toAdd.setDestination(otherBasePos);
			
			entities.add(toAdd);
			
			resources -= tankCost;
			tankCooldown = tankCooldownMax;
		}
		
		if (input.isKeyDown(input.KEY_U) && unitCooldown <= 0 && resources >= unitCost){
			Entity toAdd = GU.generateEntity(generatePos);
			toAdd.setDestination(otherBasePos);
			
			entities.add(toAdd);
			
			resources -= unitCost;
			unitCooldown = unitCooldownMax;
		}
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
		
		if (numUnits < 0)
			wantToMakeUnit = true;
		
		if (numTanks < 5)
			wantToMakeTank = true;
		
		if (wantToMakeUnit && resources > unitCost && unitCooldown <= 0){
			//Make Unit
			Entity toAdd = RU.generateEntity(generatePos);
			toAdd.destination = otherBasePos.clone();
			
			entities.add(toAdd);
			
			resources -= unitCost;
			unitCooldown = unitCooldownMax;
		}
		
		if (wantToMakeTank && resources > tankCost && tankCooldown <= 0){
			//Make Tank
			Entity toAdd = RT.generateEntity(generatePos);
			toAdd.destination = otherBasePos.clone();
			
			entities.add(toAdd);
			
			resources -= tankCost;
			tankCooldown = tankCooldownMax;
			
		}
		
	}
	
	
	
}
