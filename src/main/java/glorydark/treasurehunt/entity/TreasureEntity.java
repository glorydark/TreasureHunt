package glorydark.treasurehunt.entity;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.Level;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.RemoveEntityPacket;

/**
 * OriginalAuthor: SmallasWater
 */
public class TreasureEntity extends EntityHuman {
    private final Position position;
    private final double yawSpeed;

    private final Treasure treasure;

    private long updateParticleLastMillis;

    public TreasureEntity(FullChunk chunk, CompoundTag nbt, Position position, Treasure treasure, double yawSpeed) {
        super(chunk, nbt);
        this.position = position;
        this.yawSpeed = yawSpeed;
        this.treasure = treasure;
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
        if (Server.getInstance().getOnlinePlayers().size() > 0) {
            this.setRotation(this.getYaw() + yawSpeed, this.getPitch());
            if (this.getYaw() > 720) {
                this.setRotation(this.getYaw() - 720, this.getPitch());
            }
            this.updateMovement();

            if (System.currentTimeMillis() - updateParticleLastMillis >= 2000) {
                for (Entity nearbyEntity : this.level.getNearbyEntities(this.boundingBox.grow(10, 10, 10))) {
                    if (nearbyEntity instanceof Player) {
                        Player player = (Player) nearbyEntity;
                        Position pos = new Position(this.x, this.y + 0.5, this.z, this.level);
                        if (!treasure.getPlayerCollect(player.getName()).contains(this.getIdentifier())) {
                            ParticleEffect particleeffect = ParticleEffect.BLUE_FLAME;
                            for (int angle = 0; angle < 720; angle++) {
                                double x1 = pos.x + 1 * Math.cos(angle * 3.14 / 180);
                                double z1 = pos.z + 1 * Math.sin(angle * 3.14 / 180);
                                if (angle % 30 == 0) {
                                    pos.getLevel().addParticleEffect(new Position(x1, pos.y, z1), particleeffect);
                                }
                            }
                        } else {
                            ParticleEffect particleeffect = ParticleEffect.FALLING_DUST_GRAVEL;
                            for (int angle = 0; angle < 720; angle++) {
                                double x1 = pos.x + 1 * Math.cos(angle * 3.14 / 180);
                                double z1 = pos.z + 1 * Math.sin(angle * 3.14 / 180);
                                if (angle % 30 == 0) {
                                    pos.getLevel().addParticleEffect(new Position(x1, pos.y, z1), particleeffect);
                                }
                            }
                        }
                    }
                }
                updateParticleLastMillis = System.currentTimeMillis();
            }
        }
        return super.onUpdate(currentTick);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void spawnTo(Player player) {
        if (this.getNetworkId() == -1) {
            super.spawnTo(player);
        }

        if (!this.hasSpawned.containsKey(player.getLoaderId()) && this.chunk != null && player.usedChunks.containsKey(Level.chunkHash(this.chunk.getX(), this.chunk.getZ()))) {
            this.hasSpawned.put(player.getLoaderId(), player);
            player.dataPacket(this.createAddEntityPacket());
        }
    }

    @Override
    public void despawnFrom(Player player) {
        if (this.getNetworkId() == -1) {
            super.despawnFrom(player);
        }

        if (this.hasSpawned.containsKey(player.getLoaderId())) {
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = this.getId();
            player.dataPacket(pk);
            this.hasSpawned.remove(player.getLoaderId());
        }
    }

}