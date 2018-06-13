package org.qweco.dndproject.model

import android.content.Context
import org.qweco.dndproject.R
import java.io.Serializable

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

        var skills: Map<String, Int?>, //use constants from class body, pls
        var savingThrows: Map<String, Int?>, //here the same

        var strength: Int,
        var dexterity: Int,
        var constitution: Int,
        var intelligence: Int,
        var wisdom: Int,
        var perception: Int,
        var charisma: Int,


        var eyeColor: Int,
        var hairStyle: Int,
        var skinColor: Int): Serializable{

    constructor(): this(-1, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, mapOf(), mapOf(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    constructor(race: Int): this(-1, "", 0, race, 0, 0, 0, 0, 0, 0, 0, mapOf(), mapOf(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    companion object {
        //Races
        const val HUMAN = 0 // WTF ALL +1 speed 30
        const val DWARF = 1 // con +2 speed 25
        const val HILL_DWARF = 2// wis +1 speed 25
        const val MOUNTAIN_DWARF = 3 // str +2 speed
        const val ELF = 4 // dex +2 speed 30
        const val HIGH_ELF = 5 // int +1 speed 30
        const val WOOD_ELF = 6 // wis +1 speed 30
        const val DARK_ELF = 7 // cha +1 speed 30
        const val TIFLING = 8 // int +1 cha +2 speed 30
        const val HALF_ORC = 9 // str +2 con +1 speed 30
        const val HALF_ELF = 10 // cha +2 speed 30
        const val DRAGONBORN = 11 // str +2 cha +1 speed 30
        const val HALFLING = 12 // dex +2 speed 25
        const val LIGHTFOOT_HALFLING = 13 // cha +1 speed 25
        const val STOUT_HALFLING = 14 // con +1 speed 25
        const val GNOM = 15 // int +2 speed 25
        const val FOREST_GNOM = 16 // dex +1 speed 25
        const val ROCK_GNOM = 17 // con +1 speed 25

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
        const val ACROBATICS = "ACROBATICS"            //DEX
        const val ANIMAL_HANDLING = "ANIMAL_HANDLING"  //WIS
        const val ARCANA = "ARCANA"                    //INT
        const val ATHLETICS = "ATHLETICS"              //STR
        const val DECEPTION = "DECEPTION"              //CHA
        const val HISTORY = "HISTORY"                  //INT
        const val INSIGHT = "INSIGHT"                  //WIS
        const val INTIMIDATION = "INTIMIDATION"        //CHA
        const val INVESTIGATION = "INVESTIGATION"      //INT
        const val MEDICINE = "MEDICINE"                //WIS
        const val NATURE = "NATURE"                    //INT
        const val PERCEPTION = "PERCEPTION"            //WIS
        const val PERFORMANCE = "PERFORMANCE"          //CHA
        const val PERSUASION = "PERSUASION"            //CHA
        const val RELIGION = "RELIGION"                //INT
        const val SLEIGHT_OF_HAND = "SLEIGHT_OF_HAND"  //DEX
        const val STEALTH = "STEALTH"                  //DEX
        const val SURVIVAL = "SURVIVAL"                //WIS

        //Saving Throws
        const val STR = "STR"
        const val DEX = "DEX"
        const val CONST = "CONST"
        const val INT = "INT"
        const val WIS = "WIS"
        const val CHA = "CHA"

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
            Character.DARK_ELF -> {
                return context.resources.getString(R.string.race_elf3)
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

    fun getRaceBonusStrength (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1;
            }
            Character.MOUNTAIN_DWARF -> {
                return 2
            }
            Character.HALF_ORC -> {
                return 2
            }
            Character.DRAGONBORN -> {
                return 2
            }
        }

        return 0;
    }

    fun getRaceBonusDexterity (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1
            }
            Character.ELF -> {
                return 2
            }
            Character.HALFLING -> {
                return 2
            }
            Character.FOREST_GNOM -> {
                return 1
            }
        }

        return 0;
    }

    fun getRaceBonusConstitution (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1;
            }
            Character.DWARF -> {
                return 2
            }
            Character.HALF_ORC -> {
                return 1
            }
            Character.STOUT_HALFLING -> {
                return 1
            }
        }

        return 0;
    }

    fun getRaceBonusIntelligence (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1;
            }
            Character.HIGH_ELF -> {
                return 1
            }
            Character.TIFLING -> {
                return 1
            }
            Character.GNOM -> {
                return 2
            }
        }

        return 0;
    }

    fun getRaceBonusWisdom (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1;
            }
            Character.HILL_DWARF -> {
                return 1
            }
            Character.WOOD_ELF -> {
                return 1
            }
        }

        return 0;
    }

    fun getRaceBonusCharisma (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1;
            }
            Character.DARK_ELF -> {
                return 1
            }
            Character.TIFLING -> {
                return 2
            }
            Character.HALF_ELF -> {
                return 2
            }
            Character.DRAGONBORN -> {
                return 1
            }
            Character.LIGHTFOOT_HALFLING -> {
                return 1
            }
        }

        return 0;
    }

    fun getDefaultSpeed (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 30;
            }
            Character.ELF -> {
                return 30
            }
            Character.HIGH_ELF -> {
                return 30
            }
            Character.WOOD_ELF -> {
                return 30
            }
            Character.DARK_ELF -> {
                return 30
            }
            Character.TIFLING -> {
                return 30
            }
            Character.HALF_ORC -> {
                return 30
            }
            Character.HALF_ELF -> {
                return 30
            }
            Character.DRAGONBORN -> {
                return 30
            }
        }

        return 25
    }

    fun getSawethrows (context: Context) : Array<String>
    {
        when (race){
            Character.HUMAN -> {
                return arrayOf()
            }
            Character.DWARF -> {
                return arrayOf()
            }
            Character.HILL_DWARF -> {
                return arrayOf()
            }
            Character.MOUNTAIN_DWARF -> {
                return arrayOf()
            }
            Character.ELF -> {
                return arrayOf()
            }
            Character.HIGH_ELF -> {
                return arrayOf()
            }
            Character.WOOD_ELF -> {
                return arrayOf()
            }
            Character.DARK_ELF -> {
                return arrayOf()
            }
            Character.TIFLING -> {
                return arrayOf()
            }
            Character.HALF_ORC -> {
                return arrayOf()
            }
            Character.HALF_ELF -> {
                return arrayOf()
            }
            Character.DRAGONBORN -> {
                return arrayOf()
            }
            Character.HALFLING -> {
                return arrayOf()
            }
            Character.LIGHTFOOT_HALFLING -> {
                return arrayOf()
            }
            Character.STOUT_HALFLING -> {
                return arrayOf()
            }
            Character.GNOM -> {
                return arrayOf()
            }
            Character.FOREST_GNOM -> {
                return arrayOf()
            }
            Character.ROCK_GNOM -> {
                return arrayOf()
            }
        }

        return arrayOf()
    }

    fun getSkills (context: Context) : Array<String>
    {
        when (race){
            Character.HUMAN -> {
                return arrayOf()
            }
            Character.DWARF -> {
                return arrayOf()
            }
            Character.HILL_DWARF -> {
                return arrayOf()
            }
            Character.MOUNTAIN_DWARF -> {
                return arrayOf()
            }
            Character.ELF -> {
                return arrayOf()
            }
            Character.HIGH_ELF -> {
                return arrayOf()
            }
            Character.WOOD_ELF -> {
                return arrayOf()
            }
            Character.DARK_ELF -> {
                return arrayOf()
            }
            Character.TIFLING -> {
                return arrayOf()
            }
            Character.HALF_ORC -> {
                return arrayOf()
            }
            Character.HALF_ELF -> {
                return arrayOf()
            }
            Character.DRAGONBORN -> {
                return arrayOf()
            }
            Character.HALFLING -> {
                return arrayOf()
            }
            Character.LIGHTFOOT_HALFLING -> {
                return arrayOf()
            }
            Character.STOUT_HALFLING -> {
                return arrayOf()
            }
            Character.GNOM -> {
                return arrayOf()
            }
            Character.FOREST_GNOM -> {
                return arrayOf()
            }
            Character.ROCK_GNOM -> {
                return arrayOf()
            }
        }

        return arrayOf()
    }

    fun getHitDiceForClass(context: Context): Int //we need to make it a second char in a string, like "Hit dice: d${here this int}" so it looks like "Hit dice: d6"
    {
        when (character_class){
            Character.WIZARD -> {
                return 6
            }
            Character.WARLOCK -> {
                return 8
            }
            Character.SORCERER -> {
                return 6
            }
            Character.WARRIOR -> {
                return 10
            }
            Character.MONK -> {
                return 8
            }
            Character.RANGER -> {
                return 10
            }
            Character.THIEF -> {
                return 8
            }
            Character.CLERIC -> {
                return 8
            }
            Character.PALADIN -> {
                return 10
            }
            Character.BARBARIAN -> {
                return 12
            }
            Character.DRUID -> {
                return 8
            }
            Character.BARD -> {
                return 8
            }
        }

        return 0

    }

}