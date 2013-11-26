package com.test.bean;

import java.sql.*;

public class databaseBean {

    // 数据库连接字符串
    private String DBLocation = "jdbc:mysql://localhost:3306/spider?user=root&password=123&useUnicode=true&characterEncoding=GB2312";

    // 数据库连接驱动
    private String DBDriver = "com.mysql.jdbc.Driver";

    // 数据库连接
    private Connection conn = null;

    public databaseBean() {
    }

    public String getDBLocation() {
        return (DBLocation);
    }

    public void setDBLocation(String location) {
        DBLocation = location;
    }

    public String getDBDriver() {
        return (DBDriver);
    }

    public void setDBDriver(String driver) {
        DBDriver = driver;
    }

    public Connection getconn() {
        return (conn);
    }

    public void setconn(Connection conn) {
        this.conn = conn;
    }

    /**
     * 建立连接
     * 
     * @return 连接建立状态
     */
    public String DBConnect() {
        String strExc = "Success!";

        try {
            Class.forName(DBDriver);
            conn = DriverManager.getConnection(DBLocation);
        } catch (ClassNotFoundException e) {
            strExc = "数据库驱动没有找到，错误提示：<br>" + e.toString();
        } catch (SQLException e) {

            strExc = "sql语句错误，错误提示<br>" + e.toString();
        } catch (Exception e) {
            strExc = "错误提示：<br>" + e.toString();
        }

        return (strExc);
    }

    /**
     * 断开连接
     * 
     * @return 连接断开状态
     */
    public String DBDisconnect() {
        String strExc = "Success!";

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            strExc = e.toString();
        }

        return (strExc);
    }

    /**
     * 通过传入sql语句来返回一个结果集
     * 
     * @param sql
     *            sql查询语句
     * @return 查询结果集
     * @throws SQLException
     * @throws Exception
     */
    public ResultSet query(String sql) throws SQLException, Exception {

        ResultSet rs = null;

        if (conn == null) {
            DBConnect();
        }

        if (conn == null) {
            rs = null;
        } else {
            try {
                Statement s = conn.createStatement();
                rs = s.executeQuery(sql);
            } catch (SQLException e) {
                throw new SQLException("Cound not execute query.");
            } catch (Exception e) {
                throw new Exception("Cound not execute query.");
            }
        }

        return (rs);
    }

    /**
     * 执行sql语句，并返回结果的页数
     * 
     * @param sql
     *            sql查询语句
     * @param pageSize
     *            每页所显示的结果数目
     * @return 总页数
     */
    public int getTotalPage(String sql, int pageSize) {

        ResultSet rs = null; // 查询后的结果集
        int totalRows = 0; // 结果集中条目数
        int pages = 0; // 总页数

        if (conn == null) {
            DBConnect();
        }

        if (conn == null) {
            rs = null;
        } else {
            try {
                Statement s = conn.createStatement();

                rs = s.executeQuery(sql);
                while (rs.next())
                    totalRows++;
            } catch (SQLException e) {
            }
            pages = ((totalRows - 1) / pageSize + 1);
            rs = null;
        }
        return pages;
    }

    /**
     * 得到指定页码的查询结果集
     * 
     * @param sql
     *            sql查询语句
     * @param pageSize
     *            每页显示的条目数
     * @param pageNumber
     *            页码
     * @return 查询结果集
     */
    public ResultSet getPagedRs(String sql, int pageSize, int pageNumber) {
    	
        ResultSet rs = null;
        
        int absoluteLocation;
        if (conn == null) {
            DBConnect();
        }

        if (conn == null) {
            rs = null;
        } else
            try {
                Statement s = conn.createStatement();

                // 计算出最后一行结果的编号，
                // 任何编号大于这个maxrows的结果都会被drop
                s.setMaxRows(pageSize * pageNumber);

                rs = s.executeQuery(sql);
            } catch (SQLException e) {
            }

        // 计算出上一页最后一个结果的编号
        absoluteLocation = pageSize * (pageNumber - 1);

        try {

            //让结果集rs定位到本页之前的最后一个结果处
            for (int i = 0; i < absoluteLocation; i++) {
                rs.next();
            }
        } catch (SQLException e) {
        }
        
        // 返回该页要显示的结果
        return (rs);

    }

    /**
     * 执行sql语句
     * 
     * @param sql
     *            sql查询语句
     * @return 标识状态的字符串
     */
    public String execute_sql(String sql) {
        String strExc = "Success!";

        if (conn != null) {
            try {
                PreparedStatement update;
                update = conn.prepareStatement(sql);
                update.execute();
            } catch (SQLException e) {
                strExc = e.toString();
            } catch (Exception e) {
                strExc = e.toString();
            }
        } else {
            strExc = "Connection Lost!";
        }
        return (strExc);

    }
}