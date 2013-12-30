package com.mywebsite.mykillcounter;

/**
* Other imports
*/

public class MyKills extends Data{  //Declares a class named "MyKills".
//It inherits Data to retrieve data when loading and saving
//Data saving is called in onSave();
//DataManagerAPI handles the rest of it! :)
    
    int kills; //Declares a field called "kills" that is an integer (int)
    //int deaths;
    
    protected MyKills(DataManager<MyKills>manager, Player p, JavaPlugin plug){  //The constructor. It MUST be in this format.
//protected [class name](DataManager<[class name]>manager,Player p,JavaPlugin plug)
        super(manager, p, plug);  //This line is ESSENTIAL. You MUST include this.
        kills=0;  //Sets the kills to 0
    }

    @Override
    protected void onSave(){
        set("kills", kills);  //Saves the data to a MySQL server or a file. This supports multiple values.
        //The set function supports [int,boolean,String,long,double].
        //Strings MUST ONLY contain A-Z,a-z,1-9.
        //set("deaths", deaths);
    }
}
