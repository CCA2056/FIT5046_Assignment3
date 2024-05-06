import android.content.Context

class PreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, password: String) {
        sharedPreferences.edit().apply {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    fun getUser(): Pair<String?, String?> {
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        return Pair(email, password)
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveUserName(userName: String) {
        sharedPreferences.edit().putString("userName", userName).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString("userName", "") ?: ""
    }
}
