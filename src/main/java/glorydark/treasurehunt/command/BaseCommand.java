package glorydark.treasurehunt.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import glorydark.treasurehunt.TreasureHuntMain;

import java.io.File;
import java.util.Objects;

public class BaseCommand extends Command {
    public BaseCommand(String command) {
        super(command);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.isPlayer()) {
            if (commandSender.isOp()) {
                if (strings.length == 0) {
                    commandSender.sendMessage(TreasureHuntMain.translateString("command_wrongUsage"));
                    return true;
                }
                Player player = (Player) commandSender;
                switch (strings[0]) {
                    case "help":
                        player.sendMessage(TreasureHuntMain.translateString("command_help_1"));
                        player.sendMessage(TreasureHuntMain.translateString("command_help_2"));
                        break;
                    case "create":
                        if (strings.length < 2) {
                            return false;
                        }
                        TreasureHuntMain.createTreasure(player, player.getLocation(), strings[1]);
                        break;
                    case "remove":
                        if (strings.length < 2) {
                            return false;
                        }
                        TreasureHuntMain.deleteTreasure(player, strings[1]);
                        break;
                    case "clearall":
                        File file = new File(TreasureHuntMain.path + "/players");
                        for (File one : Objects.requireNonNull(file.listFiles())) {
                            if (one.delete()) {
                                commandSender.sendMessage(TreasureHuntMain.translateString("clearall_success"));
                            }
                        }
                        player.sendMessage(TreasureHuntMain.translateString("clearall_success"));
                        break;
                }
            } else {
                commandSender.sendMessage(TreasureHuntMain.translateString("command_noPermission"));
            }
        } else {
            switch (strings[0]) {
                case "help":
                    commandSender.sendMessage(TreasureHuntMain.translateString("command_help_1"));
                    commandSender.sendMessage(TreasureHuntMain.translateString("command_help_2"));
                    break;
                case "create":
                case "remove":
                    commandSender.sendMessage(TreasureHuntMain.translateString("command_useInGame"));
                    break;
                case "clearall":
                    File file = new File(TreasureHuntMain.path + "/players");
                    for (File one : Objects.requireNonNull(file.listFiles())) {
                        if (one.delete()) {
                            commandSender.sendMessage(TreasureHuntMain.translateString("clearall_success"));
                        }
                    }
                    break;
            }
        }
        return true;
    }
}
