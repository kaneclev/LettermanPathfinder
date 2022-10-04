import gnu.getopt.LongOpt;
import gnu.getopt.Getopt;

import java.util.Objects;
/*
Purpose: Store and process all configurations for project1
 */

public class config {
    // need member variables to store our settings
    // check for stack v. queue
    private boolean stackMode;
    // morph modes
    private boolean changeMode;
    private boolean swapMode;
    private boolean lengthMode;
    //output mode
    private boolean wordOutput = true; // true if -w is set, false if -modification is set

    // beginWord and endWord gets the string of the word that we will begin and end with
    private String beginWord;
    private String endWord;


    // checkpoint booleans
    private boolean checkpoint1;
    private boolean checkpoint2;

    // error checking variable
    boolean routingModeSet = false;
    boolean isSpecified = false;
    // xor-- either stack OR queue; exclusive or
    boolean xor = false;

    boolean helpRequest = false;


    // constructor, also processes the command line arguments
    public config(String[] args) {
        // getopt processing
        LongOpt[] longOptions = {
                new LongOpt("stack", LongOpt.NO_ARGUMENT, null, 's'),
                new LongOpt("queue", LongOpt.NO_ARGUMENT, null, 'q'),

                new LongOpt("change", LongOpt.NO_ARGUMENT, null, 'c'),
                new LongOpt("swap", LongOpt.NO_ARGUMENT, null, 'p'),
                new LongOpt("length", LongOpt.NO_ARGUMENT, null, 'l'),
                new LongOpt("output", LongOpt.REQUIRED_ARGUMENT, null, 'o'),

                new LongOpt("begin", LongOpt.REQUIRED_ARGUMENT, null, 'b'),
                new LongOpt("end", LongOpt.REQUIRED_ARGUMENT, null, 'e'),

                new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),

                new LongOpt("checkpoint1", LongOpt.NO_ARGUMENT, null, 'x'),
                new LongOpt("checkpoint2", LongOpt.NO_ARGUMENT, null, 'y')
        };
        // construct the getopt object
        Getopt g = new Getopt("Project1", args, "sqcplo:b:e:hxy", longOptions);
        // nicer "error parsing"
        g.setOpterr(true);

        int choice;
        // now loop while a choice is available
        while ((choice = g.getopt()) != -1) {
            switch (choice) {
                case 's':
                    // checks for identical arguments
                    if (stackMode && routingModeSet) {
                        System.err.println("Only use one of each argument.");
                        System.exit(1);

                    }
                    if (xor) {
                        // we know that there is stack AND queue set
                        System.err.println("Either Stack OR Queue; Not both.");
                        System.exit(1);
                    }
                    xor = true;
                    stackMode = true;
                    routingModeSet = true;
                    break;
                case 'q':
                    // checks for identical arguments
                    if (!stackMode && routingModeSet) {
                        System.err.println("Only use one of each argument.");
                        System.exit(1);
                    }
                    stackMode = false;
                    routingModeSet = true;
                    xor = true;

                    break;
                case 'c':
                    changeMode = true;

                    break;
                case 'p':
                    swapMode = true;

                    break;
                case 'l':
                    lengthMode = true;

                    break;
                case 'o':
                    // need to read the required string arguments
                    String mode = g.getOptarg();
                    if (!Objects.equals(mode, "M") && !Objects.equals(mode, "W")) {
                        // then there is a problem
                        System.err.println("Only W and M are supported for modes. ");
                        System.exit(1);
                    }
                    // defaults the result to be W output as per the spec
                    wordOutput = mode.equals("W");
                    isSpecified = true;

                    break;
                case 'b':
                    // beginning word as specified above

                    beginWord = g.getOptarg();
                    // checks to make sure this is specified.
                    isSpecified = true;

                    break;
                case 'e':
                    endWord = g.getOptarg();
                    isSpecified = true;
                    break;

                case 'h':
                    helpRequest = true;
                    printHelp();
                    break;

                case 'x':
                    checkpoint1 = true;
                    break;

                case 'y':

                    checkpoint2 = true;


                    break;


                default:
                    System.err.println("Unknown command line argument option: " + choice);
                    System.exit(1);

            }


        } // back to constructor

        // todo: check that all required arguments are specified. (like beginning word).
        // todo: also make sure that we can't do stack mode twice in the command line
        // todo: also make sure that there is only stack OR queue specified, not both.

        // every letterman function requires:
        // --stack/queue --begin(word) --end(word) --morphMode(c,l,s) --output(word/modification)

        // the only cases where we really need to worry about the errors are
        // when it isn't checkpoint 1 and help isn't requested.
        if (!checkpoint1 && !helpRequest) {
            if (!routingModeSet) {
                System.err.println("You must specify a routing mode; stack or queue.");
                System.exit(1);
            }

            if (!isSpecified) {
                System.err.println("You must specify an additional argument.");
                System.exit(1);
            }
        }
    }

    private void printHelp () {
        System.out.println("This program modifies words in a variety of ways " +
                "depending on your specifications. " +
                "Specifications include the data structure to use, " +
                "the words we will modify, " +
                "how we modify them, " +
                "and in what way the output will be shown.");
    }

    public boolean isStackMode() {

        return stackMode;
    }

    public boolean isChangeMode() {
        return changeMode;
    }

    public boolean isCheckpoint1() {
        return checkpoint1;
    }

    public boolean isCheckpoint2() {
        return checkpoint2;
    }

    public boolean isLengthMode() {
        return lengthMode;
    }

    public boolean isSwapMode() {
        return swapMode;
    }

    public boolean isWordOutput() {
        return wordOutput;
    }

    public String getBeginWord() {
        return beginWord;
    }

    public String getEndWord() {
        return endWord;
    }
}
