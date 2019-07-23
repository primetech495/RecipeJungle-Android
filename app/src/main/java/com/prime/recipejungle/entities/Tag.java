package com.prime.recipejungle.entities;
import java.util.*;
public class Tag {
    public int Id ;
    public String Text ;
    public List<RecipeTag> RecipeTags;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
