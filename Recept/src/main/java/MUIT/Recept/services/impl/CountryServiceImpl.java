package MUIT.Recept.services.impl;

import MUIT.Recept.entities.Countries;
import MUIT.Recept.repositories.CountriesRepository;
import MUIT.Recept.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public List<Countries> getAllCountry() {
        return countriesRepository.findAll();
    }

    @Override
    public Countries getCountry(Long id) {
        return countriesRepository.findByIdAndDeletedAtNull(id);
    }
}
