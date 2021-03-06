package sfpark.adf.tools.model.data.dO.pcoBeats;

import java.sql.ResultSet;

import java.sql.SQLException;

import sfpark.adf.tools.model.data.dO.BaseDO;

public class PCOBeatsDO extends BaseDO {

    public static String getDatabaseTableName() {
        return "PCO_BEATS";
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public PCOBeatsDO() {
        super();
    }

    private PCOBeatsDO(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.setBeatName(resultSet.getString(BEATNAME));
    }

    public static final String BEATNAME = "BEATNAME";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static PCOBeatsDO extract(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            return null;
        }

        return new PCOBeatsDO(resultSet);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private String BeatName;

    public void setBeatName(String BeatName) {
        this.BeatName = BeatName;
    }

    public String getBeatName() {
        return BeatName;
    }
}
