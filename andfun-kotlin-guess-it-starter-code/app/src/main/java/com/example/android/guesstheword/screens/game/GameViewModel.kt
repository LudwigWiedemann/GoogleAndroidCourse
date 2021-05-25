package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel: ViewModel() {

    enum class BuzzType(val pattern: LongArray){
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object {
        // This is when the game is over
        private const val DONE = 0L

        //number of milliseconds in one se
        private const val ONE_SECOND = 1000L

        // total time of the game
        private const val COUNTDOWN_TIME = 10000L

        private const val COUNTDOWN_PANIC_SECONDS = 4L
    }

    private val timer: CountDownTimer

    // the current time the timer holds
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    private val _buzzEvent = MutableLiveData<BuzzType>()
    val buzzEvent: LiveData<BuzzType>
        get() = _buzzEvent

    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // LivaData that tells when the game is finished
    private val _eventFameFinish =  MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventFameFinish

    // the list of words
    private lateinit var wordList: MutableList<String>

    init  {
        resetList()
        nextWord()
        _score.value = 0
        _currentTime.value = COUNTDOWN_TIME / 1000
        // creates a timer that triggers the end of the game
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = currentTime.value?.minus(1)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _buzzEvent.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                if(_currentTime.value == DONE) {
                    _eventFameFinish.value = true
                }
            }
        }
        timer.start()

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFInishComplete() {
        _eventFameFinish.value = false
    }

    fun onBuzzComplete() {
        _buzzEvent.value = BuzzType.NO_BUZZ
    }
}