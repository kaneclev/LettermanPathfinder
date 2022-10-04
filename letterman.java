// stores all of the dictionary data and provides methods for solving the puzzle

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.StringBuilder;

public class letterman {
    private config c;
    private int count;
    private int wordCheckedCount = 0;


    // variable to store our dictionary
    private ArrayList<wordInfo> dictionary;
    // integers to store indices
    private int beginIndex, endIndex;

    // constructor
    public letterman(config c) {
        this.c = c;
    }

    // method to read in our dictionary

    public void readDictionary() {
        // scanner to read the words through standard input
        Scanner in = new Scanner(System.in);

        // this int reads the number of words in the dict
        this.count = in.nextInt();
        in.nextLine();



        dictionary = new ArrayList<>(count);



        // while loop to read all of the words
        while (in.hasNextLine()) {
            String line = in.nextLine();

            // blank line means the end of the file; check for blank line

            if (line.length() == 0) {
                break;
            }
            //now we need to filter out comments
            if (line.charAt(0) == '/' && line.charAt(1) == '/') {
                continue;
            }
            // gives our beginIndex related to the dictionary at hand.

            if (line.equals(c.getBeginWord())) {
                beginIndex = dictionary.size();
                //System.out.println(beginIndex);
            }
            if (line.equals(c.getEndWord())) {
                endIndex = dictionary.size();
                //System.out.println(endIndex);
            }


            // read word after filter checking
            dictionary.add(new wordInfo(line));


        }

    }

    // get the number of words checked through the morph


    public int getCount() {
        return this.count;
    }

    // for checkpoint 1

    public void printElements() {
        System.out.println("Words in dictionary: " + getCount());

        for (wordInfo w : dictionary) {
            System.out.println(w.text);
        }
    }


    public void search() {
        // need a deque to keep track of our reachable collection
        // store the index of the word we are processing from our dictionary arraylist
        ArrayDeque<Integer> processing = new ArrayDeque<>();
        boolean success = false;

        // need to populate this with the starting word: see "project 1 algorithm notes" step 1
        // mark as visited, add to deque
        dictionary.get(beginIndex).visited = true;

        // is it stack mode
        if (c.isStackMode()) {
            processing.addFirst(beginIndex);
        }
        else {
            // no? then it must be queue mode
            processing.addLast(beginIndex);
        }
        wordCheckedCount++;

        // tells the system what the begin word is as we see the morphs
        System.out.println("Words in dictionary: " + this.count);
        //System.out.println(c.getBeginWord());
        int timesMorphed = 0;
        int j = 1;


        // look through dictionary; analyze sufficiently similar rules
        while (!processing.isEmpty()) {
            timesMorphed++;

            // makes deque empty; also assigns our curr index to the correct position after for-loop iteration
            int currIdx = processing.removeFirst();
            // curr is the word we are comparing and it is at index currIdx
            wordInfo curr = dictionary.get(currIdx);

            /* TODO: checkpoint 2 asks for us to simply show what word is being added to the stack or queue;
                not the exact path that gets us from the start point to the end point.
                So, we need to output the matches for our curr_word that are sufficiently similar:
                1: processing shy:
                    adding she
                    adding sky
                etc.
            */
            // check if checkpoint 2 is true
            if (c.isCheckpoint2()) {

                System.out.println(j + ": " +
                        "processing " + curr.text);
                j++;
            }


            for (int i = 0; i < dictionary.size(); i++) {



                if (i == currIdx) {
                    continue;
                }

                // comparable is the word we are comparing ourselves to
                wordInfo comparable = dictionary.get(i);



                // need a boolean method that compares the two words as we go
                if (!comparable.visited && sufficientlySimilar(curr.text, comparable.text)) {
                    wordCheckedCount++;

                    if (c.isCheckpoint2()) {
                        System.out.println("  adding " + comparable.text);
                    }

                    // if they are sufficiently similar, and it is not visited yet, set it as visited
                    comparable.visited = true;

                    /*
                    now we add to the deque differently depending on if we are in stack mode or queue mode
                    however, it is not necessary yet at this point to change our curr.
                    if stack mode, add another item on top of the stack
                    if queue mode, add another item to the back of the line
                    */
                    if (c.isStackMode()) {
                        // is this our word?
                        if (comparable.text.equals(c.getEndWord())) {
                            // success is true; we found the end word
                            success = true;
                            // we need to include the end word in our "morphed words" counter
                            timesMorphed++;
                            // add it to the queue to consider it finalized
                            processing.addFirst(i);

                            if (c.isCheckpoint2()) {
                                System.out.println("Solution, " + wordCheckedCount + " words checked.");
                            }
                            else {
                                System.out.println(c.getEndWord());
                                //add to the number of words checked based on if it was added to the stack/queue
                                System.out.println("Found the end word: " + c.getEndWord());

                            }
                            System.exit(0);
                        } else {
                            // it is not our end word, just add it to the data structure
                            processing.addFirst(i);

                        }
                    }
                    if (!c.isStackMode()) {
                        // is this our word?
                        if (comparable.text.equals(c.getEndWord())) {
                            // our boolean success is true; we found the end word.
                            success = true;
                            // we need to include the end word in our "morphed words" counter
                            timesMorphed++;

                            processing.addLast(i);
                            if (c.isCheckpoint2()) {
                                System.out.println("Solution, " + wordCheckedCount + " words checked.");

                            }
                            else {
                                // sequence of messages if the end word is found
                                System.out.println(c.getEndWord());
                                System.out.println("Found the end word: " + c.getEndWord());
                                System.out.println("Words checked: " + wordCheckedCount);
                                System.out.println("Words morphed: " + timesMorphed);

                            }



                            System.exit(0);
                        } else {
                            processing.addLast(i);
                        }
                    }


                } // otherwise: they are not sufficiently similar. carry on to the next word within our for loop



            } // back to while loop


        } // back to method
        if (!success) {
            System.out.println("No solution, " + wordCheckedCount + " words checked.");
        }



    }

    public boolean sufficientlySimilar(String curr, String comparable) {
        int i;
        int j = 0;
        int differenceIndex = 0;

        // for loop outside the rest of the modes so that we dont have to use multiple for loops within the method
        for (i = 0; i < curr.length(); i++) {
            if (curr.charAt(i) != comparable.charAt(i)) {

                // add 1 to j each time there is a difference in letters.
                j++;
                if (j == 2) {
                    differenceIndex = i;
                }
                }
            }

        if (c.isChangeMode()) {
            // first need to see if they are even the same length
            if (curr.length() == comparable.length()) {
                if (!c.isSwapMode() || j < 2) {
                    return j <= 1;
                }
            }
        }


        if (c.isSwapMode()) {
            if (curr.length() == comparable.length()) {
                if (j == 2) {
                    return curr.charAt(differenceIndex) == comparable.charAt(differenceIndex - 1)
                            && curr.charAt(differenceIndex - 1) == comparable.charAt(differenceIndex);
                }
            }
        }

        if (c.isLengthMode()) {
            if (curr.length() - comparable.length() == 1 || curr.length() - comparable.length() == -1) {
                // case which length mode is true; will only run given that swap and change mode do not work

            }

        }

        // required return statement; my cases, however, should fit every situation
        return false;
    }






    // inner class in order to store dictionary words.
    private class wordInfo {
        String text;
        // boolean checks to see if a word has been visited
        boolean visited;
        // as we add things into our arrayList, this constructor gives each object a couple of attributes:
        // the object is a string; and the object has a boolean value, either visited or not visited.
        public wordInfo(String text) {
            this.text = text;
            // constructor assumes no words have been visited.
            this.visited = false;

        }
    }


}
