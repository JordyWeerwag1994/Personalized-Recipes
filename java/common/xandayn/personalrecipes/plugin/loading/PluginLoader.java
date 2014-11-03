package common.xandayn.personalrecipes.plugin.loading;

import common.xandayn.personalrecipes.PersonalizedRecipes;
import common.xandayn.personalrecipes.io.FileHandler;
import common.xandayn.personalrecipes.plugin.annotation.PersonalizedRecipesPlugin;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * The PluginLoader class finds and loads PersonalizedRecipes plugins.
 *
 * @license
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew DePalma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class PluginLoader {

    /**
     * This function looks through the directory returned by FileHandler#getPluginsDirectory()
     * and finds all jar/zip files, once found it sends the file to PluginLoader#loadPluginFile(File).
     * If the class returned by PluginLoader#loadPluginFile(File) is not null, it will proceed to
     * check for the @PersonalizedRecipesPlugin.Initialize and the @PersonalizedRecipes.Instance
     * annotations, if the @PersonalizedRecipes.Instance it will set the object to the instance of
     * the class, created by Class#newInstace(). Afterwards it will call Method#invoke(Object, Object...) the method returned by
     * PluginLoader#getMethodFromAnnotation(Class, Class).
     *
     * @see common.xandayn.personalrecipes.plugin.loading.PluginLoader#loadPluginFile(java.io.File)
     * @see common.xandayn.personalrecipes.plugin.annotation.PersonalizedRecipesPlugin.Initialize
     * @see common.xandayn.personalrecipes.plugin.annotation.PersonalizedRecipesPlugin.Instance
     * @see Class#newInstance()
     * @see Method#invoke(Object, Object...)
     */
    public static void loadPlugins(){
        File baseDirectory = FileHandler.getBaseWorkingDirectory();
        File pluginsDirectory = FileHandler.getPluginsDirectory();
        if(!baseDirectory.exists() || !pluginsDirectory.exists()) return;
        File[] files = pluginsDirectory.listFiles();
        if(files == null) return;
        for(File file : files) {
            if (file.isDirectory() || !FileHandler.isFileZipOrJar(file)) continue;
            Class mainClass = loadPluginFile(file);
            if(mainClass != null) {
                try {
                    Object instance = mainClass.newInstance();
                    Field instanceField = getFieldFromAnnotation(mainClass, PersonalizedRecipesPlugin.Instance.class);
                    if(instanceField != null) {
                        instanceField.setAccessible(true);
                        instanceField.set(null, instance);
                    }

                    Method initMethod = getMethodFromAnnotation(mainClass, PersonalizedRecipesPlugin.Initialize.class);
                    if (initMethod != null) {
                        System.out.println("Found Custom Recipes plugin in file \"" + file.getName() + "\", inside class, \"" + mainClass.getName() + "\", loading plugin.");
                        initMethod.invoke(instance);
                    } else {
                        System.err.println("Found Custom Recipes plugin in class, \"" + mainClass.getName() + "\", but no @PersonalizedRecipePlugin.Initialize method with one parameter of type RecipeRegistry found, unable to load plugin.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Found jar/zip file \"" + file.getName() + "\" in Personalized_Recipes/Plugins folder, but no @PersonalizedRecipesPlugin annotation found, ignoring.");
            }
        }
    }

    /**
     * This function attempts to return a class with the @PersonalizedRecipePlugin annotation attached to
     * it, it will iterate over the entire zip/jar file provided and search for .class files, ignoring anything
     * that isn't one. It uses a URLClassLoader to load the class files and check for the annotation mentioned
     * earlier.
     *
     * @param zipJarFile The zip or jar file we want to attempt to load a plugin from.
     * @return A class that is annotated with @PersonalizedRecipesPlugin, or null if the annotation does not
     * exists in the zip or jar file.
     *
     * @see common.xandayn.personalrecipes.plugin.annotation.PersonalizedRecipesPlugin
     * @see java.net.URLClassLoader
     */
    private static Class loadPluginFile(File zipJarFile) {
        try {
            ZipFile zip = new ZipFile(zipJarFile);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            if (entries != null) {
                ArrayList<URL> urls = new ArrayList<>();
                ArrayList<String> classNames = new ArrayList<>();
                String baseURL = "jar:".concat(zipJarFile.toURI().toURL().toString()).concat("!/");
                urls.add(new URL(baseURL));
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String fileName = entry.getName();
                    if (!entry.isDirectory()) {
                        if (fileName.toLowerCase().endsWith(".class")) {
                            urls.add(new URL(baseURL.concat(fileName)));
                            classNames.add(fileNameToClassName(fileName));
                        }
                    }
                }
                zip.close();
                URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]), PersonalizedRecipes.class.getClassLoader());
                Class clazz = null;
                for(String s : classNames) {
                    Class tempClazz = classLoader.loadClass(s);
                    if(tempClazz != null && tempClazz.isAnnotationPresent(PersonalizedRecipesPlugin.class)){
                        clazz = tempClazz;
                        break;
                    }
                }
                return clazz;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function searched through every field in a specific class and looks for
     * one that has a specified annotation.
     *
     * @param toSearch The class you want to search in.
     * @param annotationClass The annotation you wish to look for
     * @return The field with the annotation specified in the parameters, or null
     * if none was found.
     */
    private static Field getFieldFromAnnotation(Class toSearch, Class<? extends Annotation> annotationClass){
        for(final Field field : toSearch.getDeclaredFields()){
            final Annotation fieldAnnotation = field.getAnnotation(annotationClass);
            if(fieldAnnotation != null) {
                return field;
            }
        }
        return null;
    }

    /**
     * This function searched through every method in a specific class and looks one
     * that has a specified annotation.
     *
     * @param toSearch The class you want to search in.
     * @param annotationClass The annotation you wish to look for
     * @return The method with the annotation specified in the parameters, or null
     * if none was found.
     */
    private static Method getMethodFromAnnotation(Class toSearch, Class<? extends Annotation> annotationClass){
        for(final Method method : toSearch.getDeclaredMethods()){
            final Annotation plugin = method.getAnnotation(annotationClass);
            if(plugin != null) {
                return method;
            }
        }
        return null;
    }

    /**
     * A utility function that converts a path to a class name.
     * @param fileName The path name to convert.
     * @return A converted class name.
     */
    private static String fileNameToClassName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf(".class")).replace("/", ".");
    }
}
