package com.xxds.kotlin_git.kotlin.model.bean

class PushEventCommit {

    var sha: String? = null
    //email&name
    var author: User? = null
    var message: String? = null
    var distinct: Boolean = false
    var url: String? = null


}