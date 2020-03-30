package com.deividasstr.data.store

import com.deividasstr.data.Database
import com.deividasstr.testutils.UnitTest
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.Before
import java.io.IOException

abstract class AbstractDbTest : UnitTest() {

    protected lateinit var baseDb: Database

    @Before
    @Throws(IOException::class)
    fun setUp() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        baseDb = Database(driver)
    }
}
