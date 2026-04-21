/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Resource;
import java.util.List;

/**
 *
 * @author User
 */
public interface ResourceInterface {
    boolean insertResource(Resource resource);
    boolean deleteResource(Resource resource);
    boolean updateResource(Resource resource);
    Resource findResourceById(int id);
    List<Resource> getAllResources();
}
