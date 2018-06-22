package com.coolguys.dndproject.model

import android.content.Context
import android.graphics.drawable.Drawable
import com.coolguys.dndproject.R
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

        var skills: HashMap<String, Int?>, //use constants from class body, pls
        @Deprecated("will be deleted") var savingThrows: HashMap<String, Int?>, //here the same

        var strength: Int,
        var dexterity: Int,
        var constitution: Int,
        var intelligence: Int,
        var wisdom: Int,
        var perception: Int,
        var charisma: Int,

        var eyeColor: Int,
        @Deprecated("will be deleted") var hairStyle: Int,
        var skinColor: Int): Serializable{

    constructor(): this(-1, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, HashMap<String, Int?>(), HashMap<String, Int?>(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    constructor(race: Int): this(-1, "", 0, race, 0, 0, 0, 0, 0, 0, 0, HashMap<String, Int?>(), HashMap<String, Int?>(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

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
        const val ACROBATICS = 0            //DEX
        const val ANIMAL_HANDLING = 1       //WIS
        const val ARCANA = 2                //INT
        const val ATHLETICS = 3             //STR
        const val DECEPTION = 4             //CHA
        const val HISTORY = 5               //INT
        const val INSIGHT = 6               //WIS
        const val INTIMIDATION = 7          //CHA
        const val INVESTIGATION = 8         //INT
        const val MEDICINE = 9              //WIS
        const val NATURE = 10               //INT
        const val PERCEPTION = 11           //WIS
        const val PERFORMANCE = 12          //CHA
        const val PERSUASION = 13           //CHA
        const val RELIGION = 14             //INT
        const val SLEIGHT_OF_HAND = 15      //DEX
        const val STEALTH = 16              //DEX
        const val SURVIVAL = 17             //WIS

        /*

        ACROBATICS ANIMAL_HANDLING ARCANA ATHLETICS DECEPTION HISTORY INSIGHT INTIMIDATION INVESTIGATION MEDICINE NATURE PERCEPTION PERFORMANCE PERSUASION RELIGION SLEIGHT_OF_HAND STEALTH SURVIVAL

                //Skills BACKUP
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
         */

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
                return 1
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

        return 0
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

        return 0
    }

    fun getRaceBonusConstitution (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1
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

        return 0
    }

    fun getRaceBonusIntelligence (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1
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

        return 0
    }

    fun getRaceBonusWisdom (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1
            }
            Character.HILL_DWARF -> {
                return 1
            }
            Character.WOOD_ELF -> {
                return 1
            }
        }

        return 0
    }

    fun getRaceBonusCharisma (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 1
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

        return 0
    }

    fun getDefaultSpeed (context: Context) : Int
    {
        when (race){
            Character.HUMAN -> {
                return 30
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

    fun getSawethrows (context: Context) : Array<Int>
    {
        when (character_class){
            Character.WIZARD -> {
                return arrayOf(INT, WIS)
            }
            Character.WARLOCK -> {
                return arrayOf(WIS, CHA)
            }
            Character.SORCERER -> {
                return arrayOf(CONST, CHA)
            }
            Character.WARRIOR -> {
                return arrayOf(STR, CONST)
            }
            Character.MONK -> {
                return arrayOf(STR, DEX)
            }
            Character.RANGER -> {
                return arrayOf(STR, DEX)
            }
            Character.THIEF -> {
                return arrayOf(DEX, INT)
            }
            Character.CLERIC -> {
                return arrayOf(WIS, CHA)
            }
            Character.PALADIN -> {
                return arrayOf(WIS, CHA)
            }
            Character.BARBARIAN -> {
                return arrayOf(STR, CONST)
            }
            Character.DRUID -> {
                return arrayOf(INT, WIS)
            }
            Character.BARD -> {
                return arrayOf(DEX, CHA)
            }
        }

        return arrayOf()
    }

    fun getAvailableSkills (context: Context) : Array<Int>
    {
        when (character_class){
            Character.WIZARD -> {
                return arrayOf(INVESTIGATION, HISTORY, ARCANA, MEDICINE, INSIGHT, RELIGION)
            }
            Character.WARLOCK -> {
                return arrayOf(INVESTIGATION, INTIMIDATION, HISTORY, ARCANA, DECEPTION, NATURE, RELIGION)
            }
            Character.SORCERER -> {
                return arrayOf(INTIMIDATION, ARCANA, DECEPTION, INSIGHT, RELIGION, PERSUASION)
            }
            Character.WARRIOR -> {
                return arrayOf(ACROBATICS, ATHLETICS, PERCEPTION, SURVIVAL, INTIMIDATION, HISTORY, INSIGHT, ANIMAL_HANDLING)
            }
            Character.MONK -> {
                return arrayOf(ACROBATICS, ATHLETICS, HISTORY, INSIGHT, RELIGION, STEALTH)
            }
            Character.RANGER -> {
                return arrayOf(INVESTIGATION, ATHLETICS, PERCEPTION, SURVIVAL, NATURE, INSIGHT, STEALTH, ANIMAL_HANDLING)
            }
            Character.THIEF -> {
                return arrayOf(ACROBATICS, INVESTIGATION, ATHLETICS, PERCEPTION, PERFORMANCE, INTIMIDATION, SLEIGHT_OF_HAND, DECEPTION, INSIGHT, STEALTH, PERSUASION)
            }
            Character.CLERIC -> {
                return arrayOf(HISTORY, MEDICINE, INSIGHT, RELIGION, PERSUASION)
            }
            Character.PALADIN -> {
                return arrayOf(ATHLETICS, INTIMIDATION, MEDICINE, INSIGHT, RELIGION, PERSUASION)
            }
            Character.BARBARIAN -> {
                return arrayOf(ATHLETICS, PERCEPTION, SURVIVAL, INTIMIDATION, NATURE, ANIMAL_HANDLING)
            }
            Character.DRUID -> {
                return arrayOf(PERCEPTION, SURVIVAL, ARCANA, MEDICINE, ANIMAL_HANDLING, NATURE, INSIGHT, RELIGION)
            }
            Character.BARD -> {
                return arrayOf(ACROBATICS, ANIMAL_HANDLING, ARCANA, ATHLETICS, DECEPTION, HISTORY, INSIGHT, INTIMIDATION, INVESTIGATION, MEDICINE, NATURE, PERCEPTION, PERFORMANCE, PERSUASION, RELIGION, SLEIGHT_OF_HAND, STEALTH, SURVIVAL)
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

    fun getAmountOfSkills (context: Context) :Int
    {
        when (character_class){

            Character.RANGER -> {
                return 3
            }
            Character.THIEF -> {
                return 4
            }
            Character.BARD -> {
                return 3
            }
        }

        return 2
    }

    fun getDrawableForClass (context: Context) : Drawable
    {
        when (character_class){
            Character.WIZARD -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.wizard_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.wizard_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.wizard_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.wizard_green)
                    }
                }
                //context.resources.getDrawable(R.drawable.wizard)
            }
            Character.WARLOCK -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.warlock_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.warlock_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.warlock_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.warlock_green)
                    }
                }            }
            Character.SORCERER -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.sorcerer_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.sorcerer_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.sorcerer_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.sorcerer_green)
                    }
                }              }
            Character.WARRIOR -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.warrior_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.warrior_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.warrior_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.warrior_green)
                    }
                }
            }
            Character.MONK -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.monk_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.monk_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.monk_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.monk_green)
                    }
                }            }
            Character.RANGER -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.ranger_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.ranger_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.ranger_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.ranger_green)
                    }
                }            }
            Character.THIEF -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.thief_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.thief_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.thief_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.thief_green)
                    }
                }            }
            Character.CLERIC -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.cleric_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.cleric_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.cleric_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.cleric_green)
                    }
                }
            }
            Character.PALADIN -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.paladin_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.paladin_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.paladin_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.paladin_green)
                    }
                }            }
            Character.BARBARIAN -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.barbarian_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.barbarian_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.barbarian_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.barbarian_green)
                    }
                }
            }
            Character.DRUID -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.druid_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.druid_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.druid_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.druid_green)
                    }
                }
            }
            Character.BARD -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.bard_blue)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.bard_yellow)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.bard_red)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.bard_green)
                    }
                }
            }
        }

        return context.resources.getDrawable(R.drawable.warrior)
    }

    fun getDrawableIconForClass (context: Context) : Drawable
    {
        when (character_class){
            Character.WIZARD -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.wiz_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.wiz_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.wiz_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.wiz_g)
                    }
                }
                //context.resources.getDrawable(R.drawable.wizard)
            }
            Character.WARLOCK -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.warl_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.warl_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.warl_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.warl_g)
                    }
                }            }
            Character.SORCERER -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.so_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.so_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.so_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.so_g)
                    }
                }              }
            Character.WARRIOR -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.warr_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.warr_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.warr_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.warr_g)
                    }
                }
            }
            Character.MONK -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.mo_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.mo_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.mo_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.mo_g)
                    }
                }            }
            Character.RANGER -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.ra_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.ra_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.ra_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.ra_g)
                    }
                }            }
            Character.THIEF -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.th_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.th_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.th_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.th_g)
                    }
                }            }
            Character.CLERIC -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.cl_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.cl_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.cl_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.cl_g)
                    }
                }
            }
            Character.PALADIN -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.pa_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.pa_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.pa_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.pa_g)
                    }
                }            }
            Character.BARBARIAN -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_g)
                    }
                }
            }
            Character.DRUID -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.dr_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.dr_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.dr_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.dr_g)
                    }
                }
            }
            Character.BARD -> {
                when (eyeColor)
                {
                    EYE_COLOR_BLUE ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_b)
                    }
                    EYE_COLOR_AMBER ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_y)
                    }
                    EYE_COLOR_RED ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_r)
                    }
                    EYE_COLOR_GREEN ->
                    {
                        return context.resources.getDrawable(R.drawable.bar_g)
                    }
                }
            }
        }

        return context.resources.getDrawable(R.drawable.warrior)
    }

}