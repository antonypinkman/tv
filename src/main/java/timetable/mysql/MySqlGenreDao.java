package timetable.mysql;

import timetable.dao.AbstractJDBCDao;
import timetable.domain.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlGenreDao extends AbstractJDBCDao<Genre> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `genreName` FROM `Genre` WHERE `id` = ?;";
    }

    @Override
    public String getSelect2Query() {
        return "SELECT `id`, `genreName` FROM `Genre`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `Genre` (`genreName`) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `Genre` SET `genreName` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `Genre` WHERE `id`= ?;";
    }

    public MySqlGenreDao(Connection connection) {
        super(connection);
    }

    @Override
    public Genre insertNew() {
        var newObj = new Genre();
        return insert(newObj);
    }

    @Override
    protected List<Genre> parseResultSet(ResultSet rs) {
        LinkedList<Genre> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setGenreName(rs.getString("genreName"));
                result.add(genre);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, Genre object) {
        try {
            statement.setString(1, object.getGenreName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Genre object) {
        try {
            statement.setString(1, object.getGenreName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
