package dao;

import entity.ElectricalAppliance;

public interface ElectricalApplianceDao extends EntityDao<ElectricalAppliance> {
    ElectricalAppliance getByID(int applianceId);
}
