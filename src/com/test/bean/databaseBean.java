package com.test.bean;

import java.sql.*;

public class databaseBean {

    // ���ݿ������ַ���
    private String DBLocation = "jdbc:mysql://localhost:3306/spider?user=root&password=123&useUnicode=true&characterEncoding=GB2312";

    // ���ݿ���������
    private String DBDriver = "com.mysql.jdbc.Driver";

    // ���ݿ�����
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
     * ��������
     * 
     * @return ���ӽ���״̬
     */
    public String DBConnect() {
        String strExc = "Success!";

        try {
            Class.forName(DBDriver);
            conn = DriverManager.getConnection(DBLocation);
        } catch (ClassNotFoundException e) {
            strExc = "���ݿ�����û���ҵ���������ʾ��<br>" + e.toString();
        } catch (SQLException e) {

            strExc = "sql�����󣬴�����ʾ<br>" + e.toString();
        } catch (Exception e) {
            strExc = "������ʾ��<br>" + e.toString();
        }

        return (strExc);
    }

    /**
     * �Ͽ�����
     * 
     * @return ���ӶϿ�״̬
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
     * ͨ������sql���������һ�������
     * 
     * @param sql
     *            sql��ѯ���
     * @return ��ѯ�����
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
     * ִ��sql��䣬�����ؽ����ҳ��
     * 
     * @param sql
     *            sql��ѯ���
     * @param pageSize
     *            ÿҳ����ʾ�Ľ����Ŀ
     * @return ��ҳ��
     */
    public int getTotalPage(String sql, int pageSize) {

        ResultSet rs = null; // ��ѯ��Ľ����
        int totalRows = 0; // ���������Ŀ��
        int pages = 0; // ��ҳ��

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
     * �õ�ָ��ҳ��Ĳ�ѯ�����
     * 
     * @param sql
     *            sql��ѯ���
     * @param pageSize
     *            ÿҳ��ʾ����Ŀ��
     * @param pageNumber
     *            ҳ��
     * @return ��ѯ�����
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

                // ��������һ�н���ı�ţ�
                // �κα�Ŵ������maxrows�Ľ�����ᱻdrop
                s.setMaxRows(pageSize * pageNumber);

                rs = s.executeQuery(sql);
            } catch (SQLException e) {
            }

        // �������һҳ���һ������ı��
        absoluteLocation = pageSize * (pageNumber - 1);

        try {

            //�ý����rs��λ����ҳ֮ǰ�����һ�������
            for (int i = 0; i < absoluteLocation; i++) {
                rs.next();
            }
        } catch (SQLException e) {
        }
        
        // ���ظ�ҳҪ��ʾ�Ľ��
        return (rs);

    }

    /**
     * ִ��sql���
     * 
     * @param sql
     *            sql��ѯ���
     * @return ��ʶ״̬���ַ���
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