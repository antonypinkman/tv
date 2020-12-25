package timetable.mysql;

import timetable.dao.AbstractJDBCDao;
import timetable.domain.Channel;
import timetable.domain.Genre;
import timetable.domain.TVProgram;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlTVProgramDao extends AbstractJDBCDao<TVProgram> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `channel_id`, `title`, `beginTime`, `genre_id` FROM `TVProgram` WHERE id = ?;";
    }

    @Override
    public String getSelect2Query() {
        return "SELECT `id`, `channel_id`, `title`, `beginTime`, `genre_id` FROM `TVProgram`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `TVProgram` (`channel_id`, `title`, `beginTime`, `genre_id`) VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `TVProgram` SET `channel_id` = ?, `title`  = ?, `beginTime` = ?, `genre_id` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `TVProgram` WHERE `id`= ?;";
    }

    public MySqlTVProgramDao(Connection connection) {
        super(connection);
    }

    @Override
    public TVProgram insertNew() {
        var newObj = new TVProgram();
        return insert(newObj);
    }

    @Override
    protected List<TVProgram> parseResultSet(ResultSet rs) {
        LinkedList<TVProgram> result = new LinkedList<>();
        try {
            while (rs.next()) {
                TVProgram TVProgram = new TVProgram();
                TVProgram.setId(rs.getInt("id"));
                TVProgram.setChannel(
                        MySqlDaoFactory.instance.getDao(MySqlDaoFactory.instance.getContext(), Channel.class).
                                getObjectById(rs.getInt("channel_id"))
                );
                TVProgram.setTitle(rs.getString("title"));
                TVProgram.setBeginTime(rs.getDate("beginTime"));
                TVProgram.setGenre(
                        MySqlDaoFactory.instance.getDao(MySqlDaoFactory.instance.getContext(), Genre.class).
                                getObjectById(rs.getInt("genre_id"))
                );
                result.add(TVProgram);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, TVProgram object) {
        try {
            Date sqlDate = convert(object.getBeginTime());
            int groupId = (object.getGenre() == null || object.getGenre().getId() == null) ? -1
                    : object.getGenre().getId();
            int channel_id = (object.getChannel()== null || object.getChannel().getId() == null) ? -1
                    : object.getChannel().getId();
            statement.setInt(1, channel_id);
            statement.setString(2, object.getTitle());
            statement.setDate(3, sqlDate);
            statement.setInt(4, groupId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, TVProgram object) {
        try {
            Date sqlDate = convert(object.getBeginTime());
            int groupId = (object.getGenre() == null || object.getGenre().getId() == null) ? -1
                    : object.getGenre().getId();
            int channel_id = (object.getChannel()== null || object.getChannel().getId() == null) ? -1
                    : object.getChannel().getId();
            statement.setInt(1, channel_id);
            statement.setString(2, object.getTitle());
            statement.setDate(3, sqlDate);
            statement.setInt(4, groupId);
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected java.sql.Date convert(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
}
