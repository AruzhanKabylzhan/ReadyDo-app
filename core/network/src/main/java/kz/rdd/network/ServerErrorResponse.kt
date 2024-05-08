package kz.rdd.core.network

import com.google.gson.annotations.SerializedName
import kz.rdd.core.utils.outcome.Outcome

class ServerErrorResponse(
    @SerializedName("message", alternate = ["detail"]) val message: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("status") val status: String?,
)
