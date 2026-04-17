/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Place;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */ 
public interface PlaceInterface extends ResourceInterface {
    boolean insertPlace(Place place);
    boolean deletePlace(Place place);
    boolean upgradePlace(Place place);
    Place findPlaceById(int id);
    ListView<Place> getAllPlaces();
}
