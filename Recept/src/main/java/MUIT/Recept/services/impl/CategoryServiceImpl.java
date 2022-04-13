package MUIT.Recept.services.impl;

import MUIT.Recept.entities.Categories;
import MUIT.Recept.repositories.CategoriesRepository;
import MUIT.Recept.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<Categories> getAllCategory() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return categoriesRepository.getById(id);
    }
}
