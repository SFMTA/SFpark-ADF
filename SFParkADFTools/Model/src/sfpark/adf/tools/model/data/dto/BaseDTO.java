package sfpark.adf.tools.model.data.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * DTO (Data Transfer Object) is used to carry database data to-and-from the
 * display UI and database.
 *
 * Read/Write are both allowed
 */
public abstract class BaseDTO {

    public static final String CREATED_DT = "CREATED_DT";
    public static final String LAST_UPD_DT = "LAST_UPD_DT";
    public static final String LAST_UPD_USER = "LAST_UPD_USER";
    public static final String LAST_UPD_PGM = "LAST_UPD_PGM";

    private Timestamp CreatedDate;
    private Timestamp LastUpdatedDate;
    private String LastUpdatedUser;
    private String LastUpdatedProgram;

    protected BaseDTO() {
        super();
    }

    protected BaseDTO(BaseDTO DTO) {
        super();

        this.setCreatedDate(DTO.getCreatedDate());
        this.setLastUpdatedDate(DTO.getLastUpdatedDate());
        this.setLastUpdatedProgram(DTO.getLastUpdatedProgram());
        this.setLastUpdatedUser(DTO.getLastUpdatedUser());
    }

    protected BaseDTO(ResultSet resultSet) throws SQLException {
        super();

        this.CreatedDate = resultSet.getTimestamp(CREATED_DT);
        this.LastUpdatedDate = resultSet.getTimestamp(LAST_UPD_DT);
        this.LastUpdatedProgram = resultSet.getString(LAST_UPD_PGM);
        this.LastUpdatedUser = resultSet.getString(LAST_UPD_USER);

    }

    public void setCreatedDate(Timestamp CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public Timestamp getCreatedDate() {
        return CreatedDate;
    }

    public void setLastUpdatedDate(Timestamp LastUpdatedDate) {
        this.LastUpdatedDate = LastUpdatedDate;
    }

    public Timestamp getLastUpdatedDate() {
        return LastUpdatedDate;
    }

    public void setLastUpdatedUser(String LastUpdatedUser) {
        this.LastUpdatedUser = LastUpdatedUser;
    }

    public String getLastUpdatedUser() {
        return LastUpdatedUser;
    }

    public void setLastUpdatedProgram(String LastUpdatedProgram) {
        this.LastUpdatedProgram = LastUpdatedProgram;
    }

    public String getLastUpdatedProgram() {
        return LastUpdatedProgram;
    }
}
