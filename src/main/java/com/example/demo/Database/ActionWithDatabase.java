package com.example.demo.Database;

import java.sql.*;
import java.util.Scanner;

public class ActionWithDatabase {
    public Scanner scanner = new Scanner(System.in);
    private final String userName = "root";
    private final String password = "root";
    private final String connectionURL = "jdbc:mysql://localhost:3306/mydata";
    public String[] user = new String[10];
    private Connection connection;
    private String countMoneyRichUser = "";
    private int idRichUser = -1;

    public void getConnection() throws SQLException, ClassNotFoundException {
        Statement statement = connectToDatabase();
        System.out.println("This line");
    }

    public Statement connectToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(connectionURL, userName, password);
        Statement statement = connection.createStatement();

        System.out.println("Connect to dataBase successful");

        return statement;
    }

    public static double getNumberObjInTable(String sql, Statement statement) throws SQLException {
        ResultSet resultAccount = statement.executeQuery(sql);
        resultAccount.next();
        return resultAccount.getInt(1);
    }

    public void getUSerWhoHaveMostAverageMoney() throws SQLException {
        Statement statementForGetNumber = connection.createStatement();
        Statement statementForAccount = connection.createStatement();
        Statement statement = connection.createStatement();
        ResultSet resultUser = statement.executeQuery("select * from user");

        double moneyOfRichUser = -1;
        int countAllAccount = (int) getNumberObjInTable("select count(accountId) from account", statementForGetNumber);
        int countAllUser = (int) getNumberObjInTable("select count(userid) from user", statementForGetNumber);
        int idRichUser = -1;

        while (resultUser.next()) {
            ResultSet resultAccount = statementForAccount.executeQuery(
                    "select SUM(account) from account where userid = " + resultUser.getInt(1) + ";"
            );
            resultAccount.next();
            if (moneyOfRichUser < resultAccount.getInt(1)) {
                idRichUser = resultUser.getInt(1);
                moneyOfRichUser = resultAccount.getInt(1);
            }
        }
        countMoneyRichUser = "" + moneyOfRichUser;
        this.idRichUser = idRichUser;
        System.out.println(idRichUser + "|" + moneyOfRichUser);
    }

    public String getCountMoneyRichUser() {
        return countMoneyRichUser;
    }

    public String getInformationAboutRichUser() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where userid = " + idRichUser + ";");
        resultSet.next();
        return resultSet.getString(2) + " " + resultSet.getString(3);
    }
}
