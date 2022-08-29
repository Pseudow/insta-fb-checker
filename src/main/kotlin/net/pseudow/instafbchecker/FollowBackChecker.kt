package net.pseudow.instafbchecker

import com.xcoder.easyinsta.Instagram
import com.xcoder.easyinsta.exceptions.IGLoginException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class FollowBackChecker @kotlin.jvm.Throws() constructor(private val credentials: IGCredentials,
                                                         private val debug: Boolean,
                                                         private val interval: Int,
                                                         private val timeout: Int) {
    private var client: Instagram? = null
    private var intervalTask: CompletableFuture<Void>? = null

    /**
     * Login into instagram
     *
     * @throws LoggingException if already connected
     *
     * @return error message to display
     */
    @kotlin.jvm.Throws(LoggingException::class)
    fun login(): String? {
        debug("Logging into instagram")

        if(this.isConnected()) {
            debug("Already logged into instagram")

            throw LoggingException(alreadyLoggedMessage)
        }

        debug("No logged into instagram")

        try {
            debug("Trying to log in into instagram")

            this.client = Instagram.login(this.credentials.username, this.credentials.password)

            debug("Successfully logged into instagram")
        } catch(exception: IGLoginException) {
            debug("Failed to log into instagram")

            return exception.message
        }

        return null
    }

    /**
     * Check whether two users are following each other or not
     *
     * @throws LoggingException if not connected
     */
    fun followBack(profile: String, profile2: String): Boolean {
        debug("Checking if $profile follow back $profile2")

        if(!this.isConnected()) {
            debug("Not logged into instagram")

            throw LoggingException(notLoggedMessage)
        }

        debug("Already logged into instagram")

        this.waitInterval()
        val value = this.following(profile).contains(profile2)
        this.startInterval()

        debug("$profile and $profile2 follow back value is $value")

        return value
    }

    /**
     * get a list of followers
     *
     * @throws LoggingException if not connected
     */
    @kotlin.jvm.Throws(LoggingException::class)
    fun following(user: String): List<String> {
        debug("Getting all users $user follows")

        if(!this.isConnected()) {
            debug("Not logged into instagram")

            throw LoggingException(notLoggedMessage)
        }

        this.waitInterval()
        val result = this.client!!.profile().getFollowings(user)
        val value = result.getResult(this.timeout)
        this.startInterval()

        return if(result.isSuccessful) {
            debug("$user follows $value")

            value
        } else {
            debug("Couldn't get following of user $user, error: ${result.exception}")
            System.err.println("Couldn't get following of user $user, error: ${result.exception}")

            emptyList()
        }
    }

    fun isConnected() = this.client != null

    private fun waitInterval() {
        this.intervalTask?.let {
            debug("Waiting for end of interval")

            it.get(this.timeout.toLong(), TimeUnit.SECONDS)

            this.intervalTask = null

            debug("Interval ended!")
        }
    }

    private fun startInterval() {
        debug("Starting new interval")

        this.intervalTask = CompletableFuture.runAsync {
            TimeUnit.SECONDS.sleep(this.interval.toLong())
        }
    }

    private fun debug(message: String) {
        if(this.debug)
            println("DEBUG: $message")
    }
}