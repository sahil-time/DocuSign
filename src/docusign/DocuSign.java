package docusign;

public class DocuSign {

    public static void main(String[] args) {
        
        BLL bl = new BLL();
        
        /**
         * From what I understand, the Output DOES NOT need to terminate with
         * "Leaving House". The subject may as well be in the house, provided
         * he satisfies the BUSINESS LOGIC RULES. So I have NOT "failed" the tests
         * where the INPUT does not CONTAIN '7'. I hope that is okay.
         * 
         * Also, ONCE '7' is read, the program successfully terminates, it will NOT
         * read the SUBSEQUENT INPUTS because the Subject is OUT OF THE HOUSE.
         * This is the assumption I have made.
         */
        
        //Given Inputs
        bl.output("HOT 8, 6, 4, 2, 1, 7");
        bl.output("COLD 8, 6, 3, 4, 2, 5, 1, 7");
        bl.output("HOT 8, 6, 6");
        bl.output("HOT 8, 6, 3");
        bl.output("COLD 8, 6, 3, 4, 2, 5, 7");
        bl.output("COLD 6");
        
        //Custom Inputs
        bl.output("WARM 8");
        bl.output("HOT 9");
        bl.output("HOT 8,4");
        bl.output("HOT 8, 6, 4, 2, 1, 7, 8");
        bl.output("");
        bl.output(null);
    }
}


