package com.example.qrstockmateapp.navigation.repository

import com.example.qrstockmateapp.api.models.Company
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.models.Warehouse

object DataRepository {
    private var user: User? = null
    private var company: Company? =null
    private var warehouses: List<Warehouse>? = null
    private var employees: List<User>? = null
    private var token: String = ""

    fun setEmployees(newEmployees: List<User>) {
        employees= newEmployees
    }

    fun getEmployees(): List<User>?{
        return employees
    }
    fun setWarehouses(newWarehouses: List<Warehouse>) {
        warehouses= newWarehouses
    }

    fun getWarehouses(): List<Warehouse>?{
        return warehouses
    }

    fun setCompany(newCompany: Company) {
        company = newCompany
    }

    fun getCompany(): Company?{
        return company
    }

    fun setToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String {
        return token
    }

    fun setUser(newUser: User) {
        user = newUser
    }

    fun getUser(): User? {
        return user
    }


    fun LogOut(){
        user = null
        company = null
        token = ""
        warehouses = null
        employees = null
    }

}
