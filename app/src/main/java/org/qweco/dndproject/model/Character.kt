package org.qweco.dndproject.model

data class Character (
        var id: Long,
        var name: String,
        var character_class: Int, //use constants from class body, pls
        var race: Int, //here the same
        var xp: Int,
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

        var strenght: Int,
        var dexterity: Int,
        var constution: Int,
        var intelligence: Int,
        var wisdom: Int,
        var perception: Int,
        var charisma: Int,


        var eyeColor: Int,
        var hairStyle: Int,
        var skinColor: Int){

    companion object {
        //Classes
        const val HUMAN = 0
        const val DWARF = 1
        const val ELF = 2
        //TODO: write more

        //Skills
        const val ACROBATICS = 0
        const val ANIMAL_HANDLING = 1
        //TODO: write more

        //TODO: add savingThrows
    }
}