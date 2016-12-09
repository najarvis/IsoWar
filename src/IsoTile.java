import org.newdawn.slick.SlickException;

import OldFiles.Vector2d;

public class IsoTile extends Tile {
	/*        .   <>             | The values inside of the parenthesis are the coordinates
	 *        < (0,0)  >         | in terms of indexes in an array.
	 * .   <>     <> .   <>      | 
	 * < (0,1)  >    < (1,0)  >  | xPos = (WIDTH / 2 - TILE_WIDTH / 2) + (x - y)(TILE_WIDTH / 2)
	 *     <> .   <>     <>      | yPos = (x + y)(TILE_HEIGHT / 2)
	 *        < (1,1)  >         | 
	 *            <>             | (0, 0) = (WIDTH / 2 - TILE_WIDTH / 2),                    0
	 * --------------------------- (1, 0) = (WIDTH / 2 - TILE_WIDTH / 2) + (TILE_WIDTH / 2), TILE_HEIGHT / 2
	 *                             (0, 1) = (WIDTH / 2 - TILE_WIDTH / 2) - (TILE_WIDTH / 2), TILE_HEIGHT / 2
	 *                             (1, 1) = (WIDTH / 2 - TILE_WIDTH / 2),                    TILE_HEIGHT
	 */
	
	
	public IsoTile(Vector3d pos, String filename) throws SlickException {
		super(pos, filename);
	}
	
	public void render(Camera camera){
		Vector3d drawPos = IsoFuncs.IsoToEuc(pos, camera);
		image.draw((float)drawPos.x, (float)drawPos.y);
	}

}
