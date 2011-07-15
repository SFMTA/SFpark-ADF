package sfpark.asset.manager.view.provider;

import sfpark.adf.tools.utilities.constants.RegularExpression;

public final class CommonUtils {

    /**
     * To avoid instantiation
     */
    private CommonUtils() {
        super();
    }

    /**
     * Checks if the combination of Post ID and Blockface ID works based on the
     * parity digit position
     *
     * @param postID
     * @param blockfaceID
     * @param parityDigitPosition
     * @return
     */
    public static boolean willPostIDAndBlockfaceIDWork(String postID,
                                                       String blockfaceID,
                                                       int parityDigitPosition) {

        boolean willWork =
            postID.matches(generatePostIDRegexForONStreet(blockfaceID,
                                                          parityDigitPosition));

        return willWork;
    }

    /**
     * Generates a Post ID regular expression based on Blockface ID and parity
     * digit position
     * Blockface ID = XXX XX P
     * Post ID      = XXX-XXDDD
     * 'P' is the parity
     * 'D' is [0-9], [02468] or [13579] based on 'P' and parity digit position
     *
     * @param blockfaceID
     * @param parityDigitPosition
     * @return
     */
    public static String generatePostIDRegexForONStreet(String blockfaceID,
                                                        int parityDigitPosition) {

        int parityDigit = Integer.parseInt(blockfaceID.substring(5, 6));
        String parityString = (parityDigit % 2 == 0) ? "[02468]" : "[13579]";

        StringBuffer regex = new StringBuffer();

        regex.append("(");
        regex.append("[" + blockfaceID.charAt(0) + "]");
        regex.append("[" + blockfaceID.charAt(1) + "]");
        regex.append("[" + blockfaceID.charAt(2) + "]");
        regex.append("-");
        regex.append("[" + blockfaceID.charAt(3) + "]");
        regex.append("[" + blockfaceID.charAt(4) + "]");

        switch (parityDigitPosition) {

        case 3:
            {
                regex.append(parityString);
                regex.append("[0-9]");
                regex.append("[0-9]");
            }
            break;

        case 2:
            {
                regex.append("[0-9]");
                regex.append(parityString);
                regex.append("[0-9]");
            }
            break;

        case 1:
            {
                regex.append("[0-9]");
                regex.append("[0-9]");
                regex.append(parityString);
            }
            break;

        default:
            {
                regex.append("[0-9]");
                regex.append("[0-9]");
                regex.append("[0-9]");
            }
            break;

        }

        regex.append(")");

        return regex.toString();
    }

    public static boolean willPostIDAndPayStationIDWork(String postID,
                                                        String msPayStationID,
                                                        String streetType) {

        if (!postID.matches(RegularExpression.POST_ID_REGEX.getRegEx()) ||
            !msPayStationID.matches(RegularExpression.POST_ID_REGEX.getRegEx())) {
            return false;
        }

        int position = (streetType.contains("ON")) ? 6 : 4;

        boolean willWork =
            postID.substring(0, position).equalsIgnoreCase(msPayStationID.substring(0,
                                                                                    position));

        return willWork;
    }

    /**
     * Generates a workable Post ID based on the provided Blockface ID
     * Blockface ID = XXX XX B
     * Post ID      = XXX-XX#P#
     * 'P' is 0 or 1 based on the parity of 'B'
     * Both 'P' and 'B' should be even/odd simultaneously
     *
     * @param blockfaceID
     * @return Generated Post ID
     */
    public static String generateWorkablePostIDFromBlockfaceID(String blockfaceID) {

        String postID =
            blockfaceID.substring(0, 3) + "-" + blockfaceID.substring(3, 5) +
            "0";

        int parityDigit = Integer.parseInt(blockfaceID.substring(5, 6));

        postID += (parityDigit % 2 == 0) ? "00" : "10";

        return postID;
    }

    /**
     * Extracts Block ID from the provided Blockface ID
     * Blockface ID = XXXXXB
     * Block ID     = XXXXX
     *
     * @param blockfaceID
     * @return
     */
    public static String extractBlockIDFromBlockfaceID(String blockfaceID) {

        String blockID = blockfaceID.substring(0, 5);

        return blockID;
    }

    /**
     * For ON Street, the Street Number is based on the Post ID as follows
     * Post ID   = XXX-YYYYX
     * Street Number = YYYY
     *
     * @param postID
     * @return Generated Street Number
     */
    public static int generateStreetNumFromPostID(String postID) {

        String postIDArr[] = postID.split("-");
        String streetNumStr = postIDArr[1].substring(0, 4);

        int streetNum = Integer.parseInt(streetNumStr);

        return streetNum;
    }
}
