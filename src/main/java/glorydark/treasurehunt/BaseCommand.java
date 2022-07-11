package glorydark.treasurehunt;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;

import java.io.File;

public class BaseCommand extends Command {
    public BaseCommand(String command) {
        super(command);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender.isPlayer()){
            if(commandSender.isOp()){
                if(strings.length == 0){ commandSender.sendMessage(MainClass.translateString("command_wrongUsage")); return true; }
                Player player = (Player) commandSender;
                switch (strings[0]){
                    case "help":
                        player.sendMessage(MainClass.translateString("command_help_1"));
                        player.sendMessage(MainClass.translateString("command_help_2"));
                        break;
                    case "create":
                        MainClass.createTreasure(player, player.getPosition());
                        break;
                    case "remove":
                        MainClass.deleteTreasure(player, player.getPosition());
                        break;
                    case "clearall":
                        File file = new File(MainClass.path+"/players");
                        if(file.delete()){
                            player.sendMessage(MainClass.translateString("clearall_success"));
                        }
                        break;
                    case "test":
                        FormWindowSimple simple = new FormWindowSimple(strings[1], strings[2]);
                        simple.addButton(new ElementButton(strings[3]));
                        ((Player) commandSender).showFormWindow(simple);
                        break;
                }
            }else{
                commandSender.sendMessage(MainClass.translateString("command_noPermission"));
            }
        }else{
            commandSender.sendMessage(MainClass.translateString("command_useInGame"));
        }
        return true;
    }
}
