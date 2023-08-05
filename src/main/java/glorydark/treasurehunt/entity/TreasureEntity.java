package glorydark.treasurehunt.entity;

import cn.nukkit.Server;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * OriginalAuthor: SmallasWater
 */
public class TreasureEntity extends EntityHuman {
    private final Position position;
    private final double yawSpeed;

    private final Treasure treasure;

    public TreasureEntity(FullChunk chunk, CompoundTag nbt, Position position, Treasure treasure, double yawSpeed) {
        super(chunk, nbt);
        this.position = position;
        this.yawSpeed = yawSpeed;
        this.treasure = treasure;
    }

    @Deprecated //只是为了兼容PN核心
    public TreasureEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.position = new Position(0, 0, 0, Server.getInstance().getDefaultLevel()); //防止NPE
        this.yawSpeed = 4;
        this.treasure = null;
        this.close();
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setMaxHealth(20);
        this.setHealth(20.0F);
    }

    public String getIdentifier() {
        return this.treasure.getIdentifier();
    }

    public Treasure getTreasure() {
        return treasure;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(Server.getInstance().getOnlinePlayers().size() > 0){
            this.setRotation(this.getYaw() + yawSpeed, this.getPitch());
            if (this.getYaw() > 720) {
                this.setRotation(this.getYaw() - 720, this.getPitch());
            }
            this.updateMovement();
        }
        return super.onUpdate(currentTick);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Position getPosition() {
        return position;
    }

}