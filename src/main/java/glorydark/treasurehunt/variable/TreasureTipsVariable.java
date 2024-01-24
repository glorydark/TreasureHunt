package glorydark.treasurehunt.variable;

import cn.nukkit.Player;
import glorydark.treasurehunt.TreasureHuntMain;
import tip.utils.variables.BaseVariable;

import java.util.Map;

/**
 * @author glorydark
 * @date {2023/7/26} {17:21}
 */
public class TreasureTipsVariable extends BaseVariable {

    public TreasureTipsVariable(Player player) {
        super(player);
    }

    @Override
    public void strReplace() {
        for (Map.Entry<String, TreasureCategoryData> entry : TreasureHuntMain.treasureCategoryDataMap.entrySet()) {
            this.addStrReplaceString("{treasurehunt_found_treasure_count}", String.valueOf(TreasureHuntMain.plugin.getPlayerCollect(player.getName(), entry.getKey()).size()));
            this.addStrReplaceString("{treasurehunt_max_collect_count}", String.valueOf(entry.getValue().getMaxCollectCount()));
        }
    }
}
