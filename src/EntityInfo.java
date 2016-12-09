import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*
 * This makes it way easier to load in entities, and we can do it from a
 * place away from other important code, so it becomes less clunky.
 */
public enum EntityInfo {
	GreenTank ("Tank", "Tanks/GreenTankSheet.png", 0.25f, 256, 192, 8, true),
	RedTank   ("Tank", "Tanks/RedTankSheet.png",   0.25f, 256, 192, 8, false),
	GreenUnit ("Unit", "Units/GreenUnit.png", 0.5f, 32, 48, 1, true),
	RedUnit   ("Unit", "Units/RedUnit.png",   0.5f, 32, 48, 1, false),
	GreenBase ("Building", "Buildings/GreenBase.png", 0.5f, 512, 384, 1, true),
	RedBase   ("Building", "Buildings/RedBase.png",   0.5f, 512, 384, 1, false);
	
	Entity entity;
	Image[] images;
	
	EntityInfo(String type, String filename, float scale, int w, int h, int n, boolean control){
		Image sheetImage = null;
		images = new Image[n];
		try {
			sheetImage = new Image("res/"+filename).getScaledCopy(scale);
			for (int i = 0; i < n; i++){
				images[i] = sheetImage.getSubImage((int)(i * w * scale), 0, (int)(w * scale), (int)(h * scale));
			}

		} catch (SlickException e) { e.printStackTrace(); }
		if (type.equals("Tank")){
			entity = new Tank(new Vector3d(), images, 0, control);
		}
		else if (type.equals("Unit")){
			entity = new Unit(new Vector3d(), images, 0, control);
		}
		else if (type.equals("Building")){
			entity = new Building(new Vector3d(), images, 0, control);
		}
	}

	public Entity generateEntity(Vector3d pos){
		entity.setPos(pos);
		return entity.clone();
	}
	
	public Entity generateEntity(double x, double y){
		entity.setPos(new Vector3d(x, y));
		return entity.clone();
	}
}
