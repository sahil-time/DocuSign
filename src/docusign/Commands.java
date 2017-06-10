package docusign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Commands are GENERIC. They are NOT dependent to business rules. For e.g. in the
 * "addAction" function, the description ("desc") can be null, now this makes no
 * sense as a Command, but that is decided and taken care of in the BLL NOT here.
 * This class, simply deals with the Data Structure used to Organize Information
 * and its manipulation. It does not decide what is SEMANTICALLY correct or what is not.
 */

/**
 * DOES NOT MAINTAIN STATE.
 */
public class Commands {

    private Map<Integer, Action> hot;
    private Map<Integer, Action> cold;

    //Initialize HashMaps when Object is created to save memory before being used
    public Commands() {
        hot = new HashMap<>();
        cold = new HashMap<>();
    }

    //Add an entry into the hashmap
    public int addAction(int num, boolean isHot, String desc, int[] before, String response) {  //before - DEPENDENCY
        if (isHot == true) {
            if (hot.containsKey(num)) {
                System.out.println("Command Not Added, Already Exists - " + num);
                return -1;
            }
            hot.put(num, new Action(desc, before, response));
        } else {
            if (cold.containsKey(num)) {
                System.out.println("Command Not Added, Already Exists - " + num);
                return -1;
            }
            cold.put(num, new Action(desc, before, response));
        }

        return 0;
    }

    public String getDesc(int num, boolean isHot) {
        if (isHot == true) {

            if (hot.containsKey(num)) {
                return hot.get(num).getDesc();
            }
            return null;
        }

        if (cold.containsKey(num)) {
            return cold.get(num).getDesc();
        }
        return null;
    }

    public String getResponse(int num, boolean isHot) {
        if (isHot == true) {

            if (hot.containsKey(num)) {
                return hot.get(num).getResponse();
            }
            return null;
        }

        if (cold.containsKey(num)) {
            return cold.get(num).getResponse();
        }
        return null;
    }

    public ArrayList<Integer> getBefore(int num, boolean isHot) {
        if (isHot == true) {

            if (hot.containsKey(num)) {
                return hot.get(num).getBefore();
            }
            return null;
        }

        if (cold.containsKey(num)) {
            return cold.get(num).getBefore();
        }
        return null;
    }

    //Not Used
    public int removeAction(int num, boolean isHot) {
        if (isHot == true) {
            if (hot.containsKey(num)) {
                hot.remove(num);
                return 0;
            }
            return -1;
        }
        if (cold.containsKey(num)) {
            cold.remove(num);
            return 0;
        }
        return -1;
    }
}

class Action {

    private String desc;
    private ArrayList<Integer> before = new ArrayList<>(); //Command that needs to be executed before this action
    private String response;

    public Action(String desc, int[] before, String response) {
        this.desc = desc;

        if (before != null) {
            for (int index = 0; index < before.length; index++) {
                this.before.add(before[index]);
            }
        } else {
            this.before = null;
        }

        this.response = response;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<Integer> getBefore() {
        return before;
    }

    public void setBefore(int[] before) {

        this.before = new ArrayList<>();

        for (int index = 0; index < before.length; index++) {
            this.before.add(before[index]);
        }
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
