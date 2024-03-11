package glorydark.treasurehunt.entity;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.Location;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import glorydark.treasurehunt.TreasureHuntMain;
import glorydark.treasurehunt.variable.TreasureCategoryData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Treasure {

    private String position;

    private double yawSpeed;

    private TreasureEntity entity;

    private boolean isKnockback;

    private double scale;

    private boolean isParticleMarked;

    private boolean explodeParticleAfterFound;

    private List<String> messages;

    private List<String> commands;

    private String identifier;
    private Skin skin;

    private String category;

    public Treasure(String identifier, String category, String position, Skin skin, double yawSpeed, double scale, boolean isKnockback, boolean isParticleMarked, boolean explodeParticleAfterFound, List<String> messages, List<String> commands) {
        this.identifier = identifier;
        this.category = category;
        this.position = position;
        this.skin = skin;
        this.yawSpeed = yawSpeed;
        this.scale = scale;
        this.isKnockback = isKnockback;
        this.isParticleMarked = isParticleMarked;
        this.commands = commands;
        this.messages = messages;
        this.explodeParticleAfterFound = explodeParticleAfterFound;
    }

    public boolean spawnByStringPos() {
        Location pos = getLocationByString(position);
        if (pos.getLevel() == null) {
            return false;
        }
        try {
            pos.getChunk().load();
            CompoundTag tag = Entity.getDefaultNBT(pos);
            tag.putCompound("Skin", new CompoundTag().putByteArray("Data", skin.getSkinData().data).putString("ModelId", skin.getSkinId()));
            TreasureEntity entity = new TreasureEntity(pos.getChunk(), tag, pos, this, yawSpeed);
            entity.setLevel(pos.getLevel());
            entity.setSkin(skin);
            entity.setScale((float) scale);
            entity.setNameTagVisible(false);
            entity.setImmobile();
            entity.spawnToAll();
            entity.scheduleUpdate();
            this.entity = entity;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Location getLocationByString(String str) {
        String[] strings = str.split(":");
        if (strings.length < 4) {
            return new Location();
        }
        Location pos = new Location(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]), Double.parseDouble(strings[2]), Server.getInstance().getLevelByName(strings[3]));
        switch (strings.length) {
            case 7:
                pos.headYaw = Double.parseDouble(strings[6]);
            case 6:
                pos.pitch = Double.parseDouble(strings[5]);
            case 5:
                pos.yaw = Double.parseDouble(strings[4]);
                break;
        }
        return pos;
    }

    public List<String> getPlayerCollect(String player) {
        Config config = new Config(TreasureHuntMain.path + "/players/" + player + ".yml", Config.YAML);
        return new ArrayList<>(config.getStringList("list"));
    }

    @Override
    public String toString() {
        return "Treasure{" +
                "position='" + position + '\'' +
                ", yawSpeed=" + yawSpeed +
                ", entity=" + entity +
                ", isKnockback=" + isKnockback +
                ", scale=" + scale +
                ", isParticleMarked=" + isParticleMarked +
                ", messages=" + messages +
                ", commands=" + commands +
                ", name='" + identifier + '\'' +
                ", skin=" + skin +
                '}';
    }

    public TreasureCategoryData getTreasureCategory() {
        return TreasureHuntMain.treasureCategoryDataMap.getOrDefault(category, null);
    }

}
