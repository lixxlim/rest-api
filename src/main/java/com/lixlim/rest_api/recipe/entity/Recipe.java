package com.lixlim.rest_api.recipe.entity;

import com.lixlim.rest_api._common.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "t1_recipe")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "t1_recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn(name = "ingredient_order")
    @Column(name = "ingredient", nullable = false)
    @Default
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "t1_recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn(name = "step_order")
    @Column(name = "step", nullable = false)
    @Default
    private List<String> steps = new ArrayList<>();

    @Column(nullable = false)
    private int servings;

    @Column(nullable = false)
    private int cookTimeMinutes;

    @ElementCollection
    @CollectionTable(name = "t1_recipe_tags", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "tag")
    @Default
    private List<String> tags = new ArrayList<>();

    public void update(
            String title,
            String description,
            List<String> ingredients,
            List<String> steps,
            int servings,
            int cookTimeMinutes,
            List<String> tags
    ) {
        this.title = title;
        this.description = description;
        this.servings = servings;
        this.cookTimeMinutes = cookTimeMinutes;

        if (this.ingredients == null) {
            this.ingredients = new ArrayList<>();
        }
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);

        if (this.steps == null) {
            this.steps = new ArrayList<>();
        }
        this.steps.clear();
        this.steps.addAll(steps);

        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.clear();
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }
}
