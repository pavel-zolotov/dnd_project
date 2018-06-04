package org.qweco.dndproject

data class Character (
        var name: String,
        var xp: Int,
        var lvl: Int,
        var hp: Int,

        var speed: Int,
        var initiative: Int,
        var hitDice: Int,
        var armourClass: Int,
        var proficiency: Int,

        var skills: Array<Int>, //use constants from class body, pls
        var savingThrows: Array<Int>, //here the same

        var strenght: Int,
        var dexterity: Int,
        var constution: Int,
        var intelligence: Int,
        var wisdom: Int,
        var charisma: Int){
    //Skills
    val ACROBATICS = 1
    val ANIMAL_HANDLING = 2
}