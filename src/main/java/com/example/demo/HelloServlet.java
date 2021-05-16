package com.example.demo;

import com.example.demo.Database.ActionWithDatabase;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    public String inf;


    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        ActionWithDatabase actionWithDatabase = connectToDatabase();
        inf = actionWithDatabase.getCountMoneyRichUser();
        PrintWriter out = response.getWriter();

        try {

            out.print("<script type=\"text/javascript\">\n" +
                    "            function callServlet() {\n" +
                    "                var change = document.getElementById('" + "userInformation" + "');" + "\n" +
                    "                change.innerHTML = '" + actionWithDatabase.getInformationAboutRichUser() + "';" +
                    "            }\n" +
                    "        </script>\n");

            out.print("<script type=\"text/javascript\">\n" +
                    "            function getTotalAmountOfMoney() {\n" +
                    "                var change = document.getElementById('" + "totalMoney" + "');" + "\n" +
                    "                change.innerHTML = '" + actionWithDatabase.getCountMoneyRichUser() + "';" +
                    "            }\n" +
                    "        </script>\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        out.println("<div>");
        out.println("<button id = userInformation style = \"width = 100px;\" onclick = '" + "callServlet()" + "'>" + "get user information" + "</button>");
        out.println("<button id = totalMoney style = \"width = 100px;\" onclick = '" + "getTotalAmountOfMoney()" + "'>" + "get total amount of money" + "</button>");
        out.println("</div>");
        //out.println("<br>" + inf + "</br>");
        out.println("</body></html>");
    }

    public ActionWithDatabase connectToDatabase() {
        ActionWithDatabase actionWithDatabase = new ActionWithDatabase();
        try {
            actionWithDatabase.getConnection();
            actionWithDatabase.getUSerWhoHaveMostAverageMoney();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return actionWithDatabase;
    }


    public String getInf() {
        return inf;
    }

    public void destroy() {
    }
}
