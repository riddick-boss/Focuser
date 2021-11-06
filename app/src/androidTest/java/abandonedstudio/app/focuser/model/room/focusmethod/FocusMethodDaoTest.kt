package abandonedstudio.app.focuser.model.room.focusmethod

import abandonedstudio.app.focuser.model.room.LocalDatabase
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FocusMethodDaoTest {

    private lateinit var db: LocalDatabase
    private lateinit var dao: FocusMethodDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.focusMethodDao()
    }

    @After
    fun shutdown() {
        db.close()
    }

    @Test
    fun getAllMethodsCorrectly() = runBlockingTest {
        val method1 = FocusMethod("name1")
        dao.insert(method1)
        val method2 = FocusMethod("name2")
        dao.insert(method2)
        val method3 = FocusMethod("name3")
        dao.insert(method3)
        val allMethods = dao.getAllMethods()
        Truth.assertThat(allMethods).isEqualTo(
            listOf(
                FocusMethod("name1", 1),
                FocusMethod("name2", 2),
                FocusMethod("name3", 3)
            )
        )
    }

    @Test
    fun getAllMethodsWithoutFavouriteCorrectly() = runBlockingTest {
        val method1 = FocusMethod("name1", 1)
        dao.insert(method1)
        val method2 = FocusMethod("name2", 2)
        dao.insert(method2)
        val method3 = FocusMethod("name3", 3)
        dao.insert(method3)
        val allMethodsWithoutFav = dao.getAllMethodsWithoutFavourite(2)
        Truth.assertThat(allMethodsWithoutFav).isEqualTo(listOf(method1, method3))
    }
}
