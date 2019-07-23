package com.prime.recipejungle.entities;

public class UserRecipe {
    public int RecipeId;
    public Recipe Recipe;
    public int UserId;
    public User User;

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public com.prime.recipejungle.entities.Recipe getRecipe() {
        return Recipe;
    }

    public void setRecipe(com.prime.recipejungle.entities.Recipe recipe) {
        Recipe = recipe;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public com.prime.recipejungle.entities.User getUser() {
        return User;
    }

    public void setUser(com.prime.recipejungle.entities.User user) {
        User = user;
    }
}
