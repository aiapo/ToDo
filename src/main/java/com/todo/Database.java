package com.todo;

import java.sql.*;
public class Database {

    // Create a database connection
    protected Connection connection;

    // Import the Query class (this class will format the queries)
    protected QueryBuilder query;

    // Constructor to create connection to specified database
    public Database(String databaseFile) throws SQLException{
        connection = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
    }

    // Execute the queries
    // This takes the prepared query (ex. DELETE FROM ? WHERE ? = ?)
    // And the params and creates a prepared statement for efficiency
    // Then executes...
    // Special cases: CREATE and SELECT
    private int execute(String query, Object[] params) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(query);
        if (params != null){
            int index = 1;
            for(Object param : params){
                ps.setObject(index, param);
                index++;
            }
        }
        return ps.executeUpdate();
    }

    // Creates a new table based on an object of attributes
    public int create(String table, Object[] params) throws SQLException{
        query = new QueryBuilder();

        query.create(table).attributes(params);

        return execute(query.stringifyQuery(), null);
    }

    // Inserts into a table an object of values
    public int insert(String table, Object[] params) throws SQLException{
        query = new QueryBuilder();

        query.insert(table).values(params);

        return execute(query.stringifyQuery(), params);
    }


    // Updates tuple(s) in a table based on where condition
    public int update(String table, String[] attribute, String condition, Object[] params) throws SQLException{
        query = new QueryBuilder();

        query.update(table).set(attribute).where(condition);

        return execute(query.stringifyQuery(), params);
    }

    public ResultSet select(String table, Object[] columns, Object[] params) throws SQLException{
        return this.select(table, columns, "", params);
    }

    public ResultSet select(String table, Object[] columns, String condition, Object[] params) throws SQLException{
        query = new QueryBuilder();
        query.select(columns).from(table).where(condition);

        PreparedStatement ps = connection.prepareStatement(query.stringifyQuery());
        if(params != null){
            int index = 1;
            for(Object param : params){
                ps.setObject(index, param);
                index++;
            }
        }

        ResultSet rs = ps.executeQuery();
        return rs;
    }

    // Deletes from a table based on a where condition
    public int delete(String table, String requirement, Object[] param) throws SQLException{
        query = new QueryBuilder();
        query.delete(table).where(requirement);
        return execute(query.stringifyQuery(), param);
    }
}
