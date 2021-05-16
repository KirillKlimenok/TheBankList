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

//    private void createNewUser(Statement statement) throws SQLException {
//        System.out.println("Please, enter name new User: ");
//        String name1 = scanner.next();
//        System.out.println("Please, enter surname new User: ");
//        String surname1 = scanner.next();
//
//        statement.execute("insert into user(name, surename) value('" + name1 + "','" + surname1 + "')");
//    }
//
//    private void addUserBankAccount(Statement statement) throws SQLException {
//        user = showAllUsers(statement);
//        System.out.println("Please, enter id user to whom you want to open an account");
//        int idUser = scanner.nextInt();
//        float countMoney = (float) (200 + Math.random() * 1000);
//        statement.execute("insert into account(account, userid) value('" + countMoney + "','" + idUser + "')");
//    }
//
//    public String[] showAllUsers(Statement statement) throws SQLException {
//        ResultSet resultSet = statement.executeQuery("select * from user");
//        int countUsers = statement.executeUpdate("select count(accountId) from user");
//        String[] strings = new String[countUsers];
//        int i = 0;
//        while (resultSet.next()) {
//            strings[1] = resultSet.getString(1) + "   " + resultSet.getString(2) + "   " + resultSet.getString(3);
//            System.out.print(resultSet.getString(1) + "   ");
//            System.out.print(resultSet.getString(2) + "   ");
//            System.out.print(resultSet.getString(3) + "\n__________________\n");
//        }
//
//        return strings;
//    }

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
