package com.prime.recipejungle.entities;
import java.util.*;
public class User {
    public int Id ;
    public String Username;
    public String Email;
    public String Token;
    public List<Recipe> RecipesOfUser;
    public List<UserRecipe> LikedRecipesOfUser;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public List<Recipe> getRecipesOfUser() {
        return RecipesOfUser;
    }

    public void setRecipesOfUser(List<Recipe> recipesOfUser) {
        RecipesOfUser = recipesOfUser;
    }

    public List<UserRecipe> getLikedRecipesOfUser() {
        return LikedRecipesOfUser;
    }

    public void setLikedRecipesOfUser(List<UserRecipe> likedRecipesOfUser) {
        LikedRecipesOfUser = likedRecipesOfUser;
    }
}
