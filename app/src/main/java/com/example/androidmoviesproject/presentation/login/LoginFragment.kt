package com.example.androidmoviesproject.presentation.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.databinding.FragmentLoginBinding
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        findNavController().clearBackStack(R.id.homeFragment)
    }

    private fun authorizeTokenId(tokenId: String) {
        viewModel.firebaseAuthWithGoogle(
            idToken = tokenId,
            success = {
                if (it != null) {
                    statusLogin(
                        isSuccessful = true,
                        additionalMessage = it.displayName
                    )
                }

                val navController = findNavController()
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            },
            failure = { message: String? ->
                Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun getCredentialToken(
        onGetCredentialSuccess: (String) -> Unit,
        onGetCredentialFailure: (Exception) -> Unit = {},
    ) {
        val context = requireContext()
        val credentialManager = CredentialManager.create(context = context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .setAutoSelectEnabled(true)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest
            .Builder()
            .setPreferImmediatelyAvailableCredentials(true)
            .setPreferIdentityDocUi(true)
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                when (val credential = result.credential) {
                    is PublicKeyCredential -> {}

                    is PasswordCredential -> {}

                    is CustomCredential -> {
                        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            try {
                                val googleIdTokenCredential =
                                    GoogleIdTokenCredential.createFrom(credential.data)
                                Log.d(
                                    AUTHENTICATION_LOGGER,
                                    """
                                        LoginScreen - handlerLogin - googleIdTokenCredential:
                                        id: ${googleIdTokenCredential.id}
                                        idToken: ${googleIdTokenCredential.idToken}
                                        type: ${googleIdTokenCredential.type}
                                        displayName: ${googleIdTokenCredential.displayName}
                                        profilePictureUri: ${googleIdTokenCredential.profilePictureUri}
                                        """.trimIndent()
                                )

                                onGetCredentialSuccess(googleIdTokenCredential.idToken)
                            } catch (e: GoogleIdTokenParsingException) {
                                Log.e(AUTHENTICATION_LOGGER, "Received an invalid google id token response",)
                            }
                        } else {
                            Log.e(AUTHENTICATION_LOGGER, "Unexpected type of credential")
                        }
                    }

                    else -> {
                        Log.e(AUTHENTICATION_LOGGER, "Unexpected type of credential")
                    }
                }
            } catch (ex: GetCredentialException) {
                if (ex !is GetCredentialCancellationException) {
                    Log.e(AUTHENTICATION_LOGGER, "Can't login", ex)

                    val intent = Intent(Settings.ACTION_ADD_ACCOUNT)
                    intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                    context.startActivity(intent)
                }
                onGetCredentialFailure(ex)
            }
        }
    }

    private fun statusLogin(isSuccessful: Boolean, additionalMessage: String? = null) {
        val baseMessage = if (isSuccessful) {
            getString(R.string.login_success)
        } else getString(R.string.login_failure)
        val statusMessage = additionalMessage?.let { "$baseMessage $it" } ?: baseMessage

        Toast.makeText(requireContext(), statusMessage, Toast.LENGTH_SHORT).show()
    }

    private fun signInWithGoogle() {
        if (networkStatus.isOnline()) {
            getCredentialToken(
                onGetCredentialSuccess = {
                    authorizeTokenId(tokenId = it)
                },
                onGetCredentialFailure = {

                },
            )
        } else Toast.makeText(requireContext(), getString(R.string.all_reconnect_network), Toast.LENGTH_SHORT).show()
    }

    private fun setUp() {
        binding.btnGoogle.setOnClickListener {
            isOnline(online = { signInWithGoogle() })
        }

        binding.goToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }
        buttonEnable()

        binding.inputAccount.addTextChangedListener {
            buttonEnable()
        }

        binding.inputPassword.addTextChangedListener {
            buttonEnable()
        }

        binding.btnLogin.setOnClickListener {
            isOnline(
                online = {
                    val account = Account(
                        userName = binding.inputAccount.text.toString(),
                        passWord = binding.inputPassword.text.toString()
                    )

                    viewModel.loginAccount(
                        account = account,
                        success = {
                            if (it != null) {
                                statusLogin(
                                    isSuccessful = true,
                                    additionalMessage = it.displayName
                                )
                            }

                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        },
                        failure = { statusLogin(false) }
                    )
                }
            )
        }
    }

    private fun buttonEnable() {
        val isEnableLoginButton = !(binding.inputAccount.text.toString()
            .isBlank() || binding.inputPassword.text.toString().isBlank())

        binding.btnLogin.isEnabled = isEnableLoginButton
        val color = if (isEnableLoginButton) {
            ContextCompat.getColor(requireContext(), R.color.button_login)
        } else ContextCompat.getColor(requireContext(), R.color.gray_color)

        binding.btnLogin.setBackgroundColor(color)
    }

    private inline fun isOnline(
        crossinline online: () -> Unit = {},
        crossinline offline: () -> Unit = { Toast.makeText(requireContext(), getString(R.string.all_reconnect_network), Toast.LENGTH_SHORT).show() }
    ) {
        if (networkStatus.isOnline()) {
            online.invoke()
        } else offline.invoke()
    }

    companion object {
        private const val AUTHENTICATION_LOGGER = "AUTHENTICATION_LOGGER"
    }
}