package ru.nsu.reciepebook.data.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(user: User): Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.register(user)
            if (response.isSuccessful && response.body() != null) {
                jwtTokenManager.saveAccessJwt(response.body()!!.access_token)
                jwtTokenManager.saveRefreshJwt(response.body()!!.refresh_token)
                emit(Resource.Success(response.body()!!))
            }

            else {
                val body = response.errorBody()?.string()
                if (body != null) {
                    if (body.contains("email"))
                        emit(Resource.Error("Неверная почта"))
                    else if (body.contains("password"))
                        emit(Resource.Error("Некорректный пароль"))
                    else
                        emit(Resource.Error("Логин уже занят"))
                }
            }
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            emit(Resource.Error("Технические неполадки"))
        }
    }
}