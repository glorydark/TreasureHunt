package glorydark.treasurehunt.variable;

import cn.nukkit.Player;
import glorydark.treasurehunt.TreasureHuntMain;
import tip.utils.variables.BaseVariable;

/**
 * @author glorydark
 * @date {2023/7/26} {17:21}
 */
public class TreasureVariable extends BaseVariable {

    public TreasureVariable(Player player) {
        super(player);
    }

    @Override
    public void strReplace() {
        this.addStrReplaceString("{treasurehunt_found_treasure_count}", String.valueOf(TreasureHuntMain.plugin.getPlayerCollect(player.getName()).size()));
        this.addStrReplaceString("{treasurehunt_max_collect_count}", String.valueOf(TreasureHuntMain.plugin.maxCollectCount));
    }
}
