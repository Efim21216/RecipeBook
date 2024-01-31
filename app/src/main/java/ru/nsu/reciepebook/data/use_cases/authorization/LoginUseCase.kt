package ru.nsu.reciepebook.data.use_cases.authorization

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    /*operator fun invoke(user: User): Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.login(user)
            if (response.isSuccessful && response.body() != null){
                jwtTokenManager.saveAccessJwt(response.body()!!.access_token)
                jwtTokenManager.saveRefreshJwt(response.body()!!.refresh_token)
                emit(Resource.Success(response.body()!!))
            }
            else
                emit(Resource.Error("Неверный логин или пароль"))
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            emit(Resource.Error("Технические неполадки"))
        }
    }*/
    operator fun invoke(user: User): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(AuthResponse("access", "refresh")))
    }
}