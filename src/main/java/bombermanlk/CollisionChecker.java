package bombermanlk;


import com.example.bombermanlk.entities.entities;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(entities entity){
        try {
            int entityLeftX = entity.x + entity.solidArea.x;
            int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
            int entityTopY = entity.y + entity.solidArea.y;
            int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

            int entityLeftCol = entityLeftX / gp.tileSize;
            int entityRightCol = entityRightX / gp.tileSize;
            int entityTopRow = entityTopY / gp.tileSize;
            int entityBottomRow = entityBottomY / gp.tileSize;

            int tileNum1, tileNum2;
            switch (entity.direction) {
                case "up": {
                    entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                    tileNum1 = gp.tile.map[gp.tile.mapNum][entityTopRow][entityLeftCol];
                    tileNum2 = gp.tile.map[gp.tile.mapNum][entityTopRow][entityRightCol];
                    if (gp.tile.tiles[tileNum1].collision || gp.tile.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    if (gp.bomb.x / gp.tileSize == (entity.x + gp.tileSize / 3) / gp.tileSize
                            && entity.y - entity.speed - gp.tileSize + 5 <= gp.bomb.y && entity.y > gp.bomb.y + gp.tileSize - 5
                            && gp.bomb.bombCount == 1) {
                        entity.collisionOn = true;
                    }
                    break;
                }
                case "down": {
                    entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                    tileNum1 = gp.tile.map[gp.tile.mapNum][entityBottomRow][entityLeftCol];
                    tileNum2 = gp.tile.map[gp.tile.mapNum][entityBottomRow][entityRightCol];
                    if (gp.tile.tiles[tileNum1].collision || gp.tile.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    if (gp.bomb.x / gp.tileSize == (entity.x + gp.tileSize / 3) / gp.tileSize
                            && entity.y + entity.speed + gp.tileSize - 5 >= gp.bomb.y && entity.y < gp.bomb.y - gp.tileSize + 5
                            && gp.bomb.bombCount == 1) {
                        entity.collisionOn = true;
                    }
                    break;
                }
                case "left": {
                    entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                    tileNum1 = gp.tile.map[gp.tile.mapNum][entityTopRow][entityLeftCol];
                    tileNum2 = gp.tile.map[gp.tile.mapNum][entityBottomRow][entityLeftCol];
                    if (gp.tile.tiles[tileNum1].collision || gp.tile.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    if (gp.bomb.y / gp.tileSize == (entity.y + gp.tileSize / 3) / gp.tileSize
                            && entity.x - entity.speed - gp.tileSize + 5 <= gp.bomb.x && entity.x > gp.bomb.x + gp.tileSize - 5
                            && gp.bomb.bombCount == 1) {
                        entity.collisionOn = true;
                    }
                    break;
                }
                case "right": {
                    entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                    tileNum1 = gp.tile.map[gp.tile.mapNum][entityTopRow][entityRightCol];
                    tileNum2 = gp.tile.map[gp.tile.mapNum][entityBottomRow][entityRightCol];
                    if (gp.tile.tiles[tileNum1].collision || gp.tile.tiles[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                    if (gp.bomb.y / gp.tileSize == (entity.y + gp.tileSize / 3) / gp.tileSize
                            && entity.x + entity.speed + gp.tileSize - 5 >= gp.bomb.x && entity.x < gp.bomb.x - gp.tileSize + 5
                            && gp.bomb.bombCount == 1) {
                        entity.collisionOn = true;
                    }
                    break;
                }
            }
        } catch (Exception e){
        }
    }

}