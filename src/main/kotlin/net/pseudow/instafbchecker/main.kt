package net.pseudow.instafbchecker

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = ArgParser("instafbchecker")

    val username by parser.argument(ArgType.String, description = "The username of the account")
    val password by parser.argument(ArgType.String, description = "The password of the account")

    val format by parser.option(ArgType.Choice<Format>(), shortName = "f", description = "Format for output")
        .default(Format.TEXT)
    var target by parser.option(ArgType.String, shortName = "T", description = "The target to use this command (By default if not provided it is the user itself)")
        .default("")
    val interval by parser.option(ArgType.Int, shortName = "i", description = "The interval between each request (Used to avoid being blocked by the API)")
        .default(3)
    val timeout by parser.option(ArgType.Int, shortName = "t", description = "Max timeout for each request")
        .default(20)
    val debug by parser.option(ArgType.Boolean, shortName = "d", description = "Turn on debug mode")
        .default(false)

    parser.parse(args)

    if(target.isBlank() || target.isEmpty()) {
        target = username
    }


    val checker = FollowBackChecker(IGCredentials(username, password), debug, interval, timeout)

    val errorMessage = checker.login()
    if(errorMessage != null) {
        System.err.println(errorMessage)
        exitProcess(1)
    }

    System.err.println("Starting the check")

    val followings = checker.following(target)
    if(followings.isEmpty()) {
        System.err.println("You do not follow nobody")
    }

    val nonFollowingBack = ArrayList<String>()
    for(following in followings) {
        if(!checker.followBack(following, target)) {
            nonFollowingBack.add(following)
        }
    }

    if(format == Format.TEXT) {
        if(nonFollowingBack.isEmpty()) {
            println("Everybody follow back $target!")
        } else {
            println("These users doesn't follow back $target: $nonFollowingBack!")
        }
    }

    exitProcess(0)
}

enum class Format {
    TEXT
}