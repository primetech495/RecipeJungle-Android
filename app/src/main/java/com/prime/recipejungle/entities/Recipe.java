package com.prime.recipejungle.entities;
import org.joda.time.DateTime;
import java.util.*;
public class Recipe {
    public int Id;
    public String Title;
    public String Text;
    public List<RecipeTag> RecipeTags;
    public List<UserRecipe> RecipeLikes;
    public String Steps;
    public String Ingredients ;
    public String Photos ;
    public DateTime CreatedTime ;
    public DateTime ModifiedTime ;
    public int PrepareTime ;
    public int Portion;
    public User User;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public List<RecipeTag> getRecipeTags() {
        return RecipeTags;
    }

    public void setRecipeTags(List<RecipeTag> recipeTags) {
        RecipeTags = recipeTags;
    }

    public List<UserRecipe> getRecipeLikes() {
        return RecipeLikes;
    }

    public void setRecipeLikes(List<UserRecipe> recipeLikes) {
        RecipeLikes = recipeLikes;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }

    public DateTime getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        CreatedTime = createdTime;
    }

    public DateTime getModifiedTime() {
        return ModifiedTime;
    }

    public void setModifiedTime(DateTime modifiedTime) {
        ModifiedTime = modifiedTime;
    }

    public int getPrepareTime() {
        return PrepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        PrepareTime = prepareTime;
    }

    public int getPortion() {
        return Portion;
    }

    public void setPortion(int portion) {
        Portion = portion;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }


}
