/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Looker Data Sciences, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.looker.rtl.Transport
import com.looker.rtl.UserSession
import java.io.File
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Test as test

class TestUserSession {
    val config = TestConfig()
    val settings = config.settings
    val testSettings = config.testSettings(settings)

    @test
    fun testTestFiles() {
        assertTrue(File(config.dataFile).exists(), "${config.dataFile} should exist")
        assertTrue(File(config.localIni).exists(), "${config.localIni} should exist")
    }

    @test
    fun testIsAuthenticated() {
        val session = UserSession(settings, Transport(testSettings))
        assertFalse(session.isAuthenticated())
    }

    @test
    fun testLoginWithValidCreds() {
        val session = UserSession(settings, Transport(testSettings))
        session.login()
        assertTrue(session.isAuthenticated())
    }

    @test
    fun testUnauthenticatedLogout() {
        val session = UserSession(settings, Transport(testSettings))
        assertFalse(session.isAuthenticated())
        assertFalse(session.logout())
    }

    @test
    fun testLogsInAndOutWithGoodCreds() {
        val session = UserSession(settings, Transport(testSettings))
        assertFalse(session.isAuthenticated())
        session.login()
        assertTrue(session.isAuthenticated())
        assertTrue(session.logout())
        assertFalse(session.isAuthenticated())
    }
}
