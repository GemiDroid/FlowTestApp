package com.meinvodafone.testflow.data.repository.remote

import com.meinvodafone.testflow.data.models.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RemoteRepositoryImplTest {

    // It acts your dataSource (Emitter/Producer)..
    private fun getData() = flow {
        val movieList = listOf(
            Movie(title = "Movie0", details = "Movie0 Movie0", publishDate = "1/0/2000"),
            Movie(title = "Movie1", details = "Movie1 Movie1", publishDate = "1/1/2000"),
            Movie(title = "Movie2", details = "Movie2 Movie2", publishDate = "1/2/2000"),
            Movie(title = "Movie3", details = "Movie3 Movie3", publishDate = "1/3/2000"),
            Movie(title = "Movie4", details = "Movie4 Movie4", publishDate = "1/4/2000"),
            Movie(title = "Movie5", details = "Movie5 Movie5", publishDate = "1/5/2000"),
            Movie(title = "Movie6", details = "", publishDate = "1/6/2000")
        )
        emit(movieList)
        _sharedFlow.tryEmit(movieList)
    }

    private val _sharedFlow = MutableSharedFlow<List<Movie>>(
        replay = 1,
        DEFAULT_BUFFER_SIZE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val itemSharedFlow: SharedFlow<List<Movie>> = _sharedFlow

    /*
     * It acts as collector (Consumer)
     **/
    @ExperimentalCoroutinesApi
    @Test
    fun emitItems() {
        runBlockingTest {
            getData().shareIn(
                    TestCoroutineScope(),
                    SharingStarted.WhileSubscribed(),
                    replay = 1)
                .map {
                    // Dummy operation on list..
                    it.filter { movie -> !movie.details.isNullOrEmpty() }
                }
                .catch {
                    // Do your error handling work..
                }
                .collect {
                    println("List items: ${it.count()}")
                    it.forEach { movie ->
                        println(movie.toString())
                    }
                }
        }
    }

    // It fails with "This job has not completed yet" (coroutine live issue)
    // https://github.com/Kotlin/kotlinx.coroutines/issues/1204
    @ExperimentalCoroutinesApi
    @Test
    fun sharedFlow() {
        runBlockingTest {
            itemSharedFlow.collect {
                it.forEach { movie ->
                    println(movie.details)
                }
            }
        }
    }
}