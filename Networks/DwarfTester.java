import java.util.*;
public class DwarfTester {
    public static void main(String [] args) {
        for(int i = 0; i < Dwarf.clan.length; i++) {
            Dwarf.clan[i] = new Dwarf(randInt(0, 99));
        }
        while(!Dwarf.isConnected()) {
            Dwarf.makeConnection();
        }
        Dwarf.showNetwork();
        //Dwarf.audit();
        for(int i = 0; i < 20; i++) {
            Dwarf.iterate();
            //Dwarf.audit();
        }
    }
    public static int randInt(int min, int max) {
        double rand = Math.random();
        double rand2 = rand * (max - min + 1);
        int result = (int)(rand2 + min);
        return result;
    }
}
//Does system behavior change if the same set of connections starts with different amounts?
//If I vary the number of Dwarves?