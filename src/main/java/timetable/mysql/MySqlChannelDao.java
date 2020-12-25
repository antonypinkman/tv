package timetable.mysql;

import timetable.dao.AbstractJDBCDao;
import timetable.domain.Channel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlChannelDao extends AbstractJDBCDao<Channel> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `title` FROM `Channel` WHERE `id` = ?;";
    }

    @Override
    public String getSelect2Query() {
        return "SELECT `id`, `title` FROM `Channel`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `Channel` (`title`) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `Channel` SET `title` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `Channel` WHERE `id`= ?;";
    }

    public MySqlChannelDao(Connection connection) {
        super(connection);
    }

    @Override
    public Channel insertNew() {
        var newObj = new Channel();
        return insert(newObj);
    }

    @Override
    protected List<Channel> parseResultSet(ResultSet rs) {
        LinkedList<Channel> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Channel genre = new Channel();
                genre.setId(rs.getInt("id"));
                genre.setTitle(rs.getString("title"));
                result.add(genre);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, Channel object) {
        try {
            statement.setString(1, object.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Channel object) {
        try {
            statement.setString(1, object.getTitle());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
