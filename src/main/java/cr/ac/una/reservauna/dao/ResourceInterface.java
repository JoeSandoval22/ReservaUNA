/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Resource;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */
public interface ResourceInterface {
    boolean insertResource(Resource resource);
    boolean deleteResource(Resource resource);
    boolean upgradeResource(Resource resource);
    Resource findResourceById(int id);
    ListView<Resource> getAllResources();
}
