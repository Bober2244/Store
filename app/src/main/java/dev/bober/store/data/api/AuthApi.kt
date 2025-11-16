package dev.bober.store.data.api

import dev.bober.store.data.dto.auth.LoginBodyDto
import dev.bober.store.data.dto.auth.LoginResponseDto
import dev.bober.store.data.dto.auth.RegisterBodyDto
import dev.bober.store.data.dto.auth.ResendConfirmationBodyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/api/auth/register")
    suspend fun register(
        @Body body: RegisterBodyDto,
    ): Response<Unit>

    @POST("/api/auth/login")
    suspend fun login(
        @Body body: LoginBodyDto
    ): Response<LoginResponseDto>

    @GET("/api/auth/confirm-email")
    suspend fun confirmEmail(
        @Query("token") token: String
    ): Response<Unit>

    @POST("/api/auth/resend-confirmation")
    suspend fun resendConfirmation(
        @Body body: ResendConfirmationBodyDto
    ): Response<Unit>
}
