package dao;

import entity.ApplianceBrand;
import entity.ApplianceColor;
import entity.ApplianceName;
import entity.ElectricalAppliance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistance.DataSourceConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElectricalApplianceDaoImpl implements ElectricalApplianceDao {
    private static final Logger LOG = LogManager.getLogger(ElectricalApplianceDaoImpl.class);

    private static final String GET_APPLIANCE_BY_ID = "SELECT * FROM `el_appliance` WHERE id = ?";
    private static final String GET_ALL_APPLIANCE = "SELECT * FROM `el_appliance`";
    private static final String INSERT_INTO_APPLIANCE = String.format(
            "INSERT INTO el_appliance (%s, %s, %s, %s, %s, %s, %s, %s) VALUE (?, ?, ?, ?, ?, ?, ?, ?)",
            "weight",
            "color",
            "name",
            "brand",
            "power_consumption",
            "is_for_continuous_work",
            "is_connect_to_socket",
            "is_turn_on");
    private static final String UPDATE_APPLIANCE = String.format(
            "UPDATE el_appliance %s= ?, %s= ?, %s= ?, %s= ?, %s= ?, %s= ?, %s= ?, %s= ? WHERE %s = ?",
            "weight",
            "color",
            "name",
            "brand",
            "power_consumption",
            "is_for_continuous_work",
            "is_connect_to_socket",
            "is_turn_on",
            "id");
    private static final String DELETE_APPLIANCE = "DELETE FROM el_appliance WHERE id = ?";

    @Override
    public List<ElectricalAppliance> getAll() {
        LOG.info("\"getAll\" operation start.");
        List<ElectricalAppliance> result = new ArrayList<>();

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_ALL_APPLIANCE);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                result.add(extractElectricalAppliance(resultSet));
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting all entities");
        }
        return result;
    }

    public ElectricalAppliance getByID(int applianceId) {
        LOG.info("\"getById\" operation start for id={}.", applianceId);
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement statement = createStatementForActionById(GET_APPLIANCE_BY_ID, conn, applianceId);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return extractElectricalAppliance(resultSet);
            }

        } catch (SQLException e) {
            LOG.error("Get Electrical Appliance by id failed.", e);
        }
        return null;
    }

    @Override
    public boolean create(ElectricalAppliance entity) {
        LOG.info("\"create\" operation start for entity\n={}.", entity);
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createStatementWithMainFields(INSERT_INTO_APPLIANCE, conn, entity)) {
            int insertionResult = preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setId(generatedId);
                    LOG.info("Id for new entity record id={}.", generatedId);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return insertionResult == 1;
        } catch (SQLException e) {
            LOG.error("Could not create entity!!", e);
        }
        return false;
    }

    @Override
    public boolean update(ElectricalAppliance entity) {
        LOG.info("\"update\" operation start for entity\n={}.", entity);
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createStatementWithMainFields(UPDATE_APPLIANCE, conn, entity)) {
            preparedStatement.setInt(9, entity.getId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            LOG.error("Could not update entity!!", e);
        }
        return false;
    }

    @Override
    public boolean remove(ElectricalAppliance entity) {
        LOG.info("\"remove\" operation start for entity\n={}.", entity);
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = createStatementForActionById(DELETE_APPLIANCE, conn, entity.getId())) {

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            LOG.error("Could not remove entity!!", e);
        }
        return false;
    }

    private ElectricalAppliance extractElectricalAppliance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        double weight = resultSet.getDouble("weight");
        ApplianceColor color = ApplianceColor.valueOf(resultSet.getString("color").toUpperCase());
        ApplianceName name = ApplianceName.getValueByName(resultSet.getString("name"));
        ApplianceBrand brand = ApplianceBrand.valueOf(resultSet.getString("brand").toUpperCase());
        int powerCons = resultSet.getInt("power_consumption");
        boolean isForContWork = resultSet.getBoolean("is_for_continuous_work");
        boolean isConnect = resultSet.getBoolean("is_connect_to_socket");
        boolean isTurnOn = resultSet.getBoolean("is_turn_on");

        ElectricalAppliance appliance = new ElectricalAppliance(powerCons, isForContWork, weight, color, name, brand);
        appliance.setId(id);
        appliance.setTurnOn(isTurnOn);
        appliance.setConnectToSocket(isConnect);
        return appliance;
    }

    private PreparedStatement createStatementForActionById(String query, Connection conn, int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    private PreparedStatement createStatementWithMainFields(String query, Connection conn, ElectricalAppliance entity) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setDouble(1, entity.getWeight());
        statement.setString(2, entity.getColor().toString());
        statement.setString(3, entity.getName().toString());
        statement.setString(4, entity.getBrand().toString());
        statement.setInt(5, entity.getPowerConsumption());
        statement.setBoolean(6, entity.isForContinuousWork());
        statement.setBoolean(7, entity.isConnectToSocket());
        statement.setBoolean(8, entity.isTurnOn());
        return statement;
    }
}