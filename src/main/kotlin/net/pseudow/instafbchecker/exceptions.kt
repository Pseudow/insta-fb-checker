package net.pseudow.instafbchecker

const val alreadyLoggedMessage = "You are already logged in!"
const val notLoggedMessage = "You are not logged in!"

class LoggingException(message: String): Exception(message)