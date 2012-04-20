package sfpark.adf.tools.model.provider;

final class StatementGenerator {

    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";

    private static final String WHERE = " WHERE ";
    private static final String IN = " IN ";
    private static final String GROUP_BY = " GROUP BY ";
    private static final String ORDER_BY = " ORDER BY ";

    private static final String UPDATE = "UPDATE ";
    private static final String SET = " SET ";

    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String VALUES = " VALUES ";

    private static final String DELETE_FROM = "DELETE FROM ";

    private static final String OPEN_PARENTHESES = " ( ";
    private static final String CLOSE_PARENTHESES = " ) ";

    private static final String LIKE = " LIKE ";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String COMMA = " , ";
    private static final String QUESTION_MARK = " ? ";
    private static final String LESS_THAN = " < ";
    private static final String LESS_THAN_OR_EQUAL_TO = " <= ";
    private static final String GREATER_THAN = " > ";
    private static final String GREATER_THAN_OR_EQUAL_TO = " >= ";
    private static final String EQUAL_TO = " = ";

    private static final String DISTINCT = " DISTINCT ";
    private static final String UPPER = " UPPER ";
    private static final String MAX = " MAX ";

    private static final String GEOM = "GEOM";

    private static final String SDO_RELATE = " sdo_relate ";
    private static final String NULL = " null ";

    private static class MDSYS {
        private static final String SDO_POINT_TYPE = "MDSYS.SDO_POINT_TYPE";
        private static final String SDO_GEOMETRY = "MDSYS.SDO_GEOMETRY";
    }

    /**
     * To avoid instantiation
     */
    private StatementGenerator() {
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // OTHER FUNCTION HELPERS

    public static String getSDORelateFunctionString() {
        StringBuffer statement = new StringBuffer(SDO_RELATE);

        statement.append(OPEN_PARENTHESES);
        statement.append(GEOM);
        statement.append(COMMA);
        statement.append(getSDOGeometryString());
        statement.append(COMMA);
        statement.append("'mask=ANYINTERACT'");
        statement.append(CLOSE_PARENTHESES);
        statement.append(" ='TRUE' ");

        return statement.toString();
    }

    private static String getSDOGeometryString() {
        StringBuffer statement = new StringBuffer(MDSYS.SDO_GEOMETRY);

        statement.append(OPEN_PARENTHESES);
        statement.append("2001");
        statement.append(COMMA);
        statement.append("8307");
        statement.append(COMMA);
        statement.append(getSDOPointTypeString());
        statement.append(COMMA);
        statement.append(NULL);
        statement.append(COMMA);
        statement.append(NULL);
        statement.append(CLOSE_PARENTHESES);

        return statement.toString();
    }

    private static String getSDOPointTypeString() {
        StringBuffer statement = new StringBuffer(MDSYS.SDO_POINT_TYPE);

        statement.append(OPEN_PARENTHESES);
        statement.append(QUESTION_MARK);
        statement.append(COMMA);
        statement.append(QUESTION_MARK);
        statement.append(COMMA);
        statement.append(NULL);
        statement.append(CLOSE_PARENTHESES);

        return statement.toString();
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // OPERATOR HELPERS

    protected static String inOperator(final String ColumnName,
                                       final String Values) {
        return comparisonOperator(ColumnName, IN, Values);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // MULTI CONDITION OPERATOR HELPERS

    protected static String andOperator(final String... strings) {
        return multiConditionOperator(AND, strings);
    }

    protected static String orOperator(final String... strings) {
        return multiConditionOperator(OR, strings);
    }

    protected static String commaOperator(final String... strings) {
        return multiConditionOperator(COMMA, strings);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SINGLE CONDITION OPERATOR HELPERS

    protected static String likeOperator(final String LHS) {
        return likeOperator(LHS, QUESTION_MARK);
    }

    protected static String likeOperator(final String LHS, final String RHS) {
        return singleConditionOperator(LHS, LIKE, RHS);
    }

    protected static String lessThanOperator(final String LHS) {
        return lessThanOperator(LHS, QUESTION_MARK);
    }

    protected static String lessThanOperator(final String LHS,
                                             final String RHS) {
        return singleConditionOperator(LHS, LESS_THAN, RHS);
    }

    protected static String lessThanOrEqualToOperator(final String LHS) {
        return lessThanOrEqualToOperator(LHS, QUESTION_MARK);
    }

    protected static String lessThanOrEqualToOperator(final String LHS,
                                                      final String RHS) {
        return singleConditionOperator(LHS, LESS_THAN_OR_EQUAL_TO, RHS);
    }

    protected static String greaterThanOperator(final String LHS) {
        return greaterThanOperator(LHS, QUESTION_MARK);
    }

    protected static String greaterThanOperator(final String LHS,
                                                final String RHS) {
        return singleConditionOperator(LHS, GREATER_THAN, RHS);
    }

    protected static String greaterThanOrEqualToOperator(final String LHS) {
        return greaterThanOrEqualToOperator(LHS, QUESTION_MARK);
    }

    protected static String greaterThanOrEqualToOperator(final String LHS,
                                                         final String RHS) {
        return singleConditionOperator(LHS, GREATER_THAN_OR_EQUAL_TO, RHS);
    }

    protected static String equalToOperator(final String LHS) {
        return equalToOperator(LHS, QUESTION_MARK);
    }

    protected static String equalToOperator(final String LHS,
                                            final String RHS) {
        return singleConditionOperator(LHS, EQUAL_TO, RHS);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // FUNCTION HELPERS

    protected static String distinctFunction(final String Value) {
        return function(DISTINCT, Value);
    }

    protected static String upperFunction(final String Value) {
        return function(UPPER, Value);
    }

    protected static String maxFunction(final String Value) {
        return function(MAX, Value);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // SELECT HELPERS

    protected static String selectStatement(final String Attributes,
                                            final String TableName) {

        return select(Attributes, TableName, null, null, null);
    }

    protected static String selectStatement(final String Attributes,
                                            final String TableName,
                                            final String Where) {

        return select(Attributes, TableName, Where, null, null);
    }

    protected static String selectStatement(final String Attributes,
                                            final String TableName,
                                            final String Where,
                                            final String OrderBy) {

        return select(Attributes, TableName, Where, OrderBy, null);
    }

    protected static String selectStatement(final String Attributes,
                                            final String TableName,
                                            final String Where,
                                            final String OrderBy,
                                            final String GroupBy) {

        return select(Attributes, TableName, Where, OrderBy, GroupBy);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // INSERT HELPERS

    protected static String insertStatement(final String TableName,
                                            final String Columns,
                                            final String Values) {
        return insert(TableName, Columns, Values);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // UPDATE HELPERS

    protected static String updateStatement(final String TableName,
                                            final String SetColumnValues) {
        return update(TableName, SetColumnValues, null);
    }

    protected static String updateStatement(final String TableName,
                                            final String SetColumnValues,
                                            final String Where) {
        return update(TableName, SetColumnValues, Where);
    }

    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++
    // DELETE HELPERS

    protected static String deleteStatement(final String TableName,
                                            final String Where) {
        return delete(TableName, Where);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static String select(final String Attributes,
                                 final String TableName, final String Where,
                                 final String OrderBy, final String GroupBy) {
        StringBuffer statement = new StringBuffer();

        statement.append(SELECT);
        statement.append(Attributes);
        statement.append(FROM);
        statement.append(TableName);

        if (Where != null) {
            statement.append(WHERE);
            statement.append(Where);
        }

        if (GroupBy != null) {
            statement.append(GROUP_BY);
            statement.append(GroupBy);
        }

        if (OrderBy != null) {
            statement.append(ORDER_BY);
            statement.append(OrderBy);
        }

        return statement.toString();
    }

    private static String insert(final String TableName, final String Columns,
                                 final String Values) {
        StringBuffer statement = new StringBuffer();

        statement.append(INSERT_INTO);
        statement.append(TableName);
        statement.append(OPEN_PARENTHESES);
        statement.append(Columns);
        statement.append(CLOSE_PARENTHESES);
        statement.append(VALUES);
        statement.append(OPEN_PARENTHESES);
        statement.append(Values);
        statement.append(CLOSE_PARENTHESES);

        return statement.toString();
    }

    private static String update(final String TableName,
                                 final String SetColumnValues,
                                 final String Where) {
        StringBuffer statement = new StringBuffer();

        statement.append(UPDATE);
        statement.append(TableName);
        statement.append(SET);
        statement.append(SetColumnValues);

        if (Where != null) {
            statement.append(WHERE);
            statement.append(Where);
        }

        return statement.toString();
    }

    private static String delete(final String TableName, final String Where) {
        StringBuffer statement = new StringBuffer();

        statement.append(DELETE_FROM);
        statement.append(TableName);

        if (Where != null) {
            statement.append(WHERE);
            statement.append(Where);
        }

        return statement.toString();
    }

    private static String singleConditionOperator(final String LHS,
                                                  final String Operator,
                                                  final String RHS) {
        StringBuffer statement = new StringBuffer();

        statement.append(LHS);
        statement.append(Operator);
        statement.append(RHS);

        return statement.toString();
    }

    private static String multiConditionOperator(final String Operator,
                                                 final String... strings) {
        StringBuffer statement = new StringBuffer();

        int numOfStrings = strings.length;

        for (int i = 0; i < numOfStrings - 1; i++) {
            statement.append(strings[i]);
            statement.append(Operator);
        }

        statement.append(strings[numOfStrings - 1]);

        return statement.toString();
    }

    private static String comparisonOperator(final String ColumnName,
                                             final String Operator,
                                             final String Values) {
        StringBuffer statement = new StringBuffer();

        statement.append(ColumnName);
        statement.append(Operator);
        statement.append(OPEN_PARENTHESES);
        statement.append(Values);
        statement.append(CLOSE_PARENTHESES);

        return statement.toString();
    }

    private static String function(final String Function, final String Value) {
        StringBuffer statement = new StringBuffer();

        statement.append(Function);
        statement.append(OPEN_PARENTHESES);
        statement.append(Value);
        statement.append(CLOSE_PARENTHESES);

        return statement.toString();
    }
}
