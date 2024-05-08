package kz.rdd.store

import android.content.SharedPreferences
import kz.rdd.core.utils.IntPreference

class UserInteractionStore(
    sharedPrefs: SharedPreferences,
) {

    var tasksAudioTooltipInitialShownCount: Int by IntPreference(
        sharedPreferences = sharedPrefs,
        key = TASKS_AUDIO_TOOLTIP_INITIAL_KEY
    )

    var tasksAudioTooltipPlayingCancelShownCount: Int by IntPreference(
        sharedPreferences = sharedPrefs,
        key = TASKS_AUDIO_TOOLTIP_PLAYING_CANCEL_KEY
    )

    companion object {
        const val TASKS_AUDIO_TOOLTIP_INITIAL_KEY = "TASKS_AUDIO_TOOLTIP_INITIAL"
        const val TASKS_AUDIO_TOOLTIP_PLAYING_CANCEL_KEY = "TASKS_AUDIO_TOOLTIP_PLAYING_CANCEL_KEY"
    }
}
