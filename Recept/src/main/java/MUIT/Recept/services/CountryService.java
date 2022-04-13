package MUIT.Recept.services;

import MUIT.Recept.entities.Countries;
import MUIT.Recept.entities.Food;

import java.util.List;

public interface CountryService {
    List<Countries> getAllCountry();

    Countries getCountry(Long id);
}
