package net.wazim.jordan.persistence;

import net.wazim.jordan.domain.BluRay;

import java.util.List;

public interface BluRayDatabase {

    public BluRay findBluRayByName(String name);

    public BluRay findBluRayById(int id);

    public BluRay getFirstBluRay();

    public List<BluRay> getAllBluRays();

    public List<BluRay> getAllInterestingBluRays();

    public void saveBluRay(BluRay bluRay);

    public void clearDownDatabase();

    void removeInterest(String name);

    void updateBluray(BluRay bluRay);

    void deleteBluRay(BluRay bluRay);
}
