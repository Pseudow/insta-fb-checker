package net.pseudow.instafbchecker

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FollowBackCheckerTest {
    private val credentials = IGCredentials(System.getenv("INSTAGRAM_USERNAME"), System.getenv("INSTAGRAM_PASSWORD"))
    private val checker = FollowBackChecker(this.credentials, true, 2, 20)

    @Before
    fun login() {
        val errorMessage = this.checker.login()

        if(errorMessage != null) {
            throw Exception(errorMessage)
        }
    }

    @Test
    fun test_auth() {
        Assert.assertTrue(this.checker.isConnected())
    }

    @Test
    fun test_get_followers() {
        Assert.assertTrue(this.checker.following("kendalljenner").isNotEmpty())
    }

    @Test
    fun test_follow_back_true() {
        Assert.assertTrue(this.checker.followBack("kendalljenner", "kimkardashian"))
    }

    @Test
    fun test_follow_back_false() {
        Assert.assertFalse(this.checker.followBack("kendalljenner", "kevadams",))
    }
}