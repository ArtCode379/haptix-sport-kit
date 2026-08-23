package haptixgolf.sports.haptixsportkit.data.repository

import haptixgolf.sports.haptixsportkit.data.datastore.YJIJWOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class YJIJWOnboardingRepo(
    private val yjijwOnboardingStoreManager: YJIJWOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return yjijwOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            yjijwOnboardingStoreManager.setOnboardedState(state)
        }
    }
}