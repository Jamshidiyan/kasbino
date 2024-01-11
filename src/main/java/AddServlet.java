import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddServlet", value = "/AddServlet")
public class AddServlet extends HttpServlet {
    final ConfigDataBase db = new ConfigDataBase();
    User user;
    public static HashMap<Integer, User> userList = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db.connect();
        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        int age = Integer.parseInt(req.getParameter("age"));
        user = new User(fName, lName, age);
        insertQueryUser(db, user);
        resp.sendRedirect("firstPage.jsp");
        db.closeConnection();

    }

    private static void insertQueryUser(ConfigDataBase configDataBase, User user) {
        String insertQuery = "INSERT INTO user (fName, lName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = configDataBase.getConnection()
                .prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFName());
            preparedStatement.setString(2, user.getLName());
            preparedStatement.setInt(3, user.getAge());
            int row = preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("create success id= " + generatedId);
                }
            }

            System.out.println(row + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        configDataBase.closeConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        db.connect();
        getUser(db);
        int id = Integer.parseInt(req.getParameter("id"));
        user = userList.get(id);
        req.setAttribute("user", user);
        RequestDispatcher rDis = req.getRequestDispatcher("ShowUserInformation.jsp");
        rDis.forward(req, resp);
        resp.sendRedirect("ShowUserInformation.jsp\n");
        db.closeConnection();
    }

    private static void getUser(ConfigDataBase configDataBase) {
        String getUser = "SELECT * FROM user";
        try (PreparedStatement preparedStatement =
                     configDataBase.getConnection().prepareStatement(getUser);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                int age = resultSet.getInt("age");
                User user = new User(fName, lName, age);
                user.setUserId(id);
                userList.put(user.getUserId(), user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
