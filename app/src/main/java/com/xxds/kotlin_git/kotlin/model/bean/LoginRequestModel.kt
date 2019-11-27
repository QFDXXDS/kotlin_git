package com.xxds.kotlin_git.kotlin.model.bean

import com.google.gson.annotations.SerializedName
import com.xxds.kotlin_git.BuildConfig
import java.util.*

class LoginRequestModel {

    var scopes: List<String>? = null
        private set
    var note: String? = null
        private set
    @SerializedName("client_id")
    var clientId: String? = null
        private set
    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        fun generate(): LoginRequestModel {
            val model = LoginRequestModel()
            model.scopes = Arrays.asList("user", "repo", "gist", "notifications")
            model.note = "admin_script"
            model.clientId = BuildConfig.CLIENT_ID
            model.clientSecret = BuildConfig.CLIENT_SECRET
            return model
        }
    }
}