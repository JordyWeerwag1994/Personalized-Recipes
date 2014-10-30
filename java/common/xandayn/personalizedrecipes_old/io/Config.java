package common.xandayn.personalizedrecipes_old.io;

import common.xandayn.personalizedrecipes_old.PersonalizedRecipes;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * PersonalizedRecipes - Config
 *
 * A class that handles the creation of the Forge config file, and also sets
 * the variables needed inside of it.
 *
 * @license
 *   Copyright (C) 2014  xandayn
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author xandayn
 */
public class Config {

    public static void initialize(File configFile){
        Configuration config = new Configuration(configFile, true);
        try {
            config.load();
            PersonalizedRecipes.ALLOW_NON_OP_COMMANDS = config.get("Commands", "allowNonOP", false).getBoolean(false);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(config.hasChanged()){
                config.save();
            }
        }

    }

}
