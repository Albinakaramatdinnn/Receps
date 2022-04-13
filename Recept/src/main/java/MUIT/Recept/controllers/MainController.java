package MUIT.Recept.controllers;

import MUIT.Recept.entities.Categories;
import MUIT.Recept.entities.Countries;
import MUIT.Recept.entities.Food;
import MUIT.Recept.repositories.CategoriesRepository;
import MUIT.Recept.repositories.CountriesRepository;
import MUIT.Recept.repositories.FoodRepository;
import MUIT.Recept.services.CategoryService;
import MUIT.Recept.services.CountryService;
import MUIT.Recept.services.FoodService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class MainController {

    @Value("${baseURL}")
    private String baseUrl;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/")
    public String indexPage(Model model) {
        List<Countries> countries = countryService.getAllCountry();
        model.addAttribute("countries", countries);
        return "index";
    }

    @GetMapping("/foods")
    public String foodsPage(Model model) {
        List<Food> fd = foodService.getAllFoods();
        model.addAttribute("listfood", fd);

        return "foods";
    }

    @GetMapping("/menu")
    public String menuPage() {
        return "menu";
    }

    @PostMapping("/search")
    public String Search(Model model,
                         @RequestParam(name = "name") String name) {
        Food food = foodService.getName(name);
        if(food!=null) {
            return "redirect:/details/" + food.getId();
        }
        return "redirect:/error";
    }

    @GetMapping("/details/{foodId}")
    public String details(Model model,
                          @PathVariable(name = "foodId") Long id) {

        Food food = foodService.getFood(id);
        model.addAttribute("food", food);

        List<Countries> countries = countryService.getAllCountry();
        model.addAttribute("countries", countries);

        List<Categories> categories = categoryService.getAllCategory();

        if(food.getCategories()!=null){
            categories.removeAll(food.getCategories());
        }

        model.addAttribute("category",categories);

        return "details";
    }


    @PostMapping("/reassigncategory")
    public String reassigncategory(@RequestParam(name="category_id")Long categoryId,
                                 @RequestParam(name = "food_id")Long foodId){
        Optional<Categories> opt = Optional.ofNullable(categoryService.getCategory(categoryId));
        if(opt.isPresent()){
            Categories cat = opt.get();

            Food food = foodService.getFood(foodId);
            if(food!=null){
                List<Categories> categories = food.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.remove(cat);
                food.setCategories(categories);
                foodService.saveFood(food);
                return "redirect:/details/" + foodId+"#removeTable";
            }

        }
        return "redirect:/details/";
    }


    @PostMapping("/assigncategory")
    public String assignCategory(@RequestParam(name="category_id")Long categoryId,
                                 @RequestParam(name = "food_id")Long foodId){
        Optional<Categories> opt = Optional.ofNullable(categoryService.getCategory(categoryId));
        if(opt.isPresent()){
            Categories cat = opt.get();

            Food food = foodService.getFood(foodId);
            if(food!=null){
                List<Categories> categories = food.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);
                food.setCategories(categories);
                foodService.saveFood(food);
                return "redirect:/details/" + foodId+"#addTable";
            }

        }
        return "redirect:/details/";
    }

    @PostMapping("/addfood")
    public String addFood(@RequestParam(name = "name") String name,
                          @RequestParam(name = "price") int price,
                          @RequestParam(name = "amount") int amount,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "weight") double weight,
                          @RequestParam(name = "country_id") Long country_id,
                          Model model) {
        Optional<Countries> opt = Optional.ofNullable(countryService.getCountry(country_id));
        if (opt.isPresent()) {
            Countries country = opt.get();
            Food food = new Food();
            food.setName(name);
            food.setPrice(price);
            food.setAmount(amount);
            food.setWeight(weight);
            food.setDescription(description);
            food.setCountry(country);
            foodService.saveFood(food);
            return "redirect:?success";
        }
        return "redirect:?error";
    }

    @PostMapping("/savefood")
    public String addFood(@RequestParam(name = "id") Long id,
                          @RequestParam(name = "name") String name,
                          @RequestParam(name = "price") int price,
                          @RequestParam(name = "amount") int amount,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "weight") double weight,
                          @RequestParam(name = "avatar") MultipartFile file,
                          @RequestParam(name = "country_id") Long country_id) {
        Optional<Countries> opt = Optional.ofNullable(countryService.getCountry(country_id));
        if (opt.isPresent()) {
            Countries country = opt.get();
            Food food = new Food();
            food.setId(id);
            food.setName(name);
            food.setPrice(price);
            food.setAmount(amount);
            food.setWeight(weight);
            food.setDescription(description);
            food.setCountry(country);
            if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
                try {
                    String fileName = DigestUtils.sha1Hex(id + "avatar_photo");
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(baseUrl + fileName + ".jpg");
                    Files.write(path, bytes);
                    food.setAvatar(fileName);
                    foodService.updateAvatar(food);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            foodService.saveFood(food);

            return "redirect:/details/" + id;
        }
        return "redirect:/";
    }



    @PostMapping("/deletefood")
    public String deleteFood(@RequestParam(name = "id")Long id){
        Food food = foodService.getFood(id);
        foodService.deleteFood(food);
        return "redirect:/";
    }
}
