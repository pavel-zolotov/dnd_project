package org.qweco.dndproject.model

import android.content.Context
import kotlinx.android.synthetic.main.fragment_character_specs.view.*
import org.qweco.dndproject.R
import org.qweco.dndproject.SpecsFragment

data class Character (
        var id: Long,
        var name: String,
        var character_class: Int, //use constants from class body, pls
        var race: Int, //here the same
        var lvl: Int,
        var hp: Int,

        var speed: Int,
        var initiative: Int,
        var hitDice: Int,
        var armourClass: Int,
        var proficiency: Int,

        var skills: Map<Int, Int?>, //use constants from class body, pls
        var savingThrows: Map<Int, Int?>, //here the same

        var strength: Int,
        var dexterity: Int,
        var constitution: Int,
        var intelligence: Int,
        var wisdom: Int,
        var perception: Int,
        var charisma: Int,


        var eyeColor: Int,
        var hairStyle: Int,
        var skinColor: Int){

    companion object {
        //Races
        const val HUMAN = 0
        const val DWARF = 1
        const val HILL_DWARF = 2
        const val MOUNTAIN_DWARF = 3
        const val ELF = 4
        const val HIGH_ELF = 5
        const val WOOD_ELF = 6
        const val TIFLING = 7
        const val HALF_ORC = 8
        const val HALF_ELF = 9
        const val DRAGONBORN = 10
        const val HALFLING = 11
        const val LIGHTFOOT_HALFLING = 12
        const val STOUT_HALFLING = 13
        const val GNOM = 14
        const val FOREST_GNOM = 15
        const val ROCK_GNOM = 16

        //Classes
        const val WIZARD = 0
        const val SORCERER = 1
        const val WARLOCK = 2
        const val WARRIOR = 3
        const val BARBARIAN = 4
        const val DRUID = 5
        const val BARD = 6
        const val RANGER = 7
        const val THIEF = 8
        const val MONK = 9
        const val PALADIN = 10
        const val CLERIC = 11


        //Skills
        const val ACROBATICS = 0        //DEX
        const val ANIMAL_HANDLING = 1   //WIS
        const val ARCANA = 2            //INT
        const val ATHLETICS = 3         //STR
        const val DECEPTION = 4         //CHA
        const val HISTORY = 5           //INT
        const val INSIGHT = 6           //WIS
        const val INTIMIDATION = 7      //CHA
        const val INVESTIGATION = 8     //INT
        const val MEDICINE = 9          //WIS
        const val NATURE = 10           //INT
        const val PERCEPTION = 11       //WIS
        const val PERFORMANCE = 12      //CHA
        const val PERSUASION = 13       //CHA
        const val RELIGION = 14         //INT
        const val SLEIGHT_OF_HAND = 15  //DEX
        const val STEALTH = 16          //DEX
        const val SURVIVAL = 17         //WIS

        //Saving Throws
        const val STR = 0
        const val DEX = 1
        const val CONST = 2
        const val INT = 3
        const val WIS = 4
        const val CHA = 5

        //Apperance
        const val EYE_COLOR_BLUE = 0
        const val EYE_COLOR_RED = 1
        const val EYE_COLOR_AMBER = 2
        const val EYE_COLOR_GREEN = 3
        const val EYE_COLOR_WHITE = 4

        const val SKIN_COLOR_LIGHT = 0
        const val SKIN_COLOR_DARK = 1
    }

    fun getStringForRace(context: Context): String
    {
        when (race){
            Character.HUMAN -> {
                return context.resources.getString(R.string.race_human)
            }
            Character.DWARF -> {
                return context.resources.getString(R.string.race_dwarf)
            }
            Character.HILL_DWARF -> {
                return context.resources.getString(R.string.race_dwarf1)
            }
            Character.MOUNTAIN_DWARF -> {
                return context.resources.getString(R.string.race_dwarf2)
            }
            Character.ELF -> {
                return context.resources.getString(R.string.race_elf)
            }
            Character.HIGH_ELF -> {
                return context.resources.getString(R.string.race_elf1)
            }
            Character.WOOD_ELF -> {
                return context.resources.getString(R.string.race_elf2)
            }
            Character.TIFLING -> {
                return context.resources.getString(R.string.race_tifling)
            }
            Character.HALF_ORC -> {
                return context.resources.getString(R.string.race_half_orc)
            }
            Character.HALF_ELF -> {
                return context.resources.getString(R.string.race_half_elf)
            }
            Character.DRAGONBORN -> {
                return context.resources.getString(R.string.race_dragonborn)
            }
            Character.HALFLING -> {
                return context.resources.getString(R.string.race_halfling)
            }
            Character.LIGHTFOOT_HALFLING -> {
                return context.resources.getString(R.string.race_halfling1)
            }
            Character.STOUT_HALFLING -> {
                return context.resources.getString(R.string.race_halfling2)
            }
            Character.GNOM -> {
                return context.resources.getString(R.string.race_gnom)
            }
            Character.FOREST_GNOM -> {
                return context.resources.getString(R.string.race_gnom1)
            }
            Character.ROCK_GNOM -> {
                return context.resources.getString(R.string.race_gnom2)
            }
        }

        return ""
    }

    fun getStringForClass(context: Context): String
    {
        when (character_class){
            Character.WIZARD -> {
                return context.resources.getString(R.string.class_wizard)
            }
            Character.WARLOCK -> {
                return context.resources.getString(R.string.class_warlock)
            }
            Character.SORCERER -> {
                return context.resources.getString(R.string.class_sorcerer)
            }
            Character.WARRIOR -> {
                return context.resources.getString(R.string.class_warrior)
            }
            Character.MONK -> {
                return context.resources.getString(R.string.class_monk)
            }
            Character.RANGER -> {
                return context.resources.getString(R.string.class_ranger)
            }
            Character.THIEF -> {
                return context.resources.getString(R.string.class_thief)
            }
            Character.CLERIC -> {
                return context.resources.getString(R.string.class_cleric)
            }
            Character.PALADIN -> {
                return context.resources.getString(R.string.class_paladin)
            }
            Character.BARBARIAN -> {
                return context.resources.getString(R.string.class_barbarian)
            }
            Character.DRUID -> {
                return context.resources.getString(R.string.class_druid)
            }
            Character.BARD -> {
                return context.resources.getString(R.string.class_bard)
            }
        }

        return ""

    }

}