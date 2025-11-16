package dev.bober.store.data.repository

import dev.bober.store.data.source.RemoteDataSource
import dev.bober.store.data.utils.Resource
import dev.bober.store.domain.LoginResponseModel

class OnboardingRepository(
    private val source: RemoteDataSource
) {
    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) = source.register(
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        passwordConfirmation = passwordConfirmation
    )


    suspend fun login(
        email: String,
        password: String
    ): Resource<LoginResponseModel> {
        return when (val res = source.login(
            email = email,
            password = password
        )) {
            is Resource.Error -> Resource.Error(res.code, res.error)
            is Resource.Loading -> Resource.Loading()
            is Resource.Success -> {
                Resource.Success(res.data?.toModel() ?: LoginResponseModel())
            }
        }
    }


    suspend fun confirmRegistration(
        token: String
    ) = source.confirmRegistration(
        token = token
    )


    suspend fun resendConfirmation(
        email: String
    ) = source.resendConfirmation(
        email = email
    )
}