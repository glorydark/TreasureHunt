package glorydark.treasurehunt.variable;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author glorydark
 * @date {2023/7/26} {17:38}
 */
@AllArgsConstructor
@Data
public class TreasureCategoryData {

    private int maxCollectCount;

    private List<String> rewardCommands;

    private List<String> messages;

}
