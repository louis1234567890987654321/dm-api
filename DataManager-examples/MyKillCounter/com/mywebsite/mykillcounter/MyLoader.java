package com.mywebsite.mykillcounter;

/**
* Other imports
*/

public class MyLoader extends DataLoader<MyKills>{  //This class loads data for your plugin.
//The DataLoader provided by DataManagerAPI finishes 90% of it.
//You just have to get and set values! :)
    @Override
    protected MyKills loadData(Player p, JavaPlugin plugin) {
        int kills=getInt("kills",0); //Gets the number of kills. Returns 0 if not specified.
        MyKills data=new MyKills(getDataManager(),p,plugin);  //Constructs a new MyKills instance
        //It will be disposed after the player disconnects
        data.kills=kills;  //Sets the kills
        return data;
    }

    @Override
    protected MyKills getNewData(Player p, JavaPlugin plugin) {
        return new MyKills(getDataManager(),p,plugin);  //Just create a new instance of the object
    }
    
