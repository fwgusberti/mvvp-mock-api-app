package br.com.nerdrapido.mvvmmockapiapp.data.model

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import br.com.nerdrapido.mvvmmockapiapp.di.MainModule
import br.com.nerdrapido.mvvmmockapiapp.helpers.json.JsonMapper
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.date
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.description
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.eventId
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.eventJson
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.image
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.latitude
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.longitude
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.price
import br.com.nerdrapido.mvvmmockapiapp.remote.model.RemoteModelMock.title
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import java.io.InputStream

/**
 * Created By FELIPE GUSBERTI @ 08/08/2020
 */
@RunWith(RobolectricTestRunner::class)
class EventDataTest : KoinTest {

    private val context = ApplicationProvider.getApplicationContext<Application>()

    private val jsonMapper: JsonMapper by inject()

    @Before
    fun setUp() {
        startKoin {
            androidContext(context)
            modules(
                MainModule.module
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test item construction`() {
        val itemResponse = EventData(
            date,
            description,
            image,
            longitude,
            latitude,
            price,
            title,
            eventId,
            emptyList(),
            emptyList()
        )
        Assert.assertEquals(date, itemResponse.date)
        Assert.assertEquals(description, itemResponse.description)
        Assert.assertEquals(image, itemResponse.image)
        Assert.assertEquals(longitude, itemResponse.longitude, 0.0000001)
        Assert.assertEquals(latitude, itemResponse.latitude, 0.0000001)
        Assert.assertEquals(price, itemResponse.price)
        Assert.assertEquals(title, itemResponse.title)
        Assert.assertEquals(eventId, itemResponse.id)
        Assert.assertEquals(0, itemResponse.cupons.count())
        Assert.assertEquals(0, itemResponse.people.count())
    }

    @Test
    fun `test item default construction`() {
        val itemResponse = EventData(
            date,
            description,
            image,
            longitude,
            latitude,
            price,
            title,
            eventId
        )
        Assert.assertEquals(date, itemResponse.date)
        Assert.assertEquals(description, itemResponse.description)
        Assert.assertEquals(image, itemResponse.image)
        Assert.assertEquals(longitude, itemResponse.longitude, 0.0000001)
        Assert.assertEquals(latitude, itemResponse.latitude, 0.0000001)
        Assert.assertEquals(price, itemResponse.price)
        Assert.assertEquals(title, itemResponse.title)
        Assert.assertEquals(eventId, itemResponse.id)
        Assert.assertEquals(0, itemResponse.cupons.count())
        Assert.assertEquals(0, itemResponse.people.count())
    }
}