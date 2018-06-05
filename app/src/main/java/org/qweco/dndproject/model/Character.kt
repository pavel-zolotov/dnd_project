package org.qweco.dndproject.model

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

        //TODO: remove question marks
        var skills: Array<Int>?, //use constants from class body, pls
        var savingThrows: Array<Int>?, //here the same

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
        const val ELF = 2
        const val TIFLING = 3
        const val HALF_ORC = 4
        const val HALF_ELF = 5
        const val DRAGONBORN = 6
        const val HALFLING = 7
        const val GNOM = 8

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
        const val NONK = 9
        const val PALADIN = 10
        const val CLERIC = 11


        //Skills
        var ACROBATICS = 0        //DEX
        var ANIMAL_HANDLING = 1   //WIS
        var ARCANA = 2            //INT
        var ATHLETICS = 3         //STR
        var DECEPTION = 4         //CHA
        var HISTORY = 5           //INT
        var INSIGHT = 6           //WIS
        var INTIMIDATION = 7      //CHA
        var INVESTIGATION = 8     //INT
        var MEDICINE = 9          //WIS
        var NATURE = 10           //INT
        var PERCEPTION = 11       //WIS
        var PERFORMANCE = 12      //CHA
        var PERSUASION = 13       //CHA
        var RELIGION = 14         //INT
        var SLEIGHT_OF_HAND = 15  //DEX
        var STEALTH = 16          //DEX
        var SURVIVAL = 17         //WIS

        //Saving Throws
        var STR = 0
        var DEX = 1
        var CONST = 2
        var INT = 3
        var WIS = 4
        var CHA = 5
    }
}