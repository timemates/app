# CredentialsStorage Library Documentation

The **CredentialsStorage** library simplifies storing and retrieving credentials in Kotlin applications while offering a seamless interface for different platforms. The library leverages Windows Credential Manager on Windows and Keychain Services on macOS to ensure secure storage of sensitive information. This guide will help you integrate and use the library effectively in your projects.

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Storing and Retrieving Credentials](#storing-and-retrieving-credentials)
- [Default Values and Extensions](#default-values-and-extensions)
- [Platform Compatibility](#platform-compatibility)
- [References](#references)

## Introduction

The **CredentialsStorage** library provides a uniform API to store and access credentials securely across various platforms. By abstracting the platform-specific implementations of Windows Credential Manager and macOS Keychain Services, the library ensures that your credentials are managed in a consistent and safe manner.

## Getting Started

1. **Initialize CredentialsStorage**: Create an instance of the `CredentialsStorage` interface based on the current platform. The `ofCurrentPlatform` function automatically detects the platform and returns the appropriate storage instance:

    ```kotlin
    val credentialsStorage = CredentialsStorage.ofCurrentPlatform("myAppName")
    ```

   Replace `"myAppName"` with your application's name, which acts as a namespace to prevent conflicts with other applications.

## Storing and Retrieving Credentials

Use the `CredentialsStorage` methods to store and retrieve credentials:

- Storing credentials:

    ```kotlin
    credentialsStorage.setString("username", "john_doe")
    credentialsStorage.setInt("user_id", 123)
    credentialsStorage.setLong("last_login", System.currentTimeMillis())
    credentialsStorage.setBoolean("remember_me", true)
    ```

- Retrieving credentials:

    ```kotlin
    val username = credentialsStorage.getString("username")
    val userId = credentialsStorage.getInt("user_id")
    val lastLogin = credentialsStorage.getLong("last_login")
    val rememberMe = credentialsStorage.getBoolean("remember_me")
    ```

## Default Values and Extensions

The library provides convenient extensions to handle default values for cases when credentials are not present:

- Retrieving credentials with default values:

    ```kotlin
    val defaultUsername = credentialsStorage.getStringOrDefault("username") { "guest" }
    val defaultUserId = credentialsStorage.getIntOrDefault("user_id") { -1 }
    ```

- Retrieving credentials and setting default values if absent:

    ```kotlin
    val username = credentialsStorage.getStringOrSet("username") { "john_doe" }
    val userId = credentialsStorage.getIntOrSet("user_id") { 123 }
    ```

## Platform Compatibility

The library ensures compatibility across platforms with automatic detection of the operating system. The appropriate storage mechanism is selected based on the following:

- On Windows: Windows Credential Manager
- On macOS: Keychain Services

This guarantees consistent and secure credential management regardless of the platform.

## References

For more details on Windows Credential Manager and macOS Keychain Services, refer to the official documentation:

- [Windows Credential Manager](https://docs.microsoft.com/en-us/windows/win32/secauthn/credential-manager)
- [macOS Keychain Services](https://developer.apple.com/documentation/security/keychain_services)

If the platform is not supported or you encounter any issues, exceptions will be thrown, ensuring a smooth and predictable experience. Feel free to reach out for further assistance or questions!
