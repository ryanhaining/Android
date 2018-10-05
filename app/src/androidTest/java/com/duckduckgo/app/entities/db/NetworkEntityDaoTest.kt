/*
 * Copyright (c) 2018 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.entities.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.duckduckgo.app.global.db.AppDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NetworkEntityDaoTest {

    @get:Rule
    @Suppress("unused")
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: NetworkEntityDao

    @Before
    fun before() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.networkEntityDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun testWhenNetworkEntityAddedThenItIsSaved() {
        val entity = NetworkEntity("entity", domainName = "domain")
        dao.insert(entity)
        assertEquals(arrayOf(entity).asList(), dao.getAll())
    }

    @Test
    fun testWhenDatabaseIsNewThenItIsEmpty() {
        assertTrue(dao.getAll().isEmpty())
    }

}