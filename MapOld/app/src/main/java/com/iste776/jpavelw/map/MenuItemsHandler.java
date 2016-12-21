package com.iste776.jpavelw.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpavelw on 11/15/16.
 */

public class MenuItemsHandler {

    private final List<MenuItemsHandler.Category> categoriesList = new ArrayList<>();
    private final List<String> categoriesArray = new ArrayList<>();

    public MenuItemsHandler() {
        MenuItemsHandler.Category category1 = new MenuItemsHandler.Category(5, "Category 1");
        MenuItemsHandler.Category category2 = new MenuItemsHandler.Category(6, "Category 2");
        MenuItemsHandler.Category category3 = new MenuItemsHandler.Category(7, "Category 3");
        MenuItemsHandler.Category category4 = new MenuItemsHandler.Category(8, "Category 4");
        MenuItemsHandler.Category category5 = new MenuItemsHandler.Category(9, "Category 5");

        this.categoriesList.add(category1);
        this.categoriesList.add(category2);
        this.categoriesList.add(category3);
        this.categoriesList.add(category4);
        this.categoriesList.add(category5);

        this.categoriesArray.add(category1.getName());
        this.categoriesArray.add(category2.getName());
        this.categoriesArray.add(category3.getName());
        this.categoriesArray.add(category4.getName());
        this.categoriesArray.add(category5.getName());
        this.categoriesArray.add(category1.getName());
        this.categoriesArray.add(category2.getName());
        this.categoriesArray.add(category3.getName());
        this.categoriesArray.add(category4.getName());
        this.categoriesArray.add(category5.getName());
        this.categoriesArray.add(category1.getName());
        this.categoriesArray.add(category2.getName());
        this.categoriesArray.add(category3.getName());
        this.categoriesArray.add(category4.getName());
        this.categoriesArray.add(category5.getName());
    }

    public List<MenuItemsHandler.Category> getCategoriesList() { return this.categoriesList; }
    public List<String> getCategoriesArray() { return this.categoriesArray; }

    public class Category {

        private int ID;
        private String name;

        public Category(int ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        public int getID() { return ID; }
        public String getName() { return name; }
        public void setID(int ID) { this.ID = ID; }
        public void setName(String name) { this.name = name; }
    }
}
