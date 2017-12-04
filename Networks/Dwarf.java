public class Dwarf {
    public Dwarf(int money) {
        wealth = money;
        if(!didThis) {
            for(int i = 0; i < connections.length; i++) {
                connections[i][i] = true;
            }
            didThis = true;
        }
    }
    public int wealth;
    public static Dwarf[] clan = new Dwarf[7];
    public int[] sendTo = new int[clan.length];
    public int[] getFrom = new int[clan.length];//shows connections to each dwarf
    //make sure the one corresponding to its own index never gets done
    private static boolean[][] connections = new boolean[clan.length][clan.length];//tracks connections
    private static boolean didThis = false;
    //row = start dwarf, column = end
    public static boolean isConnected() {
        int[] clanSum = new int[clan.length - 1];
        for(int i = 0; i < clan.length; i++) {
            for(int j = 0; j < (clan.length - 1); j++) {
                clanSum[j]+=clan[i].sendTo[j];
                clanSum[j]+=clan[i].getFrom[j];
            }
        }
        boolean sumGood = true;
        for(int i = 0; i < clanSum.length; i++) {
            if(clanSum[i] < 3)
            sumGood = false;
        }
        if(sumGood)
        return true;
        else
        return false;
    }
    public static void makeConnection() {
        int startDwarfIndx = 0;
        int endDwarfIndx = 0;
        while((startDwarfIndx == endDwarfIndx) || (connections[startDwarfIndx][endDwarfIndx])) {
            endDwarfIndx = DwarfTester.randInt(0, (clan.length - 1));
            startDwarfIndx = DwarfTester.randInt(0, (clan.length - 1));
        }//re-generates a start/end combo until it hits one that it hasn't done yet
        //and isn't from a Dwarf to itself
        connections[startDwarfIndx][endDwarfIndx] = true;
        clan[startDwarfIndx].sendTo[endDwarfIndx]++;
        clan[endDwarfIndx].getFrom[startDwarfIndx]++;
    }
    public static void iterate() {//every iteration, each Dwarf will send a random fraction of its
        //money to a random Dwarf to whom it is connected
        int sum;
        int[] whichFriend = new int[clan.length];//will store, for each dwarf, which other dwarf they
        //are sending money to
        int[] howMuch = new int[clan.length];//as above: stores how much money they're sending
        /*System.out.println("Testing howMuch array:");
        for(int i = 0; i < clan.length; i++) {
            System.out.println(howMuch[i]);
        }
        System.out.println("Done testing.");*/
        for(int i = 0; i < clan.length; i++) {//picks a benefactor for each Dwarf
            sum = 0;
            for(int j = 0; j < clan[i].sendTo.length; j++) {
                sum += clan[i].sendTo[j];
            }
            int whichOne = DwarfTester.randInt(0, sum);
            whichFriend[i] = whichOne;
        }
        for(int i = 0; i < clan.length; i++) {//picks an amount for each Dwarf
            double randFraction = DwarfTester.randInt(40,60)/100.0;
            int amount = (int)(randFraction * clan[i].wealth);
            howMuch[i] = amount;
            if(amount >= clan[i].wealth)
            System.out.println("This could be the source of the possible error");
        }
        for(int i = 0; i < clan.length; i++) {//sends the money to each Dwarf
            int countdown = whichFriend[i];
            Dwarf recipient = interpret(clan, clan[i].sendTo, countdown);
            if(recipient.wealth == 0)
            System.out.println("Possible error!");/*Make it so dwarves can never have 0 money
            otherwise to test?
            There is almost certainly a problem here; trying to confirm*/
            clan[i].wealth -= howMuch[i];
            recipient.wealth += howMuch[i];
        }
    }
    private static Dwarf interpret(Dwarf[] theClan, int[] recipients, int whichOne) {
        for(int j = 0; j < recipients.length; j++) {
            if(whichOne == 0)
            return theClan[j];
            whichOne -= recipients[j];/*sometimes whichOne is starting at 0! That's the issue
            Fixed by adding a checker @beginning of loop*/
            /*System.out.print("Recipients index "+j+":");
            System.out.println(" whichOne equals "+whichOne);*/
            if(whichOne == 0)
            return theClan[j];
        }
        System.out.println("Emergeny 0 Dwarf was put out!");
        return new Dwarf(0);/*Make sure changing this from -1 to 0 did not mess up the program.*/
        /*This method really needs to be edited, the emergency return of a 0 Dwarf is being used
           way too often.*/
    }
    public static void audit() {
        for(int i = 0; i < clan.length; i++) {
            System.out.println("Dwarf "+(i+1)+" has "+clan[i].wealth+" gold coins.");
        }
        System.out.println("");
    }
    public static void showNetwork() {/** This isn't working, figure it out*/
        for(int i = 0; i < connections.length; i++) {
            for(int j = 0; j < connections[i].length; j++) {
                System.out.print("Dwarf " + (i+1) + " gives money to ");
                StringBuffer sb = new StringBuffer();
                if(connections[i][j]) {
                    sb.append("Dwarf " + (j+1) + ", ");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.deleteCharAt(sb.length()-1);
                System.out.println(sb);
            }
        }
    }
}