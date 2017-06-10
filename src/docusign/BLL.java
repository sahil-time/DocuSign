package docusign;

import java.util.ArrayList;

/**
 * BLL is for a SPECIFIC business. So this will be programmed according to
 * business requirements. MAINTAINS STATE.
 */
public class BLL {

    private String TYPE1 = "HOT ";
    private String TYPE2 = "COLD ";
    private String ERROR = "fail";
    private int END_CONDITION = 7;
    private int[] STABLE_TYPE1 = {1, 2, 4, 6};
    private int[] STABLE_TYPE2 = {1, 2, 3, 4, 5, 6};

    //Changing Data from Business to Business
    private void addData() //Add some Business Rules as well
    {
        /**
         * For Semantic Correctness, make sure the "desc" is NOT null.
         */

        //Hot Data
        comm.addAction(1, true, "Put on footwear", new int[]{6}, "sandals");
        comm.addAction(2, true, "Put on headwear", new int[]{4}, "sun visor");
        comm.addAction(3, true, "Put on socks", null, null);
        comm.addAction(4, true, "Put on shirt", null, "t-shirt");
        comm.addAction(5, true, "Put on jacket", null, null);
        comm.addAction(6, true, "Put on pants", null, "shorts");
        comm.addAction(7, true, "Leave house", STABLE_TYPE1, "leaving house");
        comm.addAction(8, true, "Take off pajamas", null, "Removing PJ's");

        //Cold Data
        comm.addAction(1, false, "Put on footwear", new int[]{3, 6}, "boots");
        comm.addAction(2, false, "Put on headwear", new int[]{4}, "hat");
        comm.addAction(3, false, "Put on socks", null, "socks");
        comm.addAction(4, false, "Put on shirt", null, "shirt");
        comm.addAction(5, false, "Put on jacket", new int[]{4}, "jacket");
        comm.addAction(6, false, "Put on pants", null, "pants");
        comm.addAction(7, false, "Leave house", STABLE_TYPE2, "leaving house");
        comm.addAction(8, false, "Take off pajamas", null, "Removing PJ's");
    }

    /*-------------------------NO CHANGES BELOW THIS----------------------------*/
    //Generalized Code Below for the given problem, modify the data and initial
    //conditions ABOVE BELOW CODE.
    private Commands comm;
    private ArrayList<Integer> commComplete;

    public BLL() {
        comm = new Commands();
        addData();
    }

    //PARSES data and PROCESSES the 1st CONDITION i.e. COMMAND 8
    public void output(String input) {

        if (input == null) {
            System.out.println(ERROR);
            return;
        }

        commComplete = new ArrayList<>();

        if (input.contains(TYPE1) || input.contains(TYPE2)) {

            String TYPE;
            boolean isHOT;

            if (input.contains(TYPE1)) {
                TYPE = TYPE1;
                isHOT = true;
            } else {
                TYPE = TYPE2;
                isHOT = false;
            }

            input = input.replace(TYPE, "").replace(TYPE + " ", "").replace(" ", "");
            String[] nums = input.split(",");
            if (Integer.parseInt(nums[0]) != 8) {
                System.out.println(ERROR);
                return;
            }

            print(nums, isHOT);

            System.out.print("\n");

            return;
        }

        System.out.println("WRONG INPUT TYPE");
    }

    //IMPLEMENTATION of Logic
    private void print(String[] nums, boolean isHot) {

        System.out.print(comm.getResponse(Integer.parseInt(nums[0]), isHot) + ", ");

        ArrayList<Integer> done = new ArrayList<>();

        done.add(Integer.parseInt(nums[0]));

        int i = 1;

        while (i < nums.length) {
            int temp = Integer.parseInt(nums[i]);

            //Check if END CONDITION IS REACHED
            if (temp == END_CONDITION) {
                if (checkIfDone(comm.getBefore(END_CONDITION, isHot))) {
                    System.out.print(comm.getResponse(END_CONDITION, isHot));
                    return;
                } else {
                    System.out.print(ERROR);
                    return;
                }
            }

            //Check if COMMAND is valid for the given TYPE
            if (!isValidCommand(temp, isHot)) {
                System.out.print(ERROR);
                return;
            }

            if (!done.contains(temp)) {

                ArrayList<Integer> before = comm.getBefore(temp, isHot);

                if (before != null) {
                    if (!checkIfDone(before)) {
                        System.out.print(ERROR);
                        return;
                    }
                }

                System.out.print(comm.getResponse(temp, isHot) + ", ");

                commComplete.add(temp);

                done.add(temp);

                i++;
            } else {
                System.out.print(ERROR);
                return;
            }
        }
    }

//Tested
    /**
     * CHECK if the Pre-Requisite Commands for the COMMAND are completed.
     */
    private boolean checkIfDone(ArrayList<Integer> before) {

        int i = 0;

        while (i < before.size()) {

            int temp = before.get(i);

            if (!commComplete.contains(temp)) {
                return false;
            }

            i++;
        }

        return true;
    }

    /**
     * Check if the INPUT value is a VALID COMMAND for the given Type. Search
     * through the ARRAY of VALID COMMANDS called "STABLE_TYPE1" or
     * "STABLE_TYPE2". No need to store in an ArrayList or HashMap for small
     * arrays. Once the arrays become very huge, and memory is not an issue, you
     * can store in ArrayList etc.
     */
    private boolean isValidCommand(int num, boolean isHot) {

        int[] test;

        if (isHot == true) {
            test = STABLE_TYPE1;
        } else {
            test = STABLE_TYPE2;
        }

        //Linear Search to find if 'num' exists in ONE of the STABLE ARRAYS
        for (int i = 0; i < test.length; i++) {
            if (num == test[i]) {
                return true;
            }
        }

        return false;
    }
}
