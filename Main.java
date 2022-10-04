


public class Main {

    //FIXME: for some reason SLUGS seems to fail me on every actual test, as if it isn't even able to run to begin with

    public static void main(String[] args) {
        /*
        modes that are required:
        [--stack or --queue for storage type]
        --change [change one letter into another]
        --swap [swap two letters]
        --length [change the length of the word by either adding or removing a letter]
        --output (either Word or Modification format); if not specified, use word output format
        --begin <word>, -b <word>, specifying what word we are going to begin with
        --end <word>, -e<word>, specifying what word we want to end up on
        --help, -h, sending a message to the user to describe the program's purpose and the user's options
        --checkpoint1, -x, stop the program after completing checkpoint 1
        --checkpoint2, -y, stop the program after completing checkpoint 2
         */

        // config option in order to access configurations
        config c = new config(args);
        // to construct the letterman puzzle solver
        letterman l = new letterman(c);

        // process our input
        if (c.isCheckpoint2()) {
            l.readDictionary();
            l.search();
        }
        else if (c.isCheckpoint1()){
            l.readDictionary();
            l.printElements();
        }
        else {
            l.readDictionary();
            l.search();
        }












        // some info on the specifications that are turned on when the program runs
        /*
        if (c.routingModeSet) {
            if (c.isStackMode()) {
                System.out.println("stack mode");
            }
            else {
                System.out.println("queue mode");
                }
        }

        if (c.isChangeMode()) {
            System.out.println("Changemode is enabled.");
        }
        if (c.isCheckpoint1()) {
            System.out.println("Checkpoint 1 is enabled.");
            l.printElements();

        }
        if (c.isWordOutput()) {
            System.out.println("Word output mode.");
        }
        else {
            System.out.println("Word modification mode.");
        }

         */


        // outputs the word we are starting with
        //if (c.getBeginWord() != null) {
           // System.out.println("The beginning word is: " + c.getBeginWord());

       // }

    }

}
