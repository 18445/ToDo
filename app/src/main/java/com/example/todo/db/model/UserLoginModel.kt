package com.example.todo.db.model

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.db.model
 * @ClassName:      UserLoginModel
 * @Author:         Yan
 * @CreateDate:     2022年04月08日 22:42:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     用户登录的信息
 */
data class UserLoginModel (
    var account : String,
    var password : String,
    var phone : String,
    var verify : String
)