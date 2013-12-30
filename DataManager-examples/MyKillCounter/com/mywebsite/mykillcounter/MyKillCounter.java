package com.mywebsite.mykillcounter;

import org.bukkit.Bukkit;
/**
* Other imports. 
*/
import com.gmail.louis1234567890987654321.datamanagerapi.*;

public class MyKillCounter extends JavaPlugin implements Listener{
    
    DataManager<MyKills>myDataManager;  //Declaration of the variable myDataManager
    
    @Override
    public void onEnable(){
        myDataManager = DataManager.<MyKills>generateDataManager("MyKillCounter", this, new MyLoader());  //Generates a new DataManager by DataManagerAPI
        Bukkit.getPluginManager().registerEvents(this, this);  //Registers the events
    }
    
    @Override
    public void onDisable(){
        DataManager.removeAndSaveDataManagers(this);  //Removes and saves all data managers used by this plugin
    }
    
    @Override
    public void onCommand(CommandSender s,Command c,String lbl,String[]args){
        if(c.getName().equalsIgnoreCase("kills")){  //Checks if the command executed is "/kills"
            if(args.length<1){  //If no arguments were specified (just "/kills")
                if(s instanceof Player){  //If the one who executed this command is a player
                    KillsData d=myDataManager.getData(s.getName());  //Gets the data by the player's name
                    if(d!=null){  //Checks if the data is properly loaded
                        int kills=d.kills;
                        s.sendMessage("You have "+kills+" kills! Awesome!");
                    }else{  //Data is NOT properly loaded. This occurs VERY rarely. It most likely happens when an administrator of the server modified the data in an incorrect way.
                        s.sendMessage("The data is not properly loaded! Please contact the server administrators for help");
                    }
                }else{  //If the one who executed this command is not a player
                    s.sendMessage("Usage: /kills <player>");  //Shows help
                }
            }else{  //If arguments are more than one (like "/kills louis3325" or "/kills myign")
                MyKills k=myDataManager.getData(args[0]);  //Retrieves the data
                if(k==null){
                    s.sendMessage(args[0]+" hasn't been on this server!");  //The player has not been on this server before!
                }else{
                    s.sendMessage(args[1]+" has "+k.kills+" kills!");  //Displays the kills
                }
            }
        }else if(c.getName().equalsIgnoreCase("topkills"){  //If the command executed is "/topkills"
            List<Entry<String,Integer>>topData=myDataManager.getTopData("kills");  //Lists the top 5 data sorted in descending order
            if(topData==null){  //Data does not exist?
                s.sendMessage("No one has been killed in this server :o");
            }
            int count=1;  //This variable counts the ranking of the player
            for(Entry<String,Integer>e:topData){
                int kills=e.getValue();  //Retrieves the kills
                String ign=e.getKey();  //Retrieves the ingame name of a player
                s.sendMessage("#"+count+" Kills:"+kills+" IGN:"+ign);  //Displays it
                count++;  //Count increases by one
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(e.getPlayer().getKiller()!=null){
            MyKills k=myDataManager.getData(e.getPlayer().getKiller().getName()));  //Gets the data
            if(k!=null){  //Checks if the data is properly loaded
                k.kills++;  //Increases to the field "kills" defined by the author of the plugin
                myDataManager.onValueChange("kills", k, k.kills);  //Requests to sort the kills in desending order. This is NOT needed when sorting is not required! @see DataManager.getTopData()
            }else{  //Data is NOT properly loaded. This occurs VERY rarely. It most likely happens when an administrator of the server modified the data in an incorrect way.
                getLogger().warning("Data is not loaded!");
            }
        }
    }
}
