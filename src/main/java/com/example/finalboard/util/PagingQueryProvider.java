package com.example.finalboard.util;

public class PagingQueryProvider {

    public static String getPagingQuery(String dbType, String baseQuery, int startRow, int pageSize) {
        switch (dbType.toUpperCase()) {
            case "ORACLE":
                return getOraclePagingQuery(baseQuery, startRow, pageSize);
            case "MYSQL":
            case "MARIADB":
                return getMySqlPagingQuery(baseQuery, startRow, pageSize);
            case "H2":
                return getH2PagingQuery(baseQuery, startRow, pageSize);
            case "POSTGRESQL":
                return getPostgrePagingQuery(baseQuery, startRow, pageSize);
            default:
                throw new IllegalArgumentException("Unsupported DB type: " + dbType);
        }
    }

    private static String getOraclePagingQuery(String baseQuery, int startRow, int pageSize) {
        int endRow = startRow + pageSize;
        return "SELECT * FROM (SELECT A.*, ROWNUM RNUM FROM (" + baseQuery +
                ") A WHERE ROWNUM <= " + endRow + ") WHERE RNUM > " + startRow;
    }

    private static String getMySqlPagingQuery(String baseQuery, int startRow, int pageSize) {
        return baseQuery + " LIMIT " + startRow + ", " + pageSize;
    }

    private static String getH2PagingQuery(String baseQuery, int startRow, int pageSize) {
        return baseQuery + " LIMIT " + pageSize + " OFFSET " + startRow;
    }

    private static String getPostgrePagingQuery(String baseQuery, int startRow, int pageSize) {
        return baseQuery + " LIMIT " + pageSize + " OFFSET " + startRow;
    }
}
