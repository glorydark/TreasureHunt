package glorydark.treasurehunt.variable;

import cn.nukkit.Player;
import com.smallaswater.npc.data.RsNpcConfig;
import com.smallaswater.npc.variable.BaseVariableV2;
import glorydark.treasurehunt.TreasureHuntMain;
import tip.utils.variables.BaseVariable;

import java.util.Map;

/**
 * @author glorydark
 * @date {2023/7/26} {17:21}
 */
public class TreasureRsNPCVariable extends BaseVariableV2 {

    @Override
    public void onUpdate(Player player, RsNpcConfig rsNpcConfig) {
        for (Map.Entry<String, TreasureCategoryData> entry : TreasureHuntMain.treasureCategoryDataMap.entrySet()) {
            this.addVariable("{treasurehunt_found_treasure_count}", String.valueOf(TreasureHuntMain.plugin.getPlayerCollect(player.getName(), entry.getKey()).size()));
            this.addVariable("{treasurehunt_max_collect_count}", String.valueOf(entry.getValue().getMaxCollectCount()));
        }
    }
}
