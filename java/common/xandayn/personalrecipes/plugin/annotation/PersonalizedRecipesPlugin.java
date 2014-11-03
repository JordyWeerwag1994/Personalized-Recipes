package common.xandayn.personalrecipes.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation that marks a Personalized Recipe Plugin, this
 * annotation is required to load your plugin.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PersonalizedRecipesPlugin {

    /**
     * The annotation that marks the Initialize function for your
     * plugin, this annotation is required to load your plugin.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Initialize { }

    /**
     * The annotation that marks a field variable that will contain
     * the instance of your plugin that Personalized Recipes will create,
     * this annotation is not required, but is recommended.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Instance{ }

}
