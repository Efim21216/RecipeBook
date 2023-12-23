package ru.nsu.reciepebook.data.use_cases.start

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.StartEvent
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(): Flow<StartEvent> = flow {
        try {
            val ref = jwtTokenManager.getRefreshJwt()
            val refresh = ref ?: return@flow
            val response = repository.refresh(refresh)
            if (response.isSuccessful && response.body() != null) {
                jwtTokenManager.saveAccessJwt(response.body()!!.access_token)
                jwtTokenManager.saveRefreshJwt(response.body()!!.refresh_token)
                emit(StartEvent.Success)
            }
        } catch(_: Exception) { }
        finally {
            emit(StartEvent.End)
        }
    }
}