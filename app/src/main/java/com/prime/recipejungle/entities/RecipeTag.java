package com.prime.recipejungle.entities;

public class RecipeTag {
    public int RecipeId;
    public Recipe Recipe;
    public int TagId;
    public Tag Tag;

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

    public int getTagId() {
        return TagId;
    }

    public void setTagId(int tagId) {
        TagId = tagId;
    }

    public Tag getTag() {
        return Tag;
    }

    public void setTag(Tag tag) {
        Tag = tag;
    }
}
