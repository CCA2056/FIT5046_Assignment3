import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class LoginViewModel(private val prefs: PreferencesHelper) : ViewModel() {
    var isLoggedIn = mutableStateOf(false)

    fun login(email: String, password: String) {
        val (savedEmail, savedPassword) = prefs.getUser()
        isLoggedIn.value = email == savedEmail && password == savedPassword
    }

    fun requestOtp(phoneNumber: String) {
        // Implement your OTP request logic here
        // Example: set some state or send an API call to request an OTP
    }

    fun loginWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            callback(false, "Email or password cannot be empty")
            return
        }
        val (savedEmail, savedPassword) = prefs.getUser()
        if (email == savedEmail && password == savedPassword) {
            callback(true, null)  // Success, no error message needed.
        } else {
            callback(false, "Invalid email or password")  // Optionally handle incorrect inputs.
        }
    }
}


class RegisterViewModel(private val prefs: PreferencesHelper) : ViewModel() {
    var registerSuccess = mutableStateOf(false)

    fun register(userName: String, email: String, password: String, confirmPassword: String, phone: String) {
        if (password == confirmPassword) {
            prefs.saveUser(email, password)
            prefs.saveUserName(userName)  // 保存用户名
            registerSuccess.value = true
        } else {
            registerSuccess.value = false
        }
    }
}

